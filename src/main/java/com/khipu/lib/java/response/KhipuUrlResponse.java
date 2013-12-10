/* Copyright (c) 2013, khipu SpA
 * All rights reserved.
 * Released under BSD LICENSE, please refer to LICENSE.txt
 */

package com.khipu.lib.java.response;

public class KhipuUrlResponse implements KhipuResponse {
    private final String _id;
    private final String _url;
    private final String _mobileUrl;

    public KhipuUrlResponse(String id, String url, String mobileUrl) {
        _id = id;
        _url = url;
        _mobileUrl = mobileUrl;
    }

    public String getId() {
        return _id;
    }

    public String getUrl() {
        return _url;
    }

    public String getMobileUrl() {
        return _mobileUrl;
    }

    public String toString() {
        return new StringBuilder().append("id: ").append(getId()).append(" url: ").append(getUrl()).append(" mobileUrl: ").append(getMobileUrl()).toString();
    }
}
