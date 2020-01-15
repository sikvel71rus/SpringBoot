<#import "parts/common.ftl" as c>

<@c.page>
User editor
<div class = "form-row">
    <form action="/user" method="post">
        <input type="text" name="username" class="form-control" value="${user.username}">
        <#list roles as role>
        <div class="custom-control custom-checkbox">
<!--            <input type="checkbox" class="custom-control-input" id="${role}" ${user.roles?seq_contains(role)?string("checked", "")}>-->
<!--            <label class="custom-control-label" for="${role}">${role}</label>-->
            <label><input type="checkbox" name="${role}" ${user.roles?seq_contains(role)?string("checked", "")}>${role}</label>
        </div>
    </#list>
    <input type="hidden" value="${user.id}" name="userId">
    <input type="hidden" value="${_csrf.token}" name="_csrf">
    <button class="btn btn-primary" type="submit">Save</button>
    </form>
</div>
</@c.page>