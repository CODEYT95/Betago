document.addEventListener("DOMContentLoaded", function() {
    //본인 인증 값에 따라 input 변경
    $(document).ready(function () {
        $("#phoneInfo").hide();
        $("#emailInfo").hide();

        $("#emailField").hide();
        $("#phoneField").hide()

        var phone = $("#phoneInfo2").text();
        var email = $("#emailInfo2").text();


        if (email !== "") {
            $("#emailInfo").show();
            $("#email").prop("disabled", true);
            $("#phoneField").show();
        }
        if (phone !== "") {
            $("#phoneInfo").show();
            $("#phone").prop("disabled", true);
            $("#emailField").show();
        }
    });

    //생년월일 드롭다운
    $(document).ready(function () {
        var now = new Date();
        var year = now.getFullYear();
        var mon = (now.getMonth() + 1) > 9 ? '' + (now.getMonth() + 1) : '0' + (now.getMonth() + 1);
        var day = (now.getDate()) > 9 ? '' + (now.getDate()) : '0' + (now.getDate());

        //년도
        for (var i = 1900; i <= year; i++) {
            $('#year').append('<option value="' + i + '">' + i + '년</option>');

        }

        //월
        for (var i = 1; i <= 12; i++) {
            var mm = i > 9 ? i : "0" + i;
            $('#month').append('<option value="' + mm + '">' + mm + '월</option>');
        }

        //일
        for (var i = 1; i <= 31; i++) {
            var dd = i > 9 ? i : "0" + i;
            $('#day').append('<option value="' + dd + '">' + dd + '일</option>');
        }
        $("#year > option[value=" + year + "]").attr("selected", "true");
        $("#month > option[value=" + mon + "]").attr("selected", "true");
        $("#day > option[value=" + day + "]").attr("selected", "true");

        // 각 드롭다운을 "출생 연도", "월", "일"로 기본 값으로 설정
        $('#year').val('출생 연도'); // "출생 연도"로 설정
        $('#month').val('월'); // "월"로 설정
        $('#day').val('일'); // "일"로 설정
    });

    //생년월일 합치기
    function birth() {
        const year = document.getElementById("year").value;
        const month = document.getElementById("month").value;
        const day = document.getElementById("day").value;

        // 년, 월, 일을 하나의 문자열로 합칩니다. (예: "2023-09-26")
        const birth = year + "-" + month + "-" + day;

        // member_birth 필드에 값을 설정합니다.
        document.getElementById("member_birth").value = birth;

    }

    //아이디 유효성
    $("#id").keyup(function() {
        var idValue = $(this).val();
        var idPattern = /^(?=.*[a-z])(?=.*\d)[a-z0-9]{4,20}$/;
        var id_ok = $(".id_ok");
        var id_required = $(".id_required");
        var id_pattern = $(".id_pattern");

        if (idValue === "") {
            id_required.show();
            id_pattern.hide();
            id_ok.hide();
            return;
        }

        if (!idPattern.test(idValue)) {
            id_required.hide();
            id_pattern.show();
            id_ok.hide();
            return;
        }

        id_required.hide();
        id_pattern.hide();
        id_ok.show();

        //아이디 중복검사
        checkId(idValue);
    });

    //아이디 동기화방식 중복체크
    function checkId(){
        var id = $('#id').val();
        if (id === "") {
            // 아이디가 비어있으면 중복 검사를 실행하지 않음
            return;
        }
        $.ajax({
            url:'idCheck', //controller요청주소
            type:'post',
            data:{id:id},
            success:function (cnt){
                console.log(cnt)
                if(cnt == 0){ //cnt 1 = 중복 0 사용가능
                    $('.id_ok').css("display","inline-block");
                    $('.id_already').css("display","none");
                }else{
                    $('.id_already').css("display","inline-block");
                    $('.id_ok').css("display","none");
                }
            },
            error:function (){
                alert("error")
            }
        });
    }

    $(document).ready(function() {
        // 비밀번호 유효성 검사
        $("#pw").keyup(function() {
            var pwValue = $(this).val();
            var pwPattern = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[\W_]).{8,}$/;
            var pw_required = $(".pw_required");
            var pw_pattern = $(".pw_pattern");

            if (pwValue === "") {
                pw_required.show();
                pw_pattern.hide();
            } else if (!pwPattern.test(pwValue)) {
                pw_required.hide();
                pw_pattern.show();
            } else {
                pw_required.hide();
                pw_pattern.hide();
            }

            checkPwd(); // 비밀번호 확인 함수 호출
        });

        // 비밀번호 확인 일치 여부 검사
        $("#repw").keyup(function() {
            checkPwd();
        });
    });

    // 비밀번호 일치여부 확인 함수
    function checkPwd() {
        var pw = $("#pw").val();
        var repw = $('#repw').val();
        var pwCheck = $('.pwCheck');

        if (pw !== repw) {
            pwCheck.show();
        } else {
            pwCheck.hide();
        }
    }

    //생년월일 유효성
    const birthArr = [-1, -1, -1];
    const birthErrorMsgEl = document.querySelector('.error-msg');

    const birthYearEl = document.querySelector('#year');
    const birthMonthEl = document.querySelector('#month');
    const birthDayEl = document.querySelector('#day');

    birthYearEl.addEventListener('change', () => {
        birthArr[0] = birthYearEl.value;
        checkBirthValid(birthArr);
    });

    birthMonthEl.addEventListener('change', () => {
        birthArr[1] = birthMonthEl.value;
        checkBirthValid(birthArr);
    });

    birthDayEl.addEventListener('change', () => {
        birthArr[2] = birthDayEl.value;
        checkBirthValid(birthArr);
    });

    function checkBirthValid(birthArr) {
        let isBirthValid = true;
        const y = parseInt(birthArr[0]);
        const m = parseInt(birthArr[1]);
        const d = parseInt(birthArr[2]);

        if (y > 0 && m > 0 && d > 0) {
            if (m === 4 || m === 6 || m === 9 || m === 11) {
                if (d === 31) {
                    isBirthValid = false;
                }
            } else if (m === 2) {
                if (((y % 4 === 0) && (y % 100 !== 0)) || (y % 400 === 0)) {
                    if (d > 29) {
                        isBirthValid = false;
                    }
                } else {
                    if (d > 28) {
                        isBirthValid = false;
                    }
                }
            } else {
                if (d > 31) {
                    isBirthValid = false;
                }
            }

            if (isBirthValid) {
                birthErrorMsgEl.textContent = "";
                // 이 부분에서 생년월일을 취합할 수 있습니다.
                const birthDate = y + '-' + (m < 10 ? '0' : '') + m + '-' + (d < 10 ? '0' : '') + d;
                document.getElementById('member_birth').value = birthDate;
            } else {
                birthErrorMsgEl.textContent = "생년월일을 다시 확인해주세요";
                birthErrorMsgEl.style.color = "red";
                document.getElementById('member_birth').value = "";
            }
        }
    }

    //핸드폰번호 유효성
    $("#phone").keyup(function() {
        var phoneValue = $(this).val();
        var phonePattern = /^01[016789]\d{3,4}\d{4}$/;
        var phone_required = $(".phone_required");
        var phone_pattern = $(".phone_pattern");

        if (phoneValue === "") {
            phone_required.show();
            phone_pattern.hide();
            return;
        }

        if (!phonePattern.test(phoneValue)) {
            phone_required.hide();
            phone_pattern.show();
            return;
        }

        phone_required.hide();
        phone_pattern.hide();
    });

    //이메일 유효성
    $("#email").keyup(function() {
        var emailValue = $(this).val();
        var emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
        var email_required = $(".email_required");
        var email_pattern = $(".email_pattern");

        if (emailValue === "") {
            email_required.show();
            email_pattern.hide();
            return;
        }

        if (!emailPattern.test(emailValue)) {
            email_required.hide();
            email_pattern.show();
            return;
        }

        email_required.hide();
        email_pattern.hide();
    });
$(document).ready(function() {
    $('#email').keyup(function() {
        var email = $(this).val();

        $.ajax({
            url: '/member/emailCheck',
            type: 'POST',
            data: {email: email},
            success: function(response) {
                if(response === 1) {
                $('.email_pattern').hide();
                    $('.email_duplicate').show();
                } else if(response === 0) {
                    $('.email_duplicate').hide();
                }
            },
            error: function(error) {
                console.log(error);
            }
        });
    });
});
        // 라디오 버튼 변경 이벤트 핸들러
        $('.typeRadio').change(function () {
            if ($(this).val() === 'ROLE_TEACHER') {
                $('#codeInput').show(); // 교육자 선택 시 input 표시
            } else {
                $('#codeInput').hide(); // 회원 선택 시 input 숨김
            }
        });
});

