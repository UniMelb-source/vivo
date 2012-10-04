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



<#include "individual-setup.ftl">
<#import "lib-vivo-properties.ftl" as vp>
<#import "lib-datetime.ftl" as dt>
<#import "lib-sequence.ftl" as s>
<#assign webpage = propertyGroups.pullProperty("${core}webpage")!>
<#assign individualProductExtension>
    <#-- Include for any class specific template additions -->
    <@vp.webpages propertyGroups editable />
    <!--PREINDIVIDUAL OVERVIEW.FTL-->
    <#include "individual-overview.ftl">
        </section> <!-- #individual-info -->
    </section> <!-- #individual-intro -->
    <!--postindiviudal overiew tfl-->
</#assign>


 <div class="col-6 first">

<#include "individual-vitro.ftl">

 </div>

<#assign journal  = propertyGroups.pullProperty("${core}hasPublicationVenue") !>
<#assign pageStart  = propertyGroups.pullProperty("http://purl.org/ontology/bibo/pageStart") !>
<#assign pageEnd  = propertyGroups.pullProperty("http://purl.org/ontology/bibo/pageEnd") !>
<#assign year  = propertyGroups.pullProperty("http://vivoweb.org/ontology/core#dateTimeValue") !>
<#assign volume  = propertyGroups.pullProperty("http://purl.org/ontology/bibo/volume") !>
<#assign issue  = propertyGroups.pullProperty("http://purl.org/ontology/bibo/issue") !>
<#assign doi  = propertyGroups.pullProperty("http://purl.org/ontology/bibo/doi") !>

<#assign yearValue><#-- No core:dateTime data property assigned. Display a link to the core:DateTimeValue object -->
<#if ! year.statements[0].dateTime??>
<#else>${dt.formatXsdDateTimeLong(year.statements[0].dateTime, year.statements[0].precision!)}</#if></#assign>

<#assign sfxtitle>&rtf.atitle=${individual.nameStatement.value?url}</#assign>
<#assign sfxjournal><#if journal.statements?has_content><#if journal.statements[0].label??>&rft.jtitle=${journal.statements[0].label?url!}</#if></#if></#assign>
<#assign sfxpageStart><#if pageStart.statements?has_content>&rft.spage=${pageStart.statements[0].value!}</#if></#assign>
<#assign sfxpageEnd><#if pageStart.statements?has_content>&rft.epage=${pageEnd.statements[0].value!}</#if></#assign>
<#assign sfxvolume><#if volume.statements?has_content>&rft.volume=${volume.statements[0].value!}</#if></#assign>
<#assign sfxissue><#if issue.statements?has_content>&rft.issue=${issue.statements[0].value!}</#if></#assign>
<#assign sfxyear><#if yearValue?has_content>&rft.date=${yearValue}</#if></#assign>

 <div class="col-2 nav">
<ul>
<li>External Links
<ul>
 <#if webpage?has_content> <#-- true when the property is in the list, even if not populated (when editing) -->

<li> 
  <#--          <#assign wlabel = "Web Pages">
            <@p.addLinkWithLabel webpage editable wlabel /> -->
            <#if webpage.statements?has_content> <#-- if there are any statements -->
                <ul>
                        <@p.objectPropertyList webpage editable />
                </ul>
            </#if>
</li>
</#if>
            <#if doi.statements?has_content>
              <li>Digital Object Identifier
               <ul>
                 <li>
                    <a href="http://dx.doi.org/${doi.statements[0].value!}" title="Digital Object Identifier (DOI)" target="doi">${doi.statements[0].value!}</a>
                 </li>
               </ul>
              </li>
           </#if>
