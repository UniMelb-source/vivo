/* $This file is distributed under the terms of the license in /doc/license.txt$ */
package edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.preprocessors;

import com.hp.hpl.jena.rdf.model.Literal;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.BaseEditSubmissionPreprocessorVTwo;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.EditConfigurationVTwo;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.MultiValueEditSubmission;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SetEntityReturnPreprocessor extends BaseEditSubmissionPreprocessorVTwo {

    protected static final Log log = LogFactory.getLog(SetEntityReturnPreprocessor.class);
    protected static String itemType;
    protected static String roleToItemPredicate;
    protected static String itemToRolePredicate;
    private String forwardUri;

    public SetEntityReturnPreprocessor(EditConfigurationVTwo editConfig, String forwardUri) {
        super(editConfig);
        this.forwardUri = forwardUri;
    }

    public void preprocess(MultiValueEditSubmission submission) {
        try {
            Map<String, List<String>> urisFromForm = submission.getUrisFromForm();
            Map<String, List<Literal>> literalsFromForm = submission.getLiteralsFromForm();
            String uri = "?childOrParent";

            if (urisFromForm.containsKey(uri)) {
                urisFromForm.remove(uri);
            }
            String redirectUri;

            if (literalsFromForm.containsKey("redirectForward")) {
                redirectUri = this.forwardUri;
            } else {
                redirectUri = this.editConfiguration.getSubjectUri();
            }

            urisFromForm.put(uri, Arrays.asList(new String[]{redirectUri}));
            submission.setUrisFromForm(urisFromForm);
        } catch (Exception e) {
            log.error("Error selecting child or parent to redirect to.");
        }

    }
}
