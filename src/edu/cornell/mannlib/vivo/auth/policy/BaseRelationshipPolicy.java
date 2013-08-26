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
import java.util.Collections;
import java.util.List;
import javax.servlet.ServletContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BaseRelationshipPolicy extends AbstractRelationshipPolicy implements PolicyIface {

    private static final Log log = LogFactory.getLog(BaseRelationshipPolicy.class);

    public BaseRelationshipPolicy(ServletContext ctx, OntModel model) {
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

    protected List<String> getAssociatedPrincipalUris(String resourceUri) {
        return Collections.<String>emptyList();
    }

    protected List<String> getAssociatedTypes() {
        return Collections.<String>emptyList();
    }

    private boolean isTypeOrSubType(String resourceUri) {
        for (String typeUri : getAssociatedTypes()) {
            if (isResourceOfType(resourceUri, typeUri)) {
                return true;
            }
        }
        return false;
    }

    protected String relationshipName() {
        return "relationship";
    }

    private PolicyDecision isAuthorized(IdentifierBundle whoToAuth, DistilledAction action) {
        List<String> userUris = new ArrayList<String>(HasAssociatedIndividual.getIndividualUris(whoToAuth));

        if (userUris.isEmpty()) {
            return inconclusiveDecision("No user to check " + relationshipName() + "authorisation.");
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
            if (isTypeOrSubType(resourceUri)) {
                if (anyUrisInCommon(userUris, getAssociatedPrincipalUris(resourceUri))) {
                    return authorizedDecision("User has valid " + relationshipName() + " for " + resourceUri);
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

    static class DistilledAction {

        final String[] resourceUris;
        final String predicateUri;

        public DistilledAction(String predicateUri, String... resourceUris) {
            this.resourceUris = resourceUris;
            this.predicateUri = predicateUri;
        }
    }
}
