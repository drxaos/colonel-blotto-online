<html>
<head>
    <meta name="layout" content="main"/>
    <title>Игра полковника Блотто</title>
</head>

<body>

<div class="row">
    <div class="col-md-3"></div>

    <div class="col-md-6">
        <div class="page-header">
            <h1>Игра полковника Блотто</h1>
        </div>

        <div class="wrapper">
            <form class="form-signin text-left" action="/login/authenticate" method="post">

                <h3>Логин</h3>
                <input type="text" class="form-control" name="username" placeholder="Имя" required="" autofocus=""/>

                <h3>Пароль</h3>
                <input type="password" class="form-control" name="password" placeholder="Пароль" required=""/>

                <div style="padding-left: 20px; margin-bottom: 50px">
                    <a href="/signup" class="pull-right" style="margin: 10px 0; color: red"><b>Регистрация</b></a>
                    <label class="checkbox pull-left">
                        <input type="checkbox" value="remember-me" id="rememberMe" name="rememberMe"
                               checked="checked"> Запомнить меня
                    </label>
                </div>
                <button class="btn btn-lg btn-primary btn-block login__submitButton" type="submit">В бой!</button>
            </form>
        </div>
    </div>

    <div class="col-md-3"></div>
</div>
<script>
    var error = ${error};
    if (error == 1) {
        BUS.trigger("page.alert", {alert: "error", message: "Не удалось войти"})
    }
</script>
</body>
</html>