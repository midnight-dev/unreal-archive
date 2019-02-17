<#assign ogDescription="Custom map packs for Unreal, Unreal Tournament, and Unreal Tournament 2004 and their mods">
<#assign ogImage="${staticPath(static)}/images/contents/maps.png">

<#include "../../_header.ftl">
<#include "../../macros.ftl">

	<@heading bg=["${staticPath(static)}/images/contents/mappacks.png"]>
		Map Packs
	</@heading>

	<@content class="biglist">
		<ul>
		<#list games.games as k, v>
			<li style='background-image: url("${staticPath(static)}/images/games/${v.name}.png")'>
				<span class="meta">${v.packs}</span>
				<a href="${v.path}/index.html">${v.name}</a>
			</li>
		</#list>
		</ul>
	</@content>

<#include "../../_footer.ftl">