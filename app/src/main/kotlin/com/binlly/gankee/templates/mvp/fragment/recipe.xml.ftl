<?xml version="1.0"?>
<#import "root://mvp/common/kotlin_macros.ftl" as kt>
<recipe>
    <@kt.addAllKotlinDependencies />

<#if generateLayout>
    <#include "../common/recipe_mvp_fragment.xml.ftl" />
    <open file="${escapeXmlAttribute(resOut)}/layout/${layoutName}.xml" />
</#if>

    <instantiate from="root/src/app_package/MvpFragment.kt.ftl"
                   to="${escapeXmlAttribute(srcOut)}/${moduleName}Fragment.kt" />

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

    <open file="${escapeXmlAttribute(srcOut)}/${moduleName}Fragment.kt" />

</recipe>
