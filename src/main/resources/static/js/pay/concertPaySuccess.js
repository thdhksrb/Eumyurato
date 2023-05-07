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
xhr.send(JSON.stringify(data));