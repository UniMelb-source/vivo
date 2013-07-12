package edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.generators;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author dcliff
 */
public class AddResearchDataToResearcherGenerator extends AddResearchDataToThingGenerator {

    private static final Log log = LogFactory.getLog(AddResearchDataToResearcherGenerator.class);

    @Override
    protected List<String> getN3Required() {
        List<String> n3Required = super.getN3Required();
        n3Required.addAll(list(
            N3_PREFIX + "?researcher ands:isCollectorOf ?researchDataUri .",
            N3_PREFIX + "?researchDataUri ands:hasCollector ?researcher ."));
        return n3Required;
    }

    @Override
    protected Log getLog() {
        return log;
    }

    @Override
    protected String getTemplate() {
        return "addResearchDataToResearcher.ftl";
    }

    protected Map<String, String> getInheritedCustodianDepartments(String subjectUri) {
        String query = SPARQL_PREFIX
                + "SELECT DISTINCT ?org ?orgLabel WHERE { \n"
                + "<" + subjectUri + "> core:personInPosition ?position. \n"
                + "?position core:positionInOrganization ?org. \n"
                + "?org rdfs:label ?orgLabel}";
        return getResults(query, "org", "orgLabel");
    }

    protected Map<String, String> getInheritedCustodians(String subjectUri) {
        return Collections.<String, String>emptyMap();
    }

    @Override
    protected String getSubjectName() {
        return "researcher";
    }

    @Override
    protected String getPredicateName() {
        return "predicate";
    }

    @Override
    protected String getObjectName() {
        return "researchDataUri";
    }
}
