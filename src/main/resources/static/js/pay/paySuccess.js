const myprice = localStorage.getItem("price");
localStorage.removeItem("price");
const data = {
    price : myprice
};

window.onload = function() {
    // XMLHttpRequest 객체 생성
    const xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                console.log(xhr.responseText);
            } else {
                console.log('Error!');
            }
        }
    };
    xhr.open('POST', 'http://localhost:8081/kakaopay/success/donation', true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.send(JSON.stringify(data));
};