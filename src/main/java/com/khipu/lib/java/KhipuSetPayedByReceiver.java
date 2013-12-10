/* Copyright (c) 2013, khipu SpA
 * All rights reserved.
 * Released under BSD LICENSE, please refer to LICENSE.txt
 */

package com.khipu.lib.java;

import com.khipu.lib.java.exception.KhipuException;
import com.khipu.lib.java.exception.KhipuJSONException;
import com.khipu.lib.java.response.KhipuResponse;
import com.khipu.lib.java.response.KhipuSetPayedByReceiverResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Servicio para que el cobrador modificar el estado de un pago a realizado.
 *
 * @author Alejandro Vera (alejandro.vera@khipu.com)
 * @version 1.2
 * @since 2013-05-24
 */
public class KhipuSetPayedByReceiver extends KhipuService {

    private String _paymentId;

    public String getPaymentId() {
        return _paymentId;
    }

    public void setPaymentId(String paymentId) {
        _paymentId = paymentId;
    }

    KhipuSetPayedByReceiver(long receiverId, String secret) {
        super(receiverId, secret);
    }

    String getMethodEndpoint() {
        return "setPayedByReceiver";
    }

    public KhipuResponse execute() throws KhipuException, IOException {
        Map map = new HashMap();
        map.put("receiver_id", "" + getReceiverId());
        map.put("payment_id", "" + getPaymentId());
        map.put("hash", HmacSHA256(getSecret(), getConcatenated()));
        try {
            post(map);
            return new KhipuSetPayedByReceiverResponse();
        } catch (KhipuJSONException e) {
            throw Khipu.getErrorsException(e.getJSON());
        }
    }

    private String getConcatenated() {
        return new StringBuilder("receiver_id=").append(getReceiverId()).append("&payment_id=").append(getPaymentId()).toString();
    }
}
