document.addEventListener("DOMContentLoaded", function () {
    const submitBtn = document.getElementById("submitButton");
    const checkboxes = document.querySelectorAll(".checkbox-input");

    // 체크박스 상태 확인 및 버튼 활성화/비활성화
    checkboxes.forEach(function (checkbox) {
        checkbox.addEventListener("change", function () {
        let isAnyCheckboxChecked = false;

            checkboxes.forEach(function (checkbox) {
                if (checkbox.checked) {
                isAnyCheckboxChecked = true;
                }
            });

            if (isAnyCheckboxChecked) {
            submitBtn.removeAttribute("disabled");
            } else {
            submitBtn.setAttribute("disabled", "disabled");
            }
        });
    });

   // 모달 열기
    function openWriteModal(tasksend_no) {
        $(".modal-bg").css("display", "block");
        $("#writeModal").css("display", "block"); // 모달 열기

        $.ajax({
            url: "/student/taskDetail",
            type: "POST",
            data: {
                tasksend_no: tasksend_no
            },
            dataType: "json",
            success: function(response) {
                var deadline = new Date(response.task_deadline);
                var dateFormat = deadline.getFullYear() + '-' + ('0' + (deadline.getMonth() + 1)).slice(-2) + '-' + ('0' + deadline.getDate()).slice(-2);

                var modalTask = $("#modal-state");
                modalTask.text(response.task_state);

                if (response.task_state == '작성중') {
                    modalTask.css('color', 'green');
                } else {
                    modalTask.css('color', 'red');
                }

                $("#modal-title").text(response.task_title);
                $("#modal-name").text(response.member_name);
                $("#modal-content").text(response.task_content);
                $("#modal-chapter").text(response.task_chapter);
                $("#modal-deadline").text(dateFormat);
                $("#modal-state").text(response.task_state);
                $("#modal-tasksend-no").val(response.tasksend_no);
                $("#modal-task-no").val(response.task_no);
            }
        });
    }

    const chapterInput = document.getElementById('m-tasksubmit_chapter');
    const contentTextarea = document.getElementById('m-tasksubmit_content');
    const saveButton = document.querySelector('.save');

    chapterInput.addEventListener('input', useSaveBtn);
    contentTextarea.addEventListener('input', useSaveBtn);

    //버튼 활성화
    function useSaveBtn() {
        if (chapterInput.value.trim() !== '' && contentTextarea.value.trim() !== '') {
            saveButton.removeAttribute('disabled');
        } else {
            saveButton.setAttribute('disabled', 'disabled');
        }
    }
    // 모달 닫기
    function closeWriteModal() {
        $(".modal-bg").css("display", "none");
        $("#writeModal").css("display", "none");
    }

    // 조회하기 버튼 클릭 시 모달 열기
    $(document).on("click", ".writeBtn", function() {
        var tasksend_no = $(this).data("tasksend-no");
        openWriteModal(tasksend_no);
    });

    $(document).on("click", ".cancel", function() {
        closeWriteModal();
    });

    $(document).on("click", ".modal-bg", function() {
        closeWriteModal();
    });

   // 모달 열기
    function openModifyModal(tasksend_no) {
        $(".modal-bg").css("display", "block");
        $("#modifyModal").css("display", "block"); // 모달 열기

        // 모달 내용을 AJAX로 가져오고 채우는 코드
        $.ajax({
            url: "/student/modify",
            type: "POST",
            data: {
                tasksend_no: tasksend_no
            },
            dataType: "json",
            success: function(response) {
                var date = new Date(response.task_deadline);
                var formattedDate = date.getFullYear() + '-' + ('0' + (date.getMonth() + 1)).slice(-2) + '-' + ('0' + date.getDate()).slice(-2);

                var modalTaskState = $("#m-task-state");
                modalTaskState.text(response.task_state);

                if (response.task_state == '미작성') {
                    modalTaskState.css('color', 'green');
                } else {
                    modalTaskState.css('color', 'red');
                }

                $("#m-game-name").text(response.game_name);
                $("#m-member-name").text(response.member_name);
                $("#m-task-content").text(response.task_content);
                $("#m-task-chapter").text(response.task_chapter);
                $("#m-task-deadline").text(formattedDate);
                $("#m-tasksend-no").val(response.tasksend_no);
                $("#update-task-state").val(response.task_state);
                $("#tasksubmit_chapter").val(response.tasksubmit_chapter);
                $("#tasksubmit_content").val(response.tasksubmit_content);
                $("#tasksubmit_add").val(response.tasksubmit_add);
            }
        });
    }

    const chapterValue = document.getElementById('tasksubmit_chapter');
    const contentValue = document.getElementById('tasksubmit_content');
    const saveBtn = document.querySelector('.saveBtn');

    chapterValue.addEventListener('input', toggleSaveBtn);
    contentValue.addEventListener('input', toggleSaveBtn);

    //버튼 활성화
    function toggleSaveBtn() {
        if (chapterValue.value.trim() !== '' && contentValue.value.trim() !== '') {
            saveBtn.removeAttribute('disabled');
        } else {
            saveBtn.setAttribute('disabled', 'disabled');
        }
    }
    // 모달 닫기
    function closeModifyModal() {
        $(".modal-bg").css("display", "none");
        $("#modifyModal").css("display", "none");
    }

    // 수정하기 버튼 클릭 시 모달 열기
    $(document).on("click", ".modifyBtn", function() {
        var tasksend_no = $(this).data("tasksend-no");
        openModifyModal(tasksend_no);
    });

    $(document).on("click", ".cancelBtn", function() {
        closeModifyModal();
    });

    $(document).on("click", ".modal-bg", function() {
        closeModifyModal();
    });

    //스크롤
    (function($) { "use strict";
        $(document).ready(function(){"use strict";
            var progressPath = document.querySelector('.progress-wrap path');
            var pathLength = progressPath.getTotalLength();
            progressPath.style.transition = progressPath.style.WebkitTransition = 'none';
            progressPath.style.strokeDasharray = pathLength + ' ' + pathLength;
            progressPath.style.strokeDashoffset = pathLength;
            progressPath.getBoundingClientRect();
            progressPath.style.transition = progressPath.style.WebkitTransition = 'stroke-dashoffset 10ms linear';
            var updateProgress = function () {
              var scroll = $(window).scrollTop();
              var height = $(document).height() - $(window).height();
              var progress = pathLength - (scroll * pathLength / height);
              progressPath.style.strokeDashoffset = progress;
            }
            updateProgress();
            $(window).scroll(updateProgress);
            var offset = 50;
            var duration = 550;
            jQuery(window).on('scroll', function() {
              if (jQuery(this).scrollTop() > offset) {
                jQuery('.progress-wrap').addClass('active-progress');
              } else {
                jQuery('.progress-wrap').removeClass('active-progress');
              }
            });
            jQuery('.progress-wrap').on('click', function(event) {
              event.preventDefault();
              jQuery('html, body').animate({scrollTop: 0}, duration);
              return false;
            })
        });
    })(jQuery);
});
