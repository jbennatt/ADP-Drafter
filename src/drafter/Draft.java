package drafter;

import java.io.File;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import adp_db.ADPDatabase;
import adp_db.ADPElement;
//import adp_db.ADPTiers;
import adp_db.gui.PlayerSelectChooserFrame;
import adp_db.gui.TeamOptionsDisplay;
import adp_db.gui.TierColoring;
import adp_parser.ADPParser;
//import adp_tiers.CreateTiers;
//import adp_tiers.TierData;

public class Draft implements Iterable<ADPElement> {
	protected final Lineup[] teams;
	protected int round = 1;
	protected int index = 0;
	private final ADPDatabase undrafted;
	private final ArrayList<ADPElement> list = new ArrayList<ADPElement>();

	public Draft(ADPDatabase db, final int numTeams, final int size) {
		this.undrafted = db;
		teams = new Lineup[numTeams];

		for (int i = 0; i < numTeams; ++i)
			teams[i] = new Lineup(size);
	}

	public int getTeamNthSelection(final int teamIndex, final int nthSelect) {
		final int DELTA_INCREASE = 1 + 2 * (teams.length - teamIndex - 1);
		final int DELTA_DECREASE = 1 + 2 * teamIndex;

		final int d0 = teamIndex + 1;

		boolean addIncrease = true;
		int ans = d0;

		for (int pick = 2; pick <= nthSelect; ++pick) {
			final int add = addIncrease ? DELTA_INCREASE : DELTA_DECREASE;
			// System.err.println("adding "
			// + (addIncrease ? "increase = " : "decrease = ") + add);
			ans += add;
			addIncrease = !addIncrease;
		}

		return ans;
	}

	public int getRound() {
		return round;
	}

	/**
	 * This is used by the selector frame to query the draft and display possible
	 * matches.
	 * 
	 * @param query
	 * @return
	 */
	public boolean queryDraft(final String query) {
		undrafted.findElements(query, list);
		return list.size() > 0;
	}

	public int querySize() {
		return list.size();
	}

	public ADPElement getQuery(final int i) {
		return list.get(i);
	}

	public String promptDraft() {
		// System.out
		// .println("Pick #" + currentPick() + ", Round " + round + ": ");
		// return scanner.nextLine();
		return "Round " + round + ", Pick #" + currentPick();
	}

	public void draft(final String listIndex) {
		final ADPElement player = getSelectedPlayer(listIndex);

		this.draft(player);
	}

	public void draft(final ADPElement player) {
		if (player != null)
			undrafted.removePlayer(player);

		incrementIndex();
	}

	public ADPElement getSelectedPlayer(final String listIndex) {
		final int choice = Integer.parseInt(listIndex);
		if (choice >= 0)
			return list.get(choice);

		// else
		return null;
	}

	private void incrementIndex() {
		if (increasing()) {
			index++;
			if (index == teams.length) {
				// reached the end of this round
				index = teams.length - 1;
				round++;
			}
		} else {
			--index;
			if (index == -1) {
				index = 0;
				round++;
			}
		}
	}

	/**
	 * Says whether the next index should increase or decrease
	 * 
	 * @return
	 */
	public boolean increasing() {
		return round % 2 != 0;
	}

	private static void writeADP(final File htmlTeam, final String CSV_FILE) throws FileNotFoundException {
		final File csvFile = new File(CSV_FILE);
		final PrintWriter pw = new PrintWriter(csvFile);
		try {
			ADPParser.parseADP(pw, htmlTeam);
			System.out.println("Wrote to " + csvFile);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			pw.close();
		}
	}

	private static void writeADP(final String htmlTeam, final String CSV_FILE) throws FileNotFoundException {
		final File csvFile = new File(CSV_FILE);
		final PrintWriter pw = new PrintWriter(csvFile);
		try {
			ADPParser.parseADP(pw, htmlTeam);
			System.out.println("Wrote to " + csvFile);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			pw.close();
		}
	}

