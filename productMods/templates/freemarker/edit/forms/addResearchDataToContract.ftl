<#-- $This file is distributed under the terms of the license in /doc/license.txt$ -->

<#-- Custom form for adding authors to information resources -->

<#import "lib-vivo-form.ftl" as lvf>

<#include "setupResearchDataInferences.ftl" />

<#assign title="<em>${editConfiguration.subjectName}</em>" />
<#assign requiredHint="<span class='requiredHint'> *</span>" />
<#assign initialHint="<span class='hint'>(initial okay)</span>" />

<@lvf.unsupportedBrowser urls.base/>

<h2>${title}</h2>

<#include "displayErrors.ftl" />

<h3>Add Research Data to Contract</h3>

<#include "addResearchDataForm.ftl" />

${stylesheets.add(
	'<link rel="stylesheet" href="${urls.base}/js/jquery-ui/css/smoothness/jquery-ui-1.8.9.custom.css" />',
	'<link rel="stylesheet" href="${urls.base}/edit/forms/css/customForm.css" />',
	'<link rel="stylesheet" href="${urls.base}/edit/forms/css/autocomplete.css" />')}


${scripts.add('<script type="text/javascript" src="${urls.base}/js/jquery-ui/js/jquery-ui-1.8.9.custom.min.js"></script>')}
${scripts.add('<script type="text/javascript" src="${urls.base}/js/customFormUtils.js"></script>')}
${scripts.add('<script type="text/javascript" src="${urls.base}/js/browserUtils.js"></script>')}
