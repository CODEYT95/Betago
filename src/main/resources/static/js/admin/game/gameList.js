document.addEventListener('DOMContentLoaded', function() {
    // Variables
    const checkboxes = document.querySelectorAll('.checkbox-input');
    const subscribeButton = document.getElementById('subscribeButton');
    const gameTitleSelect = document.getElementById("gameTitleSelect");
    const searchButton = document.getElementById("searchButton");
    const gameItems = document.querySelectorAll('.list-box li');
    const selectedGameNos = [];
    const myButton = document.getElementById("myBtn");

    // Check if any checkbox is checked
    function isAnyCheckboxChecked() {
        return Array.from(checkboxes).some(checkbox => checkbox.checked);
    }

    // Update the state of the subscribe button
    function updateSubscribeButtonState() {
        if (isAnyCheckboxChecked()) {
            subscribeButton.removeAttribute('disabled');
        } else {
            subscribeButton.setAttribute('disabled', 'disabled');
        }
    }

    // Handle checkbox change
    function handleCheckboxChange() {
        const gameNo = this.getAttribute('data-game-no');
        if (this.checked) {
            selectedGameNos.push(Number(gameNo));
        } else {
            const index = selectedGameNos.indexOf(Number(gameNo));
            if (index !== -1) {
                selectedGameNos.splice(index, 1);
            }
        }
        updateSubscribeButtonState();
    }

    // Handle game title dropdown change
    function handleGameTitleChange() {
        const selectedGameTitle = this.options[this.selectedIndex].val();
        const defaultOption = this.querySelector("option[value='game_title']");
        defaultOption.textContent = selectedGameTitle;
    }

    // Handle search button click
    function handleSearchButtonClick(e) {
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
    }

    // Handle subscribe button click
    function handleSubscribeButtonClick(e) {
        if (!isAnyCheckboxChecked()) {
            e.preventDefault();
            alert("게임을 선택해주세요!");
            return;
        }
        const checkedCheckboxes = document.querySelectorAll('.checkbox-input:checked');
        const selectedGameNo = Array.from(checkedCheckboxes).map(cb => cb.getAttribute('data-game-no')).join(",");
        window.location.href = "/pay/cartList?game_no=" + selectedGameNo;
    }

    // Initialize the page
    function initializePage() {
        $(".checkbox-input:checked").prop("checked", false);
    }

    // Handle scroll event
    function scrollFunction() {
        if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {
            myButton.style.display = "block";
        } else {
            myButton.style.display = "none";
        }
    }

    // Scroll to top function
    function topFunction() {
        const currentScroll = document.documentElement.scrollTop || document.body.scrollTop;
        if (currentScroll > 0) {
            window.requestAnimationFrame(topFunction);
            window.scrollTo(0, currentScroll - (currentScroll / 10));
        }
    }

    // Event Listeners
    checkboxes.forEach(checkbox => {
        checkbox.addEventListener('change', handleCheckboxChange);
    });
    gameTitleSelect.addEventListener("change", handleGameTitleChange);
    searchButton.addEventListener('click', handleSearchButtonClick);
    subscribeButton.addEventListener('click', handleSubscribeButtonClick);
    myButton.addEventListener("click", topFunction);
    window.onload = initializePage;
    window.onpageshow = function(event) {
        if (event.persisted) initializePage();
    };
    window.onscroll = scrollFunction;
});