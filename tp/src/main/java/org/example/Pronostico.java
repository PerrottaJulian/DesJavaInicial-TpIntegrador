package org.example;

public class Pronostico {
    public Pronostico(String participante, Partido partido, RESULTADO resultado){
        this.participante = participante;
        this.partido = partido;
        this.resultado = resultado;
        if (partido.resultado == this.resultado){
            acierta = true;
        }
    }
    private String participante;
    private Partido partido;
    private RESULTADO resultado;
    public boolean acierta = false;


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

    public String getParticipante() {
        return participante;
    }
}
