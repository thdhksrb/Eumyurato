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

// 아이디 유효성 검사
idInput.addEventListener("input", function () {
    const idValue = idInput.value.trim();

    if (idValue === "") {
        document.getElementById("uid-empty").style.display = "block";
    } else if (idValue.length < 5 || idValue.length > 20) {
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
                } else if (!data.duplicate){
                    document.getElementById(`uid-available`).style.display = "block";
                    document.getElementById("uid-duplicate").style.display = "none";
                } else{
                    document.getElementById(`uid-available`).style.display = "none";
                    document.getElementById("uid-duplicate").style.display = "none";
                }
            })
            .catch(error => console.error(error));
    }
});

// 비밀번호 유효성 검사
pwdInput.addEventListener("input", function () {
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
cpasswordInput.addEventListener("input", function () {
    const pwdValue = pwdInput.value.trim();
    const cpasswordValue = cpasswordInput.value.trim();

    if (pwdValue !== cpasswordValue) {
        document.getElementById("cpassword-mismatch").style.display = "block";
    } else {
        document.getElementById("cpassword-mismatch").style.display = "none";
    }
});

// 이름 유효성 검사
nameInput.addEventListener("input", function () {
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


// 닉네임 / 아티스트명 유효성 검사
nameInput.addEventListener("input", function () {
    const nidValue = nidInput.value.trim();

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
});

// 성별 라디오 버튼 유효성 검사
nameInput.addEventListener("radio", function () {

    if (!sexRadio1.checked && !sexRadio2.checked) {
        document.getElementById("sexRadio_check").style.display = "block";
    } else {
        document.getElementById("sexRadio_check").style.display = "none";
    }

});

// 생년월일 유효성 검사
nameInput.addEventListener("date", function () {
    const birthValue = birthInput.value.trim();

    if (birthValue === "") { // 추가된 부분
        document.getElementById("date-empty").style.display = "block";
    } else {
        document.getElementById("date-empty").style.display = "none";
    }

});

// 이메일 유효성 검사
nameInput.addEventListener("input", function () {
    const emailValue = emailInput.value.trim();

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
});

// 휴대폰 번호 유효성 검사
nameInput.addEventListener("input", function () {
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

// 약관 동의 체크박스 유효성 검사
nameInput.addEventListener("checkbox", function () {

    if (!document.getElementById("chk_agree").checked) {
        // 체크박스가 체크되지 않았을 경우
        document.getElementById("chk_agree-invalid").style.display = "block";
        return false;
    } else {
        document.getElementById("chk_agree-invalid").style.display = "none";
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

// 회원가입 이벤트 핸들러
const joinButton = document.getElementById('joinButton');
joinButton.addEventListener('click', function (e) {
    e.preventDefault(); // 기본 이벤트 동작(폼 전송) 중단

    // 모든 유효성 검사 통과 여부 확인
    const allValidationsPassed = !document.querySelectorAll('.validation-message.show').length;
    if (allValidationsPassed) {
        document.getElementById('myForm').submit(); // 폼 전송 실행
    }
});

// 이메일 정규식
var email_pattern = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
var code_pattern = /[0-9]{6,6}/;
let email_input, RFCBtn, email_msg, misspell_box, email_change_btn;
let auth_container, code_input, timer, code_msg, code_check, ReRFCBtn;
let ResponseCode = "";

const border_err_color="red", border_succ_color="gray";
const msg_err_color = "red", msg_succ_color = "green";
const input_readonly_color = "#e1e4ee";

// 코드 유효성 (유효하면 true, 아니면 false)
let code_valid = false
// 발송 후 지난 초
let current_time = 0;
// 유효시간
let minutes,seconds;
let timer_thread;

// 형식 유효성 검사
function type_validation(pattern,input,msg, success){

    // 검사할 입력값
    let value = input.value;
    // 형식 유효성 검사
    // 형식검사 1. "" 아무것도 입력하지 않았는가?
    if(value.length == 0){
        // 입력창 빨간색 테두리
        input.style.borderColor = border_err_color;
        // 메세지 보임 처리
        msg.style.display = "block"
        // 메세지 실패 색상 설정
        msg.style.color = msg_err_color;
        // 메세지에 이메일을 입력해주세요. 출력
        msg.textContent = "입력해주세요.";
    }
    // 형식검사 2. 정규 표현식을 만족하지 못하는가?
    else if(value.match(pattern) == null){
        // 입력창 빨간색 테두리
        input.style.borderColor = border_err_color;
        // 메세지 보임 처리
        msg.style.display = "block"
        // 메세지 실패 색상 설정
        msg.style.color = msg_err_color;
        // 메세지에 이메일을 입력해주세요. 출력
        msg.textContent = "형식을 다시 확인해주세요.";
    }
    // 형식 유효성 검사 통과
    else{

        success();

    }

}

// 인증코드 유효시간 카운트다운 및 화면 출력
function timer_start(){


    // 인증코드 유효성 true
    code_valid = true;
    // 현재 발송 시간 초기화
    current_time = 0
    // 20초
    let count = 20

    timer.innerHTML = "00:20"
    // 1초마다 실행
    timer_thread = setInterval(function () {

        minutes = parseInt(count / 60, 10);
        seconds = parseInt(count % 60, 10);

        minutes = minutes < 10 ? "0" + minutes : minutes;
        seconds = seconds < 10 ? "0" + seconds : seconds;


        timer.innerHTML  = minutes + ":" + seconds;

        // alert(minutes + ":" + seconds);

        // 타이머 끝
        if (--count < 0) {
            timer_stop();
            timer.textContent = "시간초과"
            // code msg 보임
            code_msg.style.display = "block";
            // code msg "인증코드가 만료되었습니다."
            code_msg.textContent = "인증코드가 만료되었습니다.";
            // 코드 색상 비정상
            code_msg.style.color = msg_err_color;
        }

        current_time++

    }, 1000);

}

// 타이머 종료
function timer_stop(){
    // 타이머 종료
    clearInterval(timer_thread)
    // 유효시간 만료
    code_valid = false
}

// 인증코드가 유효하면 true, 만료되었다면 false 반환
function iscodeValid(){

    return code_valid;

}

// 인증코드 발송 후 10초가 지났는가? 지났으면 true, 안지났으면 false
function isRerequest(){

    return  current_time>=10?true:false;

}
// 화면이 로드되었을 때 발생하는 이벤트
window.onload = function(event){

    email_input = document.querySelector(".input_email");
    RFCBtn = document.querySelector(".RFCBtn");
    email_msg = document.querySelector(".email_msg");
    misspell_box = document.querySelector(".misspell_box");
    email_change_btn = document.querySelector(".email_change");

    auth_container = document.querySelector(".auth_container");
    code_input = document.querySelector(".input_auth");
    code_msg = document.querySelector(".auth_msg");
    timer = document.querySelector(".timer");
    code_check = document.querySelector(".AuthBtn");
    ReRFCBtn = document.querySelector(".ReRFCBtn");


    // 이메일 입력창 포커스 얻을 때
    email_input.onfocus = function(e){
        // 이메일을 작성할 수 있을 때
        if(email_input.readOnly == false){
            // 인증 코드 발송 btn 비활성화
            RFCBtn.disabled = true;
            // 이메일 input 테두리 색상 복구
            this.style.borderColor = border_succ_color;
            // 메세지 안보임
            email_msg.style.display = "none";
        }

    }

    // 이메일 입력창 포커스 잃을 때
    email_input.onblur = function(e){

        // 이메일 형식 유효성검사
        type_validation(email_pattern,email_input,email_msg,function(){
            // 이메일 형식 유효성검사 통과시 실행는 함수

            // 입력창 정상 테두리
            email_input.style.borderColor = border_succ_color;
            // 메세지 보임 처리
            email_msg.style.display = "block"
            // 메세지 성공 색상 설정
            email_msg.style.color = msg_succ_color;
            // 메세지에 올바른 이메일 형식입니다. 출력되
            email_msg.textContent = "올바른 이메일 형식입니다.";
            // 인증 코드 발송 btn 활성화
            RFCBtn.disabled = false;
        })

    }

    // 인증코드 발송 버튼 클릭시
    RFCBtn.onclick = function(e){

        // 인증코드 발송 처리
        // 발송된 코드
        ResponseCode = "123456";



        // 화면 처리
        // 이메일 입력창 readonly로 변경
        email_input.readOnly = true;
        // 이메일 입력창 색 파란색으로 변경
        email_input.style.backgroundColor = input_readonly_color;
        // 인증코드 발송 버튼 비활
        this.disabled = true;
        // 이메일 misspell box 보임으로 변경
        misspell_box.style.display = "block";

        // 타이머 설정 및 화면에 출력
        timer_start();

        // auth_box 보임으로 변경
        auth_container.style.display = "block";
        // 인증 msg 인증코드 발송 성공. (10초 뒤부터 재발송 가능합니다.)
        code_msg.textContent = "인증코드 발송 성공. (10초 뒤부터 재발송 가능합니다.)";

        // 인증 msg 색 변경
        code_msg.style.color = msg_succ_color;
        // 인증 msg 보임 처리
        code_msg.style.display = "block";



    }

    // 이메일 변경 버튼을 클릭할 때
    // 이메일을 잘못 입력해서, 이메일을 변경하고자 함.
    email_change_btn.onclick = function(e){
        // 인증 절차 초기화

        // 인증 코드 만료
        ResponseCode = "";
        // 타이머 종료
        timer_stop();

        // 화면 초기화
        // auth container 안보임
        auth_container.style.display = "none";
        // misspell box 안보임
        misspell_box.style.display = "none";
        // 인증코드 btn 활성화
        RFCBtn.disabled = false;
        // 이메일 입력창 배경색 흰색으로 변경
        email_input.style.backgroundColor = "white";
        // 이메일 입력창 활성화
        email_input.readOnly = false;

        // 인증 확인 버튼 비활성화
        code_check.disabled = true;
    }


    // 인증코드 입력창 포커스 얻을 때
    code_input.onfocus = function(e){
        // 입력창 정상 테두리
        this.style.borderColor = border_succ_color;
        // msg 제거
        code_msg.style.display = "none";
        // 인증 확인 버튼 비활성화
        code_check.disabled = true;
    }


    // 인증코드 입력창 포커스 잃을 때
    code_input.onblur = function(e){

        // 입력한 인증코드의 상태를 검사한다.

        // 1. 인증코드가 만료되었는지 확인
        if(!iscodeValid()){
            // code 입력창 테두리 빨간색으로 변경
            code_input.style.borderColor = border_err_color;
            // code msg 보임
            code_msg.style.display = "block";
            // code msg "인증코드가 만료되었습니다."
            code_msg.textContent = "인증코드가 만료되었습니다.";
            // 코드 색상 비정상
            code_msg.style.color = msg_err_color;
        }
        else{
            // 2. 입력값이 존재하는지 확인
            // 3. 정규식을 만족하는지 확인

            type_validation(code_pattern,this,code_msg,function(){
                // 형식 유효성 검사를 통과했다면

                // code 입력창 테두리 정상으로 변경
                code_input.style.borderColor = border_succ_color;
                // code msg 보임
                code_msg.style.display = "block";
                // code msg "올바른 인증코드 형식입니다."
                code_msg.textContent = "올바른 인증코드 형식입니다.";
                // 코드 색상 정상
                code_msg.style.color = msg_succ_color;
                // 인증 확인 버튼 활성화
                code_check.disabled = false;
            })
        }


    }


    // 인증코드 확인 버튼 클릭할 때
    code_check.onclick = function(e){

        // 타이머 시간 초과 확인
        if(iscodeValid()){
            let code = code_input.value;
            // 인증코드 일치성 검사
            // 통과시
            if(ResponseCode == code){
                // code msg "이메일 인증 성공!"
                code_msg.textContent = "이메일 인증 성공!"
                // code msg 보임
                code_msg.style.display = "block";
                // code msg 색상 정상
                code_msg.style.color = msg_succ_color;
            }
            else{
                // 미통과
                // code msg "인증코드가 일치하지 않습니다."
                code_msg.textContent = "인증코드가 일치하지 않습니다."
                // code msg 보임
                code_msg.style.display = "block";
                // code msg 색상 비정상
                code_msg.style.color = msg_err_color;
            }
        }
    }

    // 인증코드 재발송 버튼 클릭할 때
    ReRFCBtn.onclick = function(e){

        // 인증코드 발송 후 10초가 지났는지 확인
        if(isRerequest()){
            // 인증코드 재발송
            ResponseCode = "987654"

            // code msg 인증코드 발송 성공
            code_msg.textContent = "인증코드 발송 성공. (10초 뒤부터 재발송 가능합니다.)";
            // code msg 보임
            code_msg.style.display = "block";
            // code msg 정상 색
            code_msg.style.color = msg_succ_color;

            // code 테두리 색 정상으로 변경
            code_input.style.borderColor = border_succ_color

            // 타이머 리셋
            timer_stop()
            timer_start()
        }
        else{
            // code msg 인증코드 발송 거부
            code_msg.textContent = "인증코드 발송 후 10초 뒤부터 재발송 가능합니다.";
            // code msg 보임
            code_msg.style.display = "block";
            // code msg 정상 색
            code_msg.style.color = msg_err_color;
        }


    }

}
