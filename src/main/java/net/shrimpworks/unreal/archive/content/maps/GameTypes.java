package net.shrimpworks.unreal.archive.content.maps;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class GameTypes {

	private static final List<GameType> GAME_TYPES = Arrays.asList(
			new GameType("Single Player", "SP", "OSM"),
			new GameType("1 on 1", "DM-1on1", "DM-1v1", "DM-1-on-1"),
			new GameType("Infiltration", "INF", "CTF-INF-", "DM-INF-", "DOM-INF-", "AS-INF-", "EAS-INF-"),
			new GameType("DeathMatch", "DM"),
			new GameType("DarkMatch", "DK"),
			new GameType("BunnyTrack", "CTF-BT", "BT"),
			new GameType("Multi-Team CTF", "CTF4", "CTFM", "MCTF"),
			new GameType("Capture The Flag", "CTF"),
			new GameType("Domination", "DOM"),
			new GameType("Assault", "AS"),
			new GameType("Bombing Run", "BR"),
			new GameType("Onslaught", "ONS"),
			new GameType("Vehicle CTF", "VCTF"),
			new GameType("Monster Hunt", "MH"),
			new GameType("Monster Arena", "MA"),
			new GameType("Team Monster Hunt", "TMH"),
			new GameType("Rocket Arena", "RA-"),
			new GameType("Jailbreak", "JB"),
			new GameType("Tactical Ops", "TO"),
			new GameType("Tactical Ops", "SW"),
			new GameType("Strike Force", "SF"),
			new GameType("UnWheel", "UW"),
			new GameType("Thievery", "TH-"),
			new GameType("Unreal4Ever", "U4E"),
			new GameType("Unreal Fortress", "UNF"),
			new GameType("XMP", "XMP"),
			new GameType("FragBall", "FB"),
			new GameType("Flag Domination", "FD"),
			new GameType("Soccer Tournament", "SCR"),
			new GameType("Killing Floor", "KF"),
			new GameType("AirFight", "AF", "DM-AF-", "CTF-AF-", "DOM-AF-"),
			new GameType("DeathBall", "DB"),
			new GameType("Unreal Racer", "UNR-"),
			new GameType("Air Buccaneers", "ABU"),
			new GameType("Clone Bandits", "CLN"),
			new GameType("Red Orchestra", "RO-"),
			new GameType("Fraghouse Invasion", "FHI"),
			new GameType("SoldatUT", "2DDM", "2DDOM", "2DONS", "2DCTF", "2DBR"),
			new GameType("Dodge Professional Modification", "DPM"),
			new GameType("Scavenger Hunt", "SH"),
			new GameType("Smashdroids", "SD"),
			new GameType("ChaosUT", "KOTH", "DM-CUT", "CTF-CUT", "DOM-CUT"),
			new GameType("Funnel", "FN"),
			new GameType("Survival", "SV"),
			new GameType("Conquest", "CNQ"),
			new GameType("RealCTF", "Real_"),
			new GameType("Unreal Badlands", "BL-", "BLC-")
	);

	public static GameType forMap(String mapName) {
		String lower = mapName.toLowerCase();
		for (GameType gt : GAME_TYPES) {
			for (String p : gt.mapPrefixes) {
				if (lower.startsWith(p.toLowerCase())) return gt;
			}
		}
		return null;
	}

	public static GameType byName(String name) {
		String lower = name.toLowerCase();
		for (GameType gt : GAME_TYPES) {
			if (gt.name.toLowerCase().equals(lower)) return gt;
		}
		return null;
	}

	public static class GameType {

		public final String name;
		public final Collection<String> mapPrefixes;

		public GameType(String name, Collection<String> mapPrefixes) {
			this.name = name;
			this.mapPrefixes = mapPrefixes;
		}

		public GameType(String name, String... mapPrefixes) {
			this(name, Arrays.asList(mapPrefixes));
		}
	}
}
