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

// 일반 지도와 스카이뷰로 지도 타입을 전환할 수 있는 지도타입 컨트롤을 생성합니다
var mapTypeControl = new kakao.maps.MapTypeControl();

// 지도에 컨트롤을 추가해야 지도위에 표시됩니다
// kakao.maps.ControlPosition은 컨트롤이 표시될 위치를 정의하는데 TOPRIGHT는 오른쪽 위를 의미합니다
map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPLEFT);

// 지도 확대 축소를 제어할 수 있는  줌 컨트롤을 생성합니다
var zoomControl = new kakao.maps.ZoomControl();
map.addControl(zoomControl, kakao.maps.ControlPosition.LEFT);

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
