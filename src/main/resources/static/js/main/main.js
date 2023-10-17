document.addEventListener("DOMContentLoaded", function() {
    // For game-box
    let gamePosition = 0;
    const gameSlidesToShow = 4;
    const gameSlidesToScroll = 4;
    const gameContainerWidth = document.querySelector(".slider").offsetWidth;
    const gameGap = parseInt(getComputedStyle(document.querySelector(".slide-track")).gap);
    const gameSlideWidth = (gameContainerWidth - gameGap * (gameSlidesToShow - 1)) / gameSlidesToShow;
    let gameTotalSlides = document.querySelectorAll(".game-box .slide").length;

    gameTotalSlides = Math.min(gameTotalSlides, 8);

    const gamePrevBtn = document.getElementById("prevBtn");
    const gameNextBtn = document.getElementById("nextBtn");

    function setGamePosition() {
        document.querySelector(".game-box .slide-track").style.transform = `translateX(${gamePosition}px)`;
        checkGameButtonState();
    }

    function checkGameButtonState() {
        gamePrevBtn.disabled = gamePosition >= 0;
        gameNextBtn.disabled = -gamePosition >= (gameSlideWidth + gameGap) * (gameTotalSlides - gameSlidesToShow);
    }

 window.previousGameSlide = function() {
        if (gamePrevBtn.disabled) return;
        gamePosition += (gameSlideWidth + gameGap) * gameSlidesToScroll;
        if (gamePosition > 0) {
            gamePosition = 0;
        }
        setGamePosition();
    }

    window.nextGameSlide = function() {
            if (gameNextBtn.disabled) return;
            gamePosition -= (gameSlideWidth + gameGap) * gameSlidesToScroll;
            if (-gamePosition > (gameSlideWidth + gameGap) * (gameTotalSlides - gameSlidesToShow)) {
                gamePosition = -((gameSlideWidth + gameGap) * (gameTotalSlides - gameSlidesToShow));
            }
            setGamePosition();
        }

    setGamePosition();

   // For video-box
   let videoPosition = 0;
    const videoSlidesToShow = 4;
    const videoSlidesToScroll = 4;
    const videoContainerWidth = document.querySelector(".v-slider").offsetWidth;
    const videoGap = parseInt(getComputedStyle(document.querySelector(".v-slide-track")).gap);
    const videoSlideWidth = (videoContainerWidth - videoGap * (videoSlidesToShow - 1)) / videoSlidesToShow;
    let videoTotalSlides = document.querySelectorAll(".video-box iframe").length;

    videoTotalSlides = Math.min(videoTotalSlides, 8);

    const videoPrevBtn = document.getElementById("v-prevBtn");
    const videoNextBtn = document.getElementById("v-nextBtn");

    function setVideoPosition() {
        document.querySelector(".video-box .v-slide-track").style.transform = `translateX(${videoPosition}px)`;
        checkVideoButtonState();
    }

    function checkVideoButtonState() {
        videoPrevBtn.disabled = videoPosition >= 0;
        videoNextBtn.disabled = -videoPosition >= (videoSlideWidth + videoGap) * (videoTotalSlides - videoSlidesToShow); // 수정된 조건문
    }

    window.v_previousVideoSlide = function() {
        if (videoPrevBtn.disabled) return;
        videoPosition += (videoSlideWidth + videoGap) * videoSlidesToScroll;
        if (videoPosition > 0) {
            videoPosition = 0;
        }
        setVideoPosition();
    }

    window.v_nextVideoSlide = function() {
        if (videoNextBtn.disabled) return;
        videoPosition -= (videoSlideWidth + videoGap) * videoSlidesToScroll;
        if (-videoPosition > (videoSlideWidth + videoGap) * (videoTotalSlides - videoSlidesToShow)) {
            videoPosition = -((videoSlideWidth + videoGap) * (videoTotalSlides - videoSlidesToShow));
        }
        setVideoPosition();
    }
    setVideoPosition();

     // For automatic image-slider
        let currentSlideIndex = 0;
        const slides = document.querySelectorAll(".image-slider .slider-image");
        const totalSlides = slides.length;

        setInterval(function() {
            slides[currentSlideIndex].style.display = 'none'; // 현재 이미지 숨김
            currentSlideIndex = (currentSlideIndex + 1) % totalSlides; // 다음 이미지 인덱스 계산
            slides[currentSlideIndex].style.display = 'block'; // 다음 이미지 표시
        }, 3000); // 3초마다 이미지 변경

});