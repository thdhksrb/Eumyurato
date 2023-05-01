const form = document.querySelector("form");
const idInput = document.getElementById("id");
const pwdInput = document.getElementById("pwd");
const cpasswordInput = document.getElementById("cpassword");
const nameInput = document.getElementById("name");
const nidInput = document.getElementById("nid");
const birthInput = document.getElementById("birth");
const sexRadio1 = document.getElementById("sexRadio1");
const sexRadio2 = document.getElementById("sexRadio2");
const emailInput = document.getElementById("email");
const phoneInput = document.getElementById("phone");

const idRegex = /^(?=.*[a-zA-Z])[a-zA-Z0-9]{5,20}$/;
const pwdRegex = /^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!])(?=\S+$).{8,20}$/;
const nameRegex = /^[가-힣]{1,20}$/u; // 이름 정규식 패턴
const nidRegex = /^[가-힣]{1,20}$/u; // 이름 정규식 패턴
const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]{1,30}$/;
const phoneRegex = /^\d{1,20}$/;

form.addEventListener("submit", function (event) {
    event.preventDefault(); // 폼 제출 이벤트를 취소하여 새로고침을 막음

    const idValue = idInput.value.trim();
    const pwdValue = pwdInput.value.trim();
    const cpasswordValue = cpasswordInput.value.trim();
    const nameValue = nameInput.value.trim();
    const nidValue = nidInput.value.trim();
    const birthValue = birthInput.value.trim();
    const emailValue = emailInput.value.trim();
    const phoneValue = phoneInput.value.trim();

// 아이디 유효성 검사
    if (idValue === "") {
        document.getElementById("uid-empty").style.display = "block";
    } else if (idValue.length < 5 || idValue.length > 20) { // 수정된 부분
        document.getElementById("uid-length").style.display = "block";
        document.getElementById("uid-empty").style.display = "none";
        document.getElementById("uid-alphanumeric").style.display = "none";
    } else if (!idRegex.test(idValue)) {
        document.getElementById("uid-alphanumeric").style.display = "block";
        document.getElementById("uid-empty").style.display = "none";
        document.getElementById("uid-length").style.display = "none";
    } else {
        document.getElementById("uid-empty").style.display = "none";
        document.getElementById("uid-length").style.display = "none";
        document.getElementById("uid-alphanumeric").style.display = "none";
    }


    // 비밀번호 유효성 검사
    if (pwdValue === "") {
        document.getElementById("password-empty").style.display = "block";
    } else if (!pwdRegex.test(pwdValue)) {
        document.getElementById("password-invalid").style.display = "block";
        document.getElementById("password-empty").style.display = "none";
    } else {
        document.getElementById("password-empty").style.display = "none";
        document.getElementById("password-invalid").style.display = "none";
    }

    // 비밀번호 일치 여부 검사
    if (pwdValue !== cpasswordValue) {
        document.getElementById("cpassword-mismatch").style.display = "block";
    } else {
        document.getElementById("cpassword-mismatch").style.display = "none";
    }

// 이름 유효성 검사
    if (nameValue === "") {
        // 이름이 빈 문자열일 경우
        document.getElementById("name-empty").style.display = "block";
        document.getElementById("name-invalid").style.display = "none";
        document.getElementById("name-vowel").style.display = "none";
    } else if (!/^[^A-Za-z]*$/.test(nameValue)) {
        // 이름에 한글과 자음/모음 이외의 문자나 공백이 포함된 경우
        document.getElementById("name-vowel").style.display = "block";
        document.getElementById("name-empty").style.display = "none";
        document.getElementById("name-invalid").style.display = "none";
    } else if (!nameRegex.test(nameValue)) {
        // 이름이 한글 1자 이상 20자 이하가 아닌 경우
        document.getElementById("name-invalid").style.display = "block";
        document.getElementById("name-empty").style.display = "none";
        document.getElementById("name-vowel").style.display = "none";
    } else {
        // 유효한 이름인 경우
        document.getElementById("name-empty").style.display = "none";
        document.getElementById("name-invalid").style.display = "none";
        document.getElementById("name-vowel").style.display = "none";
    }

// 닉네임 / 아티스트명 유효성 검사
    if (nidValue === "") { // 수정된 부분
        document.getElementById("nid-empty").style.display = "block";
    } else if (!nidRegex.test(nidValue)) {
        // 이름이 한글 1자 이상 20자 이하가 아닌 경우
        document.getElementById("nid-invalid").style.display = "block";
        document.getElementById("nid-empty").style.display = "none";
    } else {
        // 유효한 이름인 경우
        document.getElementById("nid-empty").style.display = "none";
        document.getElementById("nid-invalid").style.display = "none";
    }

    // 성별 라디오 버튼 유효성 검사
    if (!sexRadio1.checked && !sexRadio2.checked) {
        document.getElementById("sexRadio_check").style.display = "block";
    } else {
        document.getElementById("sexRadio_check").style.display = "none";
    }

// 생년월일 유효성 검사
    if (birthValue === "") { // 추가된 부분
        document.getElementById("date-empty").style.display = "block";
    } else {
        document.getElementById("date-empty").style.display = "none";
    }

// 이메일 유효성 검사
    if (emailValue === "") {
        // 이메일이 빈 문자열일 경우
        document.getElementById("email-empty").style.display = "block";
    } else if (!emailRegex.test(emailValue)) {
        // 이메일 형식이 올바르지 않을 경우
        document.getElementById("email-invalid").style.display = "block";
        document.getElementById("email-empty").style.display = "none";
    } else {
        document.getElementById("email-empty").style.display = "none";
        document.getElementById("email-invalid").style.display = "none";
    }

// 휴대폰 번호 유효성 검사
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

// 약관 동의 체크박스 유효성 검사
    if (!document.getElementById("chk_agree").checked) {
        // 체크박스가 체크되지 않았을 경우
        document.getElementById("chk_agree-invalid").style.display = "block";
        return false;
    } else {
        document.getElementById("chk_agree-invalid").style.display = "none";
    }


});



