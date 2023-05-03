var buskerInfo = $('#buskerInfo');
var url = location.pathname;
var id = url.match(/\d+/)[0];



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
    url: '/busking/detail/'+id+'/donation/json',
    dataType: 'json',
    success: function(data) {
        var li = $('<li>');
        console.log(data.name);

        li.append($('<h2>').html(data.name));
        li.append($('<h2>').html('아티스트 : ' + data.nid));

        buskerInfo.append(li);

        // 첫 번째 Ajax 호출이 완료된 후에 두 번째 Ajax 호출을 실행
        $('#pay').click(function (){
            localStorage.setItem('price',priceValue);
            localStorage.setItem('id',id);
            const loginUserJson = window.sessionStorage.getItem("loginUser");
            if(loginUserJson !==null){
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
