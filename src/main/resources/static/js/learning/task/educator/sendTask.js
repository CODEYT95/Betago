document.addEventListener("DOMContentLoaded", function () {
    // 모달 열기 버튼에 클릭 이벤트 리스너 추가하는 함수
    function ModalListeners() {
        const detailButtons = document.querySelectorAll(".detailBtn");
        const modalBg = document.querySelector(".modal-bg");
        const modal = document.querySelector(".modal");

        detailButtons.forEach(function (button) {
            button.addEventListener("click", function (e) {
                e.preventDefault(); // 기본 이벤트 방지

                // 클릭한 버튼의 데이터 속성을 이용하여 숙제 정보 가져오기
                const taskTitle = button.parentElement.parentElement.querySelector(".list_name").textContent;
                const taskContent = button.parentElement.parentElement.querySelector(".list_content").textContent;
                const taskProgress = button.parentElement.parentElement.querySelector(".list_progress").textContent;
                const taskDeadline = button.parentElement.parentElement.querySelector(".list_limit").textContent;

                // 모달 내용 업데이트
                const modalTitle = modal.querySelector("#modal-task-title");
                modalTitle.textContent = taskTitle;
                const modalContent = modal.querySelector(".modal-content");
                modalContent.querySelector("#modal-task-content").textContent = taskContent;
                modalContent.querySelector("#modal-task-chapter").textContent = taskProgress;
                modalContent.querySelector("#modal-task-deadline").textContent = taskDeadline;

                // 모달 및 배경 표시
                modal.style.display = "block";
                modalBg.style.display = "block";
            });
        });

        // 모달 배경 또는 취소 버튼을 클릭하면 모달 닫기
        modalBg.addEventListener("click", function () {
            modal.style.display = "none";
            modalBg.style.display = "none";
        });

        const cancelButton = modal.querySelector(".conceal");
        cancelButton.addEventListener("click", function () {
            modal.style.display = "none";
            modalBg.style.display = "none";
        });

        document.getElementById("sendTaskBtn").addEventListener("click", function (e) {
            e.preventDefault(); // 기본 이벤트 방지

            // 필요한 조건 검사
            const selectedTask = document.querySelector(".list_no input[type='checkbox']:checked");
            const selectedOption = document.querySelector("#selectGroupTitle option:checked");
            const selectedCheckboxes = document.querySelectorAll(".list_groupNo input[type='checkbox']:checked");
            const taskNoMsg = document.querySelector(".taskNoMsg");
            const groupNameMsg = document.querySelector(".groupNameMsg");
            const memberNoMsg = document.querySelector(".memberNoMsg");

            // 초기화
            taskNoMsg.textContent = "";
            groupNameMsg.textContent = "";
            memberNoMsg.textContent = "";

            if (!selectedTask) {
                taskNoMsg.textContent = "숙제를 선택해주세요.";
            }
            if (!selectedOption || !selectedOption.value) {
                groupNameMsg.textContent = "그룹을 조회해주세요.";
            }
            if (selectedCheckboxes.length === 0) {
                memberNoMsg.textContent = "수강생을 선택해주세요.";
            }

            if (!selectedTask || !selectedOption || !selectedOption.value || selectedCheckboxes.length === 0) {
                return;
            }

            // 모달2 열기
            const modal2 = document.querySelector("#myModal2");
            const modalBg = document.querySelector(".modal-bg");

            modal2.style.display = "block";
            modalBg.style.display = "block";
        });

        modalBg.addEventListener("click", function () {
            const modal2 = document.querySelector("#myModal2");
            const modalBg = document.querySelector(".modal-bg");

            modal2.style.display = "none";
            modalBg.style.display = "none";
        });
    }

    // 초기 페이지 로딩 시 모달 열기 버튼에 클릭 이벤트 리스너 연결
    ModalListeners();

    var isSelectAllChecked = false;
    document.getElementById("selectAllBtn").addEventListener("click", function (e) {
        e.stopPropagation();

        var checkboxes = document.querySelectorAll(".list_groupNo input[type='checkbox']");
        checkboxes.forEach(function (checkbox) {
            checkbox.checked = !isSelectAllChecked;
        });

        isSelectAllChecked = !isSelectAllChecked;
    });

    // 그룹 조회 버튼 클릭 시
    $("#selectGroupBtn").click(function (e) {
        e.preventDefault(); // 기본 이벤트 방지
        var selectedOption = $("#selectGroupTitle option:checked");
        var group_no = selectedOption.attr("data-groupNo");
        var group_name = selectedOption.attr("data-groupName");

        $.ajax({
            type: "GET",
            url: "/educator/sendTask",
            data: { group_name: group_name, group_no: group_no },
            dataType: "html",
            success: function (response) {
                // 왼쪽 테이블의 HTML 가져오기
                var leftTbodyHtml = $(response).find("#memberTableBodyLeft").html();
                // 오른쪽 테이블의 HTML 가져오기
                var rightTbodyHtml = $(response).find("#memberTableBodyRight").html();

                // 왼쪽 테이블 업데이트
                var leftTableBody = $("#memberTableBodyLeft");
                leftTableBody.html(leftTbodyHtml);

                // 오른쪽 테이블 업데이트
                var rightTableBody = $("#memberTableBodyRight");
                rightTableBody.html(rightTbodyHtml);

                // 모달 열기 버튼에 클릭 이벤트 리스너 다시 연결
                ModalListeners();
            },
            error: function () {
                alert("작업을 가져오는 중 오류가 발생했습니다.");
            }
        });
    });

    $(document).ready(function () {
        $("#selectTaskBtn").click(function (e) {
            e.preventDefault(); // 기본 이벤트 방지
            var task_title = $("#selectTaskTitle").val();

            $.ajax({
                type: "GET",
                url: "/educator/sendTask",
                data: { task_title: task_title },
                dataType: "html",
                success: function (response) {
                    console.log(response);
                     var tbodyHtml = $(response).find("#taskTableBody").html();
                        $("#taskTableBody").html(tbodyHtml);

                    var pageHtml = $(response).find("#pagination").html();
                        $("#pagination").html(pageHtml);

                    ModalListeners();
                },
            });
        });
    });
});
