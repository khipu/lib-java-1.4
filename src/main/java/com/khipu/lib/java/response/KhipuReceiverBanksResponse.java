/* Copyright (c) 2013, khipu SpA
 * All rights reserved.
 * Released under BSD LICENSE, please refer to LICENSE.txt
 */

package com.khipu.lib.java.response;

import com.khipu.lib.java.Bank;

import java.util.Iterator;
import java.util.List;

/**
 * Objeto con la respuesta los bancos disponibles para pagar a un cobrador.
 *
 * @author Alejandro Vera (alejandro.vera@khipu.com)
 * @version 1.2
 * @since 2013-12-02
 */
public class KhipuReceiverBanksResponse implements KhipuResponse {

    private List _banks;

    public KhipuReceiverBanksResponse(List banks) {
        _banks = banks;
    }

    /**
     * Establece los bancos disponibles para un pago al cobrador.
     *
     * @param banks listado de bancos
     */
    public void setBanks(List banks) {
        _banks = banks;
    }

    /**
     * Obtiene los bancos disponibles para hacer un pago al cobrador.
     *
     * @return el listado de bancos.
     * @since 2013-12-02
     */
    public List getBanks() {
        return _banks;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (_banks != null) {

            for (Iterator iterator = _banks.iterator(); iterator.hasNext(); ) {
                Bank bank = (Bank) iterator.next();
                builder.append("id: ").append(bank.getId()).append(" name: ").append(bank.getName()).append(" minAmount: ").append(bank.getMinAmount()).append("\n");
            }
        }
        return builder.toString();
    }
}
