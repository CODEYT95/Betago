 document.addEventListener('DOMContentLoaded', function() {
        let subscriptionDuration = document.getElementById('game_date');
        let gameTotal = document.getElementById('game_total');
        let gamePrice = document.getElementById('game_price');
        let gameSell = document.getElementById('game_sell');
        let gameDiscount = document.getElementById('game_discount');

        let durationDiscountRates = {
                1: 0, 2: 2, 3: 4, 4: 6, 5: 8, 6: 10, 7: 12,
                8: 14, 9: 16, 10: 18, 11: 20, 12: 22
            };
            let totalDiscountRates = {
                        10: 3, 20: 6, 30: 9, 40: 12, 50: 15, 60: 18
            };
         function updateDiscount() {
                   let durationDiscount = durationDiscountRates[subscriptionDuration.value] || 0;
                   let totalDiscount = totalDiscountRates[gameTotal.value] || 0;
                   gameDiscount.value = durationDiscount + totalDiscount;
               }

               function updateSellPrice() {
                   updateDiscount();
                   let discountRate = parseFloat(gameDiscount.value) / 100;
                   let originalPrice = parseFloat(gamePrice.value) || 0;
                   let discountedPrice = originalPrice * (1 - discountRate);
                   gameSell.value = discountedPrice.toFixed();  // 소수점 둘째자리까지 표시
               }

               subscriptionDuration.addEventListener('change', updateSellPrice);
               gameTotal.addEventListener('change', updateSellPrice);
               gamePrice.addEventListener('input', updateSellPrice); // 이 부분이 정가 input box에서 값이 변경될 때마다 리스너를 추가하는 부분입니다.
           });
           // validateForm 함수를 이벤트 리스너 밖에 추가합니다.
           function validateForm() {
               // 각 필드의 값을 가져옵니다.
               var game_title = document.getElementById("game_title").value;
               var game_level = document.getElementById("game_level").value;
               var game_date = document.getElementById("game_date").value;
               var game_total = document.getElementById("game_total").value;
               var game_price = document.getElementById("game_price").value;
               var game_discount = document.getElementById("game_discount").value;
               var game_sell = document.getElementById("game_sell").value;
               var game_content = document.getElementById("game_content").value;

               // 필요한 필드가 비어있는지 확인합니다.
               if(!game_title || !game_level || !game_date || !game_total || !game_price || !game_discount || !game_sell || !game_content) {
                   alert("모두 입력해주세요.");
                   return false;  // 폼 제출을 중단합니다.
               }

               return true;  // 폼 제출을 계속합니다.
           }

function displayFileName(input) {
    var uploadName = document.querySelector('.upload-name');
    if (input.files.length > 0) {
    // 파일이 선택되었을 때 파일명을 입력란에 표시
        uploadName.value = input.files[0].name;
    } else {
     // 파일 선택이 해제되었을 때 입력란 초기화
        uploadName.value = '';
    }
}

