// HTML form 제출 이벤트 처리
$(function() {
    // HTML form 제출 이벤트 등록
    $('#login-form').on('submit', function(event) {
        // 기본 제출 동작 방지
        event.preventDefault();

        // form 데이터 수집
        var formData = {
            'id': $('#id').val(), // id 필드의 값
            'pw': $('#pw').val()  // pw 필드의 값
        };

        // AJAX 요청
        $.ajax({
            type: 'POST',           // HTTP 요청 방식
            url: '/login-common',   // 요청 URL
            data: formData,         // 전송할 데이터
            dataType: 'json',       // 응답 데이터 형식
            encode: true            // 데이터 인코딩 여부
        })
            .done(function(response) {
                // 서버 응답 처리
                console.log(response);
                // 페이지 이동, 메시지 출력 등
            })
            .fail(function(response) {
                // AJAX 요청 실패 처리
                console.log(response);
                // 에러 메시지 출력, 로그인 재시도 등
            });
    });
});
