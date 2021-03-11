<#if page??>
  <#assign authors=page.authors>
</#if>

<#assign ogImage="${staticPath()}/images/games/All.png">

<#include "../../_header.ftl">
<#include "../../macros.ftl">

	<@heading bg=[ogImage]>
		<a href="${relPath(sectionPath + "/index.html")}">Authors</a>
		<#if page?? && letters?size gt 1>/ ${page.letter.letter}</#if>
		<#if page?? && page.letter.pages?size gt 1>/ pg ${page.number}</#if>
	</@heading>

	<@content class="biglist bigger">

		<#if page??>
			<@letterPages letters=letters currentLetter=page.letter.letter pages=page.letter.pages currentPage=page />
		</#if>

		<div class="authors">
			<ul>
				<#list authors as a>
					<#list a.contents as content>
            <#assign bg="">
            <#if content.leadImage?has_content>
                <#assign bg=urlEncode(content.leadImage)>
								<#break>
            </#if>
					</#list>

					<li style='background-image: url("${bg}")'>
						<span class="meta">${a.contents?size}</span>
						<a href="${relPath(a.path + "/index.html")}">${a.author}</a>
					</li>
				</#list>
			</ul>
		</div>

		<#if page??>
			<@paginator pages=page.letter.pages currentPage=page />
		</#if>

  </@content>

<#include "../../_footer.ftl">