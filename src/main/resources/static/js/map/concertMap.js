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

var markers = [];


// 모든 마커 정보를 가져와서 출력
var xhr = new XMLHttpRequest();
xhr.open('GET', '/all');
xhr.onload = function () {
    if (xhr.status === 200) {
        var data = JSON.parse(xhr.responseText);

        viewBusking(data);
        viewSmallConcert(data);
        viewLocalFest(data);

        // 버튼 클릭 이벤트 핸들러 등록
        var links = document.querySelectorAll('a');
        links.forEach(function(link) {
            link.addEventListener('click', function() {
                var type = this.id;
                clearConcert();
                if (type === 'all') {
                    viewBusking(data);
                    viewSmallConcert(data);
                    viewLocalFest(data);
                } else if (type === 'busking') {
                    viewBusking(data);
                } else if (type === 'smallconcert') {
                    viewSmallConcert(data);
                } else if (type === 'localfestival') {
                    viewLocalFest(data);
                } else if (type === 'favorite') {
                    // 즐겨찾기 뷰 함수 호출
                }
            });
        });

    }
};
xhr.send();

function clearConcert() {
    var concert = document.getElementById('concert');
    while (concert.firstChild) {
        concert.removeChild(concert.firstChild);
    }

    for (var i = 0; i < markers.length; i++) {
        markers[i].setMap(null);
    }
    markers = [];
}

function viewBusking(data){
    for (var i = 0; i < data.busking.length; i++) {
        var record = data.busking[i];
        console.log(record);
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
                    markers.push(marker);

                    // 마커에 표시할 인포윈도우를 생성
                    var infowindow = new kakao.maps.InfoWindow({
                        content: record.name // 인포윈도우에 표시할 내용
                    });

                    // 마커에 클릭 이벤트를 등록
                    kakao.maps.event.addListener(marker, 'mouseover', clickMakerListener(map, marker, infowindow));
                    kakao.maps.event.addListener(marker, 'mouseout', clickMapListener(infowindow));
                    kakao.maps.event.addListener(marker, 'click', modalBusking(record));

                    var li = document.createElement('li');
                    li.id=record.id;
                    var startDate = new Date(record.startDate);
                    var lastDate = new Date(record.lastDate);
                    li.innerHTML = '<h3>' + record.name + '</h3>' +
                        '<p><strong>장소: </strong>' + record.location + '</p>' +
                        '<p><strong>공연 일자: </strong>' + formatDate(date) + '</p>' +
                        '<p><strong>공연자: </strong>' + record.artName + '</p>';
                    li.style.padding='18px';
                    li.style.borderTop = '1px solid #ccc';
                    concert.appendChild(li);

                    li.addEventListener('click',function(){
                        goToBuskingDetail(record.id);
                    })
                }
            });
        })(record);
    }
}

function viewSmallConcert(data){
    for (var i = 0; i < data.smallConcert.length; i++) {
        var record = data.smallConcert[i];
        console.log(record);
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
                    markers.push(marker);

                    // 마커에 표시할 인포윈도우를 생성
                    var infowindow = new kakao.maps.InfoWindow({
                        content: record.name // 인포윈도우에 표시할 내용
                    });

                    // 마커에 클릭 이벤트를 등록
                    kakao.maps.event.addListener(marker, 'mouseover', clickMakerListener(map, marker, infowindow));
                    kakao.maps.event.addListener(marker, 'mouseout', clickMapListener(infowindow));
                    kakao.maps.event.addListener(marker, 'click', modalSmall(record));

                    var li = document.createElement('li');
                    li.id=record.id;
                    var startDate = new Date(record.startDate);
                    var lastDate = new Date(record.lastDate);
                    li.innerHTML = '<h3>' + record.name + '</h3>' +
                        '<p><strong>장소: </strong>' + record.location + '</p>' +
                        '<p><strong>공연 일자: </strong>' + formatDate(startDate) + ' ~ ' + formatDate(lastDate) + '</p>' +
                        '<p><strong>공연자: </strong>' + record.pname + '</p>';
                    li.style.padding='18px';
                    li.style.borderTop = '1px solid #ccc';
                    concert.appendChild(li);

                    li.addEventListener('click',function(){
                        goToSmallConcertDetail(record.id);
                    })
                }
            });
        })(record);
    }
}

