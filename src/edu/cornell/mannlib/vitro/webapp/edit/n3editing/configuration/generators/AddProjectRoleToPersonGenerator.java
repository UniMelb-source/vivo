package edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.generators;

import edu.cornell.mannlib.vitro.webapp.beans.ObjectProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import edu.cornell.mannlib.vitro.webapp.controller.VitroRequest;
import edu.cornell.mannlib.vitro.webapp.dao.VitroVocabulary;
import edu.cornell.mannlib.vitro.webapp.dao.WebappDaoFactory;
import edu.cornell.mannlib.vitro.webapp.dao.jena.QueryUtils;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.DateTimeIntervalValidationVTwo;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.DateTimeWithPrecisionVTwo;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.EditConfigurationUtils;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.EditConfigurationVTwo;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.FieldVTwo;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.preprocessors.RoleToActivityPredicatePreprocessor;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.validators.AntiXssValidation;
import edu.cornell.mannlib.vitro.webapp.utils.FrontEndEditingUtils;
import edu.cornell.mannlib.vitro.webapp.utils.generators.EditModeUtils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.vivoweb.webapp.util.ModelUtils;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;
import com.hp.hpl.jena.vocabulary.XSD;

public class AddProjectRoleToPersonGenerator extends BaseEditConfigurationGenerator implements EditConfigurationGenerator {

    private static String template = "addProjectRoleToPerson.ftl";

    String getTemplate() {
        return template;
    }

    String getRoleType() {
        return "https://rdr.unimelb.edu.au/config/ProjectRole";
    }

    ProjectOptionTypes getProjectTypeOptionsType() {
        return ProjectOptionTypes.HARDCODED_LITERALS;
    }

    String getProjectTypeObjectClassUri(VitroRequest vreq) {
        return null;
    }

    protected HashMap<String, String> getProjectTypeLiteralOptions() {
        HashMap<String, String> literalOptions = new HashMap<String, String>();
        literalOptions.put("http://vivoweb.org/ontology/core#Project", "Project");
        return literalOptions;
    }

    boolean isShowRoleLabelField() {
        return false;
    }
    private Log log = LogFactory.getLog(AddRoleToPersonTwoStageGenerator.class);

    @Override
    public EditConfigurationVTwo getEditConfiguration(VitroRequest vreq, HttpSession session) {
        EditConfigurationVTwo editConfiguration = new EditConfigurationVTwo();
        initProcessParameters(vreq, session, editConfiguration);

        editConfiguration.setVarNameForSubject("person");
        editConfiguration.setVarNameForPredicate("rolePredicate");
        editConfiguration.setVarNameForObject("role");

        // Required N3
        editConfiguration.setN3Required(list(
                N3_PREFIX + "\n"
                + "?person ?rolePredicate ?role .\n"
                + "?role a ?roleType .\n"));

        // Optional N3 
        //Note here we are placing the role to activity relationships as optional, since
        //it's possible to delete this relationship from the activity
        //On submission, if we kept these statements in n3required, the retractions would
        //not have all variables substituted, leading to an error
        //Note also we are including the relationship as a separate string in the array, to allow it to be
        //independently evaluated and passed back with substitutions even if the other strings are not 
        //substituted correctly. 
        editConfiguration.setN3Optional(list(
                "?role <https://rdr.unimelb.edu.au/config/projectRoleIn> ?project .\n"
                + "?project <https://rdr.unimelb.edu.au/config/relatedProjectRole> ?role .",
                "?role ?inverseRolePredicate ?person .",
                getN3ForProjectLabel(),
                getN3ForProjectType(),
                getN3RoleLabelAssertion(),
                getN3ForStart(),
                getN3ForEnd()));

        editConfiguration.setNewResources(newResources(vreq));

        //In scope
        setUrisAndLiteralsInScope(editConfiguration, vreq);

        //on Form
        setUrisAndLiteralsOnForm(editConfiguration, vreq);

        //Sparql queries
        setSparqlQueries(editConfiguration, vreq);

        //set fields
        setFields(editConfiguration, vreq, EditConfigurationUtils.getPredicateUri(vreq));

        //Form title and submit label now moved to edit configuration template
        //TODO: check if edit configuration template correct place to set those or whether
        //additional methods here should be used and reference instead, e.g. edit configuration template could call
        //default obj property form.populateTemplate or some such method
        //Select from existing also set within template itself
        editConfiguration.setTemplate(getTemplate());

        //Add validator
        editConfiguration.addValidator(new DateTimeIntervalValidationVTwo("startField", "endField"));
        editConfiguration.addValidator(new AntiXssValidation());

        //Add preprocessors
        addPreprocessors(editConfiguration, vreq.getWebappDaoFactory());
        //Adding additional data, specifically edit mode
        addFormSpecificData(editConfiguration, vreq);

        //prepare
        prepare(vreq, editConfiguration);
        return editConfiguration;
    }

