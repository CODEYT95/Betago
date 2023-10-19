document.addEventListener("DOMContentLoaded", function() {
    //아작스로 추가 데이터 불러오기
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
                                                            <a  href="/student/taskModify/${task.tasksend_no}" style="display: ${task.task_state == '제출완료' ? 'block' : 'none'}">
                                                                <input class="modifyBtn" type="button" value="수정하기">
                                                            </a>
                                                            <a href="/student/viewEval/${task.tasksend_no}" style="display: ${task.task_state == '평가완료' ? 'block' : 'none'}">
                                                                <input type="button" class="detailBtn" value="조회하기">
                                                            </a>
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
 });