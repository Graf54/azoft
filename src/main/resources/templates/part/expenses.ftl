<#assign path="/tracker">
<div class="container">
    <div>
        <h3>Расходы</h3>
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
            <form action="/tracker/add" name="expenses" method="post">
                <input type="hidden" id="id" name="id" value="0">
                <td>
                    <div class="form-group">
                        <input type="date" required
                               class="form-control" id="dateS" name="dateS"
                               placeholder="Введите день"
                               value="">
                        <#--                               value="${.now?date?string("yyyy-dd-MM")}">-->
                    </div>
                </td>
                <td>
                    <div class="form-group">
                        <input type="time" required
                               class="form-control" id="timeS" name="timeS"
                               placeholder="Введите время"
                               value="${.now?time}">
                    </div>
                </td>
                <td>
                    <div class="form-group">
                        <input type="text" class="form-control" id="description"
                               name="description"
                               placeholder="Введите описание"
                               value="">
                    </div>
                </td>
                <td>
                    <div class="form-group">
                        <input type="number" step="0.01" min="0.00" max="1000000.00" class="form-control"
                               id="value"
                               name="value"
                               required
                               placeholder="Введите стоимость"
                               value="">
                    </div>
                </td>
                <td>
                    <div class="form-group">
                        <input type="text" class="form-control" id="comment" name="comment"
                               placeholder="Введите комментарий"
                               value="">
                    </div>
                </td>
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <td>
                    <button type="submit" class="btn btn-primary">Добавить</button>
                </td>
            </form>
        </tr>
        <#list expenses as exp>
            <tr>
                <#if (idEdit?? && exp.id==idEdit)>
                    <form action="/edit" name="expenses" method="post">
                        <input type="hidden" id="id" name="id" value="${(exp.id)!}">
                        <td>
                            <div class="form-group">
                                <input type="date" class="form-control" id="day" name="day"
                                       placeholder="Введите дату"
                                       required
                                       value="${exp.date?date?string("MM/dd/yyyy")}">
                            </div>
                        </td>
                        <td>
                            <div class="form-group">
                                <input type="time" required
                                       class="form-control" id="timeS" name="timeS"
                                       placeholder="Введите время"
                                       value="${exp.date?time}">
                            </div>
                        </td>

                        <td>
                            <div class="form-group">
                                <input type="text" class="form-control" id="description"
                                       name="description"
                                       maxlength="255"
                                       placeholder="Введите описание"
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
                                       value="${exp.value!}">
                            </div>
                        </td>
                        <td>
                            <div class="form-group">
                                <input type="text" class="form-control" id="comment" name="comment"
                                       placeholder="Введите комментарий"
                                       maxlength="255"
                                       value="${exp.comment!}">
                            </div>
                        </td>
                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
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
                        <a href="/tracker/delete?id=${exp.id}" class="btn btn-danger float-right mr-2"
                           role="button">Delete</a>
                        <a href="/tracker/edit?id=${exp.id}" class="btn btn-secondary float-right mr-2"
                           role="button">Edit</a>
                    </td>
                </#if>
            </tr>
        </#list>
        </tbody>
    </table>
</div>
