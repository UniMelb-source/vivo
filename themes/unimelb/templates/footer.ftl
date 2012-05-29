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
</div> <!-- #wrapper-content -->

<div class="wrapper"> 
<div class="footer-wrapper">
<footer  role="contentinfo">
    <p class="copyright">
        <#if copyright??>
            <small>&copy;${copyright.year?c}
            <#if copyright.url??>
                <a href="${copyright.url}" title="copyright">${copyright.text}</a>
            <#else>
                ${copyright.text}
            </#if>
             | <a class="terms" href="${urls.termsOfUse}" title="terms of use">Terms of Use</a></small> | 
        </#if>
        Powered by <a class="powered-by-vivo" href="http://vivoweb.org" target="_blank" title="powered by VIVO"><strong>VIVO</strong></a>
        <#if user.hasRevisionInfoAccess>
             | Version <a href="${version.moreInfoUrl}" title="version">${version.label}</a>
        </#if>
    </p>
   <!-- 
    <nav role="navigation">
        <ul id="footer-nav" role="list">
            <li role="listitem"><a href="${urls.about}" title="about">About</a></li>
            <#if urls.contact??>
                <li role="listitem"><a href="${urls.contact}" title="contact us">Contact Us</a></li>
            </#if> 
            <li role="listitem"><a href="http://www.vivoweb.org/support" target="blank" title="support">Support</a></li>
        </ul>
    </nav>-->
</footer>
</div>
   <hr>

<div class="footer">
     <div id="local" class="wrapper">
       <p class="footertitle">Melbourne Research</p>
       <div id="org-details" class="col-2">

         <p class="location">Level 5, 161 Barry Street<br> The University of Melbourne <br>Parkville 3010 VIC Australia</p>
       </div>
       <ul class="contact col-2">
         <li><strong>Phone:</strong> +61 3 8344 2000</li>
         <li><strong>Fax:</strong> +61 3 9347 6739</li>
         <li><strong>Email:</strong> <a href="mailto:ed-research@unimelb.edu.au" onClick="recordOutboundLink(this, 'Global Nav Research', 'email enquiry');return false;">Make an enquiry</a></li>
       </ul>
       <ul class="local-maintainer col-2">
         <li><strong>Authoriser:</strong><br>Deputy Vice-Chancellor (Research)</li>
         <li><strong>Maintainer:</strong><br>Research Marketing Manager</li>
       </ul>
       <hr>
     </div>
     <hr>
   </div>
</div>
<#include "scripts.ftl">
