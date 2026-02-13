package lab.pkg4_juegoahorcado;


public class JuegoAhorcadoFijo extends JuegoAhorcadoBase {
    private String palabraFija;
    
    public JuegoAhorcadoFijo(String palabra) {
        super();
        this.palabraFija = palabra.toUpperCase();
        inicializarPalabraSecreta();
    }
    

    @Override
    public void inicializarPalabraSecreta() {
        this.palabraSecreta = palabraFija;
        this.palabraActual = "";
        
        for (int i = 0; i < palabraSecreta.length(); i++) {
            palabraActual += "_";
        }
    }
    
    @Override
    protected void actualizarPalabraActual(char letra) {
        StringBuilder nuevaPalabra = new StringBuilder(palabraActual);
        
        for (int i = 0; i < palabraSecreta.length(); i++) {
            if (palabraSecreta.charAt(i) == letra) {
                nuevaPalabra.setCharAt(i, letra);
            }
        }
        
        palabraActual = nuevaPalabra.toString();
    }
    
    @Override
    protected boolean verificarLetra(char letra) {
        return palabraSecreta.contains(String.valueOf(letra));
    }
    

    @Override
    protected boolean hasGanado() {
        return !palabraActual.contains("_");
    }
    

    @Override
    public void jugar() {
        System.out.println("Juego del Ahorcado - Palabra Fija");
        System.out.println("Palabra: " + palabraActual);
    }
}