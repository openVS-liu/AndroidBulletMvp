<?xml version="1.0"?>
<globals>
    <#assign generateKotlin=(((includeKotlinSupport!false) || (language!'Java')?string == 'Kotlin'))>
    <global id="generateKotlin" type="boolean" value="${generateKotlin?string}" />
    <global id="ktOrJavaExt" type="string" value="${generateKotlin?string('kt','java')}" /> 
    
    <global id="resOut" value="${resDir}" />
    <global id="srcOut" value="${srcDir}/${slashedPackageName(packageName)}" />
    <global id="relativePackage" value="<#if relativePackage?has_content>${relativePackage}<#else>${packageName}</#if>" />
</globals>