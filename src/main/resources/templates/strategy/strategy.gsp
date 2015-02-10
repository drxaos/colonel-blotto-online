<html>
<head>
    <meta name="layout" content="main"/>
    <title>Игра полковника Блотто</title>
    <sitemesh:parameter name="current" value="strategy"/>
    <sitemesh:parameter name="username" value="${player?.fullName}"/>
</head>

<body>

<div class="page-header text-center">
    <h1>Игра полковника Блотто</h1>
</div>

<p class="text-center">
    Распределите 100 солдат по 9 полям сражений. Ваша цель — победить на как можно <br>
    большем числе полей в битве с каждым другим участником игры!
</p>

<br>

<form class="strategy__form  text-center" name="form" action="/strategy/update" method="post">

    <div class="row">
        <div class="col-md-4"></div>

        <div class="col-md-4 strategy__container">

            <div class="row ">

                <div class="col-sm-4">
                    <input class="form-control input-sm  strategy__field" onblur="findTotal()" id="f1"
                           type="number"
                           min="0" max="100" step="1" value="${player.strategy.f1}" name="f1"
                           onkeypress="return isNumberKey(event)">
                </div>

                <div class="col-sm-4">
                    <input class="form-control input-sm  strategy__field" onblur="findTotal()" id="f2"
                           type="number"
                           min="0" max="100" step="1" value="${player.strategy.f2}" name="f2"
                           onkeypress="return isNumberKey(event)">
                </div>

                <div class="col-sm-4 ">
                    <input class="form-control input-sm  strategy__field" onblur="findTotal()" id="f3"
                           type="number"
                           min="0" max="100" step="1" value="${player.strategy.f3}" name="f3"
                           onkeypress="return isNumberKey(event)">
                </div>
            </div>

            <div class="row">

                <div class="col-sm-4">
                    <input class="form-control input-sm  strategy__field" onblur="findTotal()" id="f4"
                           type="number"
                           min="0" max="100" step="1" value="${player.strategy.f4}" name="f4"
                           onkeypress="return isNumberKey(event)">
                </div>

                <div class="col-sm-4">
                    <input class="form-control input-sm  strategy__field" onblur="findTotal()" id="f5"
                           type="number"
                           min="0" max="100" step="1" value="${player.strategy.f5}" name="f5"
                           onkeypress="return isNumberKey(event)">
                </div>

                <div class="col-sm-4">
                    <input class="form-control input-sm  strategy__field" onblur="findTotal()" id="f6"
                           type="number"
                           min="0" max="100" step="1" value="${player.strategy.f6}" name="f6"
                           onkeypress="return isNumberKey(event)">
                </div>
            </div>

            <div class="row">

                <div class="col-sm-4">
                    <input class="form-control input-sm  strategy__field" onblur="findTotal()" id="f7"
                           type="number"
                           min="0" max="100" step="1" value="${player.strategy.f7}" name="f7"
                           onkeypress="return isNumberKey(event)">
                </div>

                <div class="col-sm-4">
                    <input class="form-control input-sm  strategy__field" onblur="findTotal()" id="f8"
                           type="number"
                           min="0" max="100" step="1" value="${player.strategy.f8}" name="f8"
                           onkeypress="return isNumberKey(event)">
                </div>

                <div class="col-sm-4">
                    <input class="form-control input-sm  strategy__field" onblur="findTotal()" id="f9"
                           type="number"
                           min="0" max="100" step="1" value="${player.strategy.f9}" name="f9"
                           onkeypress="return isNumberKey(event)">
                </div>
            </div>

        </div>

        <div class="col-md-4"></div>
    </div>

    <br>

    <p>Количество солдат на полях сражений: <span class="strategy__total"></span> <span
            class="strategy__totalError"></span></p>

    <p>
        <span class="strategy__totalErrorMessage">
            Вы не сможете принимать участие в боях, пока не выведете ровно 100 солдат на поля!
        </span>
    </p>

    <br>

    <button type="submit" class="btn btn-primary">
        <span class="glyphicon glyphicon-ok" aria-hidden="true"></span> Утвердить
    </button>
</form>


<script type="text/javascript">

    $('.strategy__form').ajaxForm(function (data) {
        BUS.trigger("page.alert", data);
    });

    var tick = '<img src="https://d396qusza40orc.cloudfront.net/gt/images/tick_new.png" style="width:16px;margin: 0 0 3px 5px;"/>';
    var cross = '<img src="https://d396qusza40orc.cloudfront.net/gt/images/cross_new.png" style="width:16px;margin: 0 0 3px 2px;"/>';

    sum = 0;

    function findTotal() {
        pattern = /^[0]+\d*$/;
        sum = 0;

        $(".strategy__field").each(function (el) {
            var val = $(this).val();
            if (val == "" || pattern.test(val)) {
                $(this).val(val = "0");
            }
            if (parseInt(val)) {
                sum += parseInt(val);
            }

        });

        $('.strategy__total').text(sum);

        if (sum == 100) {
            $('.strategy__totalError').html(tick);
            $('.strategy__totalErrorMessage').css("color", "white");
        } else {
            $('.strategy__totalError').html(cross);
            $('.strategy__totalErrorMessage').css("color", "red");
        }
    }

    $('.strategy__total').text(sum);

    function isNumberKey(evt) {
        var charCode = (evt.which) ? evt.which : evt.keyCode;
        if (charCode != 46 && charCode != 8 && charCode > 31 && (charCode < 48 || charCode > 57)) {
            return false;
        }
        return true;
    }

    findTotal();
</script>

</body>
</html>