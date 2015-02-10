<!DOCTYPE html>
<html>
<head>
    <title><g:layoutTitle default="Игра полковника Блотто"/></title>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <link href="http://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>

    <link href='favicon.ico' rel='icon'>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" href="/css/main.css"/>
    <script type="text/javascript" src="/js/main.js"></script>
    <script type="text/javascript" src="/js/number-polyfill.js"></script>
    <script type="text/javascript" src="/js/jquery.form.min.js"></script>

    <g:layoutHead/>
</head>

<body>
<%
    def username = pageProperty(name: "page.username") ?: null
%>
<div id="clearfix" class="clearfix">

    <div class="container layout__container">
        <nav class="navbar navbar-inverse">
            <div class="container-fluid">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                            data-target="#bs-example-navbar-collapse-1">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="/">Блотто онлайн</a>
                </div>

                <g:if test="${username}">
                    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                        <ul class="nav navbar-nav">
                            <li <% print pageProperty(name: "page.current") == "strategy" ? 'class="active"' : '' %>>
                                <a href="/strategy">Штаб</a>
                            </li>
                            <li <% print pageProperty(name: "page.current") == "help" ? 'class="active"' : '' %>>
                                <a href="/help">Разведданные</a>
                            </li>
                            <li <% print pageProperty(name: "page.current") == "profile" ? 'class="active"' : '' %>>
                                <a href="/profile">Карточка</a>
                            </li>
                            <li <% print pageProperty(name: "page.current") == "players" ? 'class="active"' : '' %>>
                                <a href="/players">Противники</a>
                            </li>
                            <li <% print pageProperty(name: "page.current") == "result" ? 'class="active"' : '' %>>
                                <a href="/result">Сводки</a>
                            </li>
                        </ul>

                        <ul class="nav navbar-nav navbar-right">
                            <li>
                                <p class="navbar-text ellipsis" style="max-width: 35ex; color: #ffffff">
                                    ${"[${username}]"}
                                </p>
                            </li>
                            <li>
                                <a href="/signout" class="navbar-link" style="text-decoration: underline">Выход</a>
                            </li>
                        </ul>
                    </div>
                </g:if>

            </div>
        </nav>

        <div class="alertsHolder__container" style="height: 1px"></div>

        <g:layoutBody/>

    </div>

</div>
</body>
</html>