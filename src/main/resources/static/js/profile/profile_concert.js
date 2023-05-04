document.getElementById('buskingBtn').addEventListener('click', () => {
    getEvents('busking');
    updateSearchColumns('busking'); // 추가된 부분
});

document.getElementById('smallConcertBtn').addEventListener('click', () => {
    getEvents('smallconcert');
    updateSearchColumns('smallconcert'); // 추가된 부분
});

document.getElementById('localFestivalBtn').addEventListener('click', () => {
    getEvents('localfestival');
    updateSearchColumns('localfestival'); // 추가된 부분
});

window.onload = function() {
    getEvents('busking');
    updateSearchColumns('busking'); // 추가된 부분
};

    function getEvents(category, page = 1) {
        let url = `/profile/admin/management?category=${category}&page=${page}`;
        if (column && keyword) {
            url += `&column=${column}&keyword=${keyword}`;
        }

        fetch(url , {
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
                updateSearchColumns(category); // 추가된 부분
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
                    // 버스킹 공연에 대한 데이터를 생성
                    eventRow.insertCell().textContent = event.name;
                    eventRow.insertCell().textContent = event.artId;
                    eventRow.insertCell().textContent = event.location;
                    eventRow.insertCell().textContent = event.date;
                    eventRow.insertCell().textContent = event.regDate;
                    eventRow.insertCell().appendChild(deleteButton);
                    break;

                case 'smallconcert':
                    // 소규모 공연에 대한 데이터를 생성
                    eventRow.insertCell().textContent = event.name;
                    eventRow.insertCell().textContent = event.enterId;
                    eventRow.insertCell().textContent = event.pname;
                    eventRow.insertCell().textContent = event.location;
                    eventRow.insertCell().textContent = event.startDate + ' - ' + event.lastDate;
                    eventRow.insertCell().textContent = event.price;
                    eventRow.insertCell().appendChild(deleteButton);
                    break;

                case 'localfestival':
                    // 지역 축제에 대한 데이터를 생성
                    eventRow.insertCell().textContent = event.name;
                    eventRow.insertCell().textContent = event.org;
                    eventRow.insertCell().textContent = event.location;
                    eventRow.insertCell().textContent = event.startDate + ' - ' + event.lastDate;
                    eventRow.insertCell().appendChild(deleteButton);
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
                alert('이벤트 삭제가 완료되었습니다.');
                getEvents(category);
            })
            .catch((error) => {
                console.error('이벤트 삭제 중 에러가 발생했습니다.', error);
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

    const searchColumns = {
        busking: [
            { value: 'name', text: '공연명' },
            { value: 'artId', text: '아티스트명' },
            { value: 'location', text: '위치' }
        ],
        smallconcert: [
            { value: 'name', text: '공연명' },
            { value: 'enterId', text: '기업명' },
            { value: 'pname', text: '공연자명' },
            { value: 'location', text: '위치' }
            // 필요한 경우 추가 검색 컬럼 옵션을 추가하세요.
        ],
        localfestival: [
            { value: 'name', text: '축제명' },
            { value: 'org', text: '주최기관' },
            { value: 'location', text: '위치' }
            // 필요한 경우 추가 검색 컬럼 옵션을 추가하세요.
        ],
    };

    function updateSearchColumns(category) {
        const searchColumnSelect = document.getElementById('searchColumn');
        searchColumnSelect.innerHTML = '<option value="">검색 컬럼 선택</option>';

        searchColumns[category].forEach((column) => {
            const option = document.createElement('option');
            option.value = column.value;
            option.textContent = column.text;
            searchColumnSelect.appendChild(option);
        });
    }

    document.getElementById('searchBtn').addEventListener('click', () => {
        const column = document.getElementById('searchColumn').value;
        const keyword = document.getElementById('searchKeyword').value;
        getEvents(currentCategory, 1, column, keyword);
    });