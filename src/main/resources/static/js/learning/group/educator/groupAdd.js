document.addEventListener("DOMContentLoaded", function() {
  // 필드와 버튼 요소 가져오기
  var groupNameInput = document.querySelector(".group-name");
  var groupPeopleSelect = document.querySelector(".group-people");
  var startDateInput = document.querySelector(".startDate");
  var endDateInput = document.querySelector(".endDate");
  var submitButton = document.getElementById("submitButton");
  var groupIntroTextarea = document.querySelector(".content");

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

  // 페이지 로드 시 초기 필드 확인 실행
  checkFields();

  // 버튼 클릭 시 필드가 비어있는지 확인하고 알림 표시
  submitButton.addEventListener("click", function(e) {
    var groupName = groupNameInput.value.trim();
    var groupPeople = groupPeopleSelect.value.trim();
    var startDate = startDateInput.value;
    var endDate = endDateInput.value;
    var content = groupIntroTextarea.value.trim();

    if (groupName === "" || groupPeople === "" || startDate === "" || endDate === "" || content === "") {
      alert("모든 항목을 채워 주세요");
      e.preventDefault();
    }
  });

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

});
