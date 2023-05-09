const jwtToken = sessionStorage.getItem("jwtToken");

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

function displayCommonData(common) {
    document.getElementById("id").value = common.id;
    document.getElementById("pwd").value = common.pwd;
    document.getElementById("name").value = common.name;
    document.getElementById("nid").value = common.nid;
    document.getElementById("email").value = common.email;
    document.getElementById("phone").value = common.phone;

// 이미지 URL을 가져온다.
    var imageUrl = common.image;

    if (imageUrl !== null && imageUrl.startsWith("https://")) {
        // 이미지 요소를 생성한다.
        var img = document.createElement("img");
        img.src = imageUrl;
        img.style.objectFit = "contain";
        img.style.width = "100%";
        img.style.height = "100%";

        // 이미지 요소를 포함할 div를 찾는다.
        var profileImg = document.getElementById("profileImg");

        // div에 이미지 요소를 추가한다.
        profileImg.appendChild(img);
    } else if(imageUrl !== null && !imageUrl.startsWith("https://")) {
        var replacedImageUrl = 'https://storage.googleapis.com/eumyurato/' + imageUrl;
        console.log(replacedImageUrl);
        // 이미지 요소를 생성한다.
        var img = document.createElement("img");
        img.src = replacedImageUrl;
        img.style.objectFit = "contain";
        img.style.width = "100%";
        img.style.height = "100%";

        // 이미지 요소를 포함할 div를 찾는다.
        var profileImg = document.getElementById("profileImg");

        // div에 이미지 요소를 추가한다.
        profileImg.appendChild(img);
    }else{
        // 이미지 요소를 생성한다.
        var img = document.createElement("img");
        img.src = "/img/default.jpg";
        img.style.objectFit = "contain";
        img.style.width = "100%";
        img.style.height = "100%";

        // 이미지 요소를 포함할 div를 찾는다.
        var profileImg = document.getElementById("profileImg");

        // div에 이미지 요소를 추가한다.
        profileImg.appendChild(img);
    }
}
// 회원 정보 수정 폼 제출 이벤트 핸들러
document.getElementById("kt_login_signin_form").addEventListener("submit", function (event) {
    event.preventDefault(); // 기본 동작 중단

    // 수정된 정보 가져오기
    const pwd = document.getElementById("pwd").value;
    const nid = document.getElementById("nid").value;
    const email = document.getElementById("email").value;
    const phone = document.getElementById("phone").value;

    // 서버로 전송할 데이터 생성
    const data = {
        pwd: pwd,
        nid: nid,
        email: email,
        phone: phone,
    };

    // 서버로 데이터 전송
    const xhr = new XMLHttpRequest();
    xhr.open("PUT", "/profile/common");
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.setRequestHeader("Authorization", `Bearer ${jwtToken}`);
    xhr.onload = function () {
        if (xhr.status === 200) {
            alert("회원 정보가 수정되었습니다.");
        } else {
            alert("회원 정보 수정에 실패했습니다.");
        }
    };
    xhr.send(JSON.stringify(data));
});

function concertRegister() {
    var formData = new FormData();
    var fileInput = document.querySelector('input[name="avatar"]');
    var file = fileInput.files[0];
    formData.append('imgFile', file);
    var data = {
        name: $("input[name='name']").val(),
        pname: $("input[name='pname']").val(),
        location: $("input[name='sample4_roadAddress']").val(),
        enterId: $("input[name='enterId']").val(),
        startDate: $("input[name='startDate']").val(),
        lastDate: $("input[name='lastDate']").val(),
        price: $("input[name='price']").val()
    };

    formData.append('registerDTO', new Blob([JSON.stringify(data)], { type: 'application/json' }));
    console.log(formData);
    $.ajax({
        type: 'POST',
        url: '/profile/admin/register',
        data: formData,
        processData: false,
        contentType: false,
        success: function() {
            alert('공연등록 성공');
            window.location.href = '/profile/admin/management/view';
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(textStatus + ': ' + errorThrown);
        }
    });
}



window.onload = function () {
    getCommonData();
};

