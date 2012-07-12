
<#if subjectAreaKeys?has_content>
	<br>
	<label>Inherited Subject</label>
	<#list subjectAreaKeys as key>
    	<input type="checkbox" name="subjectAreas" value="${key}" id="subjectAreas${key_index}"> ${inheritedSubjectAreas[key]}
    	<br>
	</#list>
</#if>
<#if rolesKeys?has_content>
	<br>
	<label>Inherited Role</label>
	<#list rolesKeys as key>
		<input type="checkbox" name="roles" value="${key}" id="roles${key_index}"> ${inheritedRoles[key]}
		<br>
	</#list>
</#if>
