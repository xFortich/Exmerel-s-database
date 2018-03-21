/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bases;

import java.sql.Connection;
import java.sql.*;
import java.util.LinkedList;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author fortich
 */
public class muestraCambiaYElimina extends javax.swing.JFrame {

    /**
     * Creates new form comoQuieras
     */
    DefaultTableModel modeloTabla = new DefaultTableModel();
    String nombreDeTabla;
    Connection c;
    String consulta;

    public muestraCambiaYElimina(String consulta, Connection c, String tabla) {
        initComponents();
        this.c = c;
        nombreDeTabla = tabla;
        modeloTabla = new DefaultTableModel();
        this.consulta = consulta;
        jTable.getTableHeader().setReorderingAllowed(false);
        actualizarTabla();
    }

    public void actualizarTabla() {
        try {
            modeloTabla = new DefaultTableModel();
            Statement s = c.createStatement();
            ResultSet ventas = s.executeQuery(consulta);
            ResultSetMetaData datos = ventas.getMetaData();
            int columnas = datos.getColumnCount();
            for (int i = 0; i < columnas; i++) {
                tipo.add(datos.getColumnClassName(i + 1));
                modeloTabla.addColumn(datos.getColumnName(i + 1));
            }
            while (ventas.next()) {
                Object[] filas = new Object[columnas];
                for (int i = 0; i < columnas; i++) {
                    filas[i] = ventas.getObject(i + 1);
                }
                modeloTabla.addRow(filas);
            }
            modeloTabla.addTableModelListener(new TableModelListener() {
                @Override
                public void tableChanged(TableModelEvent e) {
                    Object[] dataToChange = new Object[modeloTabla.getColumnCount()];
                    for (int i = 0; i < dataToChange.length; i++) {
                        dataToChange[i] = modeloTabla.getValueAt(e.getLastRow(), i);
                    }
                    cambios.add(dataToChange);
                }
            });
            jTable.setModel(modeloTabla);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    LinkedList<Object[]> cambios = new LinkedList<>();
    LinkedList<String> tipo = new LinkedList<>();

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable);

        jButton1.setText("Guardar cambios");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Descartar cambios");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Eliminar fila");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3)
                        .addGap(0, 94, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 572, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton1)
                    .addComponent(jButton3))
                .addContainerGap(40, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        for (Object[] cambioActual : cambios) {
            try {
                Statement s = c.createStatement();
                StringBuilder query = new StringBuilder();
                query.append("call " + nombreDeTabla + "_update(");
                for (int i = 0; i < cambioActual.length; i++) {
                    String tipob = tipo.get(i);
                    if (tipob.endsWith("Integer") || tipob.endsWith("Double") || tipob.endsWith("Long") || cambioActual[i] == null) {
                        query.append(cambioActual[i]);
                    } else {
                        query.append("\"" + cambioActual[i] + "\"");
                    }
                    if (i != cambioActual.length - 1) {
                        query.append(", ");
                    }
                }
                query.append(");");
                s.executeQuery(query.toString());
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        actualizarTabla();
        cambios = new LinkedList<>();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        actualizarTabla();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableMouseClicked

    }//GEN-LAST:event_jTableMouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        int[] filas = (jTable.getSelectedRows());
        try {
            Statement s = c.createStatement();
            
            for (int i = 0; i < filas.length; i++) {
                String query = "call "+nombreDeTabla+"_delete("+jTable.getValueAt(filas[i], 0) + ", " + jTable.getValueAt(filas[i], 1)+");";
                s.execute(query);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }//GEN-LAST:event_jButton3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable;
    // End of variables declaration//GEN-END:variables
}