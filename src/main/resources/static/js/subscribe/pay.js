function handleCheckboxChange(checkbox) {
    // 모든 체크박스 가져오기
    var checkboxes = document.querySelectorAll('.checkbox');

    // 선택된 체크박스가 아니면 나머지 체크박스 해제
    checkboxes.forEach(function (currentCheckbox) {
        if (currentCheckbox !== checkbox) {
            currentCheckbox.checked = false;
        }
    });
 }
function updateTotalPrice() {
    // 선택된 게임의 인덱스 가져오기
    var selectedGameIndex = document.querySelector('.mypaylist').selectedIndex;

    // 해당 인덱스의 게임 정보 가져오기
    var selectedGame = document.querySelectorAll('.mypaylist option')[selectedGameIndex];
    var selectedGamePrice = selectedGame.dataset.price;

    // 결과를 화면에 출력
    var totalPriceInput = document.querySelector('.order-total');
    totalPriceInput.value = selectedGamePrice;
}

//발리데이션

document.addEventListener("DOMContentLoaded", function() {
    // 이벤트 리스너 등록 및 기타 작업들
    var submitButton = document.getElementById("submitbutton");
    submitButton.addEventListener("click", function(event) {
    var checkboxes = document.getElementsByName("pay_type");
    var isChecked = false;

    // 최소 한 개의 checkbox가 선택되었는지 확인
    for (var i = 0; i < checkboxes.length; i++) {
        if (checkboxes[i].checked) {
            isChecked = true;
            break;
        }
    }

    // 최소 한 개의 checkbox가 선택되지 않은 경우
    if (!isChecked) {
        alert("결제수단을 선택해주세요");
        return false;
    }

    // 추가로 필요한 로직을 여기에 추가할 수 있어
    // 예를 들어, 서버로 데이터 전송 등

    // 유효성 검사 함수 호출
    if (!validateForm()) {
        // 유효성 검사에서 실패한 경우 추가로 실행할 로직을 여기에 추가할 수 있어
        return false;
    }

    // 성공적으로 모든 유효성 검사를 통과한 경우
    // 여기에 추가로 실행할 로직을 추가할 수 있어

    return true;
});

function validateForm() {
    var buyerName = document.getElementById("buyer-name").value;
    var buyerPhone = document.getElementById("buyer-phone").value;
    var payDepositor = document.getElementById("payname").value;

    // 구매자명 유효성 검사
    if (!validateName(buyerName)) {
        document.getElementById("buyer-name-error").innerHTML = "다시 입력해주세요. (올바른 이름을 입력하세요)";
        isNameValid = false;
    } else {
        document.getElementById("buyer-name-error").innerHTML = "";
        isNameValid = true;
    }

    // 연락처 유효성 검사
    if (!validatePhone(buyerPhone)) {
        document.getElementById("buyer-phone-error").innerHTML = "다시 입력해주세요. (올바른 연락처를 입력하세요)";
        isPhoneValid = false;
    } else {
        document.getElementById("buyer-phone-error").innerHTML = "";
        isPhoneValid = true;
    }

    // 입금자명 유효성 검사
    if (!validatePayDepositor(payDepositor)) {
        document.getElementById("pay-name-error").innerHTML = "다시 입력해주세요. (한글 또는 영어 2글자 이상)";
        isDepositorValid = false;
    } else {
        document.getElementById("pay-name-error").innerHTML = "";
        isDepositorValid = true;
    }

    // 결제수단 유효성 검사
    var checkboxes = document.getElementsByName("pay_type");
    var isChecked = false;

    // 최소 한 개의 checkbox가 선택되었는지 확인
    for (var i = 0; i < checkboxes.length; i++) {
        if (checkboxes[i].checked) {
            isChecked = true;
            break;
        }
    }

    // 최소 한 개의 checkbox가 선택되지 않은 경우
    if (!isChecked) {
        alert("결제수단을 선택해주세요");
        return false;
    }

    return isNameValid && isPhoneValid && isDepositorValid;
}

// 구매자명, 입금자명 유효성 검사 함수
function validateName(name) {
    return /^[가-힣a-zA-Z]+$/.test(name) && name.length >= 2;
}

function validatePayDepositor(depositor) {
    return /^[가-힣a-zA-Z]{2,}$/.test(depositor);
}

function validatePhone(phone) {
    if (!/^(010)\d{8}$/.test(phone)) {
        return false;
    }

    return /^\d+$/.test(phone);
}
}); // 이 부분에 주석을 닫아주어야 합니다.

function handleCheckboxChange(checkbox) {
    if (checkbox.checked) {
        const gameTitle = checkbox.getAttribute('data-game-title');
        const gamePrice = checkbox.getAttribute('data-game-price');
        const gameNo = checkbox.getAttribute('data-game-no');

        // sessionStorage에 선택한 게임 정보 저장
        sessionStorage.setItem('selectedGameNo', gameNo);
        sessionStorage.setItem('selectedGameTitle', gameTitle);
        sessionStorage.setItem('selectedGamePrice', gamePrice);
    }
}

document.addEventListener('DOMContentLoaded', function() {
    const selectedGameNo = sessionStorage.getItem('selectedGameNo');
    const selectedGameTitle = sessionStorage.getItem('selectedGameTitle');
    const selectedGamePrice = sessionStorage.getItem('selectedGamePrice');

    if (selectedGameNo && selectedGameTitle && selectedGamePrice) {
        // 원하는 방식으로 선택한 게임 정보를 사용
        console.log('Selected Game Title:', selectedGameTitle);
        console.log('Selected Game Price:', selectedGamePrice);

        // 저장된 정보를 사용한 후 sessionStorage에서 삭제
        sessionStorage.removeItem('selectedGameNo');
        sessionStorage.removeItem('selectedGameTitle');
        sessionStorage.removeItem('selectedGamePrice');
    }
});


////////////////////////////////////////////////////////////////////////
document.addEventListener('DOMContentLoaded', function() {
    var totalPrice = 0;
    var gameNos = [];

    //delete-btn 클릭 시 가격 업데이트 및 태그 삭제
    document.querySelectorAll('.delete-btn').forEach(function(button) {
        gameNos.push(button.getAttribute('data-game-no'));
        button.addEventListener('click', deleteTag);
    });

    document.querySelectorAll('.price').forEach(function(input) {
        totalPrice += parseFloat(input.value);
    });

    updateTotalPrice();

    //태그 삭제 하고 url에 넣을 game_no배열 업데이트하는 함수
    function deleteTag(event) {
        event.preventDefault();

        const tag = event.target.closest('.tag');
        if (tag) {
            var deletedGamePriceInput = tag.querySelector('input[hidden]');
            var deletedGamePrice = parseFloat(deletedGamePriceInput.value);

            const gameNo = tag.querySelector('.delete-btn').getAttribute('data-game-no');

            const index = gameNos.indexOf(gameNo);
            if (index > -1) {
                gameNos.splice(index, 1);
            }

            tag.remove();

            totalPrice -= deletedGamePrice;

           updateTotalPrice();
           updateUrl();
        }
    }
     //태그 삭제시 url 업데이트 해주는 함수
     function updateUrl() {
         let newUrl = 'http://localhost:8800/cartList?game_nos=' + gameNos.join(',');
         window.history.pushState({}, null, newUrl);
     }
     //가격 업데이트 해주는 함수
     function updateTotalPrice() {
         var productListSpan = document.querySelector('span[name=productName].mypaylist');
         productListSpan.textContent = Math.round(totalPrice) + "원";
     }
});
