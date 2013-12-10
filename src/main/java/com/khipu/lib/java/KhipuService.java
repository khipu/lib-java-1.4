/* Copyright (c) 2013, khipu SpA
 * All rights reserved.
 * Released under BSD LICENSE, please refer to LICENSE.txt
 */

package com.khipu.lib.java;

import com.khipu.lib.java.exception.KhipuException;
import com.khipu.lib.java.exception.KhipuJSONException;
import com.khipu.lib.java.response.KhipuResponse;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Map;

public abstract class KhipuService {

    private long _receiverId;
    private String _secret;

    public KhipuService(long receiverId, String secret) {
        _receiverId = receiverId;
        _secret = secret;
    }

    abstract String getMethodEndpoint();

    public abstract KhipuResponse execute() throws KhipuException, IOException;

    public long getReceiverId() {
        return _receiverId;
    }

    public String getSecret() {
        return _secret;
    }

    public void setSecret(String secret) {
        _secret = secret;
    }


    protected String post(Map data) throws KhipuJSONException, IOException {

        HttpClient client = new HttpClient();
        PostMethod postMethod = new PostMethod(Khipu.API_URL + getMethodEndpoint());
        NameValuePair[] params = new NameValuePair[data.keySet().size()];

        int i = 0;
        for (Iterator iterator = data.keySet().iterator(); iterator.hasNext(); i++) {
            String key = (String) iterator.next();
            params[i] = new NameValuePair(key, (String) data.get(key));
        }

        postMethod.setRequestBody(params);

        postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
                new DefaultHttpMethodRetryHandler(3, false));
        if (client.executeMethod(postMethod) == HttpStatus.SC_OK) {
            InputStream stream = postMethod.getResponseBodyAsStream();
            String contentAsString = getContentAsString(stream);
            stream.close();
            postMethod.releaseConnection();
            return contentAsString;
        }
        InputStream stream = postMethod.getResponseBodyAsStream();
        String contentAsString = getContentAsString(stream);
        stream.close();
        postMethod.releaseConnection();
        throw new KhipuJSONException(contentAsString);
    }

    private String getContentAsString(InputStream stream) throws IOException {
        final byte[] buffer = new byte[2048];
        StringBuilder builder = new StringBuilder();
        for (; ; ) {
            int rsz = stream.read(buffer, 0, buffer.length);
            if (rsz < 0) {
                break;
            }
            builder.append(new String(buffer));
        }
        return builder.toString();
    }

    public static String HmacSHA256(String secret, String data) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(secretKeySpec);
            byte[] digest = mac.doFinal(data.getBytes("UTF-8"));
            return byteArrayToString(digest);
        } catch (InvalidKeyException e) {
            throw new RuntimeException("Invalid key exception while converting to HMac SHA256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Algorithm not supported");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Encoding not supported");
        }
    }

    private static String byteArrayToString(byte[] data) {
        BigInteger bigInteger = new BigInteger(1, data);
        String hash = bigInteger.toString(16);
        while (hash.length() < 64) {
            hash = "0" + hash;
        }
        return hash;
    }
}
