document.getElementById('kt_aside_toggler').addEventListener('click', function() {
    // 사이드바 내용이 나타나거나 사라지도록 토글 로직 구현
    var sidebarContent = document.getElementById('sidebarContent');
    if (sidebarContent.style.display === 'none') {
        sidebarContent.style.display = 'block';
    } else {
        sidebarContent.style.display = 'none';
    }
});