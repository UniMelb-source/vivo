<#-- $This file is distributed under the terms of the license in /doc/license.txt$ -->

<#-- Custom form for adding authors to information resources -->

<#import "lib-vivo-form.ftl" as lvf>

<#include "setupProjectInferences.ftl" />

<#assign title="<em>${editConfiguration.subjectName}</em>" />
<#assign requiredHint="<span class='requiredHint'> *</span>" />
<#assign initialHint="<span class='hint'>(initial okay)</span>" />

<@lvf.unsupportedBrowser urls.base/>

<h2>${title}</h2>

<#include "displayErrors.ftl" />

<h3>Add Project to Grant</h3>

<form id="addProjectForm" action ="${submitUrl}" class="customForm rdrCustomForm noIE67">

    <label for="projectLabel">Title <span class="requiredHint"> *</span></label>
    <input size="30" type="text" id="projectLabel" name="projectLabel" value="">

    <br><br>

<#include "displayProjectInferences.ftl" />

    <label for="projectDescription">Description <span class="requiredHint"> *</span></label>
    <textarea name="projectDescription" id="projectDescription" style="width:90%;"></textarea>

<#include "setupChildOrParent.ftl" />

    <p class="submit">
        <input type="hidden" name = "editKey" value="${editKey}" role="input" />
        <input type="submit" id="submit" value="Add Project" role="button" role="input" />

        <span class="or"> or </span>

        <a class="cancel" href="${cancelUrl}" title="Cancel">Cancel</a>
    </p>

    <p id="requiredLegend" class="requiredHint">* required fields</p>
</form>

${stylesheets.add(
    ,
	'<link rel="stylesheet" href="${urls.base}/edit/forms/css/customForm.css" />',
	'<link rel="stylesheet" href="${urls.base}/edit/forms/css/autocomplete.css" />')}



${scripts.add('<script type="text/javascript" src="${urls.base}/js/customFormUtils.js"></script>')}
${scripts.add('<script type="text/javascript" src="${urls.base}/js/browserUtils.js"></script>')}
