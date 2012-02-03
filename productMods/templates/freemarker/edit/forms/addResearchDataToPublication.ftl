<#-- $This file is distributed under the terms of the license in /doc/license.txt$ -->

<#-- Custom form for adding authors to information resources -->

<#import "lib-vivo-form.ftl" as lvf>

<#assign title="<em>${editConfiguration.subjectName}</em>" />
<#assign requiredHint="<span class='requiredHint'> *</span>" />
<#assign initialHint="<span class='hint'>(initial okay)</span>" />

<@lvf.unsupportedBrowser urls.base/>

<h2>${title}</h2>

<#if submissionErrors?has_content>
    <section id="error-alert" role="alert" class="validationError">
        <img src="${urls.images}/iconAlert.png" width="24" height="24" alert="Error alert icon" />
        <p>
        <#--below shows examples of both printing out all error messages and checking the error message for a specific field-->
        <#list submissionErrors?keys as errorFieldName>
        		  ${submissionErrors[errorFieldName]} <br/>
        </#list>
        
        </p>
    </section>
</#if>

<h3>Add Research Data to Publication</h3>

<form id="addResearchDataForm" action ="${submitUrl}" class="customForm noIE67">
    
        <p class="submit">
            <input type="hidden" name = "editKey" value="${editKey}" role="input" />
            <input type="submit" id="submit" value="Add Author" role="button" role="input" />
            
            <span class="or"> or </span>
            
            <a class="cancel" href="${cancelUrl}" title="Cancel">Cancel</a>
        </p>

        <p id="requiredLegend" class="requiredHint">* required fields</p>
</form>

${stylesheets.add('<link rel="stylesheet" href="${urls.base}/js/jquery-ui/css/smoothness/jquery-ui-1.8.9.custom.css" />',
									'<link rel="stylesheet" href="${urls.base}/edit/forms/css/customForm.css" />',
									'<link rel="stylesheet" href="${urls.base}/edit/forms/css/autocomplete.css" />')}


${scripts.add('<script type="text/javascript" src="${urls.base}/js/jquery-ui/js/jquery-ui-1.8.9.custom.min.js"></script>')}
${scripts.add('<script type="text/javascript" src="${urls.base}/js/customFormUtils.js"></script>')}
${scripts.add('<script type="text/javascript" src="${urls.base}/js/browserUtils.js"></script>')}