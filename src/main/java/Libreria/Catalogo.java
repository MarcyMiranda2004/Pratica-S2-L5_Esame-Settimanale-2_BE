package Libreria;

import java.time.LocalDate;

public abstract class Catalogo {
    private static long lastIsbn = 0L;

    private long isbn;
    private String title;
    private LocalDate annoPubblicazione;
    private int nPagine;

    //ISBN
    public long getIsbn() { return isbn; }
    public void setIsbn(long isbn) { this.isbn = isbn; }

    //title
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    //annoPubblicazione
    public LocalDate getAnnoPubblicazione() { return annoPubblicazione; }
    public void setAnnoPubblicazione(LocalDate annoPubblicazione) { this.annoPubblicazione = annoPubblicazione; }

    //nPagine
    public int getnPagine() { return nPagine; }
    public void setnPagine(int nPagine) { this.nPagine = nPagine; }

    public Catalogo(String title, LocalDate annoPubblicazione, int nPagine) {
        this.isbn = ++lastIsbn;
        this.title = title;
        this.annoPubblicazione = annoPubblicazione;
        this.nPagine = nPagine;
    }

    @Override
    public String toString() {
        return "Catalogo{" +
                "Isbn: " + isbn +
                ", Title: " + title +
                ", Anno Di Pubblicazione: " + annoPubblicazione +
                ", Numero Di Pagine: " + nPagine +
                '}';
    }
}
