<html>
<head>
    <meta name="layout" content="main"/>
    <title>Игра полковника Блотто</title>
    <sitemesh:parameter name="current" value="result"/>
</head>

<body>

<g:if test="${best}">

    <div class="page-header text-center">
        <h1>Лучшие стратегии</h1>
    </div>

    <div class="row text-center">
        <div class="col-lg-3 col-sm-0 col-xs-2"></div>

        <div class="col-lg-6 col-sm-12 col-xs-8">

            <g:each in="${best}" var="st">
                <div class="col-lg-1 col-sm-1">&nbsp;</div>

                <div class="col-lg-3 col-sm-3 strategy__view">
                    <div class="row">
                        <div class="col-xs-4">${st.f1}</div>

                        <div class="col-xs-4">${st.f2}</div>

                        <div class="col-xs-4">${st.f3}</div>
                    </div>

                    <div class="row ">
                        <div class="col-xs-4">${st.f4}</div>

                        <div class="col-xs-4">${st.f5}</div>

                        <div class="col-xs-4">${st.f6}</div>
                    </div>

                    <div class="row ">
                        <div class="col-xs-4">${st.f7}</div>

                        <div class="col-xs-4">${st.f8}</div>

                        <div class="col-xs-4">${st.f9}</div>
                    </div>
                </div>
            </g:each>
        </div>

        <div class="col-lg-3 col-sm-0 col-xs-2"></div>

    </div>
</g:if>

<g:if test="${player.position > 0}">
    <div class="page-header row text-center">
        <div class="col-sm-2 col-xs-1"></div>

        <div class="col-sm-9 col-xs-10">
            <h3>
                Ваша стратегия заняла ${player.position} место,
                сыграв ${player.wins + player.loses + player.draws} игр,
                из них ${player.wins} победы,
                ${player.loses} поражений
                и ${player.draws} игр вничью.</h3>
        </div>

        <div class="col-sm-1 col-xs-1"></div>
    </div>
</g:if>

<div class="page-header row text-center">
    <div class="col-sm-2 col-xs-1"></div>

    <div class="col-sm-9 col-xs-10">
        <h1>Следующее сражение через <span class="results__clock"></span></h1>
        <div class="result__refreshContainer">
            <g:render template="result/refresh"/>
        </div>
    </div>

    <div class="col-sm-1 col-xs-1"></div>
</div>

<script>

    var next = "${next}";

    function updateClock () {
        $('.results__clock').text(toHHMMSS(next));
        if(next <= 0){
            $(".result__refreshContainer").fadeIn();
        }
        if (next > 0) {
            next--;
        }
    }
    updateClock();
    setInterval(updateClock, 1000);

</script>

</body>
</html>