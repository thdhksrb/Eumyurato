const form = document.querySelector("Form");
const uidInput = document.getElementById("uid");
const passwordInput = document.getElementById("password");
const cpasswordInput = document.getElementById("cpassword");
const fullnameInput = document.getElementById("fullname");
const nidInput = document.getElementById("nid");
const birYY = document.getElementById("yy");
const birMM = document.getElementById("mm");
const birDD = document.getElementById("dd");
const sexRadio1 = document.getElementById("sexRadio1");
const sexRadio2 = document.getElementById("sexRadio2");
const emailInput = document.getElementById("email");
const num1Input = document.getElementById("num1");
const num2Input = document.getElementById("num2");

form.addEventListener("submit", function (event) {
    event.preventDefault(); // 폼 제출 이벤트를 취소하여 새로고침을 막음

    const uidValue = uidInput.value.trim();
    const passwordValue = passwordInput.value.trim();
    const cpasswordValue = cpasswordInput.value.trim();
    const fullnameValue = fullnameInput.value.trim();
    const nidvalue = nidInput.value.trim();
    const emailValue = emailInput.value.trim();
    const num1Value = num1Input.value.trim();
    const num2Value = num2Input.value.trim();
    const uidRegex = /^(?=.*[a-zA-Z])[a-zA-Z0-9]{5,20}$/;
    const passwordRegex = /^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!])(?=\S+$).{8,20}$/;
    const fullnameRegex = /^[가-힣]{1,20}$/u; // 이름 정규식 패턴
    const nidRegex = /^[가-힣]{1,20}$/u; // 이름 정규식 패턴

    //각각의 필드가 유효한 범위 내의 값을 입력했는지 확인합니다.
    const year = parseInt(birYY.value);
    const month = parseInt(birMM.value);
    const day = parseInt(birDD.value);

    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]{1,30}$/;
    const phoneRegex = /^\d{1,20}$/;


    // 아이디 유효성 검사
    if (uidValue === "") {
        document.getElementById("uid-empty").style.display = "block";
    } else if (uidValue.length < uidRegex) {
        document.getElementById("uid-length").style.display = "block";
        document.getElementById("uid-empty").style.display = "none";
        document.getElementById("uid-alphanumeric").style.display = "none";
    } else if (!uidRegex.test(uidValue)) {
        document.getElementById("uid-alphanumeric").style.display = "block";
        document.getElementById("uid-empty").style.display = "none";
        document.getElementById("uid-length").style.display = "none";
    } else {
        document.getElementById("uid-empty").style.display = "none";
        document.getElementById("uid-length").style.display = "none";
        document.getElementById("uid-alphanumeric").style.display = "none";
    }

    // 비밀번호 유효성 검사
    if (passwordValue === "") {
        document.getElementById("password-empty").style.display = "block";
    } else if (!passwordRegex.test(passwordValue)) {
        document.getElementById("password-invalid").style.display = "block";
        document.getElementById("password-empty").style.display = "none";
    } else {
        document.getElementById("password-empty").style.display = "none";
        document.getElementById("password-invalid").style.display = "none";
    }

    // 비밀번호 일치 여부 검사
    if (passwordValue !== cpasswordValue) {
        document.getElementById("cpassword-mismatch").style.display = "block";
    } else {
        document.getElementById("cpassword-mismatch").style.display = "none";
    }

    // 이름 유효성 검사
    if (fullnameValue === "") {
        // 이름이 빈 문자열일 경우
        document.getElementById("fullname-empty").style.display = "block";
        document.getElementById("fullname-invalid").style.display = "none";
        document.getElementById("fullname-vowel").style.display = "none";
    } else if (!/^[^A-Za-z]*$/.test(fullnameValue)) {
        // 이름에 한글과 자음/모음 이외의 문자나 공백이 포함된 경우
        document.getElementById("fullname-vowel").style.display = "block";
        document.getElementById("fullname-empty").style.display = "none";
        document.getElementById("fullname-invalid").style.display = "none";
    } else if (!fullnameRegex.test(fullnameValue)) {
        // 이름이 한글 1자 이상 20자 이하가 아닌 경우
        document.getElementById("fullname-invalid").style.display = "block";
        document.getElementById("fullname-empty").style.display = "none";
        document.getElementById("fullname-vowel").style.display = "none";
    } else {
        // 유효한 이름인 경우
        document.getElementById("fullname-empty").style.display = "none";
        document.getElementById("fullname-invalid").style.display = "none";
        document.getElementById("fullname-vowel").style.display = "none";
    }

    //닉네임 / 아티스트명 유효성 검사
    if (nidvalue === "") {
        document.getElementById("nid-empty").style.display = "block";
    } else if (!nidRegex.test(nidvalue)) {
        // 이름이 한글 1자 이상 20자 이하가 아닌 경우
        document.getElementById("nid-invalid").style.display = "block";
        document.getElementById("nid-empty").style.display = "none";
    } else {
        // 유효한 이름인 경우
        document.getElementById("nid-empty").style.display = "none";
        document.getElementById("nid-invalid").style.display = "none";
    }

    //생년월일
    // 1. 모든 필드가 값이 입력되었는지 확인합니다.
    if (!birYY.value || !birMM.value || !birDD.value) {
        document.getElementById("bir-empty").style.display = "block";
        document.getElementById("bir-invalid").style.display = "none";
    } else {
        document.getElementById("bir-empty").style.display = "none";
    }

    // 2. 각각의 필드가 숫자로만 입력되었는지 확인합니다.
    const regex = /^\d+$/;
    if (!regex.test(birYY.value) || !regex.test(birMM.value) || !regex.test(birDD.value)) {
        document.getElementById("bir-invalid").style.display = "block";
        document.getElementById("bir-empty").style.display = "none";
    } else {
        document.getElementById("bir-invalid").style.display = "none";
    }

    if (year < 1900 || year > new Date().getFullYear()) {
        document.getElementById("bir-year-invalid").style.display = "block";
    } else {
        document.getElementById("bir-year-invalid").style.display = "none";
    }
    if (month < 1 || month > 12) {
        document.getElementById("bir-month-invalid").style.display = "block";
    } else {
        document.getElementById("bir-month-invalid").style.display = "none";
    }
    const maxDay = new Date(year, month, 0).getDate();
    if (day < 1 || day > maxDay) {
        document.getElementById("bir-day-invalid").style.display = "block";
    } else {
        document.getElementById("bir-day-invalid").style.display = "none";
    }

    //라디오
    if (!sexRadio1.checked && !sexRadio2.checked) {
        document.getElementById("sexRadio_check").style.display = "block";
    } else {
        document.getElementById("sexRadio_check").style.display = "none";
    }

    //이메일
    if (emailValue === "") {
        // Email is required
        document.getElementById("email-empty").style.display = "block";
    } else if (!emailRegex.test(emailValue)) {
        document.getElementById("email-invalid").style.display = "block";
        document.getElementById("email-empty").style.display = "none";
    } else {
        document.getElementById("email-empty").style.display = "none";
        document.getElementById("email-invalid").style.display = "none";
    }

    //휴대폰
    if (num1Value === "") {
        // Phone number is required
        document.getElementById("num1-empty").style.display = "block";
        document.getElementById("num1-invalid").style.display = "none";
    } else if (!phoneRegex.test(num1Value)) {
        // Invalid phone number format
        document.getElementById("num1-invalid").style.display = "block";
        document.getElementById("num1-empty").style.display = "none";
    } else {
        document.getElementById("num1-empty").style.display = "none";
        document.getElementById("num1-invalid").style.display = "none";
    }

    if (num2Value === "") {
        // Phone number is required
        document.getElementById("num2-empty").style.display = "block";
        document.getElementById("num2-invalid").style.display = "none";
    } else if (!phoneRegex.test(num2Value)) {
        // Invalid phone number format
        document.getElementById("num2-invalid").style.display = "block";
        document.getElementById("num2-empty").style.display = "none";
    } else {
        document.getElementById("num2-empty").style.display = "none";
        document.getElementById("num2-invalid").style.display = "none";
    }

    //약관동의
    if (!document.getElementById("chk_agree").checked) {
        // Checkbox is not checked
        document.getElementById("chk_agree-invalid").style.display = "block";
        return false;
    } else {
        document.getElementById("chk_agree-invalid").style.display = "none";
    }

});