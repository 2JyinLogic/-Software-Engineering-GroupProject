// $("header .userinfo .user img").click(function () {
//   $(this).parents(".user").find(".user_wrap").toggleClass("show");
// });

$(".services .dropdown span").click(function () {
    $(".services .dp_list").toggleClass("show");
});

$(".grooming_tab .tab").click(function () {
    $(this).addClass("cur").siblings().removeClass("cur");
    $(this)
        .parents(".wrap")
        .find(".tabs_conent .wraps")
        .eq($(this).index())
        .addClass("cur")
        .siblings()
        .removeClass("cur");
});

$("main .grooming_tab .wrap .tabs_conent .wraps .left>div").click(function () {
    $(this).addClass("sel").siblings().removeClass("sel");
});

$(".box1 .left .item").hover(function () {
    $(this)
        .parents(".wraps")
        .find(".right_wraps")
        .eq($(this).index())
        .addClass("cur")
        .siblings()
        .removeClass("cur");
});

$(".box3 .left .item").hover(function () {
    $(this).addClass("cur").siblings().removeClass("cur");
    $(this)
        .parents(".wraps")
        .find(".right_wraps")
        .eq($(this).index())
        .addClass("cur")
        .siblings()
        .removeClass("cur");
});

$(".box4 li .top").click(function () {
    $(this).parents("li").toggleClass("cur");
    $(this).siblings().slideToggle();
});

const swiper = new Swiper(".swiper", {
    loop: true,
    autoplay: {
        delay: 10000,
    },
    pagination: {
        el: ".swiper-pagination",
        clickable: true,
    },
});



$(".box2 li .btm .more").click(function () {
    var id = $(this).data('id');
    $('#' + id + '-box').slideToggle("show");
});

$(".side_box .mask, .side_box .close").click(function () {
    $(this).closest(".side_box").slideToggle("show");
});


//signIn and signOut
// const signInLink = document.getElementById('sign-in-link');
// const userWrap = document.querySelector('.user_wrap');

// signInLink.addEventListener('click', () => {

//     userWrap.innerHTML = `
//         <a th:href="@{/my/profile}">My Account</a>
//         <a th:href="@{/appointment}">Make Appointments</a>
//         <a th:href="#" id="sign-out-link">Sign Out</a>
//     `;


//     const signOutLink = document.getElementById('sign-out-link');
//     signOutLink.addEventListener('click', () => {

//         userWrap.innerHTML = `
//             <a href="th:href="@{/account/register}" id="sign-in-link">Sign In</a>
//         `;
//     });
// });
