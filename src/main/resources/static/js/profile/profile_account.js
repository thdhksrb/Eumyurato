const jwtToken = sessionStorage.getItem("jwtToken");

const xhr = new XMLHttpRequest();
xhr.open("POST", "/profile/admin/account");
xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
xhr.setRequestHeader("Authorization", "Bearer " + jwtToken);
xhr.onload = function() {
    if (xhr.status === 200) {
        const responseData = JSON.parse(xhr.responseText);
        const admin = responseData.admin;

        // HTML 요소 선택 후 값 변경
        document.querySelector('input[name="id"]').value = admin.id;
        document.querySelector('input[name="fullname"]').value = admin.name;
        document.querySelector('input[name="num"]').value = admin.num;
        document.querySelector('input[name="email"]').value = admin.email;
        document.querySelector('input[name="phone"]').value = admin.phone;

    } else {
        console.error('Request failed.  Returned status of ' + xhr.status);
    }
};
xhr.send();

// 회원정보 조회 기능
function setCommonMemberSearch() {
    const CommonDropdown = document.getElementById("CommonDropdown");
    const searchValue = document.getElementById("searchValue");
    const column = document.getElementById("column");
    const keyword = document.getElementById("keyword");

    column.value = CommonDropdown.value;
    keyword.value = searchValue.value;

};

function resetCommonMemberList() {
    document.querySelector('form[action="/profile/admin/total/commSearch"]').reset();
    window.location.href = '/profile/admin/total';
};

function setArtMemberSearch() {
    const ArtistDropdown = document.getElementById("ArtistDropdown");
    const searchValue2 = document.getElementById("searchValue2");
    const column2 = document.getElementById("column2");
    const keyword2 = document.getElementById("keyword2");

    column2.value = ArtistDropdown.value;
    keyword2.value = searchValue2.value;

};

function resetArtMemberList() {
    document.querySelector('form[action="/profile/admin/total/artSearch"]').reset();
    window.location.href = '/profile/admin/total';
};


function setEntMemberSearch() {
    const EntDropdown = document.getElementById("EntDropdown");
    const searchValue3 = document.getElementById("searchValue3");
    const column3 = document.getElementById("column3");
    const keyword3 = document.getElementById("keyword3");

    column3.value = EntDropdown.value;
    keyword3.value = searchValue3.value;

};


function resetEntMemberList() {
    document.querySelector('form[action="/profile/admin/total/entSearch"]').reset();
    window.location.href = '/profile/admin/total';
};

document.getElementById("showCommons").addEventListener("click", function() {
    document.getElementById("commonMembers").style.display = "block";
    document.getElementById("artistMembers").style.display = "none";
    document.getElementById("enterpriseMembers").style.display = "none";

});

document.getElementById("showArtists").addEventListener("click", function() {
    document.getElementById("commonMembers").style.display = "none";
    document.getElementById("artistMembers").style.display = "block";
    document.getElementById("enterpriseMembers").style.display = "none";

});

document.getElementById("showEnterprises").addEventListener("click", function() {
    document.getElementById("commonMembers").style.display = "none";
    document.getElementById("artistMembers").style.display = "none";
    document.getElementById("enterpriseMembers").style.display = "block";

});


// 예약내역 조회 기능
// function setArtReservationsSearch() {
//     const searchArtReservationDropdown = document.getElementById("searchArtReservationDropdown");
//     const column = document.getElementById("column");
//     const keyword = document.getElementById("keyword");
//     const searchArtReservationValue = document.getElementById("searchArtReservationValue");
//
//     column.value = searchArtReservationDropdown.value;
//     keyword.value = searchArtReservationValue.value;
// };
//
// function resetReservations() {
//     window.location.href = "/profile/artist/reservation";
// };
