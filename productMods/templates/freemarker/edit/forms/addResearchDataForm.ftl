<form id="addResearchDataForm" action="${urls.base}/edit/process" class="customForm rdrCustomForm noIE67">
    <#assign availableResearchRepositories = editConfiguration.pageData.AvailableResearchRepositories />
    <#assign autoLabels = editConfiguration.pageData.AutoLabels />
    <#assign infoLabels = editConfiguration.pageData.InfoLabels />
    <#assign researchRepositoryKeys = availableResearchRepositories?keys>
    <#assign htmlForElements = editConfiguration.pageData.htmlForElements />
    <label for="researchDataLabel">Title <span class="requiredHint"> *</span></label>
    <input size="60" type="text" id="researchDataLabel" name="researchDataLabel" value="">
    <br>
    <fieldset class="property-grouping">
        <legend><h2>Descriptive Metadata</h2></legend>

        <div class="label-info">
            <label for="researchDataDescription">${autoLabels["ands:researchDataDescription"]}<span class="requiredHint"> *</span></label>
            <a href="#" rel="popover" data-content="${infoLabels["ands:researchDataDescription"]}">
                <i class="icon-info-sign"></i>
            </a>
        </div>
        <textarea name="researchDataDescription" id="researchDataDescription"></textarea>
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
        <legend><h2>Administrative Metadata</h2></legend>
        <#if researchRepositoryKeys?has_content>
        <div class="label-info">
            <label for="physicalDataLocation">${autoLabels["ands:isLocatedIn"]}</label>
            <a href="#" rel="popover" data-content="${infoLabels["ands:isLocatedIn"]}">
                <i class="icon-info-sign"></i>
            </a>
        </div>
        <!--label for="researchRepository">Location</label-->
        <select id="researchRepository" name="researchRepository">
            <#list researchRepositoryKeys as key>
            <option value="${key}">${availableResearchRepositories[key]}</option>
            </#list>
        </select>
        <br>
        </#if>
        <div class="label-info">
            <label for="physicalDataLocation">${autoLabels["unimelb-rdr:nonDigitalLocation"]}</label>
            <a href="#" rel="popover" data-content="${infoLabels["unimelb-rdr:nonDigitalLocation"]}">
                <i class="icon-info-sign"></i>
            </a>
        </div>
        <!--label for="physicalDataLocation">Location Details</label-->
        <textarea name="physicalDataLocation" id="physicalDataLocation"></textarea>
        <br>
        <div class="label-info">
            <label for="physicalDataLocation">${autoLabels["unimelb-rdr:digitalLocation"]}</label>
            <a href="#" rel="popover" data-content="${infoLabels["unimelb-rdr:digitalLocation"]}">
                <i class="icon-info-sign"></i>
            </a>
        </div>
        <!--label for="digitalDataLocation">Location URI</label-->
        <textarea name="digitalDataLocation" id="digitalDataLocation"></textarea>
        <br>
        <fieldset class="property-grouping property-grouping-child">
            <legend>Data Management Plan</legend>

            <div class="inline-field">
                <div class="label-info label-info-data-management-plan-available">
                    <label for="dataManagementPlanAvailable">${autoLabels["unimelb-rdr:dataManagementPlanAvailable"]}</label>
                    <a href="#" rel="popover" data-content="${infoLabels["unimelb-rdr:dataManagementPlanAvailable"]}">
                        <i class="icon-info-sign"></i>
                    </a>
                </div>
                <input type="checkbox" name="dataManagementPlanAvailable" value="true" id="dataManagementPlanAvailable">
            </div>

            <div id="dataManagementPlanDescriptionWrapper">
                <div class="label-info">
                    <label for="dataManagementPlanDescription">${autoLabels["unimelb-rdr:dataManagementPlanDescription"]}</label>
                    <a href="#" rel="popover" data-content="${infoLabels["unimelb-rdr:dataManagementPlanDescription"]}">
                        <i class="icon-info-sign"></i>
                    </a>
                </div>
                <textarea name="dataManagementPlanDescription" id="dataManagementPlanDescription"></textarea>
            </div>
        </fieldset>

        <div class="label-info">
            <label for="accessibility">${autoLabels["unimelb-rdr:accessibility"]}</label>
            <a href="#" rel="popover" data-content="${infoLabels["unimelb-rdr:accessibility"]}">
                <i class="icon-info-sign"></i>
            </a>
        </div>
        <textarea name="accessibility" id="accessibility"></textarea>
        <br>

        <div class="label-info">
            <label for="rights">${autoLabels["ands:rights"]}</label>
            <a href="#" rel="popover" data-content="${infoLabels["ands:rights"]}">
                <i class="icon-info-sign"></i>
            </a>
        </div>
        <textarea name="rights" id="rights"></textarea>
        <br>

        <div class="label-info">
            <label for="retention">${autoLabels["unimelb-rdr:retentionPeriod"]}</label>
            <a href="#" rel="popover" data-content="${infoLabels["unimelb-rdr:retentionPeriod"]}">
                <i class="icon-info-sign"></i>
            </a>
        </div>
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
        <legend><h2>Geographic Coverage</h2></legend>
        <label for="gml">${autoLabels["ands:gml"]}</label>
        <!--label for="gml">GML</label-->
        <textarea name="gml" id="gml"></textarea>
        <br>
        <label for="gmlKmlPolyCoords">${autoLabels["ands:gmlKmlPolyCoords"]}</label>
        <!--label for="gmlKmlPolyCoords">GML/KML Poly Co-ordinates</label-->
        <textarea name="gmlKmlPolyCoords" id="gmlKmlPolyCoords"></textarea>
        <br>
        <label for="gpx">${autoLabels["ands:gpx"]}</label>
        <!--label for="gpx">GPX</label-->
        <textarea name="gpx" id="gpx"></textarea>
        <br>
        <label for="kml">${autoLabels["ands:kml"]}</label>
        <!--label for="kml">KML</label-->
        <textarea name="kml" id="kml"></textarea>
        <br>
        <label for="kmlPolyCoords">${autoLabels["ands:kmlPolyCoords"]}</label>
        <!--label for="kmlPolyCoords">KML Poly Co-ordinates</label-->
        <textarea name="kmlPolyCoords" id="kmlPolyCoords"></textarea>
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
