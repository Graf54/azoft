<#import "part/common.ftl" as c>
<#import "part/login.ftl" as l>

<@c.page>
    <div class="mb-1">Add new user</div>
    ${message!}
    <@l.login "/registration" true />
</@c.page>
