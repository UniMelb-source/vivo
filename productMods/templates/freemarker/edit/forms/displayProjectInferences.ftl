
<#if subjectAreaKeys?has_content>
	<br>
	<label>Inherited Subject</label>
	<#list subjectAreaKeys as key>
    	<input type="checkbox" name="subjectAreas" value="${key}" id="subjectAreas${key_index}"> ${inheritedSubjectAreas[key]}
    	<br>
	</#list>
</#if>
<#if personsKeys?has_content>
	<br>
	<label>Inherited Role</label>
	<#list personsKeys as key>
		<input type="checkbox" name="persons" value="${key}" id="persons${key_index}"> ${inheritedPersons[key]}
		<br>
	</#list>
</#if>
