'use strict';

$(document).ready(function() {
    /*
     * Constant declarations
     */
    var COOKIE_NAME_PREVIOUS_PAGE = 'rdr_previous_page';
    var COOKIE_NAME_CURRENT_PAGE = 'rdr_current_page';
    var DEFAULT_REDIRECT_PAGE = '/';

    // Data Management Plan show/hide section
    $('#dataManagementPlanDescriptionWrapper').hide();

    $('#dataManagementPlanAvailable').change(function(){
        var $wrapper = $('#dataManagementPlanDescriptionWrapper');
        $(this).attr('checked') ? $wrapper.slideDown() : $wrapper.slideUp();
    });

    // Setup tooltips
    $('a[rel=popover]').popover({
            title : 'Further Information'
    }).click(function(event) {
		event.preventDefault();
	});

    /*
     * Fix to turn Content Verified? to radio buttons
     *
     * TODO: this check currently works off text matching
     * and should be moved to a class or identifier
     * if possible
     */
    var heading = $('div[role=main] h2 em').text();

    if (heading.indexOf('content verified') >= 0 || heading.indexOf('data management plan') >= 0) {
        var textField = $('form textarea[name=literal]');
        var radioField = $(
            '<div class="radio-set">' +
                '<input id="literalFalse" type="radio" name="literal" value="false" /><label for="literalFalse">False</label>' +
                '<input id="literalTrue" type="radio" name="literal" value="true" /><label for="literalTrue">True</label>' +
            '</div>');

        textField.val() === "true" ? radioField.children('#literalTrue').prop('checked', true) : radioField.children('#literalFalse').prop('checked', true);
        textField.after(radioField).remove();
    }

    /*
     * Map the current and previous pages,
     * this is used for the cancel buttons
     *
     * TODO: This could be performed on the server and should
     * be when possible
     */
    $.cookie.defaults.path = '/';

    /*
     * Attempt to handle a refresh and not overwrite the
     * previous page if so
     */
    if (window.location.href !== $.cookie(COOKIE_NAME_CURRENT_PAGE)) {
        $.cookie(COOKIE_NAME_PREVIOUS_PAGE, $.cookie(COOKIE_NAME_CURRENT_PAGE));
        $.cookie(COOKIE_NAME_CURRENT_PAGE, window.location.href);
    }

    /*
     * For all of our custom forms, overwrite the cancel
     * link to redirect back to the previous page
     */
    var rdrCustomForm = $('form.rdrCustomForm');

    if (rdrCustomForm.length > 0) {
        rdrCustomForm.find('a.cancel').click(function(event) {
            event.preventDefault();
            var previousPage = $.cookie(COOKIE_NAME_PREVIOUS_PAGE);
            var redirectPage = previousPage ? previousPage : "//" + window.location.hostname + DEFAULT_REDIRECT_PAGE;
            window.location.href = redirectPage;
        });
    }
});
