document.addEventListener('DOMContentLoaded', function (events) {
function fetchData() {
    var startDate = document.getElementById("startDate").value;
    var endDate = document.getElementById("endDate").value;

    // startDate, endDate 중 하나라도 비어있을 경우에 알림 메시지 표시 및 폼 제출 방지
    if (!startDate || !endDate) {
        alert("날짜를 선택해주세요.");
        event.preventDefault(); // 폼의 기본 제출을 방지
        return; // 이후 로직 실행을 중단
    }

    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4 && xhr.status == 200) {
            var response = JSON.parse(xhr.responseText);

            // 조회 결과를 동적으로 표시
            updateGameList(response);
        }
    };
   var url = "http://localhost:8800/list?startDate=" + encodeURIComponent(startDate) + "&endDate=" + encodeURIComponent(endDate);
   xhr.open("GET", url, true);
   xhr.send();
}
// start date input 요소 가져오기
    const startDateInput = document.getElementById('startDate');
    // end date input 요소 가져오기
    const endDateInput = document.getElementById('endDate');

    // start date input의 변경 이벤트에 대한 핸들러 등록
    startDateInput.addEventListener('change', function() {
        // start date input의 값을 가져오기
        const startDateValue = startDateInput.value;

        // end date input의 최솟값을 start date로 설정
        endDateInput.setAttribute('min', startDateValue);

        // start date가 변경될 때 end date를 업데이트하는 추가적인 로직을 여기에 작성할 수 있어
        // 예를 들어, 특정 기간 이후의 날짜만 end date로 선택 가능하게 하는 등의 조건을 설정할 수 있어
    });