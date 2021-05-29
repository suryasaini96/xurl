package com.crio.shorturl;

import java.util.HashMap;
import java.util.Random;
import java.util.Map.Entry;

public class XUrlImpl implements XUrl {

    HashMap<String, String> url = new HashMap<String, String>(); // long url to short url
    HashMap<String, Integer> urlcount = new HashMap<String, Integer>(); // long url lookup count

    static final String c = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    // If longUrl already has a corresponding shortUrl, return that shortUrl
    // If longUrl is new, create a new shortUrl for the longUrl and return it
    public String registerNewUrl(String longUrl) {
        if (url.containsKey(longUrl))
            return url.get(longUrl);

        Random rnd = new Random();
        StringBuilder sb = new StringBuilder();
        sb.append("http://short.url/");
        for (int i = 0; i < 9; i++)
            sb.append(c.charAt(rnd.nextInt(c.length())));

        url.put(longUrl, sb.toString());
        return sb.toString();
    }

    // If shortUrl is already present, return null
    // Else, register the specified shortUrl for the given longUrl
    // Note: You don't need to validate if longUrl is already present, 
    //       assume it is always new i.e. it hasn't been seen before 
    public String registerNewUrl(String longUrl, String shortUrl){
        if (url.containsValue(shortUrl))
            return null;

        url.put(longUrl, shortUrl);
        return shortUrl;     
    }

    // If shortUrl doesn't have a corresponding longUrl, return null
    // Else, return the corresponding longUrl
    public String getUrl(String shortUrl) {   // longUrl -- key , shortUrl -- value
        if (url.containsValue(shortUrl)){
            for (Entry<String, String> entry: url.entrySet()){
                if (entry.getValue().equals(shortUrl)){
                    int count = (urlcount.get(entry.getKey()) == null)? 0 : urlcount.get(entry.getKey());
                    urlcount.put(entry.getKey(), count + 1);
                    return entry.getKey();
                }      
            }
        }  
        return null;
    }

    // Return the number of times the longUrl has been looked up using getUrl()
    public Integer getHitCount(String longUrl){
        return (urlcount.get(longUrl)==null? 0: urlcount.get(longUrl));
    }

    // Delete the mapping between this longUrl and its corresponding shortUrl
    // Do not zero the Hit Count for this longUrl
    public String delete(String longUrl){
        return url.remove(longUrl);
    }

}