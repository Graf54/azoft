<h3>Расчет за период</h3>
<table class="table">
    <thead class="thead-dark">
    <tr>
        <th scope="col">Начало</th>
        <th scope="col">Конец</th>
        <th scope="col">Стоимость</th>
        <th scope="col">Редактор</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <form action="/calc" name="calculate" method="get">
            <td>
                <div class="form-group">
                    <input type="datetime-local" required
                           class="form-control" id="dateS" name="dateS"
                           value="${calc.start?datetime?string("dd.MM.yy HH:mm:ss")!}">
                </div>

            </td>
            <td>
                <div class="form-group">
                    <input type="datetime-local" required
                           class="form-control" id="dateE" name="dateE"
                           value="${calc.end?datetime?string("dd.MM.yy HH:mm:ss")!}">
                </div>

            </td>
            <td>
                ${calc.total!}
            </td>
            <td>
                <button type="submit" class="btn btn-primary">Подсчитать</button>
            </td>
        </form>
    </tr>
    </tbody>
</table>