/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab.pkg4_juegoahorcado;

/**
 *
 * @author janinadiaz
 */
public class JuegoAhoracadoAzar extends JuegoAhorcadoBase{
        private AdminPalabrasSecretas adminPalabras;

        public JuegoAhoracadoAzar(AdminPalabrasSecretas admin){
        super();
        this.adminPalabras=admin;
        inicializarPalabraSecreta();

        
        
       }

    @Override
    protected void actualizarPalabraActual(char letra){
        
        String nuevaPalabra="";
        for(int i=0;i<palabraSecreta.length();i++){
            char letraSecreta=palabraSecreta.charAt(i);

            if(letraSecreta==letra){
                nuevaPalabra+=letra;
            }else{
                nuevaPalabra+=palabraActual.charAt(i);
            }
        }

        palabraActual=nuevaPalabra;
        
        
    }

    @Override
    protected boolean verificarLetra(char letra){
        return palabraSecreta.indexOf(letra)>=0;
    }
    @Override
    protected boolean hasGanado(){
       return !palabraActual.contains("_");
    }

    @Override
    public void inicializarPalabraSecreta(){
        try{
            palabraSecreta=adminPalabras.obtenerPalabraRandm().toLowerCase();
            palabraActual="";
            
            for(int i=0;i<palabraSecreta.length();i++){
                if(palabraSecreta.charAt(i)==' '){
                    palabraActual+=" ";
                }else{
                    palabraActual+= "_";
                }
            }

            intentos = limiteIntentos;
            letrasUsadas.clear();

        }catch(ListaVaciaException e){
            System.out.println("Error: "+e.getMessage());
        }
        
    }

    @Override
    public void jugar(){
        
       
    }
        
    
}
