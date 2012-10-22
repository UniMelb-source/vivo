<#-- $This file is distributed under the terms of the license in /doc/license.txt$ -->

<#-- Custom object property statement view for https://rdr.unimelb.edu.au/config/recordCreated. 
    
     This template must be self-contained and not rely on other variables set for the individual page, because it
     is also used to generate the property statement during a deletion.  
 -->

<#import "lib-datetime.ftl" as dt>

<#-- No core:dateTime data property assigned. Display a link to the core:DateTimeValue object -->
<#if ! statement.dateTime??>
    <a href="${profileUrl(statement.uri("recordCreated"))}" title="incomplete date time">incomplete date/time</a>
<#else>
    ${dt.formatXsdDateTimeLong(statement.dateTime, statement.precision!)}
</#if>
