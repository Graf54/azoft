<#import "template.ftl" as layout />

<#setting datetime_format="dd.MM.yy HH:mm:ss">
<@layout.mainLayout>
    <#include "part/expensesForAdmin.ftl">
</@layout.mainLayout>