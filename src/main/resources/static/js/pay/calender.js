$(function() {
    $( "#testDatepicker" ).datepicker({dateFormat: 'yy-mm-dd',showOn: "both",
        buttonImage: "http://jqueryui.com/resources/demos/datepicker/images/calendar.gif",
        buttonImageOnly: true,
        buttonText: "선택",
        yearSuffix: "년",
        monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
        dayNamesMin: ['일','월','화','수','목','금','토'],
        minDate: 0,
        maxDate: "+1M",
        showMonthAfterYear: true
    });
    $('#datepicker').datepicker('setDate', 'today');
});