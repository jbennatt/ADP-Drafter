package drafter;

import adp_db.ADPDatabase;
import adp_db.ADPElement;

public class DraftingPool extends Draft {

	private final Draft[] teamDraft;

	public DraftingPool(ADPDatabase db, int numTeams, int size) {
		super(db, numTeams, size);
		teamDraft = new Draft[numTeams];

		for (int i = 0; i < numTeams; ++i)
			teamDraft[i] = new Draft(db, numTeams, size);
	}

	public Draft getDraft(final int i) {
		return teamDraft[i];
	}

	@Override
	public void draft(final ADPElement player) {
		super.draft(player);

		for (final Draft draft : teamDraft)
			draft.draft(player);
	}

}
