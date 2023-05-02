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

    function getEvents(category) {
        fetch(`/profile/admin/management?category=${category}`, {
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
                displayEvents(data, category);
            })
            .catch((error) => {
                console.error('fetch 작동에 문제가 있습니다.', error);
            });
    }

    // function displayEvents(events, category) {
    //     const eventList = document.getElementById('eventList');
    //     eventList.innerHTML = '';
    //
    //     events.forEach((event) => {
    //         const eventItem = document.createElement('div');
    //         let eventHTML = '';
    //
    //         switch (category) {
    //             case 'busking':
    //                 // 버스킹 공연에 대한 HTML 구조를 생성
    //                 eventHTML = `
    //                 <h3>${event.name}</h3>
    //                 <p>${event.artId}</p>
    //                 <p>${event.location}</p>
    //                 <p>${event.date}</p>
    //                 <p>${event.regDate}</p>
    //                 `;
    //                 break;
    //             case 'smallconcert':
    //                 // 소규모 공연에 대한 HTML 구조를 생성
    //                 eventHTML = `
    //                 <h3>${event.name}</h3>
    //                 <p>${event.enterId}</p>
    //                 <p>${event.pname}</p>
    //                 <p>${event.location}</p>
    //                 <p>${event.startDate} - ${event.lastDate}</p>
    //                 <p>${event.price}</p>
    //                 `;
    //                 break;
    //             case 'localfestival':
    //                 // 지역 축제에 대한 HTML 구조를 생성
    //                 eventHTML = `
    //                 <h3>${event.name}</h3>
    //                 <p>${event.org}</p>
    //                 <p>${event.location}</p>
    //                 <p>${event.startDate} - ${event.lastDate}</p>
    //                 `;
    //                 break;
    //             default:
    //                 break;
    //         }
    //
    //         eventItem.innerHTML = eventHTML;
    //         eventList.appendChild(eventItem);
    //     });
    // }

    function displayEvents(events, category) {
        const eventTbody = document.getElementById('eventTbody');
        eventTbody.innerHTML = '';

        events.forEach((event) => {
            const eventRow = eventTbody.insertRow();

            // 기본 공통 컬럼

            switch (category) {
                case 'busking':
                    // 버스킹 공연에 대한 데이터를 생성
                    eventRow.insertCell().textContent = event.name;
                    eventRow.insertCell().textContent = event.artId;
                    eventRow.insertCell().textContent = event.location;
                    eventRow.insertCell().textContent = event.date;
                    eventRow.insertCell().textContent = event.regDate;
                    break;

                case 'smallconcert':
                    // 소규모 공연에 대한 데이터를 생성
                    eventRow.insertCell().textContent = event.name;
                    eventRow.insertCell().textContent = event.enterId;
                    eventRow.insertCell().textContent = event.pname;
                    eventRow.insertCell().textContent = event.location;
                    eventRow.insertCell().textContent = event.startDate + ' - ' + event.lastDate;
                    eventRow.insertCell().textContent = event.price;
                    break;

                case 'localfestival':
                    // 지역 축제에 대한 데이터를 생성
                    eventRow.insertCell().textContent = event.name;
                    eventRow.insertCell().textContent = event.org;
                    eventRow.insertCell().textContent = event.location;
                    eventRow.insertCell().textContent = event.startDate + ' - ' + event.lastDate;
                    break;

                default:
                    break;
            }
        });
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