<#macro logoutPage>
    <div>
        <form action="/logout" method="post">
            <input type="submit" value="Sign out"/>
            <input type="hidden" name="_csrf" value="${_csrf.token}" />
        </form>
    </div>
    <#nested>
</#macro>
