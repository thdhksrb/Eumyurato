/*window.onload = function() {
    const loginUserJson = window.sessionStorage.getItem("loginUser");
    if (loginUserJson !== null) {
        // 로그인 상태인 경우
        const loginUser = JSON.parse(loginUserJson);

        const userNameElem = document.getElementById("userName");
        userNameElem.innerText = loginUser.name;

        const logoutBtn = document.createElement("a");
        logoutBtn.setAttribute("href", "/logout");
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
        const navLogin = document.getElementById("navLogin");
        navLogin.style.display = "flex";

        const navLogout = document.getElementById("navLogout");
        navLogout.style.display = "none";
    }
};*/

window.onload = function () {
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
        mypageBtn.onclick = function () {
            const xhr = new XMLHttpRequest(); // 새로운 XMLHttpRequest 객체 생성
            xhr.open('POST', '/profile'); // POST 요청, /profile 엔드포인트로 요청
            xhr.setRequestHeader('Content-Type', 'application/json'); // 요청 헤더에 JSON 형태의 데이터를 전송한다고 명시
            xhr.setRequestHeader('Authorization', `Bearer ${jwtToken}`); // 요청 헤더에 JWT 토큰 추가

            xhr.onload = function () { // 요청이 끝난 후, 콜백 함수 실행
                if (xhr.status === 200) { // 요청이 성공적으로 처리되었다면
                    const result = JSON.parse(xhr.responseText); // 서버로부터 받은 JSON 데이터를 객체로 변환
                    const pageUri = result.URI; // URI 값을 변수에 저장
                    window.location.href = pageUri; // 받아온 페이지 URI로 이동
                } else { // 요청이 처리되지 않았다면
                    console.log('Request failed.  Returned status of ' + xhr.status); // 콘솔에 에러 메시지 출력
                }
            };

            xhr.send(); // 요청 전송, JSON 형태의 데이터로 바꿔서 보냅니다.
        };


        // 로그아웃
        const logoutBtn = document.createElement("a");
        logoutBtn.setAttribute("href", "/logout");
        logoutBtn.onclick = function () {
            fetch('/logout', { method: 'POST', credentials: 'include' })
                .then(response => {
                    if (response.ok) {
                        // 세션 스토리지에서 토큰 제거
                        window.sessionStorage.removeItem("jwtToken");
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

fetch('/top5artists', {
    method: 'POST',
    headers: {
        'Content-Type': 'application/json'
    }
})
    .then(response => response.json()) // JSON 형태로 응답 받기
    .then(data => {
        const tbody = document.querySelector('#artistTableBody'); // tbody 태그 선택

        // 기존 테이블 내용 삭제
        while (tbody.firstChild) {
            tbody.removeChild(tbody.firstChild);
        }

        // 데이터를 이용하여 행 추가
        data.forEach((artist, index) => {
            const row = document.createElement('tr');
            const imgCell = document.createElement('td');//랭킹 이미지 크기는 100x100
            const rankCell = document.createElement('td');
            const nameCell = document.createElement('td');
            const genreCell = document.createElement('td');

            rankCell.textContent = index + 1;
            imgCell.textContent = artist.img;//랭킹 이미지 100x100
            nameCell.textContent = artist.name;
            genreCell.textContent = artist.genre;

            row.appendChild(rankCell);
            row.appendChild(imgCell);//랭킹 이미지
            row.appendChild(nameCell);
            row.appendChild(genreCell);
            tbody.appendChild(row);
        });
    })
    .catch(error => console.error(error)); // 에러 처리하기




fetch('/top5concert', {
    method: 'POST',
    headers: {
        'Content-Type': 'application/json'
    }
})
    .then(response => response.json()) // JSON 형태로 응답 받기
    .then(data => {
        const tbody = document.querySelector('#concetTableBody'); // tbody 태그 선택

        // 기존 테이블 내용 삭제
        while (tbody.firstChild) {
            tbody.removeChild(tbody.firstChild);
        }

        // 데이터를 이용하여 행 추가
        data.forEach((concert, index) => {
            const row = document.createElement('tr');
            const rankCell = document.createElement('td');
            const nameCell = document.createElement('td');

            rankCell.textContent = index + 1;
            nameCell.textContent = concert.name;

            row.appendChild(rankCell);
            row.appendChild(nameCell);
            tbody.appendChild(row);
        });
    })
    .catch(error => console.error(error)); // 에러


const rows = document.querySelectorAll('#artistTableBody tr');

rows.forEach(row => {
    row.addEventListener('click', function() {
        // 기존에 확대된 행이 있을 경우, 확대를 원래 크기로 되돌립니다.
        const activeRow = document.querySelector('#artistTableBody .active');
        if (activeRow) {
            activeRow.classList.remove('active');
        }

        // 현재 클릭한 행의 크기를 확대합니다.
        this.classList.add('active');
    });
});
