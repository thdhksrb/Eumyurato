(function () {
    'use strict';

    // Fetch all the forms we want to apply custom Bootstrap validation styles to
    var forms = document.querySelectorAll('.needs-validation');

    // Loop over them and prevent submission
    Array.prototype.slice.call(forms).forEach(function (form) {
        form.addEventListener('submit', function (event) {
            if (!form.checkValidity()) {
                event.preventDefault();
                event.stopPropagation();
            }

            form.classList.add('was-validated');
        }, false);

        // 아이디 유효성 검사
        const idInput = document.querySelector('input[name="id"]');
        const idRegExp = /^[A-Za-z0-9]+$/;
        idInput.addEventListener('blur', function (event) {
            if (idInput.value.trim() === '') {
                idInput.classList.add('is-invalid');
                if (idInput.nextElementSibling !== null) {
                    idInput.nextElementSibling.style.display = 'block';
                    idInput.nextElementSibling.nextElementSibling.style.display = 'none';
                }
            } else if (!idRegExp.test(idInput.value)) {
                idInput.classList.add('is-invalid');
                if (idInput.nextElementSibling !== null) {
                    idInput.nextElementSibling.style.display = 'none';
                    idInput.nextElementSibling.nextElementSibling.style.display = 'block';
                }
            } else {
                idInput.classList.remove('is-invalid');
                if (idInput.nextElementSibling !== null) {
                    idInput.nextElementSibling.style.display = 'none';
                    idInput.nextElementSibling.nextElementSibling.style.display = 'none';
                }
            }
        });

        // 이름 유효성 검사
        const nameInput = document.querySelector('input[name="name"]');
        const nameRegExp = /^[가-힣]+$/;
        nameInput.addEventListener('blur', function (event) {
            if (nameInput.value.trim() === '') {
                nameInput.classList.add('is-invalid');
                if (nameInput.nextElementSibling !== null) {
                    nameInput.nextElementSibling.style.display = 'block';
                    nameInput.nextElementSibling.nextElementSibling.style.display = 'none';
                }
            } else if (!nameRegExp.test(nameInput.value)) {
                nameInput.classList.add('is-invalid');
                if (nameInput.nextElementSibling !== null) {
                    nameInput.nextElementSibling.style.display = 'none';
                    nameInput.nextElementSibling.nextElementSibling.style.display = 'block';
                }
            } else {
                nameInput.classList.remove('is-invalid');
                if (nameInput.nextElementSibling !== null) {
                    nameInput.nextElementSibling.style.display = 'none';
                    nameInput.nextElementSibling.nextElementSibling.style.display = 'none';
                }
            }
        });

        // 이메일 유효성 검사
        const emailInput = document.querySelector('input[name="email"]');
        const emailRegExp = /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/;
        emailInput.addEventListener('blur', function (event) {
            if (emailInput.value.trim() === '') {
                emailInput.classList.add('is-invalid');
                if (emailInput.nextElementSibling !== null) {
                    emailInput.nextElementSibling.style.display = 'block';
                    emailInput.nextElementSibling.nextElementSibling.style.display = 'none';
                }
            } else if (!emailRegExp.test(emailInput.value)) {
                emailInput.classList.add('is-invalid');
                if (emailInput.nextElementSibling !== null) {
                    emailInput.nextElementSibling.style.display = 'none';
                    emailInput.nextElementSibling.nextElementSibling.style.display = 'block';
                }
            } else {
                emailInput.classList.remove('is-invalid');
                if (emailInput.nextElementSibling !== null) {
                    emailInput.nextElementSibling.style.display = 'none';
                    emailInput.nextElementSibling.nextElementSibling.style.display = 'none';
                }
            }
        });
    });
})();


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
                var resultMessage = response.message;
                $('#result_message').text(resultMessage);
                $('#result_modal').modal('show');
                setTimeout(function() {
                    window.location.href = "/loginjoin/common/login";
                }, 3000); // 3초 후 로그인 페이지로 이동
            },
            error: function(xhr) {
                // 비밀번호 찾기 실패 시 처리
                var errorMessage = xhr.responseJSON.errorMessage;
                $('#error_message').text(errorMessage);
                $('#error_modal').modal('show');
            },
        });
    });
});