document.addEventListener('DOMContentLoaded', function () {
    const submitBtn = document.getElementById('submitBtn');
    const idInput = document.getElementById('id');
    const pwInput = document.getElementById('pw');
    const repwInput = document.getElementById('repw');
    const yearSelect = document.getElementById('year');
    const monthSelect = document.getElementById('month');
    const daySelect = document.getElementById('day');
    const phoneInput = document.getElementById('phone');
    const emailInput = document.getElementById('email');
    const teacherRadio = document.getElementById('teacher');
    const userRadio = document.getElementById('user');
    const maleRadio = document.getElementById('male');
    const femaleRadio = document.getElementById('female');
    const telNumberInput = document.getElementById('telNumberSelect');
    const noPhoneNumberCheckbox = document.getElementById('noPhoneNumber');
    const roleCodeInput = document.getElementById('roleCode');
    const emailInfo = document.getElementById('emailInfo2');
    const phoneInfo = document.getElementById('phoneInfo2');
    const emailPatternInfo = document.querySelector('.email_pattern');
    const emailDuplicateInfo = document.querySelector('.email_duplicate');

    function checkIdValidity() {
        const idValue = idInput.value.trim();
        const isIdValid = idValue !== '';
        const isIdPatternValid = /^[a-z0-9]{4,20}$/.test(idValue);

        document.querySelector('.id_required').style.display = isIdValid ? 'none' : 'inline';
        document.querySelector('.id_pattern').style.display = isIdPatternValid || idValue === '' ? 'none' : 'inline';

        return isIdValid && isIdPatternValid;
    }

    function checkPwValidity() {
        const pwValue = pwInput.value.trim();
        const isPwValid = pwValue !== '';
        const isPwPatternValid = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[\W_]).{8,}$/.test(pwValue);

        document.querySelector('.pw_required').style.display = isPwValid ? 'none' : 'inline';
        document.querySelector('.pw_pattern').style.display = isPwPatternValid || pwValue === '' ? 'none' : 'inline';

        return isPwValid && isPwPatternValid;
    }

    function checkRepwValidity() {
        const pwValue = pwInput.value.trim();
        const repwValue = repwInput.value.trim();
        const isRepwValid = repwValue !== '';
        const isPwMatch = pwValue === repwValue;

        document.querySelector('.pwCheck').style.display = isPwMatch || repwValue === '' ? 'none' : 'inline';

        return isRepwValid && isPwMatch;
    }

    function checkEmailValidity() {
        const emailValue = emailInput.value.trim();
        const isEmailPatternValid = /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(emailValue);
        emailPatternInfo.style.display = isEmailPatternValid || emailValue === '' ? 'none' : 'inline';

        const emailInfoText = emailInfo.textContent.trim();
        const isEmailDuplicate = emailInfoText === '이미 사용중인 이메일입니다.';
        emailDuplicateInfo.style.display = isEmailDuplicate ? 'inline' : 'none';

        return isEmailPatternValid && !isEmailDuplicate;
    }

    function checkFields() {
        const isIdValid = checkIdValidity();
        const isPwValid = checkPwValidity();
        const isRepwValid = checkRepwValidity();
        const isYearSelected = yearSelect.value !== '출생 연도';
        const isMonthSelected = monthSelect.value !== '월';
        const isDaySelected = daySelect.value !== '일';
        const isRoleSelected = teacherRadio.checked || userRadio.checked;
        const isGenderSelected = maleRadio.checked || femaleRadio.checked;
        const isTelNumberValid = noPhoneNumberCheckbox.checked || telNumberInput.value.trim() !== '';
        const isRoleCodeValid = !teacherRadio.checked || (teacherRadio.checked && roleCodeInput.value.trim() !== '');

        const phoneInfoText = phoneInfo.textContent.trim();
        const isPhoneValid = phoneInfoText === '' || phoneInput.value.trim() !== '';

        const isEmailValid = checkEmailValidity();

        return isIdValid && isPwValid && isRepwValid && isYearSelected && isMonthSelected && isDaySelected && isPhoneValid && isEmailValid && isRoleSelected && isGenderSelected && isTelNumberValid && isRoleCodeValid;
    }

    function updateSubmitButton() {
        submitBtn.disabled = !checkFields();
        if (!submitBtn.disabled) {
            submitBtn.style.backgroundColor = 'blue';
            submitBtn.style.color = 'white';
        } else {
            submitBtn.style.backgroundColor = 'grey';
            submitBtn.style.color = 'black';
        }
    }

    idInput.addEventListener('input', updateSubmitButton);
    pwInput.addEventListener('input', updateSubmitButton);
    repwInput.addEventListener('input', updateSubmitButton);
    yearSelect.addEventListener('change', updateSubmitButton);
    monthSelect.addEventListener('change', updateSubmitButton);
    daySelect.addEventListener('change', updateSubmitButton);
    phoneInput.addEventListener('input', updateSubmitButton);
    emailInput.addEventListener('input', updateSubmitButton);
    teacherRadio.addEventListener('change', updateSubmitButton);
    userRadio.addEventListener('change', updateSubmitButton);
    maleRadio.addEventListener('change', updateSubmitButton);
    femaleRadio.addEventListener('change', updateSubmitButton);
    telNumberInput.addEventListener('input', updateSubmitButton);
    roleCodeInput.addEventListener('input', updateSubmitButton);
    noPhoneNumberCheckbox.addEventListener('change', function () {
        if (noPhoneNumberCheckbox.checked) {
            telNumberInput.value = '';
            telNumberInput.readOnly = true;
        } else {
            telNumberInput.readOnly = false;
        }
        updateSubmitButton();
    });
});

