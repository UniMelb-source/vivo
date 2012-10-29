package edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.generators;

import java.util.HashMap;

import edu.cornell.mannlib.vitro.webapp.controller.VitroRequest;

public class AddProjectRoleToPersonGenerator extends AddRoleToPersonTwoStageGenerator {

    private static String template = "addProjectRoleToPerson.ftl";

    @Override
    String getTemplate() {
        return template;
    }

    @Override
    String getRoleType() {
        return "https://rdr.unimelb.edu.au/config/ProjectRole";
    }

    @Override
    RoleActivityOptionTypes getRoleActivityTypeOptionsType() {
        return RoleActivityOptionTypes.HARDCODED_LITERALS;
    }

    @Override
    String getRoleActivityTypeObjectClassUri(VitroRequest vreq) {
        return null;
    }

    @Override
    protected HashMap<String, String> getRoleActivityTypeLiteralOptions() {
        HashMap<String, String> literalOptions = new HashMap<String, String>();
        literalOptions.put("http://vivoweb.org/ontology/core#Project", "Project");
        return literalOptions;
    }

    @Override
    boolean isShowRoleLabelField() {
        return true;
    }
}
