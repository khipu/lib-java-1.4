/* Copyright (c) 2013, khipu SpA
 * All rights reserved.
 * Released under BSD LICENSE, please refer to LICENSE.txt
 */

package com.khipu.lib.java.response;

/**
 * Objeto con la respuesta a verificar el estado de un cobrador.
 *
 * @author Alejandro Vera (alejandro.vera@khipu.com)
 * @version 1.2
 * @since 2013-05-24
 */
public class KhipuReceiverStatusResponse implements KhipuResponse {

    private boolean _readyToCollect;
    private String _type;

    public KhipuReceiverStatusResponse(boolean readyToCollect, String type) {
        _readyToCollect = readyToCollect;
        _type = type;
    }

    /**
     * Obtiene el estado del cobrador para poder hacer cobros en khipu.
     *
     * @return true si el cobrador puede efectuar cobros.
     * @since 2013-05-24
     */
    public boolean getReadyToCollect() {
        return _readyToCollect;
    }

    /**
     * Obtiene el estado del cobrador para poder hacer cobros en khipu.
     *
     * @return true si el cobrador puede efectuar cobros.
     * @since 2013-05-24
     */
    public boolean isReadyToCollect() {
        return _readyToCollect;
    }

    /**
     * Establece si la cuenta de cobro está listo para cobrar o no.
     *
     * @param readyToCollect el estado de la cuenta de cobro.
     */
    public void setReadyToCollect(boolean readyToCollect) {
        _readyToCollect = readyToCollect;
    }

    /**
     * Obtiene el tipo de cuenta de cobro. Las cuentas pueden ser de tipo
     * <i>development</i> para desarrollo y <i>production</i> para producción.
     *
     * @return el tipo de cuenta de cobro.
     * @since 2013-05-24
     */
    public String getType() {
        return _type;
    }

    /**
     * Establece el tipo de cuenta de cobro.
     *
     * @param type el tipo de cuenta.
     */
    public void setType(String type) {
        _type = type;
    }

    public String toString() {
        return new StringBuilder("ready_to_collect: ").append(getReadyToCollect()).append(" type: ").append(getType()).toString();
    }
}
