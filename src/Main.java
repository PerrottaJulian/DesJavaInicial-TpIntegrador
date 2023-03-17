import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //Prueba de funcionamiento Partido
        Equipo argentina = new Equipo("Argentina", "El mejor pais del mundo");
        Equipo arabia = new Equipo("Arabia Saudita", "Los odio");

        Equipo polonia = new Equipo("Polonia", "Lewandowski y 10 mas");
        Equipo mexico = new Equipo("Mexico", "Son malisimos");

        Partido partido = new Partido(argentina, arabia, 1,2);
        Partido partido2 = new Partido(polonia, mexico, 0, 0);

        Ronda ronda = new Ronda(1, partido, partido2);
        VerRonda(ronda);

        //Pronostico pronostico = new Pronostico(partido, argentina, RESULTADO.EMPATE);
        //System.out.println(pronostico.puntos);
        // aparte, obviamente integras los archivos

        System.out.println("Escribir solo ganador/perdedor/empate");
        for (int i = 0; i < ronda.getPartidos().length; i++) {
            System.out.println("Pronostico para: "+ronda.getPartidos()[i].getEquipo1().getNombre());
            RESULTADO resultado_pronostico = PasarStringAResultado(scanner.nextLine());
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

    public static RESULTADO PasarStringAResultado(String resultado){
        resultado = resultado.toLowerCase();
        if (resultado.equals("ganador")){
            return RESULTADO.GANADOR;
        }else if(resultado.equals("perdedor")){
            return RESULTADO.PERDEDOR;
        }else {
            return RESULTADO.EMPATE;}
    }
}