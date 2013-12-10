/* Copyright (c) 2013, khipu SpA
 * All rights reserved.
 * Released under BSD LICENSE, please refer to LICENSE.txt
 */

package com.khipu.lib.java;

import com.khipu.lib.java.exception.KhipuException;
import com.khipu.lib.java.exception.KhipuJSONException;
import com.khipu.lib.java.response.KhipuReceiverStatusResponse;
import com.khipu.lib.java.response.KhipuResponse;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Servicio para verificar el estado de una cuenta de cobro.
 *
 * @author Alejandro Vera (alejandro.vera@khipu.com)
 * @version 1.2
 * @since 2013-05-24
 */
public class KhipuReceiverStatus extends KhipuService {

    KhipuReceiverStatus(long receiverId, String secret) {
        super(receiverId, secret);
    }

    String getMethodEndpoint() {
        return "receiverStatus";
    }

    public KhipuResponse execute() throws KhipuException, IOException {
        Map map = new HashMap();
        map.put("receiver_id", "" + getReceiverId());
        map.put("hash", HmacSHA256(getSecret(), getConcatenated()));
        try {
            JSONObject json = new JSONObject(post(map));
            return new KhipuReceiverStatusResponse(json.getBoolean("ready_to_collect"), json.getString("type"));
        } catch (KhipuJSONException e) {
            throw Khipu.getErrorsException(e.getJSON());
        }
    }

    private String getConcatenated() {
        return "receiver_id=" + getReceiverId();
    }

}
