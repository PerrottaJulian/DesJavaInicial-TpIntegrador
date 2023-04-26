package org.example;

import com.google.protobuf.Value;

import java.io.BufferedReader;
import java.io.IOException;
import java.security.Key;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        ArrayList<String> participantes = new ArrayList<>();

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/torneo", "root", "my.jperrotta123");
            Statement stmt = con.createStatement(); //creo conexion con la base de datos y el statement con el que podes acceder a los codigos SQL
            Statement stmt_temp = con.createStatement(); //como tengo que usar statement y resulsets en algunas funciones y no puedo tener 2 resultsets a la vez creoq este que es temporal, y asi funciona bien

            setParticipantesList(stmt, participantes); //saco a los participantes de la DB

            HashMap<String, Integer> puntajes = new HashMap<>();// Coleccion en donde voy a guardar los puntajes totales de cada participante
            HashMap<String, Integer> aciertos = new HashMap<>(); //aciertos me sirve para calcular si uno adivino todos los resultados de una ronda
            for (String participante: participantes){
                puntajes.put(participante, 0); // para inicializar el Map
                aciertos.put(participante, 0);
            }

            // empieza la ronda
            for (int i = 1; i <= getNRondas(stmt); i++) {  //con getNRondas saco de la DB la cant de rondas que tengo (ahora 4)
                int ronda_id = i; //saco el numero de ronda con el que filtrar (primero 1, dps 2, etc)
                Ronda ronda = new Ronda(ronda_id, getNombreFromIdRonda(stmt_temp, ronda_id));
                print(ronda.getNombre());
                ResultSet rs = stmt.executeQuery("SELECT * FROM partido WHERE ronda_id = " + ronda_id); //filtro todos los partidos que son de la ronda x

                while(rs.next()){ //lee cada linea del result set (que seria cada partido)
                    int partido_id = rs.getInt(1);
                    String local = getNombreFromIdEquipo(stmt_temp, rs.getInt(3));
                    String visitante = getNombreFromIdEquipo(stmt_temp,rs.getInt(5));
                    int goles_local = rs.getInt(4);
                    int goles_visitante = rs.getInt(6);
                    //guardo todos los datos en variables para mejor uso

                    Partido partido = new Partido(new Equipo(local), new Equipo(visitante), goles_local, goles_visitante);
                    print(partido.getLocal().getNombre()+" vs "+partido.getVisitante().getNombre()+" = "+ partido.getResultadoString()); //creo un objeto Partido con todos los datos sacados del RS

                    for (String participante: participantes){
                        RESULTADO resultado = null;
                        int ganador_id = getIdGanador(stmt_temp, partido_id, participante ); //filtro los pronosticos de cada participante de el partido actual (son 3 participantes entonces 3)
                        if (ganador_id == 0){
                            resultado = RESULTADO.EMPATE;
                        } else if (ganador_id == rs.getInt(3)) {
                            resultado = RESULTADO.GANADOR_LOCAL;
                        } else if (ganador_id == rs.getInt(5)) {
                            resultado = RESULTADO.GANADOR_VISITANTE;
                        } // defino si el id del equipo que el participante eligio ganador es originalmente el local o visitante (o si es 0 empate) y defino un valor enum RESULTADO a una variable

                        Pronostico pronostico = new Pronostico(participante, partido, resultado); //creo un objeto pronostico con el nombre del paticipante, el objeto partido del que estamos hablando y el enum resultado
                        print(pronostico.getParticipante() + ": "+ pronostico.getResultadoString() +" -->> "+ pronostico.isAcertado()); //muestro y veo si el participante acerto


                        //aumento el valor de los puntos en los Map de puntajes y aciertos, si el resultado coincide
                        if (pronostico.isAcertado()){
                            aciertos.replace(pronostico.getParticipante(), aciertos.get(pronostico.getParticipante()), aciertos.get(pronostico.getParticipante()) + 1);
                            puntajes.replace(pronostico.getParticipante(), puntajes.get(pronostico.getParticipante()), puntajes.get(pronostico.getParticipante()) + 1);
                        }

                    }
                    print("-------------------------------");
                }//(termina cada partido de la ronda)

                print("\nAciertos "+aciertos);
                for (String participante: participantes){ // suma un punto extra si el participante acerto todos los resultados de una ronda, cosa que pasa con LUCIANO en la Ronda3
                    if (aciertos.get(participante) == 6){
                        puntajes.replace(participante, puntajes.get(participante), puntajes.get(participante) + 1);
                        print("punto extra para "+participante+" por acertar todos los resultados de la "+ronda.getNombre());
                    }
                }

                print("\n*************************************************************");


                for (String participante: participantes){
                    aciertos.put(participante, 0); //inicializo devuelta aciertos
                }

            }//(termina la ronda) y pasa a la siguiente

            //al final del torneo

            print("Puntajes: "+puntajes);
            mostrarGanadoryPerdedor(puntajes);

        } catch (Exception e) {
            System.out.println(e);
        }//fin del torneo

    } // FIN MAIN



    public static int getNRondas(Statement statement) throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT ronda_id FROM ronda");
        int ult_ronda = 0;
        while(resultSet.next()){
            ult_ronda = resultSet.getInt(1);
        }
        return ult_ronda;
    }

    public static int getIDEquipo(Statement statement, String equipo) throws SQLException {
        ResultSet rs= statement.executeQuery("SELECT equipo_id FROM equipo WHERE nombre LIKE '"+equipo+"'");
        int id = 0;
        while (rs.next()){
            id = rs.getInt(1);};
        return id;
    }

    public static String getNombreFromIdEquipo(Statement statement, int id) throws SQLException {
        ResultSet rs= statement.executeQuery("SELECT nombre FROM equipo WHERE equipo_id = "+id);
        String nombre_equipo = null;
        while (rs.next()){
            nombre_equipo = rs.getString(1);};
        return nombre_equipo;
    }

    public static String getNombreFromIdRonda(Statement statement, int id) throws SQLException {
        ResultSet rs= statement.executeQuery("SELECT nombre FROM ronda WHERE ronda_id = "+id);
        String nombre_ronda = null;
        while (rs.next()){
            nombre_ronda = rs.getString(1);};
        return nombre_ronda;
    }
    public static void print(String string){
        System.out.println(string);
    }

    public static int getIdGanador(Statement statement, int partido_id, String participante) throws SQLException {
        int id_ganador = 0;
        ResultSet rs = statement.executeQuery("SELECT id_ganador FROM pronostico WHERE partido_id = "+partido_id+" " +
                "AND participante = '"+participante+"'");

        while (rs.next()){
            //tendria que comparar el id del equipo que saque y compararlo con los ids de los equipos del partido, para asi saber si el equipo que se pronostico es el local o el visitante
            id_ganador = rs.getInt(1);
        }
        return id_ganador;

    }

    public static void setParticipantesList (Statement statement, ArrayList<String> lista) throws SQLException {
        ResultSet rs = statement.executeQuery("SELECT participante FROM pronostico"); //creo lista de participantes desde la DB
        while(rs.next()){
            String nombre = rs.getString(1);
            if (!lista.contains(nombre)){
                lista.add(nombre);
            }
        }
    }

    public static void mostrarGanadoryPerdedor(HashMap<String,Integer> puntajes){
        String[] nombres = puntajes.keySet().toArray(new String[0]);
        Integer max = -1;
        Integer min = 999999;
        String ganador = null;
        String perdedor = null;
        int i = 0;

        for (Integer puntos: puntajes.values()){
            if (puntos > max){
                max = puntos;
                ganador = nombres[i];
            }
            if (puntos < min){
                min = puntos;
                perdedor = nombres[i];
            }
            i++;
        }

        print("EL GANADOR FUE: " + ganador + "\nEl PERDEDOR FUE: " + perdedor);
    }






}