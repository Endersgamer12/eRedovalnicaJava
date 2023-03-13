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
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

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

    JLabel logoLabel;

    String meil;

    ArrayList<String> arRazredi = new ArrayList<String>(); // Create an ArrayList object
    ArrayList<String> arPredmeti = new ArrayList<String>(); // Create an ArrayList object

    ArrayList<ArrayList<String>> oceneAr = new ArrayList<ArrayList<String>>();
    DefaultTableModel tableModel;

    JComboBox razred;
    JComboBox predmet;

    JButton odjavaButton;

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
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 10, 15);
        razred.setBackground(new Color(48, 56, 71));
        razred.setForeground(new Color(255, 255, 255));
        panel.add(razred, gbc);

        dobiPredmete(gmail);
        String[] predmetiTempAr = new String[arPredmeti.size()];
        predmetiTempAr = arPredmeti.toArray(predmetiTempAr);
        predmet = new JComboBox(predmetiTempAr);
        predmet.setSelectedIndex(0);
        predmet.addActionListener(this);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 10, 15);
        predmet.setBackground(new Color(48, 56, 71));
        predmet.setForeground(new Color(255, 255, 255));
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
        booksTable.setBackground(new Color(48, 56, 71));
        booksTable.setForeground(new Color(255, 255, 255));
        booksTable.setFont(new Font("Arial", Font.PLAIN, 15));
        booksTable.setShowVerticalLines(false);
        // booksTable.getColumnModel().setColumnMargin(20);
        booksTable.getTableHeader().setFont(new Font("Arial", Font.ITALIC, 16));
        booksTable.getTableHeader().setBackground(new Color(48, 56, 71));
        booksTable.getTableHeader().setForeground(new Color(255, 255, 255));
        booksTable.setRowHeight(30);
        booksTable.getSelectionModel().addListSelectionListener(this);

        TableColumn col0 = booksTable.getColumnModel().getColumn(0);
        TableColumn col1 = booksTable.getColumnModel().getColumn(1);
        TableColumn col2 = booksTable.getColumnModel().getColumn(2);
        TableColumn col3 = booksTable.getColumnModel().getColumn(3);
        DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
        dtcr.setHorizontalAlignment(SwingConstants.CENTER);
        col0.setCellRenderer(dtcr);
        col1.setCellRenderer(dtcr);
        col2.setCellRenderer(dtcr);
        col3.setCellRenderer(dtcr);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 5;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        scrollPane = new JScrollPane(booksTable);
        scrollPane.setPreferredSize(new Dimension(800, 400));
        scrollPane.getViewport().setBackground(new Color(33, 42, 53));
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(33, 42, 53);
            }
        });
        scrollPane.getVerticalScrollBar().setBackground(new Color(50, 59, 70));
        scrollPane.setBackground(new Color(48, 56, 71));
        scrollPane.setVisible(true);

        panel.add(scrollPane, gbc);

        odjavaButton = new RoundedJButton("Odjava");
        odjavaButton.setPreferredSize(new Dimension(100, 30));
        odjavaButton.addActionListener(this);
        odjavaButton.setBorder(null);
        odjavaButton.setBackground(new Color(41, 53, 66));
        odjavaButton.setForeground(new Color(255, 255, 255));

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(15, 5, 5, 5);

        panel.add(odjavaButton, gbc);

        logoLabel = new JLabel("eRedovalnica");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 0, 10, 0);
        logoLabel.setFont(new Font("Arial", Font.BOLD, 15));
        logoLabel.setForeground(new Color(255, 255, 255));
        panel.add(logoLabel, gbc);

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
        if (e.getSource() == odjavaButton) {
            try {
                LoginPage lPage = new LoginPage();
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            frame.dispose();
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {

        int row = booksTable.getSelectedRow();
        Integer id = Integer.parseInt(booksTable.getModel().getValueAt(row, 0).toString());
        String dime = booksTable.getModel().getValueAt(row, 1).toString();
        String dpriimek = booksTable.getModel().getValueAt(row, 2).toString();

        uciteljSpremeniOcene uSpremeniOcene = new uciteljSpremeniOcene(meil, id, predmet.getSelectedItem().toString(),
                dime, dpriimek);
        frame.dispose();
    }

}
