package juegoEj2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Random;

public class ServerHilo extends Thread{
	int ronda = 1, contadorcl1 = 0, Jserver=0;
	boolean juego = true;
	Socket clienteConectado = null;
	private int partida = 0;
	public ServerHilo(Socket a, int partida) {
		clienteConectado = a;
		this.partida = partida;
	}
	@Override
	public void run() {
		//CREA INPUT Y OUTPUT
				InputStream entradaCliente = null;
				OutputStream salidaCliente = null;
				try {
					entradaCliente = clienteConectado.getInputStream();
					DataInputStream flujoEntradaCliente = new DataInputStream(entradaCliente);
					salidaCliente = clienteConectado.getOutputStream();
					DataOutputStream flujoSalidaCliente = new DataOutputStream(salidaCliente);
					
					
					while (juego) {//probar que no sigan jugando cuando uno gane
						//jugadores cada jugada
						String jd1 , servidor;
						
						int jdGanador;//0empate 1JD1 2JD2
						//Recibo lo que juega el jd
						jd1 = flujoEntradaCliente.readUTF();
						
						servidor = sacaJugador();
						System.out.println(toString()+"El server saca "+ servidor);
						System.out.println(toString()+"El Cliente saca "+ jd1);
						//Envio lo que juego 
						flujoSalidaCliente.writeUTF(servidor);
						
						//llama al metodo para calcular el jugador 0 es empate, 1 gana el cliente y 2 gana el server
						jdGanador = calculaGanador(jd1, servidor);
						
						switch (jdGanador) {
						case 0: {
							System.out.println(toString()+ "empate");
							break;
						}
						case 1: {
							System.out.println(toString()+ "Ha ganado el JUGADOR");
							break;
									}
						case 2: {
							System.out.println(toString()+ "Ha ganado el SERVER");
							break;
						}
						default:
							System.out.println(toString()+ "ERROR");
						}
						System.out.println(toString()+ "Ronda " + ronda + " Jugador[ "+contadorcl1+" - "+Jserver + " ]Servidor");
						
						ronda++;
						//Contador de victorias
						if ((contadorcl1 <3) && (Jserver <3)) {
							juego = true;
							
						}
						else {
							juego = false;
						}
					}
					if (contadorcl1 ==3) {
						flujoSalidaCliente.writeUTF(toString()+ "Has ganado el juego");
						System.out.println( toString()+ "gana el cliente");
					}
					else {
						flujoSalidaCliente.writeUTF(toString()+ "Has perdido el juego");
						System.out.println(toString()+ "gana el server");
					}
					flujoEntradaCliente.close();
					flujoSalidaCliente.close();
					entradaCliente.close();
					salidaCliente.close();
					clienteConectado.close();
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				
				
	}
	public int calculaGanador(String jd1,String jd2) {
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
	public String sacaJugador() {
		Random random = new Random();
		  int num = random.nextInt(3) + 1;
		if (num == 1) {
			return "PIEDRA";
		} else if (num == 2) {
			return "PAPEL";
		} else {
			return "TIJERA";
		} 
	}
	@Override
	public String toString() {
		return "[Partida " + partida + "] ";
	}
}
