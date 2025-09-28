/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gui;

import Model.InvoiceItemM;
import Model.MySQL;
import com.formdev.flatlaf.intellijthemes.FlatArcOrangeIJTheme;
import static gui.SignIn.log1;
import java.io.InputStream;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.ButtonModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Savindi
 */
public class Invoice_1_1 extends javax.swing.JFrame {

    HashMap<String, InvoiceItemM> invoiceItemMap = new HashMap<>();

    public Invoice_1_1(String username) {
        initComponents();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        jLabel33.setText(username);
        genaratedNumber();

    }

    public JLabel getCustomerName() {
        return jLabel3;
    }

    public JLabel getCustomerMobile() {
        return jLabel12;
    }

    public JLabel getCustomerPoints() {
        return jLabel14;
    }

    public JTextField getTextFieldMo() {
        return jTextField2;
    }

    private void claerAll() {
        jLabel16.setText("0.00");
        jLabel30.setText("0.00");
        jTextField3.setText("0.00");
        jTextField4.setText("0.00");
        jTextField2.setText("");
        jLabel22.setText("0.00");
        clear();
        jTextField2.setText("");
        jLabel3.setText("Customer Name");
        jLabel12.setText("0XX XXXXXXX");
        jLabel14.setText("0");
        DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
        dtm.setRowCount(0);
        genaratedNumber();
    }

    private void calculatetotal() {
        double total = 0;
        for (int i = 0; i < jTable1.getRowCount(); i++) {
            String fees = String.valueOf(jTable1.getValueAt(i, 5));
            double rowTotal = Double.parseDouble(fees);
            total = total + rowTotal;

        }
        DecimalFormat df = new DecimalFormat("0.00");
        jLabel16.setText(String.valueOf(df.format(total)));
        jLabel30.setText(String.valueOf(df.format(total)));
    }

    private void clear() {
        jTextField1.setText("");
        jTextField5.setText("1");
        DefaultTableModel dtm = (DefaultTableModel) jTable2.getModel();
        dtm.setRowCount(0);
    }

    private void loadInvoiceItems() {

        DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
        dtm.setRowCount(0);

        double total = 0;

        for (InvoiceItemM inItem : invoiceItemMap.values()) {
            Vector<String> v = new Vector<>();

            int quentity = (int) inItem.getQty();

            v.add(inItem.getStockId());
            v.add(inItem.getProductName());
            v.add(String.valueOf(quentity));
            v.add(inItem.getUnit());

            double price = inItem.getSellingprice();

            DecimalFormat df = new DecimalFormat("0.00");
            String formated_price = df.format(price);

            v.add(formated_price);

            if (inItem.getUnit().equals("no unit")) {
                double itemtotal = inItem.getQty() * inItem.getSellingprice();
                total += itemtotal;
                v.add(String.valueOf(df.format(itemtotal)));
            } else if (inItem.getUnit().equals("Kg")) {
                double itemtotal = inItem.getQty() * inItem.getSellingprice();
                total += itemtotal;
                v.add(String.valueOf(df.format(itemtotal)));
            } else if (inItem.getUnit().equals("g")) {
                double itemtotal = inItem.getQty() * inItem.getSellingprice() / 1000;
                total += itemtotal;
                v.add(String.valueOf(df.format(itemtotal)));
            }

            System.out.println(inItem.getQty());
            System.out.println(quentity);

            dtm.addRow(v);
        }
        calculatetotal();

    }

    private void genaratedNumber() {

        long id = System.currentTimeMillis();
        jTextField6.setText(String.valueOf(id));

    }

    public JTextField getStockId() {
        return jTextField1;
    }

