$(document).ready(function() {
    $('#dataManagementPlanDescriptionWrapper').hide();
    $('#dataManagementPlanAvailable').change(function(){
        var $wrapper = $('#dataManagementPlanDescriptionWrapper');
        $(this).attr('checked') ? $wrapper.show() : $wrapper.hide();
    });
});
