<html>
<head>
    <meta name="layout" content="main"/>
    <title>Игра полковника Блотто</title>
    <g:layoutHead/>
</head>

<body>

<div class="page-header">
    <h1>Игра полковника Блотто</h1>
</div>

<p>
    Распределите 100 солдат по 9 полям сражений. Ваша цель — победить на как можно <br>
    большем числе полей в битве с каждым другим участником курса!
</p>

<br>

<form name="form">

    <div class="row">
        <div class="col-sm-4"></div>

        <div class="col-sm-2">
            <input class="form-control input-sm col-md-offset-2" onblur="findTotal()" id="f1" type="number"
                   min="0" max="100" step="1" value="0" name="soldiers" onkeypress="return isNumberKey(event)">
        </div>

        <div class="col-sm-2">
            <input class="form-control input-sm col-md-offset-2" onblur="findTotal()" id="f2" type="number"
                   min="0" max="100" step="1" value="0" name="soldiers" onkeypress="return isNumberKey(event)">
        </div>


        <div class="col-sm-2 ">
            <input class="form-control input-sm col-md-offset-2" onblur="findTotal()" id="f3" type="number"
                   min="0" max="100" step="1" value="0" name="soldiers" onkeypress="return isNumberKey(event)">
        </div>
    </div>

    <div class="row">
        <div class="col-sm-4"></div>

        <div class="col-sm-2">
            <input class="form-control input-sm col-md-offset-2" onblur="findTotal()" id="f4" type="number"
                   min="0" max="100" step="1" value="0" name="soldiers" onkeypress="return isNumberKey(event)">
        </div>


        <div class="col-sm-2">
            <input class="form-control input-sm col-md-offset-2" onblur="findTotal()" id="f5" type="number"
                   min="0" max="100" step="1" value="0" name="soldiers" onkeypress="return isNumberKey(event)">
        </div>


        <div class="col-sm-2">
            <input class="form-control input-sm col-md-offset-2" onblur="findTotal()" id="f6" type="number"
                   min="0" max="100" step="1" value="0" name="soldiers" onkeypress="return isNumberKey(event)">
        </div>
    </div>

    <div class="row">
        <div class="col-sm-4"></div>

        <div class="col-sm-2">
            <input class="form-control input-sm col-md-offset-2" onblur="findTotal()" id="f7" type="number"
                   min="0" max="100" step="1" value="0" name="soldiers" onkeypress="return isNumberKey(event)">
        </div>


        <div class="col-sm-2">
            <input class="form-control input-sm col-md-offset-2" onblur="findTotal()" id="f8" type="number"
                   min="0" max="100" step="1" value="0" name="soldiers" onkeypress="return isNumberKey(event)">
        </div>


        <div class="col-sm-2">
            <input class="form-control input-sm col-md-offset-2" onblur="findTotal()" id="f9" type="number"
                   min="0" max="100" step="1" value="0" name="soldiers" onkeypress="return isNumberKey(event)">
        </div>
    </div>
</form>

<p>Количество солдат на полях сражений:</p>

</body>
</html>