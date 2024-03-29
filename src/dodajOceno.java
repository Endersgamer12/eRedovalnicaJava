
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.GridBagConstraints;

import java.awt.event.ActionListener;

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

public class dodajOceno implements ActionListener {

    JPanel panel;
    JFrame frame;
    JPanel panel2;

    Integer id;
    String prdmt;
    String gmail;

    uciteljSpremeniOcene usc;

    JLabel ocenalabel;
    JLabel opislabel;

    private static JTextField ocenaText;
    private static JTextField opisText;

    RoundedJButton spremeniButton;

    GridBagConstraints gbc = new GridBagConstraints();

    public dodajOceno(Integer idd, String predmet, String meil, uciteljSpremeniOcene uscc) {

        id = idd;
        prdmt = predmet;
        gmail = meil;
        usc = uscc;

        // settanje up frama pa pannela
        panel = new JPanel();
        frame = new JFrame();
        frame.setSize(500, 340);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setTitle("Dodaj oceno");
        panel.setBackground(new Color(33, 42, 53));
        panel.setLayout(new GridBagLayout());
        frame.add(panel);

        // vsi objekti na framu
        panel2 = new JPanel();
        panel2.setLayout(new GridBagLayout());
        panel2.setBackground(new Color(48, 56, 71));
        panel2.setPreferredSize(new Dimension(350, 150));

        ocenalabel = new JLabel("Ocena:");
        ocenalabel.setPreferredSize(new Dimension(100, 30));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(0, 0, 20, 15);

        ocenalabel.setFont(new Font("Arial", Font.PLAIN, 15));
        ocenalabel.setForeground(new Color(255, 255, 255));

        panel2.add(ocenalabel, gbc);

        ocenaText = new RoundedJTextField(15);
        ocenaText.setPreferredSize(new Dimension(100, 30));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 0, 0, 0);

        panel2.add(ocenaText, gbc);

        opislabel = new JLabel("Opis:");
        opislabel.setPreferredSize(new Dimension(100, 30));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 0, 20, 15);

        opislabel.setFont(new Font("Arial", Font.PLAIN, 15));
        opislabel.setForeground(new Color(255, 255, 255));

        panel2.add(opislabel, gbc);

        opisText = new RoundedJTextField(15);
        opisText.setPreferredSize(new Dimension(100, 30));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 0, 0, 0);

        panel2.add(opisText, gbc);
        panel.add(panel2, gbc);

        spremeniButton = new RoundedJButton("Dodaj");
        spremeniButton.setPreferredSize(new Dimension(100, 40));
        spremeniButton.addActionListener(this);
        spremeniButton.setBorder(null);
        spremeniButton.setBackground(new Color(41, 53, 66));
        spremeniButton.setForeground(new Color(255, 255, 255));

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;

        panel.add(spremeniButton, gbc);

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
            String sql = "SELECT dodajOceno(" + id + ", " + ocenaText.getText() + ", '" + opisText.getText()
                    + "', '" + prdmt + "', '" + gmail + "')";
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

            usc.getGradesData();
            usc.refreshTable();
            usc.counter = 3;

            frame.dispose();
        }
    }
}
