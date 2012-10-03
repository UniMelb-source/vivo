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

<#-- Individual profile page template for foaf:Person individuals -->

<#include "individual-setup.ftl">
<#import "individual-qrCodeGenerator.ftl" as qr>
<#import "lib-vivo-properties.ftl" as vp>
<!-- <div id="wrapper-content" role="main">  -->      
            <div class="col-6 first">
<section id="individual-intro" class="vcard person" role="region">
<section id="share-contact" role="region">
        <#-- Image -->
        <#assign individualImage>
            <@p.image individual=individual
                      propertyGroups=propertyGroups
                      namespaces=namespaces
                      editable=editable
                      showPlaceholder="always"
                      placeholder="${urls.images}/placeholders/person.thumbnail.jpg" />
        </#assign>

        <#if ( individualImage?contains('<img class="individual-photo"') )>
            <#assign infoClass = 'class="withThumb"'/>
        </#if>

        <div id="photo-wrapper">${individualImage}</div>

        <nav role="navigation">

            <ul id ="individual-tools-people" role="list">
                <li role="listitem"><img id="uriIcon" title="${individual.uri}" class="middle" src="${urls.images}/individual/uriIcon.gif" alt="uri icon"/></li>

                <@qr.renderCode />
            </ul>
        </nav>

    </section>
<div style="float: left; display: inline; width: 500px ">
    <section id="individual-info-unimelb" ${infoClass!} role="region">
        <#include "individual-adminPanel.ftl">

        <header>
            <#if relatedSubject??>
                <h2>${relatedSubject.relatingPredicateDomainPublic} for ${relatedSubject.name}</h2>
                <p><a href="${relatedSubject.url}" title="return to">&larr; return to ${relatedSubject.name}</a></p>
            <#else>
                <h1 class="vcard foaf-person">
                    <#assign nameStatement = individual.nameStatement.value! !>
                    <#assign nameSplit = individual.nameStatement.value?split(" ") !>
                    <#assign firstName = propertyGroups.pullProperty("http://xmlns.com/foaf/0.1/firstName")!>
                    <#assign lastName = propertyGroups.pullProperty("http://xmlns.com/foaf/0.1/lastName")!>

                    <#-- Label -->
                    <span class="fn"><#-- ${nameSplit[1]} <#if firstName?has_content>${firstName.statements[0].value}</#if>
                    <#if lastName?has_content>${lastName.statements[0].value}</#if>-->${nameStatement} </span>

                    <#--  Display preferredTitle if it exists; otherwise mostSpecificTypes -->
                    <#assign title = propertyGroups.pullProperty("${core}preferredTitle")!>
                    <#if title?has_content> <#-- true when the property is in the list, even if not populated (when editing) -->
                        <@p.addLinkWithLabel title editable />
                        <#list title.statements as statement>
                            <span class="display-title">${statement.value}</span>
                            <@p.editingLinks "${title.name}" statement editable />
                        </#list>
                    </#if>
                    <#-- If preferredTitle is unpopulated, display mostSpecificTypes -->
                    <#if ! (title.statements)?has_content>
                       <#-- <@p.mostSpecificTypes individual /> -->
                    </#if>
                </h1>
            </#if>

            <#-- Positions -->
            <#assign positions = propertyGroups.pullProperty("${core}personInPosition")!>
            <#if positions?has_content> <#-- true when the property is in the list, even if not populated (when editing) -->
                <@p.objectPropertyListing positions editable />
            </#if>
        </header>

        <#-- Overview -->
        <#include "individual-overview.ftl">

        <#-- Research Areas -->
        <#assign researchAreas = propertyGroups.pullProperty("${core}hasResearchArea")!>
        <#if researchAreas?has_content> <#-- true when the property is in the list, even if not populated (when editing) -->
            <@p.objectPropertyListing researchAreas editable />
        </#if>
    </section>
 </div>
</section>

