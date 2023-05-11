const jwtToken = sessionStorage.getItem("jwtToken");

$(document).ready(function() {
    getArtistData(); //정보 불러옴

    //프로필 이미지 사용자가 업로드한 이미지로 변경
    $('input[type="file"]').on('change', function(event) {
        var file = event.target.files[0];
        var imageUrl = URL.createObjectURL(file);
        $('#previewImage').attr('src', imageUrl);
    });

    //비밀번호 검사
    $('#pwd').on('change', function (){
        var password = $("input[name='pwd']").val();

        //비밀번호가 이전 것과 동일한 지
        if(password === artPwd){
            alert("최근 사용한 비밀번호입니다. 다른 비밀번호를 선택해 주세요.")
            $("input[name='pwd']").val("");
            $("input[name='pwd']").focus();
            return false;
        }

        // 비밀번호 형식 검사
        var regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[~․!@#\$%\^&\*\(\)_\-\+=\[\]\{\}\|\;:'"<>,\./\?]).{8,20}$/;
        if (!regex.test(password)) {
            // 비밀번호가 요구사항에 부합하지 않을 경우 처리
            alert("비밀번호는 영어 대소문자, 숫자, 특수문자를 포함하고, 8자 이상 20자 이하이어야 합니다.");
            $("input[name='pwd']").val("");
            $("input[name='pwd']").focus();
            return false;
        }
    });

    //비밀번호 재확인이 비밀번호와 같은지 검사
    $('#cpassword').on('change', function() {
        var cpassword = $("input[name='cpassword']").val();
        var pwd = $("input[name='pwd']").val();

        if (cpassword !== pwd) {
            alert("비밀번호가 다릅니다.");
            cpassword = '';
        }
    });

    //수정 버튼
    $('#modify').on('click', function(event) {
        event.preventDefault(); // 기본 동작(페이지 이동) 방지
        if ($("input[name='cpassword']").val() === ""){
            alert("비밀번호를 다시 입력해주세요.");
        }else{
            artistModify();
        }
    });
});

//정보 불러오기
function getArtistData() {
    const xhr = new XMLHttpRequest();
    xhr.open("GET", "/profile/artist/data");
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.setRequestHeader("Authorization", `Bearer ${jwtToken}`);

    xhr.onload = function () {
        if (xhr.status === 200) {
            const artist = JSON.parse(xhr.responseText);
            displayArtistData(artist);
        } else {
            console.log("Request failed. Returned status of " + xhr.status);
        }
    };

    xhr.send();
}

var artId;
var artPwd;

//정보 input창에 띄워주기
function displayArtistData(artist) {
    document.getElementById("id").value = artist.id;
    document.getElementById("pwd").value = artist.pwd;
    document.getElementById("name").value = artist.name;
    document.getElementById("nid").value = artist.nid;
    document.getElementById("email").value = artist.email;
    document.getElementById("phone").value = artist.phone;
    document.getElementById("genre").value = artist.genre;

    artId = artist.id;
    artPwd = artist.pwd;

// 이미지 URL을 가져온다.
    var imageUrl = artist.image;

    if (imageUrl !== null && imageUrl.startsWith("https://")) {
        $('#previewImage').attr('src', imageUrl);

    } else if(imageUrl !== null && !imageUrl.startsWith("https://")) {
        var replacedImageUrl = 'https://storage.googleapis.com/eumyurato/' + imageUrl;
        $('#previewImage').attr('src', replacedImageUrl);
    }else{
        var defaultImage = '/img/memberDefaultImg.jpg';
        $('#previewImage').attr('src', defaultImage);
    }
}

//회원정보 수정값 받아서 서버로 보내기
function artistModify() {
    var formData = new FormData();
    var fileInput = document.querySelector('input[name="avatar"]');
    var file = fileInput.files[0];
    formData.append('imgFile', file);
    var data = {
        id: artId,
        pwd: $("input[name='pwd']").val(),
        nid: $("input[name='nid']").val(),
        email: $("input[name='email']").val(),
        phone: $("input[name='phone']").val(),
        genre: $("select[name='genre']").val()
    };

    formData.append('artistDTO', new Blob([JSON.stringify(data)], { type: 'application/json' }));
    console.log(formData);
    $.ajax({
        type: 'POST',
        url: '/profile/artist/modify',
        data: formData,
        processData: false,
        contentType: false,
        success: function() {
            alert('회원정보가 수정되었습니다.');
            location.reload();
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(textStatus + ': ' + errorThrown);
        }
    });
}

//닉네임 팝업창
function openNidPopup() {
    var _width = '500';
    var _height = '200';
    var _left = Math.ceil(( window.screen.width - _width )/2);
    var _top = Math.ceil(( window.screen.height - _height )/2);

    // 팝업창 생성
    const popup = window.open('', '활동명 변경하기', 'width='+ _width +', height='+ _height +', left=' + _left + ', top='+ _top );

    const html = `
    <html lang="en" xmlns:th="http://www.thymeleaf.org" xmlns="http://www.w3.org/1999/html">
<head>
    <title>활동명 변경하기</title>
    <script th:src="@{https://code.jquery.com/jquery-3.6.0.min.js}"></script>
</head>
<body>
<div style="display:flex; justify-content:center; align-items:center; flex-direction:column;">
    <h2>활동명 변경</h2>
    <input type="text" name="nickname" placeholder="공백 및 특수문자 제외 20자 이하"
           style="width: 60%; padding: 10px; margin-bottom: 10px; border-radius: 5px; border: 1px solid #0099ff;">
    <br>
    <button id="submit-btn" style="padding: 10px 20px; background-color: #294ba1; color: white; border: none; border-radius: 5px;">변경</button>
</div>
<script>
document.getElementById("submit-btn").addEventListener("click", function() {
    var newNickname = document.querySelector('input[name="nickname"]').value;
    opener.document.getElementById("nid").value = newNickname;
    window.close();
});
</script>
</body>
</html>
`;
    popup.document.write(html);
    // 팝업창 스타일 지정
    popup.document.body.style.backgroundColor = '#f5f5f5'; // 배경색 지정
}

//닉네임 중복 검사
function nidCheck(){
    var nickname = $('input[name="nid"]').val();
    $.ajax({
        type: 'POST',
        url: '/profile/artist/nidcheck',
        data: { nid: nickname },
        success: function(response) {
            console.log(response);
            console.log(response.nid);
            if (parseInt(response.nid) > 0) {// 중복되는 경우 처리
                alert('이미 사용 중인 활동명입니다. 다른 활동명을 입력해주세요.');
                nickname = '';
            }
        },
        error: function(xhr, status, error) {
            // 에러 처리
            alert('에러가 발생했습니다.');
            console.log(error);
        }
    });
}

//이메일 팝업창
function openEmailPopup() {
    var _width = '500';
    var _height = '200';
    var _left = Math.ceil(( window.screen.width - _width )/2);
    var _top = Math.ceil(( window.screen.height - _height )/2);

    // 팝업창 생성
    const popup = window.open('', '이메일 변경하기', 'width='+ _width +', height='+ _height +', left=' + _left + ', top='+ _top );

// HTML 코드 조합
    const htmlCode = `
    <html>
        <head>
            <title>이메일 변경하기</title>
        </head>
        <body>
            <div style="display:flex; justify-content:center; align-items:center; flex-direction:column;">
                <h2>이메일 변경</h2>
                    <input type="email" name="email" placeholder="you@example.com" 
                    style="width: 80%; padding: 10px; margin-bottom: 10px; border-radius: 5px; border: 1px solid #0099ff;">
                    <br>
                    <button id="submit-btn" style="padding: 10px 20px; background-color: #294ba1; color: white; border: none; border-radius: 5px;">변경</button>
            </div>
            <script>
document.getElementById("submit-btn").addEventListener("click", function() {
    const email = document.querySelector('input[name="email"]').value;
    if(!/@/.test(email)){
        alert("이메일 형식이 다릅니다.");
    }else{
          opener.document.getElementById("email").value = email;
          window.close();
    }
});
</script>
        </body>
    </html>
`;
    popup.document.write(htmlCode);
    // 팝업창 스타일 지정
    popup.document.body.style.backgroundColor = '#f5f5f5'; // 배경색 지정
}

//핸드폰 번호 팝업창
function openPhonePopup() {
    var _width = '500';
    var _height = '200';
    var _left = Math.ceil(( window.screen.width - _width )/2);
    var _top = Math.ceil(( window.screen.height - _height )/2);

    // 팝업창 생성
    const popup = window.open('', '휴대번호 변경하기', 'width='+ _width +', height='+ _height +', left=' + _left + ', top='+ _top );

// HTML 코드 조합
    const htmlCode = `
    <html>
        <head>
            <title>휴대번호 변경하기</title>
        </head>
        <body>
            <div style="display:flex; justify-content:center; align-items:center; flex-direction:column;">
                <h2>휴대번호 변경</h2>
                    <label style="width: 50%; display: flex; align-items: center;">
                     <input type="tel" name="phone1" pattern="[0-9]{3}" required placeholder="010"
                     style="width: 60px; border-radius: 5px; border: 1px solid #0099ff;">
                     <span style="margin-left: 5px; margin-right: 5px;"> - </span>
                     <input type="tel" name="phone2" pattern="[0-9]{4}" required placeholder="0000"
                     style="width: 85px; border-radius: 5px; border: 1px solid #0099ff;">
                     <span style="margin-left: 5px; margin-right: 5px;"> - </span>
                     <input type="tel" name="phone3" pattern="[0-9]{4}" required placeholder="0000"
                     style="width: 85px; border-radius: 5px; border: 1px solid #0099ff;">
                    </label>
                    <br>
                    <button id="submit-btn" style="padding: 10px 20px; background-color: #294ba1; color: white; border: none; border-radius: 5px;">변경</button>
            </div>
            <script>
            document.getElementById("submit-btn").addEventListener("click", function() {
                 const phone1 = document.querySelector('input[name="phone1"]').value;
                const phone2 = document.querySelector('input[name="phone2"]').value;
                const phone3 = document.querySelector('input[name="phone3"]').value;
                const phoneNumber = phone1 + phone2 + phone3;
                
                if(phoneNumber.length !== 11){
                    alert("휴대번호를 다시 확인해주세요.");
                }else{
                    opener.document.getElementById("phone").value = phoneNumber;
                    window.close(); 
                }
            });
            </script>
        </body>
    </html>
`;
    popup.document.write(htmlCode);
    // 팝업창 스타일 지정
    popup.document.body.style.backgroundColor = '#f5f5f5'; // 배경색 지정
}

// 로그아웃
const logoutBtn = document.getElementById("logoutBtn");
logoutBtn.setAttribute("href", "/logout");
logoutBtn.onclick = function () {
    fetch('/logout', { method: 'POST', credentials: 'include' })
        .then(response => {
            if (response.ok) {
                // 세션 스토리지에서 토큰 제거
                window.sessionStorage.removeItem("jwtToken");
                console.log("로그아웃")
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