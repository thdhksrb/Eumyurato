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
}

// 페이지 로드 시 관리자 정보를 가져옵니다.
window.onload = function () {
    getAdminData();
};