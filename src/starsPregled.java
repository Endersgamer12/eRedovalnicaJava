import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.io.Console;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.GridBagConstraints;
import java.awt.Graphics;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.*;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Objects;
import java.applet.Applet;
import org.postgresql.core.BaseConnection;
import org.postgresql.copy.CopyManager;

public class starsPregled implements ActionListener {

    JPanel panel;
    JFrame frame;

    String[][] dataArray = new String[1000][1000];

    JButton editButton;
    JButton newButton;
    JButton deleteButton;
    JButton izvozButton;

    JTable booksTable;
    JScrollPane scrollPane;

    GridBagConstraints gbc = new GridBagConstraints();

    String mail = "joze.kuznik@gmail.com";

    public starsPregled() {

        panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        frame = new JFrame();
        frame.setSize(1000, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.setBackground(new Color(76, 218, 240));
        frame.setTitle("Dashboard");

        newButton = new JButton("Dodaj knjigo");
        newButton.addActionListener(this);
        newButton.setBackground(new Color(77, 152, 218));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 38, 5, 38);
        panel.add(newButton, gbc);

        editButton = new JButton("Spremeni oznaceno knjigo");
        editButton.addActionListener(this);
        editButton.setBackground(new Color(77, 152, 218));
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(editButton, gbc);

        deleteButton = new JButton("Izbrisi oznaceno knjigo");
        deleteButton.addActionListener(this);
        deleteButton.setBackground(new Color(77, 152, 218));
        gbc.gridx = 2;
        gbc.gridy = 1;
        panel.add(deleteButton, gbc);

        izvozButton = new JButton("Izvoz podatkov");
        izvozButton.addActionListener(this);
        izvozButton.setBackground(new Color(77, 152, 218));
        gbc.gridx = 3;
        gbc.gridy = 1;
        panel.add(izvozButton, gbc);

        getGradesData();
        String vrste[] = { "ID", "Stevilka", "Opis", "Ucenec", "Predmet", "Ucitelj" };
        booksTable = new JTable(dataArray, vrste);
        booksTable.getTableHeader().setOpaque(false);
        booksTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        booksTable.getTableHeader().setBackground(new Color(77, 220, 180));
        booksTable.getTableHeader().setBorder(BorderFactory.createLineBorder(new Color(20, 160, 185), 1));
        booksTable.setDefaultEditor(Object.class, null);
        booksTable.getColumnModel().getColumn(0).setPreferredWidth(1);
        booksTable.setFont(new Font("Arial", Font.PLAIN, 14));
        booksTable.setBackground(new Color(77, 218, 180));
        booksTable.setBorder(BorderFactory.createEmptyBorder());

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 5;

        gbc.fill = GridBagConstraints.HORIZONTAL;
        scrollPane = new JScrollPane(booksTable);
        scrollPane.setPreferredSize(new Dimension(800, 500));
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(20, 160, 185), 2));
        scrollPane.setFont(new Font("Arial", Font.PLAIN, 14));
        scrollPane.setVisible(true);
        panel.add(scrollPane, gbc);

        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) throws Exception {

        new starsPregled();
    }

    public void getGradesData() {

        Connection c = null;

        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection(
                            "jdbc:postgresql://trumpet.db.elephantsql.com:5432/vnkkwcle",
                            "vnkkwcle", "Ha3l6m0s1K4y8Uax3R_AmfynSejagg8H");
            Statement select = c.createStatement();
            String sql = "SELECT starsPregledOcene('" + mail + "')";
            ResultSet rs = select.executeQuery(sql);
            Integer counter = 0;
            while (rs.next()) {
                String item = rs.getString(1);
                item = item.replace("(", "");
                item = item.replace(")", "");
                item = item.replace("\"", "");
                item = item.replace("{", "");
                item = item.replace("}", "");

                String[] chop = item.split(",");
                String idOcene = chop[0];
                String stevilkaOcene = chop[1];
                String opisOcene = chop[2];
                String ucenecIme = chop[3];
                String predmet = chop[4];
                String uciteljIme = chop[5];
                dataArray[counter][0] = idOcene;
                dataArray[counter][1] = stevilkaOcene;
                dataArray[counter][2] = opisOcene;
                dataArray[counter][3] = ucenecIme;

                dataArray[counter][4] = predmet;
                dataArray[counter][5] = uciteljIme;
                counter++;
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

    public void deleteBook() {
        try {
            Class.forName("org.postgresql.Driver");
            Connection c = DriverManager
                    .getConnection(
                            "jdbc:postgresql://trumpet.db.elephantsql.com:5432/vnkkwcle",
                            "vnkkwcle", "Ha3l6m0s1K4y8Uax3R_AmfynSejagg8H");
            Statement select = c.createStatement();
            String sql = "SELECT ime FROM ucenci LIMIT 1";
            ResultSet rs = select.executeQuery(sql);
            if (rs.next()) {
                System.out.println(rs.getString(1));
            }

        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newButton) {

        } else if (e.getSource() == editButton) {

        } else if (e.getSource() == deleteButton) {
            deleteBook();
        } else if (e.getSource() == izvozButton) {
            try (Connection c = DriverManager
                    .getConnection(
                            "jdbc:postgres://@trumpet.db.elephantsql.com/vnkkwcle",
                            "vnkkwcle", "Ha3l6m0s1K4y8Uax3R_AmfynSejagg8H")) {
                CopyManager copyManager = new CopyManager((BaseConnection) c);
                File file = new File("C:/Users/nikkr/Desktop/temporar/podatki.csv");
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                // and finally execute the COPY command to the file with this method:
                copyManager.copyOut("COPY ("
                        + "SELECT  DISTINCT k.id, k.ime, p.ime, p.priimek, k.stevilo_strani, z.ime, k.datum_objave FROM knjige k INNER JOIN pisatelji p on k.pisatelji_id = p.id INNER JOIN zanri_knjige zk ON k.id = zk.knjiga_id INNER JOIN zanri z ON zk.zanr_id = z.id"
                        + ") TO STDOUT WITH (FORMAT CSV, HEADER)", fileOutputStream);
            } catch (SQLException | IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }

    }
}
