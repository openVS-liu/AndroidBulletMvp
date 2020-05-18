<?xml version="1.0"?>
<recipe>

	<instantiate from="root/src/app_package/upload/QueueDownLoadPresenter.${ktOrJavaExt}.ftl"
		to="${escapeXmlAttribute(srcOut)}/${moudle}Presenter.${ktOrJavaExt}" />

	<instantiate from="root/src/app_package/upload/QueueDownLoadActivity.${ktOrJavaExt}.ftl"
		to="${escapeXmlAttribute(srcOut)}/${moudle}Activity.${ktOrJavaExt}" />

	<instantiate from="root/res/layout/template.xml.ftl"
		to="${escapeXmlAttribute(resOut)}/layout/activity_${layout}.xml" />

    <merge from="root/AndroidManifest.xml.ftl"
           to="${escapeXmlAttribute(manifestOut)}/AndroidManifest.xml" />

    
</recipe>