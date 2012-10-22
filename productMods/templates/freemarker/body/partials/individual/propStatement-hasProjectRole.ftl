<#-- $This file is distributed under the terms of the license in /doc/license.txt$ -->

<#-- Custom object property statement view for http://vivoweb.org/ontology/core#hasProjectRole.
    
     This template must be self-contained and not rely on other variables set for the individual page, because it
     is also used to generate the property statement during a deletion.  
 -->

<#import "lib-sequence.ftl" as s>
<#import "lib-datetime.ftl" as dt>

<@showRole statement />

<#-- Use a macro to keep variable assignments local; otherwise the values carry over to the
     next statement -->
<#macro showRole statement>
    <#local linkedIndividual>
        <#if statement.project??>
            <a href="${profileUrl(statement.uri("hasProjectRole"))}" title="project name">${statement.projectLabel!statement.projectName}</a>
        <#else>
            <#-- This shouldn't happen, but we must provide for it -->
            <a href="${profileUrl(statement.uri("role"))}" title="missing presentation">missing presentation</a>
        </#if>
    </#local>
    
    <#local dateTime>
        <@dt.yearSpan statement.dateTime! /> 
    </#local>

</#macro>
