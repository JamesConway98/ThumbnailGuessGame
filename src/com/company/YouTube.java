package com.company;

import java.util.ArrayList;

public class YouTube {

    private WebReader wr;

    public YouTube(){
        wr = new WebReader();
    }

    public YouTubeVideo getVideo() throws Exception{

        ArrayList<String> inputLines = wr.returnLines("<h3 class=\"yt-lockup-title \"><a href=\"/watch", "https://www.youtube.com/");
        ArrayList<String> urls = new ArrayList<>();

        String title = "";
        String url = "";

        if(!inputLines.isEmpty()) {
            //System.out.println(inputLines.toString());
            for(String inputLine: inputLines) {

                title = inputLine.substring(inputLine.indexOf("/watch"));
                title = title.substring(0, title.indexOf("\""));

                url = "https://www.youtube.com" + title;

                urls.add(url);

            }
        }

        int random = (int)(Math.random() * urls.size());

        YouTubeVideo video = new YouTubeVideo(urls.get(random));

        return video;
    }
}
