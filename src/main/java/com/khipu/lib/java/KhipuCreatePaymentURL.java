/* Copyright (c) 2013, khipu SpA
 * All rights reserved.
 * Released under BSD LICENSE, please refer to LICENSE.txt
 */

package com.khipu.lib.java;


import com.khipu.lib.java.exception.KhipuException;
import com.khipu.lib.java.exception.KhipuJSONException;
import com.khipu.lib.java.response.KhipuResponse;
import com.khipu.lib.java.response.KhipuUrlResponse;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class KhipuCreatePaymentURL extends KhipuService {


    private String _subject;
    private String _body;
    private String _email;
    private String _bankId;
    private String _transactionId;
    private String _pictureUrl;
    private String _notifyUrl;
    private String _returnUrl;
    private String _cancelUrl;
    private String _custom;
    private double _amount;

    public KhipuCreatePaymentURL(long receiverId, String secret) {
        super(receiverId, secret);
    }

    public String getMethodEndpoint() {
        return "createPaymentURL";
    }

    public void setSubject(String subject) {
        _subject = subject;
    }

    public void setBody(String body) {
        _body = body;
    }

    public void setEmail(String email) {
        _email = email;
    }

    public void setBankId(String bankId) {
        _bankId = bankId;
    }

    public void setAmount(double amount) {
        _amount = amount;
    }

    public void setTransactionId(String transactionId) {
        _transactionId = transactionId;
    }

    public void setPictureUrl(String pictureUrl) {
        _pictureUrl = pictureUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        _notifyUrl = notifyUrl;
    }

    public void setReturnUrl(String returnUrl) {
        _returnUrl = returnUrl;
    }

    public void setCancelUrl(String cancelUrl) {
        _cancelUrl = cancelUrl;
    }

    public void setCustom(String custom) {
        _custom = custom;
    }

    private String getConcatenated() {
        StringBuilder concatenated = new StringBuilder();
        concatenated.append("receiver_id=").append(getReceiverId());
        concatenated.append("&subject=").append(_subject);
        concatenated.append("&body=").append(_body != null ? _body : "");
        concatenated.append("&amount=").append(_amount);
        concatenated.append("&payer_email=").append(_email);
        concatenated.append("&bank_id=").append(_bankId != null ? _bankId : "");
        concatenated.append("&transaction_id=").append(_transactionId != null ? _transactionId : "");
        concatenated.append("&custom=").append(_custom != null ? _custom : "");
        concatenated.append("&notify_url=").append(_notifyUrl != null ? _notifyUrl : "");
        concatenated.append("&return_url=").append(_returnUrl != null ? _returnUrl : "");
        concatenated.append("&cancel_url=").append(_cancelUrl != null ? _cancelUrl : "");
        concatenated.append("&picture_url=").append(_pictureUrl != null ? _pictureUrl : "");
        return concatenated.toString();
    }

    public KhipuResponse execute() throws KhipuException, IOException {
        Map map = new HashMap();
        map.put("receiver_id", "" + getReceiverId());
        map.put("subject", _subject);
        map.put("body", _body != null ? _body : "");
        map.put("amount", "" + _amount);
        map.put("payer_email", "" + _email);
        map.put("bank_id", _bankId != null ? _bankId : "");
        map.put("transaction_id", _transactionId != null ? _transactionId : "");
        map.put("custom", _custom != null ? _custom : "");
        map.put("notify_url", _notifyUrl != null ? _notifyUrl : "");
        map.put("return_url", _returnUrl != null ? _returnUrl : "");
        map.put("cancel_url", _cancelUrl != null ? _cancelUrl : "");
        map.put("picture_url", _pictureUrl != null ? _pictureUrl : "");
        map.put("hash", HmacSHA256(getSecret(), getConcatenated()));
        try {
            JSONObject info = new JSONObject(post(map));
            return new KhipuUrlResponse(info.getString("id"), info.getString("url"), info.getString("mobile-url"));
        } catch (KhipuJSONException e) {
            throw Khipu.getErrorsException(e.getJSON());
        }
    }
}
