const jwtToken = sessionStorage.getItem("jwtToken");

$(document).ready(function() {
    getCommonData(); //정보 불러옴

    //프로필 이미지 사용자가 업로드한 이미지로 변경
    $('input[type="file"]').on('change', function(event) {
        var file = event.target.files[0];
        var imageUrl = URL.createObjectURL(file);
        $('#previewImage').attr('src', imageUrl);
    });

    $('#cpassword').on('change', function() {
        var cpassword = $("input[name='cpassword']").val();
        var pwd = $("input[name='pwd']").val();

        if (cpassword !== pwd) {
            alert("비밀번호가 다릅니다.");
            cpassword = '';
        }
    });

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
        var defaultImage = '/img/default.jpg';
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
        phone: $("input[name='phone']").val()
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

function clickCheck(){
    var cpassword = $("input[name='cpassword']").val();

    // 값이 비어있을 경우 경고창 출력
    if (!cpassword) {
        alert("비밀번호 재확인이 필요합니다.");
        event.preventDefault();
        return;
    }

}