    private void initProcessParameters(VitroRequest vreq, HttpSession session, EditConfigurationVTwo editConfiguration) {
        editConfiguration.setFormUrl(EditConfigurationUtils.getFormUrlWithoutContext(vreq));
        editConfiguration.setEntityToReturnTo(EditConfigurationUtils.getSubjectUri(vreq));
    }

    /* N3 Required and Optional Generators as well as supporting methods */
    private String getN3ForProjectLabel() {
        return "?project <" + RDFS.label.getURI() + "> ?projectLabel .";
    }

    private String getN3ForProjectType() {
        return "?project a <http://vivoweb.org/ontology/core#Project> .";
    }

    private String getN3RoleLabelAssertion() {
        return "?role <" + RDFS.label.getURI() + "> ?roleLabel .";
    }

    //Method b/c used in two locations, n3 optional and n3 assertions
    private List<String> getN3ForStart() {
        List<String> n3ForStart = new ArrayList<String>();
        n3ForStart.add("?role  <" + RoleToIntervalURI + "> ?intervalNode ."
                + "?intervalNode  <" + RDF.type.getURI() + "> <" + IntervalTypeURI + "> ."
                + "?intervalNode <" + IntervalToStartURI + "> ?startNode ."
                + "?startNode  <" + RDF.type.getURI() + "> <" + DateTimeValueTypeURI + "> ."
                + "?startNode  <" + DateTimeValueURI + "> ?startField-value ."
                + "?startNode  <" + DateTimePrecisionURI + "> ?startField-precision .");
        return n3ForStart;
    }

    private List<String> getN3ForEnd() {
        List<String> n3ForEnd = new ArrayList<String>();
        n3ForEnd.add("?role      <" + RoleToIntervalURI + "> ?intervalNode .  "
                + "?intervalNode  <" + RDF.type.getURI() + "> <" + IntervalTypeURI + "> ."
                + "?intervalNode <" + IntervalToEndURI + "> ?endNode ."
                + "?endNode  <" + RDF.type.getURI() + "> <" + DateTimeValueTypeURI + "> ."
                + "?endNode  <" + DateTimeValueURI + "> ?endField-value ."
                + "?endNode  <" + DateTimePrecisionURI + "> ?endField-precision .");
        return n3ForEnd;
    }

    /**
     * Get new resources
     */
    private Map<String, String> newResources(VitroRequest vreq) {
        String DEFAULT_NS_TOKEN = null; //null forces the default NS

        HashMap<String, String> newResources = new HashMap<String, String>();
        newResources.put("role", DEFAULT_NS_TOKEN);
        newResources.put("project", DEFAULT_NS_TOKEN);
        newResources.put("intervalNode", DEFAULT_NS_TOKEN);
        newResources.put("startNode", DEFAULT_NS_TOKEN);
        newResources.put("endNode", DEFAULT_NS_TOKEN);
        return newResources;
    }

    /**
     * Set URIS and Literals In Scope and on form and supporting methods
     */
    private void setUrisAndLiteralsInScope(EditConfigurationVTwo editConfiguration, VitroRequest vreq) {
        HashMap<String, List<String>> urisInScope = new HashMap<String, List<String>>();

        //Setting inverse role predicate
        urisInScope.put("inverseRolePredicate", getInversePredicate(vreq));
        urisInScope.put("roleType", list(getRoleType()));

        //Uris in scope include subject, predicate, and object var
        editConfiguration.setUrisInScope(urisInScope);

        //literals in scope empty initially, usually populated by code in prepare for update
        //with existing values for variables
    }

