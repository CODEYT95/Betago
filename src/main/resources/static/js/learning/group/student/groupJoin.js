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
                url: "/student/group/joinList",
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
                                            <input type="checkbox" class="checkbox-input"  data-game-no=${item.game_no} data-group-no=${item.group_no} />
                                            <span class="checkbox-tile">
                                                <div class="card">
                                                    <div class="poster"><img src="/image/testBADUK2.png"></div>
                                                    <div class="card-details"></div>
                                                    <div class="details">
                                                        <h5>그룹명 : <span>${item.group_name}</span></h5>
                                                        <h5>교육자명 : <span>${item.member_name}</span></h5>
                                                        <h5>학습 구독 기간 : <span>${item.group_startdate}</span> ~ <span>${item.group_enddate}</span></h5>
                                                        <h5>잔여 T/O : <span>${remainingTo}명</span></h5>
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
            var title = $(".sBtn-text").text();

            //window.location.href = "/educator/group/addList?title=" + encodeURIComponent(title);
        });
    });
    //게임 콘텐츠 목록 리스트
    $(document).ready(function() {
        const groupMenu = document.querySelector(".select-menu-group");
        const selectGroupBtn = groupMenu.querySelector(".select-btn-group");
        const groupOptions = groupMenu.querySelectorAll(".option");
        const groupSBtnText = groupMenu.querySelector(".sBtn-text");

        const educatorMenu = document.querySelector(".select-menu-educator");
        const selectEducatorBtn = educatorMenu.querySelector(".select-btn-educator");
        const educatorOptions = educatorMenu.querySelectorAll(".option");
        const educatorSBtnText = educatorMenu.querySelector(".sBtn-text-educator");

        selectGroupBtn.addEventListener("click", () => {
            educatorMenu.classList.remove("active");
            groupMenu.classList.toggle("active");
        });

        selectEducatorBtn.addEventListener("click", () => {
            groupMenu.classList.remove("active");
            educatorMenu.classList.toggle("active");
        });

        groupOptions.forEach(option => {
            option.addEventListener("click", () => {
                let selectedOption = option.querySelector(".option-text").innerText;
                groupSBtnText.innerText = selectedOption;
                groupMenu.classList.remove("active");
            });
        });

        educatorOptions.forEach(option => {
            option.addEventListener("click", () => {
                let selectedOption = option.querySelector(".option-text-educator").innerText;  // NOTE: using option-text-educator
                educatorSBtnText.innerText = selectedOption;
                educatorMenu.classList.remove("active");
            });
        });
    });
   $(document).ready(function() {
     $('.join-btn').click(function() {
       var group_no = $('.checkbox-input:checked').data('group-no');
       var game_no = $('.checkbox-input:checked').data('game-no');

       if (group_no && game_no) { // 그룹을 선택한 경우
         $.ajax({
           type: 'POST',
           url: '/student/group/join',
           data: {
             group_no: group_no,
             game_no: game_no
           },
           dataType: 'text',
           success: function(response) {
             if (response === "applyable") {
               alert('가입이 완료 되었습니다.');
             } else {
               alert('잔여 T/O가 없어 가입이 불가능 합니다.');
             }
           },
           error: function(xhr, status, error) {
             console.error('오류 발생:', error);
           }
         });
       } else {
         alert('그룹을 선택해주세요.');
       }
     });
   });

    window.onload = function() {
            // .currentCnt와 .totalCnt 요소를 가져옵니다.
            var currentCountElement = document.querySelector('.currentCnt');
            var totalCountElement = document.querySelector('.totalCnt');

            // #moreBtn 요소를 가져옵니다.
            var moreButton = document.getElementById('moreBtn');

            // .currentCnt와 .totalCnt의 값을 비교합니다.
            if (parseInt(currentCountElement.textContent) >= parseInt(totalCountElement.textContent)) {
                // 값이 동일하거나 .currentCnt가 더 크다면, #moreBtn을 숨깁니다.
                moreButton.style.display = 'none';
            }
        }
 });
