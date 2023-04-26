var url = location.pathname;
var id = url.match(/\d+/)[0];
var detailList = $('#detailList');
var price = $('#price');

$.ajax({
    url: '/local_festival/detail/'+id+'/json',
    dataType: 'json',
    success: function(data) {
        var li = $('<li>');

        li.append($('<p>').html('<strong>'+ data.name+ '</strong>'));
        li.append($('<p>').html('<strong>개최 장소: </strong>' + data.location));
        li.append($('<p>').html('<strong>개최 일자: </strong>' + data.startDate + ' ~ ' + data.lastDate));
        li.append($('<p>').html('<strong>주최측: </strong>' + data.org));

        detailList.append(li);

    },
    error: function(xhr, status, error) {
        console.log('AJAX Error: ' + status + error);
    }
});

