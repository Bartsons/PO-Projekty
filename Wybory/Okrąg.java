package wybory;

import java.util.Arrays;

//Klasa reprezentująca okrąg wyborczy, wewnątrz której przeprowadzane są głosowania
public class Okrąg {

    final private int nrOkręgu;
    protected int liczbaKandydatów;
    private Wyborca[] wyborcy;
    protected Kandydat[][] kandydaci;
    final Partia[] partie;
    private int[] głosynaPartie;

    public Okrąg(int index, int rozmiar, Partia[] partie) {
        this.liczbaKandydatów = rozmiar/10;
        this.wyborcy = new Wyborca[rozmiar];
        this.kandydaci = new Kandydat[partie.length][liczbaKandydatów];
        this.partie = partie;
        this.głosynaPartie = new int[partie.length];
        this.nrOkręgu = index + 1;
    }

    public void dodajKandydata(Kandydat k, int nrPartii, int nrnaLiście) {
        kandydaci[nrPartii][nrnaLiście] = k;
    }

    public void dodajWyborcę(int nrWyborcy, Wyborca w) {
        wyborcy[nrWyborcy] = w;
    }

    public Kandydat znajdzKandydata(Partia partia, int nrKandydata) {
        return kandydaci[partia.numerListy][nrKandydata - 1];
    }

    //Metoda do włączenia okręgu z argumentu do danego okręgu
    public void scal(Okrąg drugiOkrąg) {
        Wyborca[] złączeniWyborcy = new Wyborca[wyborcy.length + drugiOkrąg.wyborcy.length];

        System.arraycopy(wyborcy, 0, złączeniWyborcy, 0, wyborcy.length);
        System.arraycopy(drugiOkrąg.wyborcy, 0, złączeniWyborcy, wyborcy.length, drugiOkrąg.wyborcy.length);
        this.wyborcy = złączeniWyborcy;

        Kandydat[] złączeniKandydaciPartii = new Kandydat[liczbaKandydatów + drugiOkrąg.liczbaKandydatów];
        for (int i = 0; i < partie.length; i++) {
            System.arraycopy(kandydaci[i], 0, złączeniKandydaciPartii, 0, liczbaKandydatów);
            System.arraycopy(drugiOkrąg.kandydaci[i], 0, złączeniKandydaciPartii, liczbaKandydatów, drugiOkrąg.liczbaKandydatów);
            kandydaci[i] = złączeniKandydaciPartii.clone();
        }
        this.liczbaKandydatów += drugiOkrąg.liczbaKandydatów;
    }

    //Metoda zliczająca głosy na każdego kandydata w okręgu i wypisująca rezultaty
    public void przeprowadźWybory() {
        System.out.print("Okrąg wyborczy nr " + nrOkręgu + ":\n");
        inicjalizujGłosy();
        zliczGłosy();
        wypiszRezultatyKandydatów();
        //wypiszRezultatyPartii();
    }

    //Metoda zliczająca głosy na kandydatów i wypisująca głosy wyborców
    private void zliczGłosy() {
        Kandydat wybranyKandydat;
        for (Wyborca wyborca : wyborcy) {
            wybranyKandydat = wyborca.głosuj(kandydaci);
            wybranyKandydat.oddajGłos();
            głosynaPartie[wybranyKandydat.partia.numerListy]++;
            System.out.print(wyborca.toString() + " oddał(a) głos na " + wybranyKandydat.toString() + ".\n");
        }
    }

    //Metoda wypisująca kandydatów i głosy na nich
    private void wypiszRezultatyKandydatów() {
        for (int nrPartii = 0; nrPartii < kandydaci.length; nrPartii++) {
            for (int i = 0; i < kandydaci[nrPartii].length; i++) {
                System.out.print(kandydaci[nrPartii][i].toString() + ", " + (i + 1) + " numer na liście " + " zebrał(a) " + kandydaci[nrPartii][i].pokażGłosy() + " głosów.\n");
            }
        }
    }

    //Metoda zerująca głosy na kandydatów i partie
    private void inicjalizujGłosy() {
        Arrays.fill(głosynaPartie, 0);
        for (Kandydat[] kandydaciPartii : kandydaci) {
            for (Kandydat kandydat :  kandydaciPartii) {
                kandydat.resetujGłosy();
            }
        }
    }

    public Wyborca[] dajListaWyborców() {
        return wyborcy;
    }

    public int[] dajGłosynaPartie() {
        return głosynaPartie;
    }

    public int numer() {
        return nrOkręgu;
    }

    //Metoda wypisująca partie i głosy na nie w danym okręgu
    private void wypiszRezultatyPartii() {
        for (int nrPartii = 0; nrPartii < partie.length; nrPartii++) {
            System.out.print(partie[nrPartii].nazwa + " zebrała "+ głosynaPartie[nrPartii] + " głosów w okręgu " + nrOkręgu + ".\n");
        }
    }
}
