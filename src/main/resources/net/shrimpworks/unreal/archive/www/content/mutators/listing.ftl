<#if page??>
  <#assign game=page.letter.game>
  <#assign mutators=page.mutators>
</#if>

<#assign ogDescription="Custom gameplay mutators for ${game.game.bigName}">
<#assign ogImage="${staticPath()}/images/games/${game.name}.png">

<#include "../../_header.ftl">
<#include "../../macros.ftl">

	<@heading bg=[ogImage]>
		<span class="crumbs">
			<a href="${relPath(sectionPath + "/index.html")}">Mutators</a>
			/</span> <a href="${relPath(game.path + "/index.html")}">${game.name}</a>
		<span class="crumbs">
			<#if page?? && game.letters?size gt 1>/ ${page.letter.letter}</#if>
			<#if page?? && page.letter.pages?size gt 1>/ pg ${page.number}</#if>
		</span>
	</@heading>

	<@content class="list">

		<#if page??>
			<@letterPages letters=game.letters currentLetter=page.letter.letter pages=page.letter.pages currentPage=page />
		</#if>

		<table class="mutators">
			<thead>
			<tr>
				<th>Mutator</th>
				<th>Author</th>
				<th class="nomobile">Info</th>
				<th class="nomobile"> </th>
			</tr>
			</thead>
			<tbody>
				<#list mutators as m>
				<tr>
					<td nowrap="nowrap"><a href="${relPath(m.path + ".html")}">${m.mutator.name}</a></td>
					<td><@authorLink m.mutator.authorName /></td>
					<td class="nomobile">
						<#if m.mutator.mutators?size gt 0>
							${m.mutator.mutators?size} mutator<#if m.mutator.mutators?size gt 1>s</#if>
							<#if m.mutator.weapons?size gt 0 || m.mutator.vehicles?size gt 0>,</#if>
						</#if>
						<#if m.mutator.weapons?size gt 0>
							${m.mutator.weapons?size} weapon<#if m.mutator.weapons?size gt 1>s</#if>
							<#if m.mutator.vehicles?size gt 0>,</#if>
						</#if>
						<#if m.mutator.vehicles?size gt 0>
							${m.mutator.vehicles?size} vehicle<#if m.mutator.vehicles?size gt 1>s</#if>
						</#if>
					</td>
					<td class="meta nomobile">
						<#if m.mutator.attachments?size gt 0>
							<img src="${staticPath()}/images/icons/image.svg" alt="Has images" height="22"/>
						</#if>
						<@dependencyIcon m.mutator.dependencies/>
					</td>
				</tr>
				</#list>
			</tbody>
		</table>

		<#if page??>
			<@paginator pages=page.letter.pages currentPage=page />
		</#if>

  </@content>

<#include "../../_footer.ftl">