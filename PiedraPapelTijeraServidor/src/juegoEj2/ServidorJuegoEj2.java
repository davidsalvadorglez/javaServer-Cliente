package juegoEj2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
//Servidor contra la maquina 
public class ServidorJuegoEj2 {
	public static void main(String[] args) throws IOException {
		int numeroPuerto = 6012;// Puerto
		ServerSocket servidor = new ServerSocket(numeroPuerto);
		int numpartida = 1;
		boolean jugar = true;
		ServerHilo partida;
		while (jugar) {
			Socket clienteConectado = null;
			System.out.println("Esperando al cliente.....\n");
			clienteConectado = servidor.accept();
			partida = new ServerHilo(clienteConectado, numpartida);
			partida.start();
			numpartida++;
		}
		
		// CREO FLUJO DE ENTRADA DEL CLIENTE
		servidor.close();
	}
	
}
