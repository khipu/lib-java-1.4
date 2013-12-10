/* Copyright (c) 2013, khipu SpA
 * All rights reserved.
 * Released under BSD LICENSE, please refer to LICENSE.txt
 */

package com.khipu.lib.java;

import com.khipu.lib.java.exception.KhipuException;
import com.khipu.lib.java.exception.KhipuJSONException;
import com.khipu.lib.java.response.KhipuResponse;
import com.khipu.lib.java.response.KhipuSetRejectedByPayerResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Servicio para que el cobrador indique que el pagador a rechazado el pago.
 *
 * @author Alejandro Vera (alejandro.vera@khipu.com)
 * @version 1.2
 * @since 2013-05-24
 */
public class KhipuSetRejectedByPayer extends KhipuService {

    private String _paymentId;

    private String _text;

    public String getText() {
        return _text;
    }

    public void setText(String text) {
        _text = text;
    }

    public String getPaymentId() {
        return _paymentId;
    }

    public void setPaymentId(String paymentId) {
        _paymentId = paymentId;
    }

    KhipuSetRejectedByPayer(long receiverId, String secret) {
        super(receiverId, secret);
    }

    String getMethodEndpoint() {
        return "setRejectedByPayer";
    }

    public KhipuResponse execute() throws KhipuException, IOException {
        Map map = new HashMap();
        map.put("receiver_id", "" + getReceiverId());
        map.put("payment_id", "" + getPaymentId());
        map.put("text", "" + getText());
        map.put("hash", HmacSHA256(getSecret(), getConcatenated()));
        try {
            post(map);
            return new KhipuSetRejectedByPayerResponse();
        } catch (KhipuJSONException xmlException) {
            throw Khipu.getErrorsException(xmlException.getJSON());
        }
    }

    private String getConcatenated() {
        return new StringBuilder("receiver_id=").append(getReceiverId()).append("&payment_id=").append(getPaymentId()).append("&text=").append(_text != null ? _text : "").toString();
    }

}
