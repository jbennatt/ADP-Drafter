package adp_tiers;

import java.io.FileNotFoundException;
import java.util.Scanner;

import adp_db.ADPElement;

/**
 * This creates a tier file which holds the adp's (low and high) at which each
 * tier changes for every position.
 * 
 * @author jbennatt
 *
 */
public class CreateTiers {
	public static final String TIER_FILE = "tiers.txt";

	public static void main(String... args) throws FileNotFoundException {
		final Scanner scanner = new Scanner(System.in);
		final TierData data = new TierData();
		while (prompting(scanner, data))
			;

		data.writeToFile(TIER_FILE);
	}

	private static boolean prompting(final Scanner scanner, final TierData data) {
		System.out.println("add another tier? (y/n)");

		String ans = scanner.nextLine();

		// we're done here
		if (!ans.toLowerCase().equals("y"))
			return false;

		while (promptingPosition(scanner, data))
			;

		return true;
	}

	private static boolean promptingPosition(final Scanner scanner,
			final TierData data) {
		System.out.println("What Position? ");
		final int pos = ADPElement.getPOS(scanner.nextLine().trim());

		// reprompt for position
		if (pos == Tier.POS_NA)
			return true;

		while (promptingLow(scanner, data, pos))
			;

		return false;
	}

	private static boolean promptingLow(final Scanner scanner,
			final TierData data, final int pos) {
		System.out.println("low ADP: ");
		try {
			final int low = Integer.parseInt(scanner.nextLine().trim());

			while (promptingHigh(scanner, data, pos, low))
				;

			return false;
		} catch (NumberFormatException nfe) {
			return true;
		}
	}

	private static boolean promptingHigh(final Scanner scanner,
			final TierData data, final int pos, final int low) {
		System.out.println("high ADP: ");
		try {
			final int high = Integer.parseInt(scanner.nextLine());
			data.addTier(pos, low, high);
			return false;
		} catch (NumberFormatException nfe) {
			return true;
		}
	}
}
