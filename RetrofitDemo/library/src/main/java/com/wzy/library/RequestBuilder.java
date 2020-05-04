package com.wzy.library;

import java.util.HashMap;
import java.util.Map;

public class RequestBuilder {


    private final String method;
    private final String baseUrl;
    private String relativeUrl;

    private Map<String, String> queryMap = new HashMap<>();
    private Map<String, String> fieldMap = new HashMap<>();


    public RequestBuilder(String httpMethod, String baseUrl, String relativeUrl, boolean hasBody) {
        this.method = httpMethod;
        this.baseUrl = baseUrl;
        this.relativeUrl = relativeUrl;

    }

    public void addQueryParam(String name, String value) {
        queryMap.put(name, value);
    }

    public void addFormField(String name, String value) {
        fieldMap.put(name, value);
    }

    public String build() {
        StringBuilder builder = new StringBuilder();
        builder.append(baseUrl).append(relativeUrl).append("?");
        int i = 0;
        for (String key : queryMap.keySet()) {
            if (i == queryMap.keySet().size() - 1) {
                builder.append(key).append("=").append(queryMap.get(key));
            } else {
                builder.append(key).append("=").append(queryMap.get(key)).append("&");
            }

        }
        return builder.toString();
    }
}
