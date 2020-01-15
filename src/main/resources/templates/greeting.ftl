<#import "parts/common.ftl" as c>
<#import "login.ftl" as l>
<#include "parts/security.ftl">
<@c.page>
<h5>Hello, user!</h5>
<#if name !="Unknown">
<div>You authorized</div>
<#else>
<div>Authorize first</div>
</#if>
</@c.page>