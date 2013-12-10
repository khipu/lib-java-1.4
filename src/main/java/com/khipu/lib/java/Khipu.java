/* Copyright (c) 2013, khipu SpA
 * All rights reserved.
 * Released under BSD LICENSE, please refer to LICENSE.txt
 */

package com.khipu.lib.java;

import com.khipu.lib.java.exception.KhipuException;
import org.json.JSONObject;

/**
 * Esta clase sirve para generar instancias de servicios de Khipu
 *
 * @author Alejandro Vera (alejandro.vera@khipu.com)
 * @version 1.2
 * @since 2013-05-24
 */
public class Khipu {
    private static final String AMAZON_IMAGES_URL = "https://s3.amazonaws.com/static.khipu.com/buttons/";
    public static String CREATE_PAYMENT_PAGE_ENDPOINT = "createPaymentPage";
    public static final String API_URL = "https://khipu.com/api/1.2/";
    public static String BUTTON_50x25 = "50x25.png";
    public static String BUTTON_100x25 = "100x25.png";
    public static String BUTTON_100x25_WHITE = "100x25-w.png";
    public static String BUTTON_100x50 = "100x50.png";
    public static String BUTTON_100x50_WHITE = "100x50-w.png";
    public static String BUTTON_150x25 = "150x25.png";
    public static String BUTTON_150x25_WHITE = "150x25-w.png";
    public static String BUTTON_150x50 = "150x50.png";
    public static String BUTTON_150x50_WHITE = "150x50-w.png";
    public static String BUTTON_150x75 = "150x75.png";
    public static String BUTTON_150x75_WHITE = "150x75-w.png";
    public static String BUTTON_200x50 = "200x50.png";
    public static String BUTTON_200x50_WHITE = "200x50-w.png";
    public static String BUTTON_200x75 = "200x75.png";
    public static String BUTTON_200x75_WHITE = "200x75-w.png";

    /**
     * Entrega un servicio para injectar pagos por email en Khipu.
     *
     * @param receiverId id de cobrador
     * @param secret     llave de cobrador
     * @return el servicio para inyectar pagos
     * @see KhipuCreateEmail
     * @since 2013-05-24
     */
    public static KhipuCreateEmail getCreateEmail(long receiverId, String secret) {
        return new KhipuCreateEmail(receiverId, secret);
    }

    public static KhipuCreatePaymentURL getCreatePaymentURL(long receiverId, String secret) {
        return new KhipuCreatePaymentURL(receiverId, secret);
    }

    /**
     * Entrega un servicio para verificar el estado de un pago.
     *
     * @param receiverId id de cobrador
     * @param secret     llave de cobrador
     * @return el servicio para verificar estados
     * @see KhipuPaymentStatus
     * @since 2013-05-24
     */
    public static KhipuPaymentStatus getPaymentStatus(int receiverId, String secret) {
        return new KhipuPaymentStatus(receiverId, secret);
    }

    /**
     * Entrega un servicio para verificar el estado de un cobrador.
     *
     * @param receiverId id de cobrador
     * @param secret     llave de cobrador
     * @return el servicio para verificar el estado
     * @see KhipuReceiverStatus
     * @since 2013-05-24
     */
    public static KhipuReceiverStatus getReceiverStatus(int receiverId, String secret) {
        return new KhipuReceiverStatus(receiverId, secret);
    }

    /**
     * Entrega un servicio para expirar cobros.
     *
     * @param receiverId id de cobrador
     * @param secret     llave de cobrador
     * @return el servicio para expirar cobros
     * @see KhipuSetBillExpired
     * @since 2013-05-24
     */
    public static KhipuSetBillExpired getSetBillExpired(int receiverId, String secret) {
        return new KhipuSetBillExpired(receiverId, secret);
    }

    /**
     * Entrega un servicio para indicar que el pago fue hecho al cobrador por un
     * medio distinto a khipu.
     *
     * @param receiverId id de cobrador
     * @param secret     llave de cobrador
     * @return el servicio para modificar el estado de un pago
     * @see KhipuSetPayedByReceiver
     * @since 2013-05-24
     */
    public static KhipuSetPayedByReceiver getSetPayedByReceiver(int receiverId, String secret) {
        return new KhipuSetPayedByReceiver(receiverId, secret);
    }

    /**
     * Entrega un servicio para obtener el listado de bancos para pagar a un cobrador.
     *
     * @param receiverId id de cobrador
     * @param secret     llave de cobrador
     * @return el servicio para obtener el listado de bancos
     * @see KhipuSetRejectedByPayer
     * @since 2013-12-02
     */
    public static KhipuReceiverBanks getReceiverBanks(int receiverId, String secret) {
        return new KhipuReceiverBanks(receiverId, secret);
    }

    /**
     * Entrega un servicio para indicar que el pago rechazado por el pagador.
     *
     * @param receiverId id de cobrador
     * @param secret     llave de cobrador
     * @return el servicio para modificar el estado de un pago
     * @see KhipuSetRejectedByPayer
     * @since 2013-05-24
     */
    public static KhipuSetRejectedByPayer getSetRejectedByPayer(int receiverId, String secret) {
        return new KhipuSetRejectedByPayer(receiverId, secret);
    }

    public static KhipuException getErrorsException(String json) {
        KhipuException exception = new KhipuException();
        JSONObject info = new JSONObject(json);
        exception.setMessage(info.getJSONObject("error").getString("message"));
        exception.setType(info.getJSONObject("error").getString("type"));
        return exception;
    }

