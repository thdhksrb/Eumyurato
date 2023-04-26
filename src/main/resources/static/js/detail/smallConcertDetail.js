var url = location.pathname;
var id = url.match(/\d+/)[0];
var detailList = $('#detailList');
var price = $('#price');


$.ajax({
    url: '/smallconcert/detail/'+id+'/json',
    dataType: 'json',
    success: function(data) {
        var li = $('<li>');

        li.append($('<p>').html('<strong>'+ data.name+ '</strong>'));
        li.append($('<p>').html('<strong>장소: </strong>' + data.location));
        li.append($('<p>').html('<strong>공연 일자: </strong>' + data.startDate + ' ~ ' + data.lastDate));
        li.append($('<p>').html('<strong>공연자: </strong>' + data.pname));

        detailList.append(li);

        // 이미지 URL을 가져온다.
        var imageUrl = data.image;

        // 이미지 요소 생성
        var img = document.createElement("img");
        img.src = imageUrl;
        img.style.objectFit = "contain";
        img.style.width = "100%";
        img.style.height = "100%";

        // 이미지 요소를 포함할 div
        var posterContainer = document.getElementById("posterContainer");

        // div에 이미지 요소 추가
        posterContainer.appendChild(img);

        price.append($('<p>').html('<strong>티켓가격: </strong><span style="color:red">' + data.price.toLocaleString() + '</span>원'));
    },
    error: function(xhr, status, error) {
        console.log('AJAX Error: ' + status + error);
    }
});

const reservation = document.querySelector('#reservation');

reservation.addEventListener('click', () => {
    window.location.href = `/smallconcert/detail/${id}/calendar`;
});