    private List<String> getInversePredicate(VitroRequest vreq) {
        List<String> inversePredicateArray = new ArrayList<String>();
        ObjectProperty op = EditConfigurationUtils.getObjectProperty(vreq);
        if (op != null && op.getURIInverse() != null) {
            inversePredicateArray.add(op.getURIInverse());
        }
        return inversePredicateArray;
    }

    private void setUrisAndLiteralsOnForm(EditConfigurationVTwo editConfiguration, VitroRequest vreq) {
        List<String> urisOnForm = new ArrayList<String>();
        //add role activity and roleActivityType to uris on form
        urisOnForm.add("project");
        //urisOnForm.add("roleActivityType");
        //Also adding the predicates
        //TODO: Check how to override this in case of default parameter? Just write hidden input to form?
        //urisOnForm.add("roleToActivityPredicate");
        //urisOnForm.add("activityToRolePredicate");
        editConfiguration.setUrisOnform(urisOnForm);

        //activity label and role label are literals on form
        List<String> literalsOnForm = new ArrayList<String>();
        literalsOnForm.add("projectLabel");
        literalsOnForm.add("roleLabel");
        editConfiguration.setLiteralsOnForm(literalsOnForm);
    }

    /**
     * Set SPARQL Queries and supporting methods.
     */
    private void setSparqlQueries(EditConfigurationVTwo editConfiguration, VitroRequest vreq) {
        //Queries for activity label, role label, start Field value, end Field value
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("projectLabel", getProjectLabelQuery(vreq));
        map.put("roleLabel", getRoleLabelQuery(vreq));
        map.put("startField-value", getExistingStartDateQuery(vreq));
        map.put("endField-value", getExistingEndDateQuery(vreq));

        editConfiguration.setSparqlForExistingLiterals(map);

        //Queries for role activity, activity type query, interval node, 
        // start node, end node, start field precision, endfield precision
        map = new HashMap<String, String>();
        map.put("project", getProjectQuery(vreq));
        map.put("intervalNode", getIntervalNodeQuery(vreq));
        map.put("startNode", getStartNodeQuery(vreq));
        map.put("endNode", getEndNodeQuery(vreq));
        map.put("startField-precision", getStartPrecisionQuery(vreq));
        map.put("endField-precision", getEndPrecisionQuery(vreq));
        //Also need sparql queries for roleToActivityPredicate and activityToRolePredicate
        map.put("roleToProjectPredicate", getRoleToProjectPredicateQuery(vreq));
        map.put("projectToRolePredicate", getProjectToRolePredicateQuery(vreq));

        editConfiguration.setSparqlForExistingUris(map);
    }

    private String getProjectToRolePredicateQuery(VitroRequest vreq) {
        String query = "SELECT ?existingProjectToRolePredicate \n "
                + "WHERE { \n"
                + "?project ?existingProjectToRolePredicate ?role .\n";
        //Get possible predicates
        List<String> addToQuery = new ArrayList<String>();
        List<String> predicates = getProjectToRolePredicates();
        for (String p : predicates) {
            addToQuery.add("(?existingProjectToRolePredicate=<" + p + ">)");
        }
        query += "FILTER (" + StringUtils.join(addToQuery, " || ") + ")\n";
        query += "}";
        return query;
    }

    private String getRoleToProjectPredicateQuery(VitroRequest vreq) {
        String query = "SELECT ?existingRoleToProjectPredicate \n "
                + "WHERE { \n"
                + "?role ?existingRoleToProjectPredicate ?project .\n";
        //Get possible predicates
        query += getFilterRoleToProjectPredicate("existingRoleToProjectPredicate");
        query += "\n}";
        return query;
    }

