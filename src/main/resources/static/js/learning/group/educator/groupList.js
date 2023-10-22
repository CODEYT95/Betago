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
        $(".delete-btn").click(function () {
            var selectCheck = [];
            var hasStudents = false; // 학생이 있는 그룹이 있는지 여부를 체크하는 변수

            $(".checkbox:checked").each(function () {
                var checkNo = $(this).attr("data-check-no");
                selectCheck.push(checkNo);

                // 해당 그룹의 학생 수를 확인
                var studentCount = parseInt($(this).closest(".li-container").find(".li-studentCnt span").text());
                if (studentCount > 0) {
                    hasStudents = true;
                    return false; // 학생이 있는 그룹이 하나라도 확인되면 루프 종료
                }
            });

            // 학생이 있는 그룹이 있다면 삭제 요청을 막고 모달창을 띄웁니다.
            if (hasStudents) {
                $("#noDeleteModal").css("display", "block");
                return;
            }
            console.log(selectCheck);
            // 선택한 항목에 대한 AJAX 삭제 요청
            $.ajax({
                url: "/educator/group/delete",
                type: "POST",
                data: { group_no: selectCheck },
                dataType: "text",
                traditional: true,
                success: function (response) {
                    if (response === "success") {
                        // 그룹 삭제가 성공한 경우
                        $("#deleteCompleteModal").css("display", "block");
                    }
                },
                error: function (xhr, status, error) {
                    console.error("삭제 요청 중 오류가 발생했습니다.");
                    console.error(error);
                },
            });
        });

        // 확인 버튼을 누르면 모달 창을 닫고 페이지를 새로고침합니다.
        $("#confirmButton2").click(function () {
            $("#noDeleteModal").css("display", "none");
        });

        // 그룹 삭제 완료 모달에서 확인 버튼을 누르면 페이지를 새로고침합니다.
        $(".modal-btn-confirm").click(function () {
            location.reload();
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

    $(".modify-btn").click(function() {
            var selectedCheckboxes = $(".checkbox:checked"); // 체크된 체크박스들을 가져옵니다.

            if (selectedCheckboxes.length === 0) {
                alert("수정할 그룹을 선택하세요.");
            } else if (selectedCheckboxes.length > 1) {
                alert("그룹 수정은 하나의 그룹만 선택이 가능합니다.");
            } else {
                // 첫 번째 체크된 체크박스의 data-check-no 값을 가져옵니다.
                var groupNo = selectedCheckboxes.first().attr("data-check-no");
                // 특정 주소에 groupNo를 추가하여 이동합니다.
                window.location.href = "/educator/group/modify?group_no=" + groupNo;
            }
        });
});