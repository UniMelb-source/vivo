<#-- $This file is distributed under the terms of the license in /doc/license.txt$ -->

<#-- Custom form for adding authors to information resources -->

<#import "lib-vivo-form.ftl" as lvf>

<#--Retrieve certain page specific information information-->
<#assign inheritedCustodianDepartments = editConfiguration.pageData.InheritedCustodianDepartments />
<#assign inheritedCustodians = editConfiguration.pageData.InheritedCustodians />
<#-- <#assign inheritedSubjectArea = editConfiguration.pageData.InheritedSubjectArea /> -->

<#assign custodianDepartmentKeys = inheritedCustodianDepartments?keys>
<#assign custodianKeys = inheritedCustodians?keys>
<#-- <#assign subjectAreaKeys = inheritedSubjectArea?keys> -->

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

    <label for="researchDataLabel">Title <span class="requiredHint"> *</span></label>
    <input size="30" type="text" id="researchDataLabel" name="researchDataLabel" value="">

    <!-- <input type="checkbox" name="subjectArea" id="subjectArea1" value="http://purl.org/asc/1297.0/1998/rfcd/280104">subject area 1

    <input type="checkbox" name="researchDataLabel" value="${key}">${inheritedCustodianDepartments[key]} -->

    <#list custodianDepartmentKeys as key><input type="checkbox" name="custodianDepartments" value="${key}">${inheritedCustodianDepartments[key]}</#list>
    <#list custodianKeys as key><input type="checkbox" name="custodians" value="${key}">${inheritedCustodians[key]}</#list>
    <#-- <#list subjectAreaKeys as key>${key} = ${inheritedSubjectArea[key]}; </#list> -->

    <label for="dataDescription">Description <span class="requiredHint"> *</span></label>
    <textarea name="dataDescription" id="dataDescription" style="width:90%;"></textarea>

    <p class="submit">
        <input type="hidden" name = "editKey" value="${editKey}" role="input" />
        <input type="submit" id="submit" value="Add Research Data" role="button" role="input" />

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