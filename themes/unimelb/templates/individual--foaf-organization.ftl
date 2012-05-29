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

<#-- Individual profile page template for foaf:Organization individuals (extends individual.ftl in vivo)-->

<#-- Do not show the link for temporal visualization unless it's enabled -->
<#if temporalVisualizationEnabled>
    <#assign visualizationGallery>
        <section id="visualization" role="region">
           <#--  <#include "individual-visualizationTemporalGraph.ftl"> -->
            <#include "individual-visualizationMapOfScience.ftl">
        </section> <!-- #visualization -->
    </#assign>
</#if>

<#include "individual-setup.ftl">
<#import "lib-vivo-properties.ftl" as vp>

<#assign individualProductExtension>
    <#-- Include for any class specific template additions -->
    <@vp.webpages propertyGroups editable />
    <!--PREINDIVIDUAL OVERVIEW.FTL-->
    <#include "individual-overview.ftl">
        </section> <!-- #individual-info -->
    </section> <!-- #individual-intro -->
    <!--postindiviudal overiew tfl-->
</#assign>

<#assign graduateStudy  = propertyGroups.pullProperty("http://www.findanexpert.unimelb.edu.au/ontology/gradResearchAddress") !>


 <div class="col-6 first">

<#include "individual-vitro.ftl">

 </div>
 <div class="col-2 nav">
<ul>
<li>Graduate Study
<ul>
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
    </ul>
    </li>
  </ul>
</li>
</#if>
</ul>
</li>
<#if temporalVisualizationEnabled>
<li>
Visualisation Gallery
<ul>
<li>Research Area Visualizations
<ul>
<li>
  <a href="${individual.mapOfScienceUrl()}" title="map of science"><img src="${urls.images}/visualization/mapofscience/scimap_icon.png" alt="Map Of Science icon" width="30px" height="30px" /></a>
                  <a href="${individual.mapOfScienceUrl()}" title="map of science" style="vertical-align: top">Map Of Science</a>
</li>
</ul>
</li>
</ul>
</li>
</ul>
</#if>
 </div>



${stylesheets.add('<link rel="stylesheet" href="${urls.base}/css/individual/individual-vivo.css" />')}

${headScripts.add('<script type="text/javascript" src="${urls.base}/js/jquery_plugins/jquery.truncator.js"></script>')}
${scripts.add('<script type="text/javascript" src="${urls.base}/js/individual/individualUtils.js"></script>')}
