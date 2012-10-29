package edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.generators;

import com.hp.hpl.jena.vocabulary.XSD;
import edu.cornell.mannlib.vitro.webapp.controller.VitroRequest;
import edu.cornell.mannlib.vitro.webapp.dao.VitroVocabulary;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.DateTimeIntervalValidationVTwo;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.DateTimeWithPrecisionVTwo;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.EditConfigurationUtils;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.EditConfigurationVTwo;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.FieldVTwo;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.N3ValidatorVTwo;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.validators.AntiXssValidation;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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

    protected List<String> getN3Required() {
        return list(
                N3_PREFIX + "?recordCreatedOn a core:DateTimeValue .",
                N3_PREFIX + "?collectedDateRangeStart a core:DateTimeValue .",
                N3_PREFIX + "?collectedDateRangeEnd a core:DateTimeValue .",
                N3_PREFIX + "?collectedDateRange a core:DateTimeInterval .",
                N3_PREFIX + "?coveredDateRangeStart a core:DateTimeValue .",
                N3_PREFIX + "?coveredDateRangeEnd a core:DateTimeValue .",
                N3_PREFIX + "?coveredDateRange a core:DateTimeInterval .");
    }

    @Override
    protected final List<String> getN3Optional() {
        return list(
                N3_PREFIX + "?researchDataUri rdfs:label ?researchDataLabel .",
                N3_PREFIX + "?researchDataUri unimelb-rdr:digitalLocation ?digitalDataLocation .",
                N3_PREFIX + "?researchDataUri unimelb-rdr:nonDigitalLocation ?physicalDataLocation .",
                N3_PREFIX + "?researchDataUri unimelb-rdr:retentionPeriod ?retention .",
                N3_PREFIX + "?researchDataUri unimelb-rdr:accessibility ?accessibility .",
                N3_PREFIX + "?researchDataUri ands:rights ?rights .",
                N3_PREFIX + "?researchDataUri unimelb-rdr:dataManagementPlanId ?dataManagementPlanNumber .",
                N3_PREFIX + "?researchDataUri unimelb-rdr:dataManagementPlanAvailable ?dataManagementPlanAvailable .",
                N3_PREFIX + "?researchDataUri ands:isLocatedIn ?researchRepository .",
                N3_PREFIX + "?researchDataUri core:hasSubjectArea ?subjectAreas .",
                N3_PREFIX + "?researchDataUri ands:isManagedBy ?custodianDepartments .",
                N3_PREFIX + "?researchDataUri ands:associatedPrincipleInvestigator ?custodians .",
                N3_PREFIX + "?researchDataUri ands:researchDataDescription ?researchDataDescription .",
                N3_PREFIX + "?researchDataUri unimelb-rdr:recordCreator ?recordCreator .",
                /* Create date time value for created on */
                N3_PREFIX + "?recordCreatedOn core:dateTime ?recordCreatedOnDateTime .",
                N3_PREFIX + "?recordCreatedOn core:dateTimePrecision core:yearMonthDayTimePrecision .",
                N3_PREFIX + "?researchDataUri unimelb-rdr:recordCreated ?recordCreatedOn .",
                /* Create date time interval for collected on */
                N3_PREFIX + "?collectedDateRangeStart core:dateTime ?collectedDateRangeStartDateTime .",
                N3_PREFIX + "?collectedDateRangeStart core:dateTimePrecision core:yearMonthDayTimePrecision .",
                N3_PREFIX + "?collectedDateRangeEnd core:dateTime ?collectedDateRangeEndDateTime .",
                N3_PREFIX + "?collectedDateRangeEnd core:dateTimePrecision core:yearMonthDayTimePrecision .",
                N3_PREFIX + "?collectedDateRange core:start ?collectedDateRangeStart .",
                N3_PREFIX + "?collectedDateRange core:end ?collectedDateRangeEnd .",
                N3_PREFIX + "?researchDataUri unimelb-rdr:collectedDateRange ?collectedDateRange .",
                /* Create date time interval for covered */
                N3_PREFIX + "?coveredDateRangeStart core:dateTime ?coveredDateRangeStartDateTime .",
                N3_PREFIX + "?coveredDateRangeStart core:dateTimePrecision core:yearMonthDayTimePrecision .",
                N3_PREFIX + "?coveredDateRangeEnd core:dateTime ?coveredDateRangeEndDateTime .",
                N3_PREFIX + "?coveredDateRangeEnd core:dateTimePrecision core:yearMonthDayTimePrecision .",
                N3_PREFIX + "?coveredDateRange core:start ?coveredDateRangeStart .",
                N3_PREFIX + "?coveredDateRange core:end ?coveredDateRangeEnd .",
                N3_PREFIX + "?researchDataUri unimelb-rdr:coveredDateRange ?coveredDateRange .");
    }

    @Override
    protected HashMap<String, Object> getFormSpecificData(EditConfigurationVTwo editConfiguration, VitroRequest vreq) {
        HashMap<String, Object> formSpecificData = new HashMap<String, Object>();
        String username = userAccount.getFirstName() + " " + userAccount.getLastName()/* + "^^xsd:string"*/;
        String dateString = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date())/* + "^^xsd:dateTime"*/;
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
        formSpecificData.put("_username", username);
        formSpecificData.put("_dateString", dateString);

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
                "dataManagementPlanAvailable",
                "researchDataDescription",
                "redirectForward",
                "sparqlQueryUrl",
                "acUrl",
                "acType",
                "editMode",
                "submitButtonTextType",
                "typeName",
                "recordCreator",
                "recordCreatedOnDateTime",
                "collectedDateRange",
                "collectedDateRangeStart",
                "collectedDateRangeStartDateTime",
                "collectedDateRangeEnd",
                "collectedDateRangeEndDateTime",
                "coveredDateRange",
                "coveredDateRangeStart",
                "coveredDateRangeStartDateTime",
                "coveredDateRangeEnd",
                "coveredDateRangeEndDateTime");
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
        fields.add(new CustomFieldVTwo("dataManagementPlanAvailable", list("datatype:" + XSD.xboolean.toString()), XSD.xboolean.toString(), null, null, null));
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
        fields.add(new CustomFieldVTwo("recordCreator", list("datatype:" + XSD.xstring.toString()), XSD.xstring.toString(), null, null, null));
        fields.add(new CustomFieldVTwo("recordCreatedOnDateTime", list("datatype:" + XSD.dateTime.toString()), XSD.dateTime.toString(), null, null, null));
        //fields.add(new CustomFieldVTwo("collectedDateRange", list("datatype:" + XSD.dateTime.toString()), XSD.dateTime.toString(), null, null, null));
        fields.add(new CustomFieldVTwo("collectedDateRangeStartDateTime", list("datatype:" + XSD.dateTime.toString()), XSD.dateTime.toString(), null, null, null));
        fields.add(new CustomFieldVTwo("collectedDateRangeEndDateTime", list("datatype:" + XSD.dateTime.toString()), XSD.dateTime.toString(), null, null, null));
        //fields.add(new CustomFieldVTwo("coveredDateRange", list("datatype:" + XSD.dateTime.toString()), XSD.dateTime.toString(), null, null, null));
        fields.add(new CustomFieldVTwo("coveredDateRangeStartDateTime", list("datatype:" + XSD.dateTime.toString()), XSD.dateTime.toString(), null, null, null));
        fields.add(new CustomFieldVTwo("coveredDateRangeEndDateTime", list("datatype:" + XSD.dateTime.toString()), XSD.dateTime.toString(), null, null, null));
        FieldVTwo startField = new FieldVTwo().setName("startField");
        startField.setEditElement(new DateTimeWithPrecisionVTwo(startField,
                VitroVocabulary.Precision.SECOND.uri(),
                VitroVocabulary.Precision.NONE.uri()));
        fields.add(startField);
        FieldVTwo endField = new FieldVTwo().setName("endField");
        endField.setEditElement(new DateTimeWithPrecisionVTwo(endField,
                VitroVocabulary.Precision.SECOND.uri(),
                VitroVocabulary.Precision.NONE.uri()));
        fields.add(endField);
        return fields;
    }

    @Override
    protected final Map<String, String> getNewResources(VitroRequest vreq) {
        HashMap<String, String> newResources = new HashMap<String, String>();
        newResources.put("researchDataUri", DEFAULT_NS_TOKEN);
        newResources.put("recordCreatedOn", DEFAULT_NS_TOKEN);
        newResources.put("collectedDateRange", DEFAULT_NS_TOKEN);
        newResources.put("collectedDateRangeStart", DEFAULT_NS_TOKEN);
        newResources.put("collectedDateRangeEnd", DEFAULT_NS_TOKEN);
        newResources.put("coveredDateRange", DEFAULT_NS_TOKEN);
        newResources.put("coveredDateRangeStart", DEFAULT_NS_TOKEN);
        newResources.put("coveredDateRangeEnd", DEFAULT_NS_TOKEN);
        return newResources;
    }

    @Override
    protected List<N3ValidatorVTwo> getValidators() {
        List<N3ValidatorVTwo> validators = new ArrayList<N3ValidatorVTwo>();

        validators.add(new AntiXssValidation());
        validators.add(new DateTimeIntervalValidationVTwo("startField","endField"));
        return validators;
    }

    @Override
    protected final String getForwardUri() {
        return "?researchDataUri";
    }
}