    private String getEndPrecisionQuery(VitroRequest vreq) {
        String query = "SELECT ?existingEndPrecision WHERE {\n"
                + "?role <" + RoleToIntervalURI + "> ?intervalNode .\n"
                + "?intervalNode <" + VitroVocabulary.RDF_TYPE + "> <" + IntervalTypeURI + "> .\n"
                + "?intervalNode <" + IntervalToEndURI + "> ?endNode .\n"
                + "?endNode <" + VitroVocabulary.RDF_TYPE + "> <" + DateTimeValueTypeURI + "> .  \n"
                + "?endNode <" + DateTimePrecisionURI + "> ?existingEndPrecision . }";
        return query;
    }

    private String getStartPrecisionQuery(VitroRequest vreq) {
        String query = "SELECT ?existingStartPrecision WHERE {\n"
                + "?role <" + RoleToIntervalURI + "> ?intervalNode .\n"
                + "?intervalNode <" + VitroVocabulary.RDF_TYPE + "> <" + IntervalTypeURI + "> .\n"
                + "?intervalNode <" + IntervalToStartURI + "> ?startNode .\n"
                + "?startNode <" + VitroVocabulary.RDF_TYPE + "> <" + DateTimeValueTypeURI + "> .  \n"
                + "?startNode <" + DateTimePrecisionURI + "> ?existingStartPrecision . }";
        return query;
    }

    private String getEndNodeQuery(VitroRequest vreq) {
        String query = "SELECT ?existingEndNode WHERE {\n"
                + "?role <" + RoleToIntervalURI + "> ?intervalNode .\n"
                + "?intervalNode <" + VitroVocabulary.RDF_TYPE + "> <" + IntervalTypeURI + "> .\n"
                + "?intervalNode <" + IntervalToEndURI + "> ?existingEndNode . \n"
                + "?existingEndNode <" + VitroVocabulary.RDF_TYPE + "> <" + DateTimeValueTypeURI + "> .}\n";
        return query;
    }

    private String getStartNodeQuery(VitroRequest vreq) {
        String query = "SELECT ?existingStartNode WHERE {\n"
                + "?role <" + RoleToIntervalURI + "> ?intervalNode .\n"
                + "?intervalNode <" + VitroVocabulary.RDF_TYPE + "> <" + IntervalTypeURI + "> .\n"
                + "?intervalNode <" + IntervalToStartURI + "> ?existingStartNode . \n"
                + "?existingStartNode <" + VitroVocabulary.RDF_TYPE + "> <" + DateTimeValueTypeURI + "> .}";
        return query;
    }

    private String getIntervalNodeQuery(VitroRequest vreq) {
        String query = "SELECT ?existingIntervalNode WHERE { \n"
                + "?role <" + RoleToIntervalURI + "> ?existingIntervalNode . \n"
                + " ?existingIntervalNode <" + VitroVocabulary.RDF_TYPE + "> <" + IntervalTypeURI + "> . }\n";
        return query;
    }

    private String getDefaultProjectTypeQuery(VitroRequest vreq) {
        String query = "PREFIX core: <" + VIVO_NS + ">\n"
                + "PREFIX vitro: <" + VitroVocabulary.vitroURI + "> \n"
                + "SELECT ?existingProjectType WHERE { \n"
                + "    ?role ?predicate ?existingProject . \n"
                + "    ?existingProject vitro:mostSpecificType ?existingProjectType . \n";
        query += getFilterRoleToProjectPredicate("predicate");
        query += "}";
        return query;
    }

    private String getSubclassProjectTypeQuery(VitroRequest vreq) {
        String query = "PREFIX core: <" + VIVO_NS + ">\n"
                + "PREFIX rdfs: <" + VitroVocabulary.RDFS + ">\n"
                + "PREFIX vitro: <" + VitroVocabulary.vitroURI + "> \n"
                + "SELECT ?existingProjectType WHERE {\n"
                + "    ?role ?predicate ?existingProject . \n"
                + "    ?existingProject vitro:mostSpecificType ?existingProjectType . \n"
                + "    ?existingProjectType rdfs:subClassOf ?objectClassUri . \n";
        query += getFilterRoleToProjectPredicate("predicate");
        query += "}";
        return query;
    }

