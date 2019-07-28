<#import "../macro/expensesForm.ftl" as form>
<#import "../pager.ftl" as p>

<#macro expensesMacro path messH3>
    <div class="container">
        <div>
            <#if messH3??>
                <h3>${messH3}</h3>
            </#if>
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
                <#assign dateAdd = .now>
                <#assign pathAdd = "${path}/add">
                <@form.expensesAdd pathAdd, 0, dateAdd, "", 0.0, "" "Добавить"/>
            </tr>
            <#list page.content as exp>
                <tr>
                    <#if (idEdit?? && exp.id==idEdit)>
                        <#assign pathEdit = "${path}/edit">
                        <@form.expensesAdd pathEdit exp.id, exp.date, exp.description, exp.value, exp.comment, "Изменить"/>
                    <#else >
                        <td>${exp.date?datetime?string("dd.MM.yy")}</td>
                        <td>${exp.date?datetime?string("HH:mm:ss")}</td>
                        <td>${exp.description}</td>
                        <td>${exp.value}</td>
                        <td>${exp.comment}</td>
                        <td>
                            <a href="${path}/delete?id=${exp.id}"
                               class="btn btn-danger float-right mr-2"
                               role="button">Delete</a>
                            <a href="${path}/edit?id=${exp.id}"
                               class="btn btn-secondary float-right mr-2"
                               role="button">Edit</a>
                        </td>
                    </#if>
                </tr>
            </#list>
            </tbody>
        </table>
        <#assign url = "${path}">
        <@p.pager page url />
    </div>

</#macro>