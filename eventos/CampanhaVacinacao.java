package eventos;
import java.time.LocalDate;
import excecoes.DadoInvalidoException;
import excecoes.InscricaoDuplicadaException;

public class CampanhaVacinacao extends Evento {  
    public CampanhaVacinacao(String titulo, LocalDate data, String local) throws DadoInvalidoException, InscricaoDuplicadaException {
        super(titulo, data, local);

    }

    @Override
    public String descricaoTipo() {
        return "Campanha de Vacinação";
        
    }
}