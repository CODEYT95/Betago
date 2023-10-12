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
});
