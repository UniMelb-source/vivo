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

<meta charset="utf-8" />
<!-- Google Chrome Frame open source plug-in brings Google Chrome's open web technologies and speedy JavaScript engine to Internet Explorer-->
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

<title>${title}</title>

<link rel="stylesheet" href="//brand.unimelb.edu.au/web-templates/1-1/css/complete.css">
<script type="text/javascript" src="//brand.unimelb.edu.au/global-header/js/injection.js"></script>


<#include "stylesheets.ftl">
<link rel="stylesheet" href="${urls.theme}/css/screen.css" />
<link rel="stylesheet" href="${urls.base}/js/jquery-ui/css/smoothness/jquery-ui.custom.css" />

<#include "headScripts.ftl">

<script type="text/javascript" src="${urls.base}/js/jquery-ui/js/jquery-ui.custom.min.js"></script>
<script type="text/javascript" src="${urls.theme}/js/jquery.cookie.js"></script>
<script type="text/javascript" src="${urls.theme}/js/rdr.js"></script>
<script type="text/javascript" src="${urls.theme}/js/tabs.js"></script>
<!--script type="text/javascript" src="${urls.theme}/js/custom-form.js"></script-->
<script type="text/javascript" src="${urls.base}/edit/forms/js/custom-form.js"></script>

<!--[if lt IE 7]>
<link rel="stylesheet" href="${urls.theme}/css/ie6.css" />
<![endif]-->

<!--[if IE 7]>
<link rel="stylesheet" href="${urls.theme}/css/ie7.css" />
<![endif]-->

<!--[if (gte IE 6)&(lte IE 8)]>
<script type="text/javascript" src="${urls.base}/js/selectivizr.js"></script>
<![endif]-->

<#-- Inject head content specified in the controller. Currently this is used only to generate an rdf link on
an individual profile page. -->
${headContent!}

<link rel="shortcut icon" type="image/x-icon" href="${urls.base}/favicon.ico">

<!-- Bootstrap Includes -->
<link rel="stylesheet" href="${urls.theme}/lib/bootstrap/css/bootstrap.min.css">
<script type="text/javascript" src="${urls.theme}/lib/bootstrap/js/bootstrap.min.js"></script>

<!-- RDR Stylesheets -->
<link rel="stylesheet" href="${urls.theme}/css/rdr.css">