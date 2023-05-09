(function () {
    'use strict';

    // Fetch all the forms we want to apply custom Bootstrap validation styles to
    var forms = document.querySelectorAll('.needs-validation');

    // Loop over them and prevent submission
    Array.prototype.slice.call(forms).forEach(function (form) {
        form.addEventListener('submit', function (event) {
            if (!form.checkValidity()) {
                event.preventDefault();
                event.stopPropagation();
            }

            form.classList.add('was-validated');
        }, false);

        // 이름 유효성 검사
        const nameInput = document.querySelector('input[name="name"]');
        const nameRegExp = /^[가-힣]+$/;
        nameInput.addEventListener('blur', function (event) {
            if (nameInput.value.trim() === '') {
                nameInput.classList.add('is-invalid');
                if (nameInput.nextElementSibling !== null) {
                    nameInput.nextElementSibling.style.display = 'block';
                    nameInput.nextElementSibling.nextElementSibling.style.display = 'none';
                }
            } else if (!nameRegExp.test(nameInput.value)) {
                nameInput.classList.add('is-invalid');
                if (nameInput.nextElementSibling !== null) {
                    nameInput.nextElementSibling.style.display = 'none';
                    nameInput.nextElementSibling.nextElementSibling.style.display = 'block';
                }
            } else {
                nameInput.classList.remove('is-invalid');
                if (nameInput.nextElementSibling !== null) {
                    nameInput.nextElementSibling.style.display = 'none';
                    nameInput.nextElementSibling.nextElementSibling.style.display = 'none';
                }
            }
        });

        // 이메일 유효성 검사
        const emailInput = document.querySelector('input[name="email"]');
        const emailRegExp = /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/;
        emailInput.addEventListener('blur', function (event) {
            if (emailInput.value.trim() === '') {
                emailInput.classList.add('is-invalid');
                if (emailInput.nextElementSibling !== null) {
                    emailInput.nextElementSibling.style.display = 'block';
                    emailInput.nextElementSibling.nextElementSibling.style.display = 'none';
                }
            } else if (!emailRegExp.test(emailInput.value)) {
                emailInput.classList.add('is-invalid');
                if (emailInput.nextElementSibling !== null) {
                    emailInput.nextElementSibling.style.display = 'none';
                    emailInput.nextElementSibling.nextElementSibling.style.display = 'block';
                }
            } else {
                emailInput.classList.remove('is-invalid');
                if (emailInput.nextElementSibling !== null) {
                    emailInput.nextElementSibling.style.display = 'none';
                    emailInput.nextElementSibling.nextElementSibling.style.display = 'none';
                }
            }
        });
    });
})();

async function findUserId(event) {
    event.preventDefault();

    const form = document.getElementById("login_id_reset_form");
    if (!form.checkValidity()) {
        form.classList.add("was-validated");
        return;
    }

    const formData = new FormData(form);
    const name = formData.get("name");
    const email = formData.get("email");

    try {
        const response = await fetch("/findUserId", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({name, email}),
        });

        if (response.ok) {
            const userIds = await response.json();
            if (userIds.length > 0) {
                const userIdList = userIds.join(", ");
                $('#result_message').text(`찾은 아이디: ${userIdList}`);
                $('#result_modal').modal('show');
            } else {
                $('#error_message').text("해당하는 아이디가 없습니다.");
                $('#error_modal').modal('show');
            }
        } else {
            $('#error_message').text("서버 오류가 발생했습니다. 다시 시도해 주세요.");
            $('#error_modal').modal('show');
        }
    } catch (error) {
        $('#error_message').text("요청 중 오류가 발생했습니다. 다시 시도해 주세요.");
        $('#error_modal').modal('show');
    }
}
