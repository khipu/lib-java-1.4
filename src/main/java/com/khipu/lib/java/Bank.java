/* Copyright (c) 2013, khipu SpA
 * All rights reserved.
 * Released under BSD LICENSE, please refer to LICENSE.txt
 */

package com.khipu.lib.java;

/**
 * Este objeto representa un banco disponible en khipu.
 *
 * @author Alejandro Vera (alejandro.vera@khipu.com)
 * @version 1.2
 * @since 2013-05-24
 */
public class Bank {

    private String _id;

    private String _name;

    private String _message;

    private double _minAmount;

    public Bank(String id, String name, String message, double minAmount) {
        _id = id;
        _name = name;
        _message = message;
        _minAmount = minAmount;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        _name = name;
    }

    public String getId() {
        return _id;
    }

    public void setId(String id) {
        _id = id;
    }

    public String getMessage() {
        return _message;
    }

    public void setMessage(String message) {
        _message = message;
    }

    public double getMinAmount() {
        return _minAmount;
    }

    public void setMinAmount(double minAmount) {
        _minAmount = minAmount;
    }
}
