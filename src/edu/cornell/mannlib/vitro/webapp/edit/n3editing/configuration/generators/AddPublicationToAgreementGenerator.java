package edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.generators;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author tom
 */
public class AddPublicationToAgreementGenerator extends AddPublicationToThingGenerator {

    private static final Log log = LogFactory.getLog(AddPublicationToAgreementGenerator.class);

    @Override
    protected Log getLog() {
        return log;
    }

    @Override
    protected String getTemplate() {
        return "addPublicationToThing.ftl";
    }

    @Override
    protected final List<String> getN3Required() {
        return list(N3_PREFIX
                + "?agreement core:supportedInformationResource ?publicationUri. \n"
                + "?publicationUri core:informationResourceSupportedBy ?agreement. \n"
                + "?publicationUri a core:InformationResource ;");
    }

    @Override
    protected String getSubjectName() {
        return "agreement";
    }

    @Override
    protected String getPredicateName() {
        return "predicate";
    }

    @Override
    protected String getObjectName() {
        return "publicationUri";
    }
}
