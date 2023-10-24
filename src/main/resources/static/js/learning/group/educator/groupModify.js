document.addEventListener("DOMContentLoaded", function() {
  // 필드와 버튼 요소 가져오기
  var groupNameInput = document.querySelector(".group-name");
  var groupPeopleSelect = document.querySelector(".group-people");
  var startDateInput = document.querySelector(".startDate");
  var endDateInput = document.querySelector(".endDate");
  var submitButton = document.getElementById("submitButton");
  var groupIntroTextarea = document.querySelector(".content");
  var startDateInput = document.getElementById("startDate");
  var currentDate = new Date().toISOString().split("T")[0];

  // min 속성을 현재 날짜로 설정합니다.
  startDateInput.min = currentDate;


  // 게임 옵션 설정
  var gameTotalElement = document.querySelector(".totalNum");
  var gameNowCntElement = document.querySelector(".currentNum");
  var select = document.querySelector(".group-people");

  var gameTotal = parseInt(gameTotalElement.innerText);
  var gameNowCnt = parseInt(gameNowCntElement.innerText);

  for (var i = 1; i <= (gameTotal - gameNowCnt); i++) {
    var option = document.createElement("option");
    option.text = i + "명";
    option.value = i;
    select.appendChild(option);
  }
  var gameStartInput = document.querySelector(".game_start");
  var gameEndInput = document.querySelector(".game_end");

  // 시작일과 종료일 값을 가져온 후, 해당 값으로 범위를 지정합니다.
  var gameStartDate = gameStartInput.value;
  var gameEndDate = gameEndInput.value;

  document.querySelector(".startDate").min = gameStartDate;
  document.querySelector(".startDate").max = gameEndDate;
  document.querySelector(".endDate").min = gameStartDate;
  document.querySelector(".endDate").max = gameEndDate;

  // 초기 필드 상태 확인 함수
  function checkFields() {
    var groupName = groupNameInput.value.trim();
    var groupPeople = groupPeopleSelect.value.trim();
    var startDate = startDateInput.value;
    var endDate = endDateInput.value;

    if (groupName === "" || groupPeople === "" || startDate === "" || endDate === "") {
      // 필드가 비어있을 경우 버튼 비활성화 및 스타일 변경
      submitButton.disabled = true;
      submitButton.style.backgroundColor = "gray";
    } else {
      // 필드가 모두 채워져 있을 경우 버튼 활성화 및 스타일 변경
      submitButton.disabled = false;
      submitButton.style.backgroundColor = "#007bff";
      submitButton.style.color = "white";
    }
  }

  // 필드 값 변경 이벤트 리스너 등록
  groupNameInput.addEventListener("input", checkFields);
  groupPeopleSelect.addEventListener("change", checkFields);
  startDateInput.addEventListener("input", checkFields);
  endDateInput.addEventListener("input", checkFields);
  groupIntroTextarea.addEventListener("input", checkFields);


  var submitButton = document.getElementById("submitButton");
    var completeModal = document.getElementById("completeModal");
    var confirmButton = document.getElementById("confirmButton");

    // 폼 제출 함수
    function submitForm() {
      // 폼 요소 가져오기
      var form = document.querySelector("form");

      // 폼을 수동으로 제출
      form.submit();
    }

    // 확인 버튼을 클릭했을 때 모달을 닫고 폼을 제출
    confirmButton.addEventListener("click", function() {
      completeModal.style.display = "none"; // 모달 숨기기
      submitForm(); // 폼 제출
    });

    // 버튼 클릭 시 모달 띄우기
    submitButton.addEventListener("click", function(e) {
      var groupName = groupNameInput.value.trim();
      var groupPeople = groupPeopleSelect.value.trim();
      var startDate = startDateInput.value;
      var endDate = endDateInput.value;
      var content = groupIntroTextarea.value;

      if (groupName === "" || groupPeople === "" || startDate === "" || endDate === "" || content === "") {
        alert("모든 항목을 채워 주세요");
        e.preventDefault();
      } else {
        // 필드가 모두 채워져 있을 경우 모달 띄우기
        completeModal.style.display = "block"; // 모달 표시
      }
    });
    document.addEventListener("DOMContentLoaded", function() {
      // 필요한 요소 가져오기
      var submitButton = document.getElementById("submitButton");
      var completeModal = document.getElementById("completeModal");
      var confirmButton = document.getElementById("confirmButton");

      // "학습 수정하기" 버튼 클릭 이벤트 처리
      submitButton.addEventListener("click", function(e) {
        e.preventDefault(); // 폼 제출 막기

        // 모달 띄우기
        completeModal.style.display = "block"; // 모달 표시
      });
    });
});
