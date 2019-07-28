<#import "template.ftl" as layout />
<#import "part/macro/expenses.ftl" as exp />

<#setting datetime_format="dd.MM.yy HH:mm:ss">
<@layout.mainLayout>
    <#include "part/calculate.ftl">
    <@exp.expensesMacro "/expenses", "Расходы"/>
</@layout.mainLayout>