document.addEventListener("DOMContentLoaded", function() {
    // 체크박스 1개만 선택되게 하기
    $(document).on("change", ".checkbox-input", function() {
        let checkedCount = $(".checkbox-input:checked").length;

        if (checkedCount > 1) {
            alert("하나의 게임 콘텐츠만 선택이 가능합니다");
            $(this).prop("checked", false);
        }
    });

    // 게임 콘텐츠 선택 후 조회
    $(".search-btn").click(function() {
        var title = $(".sBtn-text").text();
        window.location.href = "/educator/group/addList?title=" + encodeURIComponent(title);
    });

    // 아작스로 추가 데이터 불러오기
    var offset = 0;

    $("#moreBtn").click(function() {
        offset += 1;
        var title = $("#title").val();
        console.log("dd" + title);
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
                        <li data-pay-no="${item.pay_no}">
                            <div class="checkbox">
                                <label class="checkbox-wrapper">
                                    <input type="checkbox" class="checkbox-input"  data-pay-no=${item.pay_no} />
                                    <span class="checkbox-tile">
                                        <div class="card">
                                            <div class="poster"><img src="/image/game/${item.filegame_name}"></div>
                                            <div class="card-details"></div>
                                            <div class="details">
                                                <h5>게임 콘텐츠 : <span>${item.game_title}</span></h5>
                                                <h5>학습 구독 기간 : <span>${item.pay_date}</span> ~ <span>${item.pay_enddate}</span></h5>
                                                <h5>학습 가능 인원 : <span data-game-total=${item.game_total}>${item.game_total}명</span></h5>
                                                <h5>그룹 지정된 인원 : <span data-group-cnt=${item.group_cnt}>${item.group_cnt}명</span></h5>
                                            </div>
                                            <div class="backDetails">
                                            <div class="detaillist">
                                                <h6>그룹 설명 :<p>
                                                    <span style="font-size:14px;">${item.game_content}</span></p></h6>
                                            </div>
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

    // 현재 체크박스 갯수 업데이트
    updateLiCount();

    function updateLiCount() {
        var liCount = $(".list-box li").length;
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

    // 학습 그룹 상세 페이지로 이동
    var detailBtn = document.querySelector(".detail-btn");

    detailBtn.addEventListener("click", function() {
        var selectedCheckbox = document.querySelector(".checkbox-input:checked");

        if (selectedCheckbox) {
            var selectedGameNo = selectedCheckbox.getAttribute("data-pay-no");

            var form = document.createElement("form");

            form.action = "/educator/group/add";
            form.method = "GET";

            var gameNoInput = document.createElement("input");
            gameNoInput.type = "hidden";
            gameNoInput.name = "pay_no";
            gameNoInput.value = selectedGameNo;

            form.appendChild(gameNoInput);

            document.body.appendChild(form);

            form.submit();

        } else {
            alert("게임을 선택해주세요.");
        }
    });

    // 게임 콘텐츠 목록 리스트
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

    window.onload = function() {
        var currentCountElement = document.querySelector('.currentCnt');
        var totalCountElement = document.querySelector('.totalCnt');

        var moreButton = document.getElementById('moreBtn');

        if (parseInt(currentCountElement.textContent) >= parseInt(totalCountElement.textContent)) {
            moreButton.style.display = 'none';
        }
    }
});
