package usuarios;
import animais.*;
import excecoes.*;

public class Administrador extends Usuario {
    public Administrador(String nome, String cpf, String email, String senha) throws DadoInvalidoException{
        super(nome, cpf, email, senha);

    }

    @Override
    public void exibirMenu() {
    System.out.println("\n-----------------------------------------");
    System.out.println("            ÁREA DO ADMINISTRADOR");
    System.out.println("-----------------------------------------");
    System.out.println("1 - Cadastrar Animal");
    System.out.println("2 - Listar Animais");
    System.out.println("3 - Remover Animal");
    System.out.println("4 - Listar Usuários");
    System.out.println("5 - Aprovar ou Rejeitar Adoções");
    System.out.println("6 - Registrar Devolução");
    System.out.println("7 - Relatórios");
    System.out.println("8 - Estatistícas");
    System.out.println("9 - Vizualizar Notificações");
    System.out.println("10 - GerenciarEventos");
    System.out.println("0 - Sair");

    }

    public void concluirAdocao(Animal animal) throws AcessoNegadoException, AnimalIndisponivelException {
        animal.marcarComoAdotado(this);

    } 

    public void receberDevolucao(Animal animal) throws AcessoNegadoException, AnimalIndisponivelException {
        animal.registrarDevolucao(this);

    } 
}