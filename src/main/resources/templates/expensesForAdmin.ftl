<#import "template.ftl" as layout />
<#import "part/macro/expenses.ftl" as exp />

<#setting datetime_format="dd.MM.yy HH:mm:ss">
<@layout.mainLayout>
<#--    <#include "part/admin/expensesForAdmin.ftl">-->
    <#assign messH3 = "Расходы">
    <#if userName??>
        <#assign messH3 = "Расходы пользователя ${userName}">
    </#if>
    <@exp.expensesMacro "/expenses/admin", messH3/>
</@layout.mainLayout>