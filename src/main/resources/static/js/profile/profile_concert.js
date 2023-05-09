let currentCategory = 'busking';
getEvents(currentCategory);

document.getElementById('buskingBtn').addEventListener('click', () => {
    currentCategory = 'busking';
    getEvents(currentCategory);
});

document.getElementById('smallConcertBtn').addEventListener('click', () => {
    currentCategory = 'smallconcert';
    getEvents(currentCategory);
});

document.getElementById('localFestivalBtn').addEventListener('click', () => {
    currentCategory = 'localfestival';
    getEvents(currentCategory);
});

document.getElementById('searchBtn').addEventListener('click', () => {
    const searchColumn = document.getElementById('searchColumn').value;
    const searchKeyword = document.getElementById('searchKeyword').value;

    getEvents(currentCategory, 1, searchColumn, searchKeyword);
});

function getEvents(category, page = 1, searchColumn = null, searchKeyword = '') {
    let url = `/profile/admin/management?category=${category}&page=${page}`;
    if (searchColumn && searchKeyword) {
        url += `&column=${searchColumn}&keyword=${searchKeyword}`;
    }

    fetch(url, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        },
    })
        .then((response) => {
            if (response.ok) {
                return response.json();
            }
            throw new Error('응답에 문제가 있습니다.');
        })
        .then((data) => {
            showColumns(category);
            displayEvents(data, category, page);
        })
        .catch((error) => {
            console.error('fetch 작동에 문제가 있습니다.', error);
        });
}

    function displayEvents(events, category, currentPage) {
        const eventTbody = document.getElementById('eventTbody');
        eventTbody.innerHTML = '';

        const eventsPerPage = 5;
        const start = (currentPage - 1) * eventsPerPage;
        const end = start + eventsPerPage;

        const currentDate = new Date();

        events.slice(start, end).forEach((event) => {
            const eventRow = eventTbody.insertRow();
            const eventId = event.id;

            // 각 이벤트 행에 삭제 버튼 추가
            const deleteButton = document.createElement('button');
            deleteButton.textContent = '삭제';
            deleteButton.classList.add('delete-btn'); //버튼 class 지정
            deleteButton.setAttribute('data-category', category);
            deleteButton.setAttribute('data-id', eventId);
            deleteButton.addEventListener('click', () => {
                console.log(category, eventId);
                deleteConcert(category,eventId);
            });

            // 기본 공통 컬럼

            switch (category) {
                case 'busking':

                    const startBuskingDate = new Date(event.date);

                    // 버스킹 공연에 대한 데이터를 생성
                    eventRow.insertCell().textContent = event.name;
                    eventRow.insertCell().textContent = event.artId;
                    eventRow.insertCell().textContent = event.location;
                    eventRow.insertCell().textContent = event.date;
                    eventRow.insertCell().textContent = event.regDate;

                    // 현재 날짜 이전 예약은 취소 불가
                    if (startBuskingDate >= currentDate) {
                        eventRow.insertCell().appendChild(deleteButton);
                    } else {
                        eventRow.insertCell().textContent = '취소 불가';
                    }
                    break;

                case 'smallconcert':
                    // 소규모 공연에 대한 데이터를 생성
                    const startConcertDate = new Date(event.startDate);

                    eventRow.insertCell().textContent = event.name;
                    eventRow.insertCell().textContent = event.enterId;
                    eventRow.insertCell().textContent = event.pname;
                    eventRow.insertCell().textContent = event.location;
                    eventRow.insertCell().textContent = event.startDate;
                    eventRow.insertCell().textContent = event.lastDate;
                    eventRow.insertCell().textContent = event.price;

                    // 현재 날짜 이전 예약은 취소 불가
                    if (startConcertDate >= currentDate) {
                        eventRow.insertCell().appendChild(deleteButton);
                    } else {
                        eventRow.insertCell().textContent = '취소 불가';
                    }

                    break;

                case 'localfestival':
                    // 지역 축제에 대한 데이터를 생성

                    const startDate = new Date(event.startDate);

                    eventRow.insertCell().textContent = event.name;
                    eventRow.insertCell().textContent = event.org;
                    eventRow.insertCell().textContent = event.location;
                    eventRow.insertCell().textContent = event.startDate;
                    eventRow.insertCell().textContent = event.lastDate;

                    // 현재 날짜 이전 예약은 취소 불가
                    if (startDate >= currentDate) {
                        eventRow.insertCell().appendChild(deleteButton);
                    } else {
                        eventRow.insertCell().textContent = '취소 불가';
                    }

                    break;

                default:
                    break;
            }
        });

        createPagination(events.length, eventsPerPage, currentPage, category);
    }

    function showColumns(category) {
        const columns = document.querySelectorAll('th');
        const categoryColumns = document.querySelectorAll(`th.${category}`);

        // 모든 컬럼을 숨깁니다.
        for (let i = 0; i < columns.length; i++) {
            columns[i].style.display = 'none';
        }

        // 선택한 행사의 컬럼만 표시합니다.
        for (let i = 0; i < categoryColumns.length; i++) {
            categoryColumns[i].style.display = 'table-cell';
        }
    }

    function deleteConcert(category,eventId){
        fetch(`/profile/admin/management?category=${category.toLowerCase()}&id=${eventId}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
            },
        })
            .then(() => {
                alert('행사 삭제가 완료되었습니다.');
                getEvents(category);
            })
            .catch((error) => {
                console.error('행사 삭제 중 에러가 발생했습니다.', error);
            });
    }

    function createPagination(totalEvents, eventsPerPage, currentPage, category) {
        const totalPages = Math.ceil(totalEvents / eventsPerPage);
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
                getEvents(category, i);
                scrollToTop();
            });
            li.appendChild(a);
            paginationEl.appendChild(li);
        }
    }

function showColumns(category) {
    const columns = document.querySelectorAll('th');
    const categoryColumns = document.querySelectorAll(`th.${category}`);

    // 모든 컬럼을 숨깁니다.
    for (let i = 0; i < columns.length; i++) {
        columns[i].style.display = 'none';
    }

    // 선택한 행사의 컬럼만 표시합니다.
    for (let i = 0; i < categoryColumns.length; i++) {
        categoryColumns[i].style.display = 'table-cell';
    }

    // 드롭다운 옵션을 업데이트합니다.
    updateDropdownOptions(category);
}

function updateDropdownOptions(category) {
    const options = document.querySelectorAll('#searchColumn option');
    const categoryOptions = document.querySelectorAll(`#searchColumn option.${category}`);

    // 모든 옵션을 숨깁니다.
    for (let i = 0; i < options.length; i++) {
        options[i].style.display = 'none';
    }

    // 선택한 카테고리의 옵션만 표시합니다.
    for (let i = 0; i < categoryOptions.length; i++) {
        categoryOptions[i].style.display = 'block';
    }

    // 첫 번째 표시된 옵션을 선택합니다.
    const firstVisibleOption = document.querySelector(`#searchColumn option.${category}`);
    if (firstVisibleOption) {
        firstVisibleOption.selected = true;
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

