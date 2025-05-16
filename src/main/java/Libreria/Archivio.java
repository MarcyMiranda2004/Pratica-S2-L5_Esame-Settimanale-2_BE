package Libreria;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Stream;

class Archivio {
    private final List<Libro> libri = new ArrayList<>();
    private final List<Rivista> riviste = new ArrayList<>();

    //Aggiunta di un elemento
    public void aggiungiElemento(Scanner scan) throws ISBNAlreadyExistsException {
        System.out.println("Vuoi aggiungere un Libro o una Rivista?");
        System.out.println("1: Libro \n2: Rivista \n0: Esci");
        int createSelection = scan.nextInt();
        scan.nextLine();
        switch (createSelection) {
            case 1 -> {
                Libro libro = creaLibro(scan);
                if (libri.stream().anyMatch(l -> l.getIsbn() == libro.getIsbn()))
                    throw new ISBNAlreadyExistsException("ISBN " + libro.getIsbn() + " già presente.");
                libri.add(libro);
                System.out.println("Libro creato:\n" + libro);
            }
            case 2 -> {
                Rivista rivista = creaRivista(scan);
                if (riviste.stream().anyMatch(r -> r.getIsbn() == rivista.getIsbn()))
                    throw new ISBNAlreadyExistsException("ISBN " + rivista.getIsbn() + " già presente.");
                riviste.add(rivista);
                System.out.println("Rivista creata:\n" + rivista);
            }
            case 0 -> System.out.println("Uscita aggiunta elemento.");
            default -> System.out.println("Scelta non valida.");
        }
    }

    private Libro creaLibro(Scanner scan) {
        System.out.println("Inserisci il titolo del libro:");
        String title = scan.nextLine();
        LocalDate annoPubblicazione = richiediDataValida(scan, "Inserisci l'anno di pubblicazione (yyyy-MM-dd):");
        System.out.println("Inserisci il numero di pagine del libro:");
        int nPagine = scan.nextInt();
        scan.nextLine();
        System.out.println("Inserisci l'autore del libro:");
        String autore = scan.nextLine();
        System.out.println("Inserisci la categoria del libro:");
        String categoria = scan.nextLine();
        return new Libro(title, annoPubblicazione, nPagine, autore, categoria);
    }

    private Rivista creaRivista(Scanner scan) {
        System.out.println("Inserisci il titolo della rivista:");
        String title = scan.nextLine();
        LocalDate annoPubblicazione = richiediDataValida(scan, "Inserisci l'anno di pubblicazione (yyyy-MM-dd):");
        System.out.println("Inserisci il numero di pagine della rivista:");
        int nPagine = scan.nextInt();
        scan.nextLine();
        System.out.println("Che periodicità ha la rivista:");
        for (Periodicita p : Periodicita.values())
            System.out.println((p.ordinal() + 1) + ": " + p);
        int scelta = scan.nextInt();
        scan.nextLine();
        Periodicita periodicita = Periodicita.values()[scelta - 1];
        return new Rivista(title, annoPubblicazione, nPagine, periodicita);
    }

    //Ricerca Elemento Tramite ISBN
    public Catalogo ricercaPerISBN(Scanner scan) throws ElementNotFoundException {
        System.out.println("Inserisci l'ISBN dell'elemento da cercare:");
        long isbnSearch = scan.nextLong();
        scan.nextLine();
        Optional<Libro> libroTrovato = libri.stream().filter(libro -> libro.getIsbn() == isbnSearch).findFirst();
        if (libroTrovato.isPresent()) {
            System.out.println("Libro trovato:\n" + libroTrovato.get());
            return libroTrovato.get();
        }
        Optional<Rivista> rivistaTrovata = riviste.stream().filter(rivista -> rivista.getIsbn() == isbnSearch).findFirst();
        if (rivistaTrovata.isPresent()) {
            System.out.println("Rivista trovata:\n" + rivistaTrovata.get());
            return rivistaTrovata.get();
        }
        throw new ElementNotFoundException("Nessun elemento con ISBN " + isbnSearch + " trovato.");
    }

