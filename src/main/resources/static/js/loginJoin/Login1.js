




















(function () {
    'use strict'

    // Fetch all the forms we want to apply custom Bootstrap validation styles to
    var forms = document.querySelectorAll('.needs-validation')

    // Loop over them and prevent submission
    Array.prototype.slice.call(forms)
        .forEach(function (form) {
            form.addEventListener('submit', function (event) {

                if (!form.checkValidity()) {
                    event.preventDefault()
                    event.stopPropagation()
                }
                const prevUrl = window.sessionStorage.getItem("prevUrl");
                window.sessionStorage.removeItem("prevUrl");
                form.setAttribute('action', '/login-common?prevUrl=' + encodeURIComponent(prevUrl));



                form.classList.add('was-validated')
            }, false)

            // 입력창에서 포커스가 빠져나갈 때마다 유효성 검사를 수행
            form.querySelectorAll('.form-control').forEach(function (input) {
                input.addEventListener('blur', function (event) {
                    // 유효성 검사 추가
                    const idRegExp = /^[A-Za-z0-9]+$/;

                    if (input.value.trim() === '') {
                        input.classList.add('is-invalid');
                        input.nextElementSibling.style.display = 'block';
                        input.nextElementSibling.nextElementSibling.style.display = 'none';
                    } else if (!idRegExp.test(input.value)) {
                        input.classList.add('is-invalid');
                        input.nextElementSibling.style.display = 'none';
                        input.nextElementSibling.nextElementSibling.style.display = 'block';
                    } else {
                        input.classList.remove('is-invalid');
                        input.nextElementSibling.style.display = 'none';
                        input.nextElementSibling.nextElementSibling.style.display = 'none';
                    }
                });
            });
        });
})();


//로그인 실패시 팝업
$(document).ready(function(){
    const loginError = sessionStorage.getItem("loginError");
    if (loginError) {
        alert(loginError);
        sessionStorage.removeItem("loginError");
    }
});

$(function () {
    // 알림창 닫기
    $('.alert .btn-close').click(function () {
        $(this).closest('.alert').fadeOut();
    });
});

/*
//그래프 그리는 부분
const data = [
    { month: 'Jan', sales: 100, target: 80 },
    { month: 'Feb', sales: 200, target: 150 },
    { month: 'Mar', sales: 300, target: 280 },
    { month: 'Apr', sales: 400, target: 380 },
    { month: 'May', sales: 500, target: 450 },
];
const months = data.map(item => item.month);
const sales = data.map(item => item.sales);
const targets = data.map(item => item.target);
const ctx = document.getElementById('myChart').getContext('2d');
const myChart = new Chart(ctx, {
    type: 'bar',
    data: {
        labels: months,
        datasets: [
            {
                label: 'Sales',
                data: sales,
                backgroundColor: 'rgba(75, 192, 192, 0.2)',
                borderColor: 'rgba(75, 192, 192, 1)',
                borderWidth: 1,
                type: 'bar', // 막대 그래프
            },
            {
                label: 'Target',
                data: targets,
                backgroundColor: 'rgba(255, 99, 132, 0.2)',
                borderColor: 'rgba(255, 99, 132, 1)',
                borderWidth: 1,
                type: 'line', // 곡선 그래프
                tension: 0.4,
            },
        ],
    },
    options: {
        scales: {
            y: {
                beginAtZero: true,
            },
            x: {
                // x축 설정을 추가하여 막대 그래프와 곡선 그래프를 동시에 표시
                type: 'category',
                offset: true,
            },
        },
    },
});*/

const data = [
    { category: 'A', value: 100 },
    { category: 'B', value: 200 },
    { category: 'C', value: 300 },
    { category: 'D', value: 400 },
];

const categories = data.map(item => item.category);
const values = data.map(item => item.value);

const ctx = document.getElementById('myChart').getContext('2d');
const myChart = new Chart(ctx, {
    type: 'doughnut', // pie원형 , doughnut 도넛 차트 유형 설정
    data: {
        labels: categories,
        datasets: [
            {
                data: values,
                backgroundColor: [
                    'rgba(255, 99, 132, 0.2)',
                    'rgba(54, 162, 235, 0.2)',
                    'rgba(255, 206, 86, 0.2)',
                    'rgba(75, 192, 192, 0.2)',
                ],
                borderColor: [
                    'rgba(255, 99, 132, 1)',
                    'rgba(54, 162, 235, 1)',
                    'rgba(255, 206, 86, 1)',
                    'rgba(75, 192, 192, 1)',
                ],
                borderWidth: 1,
            },
        ],
    },
});
