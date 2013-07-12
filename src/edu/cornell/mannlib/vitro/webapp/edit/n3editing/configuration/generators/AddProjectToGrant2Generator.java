/* $This file is distributed under the terms of the license in /doc/license.txt$ */
package edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.generators;

import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AddProjectToGrant2Generator extends AddProjectToThingGenerator {

    private static final Log log = LogFactory.getLog(AddProjectToGrant2Generator.class);
    
    @Override
    protected final List<String> getN3Required() {
        return list(N3_PREFIX
                    + "?grant core:fundingVehicleFor ?projectUri . \n"
                    + "?projectUri core:hasFundingVehicle ?grant . \n"
                    + "?projectUri a core:Project ;");
    }
    
    @Override
    protected final Log getLog() {
        return log;
    }
    
    @Override
    protected final String getTemplate() {
        return "addProjectToGrant2.ftl";
    }
    
    @Override
    protected final Map<String, String> getInheritedPersonsLabelAndUri(String subjectUri) {
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
        return "grant";
    }

    @Override
    protected final String getPredicateName() {
        return "predicate";
    }

    @Override
    protected final String getObjectName() {
        return "projectUri";
    }
}
