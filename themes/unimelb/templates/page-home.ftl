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

<#include "browse-classgroups.ftl">

<!DOCTYPE html>
<html lang="en">
    <head>
        <#include "head.ftl">
    </head>
    
    <body class="no-logo science">
        <#include "identity.ftl">
        
        <#include "menu.ftl">
        <div class="col-6">
            <section id="intro" role="region">
                <h2>Welcome to Find An Expert</h2>

                <p>Find An Expert is a research-focused discovery tool that enables collaboration among scientists across all disciplines.</p>
                <p>Browse or search information on people, departments, courses, grants, and publications.</p>
                
                <section id="search-home" role="region">
                    <h2>Search Find An Expert</h2>
                    
                    <fieldset>
                        <legend>Search form</legend>
                        <form id="search-home-vivo" action="${urls.search}" method="post" name="search-home" role="search">
                            <div id="search-home-field">
                                <input type="text" name="querytext" class="search-home-vivo" value="${querytext!}" />
                                <input type="submit" value="Search" class="search">
                            </div>
                        </form>
                    </fieldset>
                </section> <!-- #search-home -->
            </section> <!-- #intro --> 
         </div>
 <div class="col-2">
           <img class="polaroid" src="${urls.theme}/images/find-an-expert.jpg" alt="journal front convers" style="float: right;">
         </div>

         <div class="col-6 first">
            <div class="col-2 first page-preview">
              <h2>Find Supervisors</h2>
              <img class="polaroid col-2 first" src="http://www.unimelb.edu.au/research/images/research-student-actual-sml.jpg" alt="Research student surrounded by glassware">
              <p>As members of one of Australia's largest research institutions our PhD and research masters candidates work on projects spanning emerging fields as well as the full range of traditional academic disciplines.</p>
			  <p><a href="http://futurestudents.unimelb.edu.au/info/research">How to apply</a></p>
              <p><a href="http://msgr.unimelb.edu.au/">Information for current and prospective graduate researchers</a></p>
            </div>
            <div class="col-2 page-preview">
              <h2>Researchers for Media Comment</h2>
              <img class="polaroid col-2 first" src="http://www.unimelb.edu.au/research/images/sara-bice.jpg" alt="Sara Bice">
              <p>Research is the lifeblood of the University, and we take special interest in supporting researchers at all stages of their development. </p>
              <a href="http://www.research.unimelb.edu.au/">Information for researchers</a>
            </div>
          </div>
          <#-- <@allClassGroups vClassGroups /> -->
        
        <#include "footer.ftl"> 
    </body>
</html>