// 아이디 중복 버튼 이벤트 핸들러
const duplicateBtn1 = document.querySelector("#id ~ button[type='button']");
duplicateBtn1.addEventListener("click", function() {
    const idInput = document.getElementById("id");
    const idValue = idInput.value.trim();
    if (idValue !== "") {
        // 서버로 중복 검사 요청
        fetch(`/checkIdDuplicate/${idValue}`)
            .then(response => response.json())
            .then(data => {
                if (data.duplicate) {
                    document.getElementById("uid-duplicate").style.display = "block";
                    document.getElementById(`uid-available`).style.display = "none";
                } else {
                    document.getElementById(`uid-available`).style.display = "block";
                    document.getElementById("uid-duplicate").style.display = "none";
                }
            })
            .catch(error => console.error(error));
    }
});

// 닉네임 중복 검사 버튼 이벤트 핸들러
const duplicateBtn2 = document.querySelector("#duplicateBtn2");
duplicateBtn2.addEventListener("click", function() {
    const nidInput = document.getElementById("nid");
    const nidValue = nidInput.value.trim();
    if (nidValue !== "") {
        // 서버로 중복 검사 요청
        fetch(`/checkNidDuplicate/${nidValue}`)
            .then(response => response.json())
            .then(data => {
                if (data.duplicate) {
                    document.getElementById("nid-duplicate").style.display = "block";
                    document.getElementById(`nid-available`).style.display = "none";
                } else {
                    document.getElementById(`nid-available`).style.display = "block";
                    document.getElementById("nid-duplicate").style.display = "none";
                }
            })
            .catch(error => console.error(error));
    }
});

//회원가입 이벤트 핸들러
const joinButton = document.getElementById('joinButton');
joinButton.addEventListener('click', function(e) {
    e.preventDefault();  // 기본 이벤트 동작(폼 전송) 중단
    document.getElementById('myForm').submit();  // 폼 전송 실행
});
