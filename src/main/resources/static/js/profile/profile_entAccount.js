const jwtToken = sessionStorage.getItem("jwtToken");

function getEntData() {
    const xhr = new XMLHttpRequest();
    xhr.open("GET", "/profile/ent/data");
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.setRequestHeader("Authorization", `Bearer ${jwtToken}`);

    xhr.onload = function () {
        if (xhr.status === 200) {
            const enterprise = JSON.parse(xhr.responseText);
            displayEntData(enterprise);
        } else {
            console.log("Request failed. Returned status of " + xhr.status);
        }
    };

    xhr.send();
}

function displayEntData(enterprise) {
    document.getElementById("enterprise-id").value = enterprise.id;
    document.getElementById("enterprise-name").value = enterprise.name;
    document.getElementById("enterprise-num").value = enterprise.num;
    document.getElementById("enterprise-email").value = enterprise.email;
    document.getElementById("enterprise-phone").value = enterprise.phone;

}

// 페이지 로드 시 관리자 정보를 가져옵니다.
window.onload = function () {
    getEntData();
};