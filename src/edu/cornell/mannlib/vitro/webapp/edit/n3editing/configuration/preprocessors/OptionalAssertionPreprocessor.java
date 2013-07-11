package edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.preprocessors;

import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.BaseEditSubmissionPreprocessorVTwo;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.EditConfigurationVTwo;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.MultiValueEditSubmission;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author tom
 */
public class OptionalAssertionPreprocessor extends BaseEditSubmissionPreprocessorVTwo {

    protected static final Log log = LogFactory.getLog(BaseEditSubmissionPreprocessorVTwo.class);
    private Map<String, IndirectRelationship> indirectAssertionMap;

    public OptionalAssertionPreprocessor(EditConfigurationVTwo editConfig,
            Map<String, IndirectRelationship> indirectAssertionMap) {
        super(editConfig);
        this.indirectAssertionMap = indirectAssertionMap;
    }

    public void preprocess(MultiValueEditSubmission editSubmission) {
        Map<String, List<String>> urisFromForm;
        List<String> n3Required;
        Map<String, String> newResources;

        urisFromForm = editSubmission.getUrisFromForm();
        n3Required = editConfiguration.getN3Required();
        newResources = editConfiguration.getNewResources();
        for (String assertionUri : indirectAssertionMap.keySet()) {
            if (urisFromForm.containsKey(assertionUri)) {
                IndirectRelationship relationship;

                relationship = indirectAssertionMap.get(assertionUri);
                for (int i = 0; i < urisFromForm.get(assertionUri).size(); i++) {
                    n3Required.addAll(relationship.getAssertions(i));
                    urisFromForm.put(assertionUri + i, Collections.singletonList(urisFromForm.get(assertionUri).get(i)));
                }
                newResources.putAll(relationship.getNewResources(urisFromForm.get(assertionUri).size()));
                urisFromForm.remove(assertionUri);
            }
        }
        editConfiguration.setN3Required(n3Required);
        editConfiguration.setNewResources(newResources);
        editSubmission.setUrisFromForm(urisFromForm);
    }

    public static class IndirectRelationship {

        private String n3Prefix;
        private String subjectUri;
        private String intermediateUriBase;
        private String intermediateType;
        private String objectUriBase;
        private String subjectIntermediateForwardUri;
        private String subjectIntermediateBackwardUri;
        private String intermediateObjectForwardUri;
        private String intermediateObjectBackwardUri;

        public IndirectRelationship(String n3Prefix,
                String subjectUri,
                String intermediateUriBase,
                String intermediateType,
                String objectUriBase,
                String subjectIntermediateForwardUri,
                String subjectIntermediateBackwardUri,
                String intermediateObjectForwardUri,
                String intermediateObjectBackwardUri) {
            this.n3Prefix = n3Prefix;
            this.subjectUri = subjectUri;
            this.intermediateUriBase = intermediateUriBase;
            this.intermediateType = intermediateType;
            this.objectUriBase = objectUriBase;
            this.subjectIntermediateForwardUri = subjectIntermediateForwardUri;
            this.subjectIntermediateBackwardUri = subjectIntermediateBackwardUri;
            this.intermediateObjectForwardUri = intermediateObjectForwardUri;
            this.intermediateObjectBackwardUri = intermediateObjectBackwardUri;
        }

        public List<String> getAssertions(int count) {
            ArrayList<String> modifiedAssertions;
            String intermediateUri;
            String objectUri;

            intermediateUri = intermediateUriBase + count;
            objectUri = objectUriBase + count;

            modifiedAssertions = new ArrayList<String>(5);

            modifiedAssertions.add(String.format("%s ?%s a %s .", n3Prefix, intermediateUri, intermediateType));
            modifiedAssertions.add(String.format("%s ?%s %s ?%s .", n3Prefix, subjectUri, subjectIntermediateForwardUri, intermediateUri));
            modifiedAssertions.add(String.format("%s ?%s %s ?%s .", n3Prefix, intermediateUri, subjectIntermediateBackwardUri, subjectUri));
            modifiedAssertions.add(String.format("%s ?%s %s ?%s .", n3Prefix, intermediateUri, intermediateObjectForwardUri, objectUri));
            modifiedAssertions.add(String.format("%s ?%s %s ?%s .", n3Prefix, objectUri, intermediateObjectBackwardUri, intermediateUri));

            return modifiedAssertions;
        }

        public Map<String, String> getNewResources(int count) {
            HashMap<String, String> newResourceMap;

            newResourceMap = new HashMap<String, String>();

            for (int i = 0; i < count; i++) {
                newResourceMap.put(String.format("%s%d", intermediateUriBase, i), null);
            }
            return newResourceMap;
        }
    }
}
