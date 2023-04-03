import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        ArrayList<Ronda> rondas = new ArrayList<Ronda>();

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
            System.out.println("Pronostico para " + pronostico.getPartido().getLocal().getNombre() + " vs " +
                    pronostico.getPartido().getVisitante().getNombre() + ": " + pronostico.getResultadoString());
            ronda.puntos += pronostico.puntos;
        }
        System.out.println("-----------------------------");
        System.out.println("Puntos de la ronda " + ronda.getNumero() + ": " + ronda.puntos);
    }

    public static Ronda crearRonda(int n, BufferedReader bf) throws IOException{
        Ronda ronda = new Ronda(n);

        String linea;
        while ((linea = bf.readLine()) != null){
            String[] resultado = linea.split(";");
            Equipo local = new Equipo(resultado[0]);
            Equipo visitante = new Equipo(resultado[3]);
            Partido partido = new Partido(local, visitante, Integer.parseInt(resultado[1]), Integer.parseInt(resultado[2]));
            ronda.partidos.add(partido);
        }
        return ronda;
    }


    public static RESULTADO setResultadoPronostico (String linea) throws IOException {
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

    public static void VerRonda(Ronda ronda){//solo visual
        System.out.println("Ronda " + ronda.getNumero());
        for (Partido partido : ronda.partidos) {
            System.out.println(partido.getLocal().getNombre() + " vs "+ partido.getVisitante().getNombre());
        }
        System.out.println("-------------------------------");
    }



}