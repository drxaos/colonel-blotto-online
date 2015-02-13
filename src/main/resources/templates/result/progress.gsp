<html>
<head>
    <meta name="layout" content="main"/>
    <title>Игра полковника Блотто</title>
    <sitemesh:parameter name="current" value="result"/>
</head>

<body>

<div class="page-header text-center">
    <h1>Подождите, идет сражение...</h1>
</div>

<div class="text-center">
    <a href="/result?counter=${counter + 1}" class="btn btn-primary">
        <span class="glyphicon glyphicon-refresh" aria-hidden="true"></span>
        Обновить
    </a>
</div>

</body>
</html>