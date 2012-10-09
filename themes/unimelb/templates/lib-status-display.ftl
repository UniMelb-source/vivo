<#import "lib-datetime.ftl" as dt>

<#macro print_status position_property>
    <#if position_property.statements?has_content>
        <h2 id="status">Status</h2>
        <#list position_property.statements as statement>
            <#local position_start_year = dt.xsdDateTimeToYear(statement.dateTimeStart)?number>
            <#local position_end_year = dt.xsdDateTimeToYear(statement.dateTimeEnd)?number>
            <#local now_string = .now?string>
            <#assign res = now_string?matches("(\\d{1,2})/(\\d{1,2})/(\\d{4}) (\\d{1,2}):(\\d{1,2}) (.{2})")>
            <#if ! res?has_content>
                <span>Not Current</span>
                <#break>
            </#if>
            <#local match = res[0]>
            <#local now_year = match?groups[3]?number>
            <#if (position_start_year <= now_year) && (position_end_year >= now_year)>
                <span>Current</span>
                <#break>
            </#if>
            <span>Not Current</span>
        </#list>
    </#if>
</#macro>
