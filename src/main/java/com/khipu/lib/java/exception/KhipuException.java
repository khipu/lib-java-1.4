/* Copyright (c) 2013, khipu SpA
 * All rights reserved.
 * Released under BSD LICENSE, please refer to LICENSE.txt
 */

package com.khipu.lib.java.exception;

/**
 * Excepción que se arroja en caso error en alguna petición a khipu
 *
 * @author Alejandro Vera (alejandro.vera@khipu.com)
 * @version 1.2
 * @since 2013-05-24
 */
public class KhipuException extends Exception {

    private static final long serialVersionUID = 1L;

    private String _type;

    private String _message;

    public String getType() {
        return _type;
    }

    public void setType(String type) {
        _type = type;
    }

    public String getMessage() {
        return _message;
    }

    public void setMessage(String message) {
        _message = message;
    }
}
