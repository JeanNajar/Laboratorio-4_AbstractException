package lab.pkg4_juegoahorcado;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AhoracadoGui extends JFrame{
    


    public AhoracadoGui() {
        setTitle("Juego del Ahorcado");
        setSize(700, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(0xF5F5DC));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);

        JLabel titulo = new JLabel("AHORCADO");
        titulo.setFont(new Font("Arial", Font.BOLD, 48));
        titulo.setForeground(new Color(0x8B0000));

        JButton btnJugar = new JButton("Jugar");
        JButton btnSalir = new JButton("Salir");

        btnJugar.setPreferredSize(new Dimension(200,55));
        btnJugar.setFont(new Font("Arial", Font.BOLD, 28));
        btnJugar.setForeground(new Color(0x8B0000));
        btnJugar.setBackground(new Color (0xFFF0F0));
        btnSalir.setPreferredSize(new Dimension(200,55));
        btnSalir.setFont(new Font("Arial", Font.BOLD, 28));
        btnSalir.setBackground(new Color (0xFFF0F0));
        btnSalir.setForeground(new Color(0x8B0000));
        
        gbc.gridy = 0;
        panel.add(titulo, gbc);

        gbc.gridy = 1;
        panel.add(btnJugar, gbc);

        gbc.gridy = 2;
        panel.add(btnSalir, gbc);

        add(panel);

        // Acciones
        btnSalir.addActionListener(e -> System.exit(0));

        btnJugar.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Aquí inicia el juego");
            // Aquí puedes abrir tu clase del juego
        });
    }
}