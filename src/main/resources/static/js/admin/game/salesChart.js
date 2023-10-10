     //html이 뜨자마자 ajax 실행
       getChartDataAjax();

       //----------함수 선언 ----------------

       // 차트를 그릴 데이터를 가져오는 함수
       function getChartDataAjax(){
           $.ajax({
               url: '', //요청경로
               type: 'post',
               async: true,
               contentType: "application/x-www-form-urlencoded; charset=UTF-8",
               data: { }, //필요한 데이터
               success: function(result) {
                   alert('성공');
                   console.log(result);

                   //차트 그리기
                   drawChart(result);
               },
               error: function() {
                   alert('실패ㅠㅠ');
               }
           });

       }


    // 차트를 그릴 영역을 dom요소로 가져온다.
    function drawChart(data){
    var chartArea = document.getElementById('salesChart').getContext('2d');
    // Chart.js를 사용하여 차트를 생성한다.
    var salesChart = new Chart(chartArea, {
        // 차트의 종류(String)
        type: 'bar', // 차트 종류를 선택하세요. (예: 'bar', 'line', 'pie' 등)
        // 차트의 데이터(Object)
        data: {
            //x축에 들어갈 이름들(Array)
            labels: ['1월', '2월', '3월', '4월', '5월', '6월'
                    , '7월', '8월', '9월', '10월', '11월', '12월'],
            // 실제 차트에 표시할 데이터들(Array), dataset객체들을 담고 있다.
            datasets: [{
                // dataset의 이름(String)
                label: '매출금액',
                // dataset값(Array) y축 데이터
                data: [12, 19, 3, 5, 2, 3],
                // dataset의 배경색(rgba값을 String으로 표현)
                backgroundColor: 'rgba(255, 99, 132, 0.2)',
                // dataset의 선 색(rgba값을 String으로 표현)
                borderColor: 'rgba(255, 99, 132, 1)',
                // dataset의 선 두께(Number)
                borderWidth: 1,
                yAxisId: 'y'
            } ,
              {
                label: '판매건수',
                type: 'line',
                data: [3, 22, 7, 6, 6, 9],
                backgroundColor:'rgba(97, 237, 235, 0.2)',
                borderColor:'rgba(97, 237, 235,1)',
                borderWidth:3,
                yAxisId: 'y1'
              }
            ]
       } ,
        // 차트의 설정(Object)
        options: {
            // 축에 관한 설정(Object)
            scales: {
                // y축에 대한 설정(Object)
                y: {
                    // 시작을 0부터 하게끔 설정(최소값이 0보다 크더라도)(boolean)
                    beginAtZero: true,
                    type: 'linear',
                    display: true,
                    position: 'left'
                },
                y1: {
                  beginAtZero: true,
                  type: 'linear',
                  display: true,
                  position: 'right'
                }
            }
        }
    });
    }

