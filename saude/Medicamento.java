package saude;
import usuarios.Veterinario;
import excecoes.*;
import java.time.LocalDate;

public class Medicamento extends Registro {
    private final String nome;
    private final String modoUsar;

    public Medicamento(LocalDate data, Veterinario vet, String nome, String modoUsar) throws DadoInvalidoException {
        super(data, vet);
        this.nome = exigirTexto(nome, "Nome do Medicamento");
        this.modoUsar = exigirTexto(modoUsar, "Modo de Usar/Dosagem");

    }

    public String getNome() {
        return nome;
    }

    public String getModoUsar() {
        return modoUsar;
    }

    @Override 
    public String descrever(){
        return "O tratamento com o medicamento " + getNome() + ", inicia dia " + getData() + ", Com as seguintes instruções " + getModoUsar();
    }
}
