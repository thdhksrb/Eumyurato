(function () {
    'use strict'

    // Fetch all the forms we want to apply custom Bootstrap validation styles to
    var forms = document.querySelectorAll('.needs-validation')

    // Loop over them and prevent submission
    Array.prototype.slice.call(forms)
        .forEach(function (form) {
            form.addEventListener('submit', function (event) {

                if (!form.checkValidity()) {
                    event.preventDefault()
                    event.stopPropagation()
                }

                form.classList.add('was-validated')
            }, false)

            // 입력창에서 포커스가 빠져나갈 때마다 유효성 검사를 수행
            form.querySelectorAll('.form-control').forEach(function (input) {
                input.addEventListener('blur', function (event) {
                    // 유효성 검사 추가
                    const idRegExp = /^[A-Za-z0-9]+$/;

                    if (input.value.trim() === '') {
                        input.classList.add('is-invalid');
                        if (input.nextElementSibling !== null) {
                            input.nextElementSibling.style.display = 'block';
                        }
                        if (input.nextElementSibling.nextElementSibling !== null) {
                            input.nextElementSibling.nextElementSibling.style.display = 'none';
                        }
                    } else if (!idRegExp.test(input.value)) {
                        input.classList.add('is-invalid');
                        if (input.nextElementSibling !== null) {
                            input.nextElementSibling.style.display = 'none';
                        }
                        if (input.nextElementSibling.nextElementSibling !== null) {
                            input.nextElementSibling.nextElementSibling.style.display = 'block';
                        }
                    } else {
                        input.classList.remove('is-invalid');
                        if (input.nextElementSibling !== null) {
                            input.nextElementSibling.style.display = 'none';
                        }
                        if (input.nextElementSibling.nextElementSibling !== null) {
                            input.nextElementSibling.nextElementSibling.style.display = 'none';
                        }
                    }

                });
            });
        });
})();

function submitForm(event) {
    event.preventDefault(); // 제출 버튼의 기본 동작 막기

    var form = document.getElementById("kt_login_signin_form");
    var xhr = new XMLHttpRequest();

    xhr.open("POST", "/loginjoin/enterprise/login-token", true);

    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                const authHeader = xhr.getResponseHeader('Authorization');
                if (authHeader) {
                    const jwtToken = authHeader.substring(7);
                    console.log(jwtToken);
                    sessionStorage.setItem('jwtToken', jwtToken);
                    sessionStorage.removeItem("loginError");
                    window.location.href = '/home';
                } else {
                    console.error('Authorization header not found.');
                }
            } else if (xhr.status === 401) {
                // 로그인 실패
                const loginError = "아이디 또는 비밀번호를 확인하세요.";
                sessionStorage.setItem("loginError", loginError);
                const alertDiv = document.createElement('div');
                alertDiv.className = 'alert alert-danger alert-dismissible fade show mt-3';
                alertDiv.role = 'alert';
                alertDiv.innerHTML = `
                    <strong>로그인 실패!</strong> ${loginError}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                `;
                document.getElementById('kt_login_signin_form').insertAdjacentElement('beforebegin', alertDiv);

                // 팝업 닫기 버튼 등록
                alertDiv.querySelector('.btn-close').addEventListener('click', function () {
                    alertDiv.remove();
                });
            } else {
                // 폼 제출 실패
                console.error(xhr.statusText);
            }
        }
    };

    var formData = new FormData(form);
    xhr.send(formData);
}

/*
$(document).ready(function () {
    const loginError = sessionStorage.getItem("loginError");
    if (loginError) {
        alert(loginError);
        sessionStorage.removeItem("loginError");
    }
});
*/
