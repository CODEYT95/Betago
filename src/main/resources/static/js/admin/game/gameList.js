// DOMContentLoaded 이벤트가 발생하면 실행되는 함수
document.addEventListener('DOMContentLoaded', function() {
    // 필요한 DOM 요소들을 선택
    const checkboxes = document.querySelectorAll('.checkbox-input');
    const subscribeButton = document.getElementById('subscribeButton');
    const gameTitleSelect = document.getElementById("gameTitleSelect");
    const searchButton = document.getElementById("searchButton");
    const gameItems = document.querySelectorAll('.list-box li');
    const selectedGameNos = [];
    const myButton = document.getElementById("myBtn");

    // 체크박스 중 하나 이상이 선택되었는지 확인하는 함수
    function isAnyCheckboxChecked() {
        return Array.from(checkboxes).some(checkbox => checkbox.checked);
    }


    // '구독' 버튼 상태 업데이트 함수
    function updateSubscribeButtonState() {
        if (isAnyCheckboxChecked()) {
            subscribeButton.removeAttribute('disabled');
        } else {
            subscribeButton.setAttribute('disabled', 'disabled');
        }
    }

    // 체크박스 상태 변경 시 실행되는 함수
    function handleCheckboxChange() {
        const gameNo = this.getAttribute('data-game-no');
        console.log(gameNo);
        if (this.checked) {
            selectedGameNos.push(Number(gameNo));
        } else {
            const index = selectedGameNos.indexOf(Number(gameNo));
            if (index !== -1) {
                selectedGameNos.splice(index, 1);
            }
        }
        updateSubscribeButtonState();
    }

    // 게임 제목 선택 변경 시 실행되는 함수
    function handleGameTitleChange(event) {
        event.preventDefault();
        const selectedGameTitle = document.getElementById('gameTitleSelect').value;
        const newURL = '/game/list?game_title=' + encodeURIComponent(selectedGameTitle);
        window.location.href = newURL;
    }

    // '검색' 버튼 클릭 시 실행되는 함수
    function handleSearchButtonClick(e) {
        e.preventDefault();
        const selectedGameTitle = gameTitleSelect.value;
        gameItems.forEach(item => {
            const gameTitle = item.getAttribute('data-game-title');
            if (gameTitle === selectedGameTitle || !selectedGameTitle) {
                item.style.display = 'block';
            } else {
                item.style.display = 'none';
            }
        });
    }

     // '구독' 버튼 클릭 시 실행되는 함수
    function handleSubscribeButtonClick(e) {
        const selectedGameNo = Array.from(document.querySelectorAll('.checkbox-input:checked'))
            .map(cb => cb.getAttribute('data-game-no'))
            .join(",");

        if (selectedGameNo.length === 0) {
            e.preventDefault();
            alert("게임을 선택해주세요!");
        } else {
            console.log(selectedGameNo);
            window.location.href = "/pay/cartList?game_no=" + selectedGameNo;
        }
    }
    // 페이지 초기화 함수
    function initializePage() {
        $(".checkbox-input:checked").prop("checked", false); // jQuery 사용 제거
    }

    // 페이지 스크롤 시 호출되는 함수
    function scrollFunction() {
        if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {
            myButton.style.display = "block";
        } else {
            myButton.style.display = "none";
        }
    }

    // 맨 위로 스크롤하는 함수
    function topFunction() {
        const currentScroll = document.documentElement.scrollTop || document.body.scrollTop;
        if (currentScroll > 0) {
            window.requestAnimationFrame(topFunction);
            window.scrollTo(0, currentScroll - (currentScroll / 10));
        }
    }

    // 각 이벤트 리스너 등록
    checkboxes.forEach(checkbox => {
        checkbox.addEventListener('change', handleCheckboxChange);
    });
    searchButton.addEventListener('click', handleGameTitleChange);
    if (subscribeButton) {
        subscribeButton.addEventListener('click', handleSubscribeButtonClick);
    }
    myButton.addEventListener("click", topFunction);
    window.onload = initializePage;
    window.onpageshow = function(event) {
        if (event.persisted) initializePage();
    };
    window.onscroll = scrollFunction;

    //아작스로 추가 데이터 불러오기
    var offset = 0;

    $("#moreBtn").click(function() {
        offset += 1;
        var title = $("#title").val();
        $.ajax({
            url: "/game/list",
            type: "POST",
            data: {
                offset: offset,
                title: title
            },
            dataType: "json",
            success: function(response) {
                response.forEach(item => {
                    $(".list-box").append(`
                        <li data-game-no="${item.game_no}">
                            <div class="checkbox">
                                <label class="checkbox-wrapper">
                                    <input type="checkbox" class="checkbox-input" data-game-no="${item.game_no}" name="game_nos" value="${item.game_no}" />
                                    <span class="checkbox-title">
                                    <div class="card">
                                        <div class="poster"><img src="/image/game/${item.filegame_name}" alt="${item.filegame_name}></div>
                                        <div class="card-details"></div>
                                        <div class="details">
                                            <h5>컨텐츠 이름 : <span>${item.game_title}</span></h5>
                                            <h5>구매금액 : <span>${item.game_sell}원</span></h5>
                                            <h5>구독기간 : <span>${item.game_date}개월</span></h5>
                                            <h5>그룹가능인원  : <span>${item.game_total}명</span></h5>
                                        </div>
                                        <div class="backDetails">
                                            <div class="detaillist">
                                                <h5>난이도 : <span>${item.game_level}</span></h5>
                                                <h5>총인원 : <span>${item.game_total}명</span></h5>
                                                <h5>가격 : <span>${item.game_price}원</span></h5>
                                                <h5>할인 : <span>${item.game_discount}%</span></h5>
                                                <h5>판매가 : <span>${item.game_sell}원</span></h5>
                                                <h6>상품상세설명 : <span>${item.game_content}</span></h6>
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
    //현재 체크박스 갯수 업데이트
    $(document).ready(function() {
        updateLiCount();
    });
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

    $(document).ready(function () {
        $('#deleteButton').click(function () {
            const selectedGameNo = $('input[name="game_nos"]:checked').val();

            if (selectedGameNos.length === 0) {
                alert("게임을 선택하세요.");
                return;
            } else if (selectedGameNos.length > 1) {
                alert("한 번에 하나의 게임만 선택하세요.");
                return;
            }

            // AJAX 요청을 사용하여 컨트롤러에 데이터를 전송
            $.ajax({
                type: 'POST',
                url: '/game/updateGame',
                data: { game_no: selectedGameNo },
                success: function (response) {
                    if (response == 0) {
                        alert("선택한 게임은 삭제할 수 없습니다.");
                    } else if (response == 1) {
                        alert("선택한 게임이 삭제되었습니다.");
                        location.reload();
                    } else if (response == 2) {
                        alert("다시 시도해주세요.");
                    }
                },
                error: function () {
                    alert("서버 오류가 발생했습니다.");
                }
            });
        });
    });

});