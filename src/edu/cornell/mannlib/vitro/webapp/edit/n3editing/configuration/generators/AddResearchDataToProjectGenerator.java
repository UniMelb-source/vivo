package edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.generators;

import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author tom
 */
public class AddResearchDataToProjectGenerator extends AddResearchDataToThingGenerator {

    private static final Log log = LogFactory.getLog(AddResearchDataToProjectGenerator.class);

    @Override
    protected final Log getLog() {
        return log;
    }

    @Override
    protected final String getTemplate() {
        return "addResearchDataToProject.ftl";
    }

    @Override
    protected final List<String> getN3Required() {
        List<String> n3Required = super.getN3Required();
        n3Required.add(N3_PREFIX
                + "?project unimelb-rdr:projectHasResearchDataOutput ?researchDataUri. \n"
                + "?researchDataUri unimelb-rdr:isResearchDataForProject ?project. \n"
                + "?researchDataUri a ands:ResearchData ;");
        return n3Required;
    }

    @Override
    protected final Map<String, String> getInheritedCustodians(String subjectUri) {
        //ands:associatedPrincipleInvestigator
        String query = SPARQL_PREFIX
                + "SELECT DISTINCT ?person ?personLabel WHERE { \n"
                + "<" + subjectUri + "> ands:isManagedBy ?person. \n"
                + "?person rdfs:label ?personLabel}";
        return getResults(query, "person", "personLabel");
    }

    @Override
    protected final Map<String, String> getInheritedCustodianDepartments(String subjectUri) {
        //ands:isManagedBy
        String query = SPARQL_PREFIX
                + "SELECT DISTINCT ?org ?orgLabel WHERE { \n"
                + "<" + subjectUri + "> ands:isManagedBy ?person. \n"
                + "?person core:personInPosition ?position. \n"
                + "?position core:positionInOrganization ?org. \n"
                + "?org rdfs:label ?orgLabel}";
        return getResults(query, "org", "orgLabel");
    }

    @Override
    protected final String getSubjectName() {
        return "project";
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
