package ServerAPP;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Semaphore;
import javax.swing.JOptionPane;

/**
 * @author Herik
 */
public class Server {
    
    ServerSocket sv;
    ArrayList<Pregunta> Preguntas;
    static ArrayList<Carta> Cartas;
    static int[] cartasI = {0,0,0,0};
    static int[] doubt = {-1,-1,-1,-1,-1};
    HashMap<Integer,Player> players;
    static int currentPlayer;
    int slot=1;
    boolean juegoactivo = false;
    static Semaphore sph = new Semaphore(1);
        
    
    public Server(){
        int port = 12345; 
        load();
        currentPlayer=1;
        juegoactivo=true;
        try{
            System.out.println("port:12345\nIP: localHost");
            sv = new ServerSocket(port,5);
        }catch(IOException ioex) {
            ioex.getMessage();
            System.err.println("Could not listen on port: "+port+".");
        }
        players = new HashMap<Integer,Player>();
        String[] list = new String[4];

        for(Integer i = 2;i<6;i++){
            list[i-2] = i.toString();
        }
        int selec = Integer.parseInt((String)JOptionPane.showInputDialog(null, "Cuantos jugadores\nvan a jugar", "Jugadores", JOptionPane.INFORMATION_MESSAGE, null, list,list[0]));
        System.out.println("Esperando jugadores...");
        while(players.size()<selec){
            try{
                Player px = new Player( sv.accept(), slot , players, Preguntas, Cartas);
                px.start();
                players.put(slot,px);
                System.out.println("conectado a: player "+slot+px.connection.getInetAddress()+"("+px.playerID+")");
                px.enviarMensaje("ID-"+slot);
                slot++;
            }catch(IOException ioex){
                ioex.printStackTrace();
            }
        }
        try{ 
            Thread.sleep(3000);
        }catch(InterruptedException e) 
        { System.out.println("Thread Interrupted"); }
        //posible ciclo
            Player currentpx = players.get(currentPlayer);
            currentpx.judge=false;
            //ver como manejar al juez
            currentpx.enviarTodos("Es el turno de ser juez de  "+currentpx.playernom,false);
            currentpx.enviarMensaje("Judge:-"+currentpx.playernom);
            //broadcast de las cartas
            //hacer mas aleatoria la seleccion
//            currentpx.enviarTodos("Pregunta:-p"+String.format("%02d", 1)+".jpg",true);
//            for(int i=0;i<4;i++){
//                currentpx.enviarTodos("Cartas:-a"+String.format("%02d", i)+".jpg",true);
//            }
            System.out.println("player: "+currentpx.playernom+" esta de turno.");
        //posible ciclo
    }
    
    public static void adquire(){
        try{
            sph.acquire();
        }catch(InterruptedException iex){
            System.out.println("interrupted");
        }
    }
    
    public static void release(){
        sph.release();
        
    }
    
    public static void cleanhash(){
       doubt[0] = -1;
       doubt[1] = -1;
       doubt[2] = -1;
       doubt[3] = -1;
       doubt[4] = -1;
    }
    
    public void load(){
        Preguntas = new ArrayList<Pregunta>();
        Pregunta p;
        for(int i=0;i<16;i++){
            p = new Pregunta(i,"p"+String.format("%02d", i)+".png");
            Preguntas.add(p);
        }
        Cartas = new ArrayList<Carta>();
        Carta c;
        for(int i=0;i<25;i++){
            c = new Carta(i,"a"+String.format("%02d", i)+".png");
            Cartas.add(c);
        }
    }
    public static void main(String args[]) {
        Server s = new Server();
    }
}
