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

<#-- Individual profile page template for ands:ResearchData individuals -->
<#include "individual-setup.ftl">
<#import "individual-qrCodeGenerator.ftl" as qr>
<#import "lib-vivo-properties.ftl" as vp>
<#import "lib-status-display.ftl" as lsd>
<!-- <div id="wrapper-content" role="main">  -->      
            <div class="col-6 first">
<section id="individual-intro" class="vcard person" role="region">
<!--section id="share-contact" role="region">
        <nav role="navigation">

            <ul id ="individual-tools-people" role="list">
                <li role="listitem"><img id="uriIcon" title="${individual.uri}" class="middle" src="${urls.images}/individual/uriIcon.gif" alt="uri icon"/></li>

                <@qr.renderCode />
            </ul>
        </nav>

    </section-->
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
                    <span class="fn">
                        ${nameStatement}
							</span>
                    <@p.mostSpecificTypes individual />
                </h1>
            </#if>
        </header>


        <#-- Overview -->
        <#include "individual-overview.ftl">

        <#-- Research Areas -->
        <#assign researchAreas = propertyGroups.pullProperty("${core}hasResearchArea")!>
        <#if researchAreas?has_content> <#-- true when the property is in the list, even if not populated (when editing) -->
            <@p.objectPropertyListing researchAreas editable />
        </#if>

        <#-- Location Identifier -->
        <#assign locationIdentifier = propertyGroups.pullProperty("${unimelbrdr}digitalLocation")!>
        <@dp.dataProperty locationIdentifier editable />

        <#-- Location Details -->
        <#assign locationDetails = propertyGroups.pullProperty("${unimelbrdr}nonDigitalLocation")!>
        <@dp.dataProperty locationDetails editable />

        <#-- Retention Period -->
        <#assign retentionPeriod = propertyGroups.pullProperty("${unimelbrdr}retentionPeriod")!>
        <@dp.dataProperty retentionPeriod editable />

        <#-- Access -->
        <#assign access = propertyGroups.pullProperty("${unimelbrdr}accessibility")!>
        <@dp.dataProperty access editable />

        <#-- Data Management Plan -->
        <#assign dataManagementPlan = propertyGroups.pullProperty("${unimelbrdr}dataManagementPlanId")!>
        <@dp.dataProperty dataManagementPlan editable />

        <#-- Rights -->
        <#assign rights = propertyGroups.pullProperty("${ands}rights")!>
        <@dp.dataProperty rights editable />

        <#-- Is Managed By -->
        <#assign isManagedBy = propertyGroups.pullProperty("${ands}isManagedBy")!>
        <#if isManagedBy?has_content>
            <@p.objectPropertyListing isManagedBy editable />
        </#if>
    </section>
 </div>
</section>

<#-- assigning these for use here to remove from property groups below -->
<#assign email = propertyGroups.pullProperty("${core}email")!>
<#assign webpage = propertyGroups.pullProperty("${core}webpage")!>
<!-- </div>  #wrapper-content -->
<#--
<#assign nameForOtherGroup = "other">--> <#-- used by both individual-propertyGroupMenu.ftl and individual-properties.ftl -->

<#-- Property group menu -->
<#include "individual-propertyGroupMenu-minimal-groups.ftl">

<#-- Ontology properties -->
<#include "individual-properties-minimal-groups.ftl">

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
</div>
</div>
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
              
