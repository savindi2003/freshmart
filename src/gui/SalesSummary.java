/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gui;

import Model.MySQL;
import com.formdev.flatlaf.intellijthemes.FlatArcOrangeIJTheme;
import static gui.SignIn.log1;
import java.io.InputStream;
import javax.swing.JFrame;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Savindi
 */
public class SalesSummary extends javax.swing.JFrame {

    /**
     * Creates new form UserManagement
     */
    public SalesSummary(String username) {
        initComponents();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        loadTable();
        loadCashier();
        loadSummary();
        jLabel33.setText(username);

    }

    String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

    private void loadSummary() {
        jLabel16.setText(date);

        try {

            ResultSet rs = MySQL.execute("SELECT COUNT(*) AS `invoice_count`\n"
                    + "FROM `invoice`\n"
                    + "WHERE `date_time` LIKE '" + date + "%'");

            if (rs.next()) {
                String invoicecount = rs.getString("invoice_count");
                jLabel13.setText(invoicecount);
            }

            ResultSet rs2 = MySQL.execute("SELECT COUNT(*) AS `product_count` FROM `invoice`\n"
                    + "INNER JOIN `invoice_item` ON `invoice_item`.`invoice_id` = `invoice`.`id`\n"
                    + "WHERE `invoice`.`date_time` LIKE '" + date + "%';");

            if (rs2.next()) {
                String invoicecount = rs2.getString("product_count");
                jLabel31.setText(invoicecount);
            }

            ResultSet rs3 = MySQL.execute("SELECT `invoice`.`user_username`, COUNT(*) AS `invoice_count` , SUM(`invoice`.`total`) AS `total_amount`\n"
                    + "FROM `invoice`\n"
                    + "WHERE `invoice`.`date_time` LIKE '" + date + "%'\n"
                    + "GROUP BY `invoice`.`user_username`");

            DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
            model.setRowCount(0);

            while (rs3.next()) {

                Vector<String> v = new Vector();

                v.add(rs3.getString("user_username"));
                v.add(rs3.getString("invoice_count"));

                v.add("Rs. " + rs3.getString("total_amount") + "0");

                model.addRow(v);
                jTable2.setModel(model);

            }

            ResultSet rs4 = MySQL.execute("SELECT `invoice`.`payment_method_id` , COUNT(*) AS `count` , SUM(`invoice`.`total`) AS `total_amount`\n"
                    + "FROM `invoice`\n"
                    + "WHERE `invoice`.`date_time` LIKE '" + date + "%'\n"
                    + "GROUP BY `invoice`.`payment_method_id`");

            double cashAmount = 0;
            double cardAmount = 0;
            int cashCount = 0;
            int cardCount = 0;

            while (rs4.next()) {

                int paymentMethodId = rs4.getInt("payment_method_id");
                int count = rs4.getInt("count");
                double totalAmount = rs4.getDouble("total_amount");

                if (paymentMethodId == 1 || paymentMethodId == 4) { // Cash payments
                    cashCount += count;
                    cashAmount += totalAmount;
                } else if (paymentMethodId == 2 || paymentMethodId == 5) { // Card payments
                    cardCount += count;
                    cardAmount += totalAmount;
                }

            }

            jLabel20.setText(String.valueOf(cashCount));
            jLabel27.setText(String.valueOf(cashAmount) + "0");
            jLabel24.setText(String.valueOf(cardCount));
            jLabel29.setText(String.valueOf(cardAmount) + "0");

            double totalSales = cashAmount + cardAmount;
            jLabel18.setText("Rs. " + String.valueOf(totalSales) + "0");

        } catch (Exception e) {
            e.printStackTrace();
            log1.warning(e.toString());
            JOptionPane.showMessageDialog(this, "Something Went Wrong..", "Error", JOptionPane.WARNING_MESSAGE);
        }

    }

