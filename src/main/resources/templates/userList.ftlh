<#import "parts/common.ftlh" as c>
<#import "parts/logoutPart.ftlh" as l>

<@l.logoutPage>
    <@c.page>
        <table>
            <p>Filter by Username</p>
            <form method="post" action="/user/filterByUsername">
                <input type="text" name="filterByUsername">
                <button type="submit">Find</button>
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            </form>

            <p>Filter by Role</p>
            <form method="post" action="/user/filterByRole">
                <select name="filterByRole">
                    <option value="0"></option>
                    <#list roles as role>
                        <option value="${role.id}">${role.name}</option>
                    </#list>
                </select>
                <button type="submit">Find</button>
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            </form>

            <thead>
            <tr>
                <th>Username</th>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Role</th>
                <th>Status</th>
                <th>Created At</th>
            </tr>
            </thead>
            <tbody>
            <#list users as user>
                <tr>
                    <td>${user.username}</td>
                    <td>${user.firstName}</td>
                    <td>${user.lastName}</td>
                    <td>${user.roles.name}</td>
                    <td>${user.active?then('ACTIVE','INACTIVE')}</td>
                    <td>${user.createdAt}</td>
                </tr>
            </#list>
            </tbody>
        </table>
    </@c.page>
</@l.logoutPage>
