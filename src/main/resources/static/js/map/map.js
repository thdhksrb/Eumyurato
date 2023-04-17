//toggler
$(document).ready(function(){
    $('#kt_mega_menu_toggle').on('click', function(){
        $('.mapWrap, .sideMenu').toggleClass('active');
    });
});


// 지도 객체 생성
var container = document.getElementById('map');
var options = {
    center: new kakao.maps.LatLng(37.54, 126.96),
    level: 7
};
var map = new kakao.maps.Map(container, options);

// HTML 사이드바 요소 가져오기
var festivals = document.getElementById('festivals');

// 모든 마커 정보를 가져와서 출력
var xhr = new XMLHttpRequest();
xhr.open('GET', 'festival.json');
xhr.onload = function () {
    if (xhr.status === 200) {
        var data = JSON.parse(xhr.responseText);
        for (var i = 0; i < data.records.length; i++) {
            var record = data.records[i];
            var marker = new kakao.maps.Marker({
                map: map,
                position: new kakao.maps.LatLng(record.위도, record.경도),
                title: record.축제명
            });
            var li = document.createElement('li');
            li.innerHTML = '<h3>' + record.축제명 + '</h3>' +
                '<p><strong>개최장소: </strong>' + record.개최장소 + '</p>' +
                '<p><strong>축제기간: </strong>' + record.축제시작일자 + ' - ' + record.축제종료일자 + '</p>' +
                '<p><strong>축제내용: </strong>' + record.축제내용 + '</p>';
            festivals.appendChild(li);
        }
    }
};
xhr.send();
