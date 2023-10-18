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

 var submitButton = document.getElementById("submitbutton");
    submitButton.addEventListener("click", function(event) {
        if (!validateForm()) {
            event.preventDefault(); // 폼 전송 이벤트를 중단
            return false;
        }
        return true;
    });
});

function handleNavClick(paymentMethod, clickedElement) {
    const paymentDisplay = document.getElementById('selectedPaymentMethod');
    const payType = document.getElementById('pay_type');
    payType.value = paymentMethod;
    paymentDisplay.textContent = paymentMethod;

    var navItems = document.querySelectorAll('.navbar a');

    for (var i = 0; i < navItems.length; i++) {
        if (navItems[i] === clickedElement) {
            navItems[i].classList.add('selected');
        } else {
            navItems[i].classList.remove('selected');
        }
    }
}

function prevImage() {
    const posters = document.querySelectorAll('.poster');
    if (posters.length === 0) return;
    posters[currentImageIndex].style.opacity = '0';
    currentImageIndex = (currentImageIndex - 1 + posters.length) % posters.length;
    posters[currentImageIndex].style.opacity = '1';
}

function nextImage() {
    const posters = document.querySelectorAll('.poster');
    if (posters.length === 0) return;
    posters[currentImageIndex].style.opacity = '0';
    currentImageIndex = (currentImageIndex + 1) % posters.length;
    posters[currentImageIndex].style.opacity = '1';
}

function validateForm() {
    var buyerName = document.getElementById("buyer-name").value;
    var buyerPhone = document.getElementById("buyer-phone").value;
    var payDepositor = document.getElementById("payname").value;
    var payType = document.getElementById("pay_type").value;

    // 결제 수단 선택 유효성 검사
    if (!payType) {
        alert("결제수단을 선택해 주세요.");
        return false;
    }

    // 구매자명 유효성 검사
    if (!validateName(buyerName)) {
        document.getElementById("buyer-name-error").innerHTML = "다시 입력해주세요. (올바른 이름을 입력하세요)";
        alert("구매자 이름을 올바르게 입력해주세요.");
        return false;
    } else {
        document.getElementById("buyer-name-error").innerHTML = "";
    }

    // 연락처 유효성 검사
    if (!validatePhone(buyerPhone)) {
        document.getElementById("buyer-phone-error").innerHTML = "다시 입력해주세요. (올바른 연락처를 입력하세요)";
        alert("구매자 연락처를 올바르게 입력해주세요.");
        return false;
    } else {
        document.getElementById("buyer-phone-error").innerHTML = "";
    }

    // 입금자명 유효성 검사
    if (!validatePayDepositor(payDepositor)) {
        document.getElementById("pay-name-error").innerHTML = "다시 입력해주세요. (한글 2글자 이상)";
        alert("입금자명을 올바르게 입력해주세요.");
        return false;
    } else {
        document.getElementById("pay-name-error").innerHTML = "";
    }

    return true;
}
function validateName(name) {
    return /^[가-힣]{2,}$/.test(name) && !/^[ㄱ-ㅎㅏ-ㅣ]+$/.test(name);
}

function validatePayDepositor(depositor) {
    return /^[가-힣a]{2,}$/.test(depositor);
}

function validatePhone(콜) {
    return /^(010)\d{8}$/.test(콜) && /^\d+$/.test(콜);
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



document.addEventListener('DOMContentLoaded', function() {
    let totalPrice = 0;
    let gameNos = [];

    //delete-btn 클릭 시 가격 업데이트 및 태그 삭제
    document.querySelectorAll('.delete-btn').forEach(function(button) {
        gameNos.push(button.getAttribute('data-game-no'));
        button.addEventListener('click', deleteTag);
    });

    document.querySelectorAll('.price').forEach(function(input) {
        totalPrice += parseFloat(input.value);
    });

    updateTotalPrice();

    // 태그 및 이미지 삭제
function deleteTag(event) {
    event.preventDefault();

    const tag = event.target.closest('.tag');
    if (tag) {
        // 가격 삭제 로직
        const deletedGamePriceInput = tag.querySelector('.price');
        const deletedGamePrice = parseFloat(deletedGamePriceInput.value);

        // 게임의 파일 이름을 추출
        const gameFileName = tag.getAttribute('data-game-name');

        const gameNo = tag.getAttribute('data-game-no');

        console.log(gameNo);
        // 태그 삭제
        tag.remove();

        // 해당하는 이미지 삭제 로직
         if (gameNo) {
                    // data-game-no 값을 사용하여 해당하는 이미지를 찾습니다.
                    const gameImage = document.querySelector(`.image-container .image[data-game-no='${gameNo}']`);
                    if (gameImage) {
                        gameImage.parentElement.remove(); // 이미지의 부모인 'poster' 클래스를 포함한 div도 함께 삭제
                    }
                }

        // 가격 갱신 로직
        totalPrice -= deletedGamePrice;
        updateTotalPrice();
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