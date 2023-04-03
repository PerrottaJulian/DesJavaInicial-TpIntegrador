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


    public Partido getPartido() {
        return partido;
    }

    public String getResultadoString(){
        if (resultado == RESULTADO.GANADOR_LOCAL){
            return "Gana " + partido.getLocal().getNombre();
        }else if(resultado == RESULTADO.GANADOR_VISITANTE){
            return "Gana " + partido.getVisitante().getNombre();
        }else{
            return "Empate";
        }
    }
}
