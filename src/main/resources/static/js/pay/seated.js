let test = [];
let selectedSeats = new Array();
let selectedSeatsMap = [];
const seatWrapper = document.querySelector(".seat-wrapper");
let clicked = "";
let div = "";
var url = location.pathname;
var id = url.match(/\d+/)[0];
var day = url.match(/(\d{4}-\d{2}-\d{2})/)[0];




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
selectCompletedButton.addEventListener('click', function() {
    const xhr = new XMLHttpRequest();
    xhr.open('POST', 'http://localhost:8081/smallconcert/detail/' +id+ '/calendar/' +day+ '/pay' );
    xhr.setRequestHeader('Content-Type', 'application/json');

    xhr.onload = function() {
        if (xhr.status === 200) {
            console.log(xhr.responseText);

            const response = JSON.parse(xhr.responseText);
            const result = response.result;

            if (result === 1) {
                // 예약 성공 시 다음 페이지로 이동합니다.
                window.location.href = `/smallconcert/detail/${id}/calendar/${day}/pay`;
            } else {
                // 실패한 경우 팝업창을 띄우고 페이지를 리로드합니다.
                alert('이미 선택된 좌석입니다.');
                location.reload();
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
    selectedSeats = [];
    const selectedSeatElement = document.querySelector('.selected-seat');
    selectedSeatElement.textContent = '선택좌석: ';
});

const searchButton = document.querySelector('#search-btn');
searchButton.addEventListener('click', function() {
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



