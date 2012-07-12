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
        
            <section id="intro" role="region">
                <h2>Welcome to the Central Research Data Registry</h2>

                <img class="polaroid" src="${urls.theme}/images/find-an-expert.jpg" alt="Blue brain" style="float: right;">

                <p>The Central Research Data Registry is a register and finding tool of research data created by staff and graduate researchers at the University of Melbourne.</p>
                <p>Records of research data in the Registry are tied to researchers, grants and contracts, and publications.</p>
                <ul>
                    <li>To <strong>create</strong> a record for a research data set use the Search box to find the related researcher, grant/ contract, or publication.</li>
                    <li> To <strong>find</strong> an existing record for a research data sets use the Search box and search by the name of the data set, or by researcher, grant/ contract, or publication.</li>
                </ul>
                
                <section id="search-home" role="region">
                    <h2>Search the Central Research Data Registry</h2>
                    
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
            
            <!--@allClassGroups vClassGroups /-->
        
        <#include "footer.ftl">
    </body>
</html>
