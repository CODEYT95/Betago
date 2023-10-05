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


