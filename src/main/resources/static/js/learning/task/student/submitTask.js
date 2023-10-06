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

    // 숙제하기 버튼에 클릭 이벤트 핸들러 추가
    var selectButtons = document.querySelectorAll(".selectBtn");

    selectButtons.forEach(function(button) {
        button.addEventListener("click", function() {
            // 숙제하기 버튼을 클릭한 경우
            var modal = document.getElementById("myModal"); // 모달 요소 가져오기
            var modalBg = document.querySelector(".modal-bg"); // 모달 배경 요소 가져오기
            var tasksendNo = button.getAttribute("data-tasksend-no"); // 데이터 속성에서 숙제 번호 가져오기

            console.log(tasksendNo);
            // 모달 내부에 숙제 번호 표시
            var modalTaskNo = document.querySelector("#myModal input[name='tasksend_no']");
            modalTaskNo.value = tasksendNo;

            // 모달과 모달 배경을 보이게 설정
            modal.style.display = "block";
            modalBg.style.display = "block";
        });
    });

    // 모달 닫기 버튼에 이벤트 리스너 추가
    var closeModalButtons = document.querySelectorAll(".conceal");
    closeModalButtons.forEach(function(button) {
        button.addEventListener("click", function() {
            var modal = document.getElementById("myModal"); // 모달 요소 가져오기
            var modalBg = document.querySelector(".modal-bg"); // 모달 배경 요소 가져오기

            // 모달과 모달 배경을 숨기게 설정
            modal.style.display = "none";
            modalBg.style.display = "none";
        });
    });

});
