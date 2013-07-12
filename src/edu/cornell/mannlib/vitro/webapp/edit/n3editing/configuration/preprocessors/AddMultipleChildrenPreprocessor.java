/* $This file is distributed under the terms of the license in /doc/license.txt$ */
package edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.preprocessors;

import com.hp.hpl.jena.rdf.model.Literal;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.BaseEditSubmissionPreprocessorVTwo;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.EditConfigurationVTwo;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.MultiValueEditSubmission;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AddMultipleChildrenPreprocessor extends BaseEditSubmissionPreprocessorVTwo {

    protected static final Log log = LogFactory.getLog(AddMultipleChildrenPreprocessor.class);
    private String n3Prefix;
    private String childrenUri;
    private String predicateUri;
    private String subjectUri;

    public AddMultipleChildrenPreprocessor( EditConfigurationVTwo editConfig,
                                            String n3Prefix,
                                            String childrenUri,
                                            String predicateUri,
                                            String subjectUri) {
        super(editConfig);
        this.n3Prefix = n3Prefix;
        this.childrenUri = childrenUri;
        this.predicateUri = predicateUri;
        this.subjectUri = subjectUri;
    }

    public void preprocess(MultiValueEditSubmission submission) {
        Map<String, List<String>> urisFromForm;
        List<String> n3Required;

        urisFromForm = submission.getUrisFromForm();
        n3Required = editConfiguration.getN3Required();
        if(urisFromForm.containsKey(childrenUri)) {
            for (int i = 0; i < urisFromForm.get(childrenUri).size(); i++) {
                n3Required.add(String.format("%s ?%s %s ?%s .", n3Prefix, childrenUri + i, predicateUri, subjectUri));
                urisFromForm.put(childrenUri + i, Collections.singletonList(urisFromForm.get(childrenUri).get(i)));
            }   
        }
        editConfiguration.setN3Required(n3Required);
        submission.setUrisFromForm(urisFromForm);
    }
}
