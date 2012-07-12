package edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.generators;

import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.EditConfigurationUtils;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.EditConfigurationVTwo;
import java.util.HashMap;
import java.util.ArrayList;
import edu.cornell.mannlib.vitro.webapp.controller.VitroRequest;

/**
 *
 * @author tom
 */
public class AddResearchDataToAgreementGenerator extends AddResearchDataToThingGenerator {

    private static final Log log = LogFactory.getLog(AddResearchDataToAgreementGenerator.class);

    protected List<String> getN3Required() {
        return list(N3_PREFIX
                + "?agreement unimelb-rdr:agreementHasResearchDataOutput ?researchDataUri. \n"
                + "?researchDataUri unimelb-rdr:isResearchDataForAgreement ?agreement. \n"
                + "?researchDataUri a ands:ResearchData ;");
    }

    @Override
    protected final Log getLog() {
        return log;
    }

    @Override
    protected final String getTemplate() {
        return "addResearchDataToAgreement.ftl";
    }

    @Override
    protected final Map<String, String> getInheritedCustodians(String subjectUri) {
        String query = SPARQL_PREFIX
                + "SELECT DISTINCT ?person ?personLabel WHERE { \n"
                + "<" + subjectUri + "> core:contributingRole ?role . \n"
                + "?role ?roleProp ?person . \n"
                + "?roleProp rdfs:subPropertyOf core:roleOf . \n"
                + "?person rdfs:label ?personLabel}";
        return getResults(query, "person", "personLabel");
    }

    @Override
    protected final Map<String, String> getInheritedCustodianDepartments(String subjectUri) {
        String query = SPARQL_PREFIX
                + "SELECT DISTINCT ?org ?orgLabel WHERE { \n"
                + "<" + subjectUri + "> core:contributingRole ?role . \n"
                + "?role ?roleProp ?person . \n"
                + "?roleProp rdfs:subPropertyOf core:roleOf . \n"
                + "?person core:personInPosition ?position. \n"
                + "?position core:positionInOrganization ?org. \n"
                + "?org rdfs:label ?orgLabel}";
        return getResults(query, "org", "orgLabel");
    }

    @Override
    protected final String getSubjectName() {
        return "agreement";
    }

    @Override
    protected final String getPredicateName() {
        return "predicate";
    }

    @Override
    protected final String getObjectName() {
        return "researchDataUri";
    }

    private String getSpecificType(String subjectUri) {
        String query = SPARQL_PREFIX
                +"SELECT ?type WHERE { \n"
                + "<" + subjectUri + "> rdf:type ?type}";
        List<String> results = getResults(query, "type");
        if(results.contains("http://vivoweb.org/ontology/core#Contract")) {
            return "Contract";
        }
        else if(results.contains("http://vivoweb.org/ontology/core#Grant")) {
            return "Grant";
        }
        return "Agreement";
    }

    @Override
    protected HashMap<String, Object> getFormSpecificData(EditConfigurationVTwo editConfiguration, VitroRequest vreq) {
        HashMap<String, Object> formSpecificData = super.getFormSpecificData(editConfiguration, vreq);
        String subjectUri = EditConfigurationUtils.getSubjectUri(vreq);
        formSpecificData.put("agreementType", getSpecificType(subjectUri));

        return formSpecificData;
    }
}
