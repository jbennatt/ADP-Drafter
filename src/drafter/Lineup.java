package drafter;

import java.util.ArrayList;

import adp_db.ADPElement;

public class Lineup {
	public final int size;
	private final ArrayList<ADPElement> players = new ArrayList<ADPElement>();

	Lineup(final int size) {
		this.size = size;
	}

	void addPlayer(final ADPElement player) {
		players.add(player);
	}
}
