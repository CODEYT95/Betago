$(document).ready(function() {
        // 그룹 메뉴 상호작용
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

        // 선택한 항목들을 삭제
        $(".delete-btn").click(function() {
            var selectCheck = [];
            $(".checkbox:checked").each(function() {
                var checkNo = $(this).attr("data-check-no");
                selectCheck.push(checkNo);
            });

            // 선택한 항목에 대한 AJAX 삭제 요청
            $.ajax({
                url: "/educator/group/delete",
                type: "POST",
                data: { group_no: selectCheck },
                dataType: "text",
                traditional: true,
                success: function(response) {
                    if(response==="success"){
                        console.log("삭제 요청이 성공적으로 처리되었습니다.");
                    } else {
                        console.log("삭제 실패");
                    }
                    console.log(response);
                },
                error: function(xhr, status, error) {
                    console.error("삭제 요청 중 오류가 발생했습니다.");
                    console.error(error);
                }
            });
        });

        // 학습 그룹 선택 후 조회
        $(".search-btn").click(function() {
            var groupName = $(".sBtn-text").text();
            window.location.href = "/educator/group/list?groupName=" + encodeURIComponent(groupName);
        });

        // 상세 검색 클릭 시 그룹명 설정
        $(".detail-search").click(function() {
            var groupName = $(this).attr("data-group-name");
            $(".sBtn-text1").text(groupName);
        });

        // 학생이 없을 경우의 모달 처리
        const noStudentModal = document.getElementById('noStudentModal');
        const confirmButton = document.getElementById('confirmButton');

        function openNoStudentModal() {
            noStudentModal.style.display = 'block';
        }

        function closeNoStudentModal() {
            noStudentModal.style.display = 'none';
        }

        confirmButton.addEventListener('click', closeNoStudentModal);

        $(".close-modal").click(function() {
            $(".member-list").empty();
            $("#modal").css("display", "none");
        });


        $(".detail-search").click(function() {
            var group_no = $(this).data("detail-no");
            var group_name = $(this).data("group-name");

            $.ajax({
                type: "GET",
                url: "/educator/group/listDetail",
                data: {
                    group_no: group_no,
                    group_name: group_name
                },
                dataType: "json",
                success: function(response) {
                    if (response.length === 0) {
                        openNoStudentModal();
                    } else {
                        var memberList = $(".member-list");

                        var firstListItem = response[0];
                        var game_title = firstListItem.game_title;
                        var group_cnt = firstListItem.group_cnt;
                        var group_nowcnt = firstListItem.group_cnt - firstListItem.group_nowcnt;

                        $(".groupinfo span:nth-child(1)").text("게임 콘텐츠: " + game_title);
                        $(".groupinfo span:nth-child(2)").text("그룹 제한 인원: " + group_cnt + "명");
                        $(".groupinfo span:nth-child(3)").text("그룹 가능 인원: " + group_nowcnt + "명");

                        $.each(response, function(index, groupItem) {
                            var li = $("<li>");
                            li.append("<span style='width: 10%;'>" + (index + 1) + "</span>");
                            li.append("<span style='width: 15%;'>" + groupItem.member_name + "</span>");
                            li.append("<span style='width: 20%;'>" + groupItem.member_phone + "</span>");
                            li.append("<span style='width: 40%;'>" + groupItem.member_email + "</span>");
                            li.append("<span style='width: 15%;'>" + groupItem.join_date + "</span>");

                            memberList.append(li);
                        });
                        $("#modal").css("display", "block");
                    }
                },
                error: function(error) {
                    console.error("AJAX 요청 실패:", error);
                }
            });
        });

            var groupNo; // 클릭 이벤트 핸들러 외부에 변수 선언

            $(".option-text").click(function() {
                groupNo = $(this).attr("data-no"); // 클릭된 요소의 data-no 값을 할당
                console.log(groupNo);
            });

        $(".search-btn1").click(function() {
            var selectedOption = $(".sBtn-text1").text();
            console.log(groupNo); // groupNo 값 확인

        $.ajax({
            type: "GET",
            url: "/educator/group/listDetail",
            data: {
                group_no: groupNo, // 클릭된 옵션의 group_no 값을 전달
                group_name: selectedOption
            },
            dataType: "json",
            success: function(response) {
                console.log("AJAX 요청 성공:", response);

                // 데이터를 사용하여 HTML을 업데이트
                var memberList = $(".member-list");
                memberList.empty(); // 기존 목록 초기화

                var firstListItem = response[0];
                var game_title = firstListItem.game_title;
                var group_cnt = firstListItem.group_cnt;
                var group_nowcnt = firstListItem.group_cnt-firstListItem.group_nowcnt;

                $(".groupinfo span:nth-child(1)").text("게임 콘텐츠: " + game_title);
                $(".groupinfo span:nth-child(2)").text("그룹 제한 인원: " + group_cnt+"명");
                $(".groupinfo span:nth-child(3)").text("그룹 가능 인원: " + group_nowcnt+"명");

                $.each(response, function(index, groupItem) {
                    var li = $("<li>");
                    li.append("<span style='width: 10%;'>" + (index + 1) + "</span>");
                    li.append("<span style='width: 15%;'>" + groupItem.member_name + "</span>");
                    li.append("<span style='width: 20%;'>" + groupItem.member_phone + "</span>");
                    li.append("<span style='width: 40%;'>" + groupItem.member_email + "</span>");
                    li.append("<span style='width: 15%;'>" + groupItem.join_date + "</span>");

                    memberList.append(li);
                });

                $("#modal").css("display", "block");
            },
            error: function(error) {
                console.error("AJAX 요청 실패:", error);
            }
        });
    });
});