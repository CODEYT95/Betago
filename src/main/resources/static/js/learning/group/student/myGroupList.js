    document.addEventListener("DOMContentLoaded", function() {

    //체크박스 1개만 선택되게 하기
    $(document).ready(function() {
        $(document).on("change", ".checkbox-input", function() {
            let checkedCount = $(".checkbox-input:checked").length;

            if (checkedCount > 1) {
                alert("하나의 그룹만 선택이 가능합니다");
                $(this).prop("checked", false);
            }
        });
    });

    //아작스로 추가 데이터 불러오기
    $(document).ready(function() {
        var offset = 0;

        $("#moreBtn").click(function() {
            offset += 1;
            var title = $(".title").value;

            $.ajax({
                url: "/student/group/myJoinList",
                type: "POST",
                data: {
                    offset: offset,
                    title: title
                },
                dataType: "json",
                success: function(response) {
                    response.forEach(item => {
                    var remainingTo = item.group_cnt - item.group_nowcnt;
                    console.log(remainingTo);
                        $(".content-container ul").append(`
                            <li data-game-no=${item.game_no}>
                                <input hidden="hidden" class="attr" data-game-no=${item.game_no} data-group-no=${item.group_no} data-member-no=${item.member_no}>
                                    <div class="checkbox">
                                        <label class="checkbox-wrapper">
                                            <input type="text" class="checkbox-input"  data-game-no=${item.game_no} data-group-no=${item.group_no} />
                                            <span class="checkbox-tile">
                                                <div class="card">
                                                    <div class="poster"><img src="/image/game/${item.filegame_name}"></div>
                                                    <div class="card-details"></div>
                                                    <div class="details">
                                                        <h5>게임콘텐츠명 : <span>${item.game_title}</span></h5>
                                                        <h5>그룹명 : <span>${item.group_name}</span></h5>
                                                        <h5>교육자명 : <span>${item.member_name}</span></h5>
                                                        <h5>학습 구독 기간 : <span>${item.group_startdate}</span> ~ <span>${item.group_enddate}</span></h5>
                                                        <h5>가입 상태 : <span>${item.approve_state}</span></h5>
                                                    </div>
                                                </div>
                                            </span>
                                        </label>
                                    </div>
                            </li>
                        `);
                    });
                    updateLiCount();
                }
            });
        });

        // 게임 타이틀 클릭 시 선택 클래스를 토글합니다.
        $(".options .option .option-text").click(function() {
            $(this).toggleClass("selected");
        });
    });

    //현재 체크박스 갯수 업데이트
    $(document).ready(function() {
        updateLiCount();
    });

    function updateLiCount() {
        var liCount = $(".list-box li").length;
        $(".currentCnt").text(liCount);

        // .currentCnt와 .totalCnt 요소를 가져옵니다.
        var currentCountElement = document.querySelector('.currentCnt');
        var totalCountElement = document.querySelector('.totalCnt');

        // #moreBtn 요소를 가져옵니다.
        var moreButton = document.getElementById('moreBtn');

        // .currentCnt와 .totalCnt의 값을 비교합니다.
        if (parseInt(currentCountElement.textContent) >= parseInt(totalCountElement.textContent)) {
            // 값이 동일하거나 .currentCnt가 더 크다면, #moreBtn을 숨깁니다.
            moreButton.style.display = 'none';
        } else {
            moreButton.style.display = 'block';  // Optional: 만약 total count가 더 크다면 버튼을 다시 보여줍니다.
        }
    }

    //게임 콘텐츠 선택 후 조회
    $(document).ready(function() {
        $(".search-btn").click(function() {
            var groupName = $(".sBtn-text").text();
            var educatorName = $(".sBtn-text-educator").text();

            var url = "";
            if(groupName === "전체" && educatorName === "전체") {
                url = "/student/group/myJoinList"

            } else if(groupName === "전체" && educatorName !== "전체") {
                url = "/student/group/myJoinList?educator_name=" + encodeURIComponent(educatorName);

            } else if(educatorName === "전체" && groupName !== "전체") {
                url = "/student/group/myJoinList?group_name=" + encodeURIComponent(groupName);

            }
            window.location.href = url;
        });
    });

    $(document).ready(function() {
        const groupMenu = $(".select-menu-group");
        const selectGroupBtn = groupMenu.find(".select-btn-group");
        const groupOptions = groupMenu.find(".option");
        const groupSBtnText = groupMenu.find(".sBtn-text");

        const educatorMenu = $(".select-menu-educator");
        const selectEducatorBtn = educatorMenu.find(".select-btn-educator");
        const educatorOptions = educatorMenu.find(".option");
        const educatorSBtnText = educatorMenu.find(".sBtn-text-educator");

        selectGroupBtn.on("click", (e) => {
            e.stopPropagation();
            educatorMenu.removeClass("active");
            groupMenu.toggleClass("active");
        });

        selectEducatorBtn.on("click", (e) => {
            e.stopPropagation();
            groupMenu.removeClass("active");
            educatorMenu.toggleClass("active");
        });

        groupOptions.on('click', function(e) {
            e.stopPropagation();

            let selectedOption = $(this).find('.option-text').text();
            if (educatorSBtnText.text() !== '전체' && selectedOption !== '전체') {
                alert('검색 옵션은 둘 중 하나만 선택이 가능합니다.');
                educatorOptions.removeClass('selected');
                groupOptions.removeClass('selected');
                educatorSBtnText.text('전체');
                groupSBtnText.text('전체');
                groupMenu.removeClass("active");
            } else {
                groupSBtnText.text(selectedOption);
                $(this).addClass('selected').siblings().removeClass('selected');
            }
        });

        educatorOptions.on('click', function(e) {
            e.stopPropagation();

            let selectedOption = $(this).find('.option-text-educator').text();
            if (groupSBtnText.text() !== '전체' && selectedOption !== '전체') {
                alert('검색 옵션은 둘 중 하나만 선택이 가능합니다.');
                educatorOptions.removeClass('selected');
                groupOptions.removeClass('selected');
                groupSBtnText.text('전체');
                educatorSBtnText.text('전체');
                educatorMenu.removeClass("active");
            } else {
                educatorSBtnText.text(selectedOption);
                $(this).addClass('selected').siblings().removeClass('selected');
            }
        });

        $(document).on("click", function() {
            groupMenu.removeClass("active");
            educatorMenu.removeClass("active");
        });
    });
 });