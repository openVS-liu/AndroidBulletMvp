<?xml version="1.0"?>
<recipe>

	<instantiate from="root/src/app_package/upload/Presenter.${ktOrJavaExt}.ftl"
		to="${escapeXmlAttribute(srcOut)}/${moudle}Presenter.${ktOrJavaExt}" />

	<instantiate from="root/src/app_package/upload/fragment.${ktOrJavaExt}.ftl"
		to="${escapeXmlAttribute(srcOut)}/${moudle}Fragment.${ktOrJavaExt}" />

	<instantiate from="root/res/layout/template.xml.ftl"
		to="${escapeXmlAttribute(resOut)}/layout/fragment_${layout}.xml" />

    <merge from="root/AndroidManifest.xml.ftl"
           to="${escapeXmlAttribute(manifestOut)}/AndroidManifest.xml" />

    
</recipe>