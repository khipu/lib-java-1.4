/* Copyright (c) 2013, khipu SpA
 * All rights reserved.
 * Released under BSD LICENSE, please refer to LICENSE.txt
 */

package com.khipu.lib.java;

import com.khipu.lib.java.exception.KhipuException;
import com.khipu.lib.java.exception.KhipuJSONException;
import com.khipu.lib.java.response.KhipuEmailResponse;
import com.khipu.lib.java.response.KhipuResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;

/**
 * Servicio para injectar pagos en khipu. Estos pagos pueden ser opcionalmente
 * enviados por correo electrónico
 *
 * @author Alejandro Vera (alejandro.vera@khipu.com)
 * @version 1.2
 * @since 2013-05-24
 */
public class KhipuCreateEmail extends KhipuService {

    private String _subject;
    private String _body;
    private String _transactionId;
    private String _pictureUrl;
    private String _notifyUrl;
    private String _returnUrl;
    private String _cancelUrl;
    private boolean _payDirectly;
    private boolean _sendEmails;
    private String _custom;
    private Date _expiresDate;

    private List _destinataries = new LinkedList();

    public KhipuCreateEmail(long receiverId, String secret) {
        super(receiverId, secret);
    }

    public String getMethodEndpoint() {
        return "createEmail";
    }

    public void setSubject(String subject) {
        _subject = subject;
    }

    public void setBody(String body) {
        _body = body;
    }

    public void setTransactionId(String transactionId) {
        _transactionId = transactionId;
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

    public void setPayDirectly(boolean payDirectly) {
        _payDirectly = payDirectly;
    }

    public void setSendEmails(boolean sendEmails) {
        _sendEmails = sendEmails;
    }

    public void setExpiresDate(Date expiresDate) {
        _expiresDate = expiresDate;
    }

    public void setCustom(String custom) {
        _custom = custom;
    }

    public void setDestinataries(List destinataries) {
        _destinataries = destinataries;
    }


    public void setPictureUrl(String pictureUrl) {
        _pictureUrl = pictureUrl;
    }

    private String getConcatenated() {
        StringBuilder concatenated = new StringBuilder();
        concatenated.append("receiver_id=").append(getReceiverId());
        concatenated.append("&subject=").append(_subject);
        concatenated.append("&body=").append(_body != null ? _body : "");
        concatenated.append("&destinataries=").append(getDestinatariesJSON());
        concatenated.append("&pay_directly=").append(_payDirectly);
        concatenated.append("&send_emails=").append(_sendEmails);
        concatenated.append("&expires_date=").append(_expiresDate != null ? "" + _expiresDate.getTime() : "");
        concatenated.append("&transaction_id=").append(_transactionId != null ? _transactionId : "");
        concatenated.append("&custom=").append(_custom != null ? _custom : "");
        concatenated.append("&notify_url=").append(_notifyUrl != null ? _notifyUrl : "");
        concatenated.append("&return_url=").append(_returnUrl != null ? _returnUrl : "");
        concatenated.append("&cancel_url=").append(_cancelUrl != null ? _cancelUrl : "");
        concatenated.append("&picture_url=").append(_pictureUrl != null ? _pictureUrl : "");
        return concatenated.toString();
    }


    public void addDestinatary(String name, String email, double amount) {
        Map map = new HashMap();
        map.put("name", name);
        map.put("email", email);
        map.put("amount", "" + amount);
        _destinataries.add(map);
    }

    private String getDestinatariesJSON() {
        JSONArray array = new JSONArray();
        for (Iterator iterator = _destinataries.iterator(); iterator.hasNext(); ) {
            Map map = (Map) iterator.next();
            JSONObject destinatary = new JSONObject();
            destinatary.put("name", (String) map.get("name"));
            destinatary.put("email", (String) map.get("email"));
            destinatary.put("amount", (String) map.get("amount"));
            array.put(destinatary);
        }
        return array.toString();
    }

    public KhipuResponse execute() throws KhipuException, IOException {
        Map map = new HashMap();
        map.put("receiver_id", "" + getReceiverId());
        map.put("subject", _subject);
        map.put("body", _body != null ? _body : "");
        map.put("destinataries", getDestinatariesJSON());
        map.put("expires_date", _expiresDate != null ? "" + _expiresDate.getTime() : "");
        map.put("pay_directly", "" + _payDirectly);
        map.put("send_emails", "" + _sendEmails);
        map.put("transaction_id", _transactionId != null ? _transactionId : "");
        map.put("custom", _custom != null ? _custom : "");
        map.put("notify_url", _notifyUrl != null ? _notifyUrl : "");
        map.put("return_url", _returnUrl != null ? _returnUrl : "");
        map.put("cancel_url", _cancelUrl != null ? _cancelUrl : "");
        map.put("picture_url", _pictureUrl != null ? _pictureUrl : "");
        map.put("hash", HmacSHA256(getSecret(), getConcatenated()));
        try {
            JSONObject info = new JSONObject(post(map));
            List payments = new LinkedList();
            JSONArray paymentsInfo = info.getJSONArray("payments");
            for (int i = 0; i < paymentsInfo.length(); i++) {
                JSONObject paymentInfo = paymentsInfo.getJSONObject(i);
                payments.add(new Payment(paymentInfo.getString("id"), paymentInfo.getString("email"), paymentInfo.getString("url")));
            }
            return new KhipuEmailResponse(info.getString("id"), payments);
        } catch (KhipuJSONException xmlException) {
            throw Khipu.getErrorsException(xmlException.getJSON());
        }
    }
}
