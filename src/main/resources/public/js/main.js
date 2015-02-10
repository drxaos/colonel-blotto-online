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

    var curHeight = $('.alertsHolder__container').height();
    if (curHeight < 1) {
        curHeight = 1;
    }

    $(".alertsHolder__container").html("");
    if (message && type != 'none') {
        message = '<p class="alert__msg">' + message + '</p>';
        $(".alertsHolder__container").html(
            "<div class=\"alert alert-" + type + " alertsHolder__alert alert-dismissible\" style=\"opacity:0.1; z-index: 999; margin-bottom: 0;\" role=\"alert\">"
            + "<button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\"><span aria-hidden=\"true\">&times;</span></button>"
            + message
            + "</div>"
        );
        $('.alertsHolder__container').css('height', 'auto');
        var autoHeight = $('.alertsHolder__container').height();
        $('.alertsHolder__container').height(curHeight).animate({height: autoHeight}, {
            duration: 500, complete: function () {
                $(".alertsHolder__alert").animate({opacity: 1}, 200);
            }
        });

        $('.alertsHolder__alert').bind('closed.bs.alert', function () {
            var curHeight = $('.alertsHolder__container').height();
            if (curHeight < 1) {
                curHeight = 1;
            }
            $('.alertsHolder__container').css('height', 'auto');
            var autoHeight = $('.alertsHolder__container').height();
            $('.alertsHolder__container').height(curHeight).animate({height: autoHeight}, 500);
            $(".alertsHolder__container").html("&nbsp");
        });

    }
    var newPageHeight = $(document).height();
    $(document).scrollTop($(document).scrollTop() + newPageHeight - pageHeight);
});