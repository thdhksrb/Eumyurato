document.addEventListener('DOMContentLoaded', () => {
    const token = sessionStorage.getItem('jwtToken');
    getBuskingList(token);
});

document.getElementById('searchBtn').addEventListener('click', (event) => {
    event.preventDefault();
    const token = sessionStorage.getItem('jwtToken');
    const searchColumn = document.getElementById('searchColumn').value;
    const searchKeyword = document.getElementById('searchKeyword').value;
    getBuskingList(token, searchColumn, searchKeyword);
});

function getBuskingList(token, searchColumn = null, searchKeyword = null, page = 1) {
    let url = '/profile/artist/management';
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
        .then((buskingList) => {
            displayBuskingList(buskingList, page);
        })
        .catch((error) => {
            console.error('fetch 작동에 문제가 있습니다.', error);
        });
}

function displayBuskingList(buskingList, currentPage) {
    const perPage = 5;
    const buskingTbody = document.getElementById('buskingTbody');
    buskingTbody.innerHTML = '';

    const start = (currentPage - 1) * perPage;
    const end = start + perPage;

    const currentDate = new Date();

    buskingList.slice(start, end).forEach((busking) => {
        const buskingRow = buskingTbody.insertRow();
        
        const startBuskingDate = new Date(busking.date);

        const deleteButton = document.createElement('button');
        deleteButton.textContent = '취소';
        deleteButton.classList.add('delete-btn');
        deleteButton.addEventListener('click', () => {
            deleteBusking(busking.id);
        });

        buskingRow.insertCell().textContent = busking.name;
        buskingRow.insertCell().textContent = busking.artId;
        buskingRow.insertCell().textContent = busking.location;
        buskingRow.insertCell().textContent = busking.date;
        buskingRow.insertCell().textContent = busking.regDate;

        // 현재 날짜 이전 예약은 취소 불가
        if (startBuskingDate >= currentDate) {
            buskingRow.insertCell().appendChild(deleteButton);
        } else {
            buskingRow.insertCell().textContent = '취소 불가';
        }

    });

    createPagination(buskingList.length, perPage, currentPage);
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
            getBuskingList(token, searchColumn, searchKeyword, i); // i를 페이지 인수로 전달합니다.
        });
        li.appendChild(a);
        paginationEl.appendChild(li);
    }
}

function deleteBusking(buskingId) {
    if (!confirm('정말로 이 공연을 삭제하시겠습니까?')) {
        return;
    }

    const token = sessionStorage.getItem('jwtToken');
    const url = `/profile/artist/management?id=${buskingId}`;

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
                getBuskingList(token, searchColumn, searchKeyword, currentPage);
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
