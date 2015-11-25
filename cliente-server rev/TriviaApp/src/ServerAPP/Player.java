package ServerAPP;

//package triviaserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
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
    Random rnd = new Random();
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
    /////////////////////////SEMAPHORE LOGIC/////////////////////////////////
    public void takeCard(String pid, String cid, String idcard){
        try{
            ServerAPP.Server.adquire();
            //critical section
            int[] cartas = ServerAPP.Server.cartasI;
            int[] doubt = ServerAPP.Server.doubt;
            int id = Integer.parseInt(pid);
            if(cartas[id-1]==0){
                doubt[id-1] = Integer.parseInt(cid);
                enviarTodos("Taken:-"+pid+"-"+cid+"-"+idcard+"-"+"a"+String.format("%02d", Integer.parseInt(idcard))+".png",true);
                System.out.println(playernom+" took  card "+ cid);
            }else{
                System.out.println("sorry, "+playernom+"already took  card "+ cid);
                enviarMensaje("Rollback:-"+cid);
            }
        }catch(Exception iex){
            System.out.println("interrupted");
        }finally{
            ServerAPP.Server.release();
        }
    }
    //////////////////////////////////////////////////////////////////////////////
    
    @Override
    public void run(){
        while(true){
            try{
                String mensaje = (String) entrada.readUTF();
                System.out.println("Perra: "+mensaje );
                String[] func = mensaje.split("-");
                switch(func[0]){
                    case "nom":
                        playernom=func[1];
                    break;
                    case "pick":
                        // hacer la fila y aplicar semaforo para blockear la carta
                        System.out.println(playernom+" picked card "+func[1]);
                        try{ 
                            Thread.sleep(500);
                        }catch(InterruptedException e) 
                        { System.out.println("Thread Interrupted"); }
                        takeCard(func[2],func[1],func[3]);
                    break;
                    case "choose":
                        boolean bool = false;
                        int i2;
                        System.out.println(Arrays.toString(ServerAPP.Server.doubt));
                        for(i2=1;i2<=players.size() && !bool;i2++){
                            if(ServerAPP.Server.doubt[i2] == Integer.parseInt(func[1])){
                                bool =true;
                            }
                        }
                        if(bool){
                            System.out.println("Error choose");
                            int i3 = (int) (rnd.nextDouble() * players.size() + 1);
                            System.out.println(i3);
                            Player pl = players.get(i3);
                            Player pl2 = players.get(i2);
                            pl2.puntaje++;
                            if(pl2.puntaje >= 1){
                                findeljuego(pl2.playernom);
                                return;
                            }
                            ServerAPP.Server.currentPlayer = pl.playerID;
                            System.out.println("gano "+pl.playernom);
                            siguiente(i3);
                        }
                    break; 
                    case "p":
                        if(func[1].equals("true")){
                            puntaje++;
                            actualizarP();
                            enviarTodos(playernom+" contesto correctamente", false);
                        }else{
                           // siguiente();
                        }
                    break;
                    //case q: no es llamado    
                    case "q":
                        Pregunta p;
                        int j;
                        do{
                            j = (int) (rnd.nextDouble() * 15 + 0);
                            p = preguntas.get(j);
                        }while(p.show);
                        p.show = true;
                        for (Integer i : players.keySet()) {
                            Player PL = players.get(i);
                                PL.enviarMensaje("Pregunta:-"+p.path);
                                System.out.println("se envio la pregunta a "+PL.playernom);
                        }
                        Carta c;
                        for(int k=0;k<players.size()-1;k++){
                            do{
                                j = (int) (rnd.nextDouble() * 24 + 0);
                                c = cartas.get(j);
                            }while(c.show);
                            c.show = true;
                            enviarSinJuez("Cartas:-"+c.path+"-"+c.id);
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
    public void newwaves(){
        ServerAPP.Server.cleanhash();
    }
    
    public void findeljuego(String name){
        enviarTodos("Gano:-"+name,true);
    }
    
    public void actualizarP(){
        String p="Puntaje:";
        for (Integer i : players.keySet()) {
            Player pl = players.get(i);
            p=p+"-"+pl.playernom+": "+pl.puntaje;
        }
        enviarTodos(p,true);
    }
    public void siguiente(int i){
        newwaves();
        actualizarP();
        enviarTodos("Fin:-",true);
        Player p = players.get(i);
        System.out.println("Juez es: "+p.playernom);
        p.enviarMensaje("Judge:-"+p.playernom);
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
    public void enviarSinJuez(String mensaje){
        for (Integer i : players.keySet()) {
            if(i != ServerAPP.Server.currentPlayer){
                Player p = players.get(i);
                System.out.println("se envio "+ mensaje +" a "+p.playernom);
                p.enviarMensaje(mensaje);   
            }
        }
    }
}
