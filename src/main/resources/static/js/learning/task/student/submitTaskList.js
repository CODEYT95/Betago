document.addEventListener("DOMContentLoaded", function () {

  //숙제 상태에 대한 버튼
  const taskStateElements = document.querySelectorAll('.task-state .data');
  const writeButtons = document.querySelectorAll('.modifyBtn');

  for (let i = 0; i < taskStateElements.length; i++) {
      const taskState = taskStateElements[i].textContent.trim();
      if (taskState === '제출완료') {
          modifyButtons[i].style.display = 'block';

      }
  }
});
