package edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.generators;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.vocabulary.XSD;
import edu.cornell.mannlib.vitro.webapp.controller.VitroRequest;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.EditConfigurationUtils;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.EditConfigurationVTwo;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.FieldVTwo;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.validators.AntiXssValidation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;

/**
 *
 * @author dcliff
 */
public class AddResearchDataToPublicationGenerator extends VivoBaseGenerator implements EditConfigurationGenerator
{
    private Model queryModel;

    private int inheritedCustodianDeptCount = 0;
    private int inheritedSubjectCount = 0;
    private int inheritedCustodianCount = 0;

    public EditConfigurationVTwo getEditConfiguration(VitroRequest vreq, HttpSession session)
    {        

        EditConfigurationVTwo editConfiguration = new EditConfigurationVTwo();

        //Creating an instance of SparqlEvaluateVTwo so that we can run queries
        //on our optional inferred statements.
        queryModel = editConfiguration.getQueryModelSelector().getModel(vreq, session.getServletContext());

        //Basic intialization
        initBasics(editConfiguration, vreq);
        initPropertyParameters(vreq, session, editConfiguration);

        //Overriding URL to return to (as we won't be editing)
        setUrlToReturnTo(editConfiguration, vreq);

        //set variable names
        editConfiguration.setVarNameForSubject("publication");
        editConfiguration.setVarNameForPredicate("predicate");
        editConfiguration.setVarNameForObject("researchDataUri");

        // Required N3
        editConfiguration.setN3Required(list(getN3NewResearchData()));
        editConfiguration.addNewResource("researchDataUri", DEFAULT_NS_TOKEN);

        //TODO: Include optional later? ...        
        editConfiguration.setN3Optional(generateN3Optional());

        //In scope
        setUrisAndLiteralsInScope(editConfiguration, vreq);

        //on Form
        setUrisAndLiteralsOnForm(editConfiguration, vreq);

        //Sparql queries
        setSparqlQueries(editConfiguration, vreq);

        //set fields
        setFields(editConfiguration);

        //template file
        editConfiguration.setTemplate("addResearchDataToPublication.ftl");

        //TODO: add validators

        editConfiguration.addValidator(new AntiXssValidation());

        //NOITCE this generator does not run prepare() since it
        //is never an update and has no SPARQL for existing

        return editConfiguration;
    }

    private List<String> generateN3Optional() {
		return list(
                getN3ForResearchDataLabel(),
                getN3ForResearchDataDescription());

	}

    private String getN3ForResearchDataLabel()
    {
        return getN3PrefixString() + 
            "?researchDataUri rdfs:label ?researchDataLabel";
    }

    private String getN3ForResearchDataDescription()
    {
        return getN3PrefixString() +
            "?researchDataUri ands:researchDataDescription ?dataDescription";
    }

    private Map<String, String> getInheritedSubjectAreaLabelAndUri()
    {
        Map<String, String> results = new HashMap<String, String>();        

        String query = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
	      	"PREFIX rdf:  <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
	        "PREFIX bibo: <http://purl.org/ontology/bibo/>\n" +
	        "PREFIX core: <http://vivoweb.org/ontology/core#>" +
            "SELECT ?subjectArea ?subjectAreaLabel WHERE {\n" +
            "?publication core:hasSubjectArea ?subjectArea .\n" +
            "?subjectArea rdfs:label ?subjectAreaLabel\n" +
            "}";

        ResultSet rs = sparqlQuery(queryModel, query);
        
        while(rs.hasNext())
        {
            QuerySolution qs = rs.nextSolution();
            String uriString = qs.get("subjectArea").toString();
            String labelString = qs.get("subjectAreaLabel").toString();
            results.put(uriString, labelString);
        }

        return results;
    }

    private Map<String, String> getInheritedCustodianDepartmentsLabelAndUri()
    {
        Map<String, String> results = new HashMap<String, String>();

        String query = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n" +
                "PREFIX rdf:  <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n" +
                "PREFIX bibo: <http://purl.org/ontology/bibo/> \n" +
                "PREFIX core: <http://vivoweb.org/ontology/core#> \n" +
                "SELECT DISTINCT ?org ?olabel WHERE { \n" +
                "?publication core:informationResourceInAuthorship ?la. \n" +
                "?la core:linkedAuthor ?person. \n" +
                "?person core:personInPosition ?position. \n" +
                "?position core:positionInOrganization ?org. \n" +
                "?person rdfs:label ?plabel. \n" +
                "?org rdfs:label ?olabel}";

        ResultSet rs = sparqlQuery(queryModel, query);        

        while(rs.hasNext())
        {
            QuerySolution qs = rs.nextSolution();
            String uriString = qs.get("org").toString();
            String labelString = qs.get("olabel").toString();
            results.put(uriString, labelString);
        }

        return results;
    }

