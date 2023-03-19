import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        //Prueba de funcionamiento Partido
        Path path = Paths.get("resultados.csv");
        BufferedReader bf = new BufferedReader(new FileReader(path.toFile()));
        String linea = "";

        ArrayList<Partido> partidos = new ArrayList<Partido>();

        while ((linea = bf.readLine()) != null){
            String[] linea_array = linea.split(";");
            Equipo equipo1 = new Equipo(linea_array[0]);
            Equipo equipo2 = new Equipo(linea_array[3]);

            Partido partido = new Partido(equipo1, equipo2, Integer.parseInt(linea_array[1]), Integer.parseInt(linea_array[2]));
            partidos.add(partido);

        }
        Ronda ronda = new Ronda(1, partidos.get(0), partidos.get(1));
        VerRonda(ronda);



        System.out.println("Escribir solo ganador/perdedor/empate");
        for (int i = 0; i < ronda.getPartidos().length; i++) {
            System.out.println("Pronostico para: "+ronda.getPartidos()[i].getEquipo1().getNombre());
            RESULTADO resultado_pronostico = StringAResultado(scanner.nextLine());
            Pronostico pronostico = new Pronostico(ronda.getPartidos()[i],ronda.getPartidos()[i].getEquipo1(), resultado_pronostico);
            ronda.puntos += pronostico.puntos;
        }
        System.out.println("Puntos de la ronda: " + ronda.puntos);
    }

    public static void VerRonda(Ronda ronda){//no es importante, es solo visual
        for (int i = 0; i < ronda.getPartidos().length; i++) {
            System.out.println(ronda.getPartidos()[i].getEquipo1().getNombre()+" vs "+ronda.getPartidos()[i].getEquipo2().getNombre());
        }
        System.out.println("-------------------------------");
    }

    public static RESULTADO StringAResultado(String resultado){
        resultado = resultado.toLowerCase();
        if (resultado.equals("ganador")){
            return RESULTADO.GANADOR;
        }else if(resultado.equals("perdedor")){
            return RESULTADO.PERDEDOR;
        }else {
            return RESULTADO.EMPATE;}
    }
}