    public void loadProduct() {

        String stockid = jTextField1.getText();

        if (stockid.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a product", "Error", JOptionPane.WARNING_MESSAGE);

        } else {
            try {
                ResultSet resultSet = MySQL.execute("SELECT * FROM `stock`\n"
                        + "INNER JOIN `product` ON `product`.`id` = `stock`.`product_id` \n"
                        + "WHERE `stock`.`id` = '" + stockid + "'");

                DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
                model.setRowCount(0);
                while (resultSet.next()) {
                    Vector<String> vector = new Vector<>();

                    vector.add(resultSet.getString("product.name"));
                    vector.add(resultSet.getString("stock.exp"));
                    vector.add(resultSet.getString("stock.mfd"));

                    vector.add(resultSet.getString("stock.qty"));
                    vector.add("");
                    vector.add(resultSet.getString("stock.selling_price"));

                    model.addRow(vector);
                    jTable2.setModel(model);

                }
            } catch (Exception e) {
                e.printStackTrace();
                log1.warning(e.toString());
                JOptionPane.showMessageDialog(this, "Something Went Wrong..", "Error", JOptionPane.WARNING_MESSAGE);
            }
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

    public void printInvoice() {
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

            parameters.put("Parameter3", jLabel33.getText());
            parameters.put("Parameter4", jTextField6.getText());

            parameters.put("Parameter5", jLabel30.getText());
            parameters.put("Parameter6", jTextField3.getText());
            parameters.put("Parameter7", jLabel16.getText());
            parameters.put("Parameter8", jTextField4.getText());
            parameters.put("Parameter9", jLabel22.getText());

            DefaultTableModel tableModel = (DefaultTableModel) jTable1.getModel();
            DefaultTableModel selectedColumnsModel = new DefaultTableModel();

            selectedColumnsModel.addColumn("Column1", getColumnData(tableModel, 1));
            selectedColumnsModel.addColumn("Column2", getColumnData(tableModel, 4));
            selectedColumnsModel.addColumn("Column3", getColumnData(tableModel, 2));

            selectedColumnsModel.addColumn("Column4", getColumnData(tableModel, 3));
            selectedColumnsModel.addColumn("Column5", getColumnData(tableModel, 5));

            JRTableModelDataSource dataSource = new JRTableModelDataSource(selectedColumnsModel);
            InputStream reportStream = getClass().getResourceAsStream("/reports/FreshmartCusInvoice.jasper");
            JasperViewer.viewReport(JasperFillManager.fillReport(reportStream, parameters, dataSource), false);

        } catch (Exception e) {
            log1.warning(e.toString());
            e.printStackTrace();
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel3 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jImagePanel22 = new main.JImagePanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jLabel24 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        jTextField5 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel27 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
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
        jPanel7 = new javax.swing.JPanel();
        jImagePanel10 = new main.JImagePanel();
        jLabel23 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel8.setBackground(new java.awt.Color(217, 217, 217));

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
            .addGap(0, 26, Short.MAX_VALUE)
        );
        jImagePanel22Layout.setVerticalGroup(
            jImagePanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 26, Short.MAX_VALUE)
        );

        jLabel15.setFont(jLabel15.getFont().deriveFont((jLabel15.getFont().getStyle() | java.awt.Font.ITALIC) | java.awt.Font.BOLD, jLabel15.getFont().getSize()+2));
        jLabel15.setForeground(new java.awt.Color(255, 102, 0));
        jLabel15.setText("Payble Amount");

        jLabel16.setFont(new java.awt.Font("Quicksand Medium", 1, 36)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel16.setText("00.00");

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setFont(new java.awt.Font("Quicksand Medium", 0, 14)); // NOI18N
        jRadioButton1.setText("Cash");
        jRadioButton1.setActionCommand("1");

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setFont(new java.awt.Font("Quicksand Medium", 0, 14)); // NOI18N
        jRadioButton2.setText("Card");
        jRadioButton2.setActionCommand("2");
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Quicksand Medium", 1, 14)); // NOI18N
        jLabel17.setText("Payment Method");

        jLabel18.setFont(new java.awt.Font("Quicksand Medium", 0, 24)); // NOI18N
        jLabel18.setText("Rs.");

        jLabel19.setFont(new java.awt.Font("Quicksand Medium", 1, 14)); // NOI18N
        jLabel19.setText("Discount");

        jTextField3.setFont(new java.awt.Font("Quicksand Medium", 0, 24)); // NOI18N
        jTextField3.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField3.setText("0.00");
        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });
        jTextField3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField3KeyReleased(evt);
            }
        });

        jTextField4.setFont(new java.awt.Font("Quicksand Medium", 0, 24)); // NOI18N
        jTextField4.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField4.setText(".00");
        jTextField4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField4KeyReleased(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Quicksand Medium", 1, 14)); // NOI18N
        jLabel20.setText("Paid");

        jCheckBox1.setFont(new java.awt.Font("Quicksand Medium", 0, 14)); // NOI18N
        jCheckBox1.setText("Withdraw Points");
        jCheckBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCheckBox1ItemStateChanged(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Quicksand Medium", 1, 14)); // NOI18N
        jLabel21.setText("Balance");

        jLabel22.setFont(new java.awt.Font("Quicksand Medium", 1, 24)); // NOI18N
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel22.setText("00.00");

        jButton6.setBackground(new java.awt.Color(255, 102, 0));
        jButton6.setFont(new java.awt.Font("Quicksand Medium", 1, 18)); // NOI18N
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setText("Print Invoice");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jSeparator2.setForeground(new java.awt.Color(255, 102, 0));

        jSeparator3.setForeground(new java.awt.Color(255, 102, 0));

        jButton8.setFont(new java.awt.Font("Quicksand Medium", 1, 12)); // NOI18N
        jButton8.setText("Clear Invoice");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton9.setBackground(new java.awt.Color(255, 102, 0));
        jButton9.setFont(new java.awt.Font("Quicksand Medium", 1, 12)); // NOI18N
        jButton9.setForeground(new java.awt.Color(255, 255, 255));
        jButton9.setText("Invoice History");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jLabel28.setFont(jLabel28.getFont().deriveFont((jLabel28.getFont().getStyle() | java.awt.Font.ITALIC) | java.awt.Font.BOLD, jLabel28.getFont().getSize()+2));
        jLabel28.setForeground(new java.awt.Color(255, 102, 0));
        jLabel28.setText("Total");

        jLabel29.setFont(new java.awt.Font("Quicksand Medium", 0, 24)); // NOI18N
        jLabel29.setText("Rs.");

        jLabel30.setFont(new java.awt.Font("Quicksand Medium", 1, 36)); // NOI18N
        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel30.setText("00.00");

        jSeparator4.setForeground(new java.awt.Color(255, 102, 0));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jImagePanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel8Layout.createSequentialGroup()
                                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel8Layout.createSequentialGroup()
                                        .addComponent(jRadioButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jRadioButton2)))
                                .addGap(16, 16, 16))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel8Layout.createSequentialGroup()
                                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, 72, Short.MAX_VALUE)
                                            .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextField3)
                                            .addComponent(jTextField4)))
                                    .addComponent(jCheckBox1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(15, 15, 15))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jButton8)
                                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(jPanel8Layout.createSequentialGroup()
                                            .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jButton6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel8Layout.createSequentialGroup()
                                            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGap(18, 18, 18)
                                            .addComponent(jLabel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addComponent(jSeparator2)))
                                .addGap(0, 16, Short.MAX_VALUE)))))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(189, 189, 189))
            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel8Layout.createSequentialGroup()
                    .addGap(31, 31, 31)
                    .addComponent(jSeparator4)
                    .addGap(12, 12, 12)))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jImagePanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(jLabel28)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(jLabel29))
                .addGap(9, 9, 9)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jLabel18))
                .addGap(40, 40, 40)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2))
                .addGap(36, 36, 36)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20))
                .addGap(18, 18, 18)
                .addComponent(jCheckBox1)
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(jLabel22))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton8)
                .addGap(22, 22, 22))
            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel8Layout.createSequentialGroup()
                    .addGap(252, 252, 252)
                    .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(406, Short.MAX_VALUE)))
        );

        jLabel1.setText("Stock");

        jTextField1.setFont(new java.awt.Font("Quicksand Medium", 1, 18)); // NOI18N
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });

        jTable1.setFont(new java.awt.Font("Quicksand Medium", 1, 14)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Stock Id", "Product", "Qty", "Unit", "Price", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(3).setResizable(false);
            jTable1.getColumnModel().getColumn(5).setResizable(false);
        }

        jLabel2.setFont(new java.awt.Font("Quicksand Medium", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 102, 0));
        jLabel2.setText("Customer");

        jTextField2.setFont(new java.awt.Font("Quicksand Medium", 0, 14)); // NOI18N
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });
        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField2KeyReleased(evt);
            }
        });

        jTable2.setFont(new java.awt.Font("Quicksand Medium", 1, 14)); // NOI18N
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Product ", "EXP", "MFD", "Available Qty", "Unit", "Price"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTable2);
        if (jTable2.getColumnModel().getColumnCount() > 0) {
            jTable2.getColumnModel().getColumn(0).setResizable(false);
            jTable2.getColumnModel().getColumn(1).setResizable(false);
            jTable2.getColumnModel().getColumn(2).setResizable(false);
            jTable2.getColumnModel().getColumn(3).setResizable(false);
            jTable2.getColumnModel().getColumn(4).setResizable(false);
            jTable2.getColumnModel().getColumn(5).setResizable(false);
        }

        jButton3.setText("...");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Quicksand Medium", 1, 12)); // NOI18N
        jLabel13.setText("Points");

        jLabel14.setFont(new java.awt.Font("Quicksand Medium", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 102, 0));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("0");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 14, Short.MAX_VALUE)
        );

        jLabel3.setFont(new java.awt.Font("Quicksand Medium", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 102, 0));
        jLabel3.setText(".......");

        jLabel12.setFont(new java.awt.Font("Quicksand Medium", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 102, 0));
        jLabel12.setText("0XX XXXXXXX");

        jButton4.setBackground(new java.awt.Color(255, 102, 0));
        jButton4.setFont(new java.awt.Font("Quicksand Medium", 1, 12)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Add");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("Clear");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton7.setText("...");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jLabel24.setFont(new java.awt.Font("Quicksand Medium", 1, 36)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 102, 0));
        jLabel24.setText("Invoice");

        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel26.setText("Invoice no");

        jTextField6.setFont(new java.awt.Font("Quicksand Medium", 1, 18)); // NOI18N

        jTextField5.setFont(new java.awt.Font("Quicksand Medium", 1, 18)); // NOI18N
        jTextField5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField5.setText("1");
        jTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField5ActionPerformed(evt);
            }
        });

        jButton1.setText("+");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel27.setText("Qty");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel27)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap(17, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27)))
        );

        jButton2.setText("Clear");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 360, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jButton3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton2)
                                        .addGap(0, 0, Short.MAX_VALUE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(27, 27, 27))))
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton7))
                            .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 49, Short.MAX_VALUE)
                                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(57, 57, 57)
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton7)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 308, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(4, 4, 4)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3)
                    .addComponent(jButton2))
                .addGap(5, 5, 5)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jLabel3)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        jPanel3.add(jPanel6, java.awt.BorderLayout.CENTER);

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

        jPanel7.setBackground(new java.awt.Color(255, 102, 0));

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

        jLabel23.setFont(new java.awt.Font("Quicksand Medium", 1, 12)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("Invoice");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jImagePanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel23)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap(8, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel23)
                        .addGap(15, 15, 15))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
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
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void jImagePanel22MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jImagePanel22MouseClicked
        this.dispose();
    }//GEN-LAST:event_jImagePanel22MouseClicked

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

        if (jTable2.getRowCount() > 0) {
            int row = 0; // Index of the first row
            Object value = jTable2.getValueAt(row, 4);

            Object tableQty = jTable2.getValueAt(row, 3);
            String tq = String.valueOf(tableQty);

            String stockId = jTextField1.getText();
            String stockIdNew = null;

            Object pn = jTable2.getValueAt(row, 0);
            String productName = String.valueOf(pn);

            //double qty = 0;
            int intQty = 0;
            String unit = "";

            Object u = jTable2.getValueAt(row, 4);

            Object p = jTable2.getValueAt(row, 5);
            String price = String.valueOf(p);

            double price1 = Double.parseDouble(price);

            try {

                ResultSet resultSet = MySQL.execute("SELECT * FROM `stock` WHERE `stock`.`id` = '" + stockId + "'");
                if (resultSet.next()) {
                    String sqty = jTextField5.getText();
                    intQty = Integer.parseInt(sqty);
                    stockIdNew = jTextField1.getText();
//            qty = Double.parseDouble(sqty);
                    unit = "no unit";
                } else {
                    ResultSet resultSet2 = MySQL.execute("SELECT * FROM `weight` WHERE `weight`.`id` = '" + stockId + "'");
                    if (resultSet2.next()) {
                        String wqty = resultSet2.getString("weight");
                        stockIdNew = resultSet2.getString("stock_id");
                        intQty = Integer.parseInt(wqty);
//                qty = Double.parseDouble(wqty);
                        unit = String.valueOf(u);
                    }

                }

                if (stockId.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please enter stock_id", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (tq.equals("0")) {
                    JOptionPane.showMessageDialog(this, "Product Out Of Stock", "Error", JOptionPane.ERROR_MESSAGE);
                    clear();
                } else {
                    InvoiceItemM invoice = new InvoiceItemM();

                    invoice.setStockId(stockIdNew);
                    invoice.setProductName(productName);
                    invoice.setQty(intQty);
                    invoice.setSellingprice(price1);
                    invoice.setUnit(unit);

                    if (invoiceItemMap.get(stockIdNew) == null) {
                        invoiceItemMap.put(stockIdNew, invoice);
                    } else {
                        InvoiceItemM found = invoiceItemMap.get(stockIdNew);
                        found.setQty((int) (found.getQty() + intQty));
                    }

                    System.out.println(intQty);

                    loadInvoiceItems();
                    clear();

                }
            } catch (Exception e) {
                log1.warning(e.toString());
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Something Went Wrong..", "Error", JOptionPane.WARNING_MESSAGE);
            }

        }

    }//GEN-LAST:event_jButton4ActionPerformed

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton2ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        String invoice_id = jTextField6.getText();
        String total = jLabel16.getText();
        String datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String paidAmount = jTextField4.getText();
        String discount = jTextField3.getText();
        String employee = jLabel33.getText();
        String customerMob;
        String customerId = null;
        String paymentMethodId;

        double totali = Double.parseDouble(total);
        double paidi = Double.parseDouble(paidAmount);
        double disi = Double.parseDouble(discount);

        double fullamount = totali + disi;

        if (jTextField2.getText().equals("")) {
            customerMob = "0000000000";
        } else {
            customerMob = jTextField2.getText();
        }

        ButtonModel paymentMethod = buttonGroup1.getSelection();

        if (invoice_id.equals("")) {
            JOptionPane.showMessageDialog(this, "Something went wrong. Check the invoice", "FreshMart Invoice", JOptionPane.WARNING_MESSAGE);

        } else if (paymentMethod == null) {
            if (jCheckBox1.isSelected()) {
                if (total.equals("0.00")) {
                    //only points
                    paymentMethodId = "3";

                    try {

                        ResultSet resultSet1 = MySQL.execute("SELECT * FROM `customer` WHERE `mobile` = '" + customerMob + "'");

                        if (resultSet1.next()) {
                            customerId = resultSet1.getString("id");
                        }

                        MySQL.execute("INSERT INTO `invoice` (`id`,`date_time`,`total`,`paid_amount`,`discount`,`user_username`,`customer_mobile`,`payment_method_id`) "
                                + "VALUES ('" + invoice_id + "','" + datetime + "','" + fullamount + "','" + total + "','" + discount + "','" + employee + "','" + customerId + "','" + paymentMethodId + "') ");

                        for (InvoiceItemM invoiceItem : invoiceItemMap.values()) {

                            MySQL.execute("INSERT INTO `invoice_item` (`qty`,`stock_id`,`invoice_id`,`unit`) "
                                    + "VALUES ('" + invoiceItem.getQty() + "','" + invoiceItem.getStockId() + "','" + invoice_id + "','" + invoiceItem.getUnit() + "') ");
                            
                            int qty = (int) invoiceItem.getQty();
                            
                            MySQL.execute("UPDATE `stock`\n" +
                                                "SET `qty` = GREATEST(`qty` - '"+qty+"', '0') \n" +
                                                "WHERE `id` = '"+ invoiceItem.getStockId() +"'");

                        }

                        if (jTextField2.getText().equals("")) {

                        } else {

                            customerMob = jTextField2.getText();

                            double p1 = totali / 1000;

                            DecimalFormat df = new DecimalFormat("0");

                            String point = df.format(p1);

                            try {

                                MySQL.execute("UPDATE `customer` SET `points` = '" + point + "' WHERE `mobile` = '" + customerMob + "' ");

                            } catch (Exception e) {
                                log1.warning(e.toString());
                                e.printStackTrace();
                            }

                        }

                        JOptionPane.showMessageDialog(this, "Done", "FreshMart Invoice", JOptionPane.WARNING_MESSAGE);
                        claerAll();

                    } catch (Exception e) {
                        e.printStackTrace();
                        log1.warning(e.toString());
                        JOptionPane.showMessageDialog(this, "Something Went Wrong..", "Error", JOptionPane.WARNING_MESSAGE);
                    }

                } else {
                    JOptionPane.showMessageDialog(this, "Select the payment Method for Blance Payment", "FreshMart Invoice", JOptionPane.WARNING_MESSAGE);
                }

            } else {
                JOptionPane.showMessageDialog(this, "Select the payment Method", "FreshMart Invoice", JOptionPane.WARNING_MESSAGE);
            }

        } else {

            if (jCheckBox1.isSelected()) {

                //payment method and points
                if (paymentMethod.getActionCommand().equals("1")) {
                    //cash and point

                    paymentMethodId = "4";

                    try {

                        ResultSet resultSet1 = MySQL.execute("SELECT * FROM `customer` WHERE `mobile` = '" + customerMob + "'");

                        if (resultSet1.next()) {
                            customerId = resultSet1.getString("id");
                        }

                        MySQL.execute("INSERT INTO `invoice` (`id`,`date_time`,`total`,`paid_amount`,`discount`,`user_username`,`customer_mobile`,`payment_method_id`) "
                                + "VALUES ('" + invoice_id + "','" + datetime + "','" + fullamount + "','" + total + "','" + discount + "','" + employee + "','" + customerId + "','" + paymentMethodId + "') ");

                        for (InvoiceItemM invoiceItem : invoiceItemMap.values()) {

                            MySQL.execute("INSERT INTO `invoice_item` (`qty`,`stock_id`,`invoice_id`,`unit`) "
                                    + "VALUES ('" + invoiceItem.getQty() + "','" + invoiceItem.getStockId() + "','" + invoice_id + "','" + invoiceItem.getUnit() + "') ");

                            int qty = (int) invoiceItem.getQty();
                            
                            MySQL.execute("UPDATE `stock`\n" +
                                                "SET `qty` = GREATEST(`qty` - '"+qty+"', '0') \n" +
                                                "WHERE `id` = '"+ invoiceItem.getStockId() +"'");
                        }

                        if (jTextField2.getText().equals("")) {

                        } else {

                            customerMob = jTextField2.getText();

                            double p1 = totali / 1000;

                            DecimalFormat df = new DecimalFormat("0");

                            String point = df.format(p1);

                            try {

                                MySQL.execute("UPDATE `customer` SET `points` = '" + point + "' WHERE `mobile` = '" + customerMob + "' ");

                            } catch (Exception e) {
                                log1.warning(e.toString());
                                e.printStackTrace();
                            }

                        }

                        JOptionPane.showMessageDialog(this, "Done", "FreshMart Invoice", JOptionPane.WARNING_MESSAGE);
                        claerAll();

                    } catch (Exception e) {
                        e.printStackTrace();
                        log1.warning(e.toString());
                        JOptionPane.showMessageDialog(this, "Something Went Wrong..", "Error", JOptionPane.WARNING_MESSAGE);
                    }

                } else if (paymentMethod.getActionCommand().equals("2")) {
                    //card and points

                    paymentMethodId = "5";

                    try {

                        ResultSet resultSet1 = MySQL.execute("SELECT * FROM `customer` WHERE `mobile` = '" + customerMob + "'");

                        if (resultSet1.next()) {
                            customerId = resultSet1.getString("id");
                        }

                        MySQL.execute("INSERT INTO `invoice` (`id`,`date_time`,`total`,`paid_amount`,`discount`,`user_username`,`customer_mobile`,`payment_method_id`) "
                                + "VALUES ('" + invoice_id + "','" + datetime + "','" + fullamount + "','" + total + "','" + discount + "','" + employee + "','" + customerId + "','" + paymentMethodId + "') ");

                        for (InvoiceItemM invoiceItem : invoiceItemMap.values()) {

                            MySQL.execute("INSERT INTO `invoice_item` (`qty`,`stock_id`,`invoice_id`,`unit`) "
                                    + "VALUES ('" + invoiceItem.getQty() + "','" + invoiceItem.getStockId() + "','" + invoice_id + "','" + invoiceItem.getUnit() + "') ");

                            int qty = (int) invoiceItem.getQty();
                            
                            MySQL.execute("UPDATE `stock`\n" +
                                                "SET `qty` = GREATEST(`qty` - '"+qty+"', '0') \n" +
                                                "WHERE `id` = '"+ invoiceItem.getStockId() +"'");
                        }

                        if (jTextField2.getText().equals("")) {

                        } else {

                            customerMob = jTextField2.getText();

                            double p1 = totali / 1000;

                            DecimalFormat df = new DecimalFormat("0");

                            String point = df.format(p1);

                            try {

                                MySQL.execute("UPDATE `customer` SET `points` = '" + point + "' WHERE `mobile` = '" + customerMob + "' ");

                            } catch (Exception e) {
                                e.printStackTrace();
                                log1.warning(e.toString());
                                JOptionPane.showMessageDialog(this, "Something Went Wrong..", "Error", JOptionPane.WARNING_MESSAGE);
                            }

                        }

                        JOptionPane.showMessageDialog(this, "Done", "FreshMart Invoice", JOptionPane.WARNING_MESSAGE);
                        claerAll();

                    } catch (Exception e) {
                        e.printStackTrace();
                        log1.warning(e.toString());
                        JOptionPane.showMessageDialog(this, "Something Went Wrong..", "Error", JOptionPane.WARNING_MESSAGE);
                    }

                }

            } else {

                //payment method only
                if (total.equals("0.00")) {
                    JOptionPane.showMessageDialog(this, "total eqals 0", "FreshMart Invoice", JOptionPane.WARNING_MESSAGE);
                } else if (totali > paidi) {
                    JOptionPane.showMessageDialog(this, "paid amount less", "FreshMart Invoice", JOptionPane.WARNING_MESSAGE);
                } else if (totali < disi) {
                    JOptionPane.showMessageDialog(this, "total less , discount more", "FreshMart Invoice", JOptionPane.WARNING_MESSAGE);
                } else if (paidi < disi) {
                    JOptionPane.showMessageDialog(this, "paid less , dis more", "FreshMart Invoice", JOptionPane.WARNING_MESSAGE);
                } else {

                    paymentMethodId = paymentMethod.getActionCommand();

                    try {

                        ResultSet resultSet1 = MySQL.execute("SELECT * FROM `customer` WHERE `mobile` = '" + customerMob + "'");

                        if (resultSet1.next()) {
                            customerId = resultSet1.getString("id");
                        }

                        MySQL.execute("INSERT INTO `invoice` (`id`,`date_time`,`total`,`paid_amount`,`discount`,`user_username`,`customer_mobile`,`payment_method_id`) "
                                + "VALUES ('" + invoice_id + "','" + datetime + "','" + fullamount + "','" + total + "','" + discount + "','" + employee + "','" + customerId + "','" + paymentMethodId + "') ");

                        for (InvoiceItemM invoiceItem : invoiceItemMap.values()) {

                            MySQL.execute("INSERT INTO `invoice_item` (`qty`,`stock_id`,`invoice_id`,`unit`) "
                                    + "VALUES ('" + invoiceItem.getQty() + "','" + invoiceItem.getStockId() + "','" + invoice_id + "','" + invoiceItem.getUnit() + "') ");

                            int qty = (int) invoiceItem.getQty();
                            
                            MySQL.execute("UPDATE `stock`\n" +
                                                "SET `qty` = GREATEST(`qty` - '"+qty+"', '0') \n" +
                                                "WHERE `id` = '"+ invoiceItem.getStockId() +"'");
                        }

                        if (jTextField2.getText().equals("")) {

                        } else {

                            customerMob = jTextField2.getText();

                            double p1 = totali / 1000;

                            DecimalFormat df = new DecimalFormat("0");

                            String point = df.format(p1);

                            try {

                                MySQL.execute("UPDATE `customer` SET `points` = '" + point + "' WHERE `mobile` = '" + customerMob + "' ");

                            } catch (Exception e) {
                                e.printStackTrace();
                                log1.warning(e.toString());
                                JOptionPane.showMessageDialog(this, "Something Went Wrong..", "Error", JOptionPane.WARNING_MESSAGE);
                            }

                        }

                        //JOptionPane.showMessageDialog(this, "Done", "FreshMart Invoice", JOptionPane.WARNING_MESSAGE);
                        printInvoice();
                        claerAll();

                    } catch (Exception e) {
                        e.printStackTrace();
                        log1.warning(e.toString());
                        JOptionPane.showMessageDialog(this, "Something Went Wrong..", "Error", JOptionPane.WARNING_MESSAGE);
                    }

                }

            }

        }


    }//GEN-LAST:event_jButton6ActionPerformed

    private void jPanel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel5MouseClicked

    }//GEN-LAST:event_jPanel5MouseClicked

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        StockBoxInvoice1 st = new StockBoxInvoice1();
        st.setVisible(true);
        st.setProduct(this);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField5ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        if (jTable2.getRowCount() > 0) {
            int row = 0; // Index of the first row
            Object value = jTable2.getValueAt(row, 3);

            String qtyNo = jTextField5.getText();
            int qno = Integer.parseInt(qtyNo);

            int availableQty = Integer.parseInt((String) value);

            if (qno >= availableQty || availableQty == 0) {
                JOptionPane.showMessageDialog(this, "Product quentity out of stock", "Error", JOptionPane.ERROR_MESSAGE);
                jTextField5.setText(String.valueOf(availableQty));
            } else {
                qno++;
                jTextField5.setText(String.valueOf(qno));
            }

        } else {
            // Handle the case where the table is empty
            JOptionPane.showMessageDialog(
                    this,
                    "The table is empty. No data to process.",
                    "Error",
                    JOptionPane.WARNING_MESSAGE
            );
        }


    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyPressed

    }//GEN-LAST:event_jTextField1KeyPressed

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        String id = jTextField1.getText();

        try {
            ResultSet resultSet1 = MySQL.execute("SELECT * FROM `stock` "
                    + "INNER JOIN `product` ON `product`.`id` = `stock`.`product_id` "
                    + "WHERE `stock`.`id` ='" + id + "' ");

            DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
            model.setRowCount(0);

            if (resultSet1.next()) {

                Vector<String> v = new Vector();

                v.add(resultSet1.getString("product.name"));
                v.add(resultSet1.getString("stock.exp"));
                v.add(resultSet1.getString("stock.mfd"));
                v.add(resultSet1.getString("stock.qty"));
                v.add("");

                String sp = resultSet1.getString("stock.selling_price");
                double sp_d = Double.parseDouble(sp);

                DecimalFormat df = new DecimalFormat("0.00");

                v.add(String.valueOf(df.format(sp_d)));

                model.addRow(v);
                jTable2.setModel(model);

            }

            ResultSet resultSet2 = MySQL.execute("SELECT * FROM `weight`\n"
                    + "INNER JOIN `stock` ON `stock`.`id` = `weight`.`stock_id`\n"
                    + "INNER JOIN `product` ON `product`.`id` = `stock`.`product_id`\n"
                    + "WHERE `weight`.`id` = '" + id + "'");

            if (resultSet2.next()) {

                Vector<String> v1 = new Vector();

                v1.add(resultSet2.getString("product.name"));
                v1.add(resultSet2.getString("stock.exp"));
                v1.add(resultSet2.getString("stock.mfd"));
                v1.add(resultSet2.getString("stock.qty"));
                v1.add(resultSet2.getString("weight.unit"));

                String sp = resultSet2.getString("stock.selling_price");
                double sp_d = Double.parseDouble(sp);

                DecimalFormat df = new DecimalFormat("0.00");

                v1.add(String.valueOf(df.format(sp_d)));

                model.addRow(v1);

                jTextField5.setText(resultSet2.getString("weight.weight") + " " + resultSet2.getString("weight.unit"));

            }

            jTable2.setModel(model);

        } catch (Exception e) {
            e.printStackTrace();
            log1.warning(e.toString());
            JOptionPane.showMessageDialog(this, "Something Went Wrong..", "Error", JOptionPane.WARNING_MESSAGE);
        }

    }//GEN-LAST:event_jTextField1KeyReleased

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        clear();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        int selectedRow = jTable1.getSelectedRow();
        if (evt.getClickCount() == 2) {
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.removeRow(selectedRow);
            calculatetotal();

        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jTextField3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField3KeyReleased

        calculatetotal();

        String total = jLabel16.getText();
        String discount = jTextField3.getText();

        double total1 = Double.parseDouble(total);
        double dis1 = Double.parseDouble(discount);

        double total_dis = total1 - dis1;

        //jLabel16.setText(String.valueOf(total_dis));
        jLabel16.setText(String.valueOf(total_dis));


    }//GEN-LAST:event_jTextField3KeyReleased

    private void jTextField4KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField4KeyReleased
        String total = jLabel16.getText();
        double total_d = Double.parseDouble(total);

        String paid = jTextField4.getText();
        double paid_d = Double.parseDouble(paid);

        double balence = paid_d - total_d;

        DecimalFormat df = new DecimalFormat("0.00");

        jLabel22.setText(String.valueOf(df.format(balence)));
    }//GEN-LAST:event_jTextField4KeyReleased

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        CustomerViewBox1 cs = new CustomerViewBox1();
        cs.setVisible(true);
        cs.setCustomer(this);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTextField2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyReleased
        String mobile = jTextField2.getText();

        try {

            ResultSet rs = MySQL.execute("SELECT *, COUNT(`i`.`id`) AS `invoice_count`\n"
                    + "FROM `customer` `c`\n"
                    + "LEFT JOIN `invoice` `i` ON `c`.`id` = `i`.`customer_mobile` WHERE `c`.`mobile` = '" + mobile + "' \n"
                    + "GROUP BY `c`.`id`, `c`.`mobile`");

            if (rs.next()) {
                String fname = rs.getString("fname");
                String lname = rs.getString("lname");
                String mob = rs.getString("mobile");
                String points = rs.getString("points");

                jLabel3.setText(fname + " " + lname);
                jLabel12.setText(mob);
                jLabel14.setText(points);

            } else {
                jLabel3.setText("No Customer Fount");
                jLabel12.setText(".....");
                jLabel14.setText(".....");
            }

        } catch (Exception e) {
            e.printStackTrace();
            log1.warning(e.toString());
            JOptionPane.showMessageDialog(this, "Something Went Wrong..", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jTextField2KeyReleased

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        jTextField2.setText("");
        jLabel3.setText("Customer Name");
        jLabel12.setText("0XX XXXXXXX");
        jLabel14.setText("0");
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        claerAll();
        jTextField2.setText("");
        jLabel3.setText("Customer Name");
        jLabel12.setText("0XX XXXXXXX");
        jLabel14.setText("0");
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        String username = jLabel33.getText();
        SellingHistory1 sh = new SellingHistory1(username);
        sh.setVisible(true);
        this.dispose();

    }//GEN-LAST:event_jButton9ActionPerformed

    private void jCheckBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCheckBox1ItemStateChanged

        if (jCheckBox1.isSelected()) {

            if (jTextField2.getText().equals("")) {
                //no customer
                JOptionPane.showMessageDialog(this, "If you want to withdrow points. Select customer first", "Error", JOptionPane.ERROR_MESSAGE);
            } else {

                String points = jLabel14.getText();

                double points_d = Double.parseDouble(points);

                if (points_d < 1000) {
                    JOptionPane.showMessageDialog(this, "Points can be withdrawn only if the customer's points are more than 1000", "Error", JOptionPane.ERROR_MESSAGE);

                } else {

                    calculatetotal();

                    String total = jLabel16.getText();
                    double total1 = Double.parseDouble(total);

                    double pointvalue = 0.5;

                    double pointamount = points_d * pointvalue;
                    double new_total = total1 - pointamount;

                    if (total1 < pointamount) {
                        double total_balence = pointamount - total1;
                        //jLabel16.setText("0.00");
                        double new_points = total_balence / pointvalue;
                        new_total = 00;

                        DecimalFormat df1 = new DecimalFormat("0");
                        jLabel14.setText(String.valueOf(df1.format(new_points)));
                        jTextField4.setText("0.00");
                        DecimalFormat df = new DecimalFormat("0.00");
                        jTextField3.setText(String.valueOf(df.format(total1)));
                        jLabel16.setText(String.valueOf(df.format(new_total)));

                    } else if (total1 >= pointamount) {

                        jLabel14.setText("0");
                        DecimalFormat df = new DecimalFormat("0.00");
                        jTextField3.setText(String.valueOf(df.format(pointamount)));
                        jLabel16.setText(String.valueOf(df.format(new_total)));
                    }

                }

            }

        } else {

            calculatetotal();

            String customerMob = jTextField2.getText();

            try {
                ResultSet resultSet1 = MySQL.execute("SELECT * FROM `customer` WHERE `mobile` = '" + customerMob + "'");

                if (resultSet1.next()) {
                    String cusId = resultSet1.getString("id");
                    String availablepoints = resultSet1.getString("points");

                    jLabel14.setText(availablepoints);
                    jTextField3.setText("0.00");
                    jTextField4.setText("0.00");

                } else {
                    jLabel14.setText("0");
                }

            } catch (Exception e) {
                log1.warning(e.toString());
                JOptionPane.showMessageDialog(this, "Something Went Wrong..", "Error", JOptionPane.WARNING_MESSAGE);
            }

        }
    }//GEN-LAST:event_jCheckBox1ItemStateChanged

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

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
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JCheckBox jCheckBox1;
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
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
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
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    // End of variables declaration//GEN-END:variables
}
