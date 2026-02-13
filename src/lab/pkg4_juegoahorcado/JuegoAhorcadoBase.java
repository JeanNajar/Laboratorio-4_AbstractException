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
    protected ArrayList<String> figuraAhorcado;    
    
    public JuegoAhorcadoBase(){
        letrasUsadas=new ArrayList<>();
        figuraAhorcado=new ArrayList<>();
        
        
        inicializarFiguraAhorcado();
    }
    
    
    
    protected abstract void actualizarPalabraActual(char letra);
    protected abstract boolean verificarLetra(char letra);
    protected abstract boolean hasGanado();
    private void inicializarFiguraAhorcado() {
        figuraAhorcado.add(
            "|------\n" +
            "|     |\n" +
            "|      \n" +
            "|      \n" +
            "|      \n" +
            "|      \n" +
            "======="
        );
        
        figuraAhorcado.add(
            "|------\n" +
            "|     |\n" +
            "|     0\n" +
            "|      \n" +
            "|      \n" +
            "|      \n" +
            "======="
        );
        
        figuraAhorcado.add(
            "|------\n" +
            "|     |\n" +
            "|     0\n" +
            "|     |\n" +
            "|      \n" +
            "|      \n" +
            "=======" );
        
        // Etapa 3 - 3 intentos (brazo derecho)
        figuraAhorcado.add(
            "|------\n" +
            "|     |\n" +
            "|     0\n" +
            "|     |\\\n" +
            "|      \n" +
            "|      \n" +
            "=======");
        
        figuraAhorcado.add(
            "|------\n" +
            "|     |\n" +
            "|     0\n" +
            "|    /|\\\n" +
            "|      \n" +
            "|      \n" +
            "=======");
        
        figuraAhorcado.add(
            "|------\n" +
            "|     |\n" +
            "|     0\n" +
            "|    /|\\\n" +
            "|    / \n" +
            "|      \n" +
            "=======");
        
        figuraAhorcado.add(
            "|------\n" +
            "|     |\n" +
            "|     0\n" +
            "|    /|\\\n" +
            "|    / \\\n" +
            "|      \n" +
            "=======");
    }
    public String getFiguraActual(){
        int etapa=limiteIntentos-intentos;
        return figuraAhorcado.get(etapa);
    }
    protected void procesarLetra(char letra) throws LetraInvalidaException, LetraRepetidaException {
        if(!Character.isLetter(letra)){
            throw new LetraInvalidaException("Debe ingresar una letra valida");
        }
        letra=Character.toLowerCase(letra);
        
        if(letrasUsadas.contains(letra)){
            throw new LetraRepetidaException("Esta letra ya fue ingresada");
        }
        
        letrasUsadas.add(letra);
        
        if (verificarLetra(letra)){
            actualizarPalabraActual(letra);
        }else{
            intentos--;
        }
    }
    
    
}
