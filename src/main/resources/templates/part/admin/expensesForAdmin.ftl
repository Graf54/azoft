<#assign path="/expenses/admin">
<#import "../expensesForm.ftl" as form>
<div class="container">
    <div>
        <h3>Расходы ${userName}</h3>
    </div>
    <table class="table">
        <thead class="thead-dark">
        <tr>
            <th scope="col">Дата</th>
            <th scope="col">Время</th>
            <th scope="col">Описание</th>
            <th scope="col">Стоимость</th>
            <th scope="col">Комментарий</th>
            <th scope="col">Редактор</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <#assign dateNow = .now>
            <@form.expensesForm path+"/add" 0, dateNow, "", 0.0, "", userId/>
        </tr>
        <#list expenses as exp>
            <tr>
                <#if (idEdit?? && exp.id==idEdit)>
                    <form action="/expenses/admin/edit" name="expenses" method="post">
                        <input type="hidden" id="id" name="id" value="${(exp.id)!}">
                        <input type="hidden" id="id" name="id" value="${(userId)!}">
                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                        <td>
                            <div class="form-group">
                                <input type="date" class="form-control" id="dateS" name="dateS"
                                       placeholder="Введите дату"
                                       required
                                       value="${exp.date?date?string("yyyy-MM-dd")}">
                            </div>
                        </td>
                        <td>
                            <div class="form-group">
                                <input type="time" required
                                       class="form-control" id="timeS" name="timeS"
                                       placeholder="Введите время"
                                       title="Введите время"
                                       value="${exp.date?time}">
                            </div>
                        </td>

                        <td>
                            <div class="form-group">
                                <input type="text" class="form-control" id="description"
                                       name="description"
                                       maxlength="255"
                                       placeholder="Введите описание"
                                       title="Введите описание"
                                       value="${exp.description!}">
                            </div>
                        </td>
                        <td>
                            <div class="form-group">
                                <input type="number" step="0.01" min="0.00" max="1000000000.00" class="form-control"
                                       id="value"
                                       name="value"
                                       required
                                       placeholder="Введите стоимость"
                                       title="Введите стоимость"
                                       value="${exp.value?string["0.##"]}">
                            </div>
                        </td>
                        <td>
                            <div class="form-group">
                                <input type="text" class="form-control" id="comment" name="comment"
                                       placeholder="Введите комментарий"
                                       title="Введите комментарий"
                                       maxlength="255"
                                       value="${exp.comment!}">
                            </div>
                        </td>
                        <td>
                            <button type="submit" class="btn btn-primary">Изменить</button>
                        </td>
                    </form>
                <#else >
                    <td>${exp.date?datetime?string("dd.MM.yy")}</td>
                    <td>${exp.date?datetime?string("HH:mm:ss")}</td>
                    <td>${exp.description}</td>
                    <td>${exp.value}</td>
                    <td>${exp.comment}</td>
                    <td>
                        <a href="${path}/delete?id=${exp.id}&userId=${userId}"
                           class="btn btn-danger float-right mr-2"
                           role="button">Delete</a>
                        <a href="${path}/edit?id=${exp.id}&userId=${userId}"
                           class="btn btn-secondary float-right mr-2"
                           role="button">Edit</a>
                    </td>
                </#if>
            </tr>
        </#list>
        </tbody>
    </table>
</div>