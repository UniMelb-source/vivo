/* $This file is distributed under the terms of the license in /doc/license.txt$ */
package edu.cornell.mannlib.vivo.auth.policy;

import com.hp.hpl.jena.ontology.OntModel;
import edu.cornell.mannlib.vitro.webapp.auth.policy.ServletPolicyList;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HasInvestigatorRoleRelationshipPolicy extends BaseRelationshipPolicy {

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
    protected List<String> getAssociatedPrincipalUris(String resourceUri) {
        return getObjectsOfLinkedProperty(resourceUri, URI_CONTRIBUTING_ROLE_PROPERTY, URI_INVESTIGATOR_ROLE_OF_PROPERTY);
    }

    @Override
    protected boolean isTypeOrSubType(String resourceUri) {
        return isResourceOfType(resourceUri, URI_AGREEMENT_TYPE)
                || isResourceOfType(resourceUri, URI_CONTRACT_TYPE)
                || isResourceOfType(resourceUri, URI_GRANT_TYPE);
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
