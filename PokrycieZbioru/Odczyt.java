import java.util.Scanner;

public class Odczyt {
    private Scanner scanner;
    private int[] wartosci;
    private int ostatni;
    private Rodzina rodzina;

    public Odczyt(){
        this.scanner = new Scanner(System.in);
        this.wartosci = new int[2];
        this.rodzina = new Rodzina();
    }

    public void czytaj_wejscie(){
        while(scanner.hasNextInt()) {
            czytaj_dane();
        }
    }

    private void czytaj_dane(){
        ostatni = scanner.nextInt();
        if(ostatni < 0){
            czytaj_zapytanie();
        }
        else {
            czytaj_zbior();
        }
    }

    private void czytaj_zbior() {
        //przypadek pustego zbioru
        if(ostatni == 0){
            dodaj_zbior_pusty();
        }
        else{
            cztaj_niepusty_zbior();
        }
    }

    private void cztaj_niepusty_zbior() {
        Zbior Z = new Zbior();
        while (ostatni != 0){
            dodaj_ciag(Z);
        }
        rodzina.dodajZbior(Z);
    }

    private void dodaj_ciag(Zbior Z){
        wartosci[0] = ostatni;
        ostatni = scanner.nextInt();
        if(ostatni >= 0){
            //dodajemy skladnik jednoelementowy
            Z.dodajCiag(new CiagSkonczony(wartosci[0]));
            return;
        }
        wartosci[1] = -ostatni;
        ostatni = scanner.nextInt();
        if(ostatni >= 0){
            //dodajemy ciag nieskonczony
            Z.dodajCiag(new CiagNieskonczony(wartosci[0],wartosci[1]));
            return;
        }
        //dwa ujemne znaki pod rzad czyli dodajemy ciag skonczony
        Z.dodajCiag(new CiagSkonczony(wartosci[0],wartosci[1] ,-ostatni));
        ostatni = scanner.nextInt();
    }

    private void dodaj_zbior_pusty(){
        Zbior Z = new Zbior();
        rodzina.dodajZbior(Z);
    }


    private void czytaj_zapytanie() {
        int instancja = scanner.nextInt();
        rodzina.sprawdz_pokrycie(instancja, -ostatni);
    }
}
