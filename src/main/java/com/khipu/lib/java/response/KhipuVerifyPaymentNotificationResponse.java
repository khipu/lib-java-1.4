/* Copyright (c) 2013, khipu SpA
 * All rights reserved.
 * Released under BSD LICENSE, please refer to LICENSE.txt
 */

package com.khipu.lib.java.response;

/**
 * Objeto que representa la respuesta a verificar si una notificación
 * instantanea de khipu es válida
 *
 * @author Alejandro Vera (alejandro.vera@khipu.com)
 * @version 1.2
 * @since 2013-05-24
 */
public class KhipuVerifyPaymentNotificationResponse implements KhipuResponse {

    private boolean _verified;

    public KhipuVerifyPaymentNotificationResponse(boolean verified) {
        _verified = verified;
    }

    /**
     * Obtiene el valor de autenticidad de los datos enviados a khipu.
     *
     * @return true si los datos fueron efectivamente enviados por khipu
     */
    public boolean isVerified() {
        return _verified;
    }

    /**
     * Establece el valor de autenticidad de los datos enviados a khipu.
     *
     * @param verified el valor de autenticidad
     */
    public void setVerified(boolean verified) {
        _verified = verified;
    }

}
