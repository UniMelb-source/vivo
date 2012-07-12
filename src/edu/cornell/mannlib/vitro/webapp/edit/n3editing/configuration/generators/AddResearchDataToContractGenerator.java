package edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.generators;

import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author tom
 */
public class AddResearchDataToContractGenerator extends AddResearchDataToThingGenerator {

    private static final Log log = LogFactory.getLog(AddResearchDataToContractGenerator.class);

    protected List<String> getN3Required() {
        return list(N3_PREFIX
                + "?contract unimelb-rdr:agreementHasResearchDataOutput ?researchDataUri. \n"
                + "?researchDataUri unimelb-rdr:isResearchDataForAgreement ?contract. \n"
                + "?researchDataUri a ands:ResearchData ;");
    }

    @Override
    protected final Log getLog() {
        return log;
    }

    @Override
    protected final String getTemplate() {
        return "addResearchDataToContract.ftl";
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
        return "contract";
    }

    @Override
    protected final String getPredicateName() {
        return "predicate";
    }

    @Override
    protected final String getObjectName() {
        return "researchDataUri";
    }
}
