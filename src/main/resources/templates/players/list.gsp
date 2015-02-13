<html>
<head>
    <meta name="layout" content="main"/>
    <title>Игра полковника Блотто</title>
    <sitemesh:parameter name="current" value="players"/>
</head>

<body>

<div class="page-header text-center">
    <h1>Предполагаемые противники</h1>
</div>

<div class="row">
    <div class="col-md-3"></div>

    <div class="col-md-6">

        <p class="text-right">
            <a href="#p${player.id}" class="link__self">Найти себя</a>
        </p>

        <div class="list-group">
            <g:each in="${list}" var="p" status="n">
                <a class="list-group-item ${p.id == player.id ? 'active' : ''}"
                   href="#p${p.id}" name="p${p.id}">
                    <g:if test="${p.score > 0}">
                        <span class="badge">${p.score}</span>
                    </g:if>
                    ${n + 1}. ${p.fullName} (${p.username})
                </a>
            </g:each>
        </div>
    </div>

    <div class="col-md-3"></div>

</div>

</body>
</html>