    private String getClassgroupProjectTypeQuery(VitroRequest vreq) {
        String query = "PREFIX core: <" + VIVO_NS + ">\n"
                + "PREFIX vitro: <" + VitroVocabulary.vitroURI + "> \n"
                + "SELECT ?existingProjectType WHERE { \n"
                + "    ?role ?predicate ?existingProject . \n"
                + "    ?existingProject vitro:mostSpecificType ?existingProjectType . \n"
                + "    ?existingProjectType vitro:inClassGroup ?classgroup . \n";
        query += getFilterRoleToProjectPredicate("predicate");
        query += "}";
        return query;
    }

    private String getProjectQuery(VitroRequest vreq) {
        //If role to activity predicate is the default query, then we need to replace with a union
        //of both realizedIn and the other
        String query = "PREFIX core: <" + VIVO_NS + ">";

        //Portion below for multiple possible predicates
        List<String> predicates = getRoleToProjectPredicates();
        List<String> addToQuery = new ArrayList<String>();
        query += "SELECT ?existingProject WHERE { \n"
                + " ?role ?predicate ?existingProject . \n ";
        query += getFilterRoleToProjectPredicate("predicate");
        query += "}";
        return query;
    }

    private String getExistingEndDateQuery(VitroRequest vreq) {
        String query = " SELECT ?existingEndDate WHERE {\n"
                + "?role <" + RoleToIntervalURI + "> ?intervalNode .\n"
                + "?intervalNode <" + VitroVocabulary.RDF_TYPE + "> <" + IntervalTypeURI + "> .\n"
                + "?intervalNode <" + IntervalToEndURI + "> ?endNode .\n"
                + "?endNode <" + VitroVocabulary.RDF_TYPE + "> <" + DateTimeValueTypeURI + "> .\n"
                + "?endNode <" + DateTimeValueURI + "> ?existingEndDate . }";
        return query;
    }

    private String getExistingStartDateQuery(VitroRequest vreq) {
        String query = "SELECT ?existingDateStart WHERE {\n"
                + "?role <" + RoleToIntervalURI + "> ?intervalNode .\n"
                + "?intervalNode <" + VitroVocabulary.RDF_TYPE + "> <" + IntervalTypeURI + "> .\n"
                + "?intervalNode <" + IntervalToStartURI + "> ?startNode .\n"
                + "?startNode <" + VitroVocabulary.RDF_TYPE + "> <" + DateTimeValueTypeURI + "> .\n"
                + "?startNode <" + DateTimeValueURI + "> ?existingDateStart . }";

        return query;
    }

    private String getRoleLabelQuery(VitroRequest vreq) {
        String query = "SELECT ?existingRoleLabel WHERE { \n"
                + "?role  <" + VitroVocabulary.LABEL + "> ?existingRoleLabel . }";
        return query;
    }

    private String getProjectLabelQuery(VitroRequest vreq) {
        String query = "PREFIX core: <" + VIVO_NS + ">"
                + "PREFIX rdfs: <" + RDFS.getURI() + "> \n";

        query += "SELECT ?existingTitle WHERE { \n"
                + "?role ?predicate ?existingProject . \n"
                + "?existingProject rdfs:label ?existingTitle . \n";
        query += getFilterRoleToProjectPredicate("predicate");
        query += "}";
        return query;
    }

    /**
     *
     * Set Fields and supporting methods
     */
    private void setFields(EditConfigurationVTwo editConfiguration, VitroRequest vreq, String predicateUri) {
        Map<String, FieldVTwo> fields = new HashMap<String, FieldVTwo>();
        //Multiple fields
        getProjectLabelField(editConfiguration, vreq, fields);
        getProjectTypeField(editConfiguration, vreq, fields);
        getProjectField(editConfiguration, vreq, fields);
        getRoleLabelField(editConfiguration, vreq, fields);
        getStartField(editConfiguration, vreq, fields);
        getEndField(editConfiguration, vreq, fields);
        //These fields are for the predicates that will be set later
        //TODO: Do these only if not using a parameter for the predicate?
        getRoleToProjectPredicateField(editConfiguration, vreq, fields);
        getProjectToRolePredicateField(editConfiguration, vreq, fields);
        editConfiguration.setFields(fields);
    }

