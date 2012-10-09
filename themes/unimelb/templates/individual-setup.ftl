<#-- $This file is distributed under the terms of the license in /doc/license.txt$ -->

<#-- Setup needed by all individual templates -->

<#import "lib-list.ftl" as l>
<#import "lib-properties.ftl" as p>
<#import "lib-data-properties.ftl" as dp>

<#assign editable = individual.editable>

<#assign propertyGroups = individual.propertyList>

<#assign ands = "http://purl.org/ands/ontologies/vivo/">
<#assign core = "http://vivoweb.org/ontology/core#">
<#assign fae = "http://www.findanexpert.unimelb.edu.au/ontology/">
<#assign foaf = "http://xmlns.com/foaf/0.1/">
<#assign unimelbrdr = "https://rdr.unimelb.edu.au/config/">
