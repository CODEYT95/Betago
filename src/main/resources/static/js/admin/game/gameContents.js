/*현재날짜만 선택*/
document.getElementById('currentDate').value = new Date().toISOString().substring(0, 10);;

/*현재 날짜 이후 선택*/
var now_utc = Date.now();
var timeOff = new Date().getTimezoneOffset() * 60000;
var today = new Date(now_utc - timeOff).toISOString().split("T")[0];
document.getElementById("Date").setAttribute("min", today);

