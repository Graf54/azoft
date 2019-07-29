<#include "security.ftl">
<#import "macro/login.ftl" as l>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="/">Tracker</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <#if user??>
                <li class="nav-item">
                    <a class="nav-link" href="/expenses">Мои расходы</a>
                </li>
            </#if>
            <#if isAdmin||isManager>
                <li class="nav-item">
                    <a class="nav-link" href="/users">Список пользователей</a>
                </li>
            </#if>
        </ul>

        <div class="navbar-text mr-3"><#if user??>${name}<#else>Please, login</#if></div>
        <@l.logout />
    </div>
</nav>
