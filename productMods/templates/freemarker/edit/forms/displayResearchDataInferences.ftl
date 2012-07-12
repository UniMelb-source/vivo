
<#if custodianDepartmentKeys?has_content>
	<br>
	<label>Inherited Custodian Departments</label>
	<#list custodianDepartmentKeys as key>
		<input type="checkbox" name="custodianDepartments" value="${key}" id="custodianDepartments${key_index}">  ${inheritedCustodianDepartments[key]}
    	<br>
	</#list>
</#if>
<#if custodianKeys?has_content>
	<br>
	<label>Inherited Custodian</label>
	<#list custodianKeys as key>
		<input type="checkbox" name="custodians" value="${key}" id="custodians${key_index}"> ${inheritedCustodians[key]}
		<br>
	</#list>
</#if>
<#if subjectAreaKeys?has_content>
	<br>
	<label>Inherited Subject</label>
	<#list subjectAreaKeys as key>
		<input type="checkbox" name="subjectAreas" value="${key}" id="subjectAreas${key_index}"> ${inheritedSubjectAreas[key]}
		<br>
	</#list>
</#if>
