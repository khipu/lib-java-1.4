/* Copyright (c) 2013, khipu SpA
 * All rights reserved.
 * Released under BSD LICENSE, please refer to LICENSE.txt
 */

package com.khipu.lib.java.response;

import com.khipu.lib.java.Payment;

import java.util.Iterator;
import java.util.List;

/**
 * Objeto con la respuesta a inyectar pagos en khipu. Contiene la información
 * del cobro y los pagos asociados a él.
 *
 * @author Alejandro Vera (alejandro.vera@khipu.com)
 * @version 1.2
 * @since 2013-05-24
 */
public class KhipuEmailResponse implements KhipuResponse {

    private String _billId;
    private List _payments;

    public KhipuEmailResponse(String id, List payments) {
        _billId = id;
        _payments = payments;
    }

    /**
     * Entrega el identificador del cobro. Este identificador se utiliza en
     * otros servicios de la API para ejecutar acciones sobre el cobro.
     *
     * @return el identificador del cobro
     * @since 2013-05-24
     */
    public String getBillId() {
        return _billId;
    }

    /**
     * Este método establece el valor del identificador del cobro.
     *
     * @param billId el identificador del cobro.
     * @return el identificador
     * @since 2013-05-24
     */
    public void setBillId(String billId) {
        _billId = billId;
    }

    /**
     * Entrega la lista de pagos asociados a este cobro.
     *
     * @return los pagos de este cobro
     * @since 2013-05-24
     */
    public List getPayments() {
        return _payments;
    }

    /**
     * Establece la lista de pagos asociados a un cobro.
     *
     * @param payments los pagos de este cobro
     * @since 2013-05-24
     */
    public void setPayments(List payments) {
        _payments = payments;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("id: ").append(getBillId()).append("\n");
        for (Iterator iterator = _payments.iterator(); iterator.hasNext(); ) {
            Payment payment = (Payment) iterator.next();
            builder.append("payment_id: ").append(payment.getId()).append("\n");
        }
        return builder.toString();
    }
}
