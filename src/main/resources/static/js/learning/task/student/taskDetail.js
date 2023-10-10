document.addEventListener('DOMContentLoaded', function () {
    // 감시 대상 요소
    const chapterInput = document.getElementById('tasksubmit_chapter');
    const contentTextarea = document.getElementById('tasksubmit_content');
    const saveBtn = document.querySelector('.saveBtn');

    // input 요소 또는 textarea 요소 중 하나라도 내용이 변경될 때 활성화 상태를 업데이트
    chapterInput.addEventListener('input', toggleSaveBtn);
    contentTextarea.addEventListener('input', toggleSaveBtn);

    function toggleSaveBtn() {
        // 내용이 모두 비어 있지 않으면 버튼 활성화
        if (chapterInput.value.trim() !== '' && contentTextarea.value.trim() !== '') {
            saveBtn.removeAttribute('disabled');
        } else {
            saveBtn.setAttribute('disabled', 'disabled');
        }
    }
});

function goBack() {
    window.history.back();
}