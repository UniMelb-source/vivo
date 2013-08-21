<#-- $This file is distributed under the terms of the license in /doc/license.txt$ -->

<#-- Custom form for adding authors to information resources -->

<#import "lib-vivo-form.ftl" as lvf>

<#assign title="<em>${editConfiguration.subjectName}</em>" />
<#assign requiredHint="<span class='requiredHint'> *</span>" />
<#assign initialHint="<span class='hint'>(initial okay)</span>" />
<#assign editForm = editConfiguration.pageData.editForm />

<@lvf.unsupportedBrowser urls.base/>

<h2>${title}</h2>

<#include "displayErrors.ftl" />
<#if user.hasSiteAdminAccess || editConfiguration.predicateUri != "http://purl.org/ands/ontologies/vivo/isCollectorOf">
  <#include "addExistingForm.ftl" />

  <p>If you don't find the appropriate entry on the selection list above:</p>
</#if>
<form class="editForm" action="${urls.base}/editRequestDispatch" role="input" />
    <input type="hidden" value="${editConfiguration.subjectUri}" name="subjectUri" role="input" />
    <input type="hidden" value="${editConfiguration.predicateUri}" name="predicateUri" role="input" />
    <input type="hidden" value="${editForm}" name="editForm" role="input"/>
    <input type="submit" id="submit" value="Create a new item" role="button" />
</form>

${stylesheets.add(	'<link rel="stylesheet" href="${urls.base}/edit/forms/css/customForm.css" />',
					'<link rel="stylesheet" href="${urls.base}/edit/forms/css/autocomplete.css" />')}

${scripts.add('<script type="text/javascript" src="${urls.base}/js/customFormUtils.js"></script>')}
${scripts.add('<script type="text/javascript" src="${urls.base}/js/browserUtils.js"></script>')}
