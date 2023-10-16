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
        console.log("클릭됨");
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
            traditional: true,
            success: function(response) {
                if(response==="success"){
                console.log("삭제 요청이 성공적으로 처리되었습니다.");
                }else{
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
});
    //학습 그룹 선택 후 조회
    $(document).ready(function() {
        $(".search-btn").click(function() {
            var groupName = $(".sBtn-text").text();

            window.location.href = "/educator/group/list?groupName=" + encodeURIComponent(groupName);
        });
    });

    $(document).ready(function() {
    $(".detail-search").click(function() {
        var groupName = $(this).attr("data-group-name");
        console.log(groupName);
        $(".sBtn-text1").text(groupName);
    });

    $(document).ready(function() {
        const modalGroupMenu = document.querySelector(".modal .select-menu-group");
        const modalSelectGroupBtn = modalGroupMenu.querySelector(".select-btn-group");
        const modalGroupOptions = modalGroupMenu.querySelectorAll(".option");
        const modalGroupSBtnText = modalGroupMenu.querySelector(".sBtn-text1");

        modalSelectGroupBtn.addEventListener("click", () => modalGroupMenu.classList.toggle("active"));
        modalGroupOptions.forEach(option => {
        option.addEventListener("click", () => {
        let selectedOption = option.querySelector(".option-text").innerText;
        modalGroupSBtnText.innerText = selectedOption;
        modalGroupMenu.classList.remove("active");
            });
        });
    });

    $(".close-modal").click(function() {
        // 모달 엘리먼트를 숨기는 처리
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
            dataType: "json", // 이 부분을 JSON으로 설정
            success: function(response) {
                console.log("AJAX 요청 성공:", response);

                // 데이터를 사용하여 HTML을 업데이트
                var memberList = $(".member-list");

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
                // AJAX 요청이 실패할 때 처리할 코드
                console.error("AJAX 요청 실패:", error);
            }
        });
    });

    $(document).ready(function() {
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
});