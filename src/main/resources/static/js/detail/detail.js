var url = location.pathname;
var id = url.match(/\d+/)[0];    //첫번째 숫자 추출
var detailList = $('#detailList');

$.ajax({
    url: '/smallconcert/detail/'+id+'/json',
    dataType: 'json',
    success: function(data) {
        var li = $('<li>');

        li.append($('<h3>').text(data.name));
        li.append($('<p>').html('<strong>개최장소: </strong>' + data.location));
        li.append($('<p>').html('<strong>축제기간: </strong>' + data.startDate + ' ~ ' + data.lastDate));
        li.append($('<p>').html('<strong>공연자: </strong>' + data.pname));

        detailList.append(li);
    },
    error: function(xhr, status, error) {
        console.log('AJAX Error: ' + status + error);
    }
});

