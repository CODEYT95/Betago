document.addEventListener('DOMContentLoaded', function() {
    const checkboxes = document.querySelectorAll('.checkbox-input');
    const paymentButton = document.getElementById('paymentButton');

    // 체크박스 중 하나라도 체크되어 있다면 true를 반환, 아니면 false를 반환
    function isAnyCheckboxChecked() {
        return Array.from(checkboxes).some(checkbox => checkbox.checked);
    }

    // 체크박스의 상태에 따라 버튼 활성화/비활성화 설정
    function updatePaymentButtonState() {
        if (isAnyCheckboxChecked()) {
            paymentButton.removeAttribute('disabled');
        } else {
            paymentButton.setAttribute('disabled', 'disabled');
        }
    }

    // 체크박스 상태 변경 시 함수 호출
    checkboxes.forEach((checkbox) => {
        checkbox.addEventListener('change', updatePaymentButtonState);
    });
    // 초기 로드 시 한번 확인
    updatePaymentButtonState();
});