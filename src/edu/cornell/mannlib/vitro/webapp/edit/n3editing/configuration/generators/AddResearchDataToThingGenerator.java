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
public abstract class AddResearchDataToThingGenerator extends RdrVivoBaseGenerator {

    protected abstract Map<String, String> getInheritedCustodians(String subjectUri);

    protected abstract Map<String, String> getInheritedCustodianDepartments(String subjectUri);

    @Override
    protected final List<String> getN3Optional() {
        String username = userAccount.getFirstName() + " " + userAccount.getLastName();
        return list(
                N3_PREFIX + "?researchDataUri rdfs:label ?researchDataLabel .",
                N3_PREFIX + "?researchDataUri unimelb-rdr:digitalLocation ?digitalDataLocation .",
                N3_PREFIX + "?researchDataUri unimelb-rdr:nonDigitalLocation ?physicalDataLocation .",
                N3_PREFIX + "?researchDataUri unimelb-rdr:retentionPeriod ?retention .",
                N3_PREFIX + "?researchDataUri unimelb-rdr:accessibility ?accessibility .",
                N3_PREFIX + "?researchDataUri ands:rights ?rights .",
                N3_PREFIX + "?researchDataUri unimelb-rdr:dataManagementPlanId ?dataManagementPlanNumber .",
                N3_PREFIX + "?researchDataUri ands:isLocatedIn ?researchRepository .",
                N3_PREFIX + "?researchDataUri core:hasSubjectArea ?subjectAreas .",
                N3_PREFIX + "?researchDataUri ands:isManagedBy ?custodianDepartments .",
                N3_PREFIX + "?researchDataUri ands:associatedPrincipleInvestigator ?custodians .",
                N3_PREFIX + "?researchDataUri ands:researchDataDescription ?researchDataDescription .",
                N3_PREFIX + "?researchDataUri unimelb-rdr:recordCreator \"" + username + "\"^^xsd:string .");
    }

    @Override
    protected HashMap<String, Object> getFormSpecificData(EditConfigurationVTwo editConfiguration, VitroRequest vreq) {
        HashMap<String, Object> formSpecificData = new HashMap<String, Object>();

        String subjectUri = EditConfigurationUtils.getSubjectUri(vreq);
        formSpecificData.put("InheritedCustodianDepartments", getInheritedCustodianDepartments(subjectUri));
        formSpecificData.put("InheritedCustodians", getInheritedCustodians(subjectUri));
        formSpecificData.put("InheritedSubjectAreas", getInheritedItemLabelAndUri(subjectUri, "core:hasSubjectArea"));
        formSpecificData.put("AvailableResearchRepositories", getAvailableResearchRepositories());
        formSpecificData.put("objectUri", "researchDataUri");
        formSpecificData.put("sparqlQueryUrl", "/vivo/ajax/sparqlQuery");
        formSpecificData.put("acUrl", "/vivo/autocomplete?tokenize=true");
        formSpecificData.put("acType", "http://purl.org/ands/ontologies/vivo/ResearchData");
        formSpecificData.put("editMode", "add");
        formSpecificData.put("submitButtonTextType", "simple");
        formSpecificData.put("typeName", "Research Data");

        return formSpecificData;
    }

    private Map<String, String> getAvailableResearchRepositories() {
        String query = SPARQL_PREFIX
                + "SELECT DISTINCT ?repository ?repositoryLabel WHERE { \n"
                + "?repository rdf:type ands:ResearchRepository. \n"
                + "?repository rdfs:label ?repositoryLabel}";
        return getResults(query, "repository", "repositoryLabel");
    }

    @Override
    protected final List<String> getLiteralsOnForm() {
        return list("researchDataLabel",
                "digitalDataLocation",
                "physicalDataLocation",
                "retention",
                "accessibility",
                "rights",
                "dataManagementPlanNumber",
                "researchDataDescription",
                "redirectForward",
                "sparqlQueryUrl",
                "acUrl",
                "acType",
                "editMode",
                "submitButtonTextType",
                "typeName");
    }

    @Override
    protected final List<String> getUrisOnForm() {
        return list("subjectAreas",
                "custodianDepartments",
                "custodians",
                "researchRepository");
    }

    @Override
    protected final List<FieldVTwo> getFields() {
        List<FieldVTwo> fields = new ArrayList<FieldVTwo>(20);
        fields.add(new CustomFieldVTwo("researchDataLabel", list("nonempty", "datatype:" + XSD.xstring.toString()), XSD.xstring.toString(), null, null, null));
        fields.add(new CustomFieldVTwo("researchDataDescription", list("nonempty", "datatype:" + XSD.xstring.toString()), XSD.xstring.toString(), null, null, null));
        fields.add(new CustomFieldVTwo("digitalDataLocation", list("datatype:" + XSD.xstring.toString()), XSD.xstring.toString(), null, null, null));
        fields.add(new CustomFieldVTwo("retention", list("datatype:" + XSD.xstring.toString()), XSD.xstring.toString(), null, null, null));
        fields.add(new CustomFieldVTwo("accessibility", list("datatype:" + XSD.xstring.toString()), XSD.xstring.toString(), null, null, null));
        fields.add(new CustomFieldVTwo("rights", list("datatype:" + XSD.xstring.toString()), XSD.xstring.toString(), null, null, null));
        fields.add(new CustomFieldVTwo("dataManagementPlanNumber", list("datatype:" + XSD.xstring.toString()), XSD.xstring.toString(), null, null, null));
        fields.add(new CustomFieldVTwo("physicalDataLocation", list("datatype:" + XSD.xstring.toString()), XSD.xstring.toString(), null, null, null));
        fields.add(new CustomFieldVTwo("redirectForward", null, XSD.xboolean.toString(), null, null, null));
        fields.add(new CustomFieldVTwo("subjectAreas", null, null, null, null, null));
        fields.add(new CustomFieldVTwo("researchRepository", null, null, null, null, null));
        fields.add(new CustomFieldVTwo("role", null, null, null, null, null));
        fields.add(new CustomFieldVTwo("custodianDepartments", null, null, null, null, null));
        fields.add(new CustomFieldVTwo("custodians", null, null, null, null, null));
        fields.add(new CustomFieldVTwo("sparqlQueryUrl", null, null, null, null, null));
        fields.add(new CustomFieldVTwo("acUrl", null, null, null, null, null));
        fields.add(new CustomFieldVTwo("acType", null, null, null, null, null));
        fields.add(new CustomFieldVTwo("editMode", null, null, null, null, null));
        fields.add(new CustomFieldVTwo("submitButtonTextType", null, null, null, null, null));
        fields.add(new CustomFieldVTwo("typeName", null, null, null, null, null));
        return fields;
    }

    @Override
    protected final Map<String, String> getNewResources(VitroRequest vreq) {
        HashMap<String, String> newResources = new HashMap<String, String>();
        newResources.put("researchDataUri", DEFAULT_NS_TOKEN);
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
        return "?researchDataUri";
    }
}
