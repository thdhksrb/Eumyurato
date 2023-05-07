var ReservList = $('#reservList');
var url = location.pathname;
var id = url.match(/\d+/)[0];
var day = url.match(/(\d{4}-\d{2}-\d{2})/)[0];

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
    url: '/smallconcert/detail/'+id+'/calendar/' +day+ '/pay/json',
    dataType: 'json',
    success: function(data) {
        var li = $('<li>');
        console.log(data);

        li.append($('<p>').html('<strong>'+ data.myData.concert.name+ '</strong>'));
        li.append($('<p>').html('<strong>공연 일자: </strong>' + data.myData.concert.startDate + ' ~ ' + data.myData.concert.lastDate));
        li.append($('<p>').html('<strong>장소: </strong>' + data.myData.concert.location));
        li.append($('<p>').html('<strong>공연자: </strong>' + data.myData.concert.pname));
        li.append($('<h2 style="margin-top: 20px; margin-bottom: 20px;">').html('<strong>*선택내역*</strong>'));
        li.append($('<p>').html('<strong>날짜: </strong>' + day));
        li.append($('<p>').html('<strong>시간: </strong>' + data.myData.schedule.conDate));
        li.append($('<p>').html('<strong>매수: </strong>' + data.myData.count +'매'));
        li.append($('<p>').html('<strong>좌석: </strong>' + data.myData.seat));
        li.append($('<p>').html('<strong>가격: </strong>' + data.myData.concert.price * data.myData.count + '원'))
        li.append($('<input>').attr({ type: 'radio', id: 'onSite', checked: true }));
        li.append($('<label>').attr('for', 'onSite').html('현장수령'));

        ReservList.append(li);


        // 첫 번째 Ajax 호출이 완료된 후에 두 번째 Ajax 호출을 실행
        $('#pay').click(function (){
            const jwtToken = window.sessionStorage.getItem("jwtToken");
            if (jwtToken !== null) {
                $.ajax({
                    url:'/pay/kakao',
                    dataType: 'json',
                    success:function (data){
                        var box = data.next_redirect_pc_url;
                        window.location.href = box;
                    },
                    error:function (error){
                        alert(error);
                    }
                });
            }else {
                alert("로그인 후 이용해주세요.");
            }
        });


    },
    error: function(xhr, status, error) {
        console.log('AJAX Error: ' + status + error);
    }
});
