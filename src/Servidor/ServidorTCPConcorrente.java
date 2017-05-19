package Servidor;

import Protocolo.Protocolo;
import org.w3c.dom.Document;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public final class ServidorTCPConcorrente implements Runnable {

    private ServerSocket serverSocket;
    private int serverPort;
    protected boolean isActive;
    protected Thread runningThread = null;

    public ServidorTCPConcorrente(int port) {
        this.serverSocket = null;
        this.serverPort = port;
        this.isActive = true;
    }

    private void openServerSocket() {
        try {
            this.serverSocket = new ServerSocket(this.serverPort);
        } catch (IOException e) {
            throw new RuntimeException("Cannot open serverPort " + Integer.toString(serverPort), e);
        }
    }

    public void run(){
        synchronized(this){
            this.runningThread = Thread.currentThread();
        }
        openServerSocket();
        while(isActive) {
            Socket clientSocket = null;
            try {

                System.out.println("Servidor TCP concorrente aguarda ligacao no porto " + serverPort + "..." );

                // Espera connect do cliente
                clientSocket = this.serverSocket.accept();

            } catch (IOException e) {
                System.err.println("Excepção no servidor: " + e);
            }

            Thread th = new HandleConnectionThread(clientSocket);
            th.start();
        }
        System.out.println("Servidor Parado");
    }

    private synchronized boolean isActive() {
        return this.isActive;
    }

    public synchronized void stop(){
        this.isActive = false;
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing server", e);
        }
    }

} // end ServidorTCP



class HandleConnectionThread extends Thread {

    private Socket connection;
    private XMLDecoder decoder;
    private XMLEncoder encoder;
    public boolean session;

    HandleConnectionThread(Socket connection) {
        this.connection = connection;
        this.decoder = null;
        this.encoder = null;
        this.session = true;

    }

    public void run() {
        openSocket();
        while (session) {
            readSocket();
        }
        closeSocket();
    } // end run

    private void openSocket(){

        try {
            // circuito virtual estabelecido: socket cliente na variavel newSock
            System.out.println("Thread " + this.getId() + ": " + connection.getRemoteSocketAddress());

            XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(connection.getInputStream()));


            XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(connection.getOutputStream()));

        }
        catch (IOException e) {
            System.err.println("erro na liga�ao " + connection + ": " + e.getMessage());
        }
    }

    private Document readSocket(){
        Document d = (Document) decoder.readObject();
        System.out.println("servidor recebeu --> " + Protocolo.getStringFromDocument(d));
        return d;
    }

    private boolean isConnected() {
        return this.session;
    }

    protected void end() {
        this.session = false;
    }

    public void writeSocket(String s){
        encoder.writeObject(s);
        System.out.println("Servidor Enviou --> " + s);
    }


    private void closeSocket(){

        // Fechar os streams e o socket
        try {
            if (encoder != null) encoder.close();
            if (decoder != null) decoder.close();
            if (connection != null ) connection.close();
        }
        catch (IOException e) {
            System.err.println("Erro ao fechar o socket --> " + e.getMessage());
        }
    }


} // end HandleConnectionThread
