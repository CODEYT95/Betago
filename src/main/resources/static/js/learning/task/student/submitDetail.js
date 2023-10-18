function goBack() {
    window.history.back();
}

document.addEventListener("DOMContentLoaded", function() {
    // 이벤트 핸들러 내에서 코드 실행
    var evalSpan = document.getElementById("eval");
    var taskEval = evalSpan.textContent;

    if (taskEval === '미흡') {
        evalSpan.style.color = 'red';
    } else if (taskEval === '보통') {
        evalSpan.style.color = '#ffaa00';
    } else if (taskEval === '우수') {
        evalSpan.style.color = 'green';
    }

    var stateSpan = document.querySelector(".m_state");
    var state = stateSpan.textContent;

    if(state === '제출완료'){
        stateSpan.style.color = 'green';
    } else {
        stateSpan.style.color = '#8d8d8e';
    }
});