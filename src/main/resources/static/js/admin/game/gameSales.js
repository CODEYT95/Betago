document.addEventListener('DOMContentLoaded', function() {
 // 데이터 불러오기 버튼에 이벤트 리스너 추가
    var searchButton = document.getElementById('searchButton');
    searchButton.addEventListener('click', function() {
        // 선택한 조회 기간과 기간값 가져오기
        var period = document.querySelector('input[name="period"]:checked').value;
        var startDate = document.getElementById('game_startsearch').value;
        var endDate = document.getElementById('game_endsearch').value;

        // 서버에 데이터 요청 보내기
        fetchDataFromServer(period, startDate, endDate);
    });
});

function fetchDataFromServer(period, startDate, endDate) {
    // AJAX를 사용하여 서버에 요청을 보냅니다.
    // 여기서는 서버로부터 예제 데이터를 받아옵니다.
    $.ajax({
        url: '/api/sales-data', // 실제 서버 엔드포인트를 지정하세요.
        type: 'GET',
        data: {
            period: period,
            startDate: startDate,
            endDate: endDate
        },
        dataType: 'json',
        success: function(data) {
            // 서버로부터 받은 데이터를 가지고 그래프를 그립니다.
            drawChart(data);
        },
        error: function() {
            alert('데이터를 불러오는데 실패했습니다.');
        }
    });
}