    //This is a literal technically?
    private void getProjectToRolePredicateField(
            EditConfigurationVTwo editConfiguration, VitroRequest vreq,
            Map<String, FieldVTwo> fields) {
        String fieldName = "projectToRolePredicate";
        //get range data type uri and range language
        String stringDatatypeUri = XSD.xstring.toString();

        FieldVTwo field = new FieldVTwo();
        field.setName(fieldName);
        //queryForExisting is not being used anywhere in Field

        //Not really interested in validators here
        List<String> validators = new ArrayList<String>();
        field.setValidators(validators);

        //subjectUri and subjectClassUri are not being used in Field

        field.setOptionsType("UNDEFINED");
        //why isn't predicate uri set for data properties?
        field.setPredicateUri(null);
        field.setObjectClassUri(null);
        field.setRangeDatatypeUri(null);

        field.setLiteralOptions(new ArrayList<List<String>>());
        fields.put(field.getName(), field);
    }

    private void getRoleToProjectPredicateField(
            EditConfigurationVTwo editConfiguration, VitroRequest vreq,
            Map<String, FieldVTwo> fields) {
        String fieldName = "roleToProjectPredicate";
        //get range data type uri and range language
        String stringDatatypeUri = XSD.xstring.toString();

        FieldVTwo field = new FieldVTwo();
        field.setName(fieldName);
        //queryForExisting is not being used anywhere in Field

        //Not really interested in validators here
        List<String> validators = new ArrayList<String>();
        field.setValidators(validators);

        //subjectUri and subjectClassUri are not being used in Field

        field.setOptionsType("UNDEFINED");
        //why isn't predicate uri set for data properties?
        field.setPredicateUri(null);
        field.setObjectClassUri(null);
        field.setRangeDatatypeUri(null);

        field.setLiteralOptions(new ArrayList<List<String>>());

        fields.put(field.getName(), field);
    }

    //Label of "right side" of role, i.e. label for role roleIn Activity
    private void getProjectLabelField(EditConfigurationVTwo editConfiguration,
            VitroRequest vreq, Map<String, FieldVTwo> fields) {
        String fieldName = "projectLabel";
        //get range data type uri and range language
        String stringDatatypeUri = XSD.xstring.toString();

        FieldVTwo field = new FieldVTwo();
        field.setName(fieldName);
        //queryForExisting is not being used anywhere in Field    	

        List<String> validators = new ArrayList<String>();
        //If add mode or repair, etc. need to add label required validator
        if (isAddMode(vreq) || isRepairMode(vreq)) {
            validators.add("nonempty");
        }
        validators.add("datatype:" + stringDatatypeUri);
        field.setValidators(validators);

        //subjectUri and subjectClassUri are not being used in Field

        field.setOptionsType("UNDEFINED");
        //why isn't predicate uri set for data properties?
        field.setPredicateUri(null);
        field.setObjectClassUri(null);
        field.setRangeDatatypeUri(stringDatatypeUri);

        field.setLiteralOptions(new ArrayList<List<String>>());

        fields.put(field.getName(), field);
    }

    //type of "right side" of role, i.e. type of activity from role roleIn activity
    private void getProjectTypeField(
            EditConfigurationVTwo editConfiguration, VitroRequest vreq,
            Map<String, FieldVTwo> fields) {
        String fieldName = "projectType";
        //get range data type uri and range language

        FieldVTwo field = new FieldVTwo();
        field.setName(fieldName);

        List<String> validators = new ArrayList<String>();
        if (isAddMode(vreq) || isRepairMode(vreq)) {
            validators.add("nonempty");
        }
        field.setValidators(validators);

        //subjectUri and subjectClassUri are not being used in Field
        //TODO: Check if this is correct
        field.setOptionsType(getProjectTypeOptionsType().toString());
        //why isn't predicate uri set for data properties?
        field.setPredicateUri(null);
        field.setObjectClassUri(getProjectTypeObjectClassUri(vreq));
        field.setRangeDatatypeUri(null);


        HashMap<String, String> literalOptionsMap = getProjectTypeLiteralOptions();
        List<List<String>> fieldLiteralOptions = new ArrayList<List<String>>();
        Set<String> optionUris = literalOptionsMap.keySet();
        for (String optionUri : optionUris) {
            List<String> uriLabelArray = new ArrayList<String>();
            uriLabelArray.add(optionUri);
            uriLabelArray.add(literalOptionsMap.get(optionUri));
            fieldLiteralOptions.add(uriLabelArray);
        }
        field.setLiteralOptions(fieldLiteralOptions);

        fields.put(field.getName(), field);

    }

