/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.preprocessors;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.vocabulary.XSD;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.shared.Lock;

import edu.cornell.mannlib.vitro.webapp.beans.ObjectProperty;
import edu.cornell.mannlib.vitro.webapp.controller.VitroRequest;
import edu.cornell.mannlib.vitro.webapp.dao.VitroVocabulary;
import edu.cornell.mannlib.vitro.webapp.dao.WebappDaoFactory;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.BaseEditSubmissionPreprocessorVTwo;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.EditConfigurationVTwo;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.MultiValueEditSubmission;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.FieldVTwo;
import org.vivoweb.webapp.util.ModelUtils;

public class SetEntityReturnPreprocessor extends BaseEditSubmissionPreprocessorVTwo {

    protected static final Log log = LogFactory.getLog(SetEntityReturnPreprocessor.class);
    protected static String itemType;
    protected static String roleToItemPredicate;
    protected static String itemToRolePredicate;
    
    private String forwardUri;

    public SetEntityReturnPreprocessor(EditConfigurationVTwo editConfig, String forwardUri) {
        super(editConfig);
        this.forwardUri = forwardUri;
    }

	public void preprocess(MultiValueEditSubmission submission) {
    	try {
    		Map<String, List<String>> urisFromForm = submission.getUrisFromForm();
            Map<String, List<Literal>> literalsFromForm = submission.getLiteralsFromForm();
            String uri = "?childOrParent";

            if(urisFromForm.containsKey(uri)) {
    			urisFromForm.remove(uri);
            }
            String redirectUri;
            
            if(literalsFromForm.containsKey("redirectForward")) {
                redirectUri = this.forwardUri;
            }
            else {
                redirectUri = this.editConfiguration.getSubjectUri();
            }
    			
            urisFromForm.put(uri, Arrays.asList(new String[]{redirectUri}));
            submission.setUrisFromForm(urisFromForm);
    	}
    	catch (Exception e) {
            log.error("Error selecting child or parent to redirect to.");
        }
        
    }
}
