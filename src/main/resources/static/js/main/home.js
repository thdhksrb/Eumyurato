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

            xhr.send(JSON.stringify(data)); // 요청 전송, JSON 형태의 데이터로 바꿔서 보냅니다.
        };

        // 로그아웃
        const logoutBtn = document.createElement("a");
        logoutBtn.setAttribute("href", "/logout");
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




