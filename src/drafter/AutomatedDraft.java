package drafter;

import adp_db.ADPDatabase;
import adp_db.ADPElement;

public class AutomatedDraft extends Draft {
	private final Drafter[] teams;
	private final DraftingPool mainDraft;

	public AutomatedDraft(ADPDatabase db, int size, Drafter... drafters) {
		super(db, drafters.length, size);

		teams = drafters;
		mainDraft = new DraftingPool(db, drafters.length, size);

		for (int i = 0; i < drafters.length; ++i) {
			teams[i].setDraft(mainDraft.getDraft(i));
		}
	}

	@Override
	public void draft(final ADPElement player) {
		super.draft(player);
		mainDraft.draft(player);
	}

	public void runDraft(final int nRounds) {
		for (int i = 0; i < nRounds * teams.length; ++i) {
			final ADPElement player = teams[super.index].choosePlayer();

			this.draft(player);
		}
	}

	public Lineup getLineup(final int teamIndex) {
		return teams[teamIndex].getLineup();
	}
}
