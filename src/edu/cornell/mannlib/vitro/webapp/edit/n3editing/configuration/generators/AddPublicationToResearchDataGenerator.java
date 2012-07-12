package edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.generators;

import java.util.List;
import java.util.HashMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import edu.cornell.mannlib.vitro.webapp.controller.VitroRequest;

/**
 *
 * @author tom
 */
public class AddPublicationToResearchDataGenerator extends AddPublicationToThingGenerator {

    private static final Log log = LogFactory.getLog(AddPublicationToResearchDataGenerator.class);
    
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
                + "?researchData ands:relatedInformationResource ?publicationUri. \n"
                + "?publicationUri ands:relatedResearchData ?researchData. \n"
                + "?publicationUri a core:InformationResource ;");
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
        return "publicationUri";
    }
}
