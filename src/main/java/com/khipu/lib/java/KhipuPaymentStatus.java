/* Copyright (c) 2013, khipu SpA
 * All rights reserved.
 * Released under BSD LICENSE, please refer to LICENSE.txt
 */

package com.khipu.lib.java;

import com.khipu.lib.java.exception.KhipuException;
import com.khipu.lib.java.exception.KhipuJSONException;
import com.khipu.lib.java.response.KhipuPaymentStatusResponse;
import com.khipu.lib.java.response.KhipuResponse;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Servicio para verificar el estado de un pago en khipu.
 *
 * @author Alejandro Vera (alejandro.vera@khipu.com)
 * @version 1.2
 * @since 2013-05-24
 */
public class KhipuPaymentStatus extends KhipuService {
    private String _id;
    private String _paymentId;

    KhipuPaymentStatus(long receiverId, String secret) {
        super(receiverId, secret);
    }

    String getMethodEndpoint() {
        return "paymentStatus";
    }

    public String getId() {
        return _id;
    }

    public void setId(String id) {
        _id = id;
    }

    public String getPaymentId() {
        return _paymentId;
    }

    public void setPaymentId(String paymentId) {
        _paymentId = paymentId;
    }

    public KhipuResponse execute() throws KhipuException, IOException {

        Map map = new HashMap();
        map.put("receiver_id", "" + getReceiverId());
        map.put("payment_id", _paymentId);
        map.put("hash", HmacSHA256(getSecret(), getConcatenated()));
        try {
            JSONObject info = new JSONObject(post(map));
            return new KhipuPaymentStatusResponse(info.getString("status"), info.getString("detail"));
        } catch (KhipuJSONException e) {
            throw Khipu.getErrorsException(e.getJSON());
        }
    }

    private String getConcatenated() {
        return new StringBuilder("receiver_id=" + getReceiverId()).append("&payment_id=" + _paymentId).toString();
    }
}
