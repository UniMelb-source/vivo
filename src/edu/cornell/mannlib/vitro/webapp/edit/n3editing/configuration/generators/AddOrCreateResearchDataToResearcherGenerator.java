package edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.generators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import edu.cornell.mannlib.vitro.webapp.controller.VitroRequest;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.EditConfigurationUtils;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.EditConfigurationVTwo;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.N3ValidatorVTwo;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.FieldVTwo;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.validators.AntiXssValidation;

/**
 *
 * @author tsullivan
 */
public class AddOrCreateResearchDataToResearcherGenerator extends AddOrCreateGenerator {

    private static final String template = "addOrCreateResearchData.ftl";
    private static final String subjectName = "researcher";
    private static final String predicateName = "predicate";
    private static final String objectName = "researchDataUri";
    private static final Log log = LogFactory.getLog(AddOrCreateResearchDataToResearcherGenerator.class);

    public AddOrCreateResearchDataToResearcherGenerator() {
        super("http://purl.org/ands/ontologies/vivo/ResearchData",
                "Research Data",
                objectName,
                AddResearchDataToResearcherGenerator.class);
    }

    @Override
    protected final String getSubjectName() {
        return subjectName;
    }

    @Override
    protected final String getPredicateName() {
        return predicateName;
    }

    @Override
    protected final String getObjectName() {
        return objectName;
    }

    @Override
    protected final String getTemplate() {
        return template;
    }

    @Override
    protected final List<String> getN3Required() {
        return list(N3_PREFIX
                + "?researcher ands:isCollectorOf ?researchDataUri. \n"
                + "?researchDataUri ands:hasCollector ?researcher. \n"
                + "?researchDataUri a ands:ResearchData ;");
    }

    @Override
    protected final Log getLog() {
        return log;
    }

    @Override
    protected final Map<String, String> getNewResources(VitroRequest vreq) {
        Map<String, String> newResources = new HashMap<String, String>();

        newResources.put("researchDataUri", DEFAULT_NS_TOKEN);

        return newResources;
    }

    @Override
    protected List<String> getUrisOnForm() {
        List<String> urisOnForm = super.getUrisOnForm();

        urisOnForm.add("researchDataUri");

        return urisOnForm;
    }
}
