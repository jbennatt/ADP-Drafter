package adp_db;

public class ADPNormalComparator extends ADPComparator {
	public static final double minP = 0.75;

	ADPNormalComparator(final int selection) {
		super(selection);
	}

	@Override
	public int compare(ADPElement e1, ADPElement e2) {
		final double p1 = e1.getNormalP(selection);
		final double p2 = e2.getNormalP(selection);

		if (p1 > p2) {
			if (p1 >= minP
					|| Math.abs(e1.avgRank - selection) < Math.abs(e2.avgRank
							- selection))
				return -1;// e1 goes before e2 because the average rank is
							// closer to the selection OR the probability is
							// greater and above the threshold
			else
				return 1;// e1 goes AFTER e2
		} else {
			if (p2 >= minP
					|| Math.abs(e2.avgRank - selection) < Math.abs(e1.avgRank
							- selection))
				return 1; // e1 comes AFTER e2 because e2 has a greater
							// probability (within reason) OR e2 has a higher
							// rank (higher change of getting that person)
			else
				return -1;// opposite of above
		}
	}

}
