<html>
<head>
    <meta name="layout" content="main"/>
    <title>Игра полковника Блотто</title>
</head>

<body>

<div class="page-header">
    <h1>Игра полковника Блотто</h1>
</div>

<div class="wrapper">
    <form class="form-signin text-left" action="/login/authenticate" method="post">

        <h3>Укажите ваше имя</h3>
        <input type="text" class="form-control" name="username" placeholder="Имя" required="" autofocus=""/>

        <h3>Ключевое слово (пароль)</h3>
        <input type="password" class="form-control" name="password" placeholder="Пароль" required=""/>

        <h3>Ваш E-Mail</h3>
        <input type="text" class="form-control" name="email" placeholder="E-Mail" required="" autofocus=""/>

        <div style="padding-left: 20px;">
            <label class="checkbox">
                <input type="checkbox" value="remember-me" id="rememberMe" name="rememberMe"
                       checked="checked"> Запомнить меня
            </label>
        </div>
        <button class="btn btn-lg btn-primary btn-block" type="submit">В бой!</button>
    </form>
</div>

</body>
</html>