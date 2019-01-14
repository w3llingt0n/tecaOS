package model.entidades;

/**
 *
 * @author Wellington
 */
public class Livro {

    public Livro(int id, String titulo, String anoLancamento, String autor, String categoria, int quantidade) {
        this.setId(id);
        this.setTitulo(titulo);
        this.setAnoLancamento(anoLancamento);
        this.setAutor(autor);
        this.setCategoria(categoria);
        this.setQuantidade(quantidade);
    }

    public Livro(String id, String titulo, String anoLancamento, String autor, String categoria, int quantidade) {
        this.setId(id);
        this.setTitulo(titulo);
        this.setAnoLancamento(anoLancamento);
        this.setAutor(autor);
        this.setCategoria(categoria);
        this.setQuantidade(quantidade);
    }

    public Livro(String id, String titulo, String anoLancamento, String autor, String categoria, int quantidade, int emprestados) {
        this(id, titulo, anoLancamento, autor, categoria, quantidade);
        this.setEmprestados(emprestados);
    }

    public Livro(int id, String titulo, String anoLancamento, String autor, String categoria, int quantidade, int emprestados) {
        this(id, titulo, anoLancamento, autor, categoria, quantidade);
        this.setEmprestados(emprestados);
    }

    private int id;
    private String titulo;
    private String anoLancamento;
    private String autor;
    private String categoria;
    private int quantidade;
    private int emprestados;

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getId() {
        return id;
    }

    public void setId(String id) {
        this.id = Integer.parseInt(id);
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAnoLancamento() {
        return anoLancamento;
    }

    public void setAnoLancamento(String anoLancamento) {
        this.anoLancamento = anoLancamento;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getEmprestados() {
        return emprestados;
    }

    public void setEmprestados(int emprestados) {
        this.emprestados = emprestados;
    }

}
