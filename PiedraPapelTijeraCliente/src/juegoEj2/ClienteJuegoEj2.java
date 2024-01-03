package juegoEj2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClienteJuegoEj2 {
	static int ronda = 1, contadorcl1 = 0, Jserver=0;

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
			//ENVIA AL SERVIDOR 
			String envia = sacaJugador(sc);
			System.out.println("Envias "+ envia);
			flujoSalida.writeUTF(envia);
			
			//RECIVE DEL SERVIDOR
			entrada = flujoEntrada.readUTF();
			System.out.println("El servidor ha sacado "+ entrada);
			
			//CALCULA EL GANADOR
			int jdGanador = calculaGanador(envia, entrada);
			
			switch (jdGanador) {
			case 0: {
				System.out.println("empate");
				break;
			}
			case 1: {
				System.out.println( "Ha ganado el JUGADOR");
				break;
						}
			case 2: {
				System.out.println("Ha ganado el SERVER");
				break;
			}
			default:
				System.out.println("ERROR");
			}
			System.out.println(("Ronda " + ronda + " Jugador[ "+contadorcl1+" - "+Jserver + " ]Servidor"));
			ronda++;
			if ((contadorcl1 <3) && (Jserver <3)) {
				juega = true;
				
			}
			else {
				juega = false;
			}
		}
		if (contadorcl1 ==3) {
			System.out.println("HAS GANADO");
		}
		else {
			System.out.println("HA GANADO EL SERVER");
		}
		flujoEntrada.close();
		flujoSalida.close();
		Cliente.close();
	}
	public static int calculaGanador(String jd1,String jd2) {
		int jdGanador;
		if (jd1.compareTo(jd2) == 0) {
			System.out.println("empate");
			jdGanador = 0;
		}
		else {
			if (jd1.equalsIgnoreCase("piedra")) {
				if (jd2.equalsIgnoreCase("papel")) {//J1 PIEDRA J2 PAPEL
					Jserver ++;
					jdGanador = 2;
				}
				else {//J1 PIEDRA J2 TIJERA
					contadorcl1 ++;
					jdGanador = 1;
				}
			}
			else if (jd1.equalsIgnoreCase("papel")) {
				if (jd2.equalsIgnoreCase("tijera")) {//J1 PAPEL J2 TIJERA
					Jserver ++;
					jdGanador = 2;
				}
				else {//J1 PAPEL J2 PIEDRA
					contadorcl1 ++;
					jdGanador = 1;
				}
			}
			else {//J1 TIJERA
				if (jd2.equalsIgnoreCase("piedra")) {//J1 TIJERA J2 piedra
					Jserver ++;
					jdGanador = 2;
				}
				else {//J1 Tijera J2 papel
					contadorcl1 ++;
					jdGanador = 1;
				}
			}
		}
		return jdGanador;
		}
	public static String sacaJugador(Scanner sc) {
		System.out.println("Que sacas piedra, papel o tijera?");
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
