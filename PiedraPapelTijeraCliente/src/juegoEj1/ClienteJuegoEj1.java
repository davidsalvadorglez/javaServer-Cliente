package juegoEj1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
//Juego contra otro jugador
public class ClienteJuegoEj1 {
	public static void main(String[] args) throws UnknownHostException, IOException {
		String Host = "localhost";
		int Puerto = 6012;//puerto remoto	
		Scanner sc = new Scanner(System.in);
		
		Socket Cliente = new Socket(Host, Puerto);
		// CREO FLUJO DE SALIDA AL SERVIDOR
		DataOutputStream flujoSalida = new DataOutputStream(Cliente.getOutputStream());

		// CREO FLUJO DE ENTRADA AL SERVIDOR
		DataInputStream flujoEntrada = new DataInputStream(Cliente.getInputStream());
		
		String entrada;
		boolean juega = true;
		while (juega) {
			String envia = sacaJugador(sc);
			System.out.println("Envias "+ envia);
			flujoSalida.writeUTF(envia);
			entrada = flujoEntrada.readUTF();
			System.out.println(entrada);
			entrada = flujoEntrada.readUTF();
			System.out.println(entrada);
			juega = flujoEntrada.readBoolean();
		}
		entrada = flujoEntrada.readUTF();
		System.out.println(entrada);
		
		flujoEntrada.close();
		flujoSalida.close();
		Cliente.close();
	}
	public static String sacaJugador(Scanner sc) {
		System.out.println("Que sacas Piedra, papel o tijera?");
		Boolean correcto = false;
		String saca = "";
		while (!correcto) {
			saca = sc.nextLine().toUpperCase();
			if (saca.equals("PIEDRA")) {
				correcto = true;
			}else if (saca.equals("PAPEL")) {
				correcto = true;
			}
			else if (saca.equals("TIJERA")) {
				correcto = true;
			}
			else System.out.println("Introduzca otra vez");
		}
		return saca;
	}
}
