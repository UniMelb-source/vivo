<#assign sparqlQueryUrl = editConfiguration.pageData.sparqlQueryUrl />
<#assign acUrl = editConfiguration.pageData.acUrl />
<#assign acType = editConfiguration.pageData.acType />
<#assign editMode = editConfiguration.pageData.editMode />
<#assign submitButtonTextType = editConfiguration.pageData.submitButtonTextType />
<#assign typeName = editConfiguration.pageData.typeName />
<#assign objectUriVar = editConfiguration.pageData.objectUriVar />

<form id="addExistingForm" class="customForm noIE67" action="${urls.base}/edit/process" role="add/edit grant role">
    <p>
        <label for="existingName">${typeName} Name <span class="requiredHint"> *</span></label>
        <input class="acSelector" size="35"  type="text" id="existingName" name="existingName" value="" role="input" />
    </p>
    <div id="selectedItem" class="acSelection">
        <p class="inline">
            <label>Selected ${typeName}:&nbsp;</label>
            <span class="acSelectionInfo" id="selectedItem"></span>
            <a href="${urls.base}/individual?uri=" class="verifyMatch"  title="verify match">(Verify this match)</a>
            <input class="acUriReceiver" type="hidden" id="${objectUriVar}" name="${objectUriVar}" value="" role="input" />
        </p>
    </div>
    <p class="submit">
        <input type="hidden" name = "editKey" value="${editKey}"/>
        <input type="submit" id="submit" value="Assign ${typeName}"/>
        <span class="or"> or </span>
        <a class="cancel" href="${cancelUrl}" title="Cancel">Cancel</a>
    </p>
</form>

<script type="text/javascript">
    var customFormData  = {
        sparqlQueryUrl: '${sparqlQueryUrl}',
        acUrl: '${acUrl}',
        acType: '${acType}',
        editMode: '${editMode}',
        submitButtonTextType: '${submitButtonTextType}',
        typeName: '${typeName}',
        supportEdit: true
    };
</script>

${scripts.add('<script type="text/javascript" src="${urls.base}/edit/forms/js/customFormWithAutocomplete.js"></script>')}