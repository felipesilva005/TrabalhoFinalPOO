package eventos;
import excecoes.*;
import usuarios.Voluntario;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Evento {
    private static int contId = 1;
    private final int id;
    private final String titulo;
    private final LocalDate data;
    private final String local;
    private final List<Voluntario> inscritos = new ArrayList<>();

    protected Evento(String titulo, LocalDate data, String local) throws DadoInvalidoException, InscricaoDuplicadaException {
        if(titulo == null || titulo.trim().isEmpty()) {
            throw new DadoInvalidoException("O TÍTULO do evento não pode estar vazio.");

        }
        if(local == null || local.trim().isEmpty()) {
            throw new DadoInvalidoException("O LOCAL do evento não pode estar vazio.");

        }
        if(data == null || data.isBefore(LocalDate.now())) {
            throw new DadoInvalidoException("A DATA do evento não pode estar no passado.");

        }

        this.id = contId++;
        this.titulo = titulo.trim();
        this.data = data;
        this.local = local.trim();
    }

    public int getId() { 
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public LocalDate getData() { 
        return data; 
    }

    public String getLocal() {
        return local; 
    }

    public List<Voluntario> getInscritos() { 
        return new ArrayList<>(inscritos); 
    }

    public abstract String descricaoTipo();

    public void inscrever(Voluntario v) throws InscricaoDuplicadaException, DadoInvalidoException {
        if(v == null) {
            throw new DadoInvalidoException("O VOLUNTÁRIO não pode ser nulo.");

        }
        if(inscritos.contains(v)) {
            throw new InscricaoDuplicadaException("Você já está inscrito neste evento.");

        }
        inscritos.add(v);
    }

    public boolean temInscrito(Voluntario v) {
        return inscritos.contains(v);

    }

    @Override
    public String toString() {
        return id + " | " + descricaoTipo() + " | " + titulo + " | " + data + " | " + local + " | " + inscritos.size() + " inscrito(s)";
    }
}