<?xml version="1.0"?>
<#import "root://mvp/common/kotlin_macros.ftl" as kt>
<recipe>
    <#include "../common/recipe_manifest.xml.ftl" />
    <@kt.addAllKotlinDependencies />

<#if generateLayout>
    <#include "../common/recipe_mvp_activity.xml.ftl" />
    <open file="${escapeXmlAttribute(resOut)}/layout/${layoutName}.xml" />
</#if>

     <merge from="root/AndroidManifest.xml.ftl"
                   to="${escapeXmlAttribute(manifestOut)}/AndroidManifest.xml" />

    <instantiate from="root/src/app_package/MvpActivity.kt.ftl"
                   to="${escapeXmlAttribute(srcOut)}/${moduleName}Activity.kt" />

    <instantiate from="root/src/app_package/MvpContract.kt.ftl"
                   to="${escapeXmlAttribute(srcOut)}/${moduleName}Contract.kt" />

    <instantiate from="root/src/app_package/MvpModel.kt.ftl"
                   to="${escapeXmlAttribute(srcOut)}/${moduleName}Model.kt" />

    <instantiate from="root/src/app_package/MvpPresenter.kt.ftl"
                   to="${escapeXmlAttribute(srcOut)}/${moduleName}Presenter.kt" />

    <instantiate from="root/src/app_package/AllModels.kt.ftl"
                   to="${escapeXmlAttribute(srcOut)}/di/AllModels.kt" />

    <instantiate from="root/src/app_package/PresenterModels.kt.ftl"
                   to="${escapeXmlAttribute(srcOut)}/di/PresenterModels.kt" />

    <open file="${escapeXmlAttribute(srcOut)}/${moduleName}Activity.kt" />

</recipe>
