<#--Retrieve certain page specific information information-->
<#assign inheritedCustodianDepartments = editConfiguration.pageData.InheritedCustodianDepartments />
<#assign inheritedCustodians = editConfiguration.pageData.InheritedCustodians />
<#assign inheritedSubjectAreas = editConfiguration.pageData.InheritedSubjectAreas />

<#assign custodianDepartmentKeys = inheritedCustodianDepartments?keys>
<#assign custodianKeys = inheritedCustodians?keys>
<#assign subjectAreaKeys = inheritedSubjectAreas?keys>
