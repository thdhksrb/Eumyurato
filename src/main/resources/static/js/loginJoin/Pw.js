$(function() {
    $('#kt_login_signin_form').submit(function(e) {
        e.preventDefault(); // 기본 동작 중단

        var id = $('[name="id"]').val();
        var name = $('[name="name"]').val();
        var email = $('[name="email"]').val();

        $.ajax({
            type: "POST",
            url: "/loginjoin/Pwfind",
            data: {
                id: id,
                name: name,
                email: email
            },
            success: function(response) {
                // 비밀번호 찾기 성공 시 처리
                $('#result_message').text('임시 비밀번호가 이메일로 발송되었습니다. 확인해주세요.');
                $('#result_modal').modal('show');
            },
            error: function(xhr, status, error) {
                // 비밀번호 찾기 실패 시 처리
                var errorMessage = xhr.responseJSON.errorMessage;
                $('#error_message').text(errorMessage);
                $('#error_modal').modal('show');

                // 서버 오류가 발생한 경우
                if (!xhr.responseJSON) {
                    $('#error_message').text('서버에서 오류가 발생했습니다.');
                    $('#error_modal').modal('show');
                }
            },
            complete: function(xhr, status) {
                // 요청이 완료되면 로그인 페이지로 이동합니다.
                window.location.href = "/loginjoin/common/login";
            }
        });
    });
});
