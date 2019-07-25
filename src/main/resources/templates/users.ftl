<#import "part/common.ftl" as c>

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
        <#list users as usr>
            <tr>
                <td>${usr.username}</td>
                <td><#list usr.roles as role>${role.name}<#sep>, </#list></td>
                <td>
                    <form action="employee.action" method="post" target="myFrame" id="myForm"></form>
                    <a href="/users/delete?id=${usr.id}"
                       class="btn btn-danger float-right mr-2"
                       role="button">Delete</a>
                    <a href="/users/edit?id=${usr.id}"
                       class="btn btn-secondary float-right mr-2"
                       role="button">Edit</a>
                    <a href="/expenses/user?id=${usr.id}"
                       class="btn btn-secondary float-right mr-2"
                       role="button">Расходы</a>
                </td>
            </tr>
        </#list>
        </tbody>
    </table>
</@c.page>
