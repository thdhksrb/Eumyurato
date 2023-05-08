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
}

// 페이지 로드 시 관리자 정보를 가져옵니다.
window.onload = function () {
    getArtistData();
};