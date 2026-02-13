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
public class LetraInvalidaException extends Exception {
    public LetraInvalidaException(String mensaje){
        super(mensaje);
    }
    public LetraInvalidaException() {
    }
}
