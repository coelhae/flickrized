package pt.alex.flickrized.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by nb19875 on 19/06/16.
 */
public class UrlUtils {

    //fullname=Alexandre%20Coelho&oauth_token=72157669930049605-61af209b5fe9e0f1&oauth_token_secret=43cd1d7bebc4f736&user_nsid=141821407%40N08&username=alexandrecoelho3
    public static Map<String,String> queryTomap(String query){
        Map<String,String> result = new HashMap<String,String>();

        String[] parameters =  query.split("&");
        for(String param : parameters){
            String[] pair = param.split("=");
            String key =decodeUrl(pair[0]);
            String value = (pair.length >1 ? decodeUrl(pair[1]) : "");
            result.put(key, value);
        }

        return result;
    }

    public static String decodeUrl(String s){
        String  decoded ="";
        try {
            decoded = URLDecoder.decode(s,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        finally {
            return decoded;
        }
    }

}
