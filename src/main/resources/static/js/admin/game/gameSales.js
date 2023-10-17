document.addEventListener('DOMContentLoaded', function() {
    var searchButton = document.getElementById('searchButton');
    searchButton.addEventListener('click', function() {
        var period = document.querySelector('input[name="period"]:checked').value;
        var startDate = document.getElementById('game_startsearch').value;
        var endDate = document.getElementById('game_endsearch').value;

        fetchDataFromServer(period, startDate, endDate);
    });
});

function fetchDataFromServer(period, startDate, endDate) {
    var serverUrl = '/game/gameSales';
    $.ajax({
        url: serverUrl,
        type: 'GET',
        data: {
            period: period,
            startDate: startDate,
            endDate: endDate
        },
        dataType: 'json',
        success: function(data) {
            updateTable(data);
        },
        error: function() {
            alert('데이터를 불러오는데 실패했습니다.');
        }
    });
}
function updateTable(data) {
    var dataList = document.getElementById('data-list');
    dataList.innerHTML = '';
    data.forEach(function(item, index) {
        var newRow = document.createElement('div');
        newRow.classList.add('row');
        newRow.innerHTML = `
            <span class="cell">${index + 1}</span>
            <span class="cell">${item.date}</span>
            <span class="cell">${item.sales_count}</span>
            <span class="cell">${item.sales_amount}</span>
        `;
        dataList.appendChild(newRow);
    });
}