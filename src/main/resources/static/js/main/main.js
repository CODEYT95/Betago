document.addEventListener("DOMContentLoaded", function() {
    let position = 0;
    const slidesToShow = 3;
    const slidesToScroll = 3;
    const containerWidth = document.querySelector(".slider").offsetWidth;
    const gap = parseInt(getComputedStyle(document.querySelector(".slide-track")).gap);
    const slideWidth = (containerWidth - gap * (slidesToShow - 1)) / slidesToShow;
    let totalSlides = document.querySelectorAll(".second-box .slide").length;

    // 이미지 최대 6개로 제한
    totalSlides = Math.min(totalSlides, 6);

    const prevBtn = document.getElementById("prevBtn");
    const nextBtn = document.getElementById("nextBtn");

    function setPosition() {
        document.querySelector(".slide-track").style.transform = `translateX(${position}px)`;
        checkButtonState();
    }

    function checkButtonState() {
        prevBtn.disabled = position >= 0;
        nextBtn.disabled = -position >= (slideWidth + gap) * (totalSlides - slidesToShow);
    }

    window.previousSlide = function() {
        if (prevBtn.disabled) return;

        position += (slideWidth + gap) * slidesToScroll;
        if (position > 0) {
            position = 0;
        }
        setPosition();
    }

    window.nextSlide = function() {
        if (nextBtn.disabled) return;

        position -= (slideWidth + gap) * slidesToScroll;
        if (-position > (slideWidth + gap) * (totalSlides - slidesToShow)) {
            position = -((slideWidth + gap) * (totalSlides - slidesToShow));
        }
        setPosition();
    }

    setPosition();
});
