package modelo;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "video20242370036")
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String titulo;
    private LocalDate data;
    private String linksite = "Desconhecido";
    private int classificacao;
    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE})

    private List<Genero> listaGeneros = new ArrayList<>(); // Um vídeo pode ter vários gêneros N:N

    // Construtor
    public Video() {};

    public Video(String titulo, LocalDate data, String linksite, int classificacao){
        this.titulo = titulo;
        this.data = data;
        this.linksite = linksite;
        this.classificacao = classificacao;
    }

    // Construtor com sobrecarga para exemplo órfão
    public Video(String titulo) {
        this.titulo = titulo;
    }

    // Metodos Get

    public int getId() {
        return id;
    }
    public String getTitulo() {
        return titulo;
    }

    public LocalDate getData() {
        return data;
    }
    public String getLinksite() {
        return linksite;
    }

    public int getClassificacao() {
        return classificacao;
    }

    public List<Genero> getListaGeneros() {
        return listaGeneros;
    }

    // Metodos Set / add e remover

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public void setLinksite(String linksite) {
        this.linksite = linksite;
    }

    public void setClassificacao(int classificacao) {
        this.classificacao = classificacao;
    }

    public void addGenero(Genero genero) {
        if(!listaGeneros.contains(genero)){
            listaGeneros.add(genero);
            genero.addVideo(this);
        }
    }

    public void removerGenero(Genero genero) {
        if(listaGeneros.contains(genero)){
            listaGeneros.remove(genero);
            genero.removerVideo(this);
        }

    }

    // Metodo toString

    @Override
    public String toString() {
        String texto = "\nVídeo " + id + ": " + titulo + "\nData: " + data + "\nClassificação: " + classificacao + " estrelas" + "\nURL: " + linksite + "\nGêneros: " ;
        if (listaGeneros.isEmpty())
            texto += "Sem gêneros";
        else
            for(Genero g: listaGeneros) {
                if (g != null) {
                	texto += g.getNome() + "; ";
                }
            }
        return texto;
    }
}
