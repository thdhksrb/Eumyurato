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
        const uidInput = document.querySelector('input[name="id"]');
        const uidRegex = /^[a-zA-Z0-9]{5,20}$/;
        const uidEmptyFeedback = document.getElementById("id-empty");
        const uidLengthFeedback = document.getElementById("id-length");
        const uidAlphanumericFeedback = document.getElementById("id-alphanumeric");
        const uidAvailableFeedback = document.getElementById("id-available");
        const uidDuplicateFeedback = document.getElementById("id-duplicate");

        uidInput.addEventListener('blur', function () {
            const uidValue = uidInput.value.trim();

            if (uidValue === "") {
                uidEmptyFeedback.style.display = "block";
                uidLengthFeedback.style.display = "none";
                uidAlphanumericFeedback.style.display = "none";
                uidAvailableFeedback.style.display = "none";
                uidDuplicateFeedback.style.display = "none";
            } else if (!uidRegex.test(uidValue)) {
                uidEmptyFeedback.style.display = "none";
                uidLengthFeedback.style.display = "block";
                uidAlphanumericFeedback.style.display = "none";
                uidAvailableFeedback.style.display = "none";
                uidDuplicateFeedback.style.display = "none";
            } else {
                uidEmptyFeedback.style.display = "none";
                uidLengthFeedback.style.display = "none";
                uidAlphanumericFeedback.style.display = "none";

                // 아이디 중복 검사 버튼 이벤트 핸들러
                const duplicateBtn1 = document.querySelector("#duplicateBtn1");
                duplicateBtn1.addEventListener("click", function (event) {
                    event.preventDefault();

                    const idInput = document.getElementById("id");
                    const idValue = idInput.value.trim();
                    if (idValue !== "") {
                        // 서버로 중복 검사 요청
                        fetch(`/checkIdDuplicate/${idValue}`)
                            .then(response => response.json())
                            .then(data => {
                                if (data.duplicate) {
                                    uidAvailableFeedback.style.display = "none";
                                    uidDuplicateFeedback.style.display = "block";
                                    document.getElementById("id-available").style.color = "green";
                                } else {
                                    uidAvailableFeedback.style.display = "block";
                                    document.getElementById("id-available").style.color = "green";
                                    uidDuplicateFeedback.style.display = "none";
                                }
                            })
                            .catch(error => console.error(error));
                    }
                });
            }
        });


// 비밀번호 유효성 검사
        const pwdInput = document.querySelector('input[name="pwd"]');
        const pwdRegex = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,16}$/;
        pwdInput.addEventListener("blur", function () {
            const pwdValue = pwdInput.value.trim();

            if (pwdValue === "") {
                document.getElementById("password-empty").style.display = "block";
            } else if (!pwdRegex.test(pwdValue)) {
                document.getElementById("password-invalid").style.display = "block";
                document.getElementById("password-empty").style.display = "none";
            } else {
                document.getElementById("password-empty").style.display = "none";
                document.getElementById("password-invalid").style.display = "none";
            }
        });

