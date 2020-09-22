package adp_db;

import java.util.Comparator;

public abstract class ADPComparator implements Comparator<ADPElement> {

	protected double selection;

	protected ADPComparator(final int selection) {
		setSelection(selection);
	}

	void setSelection(final int selection) {
		this.selection = selection;
	}
}
