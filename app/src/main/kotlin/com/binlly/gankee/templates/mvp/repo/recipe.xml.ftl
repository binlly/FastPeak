<?xml version="1.0"?>
<#import "root://mvp/common/kotlin_macros.ftl" as kt>
<recipe>

    <instantiate from="root/src/app_package/Repo.kt.ftl"
                   to="${escapeXmlAttribute(srcOut)}/${apiService}Repo.kt" />

    <instantiate from="root/src/app_package/Service.kt.ftl"
                   to="${escapeXmlAttribute(srcOut)}/service/${apiService}Service.kt" />

    <open file="${escapeXmlAttribute(srcOut)}/service/${apiService}Service.kt" />

</recipe>
