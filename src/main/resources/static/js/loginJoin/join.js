//joinform_check 함수로 유효성 검사
function joinform_check() {
    //변수에 담아주기
    var uid = document.getElementById("uid");
    var pwd = document.getElementById("pwd");
    var repwd = document.getElementById("repwd");
    var uname = document.getElementById("uname");
    var nid = document.getElementById("nid");
    var female = document.getElementById("female");
    var male = document.getElementById("male");
    var mobile = document.getElementById("mobile");
  /*  var email_id = document.getElementById("email_id");*/
    var agree = document.getElementById("agree");

    if (uid.value == "") { //해당 입력값이 없을 경우 같은말: if(!uid.value)
        alert("아이디를 입력하세요.");
        uid.focus(); //focus(): 커서가 깜빡이는 현상, blur(): 커서가 사라지는 현상
        return false; //return: 반환하다 return false:  아무것도 반환하지 말아라 아래 코드부터 아무것도 진행하지 말것
    };

    if (pwd.value == "") {
        alert("비밀번호를 입력하세요.");
        pwd.focus();
        return false;
    };

    //비밀번호 영문자+숫자+특수조합(8~25자리 입력) 정규식
    var pwdCheck = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,25}$/;

    if (!pwdCheck.test(pwd.value)) {
        alert("비밀번호는 영문자+숫자+특수문자 조합으로 8~25자리 사용해야 합니다.");
        pwd.focus();
        return false;
    };

    if (repwd.value !== pwd.value) {
        alert("비밀번호가 일치하지 않습니다..");
        repwd.focus();
        return false;
    };

    if (uname.value == "") {
        alert("이름을 입력하세요.");
        uname.focus();
        return false;
    };

    if (nid.value == "") { //해당 입력값이 없을 경우 같은말: if(!uid.value)
        alert("아이디를 입력하세요.");
        nid.focus(); //focus(): 커서가 깜빡이는 현상, blur(): 커서가 사라지는 현상
        return false; //return: 반환하다 return false:  아무것도 반환하지 말아라 아래 코드부터 아무것도 진행하지 말것
    };

    if (!female.checked && !male.checked) { //둘다 미체크시
        alert("성별을 선택해 주세요.");
        female.focus();
        return false;
    }

    var reg = /^[0-9]+/g; //숫자만 입력하는 정규식

    if (!reg.test(mobile.value)) {
        alert("전화번호는 숫자만 입력할 수 있습니다.");
        mobile.focus();
        return false;
    }

/*    if (email_id.value == "") {
        alert("이메일 주소를 입력하세요.");
        email_id.focus();
        return false;
    }*/

    if (!agree.checked) { //체크박스 미체크시
        alert("약관 동의를 체크하세요.");
        agree.focus();
        return false;
    }

    //입력 값 전송
    document.join_form.submit(); //유효성 검사의 포인트
}

//아이디 중복체크 팝업창(현재 공백문서)
function id_check() {
    //window.open("팝업될 문서 경로", "팝업될 문서 이름", "옵션");
    window.open("", "", "width=600, height=200, left=200, top=100");
}

//이메일 옵션 선택후 주소 자동 완성
function change_email() {
    var email_add = document.getElementById("email_add");
    var email_sel = document.getElementById("email_sel");

    //지금 골라진 옵션의 순서와 값 구하기
    var idx = email_sel.options.selectedIndex;
    var val = email_sel.options[idx].value;

    email_add.value = val;
}
