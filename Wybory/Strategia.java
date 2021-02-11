package wybory;

import java.util.ArrayList;
import java.util.Random;

//Klasa reprezentujaca kampanię wyborczą, która wybiera odpowiednie działania na podstawie budżetu parii w danym momencie
public abstract class Strategia {

    protected final Partia partia;
    protected final Działanie[] dostępneDziałania;
    protected Wyborca[][] listaWyborców;
    protected final int najmniejszyKosztDziałania;//Wartości przechowywane by ustalić czy istnieją dalsze działania mieszczące się w budżecie
    protected final ArrayList<Integer> nrnajmniejszychOkręgów;
    protected Działanie najtańszeDziałanie;
    protected final Random rand;//Do losowego wybierania spośród wielu opcji np. o tym samym koszcie

    public Strategia (Partia partia, Działanie[] dostępneDziałania, Wyborca listaWyborców[][]) {
        this.partia = partia;
        this.dostępneDziałania = dostępneDziałania;
        this.listaWyborców = listaWyborców;
        this.nrnajmniejszychOkręgów = namniejszeOkręgi();
        this.najtańszeDziałanie = dajnajtańszeDziałanie();
        this.najmniejszyKosztDziałania = najmniejszyKoszt();
        rand = new Random();

    }

    //Metoda odpowiadają za wybór najlpepszego działania przy zadanym budżecie i zwrócenie kosztu
    public abstract int zakupDziałanie(int budżet);

   //Metoda obliczająca koszt najtańszego działania w celu sprawdzenia czy kampanię stać na dalsze działanie
    private int najmniejszyKoszt() {
        return listaWyborców[nrnajmniejszychOkręgów.get(0)].length * najtańszeDziałanie.koszt;
    }



    //Metoda zwracająca listę numerów okręgów, w których jest najmniej wyborców
    private ArrayList<Integer> namniejszeOkręgi() {
        ArrayList<Integer> najmniesze = new ArrayList<>();
        int minObywateli = 1001;//Większe niż maksymalna liczba wyborców w okręgu

        for (int i = 0; i < listaWyborców.length; i++) {
            if (listaWyborców[i].length == minObywateli) {
                najmniesze.add(i);
            }
            if (listaWyborców[i].length < minObywateli) {
                minObywateli = listaWyborców[i].length;
                najmniesze = new ArrayList<>();
                najmniesze.add(i);
            }
        }
        return najmniesze;
    }

    protected Działanie dajnajtańszeDziałanie() {
        Działanie wybrane = dostępneDziałania[0];
        int min = dostępneDziałania[0].koszt; //Większe niż najdroższe możliwe działanie

        for (Działanie d : dostępneDziałania) {
            if (d.koszt < min) {
                wybrane = d;
                min = d.koszt;
            }
        }
        return wybrane;
    }

    protected boolean czyStarczaBudżetu(int budżet) {
        if (najmniejszyKosztDziałania> budżet) {
            return false;
        }
        else {
            return true;
        }
    }

    //Metoda do zastosowania działania na całej populacji danego okręgu wyborczego
    protected void działajwOkręgu(int nrOkręgu, Działanie d) {
        for (Wyborca w : listaWyborców[nrOkręgu]) {
            d.działajnaWyborcę(w);
        }
    }
}