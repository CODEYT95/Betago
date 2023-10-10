function submitForm() {
    document.getElementById("searchForm").submit();
}

//체크박스
    const checkboxes = document.querySelectorAll('.checkbox-input');
    const subscribeButton = document.getElementById('subscribeButton');

    // 체크박스 중 하나라도 체크되어 있다면 true를 반환, 아니면 false를 반환
function isAnyCheckboxChecked() {
    const checked = Array.from(checkboxes).some(checkbox => checkbox.checked);
    console.log('Any checkbox checked:', checked);
    return checked;
}
    // 체크박스의 상태에 따라 버튼 활성화/비활성화 설정
    function updateSubscribeButtonState() {
        console.log('Updating button state...');
        if (isAnyCheckboxChecked()) {
            subscribeButton.removeAttribute('disabled');
        } else {
            subscribeButton.setAttribute('disabled', 'disabled');
        }
    }
    // 체크박스 상태 변경 시 함수 호출
    checkboxes.forEach((checkbox) => {
        checkbox.addEventListener('change', updateSubscribeButtonState);
    });
    // 초기 로드 시 한 번 확인
    updateSubscribeButtonState();
    document.addEventListener('DOMContentLoaded', function() {
        // 선택한 체크박스의 값을 저장할 배열
        const selectedGameNos = [];

        // 체크박스 변경 시 배열에 추가 또는 제거
        const checkboxes = document.querySelectorAll('.checkbox-input');
        checkboxes.forEach((checkbox) => {
            checkbox.addEventListener('change', function() {
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
function goToPayList() {
    const selectedCheckboxes = document.querySelectorAll('.checkbox-input:checked');

    // 선택한 체크박스의 game_no 값을 배열로 가져옵니다.
    const selectedGameNos = Array.from(selectedCheckboxes).map(cb => cb.getAttribute('data-game-no'));

    if (selectedGameNos.length > 0) {
        // 배열을 콤마로 연결한 문자열로 변환합니다.
        const gameNosParam = selectedGameNos.join(",");

        window.location.href = `/paylist?game_nos=${gameNosParam}`;
    } else {
        alert("게임을 선택하세요!");
    }
}