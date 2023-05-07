let test = [];
let selectedSeats = new Array();
let selectedSeatsMap = [];
const seatWrapper = document.querySelector(".seat-wrapper");
let clicked = "";
let div = "";
var url = location.pathname;
var id = url.match(/\d+/)[0];
var day = url.match(/(\d{4}-\d{2}-\d{2})/)[0];

window.onload = function() {
    const jwtToken = window.sessionStorage.getItem("jwtToken");
    if (jwtToken !== null) {
        // 로그인 상태인 경우

        // 토큰을 '.'으로 분리한 후, 두 번째 부분(payload)만 추출
        const payloadBase64Url = jwtToken.split('.')[1];

        // Base64Url 디코딩
        const payloadBase64 = payloadBase64Url.replace(/-/g, '+').replace(/_/g, '/');
        const payload = JSON.parse(atob(payloadBase64));
        const decodedName = decodeURIComponent(escape(payload.name));
        const userNameElem = document.getElementById("userName");
        userNameElem.innerText = decodedName;

        const mypageBtn = document.getElementById("mypageBtn");
        mypageBtn.onclick = function (){


            const token = sessionStorage.getItem("jwtToken");
            const headers = {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            };
            const options = {
                method: 'POST',
                headers,
                body: JSON.stringify(payload)
            };

            fetch('/profile', options)
                .then(response => {
                    if (response.ok) {
                        return response.text();
                    } else {
                        throw new Error('Network response was not ok');
                    }
                })
                .then(data => {
                    const URI = JSON.parse(data).URI; // 문자열을 자바스크립트 객체로 변환하여 URI 추출
                    console.log(URI);
                    window.location.href = URI; // 추출한 URI로 페이지 이동
                })
                .catch(error => console.error(error));
        }

        // 로그아웃
        const logoutBtn = document.createElement("a");
        logoutBtn.setAttribute("href", window.location.href);
        logoutBtn.onclick = function() {
            window.sessionStorage.removeItem("jwtToken");
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
        const navLogin = document.getElementById("navLogin");
        navLogin.style.display = "flex";

        const navLogout = document.getElementById("navLogout");
        navLogout.style.display = "none";
    }
};


const xhr = new XMLHttpRequest();
xhr.onload = function() {
    if (xhr.status === 200) {
        const response = JSON.parse(xhr.responseText);
        // 서버에서 받아온 데이터(response)를 처리하는 코드
        response.forEach((item) => {
            console.log(item);
        });
        for (let i = 1; i <= 7; i++) {
            div = document.createElement("div");
            seatWrapper.append(div);
            for (let j = 1; j <= 7; j++) {
                const input = document.createElement('input');
                input.type = "button";
                input.name = "seats"
                input.classList = "seat";
                //3중포문을 사용하지 않기위해
                mapping(input, i, j);
                div.append(input);

                if (response.includes(input.value)) {
                    input.disabled = true;
                }

                input.addEventListener('click', function(e) {
                    console.log(e.target.value);
                    //중복방지 함수
                    selectedSeats = selectedSeats.filter((element, index) => selectedSeats.indexOf(element) != index);

                    //click class가 존재할때(제거해주는 toggle)
                    if (input.classList.contains("clicked")) {
                        input.classList.remove("clicked");
                        clicked = document.querySelectorAll(".clicked");
                        selectedSeats.splice(selectedSeats.indexOf(e.target.value), 1);
                        clicked.forEach((data) => {
                            selectedSeats.push(data.value);
                        });
                        //click class가 존재하지 않을때 (추가해주는 toggle)
                    } else {
                        input.classList.add("clicked");
                        clicked = document.querySelectorAll(".clicked");
                        clicked.forEach((data) => {
                            selectedSeats.push(data.value);
                        })
                    }
                    console.log(selectedSeats);
                })
            }
        }
    } else {
        console.error('Error: ' + xhr.status);
    }
};
xhr.open('GET', 'http://localhost:8081/smallconcert/detail/'+id+'/calendar/'+day+'/json');
xhr.send();

const selectCompletedButton = document.querySelector('#selectCompleted');
selectCompletedButton.disabled = true;
selectCompletedButton.addEventListener('click', function() {
    const xhr = new XMLHttpRequest();
    xhr.open('POST', 'http://localhost:8081/smallconcert/detail/' +id+ '/calendar/' +day+ '/pay' );
    xhr.setRequestHeader('Content-Type', 'application/json');

    xhr.onload = function() {
        if (xhr.status === 200) {
            console.log(xhr.responseText);

            const response = JSON.parse(xhr.responseText);
            const result = response.result;

            const token = sessionStorage.getItem("jwtToken");
            if (token !== null) {
                if (result === 1) {
                    // 예약 성공 시 다음 페이지로 이동합니다.
                    window.location.href = `/smallconcert/detail/${id}/calendar/${day}/pay`;
                } else {
                    // 실패한 경우 팝업창을 띄우고 페이지를 리로드합니다.
                    alert('이미 선택된 좌석입니다.');
                    location.reload();
                }
            }else {
                alert("로그인 후 이용해주세요.");
            }


        } else {
            console.error('Error: ' + xhr.status);
        }
    };

    const data = {
        selectedSeats: selectedSeats
    };

    xhr.send(JSON.stringify(data));
});

const resetButton = document.querySelector('#reset');
resetButton.addEventListener('click', function() {
    const clickedSeats = document.querySelectorAll('.clicked');
    clickedSeats.forEach(seat => {
        seat.classList.remove('clicked');
    });
    selectCompletedButton.disabled = true;
    selectedSeats = [];
    const selectedSeatElement = document.querySelector('.selected-seat');
    selectedSeatElement.textContent = '선택좌석: ';
});

const searchButton = document.querySelector('#search-btn');
searchButton.addEventListener('click', function() {
    selectCompletedButton.disabled = false;
    const selectedSeatElement = document.querySelector('.selected-seat');
    selectedSeatElement.textContent = '선택좌석: ' + selectedSeats.join(', ');
});


function mapping(input, i, j) {
    if (i === 1) {
        input.value = "A" + j;
    } else if (i === 2) {
        input.value = "B" + j;
    } else if (i === 3) {
        input.value = "C" + j;
    } else if (i === 4) {
        input.value = "D" + j;
    } else if (i === 5) {
        input.value = "E" + j;
    } else if (i === 6) {
        input.value = "F" + j;
    } else if (i === 7) {
        input.value = "G" + j;
    }
}



