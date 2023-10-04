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

document.getElementById("submitbutton").addEventListener("click", function() {
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

function validatePhone(phone) {
    if (!/^(010)\d{8}$/.test(phone)) {
        return false;
    }

    return /^\d+$/.test(phone);
}

function validatePayDepositor(depositor) {
    return /^[가-힣a-zA-Z]+$/.test(depositor) && depositor.length >= 2;
}
