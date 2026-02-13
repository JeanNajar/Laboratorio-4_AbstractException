package lab.pkg4_juegoahorcado;

import java.util.ArrayList;

public abstract class JuegoAhorcadoBase implements JuegoAhorcado {

    protected String palabraSecreta;
    protected String palabraActual;
    protected int intentos;
    protected final int limiteIntentos = 6;
    protected ArrayList<Character> letrasUsadas;
    protected ArrayList<String> figuraAhorcado;

    public JuegoAhorcadoBase() {
        letrasUsadas = new ArrayList<>();
        figuraAhorcado = new ArrayList<>();
        inicializarFiguraAhorcado();
    }

    protected abstract void actualizarPalabraActual(char letra);
    protected abstract boolean verificarLetra(char letra);
    protected abstract boolean hasGanado();


    public String getPalabraActual()          { return palabraActual; }
    public String getPalabraSecreta()         { return palabraSecreta; }
    public int getIntentos()                  { return intentos; }
    public ArrayList<Character> getLetrasUsadas() { return new ArrayList<>(letrasUsadas); }

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
            "======="
        );
        figuraAhorcado.add(
            "|------\n" +
            "|     |\n" +
            "|     0\n" +
            "|     |\\\n" +
            "|      \n" +
            "|      \n" +
            "======="
        );
        figuraAhorcado.add(
            "|------\n" +
            "|     |\n" +
            "|     0\n" +
            "|    /|\\\n" +
            "|      \n" +
            "|      \n" +
            "======="
        );
        figuraAhorcado.add(
            "|------\n" +
            "|     |\n" +
            "|     0\n" +
            "|    /|\\\n" +
            "|    / \n" +
            "|      \n" +
            "======="
        );
        figuraAhorcado.add(
            "|------\n" +
            "|     |\n" +
            "|     0\n" +
            "|    /|\\\n" +
            "|    / \\\n" +
            "|      \n" +
            "======="
        );
    }

    public String getFiguraActual() {
        int etapa = limiteIntentos - intentos;
        return figuraAhorcado.get(etapa);
    }

    public void procesarLetra(char letra) throws LetraInvalidaException, LetraRepetidaException {
        if (!Character.isLetter(letra)) {
            throw new LetraInvalidaException("Debe ingresar una letra valida");
        }
        letra = Character.toLowerCase(letra);

        if (letrasUsadas.contains(letra)) {
            throw new LetraRepetidaException("Esta letra ya fue ingresada");
        }

        letrasUsadas.add(letra);

        if (verificarLetra(letra)) {
            actualizarPalabraActual(letra);
        } else {
            intentos--;
        }
    }
}