function viewLocalFest(data){
    for (var i = 0; i < data.localFestival.length; i++) {
        var record = data.localFestival[i];
        console.log(record);
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
                    markers.push(marker);

                    // 마커에 표시할 인포윈도우를 생성
                    var infowindow = new kakao.maps.InfoWindow({
                        content: record.name // 인포윈도우에 표시할 내용
                    });

                    // 마커에 클릭 이벤트를 등록
                    kakao.maps.event.addListener(marker, 'mouseover', clickMakerListener(map, marker, infowindow));
                    kakao.maps.event.addListener(marker, 'mouseout', clickMapListener(infowindow));
                    kakao.maps.event.addListener(marker, 'click', modalLocal(record));

                    var li = document.createElement('li');
                    li.id=record.id;
                    var startDate = new Date(record.startDate);
                    var lastDate = new Date(record.lastDate);
                    li.innerHTML = '<h3>' + record.name + '</h3>' +
                        '<p><strong>장소: </strong>' + record.location + '</p>' +
                        '<p><strong>개최 일자: </strong>' + formatDate(startDate) + ' ~ ' + formatDate(lastDate) + '</p>' +
                        '<p><strong>주최측: </strong>' + record.org + '</p>';
                    li.style.padding='18px';
                    li.style.borderTop = '1px solid #ccc';
                    concert.appendChild(li);

                    li.addEventListener('click',function(){
                        goToLocalDetail(record.id);
                    })
                }
            });
        })(record);
    }
}




// 클릭 이벤트 핸들러 함수
function modalBusking(record) {
    return function() {
        // 모달 창에 정보를 채움
        var modalTitle = document.querySelector('.modal-title');
        modalTitle.textContent = record.name;

        var modalBody = document.querySelector('.modal-body');
        modalBody.innerHTML = '<p><strong>버스킹 장소: </strong>' + record.location + '</p>' +
            '<p><strong>공연 일자: </strong>' + formatDate(new Date(record.startDate)) + '</p>' +
            '<p><strong>공연자: </strong>' + record.artName + '</p>';

        // 모달 창을 띄움
        $('#kt_modal_1').modal('show');

        //모달 창에서 상세보기 클릭 시 상세보기 페이지로 이동
        $('#viewDetail').on('click', function() {
            var id = record.id;
            goToBuskingDetail(id);
        });
    };
}

// 클릭 이벤트 핸들러 함수
function modalLocal(record) {
    return function() {
        // 모달 창에 정보를 채움
        var modalTitle = document.querySelector('.modal-title');
        modalTitle.textContent = record.name;

        var modalBody = document.querySelector('.modal-body');
        modalBody.innerHTML = '<p><strong>개최 장소: </strong>' + record.location + '</p>' +
            '<p><strong>개최 일자: </strong>' + formatDate(new Date(record.startDate)) + ' - ' +
            formatDate(new Date(record.lastDate)) + '</p>' +
            '<p><strong>주최측: </strong>' + record.org + '</p>';

        // 모달 창을 띄움
        $('#kt_modal_1').modal('show');

        //모달 창에서 상세보기 클릭 시 상세보기 페이지로 이동
        $('#viewDetail').on('click', function() {
            var id = record.id;
            goToLocalDetail(id);
        });
    };
}

// 클릭 이벤트 핸들러 함수
function modalSmall(record) {
    return function() {
        // 모달 창에 정보를 채움
        var modalTitle = document.querySelector('.modal-title');
        modalTitle.textContent = record.name;

        var modalBody = document.querySelector('.modal-body');
        modalBody.innerHTML = '<p><strong>개최 장소: </strong>' + record.location + '</p>' +
            '<p><strong>공연 일자: </strong>' + formatDate(new Date(record.startDate)) + ' - ' +
            formatDate(new Date(record.lastDate)) + '</p>' +
            '<p><strong>공연자: </strong>' + record.pname + '</p>';

        // 모달 창을 띄움
        $('#kt_modal_1').modal('show');

        //모달 창에서 상세보기 클릭 시 상세보기 페이지로 이동
        $('#viewDetail').on('click', function() {
            var id = record.id;
            goToDetailPage(id);
        });
    };
}

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

function goToSmallConcertDetail(id){

    var detailPageUrl = '/smallconcert/detail/' + id;

    window.location.href = detailPageUrl;
}

//지역축제 디테일 페이지
function goToLocalDetail(id){
    var detailPageUrl = '/local_festival/detail/' + id;

    window.location.href = detailPageUrl;
}

//버스킹 디테일 페이지
function goToBuskingDetail(id){
    var detailPageUrl = '/busking/detail/' + id;

    window.location.href = detailPageUrl;
}