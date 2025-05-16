package Libreria;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Stream;

public class Archivio {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        List<Libro> libri = new ArrayList<>();
        List<Rivista> riviste = new ArrayList<>();

        boolean running = true;

        while (running) {
            menu();
            int selection = scan.nextInt();
            scan.nextLine();

            switch (selection) {
                case 1 -> aggiungiElemento(scan, libri, riviste);
                case 2 -> ricercaPerISBN(scan, libri, riviste);
                case 3 -> rimuoviElemento(scan, libri, riviste);
                case 4 -> ricercaPerAnno(scan, libri, riviste);
                case 5 -> ricercaPerAutore(scan, libri);
                case 6 -> aggiornaElemento(scan, libri, riviste);
                case 7 -> stampaStatistiche(libri, riviste);
                case 8 -> visualizzaElementi(libri, riviste);
                case 0 -> {
                    System.out.println("Uscita in corso...");
                    running = false;
                }
                default -> System.out.println("Operazione non implementata o scelta non valida.");
            }
        }

        scan.close();
    }

    private static void menu() {
        System.out.println("\nGestionale Libreria: Ciao, che operazione vuoi effettuare?");
        System.out.println("""
                1: Aggiungi un nuovo elemento all'archivio.
                2: Ricerca un elemento tramite ISBN.
                3: Rimuovi un elemento dal catalogo tramite ISBN.
                4: Ricerca un elemento per anno di pubblicazione.
                5: Ricerca un elemento per autore.
                6: Aggiorna un elemento tramite ISBN.
                7: Stampa statistiche del catalogo.
                8: Visualizza tutti gli elementi.
                0: Esci.
                """);
    }

    private static void aggiungiElemento(Scanner scan, List<Libro> libri, List<Rivista> riviste) {
        System.out.println("Vuoi aggiungere un Libro o una Rivista?");
        System.out.println("1: Libro \n2: Rivista \n0: Esci");
        int createSelection = scan.nextInt();
        scan.nextLine();

        switch (createSelection) {
            case 1 -> {
                System.out.println("Inserisci il titolo del libro:");
                String title = scan.nextLine();

                LocalDate annoPubblicazione = null;
                while (true) {
                    try {
                        System.out.println("Inserisci l'anno di pubblicazione (es. 2023-05-16):");
                        String dataInput = scan.nextLine();
                        annoPubblicazione = LocalDate.parse(dataInput);
                        break;
                    } catch (DateTimeParseException e) {
                        System.out.println("Formato data non valido. Riprova.");
                    }
                }

                System.out.println("Inserisci il numero di pagine del libro:");
                int nPagine = scan.nextInt();
                scan.nextLine();

                System.out.println("Inserisci l'autore del libro:");
                String autore = scan.nextLine();

                System.out.println("Inserisci la categoria del libro:");
                String categoria = scan.nextLine();

                Libro l = new Libro(title, annoPubblicazione, nPagine, autore, categoria);
                libri.add(l);
                System.out.println("Libro creato:\n" + l);
            }
            case 2 -> {
                System.out.println("Inserisci il titolo della rivista:");
                String title = scan.nextLine();

                LocalDate annoPubblicazione = null;
                while (true) {
                    try {
                        System.out.println("Inserisci l'anno di pubblicazione (yyyy-mm-dd):");
                        String dataInput = scan.nextLine();
                        annoPubblicazione = LocalDate.parse(dataInput);
                        break;
                    } catch (DateTimeParseException e) {
                        System.out.println("Formato data non valido. Riprova.");
                    }
                }

                System.out.println("Inserisci il numero di pagine della rivista:");
                int nPagine = scan.nextInt();
                scan.nextLine();

                System.out.println("Che periodicità ha la rivista:");
                for (Periodicita p : Periodicita.values()) {
                    System.out.println((p.ordinal() + 1) + ": " + p);
                }

                int scelta = scan.nextInt();
                scan.nextLine();
                Periodicita periodicita = Periodicita.values()[scelta - 1];

                Rivista r = new Rivista(title, annoPubblicazione, nPagine, periodicita);
                riviste.add(r);
                System.out.println("Rivista creata:\n" + r);
            }
            case 0 -> System.out.println("Uscita aggiunta elemento.");
            default -> System.out.println("Scelta non valida.");
        }
    }

    private static void ricercaPerISBN(Scanner scan, List<Libro> libri, List<Rivista> riviste) {
        System.out.println("Vuoi cercare un Libro o una Rivista?");
        System.out.println("1: Libro \n2: Rivista \n0: Esci");

        int searchSelection = scan.nextInt();
        scan.nextLine();

        switch (searchSelection) {
            case 1 -> {
                System.out.println("Inserisci l'ISBN del Libro da cercare:");
                long isbnSearch = scan.nextLong();
                scan.nextLine();

                Optional<Libro> libroTrovato = libri.stream()
                        .filter(libro -> libro.getIsbn() == isbnSearch)
                        .findFirst();

                if (libroTrovato.isPresent()) {
                    System.out.println("Libro trovato:\n" + libroTrovato.get());
                } else {
                    System.out.println("Libro con ISBN " + isbnSearch + " non trovato.");
                }
            }
            case 2 -> {
                System.out.println("Inserisci l'ISBN della Rivista da cercare:");
                long isbnSearch = scan.nextLong();
                scan.nextLine();

                Optional<Rivista> rivistaTrovata = riviste.stream()
                        .filter(rivista -> rivista.getIsbn() == isbnSearch)
                        .findFirst();

                if (rivistaTrovata.isPresent()) {
                    System.out.println("Rivista trovata:\n" + rivistaTrovata.get());
                } else {
                    System.out.println("Rivista con ISBN " + isbnSearch + " non trovato.");
                }
            }
            case 0 -> System.out.println("Uscita ricerca elemento.");
            default -> System.out.println("Scelta non valida.");
        }
    }

    private static void rimuoviElemento(Scanner scan, List<Libro> libri, List<Rivista> riviste) {
        System.out.println("Vuoi rimuovere un Libro o una Rivista?");
        System.out.println("1: Libro \n2: Rivista \n0: Esci");
        int removeSelection = scan.nextInt();
        scan.nextLine();

        switch (removeSelection) {
            case 1 -> {
                System.out.println("Inserisci l'ISBN del Libro da rimuovere:");
                long isbnRemove = scan.nextLong();
                scan.nextLine();

                boolean removed = libri.removeIf(libro -> libro.getIsbn() == isbnRemove);
                if (removed) {
                    System.out.println("Libro con ISBN " + isbnRemove + " rimosso.");
                } else {
                    System.out.println("Libro con ISBN " + isbnRemove + " non trovato.");
                }
            }
            case 2 -> {
                System.out.println("Inserisci l'ISBN della Rivista da rimuovere:");
                long isbnRemove = scan.nextLong();
                scan.nextLine();

                boolean removed = riviste.removeIf(rivista -> rivista.getIsbn() == isbnRemove);
                if (removed) {
                    System.out.println("Rivista con ISBN " + isbnRemove + " rimossa.");
                } else {
                    System.out.println("Rivista con ISBN " + isbnRemove + " non trovata.");
                }
            }
            case 0 -> System.out.println("Uscita rimozione elemento.");
            default -> System.out.println("Scelta non valida.");
        }
    }

    private static void ricercaPerAnno(Scanner scan, List<Libro> libri, List<Rivista> riviste) {
        System.out.println("Inserisci l'anno di pubblicazione da cercare (es. 2023):");
        int anno = scan.nextInt();
        scan.nextLine();

        System.out.println("\n----- Libri pubblicati nel " + anno + " -----");
        List<Libro> libriTrovati = libri.stream()
                .filter(libro -> libro.getAnnoPubblicazione().getYear() == anno)
                .toList();

        if (libriTrovati.isEmpty()) {
            System.out.println("Nessun libro pubblicato nel " + anno + " è stato trovato.");
        } else {
            libriTrovati.forEach(System.out::println);
        }

        System.out.println("\n----- Riviste pubblicate nel " + anno + " -----");
        List<Rivista> rivisteTrovate = riviste.stream()
                .filter(rivista -> rivista.getAnnoPubblicazione().getYear() == anno)
                .toList();

        if (rivisteTrovate.isEmpty()) {
            System.out.println("Nessuna rivista pubblicata nel " + anno + " è stata trovata.");
        } else {
            rivisteTrovate.forEach(System.out::println);
        }

    }

    private static void ricercaPerAutore(Scanner scan, List<Libro> libri) {
        System.out.println("Inserisci il nome dell'autore da cercare:");
        String autore = scan.nextLine();

        System.out.println("\n----- Libri di " + autore + " -----");
        List<Libro> libriTrovati = libri.stream()
                .filter(libro -> libro.getAutore().equalsIgnoreCase(autore))
                .toList();

        if (libriTrovati.isEmpty()) {
            System.out.println("Nessun libro dell'autore \"" + autore + "\" è stato trovato.");
        } else {
            libriTrovati.forEach(System.out::println);
        }
    }

    private static void aggiornaElemento(Scanner scan, List<Libro> libri, List<Rivista> riviste) {
        System.out.println("Vuoi aggiornare un Libro o una Rivista?");
        System.out.println("1: Libro \n2: Rivista \n0: Esci");
        int updateSelection = scan.nextInt();
        scan.nextLine();

        switch (updateSelection) {
            case 1 -> {
                System.out.println("Inserisci l'ISBN del Libro da aggiornare:");
                long isbnUpdate = scan.nextLong();
                scan.nextLine();

                Optional<Libro> libroOpt = libri.stream()
                        .filter(libro -> libro.getIsbn() == isbnUpdate)
                        .findFirst();

                if (libroOpt.isPresent()) {
                    Libro libro = libroOpt.get();

                    System.out.println("Titolo attuale: " + libro.getTitle());
                    System.out.println("Inserisci nuovo titolo (oppure premi invio per mantenere):");
                    String titolo = scan.nextLine();
                    if (!titolo.isBlank()) libro.setTitle(titolo);

                    System.out.println("Anno pubblicazione attuale: " + libro.getAnnoPubblicazione());
                    System.out.println("Inserisci nuova data pubblicazione (yyyy-MM-dd) o invio per mantenere:");
                    String dataInput = scan.nextLine();
                    if (!dataInput.isBlank()) {
                        libro.setAnnoPubblicazione(richiediDataValida(scan, dataInput));
                    }

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
                } else {
                    System.out.println("Libro con ISBN " + isbnUpdate + " non trovato.");
                }
            }
            case 2 -> {
                System.out.println("Inserisci l'ISBN della Rivista da aggiornare:");
                long isbnUpdate = scan.nextLong();
                scan.nextLine();

                Optional<Rivista> rivistaOpt = riviste.stream()
                        .filter(rivista -> rivista.getIsbn() == isbnUpdate)
                        .findFirst();

                if (rivistaOpt.isPresent()) {
                    Rivista rivista = rivistaOpt.get();

                    System.out.println("Titolo attuale: " + rivista.getTitle());
                    System.out.println("Inserisci nuovo titolo (oppure premi invio per mantenere):");
                    String titolo = scan.nextLine();
                    if (!titolo.isBlank()) rivista.setTitle(titolo);

                    System.out.println("Anno pubblicazione attuale: " + rivista.getAnnoPubblicazione());
                    System.out.println("Inserisci nuova data pubblicazione (yyyy-MM-dd) o invio per mantenere:");
                    String dataInput = scan.nextLine();
                    if (!dataInput.isBlank()) {
                        rivista.setAnnoPubblicazione(richiediDataValida(scan, dataInput));
                    }

                    System.out.println("Numero pagine attuale: " + rivista.getnPagine());
                    System.out.println("Inserisci nuovo numero pagine o invio per mantenere:");
                    String pagineInput = scan.nextLine();
                    if (!pagineInput.isBlank()) rivista.setnPagine(Integer.parseInt(pagineInput));

                    System.out.println("Periodicità attuale: " + rivista.getPeriodicita());
                    System.out.println("Inserisci nuova periodicità (1: SETTIMANALE, 2: MENSILE, 3: SEMESTRALE) o invio per mantenere:");
                    String periodicitaInput = scan.nextLine();
                    if (!periodicitaInput.isBlank()) {
                        int pIndex = Integer.parseInt(periodicitaInput) - 1;
                        if (pIndex >= 0 && pIndex < Periodicita.values().length) {
                            rivista.setPeriodicita(Periodicita.values()[pIndex]);
                        } else {
                            System.out.println("Periodicità non valida, mantengo quella attuale.");
                        }
                    }

                    System.out.println("Rivista aggiornata:\n" + rivista);
                } else {
                    System.out.println("Rivista con ISBN " + isbnUpdate + " non trovata.");
                }
            }
            case 0 -> System.out.println("Uscita aggiornamento elemento.");
            default -> System.out.println("Scelta non valida.");
        }
    }

    private static LocalDate richiediDataValida(Scanner scan, String primoTentativo) {
        while (true) {
            try {
                return LocalDate.parse(primoTentativo);
            } catch (DateTimeParseException e) {
                System.out.println("Data non valida. Inserisci una data valida (yyyy-MM-dd):");
                primoTentativo = scan.nextLine();
            }
        }
    }
    
    private static void stampaStatistiche(List<Libro> libri, List<Rivista> riviste) {
        System.out.println("Libri prsenti nel catalogo: " + libri.size());
        System.out.println("Riviste prsenti nel catalogo: " + riviste.size());

        Optional<Libro> libroConPiuPagine = libri.stream().max(Comparator.comparingInt(Libro::getnPagine));
        Optional<Rivista> rivistaConPiuPagine = riviste.stream().max(Comparator.comparingInt(Rivista::getnPagine));

        int maxPagineLibro = libroConPiuPagine.map(Libro::getnPagine).orElse(0);
        int maxPagineRivista = rivistaConPiuPagine.map(Rivista::getnPagine).orElse(0);

        System.out.println("Il Libro con più pagine è: " + libroConPiuPagine);
        System.out.println("La Rivista con più pagine è: " + rivistaConPiuPagine);

        if (maxPagineLibro > maxPagineRivista) {
            System.out.println("L'elemento con più pagine è: " + libroConPiuPagine.get());
        } else if (maxPagineRivista > maxPagineLibro) {
            System.out.println("L'elemento con più pagine è: " + rivistaConPiuPagine.get());
        } else {
            System.out.println("Questi Libiri e/o Riviste hanno tutti il numero più alto di pagine: " + maxPagineLibro);
        }

        double mediaPagine = Stream.concat(libri.stream().map(libro -> libro.getnPagine()), riviste.stream()
                        .map(rivista -> rivista.getnPagine()))
                        .mapToInt(Integer::intValue)
                        .average()
                        .orElse(0);

        System.out.println("Media pagine di tutti gli elementi: " + mediaPagine);

    }

    private static void visualizzaElementi(List<Libro> libri, List<Rivista> riviste) {
        if (libri.isEmpty()) {
            System.out.println("Nessun Libro Presene Nel Catalogo");
        } else {
            System.out.println("----- Libri -----");
            System.out.println(libri);
        }

        if (riviste.isEmpty()) {
            System.out.println("Nessuna Rivista Presene Nel Catalogo");
        } else {
            System.out.println("----- Riviste -----");
            System.out.println(riviste);
        }

    }

}