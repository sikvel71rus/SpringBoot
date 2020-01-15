<#import "parts/common.ftl" as c>

<@c.page>
<table class="table">
    <thead class="thead-dark">
    <tr>
        <th>Name</th>
        <th>Role</th>
        <th>Edit user role</th>
    </tr>
    </thead>
    <tbody>
    <#list users as user>
    <tr>
        <td>${user.username}</td>
        <td><#list user.roles as role>${role}<#sep>, </#list></td>
<!--        <td><a href="/user/${user.id}">edit</a></td>-->
        <td>
            <a href="/user/${user.id}" class="btn btn-primary ml-2">Edit</button>
        </td>
    </tr>
    </#list>
    </tbody>
</table>
</@c.page>
