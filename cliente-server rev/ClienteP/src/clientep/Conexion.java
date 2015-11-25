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
/**
 *
 * @author remojosefiorentinocasadiego
 */
public class Conexion extends Thread{
    Socket s;
    String id;
    DataInputStream entrada;
    DataOutputStream salida;
    String nombre;
    String Answer;

    public Conexion(String ip, int puerto, String nombre) throws IOException{
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
                System.out.println("#"+mensaje);
                String[] sp = mensaje.split("-");
                String path;
                switch(sp[0]){
                    case "ID":
                        this.id = sp[1];
                        break;
                    case "Pregunta:":
                        path = sp[1];
                        ImageIcon card = new ImageIcon(getClass().getClassLoader().getResource(path));
                        clientep.Juego.setCard(-1,card);
                        break;
                    case "Cartas:":
                        path = sp[1];
                        clientep.Juego.cartas[cont%4] = Integer.parseInt(sp[2]);
                        card = new ImageIcon(getClass().getClassLoader().getResource(path));
                        clientep.Juego.setCard(cont%4,card);
                        cont++;
                        break;
                    case "Choose:":
                        path = sp[1];
                        card = new ImageIcon(getClass().getClassLoader().getResource(path));
                        clientep.Juego.setCard(cont%4+1,card);
                        cont++;
                        break;
                    case "Judge:":
                        if(nombre.equals(sp[1])){
                            clientep.Juego.setJudge();
                            clientep.Juego.jTextArea1.append("Usted es el Juez\n");
                            clientep.Juego.jLabel1.setText("Juez");
                            enviarMensaje("q-");
                        }
                        break;
                    case "Fin:":
                        clientep.Juego.reset();
                        break;
                    case "Puntaje:":
                        clientep.Juego.jTextArea1.append("Puntaje de "+sp[1]+"\n");
                        break;
                    case "Gano:":
                        clientep.Juego.jTextArea1.append("El Jugador "+sp[1]+" gano.\n");
                        break;
                ////////////////////////////Pick & lock logic/////////////////////////////        
                    case "Taken:":
                        if(sp[1].equals(id)){
                            //false disables the other cards
                            clientep.Juego.cardLock(sp[2],false);
                            clientep.Juego.jTextArea1.append("Usted escogio la carta "+sp[2]+"\n");
                            System.out.println("card "+sp[2]+" taken");
                            int n = Integer.parseInt(sp[2])-1;
                            clientep.Juego.printCartas();
                            clientep.Juego.cartas[n]=Integer.parseInt(sp[3]);
                            clientep.Juego.printCartas();
                        }else{
                            if(clientep.Juego.judge){
                                //cargar cartas
                                path = sp[4];
                                clientep.Juego.cartas[cont%4] = Integer.parseInt(sp[2]);
                                card = new ImageIcon(getClass().getClassLoader().getResource(path));
                                clientep.Juego.setCard(cont%4,card);
                                cont++;
                            }else{
                                clientep.Juego.taken(sp[2]);
                            }
                            System.out.println("player "+sp[1]+" took card "+sp[2]);
                        } 
                        break;
                    case "Rollback:":
                        //true enables cards
                        clientep.Juego.cardLock(sp[2],true);
                        System.out.println("card "+sp[2]+" already taken");
                        clientep.Juego.jTextArea1.append("La carta "+sp[2]+" ya fue escogida.\n");
                        break;
                /////////////////////////////////////////////////////////////////////////
                    default:
                        //append to chat
                        System.out.println(mensaje);
                        break;
                }
            }catch(Exception ex){
                System.out.println("shit happens, "+ex.getMessage()+"\n"+ex.getStackTrace());
            }
        }
    }
    
    public void finJuego(){
        //jbt.setEnabled(false);
    }
    
    public void mostrarMensaje(final String mensaje){
        clientep.Juego.jTextArea1.append(mensaje);
    }
    public void selectCard(int card, int idgen){
        enviarMensaje("pick-"+card+"-"+id+"-"+idgen);
        System.out.println("Card "+card+" selected");
    }
    
    public void chooseCard(int card, int idgen){
        enviarMensaje("choose-"+card+"-"+idgen);
        System.out.println("Card a"+idgen+".png chosen");
    }
    public void enviarMensaje(String mensaje){
         try{
             System.out.println("se envio: "+mensaje);
             salida.writeUTF(mensaje);
             salida.flush();
         }catch (IOException ex){
             Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
}
