package usuarios;
import excecoes.DadoInvalidoException;

public class Voluntario extends Usuario {
    public Voluntario(String nome, String cpf, String email, String senha) throws DadoInvalidoException{
        super(nome, cpf, email, senha);

    }

    @Override
    public void exibirMenu() {
        System.out.println("\n--------------------------------------------");
        System.out.println("              ÁREA DO VOLUNTÁRIO");
        System.out.println("--------------------------------------------");
        System.out.println("1 - Visualizar Animais Disponíveis");
        System.out.println("2 - Solicitar Adoção");
        System.out.println("3 - Consultar Minhas Solicitações");
        System.out.println("4 - Notificações");
        System.out.println("5 - Participar de Eventos");
        System.out.println("6 - Histórico de Participação");
        System.out.println("7 - Sair");
    }
}