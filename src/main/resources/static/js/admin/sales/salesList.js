document.addEventListener("DOMContentLoaded", function () {
        // 검색 버튼 이벤트 리스너 추가
        var searchBtn = document.querySelector(".search-btn");
        searchBtn.addEventListener("click", function () {
            var intervalRadio = document.querySelector('input[name="interval"]:checked');
            var startDateSingle = document.getElementById('startdate_single').value;
            var startDate = document.getElementById('startdate').value;
            var endDate = document.getElementById('enddate').value;

            var testURL = '/game/salesList?pay_date=' + encodeURIComponent(startDateSingle);

            if (intervalRadio && intervalRadio.value === 'month') {
                // 월단위 선택 시, startDate와 endDate를 URL에 추가
                testURL = '/game/salesList?pay_date=' + encodeURIComponent(startDate) + '&pay_enddate=' + encodeURIComponent(endDate);
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

        // 통화 형식 함수
        function formatCurrency(amount) {
            return new Intl.NumberFormat('ko-KR', { style: 'currency', currency: 'KRW' }).format(amount);
        }

        $(".detail-search").click(function () {
            // data-day-no 속성에서 값을 가져옵니다.
            var pay_date_string = $(this).find('i').attr('data-day-no');

            if (pay_date_string.length === 7) {
                pay_date2 = pay_date_string;
                pay_date_string = null;
            } else if (pay_date_string.length === 10) {
                pay_date2 = null;
            }

            console.log(pay_date_string);

            $.ajax({
                type: "POST",
                url: "/game/sales",
                data: {
                    pay_date: pay_date_string,
                    pay_date2: pay_date2,
                },
                success: function (response) {
                    console.log("AJAX 요청 성공:", response);

                    // 데이터를 사용하여 HTML을 업데이트
                    var memberList = $(".member-list");

                    $.each(response, function (index, groupItem) {
                        var li = $("<li>");
                        li.append("<span style='width: 10%;'>" + (index + 1) + "</span>");
                        li.append("<span style='width: 15%;'>" + groupItem.day + "</span>");
                        li.append("<span style='width: 20%;'>" + groupItem.member_name + "</span>");
                        li.append("<span style='width: 40%;'>" + groupItem.game_title + "</span>");

                        // groupItem.game_sell 값을 통화 형식으로 포맷하여 추가
                        li.append("<span style='width: 15%;'>" + formatCurrency(groupItem.game_sell) + "</span>");

                        memberList.append(li);
                    });

                    $("#modal").css("display", "block");
                },
                error: function (error) {
                    // AJAX 요청이 실패할 때 처리할 코드
                    console.error("AJAX 요청 실패:", error);
                }
            });
        });

        $(".close-modal").click(function () {
            // 모달 엘리먼트를 숨기는 처리
            $(".member-list").empty();
            $("#modal").css("display", "none");
        });

        var totalSellSum = 0;
        var totalSalesSum = 0;

        var dayListItems = document.querySelectorAll(".li-container");
        dayListItems.forEach(function (dayListItem) {
            var totalSellText = dayListItem.querySelector(".li-date span").textContent;
            var totalSalesText = dayListItem.querySelector(".li-groupTO span").textContent;

            // 콤마와 "원"을 제거한 후 숫자로 파싱
            var totalSell = parseFloat(totalSellText.replace(/[^0-9.-]+/g, ""));
            var totalSales = parseInt(totalSalesText.replace(/[^0-9]+/g, ""));

            if (!isNaN(totalSell)) {
                totalSellSum += totalSell;
            }

            if (!isNaN(totalSales)) {
                totalSalesSum += totalSales;
            }
        });

        // 총매출액과 건수 업데이트
        var totalSellElement = document.querySelector(".totalsell");
        var totalSalesElement = document.querySelector(".totalnum");

        // 통화 형식 함수 사용하여 업데이트
        totalSellElement.textContent = formatCurrency(totalSellSum) + "원";
        totalSalesElement.textContent = totalSalesSum + "건";
    });