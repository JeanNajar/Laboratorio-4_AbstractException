/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab.pkg4_juegoahorcado;
import java.util.ArrayList;
import java.util.Random;
/**
 *
 * @author andres
 */
public class AdminPalabrasSecretas {
    private ArrayList<String> palabras;
    private Random random;
    
    public void agregarPalabra(String palabra) throws PalabraDuplicadaException{
        String palabraMinuscula=palabra.toLowerCase().trim();
        if(palabras.contains(palabraMinuscula)){
            throw new PalabraDuplicadaException("La palabra '"+palabra+"' ya existe en la lista");
        }
        palabras.add(palabraMinuscula);
    }
    public String obtenerPalabraRandm() throws ListaVaciaException{
        if(palabras.isEmpty()){
            throw new ListaVaciaException("No hay palabras disponibles");
        }
        int indice;
        indice=random.nextInt(palabras.size());
        return palabras.get(indice);
    }
    public ArrayList<String> obtenerPalabras(){
        return new ArrayList<>(palabras);
    }
    public int getCantidadPalabras(){
        return palabras.size();
    }
    private void cargarPalabras(){
        try{
            agregarPalabra("programacion");
            agregarPalabra("computadora");
            agregarPalabra("netbeans");
            agregarPalabra("internet");
            agregarPalabra("tecnologia");
            agregarPalabra("whatsapp");
            agregarPalabra("sueter");
            agregarPalabra("destornillador");
            agregarPalabra("supercalifragilisticoespialidoso");
            agregarPalabra("aplicacion");
            agregarPalabra("algoritmo");
            agregarPalabra("software");
            agregarPalabra("hardware");
            agregarPalabra("configuraciones");
            
        }
        catch(PalabraDuplicadaException e){
            System.out.println("Error en cargar las palabras: "+e.getMessage());
        }
    }
    public AdminPalabrasSecretas(){
        palabras=new ArrayList<>();
        random=new Random();
        cargarPalabras();
    }
}
