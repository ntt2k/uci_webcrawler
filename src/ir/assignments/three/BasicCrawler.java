package ir.assignments.three;

/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

import java.util.List;
//import java.util.Set;
import java.util.regex.Pattern;
//import java.lang.Throwable;

import org.apache.http.Header;


public class BasicCrawler extends WebCrawler {

    private final static Pattern BINARY_FILES_EXTENSIONS =
            Pattern.compile(".*\\.(bmp|gif|jpe?g|png|tiff?|pdf|ico|xaml|pict|rif|pptx?|ps" +
                    "|mid|mp2|mp3|mp4|wav|wma|au|aiff|flac|ogg|3gp|aac|amr|au|vox" +
                    "|avi|mov|mpe?g|ra?m|m4v|smil|wm?v|swf|aaf|asf|flv|mkv" +
                    "|zip|rar|gz|7z|aac|ace|alz|apk|arc|arj|dmg|jar|lzip|lha)" +
                    "(\\?.*)?$"); // For url Query parts ( URL?q=... )

    /**
     * You should implement this function to specify whether the given url
     * should be crawled or not (based on your crawling logic).
     */
    @Override
    public boolean shouldVisit(WebURL url) {
        String href = url.getURL().toLowerCase();

        return !BINARY_FILES_EXTENSIONS.matcher(href).matches() && href.startsWith("http://www.ics.uci.edu/");
//        return !BINARY_FILES_EXTENSIONS.matcher(href).matches() && href.startsWith("http://ntt2k.technology/");
    }

    /**
     * This function is called when a page is fetched and ready to be processed
     * by your program.
     */
    @Override
    public void visit(Page page) {
        int docid = page.getWebURL().getDocid();
        String url = page.getWebURL().getURL();
        String domain = page.getWebURL().getDomain();
        String path = page.getWebURL().getPath();
        String subDomain = page.getWebURL().getSubDomain();
        String parentUrl = page.getWebURL().getParentUrl();
        String anchor = page.getWebURL().getAnchor();

        logger.info("Docid: {}" + docid);
        logger.info("URL: " + url);
        logger.info("Domain: '{}'" + domain);
        logger.info("Sub-domain: '{}'" + subDomain);
        logger.info("Path: '{}'" + path);
        logger.info("Parent page: {}" + parentUrl);
        logger.info("Anchor text: {}" + anchor);

        // Print out
        System.out.println("Docid: " + docid);
        System.out.println("URL: " + url);
        System.out.println("Domain: " + domain);
        System.out.println("Sub-domain: " + subDomain);
        System.out.println("Path: " + path);
        System.out.println("Parent page: " + parentUrl);
        System.out.println("Anchor text: " + anchor);

        if (page.getParseData() instanceof HtmlParseData) {
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            String text = htmlParseData.getText();
            String html = htmlParseData.getHtml();
            List<WebURL> links = htmlParseData.getOutgoingUrls();

            logger.info("Text length: " + text.length());
            logger.info("Html length: " + html.length());
            logger.info("Number of outgoing links: {}" + links.size());

            // Print out
            System.out.println("Text length: " + text.length());
            System.out.println("Html length: " + html.length());
            System.out.println("Number of outgoing links: " + links.size());
        }

        Header[] responseHeaders = page.getFetchResponseHeaders();
        if (responseHeaders != null) {

            logger.info("Response headers:");
            System.out.println("Response headers:");
            for (Header header : responseHeaders) {
                logger.info("\t{}: {}" + header.getName() + " " + header.getValue());

                System.out.println("\t" + header.getName() + ": " + header.getValue());
            }
        }

        logger.debug("=============");
    }
}

