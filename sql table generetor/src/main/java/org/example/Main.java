package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws SQLException, IOException {
        try {
            /*Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/torneo", "root", "my.jperrotta123");
            Statement stmt = con.createStatement();


            ResultSet rs=stmt.executeQuery("SELECT * FROM actor WHERE first_name LIKE 'j%'");
            while(rs.next()) System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));

            BufferedReader bf = new BufferedReader(new FileReader("resultados.csv"));
            String linea;
            int nronda = 0;

            while((linea = bf.readLine()) != null){
                Random ran = new Random();
                String[] resultado = linea.split(";");
                int ronda = Integer.parseInt(resultado[0]);
                if (ronda != nronda) {
                    setRonda(stmt, ronda);
                    nronda = ronda;
                }
                String local = resultado[1];
                int goles_local = Integer.parseInt(resultado[2]);
                String visitante = resultado[4];
                int goles_visitante = Integer.parseInt(resultado[3]);

                int id = ran.nextInt(10000, 30000);

                setPartido(stmt, id, ronda, local, goles_local, visitante, goles_visitante);
                //System.out.println(getIDEquipo(stmt, linea));
            }
            BufferedReader bf = new BufferedReader(new FileReader("pronostico.csv"));
            String[] participantes = new String[]{"JULIAN", "FLOR", "LUCIANO"};
            String linea;

            while ((linea = bf.readLine()) != null){
                Random ran = new Random();
                String[] pronostico = linea.split(" vs ");
                String local = pronostico[0];
                String visitante = pronostico[1];
                int id = ran.nextInt(1000,10000);

                ResultSet partido = stmt.executeQuery("SELECT partido_id FROM partido WHERE local_id="+getIDEquipo(stmt, local)+" AND visitante_id="+getIDEquipo(stmt, visitante));
                System.out.println(local+ " "+visitante);
                int partido_id = 0;
                while (partido.next()){partido_id = partido.getInt(1);}

                int aux = ran.nextInt(1,4);
                switch (aux){
                    case 1:
                        stmt.execute("INSERT INTO pronostico " +
                                "VALUES ( "+id+", 'julian', "+partido_id+", "+getIDEquipo(stmt, local)+")");
                        break;
                    case 2:
                        stmt.execute("INSERT INTO pronostico " +
                                "VALUES ( "+id+", 'julian', "+partido_id+", 0)");
                        break;
                    case 3:
                        stmt.execute("INSERT INTO pronostico " +
                                "VALUES ( "+id+", 'julian', "+partido_id+", "+getIDEquipo(stmt, visitante)+")");
                        break;

                }
            }*/

        } catch (Exception e){
            System.out.println(e);
        }
    }

    /*public static void setRonda(Statement statement, int n) throws SQLException {
        statement.execute("INSERT INTO ronda\n"+
                "VALUES("+n+", 'Ronda"+n+"')");
    }

    public static int getIDEquipo(Statement statement, String equipo) throws SQLException {
        ResultSet rs= statement.executeQuery("SELECT equipo_id FROM equipo WHERE nombre LIKE '"+equipo+"'");
        int id = 0;
        while (rs.next()){
             id = rs.getInt(1);};
        return id;
    }

    public static void setPartido(Statement statement, int id, int ronda_id, String local, int goles_local, String visitante, int goles_visitante) throws SQLException {
        statement.execute("INSERT INTO partido\n"+
                "VALUES("+id+", "+ronda_id+", "+getIDEquipo(statement,local)+", "+goles_local+", "+getIDEquipo(statement,visitante)+", "+goles_visitante+")");
    }*/

}