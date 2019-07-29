<#import "part/macro/common.ftl" as c>
<#import "part/macro/pager.ftl" as p>
<#include "part/security.ftl">
<@c.page>
    <h3>Список пользователей</h3>
    <#if message??>
        <h4 class="text-danger">${message}</h4>
    </#if>
    <table class="table">
        <thead class="thead-dark">
        <tr>
            <th>Имя пользователя</th>
            <th>Роли</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <#--            FIND BY DATE -->
        <tr>
            <form action="/users/find" name="find" method="get">
                <td>
                    <div class="form-group">
                        <input type="text"
                               class="form-control" id="filter" name="filter"
                               placeholder="Введите имя или часть имени"
                               title="Введите имя или часть имени"
                               maxlength="50"
                               required
                                <#if filter??>
                                    value="${filter}"
                                <#else >
                                    value=""
                                </#if>
                        >
                    </div>
                </td>
                <td></td>
                <td>
                    <button type="submit" class="btn btn-primary">Найти</button>
                </td>
            </form>
        </tr>
        <#list page.content as usr>
            <tr>
                <td>${usr.username}</td>
                <td><#list usr.roles as role>${role.name}<#sep>, </#list></td>
                <td>
                    <a href="/users/delete?id=${usr.id}"
                       class="btn btn-danger float-right mr-2"
                       role="button">Delete</a>
                    <a href="/users/edit?id=${usr.id}"
                       class="btn btn-secondary float-right mr-2"
                       role="button">Edit</a>
                    <#if isAdmin>
                        <a href="/expenses/admin/user?id=${usr.id}"
                           class="btn btn-secondary float-right mr-2"
                           role="button">Расходы</a>
                    </#if>
                </td>
            </tr>
        </#list>
        </tbody>
    </table>
    <@p.pager page url parametrs/>
</@c.page>
