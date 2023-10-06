document.addEventListener("DOMContentLoaded", function () {
  const submitBtn = document.getElementById("submitButton");
  const checkboxes = document.querySelectorAll(".checkbox-input");

  // 체크박스 상태 확인 및 버튼 활성화/비활성화
  checkboxes.forEach(function (checkbox) {
    checkbox.addEventListener("change", function () {
      let isAnyCheckboxChecked = false;

      checkboxes.forEach(function (checkbox) {
        if (checkbox.checked) {
          isAnyCheckboxChecked = true;
        }
      });

      if (isAnyCheckboxChecked) {
        submitBtn.removeAttribute("disabled");
      } else {
        submitBtn.setAttribute("disabled", "disabled");
      }
    });
  });
    // 모달 닫기 버튼에 이벤트 리스너 추가
    var closeModalButtons = document.querySelectorAll(".conceal");
    closeModalButtons.forEach(function(button) {
        button.addEventListener("click", function() {
            var modal = document.getElementById("myModal"); // 모달 요소 가져오기
            var modalBg = document.querySelector(".modal-bg"); // 모달 배경 요소 가져오기

            // 모달과 모달 배경을 숨기게 설정
            modal.style.display = "none";
            modalBg.style.display = "none";
        });
    });
    //선택한 숙제 정보 모달창에 띄워주기
    $(document).ready(function() {
       $(document).ready(function() {
           $('.selectBtn').click(function() {
               var taskSendNo = $(this).data('tasksend-no');
                console.log(taskSendNo);
               $.ajax({
                   type: 'POST',
                   url: '/student/taskInfo',
                   data: { taskSendNo: taskSendNo },
                   dataType: 'json',
                   success: function(data) {
                       if (data.length > 0) {

                           var task = data[0];
                           var date = new Date(task.task_deadline);
                           var formattedDate = date.getFullYear() + '-' +
                                               ('0' + (date.getMonth() + 1)).slice(-2) + '-' +
                                               ('0' + date.getDate()).slice(-2);

                           $('.m_title').text(task.game_title);
                           $('.m_educator').text(task.member_name);
                           $('.m_content').text(task.task_content);
                           $('.m_chapter').text(task.task_chapter);
                           $('.m_deadline').text(formattedDate);
                           $('.m_state').text('상태 : ' + task.task_state).css('color', task.task_state === '미작성' ? 'red' : 'green');
                       }

                       var modal = document.getElementById("myModal");
                       var modalBg = document.querySelector(".modal-bg");
                       modal.style.display = "block";
                       modalBg.style.display = "block";
                   },
                   error: function() {
                       alert('서버 요청 중 오류가 발생했습니다.');
                   }
               });

           });
       });
    });
});
