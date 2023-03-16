public class Main {
    public static void main(String[] args) {
        //Prueba de funcionamiento Partido
        Equipo argentina = new Equipo("Argentina", "El mejor pais del mundo");
        Equipo arabia = new Equipo("Arabia Saudita", "Los odio");

        Partido partido = new Partido(argentina, arabia, 3,2);

        System.out.println(partido.resultado);
        // asignar resultado automaticamente funciona piola

    }
}