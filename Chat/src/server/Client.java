package server;

import java.awt.Panel;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

//usunac applet z poczatku (moze byc wiecej do poprawy)
//nalezy zastapic wpisywanie do appletu na wyswietlanie tekstu uzytkownikom (chyba juz jest cos takiego)
//dodac wyjatek: osoba ktora juz to napisala

public class Client extends Panel implements Runnable {
// The socket connecting us to the server
    private Socket socket;
// The streams we communicate to the server; these come from the socket
    private DataOutputStream dout;
    private DataInputStream din;
    
    String actuallmessage[];
    
// Constructor
    public Client(String host, int port) {
// We want to receive messages when someone types a line and hits return,
// using an anonymous class as a callback
        Scanner scanner = new Scanner(System.in);
        String message = scanner.nextLine();
        processMessage(message);
// Connect to the server
        try {
// Initiate the connection
            socket = new Socket(host, port);
// We got a connection! Tell the world
            System.out.println("connected to " + socket);
// Let's grab the streams and create DataInput/Output streams from them
            din = new DataInputStream(socket.getInputStream());
            dout = new DataOutputStream(socket.getOutputStream());
// Start a background thread for receiving messages
            new Thread(this).start();
        } catch (IOException ie) {
            System.out.println(ie);
        }
    }
    
// Gets called when the user types something
    private void processMessage(String message) {
        try {
// Send it to the server
            dout.writeUTF(message);
        } catch (IOException ie) {
            System.out.println(ie);
        }
    }
    
// Background thread runs this: show messages from other window
    public void run() {
        try {
// Receive messages one-by-one, forever
            while (true) {
// Get the next message
                String message = din.readUTF();
// Print it to our text window
                System.out.println(message);
            }
        } catch (IOException ie) {
            System.out.println(ie);
        }
    }
}