package adp_tiers;

import adp_db.ADPElement;

public class Tier {
	public static final int POS_NA = -1;
	// public static final int RB = 0;
	// public static final int WR = 1;
	// public static final int TE = 2;
	// public static final int QB = 3;
	// public static final int K = 4;
	// public static final int DST = 5;

	public final int pos;
	public final int low, high;
	public final int tierIndex;

	public Tier(final int pos, final int tierIndex, final int low,
			final int high) {
		this.pos = pos;
		this.tierIndex = tierIndex;
		this.low = low;
		this.high = high;
	}

	public Tier(final String pos, final int tierIndex, final int low,
			final int high) {
		this(ADPElement.getPOS(pos), tierIndex, low, high);
	}

	public String getPOS() {
		return ADPElement.getPOS(pos);
	}
}
