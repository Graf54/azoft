<#macro expensesForm path id date desc value comment userId>
    <form action="${path}" name="expenses" method="post">
        <input type="hidden" id="id" name="id" value="${id}">
        <#if userId??>
            <input type="hidden" id="userId" name="userId" value="${userId}">
        </#if>
        <td>
            <div class="form-group">
                <input type="date"
                       class="form-control" id="dateS" name="dateS"
                       placeholder="Введите день"
                       required
                       value="${date?date?string("yyyy-MM-dd")}">
            </div>
        </td>
        <td>
            <div class="form-group">
                <input type="time"
                       class="form-control" id="timeS" name="timeS"
                       placeholder="Введите время"
                       required
                       value="${date?time}">
            </div>
        </td>
        <td>
            <div class="form-group">
                <input type="text" class="form-control" id="description"
                       name="description"
                       placeholder="Введите описание"
                       value="${desc}">
            </div>
        </td>
        <td>
            <div class="form-group">
                <input type="number" step="0.01" min="0.00" max="1000000.00" class="form-control"
                       id="value"
                       name="value"
                       required
                       placeholder="Введите стоимость"
                       value="${value}">
            </div>
        </td>
        <td>
            <div class="form-group">
                <input type="text" class="form-control" id="comment" name="comment"
                       placeholder="Введите комментарий"
                       value="${comment}">
            </div>
        </td>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <td>
            <button type="submit" class="btn btn-primary">Добавить</button>
        </td>
    </form>
</#macro>