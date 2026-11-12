package servico;
import animais.*;
import excecoes.*;
import usuarios.*;
import java.util.List;
import java.util.ArrayList;

public class GerenciadorAnimais {
    private final List<Animal> animaisList = new ArrayList<>(); //Lista com os animais cadastrados.

    private static void acessadoPorAdministrador(Usuario u) throws AcessoNegadoException {
        if(!(u instanceof Administrador)) {
            throw new AcessoNegadoException("Apenas ADMINISTRADORES podem realizar está ação.");

        }
    }

    public Animal buscarPorId(int id) throws AnimalNaoEncontradoException {
        for(Animal i : animaisList) {
            if(i.getId() == id) {
                return i;
            }
        }

        throw new AnimalNaoEncontradoException("O Animal ID(" + id + ") não foi encontrado.");
    }

    public void cadastrarAnimais(Usuario responsavel, Animal animal) throws AcessoNegadoException, AnimalNaoEncontradoException {
        acessadoPorAdministrador(responsavel);
        animaisList.add(animal);

    }

    public void removerAnimais(Usuario responsavel, int id) throws AcessoNegadoException, AnimalNaoEncontradoException, RemocaoNaoPermitidaException {
        acessadoPorAdministrador(responsavel);
        Animal animal = buscarPorId(id);

        if(animal.getStatusAdocao() == StatusAdocao.ADOTADO) {
            throw new RemocaoNaoPermitidaException("Animais adotados não devem ser removidos.");
        }

        animaisList.remove(animal);

    }

    //Filtros de busca:
    public List<Animal> filtrarStatus(StatusAdocao status) {
        List<Animal> resultado = new ArrayList<>();

        for(Animal a : animaisList) {
            if(a.getStatusAdocao() == status) {
                resultado.add(a);
            }
        }

        return resultado;

    }

    public List<Animal> filtrarEspecie(Especie especie) {
        List<Animal> resultado = new ArrayList<>();

        for(Animal a : animaisList) {
            if(a.getEspecie() == especie) {
                resultado.add(a);
            }
        }

        return resultado;

    }

    public List<Animal> buscaPorNome(String nome) {
        List<Animal> resultado = new ArrayList<>();

        for(Animal a : animaisList) {
            if(a.getNome().toLowerCase().contains(nome.trim().toLowerCase())) { /*Nessa condição ele busca os animais pelo nome, para isso
                ele deixa tanto o nome da busca em caixa baixa como o nome cadastrado.*/
                resultado.add(a);
            }
        }

        return resultado;

    }

    public List<Animal> listarsDisponiveis() {
        return filtrarStatus(StatusAdocao.DISPONIVEL);

    }

    public List<Animal> listarTodos(){
        return new ArrayList<> (animaisList);
    }
}
