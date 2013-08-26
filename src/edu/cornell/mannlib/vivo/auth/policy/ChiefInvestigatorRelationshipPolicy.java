/* $This file is distributed under the terms of the license in /doc/license.txt$ */
package edu.cornell.mannlib.vivo.auth.policy;

import com.hp.hpl.jena.ontology.OntModel;
import edu.cornell.mannlib.vitro.webapp.auth.policy.ServletPolicyList;
import java.util.Arrays;
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
    private List<String> typeList = Arrays.<String>asList(ANDS_CORE + "ResearchData",
            ANDS_CORE + "ResearchCatalog",
            ANDS_CORE + "ResearchCollection",
            ANDS_CORE + "ResearchDataSet",
            ANDS_CORE + "ResearchRecordsCollection",
            ANDS_CORE + "ResearchRegistry",
            ANDS_CORE + "ResearchRepository");

    public ChiefInvestigatorRelationshipPolicy(ServletContext ctx, OntModel model) {
        super(ctx, model);
    }

    @Override
    protected List<String> getAssociatedPrincipalUris(String resourceUri) {
        return getObjectsOfProperty(resourceUri, URI_CI_PROPERTY);
    }

    @Override
    protected List<String> getAssociatedTypes() {
        return typeList;
    }

    @Override
    protected String getRelationshipName() {
        return "Chief Investigator";
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
