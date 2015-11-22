/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clientep;
import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;
/**
 *
 * @author remojosefiorentinocasadiego
 */
public class Conexion extends Thread{
    Socket s;
    DataInputStream entrada;
    DataOutputStream salida;
    JTextArea jta;
    JTextArea Pregunta;
    JTextArea punt;
    String nombre;
    JButton jbt;
    String Answer;

    public Conexion(String ip, int puerto, String nombre) throws IOException{//, JTextArea jta, JTextArea pregunta, JTextArea punt, JButton jbt) throws IOException{
        this.jta = jta;
        this.punt=punt;
        this.jbt=jbt;
        //this.Pregunta=pregunta;
        this.nombre = nombre;
        this.s = new Socket(ip, puerto);
        salida = new DataOutputStream(s.getOutputStream());
        salida.flush();
        entrada = new DataInputStream(s.getInputStream());
    }
    
    @Override
    public void run(){
        int cont = 1;
        while(true){
            try{
                String mensaje = (String) entrada.readUTF();
                String[] sp = mensaje.split("-");
                String path;
                switch(sp[0]){
                    case "Pregunta:":
                        path = sp[1];
                        ImageIcon card = new ImageIcon(getClass().getClassLoader().getResource(path));
                        //JLabel jl = new JLabel();
                        clientep.Juego.setCard(0,card);
                        break;
                    case "Cartas:":
                        path = sp[1];
                        card = new ImageIcon(getClass().getClassLoader().getResource(path));
                        clientep.Juego.setCard(1+cont%4,card);
                        break;
                    default:
                        
                        break;
                }
                /*if(sp[0].equals("Puntaje:")){
                    punt.setText("");
                    for (int i=0;i<sp.length;i++){
                        punt.append(sp[i]+"\n");
                    }
                }else if(sp[0].equals("Turno:")){
                    if(sp[1].equals(nombre)){
                        System.out.println("su turno");
                        jbt.setEnabled(true);
                    }
                }else if(sp[0].equals("Pregunta:")){
                    Pregunta.setText("");
                    String[] q = sp[1].split("_");
                    for(String s : q){
                        Pregunta.append(s+"\n");
                    }   
                    Answer=sp[2]; 
                }else if(sp[0].equals("Pregunta:")){
                    finJuego();
                    jta.append("El ganador del juego es :"+sp[1]);
                }else{
                    jta.append(sp[0]+"\n");
                }*/
            }catch(Exception ex){
                System.out.println(ex.getMessage());
            }
        }
    }
    
    public void finJuego(){
        jbt.setEnabled(false);
    }
    
    public void mostrarMensaje(final String mensaje){
        jta.append(mensaje);
    }
    
    public void enviarMensaje(String mensaje){
         try{
             salida.writeUTF(mensaje);
             salida.flush();
         }catch (IOException ex){
             Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
}
