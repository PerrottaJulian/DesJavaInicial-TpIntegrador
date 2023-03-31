public class Partido {
    public Partido(Equipo local, Equipo visitante, int goles_local, int goles_visitante){
        this.local = local;
        this.visitante = visitante;
        this.goles_local = goles_local;
        this.goles_visitante = goles_visitante;
        if (goles_local > goles_visitante){
            setResultado(RESULTADO.GANADOR_LOCAL);
        }else if(goles_local < goles_visitante){
            setResultado(RESULTADO.GANADOR_VISITANTE);
        }else{
            setResultado(RESULTADO.EMPATE);
        }
    }
    private Equipo local;
    private Equipo visitante;
    private int goles_local;
    private int goles_visitante;
    public RESULTADO resultado;

    public void setResultado(RESULTADO resultado){
        this.resultado = resultado;
    }

    public Equipo getLocal() {
        return local;
    }
    public Equipo getVisitante() {
        return visitante;
    }
}
