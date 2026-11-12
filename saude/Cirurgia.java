package saude;
import usuarios.Veterinario;
import excecoes.*;
import java.time.LocalDate;

public class Cirurgia extends Registro{
    private final String procedimento;

    public Cirurgia(LocalDate data, Veterinario vet, String procedimento) throws DadoInvalidoException  {
        this.procedimento = exigirTexto(procedimento, "Procedimento Cirurgico");
        super(data, vet);

    }

    public String getProcedimento() {
        return procedimento;
    }

    @Override 
    public String descrever(){
        return "A cirurgia de " + getProcedimento() + ", será realizada dia " + getData();
        
    }
}