    //Assuming URI for activity for role?
    private void getProjectField(EditConfigurationVTwo editConfiguration,
            VitroRequest vreq, Map<String, FieldVTwo> fields) {
        String fieldName = "project";
        //get range data type uri and range language

        FieldVTwo field = new FieldVTwo();
        field.setName(fieldName);

        List<String> validators = new ArrayList<String>();
        field.setValidators(validators);

        //subjectUri and subjectClassUri are not being used in Field

        field.setOptionsType("UNDEFINED");
        //why isn't predicate uri set for data properties?
        field.setPredicateUri(null);
        field.setObjectClassUri(null);
        field.setRangeDatatypeUri(null);
        //empty
        field.setLiteralOptions(new ArrayList<List<String>>());

        fields.put(field.getName(), field);
    }
    
    private void getRoleLabelField(EditConfigurationVTwo editConfiguration,
            VitroRequest vreq, Map<String, FieldVTwo> fields) {
        String fieldName = "roleLabel";
        String stringDatatypeUri = XSD.xstring.toString();

        FieldVTwo field = new FieldVTwo();
        field.setName(fieldName);

        List<String> validators = new ArrayList<String>();
        validators.add("datatype:" + stringDatatypeUri);
        if (isShowRoleLabelField()) {
            validators.add("nonempty");
        }
        field.setValidators(validators);

        //subjectUri and subjectClassUri are not being used in Field

        field.setOptionsType("UNDEFINED");
        //why isn't predicate uri set for data properties?
        field.setPredicateUri(null);
        field.setObjectClassUri(null);
        field.setRangeDatatypeUri(stringDatatypeUri);
        //empty
        field.setLiteralOptions(new ArrayList<List<String>>());

        fields.put(field.getName(), field);
    }

    private void getStartField(EditConfigurationVTwo editConfiguration,
            VitroRequest vreq, Map<String, FieldVTwo> fields) {
        String fieldName = "startField";

        FieldVTwo field = new FieldVTwo();
        field.setName(fieldName);

        List<String> validators = new ArrayList<String>();
        field.setValidators(validators);

        //subjectUri and subjectClassUri are not being used in Field

        field.setOptionsType("UNDEFINED");
        //why isn't predicate uri set for data properties?
        field.setPredicateUri(null);
        field.setObjectClassUri(null);
        field.setRangeDatatypeUri(null);
        //empty
        field.setLiteralOptions(new ArrayList<List<String>>());

        //This logic was originally after edit configuration object created from json in original jsp
        field.setEditElement(
                new DateTimeWithPrecisionVTwo(field,
                VitroVocabulary.Precision.YEAR.uri(),
                VitroVocabulary.Precision.NONE.uri()));

        fields.put(field.getName(), field);
    }

    private void getEndField(EditConfigurationVTwo editConfiguration,
            VitroRequest vreq, Map<String, FieldVTwo> fields) {
        String fieldName = "endField";

        FieldVTwo field = new FieldVTwo();
        field.setName(fieldName);

        List<String> validators = new ArrayList<String>();
        field.setValidators(validators);

        //subjectUri and subjectClassUri are not being used in Field

        field.setOptionsType("UNDEFINED");
        //why isn't predicate uri set for data properties?
        field.setPredicateUri(null);
        field.setObjectClassUri(null);
        field.setRangeDatatypeUri(null);
        //empty
        field.setLiteralOptions(new ArrayList<List<String>>());

        //Set edit element
        field.setEditElement(
                new DateTimeWithPrecisionVTwo(field,
                VitroVocabulary.Precision.YEAR.uri(),
                VitroVocabulary.Precision.NONE.uri()));

        fields.put(field.getName(), field);
    }

