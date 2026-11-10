package saude;
import usuarios.Veterinario;
import excecoes.*;
import java.time.LocalDate;

public class Cirurgia extends Registro{
    private final String procedimento;

    public Cirurgia(LocalDate data, Veterinario veterinario, String procedimento) throws DadoInvalidoException {
        super(data, veterinario);
        this.procedimento = exigirTexto(procedimento, "Procedimento Cirurgico");

    }

    public String getProcedimento() {
        return procedimento;
    }

    @Override 
    public String descrever(){
        return "A cirugia de " + getProcedimento() + ", será realizada dia " + getData();
        
    }
}
