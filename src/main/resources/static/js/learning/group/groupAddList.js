document.addEventListener("DOMContentLoaded", function() {

    //체크박스 1개만 선택되게 하기
    $(document).ready(function() {
        $(document).on("change", ".checkbox-input", function() {
            let checkedCount = $(".checkbox-input:checked").length;

            if (checkedCount > 1) {
                alert("하나의 게임 콘텐츠만 선택이 가능합니다");
                $(this).prop("checked", false);
            }
        });
    });

    //아작스로 추가 데이터 불러오기
    $(document).ready(function() {
        var offset = 0;

        $("#moreBtn").click(function() {
            offset += 1;
            var title = $(".sBtn-text").text();

            $.ajax({
                url: "/educator/group/addList",
                type: "POST",
                data: {
                    offset: offset,
                    title: title
                },
                dataType: "json",
                success: function(response) {
                    response.forEach(item => {
                        $(".content-container ul").append(`
                            <li>
                                <div class="checkbox">
                                    <label class="checkbox-wrapper">
                                        <input type="checkbox" class="checkbox-input"  data-game-no=${item.game_no} />
                                        <span class="checkbox-tile">
                                            <div class="card">
                                                <div class="poster"><img src="/image/testBADUK2.png"></div>
                                                <div class="card-details"></div>
                                                <div class="details">
                                                    <h5>게임 콘텐츠 : <span>${item.game_title}</span></h5>
                                                    <h5>학습 구독 기간 : <span>${item.group_startdate}</span> ~ <span>${item.group_enddate}</span></h5>
                                                    <h5>학습 가능 인원 : 100명</h5>
                                                    <h5>그룹 지정된 인원 : 50명</h5>
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
    }

    //게임 콘텐츠 선택 후 조회
    $(document).ready(function() {
        $(".search-btn").click(function() {
            var title = $(".sBtn-text").text();

            window.location.href = "/educator/group/addList?title=" + encodeURIComponent(title);
        });
    });
    //학습 그룹 상세 페이지로 이동
    var detailBtn = document.querySelector(".detail-btn");

    detailBtn.addEventListener("click", function() {

        var selectedCheckbox = document.querySelector(".checkbox-input:checked");

        if (selectedCheckbox) {

            var selectedGameNo = selectedCheckbox.getAttribute("data-game-no");

            var url = "/educator/group/add?game_no=" + selectedGameNo;
            location.href = url;
        } else {
            alert("게임을 선택해주세요.");
        }
    });
 });
