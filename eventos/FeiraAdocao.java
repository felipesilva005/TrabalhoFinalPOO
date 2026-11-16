package eventos;
import java.time.LocalDate;
import excecoes.DadoInvalidoException;
import excecoes.InscricaoDuplicadaException;

public class FeiraAdocao extends Evento {  
    public FeiraAdocao(String titulo, LocalDate data, String local) throws DadoInvalidoException, InscricaoDuplicadaException {
        super(titulo, data, local);

    }

    @Override
    public String descricaoTipo() {
        return "Feira de Adoção";
        
    }
}