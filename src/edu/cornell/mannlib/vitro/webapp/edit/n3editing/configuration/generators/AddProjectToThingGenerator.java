package edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.generators;

import com.hp.hpl.jena.vocabulary.XSD;
import edu.cornell.mannlib.vitro.webapp.controller.VitroRequest;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.EditConfigurationUtils;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.EditConfigurationVTwo;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.FieldVTwo;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.N3ValidatorVTwo;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.validators.AntiXssValidation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author tom
 */
public abstract class AddProjectToThingGenerator extends RdrVivoBaseGenerator {

    protected abstract Map<String, String> getInheritedPersonsLabelAndUri(String subjectUri);

    @Override
    protected List<String> getN3Required() {
        return list(N3_PREFIX + "?roles a unimelb-rdr:ProjectRole .");
        //person unimelb-rdr:hasProjectRole role
        //role unimelb-rdr:projectRoleIn project
        //project unimelb-rdr:relatedProjectRole role
        //role unimelb-rdr:projectRoleOf person
    }

    @Override
    protected final List<String> getN3Optional() {
        return list(
                N3_PREFIX + "?projectUri rdfs:label ?projectLabel .",
                N3_PREFIX + "?projectUri core:hasSubjectArea ?subjectAreas .",
                N3_PREFIX + "?projectUri unimelb-rdr:relatedProjectRole ?roles .",
                N3_PREFIX + "?roles unimelb-rdr:projectRoleIn ?projectUri .",
                N3_PREFIX + "?roles unimelb-rdr:projectRoleOf ?persons .",
                N3_PREFIX + "?persons unimelb-rdr:hasProjectRole ?roles .",
                N3_PREFIX + "?projectUri core:description ?projectDescription .");
    }

    @Override
    protected HashMap<String, Object> getFormSpecificData(EditConfigurationVTwo editConfiguration, VitroRequest vreq) {
        HashMap<String, Object> formSpecificData = new HashMap<String, Object>();

        String subjectUri = EditConfigurationUtils.getSubjectUri(vreq);
        formSpecificData.put("InheritedSubjectAreas", getInheritedItemLabelAndUri(subjectUri, "core:hasSubjectArea"));
        formSpecificData.put("InheritedPersons", getInheritedPersonsLabelAndUri(subjectUri));

        return formSpecificData;
    }

    @Override
    protected final List<String> getLiteralsOnForm() {
        return list("projectLabel",
                "projectDescription",
                "redirectForward");
    }

    @Override
    protected final List<String> getUrisOnForm() {
        return list("subjectAreas", "roles");
    }

    @Override
    protected final List<FieldVTwo> getFields() {
        List<FieldVTwo> fields = new ArrayList<FieldVTwo>(5);
        fields.add(new CustomFieldVTwo("projectLabel", list("nonempty", "datatype:" + XSD.xstring.toString()), XSD.xstring.toString(), null, null, null));
        fields.add(new CustomFieldVTwo("projectDescription", list("nonempty", "datatype:" + XSD.xstring.toString()), XSD.xstring.toString(), null, null, null));
        fields.add(new CustomFieldVTwo("redirectForward", null, XSD.xboolean.toString(), null, null, null));
        fields.add(new CustomFieldVTwo("subjectAreas", null, null, null, null, null));
        fields.add(new CustomFieldVTwo("roles", null, null, null, null, null));
        return fields;
    }

    @Override
    protected final Map<String, String> getNewResources(VitroRequest vreq) {
        Map<String, String> newResources = new HashMap<String, String>();
        newResources.put("projectUri", DEFAULT_NS_TOKEN);
        return newResources;
    }

    @Override
    protected List<N3ValidatorVTwo> getValidators() {
        List<N3ValidatorVTwo> validators = new ArrayList<N3ValidatorVTwo>();

        validators.add(new AntiXssValidation());
        return validators;
    }

    @Override
    protected final String getForwardUri() {
        return "?projectUri";
    }
}
