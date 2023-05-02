var buskerInfo = $('#buskerInfo');
var url = location.pathname;
var id = url.match(/\d+/)[0];

$.ajax({
    url: '/busking/detail/'+id+'/donation/json',
    dataType: 'json',
    success: function(data) {
        var li = $('<li>');
        console.log(data.name);

        li.append($('<h2>').html(data.name));
        li.append($('<h2>').html('아티스트 : ' + data.nid));

        buskerInfo.append(li);

        // 첫 번째 Ajax 호출이 완료된 후에 두 번째 Ajax 호출을 실행
        $('#pay').click(function (){
            localStorage.setItem('price',priceValue);
            localStorage.setItem('id',id);
            $.ajax({
                url:'/pay/kakao/donation',
                dataType: 'json',
                success:function (data){
                    var box = data.next_redirect_pc_url;
                    window.location.href = box;
                },
                error:function (error){
                    alert(error);
                }
            });
        });
    },
    error: function(xhr, status, error) {
        console.log('AJAX Error: ' + status + error);
    }
});

const price = document.querySelector('#price');
const output = document.querySelector('.output');
let priceValue;
const resetButton = document.querySelector("#reset");
resetButton.addEventListener('click',function (){
    price.value = '';
    output.textContent = '후원금액 : ';
});

const searchButton = document.querySelector("#search-btn");
searchButton.addEventListener('click', function (){
    priceValue = price.value;
    output.textContent = '후원금액 : ' + priceValue + '원';
});
