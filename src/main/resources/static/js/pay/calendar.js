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
const schedulesUl = document.querySelector('#schedules');
var url = location.pathname;
var id = url.match(/\d+/)[0];

searchBtn.addEventListener('click', () => {
    const selectedDate = datePicker.value;

    // Create JSON data
    const data = { selectedDate };

    // Send AJAX request
    fetch('/smallconcert/detail/' + id + '/calendar', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data),
    })
        .then(response => response.json())
        .then(schedules => {
            // Clear current schedules
            schedulesUl.innerHTML = '';

            // If there are no schedules, show message
            if (schedules.message == null) {
                const li = document.createElement('li');
                li.textContent = '선택한 날짜에 해당하는 회차 정보가 없습니다.';
                schedulesUl.appendChild(li);
                console.log(schedules);
                selectSeat.disabled = true;
            } else {
                // Add schedules to list
                const schedule = schedules.message;
                const li = document.createElement('li');
                li.textContent = schedule.conDate;
                schedulesUl.appendChild(li);
                console.log(schedules);
                selectSeat.disabled = false;
            }

        })
        .catch(error => console.error(error));
});

const selectSeat = document.querySelector('#selectSeat');

selectSeat.addEventListener('click', () => {
    const selectedDate = datePicker.value;
    window.location.href = `/smallconcert/detail/${id}/calendar/${selectedDate}`;
});