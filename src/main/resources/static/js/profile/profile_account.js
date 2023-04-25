function setSearchFields() {
    const searchFieldDropdown = document.getElementById("searchFieldDropdown");
    const searchValue = document.getElementById("searchValue");
    const column = document.getElementById("column");
    const keyword = document.getElementById("keyword");

    column.value = searchFieldDropdown.value;
    keyword.value = searchValue.value;
}

function resetFormAndRedirect() {
    document.querySelector('form[action="/profile/admin/gender/search"]').reset();
    window.location.href = '/profile/admin/gender';
}