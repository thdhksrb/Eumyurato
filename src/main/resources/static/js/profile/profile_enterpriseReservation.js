document.addEventListener('DOMContentLoaded', () => {
    const token = sessionStorage.getItem('jwtToken');
    getReservationList(token);
});

document.getElementById('searchBtn').addEventListener('click', (event) => {
    event.preventDefault();
    const token = sessionStorage.getItem('jwtToken');
    const searchColumn = document.getElementById('searchColumn').value;
    const searchKeyword = document.getElementById('searchKeyword').value;
    getReservationList(token, searchColumn, searchKeyword);
});

function getReservationList(token, searchColumn = null, searchKeyword = null, page = 1) {
    let url = '/profile/ent/reservation';
    if (searchColumn && searchKeyword) {
        url += `?column=${searchColumn}&keyword=${searchKeyword}`;
    }

    fetch(url, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`,
        },
    })
        .then((response) => {
            if (response.ok) {
                return response.json(); // response.json()을 실행한 Promise 객체 반환
            }
            throw new Error('응답에 문제가 있습니다.');
        })
        .then((reservationList) => {
            console.log(reservationList); // 응답 데이터 출력
            displayReservationList(reservationList, page);
        })
        .catch((error) => {
            console.error('fetch 작동에 문제가 있습니다.', error);
        });
}

function displayReservationList(reservationList, currentPage) {
    const perPage = 5;
    const reservationTbody = document.getElementById('reservationTbody');
    reservationTbody.innerHTML = '';

    const start = (currentPage - 1) * perPage;
    const end = start + perPage;

    reservationList.slice(start, end).forEach((reservation) => {
        const reservationRow = reservationTbody.insertRow();

        reservationRow.insertCell().textContent = reservation.id;
        reservationRow.insertCell().textContent = reservation.name;
        reservationRow.insertCell().textContent = reservation.cid;
        reservationRow.insertCell().textContent = reservation.payTime;
        reservationRow.insertCell().textContent = reservation.viewDate;
        reservationRow.insertCell().textContent = reservation.memberNum;
        reservationRow.insertCell().textContent = reservation.reservPay;
         // 공연명 추가
    });

    const totalItems = reservationList.length;
    createPagination(totalItems, perPage, currentPage);
}

function createPagination(totalItems, perPage, currentPage) {
    const totalPages = Math.ceil(totalItems / perPage);
    const paginationEl = document.querySelector('.pagination');

    paginationEl.innerHTML = '';

    for (let i = 1; i <= totalPages; i++) {
        const li = document.createElement('li');
        const a = document.createElement('a');
        a.href = '#';
        a.textContent = i;
        if (i === currentPage) {
            li.classList.add('active');
        }
        a.addEventListener('click', (event) => {
            event.preventDefault();
            const token = sessionStorage.getItem('jwtToken');
            const searchColumn = document.getElementById('searchColumn').value;
            const searchKeyword = document.getElementById('searchKeyword').value;
            getReservationList(token, searchColumn, searchKeyword, i); // i를 페이지 인수로 전달합니다.
        });
        li.appendChild(a);
        paginationEl.appendChild(li);
    }
}

// 로그아웃
const logoutBtn = document.getElementById("logoutBtn");
logoutBtn.setAttribute("href", "/logout");
logoutBtn.onclick = function () {
    fetch('/logout', { method: 'POST', credentials: 'include' })
        .then(response => {
            if (response.ok) {
                // 세션 스토리지에서 토큰 제거
                window.sessionStorage.removeItem("jwtToken");
                console.log("로그아웃")
                // 홈페이지로 이동
                window.location.href = "/home";
            } else {
                throw new Error("로그아웃 처리에 실패하였습니다.");
            }
        })
        .catch(error => {
            console.error(error);
            alert(error.message);
        });
};