<#import "lib-properties.ftl" as p>

<#macro dataProperty property editable>
	<#if editable>
		<@p.addLinkWithLabel property editable />
		<@p.dataPropertyList property editable />
	<#else>
		<#if property.statements?has_content>
			<h2 id="${property.localName}">${property.name?capitalize}</h2>
			<@p.dataPropertyList property editable />
		</#if>
	</#if>
</#macro>
