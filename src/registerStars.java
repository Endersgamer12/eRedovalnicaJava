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
import java.util.ArrayList;
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

public class registerStars implements ActionListener {

    JPanel panel;
    JFrame frame;
    JPanel panel2;

    JLabel imelabel;
    JLabel priimeklabel;
    JLabel ulabel;
    JLabel plabel;
    JLabel emaillabel;
    JLabel passwordlabel;
    JLabel logoLabel;

    private static JTextField imeText;
    private static JTextField priimekText;
    private static JTextField uText;
    private static JTextField pText;
    private static JTextField emailText;
    private static JTextField passwordText;

    ArrayList<String> arRazredi = new ArrayList<String>(); // Create an ArrayList object

    JComboBox razred;

    RoundedJButton loginButton;
    RoundedJButton registerButton;

    GridBagConstraints gbc = new GridBagConstraints();

    public registerStars() throws Exception {

        // settanje up frama pa pannela
        panel = new JPanel();
        frame = new JFrame();
        frame.setSize(450, 500);
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
        panel2.setPreferredSize(new Dimension(330, 300));

        imelabel = new JLabel("Ime:");
        imelabel.setPreferredSize(new Dimension(100, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.CENTER;
        imelabel.setFont(new Font("Arial", Font.PLAIN, 15));
        imelabel.setForeground(new Color(255, 255, 255));

        panel2.add(imelabel, gbc);

        imeText = new RoundedJTextField(15);
        imeText.setPreferredSize(new Dimension(150, 30));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;

        panel2.add(imeText, gbc);

        priimeklabel = new JLabel("Priimek:");
        priimeklabel.setPreferredSize(new Dimension(100, 20));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.CENTER;
        priimeklabel.setFont(new Font("Arial", Font.PLAIN, 15));
        priimeklabel.setForeground(new Color(255, 255, 255));

        panel2.add(priimeklabel, gbc);

        priimekText = new RoundedJTextField(15);
        priimekText.setPreferredSize(new Dimension(150, 30));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;

        panel2.add(priimekText, gbc);

        emaillabel = new JLabel("Email:");
        emaillabel.setPreferredSize(new Dimension(100, 20));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.CENTER;
        emaillabel.setFont(new Font("Arial", Font.PLAIN, 15));
        emaillabel.setForeground(new Color(255, 255, 255));

        panel2.add(emaillabel, gbc);

        emailText = new RoundedJTextField(15);
        emailText.setPreferredSize(new Dimension(150, 30));
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;

        panel2.add(emailText, gbc);

        passwordlabel = new JLabel("Geslo:");
        passwordlabel.setPreferredSize(new Dimension(100, 20));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        passwordlabel.setFont(new Font("Arial", Font.PLAIN, 15));
        passwordlabel.setForeground(new Color(255, 255, 255));

        panel2.add(passwordlabel, gbc);

        passwordText = new RoundedJPasswordField(15);
        passwordText.setPreferredSize(new Dimension(150, 30));
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 1;

        panel2.add(passwordText, gbc);

        ulabel = new JLabel("Ucenec ime:");
        ulabel.setPreferredSize(new Dimension(100, 20));
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.CENTER;
        ulabel.setFont(new Font("Arial", Font.PLAIN, 15));
        ulabel.setForeground(new Color(255, 255, 255));

        panel2.add(ulabel, gbc);

        uText = new RoundedJTextField(15);
        uText.setPreferredSize(new Dimension(150, 30));
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 1;

        panel2.add(uText, gbc);

        plabel = new JLabel("Ucenec priimek:");
        plabel.setPreferredSize(new Dimension(100, 20));
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.CENTER;
        plabel.setFont(new Font("Arial", Font.PLAIN, 15));
        plabel.setForeground(new Color(255, 255, 255));

        panel2.add(plabel, gbc);

        pText = new RoundedJTextField(15);
        pText.setPreferredSize(new Dimension(150, 30));
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.gridwidth = 1;

        panel2.add(pText, gbc);

        dobiRazrede();
        String[] razrediTempAr = new String[arRazredi.size()];
        razrediTempAr = arRazredi.toArray(razrediTempAr);
        razred = new JComboBox(razrediTempAr);
        razred.setSelectedIndex(0);
        razred.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 10, 15);
        razred.setBackground(new Color(48, 56, 71));
        razred.setForeground(new Color(255, 255, 255));
        panel2.add(razred, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(panel2, gbc);

        logoLabel = new JLabel("eRedovalnica");
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth = 1;
        logoLabel.setFont(new Font("Arial", Font.BOLD, 15));
        logoLabel.setForeground(new Color(255, 255, 255));
        panel.add(logoLabel, gbc);

        loginButton = new RoundedJButton("Registracija");
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

        registerButton = new RoundedJButton("Nazaj na prijavo");
        registerButton.setPreferredSize(new Dimension(120, 25));
        registerButton.addActionListener(this);
        registerButton.setBorder(null);
        registerButton.setBackground(new Color(41, 53, 66));
        registerButton.setForeground(new Color(255, 255, 255));
        gbc.gridy = 3;
        panel.add(registerButton, gbc);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) throws Exception {

        new registerStars();
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
            String sql = "SELECT dodajStars('" + imeText.getText() + "', '" + priimekText.getText() + "' , '" +
                    emailText.getText() + "' , '" + encodedString + "' , '" + uText.getText() + "', '" + pText.getText()
                    + "','" + razred.getSelectedItem().toString() + "');";

            ResultSet rs = select.executeQuery(sql);
            System.out.println(emailText.getText() + "', '" + passwordText.getText());
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

    public void dobiRazrede() {

        Connection c = null;

        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection(
                            "jdbc:postgresql://trumpet.db.elephantsql.com:5432/vnkkwcle",
                            "vnkkwcle", "Ha3l6m0s1K4y8Uax3R_AmfynSejagg8H");
            Statement select = c.createStatement();

            String sql = "SELECT registerRazredi()";
            ResultSet rs = select.executeQuery(sql);
            while (rs.next()) {
                String item = rs.getString(1);
                item = item.replace("(", "");
                item = item.replace(")", "");
                item = item.replace("\"", "");
                item = item.replace("{", "");
                item = item.replace("}", "");

                arRazredi.add(item);
            }

        } catch (Exception e1) {
            e1.printStackTrace();
        } finally {
            try {
                c.close();
            } catch (Exception e) {
                /* Ignored */ }
        }
    }

    // event caller, dobi event ki ga izvede button in naredi nekaj
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            gettrust();
            try {
                LoginPage lPage = new LoginPage();
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            frame.dispose();
        } else if (e.getSource() == registerButton) {
            try {
                LoginPage lPage = new LoginPage();
                frame.dispose();
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

        }
    }
}