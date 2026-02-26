package com.WadhuWarProject.WadhuWar.extras;

import java.util.UUID;

public class Constants {




    public static final String M_ID = "VoAzrU52765434308425"; //Paytm Merchand Id we got it in paytm credentials
    public static final String CHANNEL_ID = "WAP"; //Paytm Channel Id, got it in paytm credentials
    public static final String INDUSTRY_TYPE_ID = "Retail"; //Paytm industry type got it in paytm credential

    public static final String WEBSITE = "WEBSTAGING";
    public static final String MKEY = "rfZ_jrMo7A8A&7n@";


    /*public static final String ORDERID =generateString();
    private static String generateString() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "");
    }*/
//    public static final String CALLBACK_URL = "https://pguat.paytm.com/paytmchecksum/paytmCallback.jsp";

    public static final String ORDERID = "123";

    public static final String CALLBACK_URL = "https://securegw-stage.paytm.in/theia/paytmCallback?ORDER_ID="+ORDERID;

//    public static final String CALLBACK_URL = "https://securegw.paytm.in/theia/paytmCallback?ORDER_ID="+ORDERID;


}