    /**
     * Entrega un String que contiene un botón de pago que dirije a khipu.
     *
     * @param receiverId    id de cobrador
     * @param secret        llave de cobrador
     * @param email         correo del pagador. Este correo aparecerá pre-configurado en
     *                      la página de pago (opcional).
     * @param subject       asunto del cobro. Con un máximo de 255 caracteres.
     * @param body          la descripción del cobro (opcional).
     * @param amount        el monto del cobro.
     * @param notifyUrl     la dirección de tu web service que utilizará khipu para
     *                      notificarte de un pago realizado (opcional).
     * @param returnUrl     la dirección URL a donde enviar al cliente una vez que el pago
     *                      sea realizado (opcional).
     * @param cancelUrl     la dirección URL a donde enviar al cliente si se arrepiente de
     *                      hacer la transacción (opcional)
     * @param pictureUrl    una dirección URL de una foto de tu producto o servicio para
     *                      mostrar en la página del cobro (opcional).
     * @param custom        variable para enviar información personalizada de la
     *                      transacción (opcional).
     * @param transactionId variable disponible para enviar un identificador propio de la
     *                      transacción (opcional).
     * @param button        imagen del botón de pago.
     * @return el servicio para crear botones de pago
     * @since 2013-05-24
     */
    public static String getPaymentButton(int receiverId, String secret, String email, String bankId, String subject, String body, double amount, String notifyUrl, String returnUrl, String cancelUrl, String pictureUrl, String custom, String transactionId, String button) {
        StringBuilder builder = new StringBuilder();
        builder.append("<form action=\"" + API_URL + CREATE_PAYMENT_PAGE_ENDPOINT + "\" method=\"post\">\n");
        builder.append("<input type=\"hidden\" name=\"receiver_id\" value=\"").append(receiverId).append("\">\n");
        builder.append("<input type=\"hidden\" name=\"subject\" value=\"").append(subject != null ? subject : "").append("\"/>\n");
        builder.append("<input type=\"hidden\" name=\"body\" value=\"").append(body != null ? body : "").append("\">\n");
        builder.append("<input type=\"hidden\" name=\"amount\" value=\"").append(amount).append("\">\n");
        builder.append("<input type=\"hidden\" name=\"notify_url\" value=\"").append(notifyUrl != null ? notifyUrl : "").append("\"/>\n");
        builder.append("<input type=\"hidden\" name=\"return_url\" value=\"").append(returnUrl != null ? returnUrl : "").append("\"/>\n");
        builder.append("<input type=\"hidden\" name=\"cancel_url\" value=\"").append(cancelUrl != null ? cancelUrl : "").append("\"/>\n");
        builder.append("<input type=\"hidden\" name=\"custom\" value=\"").append(custom != null ? custom : "").append("\">\n");
        builder.append("<input type=\"hidden\" name=\"transaction_id\" value=\"").append(transactionId != null ? transactionId : "").append("\">\n");
        builder.append("<input type=\"hidden\" name=\"payer_email\" value=\"").append(email != null ? email : "").append("\">\n");
        builder.append("<input type=\"hidden\" name=\"bank_id\" value=\"").append(bankId != null ? bankId : "").append("\">\n");
        builder.append("<input type=\"hidden\" name=\"picture_url\" value=\"").append(pictureUrl != null ? pictureUrl : "").append("\">\n");
        builder.append("<input type=\"hidden\" name=\"hash\" value=\"").append(KhipuService.HmacSHA256(secret, getConcatenated(receiverId, email, bankId, subject, body, amount, notifyUrl, returnUrl, cancelUrl, pictureUrl, custom, transactionId))).append("\">\n");
        builder.append("<input type=\"image\" name=\"submit\" src=\"" + AMAZON_IMAGES_URL).append(button).append("\"/>");
        builder.append("</form>");
        return builder.toString();
    }

    private static String getConcatenated(int receiverId, String email, String bankId, String subject, String body, double amount, String notifyUrl, String returnUrl, String cancelUrl, String pictureUrl, String custom, String transactionId) {
        StringBuilder builder = new StringBuilder();
        builder.append("receiver_id=").append(receiverId);
        builder.append("&subject=").append(subject != null ? subject : "");
        builder.append("&body=").append(body != null ? body : "");
        builder.append("&amount=").append(amount);
        builder.append("&payer_email=").append(email != null ? email : "");
        builder.append("&bank_id=").append(bankId != null ? bankId : "");
        builder.append("&transaction_id=").append(transactionId != null ? transactionId : "");
        builder.append("&custom=").append(custom != null ? custom : "");
        builder.append("&notify_url=").append(notifyUrl != null ? notifyUrl : "");
        builder.append("&return_url=").append(returnUrl != null ? returnUrl : "");
        builder.append("&cancel_url=").append(cancelUrl != null ? cancelUrl : "");
        builder.append("&picture_url=").append(pictureUrl != null ? pictureUrl : "");
        return builder.toString();
    }

    /**
     * Entrega un servicio para verificar la autenticidad de una notificación
     * instantanea de khipu.
     *
     * @param receiverId id de cobrador
     * @return el servicio verificar la autenticidad de un pago.
     * @see KhipuVerifyPaymentNotification
     * @since 2013-05-24
     */
    public static KhipuVerifyPaymentNotification getVerifyPaymentNotification(int receiverId) {
        return new KhipuVerifyPaymentNotification(receiverId);
    }
}
