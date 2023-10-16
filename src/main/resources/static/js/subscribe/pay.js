let currentImageIndex = 0;

function prevImage() {
    const posters = document.querySelectorAll('.poster');
    if (posters.length === 0) return;

    // 현재 이미지를 숨김
    posters[currentImageIndex].style.opacity = '0';

    // 이전 이미지의 인덱스를 계산
    currentImageIndex = (currentImageIndex - 1 + posters.length) % posters.length;

    // 이전 이미지를 표시
    posters[currentImageIndex].style.opacity = '1';
}

function nextImage() {
    const posters = document.querySelectorAll('.poster');
    if (posters.length === 0) return;

    // 현재 이미지를 숨김
    posters[currentImageIndex].style.opacity = '0';

    // 다음 이미지의 인덱스를 계산
    currentImageIndex = (currentImageIndex + 1) % posters.length;

    // 다음 이미지를 표시
    posters[currentImageIndex].style.opacity = '1';
}

// 페이지 로딩 시 첫 번째 이미지만 표시하고 나머지는 숨김
document.addEventListener('DOMContentLoaded', function() {
    const posters = document.querySelectorAll('.poster');
    if (posters.length > 0) {
        posters[0].style.opacity = '1';
    }
});
function handleNavClick(paymentMethod, clickedElement) {
    // 선택한 결제 방법을 표시하는 요소를 가져옵니다.
    const paymentDisplay = document.getElementById('selectedPaymentMethod');
    const payType = document.getElementById('pay_type');
    payType.value = paymentMethod;
    paymentDisplay.textContent = paymentMethod;

    // 모든 navbar의 a 태그들에 대하여 반복
    var navItems = document.querySelectorAll('.navbar a');

    for (var i = 0; i < navItems.length; i++) {
        // 선택된 항목에 'selected' 클래스를 추가하고 나머지 항목에서는 삭제
        if (navItems[i] === clickedElement) {
            navItems[i].classList.add('selected');
        } else {
            navItems[i].classList.remove('selected');
        }
    }

    // 여기에 다른 로직 (예: 이미지 변경)을 추가할 수 있습니다.
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
    console.log(totalPriceInput);
}

//발리데이션

document.addEventListener("DOMContentLoaded", function() {
    // 이벤트 리스너 등록 및 기타 작업들
    var submitButton = document.getElementById("submitbutton");
    submitButton.addEventListener("click", function(event) {
    var checkboxes = document.getElementsByName("pay_type");
    var isChecked = false;

    // 유효성 검사 함수 호출
    if (!validateForm()) {
        // 유효성 검사에서 실패한 경우 추가로 실행할 로직을 여기에 추가할 수 있어
        return false;
    }

    // 성공적으로 모든 유효성 검사를 통과한 경우
    // 여기에 추가로 실행할 로직을 추가할 수 있어

    return true;
});
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
        document.getElementById("pay-name-error").innerHTML = "다시 입력해주세요. (한글 2글자 이상)";
        isDepositorValid = false;
    } else {
        document.getElementById("pay-name-error").innerHTML = "";
        isDepositorValid = true;
    }

    return isNameValid && isPhoneValid && isDepositorValid;
}

// 구매자명, 입금자명 유효성 검사 함수
function validateName(name) {
    return /^[가-힣]{2,}$/.test(name) && !/^[ㄱ-ㅎㅏ-ㅣ]+$/.test(name);
}


function validatePayDepositor(depositor) {
    return /^[가-힣a]{2,}$/.test(depositor);
}

function validatePhone(콜) {
    if (!/^(010)\d{8}$/.test(콜)) {
        return false;
    }

    return /^\d+$/.test(콜);
}


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
         let newUrl = 'http://localhost:8800/pay/cartList?game_nos=' + gameNos.join(',');
         window.history.pushState({}, null, newUrl);
     }
     //가격 업데이트 해주는 함수
     function updateTotalPrice() {
         var productListSpan = document.querySelector('span[name=productName].mypaylist');
         productListSpan.textContent = Math.round(totalPrice) + "원";
     }
     function submitPayment() {
         alert('결제가 완료되었습니다.');
         document.querySelector('.pay-list').submit();
     }

     //뒤로가기 클릭 시 게임 콘텐츠 목록 페이지로 바로 이동
     window.onpopstate = function(event) {
         window.location.href = "http://localhost:8800/game/list";
     };
});