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
            <h1>Регистрация</h1>
        </div>

        <div class="wrapper">
            <form class="form-signup text-left" action="/signup/process" method="post">

                <div class="form-group">
                    <h3 class="control-label">Укажите ваше имя</h3>
                    <input type="text" class="form-control" name="fullName" placeholder="Имя" required="" autofocus=""/>
                    <span class="help-block"></span>
                </div>

                <div class="form-group">
                    <h3 class="control-label">Выберите логин</h3>
                    <input type="text" class="form-control" name="username" placeholder="Логин" required=""/>
                    <span class="help-block"></span>
                </div>

                <div class="form-group">
                    <h3 class="control-label">Ключевое слово (пароль)</h3>
                    <input type="password" class="form-control" name="password" placeholder="Пароль" required=""/>
                    <span class="help-block"></span>
                </div>

                <div class="form-group">
                    <h3 class="control-label">Ваш E-Mail</h3>
                    <input type="text" class="form-control" name="email" placeholder="E-Mail" required="" autofocus=""/>
                    <span class="help-block"></span>
                </div>

                <div style="height: 30px">&nbsp;</div>

                <button class="btn btn-lg btn-danger btn-block" type="submit">Начать войну</button>
            </form>
        </div>
    </div>

    <div class="col-md-3"></div>

    <script type="text/javascript">

        $('.form-signup').ajaxForm(function (answer) {
            BUS.trigger("page.alert", answer);
            if (answer.data && answer.data.redirect) {
                window.location = answer.data.redirect;
            }
        });
    </script>
</div>
</body>
</html>