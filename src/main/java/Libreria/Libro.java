package Libreria;

import java.time.LocalDate;

public class Libro extends Catalogo {
    private static int count = 0;
    private int numero;

    private String autore;
    private String genere;

    //autore
    public String getAutore() { return autore; }
    public void setAutore(String autore) { this.autore = autore; }

    //genere
    public String getGenere() { return genere; }
    public void setGenere(String genere) { this.genere = genere; }

    public Libro(String title, LocalDate annoPubblicazione, int nPagine, String autore, String genere) {
        super(title, annoPubblicazione, nPagine);
        this.autore = autore;
        this.genere = genere;
        this.numero = ++count;
    }

    @Override
    public String toString() {
        return "Libro l" + numero + "{" +
                "Isbn: " + getIsbn() +
                ", Title: " + getTitle() +
                ", Anno Di Pubblicazione: " + getAnnoPubblicazione() +
                ", Numero Di Pagine: " + getnPagine() +
                ", Autore: " + autore +
                ", Genere: " + genere +
                '}';
    }
}
