$(document).ready(function() {
    // Data Management Plan show/hide section
    $('#dataManagementPlanDescriptionWrapper').hide();

    $('#dataManagementPlanAvailable').change(function(){
        var $wrapper = $('#dataManagementPlanDescriptionWrapper');
        $(this).attr('checked') ? $wrapper.slideDown() : $wrapper.slideUp();
    });

    // Setup tooltips
    $('a[rel=popover]').popover(
    	{
    		title : 'Further Information'
    	}
	).click(function(event) {
		event.preventDefault();
	});
});
