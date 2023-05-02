
// 회원정보 조회 기능
function setMemberSearch() {
    const MemberDropdown = document.getElementById("MemberDropdown");
    const searchValue = document.getElementById("searchValue");
    const column = document.getElementById("column");
    const keyword = document.getElementById("keyword");

    column.value = MemberDropdown.value;
    keyword.value = searchValue.value;

};

function resetMemberList() {
    document.querySelector('form[action="/profile/admin/total/search"]').reset();
    window.location.href = '/profile/admin/total';
};

function setEntMemberSearch() {
    const EntDropdown = document.getElementById("EntDropdown");
    const searchValue2 = document.getElementById("searchValue2");
    const column2 = document.getElementById("column2");
    const keyword2 = document.getElementById("keyword2");

    column2.value = EntDropdown.value;
    keyword2.value = searchValue2.value;

};


function resetEntMemberList() {
    document.querySelector('form[action="/profile/admin/total/entSearch"]').reset();
    window.location.href = '/profile/admin/total';
};

document.getElementById("showCommonsAndArtists").addEventListener("click", function() {
    document.getElementById("commonAndArtistMembers").style.display = "block";
    document.getElementById("enterpriseMembers").style.display = "none";

});

document.getElementById("showEnterprises").addEventListener("click", function() {
    document.getElementById("commonAndArtistMembers").style.display = "none";
    document.getElementById("enterpriseMembers").style.display = "block";

});

// 예약내역 조회 기능
function setReservationsSearch() {
    const searchReservationDropdown = document.getElementById("searchReservationDropdown");
    const column = document.getElementById("column");
    const keyword = document.getElementById("keyword");
    const searchReservationValue = document.getElementById("searchReservationValue");

    column.value = searchReservationDropdown.value;
    keyword.value = searchReservationValue.value;
};

function resetReservations() {
    window.location.href = "/profile/admin/reservation";
};
