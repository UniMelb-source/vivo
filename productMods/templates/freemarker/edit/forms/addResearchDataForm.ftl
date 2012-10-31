<form id="addResearchDataForm" action="${urls.base}/edit/process" class="customForm rdrCustomForm noIE67">
    <#assign availableResearchRepositories = editConfiguration.pageData.AvailableResearchRepositories />
    <#assign researchRepositoryKeys = availableResearchRepositories?keys>
    <#assign htmlForElements = editConfiguration.pageData.htmlForElements />
    <label for="researchDataLabel">Title <span class="requiredHint"> *</span></label>
    <input size="60" type="text" id="researchDataLabel" name="researchDataLabel" value="">
    <br>
    <fieldset class="property-grouping">
        <legend>Descriptive Metadata</legend>
        <label for="researchDataDescription">Description of the data<span class="requiredHint"> *</span></label>
        <textarea name="researchDataDescription" id="researchDataDescription" style="width:90%;"></textarea>
        <br>
        <label>Date of Data Collection</label>
        <#if htmlForElements?keys?seq_contains("collectedDateRangeStartDateTime")>
        <strong>Start</strong>
        ${htmlForElements["collectedDateRangeStartDateTime"]}
        </#if>
        <br>
        <#if htmlForElements?keys?seq_contains("collectedDateRangeEndDateTime")>
        <strong>End</strong> ${htmlForElements["collectedDateRangeEndDateTime"]}
        </#if>
        <br>
        <label>Date Range Coverage</label>
        <#if htmlForElements?keys?seq_contains("coveredDateRangeStartDateTime")>
        <strong>Start</strong> ${htmlForElements["coveredDateRangeStartDateTime"]}
        </#if>
        <br>
        <#if htmlForElements?keys?seq_contains("coveredDateRangeEndDateTime")>
        <strong>End</strong> ${htmlForElements["coveredDateRangeEndDateTime"]}
        </#if>
        <br>
    </fieldset>
    <fieldset class="property-grouping">
        <legend>Administrative Metadata</legend>
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
        <label for="digitalDataLocation">Location URI</label>
        <textarea name="digitalDataLocation" id="digitalDataLocation" style="width:90%;"></textarea>
        <br>
        <label for="dataManagementPlanAvailable">Data Management Plan?</label>
        <input type="checkbox" name="dataManagementPlanAvailable" value="true" id="dataManagementPlanAvailable">
        <br>
        <div id="dataManagementPlanDescriptionWrapper">
            <label for="dataManagementPlanDescription">Data Management Plan Description</label>
            <input size="30" type="text" id="dataManagementPlanDescription" name="dataManagementPlanDescription" value="">
            <br>
        </div>
        <label for="accessiblity">Access</label>
        <textarea name="accessibility" id="accessibility" style="width:90%;"></textarea>
        <br>
        <label for="rights">Rights</label>
        <textarea name="rights" id="rights" style="width:90%;"></textarea>
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
        <#include "displayResearchDataInferences.ftl" />
    </fieldset>
    <fieldset class="property-grouping">
        <legend>Geographic Coverage</legend>
        <label for="gml">GML</label>
        <textarea name="gml" id="gml" style="width:90%;"></textarea>
        <br>
        <label for="gmlKmlPolyCoords">GML/KML Poly Co-ordinates</label>
        <textarea name="gmlKmlPolyCoords" id="gmlKmlPolyCoords" style="width:90%;"></textarea>
        <br>
        <label for="gpx">GPX</label>
        <textarea name="gpx" id="gpx" style="width:90%;"></textarea>
        <br>
        <label for="kml">KML</label>
        <textarea name="kml" id="kml" style="width:90%;"></textarea>
        <br>
        <label for="kmlPolyCoords">KML Poly Co-ordinates</label>
        <textarea name="kmlPolyCoords" id="kmlPolyCoords" style="width:90%;"></textarea>
        <br>
    </fieldset>

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
