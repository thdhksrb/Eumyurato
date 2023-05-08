const jwtToken = sessionStorage.getItem("jwtToken");

function getArtistReservationList() {
    const xhr = new XMLHttpRequest();
    xhr.open("GET", "/profile/artist/reservation/data");
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.setRequestHeader("Authorization", `Bearer ${jwtToken}`);

    xhr.onload = function () {
        if (xhr.status === 200) {
            const reservations = JSON.parse(xhr.responseText);
            displayReservations(reservations);
        } else {
            console.log("Request failed. Returned status of " + xhr.status);
        }
    };

    xhr.send();
}

function displayReservations(reservations) {
    const tableBody = document.querySelector(".table tbody");
    tableBody.innerHTML = '';

    reservations.forEach(reservation => {
        const row = document.createElement("tr");
        for (let key in reservation) {
            const cell = document.createElement("td");
            cell.textContent = reservation[key];
            row.appendChild(cell);
        }
        tableBody.appendChild(row);
    });
}

// 페이지 로드 시 예약 목록을 가져옵니다.
window.onload = function () {
    getArtistReservationList();
};

function setReservationsSearch() {
    const searchReservationDropdown = document.getElementById("searchReservationDropdown");
    const column = document.getElementById("column");
    const keyword = document.getElementById("keyword");
    const searchReservationValue = document.getElementById("searchReservationValue");

    column.value = searchReservationDropdown.value;
    keyword.value = searchReservationValue.value;
};

function resetArtReservations() {
    window.location.href = "/profile/artist/reservation";
};