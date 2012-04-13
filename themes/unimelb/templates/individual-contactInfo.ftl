<#--
Copyright (c) 2011, Cornell University
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

    * Redistributions of source code must retain the above copyright notice,
      this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright notice,
      this list of conditions and the following disclaimer in the documentation
      and/or other materials provided with the distribution.
    * Neither the name of Cornell University nor the names of its contributors
      may be used to endorse or promote products derived from this software
      without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
-->

<#-- Contact info on individual profile page -->

<#-- Primary Email     
<@emailLinks "${core}primaryEmail" /> 
<#assign email = propertyGroups.pullProperty("${core}email")!> assigned in individual--foaf..-->

    <#if email?has_content> <#-- true when the property is in the list, even if not populated (when editing) -->
        <@p.addLinkWithLabel email editable label/>
        <#if email.statements?has_content> <#-- if there are any statements -->
                <#list email.statements as statement>
                    <li role="listitem">
                        <img class ="icon-email middle" src="${urls.images}/individual/emailIcon.gif" alt="email icon" />
                        <a class="email" href="mailto:${statement.value}" title="email">${statement.value}</a>
                        <@p.editingLinks "${email.localName}" statement editable />
                    </li>
                </#list>
        </#if>
    </#if>
  
<#-- Phone --> 
<#-- Alread assigned in individual--foaf...
<#assign phone = propertyGroups.pullProperty("${core}phoneNumber")!>
<#assign fax = propertyGroups.pullProperty("${core}faxNumber")!>
<#assign location = propertyGroups.pullProperty("http://www.findanexpert.unimelb.edu.au/ontology/hasWorkLocation") !>
<#assign mediaOnly  = propertyGroups.pullProperty("http://www.findanexpert.unimelb.edu.au/ontology/hasMediaOnlyContact") !>
<#assign graduateStudy  = propertyGroups.pullProperty("http://www.findanexpert.unimelb.edu.au/ontology/gradResearchAddress") !>
-->

<#if phone?has_content> <#-- true when the property is in the list, even if not populated (when editing) -->
    <@p.addLinkWithLabel phone editable />
    <#if phone.statements?has_content> <#-- if there are any statements -->
            <#list phone.statements as statement>
                <li role="listitem">                           
                   <img class ="icon-phone  middle" src="${urls.images}/individual/phoneIcon.gif" alt="phone icon" />${statement.value}
                    <@p.editingLinks "${phone.localName}" statement editable />
                </li>
            </#list>
    </#if>
</#if>
<#-- Fax -->
<#if fax?has_content> <#-- true when the property is in the list, even if not populated (when editing) -->
    <#if fax.statements?has_content> <#-- if there are any statements -->
            <#list fax.statements as statement>
                <li role="listitem">
                   <img class ="icon-phone  middle" src="${urls.images}/individual/phoneIcon.gif" alt="fax icon" />fax: ${statement.value}
                </li>
            </#list>
    </#if>
</#if>

<#-- mediaOnly -->
<#if mediaOnly?has_content> <#-- true when the property is in the list, even if not populated (when editing) -->
    <#if mediaOnly.statements?has_content> <#-- if there are any statements -->
            <#list mediaOnly.statements as statement>
                <li role="listitem">
                   <img class ="icon-phone  middle" src="${urls.images}/individual/phoneIcon.gif" alt="media only icon" />
                     <a href="https://newsroom.melbourne.edu/user?uri=${individual.uri?url}">Media Only (login required)</a> 
                </li>
            </#list>
    </#if>
</#if>

<#if location.statements?has_content>
<li>
<ul>
<@p.objectPropertyList location editable />
</ul>
</li>
</#if>

<#if graduateStudy.statements?has_content>
<li>
  <ul>
   <li>Interested in Graduate Study at Melbourne? Next steps:

     <ul>
    <#list graduateStudy.statements as statement>
        <li role="listitem">
            ${statement.value}
         </li>
      </#list>
    </li>
    </ul>
  </ul>
</li>
</#if>


<#macro emailLinks property>
    <#assign email = propertyGroups.pullProperty(property)!>    
    <#if property == "${core}primaryEmail">
        <#local listId = "primary-email">
        <#local label = "Primary Email">
    <#else>
        <#local listId = "additional-emails">
        <#local label = "Additional Emails">
    </#if>     
    <#if email?has_content> <#-- true when the property is in the list, even if not populated (when editing) -->
        <@p.addLinkWithLabel email editable label/>
        <#if email.statements?has_content> <#-- if there are any statements -->
                <#list email.statements as statement>
                    <li role="listitem">
                        <img class ="icon-email middle" src="${urls.images}/individual/emailIcon.gif" alt="email icon" />
                        <a class="email" href="mailto:${statement.value}" title="email">${statement.value}</a>
                        <@p.editingLinks "${email.localName}" statement editable />
                    </li>
                </#list>
        </#if>
    </#if>
</#macro>
