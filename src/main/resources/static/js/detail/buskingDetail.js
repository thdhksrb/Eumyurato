var url = location.pathname;
var id = url.match(/\d+/)[0];
var detailList = $('#detailList');
var price = $('#price');

window.onload = function() {
    const loginUserJson = window.sessionStorage.getItem("loginUser");
    if (loginUserJson !== null) {
        // 로그인 상태인 경우
        const loginUser = JSON.parse(loginUserJson);
        const userNameElem = document.getElementById("userName");
        userNameElem.innerText = loginUser.name;

        const logoutBtn = document.createElement("a");
        logoutBtn.setAttribute("href", window.location.href);
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
        const loginBtn = document.getElementById("loginBtn");
        loginBtn.onclick = function() {

            window.sessionStorage.setItem("prevUrl",window.location.href);

            window.location.assign("/login-common");
        };

        const navLogin = document.getElementById("navLogin");
        navLogin.style.display = "flex";

        const navLogout = document.getElementById("navLogout");
        navLogout.style.display = "none";
    }
};


$.ajax({
    url: '/busking/detail/'+id+'/json',
    dataType: 'json',
    success: function(data) {
        var li = $('<li>');

        li.append($('<p>').html('<strong>'+ data.name+ '</strong>'));
        li.append($('<p>').html('<strong>장소: </strong>' + data.location));
        li.append($('<p>').html('<strong>공연 일자: </strong>' + data.date));
        li.append($('<p>').html('<strong>공연자: </strong>' + data.nid));

        detailList.append(li);

        // 이미지 URL을 가져온다.
        var imageUrl = data.image;

        // 이미지 요소를 생성한다.
        var img = document.createElement("img");
        img.src = imageUrl;
        img.style.objectFit = "contain";
        img.style.width = "100%";
        img.style.height = "100%";

        // 이미지 요소를 포함할 div를 찾는다.
        var posterContainer = document.getElementById("posterContainer");

        // div에 이미지 요소를 추가한다.
        posterContainer.appendChild(img);

    },
    error: function(xhr, status, error) {
        console.log('AJAX Error: ' + status + error);
    }
});

const donation = document.querySelector('#donation');

donation.addEventListener('click', () => {
    const loginUserJson = window.sessionStorage.getItem("loginUser");
    if (loginUserJson !== null) {
    window.location.href = `/busking/detail/${id}/donation`;
    }else {
        alert("로그인 후 이용해주세요.");
    }
});

function changeStar() {
    var starImg = document.getElementById("star-img");
    if (starImg.getAttribute("src") === "/assets/star.png") {
        starImg.setAttribute("src", "/assets/fullstar.png");
    } else {
        starImg.setAttribute("src", "/assets/star.png");
    }
}