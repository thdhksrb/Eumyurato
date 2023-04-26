var url = location.pathname;
var id = url.match(/\d+/)[0];
var detailList = $('#detailList');
var price = $('#price');

$.ajax({
    url: '/busking/detail/'+id+'/json',
    dataType: 'json',
    success: function(data) {
        var li = $('<li>');

        li.append($('<p>').html('<strong>'+ data.name+ '</strong>'));
        li.append($('<p>').html('<strong>장소: </strong>' + data.location));
        li.append($('<p>').html('<strong>공연 일자: </strong>' + data.date));
        li.append($('<p>').html('<strong>공연자: </strong>' + data.nid));

        detailList.append(li);

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