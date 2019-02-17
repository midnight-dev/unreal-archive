<#include "../_header.ftl">
<#include "../macros.ftl">

	<#assign headerbg>${staticPath(static)}/images/contents/documents.png</#assign>

	<#if document.document.titleImage??>
		<#assign headerbg>${document.document.titleImage}</#assign>
	</#if>

	<@heading bg=[headerbg]>
		<a href="${siteRoot}/index.html">Articles & Guides</a>
		<#list groupPath as p>
			/ <a href="${relUrl(siteRoot, p.path)}/index.html">${p.name}</a>
		</#list>
		/ ${document.document.title}
	</@heading>

	<@content class="document">
		<div class="meta">
			<div class="label-value">
				<label>Author</label><span>${document.document.author}</span>
			</div>
			<div class="label-value">
				<label>Created Date</label><span>${document.document.createdDate}</span>
			</div>
			<div class="label-value">
				<label>Last Updated</label><span>${document.document.updatedDate}</span>
			</div>
			<div class="label-value">
				<label>Summary</label><span>${document.document.description}</span>
			</div>
		</div>
		<div class="content readable">
			${page}
		</div>
	</@content>

<#include "../_footer.ftl">