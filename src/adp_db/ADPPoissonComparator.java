package adp_db;

public class ADPPoissonComparator extends ADPComparator {
	ADPPoissonComparator(final int selection) {
		super(selection);
	}

	@Override
	public int compare(ADPElement e1, ADPElement e2) {
		final double p1 = e1.getPoissonP(selection);
		final double p2 = e2.getPoissonP(selection);

		return p1 < p2 ? -1 : 1;
	}
}
