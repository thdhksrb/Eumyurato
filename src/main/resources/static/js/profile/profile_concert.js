    document.getElementById('buskingBtn').addEventListener('click', () => {
        getEvents('busking');
    });

    document.getElementById('smallConcertBtn').addEventListener('click', () => {
        getEvents('smallconcert');
    });

    document.getElementById('localFestivalBtn').addEventListener('click', () => {
        getEvents('localfestival');
    });

    window.onload = function() {
        getEvents('busking');
    };

    function getEvents(category, page = 1) {
        fetch(`/profile/admin/management?category=${category}&page=${page}`, {
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