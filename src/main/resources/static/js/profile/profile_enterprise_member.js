let currentCategory = 'common';
getMembers(currentCategory);

document.getElementById('commonBtn').addEventListener('click', () => {
    currentCategory = 'common';
    getMembers(currentCategory);
});

document.getElementById('artistBtn').addEventListener('click', () => {
    currentCategory = 'artist';
    getMembers(currentCategory);
});

document.getElementById('enterpriseBtn').addEventListener('click', () => {
    currentCategory = 'enterprise';
    getMembers(currentCategory);
});

document.getElementById('searchBtn').addEventListener('click', () => {
    const searchColumn = document.getElementById('searchColumn').value;
    const searchKeyword = document.getElementById('searchKeyword').value;

    getMembers(currentCategory, 1, searchColumn, searchKeyword);
});

function getMembers(category, page = 1, searchColumn = null, searchKeyword = '') {
    let url = `/profile/ent/total?category=${category}&page=${page}`;
    if (searchColumn && searchKeyword) {
        url += `&column=${searchColumn}&keyword=${searchKeyword}`;
    }

    fetch(url, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        },
    })
        .then((response) => {
            if (response.ok) {
                return response.json();
            }
            throw new Error('응답에 문제가 있습니다.');
        })
        .then((data) => {
            showColumns(category);
            displayMembers(data, category, page);
        })
        .catch((error) => {
            console.error('fetch 작동에 문제가 있습니다.', error);
        });
}

    function displayMembers(members, category, currentPage) {
        const memberTbody = document.getElementById('memberTbody');
        memberTbody.innerHTML = '';

        const membersPerPage = 5;
        const start = (currentPage - 1) * membersPerPage;
        const end = start + membersPerPage;

        members.slice(start, end).forEach((member) => {
            const memberRow = memberTbody.insertRow();

            switch (category) {
                case 'common':
                    // 일반 회원에 대한 데이터를 생성
                    memberRow.insertCell().textContent = member.id;
                    memberRow.insertCell().textContent = member.nid;
                    memberRow.insertCell().textContent = member.sex;
                    memberRow.insertCell().textContent = member.birth;
                    memberRow.insertCell().textContent = member.genre;
                    break;

                case 'artist':
                    // 아티스트 회원에 대한 데이터를 생성
                    memberRow.insertCell().textContent = member.id;
                    memberRow.insertCell().textContent = member.nid;
                    memberRow.insertCell().textContent = member.sex;
                    memberRow.insertCell().textContent = member.birth;
                    memberRow.insertCell().textContent = member.genre;
                    memberRow.insertCell().textContent = member.point;
                    break;

                case 'enterprise':
                    // 기업 회원에 대한 데이터를 생성
                    memberRow.insertCell().textContent = member.id;
                    memberRow.insertCell().textContent = member.name;
                    memberRow.insertCell().textContent = member.email;
                    memberRow.insertCell().textContent = member.phone;
                    break;

                default:
                    break;
            }
        });

        createPagination(members.length, membersPerPage, currentPage, category);
    }

    function showColumns(category) {
        const columns = document.querySelectorAll('th');
        const categoryColumns = document.querySelectorAll(`th.${category}`);

        // 모든 컬럼을 숨깁니다.
        for (let i = 0; i < columns.length; i++) {
            columns[i].style.display = 'none';
        }

        // 선택한 행사의 컬럼만 표시합니다.
        for (let i = 0; i < categoryColumns.length; i++) {
            categoryColumns[i].style.display = 'table-cell';
        }
    }

    function createPagination(totalMembers, membersPerPage, currentPage, category) {
        const totalPages = Math.ceil(totalMembers / membersPerPage);
        const paginationEl = document.querySelector('.pagination');

        paginationEl.innerHTML = '';

        for (let i = 1; i <= totalPages; i++) {
            const li = document.createElement('li');
            const a = document.createElement('a');
            a.href = '#';
            a.textContent = i;
            if (i === currentPage) {
                li.classList.add('active');
            }
            a.addEventListener('click', (event) => {
                event.preventDefault();
                getMembers(category, i);
                scrollToTop();
            });
            li.appendChild(a);
            paginationEl.appendChild(li);
        }
    }

function showColumns(category) {
    const columns = document.querySelectorAll('th');
    const categoryColumns = document.querySelectorAll(`th.${category}`);

    // 모든 컬럼을 숨깁니다.
    for (let i = 0; i < columns.length; i++) {
        columns[i].style.display = 'none';
    }

    // 선택한 행사의 컬럼만 표시합니다.
    for (let i = 0; i < categoryColumns.length; i++) {
        categoryColumns[i].style.display = 'table-cell';
    }

    // 드롭다운 옵션을 업데이트합니다.
    updateDropdownOptions(category);
}

function updateDropdownOptions(category) {
    const options = document.querySelectorAll('#searchColumn option');
    const categoryOptions = document.querySelectorAll(`#searchColumn option.${category}`);

    // 모든 옵션을 숨깁니다.
    for (let i = 0; i < options.length; i++) {
        options[i].style.display = 'none';
    }

    // 선택한 카테고리의 옵션만 표시합니다.
    for (let i = 0; i < categoryOptions.length; i++) {
        categoryOptions[i].style.display = 'block';
    }

    // 첫 번째 표시된 옵션을 선택합니다.
    const firstVisibleOption = document.querySelector(`#searchColumn option.${category}`);
    if (firstVisibleOption) {
        firstVisibleOption.selected = true;
    }
}

