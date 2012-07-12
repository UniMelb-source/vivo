/* $This file is distributed under the terms of the license in /doc/license.txt$ */
package edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.generators;

import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AddProjectToPersonGenerator extends AddProjectToThingGenerator {

    private static final Log log = LogFactory.getLog(AddProjectToPersonGenerator.class);

    @Override
    protected final List<String> getN3Required() {
        return list(N3_PREFIX
                + "?person  unimelb-rdr:zwoASXx8T9  ?projectUri . \n"
                + "?projectUri unimelb-rdr:yuWVZk7RPJ ?person . \n"
                + "?projectUri a core:Project ;");
    }

    @Override
    protected final Log getLog() {
        return log;
    }

    @Override
    protected final String getTemplate() {
        return "addProjectToPerson.ftl";
    }

    @Override
    protected final Map<String, String> getInheritedRolesLabelAndUri(String subjectUri) {
        String query = SPARQL_PREFIX
                + "SELECT DISTINCT ?role ?roleLabel WHERE { \n"
                + "<" + subjectUri + "> ?roleProp ?role . \n"
                + "?roleProp rdfs:subPropertyOf core:hasRole . \n"
                + "?role rdfs:label ?roleLabel}";
        return getResults(query, "role", "roleLabel");
    }

    @Override
    protected final String getSubjectName() {
        return "person";
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
