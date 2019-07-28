<#macro expensesAdd path id date desc value comment buttonName>
    <form action="${path}" name="expenses" method="post">
        <input type="hidden" id="id" name="id" value="${id}">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <#if userId??>
            <input type="hidden" id="userId" name="userId" value="${userId}">
        </#if>
        <td>
            <div class="form-group">
                <input type="date"
                       class="form-control" id="dateS" name="dateS"
                       placeholder="Введите день"
                       title="Введите день"
                       required
                       value="${date?date?string("yyyy-MM-dd")}">
            </div>
        </td>
        <td>
            <div class="form-group">
                <input type="time"
                       class="form-control" id="timeS" name="timeS"
                       placeholder="Введите время"
                       title="Введите время"
                       required
                       value="${date?time?string["HH:mm"]}">
            </div>
        </td>
        <td>
            <div class="form-group">
                <input type="text" class="form-control" id="description"
                       name="description"
                       maxlength="255"
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
                       value="${value?string.computer}">
            </div>
        </td>
        <td>
            <div class="form-group">
                <input type="text" class="form-control" id="comment" name="comment"
                       placeholder="Введите комментарий"
                       title="Введите комментарий"
                       maxlength="255"
                       value="${comment}">
            </div>
        </td>
        <td>
            <button type="submit" class="btn btn-primary">${buttonName}</button>
        </td>
    </form>
</#macro>