    private Map<String, String> getInheritedCustodiansLabelAndUri()
    {
        Map<String, String> results = new HashMap<String, String>();

        String query = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n" +
                "PREFIX rdf:  <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n" +
                "PREFIX bibo: <http://purl.org/ontology/bibo/> \n" +
                "PREFIX core: <http://vivoweb.org/ontology/core#> \n" +
                "SELECT DISTINCT ?person ?plabel WHERE { \n" +
                "?publication core:informationResourceInAuthorship ?la. \n" +
                "?la core:linkedAuthor ?person. \n" +
                "?person core:personInPosition ?position. \n" +
                "?person rdfs:label ?plabel}";

        ResultSet rs = sparqlQuery(queryModel, query);        
        
        while(rs.hasNext())
        {
            QuerySolution qs = rs.nextSolution();
            String uriString = qs.get("person").toString();
            String labelString = qs.get("plabel").toString();
            results.put(uriString, labelString);
        }

        return results;
    }

    private ResultSet sparqlQuery(Model queryModel, String queryString)
    {
        QueryExecution qe = null;
        try
        {
            Query query = QueryFactory.create(queryString);
            qe = QueryExecutionFactory.create(query, queryModel);
            ResultSet results = null;
            results = qe.execSelect();
            if( results.hasNext())
            {
                return results;
            }
            else
            {
                return null;
            }
        }
        catch(Exception ex)
        {
            throw new Error("could not parse SPARQL in queryToUri: \n" + queryString + '\n' + ex.getMessage());
        }
        finally
        {
            if( qe != null)
            {
                qe.close();
            }
        }
    }

	private void setUrlToReturnTo(EditConfigurationVTwo editConfiguration, VitroRequest vreq)
    {
		editConfiguration.setUrlPatternToReturnTo(EditConfigurationUtils.getFormUrlWithoutContext(vreq));
	}

	public String getN3PrefixString()
    {
		return "@prefix core: <" + vivoCore + "> .\n" +
               "@prefix rdfs: <" + rdfs + "> .\n" +
               "@prefix ands: <http://purl.org/ands/ontologies/vivo/> .\n";
	}

	private String getN3NewResearchData()
    {
		return getN3PrefixString() +
               "?publication  ands:hasResearchData  ?researchDataUri. \n" +
               "?researchDataUri ands:publishedIn ?publication. \n" +
               "?researchDataUri a ands:ResearchData ;";
	}

	/** Set URIS and Literals In Scope and on form and supporting methods	 */
    private void setUrisAndLiteralsInScope(EditConfigurationVTwo editConfiguration, VitroRequest vreq) {
    	//Uris in scope always contain subject and predicate
    	HashMap<String, List<String>> urisInScope = new HashMap<String, List<String>>();
    	urisInScope.put(editConfiguration.getVarNameForSubject(),
    			Arrays.asList(new String[]{editConfiguration.getSubjectUri()}));
    	urisInScope.put(editConfiguration.getVarNameForPredicate(),
    			Arrays.asList(new String[]{editConfiguration.getPredicateUri()}));
    	editConfiguration.setUrisInScope(urisInScope);
    	//no literals in scope
    }

    private void setUrisAndLiteralsOnForm(EditConfigurationVTwo editConfiguration, VitroRequest vreq) {
    	List<String> urisOnForm = new ArrayList<String>();
    	
    	editConfiguration.setUrisOnform(urisOnForm);

    	List<String> literalsOnForm = list("researchDataLabel",
    			"dataDescription");
    	editConfiguration.setLiteralsOnForm(literalsOnForm);
    }

    /** Set SPARQL Queries and supporting methods. */
    private void setSparqlQueries(EditConfigurationVTwo editConfiguration, VitroRequest vreq) {        
    	editConfiguration.setSparqlForExistingUris(new HashMap<String, String>());
    	editConfiguration.setSparqlForExistingLiterals(new HashMap<String, String>());
    	editConfiguration.setSparqlForAdditionalUrisInScope(new HashMap<String, String>());
    	editConfiguration.setSparqlForAdditionalLiteralsInScope(new HashMap<String, String>());
    }

    /**
	 *
	 * Set Fields and supporting methods
	 */

	private void setFields(EditConfigurationVTwo editConfiguration) {
    	setResearchDataLabelField(editConfiguration);
    	setDataDescriptionField(editConfiguration);
    }

	private void setResearchDataLabelField(EditConfigurationVTwo editConfiguration) {
		editConfiguration.addField(new FieldVTwo().
				setName("researchDataLabel").
				setValidators(list("datatype:" + XSD.xstring.toString())).
				setRangeDatatypeUri(XSD.xstring.toString())
				);
	}

	private void setDataDescriptionField(EditConfigurationVTwo editConfiguration) {
		editConfiguration.addField(new FieldVTwo().
				setName("dataDescription").
				setValidators(list("datatype:" + XSD.xstring.toString())).
				setRangeDatatypeUri(XSD.xstring.toString())
				);
	}

    static final String DEFAULT_NS_TOKEN = null; //null forces the default NS
}
