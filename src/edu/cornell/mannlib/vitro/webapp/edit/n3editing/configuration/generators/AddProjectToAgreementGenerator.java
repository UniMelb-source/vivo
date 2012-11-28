/* $This file is distributed under the terms of the license in /doc/license.txt$ */
package edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.generators;

import edu.cornell.mannlib.vitro.webapp.controller.VitroRequest;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.EditConfigurationUtils;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.EditConfigurationVTwo;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AddProjectToAgreementGenerator extends AddProjectToThingGenerator {

    private static final Log log = LogFactory.getLog(AddProjectToAgreementGenerator.class);

    @Override
    protected final List<String> getN3Required() {
        return list(N3_PREFIX
                + "?agreement unimelb-rdr:fundingVehicleFor ?projectUri . \n"
                + "?projectUri unimelb-rdr:hasFundingVehicle ?agreement . \n"
                + "?projectUri a core:Project;");
    }

    @Override
    protected final Log getLog() {
        return log;
    }

    @Override
    protected final String getTemplate() {
        return "addProjectToAgreement.ftl";
    }

    @Override
    protected final Map<String, String> getInheritedRolesLabelAndUri(String subjectUri) {
        String query = SPARQL_PREFIX
                + "SELECT DISTINCT ?role ?roleLabel WHERE { \n"
                + "<" + subjectUri + "> core:contributingRole ?role . \n"
                + "?role ?roleProp ?person . \n"
                + "?roleProp rdfs:subPropertyOf core:roleOf . \n"
                + "?role rdfs:label ?roleLabel}";
        return getResults(query, "role", "roleLabel");
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
        return "projectUri";
    }

    private String getSpecificType(String subjectUri) {
        String query = SPARQL_PREFIX
                + "SELECT ?type WHERE { \n"
                + "<" + subjectUri + "> rdf:type ?type}";
        List<String> results = getResults(query, "type");
        if (results.contains("http://vivoweb.org/ontology/core#Contract")) {
            return "Contract";
        } else if (results.contains("http://vivoweb.org/ontology/core#Grant")) {
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