    //Rimuovi Elemetro Tramite ISBN
    public void rimuoviElemento(Scanner scan) throws ElementNotFoundException {
        System.out.println("Inserisci l'ISBN dell'elemento da rimuovere:");
        long isbnRemove = scan.nextLong();
        scan.nextLine();
        boolean libroRimosso = libri.removeIf(libro -> libro.getIsbn() == isbnRemove);
        boolean rivistaRimossa = riviste.removeIf(rivista -> rivista.getIsbn() == isbnRemove);
        if (libroRimosso || rivistaRimossa)
            System.out.println("Elemento con ISBN " + isbnRemove + " rimosso.");
        else
            throw new ElementNotFoundException("Nessun elemento con ISBN " + isbnRemove + " trovato.");
    }

    //Ricerca Elemento Per Anno di Pubblicazione
    public void ricercaPerAnno(Scanner scan) {
        System.out.println("Inserisci l'anno di pubblicazione da cercare:");
        int anno = scan.nextInt();
        scan.nextLine();

        System.out.println("\n----- Libri pubblicati nel " + anno + " -----");
        List<Libro> libriTrovati = libri.stream()
                .filter(libro -> libro.getAnnoPubblicazione().getYear() == anno)
                .toList();

        if (libriTrovati.isEmpty()) {
            System.out.println("Nessun libro con " + anno + " come anno di pubblicazione è stato trovato.");
        } else {
            libriTrovati.forEach(System.out::println);
        }

        System.out.println("\n----- Riviste pubblicate nel " + anno + " -----");
        List<Rivista> rivisteTrovate = riviste.stream()
                .filter(rivista -> rivista.getAnnoPubblicazione().getYear() == anno)
                .toList();

        if (rivisteTrovate.isEmpty()) {
            System.out.println("Nessuna rivista con " + anno + " come anno di pubblicazione è stata trovata.");
        } else {
            rivisteTrovate.forEach(System.out::println);
        }
    }

    //Ricerca Elemento Per Autore
    public void ricercaPerAutore(Scanner scan) {
        System.out.println("Inserisci il nome dell'autore da cercare:");
        String autore = scan.nextLine();
        List<Libro> libriTrovati = libri.stream()
                .filter(libro -> libro.getAutore().equalsIgnoreCase(autore))
                .toList();

        System.out.println("\n----- Libri di " + autore + " -----");
        if (libriTrovati.isEmpty()) {
            System.out.println("Nessun libro di " + autore + " trovato.");
        } else {
            libriTrovati.forEach(System.out::println);
        }
    }

    //Aggiorna Un Elemento
    public void aggiornaElemento(Scanner scan) throws ElementNotFoundException {
        System.out.println("Inserisci l'ISBN dell'elemento da aggiornare:");
        long isbnUpdate = scan.nextLong();
        scan.nextLine();
        Optional<Libro> libroOpt = libri.stream().filter(libro -> libro.getIsbn() == isbnUpdate).findFirst();
        if (libroOpt.isPresent()) {
            aggiornaLibro(scan, libroOpt.get());
            return;
        }
        Optional<Rivista> rivistaOpt = riviste.stream().filter(rivista -> rivista.getIsbn() == isbnUpdate).findFirst();
        if (rivistaOpt.isPresent()) {
            aggiornaRivista(scan, rivistaOpt.get());
            return;
        }
        throw new ElementNotFoundException("Nessun elemento con ISBN " + isbnUpdate + " trovato.");
    }

    //Aggiornamento Di Un Elemento
    private void aggiornaLibro(Scanner scan, Libro libro) {
        System.out.println("Titolo attuale: " + libro.getTitle());
        System.out.println("Inserisci nuovo titolo (oppure premi invio per mantenere):");
        String titolo = scan.nextLine();
        if (!titolo.isBlank()) libro.setTitle(titolo);

        System.out.println("Anno pubblicazione attuale: " + libro.getAnnoPubblicazione());
        System.out.println("Inserisci nuova data pubblicazione (yyyy-MM-dd) o invio per mantenere:");
        String dataInput = scan.nextLine();

        if (!dataInput.isBlank()) libro.setAnnoPubblicazione(richiediDataValida(scan, dataInput));
        System.out.println("Numero pagine attuale: " + libro.getnPagine());
        System.out.println("Inserisci nuovo numero pagine o invio per mantenere:");
        String pagineInput = scan.nextLine();

        if (!pagineInput.isBlank()) libro.setnPagine(Integer.parseInt(pagineInput));
        System.out.println("Autore attuale: " + libro.getAutore());
        System.out.println("Inserisci nuovo autore o invio per mantenere:");
        String autore = scan.nextLine();

        if (!autore.isBlank()) libro.setAutore(autore);
        System.out.println("Categoria attuale: " + libro.getGenere());
        System.out.println("Inserisci nuova categoria o invio per mantenere:");
        String categoria = scan.nextLine();

        if (!categoria.isBlank()) libro.setGenere(categoria);
        System.out.println("Libro aggiornato:\n" + libro);
    }

