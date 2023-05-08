$(document).ready(function() {
    getCommonMember();
});

$('#commonBtn').on('click', function() {
    $('#common').show();
    $('#enter').hide();
    $('#artist').hide();

    $('#artistSpan').hide();

    getCommonMember();
});

$('#artistBtn').on('click', function() {
    $('#common').hide();
    $('#enter').hide();
    $('#artist').show();

    $('#artistSpan').show();
    $('#enterSpan').hide();

    getArtistMember();
});

$('#enterpriseBtn').on('click', function() {
    $('#common').hide();
    $('#enter').show();
    $('#artist').hide();

    $('#artistSpan').hide();
    $('#enterSpan').show();

    const ctx = document.getElementById('myChart').getContext('2d');
    if (Chart.getChart("myChart")) {
        Chart.getChart("myChart")?.destroy();
    }
    getEnterMember();

});

//일반회원 데이터 불러오기
function getCommonMember() {
    $.ajax({
        url: '/profile/ent/total/commonMember',
        type: 'GET',
        success: function(response) {
            drawGenderChart(response);
            $('#genderButton').on('click', function() {
                drawGenderChart(response);
            });
            $('#genreButton').on('click', function() {
                drawGenreChart(response);
            });
        },
        error: function(error) {
            console.log(error);
        },
    });
}

//아티스트회원 데이터 불러오기
function getArtistMember(){
    $.ajax({
        url: '/profile/ent/total/artistMember',
        type: 'GET',
        success: function(response) {
            drawGenderChart(response);
            $('#avg').hide();
            $('#genderBtn').on('click', function() {
                $('#avg').hide();
                drawGenderChart(response);
            });
            $('#genreBtn').on('click', function() {
                $('#avg').hide();
                drawGenreChart(response);
            });
        },
        error: function(error) {
            console.log(error);
        },
    });
}

//기업회원 데이터 불러오기
function getEnterMember() {
    $.ajax({
        url: '/profile/ent/total/enterMember',
        type: 'GET',
        success: function(response) {
            const ongoingConcert = response.concertIng[0].concert_ing;
            const conNowEl = document.getElementById('afterTodayConcert');
            conNowEl.innerHTML = `현재 진행 중인 공연 : ${ongoingConcert}`;

            const concertAll = response.concertAll[0].concert_all;
            const conEl = document.getElementById('concertCount');
            conEl.innerHTML = `전체 공연 수 : ${concertAll}`;
        },
        error: function(error) {
            console.log(error);
        },
    });
}

// 성별 그래프 그리기
function drawGenderChart(genderData) {
    const ctx = document.getElementById('myChart').getContext('2d');
    if (Chart.getChart("myChart")) {
        Chart.getChart("myChart")?.destroy();
    }

    const genderCounts = genderData.genderCounts;
    const genderDataArray = [0, 0];

    for (let i = 0; i < genderCounts.length; i++) {
        if (genderCounts[i].sex === '남자') {
            genderDataArray[0] = genderCounts[i].count; // 인덱스 0이 남성
        } else if (genderCounts[i].sex === '여자') {
            genderDataArray[1] = genderCounts[i].count; // 인덱스 1이 여성
        }
    }

    const genderChart = new Chart(ctx, {
        type: 'doughnut',
        data: {
            labels: ['여성', '남성'], // 라벨 순서를 변경
            datasets: [
                {
                    label: '성별',
                    data: genderDataArray,
                    backgroundColor: ['#FF6384', '#36A2EB'], // 그래프 색깔 순서를 변경
                    borderWidth: 1,
                },
            ],
        },
        options: {
            responsive: false
        }
    });
}

//장르 그래프 그리기
function drawGenreChart(genreData) {
    const ctx = document.getElementById('myChart').getContext('2d');
    if (Chart.getChart("myChart")) {
        Chart.getChart("myChart")?.destroy();
    }

    const genreCounts = genreData.genreCounts;
    const genreDataArray = [];

    for (let i = 0; i < genreCounts.length; i++) {
        if (['가요', '발라드', '힙합', '댄스', '기타'].includes(genreCounts[i].genre)) {
            genreDataArray.push(genreCounts[i].count);
        }
    }

    const genreChart = new Chart(ctx, {
        type: 'doughnut',
        data: {
            labels: ['가요', '발라드', '힙합', '댄스', '기타'],
            datasets: [
                {
                    label: '장르',
                    data: genreDataArray,
                    backgroundColor: [
                        '#FF6384',
                        '#4BC0C0',
                        '#FFCE56',
                        '#E7E9ED',
                        '#36A2EB'
                    ],
                    borderWidth: 1,
                },
            ],
        },
        options: {
            responsive: false
        }
    });
}


