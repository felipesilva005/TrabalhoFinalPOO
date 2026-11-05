package animais;
import excecoes.*;
import usuarios.*;
import java.time.LocalDate;

public class Animal {
    private static int contId = 1;
    private final int id;
    private String nome;
    private final Especie especie;
    private String raca;
    private int idade;
    private Porte porte;
    private EstadoSaude estadoSaude;
    private final LocalDate dataResgate;
    private StatusAdocao statusAdocao;

    //Validação dos dados recebidos:
    private static void validarNome(String nome) throws DadoInvalidoException {
        if(nome == null || nome.trim().isEmpty()) { //Checagem de nulo ou vazio.
            throw new DadoInvalidoException("O campo NOME não pode estar vazio.");

        }
    }
    private static void validarEspecie(Especie especie) throws DadoInvalidoException {
        if(especie == null) {
            throw new DadoInvalidoException("O campo ESPÉCIE não pode estar vazio.");

        }
    }
    private static void validarRaca(String raca) throws DadoInvalidoException {
        if(raca == null || raca.trim().isEmpty()) {
            throw new DadoInvalidoException("O campo RAÇA não pode estar vazio.");

        }
    }
    private static void validarIdade(int idade) throws DadoInvalidoException {
        //Como um int nunca é vazio e nem nulo a verificação de campo vazio não é realizada;
        if(idade < 0){ //Checagem de idade Negativa;
             throw new DadoInvalidoException("A IDADE não pode ser negativa.");

        }
    }

    private static void validaPorte(Porte porte) throws DadoInvalidoException {
        if(porte == null) {
            throw new DadoInvalidoException("O campo PORTE não pode estar vazio.");

        }
    }

    private static void validarData(LocalDate dataResgate) throws DadoInvalidoException {
        if(dataResgate == null) {
            throw new DadoInvalidoException("O campo DATA DE RESGATE não pode estar vazio.");

        }
        if(dataResgate.isAfter(LocalDate.now())) {
            throw new DadoInvalidoException("A DATA DE RESGATE inválida");
        }

    }

    public Animal(String nome, Especie especie, String raca, int idade, Porte porte, LocalDate dataResgate) throws DadoInvalidoException {
        //As validações são chamadas antes, para não correr o risco de "queimar" um id.
        validarNome(nome);
        validarRaca(raca);
        validarEspecie(especie);
        validarIdade(idade);
        validaPorte(porte);
        validarData(dataResgate);

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

    public LocalDate getDataResgate(){
        return dataResgate;
    }

    public StatusAdocao getStatusAdocao() {
        return statusAdocao;
    }

    public void setNome(String nome) throws DadoInvalidoException {
        validarNome(nome);
        this.nome = nome;
    }

    public void setRaca(String raca) throws DadoInvalidoException {
        validarRaca(raca);
        this.raca = raca;
    }
    public void setIdade(int idade) throws DadoInvalidoException {
        validarIdade(idade);
        this.idade = idade;
    }
    
    public void setPorte(Porte porte) throws DadoInvalidoException {
        validaPorte(porte);
        this.porte = porte;
    }

    //Relações entre os diferentes usuários e suas funções/Permissões com os animais.

    /*Função responsável por atualizar a saúde de um animal, na qual só pode ser alterada pelo veterinário, a atualização
      do estado de saúde influencia na disponibilidade de adoção, só não influencia no caso do animal ser adotado*/ 
    public void atualizarEstadoSaude(Usuario responsavel, EstadoSaude novoEstado) throws DadoInvalidoException, AcessoNegadoException {
        if(!(responsavel instanceof Veterinario)) {
            throw new AcessoNegadoException("Apenas VETERINÁRIOS podem alterar o estado de saúde.");

        }

        if(novoEstado == null) {
            throw new DadoInvalidoException("O ESTADO DE SAÚDE não pode ser nulo.");

        }

        this.estadoSaude = novoEstado;

        if(statusAdocao != StatusAdocao.ADOTADO) {
            if(novoEstado == EstadoSaude.SAUDAVEL) {
                this.statusAdocao = StatusAdocao.DISPONIVEL;

            }else {
                this.statusAdocao = StatusAdocao.NAO_DISPONIVEL;

            }
        }
    }
    /*Com a autorização do Administrador essa função realiza a mudança de status do animal para adotado e verifica a disponibilidade do animal.*/
    public void marcarComoAdotado(Usuario responsavel) throws AcessoNegadoException, AnimalIndisponivelException{
        if(!(responsavel instanceof Administrador)) {
            throw new AcessoNegadoException("Apenas o ADMINISTRADOR pode autorizar uma adoção.");

        }
        if(statusAdocao != StatusAdocao.DISPONIVEL){
            throw new AnimalIndisponivelException("O animal NÃO está disponível para adoção.");

        }

        this.statusAdocao = StatusAdocao.ADOTADO;
    }

    /*Nessa função é registrado quando ocorre uma devolução do animal adotado, assim o administrador altera o status de adoção dele e verifica
      o estado de saúde do animal, e disponibiliza novamente para adoção caso o estado de saúde esteja saudável */
    public void registrarDevolucao(Usuario responsavel) throws AcessoNegadoException, AnimalIndisponivelException{
        if(!(responsavel instanceof Administrador)) {
            throw new AcessoNegadoException("Apenas o ADMINISTRADOR pode registrar um devolução.");

        }
        if(statusAdocao != StatusAdocao.ADOTADO){
            throw new AnimalIndisponivelException("O animal não foi adotado, não tem o que registrar.");

        }

        if(estadoSaude == EstadoSaude.SAUDAVEL) {
            this.statusAdocao = StatusAdocao.DISPONIVEL;

        }else {
            this.statusAdocao = StatusAdocao.NAO_DISPONIVEL;

        }
    }
}