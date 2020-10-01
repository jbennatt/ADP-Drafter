# ADP-Drafter
Java Swing project project that I've used for several years to help me in my fantasy football drafts.

## Dependencies
* jackson JSON parsing (I'm using v2.9.6 just because I haven't updated it and, at the time, this wasn't a maven project)
  * [jackson-databind](https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind)
  * [jackson-core](https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-core)
  * [jackson-annotations](https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-annotations)
* [APACHE Commons Math](https://mvnrepository.com/artifact/org.apache.commons/commons-math3) (v3.6.1)
* HTML Parsing (this is deprecated and has been replaced by JSON data, but the old parsing routines are still present in the project)
  * [HtmlUnit](https://mvnrepository.com/artifact/net.sourceforge.htmlunit/htmlunit) (v2.27) - used to make connection to Fantasy Football Calculator ADP data.
  * [jsoup](https://mvnrepository.com/artifact/org.jsoup/jsoup) (v1.7.2) - used to parse HTML data from Fantasy Football Calculator ADP Data. This will no longer work because it had to be updated every year and I haven't maintained it since 2017, when Fantasy Football Calculator started offereing CSV data.

## Running
The main method is located in [src/drafter/Draft.java](https://github.com/jbennatt/ADP-Drafter/blob/master/src/drafter/Draft.java). This is a fully functional command line program (which launches a GUI).

You can look at the example JSON files in [adp_data](https://github.com/jbennatt/ADP-Drafter/tree/master/adp_data) but the format is specifically for JSON data from [Fantasy Football Calculator](https://fantasyfootballcalculator.com/adp) (current as of 2020).

This is a fully functional command line program and it does have a functioning help command:

```
$ java -jar drafter.jar -h
java -jar drafter.jar -p position -n numTeams -r numRounds -d numDisplays -f file
-p position: YOUR draft position, e.g. 3 means you pick 3rd, DEFAULT: 7
-n numTeams: Number of teams in the draft, e.g. 8, 10, 12, etc.  DEFAULT: 12
-r numRounds: Number of rounds in the draft, e.g. 15, 16, etc. DEFAULT: 15
-d numDisplays: Number of rounds to display in the draft helper.  DEFAULT: 7
-f file: path to JSON file used to create ADP. DEFAULT: adp_data/2020_12_PPR.json
```

However, if running from an IDE (which is my preferred method), it's probably easier to just modify `ADP_JSON` on line 174 of [`Draft.java`](https://github.com/jbennatt/ADP-Drafter/blob/master/src/drafter/Draft.java).
