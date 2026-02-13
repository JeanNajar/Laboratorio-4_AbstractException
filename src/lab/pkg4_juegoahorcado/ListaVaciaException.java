/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab.pkg4_juegoahorcado;

/**
 *
 * @author andres
 */
public class ListaVaciaException extends Exception {
    public ListaVaciaException(String mensaje) {
        super(mensaje);
    }
    /**
     * Creates a new instance of <code>ListaVaciaException</code> without detail
     * message.
     */
    public ListaVaciaException() {
    }
}
