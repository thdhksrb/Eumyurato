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
    document.getElementById("common-id").value = common.id;
    document.getElementById("common-name").value = common.name;
    document.getElementById("common-nid").value = common.nid;
    document.getElementById("common-sex").value = common.sex;
    document.getElementById("common-birth").value = common.birth;
    document.getElementById("common-email").value = common.email;
    document.getElementById("common-phone").value = common.phone;
    document.getElementById("common-road").value = common.road;
    document.getElementById("common-genre").value = common.genre;

}

// 페이지 로드 시 관리자 정보를 가져옵니다.
window.onload = function () {
    getCommonData();
};