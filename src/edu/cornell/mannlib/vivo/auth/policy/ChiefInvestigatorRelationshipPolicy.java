/* $This file is distributed under the terms of the license in /doc/license.txt$ */
package edu.cornell.mannlib.vivo.auth.policy;

import com.hp.hpl.jena.ontology.OntModel;
import edu.cornell.mannlib.vitro.webapp.auth.identifier.IdentifierBundle;
import edu.cornell.mannlib.vitro.webapp.auth.identifier.common.HasAssociatedIndividual;
import edu.cornell.mannlib.vitro.webapp.auth.policy.ServletPolicyList;
import edu.cornell.mannlib.vitro.webapp.auth.policy.ifaces.PolicyDecision;
import edu.cornell.mannlib.vitro.webapp.auth.policy.ifaces.PolicyIface;
import edu.cornell.mannlib.vitro.webapp.auth.policy.specialrelationships.AbstractRelationshipPolicy;
import edu.cornell.mannlib.vitro.webapp.auth.requestedAction.ifaces.RequestedAction;
import edu.cornell.mannlib.vitro.webapp.auth.requestedAction.propstmt.AbstractDataPropertyAction;
import edu.cornell.mannlib.vitro.webapp.auth.requestedAction.propstmt.AbstractObjectPropertyAction;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ChiefInvestigatorRelationshipPolicy extends AbstractRelationshipPolicy implements PolicyIface {

    private static final Log log = LogFactory.getLog(ChiefInvestigatorRelationshipPolicy.class);
    private static final String NS_CORE = "http://vivoweb.org/ontology/core#";
    private static final String ANDS_CORE = "http://purl.org/ands/ontologies/vivo/";
    private static final String URI_CI_PROPERTY = ANDS_CORE + "hasCollector";
    private static final String URI_CI_OF_PROPERTY = ANDS_CORE + "isCollectorOf";
    private static final String URI_RESEARCH_DATA_TYPE = ANDS_CORE + "ResearchData";
    private static final String URI_RESEARCH_CATALOG_TYPE = ANDS_CORE + "ResearchCatalog";
    private static final String URI_RESEARCH_COLLECTION_TYPE = ANDS_CORE + "ResearchCollection";
    private static final String URI_RESEARCH_DATASET_TYPE = ANDS_CORE + "ResearchDataSet";
    private static final String URI_RESEARCH_RECORDS_COLLECTION_TYPE = ANDS_CORE + "ResearchRecordsCollection";
    private static final String URI_RESEARCH_REGISTRY_TYPE = ANDS_CORE + "ResearchRegistry";
    private static final String URI_RESEARCH_REPOSITORY_TYPE = ANDS_CORE + "ResearchRepository";

    public ChiefInvestigatorRelationshipPolicy(ServletContext ctx, OntModel model) {
        super(ctx, model);
    }

    @Override
    public PolicyDecision isAuthorized(IdentifierBundle whoToAuth, RequestedAction whatToAuth) {
        PolicyDecision decision;
        if (whatToAuth == null) {
            decision = inconclusiveDecision("whatToAuth was null");
        } else if (whatToAuth instanceof AbstractDataPropertyAction) {
            decision = isAuthorized(whoToAuth, distill((AbstractDataPropertyAction) whatToAuth));
        } else if (whatToAuth instanceof AbstractObjectPropertyAction) {
            decision = isAuthorized(whoToAuth, distill((AbstractObjectPropertyAction) whatToAuth));
        } else {
            decision = inconclusiveDecision("Does not authorize " + whatToAuth.getClass().getSimpleName() + " actions");
        }

        if (decision == null) {
            return userNotAuthorizedToStatement();
        } else {
            return decision;
        }
    }

    private List<String> getUrisOfChiefInvestigators(String resourceUri) {
        return getObjectsOfProperty(resourceUri, URI_CI_PROPERTY);
    }

    private PolicyDecision isAuthorized(IdentifierBundle whoToAuth, DistilledAction action) {
        List<String> userUris = new ArrayList<String>(HasAssociatedIndividual.getIndividualUris(whoToAuth));

        if (userUris.isEmpty()) {
            return inconclusiveDecision("No user to check chief-investigator authorisation.");
        }

        if (!canModifyPredicate(action.predicateUri)) {
            return cantModifyPredicate(action.predicateUri);
        }

        for (String resourceUri : action.resourceUris) {
            if (!canModifyResource(resourceUri)) {
                return cantModifyResource(resourceUri);
            }
        }

        for (String resourceUri : action.resourceUris) {
            if (isResourceOfType(resourceUri, URI_RESEARCH_DATA_TYPE)
                    || isResourceOfType(resourceUri, URI_RESEARCH_CATALOG_TYPE)
                    || isResourceOfType(resourceUri, URI_RESEARCH_COLLECTION_TYPE)
                    || isResourceOfType(resourceUri, URI_RESEARCH_DATASET_TYPE)
                    || isResourceOfType(resourceUri, URI_RESEARCH_RECORDS_COLLECTION_TYPE)
                    || isResourceOfType(resourceUri, URI_RESEARCH_REGISTRY_TYPE)
                    || isResourceOfType(resourceUri, URI_RESEARCH_REPOSITORY_TYPE)) {
                if (anyUrisInCommon(userUris, getUrisOfChiefInvestigators(resourceUri))) {
                    return authorizedCI(resourceUri);
                }
            }
        }

        return userNotAuthorizedToStatement();
    }

    private DistilledAction distill(AbstractDataPropertyAction action) {
        return new DistilledAction(action.getPredicateUri(), action.getSubjectUri());
    }

    private DistilledAction distill(AbstractObjectPropertyAction action) {
        return new DistilledAction(action.uriOfPredicate, action.uriOfSubject, action.uriOfObject);
    }

    private PolicyDecision authorizedCI(String resourceUri) {
        return authorizedDecision("User is Chief Investigator of " + resourceUri);
    }

    static class DistilledAction {

        final String[] resourceUris;
        final String predicateUri;

        public DistilledAction(String predicateUri, String... resourceUris) {
            this.resourceUris = resourceUris;
            this.predicateUri = predicateUri;
        }
    }

    public static class Setup implements ServletContextListener {

        @Override
        public void contextInitialized(ServletContextEvent sce) {
            ServletContext ctx = sce.getServletContext();
            OntModel ontModel = (OntModel) sce.getServletContext().getAttribute("jenaOntModel");
            ServletPolicyList.addPolicy(ctx, new ChiefInvestigatorRelationshipPolicy(ctx, ontModel));
        }

        @Override
        public void contextDestroyed(ServletContextEvent sce) { /* nothing */

        }
    }
}
