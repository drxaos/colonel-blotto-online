<%@ page import="blotto.Application" %>
<g:applyLayout name="email">

    <mail:subject>Вы зарегистрированы на ${Application.resolveValue("app.host")}</mail:subject>

    Добро пожаловать, ${fullName}
    <p>
        Ваш логин: ${username}<br>
        Пароль: ${password}<br>
    </p>

</g:applyLayout>