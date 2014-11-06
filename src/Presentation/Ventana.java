package Presentation;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class Ventana extends javax.swing.JFrame {

    private int dimension = 9;
    private JButton[][] botones;
    private int num = 65;
    public Ventana() {
        initComponents();

        //crear botones y agregarlos al panel
        //panelJuego.setLayout(new GridLayout(dimension, dimension));
        panelJuego.setLayout(new GridLayout(dimension, dimension));
        botones = new JButton[dimension + 2][dimension + 2];
        for (int i = 1; i <= dimension; i++) {
            for (int j = 1; j <= dimension; j++) {
                JButton jButton = new JButton();
                jButton.addActionListener(new ListenerBoton(i, j));
                panelJuego.add(jButton);
                botones[i][j] = jButton;
            }

        }
        botones[1][1].setSize((botones[1][1].getWidth())*2,botones[1][1].getHeight());
        botones[1][1].setVisible(false);
        for (int i = 2; i <= dimension; i++) {
           botones[1][i].setText(""+(i-1));
           botones[i][1].setText(((char)num)+"");
           botones[i][1].setSize((botones[i][1].getWidth())*2,botones[i][1].getHeight());
           num=num+1;
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

        panelJuego = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        getContentPane().add(panelJuego);
        panelJuego.setBounds(0, 0, 400, 300);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Ventana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Ventana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Ventana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Ventana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Ventana().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel panelJuego;
    // End of variables declaration//GEN-END:variables

    private class ListenerBoton implements ActionListener {

        private int x;
        private int y;

        public ListenerBoton(int i, int j) {
            //en el constructor se almacena la fila y la columan q presionó
            x = i;
            y = j;
        }

        public void actionPerformed(ActionEvent e) {
            //cuando se presiona un botón, se ejecutará un método
            clickBoton(x, y);
        }
    }

    private void clickBoton(int fila, int columna) {
    //METODO A COMPLETAR:
        //en fila y columna se reciben las coordenas donde presionó el ususario, relativas al comienzo de la grilla
        //fila 1 y col 1 corresponden a la posicion de arriba a la izquierda
        //debe indicarse como responder al click de ese botón
    }

}