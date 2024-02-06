document.addEventListener("DOMContentLoaded", function() {
  const inputFields = document.querySelectorAll(".input-area");
  const saveBtn = document.querySelector(".saveBtn");

  inputFields.forEach(function(inputField) {
    inputField.addEventListener("input", function() {
      const allFieldsValid = Array.from(inputFields).every(function(field) {
        // input 및 textarea 필드가 비어있지 않은지 확인
        return field.tagName !== 'SELECT' || !isNaN(field.value.trim());
      });

      saveBtn.disabled = !allFieldsValid;
    });
  });

    const selectYear = document.getElementById("select-year");
    const selectMonth = document.getElementById("select-month");
    const selectDay = document.getElementById("select-day");

    // 년도
    const currentYear = new Date().getFullYear();
    for (let year = currentYear; year <= currentYear + 3; year++) {
    const option = new Option(year, year);
    selectYear.appendChild(option);
    }

    // 월 옵션 생성
    selectYear.addEventListener("change", function() {
    const selectedYear = selectYear.value;
    const currentMonth = new Date().getMonth() + 1; // 현재 월 (1월부터 시작)

    // 월 옵션 초기화
    selectMonth.innerHTML = '<option selected>월</option>';

    // 현재 년도와 선택한 년도가 같으면 현재 월부터 생성
    // 그렇지 않으면 1월부터 생성
    const startMonth = (selectedYear == currentYear) ? currentMonth : 1;

    for (let month = startMonth; month <= 12; month++) {
      const option = new Option(month, month);
      selectMonth.appendChild(option);
    }

    // 일 옵션은 나중에 선택된 년과 월에 따라 생성하도록 함수 호출
    lastday();
    });

    // 초기화 시에도 월 옵션 생성
    selectYear.dispatchEvent(new Event('change'));

    // 초기화 시에도 일 옵션 생성 (현재 날짜 기준)
    lastday();
    });

    function lastday() {
    const selectYear = document.getElementById("select-year");
    const selectMonth = document.getElementById("select-month");
    const selectDay = document.getElementById("select-day");

    const selectedYear = selectYear.value;
    const selectedMonth = selectMonth.value;
    const lastDay = new Date(selectedYear, selectedMonth, 0).getDate();

    // 기존 일 옵션 제거
    selectDay.innerHTML = '<option selected>일</option>';

    // 일 옵션 생성
    const currentDay = new Date().getDate(); // 현재 일
    const startDay = (selectedYear == new Date().getFullYear() && selectedMonth == new Date().getMonth() + 1) ? currentDay +1: 1;
    for (let day = startDay; day <= lastDay; day++) {
    const option = new Option(day, day);
    selectDay.appendChild(option);
    }

    // 모달 열기 버튼에 클릭 이벤트 리스너 추가
    const detailButtons = document.querySelectorAll(".detailBtn");
    const modalBg = document.querySelector(".modal-bg");
    const modal = document.querySelector(".modal");

    detailButtons.forEach(function(button) {
      button.addEventListener("click", function() {
        // 클릭한 버튼의 데이터 속성을 이용하여 숙제 정보 가져오기
        const taskTitle = button.parentElement.parentElement.querySelector(".list_name").textContent;
        const taskContent = button.parentElement.parentElement.querySelector(".list_content").textContent;
        const taskProgress = button.parentElement.parentElement.querySelector(".list_progress").textContent;
        const taskDeadline = button.parentElement.parentElement.querySelector(".list_limit").textContent;

        // 모달 내용 업데이트
        const modalTitle = modal.querySelector("#modal-task-title");
        modalTitle.textContent = taskTitle;
        const modalContent = modal.querySelector(".modal-content");
        modalContent.querySelector("#modal-task-content").textContent = taskContent;
        modalContent.querySelector("#modal-task-chapter").textContent = taskProgress;
        modalContent.querySelector("#modal-task-deadline").textContent = taskDeadline;

        // 모달 및 배경 표시
        modal.style.display = "block";
        modalBg.style.display = "block";
      });
    });


    // 모달 배경 또는 취소 버튼을 클릭하면 모달 닫기
    modalBg.addEventListener("click", function() {
      modal.style.display = "none";
      modalBg.style.display = "none";
    });

    const cancelButton = modal.querySelector(".cancel");
    cancelButton.addEventListener("click", function() {
      modal.style.display = "none";
      modalBg.style.display = "none";
    });
}
