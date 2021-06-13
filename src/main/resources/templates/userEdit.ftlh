<#import "parts/common.ftlh" as c>
<#import "parts/logoutPart.ftlh" as l>

<@l.logoutPage>
<@c.page>
    <form method="post">
        <div>
            <input type="text" name="username" value="${username}" placeholder="Username"/>
            <div>
                <#if usernameError??>
                    <#list usernameError as value>
                        ${value} </br>
                    </#list>
                </#if>
                ${usernameEx!}
            </div>
        </div>
        <div>
            <input type="password" name="password" placeholder="Password"/>
            <div>
                <#if passwordError??>
                    <#list passwordError as value>
                        ${value} </br>
                    </#list>
                </#if>
            </div>
        </div>
        <div>
            <input type="text" name="firstName" value="${firstName}" placeholder="First name"/>
            <div>
                <#if firstNameError??>
                    <#list firstNameError as value>
                        ${value} </br>
                    </#list>
                </#if>
            </div>
        </div>
        <div>
            <input type="text" name="lastName" value="${lastName}" placeholder="Last name"/>
            <div>
                <#if lastNameError??>
                    <#list lastNameError as value>
                        ${value} </br>
                    </#list>
                </#if>
            </div>
        </div>

        <select name="userRoles">
            <#list userRoles as role>
                <option value="${role.name}">${role.name}</option>
            </#list>
        </select>
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