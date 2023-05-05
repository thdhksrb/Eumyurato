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



