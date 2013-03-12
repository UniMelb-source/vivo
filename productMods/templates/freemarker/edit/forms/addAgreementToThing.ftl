<#-- $This file is distributed under the terms of the license in /doc/license.txt$ -->

<#-- Custom form for adding authors to agreements -->

<#import "lib-vivo-form.ftl" as lvf>

<#assign title="<em>${editConfiguration.subjectName}</em>" />

<@lvf.unsupportedBrowser urls.base/>

<h2>${title}</h2>

<#include "displayErrors.ftl" />

<#include "addAgreementForm.ftl" />

${stylesheets.add(
									'<link rel="stylesheet" href="${urls.base}/edit/forms/css/customForm.css" />',
									'<link rel="stylesheet" href="${urls.base}/edit/forms/css/autocomplete.css" />')}



${scripts.add('<script type="text/javascript" src="${urls.base}/js/customFormUtils.js"></script>')}
${scripts.add('<script type="text/javascript" src="${urls.base}/edit/forms/js/customFormWithAutocomplete.js"></script>')}
${scripts.add('<script type="text/javascript" src="${urls.base}/js/browserUtils.js"></script>')}
