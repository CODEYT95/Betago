 function displayFileName(input) {
            var uploadName = document.querySelector('.upload-name');
            if (input.files.length > 0) {
                uploadName.value = input.files[0].name;
            } else {
                uploadName.value = '';
            }
        }

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
        gameSell.value = discountedPrice.toFixed();
    }

    subscriptionDuration.addEventListener('change', updateSellPrice);
    gameTotal.addEventListener('change', updateSellPrice);
    gamePrice.addEventListener('input', updateSellPrice);

    document.getElementById("game_price").addEventListener('input', function() {
        this.value = this.value.replace(/[^0-9]/g, "");  // 숫자 외의 문자를 제거합니다.
    });

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
        // 판매가가 0원인지 확인합니다.
        var game_sell = parseFloat(document.getElementById("game_sell").value);
        if(game_sell === 0) {
            alert("판매가는 0원이 될 수 없습니다.");
            return false;  // 폼 제출을 중단합니다.
        }

    document.getElementById("game_content").addEventListener("input", function() {
        const currentLength = this.value.length;
        if (currentLength > 100) {
            this.value = this.value.substring(0, 100);  // 100자로 잘라냅니다.
        }
        const charCountSpan = document.getElementById("charCount");
        charCountSpan.textContent = `${this.value.length} / 100`;
    });
        document.querySelector("form[name='gameContent']").addEventListener('submit', function(event) {
            if (!validateForm()) {
                event.preventDefault();  // 폼 제출을 중지합니다.
            }
        });




    });