    private void addPreprocessors(EditConfigurationVTwo editConfiguration, WebappDaoFactory wadf) {
        //Add preprocessor that will replace the role to activity predicate and inverse
        //with correct properties based on the activity type
        /*editConfiguration.addEditSubmissionPreprocessor(
                new RoleToActivityPredicatePreprocessor(editConfiguration, wadf));*/

    }

    //Ensure when overwritten that this includes the <> b/c otherwise the query won't work

    //Some values will have a default value
    public List<String> getRoleToProjectPredicates() {
        List<String> predicates = new ArrayList<String>();
        predicates.add("https://rdr.unimelb.edu.au/config/projectRoleIn");
        return predicates;
        //return ModelUtils.getPossiblePropertiesForRole();
    }

    public List<String> getProjectToRolePredicates() {
        List<String> predicates = new ArrayList<String>();
        predicates.add("https://rdr.unimelb.edu.au/config/relatedProjectRole");
        return predicates;
        //return ModelUtils.getPossibleInversePropertiesForRole();
    }

    /* Methods that check edit mode	 */
    public FrontEndEditingUtils.EditMode getEditMode(VitroRequest vreq) {
        List<String> roleToGrantPredicates = getRoleToProjectPredicates();
        return EditModeUtils.getEditMode(vreq, roleToGrantPredicates);
    }

    private boolean isAddMode(VitroRequest vreq) {
        return EditModeUtils.isAddMode(getEditMode(vreq));
    }

    private boolean isEditMode(VitroRequest vreq) {
        return EditModeUtils.isEditMode(getEditMode(vreq));
    }

    private boolean isRepairMode(VitroRequest vreq) {
        return EditModeUtils.isRepairMode(getEditMode(vreq));
    }
    /* URIS for various predicates */
    private final String VIVO_NS = "http://vivoweb.org/ontology/core#";
    private final String RoleToIntervalURI = VIVO_NS + "dateTimeInterval";
    private final String IntervalTypeURI = VIVO_NS + "DateTimeInterval";
    private final String IntervalToStartURI = VIVO_NS + "start";
    private final String IntervalToEndURI = VIVO_NS + "end";
    private final String StartYearPredURI = VIVO_NS + "startYear";
    private final String EndYearPredURI = VIVO_NS + "endYear";
    private final String DateTimeValueTypeURI = VIVO_NS + "DateTimeValue";
    private final String DateTimePrecisionURI = VIVO_NS + "dateTimePrecision";
    private final String DateTimeValueURI = VIVO_NS + "dateTime";

    //Form specific data
    public void addFormSpecificData(EditConfigurationVTwo editConfiguration, VitroRequest vreq) {
        HashMap<String, Object> formSpecificData = new HashMap<String, Object>();
        formSpecificData.put("editMode", getEditMode(vreq).name().toLowerCase());
        //Fields that will need select lists generated
        //Store field names
        List<String> objectSelect = new ArrayList<String>();
        objectSelect.add("projectType");
        //TODO: Check if this is the proper way to do this?
        formSpecificData.put("objectSelect", objectSelect);
        //Also put in show role label field
        formSpecificData.put("showRoleLabelField", isShowRoleLabelField());
        //Put in the fact that we require field
        editConfiguration.setFormSpecificData(formSpecificData);
    }

    public String getFilterRoleToProjectPredicate(String predicateVar) {
        String addFilter = "FILTER (";
        List<String> predicates = getRoleToProjectPredicates();
        List<String> filterPortions = new ArrayList<String>();
        for (String p : predicates) {
            filterPortions.add("(?" + predicateVar + "=<" + p + ">)");
        }
        addFilter += StringUtils.join(filterPortions, " || ");
        addFilter += ")";
        return addFilter;
    }

    //Types of options to populate drop-down for types for the "right side" of the role
    public static enum ProjectOptionTypes {

        VCLASSGROUP,
        CHILD_VCLASSES,
        HARDCODED_LITERALS
    };
    private final String N3_PREFIX = "@prefix core: <http://vivoweb.org/ontology/core#> .";
}
