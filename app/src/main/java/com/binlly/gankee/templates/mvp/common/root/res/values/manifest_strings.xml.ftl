<resources>
<#if !isNewProject && (generateActivityTitle!true)>
    <string name="title_${activityToLayout(moduleName)}">${escapeXmlString(activityTitle)
    }</string>
</#if>
</resources>
