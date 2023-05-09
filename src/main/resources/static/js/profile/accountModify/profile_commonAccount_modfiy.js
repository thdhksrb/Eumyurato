const jwtToken = sessionStorage.getItem("jwtToken");

$(document).ready(function() {
    getCommonData(); //정보 불러옴

    //프로필 이미지 사용자가 업로드한 이미지로 변경
    $('input[type="file"]').on('change', function(event) {
        var file = event.target.files[0];
        var imageUrl = URL.createObjectURL(file);
        $('#previewImage').attr('src', imageUrl);
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

    //닉네임 중복 검사
    $('#nid').on('change',function (){
        nidCheck();
   });

    //수정 버튼
    $('#modify').on('click', function(event) {
        event.preventDefault(); // 기본 동작(페이지 이동) 방지
        clickCheck();
        commonModify(); // 데이터 보내는 함수 실행
    });
});

//정보 불러오기
function getCommonData() {
    const xhr = new XMLHttpRequest();
    xhr.open("GET", "/profile/common/data");
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.setRequestHeader("Authorization", `Bearer ${jwtToken}`);

    xhr.onload = function () {
        if (xhr.status === 200) {
            const common = JSON.parse(xhr.responseText);
            displayCommonData(common);
        } else {
            console.log("Request failed. Returned status of " + xhr.status);
        }
    };

    xhr.send();
}

var comId;

//정보 input창에 띄워주기
function displayCommonData(common) {
    document.getElementById("id").value = common.id;
    document.getElementById("pwd").value = common.pwd;
    document.getElementById("name").value = common.name;
    document.getElementById("nid").value = common.nid;
    document.getElementById("email").value = common.email;
    document.getElementById("phone").value = common.phone;
    document.getElementById("genre").value = common.genre;

    comId = common.id;
    console.log(comId);

// 이미지 URL을 가져온다.
    var imageUrl = common.image;

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
function commonModify() {
    var formData = new FormData();
    var fileInput = document.querySelector('input[name="avatar"]');
    var file = fileInput.files[0];
    formData.append('imgFile', file);
    var data = {
        id: comId,
        pwd: $("input[name='pwd']").val(),
        nid: $("input[name='nid']").val(),
        email: $("input[name='email']").val(),
        phone: $("input[name='phone']").val(),
        genre: $("select[name='genre']").val()
    };

    formData.append('commonDTO', new Blob([JSON.stringify(data)], { type: 'application/json' }));
    console.log(formData);
    $.ajax({
        type: 'POST',
        url: '/profile/common/modify',
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

//비밀번호 재확인 비어있는지 확인
function clickCheck(){
    var cpassword = $("input[name='cpassword']").val();

    // 값이 비어있을 경우 경고창 출력
    if (!cpassword) {
        alert("비밀번호 재확인이 필요합니다.");
        event.preventDefault();
        return;
    }
}

//닉네임 팝업창
function openNidPopup() {
    var _width = '500';
    var _height = '200';
    var _left = Math.ceil(( window.screen.width - _width )/2);
    var _top = Math.ceil(( window.screen.height - _height )/2);

    // 팝업창 생성
    const popup = window.open('', '닉네임 변경하기', 'width='+ _width +', height='+ _height +', left=' + _left + ', top='+ _top );

    const html = `
    <html lang="en">
<head>
    <title>닉네임 변경하기</title>
</head>
<body>
<div style="display:flex; justify-content:center; align-items:center; flex-direction:column;">
    <h2>닉네임 변경</h2>
    <input type="text" name="nickname" placeholder="공백 및 특수문자 제외 20자 이하"
           style="width: 60%; padding: 10px; margin-bottom: 10px; border-radius: 5px; border: 1px solid #0099ff;">
    <br>
    <button id="submit-btn" style="padding: 10px 20px; background-color: #294ba1; color: white; border: none; border-radius: 5px;">변경</button>
</div>
<script>
document.getElementById("submit-btn").addEventListener("click", function() {
opener.document.getElementById("nid").value = document.querySelector('input[name="nickname"]').value;
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
        url: '/profile/common/nidcheck',
        data: { nid: nickname },
        success: function(response) {
            console.log(response);
            if (response === 'available') {
                alert('사용 가능한 닉네임입니다.');
            } else if (response === 'duplicate') {
                alert('이미 사용 중인 닉네임입니다.');
                nickname = '';
            } else {
                alert('오류가 발생했습니다.');
            }
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
                
                opener.document.getElementById("phone").value = phoneNumber;
                window.close();
            });
            </script>
        </body>
    </html>
`;
    popup.document.write(htmlCode);
    // 팝업창 스타일 지정
    popup.document.body.style.backgroundColor = '#f5f5f5'; // 배경색 지정
}
