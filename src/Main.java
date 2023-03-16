public class Main {
    public static void main(String[] args) {
        //Prueba de funcionamiento Partido
        Equipo argentina = new Equipo("Argentina", "El mejor pais del mundo");
        Equipo arabia = new Equipo("Arabia Saudita", "Los odio");

        Equipo polonia = new Equipo("Polonia", "Lewandowski y 10 mas");
        Equipo mexico = new Equipo("Mexico", "Son malisimos");

        Partido partido = new Partido(argentina, arabia, 3,2);
        Partido partido2 = new Partido(polonia, mexico, 0, 0);

        /*System.out.println(partido.resultado);
        System.out.println(partido2.resultado);*/

        // asignar resultado automaticamente funciona piola

        Ronda ronda = new Ronda(1, partido, partido2);
        System.out.println(ronda.puntos);
        ronda.SumarPuntos(2);
        ronda.SumarPuntos(5);
        System.out.println(ronda.puntos); // sumar puntos funciona bien

        //faltaria interaccion entre Pronosticos y Partidos/Rondas

    }
}