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
                const prevUrl = window.sessionStorage.getItem("prevUrl");
                window.sessionStorage.removeItem("prevUrl");
                form.setAttribute('action', '/login-common?prevUrl=' + encodeURIComponent(prevUrl));



                form.classList.add('was-validated')
            }, false)

            // 입력창에서 포커스가 빠져나갈 때마다 유효성 검사를 수행
            form.querySelectorAll('.form-control').forEach(function (input) {
                input.addEventListener('blur', function (event) {
                    // 유효성 검사 추가
                    const idRegExp = /^[A-Za-z0-9]+$/;

                    if (input.value.trim() === '') {
                        input.classList.add('is-invalid');
                        input.nextElementSibling.style.display = 'block';
                        input.nextElementSibling.nextElementSibling.style.display = 'none';
                    } else if (!idRegExp.test(input.value)) {
                        input.classList.add('is-invalid');
                        input.nextElementSibling.style.display = 'none';
                        input.nextElementSibling.nextElementSibling.style.display = 'block';
                    } else {
                        input.classList.remove('is-invalid');
                        input.nextElementSibling.style.display = 'none';
                        input.nextElementSibling.nextElementSibling.style.display = 'none';
                    }
                });
            });
        });
})();


//로그인 실패시 팝업
$(document).ready(function(){
    const loginError = sessionStorage.getItem("loginError");
    if (loginError) {
        alert(loginError);
        sessionStorage.removeItem("loginError");
    }
});

$(function () {
    // 알림창 닫기
    $('.alert .btn-close').click(function () {
        $(this).closest('.alert').fadeOut();
    });
});