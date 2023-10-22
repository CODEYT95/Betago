document.addEventListener('DOMContentLoaded', function (events) {
function fetchData() {
    var startDate = document.getElementById("startDate").value;
    var endDate = document.getElementById("endDate").value;

    // startDate, endDate 중 하나라도 비어있을 경우에 알림 메시지 표시 및 폼 제출 방지
    if (!startDate || !endDate) {
        alert("날짜를 선택해주세요.");
        event.preventDefault(); // 폼의 기본 제출을 방지
        return; // 이후 로직 실행을 중단
    }

    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4 && xhr.status == 200) {
            var response = JSON.parse(xhr.responseText);

            // 조회 결과를 동적으로 표시
            updateContentsList(response);
        }
    };
   var url = "http://localhost:8800/list?startDate=" + encodeURIComponent(startDate) + "&endDate=" + encodeURIComponent(endDate);
   xhr.open("GET", url, true);
   xhr.send();
}

    const startDateInput = document.getElementById('startDate');

    const endDateInput = document.getElementById('endDate');


    startDateInput.addEventListener('change', function() {

        const startDateValue = startDateInput.value;


        endDateInput.setAttribute('min', startDateValue);

    });
    window.onload = function() {
        var dateDiffs = document.querySelectorAll('.dateDiff');

        dateDiffs.forEach(function(element) {
            var date1 = new Date(element.getAttribute('data-enddate'));
            var date2 = new Date(element.getAttribute('data-startdate'));

            var diff = Math.abs(date1.getTime() - date2.getTime());
            var diffMonths = Math.floor(diff / (1000 * 60 * 60 * 24 * 30.44));
            var diffDays = Math.floor(diff / (1000 * 60 * 60 * 24)) % 30.44;

            if (diffMonths === 0) {
                element.innerText = diffDays + "일";
            } else {
                element.innerText = diffMonths + "개월 " + diffDays + "일";
            }
        });
    }

//체크박스
    const checkboxes = document.querySelectorAll('.checkbox-input');
    const paymentButton = document.getElementById('paymentButton');

    // 체크박스 중 하나라도 체크되어 있다면 true를 반환, 아니면 false를 반환
function isAnyCheckboxChecked() {
    const checked = Array.from(checkboxes).some(checkbox => checkbox.checked);
    console.log('Any checkbox checked:', checked);
    return checked;
}

    // 체크박스의 상태에 따라 버튼 활성화/비활성화 설정
    function updatePaymentButtonState() {
        console.log('Updating button state...');
        if (isAnyCheckboxChecked()) {
            paymentButton.removeAttribute('disabled');
        } else {
            paymentButton.setAttribute('disabled', 'disabled');
        }
    }

    // 체크박스 상태 변경 시 함수 호출
    checkboxes.forEach((checkbox) => {
        checkbox.addEventListener('change', updatePaymentButtonState);
    });
    // 초기 로드 시 한번 확인
    updatePaymentButtonState();

    function topFunction() {
            const currentScroll = document.documentElement.scrollTop || document.body.scrollTop;
            if (currentScroll > 0) {
                window.requestAnimationFrame(topFunction);
                window.scrollTo(0, currentScroll - (currentScroll / 10));
            }
        }

    //---------------modal창-------------------------

        var modal = document.getElementById("cancelSubscriptionModal");
        var modalBg = document.querySelector(".modal-bg");
        var btn = document.getElementById("paymentButton");
        var span = document.getElementsByClassName("close")[0];

        btn.onclick = function() {
            modal.style.display = "block";
            modalBg.style.display = "block";
        }

        span.onclick = function() {
            modal.style.display = "none";
            modalBg.style.display = "none";
        }

        window.onclick = function(event) {
            if (event.target == modal) {
                modal.style.display = "none";
                modalBg.style.display = "none";
            }
        }

        modalBg.addEventListener("click", function() {
          modal.style.display = "none";
          modalBg.style.display = "none";
        });

        document.getElementById("confirmCancel").addEventListener("click", function() {
            document.getElementById("cancelSubscriptionModal").style.display = "none";
        });

        document.getElementById("declineCancel").addEventListener("click", function() {
            document.getElementById("cancelSubscriptionModal").style.display = "none";
            document.querySelector(".modal-bg").style.display = "none";
        });

        document.getElementById("confirmCancel").addEventListener("click", function() {
            var contents = document.querySelectorAll(".checkbox-input");
            var isDeleted = false;

      // 모달 창과 배경을 닫는다.
            document.getElementById("cancelSubscriptionModal").style.display = "none";
            document.querySelector(".modal-bg").style.display = "none";
        });
 });
