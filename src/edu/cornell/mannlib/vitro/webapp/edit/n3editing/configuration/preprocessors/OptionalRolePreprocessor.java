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
public class OptionalRolePreprocessor extends BaseEditSubmissionPreprocessorVTwo {

    protected static final Log log = LogFactory.getLog(BaseEditSubmissionPreprocessorVTwo.class);
    private Map<String, String> dependencies;

    public OptionalRolePreprocessor(EditConfigurationVTwo editConfig, Map<String, String> dependencies) {
        super(editConfig);
        this.dependencies = dependencies;
    }

    public void preprocess(MultiValueEditSubmission editSubmission) {
        Map<String, List<String>> urisFromForm;
        List<String> n3Required;

        urisFromForm = editSubmission.getUrisFromForm();
        n3Required = editConfiguration.getN3Required();
        for (String dependencyKey : dependencies.keySet()) {
            if (urisFromForm.containsKey(dependencyKey)) {
                n3Required.remove(dependencies.get(dependencyKey));
            }
        }
        editConfiguration.setN3Required(n3Required);
    }
}
