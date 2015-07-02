package inf.ufg.br.ex04_libraries.model;

/**
 * Created by Marla Aragão on 01/07/2015.
 */
public class Curso {

    private int id;
    private String nome;

    public Curso() {
    }

    public Curso(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}