package adp_db;

import java.io.FileNotFoundException;
import java.util.Scanner;

import adp_parser.ADPParser;

public class SelectionSuggester {
	public static final double MIN_P = 0.2;
	public static final int MAX_DISPLAY = 20;
	private final ADPDatabase db;

	private final Scanner scanner;

	public SelectionSuggester() throws FileNotFoundException {
		this(new Scanner(System.in), ADPParser.CSV_FILE_NAME_10_TEAM);
	}

	public SelectionSuggester(final Scanner scanner)
			throws FileNotFoundException {
		this(scanner, ADPParser.CSV_FILE_NAME_10_TEAM);
	}

	public SelectionSuggester(final String csvFile)
			throws FileNotFoundException {
		this(new Scanner(System.in), csvFile);
	}

	public SelectionSuggester(final Scanner scanner, final String csvFile)
			throws FileNotFoundException {
		this.scanner = scanner;
		db = new ADPDatabase(csvFile);
	}

	public void close() {
		scanner.close();
	}

	private boolean displaySelection() {
		System.out.println("Choose Next Selection");
		final String line = scanner.nextLine();
		try {
			final int selection = Integer.parseInt(line);
			int count = 0;
			db.sortDatabase(selection);
			for (final ADPElement player : db) {
				// System.err.println(player.p + ":" + player);
				if (player.p > MIN_P) {
					System.out.println("p = " + player.p + ": " + player);
					if (count++ > MAX_DISPLAY)
						break;
				}
			}

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static void main(String... args) throws FileNotFoundException {
		final SelectionSuggester suggester = new SelectionSuggester(
				ADPParser.CSV_FILE_NAME_10_TEAM);
		while (suggester.displaySelection())
			;
		suggester.close();
	}
}