    public void loadCashier() {
        try {
            ResultSet resultSet = MySQL.execute("SELECT * FROM `user`");

            Vector<String> vector = new Vector<>();
            vector.add("Select");
            while (resultSet.next()) {
                vector.add(resultSet.getString("username"));

            }

            DefaultComboBoxModel model = new DefaultComboBoxModel(vector);
            jComboBox6.setModel(model);
        } catch (Exception e) {
            e.printStackTrace();
            log1.warning(e.toString());
            JOptionPane.showMessageDialog(this, "Something Went Wrong..", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void loadTable() {
        try {

            ResultSet resultSet = MySQL.execute("SELECT * FROM `invoice`\n"
                    + "INNER JOIN `payment_method` ON `payment_method`.`id` = `invoice`.`payment_method_id`\n"
                    + "INNER JOIN `customer` ON `customer`.`id` = `invoice`.`customer_mobile` WHERE `date_time` LIKE '" + date + "%'");

            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);
            while (resultSet.next()) {
                Vector<String> vector = new Vector<>();
                vector.add(resultSet.getString("invoice.id"));
                vector.add(resultSet.getString("invoice.user_username"));

                if (resultSet.getString("customer.mobile").equals("0000000000")) {
                    vector.add("-");
                } else {
                    vector.add(resultSet.getString("customer.mobile"));
                }

                String dateTimeString = resultSet.getString("invoice.date_time");

                SimpleDateFormat fullFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat stime = new SimpleDateFormat("hh:mm:ss");

                Date fullDate = fullFormat.parse(dateTimeString);

                String datePart = sdf.format(fullDate);
                String timePart = stime.format(fullDate);

                vector.add(datePart);
                vector.add(timePart);

                vector.add(resultSet.getString("payment_method.name"));

                DecimalFormat df = new DecimalFormat("0.00");

                double total = Double.parseDouble(resultSet.getString("invoice.total"));
                double discount = Double.parseDouble(resultSet.getString("invoice.discount"));
                double paid = Double.parseDouble(resultSet.getString("invoice.paid_amount"));

                vector.add(String.valueOf(df.format(total)));
                vector.add(String.valueOf(df.format(discount)));
                vector.add(String.valueOf(df.format(paid)));

                model.addRow(vector);
                jTable1.setModel(model);

            }
        } catch (Exception e) {
            e.printStackTrace();
            log1.warning(e.toString());
            JOptionPane.showMessageDialog(this, "Something Went Wrong..", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    private Object[] getColumnData(DefaultTableModel model, int columnIndex) {
        int rowCount = model.getRowCount();
        Object[] columnData = new Object[rowCount];
        for (int i = 0; i < rowCount; i++) {
            columnData[i] = model.getValueAt(i, columnIndex);
        }
        return columnData;
    }

    private void printReport() {

        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date currentDate = new Date();
            String Date = dateFormat.format(currentDate);

            SimpleDateFormat dateFormat1 = new SimpleDateFormat("HH:mm:ss a");
            Date currentTime = new Date();
            String Time = dateFormat1.format(currentTime);

            HashMap<String, Object> parameters = new HashMap<>();
            parameters.put("Parameter1", Date);
            parameters.put("Parameter2", Time);

            parameters.put("Parameter3", jLabel13.getText());
            parameters.put("Parameter4", jLabel31.getText());

            parameters.put("Parameter5", jLabel24.getText());
            parameters.put("Parameter6", "Rs. " + jLabel29.getText());

            parameters.put("Parameter7", jLabel20.getText());
            parameters.put("Parameter8", "Rs. " + jLabel27.getText());

            parameters.put("Parameter9", jLabel18.getText());

            DefaultTableModel tableModel = (DefaultTableModel) jTable2.getModel();
            DefaultTableModel selectedColumnsModel = new DefaultTableModel();

            selectedColumnsModel.addColumn("Column1", getColumnData(tableModel, 0));
            selectedColumnsModel.addColumn("Column2", getColumnData(tableModel, 1));
            selectedColumnsModel.addColumn("Column3", getColumnData(tableModel, 2));

            JRTableModelDataSource dataSource = new JRTableModelDataSource(selectedColumnsModel);
            
            InputStream reportStream = getClass().getResourceAsStream("/reports/TodaySalesReport.jasper");
            JasperViewer.viewReport(JasperFillManager.fillReport(reportStream, parameters, dataSource), false);
            
            

        } catch (Exception e) {
            e.printStackTrace();
            log1.warning(e.toString());
            JOptionPane.showMessageDialog(this, "Something Went Wrong..", "Error", JOptionPane.WARNING_MESSAGE);
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jImagePanel22 = new main.JImagePanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jComboBox7 = new javax.swing.JComboBox<>();
        jComboBox6 = new javax.swing.JComboBox<>();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jButton2 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jImagePanel2 = new main.JImagePanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel32 = new javax.swing.JPanel();
        jImagePanel3 = new main.JImagePanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel33 = new javax.swing.JPanel();
        jImagePanel4 = new main.JImagePanel();
        jLabel6 = new javax.swing.JLabel();
        jPanel34 = new javax.swing.JPanel();
        jImagePanel5 = new main.JImagePanel();
        jLabel7 = new javax.swing.JLabel();
        jPanel35 = new javax.swing.JPanel();
        jImagePanel6 = new main.JImagePanel();
        jLabel8 = new javax.swing.JLabel();
        jPanel36 = new javax.swing.JPanel();
        jImagePanel7 = new main.JImagePanel();
        jLabel9 = new javax.swing.JLabel();
        jPanel37 = new javax.swing.JPanel();
        jImagePanel8 = new main.JImagePanel();
        jLabel10 = new javax.swing.JLabel();
        jPanel38 = new javax.swing.JPanel();
        jImagePanel9 = new main.JImagePanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel53 = new javax.swing.JPanel();
        jImagePanel23 = new main.JImagePanel();
        jLabel34 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jImagePanel10 = new main.JImagePanel();
        jLabel12 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel3.setLayout(new java.awt.BorderLayout());

        jImagePanel22.setCenterImage(true);
        jImagePanel22.setImageIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/close.png"))); // NOI18N
        jImagePanel22.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jImagePanel22MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jImagePanel22Layout = new javax.swing.GroupLayout(jImagePanel22);
        jImagePanel22.setLayout(jImagePanel22Layout);
        jImagePanel22Layout.setHorizontalGroup(
            jImagePanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 31, Short.MAX_VALUE)
        );
        jImagePanel22Layout.setVerticalGroup(
            jImagePanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 34, Short.MAX_VALUE)
        );

        jPanel2.setLayout(new java.awt.GridLayout(2, 5, 10, 10));

        jLabel3.setFont(new java.awt.Font("Quicksand Medium", 1, 12)); // NOI18N
        jLabel3.setText("Invoice Num");
        jPanel2.add(jLabel3);

        jLabel14.setFont(new java.awt.Font("Quicksand Medium", 1, 12)); // NOI18N
        jLabel14.setText("Sort By");
        jPanel2.add(jLabel14);

        jLabel35.setFont(new java.awt.Font("Quicksand Medium", 1, 12)); // NOI18N
        jLabel35.setText("Cashier");
        jPanel2.add(jLabel35);

        jTextField6.setFont(new java.awt.Font("Quicksand Medium", 1, 14)); // NOI18N
        jTextField6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField6ActionPerformed(evt);
            }
        });
        jTextField6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField6KeyReleased(evt);
            }
        });
        jPanel2.add(jTextField6);

        jComboBox7.setFont(new java.awt.Font("Quicksand Medium", 1, 14)); // NOI18N
        jComboBox7.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Total Amount DESC", "Total Amount ASC" }));
        jComboBox7.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox7ItemStateChanged(evt);
            }
        });
        jPanel2.add(jComboBox7);

        jComboBox6.setFont(new java.awt.Font("Quicksand Medium", 1, 14)); // NOI18N
        jComboBox6.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox6ItemStateChanged(evt);
            }
        });
        jPanel2.add(jComboBox6);

        jPanel6.setLayout(new java.awt.GridLayout(2, 5, 10, 10));

        jTable1.setFont(new java.awt.Font("Quicksand Medium", 0, 14)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Invoice Num", "Cashier ", "Customer", "Date", "Time", "Payment method", "Total Amount (Rs)", "Discount (Rs)", "Paid Amount (Rs)"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setShowVerticalLines(true);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jPanel9.setBackground(new java.awt.Color(217, 217, 217));

        jLabel1.setFont(new java.awt.Font("Quicksand Medium", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 102, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Today Summary");

        jLabel13.setFont(new java.awt.Font("Quicksand Medium", 1, 18)); // NOI18N
        jLabel13.setText("122");

        jLabel15.setFont(new java.awt.Font("Quicksand Medium", 0, 18)); // NOI18N
        jLabel15.setText("Invoices count :");

        jLabel16.setFont(new java.awt.Font("Quicksand Medium", 1, 18)); // NOI18N
        jLabel16.setText("2024-12-12");

        jLabel17.setFont(new java.awt.Font("Quicksand Medium", 0, 18)); // NOI18N
        jLabel17.setText("Total Sales (Rs.):");

        jLabel18.setFont(new java.awt.Font("Quicksand Medium", 1, 36)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 102, 51));
        jLabel18.setText("120 000 .00");

        jLabel19.setFont(new java.awt.Font("Quicksand Medium", 2, 14)); // NOI18N
        jLabel19.setText("Cash Payments :");

        jLabel20.setFont(new java.awt.Font("Quicksand Medium", 1, 14)); // NOI18N
        jLabel20.setText("122");

        jLabel23.setFont(new java.awt.Font("Quicksand Medium", 2, 14)); // NOI18N
        jLabel23.setText("Card Payments :");

        jLabel24.setFont(new java.awt.Font("Quicksand Medium", 1, 14)); // NOI18N
        jLabel24.setText("122");

        jLabel26.setFont(new java.awt.Font("Quicksand Medium", 2, 14)); // NOI18N
        jLabel26.setText("Amount (Rs.):");

        jLabel27.setFont(new java.awt.Font("Quicksand Medium", 1, 18)); // NOI18N
        jLabel27.setText("120 000 .00");

        jLabel28.setFont(new java.awt.Font("Quicksand Medium", 2, 14)); // NOI18N
        jLabel28.setText("Amount (Rs.):");

        jLabel29.setFont(new java.awt.Font("Quicksand Medium", 1, 18)); // NOI18N
        jLabel29.setText("120 000 .00");

        jLabel30.setFont(new java.awt.Font("Quicksand Medium", 0, 18)); // NOI18N
        jLabel30.setText("Product count");

        jLabel31.setFont(new java.awt.Font("Quicksand Medium", 1, 18)); // NOI18N
        jLabel31.setText("122");

        jSeparator2.setForeground(new java.awt.Color(255, 102, 51));

        jSeparator3.setForeground(new java.awt.Color(255, 102, 51));

        jButton2.setBackground(new java.awt.Color(255, 102, 51));
        jButton2.setFont(new java.awt.Font("Quicksand Medium", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Prient Report");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jTable2.setFont(new java.awt.Font("Quicksand Medium", 0, 14)); // NOI18N
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Cashier No", "Invoice count", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTable2);
        if (jTable2.getColumnModel().getColumnCount() > 0) {
            jTable2.getColumnModel().getColumn(0).setResizable(false);
        }

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel9Layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel9Layout.createSequentialGroup()
                                .addComponent(jLabel30)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel9Layout.createSequentialGroup()
                                        .addComponent(jLabel19)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel26)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                                        .addGap(25, 25, 25)
                                        .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(30, 30, 30)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel9Layout.createSequentialGroup()
                                        .addComponent(jLabel23)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel28)
                                    .addGroup(jPanel9Layout.createSequentialGroup()
                                        .addGap(25, 25, 25)
                                        .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator3))
                        .addGap(11, 11, 11))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(105, 105, 105))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addContainerGap())))))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel16)
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(jLabel31))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 73, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20)
                            .addComponent(jLabel19))
                        .addGap(7, 7, 7)
                        .addComponent(jLabel26)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel27))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel23)
                            .addComponent(jLabel24))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel28)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel29)))
                .addGap(36, 36, 36)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jButton2)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 928, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jButton3.setText("Clear");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Quicksand Medium", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 102, 0));
        jLabel2.setText("Selling History");

        jButton4.setBackground(new java.awt.Color(255, 102, 51));
        jButton4.setFont(new java.awt.Font("Quicksand Medium", 1, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Loss/Profit");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jImagePanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(159, 159, 159))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 704, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(24, 24, 24)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(1047, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jImagePanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton3)
                        .addComponent(jButton4)))
                .addGap(18, 18, 18)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(16, 16, 16)
                    .addComponent(jLabel2)
                    .addContainerGap(789, Short.MAX_VALUE)))
        );

        jPanel3.add(jPanel1, java.awt.BorderLayout.CENTER);

        jPanel4.setBackground(new java.awt.Color(217, 217, 217));

        jPanel5.setBackground(new java.awt.Color(255, 102, 0));
        jPanel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel5MouseClicked(evt);
            }
        });

        jImagePanel2.setBackground(new java.awt.Color(255, 102, 0));
        jImagePanel2.setFitToPanel(true);
        jImagePanel2.setImageHeight(55);
        jImagePanel2.setImageIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/dashboards.png"))); // NOI18N
        jImagePanel2.setImageWidth(55);

        javax.swing.GroupLayout jImagePanel2Layout = new javax.swing.GroupLayout(jImagePanel2);
        jImagePanel2.setLayout(jImagePanel2Layout);
        jImagePanel2Layout.setHorizontalGroup(
            jImagePanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );
        jImagePanel2Layout.setVerticalGroup(
            jImagePanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 29, Short.MAX_VALUE)
        );

        jLabel4.setFont(new java.awt.Font("Quicksand Medium", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Dashboard");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jImagePanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap(8, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(15, 15, 15))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(jImagePanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        jPanel32.setBackground(new java.awt.Color(255, 102, 0));

        jImagePanel3.setBackground(new java.awt.Color(255, 102, 0));
        jImagePanel3.setFitToPanel(true);
        jImagePanel3.setImageHeight(55);
        jImagePanel3.setImageIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/grn.png"))); // NOI18N
        jImagePanel3.setImageWidth(55);

        javax.swing.GroupLayout jImagePanel3Layout = new javax.swing.GroupLayout(jImagePanel3);
        jImagePanel3.setLayout(jImagePanel3Layout);
        jImagePanel3Layout.setHorizontalGroup(
            jImagePanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );
        jImagePanel3Layout.setVerticalGroup(
            jImagePanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 29, Short.MAX_VALUE)
        );

        jLabel5.setFont(new java.awt.Font("Quicksand Medium", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("GRN");

        javax.swing.GroupLayout jPanel32Layout = new javax.swing.GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jImagePanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel32Layout.setVerticalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addContainerGap(8, Short.MAX_VALUE)
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel32Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(15, 15, 15))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel32Layout.createSequentialGroup()
                        .addComponent(jImagePanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        jPanel33.setBackground(new java.awt.Color(255, 102, 0));

        jImagePanel4.setBackground(new java.awt.Color(255, 102, 0));
        jImagePanel4.setFitToPanel(true);
        jImagePanel4.setImageHeight(55);
        jImagePanel4.setImageIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/scale.png"))); // NOI18N
        jImagePanel4.setImageWidth(55);

        javax.swing.GroupLayout jImagePanel4Layout = new javax.swing.GroupLayout(jImagePanel4);
        jImagePanel4.setLayout(jImagePanel4Layout);
        jImagePanel4Layout.setHorizontalGroup(
            jImagePanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );
        jImagePanel4Layout.setVerticalGroup(
            jImagePanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 29, Short.MAX_VALUE)
        );

        jLabel6.setFont(new java.awt.Font("Quicksand Medium", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Weighing and Labeling");

        javax.swing.GroupLayout jPanel33Layout = new javax.swing.GroupLayout(jPanel33);
        jPanel33.setLayout(jPanel33Layout);
        jPanel33Layout.setHorizontalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jImagePanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel33Layout.setVerticalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel33Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(9, 9, 9))
                    .addComponent(jImagePanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(8, Short.MAX_VALUE))
        );

        jPanel34.setBackground(new java.awt.Color(255, 102, 0));

        jImagePanel5.setBackground(new java.awt.Color(255, 102, 0));
        jImagePanel5.setFitToPanel(true);
        jImagePanel5.setImageHeight(55);
        jImagePanel5.setImageIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/exchange.png"))); // NOI18N
        jImagePanel5.setImageWidth(55);

        javax.swing.GroupLayout jImagePanel5Layout = new javax.swing.GroupLayout(jImagePanel5);
        jImagePanel5.setLayout(jImagePanel5Layout);
        jImagePanel5Layout.setHorizontalGroup(
            jImagePanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );
        jImagePanel5Layout.setVerticalGroup(
            jImagePanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 29, Short.MAX_VALUE)
        );

        jLabel7.setFont(new java.awt.Font("Quicksand Medium", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Damage Product");

        javax.swing.GroupLayout jPanel34Layout = new javax.swing.GroupLayout(jPanel34);
        jPanel34.setLayout(jPanel34Layout);
        jPanel34Layout.setHorizontalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jImagePanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel34Layout.setVerticalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addContainerGap(8, Short.MAX_VALUE)
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel34Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(15, 15, 15))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel34Layout.createSequentialGroup()
                        .addComponent(jImagePanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        jPanel35.setBackground(new java.awt.Color(255, 102, 0));

        jImagePanel6.setBackground(new java.awt.Color(255, 102, 0));
        jImagePanel6.setFitToPanel(true);
        jImagePanel6.setImageHeight(55);
        jImagePanel6.setImageIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/trolley.png"))); // NOI18N
        jImagePanel6.setImageWidth(55);

        javax.swing.GroupLayout jImagePanel6Layout = new javax.swing.GroupLayout(jImagePanel6);
        jImagePanel6.setLayout(jImagePanel6Layout);
        jImagePanel6Layout.setHorizontalGroup(
            jImagePanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );
        jImagePanel6Layout.setVerticalGroup(
            jImagePanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 29, Short.MAX_VALUE)
        );

        jLabel8.setFont(new java.awt.Font("Quicksand Medium", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Product Management");

        javax.swing.GroupLayout jPanel35Layout = new javax.swing.GroupLayout(jPanel35);
        jPanel35.setLayout(jPanel35Layout);
        jPanel35Layout.setHorizontalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jImagePanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel35Layout.setVerticalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addContainerGap(8, Short.MAX_VALUE)
                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel35Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(15, 15, 15))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel35Layout.createSequentialGroup()
                        .addComponent(jImagePanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        jPanel36.setBackground(new java.awt.Color(255, 102, 0));

        jImagePanel7.setBackground(new java.awt.Color(255, 102, 0));
        jImagePanel7.setFitToPanel(true);
        jImagePanel7.setImageHeight(55);
        jImagePanel7.setImageIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/teamwork.png"))); // NOI18N
        jImagePanel7.setImageWidth(55);

        javax.swing.GroupLayout jImagePanel7Layout = new javax.swing.GroupLayout(jImagePanel7);
        jImagePanel7.setLayout(jImagePanel7Layout);
        jImagePanel7Layout.setHorizontalGroup(
            jImagePanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );
        jImagePanel7Layout.setVerticalGroup(
            jImagePanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 29, Short.MAX_VALUE)
        );

        jLabel9.setFont(new java.awt.Font("Quicksand Medium", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Supplier Management");

        javax.swing.GroupLayout jPanel36Layout = new javax.swing.GroupLayout(jPanel36);
        jPanel36.setLayout(jPanel36Layout);
        jPanel36Layout.setHorizontalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel36Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jImagePanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel36Layout.setVerticalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel36Layout.createSequentialGroup()
                .addContainerGap(8, Short.MAX_VALUE)
                .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel36Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(15, 15, 15))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel36Layout.createSequentialGroup()
                        .addComponent(jImagePanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        jPanel37.setBackground(new java.awt.Color(255, 102, 0));

        jImagePanel8.setBackground(new java.awt.Color(255, 102, 0));
        jImagePanel8.setFitToPanel(true);
        jImagePanel8.setImageHeight(55);
        jImagePanel8.setImageIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/employees.png"))); // NOI18N
        jImagePanel8.setImageWidth(55);

        javax.swing.GroupLayout jImagePanel8Layout = new javax.swing.GroupLayout(jImagePanel8);
        jImagePanel8.setLayout(jImagePanel8Layout);
        jImagePanel8Layout.setHorizontalGroup(
            jImagePanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );
        jImagePanel8Layout.setVerticalGroup(
            jImagePanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 29, Short.MAX_VALUE)
        );

        jLabel10.setFont(new java.awt.Font("Quicksand Medium", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Employee Management");

        javax.swing.GroupLayout jPanel37Layout = new javax.swing.GroupLayout(jPanel37);
        jPanel37.setLayout(jPanel37Layout);
        jPanel37Layout.setHorizontalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel37Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jImagePanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel37Layout.setVerticalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel37Layout.createSequentialGroup()
                .addContainerGap(8, Short.MAX_VALUE)
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel37Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(15, 15, 15))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel37Layout.createSequentialGroup()
                        .addComponent(jImagePanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        jPanel38.setBackground(new java.awt.Color(255, 102, 0));

        jImagePanel9.setBackground(new java.awt.Color(255, 102, 0));
        jImagePanel9.setFitToPanel(true);
        jImagePanel9.setImageHeight(55);
        jImagePanel9.setImageIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/target (3).png"))); // NOI18N
        jImagePanel9.setImageWidth(55);

        javax.swing.GroupLayout jImagePanel9Layout = new javax.swing.GroupLayout(jImagePanel9);
        jImagePanel9.setLayout(jImagePanel9Layout);
        jImagePanel9Layout.setHorizontalGroup(
            jImagePanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );
        jImagePanel9Layout.setVerticalGroup(
            jImagePanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 29, Short.MAX_VALUE)
        );

        jLabel11.setFont(new java.awt.Font("Quicksand Medium", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Customer Management");

        javax.swing.GroupLayout jPanel38Layout = new javax.swing.GroupLayout(jPanel38);
        jPanel38.setLayout(jPanel38Layout);
        jPanel38Layout.setHorizontalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel38Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jImagePanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel38Layout.setVerticalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel38Layout.createSequentialGroup()
                .addContainerGap(8, Short.MAX_VALUE)
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel38Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(15, 15, 15))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel38Layout.createSequentialGroup()
                        .addComponent(jImagePanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        jLabel25.setFont(new java.awt.Font("Quicksand Medium", 2, 12)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 102, 0));
        jLabel25.setText("Developed By Celongen Technologies");

        jLabel32.setFont(jLabel32.getFont());
        jLabel32.setForeground(new java.awt.Color(0, 0, 0));
        jLabel32.setText("User");

        jLabel33.setFont(new java.awt.Font("Quicksand Medium", 1, 12)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(0, 0, 0));
        jLabel33.setText("Savindi_CH");

        jPanel53.setBackground(new java.awt.Color(255, 102, 0));

        jImagePanel23.setBackground(new java.awt.Color(255, 102, 0));
        jImagePanel23.setFitToPanel(true);
        jImagePanel23.setImageHeight(55);
        jImagePanel23.setImageIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/report.png"))); // NOI18N
        jImagePanel23.setImageWidth(55);

        javax.swing.GroupLayout jImagePanel23Layout = new javax.swing.GroupLayout(jImagePanel23);
        jImagePanel23.setLayout(jImagePanel23Layout);
        jImagePanel23Layout.setHorizontalGroup(
            jImagePanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );
        jImagePanel23Layout.setVerticalGroup(
            jImagePanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 29, Short.MAX_VALUE)
        );

        jLabel34.setFont(new java.awt.Font("Quicksand Medium", 1, 12)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(255, 255, 255));
        jLabel34.setText("Reports");

        javax.swing.GroupLayout jPanel53Layout = new javax.swing.GroupLayout(jPanel53);
        jPanel53.setLayout(jPanel53Layout);
        jPanel53Layout.setHorizontalGroup(
            jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel53Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jImagePanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel34)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel53Layout.setVerticalGroup(
            jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel53Layout.createSequentialGroup()
                .addContainerGap(8, Short.MAX_VALUE)
                .addGroup(jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel53Layout.createSequentialGroup()
                        .addComponent(jLabel34)
                        .addGap(15, 15, 15))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel53Layout.createSequentialGroup()
                        .addComponent(jImagePanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        jPanel8.setBackground(new java.awt.Color(255, 102, 0));

        jImagePanel10.setBackground(new java.awt.Color(255, 102, 0));
        jImagePanel10.setFitToPanel(true);
        jImagePanel10.setImageHeight(55);
        jImagePanel10.setImageIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/invoice.png"))); // NOI18N
        jImagePanel10.setImageWidth(55);

        javax.swing.GroupLayout jImagePanel10Layout = new javax.swing.GroupLayout(jImagePanel10);
        jImagePanel10.setLayout(jImagePanel10Layout);
        jImagePanel10Layout.setHorizontalGroup(
            jImagePanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );
        jImagePanel10Layout.setVerticalGroup(
            jImagePanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 29, Short.MAX_VALUE)
        );

        jLabel12.setFont(new java.awt.Font("Quicksand Medium", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Invoice");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jImagePanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap(8, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(15, 15, 15))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addComponent(jImagePanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel34, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel37, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel38, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jSeparator1)
                    .addComponent(jPanel53, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel32)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel33)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(93, 93, 93)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel53, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel25)
                .addContainerGap())
        );

        jPanel3.add(jPanel4, java.awt.BorderLayout.LINE_START);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField6ActionPerformed

    private void jImagePanel22MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jImagePanel22MouseClicked
        this.dispose();
    }//GEN-LAST:event_jImagePanel22MouseClicked

    private void jTextField6KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField6KeyReleased
        String invoiceid = jTextField6.getText();

        try {

            ResultSet resultSet = MySQL.execute("SELECT * FROM `invoice`\n"
                    + "INNER JOIN `payment_method` ON `payment_method`.`id` = `invoice`.`payment_method_id`\n"
                    + "INNER JOIN `customer` ON `customer`.`id` = `invoice`.`customer_mobile` WHERE `invoice`.`id` = '" + invoiceid + "' AND `invoice`.`date_time` LIKE '" + date + "%' ");

            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);
            if (resultSet.next()) {
                Vector<String> vector = new Vector<>();
                vector.add(resultSet.getString("invoice.id"));
                vector.add(resultSet.getString("invoice.user_username"));

                if (resultSet.getString("customer.mobile").equals("0000000000")) {
                    vector.add("-");
                } else {
                    vector.add(resultSet.getString("customer.mobile"));
                }

                String dateTimeString = resultSet.getString("invoice.date_time");

                SimpleDateFormat fullFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat stime = new SimpleDateFormat("hh:mm:ss");

                Date fullDate = fullFormat.parse(dateTimeString);

                String datePart = sdf.format(fullDate);
                String timePart = stime.format(fullDate);

                vector.add(datePart);
                vector.add(timePart);

                vector.add(resultSet.getString("payment_method.name"));

                DecimalFormat df = new DecimalFormat("0.00");

                double total = Double.parseDouble(resultSet.getString("invoice.total"));
                double discount = Double.parseDouble(resultSet.getString("invoice.discount"));
                double paid = Double.parseDouble(resultSet.getString("invoice.paid_amount"));

                vector.add(String.valueOf(df.format(total)));
                vector.add(String.valueOf(df.format(discount)));
                vector.add(String.valueOf(df.format(paid)));

                model.addRow(vector);
                jTable1.setModel(model);

            } else {
                loadTable();
            }
        } catch (Exception e) {
            e.printStackTrace();
            log1.warning(e.toString());
            JOptionPane.showMessageDialog(this, "Something Went Wrong..", "Error", JOptionPane.WARNING_MESSAGE);
        }

    }//GEN-LAST:event_jTextField6KeyReleased

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        loadTable();
        jTextField6.setText("");
        jComboBox6.setSelectedIndex(0);
        jComboBox7.setSelectedIndex(0);

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jComboBox7ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox7ItemStateChanged
        String sort = (String) jComboBox7.getSelectedItem();
        //Select,Date DESC,Date ASC,Total Amount DESC,Total Amount ASC

        ResultSet resultSet = null;

        try {

            if (sort.equals("Select")) {
                loadTable();
            } else if (sort.equals("Total Amount DESC")) {
                resultSet = MySQL.execute("SELECT * FROM `invoice`\n"
                        + "INNER JOIN `payment_method` ON `payment_method`.`id` = `invoice`.`payment_method_id` "
                        + "INNER JOIN `customer` ON `customer`.`id` = `invoice`.`customer_mobile` WHERE `invoice`.`date_time` LIKE '" + date + "%'\n"
                        + "ORDER BY `invoice`.`total` DESC");

            } else if (sort.equals("Total Amount ASC")) {
                resultSet = MySQL.execute("SELECT * FROM `invoice`\n"
                        + "INNER JOIN `payment_method` ON `payment_method`.`id` = `invoice`.`payment_method_id` "
                        + "INNER JOIN `customer` ON `customer`.`id` = `invoice`.`customer_mobile` WHERE `invoice`.`date_time` LIKE '" + date + "%'\n"
                        + "ORDER BY `invoice`.`total` ASC");

            }

            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);
            while (resultSet.next()) {
                Vector<String> vector = new Vector<>();
                vector.add(resultSet.getString("invoice.id"));
                vector.add(resultSet.getString("invoice.user_username"));

                if (resultSet.getString("customer.mobile").equals("0000000000")) {
                    vector.add("-");
                } else {
                    vector.add(resultSet.getString("customer.mobile"));
                }

                String dateTimeString = resultSet.getString("invoice.date_time");

                SimpleDateFormat fullFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat stime = new SimpleDateFormat("hh:mm:ss");

                Date fullDate = fullFormat.parse(dateTimeString);

                String datePart = sdf.format(fullDate);
                String timePart = stime.format(fullDate);

                vector.add(datePart);
                vector.add(timePart);

                vector.add(resultSet.getString("payment_method.name"));

                DecimalFormat df = new DecimalFormat("0.00");

                double total = Double.parseDouble(resultSet.getString("invoice.total"));
                double discount = Double.parseDouble(resultSet.getString("invoice.discount"));
                double paid = Double.parseDouble(resultSet.getString("invoice.paid_amount"));

                vector.add(String.valueOf(df.format(total)));
                vector.add(String.valueOf(df.format(discount)));
                vector.add(String.valueOf(df.format(paid)));

                model.addRow(vector);
                jTable1.setModel(model);
            }

        } catch (Exception e) {
            e.printStackTrace();
            log1.warning(e.toString());
            JOptionPane.showMessageDialog(this, "Something Went Wrong..", "Error", JOptionPane.WARNING_MESSAGE);
        }


    }//GEN-LAST:event_jComboBox7ItemStateChanged

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        int selectedrow = jTable1.getSelectedRow();

        String invoice_id = String.valueOf(jTable1.getValueAt(selectedrow, 0));
        String cashier = String.valueOf(jTable1.getValueAt(selectedrow, 1));
        String customer = String.valueOf(jTable1.getValueAt(selectedrow, 2));
        String date = String.valueOf(jTable1.getValueAt(selectedrow, 3));
        String time = String.valueOf(jTable1.getValueAt(selectedrow, 4));
        String total = String.valueOf(jTable1.getValueAt(selectedrow, 6));
        String discount = String.valueOf(jTable1.getValueAt(selectedrow, 7));
        String paid = String.valueOf(jTable1.getValueAt(selectedrow, 8));

        if (evt.getClickCount() == 2) {
            InvoiceItem ic = new InvoiceItem(invoice_id, cashier, customer, date, time, total, discount, paid);
            ic.setVisible(true);
        }

    }//GEN-LAST:event_jTable1MouseClicked

    private void jComboBox6ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox6ItemStateChanged
        String username = (String) jComboBox6.getSelectedItem();

        if (username.equals("Select")) {
            loadTable();
        } else {
            try {

                ResultSet resultSet = MySQL.execute("SELECT * FROM `invoice`\n"
                        + "INNER JOIN `payment_method` ON `payment_method`.`id` = `invoice`.`payment_method_id`\n"
                        + "INNER JOIN `customer` ON `customer`.`id` = `invoice`.`customer_mobile` WHERE `user_username` = '" + username + "' AND `invoice`.`date_time` LIKE '" + date + "%'");

                DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                model.setRowCount(0);
                while (resultSet.next()) {
                    Vector<String> vector = new Vector<>();
                    vector.add(resultSet.getString("invoice.id"));
                    vector.add(resultSet.getString("invoice.user_username"));

                    if (resultSet.getString("customer.mobile").equals("0000000000")) {
                        vector.add("-");
                    } else {
                        vector.add(resultSet.getString("customer.mobile"));
                    }

                    String dateTimeString = resultSet.getString("invoice.date_time");

                    SimpleDateFormat fullFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat stime = new SimpleDateFormat("hh:mm:ss");

                    Date fullDate = fullFormat.parse(dateTimeString);

                    String datePart = sdf.format(fullDate);
                    String timePart = stime.format(fullDate);

                    vector.add(datePart);
                    vector.add(timePart);

                    vector.add(resultSet.getString("payment_method.name"));

                    DecimalFormat df = new DecimalFormat("0.00");

                    double total = Double.parseDouble(resultSet.getString("invoice.total"));
                    double discount = Double.parseDouble(resultSet.getString("invoice.discount"));
                    double paid = Double.parseDouble(resultSet.getString("invoice.paid_amount"));

                    vector.add(String.valueOf(df.format(total)));
                    vector.add(String.valueOf(df.format(discount)));
                    vector.add(String.valueOf(df.format(paid)));

                    model.addRow(vector);
                    jTable1.setModel(model);

                }
            } catch (Exception e) {
                e.printStackTrace();
                log1.warning(e.toString());
                JOptionPane.showMessageDialog(this, "Something Went Wrong..", "Error", JOptionPane.WARNING_MESSAGE);
            }
        }
    }//GEN-LAST:event_jComboBox6ItemStateChanged

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        String username = jLabel33.getText();
        Income in = new Income(username);
        in.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        printReport();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jPanel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel5MouseClicked
        String username = jLabel33.getText();

        try {
            ResultSet rs = MySQL.execute("SELECT * FROM `user` WHERE `username` ='" + username + "' ");

            if (rs.next()) {

                String userName = rs.getString("username");
                String userTypeId = rs.getString("user_type_id");

                if (userTypeId.equals("1")) {

                    Dashboard2 dh2 = new Dashboard2(userName);
                    dh2.setVisible(true);
                    this.dispose();

                } else {

                    Dashboard dh = new Dashboard(userName);
                    dh.setVisible(true);
                    this.dispose();

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            log1.warning(e.toString());
            JOptionPane.showMessageDialog(this, "Something Went Wrong..", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jPanel5MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        FlatArcOrangeIJTheme.setup();
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox<String> jComboBox6;
    private javax.swing.JComboBox<String> jComboBox7;
    private main.JImagePanel jImagePanel10;
    private main.JImagePanel jImagePanel2;
    private main.JImagePanel jImagePanel22;
    private main.JImagePanel jImagePanel23;
    private main.JImagePanel jImagePanel3;
    private main.JImagePanel jImagePanel4;
    private main.JImagePanel jImagePanel5;
    private main.JImagePanel jImagePanel6;
    private main.JImagePanel jImagePanel7;
    private main.JImagePanel jImagePanel8;
    private main.JImagePanel jImagePanel9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel53;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField6;
    // End of variables declaration//GEN-END:variables
}
