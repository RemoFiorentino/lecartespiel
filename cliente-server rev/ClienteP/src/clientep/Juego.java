 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clientep;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.Timer;
  
/**
 *
 * @author remojosefiorentinocasadiego
 */
public class Juego extends javax.swing.JFrame implements Runnable  {

    /**
     * Creates new form Juego
     */
    Conexion c;
    static boolean cardlock=false;
    static boolean judge;
    static boolean[] loaded = new boolean[4];
    static int[] cartas = new int[4];
    public int time = 30;
    public int resp;
    ArrayList<String> lista = new ArrayList();
    
    Timer timer = timer = new Timer(1000, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(time>0){
                    time=time - 1;
                    //jLabel5.setText("Timer: "+time);
                }else{
                    JOptionPane.showMessageDialog(null, "Se acabo el tiempo", "Error", JOptionPane.ERROR_MESSAGE);
                    timer.stop();
                    c.enviarMensaje("p-false");
                    //finturno();
                }
            }
        });
    public Juego() {
        initComponents();
        this.setTitle("Le Carte Spiel");
        this.setResizable(false);
        reset();
    }
    public static void printCartas(){
        for(int i : cartas){
            System.out.print("["+i+"]");
        }
        System.out.println(" ");
    }
    static void reset(){
        judge=false;
        for(boolean b : loaded){
            b=false;
        }
        for(int i : cartas){
            i=-1;
        }
        ans1.setIcon(null);
        ans1.setEnabled(true);
        ans2.setIcon(null);
        ans2.setEnabled(true);
        ans3.setIcon(null);
        ans3.setEnabled(true);
        ans4.setIcon(null);
        ans4.setEnabled(true);
        cardlock=false;
        jLabel1.setText("  ");
    }
    static void setJudge() {
        judge = true;
        ans1.setEnabled(true);
        ans2.setEnabled(true);
        ans3.setEnabled(true);
        ans4.setEnabled(true);
    }
    public static void setCard(int label, ImageIcon card){
        switch(label){
            case -1: 
                preg.setIcon(card);
                System.out.println("loaded question ");
                break;
            case 0:
                ans1.setIcon(card);
                System.out.println("loaded card 1");
                loaded[0] = true;
                break;
            case 1:
                ans2.setIcon(card);
                System.out.println("loaded card 2");
                loaded[1] = true;
                break;
            case 2:
                ans3.setIcon(card);
                System.out.println("loaded card 3");
                loaded[2] = true;
                break;
            case 3:
                ans4.setIcon(card);
                System.out.println("loaded card 4");
                loaded[3] = true;
                break;            
        }
    }
    void requestQ(){  //con esto haremos el timeout de escojer las cartas supongo
        time=30;
        timer.restart(); 
    }
    
    void updatepuntaje(int puntos){
        String p="el puntaje actual del jugador es "+puntos;
        jTextArea1.setText(p);   
    }
    
    /////////////////////////////////////////////////////////////////
    
    static void taken(String c){
        //desactiva la carta que otro escogio
        switch(c){
            case "1":
                ans1.setEnabled(false);
                break;
            case "2":
                ans2.setEnabled(false);
                break;
            case "3":
                ans3.setEnabled(false);
                break;
            case "4":
                ans4.setEnabled(false);
                break;
        }
    }
    
    static void cardLock(String c,boolean open){
        //desactiva o activa las demas cartas
        switch(c){
            case "1":
                ans2.setEnabled(open);
                ans3.setEnabled(open);
                ans4.setEnabled(open);
                break;
            case "2":
                ans1.setEnabled(open);
                ans3.setEnabled(open);
                ans4.setEnabled(open);
                break;
            case "3":
                ans1.setEnabled(open);
                ans2.setEnabled(open);
                ans4.setEnabled(open);
                break;
            case "4":
                ans1.setEnabled(open);
                ans2.setEnabled(open);
                ans3.setEnabled(open);
                break;
        }
        cardlock=!open;
    }
    /////////////////////////////////////////////////////////////////////
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        ans1 = new javax.swing.JLabel();
        ans2 = new javax.swing.JLabel();
        ans3 = new javax.swing.JLabel();
        ans4 = new javax.swing.JLabel();
        preg = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        ans1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ans1MouseClicked(evt);
            }
        });

        ans2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ans2MouseClicked(evt);
            }
        });

        ans3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ans3MouseClicked(evt);
            }
        });

        ans4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ans4MouseClicked(evt);
            }
        });

        jLabel1.setText("     ");

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(219, 219, 219)
                        .add(preg, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 166, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(jLabel1))
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(32, 32, 32)
                        .add(ans1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 115, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(32, 32, 32)
                        .add(ans3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 115, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 32, Short.MAX_VALUE)
                        .add(ans2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 115, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(32, 32, 32)
                        .add(ans4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 115, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .add(19, 19, 19))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel1)
                    .add(preg, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 230, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(44, 44, 44)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(ans4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 160, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(ans1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 160, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(ans3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 160, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(ans2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 160, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel3.setText("Nombre");

        jLabel5.setText("Timer: 30");
        jLabel5.setEnabled(false);

        jLabel4.setText("IP:");

        jTextField2.setText("192.168.0.20");

        jButton2.setText("Ingresar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(jLabel3)
                        .add(18, 18, 18)
                        .add(jTextField1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 92, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(219, 219, 219)
                        .add(jLabel5, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 69, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(134, 134, 134)
                        .add(jLabel4)
                        .add(18, 18, 18)
                        .add(jTextField2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 89, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(18, 18, 18)
                        .add(jButton2)
                        .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .add(layout.createSequentialGroup()
                        .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 190, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(6, 6, 6)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
                    .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jTextField2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jButton2)
                    .add(jLabel4)
                    .add(jLabel5, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 22, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jTextField1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        try{
            if(jTextField1.getText().isEmpty()||jTextField2.getText().isEmpty()){
                if(jTextField1.getText().isEmpty()){
                    if(jTextField2.getText().isEmpty()){
                        JOptionPane.showMessageDialog(null,"Nombre e IP vacio debe escribir caracteres validos","Error", JOptionPane.ERROR_MESSAGE);
                    }else{
                        JOptionPane.showMessageDialog(null,"Nombre vacio debe escribir caracteres validos","Error", JOptionPane.ERROR_MESSAGE);
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"IP vacio debe escribir caracteres validos","Error", JOptionPane.ERROR_MESSAGE);
                }
            }else{
                c = new Conexion(jTextField2.getText(),12345, jTextField1.getText());//,jTextArea1, jTextArea3, jTextArea2,jButton1);
                c.start();
                c.enviarMensaje("nom-"+jTextField1.getText());
                c.nombre=jTextField1.getText();
                System.out.println("Name sent");
                jTextField1.setEditable(false);
                jTextField2.setEditable(false);
                jButton2.setEnabled(false);
                System.out.println("Connected");
            }
        }catch(Exception ex){
            c = null;
            System.out.print(ex.getMessage());
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void ans1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ans1MouseClicked
        if(loaded[0] && !cardlock==true){
            if(!judge){
                if(ans1.isEnabled()==true && !cardlock==true){
                    cardlock=true;
                    c.selectCard(1,cartas[0]);
                }
            }else{
                //el juez escogio
                c.chooseCard(1,cartas[0]);
                cardLock("1",false);
            }
        }
    }//GEN-LAST:event_ans1MouseClicked

    private void ans2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ans2MouseClicked
        // TODO add your handling code here:
        if(loaded[1] && !cardlock==true){
            if(!judge){
                if(ans2.isEnabled()==true && !cardlock==true){
                    cardlock=true;
                    c.selectCard(2,cartas[1]);
                }
            }else{
                //el juez escogio
                c.chooseCard(2,cartas[1]);
                cardLock("2",false);
            }
        }
    }//GEN-LAST:event_ans2MouseClicked

    private void ans3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ans3MouseClicked
        // TODO add your handling code here:
        if(loaded[2] && !cardlock==true){            
            if(!judge){
                if(ans3.isEnabled()==true && !cardlock==true){
                    cardlock=true;
                    c.selectCard(3,cartas[2]);
                }
            }else{
                //el juez escogio
                c.chooseCard(3,cartas[2]);
                cardLock("3",false);
            }
        }
    }//GEN-LAST:event_ans3MouseClicked

    private void ans4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ans4MouseClicked
        // TODO add your handling code here:
        if(loaded[3] && !cardlock==true){
            if(!judge){
                if(ans4.isEnabled()==true){
                    cardlock=true;
                    c.selectCard(4,cartas[3]);
                }
            }else{
                //el juez escogio
                c.chooseCard(4,cartas[3]);
                cardLock("4",false);
            }
        }
    }//GEN-LAST:event_ans4MouseClicked

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
            java.util.logging.Logger.getLogger(Juego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Juego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Juego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Juego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Juego().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private static javax.swing.JLabel ans1;
    private static javax.swing.JLabel ans2;
    private static javax.swing.JLabel ans3;
    private static javax.swing.JLabel ans4;
    private javax.swing.JButton jButton2;
    public static javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private static javax.swing.JLabel preg;
    // End of variables declaration//GEN-END:variables

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
