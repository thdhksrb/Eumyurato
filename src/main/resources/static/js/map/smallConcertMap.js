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

// 일반 지도와 스카이뷰로 지도 타입을 전환할 수 있는 지도타입 컨트롤을 생성
var mapTypeControl = new kakao.maps.MapTypeControl();

// 지도에 컨트롤을 추가해야 지도위에 표시
// kakao.maps.ControlPosition은 컨트롤이 표시될 위치를 정의하는데 TOPRIGHT는 오른쪽 위를 의미
map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPLEFT);

// 지도 확대 축소를 제어할 수 있는  줌 컨트롤을 생성
var zoomControl = new kakao.maps.ZoomControl();
map.addControl(zoomControl, kakao.maps.ControlPosition.LEFT);

// HTML 사이드바 요소 가져오기
var concert = document.getElementById('concert');

// 주소-좌표 변환 객체를 생성
var geocoder = new kakao.maps.services.Geocoder();

// 모든 마커 정보를 가져와서 출력
var xhr = new XMLHttpRequest();
xhr.open('GET', '/smallconcert/json');
xhr.onload = function () {
    if (xhr.status === 200) {
        var data = JSON.parse(xhr.responseText);
        for (var i = 0; i < data.length; i++) {
            var record = data[i];

            // 클로저를 사용해서 변수를 캡처
            (function(record) {
                // 장소명과 주소로 좌표를 검색
                geocoder.addressSearch(record.location, function(result, status) {
                    // 정상적으로 검색이 완료됐으면
                    if (status === kakao.maps.services.Status.OK) {
                        var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
                        var marker = new kakao.maps.Marker({
                            map: map,
                            position: coords
                        });

                        // 마커에 표시할 인포윈도우를 생성
                        var infowindow = new kakao.maps.InfoWindow({
                            content: record.name // 인포윈도우에 표시할 내용
                        });

                        // 마커에 클릭 이벤트를 등록
                        kakao.maps.event.addListener(marker, 'click', clickMakerListener(map, marker, infowindow));
                        kakao.maps.event.addListener(map, 'click', clickMapListener(infowindow));

                        var li = document.createElement('li');
                        li.id=record.id;
                        var startDate = new Date(record.startDate);
                        var lastDate = new Date(record.lastDate);
                        li.innerHTML = '<h3>' + record.name + '</h3>' +
                            '<p><strong>개최장소: </strong>' + record.location + '</p>' +
                            '<p><strong>축제기간: </strong>' + formatDate(startDate) + ' ~ ' + formatDate(lastDate) + '</p>' +
                            '<p><strong>공연자: </strong>' + record.pname + '</p>';
                        li.style.padding='18px';
                        li.style.borderTop = '1px solid #ccc';
                        concert.appendChild(li);

                        li.addEventListener('click',function(){
                            goToDetailPage(record.id);
                        })
                    }
                });
            })(record);
        }
    }
};
xhr.send();

// 인포윈도우를 표시하는 클로저를 만드는 함수
function clickMakerListener(map, marker, infowindow) {
    return function() {
        infowindow.open(map, marker);
    };
}

// 인포윈도우를 닫는 클로저를 만드는 함수
function clickMapListener(infowindow) {
    return function () {
        infowindow.close();
    };
}

function formatDate(date) {
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var day = date.getDate();
    return year + '.' + padZero(month) + '.' + padZero(day);
}

function padZero(num) {
    return (num < 10 ? '0' : '') + num;
}

function goToDetailPage(id){

    var detailPageUrl = '/smallconcert/detail?id=' + id;

    window.location.href = detailPageUrl;
}