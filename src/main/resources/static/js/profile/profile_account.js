const jwtToken = sessionStorage.getItem("jwtToken");

const xhr = new XMLHttpRequest();
xhr.open("POST", "/profile/admin/account");
xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
xhr.setRequestHeader("Authorization", "Bearer " + jwtToken);
xhr.onload = function() {
    if (xhr.status === 200) {
        const responseData = JSON.parse(xhr.responseText);
        console.log(responseData);

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

// const xhr = new XMLHttpRequest();
// xhr.open('POST', '/profile/admin/account');
// xhr.setRequestHeader('Content-Type', 'application/json');
// xhr.setRequestHeader('Authorization', `Bearer ${token}`);
// xhr.send(JSON.stringify(data));


// window.onload = function() {
//     const xhr = new XMLHttpRequest();
//     xhr.open("POST", "/profile/admin/account");
//     xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
//     xhr.setRequestHeader("Authorization", "Bearer " + jwtToken);
//     xhr.onload = function() {
//         if (xhr.status === 200) {
//             // 응답이 성공한 경우, 응답 데이터를 파싱하여 값 추출
//             const responseData = JSON.parse(xhr.responseText);
//             const admin = responseData.admin;
//
//             // HTML 엘리먼트에 값을 설정하여 뷰에 표시
//             document.querySelector("input[name='id']").value = admin.id;
//             document.querySelector("input[name='fullname']").value = admin.name;
//             document.querySelector("input[name='num']").value = admin.num;
//             document.querySelector("input[name='email']").value = admin.email;
//             document.querySelector("input[name='phone']").value = admin.phone;
//         } else {
//             // 요청이 실패한 경우, 콘솔에 로그 출력
//             console.error('Request failed.  Returned status of ' + xhr.status);
//         }
//     };
//     xhr.send();
// };

