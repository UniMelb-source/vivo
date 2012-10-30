<form id="addResearchDataForm" action="${urls.base}/edit/process" class="customForm rdrCustomForm noIE67">

    <label for="researchDataLabel">Title <span class="requiredHint"> *</span></label>
    <input size="60" type="text" id="researchDataLabel" name="researchDataLabel" value="">
    <br>
    <#assign availableResearchRepositories = editConfiguration.pageData.AvailableResearchRepositories />
    <#assign researchRepositoryKeys = availableResearchRepositories?keys>
    <#assign htmlForElements = editConfiguration.pageData.htmlForElements />
    <#if researchRepositoryKeys?has_content>
        <label for="researchRepository">Location</label>
        <select id="researchRepository" name="researchRepository">
        <#list researchRepositoryKeys as key>
             <option value="${key}">${availableResearchRepositories[key]}</option> 
        </#list>
        </select>
        <br>
    </#if>
    <label for="physicalDataLocation">Location Details</label>
    <textarea name="physicalDataLocation" id="physicalDataLocation" style="width:90%;"></textarea>
    <br>
    <label for="digitalDataLocation">Location Identifier</label>
    <textarea name="digitalDataLocation" id="digitalDataLocation" style="width:90%;"></textarea>
    <br>
    <label for="researchDataDescription">Description of the data<span class="requiredHint"> *</span></label>
    <textarea name="researchDataDescription" id="researchDataDescription" style="width:90%;"></textarea>
    <br>
    <label for="retention">Retention Period</label>
    <select id="retention" name="retention">
        <option value="5 years">5 years</option>
        <option value="7 years">7 years</option>
        <option value="15 years">15 years</option>
        <option value="permanent">Permanent</option>
        <option value="to be determined">To be determined</option>
    </select>
    <br>
    <label for="accessiblity">Access</label>
    <textarea name="accessibility" id="accessibility" style="width:90%;"></textarea>
    <br>
    <label for="rights">Rights</label>
    <textarea name="rights" id="rights" style="width:90%;"></textarea>
    <br>
    <label for="dataManagementPlanAvailable">Data Management Plan Available?</label>
    <input type="checkbox" name="dataManagementPlanAvailable" value="true" id="dataManagementPlanAvailable">
    <br>
    <label for="dataManagementPlanDescription">Data Management Plan</label>
    <input size="30" type="text" id="dataManagementPlanDescription" name="dataManagementPlanDescription" value="">
    <br>
    <#if htmlForElements?keys?seq_contains("collectedDateRangeStartDateTime")>
        Collected range start&nbsp; ${htmlForElements["collectedDateRangeStartDateTime"]}
    </#if>
    <br>
    <#if htmlForElements?keys?seq_contains("collectedDateRangeEndDateTime")>
        Collected range end&nbsp; ${htmlForElements["collectedDateRangeEndDateTime"]}
    </#if>
    <br>
    <#if htmlForElements?keys?seq_contains("coveredDateRangeStartDateTime")>
        Covered range start&nbsp; ${htmlForElements["coveredDateRangeStartDateTime"]}
    </#if>
    <br>
    <#if htmlForElements?keys?seq_contains("coveredDateRangeEndDateTime")>
        Covered range end&nbsp; ${htmlForElements["coveredDateRangeEndDateTime"]}
    </#if>
    <br>

    <#include "displayResearchDataInferences.ftl" />
    <#include "creatorFields.ftl" />
    <#include "setupChildOrParent.ftl" />

    <p class="submit">
        <input type="hidden" name = "editKey" value="${editKey}" role="input" />
        <input type="submit" id="submit" value="Add Research Data" role="button" role="input" />

        <span class="or"> or </span>

        <a class="cancel" href="${cancelUrl}" title="Cancel">Cancel</a>
    </p>

    <p id="requiredLegend" class="requiredHint">* required fields</p>
</form>