<li id='akg'>&nbsp;</li>
<script  type="text/javascript">
function callBack(data) {
   var inHTML;
   var facetURL;
   if(data.response.facets[0].categories[0].values.length)
  {
   if(data.response.facets[0].categories[0].values.length > 1){
   inHTML='<a href="http://www.akg.edu.au/s/search.html?query=t%3A%22${individual.nameStatement.value?url}%22&collection=go8-experts&search-type=expertise">Search for authors on the Australia\'s Knowledge Gateway</a>';
        inHTML = inHTML + '<ul>';
   	for (i=0; i<data.response.facets[0].categories[0].values.length;  i++)
  	{
                facetURL = 'http://www.akg.edu.au/s/search.html?query=t%3A%22${individual.nameStatement.value?url}%22&collection=go8-experts&search-type=expertise&' ;
                facetURL = facetURL + data.response.facets[0].categories[0].values[i].queryStringParam ;
                facetURL = '<a href="' + facetURL + '">'+ data.response.facets[0].categories[0].values[i].data + ' (' + data.response.facets[0].categories[0].values[i].count  + ')' +'</a>';
                inHTML = inHTML +  '<li>' + facetURL + '</li>' ;
 	}	
    
        inHTML = inHTML + '</ul>';
         document.getElementById("akg").innerHTML = inHTML;
   }
  }
 }
</script>
<script  type="text/javascript">
$.ajax({
  url:'http://www.akg.edu.au/s/search.json?query=t%3A%22${individual.nameStatement.value?url}%22&collection=go8-experts&search-type=expertise&callback=callBack' ,
  dataType: 'jsonp',
  success: callBack 
   
});

</script>
<li>Google Scholar
	<ul>
            <li>
                <a href="http://scholar.google.com.au/scholar?q=%22${individual.nameStatement.value?url}%22&btnG=&hl=en&as_sdt=0%2C5" target="googlescholar">Search by title</a>
            </li>
        </ul>
</li>
<li>Microsoft Academic Search
        <ul>
            <li>
                <a href="http://academic.research.microsoft.com/Search?query=%22${individual.nameStatement.value?url}%22" target="MAS">Search by title</a>
            </li>
<#if doi.statements?has_content>
              <li>
                    <a href="http://academic.research.microsoft.com/Search?query=doi%3a(${doi.statements[0].value!})" target="MAS">Search by doi</a>
              </li>
           </#if>
            
        </ul>
</li>
<li>Mendelay
        <ul>
            <li>
	<a href="http://www.mendeley.com/research-papers/search/?query=%22${individual.nameStatement.value?url}%22" target="mendelay">Search by title </a>
            </li>
        </ul>
</li>
<#if journal.statements?has_content> 
<li>University login required         
                <ul><li><a target="sfx" title="Source it @ Melbourne" href="http://sfx.unimelb.edu.au/sfxlcl3?ctx_enc=info%3Aofi%2Fenc%3AUTF-8&ctx_id=10_1&ctx_tim=2012-05-30T23%3A13%3A26EST&ctx_ver=Z39.88-2004&res_id=http%3A%2F%2Fsfx.unimelb.edu.au%2Fsfxlcl3&rft.genre=article&rft_val_fmt=info%3Aofi%2Ffmt%3Akev%3Amtx%3Ajournal&svc_val_fmt=info%3Aofi%2Ffmt%3Akev%3Amtx%3Asch_svc&url_ctx_fmt=info%3Aofi%3Afmt%3Akev%3Amtx%3Actx&url_ver=Z39.88-2004${sfxtitle}${sfxjournal!}${sfxpageStart!}${sfxpageEnd!}${sfxvolume!}${sfxissue!}${sfxyear!}"><img src="${urls.theme}/images/sourceit_logo.gif"/> Locate resource</a>
                </li></ul>

</li>
</#if>
</ul>
</li>
</ul>
 </div>



${stylesheets.add('<link rel="stylesheet" href="${urls.base}/css/individual/individual-vivo.css" />')}

${headScripts.add('<script type="text/javascript" src="${urls.base}/js/jquery_plugins/jquery.truncator.js"></script>')}
${scripts.add('<script type="text/javascript" src="${urls.base}/js/individual/individualUtils.js"></script>')}
