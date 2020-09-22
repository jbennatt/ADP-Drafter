package adp_parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ADPParser {
	public static final String HTML_ADD_12_TEAM = "https://fantasyfootballcalculator.com/adp.php";
	public static final String HTML_ADD_10_TEAM = "https://fantasyfootballcalculator.com/adp.php?format=standard&year=2017&teams=10";
	public static final String HTML_ADD_14_TEAM = "https://fantasyfootballcalculator.com/adp.php?format=standard&year=2017&teams=14";
	public static final String HTML_ADD_8_TEAM = "https://fantasyfootballcalculator.com/adp.php?format=standard&year=2017&teams=8";

	public static final String HTML_TEXT_12_TEAM = "adp_12.html";
	public static final String HTML_TEXT_10_TEAM = "adp_10.html";
	public static final String HTML_TEXT_8_TEAM = "adp_8.html";

	public static final String CSV_FILE_NAME_12_TEAM = "adp_12.txt";
	public static final String CSV_FILE_NAME_10_TEAM = "adp_10.txt";
	public static final String CSV_FILE_NAME_14_TEAM = "adp_14.txt";
	public static final String CSV_FILE_NAME_8_TEAM = "adp_8.txt";

	public static final String HTML_TEAM = HTML_ADD_10_TEAM;
	public static final String CSV_FILE = CSV_FILE_NAME_10_TEAM;

	// There are seven fields: 1) Name, 2) POS, 3) ADP, 4) Average ADP, 5) STD
	// ADP, 6)High round.pick, 7)Low round.pick
	public static final int NUM_FIELDS = 7;
	private static final String[] data = new String[NUM_FIELDS];

	public static void parseADP(final PrintStream pw, final String htmlTeam) throws MalformedURLException, IOException {
		final Document source = Jsoup.parse(new URL(htmlTeam), 10000);

		System.out.println("Reading from " + htmlTeam);

		// get table of data
		final Elements table = source.select("table.adp");

		// System.err.println(table);

		// get the table's rows
		final Elements trs = table.first().children().first().children();
		final int numTRs = trs.size();

		// System.err.println(numTRs);

		pw.println("Name,POS,ADP,Average ADP,STD ADP,High round.pick,Low round.pick");

		// skip the first tr--it's the header information
		for (int i = 1; i < numTRs; ++i) {
			final Element tr = trs.get(i);
			int index = 0;
			data[index++] = tr.child(2).text().trim();// name child ofthis tr
			data[index++] = tr.child(3).text().trim();// pos
			data[index++] = tr.child(0).text().trim(); // ADP
			data[index++] = tr.child(6).text().trim();// average adp
			data[index++] = tr.child(7).text().trim();// std adp
			data[index++] = tr.child(8).text().trim();// high pick (round.pick)
			data[index++] = tr.child(9).text().trim();// low pick (round.pick)

			pw.print(data[0]);

			for (int j = 1; j < data.length; ++j) {
				pw.print(',' + data[j]);
			}
			pw.println();
		}
	}

	private static String readFile(final File file) throws IOException {
		final StringBuilder sb = new StringBuilder();
		final BufferedReader br = new BufferedReader(new FileReader(file));

		String line = null;

		while ((line = br.readLine()) != null)
			sb.append(line + System.lineSeparator());

		br.close();

		return sb.toString();
	}

	public static void parseADP(final PrintWriter pw, final File htmlTeam) throws MalformedURLException, IOException {

		final Document source = Jsoup.parse(readFile(htmlTeam));

		System.out.println("Reading from " + htmlTeam);

		// get table of data
		final Elements table = source.select("table.adp");

		// System.err.println(table);

		// get the table's rows
		final Elements trs = table.first().children().first().children();
		final int numTRs = trs.size();

		// System.err.println(numTRs);

		pw.println("Name,POS,ADP,Average ADP,STD ADP,High round.pick,Low round.pick");

		// skip the first tr--it's the header information
		for (int i = 1; i < numTRs; ++i) {
			final Element tr = trs.get(i);
			int index = 0;
			data[index++] = tr.child(2).text().trim();// name child ofthis tr
			data[index++] = tr.child(3).text().trim();// pos
			data[index++] = tr.child(0).text().trim(); // ADP
			data[index++] = tr.child(6).text().trim();// average adp
			data[index++] = tr.child(7).text().trim();// std adp
			data[index++] = tr.child(8).text().trim();// high pick (round.pick)
			data[index++] = tr.child(9).text().trim();// low pick (round.pick)

			pw.print(data[0]);

			for (int j = 1; j < data.length; ++j) {
				pw.print(',' + data[j]);
			}
			pw.println();
		}
	}

	public static void parseADP(final PrintWriter pw, final String htmlTeam) throws MalformedURLException, IOException {
		final Document source = Jsoup.parse(new URL(htmlTeam), 10000);

		System.out.println("Reading from " + htmlTeam);

		// get table of data
		final Elements table = source.select("table.adp");

		// System.err.println(table);

		// get the table's rows
		final Elements trs = table.first().children().first().children();
		final int numTRs = trs.size();

		// System.err.println(numTRs);

		pw.println("Name,POS,ADP,Average ADP,STD ADP,High round.pick,Low round.pick");

		// skip the first tr--it's the header information
		for (int i = 1; i < numTRs; ++i) {
			final Element tr = trs.get(i);
			int index = 0;
			data[index++] = tr.child(2).text().trim();// name child ofthis tr
			data[index++] = tr.child(3).text().trim();// pos
			data[index++] = tr.child(0).text().trim(); // ADP
			data[index++] = tr.child(6).text().trim();// average adp
			data[index++] = tr.child(7).text().trim();// std adp
			data[index++] = tr.child(8).text().trim();// high pick (round.pick)
			data[index++] = tr.child(9).text().trim();// low pick (round.pick)

			pw.print(data[0]);

			for (int j = 1; j < data.length; ++j) {
				pw.print(',' + data[j]);
			}
			pw.println();
		}
	}

	public static void main(String... args) throws MalformedURLException, IOException {
		final File csvFile = new File(CSV_FILE);
		final PrintWriter pw = new PrintWriter(csvFile);
		try {
			ADPParser.parseADP(pw, HTML_TEAM);
			System.out.println("Wrote to " + csvFile);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			pw.close();
		}
	}
}
