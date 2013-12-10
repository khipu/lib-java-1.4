/* Copyright (c) 2013, khipu SpA
 * All rights reserved.
 * Released under BSD LICENSE, please refer to LICENSE.txt
 */

package com.khipu.lib.java;

import com.khipu.lib.java.exception.KhipuException;
import com.khipu.lib.java.exception.KhipuJSONException;
import com.khipu.lib.java.response.KhipuReceiverBanksResponse;
import com.khipu.lib.java.response.KhipuResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Servicio para obtener el listado de bancos para pagar a un cobrador..
 *
 * @author Alejandro Vera (alejandro.vera@khipu.com)
 * @version 1.2
 * @since 2013-12-02
 */
public class KhipuReceiverBanks extends KhipuService {

    KhipuReceiverBanks(long receiverId, String secret) {
        super(receiverId, secret);
    }

    String getMethodEndpoint() {
        return "receiverBanks";
    }

    public KhipuResponse execute() throws KhipuException, IOException {
        Map map = new HashMap();
        map.put("receiver_id", "" + getReceiverId());
        map.put("hash", HmacSHA256(getSecret(), getConcatenated()));

        try {
            JSONObject info = new JSONObject(post(map));
            List banks = new LinkedList();
            JSONArray banksInfo = info.getJSONArray("banks");
            for (int i = 0; i < banksInfo.length(); i++) {
                JSONObject bankInfo = banksInfo.getJSONObject(i);
                banks.add(new Bank(bankInfo.getString("id"), bankInfo.getString("name"), bankInfo.getString("message"), bankInfo.getInt("min-amount")));
            }
            return new KhipuReceiverBanksResponse(banks);
        } catch (KhipuJSONException e) {
            throw Khipu.getErrorsException(e.getJSON());
        }
    }

    private String getConcatenated() {
        return "receiver_id=" + getReceiverId();
    }
}
