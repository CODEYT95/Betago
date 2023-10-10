document.addEventListener('DOMContentLoaded', function () {

    // 클래스가 "checkbox-input"인 모든 체크박스 가져오기
    const checkboxes = document.querySelectorAll('.checkbox-input');
    const subscribeButton = document.getElementById('subscribeButton');

    // 어떤 체크박스라도 선택되었는지 확인
    function isAnyCheckboxChecked() {
        const checked = Array.from(checkboxes).some(checkbox => checkbox.checked);
        console.log('어떤 체크박스가 선택되었는가:', checked);
        return checked;
    }

    // 체크박스 상태에 따라 "subscribeButton"의 상태 업데이트
    function updateSubscribeButtonState() {
        console.log('버튼 상태 업데이트 중...');
        if (isAnyCheckboxChecked()) {
            subscribeButton.removeAttribute('disabled');
        } else {
            subscribeButton.setAttribute('disabled', 'disabled');
        }
    }

    // 체크박스 변경에 대한 이벤트 리스너 추가
    checkboxes.forEach((checkbox) => {
        checkbox.addEventListener('change', updateSubscribeButtonState);
    });

    // 초기 로드 시 버튼 상태 업데이트
    updateSubscribeButtonState();

    // 선택한 게임 번호를 저장할 배열
    const selectedGameNos = [];

    // 체크박스 변경에 대한 이벤트 리스너 추가하여 선택한 게임 번호 추적
    checkboxes.forEach((checkbox) => {
        checkbox.addEventListener('change', function () {
            const gameNo = checkbox.getAttribute('data-game-no');
            if (checkbox.checked) {
                selectedGameNos.push(Number(gameNo));
            } else {
                const index = selectedGameNos.indexOf(Number(gameNo));
                if (index !== -1) {
                    selectedGameNos.splice(index, 1);
                }
            }
        });
    });
});
    function submitForm() {
            document.getElementById("searchForm").submit();
        }

    //구독하기 - 도훈
    // 구독 버튼 클릭 시 처리
    subscribeButton.addEventListener('click', function () {
        const checkedCheckboxes = document.querySelectorAll('.checkbox-input:checked');
        const selectedGameNo = Array.from(checkedCheckboxes).map(cb => cb.getAttribute('data-game-no'));

        if (selectedGameNo.length > 0) {
            const gameNoParam = selectedGameNo.join(",");
            window.location.href = "/cartList?game_no=" + gameNoParam;
        } else {
            alert("게임을 선택하세요!");
        }
    });