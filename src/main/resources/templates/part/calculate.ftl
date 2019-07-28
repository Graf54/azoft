<h3>Расчет за период</h3>
<table class="table">
    <thead class="thead-dark">
    <tr>
        <th scope="col">Начало</th>
        <th scope="col">Конец</th>
        <th scope="col">Общие расходы</th>
        <th scope="col">Средний расход в день</th>
        <th scope="col">Редактор</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <form action="/expenses/calc" name="calculate" method="get">
            <td>
                <div class="form-group">
                    <input type="datetime-local" required
                           
                           class="form-control" id="calcStart" name="calcStart"
                           value="${calc.start?datetime?string.xs_ms_nz}">
                </div>

            </td>
            <td>
                <div class="form-group">
                    <input type="datetime-local" required
                           class="form-control" id="calcEnd" name="calcEnd"
                           value="${calc.end?datetime?string.xs_ms_nz}">
                </div>

            </td>
            <td>
                ${calc.total!}
            </td>
            <td>
                ${calc.average!}
            </td>
            <td>
                <button type="submit" class="btn btn-primary">Подсчитать</button>
            </td>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        </form>
    </tr>
    </tbody>
</table>