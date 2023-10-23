 document.addEventListener("DOMContentLoaded", function () {
         // 데이터 요소 가져오기
        var dayValues = document.querySelectorAll('.data-value#dayValue');
        var totalSellValues = document.querySelectorAll('.data-value#totalSellValue');
        var totalSalesValues = document.querySelectorAll('.data-value#totalSalesValue');

        // 데이터 배열 초기화
        var years = [];
        var sales = [];
        var transactions = [];

        // 데이터 추출
        for (var i = 0; i < dayValues.length; i++) {
            years.push(dayValues[i].value);
            sales.push(totalSellValues[i].value);
            transactions.push(totalSalesValues[i].value);
            console.log(transactions);
        }
        // Google Charts 로드
        google.charts.load('current', { 'packages': ['bar'] });
        google.charts.setOnLoadCallback(drawChart);

        // 차트 그리기 함수
        function drawChart() {
            var data = google.visualization.arrayToDataTable([
                ['날짜', '매출액', '건수']
            ]);

            var totalSales = 0; // 매출액 총합 변수 초기화
            var totalTransactions = 0; // 건수 총합 변수 초기화

            for (var i = 0; i < years.length; i++) {
                var formattedSales = parseInt(sales[i]).toLocaleString(); // sales 값을 콤마가 있는 형태로 변환
                data.addRow([years[i], formattedSales+'원', transactions[i]+'건']);

                totalSales += parseInt(sales[i]); // 매출액 누적 합산
                totalTransactions += parseInt(transactions[i]); // 건수 누적 합산
            }

            document.querySelector('.info-container h3:nth-child(1) span').textContent = totalSales.toLocaleString(); // 매출액 총합 출력
            document.querySelector('.info-container h3:nth-child(2) span').textContent = totalTransactions.toLocaleString(); // 건수 총합 출력

            var options = {
            chart: {
                title: '',
            subtitle: ''
            },
            series: {
                0: { axis: '건수', annotations: { stemColor: 'none' } }
            },
            vAxes: {
                0: {
                    gridlines: {
                        count: 0
                    },
                }
            },
            hAxis: {
                format: 'none' // 가로축 포맷 지정
            }
    };

        var chart = new google.charts.Bar(document.getElementById('columnchart_material'));
        chart.draw(data, google.charts.Bar.convertOptions(options));
    }

        // 검색 버튼 이벤트 리스너 추가
        var searchBtn = document.querySelector(".search-btn");
        searchBtn.addEventListener("click", function () {
            var intervalRadio = document.querySelector('input[name="interval"]:checked');
            var startDateSingle = document.getElementById('startdate_single').value;
            var startDate = document.getElementById('startdate').value;
            var endDate = document.getElementById('enddate').value;

            var testURL = '/game/salesGraph?pay_date=' + encodeURIComponent(startDateSingle);

            if (intervalRadio && intervalRadio.value === 'month') {
                // 월단위 선택 시, startDate와 endDate를 URL에 추가
                testURL = '/game/salesGraph?pay_date=' + encodeURIComponent(startDate) + '&pay_enddate=' + encodeURIComponent(endDate);
            }

            window.location.href = testURL;
        });

        // 라디오 버튼 요소 가져오기
        var dayRadio = document.querySelector('input[value="day"]');
        var monthRadio = document.querySelector('input[value="month"]');

        // 날짜 입력 요소 가져오기
        var dateRange = document.getElementById('daterange');
        var startDateSingle = document.getElementById('startdate_single');
        var startDate = document.getElementById('startdate'); // startdate 입력 요소 가져오기
        var endDate = document.getElementById('enddate');     // enddate 입력 요소 가져오기

        // "일단위" 라디오 버튼에 대한 이벤트 리스너 추가
        dayRadio.addEventListener("change", function () {
            dateRange.style.display = 'none'; // 날짜 범위 숨기기
            startDateSingle.style.display = ''; // 단일 날짜 표시
        });

        // "월단위" 라디오 버튼에 대한 이벤트 리스너 추가
        monthRadio.addEventListener("change", function () {
            dateRange.style.display = ''; // 날짜 범위 표시
            startDateSingle.style.display = 'none'; // 단일 날짜 숨기기
        });

        // startdate 값이 변경될 때 enddate의 min 및 max 값을 설정
        startDate.addEventListener("input", function () {
            endDate.min = startDate.value;

            // 년도와 월을 분리
            var parts = startDate.value.split('-');
            var year = parseInt(parts[0]);
            var month = parseInt(parts[1]);

            // 년도를 1년 증가시키고 해당 날짜를 max로 설정
            var maxDate = new Date(year + 1, month - 1); // month는 0부터 시작하므로 -1
            var maxYear = maxDate.getFullYear();
            var maxMonth = String(maxDate.getMonth() + 1).padStart(2, '0'); // 월도 0부터 시작하므로 +1, 두 자리 숫자로 포매팅

            endDate.max = `${maxYear}-${maxMonth}`;
        });

        // 페이지 로드 시 초기 설정
        if (dayRadio.checked) {
            dateRange.style.display = 'none';
            startDateSingle.style.display = '';
        } else if (monthRadio.checked) {
            dateRange.style.display = '';
            startDateSingle.style.display = 'none';
        }
    });