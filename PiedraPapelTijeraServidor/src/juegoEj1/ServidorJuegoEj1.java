package juegoEj1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
//Juego contra la maquina
public class ServidorJuegoEj1 {
	public static void main(String[] args) throws IOException {
		int numeroPuerto = 6012;// Puerto
		ServerSocket servidor = new ServerSocket(numeroPuerto);
		
		Socket clienteConectado1 = null;
		Socket clienteConectado2 = null;
		
		//ESPERA A LOS CLIENTES
		System.out.println("Esperando al cliente.....");
		clienteConectado1 = servidor.accept();
		System.out.println("Cliente aceptado");
		System.out.println("Esperando al cliente.....");
		clienteConectado2 = servidor.accept();
		System.out.println("Cliente aceptado");
		
		//CREA INPUT Y OUTPUT
		InputStream entradaCl1 = clienteConectado1.getInputStream();
		DataInputStream flujoEntradaCl1 = new DataInputStream(entradaCl1);
		OutputStream salidaCl1 = clienteConectado1.getOutputStream();
		DataOutputStream flujoSalidaCl1 = new DataOutputStream(salidaCl1);
		
		InputStream entradaCl2 = clienteConectado2.getInputStream();
		DataInputStream flujoEntradaCl2 = new DataInputStream(entradaCl2);
		OutputStream salidaCl2 = clienteConectado2.getOutputStream();
		DataOutputStream flujoSalidaCl2 = new DataOutputStream(salidaCl2);
		
		//CREO LAS VARIABLES DEL PROGRAMA
		boolean juego = true;
		int ronda = 1, contadorcl1 = 0, contadorcl2=0;
		while (juego) {//probar que no sigan jugando cuando uno gane
			
			String jd1 , jd2;
			int jdGanador;//0empate 1JD1 2JD2
			jd1 = flujoEntradaCl1.readUTF();
			jd2 = flujoEntradaCl2.readUTF();
			System.out.println("leoResultados "+ jd1 + jd2);
			if (jd1.compareTo(jd2) == 0) {
				System.out.println("empate");
				jdGanador = 0;
			}
			else {
				if (jd1.equalsIgnoreCase("piedra")) {
					if (jd2.equalsIgnoreCase("papel")) {//J1 PIEDRA J2 PAPEL
						contadorcl2 ++;
						jdGanador = 2;
					}
					else {//J1 PIEDRA J2 TIJERA
						contadorcl1 ++;
						jdGanador = 1;
					}
				}
				else if (jd1.equalsIgnoreCase("papel")) {
					if (jd2.equalsIgnoreCase("tijera")) {//J1 PAPEL J2 TIJERA
						contadorcl2 ++;
						jdGanador = 2;
					}
					else {//J1 PAPEL J2 PIEDRA
						contadorcl1 ++;
						jdGanador = 1;
					}
				}
				else {//J1 TIJERA
					if (jd2.equalsIgnoreCase("piedra")) {//J1 TIJERA J2 piedra
						contadorcl2 ++;
						jdGanador = 2;
					}
					else {//J1 Tijera J2 papel
						contadorcl1 ++;
						jdGanador = 1;
					}
				}
			}
			switch (jdGanador) {
			case 0: {
				flujoSalidaCl1.writeUTF("Hay un empate");
				flujoSalidaCl2.writeUTF("Hay un empate");
				System.out.println("empate");
				break;
			}
			case 1: {
				flujoSalidaCl1.writeUTF("Has ganado el otro jugador 2 saco " + jd2);
				flujoSalidaCl2.writeUTF("Has perdido el otro jugador 1 saco " + jd1);
				System.out.println("gana el jd1");
				break;
						}
			case 2: {
				flujoSalidaCl1.writeUTF("Has perdido el otro jugador saco " + jd2);
				flujoSalidaCl2.writeUTF("Has ganado el otro jugador saco " + jd1);
				System.out.println("gana el jd2");
				break;
			}
			default:
				System.out.println("ERROR");
			}
			flujoSalidaCl1.writeUTF("Ronda " + ronda + " JD1[ "+contadorcl1+" - "+contadorcl2 + " ]JD2");
			flujoSalidaCl2.writeUTF("Ronda " + ronda + " JD1[ "+contadorcl1+" - "+contadorcl2 + " ]JD2");
			ronda++;
			if ((contadorcl1 <3) && (contadorcl2 <3)) {
				flujoSalidaCl1.writeBoolean(true);
				flujoSalidaCl2.writeBoolean(true);
			}
			else {
				flujoSalidaCl1.writeBoolean(false);
				flujoSalidaCl2.writeBoolean(false);
				juego = false;
			}
		}
		if (contadorcl1 ==3) {
			flujoSalidaCl1.writeUTF("Has ganado el juego");
			flujoSalidaCl1.writeUTF("Has perdido el juego");
		}
		else {
			flujoSalidaCl2.writeUTF("Has ganado el juego");
			flujoSalidaCl1.writeUTF("Has perdido el juego");
		}
		flujoEntradaCl1.close();
		flujoEntradaCl2.close();
		flujoSalidaCl1.close();
		flujoSalidaCl2.close();
		entradaCl1.close();
		entradaCl2.close();
		salidaCl1.close();
		salidaCl2.close();
		clienteConectado1.close();
		clienteConectado2.close();
		servidor.close();
	}
}
