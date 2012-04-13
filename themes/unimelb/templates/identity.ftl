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

<header id="branding" role="banner">
	<div class="header">
        <div class="hgroup">
          <h1><a href="http://fae-dev.unimelb.edu.au/">Find An Expert</a></h1>
        </div>
        <hr>
    </div>
    
    <section id="search" role="region">
        <fieldset>
            <legend>Search form</legend>

<form id="search-form" action="http://bureau2-query.funnelback.com/search/search.cgi" name="search" role="search" accept-charset="UTF-8" method="GET"> 
    <div id="search-field">
        <input type="text" name="query" class="search-vivo" value="" autocapitalize="off">
        <input type="hidden" name="collection" value="go8_melb_local">
        <input type="hidden" name="form" value="vivo">
        Supervisors Only<input type="radio"  name="clicked_facet" value="Who%27s%20Who">
        <input type="hidden" name="gfacet_1" value="1">
        <input type="submit" value="Search" class="search">
    </div>
</form>
            
        </fieldset>
    </section>
</header>
