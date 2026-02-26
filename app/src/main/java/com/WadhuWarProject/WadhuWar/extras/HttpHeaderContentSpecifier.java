package com.WadhuWarProject.WadhuWar.extras;

import java.util.HashMap;
import java.util.Map;

public class HttpHeaderContentSpecifier {
    public static final String APPLICATION_X_WWW_FORM_URLENCODED = "application/x-www-form-urlencoded";

    public static final String CONTENT_TYPE = "Content-Type";
    public static final String ACCEPT = "Accept";
    public  static final String APPLICATION_JSON_REQUEST_CONTENT_TYPE = "application/json";

    public static Map<String,String> getHeaderContentAsApplicationFormUrlEncoded(){
        Map<String,String> headerMap = new HashMap<>();

        headerMap.put(CONTENT_TYPE,APPLICATION_X_WWW_FORM_URLENCODED);
        headerMap.put(ACCEPT,APPLICATION_JSON_REQUEST_CONTENT_TYPE);

        return headerMap;
    }
}
