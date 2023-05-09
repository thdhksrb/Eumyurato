//우편번호 API
function sample4_execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 도로명 주소의 노출 규칙에 따라 주소를 표시한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var roadAddr = data.roadAddress; // 도로명 주소 변수
            var extraRoadAddr = ''; // 참고 항목 변수

            // 법정동명이 있을 경우 추가한다. (법정리는 제외)
            // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
            if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                extraRoadAddr += data.bname;
            }
            // 건물명이 있고, 공동주택일 경우 추가한다.
            if(data.buildingName !== '' && data.apartment === 'Y'){
                extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
            }
            // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
            if(extraRoadAddr !== ''){
                extraRoadAddr = ' (' + extraRoadAddr + ')';
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('sample4_postcode').value = data.zonecode;
            document.getElementById("sample4_roadAddress").value = roadAddr;

            // 참고항목 문자열이 있을 경우 해당 필드에 넣는다.
            if(roadAddr !== ''){
                document.getElementById("sample4_extraAddress").value = extraRoadAddr;
            } else {
                document.getElementById("sample4_extraAddress").value = '';
            }

            var guideTextBox = document.getElementById("guide");
            // 사용자가 '선택 안함'을 클릭한 경우, 예상 주소라는 표시를 해준다.
            if(data.autoRoadAddress) {
                var expRoadAddr = data.autoRoadAddress + extraRoadAddr;
                guideTextBox.innerHTML = '(예상 도로명 주소 : ' + expRoadAddr + ')';
                guideTextBox.style.display = 'block';

            } else if(data.autoJibunAddress) {
                var expJibunAddr = data.autoJibunAddress;
                guideTextBox.innerHTML = '(예상 지번 주소 : ' + expJibunAddr + ')';
                guideTextBox.style.display = 'block';
            } else {
                guideTextBox.innerHTML = '';
                guideTextBox.style.display = 'none';
            }
        }
    }).open({
        autoClose: true
    });
}

$(document).ready(function() {
    getArtistData();

    //프로필 이미지 사용자가 업로드한 이미지로 변경
    var defaultImage = '/img/buskingDefaultImg.jpg';
    $('#previewImage').attr('src', defaultImage);
    $('input[type="file"]').on('change', function(event) {
        var file = event.target.files[0];
        var imageUrl = URL.createObjectURL(file);
        $('#previewImage').attr('src', imageUrl);
    });

    //등록 버튼 클릭 시 실행
    $('#buskingRegister').on('click', function(event) {
        event.preventDefault(); // 기본 동작(페이지 이동) 방지
        clickCheck();
        buskingRegister(); // 함수 실행
    });

    //날짜 형식 맞는지 검사
    $('#buskingDate').on('change', function() {
        var inputValue = $(this).val(); // buskingDate 입력 필드에 입력된 값 가져오기
        var pattern = /^\d{4}-\d{2}-\d{2} \d{2}:\d{2}$/; // 검증할 정규식 패턴
        var isValid = pattern.test(inputValue); // 입력 값이 정규식 패턴과 일치하는지 검증

        if (!isValid) {
            alert('올바른 형식으로 입력해주세요. (yyyy-MM-dd HH:mm)');
            $(this).val('');
        } else {
            var inputDate = new Date(inputValue); // 입력된 값으로부터 Date 객체 생성
            var now = new Date(); // 현재 시간을 나타내는 Date 객체 생성

            if (inputDate <= now) { // 입력된 값이 현재 시간보다 이전인 경우
                alert('현재 이후의 날짜와 시간을 입력해주세요.');
                $(this).val('');
            }
        }
    });
});

const jwtToken = sessionStorage.getItem("jwtToken");
var artistId;
//아티스트 이름(공연자) 가져오기
function getArtistData() {
    const xhr = new XMLHttpRequest();
    xhr.open("GET", "/profile/artist/data");
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.setRequestHeader("Authorization", `Bearer ${jwtToken}`);

    xhr.onload = function () {
        if (xhr.status === 200) {
            const artist = JSON.parse(xhr.responseText);
            console.log(artist);
            document.getElementById("nid").value = artist.nid;
            artistId = artist.id;
        } else {
            console.log("Request failed. Returned status of " + xhr.status);
        }
    };
    xhr.send();
}

//폼 데이터 넘기는 부분
function buskingRegister() {
    var formData = new FormData();
    var fileInput = document.querySelector('input[name="avatar"]');
    var file = fileInput.files[0];
    formData.append('imgFile', file);
    var data = {
        name: $("input[name='name']").val(),
        location: $("input[name='sample4_roadAddress']").val(),
        date: $("input[name='buskingDate']").val(),
        artId : artistId
    };
    formData.append('registerDTO', new Blob([JSON.stringify(data)], { type: 'application/json' }));
    console.log(formData);
    $.ajax({
        type: 'POST',
        url: '/profile/artist/register',
        data: formData,
        processData: false,
        contentType: false,
        success: function() {
            alert('버스킹 등록 성공');
            window.location.href = '/profile/artist/management/view';
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(textStatus + ': ' + errorThrown);
        }
    });
}

//input 필수값 받기
function clickCheck(){
    var name = $("input[name='name']").val();
    var location = $("input[name='sample4_roadAddress']").val();
    var buskingDate = $("input[name='buskingDate']").val();

    // 값이 비어있을 경우 경고창 출력
    if (!name) {
        alert("공연명은 필수 입력입니다.");
        event.preventDefault();
        return;
    }
    if (!location) {
        alert("주소는 필수 입력입니다.");
        event.preventDefault();
        return;
    }
    if (!buskingDate) {
        alert("공연 일자는 필수 입력입니다.");
        event.preventDefault();
        return;
    }
}
