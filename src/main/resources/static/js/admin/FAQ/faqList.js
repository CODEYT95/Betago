class FAQ {
    constructor(target) {
        this.target = typeof target === 'string' ? document.querySelector(target) : target;
        this.faqBody = this.target.querySelector('.faq_body');
        this.maxHeight = `${this.faqBody.scrollHeight}px`;
        this.toggleBtn = this.target.querySelector('button');
        this.opened = false;

        this.init();
    }

    init() {
        this.toggleBtn.addEventListener('click', () => {
            this.toggleButton();
        });
    }

    toggleButton() {
        const buttonIcon = this.toggleBtn.querySelector('i');

        if (this.opened) {
            buttonIcon.classList.remove('fa-square-minus');
            buttonIcon.classList.add('fa-square-plus');
            this.faqBody.style.maxHeight = '0';
            this.opened = false;
        } else {
            buttonIcon.classList.remove('fa-square-plus');
            buttonIcon.classList.add('fa-square-minus');
            this.faqBody.style.maxHeight = this.maxHeight;
            this.opened = true;
        }
    }
}