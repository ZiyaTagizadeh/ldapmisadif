$(document).ready(function () {
    $("ul .nav-item").click(function () {
        $(".nav-item").removeClass("active");
        $(this).addClass("active");
    });
});
