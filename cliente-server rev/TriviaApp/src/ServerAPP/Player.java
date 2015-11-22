package ServerAPP;

//package triviaserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Herik
 */
public class Player extends Thread {
    static int currentP = 1;
    Socket connection; 
    DataInputStream entrada;
    DataOutputStream salida;
    int playerID;
    int puntaje;
    String playernom;
    HashMap<Integer,Player> players;
    ArrayList<Pregunta> preguntas;
    ArrayList<Carta> cartas;
    boolean judge = true;
    boolean respuesta = true; 
    

    public Player( Socket sc, int ID, HashMap<Integer,Player> pls,ArrayList<Pregunta> prg,ArrayList<Carta> car){
        playerID = ID; 
        connection = sc; 
        players=pls;
        preguntas=prg;
        cartas = car;
        try{
            salida = new DataOutputStream(connection.getOutputStream());
            salida.flush();
            entrada = new DataInputStream(connection.getInputStream());
        }catch(IOException ioex){
            ioex.printStackTrace();
        }
    }
    public void takeCard(String pid, String cid){
        Semaphore sph = ServerAPP.Server.sph;
        try{
            sph.acquire();
            //critical section
            //Carta c = ServerAPP.Server.Cartas.get(cid);
            //c.owner = pid;
            int[] cartas = ServerAPP.Server.cartasI;
            int id = Integer.parseInt(pid);
            if(cartas[id-1]==0){
                enviarTodos("Taken:-"+pid+"-"+cid,true);
                System.out.println(playernom+" took  card "+ cid);
            }else{
                System.out.println("sorry, "+playernom+"already took  card "+ cid);
                enviarMensaje("Rollback:-"+cid);
            }
        }catch(InterruptedException iex){
            System.out.println("interrupted");
        }finally{
            sph.release();
        }
    }
    
    
    @Override
    public void run(){
        while(true){
            try{
                String mensaje = (String) entrada.readUTF();
                String[] func = mensaje.split("-");
                switch(func[0]){
                    case "nom":
                        playernom=func[1];
                    break;
                    case "pick":
                        // hacer la fila y aplicar semaforo para blockear la carta
                        System.out.println(playernom+" picked card "+func[1]);
                        takeCard(func[2],func[1]);
                    break;
                    case "g":
                        enviarTodos("ganador:-"+func[1],true);
                    break; 
                    case "p":
                        if(func[1].equals("true")){
                            puntaje++;
                            actualizarP();
                            enviarTodos(playernom+" contesto correctamente", false);
                        }else{
                            siguiente();
                        }
                    break;
                    //case q: no es llamado    
                    case "q":
                        Random rnd = new Random();
                        int i;
                        Pregunta p;
                        do{
                            i = (int) (rnd.nextDouble() * 1 + 0);
                            p = preguntas.get(i);
                        }while(p.show);
                        for(int k=0;k<players.size();k++){
                           players.get(k).enviarMensaje("Pregunta:-"+p.path);
                        }
                        Carta c;
                        for(int j=0;j<5;j++){
                            do{
                                i = (int) (rnd.nextDouble() * 1 + 0);
                                c = cartas.get(i);
                            }while(c.show);
                            for(int k=0;k<players.size();k++){
                                players.get(k).enviarMensaje("Carta:-"+c.path);
                            }
                        }
                    break;
                    //default:
                    //break; 
                }
            }
            catch(IOException ioex){
                break;
            }
        }
        players.remove(this);
    }
    public void actualizarP(){
        String p="Puntaje:";
        for (Integer i : players.keySet()) {
            Player pl = players.get(i);
            p=p+"-"+pl.playernom+": "+pl.puntaje;
        }
        enviarTodos(p,true);
    }
    public void siguiente(){
        if(currentP<players.size()){
            currentP++;
        }else{
            currentP=1;
        }
        Player p = players.get(currentP);
        System.out.println("next player: "+ p.playernom);
        p.enviarMensaje("Turno:-"+p.playernom);
        enviarTodos("Es el turno de "+p.playernom,false);
    }
    public void enviarMensaje(String mensaje){
        try {
            salida.writeUTF(mensaje);
            salida.flush();
        } catch (IOException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void enviarTodos(String mensaje,boolean function){
        for (Integer i : players.keySet()) {
            Player p = players.get(i);
            if(!function){
                p.enviarMensaje("Server: "+mensaje);
            }else{
                p.enviarMensaje(mensaje);
            }
        }
    
    }
}
