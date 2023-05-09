const jwtToken = sessionStorage.getItem("jwtToken");

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

function displayArtistData(artist) {
    document.getElementById("artist-id").value = artist.id;
    document.getElementById("artist-name").value = artist.name;
    document.getElementById("artist-nid").value = artist.nid;
    document.getElementById("artist-sex").value = artist.sex;
    document.getElementById("artist-birth").value = artist.birth;
    document.getElementById("artist-email").value = artist.email;
    document.getElementById("artist-phone").value = artist.phone;
    document.getElementById("artist-genre").value = artist.genre;
    document.getElementById("artist-point").value = artist.point;
    // 이미지 URL을 가져온다.
    var imageUrl = artist.image;

    if (imageUrl !== null && imageUrl.startsWith("https://")) {
        // 이미지 요소를 생성한다.
        var img = document.createElement("img");
        img.src = imageUrl;
        img.style.objectFit = "cover";
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
        img.style.objectFit = "cover";
        img.style.width = "100%";
        img.style.height = "100%";

        // 이미지 요소를 포함할 div를 찾는다.
        var profileImg = document.getElementById("profileImg");

        // div에 이미지 요소를 추가한다.
        profileImg.appendChild(img);
    }else{
        // 이미지 요소를 생성한다.
        var img = document.createElement("img");
        img.src = "/img/memberDefaultImg.jpg";
        img.style.objectFit = "cover";
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
    getArtistData();
};