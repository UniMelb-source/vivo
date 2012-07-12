package edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.generators;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import edu.cornell.mannlib.vitro.webapp.controller.VitroRequest;

/**
 *
 * @author tom
 */
public class AddPersonToPublicationGenerator extends AddPersonToThingGenerator {

    private static final Log log = LogFactory.getLog(AddPersonToPublicationGenerator.class);

    @Override
    protected final Log getLog() {
        return log;
    }

    @Override
    protected final String getTemplate() {
        return "addPersonToPublication.ftl";
    }

    @Override
    protected final List<String> getN3Required() {
        return list(N3_PREFIX
        + "?authorshipUri a core:Authorship ;" 
        + "core:linkedAuthor ?personUri ."   
        + "?personUri core:authorInAuthorship ?authorshipUri .");
    }

    @Override
    protected final List<String> getN3Optional() {
        return list(N3_PREFIX + "?authorshipUri core:linkedInformationResource ?publicationUri ." +
                "?publicationUri core:informationResourceInAuthorship ?authorshipUri .");
    }


    @Override
    protected final String getSubjectName() {
        return "publicationUri";
    }

    @Override
    protected final String getPredicateName() {
        return "predicate";
    }

    @Override
    protected final String getObjectName() {
        return "personUri";
    }

    @Override
    protected final Map<String, String> getNewResources(VitroRequest vreq) {
        Map<String, String> newResources = new HashMap<String, String>();
        newResources.put("authorshipUri", DEFAULT_NS_TOKEN);
        return newResources;
    }
}
