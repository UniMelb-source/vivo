package edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.generators;

import edu.cornell.mannlib.vitro.webapp.controller.VitroRequest;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.EditConfigurationVTwo;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.FieldVTwo;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.BaseEditSubmissionPreprocessorVTwo;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.preprocessors.SetEntityReturnPreprocessor;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

/**
 *
 * @author tom
 */
public abstract class RdrReturnEntityBaseGenerator extends RdrVivoBaseGenerator {

    protected abstract String getForwardUri();

    @Override
    protected List<BaseEditSubmissionPreprocessorVTwo> getPreprocessors(EditConfigurationVTwo editConfiguration) {
        List<BaseEditSubmissionPreprocessorVTwo> preprocessors;

        preprocessors = new ArrayList<BaseEditSubmissionPreprocessorVTwo>();
        String forwardUri = getForwardUri();
        if (null != forwardUri) {
            preprocessors.add(new SetEntityReturnPreprocessor(editConfiguration, forwardUri));
        }   
        return preprocessors;
    }

    @Override
    protected HashMap<String, Object> getFormSpecificData(EditConfigurationVTwo editConfiguration, VitroRequest vreq) {
        HashMap<String, Object> formSpecificData = super.getFormSpecificData(editConfiguration, vreq);
        formSpecificData.put("forwardUri", getForwardUri());
        return formSpecificData;
    }

    @Override
    protected List<FieldVTwo> getFields() {
        List<FieldVTwo> fields = super.getFields();
        fields.add(new CustomFieldVTwo("forwardUri", null, null, null, null, null));
        return fields;
    }

    @Override
    protected List<String> getLiteralsOnForm() {
        List<String> literalsOnForm = super.getLiteralsOnForm();
        literalsOnForm.add("forwardUri");
        return literalsOnForm;
    }

    @Override
    protected void additionalProcessing(EditConfigurationVTwo editConfiguration) {
        editConfiguration.setEntityToReturnTo("?childOrParent");
    }
}
