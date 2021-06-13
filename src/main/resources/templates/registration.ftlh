<#import "parts/common.ftlh" as c>
<#import "parts/logoutPart.ftlh" as l>

<@l.logoutPage>
    <@c.page>
        <form action="/user/new" method="post">
            <div>
                <input type="text" name="username" placeholder="Username"/>
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
                <input type="text" name="firstName" placeholder="First name"/>
                <div>
                    <#if firstNameError??>
                        <#list firstNameError as value>
                            ${value} </br>
                        </#list>
                    </#if>
                </div>
            </div>
            <div>
                <input type="text" name="lastName" placeholder="Last name"/>
                <div>
                    <#if lastNameError??>
                        <#list lastNameError as value>
                            ${value} </br>
                        </#list>
                    </#if>
                </div>
            </div>

            <select name="userRoles">
                <#list roles as role>
                    <option value="${role.name}">${role.name}</option>
                </#list>
            </select>
            <div>
                <select name="active">
                    <option value="true">ACTIVE</option>
                    <option value="false">INACTIVE</option>
                </select>
            </div>
            <div>
                <button type="submit">Create</button>
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        </form>
    </@c.page>

</@l.logoutPage>