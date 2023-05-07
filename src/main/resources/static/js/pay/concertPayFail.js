const conPrice = window.localStorage.getItem("conPrice");
const conId = window.localStorage.getItem("conId");

window.localStorage.removeItem("conId");
window.localStorage.removeItem("conPrice");

const data = {
    conId: conId
}

const xhr = new XMLHttpRequest();
xhr.open('POST', '/kakaopay/fail');
xhr.setRequestHeader('Content-Type', 'application/json');
xhr.onload = function() {
    if (xhr.status === 200) {
        console.log(xhr.response); // 응답 데이터
        const response = JSON.parse(xhr.response);
        console.log(response.message);
        console.log(response.message.name);
        const conName = response.message.name;

        const conNameElement = document.getElementById('conName');
        conNameElement.innerText = '공연명 : ' + conName;

        const priceElement = document.getElementById('price');
        priceElement.innerText = '취소금액 : ' + conPrice;
    } else {
        console.error(xhr.statusText);
    }
};
xhr.onerror = function() {
    console.error(xhr.statusText);
};
xhr.send(JSON.stringify(data));