public class Pronostico {
    public Pronostico(Partido partido, Equipo equipo, RESULTADO resultado){
        this.partido = partido;
        this.equipo = equipo;
        this.resultado = resultado;
        if (partido.resultado == this.resultado){
            puntos++;
        }
    }
    private Partido partido;
    private Equipo equipo;
    private RESULTADO resultado;
    public int puntos = 0;


}
