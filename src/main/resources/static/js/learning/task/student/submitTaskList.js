    document.addEventListener("DOMContentLoaded", function() {

        $(document).ready(function() {
            var offset = 0;

            $("#moreBtn").click(function() {
                offset += 1;

                $.ajax({
                    url: "/student/submitList",
                    type: "POST",
                    data: {
                        offset: offset
                    },
                    dataType: "json",
                    success: function(response) {
                        response.forEach(task => {
                            var date = new Date(task.task_deadline);
                            var formattedDate = date.getFullYear() + '-' + ('0' + (date.getMonth() + 1)).slice(-2) + '-' + ('0' + date.getDate()).slice(-2);

                            var taskHtml = `
                                <div class="checkbox">
                                    <label class="checkbox-wrapper">
                                        <span class="checkbox-title">
                                            <div class="card">
                                                <div class="card-details">
                                                    <div class="card-title">
                                                        <div class="game-title"><span>${task.game_title}</span></div>
                                                        <div class="member-name"><span>${task.member_name}</span></div>
                                                    </div>
                                                    <hr>
                                                    <div class="card-content-wrap">
                                                        <div class="card-content">
                                                            <span>숙제 내용</span>
                                                            <div class="task-content"><span class="data">${task.task_content}</span></div>
                                                            <div class="task-contentlist">
                                                                <div class="task-chapter">진도 : <span class="data">${task.task_chapter}</span></div>
                                                                <div class="task-deadline">제출 기한 : <span class="data">${formattedDate}</span></div>
                                                                <div class="task-state">상태 :
                                                                    <span class="data" style="${task.task_state == '제출완료' ? 'color: green;' : 'color: #8d8d8e;'}">${task.task_state}</span>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="card-button">
                                                            <div class="button-wrap">
                                                                <input class="modifyBtn" type="button" value="수정하기" style="display: ${task.task_state == '제출완료' ? 'block' : 'none'}" data-tasksend-no=${task.tasksend_no}>
                                                                <input type="button" class="detailBtn" value="조회하기" style="display: ${task.task_state == '평가완료' ? 'block' : 'none'}" data-tasksend-no=${task.tasksend_no}>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </span>
                                    </label>
                                </div>
                            `;
                            $(".mycontents-list").append(taskHtml);
                        });
                        updateLiCount();
                    }
                });
            });

                //현재 체크박스 갯수 업데이트
            $(document).ready(function() {
                updateLiCount();
            });

            function updateLiCount() {
                var liCount = $(".mycontents-list .checkbox").length;
                $(".currentCnt").text(liCount);

                var currentCountElement = document.querySelector('.currentCnt');
                var totalCountElement = document.querySelector('.totalCnt');

                var moreButton = document.getElementById('moreBtn');

                if (parseInt(currentCountElement.textContent) >= parseInt(totalCountElement.textContent)) {
                    moreButton.style.display = 'none';
                } else {
                    moreButton.style.display = 'block';
                }
            }

            window.onload = function() {
                var currentCountElement = document.querySelector('.currentCnt');
                var totalCountElement = document.querySelector('.totalCnt');

                var moreButton = document.getElementById('moreBtn');

                if (parseInt(currentCountElement.textContent) >= parseInt(totalCountElement.textContent)) {
                    moreButton.style.display = 'none';
                }
            }
        });

        // 모달 열기
        function openModal(tasksend_no) {
            $(".modal-bg").css("display", "block");
            $("#evalModal").css("display", "block"); // 모달 열기

            // 모달 내용을 AJAX로 가져오고 채우는 코드
            $.ajax({
                url: "/student/viewEval",
                type: "POST",
                data: {
                    tasksend_no: tasksend_no
                },
                dataType: "json",
                success: function(response) {
                        var date = new Date(response.task_deadline);
                        var formattedDate = date.getFullYear() + '-' + ('0' + (date.getMonth() + 1)).slice(-2) + '-' + ('0' + date.getDate()).slice(-2);

                        var modalTaskState = $("#modal-task-state");
                        modalTaskState.text(response.task_state);

                        if (response.task_state == '제출완료') {
                            modalTaskState.css('color', 'green');
                        } else {
                            modalTaskState.css('color', '#8d8d8e');
                        }

                        var modalEval = $("#modal-eval-eval");
                        modalEval.text(response.tasksubmit_eval);

                        if (response.tasksubmit_eval == '미흡') {
                            modalEval.css('color','red');
                        } else if (response.tasksubmit_eval == '보통') {
                            modalEval.css('color','#ffaa00');
                        } else {
                            modalEval.css('color','green');
                        }
                        // 모달 내용 채우기
                        $("#modal-game-name").text(response.game_name);
                        $("#modal-member-name").text(response.member_name);
                        $("#modal-task-content").text(response.task_content);
                        $("#modal-task-chapter").text(response.task_chapter);
                        $("#modal-task-deadline").text(formattedDate);
                        $("#modal-eval-chapter").text(response.tasksubmit_chapter);
                        $("#modal-eval-content").text(response.tasksubmit_content);
                        $("#modal-eval-add").text(response.tasksubmit_add);
                        $("#modal-eval-eval").text(response.tasksubmit_eval);
                        $("#modal-eval-comment").text(response.tasksubmit_comment);
                }
            });
        }

        // 모달 닫기
        function closeModal() {
            $(".modal-bg").css("display", "none");
            $("#evalModal").css("display", "none"); // 모달 열기
        }

        $(document).on("click", ".detailBtn", function() {
            var tasksend_no = $(this).data("tasksend-no");
            openModal(tasksend_no);
        });

        $(document).on("click", ".cancel", function() {
            closeModal();
        });

        $(document).on("click", ".modal-bg", function() {
            closeModal();
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

                        if (response.task_state == '제출완료') {
                            modalTaskState.css('color', 'green');
                        } else {
                            modalTaskState.css('color', '#8d8d8e');
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

            const chapterInput = document.getElementById('tasksubmit_chapter');
            const contentTextarea = document.getElementById('tasksubmit_content');
            const saveBtn = document.querySelector('.saveBtn');

            chapterInput.addEventListener('input', toggleSaveBtn);
            contentTextarea.addEventListener('input', toggleSaveBtn);

            //버튼 활성화
            function toggleSaveBtn() {
                if (chapterInput.value.trim() !== '' && contentTextarea.value.trim() !== '') {
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

            // 조회하기 버튼 클릭 시 모달 열기
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