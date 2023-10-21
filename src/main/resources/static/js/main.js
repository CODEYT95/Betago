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
    let slideIndex = 0;
    let slideTimer;
    const slides = document.querySelectorAll(".slider-image");
    const dots = document.querySelectorAll(".dot");

    function showSlide(n) {
        for (let i = 0; i < slides.length; i++) {
            slides[i].style.display = "none";
        }
        for (let i = 0; i < dots.length; i++) {
            dots[i].className = dots[i].className.replace(" active-dot", "");
        }

        slideIndex = n;
        if (slideIndex >= slides.length) {
            slideIndex = 0;
        }
        if (slideIndex < 0) {
            slideIndex = slides.length - 1;
        }
        slides[slideIndex].style.display = "block";
        dots[slideIndex].className += " active-dot";
    }

    function nextSlide() {
        slideIndex++;
        showSlide(slideIndex);
    }

    // 초기에 슬라이드를 시작
    slideTimer = setTimeout(function autoSlide() {
        nextSlide();
        slideTimer = setTimeout(autoSlide, 2000);
    }, 2000);

    dots.forEach((dot, index) => {
        dot.addEventListener("click", function() {
            clearTimeout(slideTimer); // dot 클릭시 슬라이드 타이머 중지
            showSlide(index);
        });
    });
    slides.forEach(function(image) {
        image.addEventListener("click", function() {
            var url = image.getAttribute("data-url");
            if (url) {
                window.location.href = url;
            }
        });
    });

    let isPaused = false;
    document.getElementById("pauseBtn").addEventListener("click", function() {
        const pauseIcon = this.querySelector(".fa-pause");
        const playIcon = this.querySelector(".fa-play");
        if (isPaused) {
            nextSlide();
            slideTimer = setTimeout(function autoSlide() {
                nextSlide();
                slideTimer = setTimeout(autoSlide, 2000);
            }, 2000);
            pauseIcon.style.display = "inline-block";
            playIcon.style.display = "none";
        } else {
            clearTimeout(slideTimer);
            pauseIcon.style.display = "none";
            playIcon.style.display = "inline-block";
        }
        isPaused = !isPaused;
    });
});

