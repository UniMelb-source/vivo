package edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.generators;

import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import edu.cornell.mannlib.vitro.webapp.controller.VitroRequest;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.EditConfigurationVTwo;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.SelectListGeneratorVTwo;
import edu.cornell.mannlib.vitro.webapp.search.beans.ProhibitedFromSearch;
import edu.cornell.mannlib.vitro.webapp.dao.DisplayVocabulary;
import edu.cornell.mannlib.vitro.webapp.dao.WebappDaoFactory;
import com.hp.hpl.jena.ontology.OntModel;
import javax.servlet.http.HttpSession;

/**
 *
 * @author dcliff
 */
public class AddResearchDataToPublicationGenerator extends AddResearchDataToThingGenerator {

    private static final Log log = LogFactory.getLog(AddResearchDataToPublicationGenerator.class);

    @Override
    protected final List<String> getN3Required() {
        return list(N3_PREFIX
                + "?publication ands:relatedResearchData ?researchDataUri. \n"
                + "?researchDataUri ands:relatedInformationResource ?publication. \n"
                + "?researchDataUri a ands:ResearchData ;");
    }

    @Override
    protected final Log getLog() {
        return log;
    }

    @Override
    protected final String getTemplate() {
        return "addResearchDataToPublication.ftl";
    }

    protected final Map<String, String> getInheritedCustodianDepartments(String subjectUri) {
        String query = SPARQL_PREFIX
                + "SELECT DISTINCT ?org ?orgLabel WHERE { \n"
                + "<" + subjectUri + "> core:informationResourceInAuthorship ?la. \n"
                + "?la core:linkedAuthor ?person. \n"
                + "?person core:personInPosition ?position. \n"
                + "?position core:positionInOrganization ?org. \n"
                + "?org rdfs:label ?orgLabel}";
        return getResults(query, "org", "orgLabel");
    }

    protected final Map<String, String> getInheritedCustodians(String subjectUri) {
        String query = SPARQL_PREFIX
                + "SELECT DISTINCT ?person ?personLabel WHERE { \n"
                + "<" + subjectUri + "> core:informationResourceInAuthorship ?la. \n"
                + "?la core:linkedAuthor ?person. \n"
                + "?person rdfs:label ?personLabel}";
        return getResults(query, "person", "personLabel");
    }

    @Override
    protected final String getSubjectName() {
        return "publication";
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
