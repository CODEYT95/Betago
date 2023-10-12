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
        evalSpan.style.color = 'yellow';
    } else if (taskEval === '우수') {
        evalSpan.style.color = 'green';
    }
});