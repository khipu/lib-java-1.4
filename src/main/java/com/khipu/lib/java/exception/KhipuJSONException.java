/* Copyright (c) 2013, khipu SpA
 * All rights reserved.
 * Released under BSD LICENSE, please refer to LICENSE.txt
 */

package com.khipu.lib.java.exception;

public class KhipuJSONException extends Exception {

    private static final long serialVersionUID = 1L;

    private String _json;

    public KhipuJSONException(String json) {
        _json = json;
    }

    public String getJSON() {
        return _json;
    }

    public void setJSON(String json) {
        _json = json;
    }

}
