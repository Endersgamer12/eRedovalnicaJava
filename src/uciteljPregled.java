import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

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
import java.util.function.ToIntFunction;
import java.applet.Applet;
import org.postgresql.core.BaseConnection;
import org.postgresql.copy.CopyManager;
import java.util.ArrayList; // import the ArrayList class

public class uciteljPregled implements ActionListener, ListSelectionListener {

    JPanel panel;
    JFrame frame;

    String meil;

    ArrayList<String> arRazredi = new ArrayList<String>(); // Create an ArrayList object
    ArrayList<String> arPredmeti = new ArrayList<String>(); // Create an ArrayList object

    ArrayList<ArrayList<String>> oceneAr = new ArrayList<ArrayList<String>>();
    DefaultTableModel tableModel;

    JComboBox razred;
    JComboBox predmet;

    JTable booksTable;
    JScrollPane scrollPane;

    GridBagConstraints gbc = new GridBagConstraints();

    public uciteljPregled(String gmail) {

        meil = gmail;

        panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        frame = new JFrame();
        frame.setSize(1000, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.setBackground(new Color(33, 42, 53));
        frame.setTitle("Ucitelj");

        dobiRazrede(gmail);
        String[] razrediTempAr = new String[arRazredi.size()];
        razrediTempAr = arRazredi.toArray(razrediTempAr);
        razred = new JComboBox(razrediTempAr);
        razred.setSelectedIndex(0);
        razred.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 0, 10, 15);
        panel.add(razred, gbc);

        dobiPredmete(gmail);
        String[] predmetiTempAr = new String[arPredmeti.size()];
        predmetiTempAr = arPredmeti.toArray(predmetiTempAr);
        predmet = new JComboBox(predmetiTempAr);
        predmet.setSelectedIndex(0);
        predmet.addActionListener(this);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 0, 10, 15);
        panel.add(predmet, gbc);

        getGradesData(gmail);

        String vrste[] = { "ID", "Ime", "Priimek", "Ocene" };
        tableModel = new DefaultTableModel(vrste, 0);

        for (int i = 0; i < oceneAr.size(); i++) {
            String idUcenca = oceneAr.get(i).get(0);
            String imeUcenca = oceneAr.get(i).get(1);
            String priimekUcenca = oceneAr.get(i).get(2);
            String oceneUcenca = oceneAr.get(i).get(3);

            String[] data = { idUcenca, imeUcenca, priimekUcenca, oceneUcenca };

            tableModel.addRow(data);
        }

        booksTable = new JTable(tableModel);
        booksTable.getTableHeader().setOpaque(false);
        booksTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 18));
        booksTable.getTableHeader().setForeground(new Color(255, 255, 255));
        booksTable.getTableHeader().setBackground(new Color(51, 63, 76));
        booksTable.getTableHeader().setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255), 1));
        booksTable.setDefaultEditor(Object.class, null);
        booksTable.getColumnModel().getColumn(0).setPreferredWidth(1);
        booksTable.setFont(new Font("Arial", Font.PLAIN, 14));
        booksTable.setBackground(new Color(51, 63, 76));
        booksTable.setForeground(new Color(255, 255, 255));
        booksTable.setBorder(BorderFactory.createEmptyBorder());
        booksTable.getSelectionModel().addListSelectionListener(this);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 5;

        gbc.fill = GridBagConstraints.HORIZONTAL;
        scrollPane = new JScrollPane(booksTable);
        scrollPane.setPreferredSize(new Dimension(600, 400));
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255), 2));
        scrollPane.setFont(new Font("Arial", Font.PLAIN, 14));
        scrollPane.setForeground(new Color(255, 255, 255));
        scrollPane.setVisible(true);
        panel.add(scrollPane, gbc);

        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) throws Exception {

        new uciteljPregled("vlasta.leban@scv.si");
    }

    public void getGradesData(String gmail) {

        Connection c = null;

        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection(
                            "jdbc:postgresql://trumpet.db.elephantsql.com:5432/vnkkwcle",
                            "vnkkwcle", "Ha3l6m0s1K4y8Uax3R_AmfynSejagg8H");
            Statement select = c.createStatement();

            String sql = "SELECT uciteljPregledOcene('" + gmail + "', '" + razred.getSelectedItem().toString() + "', '"
                    + predmet.getSelectedItem().toString() + "')";
            ResultSet rs = select.executeQuery(sql);
            Integer counter = 0;
            oceneAr.clear();
            while (rs.next()) {
                String item = rs.getString(1);
                item = item.replace("(", "");
                item = item.replace(")", "");
                item = item.replace("\"", "");
                String ocene = item.substring(item.indexOf("{") + 1, item.indexOf("}"));
                item = item.replace("{", "");
                item = item.replace("}", "");

                String[] chop = item.split(",");
                oceneAr.add(new ArrayList<String>());
                oceneAr.get(counter).add(0, chop[0]);
                oceneAr.get(counter).add(1, chop[1]);
                oceneAr.get(counter).add(2, chop[2]);
                oceneAr.get(counter).add(3, ocene);
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

    public void dobiRazrede(String gmail) {

        Connection c = null;

        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection(
                            "jdbc:postgresql://trumpet.db.elephantsql.com:5432/vnkkwcle",
                            "vnkkwcle", "Ha3l6m0s1K4y8Uax3R_AmfynSejagg8H");
            Statement select = c.createStatement();

            String sql = "SELECT uciteljRazredi('" + gmail + "')";
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

    public void dobiPredmete(String gmail) {

        Connection c = null;

        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection(
                            "jdbc:postgresql://trumpet.db.elephantsql.com:5432/vnkkwcle",
                            "vnkkwcle", "Ha3l6m0s1K4y8Uax3R_AmfynSejagg8H");
            Statement select = c.createStatement();

            String sql = "SELECT uciteljPredmeti('" + gmail + "')";
            ResultSet rs = select.executeQuery(sql);
            while (rs.next()) {
                String item = rs.getString(1);
                item = item.replace("(", "");
                item = item.replace(")", "");
                item = item.replace("\"", "");
                item = item.replace("{", "");
                item = item.replace("}", "");

                arPredmeti.add(item);
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

    public void refreshTable() {
        tableModel.setRowCount(0);
        for (int i = 0; i < oceneAr.size(); i++) {
            String idUcenca = oceneAr.get(i).get(0);
            String imeUcenca = oceneAr.get(i).get(1);
            String priimekUcenca = oceneAr.get(i).get(2);
            String oceneUcenca = oceneAr.get(i).get(3);

            String[] data = { idUcenca, imeUcenca, priimekUcenca, oceneUcenca };

            tableModel.addRow(data);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == razred || e.getSource() == predmet) {
            getGradesData(meil);
            refreshTable();
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        int row = booksTable.getSelectedRow();
        Integer id = Integer.parseInt(booksTable.getModel().getValueAt(row, 0).toString());

        uciteljSpremeniOcene uSpremeniOcene = new uciteljSpremeniOcene(meil, id, predmet.getSelectedItem().toString());
        frame.dispose();
    }

}
