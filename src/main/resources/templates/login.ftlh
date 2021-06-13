<#import "parts/common.ftlh" as c>

<@c.page>
    <form action="/login" method="post">
        <div>
            <input type="text" name="username"placeholder="Username"/>
        </div>
        <div>
            <input type="password" name="password" placeholder="Password"/>
        </div>
        <div>
            <button type="submit">Sign in</button>
            <input type="hidden" name="_csrf" value="${_csrf.token}" />
        </div>
    </form>
</@c.page>