	public static final int ROUNDS = 15;
	public static final int POS = 7;
	public static final int NUM_TEAMS = 12;
	public static final int NUM_DISPLAYS = 7;
	// public static String ADP_FILE = null;
	// public static String ADP_HTML = null;
	// public static String ADP_HTML_TEXT = null;
	// public static String ADP_JSON = "adp_data/adp_12_noPPR.json";
	public static String ADP_JSON = "adp_data/2020_12_PPR.json";
//	public static String ADP_JSON = "adp_data/adp_10_halfPPR.json";
	// public static String ADP_JSON = "adp_data/adp_10_nonPPR.json";
	// public static String ADP_JSON = "adp_data/adp_8_nonPPR.json";

	public static String ADP_JSON_URL = "https://fantasyfootballcalculator.com/api/v1/adp/standard?teams=12&year=2018";
	public static String ADP_JSON_12 = "json_12.html";

	public static void main(String... args) throws IOException {
		// try {
		// UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		// } catch (Exception e) {
		// }

		int pos = POS;
		int numTeams = NUM_TEAMS;
		int rounds = ROUNDS;
		int numDisplays = NUM_DISPLAYS;
		String json = ADP_JSON;

		if (args.length > 0) {
			// parse options
			int i = 0;
			while (i < args.length) {
				try {
					// there are four options
					switch (args[i]) {
					// Your draft position
					case "-p":
					case "-P":
						pos = Integer.parseInt(args[++i]);
						break;
					// number of teams
					case "-n":
					case "-N":
						numTeams = Integer.parseInt(args[++i]);
						break;
					// Number of rounds in draft
					case "-r":
					case "-R":
						rounds = Integer.parseInt(args[++i]);
						break;
					// number of forward rounds to display
					case "-d":
					case "-D":
						numDisplays = Integer.parseInt(args[++i]);
						break;
					case "-f":
					case "-F":
						// ADP_HTML_TEXT = args[++i];
						json = args[++i];
						break;
					case "-h":
					case "-H":
					case "--help":
					case "--Help":
						printUsage();
						System.exit(0);
					default:
						System.out.println("Exiting, bad option: " + args[i]);
						printUsage();
						System.exit(1);
					}
				} catch (NumberFormatException nfe) {
					System.out.println(args[i] + " isn't an integer, exiting now");
					printUsage();
					System.exit(1);
				}
				++i;
			}
		}

		final ADPDatabase db = ADPDatabase.getDBFromJSONFile(json);
		System.out.println("Reading from file: " + json);

		final Draft draft = new Draft(db, numTeams, rounds);

		// tiers.printTiers();

		final TierColoring tierColoring = new TierColoring(null);
		final TeamOptionsDisplay teamDisplay = new TeamOptionsDisplay(pos, draft, numDisplays, tierColoring);

		final PlayerSelectChooserFrame chooserFrame = new PlayerSelectChooserFrame(draft, teamDisplay);

		chooserFrame.display();
	}

	private static void printUsage() {
		System.out.println("java -jar drafter.jar -p position -n numTeams -r numRounds -d numDisplays -f file");
		System.out.println("-p position: YOUR draft position, e.g. 3 means you pick 3rd, DEFAULT: " + POS);
		System.out.println("-n numTeams: Number of teams in the draft, e.g. 8, 10, 12, etc.  DEFAULT: " + NUM_TEAMS);
		System.out.println("-r numRounds: Number of rounds in the draft, e.g. 15, 16, etc. DEFAULT: " + ROUNDS);
		System.out
				.println("-d numDisplays: Number of rounds to display in the draft helper.  DEFAULT: " + NUM_DISPLAYS);
		System.out.println("-f file: path to JSON file used to create ADP. DEFAULT: " + ADP_JSON);
	}

	public int currentPick() {
		return (round - 1) * teams.length + (increasing() ? (index + 1) : teams.length - index);
	}

	public int numTeams() {
		return teams.length;
	}

	public void sortDB(final int selection) {
		undrafted.sortDatabase(selection);
	}

	@Override
	public Iterator<ADPElement> iterator() {
		return undrafted.iterator();
	}
}
