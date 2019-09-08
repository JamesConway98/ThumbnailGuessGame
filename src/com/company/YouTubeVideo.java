package com.company;

import org.apache.commons.lang3.StringEscapeUtils;
import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class YouTubeVideo {

    private WebReader wr;
    private Image thumbnailImage;
    private Thumbnail thumbnail;
    private String webpage, title;

    public YouTubeVideo(String webpage) throws Exception{
        wr = new WebReader();
        this.webpage = webpage;
        title = retrieveTitle();
        thumbnailImage = retreiveThumbnailImage(webpage);
        thumbnail = new Thumbnail(thumbnailImage, true);
    }

    public Image retreiveThumbnailImage(String webpage)throws Exception{

        String inputLine = wr.returnLine("thumbnailUrl", webpage);

        URL imageUrl = null;

        if(inputLine == null) {
            System.out.println(webpage);
            imageUrl = new URL("https://i.ytimg.com/vi/AjWfY7SnMBI/maxresdefault.jpg");
        }else {
            Pattern p = Pattern.compile("\"([^\"]*)\"");
            Matcher m = p.matcher(inputLine);

            m.find();
            while (m.find()) {
                System.out.println(m.group(1));
                imageUrl = new URL(m.group(1));
            }
        }
        Image image = null;

        try {
            image = ImageIO.read(imageUrl);
        } catch (IOException e) {
        }
        return image;
    }

    public String retrieveTitle()throws Exception{

        String inputLine = wr.returnLine("<title>", webpage);

        String title = ("");

        if(inputLine!=null) {

            title = inputLine.substring(inputLine.indexOf(">") + 1);
            title = title.substring(0, title.indexOf("- YouTube"));

            System.out.println(title);      //will contain weird apost etc Wasn&#39;t
        }
        return StringEscapeUtils.unescapeXml(title);
    }

    public String getTitle(){
        return title;
    }

    public ArrayList<Thumbnail> getSuggestedThumbnails(GameSetup gs)throws Exception{

        ArrayList<String> inputLines = wr.returnLines("href=\"/watch?", webpage);
        ArrayList<String> imageUrls = new ArrayList<>();
        ArrayList<Thumbnail> thumbnails = new ArrayList<>();

        String urlEnd = "", start = "";
        String imageUrl;

        if(!inputLines.isEmpty()) {

            for(String inputLine: inputLines) {

                urlEnd = inputLine.substring(inputLine.indexOf("/watch"));
                urlEnd = urlEnd.substring(0, urlEnd.indexOf("\""));

                System.out.println("WWWWWW " + urlEnd);

                imageUrl = "https://www.youtube.com" + urlEnd;

                if(!imageUrls.contains(urlEnd)) {
                    start = inputLine.substring(inputLine.indexOf("/watch"), inputLine.indexOf("/watch") + 15);

                    if (!(webpage.substring(webpage.indexOf("/watch"), webpage.indexOf("/watch") + 15).equals(start))) {
                        Image image = null;

                        image = retreiveThumbnailImage(imageUrl);
                        Thumbnail tn = new Thumbnail(image, false);

                        thumbnails.add(tn);
                        imageUrls.add(urlEnd);

                        gs.addLoaded();
                    }
                    if (thumbnails.size() >= GameSetup.NO_OF_THUMBNAILS)
                        break;
                }
            }
        }
        return thumbnails;
    }

    public Thumbnail getThumbnail(){
        return thumbnail;
    }
}
