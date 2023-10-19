   // 페이지 로드가 완료되면 실행되는 스크립트
    document.addEventListener("DOMContentLoaded", function() {
        // 체크박스 관련 동작 설정
        document.querySelectorAll('.checkbox, .checkbox2').forEach(function(checkbox) {
            checkbox.addEventListener('change', function() {
                // 선택된 체크박스의 부모 노드 (tr) 찾기
                var row = this.closest('.tr-list');
                var approveCheckbox = row.querySelector('.checkbox');
                var rejectCheckbox = row.querySelector('.checkbox2');

                // 둘 다 선택되었는지 확인
                if (approveCheckbox.checked && rejectCheckbox.checked) {
                    alert('둘 중 하나만 체크가 가능합니다.');
                    // 현재 선택한 체크박스의 선택을 취소
                    this.checked = false;
                }
            });
        });

        // 선택된 그룹 번호를 저장하는 변수
        let selectedGroupNo = null;

        const groupMenu = document.querySelector(".select-menu-group");
        const selectGroupBtn = groupMenu.querySelector(".select-btn-group");
        const groupOptions = groupMenu.querySelectorAll(".option");
        const groupSBtnText = groupMenu.querySelector(".sBtn-text");
        const hiddenInput = document.querySelector("#groupNoInput"); // 숨겨진 입력 필드

        // 그룹 메뉴 열기/닫기 토글
        selectGroupBtn.addEventListener("click", () => groupMenu.classList.toggle("active"));

        groupOptions.forEach(option => {
            option.addEventListener("click", () => {
                let selectedOption = option.querySelector(".option-text").innerText;
                groupSBtnText.innerText = selectedOption;
                selectedGroupNo = option.querySelector(".option-text").getAttribute("data-group-no");
                hiddenInput.value = selectedGroupNo; // 숨겨진 입력 필드의 값을 바로 설정
                groupMenu.classList.remove("active");
            });
        });

        // 다른 영역 클릭 시 그룹 메뉴 닫기
        document.addEventListener('click', function(event) {
            if (!groupMenu.contains(event.target) && !selectGroupBtn.contains(event.target)) {
                groupMenu.classList.remove('active');
            }
        });
    });
    function saveData() {
    var approveCheckboxes = $('.checkbox:checked');
    var rejectCheckboxes = $('.checkbox2:checked');
    var group_no = document.getElementById('group_no').value;

    var approveData = approveCheckboxes.map(function () {
        return $(this).data('approve-no');
    }).get();

    var rejectData = rejectCheckboxes.map(function () {
        return $(this).data('reject-no');
    }).get();

    // 데이터 객체 생성
    var dataToSend = {
        'approve[]': approveData,
        'reject[]': rejectData,
        'group_no': group_no
    };

    var memberCountElement = document.getElementById('member_count');
    var groupCountElement = document.getElementById('group_cnt');

    if (groupCountElement && memberCountElement) {
        var memberCount = parseInt(memberCountElement.value);
        var groupCount = parseInt(groupCountElement.value);
        if (memberCount + approveData.length > groupCount) {
            alert("구독 가능 인원을 초과하였습니다. 구독 가능 인원을 확인해주세요.");
            return;
        }
    }
    // AJAX 요청 보내기
    $.ajax({
        type: 'POST',
        url: '/educator/group/approveUpdate',
        data: dataToSend,
        success: function (response) {
            openModal('joinCompleteModal');
        },
        error: function (xhr, status, error) {
            console.error(error);
            // 요청이 실패했을 때 처리할 작업
        }
    });
    }
    function openModal(modalId) {
    var modal = document.getElementById(modalId);
    modal.style.display = 'block';
}

function closeModal(modalId) {
    var modal = document.getElementById(modalId);
    modal.style.display = 'none';
}

window.addEventListener('DOMContentLoaded', function () {
  var closeButton = document.querySelector('.modal-btn-confirm');
  closeButton.addEventListener('click', function () {
      closeModal('joinCompleteModal');
      location.reload();
  });
});