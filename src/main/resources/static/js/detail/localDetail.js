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

        // 이미지 URL을 가져온다.
        var imageUrl = data.image;

        // 이미지 요소를 생성한다.
        var img = document.createElement("img");
        img.src = imageUrl;
        img.style.objectFit = "contain";
        img.style.width = "100%";
        img.style.height = "100%";

        // 이미지 요소를 포함할 div를 찾는다.
        var posterContainer = document.getElementById("posterContainer");

        // div에 이미지 요소를 추가한다.
        posterContainer.appendChild(img);

    },
    error: function(xhr, status, error) {
        console.log('AJAX Error: ' + status + error);
    }
});

