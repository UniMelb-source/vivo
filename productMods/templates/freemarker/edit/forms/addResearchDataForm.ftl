<form id="addResearchDataForm" action="${urls.base}/edit/process" class="customForm rdrCustomForm noIE67">

    <label for="researchDataLabel">Title <span class="requiredHint"> *</span></label>
    <input size="60" type="text" id="researchDataLabel" name="researchDataLabel" value="">
    <br>
    <#assign availableResearchRepositories = editConfiguration.pageData.AvailableResearchRepositories />
    <#assign researchRepositoryKeys = availableResearchRepositories?keys>
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
    <label for="dataManagementPlanNumber">Data Management Plan</label>
    <input size="30" type="text" id="dataManagementPlanNumber" name="dataManagementPlanNumber" value="">
    <br>
    <label for="collectedDateRangeStartDateTime">Collected Date Range Start</label>
    <input size="30" type="text" id="collectedDateRangeStartDateTime" name="collectedDateRangeStartDateTime" value="">
    <br>
    <label for="collectedDateRangeEndDateTime">Collected Date Range End</label>
    <input size="30" type="text" id="collectedDateRangeEndDateTime" name="collectedDateRangeEndDateTime" value="">
    <br>
    <label for="coveredDateRangeStartDateTime">Covered Date Range Start</label>
    <input size="30" type="text" id="coveredDateRangeStartDateTime" name="coveredDateRangeStartDateTime" value="">
    <br>
    <label for="coveredDateRangeEndDateTime">Covered Date Range End</label>
    <input size="30" type="text" id="coveredDateRangeEndDateTime" name="coveredDateRangeEndDateTime" value="">
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
