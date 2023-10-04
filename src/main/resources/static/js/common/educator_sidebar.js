$(document).ready(function() {
    const groupMenu = document.querySelector(".select-menu-group");
    const selectGroupBtn = groupMenu.querySelector(".select-btn-group");
    const groupOptions = groupMenu.querySelectorAll(".option");
    const groupSBtnText = groupMenu.querySelector(".sBtn-text");

    selectGroupBtn.addEventListener("click", () => groupMenu.classList.toggle("active"));
    groupOptions.forEach(option => {
        option.addEventListener("click", () => {
            let selectedOption = option.querySelector(".option-text").innerText;
            groupSBtnText.innerText = selectedOption;
            groupMenu.classList.remove("active");
        });
    });
    /*
    const educatorMenu = document.querySelector(".select-menu");
    const selectEducatorBtn = educatorMenu.querySelector(".select-btn-educator");
    const educatorOptions = educatorMenu.querySelectorAll(".option");
    const educatorSBtnText = educatorMenu.querySelector(".sBtn-text");

    selectEducatorBtn.addEventListener("click", () => educatorMenu.classList.toggle("active"));
    educatorOptions.forEach(option => {
        option.addEventListener("click", () => {
            let selectedOption = option.querySelector(".option-text").innerText;
            educatorSBtnText.innerText = selectedOption;
            educatorMenu.classList.remove("active");
        });
    });
    */
});