<%@ page import="grails.util.Holders" %>
<g:applyLayout name="email">

    <mail:subject>Вы зарегистрированы на ${Holders.config.app.host}</mail:subject>

    Добро пожаловать, ${fullName}
    <p>
        Ваш логин: ${username}<br>
        Пароль: ${password}<br>
    </p>

</g:applyLayout>