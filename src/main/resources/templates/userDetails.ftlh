<#import "parts/common.ftlh" as c>
<#import "parts/logoutPart.ftlh" as l>

<@l.logoutPage>
<@c.page>
    <form method="post">
        <div>
            <select name="active">
                <option value="true" <#if active == true>selected</#if>>ACTIVE</option>
                <option value="false" <#if active == false>selected</#if>>INACTIVE</option>
            </select>
        </div>
        <div>
            <button type="submit">Edit</button>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
    </form>
</@c.page>
</@l.logoutPage>