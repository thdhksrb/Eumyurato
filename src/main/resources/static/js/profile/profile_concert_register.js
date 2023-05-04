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
            document.getElementById("sample4_extraAddress").value = '';

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
    }).open();
}

$(document).ready(function() {
    var defaultImage = '/img/default.jpg';
    $('#previewImage').attr('src', defaultImage);
    $('input[type="file"]').on('change', function(event) {
        var file = event.target.files[0];
        var imageUrl = URL.createObjectURL(file);
        $('#previewImage').attr('src', imageUrl);
    });
    $('#concertRegister').on('click', function(event) {
        event.preventDefault(); // 기본 동작(페이지 이동) 방지
        concertRegister(); // 함수 실행
    });
});

function concertRegister() {
    var formData = new FormData();
    var fileInput = document.querySelector('input[name="avatar"]');
    var file = fileInput.files[0];
    formData.append('imgFile', file);
    var data = {
        name: $("input[name='name']").val(),
        pname: $("input[name='pname']").val(),
        location: $("input[name='sample4_roadAddress']").val(),
        enterId: $("input[name='enterId']").val(),
        startDate: $("input[name='startDate']").val(),
        lastDate: $("input[name='lastDate']").val(),
        price: $("input[name='price']").val()
    };
    formData.append('registerDTO', new Blob([JSON.stringify(data)], { type: 'application/json' }));
    console.log(formData);
    $.ajax({
        type: 'POST',
        url: '/profile/admin/register',
        data: formData,
        processData: false,
        contentType: false,
        success: function() {
            alert('공연등록 성공');
            window.location.href = '/profile/admin/management/view';
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(textStatus + ': ' + errorThrown);
        }
    });
}



