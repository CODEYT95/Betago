document.addEventListener("DOMContentLoaded", function () {
  const submitBtn = document.getElementById("submitButton");
  const checkboxes = document.querySelectorAll(".checkbox-input");

  // 체크박스 상태 확인 및 버튼 활성화/비활성화
  checkboxes.forEach(function (checkbox) {
    checkbox.addEventListener("change", function () {
      let isAnyCheckboxChecked = false;

      checkboxes.forEach(function (checkbox) {
        if (checkbox.checked) {
          isAnyCheckboxChecked = true;
        }
      });

      if (isAnyCheckboxChecked) {
        submitBtn.removeAttribute("disabled");
      } else {
        submitBtn.setAttribute("disabled", "disabled");
      }
    });
  });
  //숙제 상태에 대한 버튼
  const taskStateElements = document.querySelectorAll('.task-state .data');
  const writeButtons = document.querySelectorAll('.writeBtn');
  const modifyButtons = document.querySelectorAll('.modifyBtn');

  // 모든 task-state 요소를 확인하고 작성중인 경우 수정 버튼을 표시
  for (let i = 0; i < taskStateElements.length; i++) {
      const taskState = taskStateElements[i].textContent.trim();
      if (taskState === '작성중') {
          writeButtons[i].style.display = 'none';
          modifyButtons[i].style.display = 'block';

      }
  }
});