document.addEventListener('DOMContentLoaded', function() {
    // 선택한 체크박스의 값을 저장할 배열
    const selectedGameNos = [];

    // 체크박스 변경 시 배열에 추가 또는 제거
    const checkboxes = document.querySelectorAll('.checkbox-input');
    checkboxes.forEach((checkbox) => {
        checkbox.addEventListener('change', function() {
            const gameNo = checkbox.getAttribute('data-game-no');
            if (checkbox.checked) {
                selectedGameNos.push(Number(gameNo));
            } else {
                const index = selectedGameNos.indexOf(Number(gameNo));
                if (index !== -1) {
                    selectedGameNos.splice(index, 1);
                }
            }
        });
    });

    // 삭제 버튼 클릭 시
    const deleteButton = document.getElementById('confirmCancel');
    deleteButton.addEventListener('click', function() {
        // 선택한 체크박스의 값을 <form>으로 전송
        const deleteForm = document.getElementById('deleteForm');
        const hiddenInput = document.createElement('input');
        hiddenInput.type = 'hidden';
        hiddenInput.name = 'selectedGameNos';
        hiddenInput.value = selectedGameNos.join(',');  // 수정된 부분
        deleteForm.appendChild(hiddenInput);
        alert("삭제가 완료되었습니다.");
        deleteForm.submit();
    });

    // 현재 체크박스 갯수 업데이트
        updateLiCount();

        function updateLiCount() {
            var liCount = $(".checkbox").length;
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
        var offset = 0;
       $("#moreBtn").click(function() {
           offset += 1;

           var payDate = $(".hidestart").val();
           var endDate = $(".hideend").val();

           $.ajax({
               url: "/list",
               type: "POST",
               data: {
                   offset: offset,
                   startDate: payDate,
                   endDate: endDate
               },
               dataType: "json",
               success: function(response) {
                   response.forEach(item => {
                       var formattedPayDate = new Date(item.pay_date).toLocaleDateString('en-US', { year: 'numeric', month: '2-digit', day: '2-digit' });

                       $("#deleteForm").append(`
                           <div class="checkbox" data-game-no=${item.pay_no}>
                               <label class="checkbox-wrapper">
                                   <input type="checkbox" class="checkbox-input" data-game-no=${item.pay_no} name="selectedGameNos" value="${item.pay_no}" />
                                   <span class="checkbox-title">
                                       <div class="card">
                                           <div class="poster">
                                               <img src="/image/game/${item.filegame_name}">
                                               <div class="card-details">
                                                   <h1>컨텐츠 이름 : <span>${item.game_title}</span></h1>
                                                   <h1>구매금액 : <span>${item.game_sell}</span>원</h1>
                                                   <h1>구독기간 : <span>${item.game_date}</span>개월</h1>
                                                   <h1>구매일자 : <span>${formattedPayDate}</span></h1>
                                               </div>
                                               <div class="details">
                                                   <h5>학습그룹 등록내역 : <span>${item.group_name}</span></h5>
                                               </div>
                                           </div>
                                       </div>
                                   </span>
                               </label>
                           </div>
                       `);
                   });
                   updateLiCount();
               }
           });
       });
       const myButton = document.getElementById("myBtn");
       myButton.addEventListener("click", topFunction);

        function scrollFunction() {
            if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {
                myButton.style.display = "block";
            } else {
                myButton.style.display = "none";
            }
        }

       function topFunction() {
           const currentScroll = document.documentElement.scrollTop || document.body.scrollTop;
           if (currentScroll > 0) {
               window.requestAnimationFrame(topFunction);
               window.scrollTo(0, currentScroll - (currentScroll / 10));
           }
       }
       window.addEventListener("scroll", scrollFunction);
});