jQuery(document).ready(function($) {
	if ($('#redirectForward').length > 0) {
		$('form.customForm').each(function(i, el) {
			$('#redirectForward').parent().css('display', 'none')
			var currentInput = $(this).find('#submit');
			currentInput.attr('value', "Add and Return");
			currentInput.after('<input id="addAndContinue" type="submit" role="button" value="Add and Continue" />');

			$('#addAndContinue').click(function(event) {
				event.preventDefault();
				$('#redirectForward').attr('checked', 'checked');
				$('form.customForm #submit').click();
			});
		});
	}
});
