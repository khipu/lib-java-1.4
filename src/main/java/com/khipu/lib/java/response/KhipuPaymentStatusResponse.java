/* Copyright (c) 2013, khipu SpA
 * All rights reserved.
 * Released under BSD LICENSE, please refer to LICENSE.txt
 */

package com.khipu.lib.java.response;

/**
 * Objeto con la respuesta a verificar el estado de un pago.
 *
 * @author Alejandro Vera (alejandro.vera@khipu.com)
 * @version 1.2
 * @since 2013-05-24
 */
public class KhipuPaymentStatusResponse implements KhipuResponse {

    private String _status;
    private String _detail;

    public KhipuPaymentStatusResponse(String status, String detail) {
        _status = status;
        _detail = detail;
    }

    /**
     * Obtiene el estado de este pago.
     *
     * @return el estado de un pago. Los valores posibles son
     * @since 2013-05-24
     */
    public String getStatus() {
        return _status;
    }

    /**
     * Establece el estado de este pago.
     *
     * @param status el estado del pago.
     * @since 2013-05-24
     */
    public void setStatus(String status) {
        _status = status;
    }

    /**
     * Obtiene el detalle sobre el estado del pago. Un pago puede haber
     * terminado por el flujo normal, pero también podría haber sido marcado
     * como pagado por el cobrador.
     *
     * @return el detalle del estado
     * @since 2013-05-24
     */
    public String getDetail() {
        return _detail;
    }

    /**
     * Establece el detalle del estado de este pago.
     *
     * @param detail el detalle del estado.
     * @return el identificador
     * @since 2013-05-24
     */
    public void setDetail(String detail) {
        _detail = detail;
    }

    public String toString() {
        return new StringBuilder("status: ").append(getStatus()).append(" detail: ").append(getDetail()).toString();
    }
}
