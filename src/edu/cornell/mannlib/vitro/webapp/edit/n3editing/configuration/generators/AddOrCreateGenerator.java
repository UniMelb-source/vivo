package edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.generators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import edu.cornell.mannlib.vitro.webapp.controller.VitroRequest;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.BaseEditSubmissionPreprocessorVTwo;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.EditConfigurationUtils;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.EditConfigurationVTwo;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.N3ValidatorVTwo;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.FieldVTwo;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.validators.AntiXssValidation;

/**
 *
 * @author tsullivan
 */
public abstract class AddOrCreateGenerator extends RdrVivoBaseGenerator {

    private String acType;
    private String typeName;
    private Class editForm;
    private String objectUriVar;

    public AddOrCreateGenerator(String acType,
            String typeName,
            String objectUriVar,
            Class editForm) {
        this.acType = acType;
        this.typeName = typeName;
        this.editForm = editForm;
        this.objectUriVar = objectUriVar;
    }

    @Override
    protected final List<String> getN3Optional() {
        return list();
    }

    @Override
    protected final HashMap<String, Object> getFormSpecificData(EditConfigurationVTwo editConfiguration, VitroRequest vreq) {
        HashMap<String, Object> formSpecificData = super.getFormSpecificData(editConfiguration, vreq);

        formSpecificData.put("sparqlQueryUrl", "/vivo/ajax/sparqlQuery");
        formSpecificData.put("acUrl", "/vivo/autocomplete?tokenize=true");
        formSpecificData.put("acType", acType);
        formSpecificData.put("editMode", "add");
        formSpecificData.put("submitButtonTextType", "simple");
        formSpecificData.put("typeName", typeName);
        formSpecificData.put("editForm", editForm.getName());
        formSpecificData.put("objectUriVar", objectUriVar);

        return formSpecificData;
    }

    @Override
    protected final List<String> getLiteralsOnForm() {
        List<String> literals = super.getLiteralsOnForm();

        literals.add("sparqlQueryUrl");
        literals.add("acUrl");
        literals.add("acType");
        literals.add("editMode");
        literals.add("submitButtonTextType");
        literals.add("typeName");
        literals.add("editForm");

        return literals;
    }

    @Override
    protected List<String> getUrisOnForm() {
        return list();
    }

    @Override
    protected final List<FieldVTwo> getFields() {
        List<FieldVTwo> fields = super.getFields();
        fields.add(new CustomFieldVTwo("sparqlQueryUrl", null, null, null, null, null));
        fields.add(new CustomFieldVTwo("acUrl", null, null, null, null, null));
        fields.add(new CustomFieldVTwo("acType", null, null, null, null, null));
        fields.add(new CustomFieldVTwo("editMode", null, null, null, null, null));
        fields.add(new CustomFieldVTwo("submitButtonTextType", null, null, null, null, null));
        fields.add(new CustomFieldVTwo("typeName", null, null, null, null, null));
        fields.add(new CustomFieldVTwo("editForm", null, null, null, null, null));
        return fields;
    }

    @Override
    protected List<N3ValidatorVTwo> getValidators() {
        List<N3ValidatorVTwo> validators = new ArrayList<N3ValidatorVTwo>();

        validators.add(new AntiXssValidation());
        return validators;
    }

    @Override
    protected List<BaseEditSubmissionPreprocessorVTwo> getPreprocessors(EditConfigurationVTwo editConfiguration) {
        return Collections.<BaseEditSubmissionPreprocessorVTwo>emptyList();
    }
}
