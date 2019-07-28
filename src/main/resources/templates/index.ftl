<#import "template.ftl" as layout />
<#include "part/security.ftl">
<#setting datetime_format="dd.MM.yy HH:mm:ss">
<@layout.mainLayout>
    <h3>Добро пожаловать ${name}</h3>
</@layout.mainLayout>