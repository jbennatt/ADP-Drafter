package adp_db;

import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Scanner;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ADPDatabase implements Iterable<ADPElement> {
	public static final String JSON_PLAYERS = "players";
	public static final String JSON_NAME = "name";
	public static final String JSON_POS = "position";
	public static final String JSON_AVGRANK = "adp";
	public static final String JSON_STD = "stdev";

	public static final String CSV_FILE = "adp.txt";

	private final ADPComparator adpComp;// = new ADPNormalComparator(1);

	private final ArrayList<ADPElement> db = new ArrayList<ADPElement>(163);

	public ADPDatabase() {
		this(new ADPPoissonComparator(1));
	}

	public ADPDatabase(ADPComparator adpComp) {
		this.adpComp = adpComp;
	}

	public ADPDatabase(final String csvFileName) throws FileNotFoundException {
		this(csvFileName, new ADPPoissonComparator(1));

		// this(csvFileName, new ADPNormalComparator(1));
	}

	public ADPDatabase(final String csvFileName, final ADPComparator adpComp) throws FileNotFoundException {
		this.adpComp = adpComp;
		if (csvFileName != null) {
			final Scanner scanner = new Scanner(new FileInputStream(csvFileName));

			String line = scanner.nextLine();

			while (scanner.hasNextLine()) {
				line = scanner.nextLine();
				this.addPlayer(new ADPElement(line));
			}

			scanner.close();
		}
	}

	/**
	 * This works "in theory" but the web site I use returns a 403 Forbidden
	 * error, so this isn't going to work.
	 * 
	 * @param JSON_URL
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public static ADPDatabase getDBFromJSONURL(final String JSON_URL) throws MalformedURLException, IOException {
		final ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.readTree(new URL(JSON_URL));

		return ADPDatabase.getDBFromJSON(rootNode);
	}

	public static ADPDatabase getDBFromJSONFile(final String JSON_FILE) throws IOException {
		final byte[] jsonData = Files.readAllBytes(Paths.get(JSON_FILE));

		final ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.readTree(jsonData);

		return ADPDatabase.getDBFromJSON(rootNode);
	}

	public static ADPDatabase getDBFromJSON(final JsonNode rootNode) {
		final ADPDatabase adpDB = new ADPDatabase();

		JsonNode players = rootNode.path(JSON_PLAYERS);
		int count = 1;

		for (JsonNode player : players) {
			final String name = player.path(JSON_NAME).asText();
			final int pos = ADPElement.getPOS(player.path(JSON_POS).asText());
			final int rank = count++;
			final double avgRank = player.path(JSON_AVGRANK).asDouble();
			final double std = player.path(JSON_STD).asDouble();
			final ADPElement adpE = new ADPElement(name, pos, rank, avgRank, std);
			adpDB.addPlayer(adpE);
		}

		return adpDB;
	}

	public void addPlayer(final ADPElement player) {
		db.add(player);
	}

	public ArrayList<ADPElement> findElements(final String query, ArrayList<ADPElement> list) {
		list = list == null ? new ArrayList<ADPElement>() : list;

		list.clear();

		for (final ADPElement player : db) {
			// System.err.println("checking: " + player.name);
			if (player.name.toLowerCase().contains(query.toLowerCase().trim())) {
				// System.err.println("FOUND!!!!!!!! " + player.name +
				// " from \""
				// + query + "\"");
				list.add(player);
			}
		}

		return list;
	}

	public void sortDatabase(final int selection) {
		adpComp.setSelection(selection);
		Collections.sort(db, adpComp);
	}

	public double getSelectProbability(final ADPElement player, final int selection) {
		// calculate the chance of getting this person given the selection
		// number.

		// set the probability for this elements
		return player.getNormalP(selection);
	}

	@Override
	public Iterator<ADPElement> iterator() {
		return db.iterator();
	}

	public static void main(String... args) throws FileNotFoundException {
		final ADPDatabase db = new ADPDatabase();

		for (final ADPElement datum : db) {
			System.out.println(datum);
		}
	}

	public void removePlayer(ADPElement player) {
		int index = 0;
		int removeIndex = -1;
		for (final ADPElement p : db) {
			if (p == player)
				removeIndex = index;
			else if (p.rank > player.rank)
				p.avgRank -= 1.0;
			++index;
		}

		db.remove(removeIndex);
	}
}
