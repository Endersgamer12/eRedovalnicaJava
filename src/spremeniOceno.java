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
import javax.swing.text.AsyncBoxView.ChildLocator;
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

public class spremeniOceno implements ActionListener {

    JPanel panel;
    JFrame frame;
    JPanel panel2;

    String opis;
    Integer ocena;
    Integer id;

    uciteljSpremeniOcene usc;

    JLabel ocenalabel;
    JLabel opislabel;

    private static JTextField ocenaText;
    private static JTextField opisText;

    RoundedJButton spremeniButton;
    RoundedJButton izbrisiButton;

    GridBagConstraints gbc = new GridBagConstraints();

    public spremeniOceno(Integer idd, Integer ocenaa, String opiss, uciteljSpremeniOcene uscc) {

        opis = opiss;
        ocena = ocenaa;
        id = idd;
        usc = uscc;

        // settanje up frama pa pannela
        panel = new JPanel();
        frame = new JFrame();
        frame.setSize(285, 250);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setTitle("Prijava");
        panel.setBackground(new Color(33, 42, 53));
        panel.setLayout(new GridBagLayout());
        frame.add(panel);

        // vsi objekti na framu
        panel2 = new JPanel();
        panel2.setBackground(Color.BLUE);
        ocenalabel = new JLabel("Ocena:");
        ocenalabel.setPreferredSize(new Dimension(100, 30));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 0, 20, 15);
        ocenalabel.setFont(new Font("Arial", Font.PLAIN, 15));
        ocenalabel.setForeground(new Color(255, 255, 255));

        panel.add(ocenalabel, gbc);

        ocenaText = new RoundedJTextField(10);
        ocenaText.setPreferredSize(new Dimension(100, 30));
        ocenaText.setText(ocena.toString());
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;

        panel.add(ocenaText, gbc);

        opislabel = new JLabel("Opis:");
        opislabel.setPreferredSize(new Dimension(100, 30));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 0, 30, 15);
        opislabel.setFont(new Font("Arial", Font.PLAIN, 15));
        opislabel.setForeground(new Color(255, 255, 255));

        panel.add(opislabel, gbc);

        opisText = new RoundedJTextField(10);
        opisText.setPreferredSize(new Dimension(100, 30));
        opisText.setText(opis);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;

        panel.add(opisText, gbc);

        spremeniButton = new RoundedJButton("Spremeni");
        spremeniButton.setPreferredSize(new Dimension(70, 30));
        spremeniButton.addActionListener(this);
        spremeniButton.setBorder(null);
        spremeniButton.setBackground(new Color(41, 53, 66));
        spremeniButton.setForeground(new Color(255, 255, 255));

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 20, 15);

        panel.add(spremeniButton, gbc);

        izbrisiButton = new RoundedJButton("Izbrisi");
        izbrisiButton.setPreferredSize(new Dimension(70, 30));
        izbrisiButton.addActionListener(this);
        izbrisiButton.setBorder(null);
        izbrisiButton.setBackground(new Color(240, 0, 0));
        izbrisiButton.setForeground(new Color(255, 255, 255));

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 20, 15);

        panel.add(izbrisiButton, gbc);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // preveri login, dobi ali je ucitelj ali stars
    public void Spremeni() {
        Connection c = null;

        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection(
                            "jdbc:postgresql://trumpet.db.elephantsql.com:5432/vnkkwcle",
                            "vnkkwcle", "Ha3l6m0s1K4y8Uax3R_AmfynSejagg8H");
            Statement select = c.createStatement();
            String sql = "SELECT spremeniDanoOceno(" + id + ", " + ocenaText.getText() + ", '" + opisText.getText()
                    + "')";
            ResultSet rs = select.executeQuery(sql);
        } catch (Exception e1) {
            e1.printStackTrace();
        } finally {
            try {
                c.close();
            } catch (Exception e) {
                /* Ignored */ }
        }

    }

    public void Izbrisi() {
        Connection c = null;

        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection(
                            "jdbc:postgresql://trumpet.db.elephantsql.com:5432/vnkkwcle",
                            "vnkkwcle", "Ha3l6m0s1K4y8Uax3R_AmfynSejagg8H");
            Statement select = c.createStatement();
            String sql = "SELECT izbrisiOceno(" + id + ")";
            ResultSet rs = select.executeQuery(sql);
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
        if (e.getSource() == spremeniButton) {
            Spremeni();
            System.out.println(id);
            System.out.println(ocena);
            System.out.println(opis);
            usc.getGradesData();
            usc.Refresh();
            frame.dispose();
        }
        if (e.getSource() == izbrisiButton) {
            Izbrisi();
            usc.getGradesData();
            usc.Refresh();
            frame.dispose();
        }
    }
}
