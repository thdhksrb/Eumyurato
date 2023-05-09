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
    let url = '/profile/common/reservation';
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
                return response.json();
            }
            throw new Error('응답에 문제가 있습니다.');
        })
        .then((reservationList) => {
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

    const currentDate = new Date();

    reservationList.slice(start, end).forEach((reservation) => {
        const reservationRow = reservationTbody.insertRow();

        const reservationDate = new Date(reservation.viewDate);

        const deleteButton = document.createElement('button');
        deleteButton.textContent = '취소';
        deleteButton.classList.add('delete-btn');
        deleteButton.addEventListener('click', () => {
            deleteReservation(reservation.id);
        });
        console.log(reservation);
        reservationRow.insertCell().textContent = reservation.id;
        reservationRow.insertCell().textContent = reservation.name;
        reservationRow.insertCell().textContent = reservation.cid;
        reservationRow.insertCell().textContent = reservation.payTime;
        reservationRow.insertCell().textContent = reservation.viewDate;
        reservationRow.insertCell().textContent = reservation.memberNum;
        reservationRow.insertCell().textContent = reservation.reservPay.toLocaleString() + '원';

        // 현재 날짜 이전 예약은 취소 불가
        if (reservationDate >= currentDate) {
            reservationRow.insertCell().appendChild(deleteButton);
        } else {
            reservationRow.insertCell().textContent = '취소 불가';
        }
    });

    createPagination(reservationList.length, perPage, currentPage);
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

function deleteReservation(reservationId) {
    if (!confirm('정말로 이 공연을 취소하시겠습니까?')) {
        return;
    }

    const token = sessionStorage.getItem('jwtToken');
    const url = `/profile/common/reservation?id=${reservationId}`;

    fetch(url, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`,
        },
    })
        .then((response) => {
            if (response.ok) {
                alert('공연이 성공적으로 취소되었습니다.');
                const searchColumn = document.getElementById('searchColumn').value;
                const searchKeyword = document.getElementById('searchKeyword').value;
                getReservationList(token, searchColumn, searchKeyword, currentPage);
            } else {
                throw new Error('응답에 문제가 있습니다.');
            }
        })
        .catch((error) => {
            console.error('fetch 작동에 문제가 있습니다.', error);
        });
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