// 비밀번호 확인 유효성 검사
        const cpasswordInput = document.querySelector('input[name="cpassword"]');
        cpasswordInput.addEventListener("input", function () {
            const pwdValue = pwdInput.value.trim();
            const cpasswordValue = cpasswordInput.value.trim();

            if (pwdValue !== cpasswordValue) {
                document.getElementById("cpassword-mismatch").style.display = "block";
                document.getElementById("cpassword-match").style.display = "none";
            } else {
                document.getElementById("cpassword-match").style.display = "block";
                document.getElementById("cpassword-match").style.color = "green";
                document.getElementById("cpassword-mismatch").style.display = "none";

            }
        });

        // 이름 유효성 검사
        const nameInput = document.querySelector('input[name="name"]');
        const nameRegex = /^[가-힣]{1,20}$/u; // 이름 정규식 패턴
        nameInput.addEventListener('blur', function (event) {
            const nameValue = nameInput.value.trim();


            if (nameValue === "") {
                document.getElementById("name-empty").style.display = "block";
                document.getElementById("name-invalid").style.display = "none";
                document.getElementById("name-vowel").style.display = "none";
            } else if (!/^[^A-Za-z]*$/.test(nameValue)) {
                document.getElementById("name-vowel").style.display = "block";
                document.getElementById("name-empty").style.display = "none";
                document.getElementById("name-invalid").style.display = "none";
            } else if (!nameRegex.test(nameValue)) {
                document.getElementById("name-invalid").style.display = "block";
                document.getElementById("name-empty").style.display = "none";
                document.getElementById("name-vowel").style.display = "none";
            } else {
                document.getElementById("name-empty").style.display = "none";
                document.getElementById("name-invalid").style.display = "none";
                document.getElementById("name-vowel").style.display = "none";
            }
        });


        // 이메일 유효성 검사
        const emailInput = document.querySelector('input[name="email"]');
        const emailRegex = /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{1,30}$/;
        emailInput.addEventListener('blur', function (event) {
            const emailValue = emailInput.value.trim();
            if (emailValue === "") {
                // 이메일이 빈 문자열일 경우
                document.getElementById("email-empty").style.display = "block";
                document.getElementById("email-invalid").style.display = "none";
            } else if (!emailRegex.test(emailValue)) {
                // 이메일 형식이 올바르지 않을 경우
                document.getElementById("email-invalid").style.display = "block";
                document.getElementById("email-empty").style.display = "none";
            } else {
                document.getElementById("email-empty").style.display = "none";
                document.getElementById("email-invalid").style.display = "none";
            }
        });

        // 휴대폰 유효성 검사
        const phoneInput = document.querySelector('input[name="phone"]');
        const phoneRegex = /^\d{1,20}$/;
        phoneInput.addEventListener('blur', function (event) {
            const phoneValue = phoneInput.value.trim();

            if (phoneValue === "") {
                // 휴대폰 번호가 빈 문자열일 경우
                document.getElementById("num1-empty").style.display = "block";
                document.getElementById("num1-invalid").style.display = "none";
            } else if (!phoneRegex.test(phoneValue)) {
                // 휴대폰 번호 형식이 올바르지 않을 경우
                document.getElementById("num1-invalid").style.display = "block";
                document.getElementById("num1-empty").style.display = "none";
            } else {
                document.getElementById("num1-empty").style.display = "none";
                document.getElementById("num1-invalid").style.display = "none";
            }
        });

        // 사업자등록번호 유효성 검사
        const numInput = document.querySelector('input[name="num"]');
        const numRegex = /^\d{1,20}$/;
        numInput.addEventListener('blur', function (event) {
            const numValue = numInput.value.trim();

            if (numValue === "") {
                // 휴대폰 번호가 빈 문자열일 경우
                document.getElementById("num1-empty").style.display = "block";
                document.getElementById("num1-invalid").style.display = "none";
            } else if (!numRegex.test(numValue)) {
                // 휴대폰 번호 형식이 올바르지 않을 경우
                document.getElementById("num1-invalid").style.display = "block";
                document.getElementById("num1-empty").style.display = "none";
            } else {
                document.getElementById("num1-empty").style.display = "none";
                document.getElementById("num1-invalid").style.display = "none";
            }
        });


        // 약관 동의 체크박스 유효성 검사
        const agreeCheckbox = document.getElementById("chk_agree");
        const agreeCheckboxCheck = document.getElementById("chk_agree-invalid");

        agreeCheckbox.addEventListener("input", function () {
            if (!agreeCheckbox.checked) {
                agreeCheckboxCheck.style.display = "block";
                return false;
            } else {
                agreeCheckboxCheck.style.display = "none";
            }
        });
    });
})();


// 회원가입 이벤트 핸들러
const joinButton = document.getElementById('joinButton');
joinButton.addEventListener('click', async function (e) {
    event.preventDefault();
    const form = document.getElementById('myForm');

    // 유효성 검사
    if (!form.checkValidity()) {
        form.classList.add('was-validated');
        $('#error_modal').modal('show');
        return;
    }

    const formData = new FormData(form);

    try {
        const response = await fetch('/loginjoin/enterprise/join', {
            method: 'POST',
            body: formData
        });

        if (response.ok) {
            // 회원가입 성공 시 모달 팝업 띄우기
            $('#result_message').text(resultMessage);
            $('#result_modal').modal('show');
            setTimeout(function () {
                window.location.href = "/loginjoin/enterprise/login";
            }, 3000); // 3초 후 로그인 페이지로 이동
        } else {
            // 회원가입 실패 시 모달 팝업 띄우기
            $('#error_message').text(errorMessage);
            $('#error_modal').modal('show');
        }
    } catch (error) {
        // 회원가입 실패 시 모달 팝업 띄우기
        $('#error_message').text(errorMessage);
        $('#error_modal').modal('show');
    }
});
