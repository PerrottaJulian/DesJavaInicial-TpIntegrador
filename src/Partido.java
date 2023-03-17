public class Partido {
    public Partido(Equipo equipo1, Equipo equipo2, int goles_equipo1, int goles_equipo2){
        this.equipo1 = equipo1;
        this.equipo2 = equipo2;
        this.goles_equipo1 = goles_equipo1;
        this.goles_equipo2 = goles_equipo2;
        if (goles_equipo1 > goles_equipo2){
            setResultado(RESULTADO.GANADOR);
        }else if(goles_equipo1 < goles_equipo2){
            setResultado(RESULTADO.PERDEDOR);
        }else{
            setResultado(RESULTADO.EMPATE);
        }
    }
    private Equipo equipo1;
    private Equipo equipo2;
    private int goles_equipo1;
    private int goles_equipo2;
    public RESULTADO resultado;

    public void setResultado(RESULTADO resultado){
        this.resultado = resultado;
    }

    public Equipo getEquipo1() {
        return equipo1;
    }

    public Equipo getEquipo2() {
        return equipo2;
    }
}
