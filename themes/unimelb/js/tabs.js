(function () {
    var head = document.getElementsByTagName("head")[0];
    if (head) {
        var scriptStyles = document.createElement("link");
        scriptStyles.rel = "stylesheet";
        scriptStyles.type = "text/css";
        scriptStyles.href = "/vivo/themes/unimelb/css/tabs.css";
        head.appendChild(scriptStyles);
    }
}());

function getURLParameter(name) {
    return decodeURI(
        (RegExp('[\\?&]' + name + '=' + '(.+?)(&|$)').exec(location.search)||[,null])[1]
        );
}

$.fn.exists = function () {
    return this.length !== 0;
}

$(document).ready(function() {
    $('.property-group').hide();
});

$(document).ready(function() {

    if(window.location.hash) {
        // Fragment exists
        var hash = window.location.hash;		
        var extraParam = getURLParameter('extra');

        if(extraParam == 'true' && !$(hash).parent().parent().hasClass('metadata')) {
            //for h3 - i.e. returning from an edit
            $(hash).parent().parent().show();

            var hrefForAnchor = '#';

            //the anchor relating to the tab we want
            hrefForAnchor += $(hash).parent().siblings('h2').attr('id');

            //the tab we want
            $('a[href="' + hrefForAnchor + '"]').parent().addClass('activeTab');
        } else {
            //Non existent anchor, or misspelled bookmark etc.
	    //Also, if the field is in the global field section and not within a tab
            if(!($('a[href="' + hash + '"]').parent().exists())) {
                $('.property-group').first().show();
                $("#property-group-menu ul li").first().addClass("activeTab");				
            } else {
                //for h2 - Clicking back from a page perhaps, or bookmarks.
                //Regardless, it should be here.
                $(hash).show();

                //the tab we want
                $('a[href="' + hash + '"]').parent().addClass('activeTab');
            }
        }
    } else {
        // Fragment doesn't exist - open up the first tab
        $('.property-group').first().show();
        $("#property-group-menu ul li").first().addClass("activeTab");		
    }

    $('#property-group-menu ul li').click(function(event) {
        $('#property-group-menu ul li').removeClass('activeTab');
        var selected_tab = $(this).find('a').attr('href');
        $('.property-group').hide();
        $(selected_tab).show();
        $(this).addClass('activeTab');
		
        return false;
    });

});