//생년월일 선택에 따른 회원 ROLE값 유형 변경
document.addEventListener('DOMContentLoaded', function () {
    const yearSelect = document.getElementById('year');
    const monthSelect = document.getElementById('month');
    const daySelect = document.getElementById('day');
    const userRadio = document.getElementById('user');

    function calculateAge(birthDate) {
        const today = new Date();
        let age = today.getFullYear() - birthDate.getFullYear();
        const m = today.getMonth() - birthDate.getMonth();
        if (m < 0 || (m === 0 && today.getDate() < birthDate.getDate())) {
            age--;
        }
        return age;
    }

    function updateRoleBasedOnAge() {
        const year = parseInt(yearSelect.value);
        const month = parseInt(monthSelect.value) - 1;
        const day = parseInt(daySelect.value);
        if (!isNaN(year) && !isNaN(month) && !isNaN(day)) {
            const birthDate = new Date(year, month, day);
            const age = calculateAge(birthDate);
            console.log('Calculated age:', age);
            if (age >= 6 && age <= 12) {
                userRadio.value = 'ROLE_STUDENT';
                console.log('Role changed to ROLE_STUDENT');
            } else {
                userRadio.value = 'ROLE_USER';
                console.log('Role changed to ROLE_USER');
            }
        }
    }

    yearSelect.addEventListener('change', updateRoleBasedOnAge);
    monthSelect.addEventListener('change', updateRoleBasedOnAge);
    daySelect.addEventListener('change', updateRoleBasedOnAge);
});

$(document).ready(function() {
    $('#roleCode').keyup(function() {
        var eduCode = $(this).val();

        $.ajax({
            url: '/member/codeCheck',
            type: 'POST',
            data: {edu_code: eduCode},
            success: function(response) {
                if(response === 0) {
                    $('.code_no').show();
                    $('.code_dup').hide();
                } else if(response === 1) {
                    $('.code_dup').show();
                    $('.code_no').hide();
                } else if(response === 2) {
                    $('.code_dup').hide();
                    $('.code_no').hide();
                }
            },
            error: function(error) {
                console.log(error);
            }
        });
    });
});

$(document).ready(function() {
    $('#email').keyup(function() {
        var email = $(this).val().trim();

        if (email === '') {
            $('.email_required').show();
            $('.email_duplicate').hide();
        } else {
            $('.email_required').hide();

            $.ajax({
                url: '/emailCheck',
                type: 'POST',
                data: {email: email},
                success: function(response) {
                    if(response === 1) {
                        $('.email_duplicate').show();
                    } else {
                        $('.email_duplicate').hide();
                    }
                },
                error: function(error) {
                    console.log(error);
                }
            });
        }
    });
});


