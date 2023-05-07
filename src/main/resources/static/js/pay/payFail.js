const price = localStorage.getItem('price');
const id = localStorage.getItem('id');
console.log(price);
localStorage.removeItem('price');
localStorage.removeItem('id');

const token = sessionStorage.getItem("jwtToken");

const data = {
    price: price,
    id: id
};

const xhr = new XMLHttpRequest();
xhr.open('POST', '/kakaopay/fail/donation');
xhr.setRequestHeader('Content-Type', 'application/json');
xhr.setRequestHeader('Authorization', `Bearer ${token}`);
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
        priceElement.innerText = '취소금액 : ' + price +'원';
    } else {
        console.error(xhr.statusText);
    }
};
xhr.onerror = function() {
    console.error(xhr.statusText);
};
xhr.send(JSON.stringify(data));