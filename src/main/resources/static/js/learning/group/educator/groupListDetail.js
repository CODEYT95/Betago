//게임 콘텐츠 목록 리스트
    $(document).ready(function() {
        const groupMenu = document.querySelector(".select-menu-group");
        const selectGroupBtn = groupMenu.querySelector(".select-btn-group");
        const groupOptions = groupMenu.querySelectorAll(".option");
        const groupSBtnText = groupMenu.querySelector(".sBtn-text");

        selectGroupBtn.addEventListener("click", () => groupMenu.classList.toggle("active"));
        groupOptions.forEach(option => {
            option.addEventListener("click", () => {
                let selectedOption = option.querySelector(".option-text").innerText;
                groupSBtnText.innerText = selectedOption;
                groupMenu.classList.remove("active");
            });
        });
    });
    $(document).ready(function() {
    $(".delete-btn").click(function() {
        var selectCheck = []; // 선택된 체크박스의 data-check-no 값들을 저장할 배열

        // 선택된 체크박스를 찾아서 data-check-no 값을 배열에 추가
        $(".checkbox:checked").each(function() {
            var checkNo = $(this).attr("data-check-no");
            selectCheck.push(checkNo);
        });
            console.log(selectCheck);
        // AJAX 요청 보내기
        $.ajax({
            url: "/educator/group/delete",
            type: "POST",
            data: { group_no: selectCheck },
            dataType: "text",
            traditional: true, // traditional 옵션 추가
            success: function(response) {
                console.log("삭제 요청이 성공적으로 처리되었습니다.");
                console.log(response);
            },
            error: function(xhr, status, error) {
                console.error("삭제 요청 중 오류가 발생했습니다.");
                console.error(error);
            }
        });
    });
});