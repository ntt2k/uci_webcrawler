package ir.assignments.three;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

//import java.util.HashSet;
//import java.util.List;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Crawler {
	/**
	 * This method is for testing purposes only. It does not need to be used
	 * to answer any of the questions in the assignment. However, it must
	 * function as specified so that your crawler can be verified programatically.
	 *
	 * This methods performs a crawl starting at the specified seed URL. Returns a
	 * collection containing all URLs visited during the crawl.
	 */
    private static Logger logger = LoggerFactory.getLogger(BasicCrawlController.class);
    private static String targetURL = "http://ntt2k.technology";
    private static String crawlStorageFolder;
    private  static int numberOfCrawlers;


    public static Collection<String> crawl(String seedURL) throws Exception {

        Collection<String> result = new ArrayList<String>();

        CrawlConfig config = new CrawlConfig();

        config.setCrawlStorageFolder(crawlStorageFolder);

    /*
     * Be polite: Make sure that we don't send more than 1 request per
     * second (1000 milliseconds between requests).
     */
        config.setPolitenessDelay(300);


        /**
         * Do you want crawler4j to crawl also binary data ?
         * example: the contents of pdf, or the metadata of images etc
         */
        config.setIncludeBinaryContentInCrawling(false);

    /*
     * Do you need to set a proxy? If so, you can use:
     * config.setProxyHost("proxyserver.example.com");
     * config.setProxyPort(8080);
     *
     * If your proxy also needs authentication:
     * config.setProxyUsername(username); config.getProxyPassword(password);
     */

    /*
     * This config parameter can be used to set your crawl to be resumable
     * (meaning that you can resume the crawl from a previously
     * interrupted/crashed crawl). Note: if you enable resuming feature and
     * want to start a fresh crawl, you need to delete the contents of
     * rootFolder manually.
     */
//        config.setResumableCrawling(false);
        config.setResumableCrawling(true);

        config.setUserAgentString("UCI Inf141-CS121 crawler 14853167 67255516");

    /*
     * Instantiate the controller for this crawl.
     */
        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

    /*
     * For each crawl, you need to add some seed urls. These are the first
     * URLs that are fetched and then the crawler starts following links
     * which are found in these pages
     */
//        controller.addSeed("http://www.ics.uci.edu/");
//        controller.addSeed("http://www.ics.uci.edu/~lopes/");
//        controller.addSeed("http://www.ics.uci.edu/~welling/");
        controller.addSeed("http://ntt2k.technology");

    /*
     * Start the crawl. This is a blocking operation, meaning that your code
     * will reach the line after this only when crawling is finished.
     */
        controller.start(BasicCrawler.class, numberOfCrawlers);

        System.out.println("\n------------------------------");
        System.out.println("\n| TaDa!!! Crawling completed! |");
        System.out.println("\n------------------------------");

        Scanner sc = null;

        try {
            System.out.println("==================================");
            System.out.println(" Start to parse the log file here! ");

            File standard_output = new File("Standard_Output.log");
            sc = new Scanner(standard_output);
            while(sc.hasNextLine())
            {
                String line = sc.nextLine();
//                System.out.println("line 1 --> " + line);

                if(line.startsWith("URL: "))
                {
//                    System.out.println("line 2 --> " + line);
                    result.add(line.substring(5));
                }
            }

        }
        catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error accessing the log file: Standard_Output.log");
            System.exit(-2);
        }
        System.out.println("Result: " + result);
        System.out.println("Result size = " + result.size());
        return result;
    }



    public static void main(String[] args) {


        if (args.length != 2) {
            logger.info("Needed parameters: ");
            logger.info("\t rootFolder (it will contain intermediate crawl data)");
            logger.info("\t numberOfCralwers (number of concurrent threads)");
            return;
        }

        /*
         * crawlStorageFolder is a folder where intermediate crawl data is
         * stored.
         */
            crawlStorageFolder = args[0];

        /*
         * numberOfCrawlers shows the number of concurrent threads that should
         * be initiated for crawling.
         */
            numberOfCrawlers = Integer.parseInt(args[1]);
        try {
            crawl(targetURL);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error setting the targetURL to crawl: \"" + targetURL + "\"");
            System.exit(-2);
        }

    }
}

