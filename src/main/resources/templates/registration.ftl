<#import "part/macro/common.ftl" as c>
<#import "part/macro/login.ftl" as l>

<@c.page>
    <div class="mb-1">Регистрация</div>
    ${message!}
    <@l.login "/registration" true />
</@c.page>
