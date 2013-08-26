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

public class HasInvestigatorRoleRelationshipPolicy extends AbstractRelationshipPolicy implements PolicyIface {

    private static final Log log = LogFactory.getLog(HasInvestigatorRoleRelationshipPolicy.class);
    private static final String VIVO_CORE = "http://vivoweb.org/ontology/core#";
    private static final String URI_CONTRIBUTING_ROLE_PROPERTY = VIVO_CORE + "contributingRole";
    private static final String URI_INVESTIGATOR_ROLE_OF_PROPERTY = VIVO_CORE + "investigatorRoleOf";
    private static final String URI_AGREEMENT_TYPE = VIVO_CORE + "Agreement";
    private static final String URI_CONTRACT_TYPE = VIVO_CORE + "Contract";
    private static final String URI_GRANT_TYPE = VIVO_CORE + "Grant";

    public HasInvestigatorRoleRelationshipPolicy(ServletContext ctx, OntModel model) {
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

    private List<String> getUrisOfHasInvestigatorRoles(String resourceUri) {
        return getObjectsOfLinkedProperty(resourceUri, URI_CONTRIBUTING_ROLE_PROPERTY, URI_INVESTIGATOR_ROLE_OF_PROPERTY);
    }

    private PolicyDecision isAuthorized(IdentifierBundle whoToAuth, DistilledAction action) {
        List<String> userUris = new ArrayList<String>(HasAssociatedIndividual.getIndividualUris(whoToAuth));

        if (userUris.isEmpty()) {
            return inconclusiveDecision("No user to check has investigator role authorisation.");
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
            if (isResourceOfType(resourceUri, URI_AGREEMENT_TYPE)
                    || isResourceOfType(resourceUri, URI_CONTRACT_TYPE)
                    || isResourceOfType(resourceUri, URI_GRANT_TYPE)) {
                if (anyUrisInCommon(userUris, getUrisOfHasInvestigatorRoles(resourceUri))) {
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
            ServletPolicyList.addPolicy(ctx, new HasInvestigatorRoleRelationshipPolicy(ctx, ontModel));
        }

        @Override
        public void contextDestroyed(ServletContextEvent sce) { /* nothing */

        }
    }
}
