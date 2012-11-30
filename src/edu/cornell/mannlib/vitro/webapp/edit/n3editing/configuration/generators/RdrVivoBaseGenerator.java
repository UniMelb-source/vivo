package edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.generators;

import com.hp.hpl.jena.query.*;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.shared.PrefixMapping;
import edu.cornell.mannlib.vedit.beans.LoginStatusBean;
import edu.cornell.mannlib.vitro.webapp.beans.UserAccount;
import edu.cornell.mannlib.vitro.webapp.controller.VitroRequest;
import edu.cornell.mannlib.vitro.webapp.dao.jena.ModelContext;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.EditConfigurationVTwo;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.FieldVTwo;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.N3ValidatorVTwo;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.preprocessors.SetEntityReturnPreprocessor;
import java.util.*;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.WordUtils;
import org.apache.commons.logging.Log;

/**
 *
 * @author tom
 */
public abstract class RdrVivoBaseGenerator extends VivoBaseGenerator implements EditConfigurationGenerator {

    protected Model queryModel;
    protected UserAccount userAccount;
    protected static final String DEFAULT_NS_TOKEN = null; //null forces the default NS
    protected static final String N3_PREFIX = "@prefix core: <" + vivoCore + "> .\n"
            + "@prefix rdfs: <" + rdfs + "> .\n"
            + "@prefix ands: <http://purl.org/ands/ontologies/vivo/> .\n"
            + "@prefix foaf: <http://xmlns.com/foaf/0.1/> .\n"
            + "@prefix bibo: <http://purl.org/ontology/bibo/> .\n"
            + "@prefix unimelb-rdr: <https://rdr.unimelb.edu.au/config/> .\n";
    protected static final String SPARQL_PREFIX = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n"
            + "PREFIX rdf:  <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n"
            + "PREFIX bibo: <http://purl.org/ontology/bibo/> \n"
            + "PREFIX core: <http://vivoweb.org/ontology/core#> \n"
            + "PREFIX ands: <http://purl.org/ands/ontologies/vivo/> \n"
            + "PREFIX unimelb-rdr: <https://rdr.unimelb.edu.au/config/> \n"
            + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n"
            + "PREFIX vitro: <http://vitro.mannlib.cornell.edu/ns/vitro/0.7#> \n";

    protected abstract List<String> getN3Optional();

    protected abstract List<String> getUrisOnForm();

    protected abstract List<N3ValidatorVTwo> getValidators();

    protected abstract Log getLog();

    protected abstract String getTemplate();

    protected abstract String getForwardUri();

    protected abstract List<String> getN3Required();

    protected abstract Map<String, String> getNewResources(VitroRequest vreq);

    protected abstract String getSubjectName();

    protected abstract String getPredicateName();

    protected abstract String getObjectName();

    protected void additionalProcessing(EditConfigurationVTwo editConfiguration) {
    }

    public EditConfigurationVTwo getEditConfiguration(VitroRequest vreq, HttpSession session) {
        userAccount = LoginStatusBean.getCurrentUser(vreq);
        EditConfigurationVTwo editConfiguration = new EditConfigurationVTwo();

        //queryModel = editConfiguration.getQueryModelSelector().getModel(vreq, session.getServletContext());
        queryModel = ModelContext.getOntModelSelector(session.getServletContext()).getFullModel();

        //Basic intialization
        initBasics(editConfiguration, vreq);
        initPropertyParameters(vreq, session, editConfiguration);

        //Overriding URL to return to (as we won't be editing)
        //setUrlToReturnTo(editConfiguration, vreq);
        editConfiguration.setEntityToReturnTo("?childOrParent");

        editConfiguration.setVarNameForSubject(getSubjectName());
        editConfiguration.setVarNameForPredicate(getPredicateName());
        editConfiguration.setVarNameForObject(getObjectName());

        editConfiguration.setN3Required(getN3Required());

        editConfiguration.setN3Optional(getN3Optional());

        editConfiguration.setNewResources(getNewResources(vreq));

        setUrisAndLiteralsInScope(editConfiguration, vreq);

        editConfiguration.setUrisOnform(getUrisOnForm());
        editConfiguration.setLiteralsOnForm(getLiteralsOnForm());
        setSparqlQueries(editConfiguration, vreq);
        for (FieldVTwo field : getFields()) {
            editConfiguration.addField(field);
        }
        editConfiguration.setTemplate(getTemplate());

        for (N3ValidatorVTwo validator : getValidators()) {
            editConfiguration.addValidator(validator);
        }

        editConfiguration.setFormSpecificData(getFormSpecificData(editConfiguration, vreq));
        String forwardUri = getForwardUri();
        if (null != forwardUri) {
            editConfiguration.addEditSubmissionPreprocessor(new SetEntityReturnPreprocessor(editConfiguration, forwardUri));
        }
        
        additionalProcessing(editConfiguration);
        
        prepare(vreq, editConfiguration);

        return editConfiguration;
    }

    /**
     * Set URIS and Literals In Scope and on form and supporting methods
     */
    protected final void setUrisAndLiteralsInScope(EditConfigurationVTwo editConfiguration, VitroRequest vreq) {
        Map<String, List<String>> urisInScope = new HashMap<String, List<String>>();
        Map<String, List<Literal>> literalsInScope = new HashMap<String, List<Literal>>();

        urisInScope.put(editConfiguration.getVarNameForSubject(),
                Arrays.asList(new String[]{editConfiguration.getSubjectUri()}));
        urisInScope.put(editConfiguration.getVarNameForPredicate(),
                Arrays.asList(new String[]{editConfiguration.getPredicateUri()}));
        editConfiguration.setUrisInScope(urisInScope);

        editConfiguration.setLiteralsInScope(literalsInScope);
    }

