function initialize() {
	if (PrimeFaces.widgets['btn-03']) {
		PF('btn-03').disable();
	}

	if (PrimeFaces.widgets['btn-04']) {
		PF('btn-04').disable();
	}
}

function saveMode() {
	$('.ui-state-error').removeClass('ui-state-error');
	$('tr').removeClass('ui-state-highlight');

	if (PrimeFaces.widgets['btn-02']) {
		PF('btn-02').enable();
	}

	if (PrimeFaces.widgets['btn-03']) {
		PF('btn-03').disable();
	}

	if (PrimeFaces.widgets['btn-04']) {
		PF('btn-04').disable();
	}
}

function updateMode() {
	$('html,body').scrollTop(0);
	$('.ui-state-error').removeClass('ui-state-error');

	if (PrimeFaces.widgets['btn-02']) {
		PF('btn-02').disable();
	}

	if (PrimeFaces.widgets['btn-03']) {
		PF('btn-03').enable();
	}

	if (PrimeFaces.widgets['btn-04']) {
		PF('btn-04').enable();
	}
}

function showMsgDialog() {
	PF('wvrMsgDialog').show();
}

function hideMsgDialog() {
	PF('wvrMsgDialog').hide();
}

function currencyFormat(number) {
	if (!number) {
		number = 0;
	}

	var decimalplaces = 2;
	var decimalcharacter = ".";
	var thousandseparater = ",";
	number = parseFloat(number);
	var sign = number < 0 ? "-" : "";
	var formatted = new String(number.toFixed(decimalplaces));

	if (decimalcharacter.length && decimalcharacter != ".") {
		formatted = formatted.replace(/\./, decimalcharacter);
	}

	var integer = "";
	var fraction = "";
	var strnumber = new String(formatted);
	var dotpos = decimalcharacter.length ? strnumber.indexOf(decimalcharacter) : -1;

	if (dotpos > -1) {
		if (dotpos) {
			integer = strnumber.substr(0, dotpos);
		}
		fraction = strnumber.substr(dotpos + 1);
	} else {
		integer = strnumber;
	}

	if (integer) {
		integer = String(Math.abs(integer));
	}

	while (fraction.length < decimalplaces) {
		fraction += "0";
	}

	temparray = new Array();
	while (integer.length > 3) {
		temparray.unshift(integer.substr(-3));
		integer = integer.substr(0, integer.length - 3);
	}

	temparray.unshift(integer);
	integer = temparray.join(thousandseparater);
	return integer + decimalcharacter + fraction;
}
