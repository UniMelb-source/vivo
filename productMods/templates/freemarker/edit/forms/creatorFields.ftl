<#assign username = editConfiguration.pageData._username />
<#assign dateString = editConfiguration.pageData._dateString />

<#if username?has_content>
    <input type="hidden" name="recordCreator" value="${username}" id="recordCreator"/>
</#if>

<#if dateString?has_content>
    <input type="hidden" name="recordCreatedOnDateTime" value="${dateString}" id="recordCreatedOnDateTime"/>
</#if>
