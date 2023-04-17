package org.example;

import com.google.protobuf.Value;

import java.io.BufferedReader;
import java.io.IOException;
import java.security.Key;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        String[] participantes = new String[]{"JULIAN", "FLOR", "LUCIANO"}; //lista de participantes que uso mas adelante

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/torneo", "root", "my.jperrotta123");
            Statement stmt = con.createStatement(); //creo conexion con la base de datos y el statement con el que podes acceder a los codigos SQL
            Statement stmt_temp = con.createStatement(); //como tengo que usar statement y resulsets en algunas funciones y no puedo tener 2 resultsets a la vez creoq este que es temporal, y asi funciona bien


            for (int i = 1; i <= getNRondas(stmt); i++) {//con getNRondas saco de la DB la cant de rondas que tengo (ahora 4)
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
                    print(local+" vs "+visitante+" -> "+ partido.resultado); //creo un objeto Partido con todos los datos sacados del RS

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
                        print(pronostico.getParticipante() + ": "+ pronostico.getResultadoString() +" puntos: " + pronostico.puntos); //muestro y veo si el participante acerto
                    }
                    print("-------------------------------"); // por ahora todo esto funciona perfecto



                }

                print("*************************************************************");

            }
        } catch (Exception e) {
            System.out.println(e);
        }



        /*ArrayList<Ronda> rondas = new ArrayList<Ronda>();

        Ronda ronda = crearRonda(1, new BufferedReader(new FileReader("resultados.csv")));
        VerRonda(ronda);

        for (Partido partido : ronda.partidos){
            BufferedReader bf_pronostico = new BufferedReader(new FileReader("pronostico.csv"));
            String linea;
            while ( (linea = bf_pronostico.readLine()) != null ){
                if (linea.contains(partido.getLocal().getNombre())){
                    break;
                }
            }

            Pronostico pronostico = new Pronostico(partido, setResultadoPronostico(linea));
            System.out.println("org.example.Pronostico para " + pronostico.getPartido().getLocal().getNombre() + " vs " +
                    pronostico.getPartido().getVisitante().getNombre() + ": " + pronostico.getResultadoString());
            ronda.puntos += pronostico.puntos;
        }
        System.out.println("-----------------------------");
        System.out.println("Puntos de la ronda " + ronda.getNumero() + ": " + ronda.puntos);*/


    } // FIN MAIN


    /*public static Ronda crearRonda(int n, BufferedReader bf) throws IOException {
        Ronda ronda = new Ronda(n);

        String linea;
        while ((linea = bf.readLine()) != null) {
            String[] resultado = linea.split(";");
            Equipo local = new Equipo(resultado[0]);
            Equipo visitante = new Equipo(resultado[3]);
            Partido partido = new Partido(local, visitante, Integer.parseInt(resultado[1]), Integer.parseInt(resultado[2]));
            ronda.partidos.add(partido);
        }
        return ronda;
    }*/


    public static RESULTADO setResultadoPronostico(String linea) throws IOException {
        RESULTADO resultado = null;
        String[] pronostico = linea.split(";");
        for (int i = 0; i < pronostico.length; i++) {
            if (pronostico[i].equals("x")) {
                switch (i) {
                    case 1:
                        resultado = RESULTADO.GANADOR_LOCAL;
                        break;
                    case 2:
                        resultado = RESULTADO.EMPATE;
                        break;
                    case 3:
                        resultado = RESULTADO.GANADOR_VISITANTE;
                        break;

                }
            }
        }
        return resultado;
    }

    public static void VerRonda(Ronda ronda) {//solo visual
        System.out.println("org.example.Ronda " + ronda.getNumero());
        for (Partido partido : ronda.partidos) {
            System.out.println(partido.getLocal().getNombre() + " vs " + partido.getVisitante().getNombre());
        }
        System.out.println("-------------------------------");
    }
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






}