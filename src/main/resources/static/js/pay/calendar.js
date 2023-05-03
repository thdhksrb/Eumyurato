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

window.onload = function() {
    const loginUserJson = window.sessionStorage.getItem("loginUser");
    if (loginUserJson !== null) {
        // 로그인 상태인 경우
        const loginUser = JSON.parse(loginUserJson);
        const userNameElem = document.getElementById("userName");
        userNameElem.innerText = loginUser.name;

        const logoutBtn = document.createElement("a");
        logoutBtn.setAttribute("href", window.location.href);
        logoutBtn.onclick = function() {
            window.sessionStorage.removeItem("loginUser");
        };
        const logoutIcon = document.createElement("img");
        logoutIcon.setAttribute("src", "/img/logout.png");
        logoutIcon.setAttribute("style", "height: 30px; width: 30px;");
        logoutBtn.appendChild(logoutIcon);

        const navLogin = document.getElementById("navLogin");
        navLogin.style.display = "none";

        const navLogout = document.getElementById("navLogout");
        navLogout.style.display = "flex";
        navLogout.querySelector("#logoutBtn").appendChild(logoutBtn);
    } else {
        // 로그인 상태가 아닌 경우
        const loginBtn = document.getElementById("loginBtn");
        loginBtn.onclick = function() {

            window.sessionStorage.setItem("prevUrl",window.location.href);

            window.location.assign("/login-common");
        };

        const navLogin = document.getElementById("navLogin");
        navLogin.style.display = "flex";

        const navLogout = document.getElementById("navLogout");
        navLogout.style.display = "none";
    }
};

const selectSeat = document.querySelector('#selectSeat');
selectSeat.disabled = true;
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


selectSeat.addEventListener('click', () => {
    const selectedDate = datePicker.value;

    const loginUserJson = window.sessionStorage.getItem("loginUser");
    if (loginUserJson !== null) {
        window.location.href = `/smallconcert/detail/${id}/calendar/${selectedDate}`;
    }else {
        alert("로그인 후 이용해주세요.");
    }
});

