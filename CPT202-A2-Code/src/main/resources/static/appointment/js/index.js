$("header .userinfo .user img").click(function () {
  $(this).parents(".user").find(".user_wrap").toggleClass("show");
});

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

$(".box2 li .btm .more, .side_box .mask, .side_box i.close").click(function () {
  $(".side_box").slideToggle("show");
});

