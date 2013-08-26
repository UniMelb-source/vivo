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

public class ChiefInvestigatorRelationshipPolicy extends BaseRelationshipPolicy {

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
    protected List<String> getAssociatedPrincipalUris(String resourceUri) {
        return getObjectsOfProperty(resourceUri, URI_CI_PROPERTY);
    }

    @Override
    protected boolean isTypeOrSubType(String resourceUri) {
        return isResourceOfType(resourceUri, URI_RESEARCH_DATA_TYPE)
                || isResourceOfType(resourceUri, URI_RESEARCH_CATALOG_TYPE)
                || isResourceOfType(resourceUri, URI_RESEARCH_COLLECTION_TYPE)
                || isResourceOfType(resourceUri, URI_RESEARCH_DATASET_TYPE)
                || isResourceOfType(resourceUri, URI_RESEARCH_RECORDS_COLLECTION_TYPE)
                || isResourceOfType(resourceUri, URI_RESEARCH_REGISTRY_TYPE)
                || isResourceOfType(resourceUri, URI_RESEARCH_REPOSITORY_TYPE);
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
