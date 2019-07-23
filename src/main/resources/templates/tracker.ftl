<#import "template.ftl" as layout />

<#setting datetime_format="dd.MM.yy HH:mm:ss">
<@layout.mainLayout>
    <#include "part/calculate.ftl">
    <#include "part/trackerExpense.ftl">
</@layout.mainLayout>