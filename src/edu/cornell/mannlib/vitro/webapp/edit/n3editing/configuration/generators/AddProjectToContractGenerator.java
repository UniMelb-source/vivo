/* $This file is distributed under the terms of the license in /doc/license.txt$ */
package edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.generators;

import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.EditConfigurationVTwo;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AddProjectToContractGenerator extends AddProjectToThingGenerator {

    private static final Log log = LogFactory.getLog(AddProjectToContractGenerator.class);

    @Override
    protected List<String> getN3Required() {
        return list(N3_PREFIX
                + "?contract ands:giFfjspAQ5 ?projectUri . \n"
                + "?projectUri ands:WAuYx4Bgki ?contract . \n"
                + "?projectUri a core:Project ;");
    }

    @Override
    protected Log getLog() {
        return log;
    }

    @Override
    protected String getTemplate() {
        return "addProjectToContract.ftl";
    }

    protected Map<String, String> getInheritedRolesLabelAndUri(String subjectUri) {
        String query = SPARQL_PREFIX
                + "SELECT DISTINCT ?role ?roleLabel WHERE { \n"
                + "<" + subjectUri + "> core:contributingRole ?role . \n"
                + "?role ?roleProp ?person . \n"
                + "?roleProp rdfs:subPropertyOf core:roleOf . \n"
                + "?role rdfs:label ?roleLabel}";
        return getResults(query, "role", "roleLabel");
    }

    @Override
    protected String getSubjectName() {
        return "contract";
    }

    @Override
    protected String getPredicateName() {
        return "predicate";
    }

    @Override
    protected String getObjectName() {
        return "projectUri";
    }
}
