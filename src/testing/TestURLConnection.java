package testing;

import java.io.IOException;
import java.net.MalformedURLException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class TestURLConnection {
	public static void main(String... args) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		WebClient webClient = new WebClient(BrowserVersion.BEST_SUPPORTED);
		HtmlPage page = webClient.getPage("https://fantasyfootballcalculator.com/adp.php");

		Document doc = Jsoup.parse(page.asXml());

		webClient.close();

		System.out.println(doc.text());
	}
}
