/*
 * jQuery hashchange event - v1.3 - 7/21/2010
 * http://benalman.com/projects/jquery-hashchange-plugin/
 * 
 * Copyright (c) 2010 "Cowboy" Ben Alman
 * Dual licensed under the MIT and GPL licenses.
 * http://benalman.com/about/license/
 */
(function($,e,b){var c="hashchange",h=document,f,g=$.event.special,i=h.documentMode,d="on"+c in e&&(i===b||i>7);function a(j){j=j||location.href;return"#"+j.replace(/^[^#]*#?(.*)$/,"$1")}$.fn[c]=function(j){return j?this.bind(c,j):this.trigger(c)};$.fn[c].delay=50;g[c]=$.extend(g[c],{setup:function(){if(d){return false}$(f.start)},teardown:function(){if(d){return false}$(f.stop)}});f=(function(){var j={},p,m=a(),k=function(q){return q},l=k,o=k;j.start=function(){p||n()};j.stop=function(){p&&clearTimeout(p);p=b};function n(){var r=a(),q=o(m);if(r!==m){l(m=r,q);$(e).trigger(c)}else{if(q!==m){location.href=location.href.replace(/#.*/,"")+q}}p=setTimeout(n,$.fn[c].delay)}$.browser.msie&&!d&&(function(){var q,r;j.start=function(){if(!q){r=$.fn[c].src;r=r&&r+a();q=$('<iframe tabindex="-1" title="empty"/>').hide().one("load",function(){r||l(a());n()}).attr("src",r||"javascript:0").insertAfter("body")[0].contentWindow;h.onpropertychange=function(){try{if(event.propertyName==="title"){q.document.title=h.title}}catch(s){}}}};j.stop=k;o=function(){return a(q.location.href)};l=function(v,s){var u=q.document,t=$.fn[c].domain;if(v!==s){u.title=h.title;u.open();t&&u.write('<script>document.domain="'+t+'"<\/script>');u.close();q.location.hash=v}}})();return j})()})(jQuery,this);
/*
 * End of jQuery hashchange event script.
 */

(function () {
	var head = document.getElementsByTagName("head")[0];
	if (head) {
		var scriptStyles = document.createElement("link");
		scriptStyles.rel = "stylesheet";
		scriptStyles.type = "text/css";
		scriptStyles.href = "/themes/unimelb/css/tabs.css";
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

var hashChange = function() {

	if(window.location.hash) {
		// Fragment exists
		var hash = window.location.hash.replace("tab-", "");		
		var extraParam = getURLParameter('extra');

		if(extraParam == 'true')
		{
			//for h3 - i.e. returning from an edit
			$(hash).parent().parent().show();

			var hrefForAnchor = '#';

			//the anchor relating to the tab we want
			hrefForAnchor += $(hash).parent().siblings('h2').attr('id');

			//the tab we want
			$('a[href="' + hrefForAnchor + '"]').parent().addClass('activeTab');
		}
		else
		{
			//Non existent anchor, or misspelled bookmark etc.
			if(!($('a[href="' + hash + '"]').parent().exists()))
			{
				$('.property-group').first().show();
				$("#property-group-menu ul li").first().addClass("activeTab");				
			}
			else
			{
				//for h2 - Clicking back from a page perhaps, or bookmarks.
				//Regardless, it should be here.
				$(hash).parent().show();

				//the tab we want
				$('a[href="' + hash + '"]').parent().addClass('activeTab');
			}
		}
	} else {
		// Fragment doesn't exist - open up the first tab
		$('.property-group').first().show();
		$("#property-group-menu ul li").first().addClass("activeTab");		
	}
}
$(document).ready(hashChange);

$(document).ready(function() {
	$(window).hashchange(function() {
		$('#property-group-menu ul li').removeClass('activeTab');
		$('.property-group').hide();
		hashChange();
	});

	$('#property-group-menu ul li').click(function(event) {
		$('#property-group-menu ul li').removeClass('activeTab');
		var selected_tab = $(this).find('a').attr('href');
		$('.property-group').hide();
		$(selected_tab).parent().show();
		$(this).addClass('activeTab');
		window.location.hash = "tab-" + selected_tab.replace("#", "");
		
		return false;
	});
});
