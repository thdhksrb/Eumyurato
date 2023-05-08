const jwtToken = sessionStorage.getItem("jwtToken");

function getAdminData() {
    const xhr = new XMLHttpRequest();
    xhr.open("GET", "/profile/admin/data");
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.setRequestHeader("Authorization", `Bearer ${jwtToken}`);

    xhr.onload = function () {
        if (xhr.status === 200) {
            const admin = JSON.parse(xhr.responseText);
            displayAdminData(admin);
        } else {
            console.log("Request failed. Returned status of " + xhr.status);
        }
    };

    xhr.send();
}

function displayAdminData(admin) {
    document.getElementById("admin-id").value = admin.id;
    document.getElementById("admin-name").value = admin.name;
    document.getElementById("admin-num").value = admin.num;
    document.getElementById("admin-email").value = admin.email;
    document.getElementById("admin-phone").value = admin.phone;
    // 이미지 URL을 가져온다.
    var imageUrl = admin.image;

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
        var replacedImageUrl = imageUrl.replace(/\\/g, "/").replace("src/main/resources/static", "");
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

// 페이지 로드 시 관리자 정보를 가져옵니다.
window.onload = function () {
    getAdminData();
};

