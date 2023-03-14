import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.GridBagConstraints;

import java.awt.event.ActionListener;
import java.io.Console;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Shape;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.AbstractBorder;
import javax.swing.border.EmptyBorder;
import javax.xml.parsers.ParserConfigurationException;

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.BorderLayout;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import java.security.spec.KeySpec;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import java.util.Base64;

public class LoginPage implements ActionListener {

    JPanel panel;
    JFrame frame;
    JPanel panel2;

    JLabel emaillabel;
    JLabel passwordlabel;
    JLabel logoLabel;

    private static JTextField emailText;
    private static JTextField passwordText;

    RoundedJButton loginButton;

    GridBagConstraints gbc = new GridBagConstraints();

    public LoginPage() throws Exception {

        // settanje up frama pa pannela
        panel = new JPanel();
        frame = new JFrame();
        frame.setSize(400, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Prijava");
        frame.getContentPane().setBackground(new Color(33, 42, 53));
        panel.setBackground(new Color(33, 42, 53));
        panel.setLayout(new GridBagLayout());
        frame.setLayout(new GridBagLayout());
        frame.add(panel);

        // vsi objekti na framu
        panel2 = new JPanel();
        panel2.setLayout(new GridBagLayout());
        panel2.setBackground(new Color(48, 56, 71));
        panel2.setPreferredSize(new Dimension(330, 100));
        emaillabel = new JLabel("Email:");
        emaillabel.setPreferredSize(new Dimension(100, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.CENTER;
        emaillabel.setFont(new Font("Arial", Font.PLAIN, 15));
        emaillabel.setForeground(new Color(255, 255, 255));

        panel2.add(emaillabel, gbc);

        emailText = new RoundedJTextField(15);
        emailText.setPreferredSize(new Dimension(150, 30));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;

        panel2.add(emailText, gbc);

        passwordlabel = new JLabel("Geslo:");
        passwordlabel.setPreferredSize(new Dimension(100, 20));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        passwordlabel.setFont(new Font("Arial", Font.PLAIN, 15));
        passwordlabel.setForeground(new Color(255, 255, 255));

        panel2.add(passwordlabel, gbc);

        passwordText = new RoundedJPasswordField(15);
        passwordText.setPreferredSize(new Dimension(150, 30));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;

        panel2.add(passwordText, gbc);

        panel.add(panel2, gbc);

        logoLabel = new JLabel("eRedovalnica");
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth = 1;
        logoLabel.setFont(new Font("Arial", Font.BOLD, 15));
        logoLabel.setForeground(new Color(255, 255, 255));
        panel.add(logoLabel, gbc);

        loginButton = new RoundedJButton("Prijava");
        loginButton.setPreferredSize(new Dimension(100, 30));
        loginButton.addActionListener(this);
        loginButton.setBorder(null);
        loginButton.setBackground(new Color(41, 53, 66));
        loginButton.setForeground(new Color(255, 255, 255));

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(15, 5, 5, 5);

        panel.add(loginButton, gbc);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) throws Exception {

        new LoginPage();
    }

    // preveri login, dobi ali je ucitelj ali stars
    public int gettrust() {

        String encodedString = Base64.getEncoder().withoutPadding().encodeToString(passwordText.getText().getBytes());
        System.out.println(encodedString);

        Connection c = null;

        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection(
                            "jdbc:postgresql://trumpet.db.elephantsql.com:5432/vnkkwcle",
                            "vnkkwcle", "Ha3l6m0s1K4y8Uax3R_AmfynSejagg8H");
            Statement select = c.createStatement();
            String sql = "SELECT login('" + emailText.getText() + "', '" + encodedString + "')";
            ResultSet rs = select.executeQuery(sql);
            System.out.println(emailText.getText() + "', '" + passwordText.getText());
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        } finally {
            try {
                c.close();
            } catch (Exception e) {
                /* Ignored */ }
        }

        return 0;
    }

    // event caller, dobi event ki ga izvede button in naredi nekaj
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String mail = emailText.getText();
            int babadoey = gettrust();

            if (babadoey == 0) {
                JOptionPane.showMessageDialog(null, "email ter koda se ne ujemata", "InfoBox: " + "napaka",
                        JOptionPane.INFORMATION_MESSAGE);
            } else if (babadoey == 2) {
                starsPregled sPregled = new starsPregled(mail);
                frame.dispose();

            } else if (babadoey == 1) {
                uciteljPregled uPregled = new uciteljPregled(mail);
                frame.dispose();
            }
        }
    }
}