<#-- assigning these for use here to remove from property groups below -->
<#assign email = propertyGroups.pullProperty("${core}email")!>
<#assign phone = propertyGroups.pullProperty("${core}phoneNumber")!>
<#assign fax = propertyGroups.pullProperty("${core}faxNumber")!>
<#assign location = propertyGroups.pullProperty("http://www.findanexpert.unimelb.edu.au/ontology/hasWorkLocation") !>
<#assign mediaOnly  = propertyGroups.pullProperty("http://www.findanexpert.unimelb.edu.au/ontology/hasMediaOnlyContact") !>
<#assign graduateStudy  = propertyGroups.pullProperty("http://www.findanexpert.unimelb.edu.au/ontology/gradResearchAddress") !>
<#assign webpage = propertyGroups.pullProperty("${core}webpage")!>
<#assign overviewText2  = propertyGroups.pullProperty("http://www.findanexpert.unimelb.edu.au/ontology/overviewText2") !>
<#assign overviewText3  = propertyGroups.pullProperty("http://www.findanexpert.unimelb.edu.au/ontology/overviewText3") !>
<#assign overviewText4  = propertyGroups.pullProperty("http://www.findanexpert.unimelb.edu.au/ontology/overviewText4") !>
<#assign supervisorText2  = propertyGroups.pullProperty("http://www.findanexpert.unimelb.edu.au/ontology/supervisorText2") !>
<#assign supervisorText3  = propertyGroups.pullProperty("http://www.findanexpert.unimelb.edu.au/ontology/supervisorText3") !>
<#assign supervisorText4  = propertyGroups.pullProperty("http://www.findanexpert.unimelb.edu.au/ontology/supervisorText4") !>
<!-- </div>  #wrapper-content -->
<#--
<#assign nameForOtherGroup = "other">--> <#-- used by both individual-propertyGroupMenu.ftl and individual-properties.ftl -->

<#-- Property group menu -->
<#include "individual-propertyGroupMenu.ftl">

<#-- Ontology properties -->
<#include "individual-properties.ftl">

<#assign rdfUrl = individual.rdfUrl>

<#if rdfUrl??>
    <script>
        var individualRdfUrl = '${rdfUrl}';
    </script>
</#if>




<script type="text/javascript">


if(document.getElementById("overviewText1"))
{
  document.getElementById("overviewText1").innerHTML = "Overview";
}

</script> 

</div>
            <div class="col-2 nav">
                   <ul>
                              <#include "individual-contactInfo.ftl">
                              <#include "individual-visualizationFoafPerson.ftl">
                  </ul>
            </div>
<!--          </div>
</div> -->
${stylesheets.add('<link rel="stylesheet" href="${urls.base}/css/individual/individual.css" />',
                  '<link rel="stylesheet" href="${urls.base}/css/individual/individual-vivo.css" />',
                  '<link rel="stylesheet" href="${urls.base}/js/jquery-ui/css/smoothness/jquery-ui-1.8.9.custom.css" />')}

${headScripts.add('<script type="text/javascript" src="${urls.base}/js/tiny_mce/tiny_mce.js"></script>',
                  '<script type="text/javascript" src="${urls.base}/js/jquery_plugins/qtip/jquery.qtip-1.0.0-rc3.min.js"></script>',
                  '<script type="text/javascript" src="${urls.base}/js/jquery_plugins/jquery.truncator.js"></script>',
                  '<script type="text/javascript" src="${urls.theme}/js/tabs.js"></script>')}

${scripts.add('<script type="text/javascript" src="${urls.base}/js/individual/individualUtils.js"></script>',
              '<script type="text/javascript" src="${urls.base}/js/individual/individualUriRdf.js"></script>',
              '<script type="text/javascript" src="${urls.base}/js/jquery-ui/js/jquery-ui-1.8.9.custom.min.js"></script>',
              '<script type="text/javascript" src="${urls.base}/js/imageUpload/imageUploadUtils.js"></script>')}
              
