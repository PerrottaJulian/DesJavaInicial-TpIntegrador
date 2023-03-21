public class Pronostico {
    public Pronostico(Partido partido, RESULTADO resultado){
        this.partido = partido;
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
