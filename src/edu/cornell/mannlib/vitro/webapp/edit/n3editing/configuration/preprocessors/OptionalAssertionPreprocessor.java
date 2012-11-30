package edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.preprocessors;

import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.BaseEditSubmissionPreprocessorVTwo;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.EditConfigurationVTwo;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.MultiValueEditSubmission;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author tom
 */
public class OptionalAssertionPreprocessor extends BaseEditSubmissionPreprocessorVTwo {

    protected static final Log log = LogFactory.getLog(BaseEditSubmissionPreprocessorVTwo.class);
    private Map<String, List<String>> assertionMap;

    public OptionalAssertionPreprocessor(EditConfigurationVTwo editConfig, Map<String, List<String>> assertionMap) {
        super(editConfig);
        this.assertionMap = assertionMap;
    }

    public void preprocess(MultiValueEditSubmission editSubmission) {
        Map<String, List<String>> urisFromForm;
        List<String> n3Required;

        urisFromForm = editSubmission.getUrisFromForm();
        n3Required = editConfiguration.getN3Required();
        for (String assertionUri : assertionMap.keySet()) {
            if (urisFromForm.containsKey(assertionUri)) {
                n3Required.addAll(assertionMap.get(assertionUri));
            }
        }
        editConfiguration.setN3Required(n3Required);
    }
}
