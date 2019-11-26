package com.example.iitbqa;

class HttpUtils {

    private static final String BASE_URL_PROD = "http://178.79.171.158:8080/pitstop-customer/";
    private static final String BASE_URL_STAGE = "http://10.0.2.2:8000/api/v1/";

    public static String getBaseUrl() {
        if (BuildConfig.DEBUG) {
            return BASE_URL_STAGE;
        }
        else {
            return BASE_URL_PROD;
        }
    }
}
