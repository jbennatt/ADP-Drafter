package drafter;

import adp_db.ADPElement;

public abstract class Drafter {
	protected Draft draft;

	public void setDraft(final Draft draft) {
		this.draft = draft;
	}

	public abstract ADPElement choosePlayer();

	public abstract Lineup getLineup();
}
