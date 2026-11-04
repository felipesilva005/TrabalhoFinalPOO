package animais;
import excecoes.AcessoNegadoException;
import excecoes.DadoInvalidoException;
import usuarios.Usuario;
import usuarios.Veterinario;

public class Animal {
    private static int contId = 1;
    private final int id;
    private String nome;
    private Especie especie;
    private String raca;
    private int idade;
    private Porte porte;
    private EstadoSaude estadoSaude;
    private final String dataResgate;
    private StatusAdocao statusAdocao;

    public Animal(String nome, Especie especie, String raca, int idade, Porte porte, String dataResgate) {
        this.estadoSaude = EstadoSaude.SENDO_ACOMPANHADO;
        this.statusAdocao = StatusAdocao.NAO_DISPONIVEL;

        this.id = contId++;
        this.nome = nome;
        this.especie = especie;
        this.raca = raca;
        this.idade = idade;
        this.porte = porte;
        this.dataResgate = dataResgate;
    }
    
    public int getId(){
        return id;
    }

    public String getNome(){
        return nome;
    }

    public Especie getEspecie(){
        return especie;
    }

    public String getRaca(){
        return raca;
    }

    public int getIdade(){
        return idade;
    }

    public Porte getPorte(){
        return porte;
    }

    public EstadoSaude getEstadoSaude(){
        return estadoSaude;
    }

    public String getDataResgate(){
        return dataResgate;
    }

    public StatusAdocao getStatusAdocao() {
        return statusAdocao;
    }

    public void atualizarEstadoSaude(Usuario responsavel, EstadoSaude novoEstado) throws DadoInvalidoException, AcessoNegadoException {
        if(!(responsavel instanceof Veterinario)) {
            throw new AcessoNegadoException("APENAS VETERINÁRIOS podem alterar o estado de saúde.");

        }

        if(novoEstado == null) {
            throw new DadoInvalidoException("O ESTADO DE SAÚDE não pode ser nulo.");

        }

        this.estadoSaude = novoEstado;

        if(statusAdocao != StatusAdocao.ADOTADO) {
            if(novoEstado == EstadoSaude.SAUDAVEL) {
                this.statusAdocao = StatusAdocao.DISPONIVEL;
            } else {
                this.statusAdocao = StatusAdocao.NAO_DISPONIVEL;
            }
        }
    }   
}
