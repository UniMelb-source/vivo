package edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.generators;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author tom
 */
public class AddAgreementToResearchDataGenerator extends AddAgreementToThingGenerator {

    private static final Log log = LogFactory.getLog(AddAgreementToResearchDataGenerator.class);

    @Override
    protected Log getLog() {
        return log;
    }

    @Override
    protected String getTemplate() {
        return "addAgreementToThing.ftl";
    }

    @Override
    protected final List<String> getN3Required() {
        return list(N3_PREFIX
                + "?researchData unimelb-rdr:isResearchDataForAgreement ?agreementUri. \n"
                + "?agreementUri unimelb-rdr:agreementHasResearchDataOutput ?researchData. \n"
                + "?agreementUri a core:Agreement ;");
    }

    @Override
    protected String getSubjectName() {
        return "researchData";
    }

    @Override
    protected String getPredicateName() {
        return "predicate";
    }

    @Override
    protected String getObjectName() {
        return "agreementUri";
    }
}
