<#import "lib-datetime.ftl" as dt>

<#macro print_status position_property>
    <#if position_property.statements?has_content>
        <#local status = "Not Current">
        <#list position_property.statements as statement>
            <#if statement.dateTimeStart?? && statement.dateTimeEnd??>
                <#local position_start_year = dt.xsdDateTimeToYear(statement.dateTimeStart)?number>
                <#local position_end_year = dt.xsdDateTimeToYear(statement.dateTimeEnd)?number>
                <#local now_string = .now?string>
                <#assign res = now_string?matches("(\\d{1,2})/(\\d{1,2})/(\\d{4}) (\\d{1,2}):(\\d{1,2}) (.{2})")>
                <#if ! res?has_content>
                    <#break>
                </#if>
                <#local match = res[0]>
                <#local now_year = match?groups[3]?number>
                <#if (position_start_year <= now_year) && (position_end_year >= now_year)>
                    <#local status = "Current">
                    <#break>
                </#if>
            </#if>
        </#list>
        <#if status??>
            <h2 id="status">Status</h2>
            <span>${status}</span>
        </#if>
    </#if>
</#macro>
