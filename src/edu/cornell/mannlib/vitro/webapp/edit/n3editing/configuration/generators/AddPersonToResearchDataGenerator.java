package edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.generators;

import edu.cornell.mannlib.vitro.webapp.controller.VitroRequest;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author tom
 */
public class AddPersonToResearchDataGenerator extends AddPersonToThingGenerator {

    private static final Log log = LogFactory.getLog(AddPersonToResearchDataGenerator.class);

    @Override
    protected Log getLog() {
        return log;
    }

    @Override
    protected String getTemplate() {
        return "addPersonToThing.ftl";
    }

    @Override
    protected final List<String> getN3Required() {
        return list(N3_PREFIX
                + "?researchData ands:relatedInformationResource ?personUri. \n"
                + "?personUri ands:relatedResearchData ?researchData. \n"
                + "?personUri a foaf:Person ;");
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
        return "personUri";
    }

    @Override
    protected final Map<String, String> getNewResources(VitroRequest vreq) {
        return Collections.<String, String>emptyMap();
    }
}
