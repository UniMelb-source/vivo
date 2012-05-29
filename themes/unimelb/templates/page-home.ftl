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
    
    <body class="no-logo fae">
        <#include "identity-home.ftl">
        <hr style="clear:both;">
        <#include "menu.ftl">
        <div class="main" id="main-content" role="main">

     	    <div class="col-8"><h2>Welcome to Find An Expert</h2>
             <p>Find An Expert is a research-focused discovery tool that enables collaboration among scientists across all disciplines.</p>
        

	     <section role="region" id="search-home" class="col-4 first">
			        <h1>Search Find an Expert</h1>
	     <form id="search-form" action="http://search-au.funnelback.com/s/search.html?collection=unimelb-researchers" name="search" role="search" accept-charset="UTF-8" method="GET">
              <div id="search-field">
                 <input type="text" name="query" class="search-vivo" value="" autocapitalize="off">
                 <input type="hidden" name="collection" value="unimelb-researchers">
        	<input type="submit" value="Search" class="search"><br/>
                <input type="checkbox" name="facetScope" id="facetScope" value="f.Supervisor%20Availability%7C1=Available" >Show only available supervisors

              <p class="hint">Search for experts, by name or research topic<br /></p>
    	      </div>
            </form>
            </section> <!-- #search-home -->
			
	    <div class="col-3">
		<div id="video">
		Video poster frame (clicking this launches the movie)
		</div>
	    </div>
			

     	</div>

	<hr>
	<div class="col-8">
		<h2 class="pullin">Support</h2>
		<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco.</p>
		<div class="col-2 first">
			  <h3>I'm  looking for a research collaborator</h3>
			  <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. </p>
			  <a href="#" class="button">Read more</a>
		</div>


	  	<div class="col-2">
			  <h3>I'm looking for a research supervisor</h3>
			  <p>Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
			  <a href="#" class="button">Read more</a>
		</div>

		<div class="col-2">
			  <h3>I'm looking for an expert opinion</h3>
			  <p>Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
				  <a href="#" class="button">Read more</a>
		</div>
				
		<div class="col-2" style="width: 190px">
			<h3>Use FAE data on your site</h3>
			<p>Learn how Find an Expert can help you embed up-to-date information on your site.</p>
	 	 <a href="#" class="button">Read more</a>
		</div>
	</div>



    <hr>
</div>
</div> <!-- dic wraper set in menu.ftl -->
        <#include "footer.ftl"> 
    </body>
</html>