    private void aggiornaRivista(Scanner scan, Rivista rivista) {
        System.out.println("Titolo attuale: " + rivista.getTitle());
        System.out.println("Inserisci nuovo titolo (oppure premi invio per mantenere):");
        String titolo = scan.nextLine();
        if (!titolo.isBlank()) rivista.setTitle(titolo);
        System.out.println("Anno pubblicazione attuale: " + rivista.getAnnoPubblicazione());
        System.out.println("Inserisci nuova data pubblicazione (yyyy-MM-dd) o invio per mantenere:");
        String dataInput = scan.nextLine();
        if (!dataInput.isBlank()) rivista.setAnnoPubblicazione(richiediDataValida(scan, dataInput));
        System.out.println("Numero pagine attuale: " + rivista.getnPagine());
        System.out.println("Inserisci nuovo numero pagine o invio per mantenere:");
        String pagineInput = scan.nextLine();
        if (!pagineInput.isBlank()) rivista.setnPagine(Integer.parseInt(pagineInput));
        System.out.println("Periodicità attuale: " + rivista.getPeriodicita());
        System.out.println("Inserisci nuova periodicità (1: SETTIMANALE, 2: MENSILE, 3: SEMESTRALE) o invio per mantenere:");
        String periodicitaInput = scan.nextLine();
        if (!periodicitaInput.isBlank()) {
            int pIndex = Integer.parseInt(periodicitaInput) - 1;
            if (pIndex >= 0 && pIndex < Periodicita.values().length)
                rivista.setPeriodicita(Periodicita.values()[pIndex]);
            else
                System.out.println("Periodicità non valida, mantengo quella attuale.");
        }
        System.out.println("Rivista aggiornata:\n" + rivista);
    }

    //Richiesta di Validazione Della Data
    private LocalDate richiediDataValida(Scanner scan, String primoTentativo) {
        while (true) {
            try {
                return LocalDate.parse(primoTentativo);
            } catch (DateTimeParseException e) {
                System.out.println("Data non valida. Inserisci una data valida (yyyy-MM-dd):");
                primoTentativo = scan.nextLine();
            }
        }
    }

    //Stampa Delle Statistiche
    public void stampaStatistiche() {
        System.out.println("Libri presenti nel catalogo: " + libri.size());
        System.out.println("Riviste presenti nel catalogo: " + riviste.size());
        libri.stream().max(Comparator.comparingInt(Catalogo::getnPagine)).ifPresent(l -> System.out.println("Il Libro con più pagine è: " + l));
        riviste.stream().max(Comparator.comparingInt(Catalogo::getnPagine)).ifPresent(r -> System.out.println("La Rivista con più pagine è: " + r));
        Stream.concat(libri.stream(), riviste.stream())
                .max(Comparator.comparingInt(Catalogo::getnPagine))
                .ifPresent(e -> System.out.println("L'elemento con più pagine è: " + e));
        double mediaPagine = Stream.concat(libri.stream().map(Catalogo::getnPagine), riviste.stream().map(Catalogo::getnPagine))
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0);
        System.out.println("Media pagine di tutti gli elementi: " + mediaPagine);
    }

    //Stampa Di Tutti Gli Elementi
    public void visualizzaElementi() {
        if (!libri.isEmpty()) {
            System.out.println("----- Libri -----");
            libri.forEach(System.out::println);
        } else {
            System.out.println("Nessun Libro presente nel catalogo.");
        }
        if (!riviste.isEmpty()) {
            System.out.println("\n----- Riviste -----");
            riviste.forEach(System.out::println);
        } else {
            System.out.println("Nessuna Rivista presente nel catalogo.");
        }
    }
}