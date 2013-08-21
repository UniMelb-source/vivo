<#assign requiredHint="<span class='requiredHint'> *</span>" />
<#assign initialHint="<span class='hint'>(initial okay)</span>" />
<#assign submitButtonLabel = "Assign Agreement" />

<#--Retrieve certain page specific information information-->
<#--assign sparqlForAcFilter = editConfiguration.pageData.sparqlForAcFilter /-->
<#assign sparqlQueryUrl = editConfiguration.pageData.sparqlQueryUrl />
<#assign acUrl = editConfiguration.pageData.acUrl />
<#assign acType = editConfiguration.pageData.acType />
<#assign editMode = editConfiguration.pageData.editMode />
<#assign submitButtonTextType = editConfiguration.pageData.submitButtonTextType />
<#assign typeName = editConfiguration.pageData.typeName />

<form id="addAgreementForm" class="customForm noIE67" action="${urls.base}/edit/process" role="add/edit grant role">
    <p>
        <label for="agreementName">Agreement Name <span class="requiredHint"> *</span></label>
        <input class="acSelector" size="35"  type="text" id="agreementName" name="agreementName" value="" role="input" />
    </p>
    <div id="selectedAgreement" class="acSelection">
        <p class="inline">
            <label>Selected Agreement:&nbsp;</label>
            <span class="acSelectionInfo" id="selectedAgreement"></span>
            <a href="${urls.base}/individual?uri=" class="verifyMatch"  title="verify match">(Verify this match)</a>
            <input class="acUriReceiver" type="hidden" id="agreementUri" name="agreementUri" value="" role="input" />
        </p>
    </div>
    <p class="submit">
        <input type="hidden" name = "editKey" value="${editKey}"/>
        <input type="submit" id="submit" value="${submitButtonLabel}"/>
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
