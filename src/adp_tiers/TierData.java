package adp_tiers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import adp_db.ADPElement;

public class TierData {
	public final ArrayList<Tier> RBs = new ArrayList<Tier>();
	public final ArrayList<Tier> WRs = new ArrayList<Tier>();
	public final ArrayList<Tier> QBs = new ArrayList<Tier>();
	public final ArrayList<Tier> TEs = new ArrayList<Tier>();
	public final ArrayList<Tier> Ks = new ArrayList<Tier>();
	public final ArrayList<Tier> DSTs = new ArrayList<Tier>();

	public TierData() {

	}

	public TierData(final String file) throws FileNotFoundException {
		this();
		final Scanner scanner = new Scanner(new File(file));

		// skip first line (announcing RB Tiers)
		scanner.nextLine();

		getRBTiers(scanner);
		getWRTiers(scanner);
		getQBTiers(scanner);
		getTETiers(scanner);
		getKTiers(scanner);
		getDSTTiers(scanner);

		scanner.close();
	}

	private void getRBTiers(Scanner scanner) {
		String line = scanner.nextLine();

		while (scanner.hasNextLine() && !line.matches("[A-Z]+ Tiers")) {
			final String[] tokens = line.split(" ");
			// the third token is low
			final int low = Integer.parseInt(tokens[2].trim());
			// the fifth is high
			final int high = Integer.parseInt(tokens[4].trim());

			addRBTier(low, high);
			line = scanner.nextLine();
		}
	}

	private void getWRTiers(Scanner scanner) {
		String line = scanner.nextLine();

		while (scanner.hasNextLine() && !line.matches("[A-Z]+ Tiers")) {
			final String[] tokens = line.split(" ");
			// the third token is low
			final int low = Integer.parseInt(tokens[2].trim());
			// the fifth is high
			final int high = Integer.parseInt(tokens[4].trim());

			addWRTier(low, high);
			line = scanner.nextLine();
		}
	}

	private void getQBTiers(Scanner scanner) {
		String line = scanner.nextLine();

		while (scanner.hasNextLine() && !line.matches("[A-Z]+ Tiers")) {
			final String[] tokens = line.split(" ");
			// the third token is low
			final int low = Integer.parseInt(tokens[2].trim());
			// the fifth is high
			final int high = Integer.parseInt(tokens[4].trim());

			addQBTier(low, high);
			line = scanner.nextLine();
		}
	}

	private void getTETiers(Scanner scanner) {
		String line = scanner.nextLine();

		while (scanner.hasNextLine() && !line.matches("[A-Z]+ Tiers")) {
			final String[] tokens = line.split(" ");
			// the third token is low
			final int low = Integer.parseInt(tokens[2].trim());
			// the fifth is high
			final int high = Integer.parseInt(tokens[4].trim());

			addTETier(low, high);
			line = scanner.nextLine();
		}
	}

	private void getKTiers(Scanner scanner) {
		String line = scanner.nextLine();

		while (scanner.hasNextLine() && !line.matches("[A-Z]+ Tiers")) {
			final String[] tokens = line.split(" ");
			// the third token is low
			final int low = Integer.parseInt(tokens[2].trim());
			// the fifth is high
			final int high = Integer.parseInt(tokens[4].trim());

			addKTier(low, high);
			line = scanner.nextLine();
		}
	}

	private void getDSTTiers(Scanner scanner) {
		if (scanner.hasNextLine()) {
			String line = scanner.nextLine();

			while (!line.matches("[A-Z]+ Tiers")) {
				final String[] tokens = line.split(" ");
				// the third token is low
				final int low = Integer.parseInt(tokens[2].trim());
				// the fifth is high
				final int high = Integer.parseInt(tokens[4].trim());

				addDSTTier(low, high);
				if (scanner.hasNextLine())
					line = scanner.nextLine();
				else
					return;
			}
		}
	}

	public void addRBTier(final int low, final int high) {
		final int tierIndex = RBs.size() + 1;
		RBs.add(new Tier(ADPElement.RB, tierIndex, low, high));
	}

	public void addWRTier(final int low, final int high) {
		final int tierIndex = WRs.size() + 1;
		WRs.add(new Tier(ADPElement.WR, tierIndex, low, high));
	}

	public void addQBTier(final int low, final int high) {
		final int tierIndex = QBs.size() + 1;
		QBs.add(new Tier(ADPElement.QB, tierIndex, low, high));
	}

	public void addTETier(final int low, final int high) {
		final int tierIndex = TEs.size() + 1;
		TEs.add(new Tier(ADPElement.TE, tierIndex, low, high));
	}

	public void addKTier(final int low, final int high) {
		final int tierIndex = Ks.size() + 1;
		Ks.add(new Tier(ADPElement.K, tierIndex, low, high));
	}

	public void addDSTTier(final int low, final int high) {
		final int tierIndex = DSTs.size() + 1;
		DSTs.add(new Tier(ADPElement.DEF, tierIndex, low, high));
	}

	public void addTier(final int type, final int low, final int high) {
		switch (type) {
		case ADPElement.RB:
			addRBTier(low, high);
			break;
		case ADPElement.WR:
			addWRTier(low, high);
			break;
		case ADPElement.QB:
			addQBTier(low, high);
			break;
		case ADPElement.TE:
			addTETier(low, high);
			break;
		case ADPElement.K:
			addKTier(low, high);
			break;
		case ADPElement.DEF:
			addDSTTier(low, high);
			break;
		}
	}

	public void writeToFile(final String file) throws FileNotFoundException {
		final PrintWriter pw = new PrintWriter(new File(file));

		pw.println("RB Tiers");
		printTiers(RBs, pw);

		pw.println("WR Tiers");
		printTiers(WRs, pw);

		pw.println("QB Tiers");
		printTiers(QBs, pw);

		pw.println("TE Tiers");
		printTiers(TEs, pw);

		pw.println("K Tiers");
		printTiers(Ks, pw);

		pw.println("DST Tiers");
		printTiers(DSTs, pw);

		pw.close();
	}

	private static void printTiers(final ArrayList<Tier> tiers,
			final PrintWriter pw) {
		for (final Tier tier : tiers) {
			pw.println("Tier " + tier.tierIndex + ": " + tier.low + " to "
					+ tier.high);
		}
	}

	public static final String testOutput = "test-tier-data.txt";

	public static void main(String... args) throws FileNotFoundException {
		final TierData data = new TierData(CreateTiers.TIER_FILE);

		data.writeToFile(testOutput);
	}
}
