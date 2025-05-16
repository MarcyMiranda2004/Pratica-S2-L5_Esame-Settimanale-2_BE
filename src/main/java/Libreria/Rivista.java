package Libreria;

import java.time.LocalDate;

public class Rivista extends Catalogo {
    private static int count = 0;
    private int numero;

    private Periodicita periodicita;

    public Periodicita getPeriodicita() { return periodicita; }
    public void setPeriodicita(Periodicita periodicita) { this.periodicita = periodicita; }

    public Rivista(String title, LocalDate annoPubblicazione, int nPagine, Periodicita periodicita) {
        super(title, annoPubblicazione, nPagine);
        this.periodicita = periodicita;
        this.numero = ++count;
    }

    @Override
    public String toString() {
        return "Rivista r" + numero + "{" +
                "Isbn: " + getIsbn() +
                ", Title: " + getTitle() +
                ", Anno Di Pubblicazione: " + getAnnoPubblicazione() +
                ", Numero Di Pagine: " + getnPagine() +
                ", Periodicità: " + periodicita +
                '}';
    }
}
