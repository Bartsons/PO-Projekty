package wybory;

import java.util.ArrayList;

//Klasa reprezetująca strategię z rozmachem
public class ZRozmachem extends Strategia {
    public ZRozmachem(Partia partia, Działanie[] dostępneDziałania, Wyborca listaWyborców[][]) {
        super(partia, dostępneDziałania, listaWyborców);
    }

    @Override
    public int zakupDziałanie(int budżet) {
        if (!czyStarczaBudżetu(budżet)) {
            //Komunikat że partii nie stać już na żadne działanie
            return 0;
        }
        int[] nrOkręgu = new int[1];
        Działanie d = najdroższeDziałanie(budżet, nrOkręgu);
        działajwOkręgu(nrOkręgu[0], d);
        return listaWyborców[nrOkręgu[0]].length * d.koszt;
    }


    //Metoda służąca znalezieniu i zwróceniu losowego z najdroższych działań w okręgach przy zadanym budżecie
    private Działanie najdroższeDziałanie(int budżet, int[] nrOkręgu) {
        ArrayList<Działanie> najdroższeDziałania = new ArrayList<>();
        ArrayList<Integer> numeryOkręgów = new ArrayList<>();
        int max = 0; //Przy poprawnych danych wszystkie działania mają dodatni koszt
        int koszt;

        for (int i = 0; i < listaWyborców.length; i++) {
            for (Działanie d : dostępneDziałania) {
                koszt = d.koszt * listaWyborców[i].length;
                if (koszt <= budżet) {
                    if (koszt == max) {
                        najdroższeDziałania.add(d);
                        numeryOkręgów.add(i);
                    }
                    if (koszt > max) {
                        najdroższeDziałania = new ArrayList<>();
                        najdroższeDziałania.add(d);
                        numeryOkręgów.add(i);
                        max = koszt;
                    }
                }
            }
        }

        int index = rand.nextInt(najdroższeDziałania.size());
        nrOkręgu[0] = numeryOkręgów.get(index);

        return najdroższeDziałania.get(index);
    }
}
