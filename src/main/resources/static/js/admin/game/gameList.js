function topFunction() {
    const currentScroll = document.documentElement.scrollTop || document.body.scrollTop;

    if (currentScroll > 0) {
        window.requestAnimationFrame(topFunction);
        window.scrollTo(0, currentScroll - (currentScroll / 10));
    }
}

document.addEventListener('DOMContentLoaded', function() {
    const checkboxes = document.querySelectorAll('.checkbox-input');
    const subscribeButton = document.getElementById('subscribeButton');

    function isAnyCheckboxChecked() {
        return Array.from(checkboxes).some(checkbox => checkbox.checked);
    }

    function updateSubscribeButtonState() {
        if (isAnyCheckboxChecked()) {
            subscribeButton.removeAttribute('disabled');
        } else {
            subscribeButton.setAttribute('disabled', 'disabled');
        }
    }

    checkboxes.forEach((checkbox) => {
        checkbox.addEventListener('change', updateSubscribeButtonState);
    });

    updateSubscribeButtonState();

    const selectedGameNos = [];

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

    const gameTitleSelect = document.getElementById("gameTitleSelect");
    const searchButton = document.getElementById("searchButton");
    const gameItems = document.querySelectorAll('.list-box li');

    gameTitleSelect.addEventListener("change", function() {
        const selectedGameTitle = gameTitleSelect.options[gameTitleSelect.selectedIndex].text;
        const defaultOption = gameTitleSelect.querySelector("option[value='game_title']");
        defaultOption.textContent = selectedGameTitle;
    });

    searchButton.addEventListener('click', function(e) {
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
    });

    subscribeButton.addEventListener('click', function() {
        const checkedCheckboxes = document.querySelectorAll('.checkbox-input:checked');
        const selectedGameNo = Array.from(checkedCheckboxes).map(cb => cb.getAttribute('data-game-no'));

        if (selectedGameNo.length > 0) {
            const gameNoParam = selectedGameNo.join(",");
            window.location.href = "/cartList?game_no=" + gameNoParam;
        } else {
            alert("게임을 선택하세요!");
        }
    });

    window.onload = function() {
        initializePage();
    };
    window.onpageshow = function(event) {
        if (event.persisted) {
            initializePage();
        }
    };
    function initializePage() {
        $(".checkbox-input:checked").prop("checked", false);
    }

    const myButton = document.getElementById("myBtn");

    window.onscroll = function() {
        scrollFunction();
    };

    function scrollFunction() {
        if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {
            myButton.style.display = "block";
        } else {
            myButton.style.display = "none";
        }
    }

    myButton.addEventListener("click", function(event) {
        event.stopPropagation();
        topFunction();
    });
});