package wybory;

import java.util.ArrayList;

//Własna strategia, której celem jest zdyskredytowaniu u wyborców niewygodnej dla partii cechy.
//Oznacza to, że Strategia ta wybiera najopłacalniejsze działanie (opłacalność = zmiana/koszt) zmiejszająca u wyborców cechę najniższą spośród cech kandydatów
public class UkrywanieWad extends Strategia {

    final Kandydat[][] swoiKandydaci;
    private int niewygodnaCecha;

    public UkrywanieWad (Partia partia, Działanie[] dostępneDziałania, Wyborca listaWyborców[][], Kandydat[][] listaKandydatów) {
        super(partia, dostępneDziałania, listaWyborców);
        this.swoiKandydaci = listaKandydatów;
        this.niewygodnaCecha = znajdźniewygodnąCechę();
    }

    //Metoda do znalezienia niewygodnej cechy
    private int znajdźniewygodnąCechę() {
        int liczbaCech = dostępneDziałania[0].zmianaWag.length;
        int wybranynr = 0;
        int najgorszywpływ = 100*100*swoiKandydaci.length +1;
        int wpływCechy;

        for (int nrCechy = 0; nrCechy < liczbaCech; nrCechy++) {
            wpływCechy = policzWpływCechy(nrCechy);
            if (wpływCechy < najgorszywpływ) {
                najgorszywpływ = wpływCechy;
                wybranynr = nrCechy;
            }
        }
        return wybranynr;
    }

    //Metoda licząca sumę cechy u kandydatów partii
    private int policzWpływCechy(int nrCechy) {
        int suma = 0;
        for (int nrOkręgu = 0; nrOkręgu < swoiKandydaci.length; nrOkręgu++) {
            for (Kandydat k : swoiKandydaci[nrOkręgu]) {
                suma+=k.dajCechę(nrCechy);
            }
        }
        return suma;
    }

    @Override
    public int zakupDziałanie(int budżet) {
        if (!czyStarczaBudżetu(budżet)) {
            //Komunikat że partii nie stać już na żadne działanie
            return 0;
        }
        int[] nrOkręgu = new int[1];
        Działanie d = najlepszeDziałanie(budżet, nrOkręgu);
        działajwOkręgu(nrOkręgu[0], d);
        return  listaWyborców[nrOkręgu[0]].length * d.koszt;
    }

    //Metoda znajdująca opłacalne działanie i losowy okrąg, w którym je wykona
    private Działanie najlepszeDziałanie(int budżet, int[] nrOkręgu) {
        ArrayList<Działanie> najlepszeDziałania = new ArrayList<>();
        ArrayList<Integer> numeryOkręgów = new ArrayList<>();
        float opłacalność; //Przy poprawnych danych wszystkie działania mają dodatni koszt
        float maxopłacalność = -100;

        for (int i = 0; i < listaWyborców.length; i++) {
            for (Działanie d : dostępneDziałania) {
                opłacalność = d.koszt * listaWyborców[i].length;
                if (opłacalność <= budżet) {
                    opłacalność = d.zmianaWag[niewygodnaCecha]/d.koszt;
                    if (opłacalność == maxopłacalność) {
                        najlepszeDziałania.add(d);
                        numeryOkręgów.add(i);
                    }
                    if (opłacalność > maxopłacalność) {
                        najlepszeDziałania = new ArrayList<>();
                        najlepszeDziałania.add(d);
                        numeryOkręgów.add(i);
                        maxopłacalność = opłacalność;
                    }
                }
            }
        }
        int index = rand.nextInt(najlepszeDziałania.size());
        nrOkręgu[0] = numeryOkręgów.get(index);

        return najlepszeDziałania.get(index);
    }
}