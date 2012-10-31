$(document).ready(function() {
    $('#dataManagementPlanDescriptionWrapper').hide();
    $('#dataManagementPlanAvailable').change(function(){
        $(this).attr('checked') ? $('#dataManagementPlanDescriptionWrapper').show() : $('#dataManagementPlanDescriptionWrapper').hide();
    });
});