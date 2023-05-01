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

                // 유효성 검사 추가
                const idInput = document.getElementById("id");
                const idRegExp = /^[A-Za-z0-9]+$/;

                if (idInput.value.trim() === '') {
                    event.preventDefault();
                    event.stopPropagation();
                    idInput.classList.add('is-invalid');
                    idInput.nextElementSibling.style.display = 'none';
                    idInput.nextElementSibling.nextElementSibling.style.display = 'block';
                } else if (!idRegExp.test(idInput.value)) {
                    event.preventDefault();
                    event.stopPropagation();
                    idInput.classList.add('is-invalid');
                    idInput.nextElementSibling.style.display = 'none';
                    idInput.nextElementSibling.nextElementSibling.style.display = 'block';
                } else {
                    idInput.classList.remove('is-invalid');
                    idInput.nextElementSibling.style.display = 'none';
                    idInput.nextElementSibling.nextElementSibling.style.display = 'none';
                }

                form.classList.add('was-validated')
            }, false)
        })
})();


//로그인 실패시 팝업
$(document).ready(function(){
    const loginError = sessionStorage.getItem("loginError");
    if (loginError) {
        alert(loginError);
        sessionStorage.removeItem("loginError");
    }
});