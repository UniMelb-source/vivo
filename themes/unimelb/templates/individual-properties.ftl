<#-- $This file is distributed under the terms of the license in /doc/license.txt$ -->

<#-- Template for property listing on individual profile page -->

<#import "lib-properties.ftl" as p>

<#list propertyGroups.all as group>
    <#assign groupName = group.getName(nameForOtherGroup)>
    <#-- Display the group heading --> 
    <#if groupName?has_content>
        <#--the function replaces spaces in the name with underscores, also called for the property group menu-->
        <#assign groupNameHtmlId = p.createPropertyGroupHtmlId(groupName) >
        <!--h2 id="${groupNameHtmlId}">${groupName?capitalize}</h2-->
    <#else>
        <#assign groupNameHtmlId = "properties" >
        <!--h2 id="properties">Properties</h2-->
    </#if>
    
    <section id="${groupNameHtmlId}" class="property-group" role="region">
        <nav class="scroll-up" role="navigation">
            <a href="#branding" title="scroll up">
                <img src="${urls.images}/individual/scroll-up.gif" alt="scroll to property group menus" />
            </a>
        </nav>
        
        <#-- List the properties in the group -->
        <#list group.properties as property>
            <article class="property" role="article">
                <#-- Property display name -->
                <h3 id="${property.localName}">${property.name?cap_first} <@p.addLink property editable /> <@p.verboseDisplay property /></h3>               
                <#-- List the statements for each property -->
                <ul class="property-list" role="list">
                    <#-- data property -->
                    <#if property.type == "data">
                        <@p.dataPropertyList property editable />
                    <#-- object property -->
                    <#else>
                        <@p.objectProperty property editable />
                    </#if>
                </ul>
            </article> <!-- end property -->
        </#list>
    </section> <!-- end property-group -->
</#list>
