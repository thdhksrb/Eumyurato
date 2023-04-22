$(function() {
    $( "#datepicker" ).datepicker({dateFormat: 'yy-mm-dd',showOn: "both",
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

const searchBtn = document.querySelector('#search-btn');
const datePicker  = document.querySelector('#datepicker');
var url = location.pathname;
var id = url.match(/\d+/)[0];

searchBtn.addEventListener('click', () => {
    const selectedDate = datePicker.value;

    // Create JSON data
    const data = { selectedDate };

    // Send AJAX request
    fetch('/smallconcert/detail/' + id + '/calender/json', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data),
    })
        .then(response => response.json())
        .then(data => console.log(data))
        .catch(error => console.error(error));
});


