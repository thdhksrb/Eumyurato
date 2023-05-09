var url = location.pathname;
var id = url.match(/\d+/)[0];
var detailList = $('#detailList');
var price = $('#price');

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


            const token = sessionStorage.getItem("jwtToken");
            const headers = {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            };
            const options = {
                method: 'POST',
                headers,
                body: JSON.stringify(payload)
            };

            fetch('/profile', options)
                .then(response => {
                    if (response.ok) {
                        return response.text();
                    } else {
                        throw new Error('Network response was not ok');
                    }
                })
                .then(data => {
                    const URI = JSON.parse(data).URI; // 문자열을 자바스크립트 객체로 변환하여 URI 추출
                    console.log(URI);
                    window.location.href = URI; // 추출한 URI로 페이지 이동
                })
                .catch(error => console.error(error));
        }

        // 로그아웃
        const logoutBtn = document.createElement("a");
        logoutBtn.setAttribute("href", window.location.href);
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

$.ajax({
    url: '/smallconcert/detail/'+id+'/json',
    dataType: 'json',
    success: function(data) {
        console.log(data);
        var li = $('<li>');

        li.append($('<p>').html('<strong>'+ data.name+ '</strong>'));
        li.append($('<p>').html('<strong>장소: </strong>' + data.location));
        li.append($('<p>').html('<strong>공연 일자: </strong>' + data.startDate + ' ~ ' + data.lastDate));
        li.append($('<p>').html('<strong>공연자: </strong>' + data.pname));

        detailList.append(li);

        // 이미지 URL을 가져온다.
        var imageUrl = data.image;

        if(imageUrl !== null && imageUrl.startsWith("https://")){
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
        }else if(imageUrl !== null && !imageUrl.startsWith("https://")){
            var replacedImageUrl = 'https://storage.googleapis.com/eumyurato/' + imageUrl;
            console.log(replacedImageUrl);
            // 이미지 요소를 생성한다.
            var img = document.createElement("img");
            img.src = replacedImageUrl;
            img.style.objectFit = "contain";
            img.style.width = "100%";
            img.style.height = "100%";

            // 이미지 요소를 포함할 div를 찾는다.
            var posterContainer = document.getElementById("posterContainer");

            // div에 이미지 요소를 추가한다.
            posterContainer.appendChild(img);
        }else{
            var img = document.createElement("img");
            img.src = "/img/default.jpg";
            img.style.objectFit = "contain";
            img.style.width = "100%";
            img.style.height = "100%";

            // 이미지 요소를 포함할 div를 찾는다.
            var posterContainer = document.getElementById("posterContainer");

            // div에 이미지 요소를 추가한다.
            posterContainer.appendChild(img);
        }

        price.append($('<p>').html('<strong>티켓가격: </strong><span style="color:red">' + data.price.toLocaleString() + '</span>원'));
    },
    error: function(xhr, status, error) {
        console.log('AJAX Error: ' + status + error);
    }
});

const reservation = document.querySelector('#reservation');

reservation.addEventListener('click', () => {
    const jwtToken = window.sessionStorage.getItem("jwtToken");
    if (jwtToken !== null) {
        window.location.href = `/smallconcert/detail/${id}/calendar`;
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

