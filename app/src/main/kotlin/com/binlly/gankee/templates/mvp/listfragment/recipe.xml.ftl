<?xml version="1.0"?>
<#import "root://mvp/common/kotlin_macros.ftl" as kt>
<recipe>
    <#include "../common/recipe_manifest.xml.ftl" />
    <@kt.addAllKotlinDependencies />

<#if generateLayout>
    <#include "../common/recipe_mvp_list_fragment.xml.ftl" />
    <open file="${escapeXmlAttribute(resOut)}/layout/${layoutName}.xml" />
</#if>

    <instantiate from="root/src/app_package/MvpFragmentList.kt.ftl"
                   to="${escapeXmlAttribute(srcOut)}/${moduleName}Activity.kt" />

    <instantiate from="root/src/app_package/MvpContract.kt.ftl"
                   to="${escapeXmlAttribute(srcOut)}/${moduleName}Contract.kt" />

    <instantiate from="root/src/app_package/MvpModel.kt.ftl"
                   to="${escapeXmlAttribute(srcOut)}/${moduleName}Model.kt" />

    <instantiate from="root/src/app_package/MvpPresenter.kt.ftl"
                   to="${escapeXmlAttribute(srcOut)}/${moduleName}Presenter.kt" />

    <instantiate from="root/src/app_package/MvpAdapter.kt.ftl"
                   to="${escapeXmlAttribute(srcOut)}/adapter/${moduleName}Adapter.kt" />

    <instantiate from="root/src/app_package/Type1Delegate.kt.ftl"
                   to="${escapeXmlAttribute(srcOut)}/adapter/Type1Delegate.kt" />

    <instantiate from="root/src/app_package/Type2Delegate.kt.ftl"
                   to="${escapeXmlAttribute(srcOut)}/adapter/Type2Delegate.kt" />

    <instantiate from="root/src/app_package/AllModels.kt.ftl"
                   to="${escapeXmlAttribute(srcOut)}/di/AllModels.kt" />

    <instantiate from="root/src/app_package/PresenterModels.kt.ftl"
                   to="${escapeXmlAttribute(srcOut)}/di/PresenterModels.kt" />

    <open file="${escapeXmlAttribute(srcOut)}/${moduleName}Activity.kt" />

</recipe>
