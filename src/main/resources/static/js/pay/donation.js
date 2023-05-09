var buskerInfo = $('#buskerInfo');
var url = location.pathname;
var id = url.match(/\d+/)[0];



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
    url: '/busking/detail/'+id+'/donation/json',
    dataType: 'json',
    success: function(data) {
        var li = $('<li>');
        console.log(data.name);

        li.append($('<h2>').html(data.name));
        li.append($('<h2>').html('아티스트 : ' + data.nid));

        buskerInfo.append(li);

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

        // 첫 번째 Ajax 호출이 완료된 후에 두 번째 Ajax 호출을 실행
        $('#pay').click(function (){
            localStorage.setItem('price',priceValue);
            localStorage.setItem('id',id);
            const token = sessionStorage.getItem("jwtToken");
            if(token !==null){
                $.ajax({
                    url:'/pay/kakao/donation',
                    dataType: 'json',
                    success:function (data){
                        var box = data.next_redirect_pc_url;
                        window.location.href = box;

                    },
                    error:function (error){
                        alert(error);
                    }
                });
            }else{
                alert("로그인 후 이용해주세요.")
            }

        });
    },
    error: function(xhr, status, error) {
        console.log('AJAX Error: ' + status + error);
    }
});

const price = document.querySelector('#price');
const output = document.querySelector('.output');
let priceValue;
const resetButton = document.querySelector("#reset");
resetButton.addEventListener('click',function (){
    price.value = '';
    output.textContent = '후원금액 : ';
});

const searchButton = document.querySelector("#search-btn");
searchButton.addEventListener('click', function (){
    priceValue = price.value;
    output.textContent = '후원금액 : ' + priceValue + '원';
});

document.querySelector('#plus1000').addEventListener('click',function(){
    const currentValue = parseInt(price.value);
    if(isNaN(currentValue)){
        price.value=1000
    }else{
        price.value = currentValue + 1000;
    }
});
document.querySelector('#plus3000').addEventListener('click',function(){
    const currentValue = parseInt(price.value);
    if(isNaN(currentValue)){
        price.value=3000
    }else{
        price.value = currentValue + 3000;
    }
});
document.querySelector('#plus5000').addEventListener('click',function(){
    const currentValue = parseInt(price.value);
    if(isNaN(currentValue)){
        price.value=5000
    }else{
        price.value = currentValue + 5000;
    }
});
document.querySelector('#plus10000').addEventListener('click',function(){
    const currentValue = parseInt(price.value);
    if(isNaN(currentValue)){
        price.value=10000
    }else{
        price.value = currentValue + 10000;
    }
});
