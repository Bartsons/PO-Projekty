package wybory;

import java.util.ArrayList;

//Klasa reprezetująca strategię zachłanną, podejmującą działania o największej zmianie poglądów
public class Zachłanna extends Strategia {

    final Kandydat[][] swoiKandydaci;

    public Zachłanna(Partia partia, Działanie[] dostępneDziałania, Wyborca listaWyborców[][], Kandydat[][] listaKandydatów) {
        super(partia, dostępneDziałania, listaWyborców);
        this.swoiKandydaci = listaKandydatów;
    }
    @Override
    public int zakupDziałanie(int budżet) {
        if (!czyStarczaBudżetu(budżet)) {
            //Komunikat że partii nie stać już na żadne działanie
            return 0;
        }
        int[] nrOkręgu = new int[1];
        Działanie d = zachłanneDziałanie(budżet, nrOkręgu);
        działajwOkręgu(nrOkręgu[0], d);
        return  listaWyborców[nrOkręgu[0]].length * d.koszt;
    }

    //Metoda służąca znalezieniu działania, które w największym stopniu zwiększy sumę sum ważonych cech swoich kandydatów
    private Działanie zachłanneDziałanie(int budżet, int[] nrOkręgu) {
        ArrayList<Działanie> zachłanneDziałania = new ArrayList<>();
        ArrayList<Integer> numeryOkręgów = new ArrayList<>();
        int max = -100*1000*100;
        int zmiana;
        int koszt;

        for (int i = 0; i < listaWyborców.length; i++) {
            for (Działanie d : dostępneDziałania) {
                koszt = d.koszt * listaWyborców[i].length;
                if (koszt <= budżet) {
                    zmiana = policzZmianę(d, i);
                    if (zmiana == max) {
                        zachłanneDziałania.add(d);
                        numeryOkręgów.add(i);
                    }
                    if (zmiana > max) {
                        zachłanneDziałania = new ArrayList<>();
                        zachłanneDziałania.add(d);
                        numeryOkręgów.add(i);
                        max = zmiana;
                    }
                }
            }
        }

        int index = rand.nextInt(zachłanneDziałania.size());//Przy wielu działaniach o równej zmianie wybierane jest losowe
        nrOkręgu[0] = numeryOkręgów.get(index);

        return zachłanneDziałania.get(index);
    }

    private int policzZmianę(Działanie d, int nrOkręgu) {
        int suma = 0;
        for (Wyborca w : listaWyborców[nrOkręgu]) {
            suma += oszacujZmianę(w, nrOkręgu, d);
        }
        return suma;
    }

    private int oszacujZmianę(Wyborca w, int nrOkręgu, Działanie d) {
        int suma = 0;
        for (Kandydat k : swoiKandydaci[nrOkręgu]) {
            suma += w.policzZmianę(k, d);
        }
        return suma;
    }
}
