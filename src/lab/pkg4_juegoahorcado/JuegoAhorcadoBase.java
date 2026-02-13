/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab.pkg4_juegoahorcado;

import java.util.ArrayList;

/**
 *
 * @author janinadiaz
 */
public abstract class JuegoAhorcadoBase implements JuegoAhorcado {
    
    protected String palabraSecreta;
    protected String palabraActual;
    protected int intentos;
    protected final int limiteIntentos=6;
    protected ArrayList<Character> letrasUsadas;
    protected ArrayList<Character> figuraAhorcado;    
    
    public JuegoAhorcadoBase(){
        letrasUsadas=new ArrayList<>();
        figuraAhorcado=new ArrayList<>();
        
    }
    
    
    abstract void actualizarPalabraActual(char letra);
    abstract boolean verificarLetra(char letra);
    abstract boolean hasGanado();
}
