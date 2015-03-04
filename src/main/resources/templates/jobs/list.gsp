<%@ page import="blotto.utils.scheduler.JobUtils" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Игра полковника Блотто</title>
    <sitemesh:parameter name="current" value="jobs"/>
</head>

<body>

<div class="table-responsive">
    <table class="table table-hover table-bordered">
        <tr class="active">
            <th class="">Name</th>
            <th class="">Type</th>
            <th class="">State</th>
            <th class="">Period</th>
            <th class="">Last start</th>
            <th class="">Last end</th>
            <th class="">Next run</th>
            <th class="">Cron</th>
            <th class=""></th>
        </tr>
        <g:each in="${jobs}" var="job">
            <tr class="${job.inProgress ? "info" : ""}">
                <td class="" title="${job.fullName}">${job.name}</td>
                <td class="">job</td>
                <td class="">${job.inProgress ? "RUNNING" : "IDLE"}</td>
                <td class="active"></td>
                <td class="">${job.lastStart ?: "None"}</td>
                <td class="">${job.lastEnd ?: "None"}</td>
                <td class="">${job.nextRun}</td>
                <td class="">${job.expression}</td>
                <td class="">
                    <button class="btn btn-primary btn-xs">Run</button>
                    <button class="btn btn-danger btn-xs">Disable</button>
                </td>
            </tr>
        </g:each>
        <g:each in="${tasks}" var="task">
            <tr class="">
                <td class="" title="${task.fullName}">${task.name}</td>
                <td class="">${task.type}</td>
                <td class="active"></td>
                <td class="">${task.period}</td>
                <td class="active"></td>
                <td class="active"></td>
                <td class="${task.type == "cron" ? "" : "active"}">${task.nextRun}</td>
                <td class="${task.type == "cron" ? "" : "active"}">${task.expression}</td>
                <td class="">
                    <button class="btn btn-primary btn-xs">Run</button>
                    <button class="btn btn-info btn-xs">Enable</button>
                </td>
            </tr>
        </g:each>
    </table>
</div>

</body>
</html>