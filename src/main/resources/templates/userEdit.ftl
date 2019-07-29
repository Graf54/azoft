<#import "part/macro/common.ftl" as c>

<@c.page>
    <h3>Изменить пользователя</h3>
    <form action="/users/save" method="post">
        <div class="form-group">
            <label for="username">Имя</label>
            <input type="text" class="form-control" id="username" name="username" placeholder="Введите имя"
                   maxlength="50"
                   value="${(usr.username)!}">
        </div>
        <div class="form-group">
            <label for="name">Роли</label>
            <#list roles as role>
                <div>
                    <label><input type="checkbox"
                                  name="${role.name}" ${usr.roles?seq_contains(role)?string("checked", "")} />${role.name}
                    </label>
                </div>
            </#list>
        </div>
        <div class="form-group">
            <label for="password">Пароль</label>
            <input type="password" class="form-control" id="password" name="password" maxlength="255"
                   placeholder="Введите новый пароль"
                   value="">
        </div>
        <input type="hidden" id="id" name="id" value="${(usr.id)!}">
        <input type="hidden" value="${_csrf.token}" name="_csrf"/>
        <button type="submit" class="btn btn-primary">Применить</button>
    </form>

</@c.page>
