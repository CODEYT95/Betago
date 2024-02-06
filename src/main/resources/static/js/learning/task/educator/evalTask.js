document.addEventListener("DOMContentLoaded", function() {

    function ModalListeners() {

        const detailButtons = document.querySelectorAll(".detailBtn");
        const modalBg = document.querySelector(".modal-bg");
        const modal = document.querySelector(".modal");

        detailButtons.forEach(function(button) {
          button.addEventListener("click", function() {
            // 클릭한 버튼의 데이터 속성을 이용하여 숙제 정보 가져오기
            const taskTitle = button.parentElement.parentElement.querySelector(".list_name").textContent;
            const taskContent = button.parentElement.parentElement.querySelector(".list_content").textContent;
            const taskProgress = button.parentElement.parentElement.querySelector(".list_progress").textContent;
            const taskDeadline = button.parentElement.parentElement.querySelector(".list_deadline").textContent;
            const taskSenddate = button.parentElement.parentElement.querySelector(".list_senddate").textContent;

            // 모달 내용 업데이트
            const modalTitle = modal.querySelector("#modal-task-title");
            modalTitle.textContent = taskTitle;
            const modalContent = modal.querySelector(".modal-content");
            modalContent.querySelector("#modal-task-content").textContent = taskContent;
            modalContent.querySelector("#modal-task-chapter").textContent = taskProgress;
            modalContent.querySelector("#modal-task-deadline").textContent = taskDeadline;
            modalContent.querySelector("#modal-task-senddate").textContent = taskSenddate;

            // 모달 및 배경 표시
            modal.style.display = "block";
            modalBg.style.display = "block";
          });
        });


        // 모달 배경 또는 취소 버튼을 클릭하면 모달 닫기
        modalBg.addEventListener("click", function() {
          modal.style.display = "none";
          modalBg.style.display = "none";
        });

        const cancelButton = modal.querySelector(".cancel");
        cancelButton.addEventListener("click", function() {
          modal.style.display = "none";
          modalBg.style.display = "none";
        });
    }

    ModalListeners();

    $(document).ready(function () {
        $("#selectTaskBtn").click(function (e) {
            e.preventDefault(); // 기본 이벤트 방지
            var task_title = $("#selectTaskTitle").val();

            $.ajax({
                type: "GET",
                url: "/educator/evalTask",
                data: { task_title: task_title },
                dataType: "html",
                success: function (response) {
                    console.log(response);
                     var tbodyHtml = $(response).find("#taskTableBody").html();
                        $("#taskTableBody").html(tbodyHtml);

                        ModalListeners();
                },
            });
        });
    });

    $(document).ready(function() {
        var selectedRow = null;

        $("#table tbody").on("dblclick", "tr", function() {

            if (selectedRow) {
                selectedRow.css("background-color", ""); // 기본 배경색으로 초기화
            }

            var taskNo = $(this).find(".list_no").data("task-no");
            console.log(taskNo);

            $(this).css("background-color", "#f7ecb0");

            // 선택된 <tr>를 selectedRow 변수에 저장
            selectedRow = $(this);

            // AJAX 요청을 보내서 데이터를 가져옵니다.
            $.ajax({
                url: "/educator/member",
                method: "POST",
                data: { task_no: taskNo },
                success: function(data) {
                    // 받아온 데이터를 이용하여 HTML을 생성합니다.
                    var tableHtml = "";

                if (data.length === 0) {
                    // 데이터가 없는 경우 메시지를 표시합니다.
                    tableHtml = "<tr><td colspan='7'>숙제를 제출한 학습자가 없거나 평가를 완료했습니다.</td></tr>";
                } else {
                    $.each(data, function(index, item) {
                        var date = new Date(item.tasksubmit_regdate);
                        var formattedDate = date.getFullYear() + '-' + ('0' + (date.getMonth() + 1)).slice(-2) + '-' + ('0' + date.getDate()).slice(-2);

                        // 반복문 내에서 tableHtml을 추가합니다.
                        tableHtml += "<tr class='tr-list'>" +
                            "<td class='list_no'>" + (index + 1) + "</td>" +
                            "<td class='list_name'>" + item.member_name + "</td>" +
                            "<td class='list_senddate'>" + formattedDate + "</td>" +
                            "<td class='list_content'>" + item.tasksubmit_content + "</td>" +
                            "<td class='list_progress'>" + item.tasksubmit_chapter + "</td>" +
                            "<td class='list_add'>" + item.tasksubmit_add + "</td>" +
                            "<td class='detailBtn-wrap'>" +
                            "<button type='button' class='detailBtn' onclick='redirectToDetail(" + item.tasksubmit_no + ")'>" +
                            "<i class='ri-mail-send-line'></i></button>" +
                            "</td>" +
                            "</tr>";
                    });
                }

                    // 기존 테이블 대신 새로운 테이블로 교체합니다.
                    $("#table2 tbody").html(tableHtml);
                },
                error: function(xhr, status, error) {
                    console.error("Error:", error);
                }
            });
        });
    });

    $(document).ready(function () {
        // 평가하기 모달이 열릴 때마다 실행되는 함수
        function updateSubmitButtonStatus() {
            const selectValue = $("#selectEval").val(); // select 요소의 값

            // selectValue가 "선택"이 아니고, textareaValue가 비어있지 않으면 버튼 활성화
            if (selectValue !== "선택") {
                $(".submit").prop("disabled", false);
            } else {
                $(".submit").prop("disabled", true);
            }
        }

        // 모달이 열릴 때마다 버튼 상태를 업데이트
        $("#myModal2").on("shown.bs.modal", function () {
            updateSubmitButtonStatus();
        });

        // select 및 textarea 값 변경 이벤트를 감지하고 버튼 상태를 업데이트
        $("#selectEval").on("change keyup", function () {
            updateSubmitButtonStatus();
        });
    });
});
    function redirectToDetail(tasksubmit_no) {
        const modalBg = document.querySelector(".modal-bg");
        const modal2 = document.querySelector(".modal2");

        $.ajax({
            url: "/educator/evalDetail",
            method: "GET",
            data: { tasksubmit_no: tasksubmit_no },
            success: function(data) {
            console.log(data);
                var date = new Date(data.tasksubmit_regdate);
                var formattedDate = date.getFullYear() + '-' + ('0' + (date.getMonth() + 1)).slice(-2) + '-' + ('0' + date.getDate()).slice(-2);

                $("#member_no_input").val(data.member_no);
                $("#group_no_input").val(data.group_no);
                $("#tasksend_no_input").val(data.tasksend_no);
                $("#member_level_input").val(data.task_chapter);

                $("#modal-member-name").text(data.member_name);
                $("#modal-submit-date").text(formattedDate);
                $("#modal-submit-content").text(data.tasksubmit_content);
                $("#modal-submit-chapter").text(data.tasksubmit_chapter);
                $("#modal-submit-add").text(data.tasksubmit_add);

                // 모달 열기 코드
                modalBg.style.display = "block";
                modal2.style.display = "block";

                // 모달 배경 또는 취소 버튼을 클릭하면 모달 닫기
                modalBg.addEventListener("click", function() {
                  modal2.style.display = "none";
                  modalBg.style.display = "none";
                });

                const cancelButton = modal2.querySelector(".cancel");
                cancelButton.addEventListener("click", function() {
                  modalBg.style.display = "none";
                  modal2.style.display = "none";
                });
            },
            error: function(xhr, status, error) {
                console.error("Error:", error);
            }
        });
    }
