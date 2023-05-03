(function () {
    "use strict";

    var forms = document.querySelectorAll(".needs-validation");

    Array.prototype.slice.call(forms).forEach(function (form) {
        form.addEventListener(
            "submit",
            function (event) {
                if (!form.checkValidity()) {
                    event.preventDefault();
                    event.stopPropagation();
                }
                const prevUrl = window.sessionStorage.getItem("prevUrl");
                window.sessionStorage.removeItem("prevUrl");
                form.setAttribute("action", "/login-common?prevUrl=" + encodeURIComponent(prevUrl));

                form.classList.add("was-validated");
            },
            false
        );

        form.querySelectorAll(".form-control").forEach(function (input) {
            input.addEventListener("blur", function (event) {
                const nameRegExp = /^[ㄱ-ㅎㅏ-ㅣ가-힣]+$/;

                if (input.name === "name") {
                    if (input.value.trim() === "") {
                        input.classList.add("is-invalid");
                        input.nextElementSibling.style.display = "block";
                    } else if (!nameRegExp.test(input.value)) {
                        input.classList.add("is-invalid");
                        input.nextElementSibling.style.display = "none";
                        input.nextElementSibling.nextElementSibling.style.display = "block";
                    } else {
                        input.classList.remove("is-invalid");
                        input.nextElementSibling.style.display = "none";
                        input.nextElementSibling.nextElementSibling.style.display = "none";
                    }
                }
            });
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
            body: JSON.stringify({ name, email }),
        });

        if (response.ok) {
            const userIds = await response.json();
            if (userIds.length > 0) {
                const userIdList = userIds.join(", ");
                alert(`찾은 아이디: ${userIdList}`);
            } else {
                alert("해당하는 아이디가 없습니다.");
            }
        } else {
            alert("서버 오류가 발생했습니다. 다시 시도해 주세요.");
        }
    } catch (error) {
        alert("요청 중 오류가 발생했습니다. 다시 시도해 주세요.");
    }
}
