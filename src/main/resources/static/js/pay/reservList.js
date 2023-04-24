var ReservList = $('#reservList');
var url = location.pathname;
var id = url.match(/\d+/)[0];
var day = url.match(/(\d{4}-\d{2}-\d{2})/)[0];

$.ajax({
    url: '/smallconcert/detail/'+id+'/calendar/' +day+ '/pay/json',
    dataType: 'json',
    success: function(data) {
        var li = $('<li>');
        console.log(data);

        li.append($('<p>').html('<strong>'+ data.myData.concert.name+ '</strong>'));
        li.append($('<p>').html('<strong>공연 일자: </strong>' + data.myData.concert.startDate + ' ~ ' + data.myData.concert.lastDate));
        li.append($('<p>').html('<strong>장소: </strong>' + data.myData.concert.location));
        li.append($('<p>').html('<strong>공연자: </strong>' + data.myData.concert.pname));
        li.append($('<h2>').html('<strong>*선택내역*</strong>'));
        li.append($('<p>').html('<strong>날짜: </strong>' + day));
        li.append($('<p>').html('<strong>시간: </strong>' + data.myData.schedule.conDate));
        li.append($('<p>').html('<strong>매수: </strong>' + data.myData.count +'매'));
        li.append($('<p>').html('<strong>좌석: </strong>' + data.myData.seat));
        li.append($('<p>').html('<strong>가격: </strong>' + data.myData.concert.price * data.myData.count + '원'))
        li.append($('<input>').attr({ type: 'radio', id: 'onSite', checked: true }));
        li.append($('<label>').attr('for', 'onSite').html('현장수령'));

        ReservList.append(li);
    },
    error: function(xhr, status, error) {
        console.log('AJAX Error: ' + status + error);
    }
});

$(function(){
    $('#pay').click(function (){
        $.ajax({
            url:'/smallconcert/detail/'+id+'/calendar/' +day+ '/pay/kakao',
            dataType: 'json',
            success:function (data){
                alert(data);
            },
            error:function (error){
                alert(error);
        }
        });
    });
});