    protected final ResultSet sparqlQuery(Model queryModel, String queryString) {
        QueryExecution qe = null;
        try {
            Query query = QueryFactory.create(queryString, Syntax.syntaxARQ);
            qe = QueryExecutionFactory.create(query, queryModel);
            ResultSet results = null;
            results = qe.execSelect();
            return results;
        } catch (Exception e) {
            getLog().error("Error parsing SPARQL: \n" + queryString + '\n' + e.getMessage());
            throw new Error("Error parsing SPARQL: \n" + queryString + '\n' + e.getMessage());
        }
    }

    protected final Map<String, String> getResults(String query, String uriName, String labelName) {
        Map<String, String> results = new HashMap<String, String>();
        ResultSet rs = sparqlQuery(queryModel, query);

        while (rs.hasNext()) {
            QuerySolution qs = rs.nextSolution();
            String uriString = qs.get(uriName).toString();
            //String valueString = qs.get(valueName).toString();
            String labelString = qs.get(labelName).asNode().toString(PrefixMapping.Standard, true);
            labelString = labelString.substring(1, labelString.indexOf("\"", 1));


            results.put(uriString, labelString);
        }
        return results;
    }

    protected final List<String> getResults(String query, String uriName) {
        List<String> results = new ArrayList<String>();
        ResultSet rs = sparqlQuery(queryModel, query);
        getLog().info("Results retrieved:");
        while (rs.hasNext()) {
            QuerySolution qs = rs.nextSolution();
            String uriString = qs.get(uriName).toString();
            results.add(uriString);
            getLog().info(" - " + uriString);
        }
        getLog().info("End results");
        return results;
    }

    /**
     * Set SPARQL Queries and supporting methods.
     */
    protected final void setSparqlQueries(EditConfigurationVTwo editConfiguration, VitroRequest vreq) {
        editConfiguration.setSparqlForExistingUris(new HashMap<String, String>());
        editConfiguration.setSparqlForExistingLiterals(new HashMap<String, String>());
        editConfiguration.setSparqlForAdditionalUrisInScope(new HashMap<String, String>());
        editConfiguration.setSparqlForAdditionalLiteralsInScope(new HashMap<String, String>());
    }

    protected final void setUrlToReturnTo(EditConfigurationVTwo editConfiguration, VitroRequest vreq) {
        //editConfiguration.setUrlToReturnTo(EditConfigurationUtils.getFormUrl(vreq));
        //editConfiguration.setUrlPatternToReturnTo(EditConfigurationUtils.getFormUrlWithoutContext(vreq));
    }

    protected final void setField(EditConfigurationVTwo editConfiguration,
            String fieldName,
            List<String> validators,
            String rangeDatatypeUri,
            FieldVTwo.OptionsType optionsType,
            List<List<String>> literalOptions,
            String objectClassUri) {
        FieldVTwo field = new FieldVTwo();

        field.setName(fieldName);
        if (validators != null) {
            field.setValidators(validators);
        }
        if (rangeDatatypeUri != null) {
            field.setRangeDatatypeUri(rangeDatatypeUri);
        }
        if (optionsType != null) {
            field.setOptionsType(optionsType);
        }
        if (literalOptions != null) {
            field.setLiteralOptions(literalOptions);
        }
        if (objectClassUri != null) {
            field.setObjectClassUri(objectClassUri);
        }
        editConfiguration.addField(field);
    }

    protected final Map<String, String> getInheritedItemLabelAndUri(String subjectUri, String relationship) {
        String query = SPARQL_PREFIX
                + "SELECT ?item ?itemLabel WHERE { \n"
                + "<" + subjectUri + "> " + relationship + " ?item . \n"
                + "?item rdfs:label ?itemLabel \n"
                + "}";
        return getResults(query, "item", "itemLabel");
    }

    protected HashMap<String, Object> getFormSpecificData(EditConfigurationVTwo editConfiguration, VitroRequest vreq) {
        HashMap<String, Object> formSpecificData = new HashMap<String, Object>();

        formSpecificData.put("forwardUri", getForwardUri());

        return formSpecificData;
    }

    protected List<FieldVTwo> getFields() {
        List<FieldVTwo> fields = new ArrayList<FieldVTwo>();

        fields.add(new CustomFieldVTwo("forwardUri", null, null, null, null, null));

        return fields;
    }

    protected List<String> getLiteralsOnForm() {
        return list("forwardUri");
    }

    protected Map<String, String> getAttribute(List<String> subjects, String predicate, boolean process) {
        Map< String, String> resultMap = new HashMap<String, String>(subjects.size());

        String queryFormat = SPARQL_PREFIX
                + "SELECT DISTINCT ?label WHERE { \n"
                + "%s %s ?label \n"
                + "}";
        for (String subject : subjects) {
            String query = String.format(queryFormat, subject, predicate);
            List<String> results = getResults(query, "label");
            if (!results.isEmpty()) {
                String rawResult = results.get(0);
                int atIndex;
                atIndex = rawResult.indexOf('@');
                if (atIndex > 0) {
                    rawResult = rawResult.substring(0, atIndex);
                }
                if (process) {
                    rawResult = WordUtils.capitalize(rawResult);
                }
                resultMap.put(subject, rawResult);
            } else {
                resultMap.put(subject, "UNSET");
            }
        }

        return resultMap;
    }
}
