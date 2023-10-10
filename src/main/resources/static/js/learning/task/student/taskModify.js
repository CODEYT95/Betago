document.addEventListener('DOMContentLoaded', function () {
    const chapterInput = document.getElementById('tasksubmit_chapter');
    const contentTextarea = document.getElementById('tasksubmit_content');
    const saveBtn = document.querySelector('.saveBtn');

    chapterInput.addEventListener('input', toggleSaveBtn);
    contentTextarea.addEventListener('input', toggleSaveBtn);

    function toggleSaveBtn() {
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