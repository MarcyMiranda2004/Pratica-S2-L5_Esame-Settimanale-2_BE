package Libreria;

import java.util.InputMismatchException;
import java.util.Scanner;

//gestione ecezioni
class ISBNAlreadyExistsException extends Exception {
    public ISBNAlreadyExistsException(String message) {
        super(message);
    }
}

class ElementNotFoundException extends Exception {
    public ElementNotFoundException(String message) {
        super(message);
    }
}

//main
public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Archivio archivio = new Archivio();
        boolean running = true;

        while (running) {
            menu();
            int selection = scan.nextInt();
            scan.nextLine();

            try {
                switch (selection) {
                    case 1 -> archivio.aggiungiElemento(scan);
                    case 2 -> archivio.ricercaPerISBN(scan);
                    case 3 -> archivio.rimuoviElemento(scan);
                    case 4 -> archivio.ricercaPerAnno(scan);
                    case 5 -> archivio.ricercaPerAutore(scan);
                    case 6 -> archivio.aggiornaElemento(scan);
                    case 7 -> archivio.stampaStatistiche();
                    case 8 -> archivio.visualizzaElementi();
                    case 0 -> {
                        System.out.println("Uscita in corso...");
                        running = false;
                    }
                    default -> System.out.println("Operazione non implementata o scelta non valida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Errore di input. Inserisci un numero valido.");
                scan.nextLine();
            } catch (ISBNAlreadyExistsException | ElementNotFoundException e) {
                System.out.println("Errore: " + e.getMessage());
            }
        }
        scan.close();
    }

    //menu
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
}