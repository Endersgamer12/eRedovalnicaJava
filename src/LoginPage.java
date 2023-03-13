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

public class LoginPage implements ActionListener {

    JPanel panel;
    JFrame frame;
    JPanel panel2;

    JLabel emaillabel;
    JLabel passwordlabel;

    private static JTextField emailText;
    private static JTextField passwordText;

    RoundedJButton loginButton;

    GridBagConstraints gbc = new GridBagConstraints();

    public LoginPage() {

        // settanje up frama pa pannela
        panel = new JPanel();
        frame = new JFrame();
        frame.setSize(285, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Prijava");
        panel.setBackground(new Color(33, 42, 53));
        panel.setLayout(new GridBagLayout());
        frame.add(panel);

        // vsi objekti na framu
        panel2 = new JPanel();
        panel2.setBackground(Color.BLUE);
        emaillabel = new JLabel("User:");
        emaillabel.setPreferredSize(new Dimension(70, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 0, 20, 15);
        emaillabel.setFont(new Font("Arial", Font.PLAIN, 15));
        emaillabel.setForeground(new Color(255, 255, 255));

        panel.add(emaillabel, gbc);

        RoundedJTextField emailText = new RoundedJTextField(10);
        emailText.setPreferredSize(new Dimension(100, 20));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;

        panel.add(emailText, gbc);

        passwordlabel = new JLabel("Password:");
        passwordlabel.setPreferredSize(new Dimension(70, 20));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 0, 30, 15);
        passwordlabel.setFont(new Font("Arial", Font.PLAIN, 15));
        passwordlabel.setForeground(new Color(255, 255, 255));

        panel.add(passwordlabel, gbc);

        RoundedJTextField passwordText = new RoundedJTextField(10);
        passwordText.setPreferredSize(new Dimension(100, 20));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;

        panel.add(passwordText, gbc);

        loginButton = new RoundedJButton("Login");
        loginButton.setPreferredSize(new Dimension(70, 30));
        loginButton.addActionListener(this);
        loginButton.setBorder(null);
        loginButton.setBackground(new Color(41, 53, 66));
        loginButton.setForeground(new Color(255, 255, 255));

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 20, 15);

        panel.add(loginButton, gbc);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) throws Exception {

        new LoginPage();
    }

    // preveri login, dobi ali je ucitelj ali stars
    public int gettrust() {
        Connection c = null;

        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection(
                            "jdbc:postgresql://trumpet.db.elephantsql.com:5432/vnkkwcle",
                            "vnkkwcle", "Ha3l6m0s1K4y8Uax3R_AmfynSejagg8H");
            Statement select = c.createStatement();
            String sql = "SELECT login('" + emailText.getText() + "', '" + passwordText.getText() + "')";
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
