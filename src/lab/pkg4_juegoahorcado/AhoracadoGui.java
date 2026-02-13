package lab.pkg4_juegoahorcado;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class AhoracadoGui extends JFrame {

    
    private CardLayout cardLayout;
    private JPanel contenedor;

    private JPanel panelMenu;
    private JPanel panelJuego;
    private JPanel panelAdmin;
    private JPanel panelPalabraFija;

    private JTextArea  areaFigura;
    private JLabel     lblPalabra;
    private JLabel     lblLetrasUsadas;
    private JLabel     lblMensaje;
    private JTextField txtLetra;
    private JButton    btnAdivinar;
    private JLabel     lblModoJuego;

    private JList<String>      listaPalabras;
    private DefaultListModel<String> modeloLista;
    private JTextField         txtNuevaPalabra;
    private JLabel             lblAdminMensaje;

    private JList<String> listaPalabrasFija;
    private DefaultListModel<String> modeloListaFija;
    private JLabel     lblPalabraFijaError;

    private JuegoAhorcadoBase  juego;
    private AdminPalabrasSecretas admin;

    private static final Color BEIGE     = new Color(0xF5F5DC);
    private static final Color ROJO      = new Color(0x8B0000);
    private static final Color ROJO_CLARO= new Color(0xFFF0F0);
    private static final Color VERDE     = new Color(0x006400);

    public AhoracadoGui() {
        admin = new AdminPalabrasSecretas();

        setTitle("Juego del Ahorcado");
        setSize(700, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        cardLayout = new CardLayout();
        contenedor = new JPanel(cardLayout);

        panelMenu       = crearPanelMenu();
        panelJuego      = crearPanelJuego();
        panelAdmin      = crearPanelAdmin();
        panelPalabraFija= crearPanelPalabraFija();

        contenedor.add(panelMenu,        "menu");
        contenedor.add(panelJuego,       "juego");
        contenedor.add(panelAdmin,       "admin");
        contenedor.add(panelPalabraFija, "fija");

        add(contenedor);
        cardLayout.show(contenedor, "menu");
    }


    private JButton crearBoton(String texto, int ancho, int alto) {
        JButton btn = new JButton(texto);
        btn.setPreferredSize(new Dimension(ancho, alto));
        btn.setFont(new Font("Georgia", Font.BOLD, 18));
        btn.setForeground(ROJO);
        btn.setBackground(ROJO_CLARO);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ROJO, 2),
            BorderFactory.createEmptyBorder(4, 10, 4, 10)
        ));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private JLabel crearTitulo(String texto, int tamano) {
        JLabel lbl = new JLabel(texto, SwingConstants.CENTER);
        lbl.setFont(new Font("Georgia", Font.BOLD, tamano));
        lbl.setForeground(ROJO);
        return lbl;
    }

    private JPanel panelBeige(LayoutManager lm) {
        JPanel p = new JPanel(lm);
        p.setBackground(BEIGE);
        return p;
    }

    private JTextField crearCampoTexto(int columnas) {
        JTextField tf = new JTextField(columnas);
        tf.setFont(new Font("Georgia", Font.PLAIN, 20));
        tf.setForeground(ROJO);
        tf.setBackground(ROJO_CLARO);
        tf.setHorizontalAlignment(JTextField.CENTER);
        tf.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ROJO, 2),
            BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));
        return tf;
    }


    private JPanel crearPanelMenu() {
        JPanel panel = panelBeige(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 10, 12, 10);
        gbc.fill   = GridBagConstraints.HORIZONTAL;


      
        JLabel titulo = crearTitulo("EL AHORCADO", 48);

        JLabel subtitulo = new JLabel("Elige un modo de juego", SwingConstants.CENTER);
        subtitulo.setFont(new Font("Georgia", Font.ITALIC, 18));
        subtitulo.setForeground(new Color(0x555555));

    
        JButton btnAzar  = crearBoton("  Palabra Aleatoria", 300, 55);
        JButton btnFija  = crearBoton("  Palabra Fija",       300, 55);
        JButton btnAdmin = crearBoton("  Administrar Palabras",300, 55);
        JButton btnSalir = crearBoton("Salir",                           300, 45);
        btnSalir.setFont(new Font("Georgia", Font.PLAIN, 14));

   
        gbc.gridy = 1; panel.add(titulo,    gbc);
        gbc.gridy = 2; panel.add(subtitulo, gbc);
     
        gbc.gridy = 4; panel.add(btnAzar,   gbc);
        gbc.gridy = 5; panel.add(btnFija,   gbc);
        gbc.gridy = 6; panel.add(btnAdmin,  gbc);
        gbc.gridy = 7; panel.add(btnSalir,  gbc);

        btnAzar .addActionListener(e -> iniciarJuegoAzar());
        btnFija .addActionListener(e -> irAPantallaFija());
        btnAdmin.addActionListener(e -> irAAdmin());
        btnSalir.addActionListener(e -> System.exit(0));

        return panel;
    }

    private JPanel crearPanelPalabraFija() {
        JPanel panel=panelBeige(new GridBagLayout());
        GridBagConstraints gbc=new GridBagConstraints();
        gbc.insets=new Insets(12, 10, 12, 10);
        gbc.fill=GridBagConstraints.BOTH;

        JLabel titulo = crearTitulo("Palabra Fija", 36);

        JLabel instruccion = new JLabel("Selecciona la palabra secreta de la lista:", SwingConstants.CENTER);
        instruccion.setFont(new Font("Georgia", Font.ITALIC, 17));
        instruccion.setForeground(new Color(0x444444));

        modeloListaFija = new DefaultListModel<>();
        listaPalabrasFija = new JList<>(modeloListaFija);
        listaPalabrasFija.setFont(new Font("Georgia", Font.PLAIN, 16));
        listaPalabrasFija.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaPalabrasFija.setBackground(ROJO_CLARO);
        listaPalabrasFija.setForeground(ROJO);
        listaPalabrasFija.setBorder(BorderFactory.createLineBorder(ROJO, 2));
        JScrollPane scrollLista = new JScrollPane(listaPalabrasFija);
        scrollLista.setPreferredSize(new Dimension(350, 200));
        lblPalabraFijaError = new JLabel(" ", SwingConstants.CENTER);
        lblPalabraFijaError.setFont(new Font("Georgia", Font.BOLD, 14));
        lblPalabraFijaError.setForeground(ROJO);

        JButton btnJugar  = crearBoton("Jugar", 220, 50);
        JButton btnVolver = crearBoton("Volver", 220, 40);
        btnVolver.setFont(new Font("Georgia", Font.PLAIN, 14));
        gbc.gridy = 0; gbc.weighty = 0; panel.add(titulo, gbc);
        gbc.gridy = 1; gbc.weighty = 0; panel.add(instruccion, gbc);
        gbc.gridy = 2; gbc.weighty = 1; panel.add(scrollLista, gbc);
        gbc.gridy = 3; gbc.weighty = 0; panel.add(lblPalabraFijaError, gbc);
        gbc.gridy = 4; gbc.weighty = 0; panel.add(btnJugar, gbc);
        gbc.gridy = 5; gbc.weighty = 0; panel.add(btnVolver, gbc);
        btnJugar.addActionListener(e -> iniciarJuegoFijo());
        btnVolver.addActionListener(e -> irAMenu());
        listaPalabrasFija.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    iniciarJuegoFijo();
                }
            }
        });

        return panel;
    }


    private JPanel crearPanelJuego() {
        JPanel panel = panelBeige(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        lblModoJuego = crearTitulo("", 18);
        lblModoJuego.setFont(new Font("Georgia", Font.ITALIC, 16));
        lblModoJuego.setForeground(new Color(0x777777));

        areaFigura = new JTextArea(8, 20);
        areaFigura.setFont(new Font("Monospaced", Font.BOLD, 20));
        areaFigura.setEditable(false);
        areaFigura.setBackground(BEIGE);
        areaFigura.setForeground(ROJO);
        areaFigura.setBorder(BorderFactory.createEmptyBorder(5, 30, 5, 5));

        lblPalabra = new JLabel("", SwingConstants.CENTER);
        lblPalabra.setFont(new Font("Monospaced", Font.BOLD, 34));
        lblPalabra.setForeground(ROJO);

        lblLetrasUsadas = new JLabel("Letras usadas: ", SwingConstants.CENTER);
        lblLetrasUsadas.setFont(new Font("Georgia", Font.PLAIN, 15));
        lblLetrasUsadas.setForeground(new Color(0x555555));


        lblMensaje = new JLabel(" ", SwingConstants.CENTER);
        lblMensaje.setFont(new Font("Georgia", Font.BOLD, 15));
        lblMensaje.setForeground(VERDE);
        lblMensaje.setPreferredSize(new Dimension(0, 24));

        JPanel panelCentro = panelBeige(new BorderLayout(5, 8));
        panelCentro.add(areaFigura, BorderLayout.CENTER);

        JPanel panelInfo = panelBeige(new GridLayout(3, 1, 0, 4));
        panelInfo.add(lblPalabra);
        panelInfo.add(lblLetrasUsadas);
        panelInfo.add(lblMensaje);
        panelCentro.add(panelInfo, BorderLayout.SOUTH);

        txtLetra    = crearCampoTexto(6);
        txtLetra.setToolTipText("Escribe una letra o la palabra completa");

        JLabel lblInstruccion = new JLabel("Escribe una letra:", SwingConstants.CENTER);
        lblInstruccion.setFont(new Font("Georgia", Font.ITALIC, 14));
        lblInstruccion.setForeground(new Color(0x555555));

        btnAdivinar = crearBoton("Adivinar", 160, 45);

        JPanel panelEntrada = panelBeige(new FlowLayout(FlowLayout.CENTER, 12, 8));
        panelEntrada.add(lblInstruccion);
        panelEntrada.add(txtLetra);
        panelEntrada.add(btnAdivinar);

        JButton btnMenu = crearBoton("Menu Principal", 200, 38);
        btnMenu.setFont(new Font("Georgia", Font.PLAIN, 13));

        JPanel panelSur = panelBeige(new BorderLayout(5, 5));
        panelSur.add(panelEntrada, BorderLayout.CENTER);
        panelSur.add(btnMenu,      BorderLayout.SOUTH);

        JPanel panelNorte = panelBeige(new BorderLayout());
        panelNorte.add(lblModoJuego, BorderLayout.CENTER);
        panelNorte.setBorder(BorderFactory.createEmptyBorder(0, 0, 6, 0));

        panel.add(panelNorte,  BorderLayout.NORTH);
        panel.add(panelCentro, BorderLayout.CENTER);
        panel.add(panelSur,    BorderLayout.SOUTH);


        btnAdivinar.addActionListener(e -> procesarEntrada());
        txtLetra.addActionListener(e -> procesarEntrada());
        btnMenu.addActionListener(e -> irAMenu());

        return panel;
    }


    private JPanel crearPanelAdmin() {
        JPanel panel = panelBeige(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        JLabel titulo = crearTitulo("\u2699 Administrar Palabras", 28);
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        modeloLista  = new DefaultListModel<>();
        listaPalabras = new JList<>(modeloLista);
        listaPalabras.setFont(new Font("Georgia", Font.PLAIN, 16));
        listaPalabras.setBackground(ROJO_CLARO);
        listaPalabras.setForeground(ROJO);
        listaPalabras.setSelectionBackground(ROJO);
        listaPalabras.setSelectionForeground(Color.WHITE);
        listaPalabras.setBorder(BorderFactory.createLineBorder(ROJO, 1));

        JScrollPane scroll = new JScrollPane(listaPalabras);
        scroll.setBorder(BorderFactory.createLineBorder(ROJO, 2));
        scroll.setPreferredSize(new Dimension(0, 250));

        JLabel lblConteo = new JLabel("", SwingConstants.RIGHT);
        lblConteo.setFont(new Font("Georgia", Font.ITALIC, 13));
        lblConteo.setForeground(new Color(0x777777));

        JLabel lblAgregar = new JLabel("Nueva palabra:");
        lblAgregar.setFont(new Font("Georgia", Font.BOLD, 15));
        lblAgregar.setForeground(ROJO);

        txtNuevaPalabra = crearCampoTexto(16);
        txtNuevaPalabra.setFont(new Font("Georgia", Font.PLAIN, 16));

        JButton btnAgregar  = crearBoton("Agregar", 130, 40);
        JButton btnEliminar = crearBoton("Eliminar seleccionada", 220, 40);
        btnEliminar.setFont(new Font("Georgia", Font.PLAIN, 14));

        lblAdminMensaje = new JLabel(" ", SwingConstants.CENTER);
        lblAdminMensaje.setFont(new Font("Georgia", Font.BOLD, 14));
        lblAdminMensaje.setForeground(VERDE);

        JPanel panelAgregar = panelBeige(new FlowLayout(FlowLayout.CENTER, 10, 5));
        panelAgregar.add(lblAgregar);
        panelAgregar.add(txtNuevaPalabra);
        panelAgregar.add(btnAgregar);

        JPanel panelBotones = panelBeige(new FlowLayout(FlowLayout.CENTER, 10, 5));
        panelBotones.add(btnEliminar);

        JButton btnVolver = crearBoton("Volver al Men\u00fa", 200, 38);
        btnVolver.setFont(new Font("Georgia", Font.PLAIN, 13));
        
        JPanel panelCentro = panelBeige(new BorderLayout(5, 5));
        panelCentro.add(scroll,     BorderLayout.CENTER);
        panelCentro.add(lblConteo,  BorderLayout.SOUTH);

        JPanel panelSur = panelBeige(new GridLayout(4, 1, 0, 4));
        panelSur.add(panelAgregar);
        panelSur.add(panelBotones);
        panelSur.add(lblAdminMensaje);
        panelSur.add(btnVolver);

        panel.add(titulo,      BorderLayout.NORTH);
        panel.add(panelCentro, BorderLayout.CENTER);
        panel.add(panelSur,    BorderLayout.SOUTH);

        btnAgregar.addActionListener(e -> {
            String nueva = txtNuevaPalabra.getText().trim();
            if (nueva.isEmpty()) {
                mostrarMensajeAdmin("Escribe una palabra primero.", ROJO);
                return;
            }
            try {
                admin.agregarPalabra(nueva);
                modeloLista.addElement(nueva.toLowerCase());
                lblConteo.setText("Total: " + admin.getCantidadPalabras() + " palabras");
                txtNuevaPalabra.setText("");
                mostrarMensajeAdmin("\u00a1Palabra '" + nueva + "' agregada!", VERDE);
            } catch (PalabraDuplicadaException ex) {
                mostrarMensajeAdmin(ex.getMessage(), ROJO);
            }
        });

        btnEliminar.addActionListener(e -> {
            int idx = listaPalabras.getSelectedIndex();
            if (idx == -1) {
                mostrarMensajeAdmin("Selecciona una palabra de la lista.", ROJO);
                return;
            }
            String palabra = modeloLista.get(idx);
            modeloLista.remove(idx);
            sincronizarAdminDesdeModelo();
            lblConteo.setText("Total: " + admin.getCantidadPalabras() + " palabras");
            mostrarMensajeAdmin("Palabra '" + palabra + "' eliminada.", VERDE);
        });

        txtNuevaPalabra.addActionListener(e -> btnAgregar.doClick());

        btnVolver.addActionListener(e -> irAMenu());

        return panel;
    }

    private void irAMenu() {
        cardLayout.show(contenedor, "menu");
    }

    private void irAPantallaFija() {
        modeloListaFija.clear();
        if (admin.getCantidadPalabras() == 0) {
            JOptionPane.showMessageDialog(this,
                "No hay palabras disponibles. Agrega palabras desde el menú de administración.",
                "Lista vacía", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        for (String p : admin.obtenerPalabras()) {
            modeloListaFija.addElement(p);
        }
        
        lblPalabraFijaError.setText(" ");
        cardLayout.show(contenedor, "fija");
        listaPalabrasFija.requestFocus();
    }

    private void irAAdmin() {
        modeloLista.clear();
        for (String p : admin.obtenerPalabras()) {
            modeloLista.addElement(p);
        }
        lblAdminMensaje.setText(" ");
        cardLayout.show(contenedor, "admin");
    }

    private void iniciarJuegoAzar() {
        try {

            if (admin.getCantidadPalabras() == 0) {
                JOptionPane.showMessageDialog(this,
                    "No hay palabras disponibles. Agrega palabras desde el men\u00fa de administraci\u00f3n.",
                    "Lista vac\u00eda", JOptionPane.WARNING_MESSAGE);
                return;
            }
            juego = new JuegoAhoracadoAzar(admin);
            lblModoJuego.setText("Modo: Palabra Aleatoria");
            prepararPantallaJuego();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void iniciarJuegoFijo() {
        String palabraSeleccionada = listaPalabrasFija.getSelectedValue();
        
        if (palabraSeleccionada == null || palabraSeleccionada.isEmpty()) {
            lblPalabraFijaError.setText("Debes seleccionar una palabra de la lista.");
            return;
        }
        
        juego = new JuegoAhorcadoFijo(palabraSeleccionada);
        lblModoJuego.setText("Modo: Palabra Fija - (" + palabraSeleccionada + ")");
        prepararPantallaJuego();
    }

    private void prepararPantallaJuego() {
        txtLetra.setText("");
        lblMensaje.setText(" ");
        txtLetra.setEnabled(true);
        btnAdivinar.setEnabled(true);
        actualizarVista();
        cardLayout.show(contenedor, "juego");
        txtLetra.requestFocus();
    }

    private void procesarEntrada() {
        String entrada = txtLetra.getText().trim();

        if (entrada.isEmpty()) {
            mostrarMensajeJuego("Escribe una letra primero.", ROJO);
            return;
        }

        if (entrada.length() == 1) {
            try {
                juego.procesarLetra(entrada.charAt(0));
                txtLetra.setText("");
                actualizarVista();

                if (juego.hasGanado()) {
                    mostrarMensajeJuego("\u00a1GANASTE! La palabra era: " + juego.getPalabraSecreta(), VERDE);
                    txtLetra.setEnabled(false);
                    btnAdivinar.setEnabled(false);
                    preguntarReinicio(true);
                } else if (juego.getIntentos() <= 0) {
                    mostrarMensajeJuego("\u00a1PERDISTE! Era: " + juego.getPalabraSecreta(), ROJO);
                    txtLetra.setEnabled(false);
                    btnAdivinar.setEnabled(false);
                    preguntarReinicio(false);
                } else {
                    boolean estaEnPalabra = juego.getPalabraSecreta()
                            .indexOf(Character.toLowerCase(entrada.charAt(0))) >= 0;
                    if (estaEnPalabra) {
                        mostrarMensajeJuego("\u2713 \u00a1Letra correcta! Intentos: " + juego.getIntentos(), VERDE);
                    } else {
                        mostrarMensajeJuego("\u2717 Letra incorrecta. Intentos: " + juego.getIntentos(), ROJO);
                    }
                }

            } catch (LetraRepetidaException ex) {
                mostrarMensajeJuego("\u26a0 " + ex.getMessage(), new Color(0xCC6600));
                txtLetra.selectAll();
            } catch (LetraInvalidaException ex) {
                mostrarMensajeJuego("\u26a0 " + ex.getMessage(), ROJO);
                txtLetra.selectAll();
            }

        } else {
            String intento = entrada.toLowerCase().trim();
            String secreta = juego.getPalabraSecreta().toLowerCase().trim();

            if (intento.equals(secreta)) {

                mostrarMensajeJuego("\u00a1GANASTE adivinando la palabra!", VERDE);
                lblPalabra.setText(juego.getPalabraSecreta().toUpperCase()
                        .chars()
                        .collect(StringBuilder::new,
                                 (sb, c) -> sb.append((char) c).append(' '),
                                 StringBuilder::append)
                        .toString().trim());
                txtLetra.setEnabled(false);
                btnAdivinar.setEnabled(false);
                preguntarReinicio(true);
            } else {

                juego.intentos--;
                txtLetra.setText("");
                actualizarVista();
                if (juego.getIntentos() <= 0) {
                    mostrarMensajeJuego("\u00a1PERDISTE! Era: " + juego.getPalabraSecreta(), ROJO);
                    txtLetra.setEnabled(false);
                    btnAdivinar.setEnabled(false);
                    preguntarReinicio(false);
                } else {
                    mostrarMensajeJuego("\u26a0 Palabra incorrecta. Intentos: " + juego.getIntentos(), ROJO);
                }
            }
        }
    }

    private void actualizarVista() {
        areaFigura.setText(juego.getFiguraActual());

        StringBuilder sb = new StringBuilder();
        for (char c : juego.getPalabraActual().toCharArray()) {
            sb.append(c == '_' ? '_' : Character.toUpperCase(c)).append(' ');
        }
        lblPalabra.setText(sb.toString().trim());

        StringBuilder usadas = new StringBuilder("Letras usadas:  ");
        for (Character c : juego.getLetrasUsadas()) {
            usadas.append(Character.toUpperCase(c)).append("  ");
        }
        lblLetrasUsadas.setText(usadas.toString());
    }

    private void mostrarMensajeJuego(String msg, Color color) {
        lblMensaje.setForeground(color);
        lblMensaje.setText(msg);
    }

    private void mostrarMensajeAdmin(String msg, Color color) {
        lblAdminMensaje.setForeground(color);
        lblAdminMensaje.setText(msg);
    }

    private void preguntarReinicio(boolean gano) {
        String msg = gano
                ? "\u00a1Felicidades! \u00bfJugar de nuevo?"
                : "\u00a1Mejor suerte la pr\u00f3xima! \u00bfJugar de nuevo?";
        int op = JOptionPane.showConfirmDialog(this, msg, "Fin del juego",
                JOptionPane.YES_NO_OPTION);
        if (op == JOptionPane.YES_OPTION) {
            irAMenu();
        } else {
            irAMenu();
        }
    }

    private void sincronizarAdminDesdeModelo() {
  
        ArrayList<String> palabrasActuales = new ArrayList<>();
        for (int i = 0; i < modeloLista.size(); i++) {
            palabrasActuales.add(modeloLista.get(i));
        }
     
        admin = new AdminPalabrasSecretas(palabrasActuales);
    }
}