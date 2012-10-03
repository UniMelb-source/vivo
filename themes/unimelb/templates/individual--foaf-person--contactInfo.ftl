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
<li>Contact Details
    <ul>
        <#if email?has_content>
            <@p.addLinkWithLabel email editable label/>
            <#if email.statements?has_content> <#-- if there are any statements -->
                <#list email.statements as statement>
                    <li role="listitem">
                        <img class ="icon-email middle" src="${urls.images}/individual/emailIcon.gif" alt="email icon" />
                        <a class="email" style="display: inline;" href="mailto:${statement.value}" title="email">${statement.value}</a>
                        <@p.editingLinks "${email.localName}" statement editable />
                    </li>
                </#list>
            </#if>
        </#if>

        <#if phone?has_content>
            <@p.addLinkWithLabel phone editable />
            <#if phone.statements?has_content>
                <#list phone.statements as statement>
                    <li role="listitem">                           
                        <img class ="icon-phone  middle" src="${urls.images}/individual/phoneIcon.gif" alt="phone icon" />${statement.value}
                        <@p.editingLinks "${phone.localName}" statement editable />
                    </li>
                </#list>
            </#if>
        </#if>
        
        <#if fax?has_content>
            <#if fax.statements?has_content>
                <#list fax.statements as statement>
                    <li role="listitem">
                        <img class ="icon-phone  middle" src="${urls.images}/individual/phoneIcon.gif" alt="fax icon" />fax: ${statement.value}
                    </li>
                </#list>
            </#if>
        </#if>

        <#if mediaOnly?has_content>
            <#if mediaOnly.statements?has_content>
                <#list mediaOnly.statements as statement>
                    <li role="listitem">
                        <img class ="icon-phone  middle" src="${urls.images}/individual/phoneIcon.gif" alt="media only icon" />
                        After hours: <a href="https://newsroom.melbourne.edu/user?uri=${individual.uri?url}" target="_blank">Media Only (login required)</a> 
                    </li>
                </#list>
            </#if>
        </#if>

        <#if webpage?has_content>
            <nav role="navigation">
                <#if webpage.statements?has_content> <#-- if there are any statements -->
                    <ul>
                        <@p.objectPropertyList webpage editable />
                    </ul>
                </#if>
            </nav>
        </#if>

        <#if location.statements?has_content>
            <li>
                Location
                <ul>
                    <@p.objectPropertyList location editable />
                </ul>
            </li>
        </#if>
    </ul>
</li>

<#macro emailLinks property>
    <#assign email = propertyGroups.pullProperty(property)!>    
    <#if property == "${core}primaryEmail">
        <#local listId = "primary-email">
        <#local label = "Primary Email">
    <#else>
        <#local listId = "additional-emails">
        <#local label = "Additional Emails">
    </#if>     
    <#if email?has_content>
        <@p.addLinkWithLabel email editable label/>
        <#if email.statements?has_content>
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
