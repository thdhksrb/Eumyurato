// // HTML 사이드바 요소 가져오기
// var detailList = document.getElementById('detailList');
//
// // 모든 마커 정보를 가져와서 출력
// var xhr = new XMLHttpRequest();
// xhr.open('GET', '/smallconcert/detail/{id}/json');
// xhr.onload = function () {
//     if (xhr.status === 200) {
//         var data = JSON.parse(xhr.responseText);
//         var li = document.createElement('li');
//
//
//         li.innerHTML = '<h3>' + data.name + '</h3>' +
//             '<p><strong>개최장소: </strong>' + data.location + '</p>' +
//             '<p><strong>축제기간: </strong>' + data.startDate + ' ~ ' + data.lastDate + '</p>' +
//             '<p><strong>공연자: </strong>' + data.pname + '</p>';
//
//         detailList.appendChild(li);
//     }
// };
// xhr.send();

var detailList = $('#detailList');

$.ajax({
    url: '/smallconcert/detail/{id}/json',
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