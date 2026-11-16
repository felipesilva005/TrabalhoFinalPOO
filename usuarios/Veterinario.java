package usuarios;
import animais.Animal;
import animais.EstadoSaude;
import excecoes.AcessoNegadoException;
import excecoes.DadoInvalidoException;
import excecoes.VacinaDuplicadaException;
import saude.Registro;

public class Veterinario extends Usuario {
    private final String crmv;

    private static void validarCrmv(String crmv) throws DadoInvalidoException {
        if(crmv == null || crmv.trim().isEmpty()) { //Checagem de nulo ou vazio.
            throw new DadoInvalidoException("O campo CRMV não pode estar vazio.");

        }
    }
    public Veterinario(String nome, String cpf, String email, String senha, String crmv) throws DadoInvalidoException{
        validarCrmv(crmv);

        super(nome, cpf, email, senha);
        this.crmv = crmv.toUpperCase();
    }

    public String getCrmv(){
        return crmv;
    }

    @Override
    public void exibirMenu() {
        System.out.println("\n---------------------------------------------");
        System.out.println("             ÁREA DO VETERINÁRIO");
        System.out.println("---------------------------------------------");
        System.out.println("1 - Consultar Animais");
        System.out.println("2 - Registrar Atendimento");
        System.out.println("3 - Atualizar Estado de Saúde");
        System.out.println("4 - Ver Histórico Médico");
        System.out.println("5 - Vizualizar Notificações");
        System.out.println("6 - Sair");
    }

    public void atualizarEstadoSaude(Animal animal, EstadoSaude novoEstado) throws DadoInvalidoException, AcessoNegadoException {
        animal.atualizarEstadoSaude(this, novoEstado);
    }

    public void registrarAtendimento(Animal animal, Registro registro) throws AcessoNegadoException, DadoInvalidoException, VacinaDuplicadaException {
        animal.adicionarRegistro(this, registro);
    }
}