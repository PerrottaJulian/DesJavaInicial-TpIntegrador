public class Main {
    public static void main(String[] args) {
        //Prueba de funcionamiento Partido
        Equipo argentina = new Equipo("Argentina", "El mejor pais del mundo");
        Equipo arabia = new Equipo("Arabia Saudita", "Los odio");

        Equipo polonia = new Equipo("Polonia", "Lewandowski y 10 mas");
        Equipo mexico = new Equipo("Mexico", "Son malisimos");

        Partido partido = new Partido(argentina, arabia, 2,2);
        Partido partido2 = new Partido(polonia, mexico, 0, 0);

        Ronda ronda = new Ronda(1, partido, partido2);
        VerRonda(ronda);

        Pronostico pronostico = new Pronostico(partido, argentina, RESULTADO.EMPATE);
        //System.out.println(pronostico.puntos);
        //sumas puntos en Pronostico funciona bien, pero falta detallar el uso de Equipo
        // tambien falta sumar los puntos conseguidos a los puntos de la ronda
        // aparte, obviamente integras los archivos

    }

    public static void VerRonda(Ronda ronda){//no es importante, es solo visual
        for (int i = 0; i < ronda.getPartidos().length; i++) {
            System.out.println(ronda.getPartidos()[i].getEquipo1().getNombre()+" vs "+ronda.getPartidos()[i].getEquipo2().getNombre());
        }
        System.out.println("-------------------------------");
    }
}