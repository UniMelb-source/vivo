package edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.generators;

import edu.cornell.mannlib.vitro.webapp.controller.VitroRequest;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.BaseEditSubmissionPreprocessorVTwo;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.EditConfigurationVTwo;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.FieldVTwo;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.N3ValidatorVTwo;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.validators.AntiXssValidation;
import java.util.*;

/**
 *
 * @author tom
 */
public abstract class AddPublicationToThingGenerator extends RdrVivoBaseGenerator {

    @Override
    protected List<String> getN3Optional() {
        return Collections.<String>emptyList();
    }

    @Override
    protected final List<FieldVTwo> getFields() {
        List<FieldVTwo> fields = new ArrayList<FieldVTwo>(6);
        fields.add(new CustomFieldVTwo("sparqlQueryUrl", null, null, null, null, null));
        fields.add(new CustomFieldVTwo("acUrl", null, null, null, null, null));
        fields.add(new CustomFieldVTwo("acType", null, null, null, null, null));
        fields.add(new CustomFieldVTwo("editMode", null, null, null, null, null));
        fields.add(new CustomFieldVTwo("submitButtonTextType", null, null, null, null, null));
        fields.add(new CustomFieldVTwo("typeName", null, null, null, null, null));
        return fields;
    }

    @Override
    protected List<String> getUrisOnForm() {
        return list("publicationUri");
    }

    @Override
    protected List<String> getLiteralsOnForm() {
        return list("sparqlQueryUrl",
                "acUrl",
                "acType",
                "editMode",
                "submitButtonTextType",
                "typeName");
    }

    @Override
    protected List<N3ValidatorVTwo> getValidators() {
        List<N3ValidatorVTwo> validators = new ArrayList<N3ValidatorVTwo>();

        validators.add(new AntiXssValidation());
        return validators;
    }

    @Override
    protected HashMap<String, Object> getFormSpecificData(EditConfigurationVTwo editConfiguration, VitroRequest vreq) {
        HashMap<String, Object> formSpecificData = new HashMap<String, Object>();

        formSpecificData.put("sparqlQueryUrl", "/ajax/sparqlQuery");
        formSpecificData.put("acUrl", "/autocomplete?tokenize=true");
        formSpecificData.put("acType", "http://vivoweb.org/ontology/core#InformationResource");
        formSpecificData.put("editMode", "add");
        formSpecificData.put("submitButtonTextType", "simple");
        formSpecificData.put("typeName", "Publication");

        return formSpecificData;
    }

    @Override
    protected Map<String, String> getNewResources(VitroRequest vreq) {
        Map<String, String> newResources = new HashMap<String, String>();
        newResources.put("publicationUri", DEFAULT_NS_TOKEN);
        return newResources;
    }

    @Override
    protected List<BaseEditSubmissionPreprocessorVTwo> getPreprocessors(EditConfigurationVTwo editConfiguration) {
        return Collections.<BaseEditSubmissionPreprocessorVTwo>emptyList();

    }   
}
