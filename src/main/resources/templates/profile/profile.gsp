<html>
<head>
    <meta name="layout" content="main"/>
    <title>Игра полковника Блотто</title>
    <sitemesh:parameter name="current" value="profile"/>
</head>

<body>

<div class="page-header text-center">
    <h1>Личная карточка</h1>
</div>

<div class="row">
    <div class="col-md-3"></div>

    <div class="col-md-6">

        <div class="wrapper">
            <form class="form-profile text-left" action="/profile/update" method="post">

                <div class="form-group">
                    <h3 class="control-label">Логин</h3>
                    <input value="${player.username}" type="text" class="form-control" name="username" placeholder="Логин" required="" readonly="readonly"/>
                    <span class="help-block"></span>
                </div>

                <div class="form-group">
                    <h3 class="control-label">Имя</h3>
                    <input value="${player.fullName}" type="text" class="form-control" name="fullName" placeholder="Имя" required="" autofocus=""/>
                    <span class="help-block"></span>
                </div>

                <div class="form-group">
                    <h3 class="control-label">Пароль</h3>
                    <input value="${player.password}" type="password" class="form-control" name="password" placeholder="Пароль" required=""/>
                    <span class="help-block"></span>
                </div>

                <div class="form-group">
                    <h3 class="control-label">E-Mail</h3>
                    <input value="${player.email}" type="text" class="form-control" name="email" placeholder="E-Mail" required="" autofocus=""/>
                    <span class="help-block"></span>
                </div>

                <div style="height: 30px">&nbsp;</div>

                <button class="btn btn-lg btn-primary btn-block" type="submit">Внести изменения</button>
            </form>
        </div>
    </div>

    <div class="col-md-3"></div>

    <script type="text/javascript">

        $('.form-profile').ajaxForm(function (answer) {
            BUS.trigger("page.alert", answer);
        });
    </script>
</div>


</body>
</html>