package lab.pkg4_juegoahorcado;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AhoracadoGui extends JFrame {

 
    private JPanel panelMenu;
    private JPanel panelJuego;
    private CardLayout cardLayout;


    private JTextArea areaFigura;
    private JLabel lblPalabra;
    private JLabel lblLetrasUsadas;
    private JLabel lblMensaje;
    private JButton[] botonesLetras;

    private JuegoAhoracadoAzar juego;
    private AdminPalabrasSecretas admin;

    public AhoracadoGui() {
        admin = new AdminPalabrasSecretas();

        setTitle("Juego del Ahorcado");
        setSize(700, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        JPanel contenedor = new JPanel(cardLayout);

        panelMenu = crearPanelMenu();
        panelJuego = crearPanelJuego();

        contenedor.add(panelMenu, "menu");
        contenedor.add(panelJuego, "juego");

        add(contenedor);
        cardLayout.show(contenedor, "menu");
    }

    private JPanel crearPanelMenu() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(0xF5F5DC));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titulo = new JLabel("AHORCADO");
        titulo.setFont(new Font("Arial", Font.BOLD, 48));
        titulo.setForeground(new Color(0x8B0000));

        JButton btnJugar = new JButton("Jugar");
        JButton btnSalir = new JButton("Salir");

        btnJugar.setPreferredSize(new Dimension(200, 55));
        btnJugar.setFont(new Font("Arial", Font.BOLD, 28));
        btnJugar.setForeground(new Color(0x8B0000));
        btnJugar.setBackground(new Color(0xFFF0F0));

        btnSalir.setPreferredSize(new Dimension(200, 55));
        btnSalir.setFont(new Font("Arial", Font.BOLD, 28));
        btnSalir.setBackground(new Color(0xFFF0F0));
        btnSalir.setForeground(new Color(0x8B0000));

        gbc.gridy = 0;
        panel.add(titulo, gbc);
        gbc.gridy = 1;
        panel.add(btnJugar, gbc);
        gbc.gridy = 2;
        panel.add(btnSalir, gbc);

        btnSalir.addActionListener(e -> System.exit(0));
        btnJugar.addActionListener(e -> iniciarJuego());

        return panel;
    }


    private JPanel crearPanelJuego() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(0xF5F5DC));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));


        areaFigura = new JTextArea();
        areaFigura.setFont(new Font("Monospaced", Font.BOLD, 18));
        areaFigura.setEditable(false);
        areaFigura.setBackground(new Color(0xF5F5DC));
        areaFigura.setForeground(new Color(0x8B0000));
        areaFigura.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 5));

        lblPalabra = new JLabel("", SwingConstants.CENTER);
        lblPalabra.setFont(new Font("Monospaced", Font.BOLD, 32));
        lblPalabra.setForeground(new Color(0x8B0000));

        lblLetrasUsadas = new JLabel("Letras usadas: ", SwingConstants.CENTER);
        lblLetrasUsadas.setFont(new Font("Arial", Font.PLAIN, 16));
        lblLetrasUsadas.setForeground(Color.DARK_GRAY);

  
        lblMensaje = new JLabel(" ", SwingConstants.CENTER);
        lblMensaje.setFont(new Font("Arial", Font.BOLD, 16));
        lblMensaje.setForeground(new Color(0x006400));

     
        JPanel panelCentro = new JPanel(new BorderLayout(5, 10));
        panelCentro.setBackground(new Color(0xF5F5DC));
        panelCentro.add(areaFigura, BorderLayout.CENTER);

        JPanel panelPalabra = new JPanel(new GridLayout(3, 1, 0, 4));
        panelPalabra.setBackground(new Color(0xF5F5DC));
        panelPalabra.add(lblPalabra);
        panelPalabra.add(lblLetrasUsadas);
        panelPalabra.add(lblMensaje);
        panelCentro.add(panelPalabra, BorderLayout.SOUTH);

        JPanel panelTeclado = crearTeclado();

     
        JButton btnMenu = new JButton("Menu Principal");
        btnMenu.setFont(new Font("Arial", Font.BOLD, 14));
        btnMenu.setBackground(new Color(0xFFF0F0));
        btnMenu.setForeground(new Color(0x8B0000));
        btnMenu.addActionListener(e -> volverAlMenu());

        JPanel panelSur = new JPanel(new BorderLayout(5, 5));
        panelSur.setBackground(new Color(0xF5F5DC));
        panelSur.add(panelTeclado, BorderLayout.CENTER);
        panelSur.add(btnMenu, BorderLayout.SOUTH);

        panel.add(panelCentro, BorderLayout.CENTER);
        panel.add(panelSur, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel crearTeclado() {

        String[] filas = {"QWERTYUIOP", "ASDFGHJKL", "ZXCVBNM"};
        botonesLetras = new JButton[26];

        JPanel panelTeclado = new JPanel(new GridBagLayout());
        panelTeclado.setBackground(new Color(0xF5F5DC));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 3, 3, 3);

        int indice = 0;
        for (int fila = 0; fila < filas.length; fila++) {
            gbc.gridy = fila;
            String letrasF = filas[fila];
         
            gbc.gridx = (10 - letrasF.length()) / 2; 
            for (int col = 0; col < letrasF.length(); col++) {
                char letra = letrasF.charAt(col);
                JButton btn = new JButton(String.valueOf(letra));
                btn.setPreferredSize(new Dimension(48, 40));
                btn.setFont(new Font("Arial", Font.BOLD, 14));
                btn.setBackground(new Color(0xFFF0F0));
                btn.setForeground(new Color(0x8B0000));
                btn.setFocusPainted(false);

                final char letraFinal = Character.toLowerCase(letra);
                btn.addActionListener(e -> procesarLetraGui(letraFinal, btn));

                botonesLetras[indice++] = btn;
                gbc.gridx++;
                panelTeclado.add(btn,gbc);
            }
        }
        return panelTeclado;
    }

    private void iniciarJuego() {
        juego = new JuegoAhoracadoAzar(admin);
        habilitarTeclado(true);
        lblMensaje.setText(" ");
        actualizarVista();
 
        ((CardLayout) ((JPanel) getContentPane()).getLayout()).show(
                (JPanel) getContentPane(), "juego");
    }

    private void procesarLetraGui(char letra, JButton boton) {
        try {
            juego.procesarLetra(letra);
            boton.setEnabled(false);
            boton.setBackground(Color.LIGHT_GRAY);
            actualizarVista();

            if (juego.hasGanado()) {
                lblMensaje.setForeground(new Color(0x006400));
                lblMensaje.setText("¡GANASTE! La palabra era: " + juego.getPalabraSecreta());
                habilitarTeclado(false);
                preguntarReinicio(true);
            } else if (juego.getIntentos() <= 0) {
                lblMensaje.setForeground(new Color(0x8B0000));
                lblMensaje.setText("¡PERDISTE! La palabra era: " + juego.getPalabraSecreta());
                habilitarTeclado(false);
                preguntarReinicio(false);
            }

        } catch (LetraRepetidaException e) {
            lblMensaje.setForeground(Color.ORANGE.darker());
            lblMensaje.setText("Letra ya usada.");
        } catch (LetraInvalidaException e) {
            lblMensaje.setForeground(Color.RED);
            lblMensaje.setText("Letra invalida.");
        }
    }

    private void actualizarVista() {
    
        areaFigura.setText(juego.getFiguraActual());

    
        StringBuilder palabraMostrar = new StringBuilder();
        for (char c : juego.getPalabraActual().toCharArray()) {
            palabraMostrar.append(c).append(' ');
        }
        lblPalabra.setText(palabraMostrar.toString().trim());

        StringBuilder usadas = new StringBuilder("Letras usadas:  ");
        for (Character c : juego.getLetrasUsadas()) {
            usadas.append(c.toString().toUpperCase()).append("  ");
        }
        lblLetrasUsadas.setText(usadas.toString());
    }

    private void habilitarTeclado(boolean habilitar) {
        for (JButton btn : botonesLetras) {
            if (btn != null) {
                btn.setEnabled(habilitar);
                btn.setBackground(habilitar ? new Color(0xFFF0F0) : Color.LIGHT_GRAY);
            }
        }
    }

    private void preguntarReinicio(boolean gano) {
        String msg = gano
                ? "¡Felicidades! ¿Quieres jugar de nuevo?"
                : "¡Mejor suerte la próxima! ¿Quieres jugar de nuevo?";
        int opcion = JOptionPane.showConfirmDialog(this, msg, "Fin del juego",
                JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            iniciarJuego();
        } else {
            volverAlMenu();
        }
    }

    private void volverAlMenu() {
        ((CardLayout) ((JPanel) getContentPane()).getLayout()).show(
                (JPanel) getContentPane(), "menu");
    }
}