const conId = window.localStorage.getItem("conId");
const conDate = window.localStorage.getItem("conDate");
const conSeat = window.localStorage.getItem("conSeat");
const conPrice = window.localStorage.getItem("conPrice");
const token = sessionStorage.getItem("jwtToken");

window.localStorage.removeItem("conId");
window.localStorage.removeItem("conDate");
window.localStorage.removeItem("conSeat");
window.localStorage.removeItem("conPrice");

const data = {
    conId: conId,
    conDate: conDate,
    conSeat : conSeat,
    conPrice : conPrice
};

const xhr = new XMLHttpRequest();
xhr.open('POST', '/kakaopay/success');
xhr.setRequestHeader('Content-Type', 'application/json');
xhr.setRequestHeader('Authorization', `Bearer ${token}`);
xhr.onload = function() {
    if (xhr.status === 200) {
        console.log(xhr.response); // 응답 데이터
        const response = JSON.parse(xhr.response);
        console.log(response.message);
        console.log(response.message.name);
        const conName = response.message.name;
        const payTimeElement = document.getElementById('payTime');
        payTimeElement.innerText = '결제일시 : ' + new Date().toLocaleString();

        const conNameElement = document.getElementById('conName');
        conNameElement.innerText = '공연명 : ' + conName;

        const priceElement = document.getElementById('price');
        priceElement.innerText = '결제금액 : ' + conPrice +'원';
    } else {
        console.error(xhr.statusText);
    }
};
xhr.onerror = function() {
    console.error(xhr.statusText);
};
xhr.send(JSON.stringify(data));
