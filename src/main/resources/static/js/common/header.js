//화면 줄어들면 사이드 바 생성
$(document).ready(function() {
    let navbar = document.querySelector(".header-navi");
    let navLinks = document.querySelector(".navi_links");
    let menuOpenBtn = document.querySelector("#main_sideBtn");
    let menuCloseBtn = document.querySelector(".navi_links .bx-x");

    menuOpenBtn.onclick = function() {
        navLinks.style.left = "0";
    }
    menuCloseBtn.onclick = function() {
        navLinks.style.left = "-100%";
    }
    let useInfoArrow = document.querySelector(".useInfo-arrow");
    useInfoArrow.onclick = function() {
        navLinks.classList.toggle("show1");
    }
    let moreArrow = document.querySelector(".more-arrow");
    moreArrow.onclick = function() {
        navLinks.classList.toggle("show2");
    }
    let subscribeArrow = document.querySelector(".subscribe-arrow");
    subscribeArrow.onclick = function() {
        navLinks.classList.toggle("show3");
    }
    let learningArrow = document.querySelector(".learning-arrow");
    learningArrow.onclick = function() {
        navLinks.classList.toggle("show4");
    }
    let educatorArrow = document.querySelector(".educator-arrow");
    educatorArrow.onclick = function() {
        navLinks.classList.toggle("show5");
    }
//    let adminArrow = document.querySelector(".admin-arrow");
//    adminArrow.onclick = function() {
//        navLinks.classList.toggle("show6");
//    }
});