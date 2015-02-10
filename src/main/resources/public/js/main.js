var BUS = {};

// Работа с событиями
BUS.on = function (name, handler) {
    $(document).bind("EVENT_" + name, handler);
};
BUS.trigger = function (name, params) {
    $(document).trigger("EVENT_" + name, [params]);
};
BUS.once = function (name, handler) {
    $(document).one("EVENT_" + name, handler);
};
$(document).ready(function () {
    BUS.trigger("page.ready")
});

BUS.on("page.alert", function (event, data) {
    var message = data.message;
    var type = data.alert;
    if (type == "error") {
        type = "danger"
    }

    var pageHeight = $(document).height();

    $(".alertsHolder__container").html("");
    if (message && type != 'none') {
        message = '<p class="alert__msg">' + message + '</p>';
        $(".alertsHolder__container").html(
            "<div class=\"alert alert-" + type + " alertsHolder__alert alert-dismissible\" style=\"opacity:0.1; z-index: 999\" role=\"alert\">"
            + "<button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\"><span aria-hidden=\"true\">&times;</span></button>"
            + message
            + "</div>"
        );
        $(".alertsHolder__alert").animate({opacity: 1}, 1000);
    }
    var newPageHeight = $(document).height();
    $(document).scrollTop($(document).scrollTop() + newPageHeight - pageHeight);
});