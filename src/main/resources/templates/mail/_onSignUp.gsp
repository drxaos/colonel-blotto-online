<g:applyLayout name="email">

    <mail:subject>Вы зарегистрированы на ${serviceName}</mail:subject>

    Добро пожаловать, ${fullName}
    <p>
        Ваш логин: ${username}<br>
        Пароль: ${password}<br>
    </p>

    <g:render template="/mail/footer"/>

</g:applyLayout>