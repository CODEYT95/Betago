document.addEventListener("DOMContentLoaded", function() {
  // 필드와 버튼 요소 가져오기
  var groupNameInput = document.querySelector(".group-name");
  var groupPeopleSelect = document.querySelector(".group-people");
  var startDateInput = document.querySelector(".startDate");
  var endDateInput = document.querySelector(".endDate");
  var submitButton = document.getElementById("submitButton");

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
      submitButton.style.backgroundColor = "blue";
    }
  }

  // 필드 값 변경 이벤트 리스너 등록
  groupNameInput.addEventListener("input", checkFields);
  groupPeopleSelect.addEventListener("change", checkFields);
  startDateInput.addEventListener("input", checkFields);
  endDateInput.addEventListener("input", checkFields);

  // 페이지 로드 시 초기 필드 확인 실행
  checkFields();

  // 버튼 클릭 시 필드가 비어있는지 확인하고 알림 표시
  submitButton.addEventListener("click", function(e) {
    var groupName = groupNameInput.value.trim();
    var groupPeople = groupPeopleSelect.value.trim();
    var startDate = startDateInput.value;
    var endDate = endDateInput.value;

    if (groupName === "" || groupPeople === "" || startDate === "" || endDate === "") {
      e.preventDefault(); // 기본 동작 중단
      alert("기타 항목을 제외한 나머지 항목을 채워주세요");
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
});
