package wybory;

import java.util.ArrayList;

public class HareNiemeyer extends PodziałMandatów {

    public HareNiemeyer (Partia[] partie) {
        super(partie);
        this.nazwaMetody = "Hare’a-Niemeyera";
    }

    @Override
    public void przydzielMandatyOkręgom(Okrąg okrąg) {
        int[] głosynaPartie = okrąg.dajGłosynaPartie();
        int wszystkieGłosy = 0;
        int[] zebraneMandaty = new int[this.istniejącePartie().length];
        double[] poPrzecinku = new double[this.istniejącePartie().length];
        int wolneMandaty = okrąg.liczbaKandydatów;
        int wszystkieMandaty = okrąg.liczbaKandydatów;

        for (int i = 0; i < this.istniejącePartie().length; i ++) {
            wszystkieGłosy += głosynaPartie[i];
        }

        for (int i = 0; i < this.istniejącePartie().length; i ++) {
            zebraneMandaty[i]  = (wszystkieMandaty * głosynaPartie[i])/wszystkieGłosy;
            wolneMandaty -= zebraneMandaty[i];
            poPrzecinku[i] += (wszystkieMandaty * głosynaPartie[i])/wszystkieGłosy - 1.0 * zebraneMandaty[i];
            for (int j = 0; j < zebraneMandaty[i]; j++) {
                this.istniejącePartie()[i].oddajMandat();
            }
        }
        przydzielPozostałe(wolneMandaty, zebraneMandaty, poPrzecinku);
        podsumujPrzydziałwOkręgu(zebraneMandaty, okrąg.numer());
    }

    //Metoda przydzielająca wolne mandaty
    private void przydzielPozostałe(int wolneMandaty, int[] zebraneMandaty, double[] poPrzecinku) {
        while (wolneMandaty > 0) {
            przydzielMandat(zebraneMandaty, poPrzecinku);
            wolneMandaty--;
        }
    }

    //Metoda przydzielająca jeden wolny mandat
    private void przydzielMandat(int[] zebraneMandaty, double[] poPrzecinku) {
        ArrayList<Integer> remisujące = new ArrayList<>();
        double max = 0;
        for (int nrPartii = 0; nrPartii < poPrzecinku.length; nrPartii++) {
            if (poPrzecinku[nrPartii] == max) {
                remisujące.add(nrPartii);
            }
            if (poPrzecinku[nrPartii] > max) {
                remisujące = new ArrayList<>();
                remisujące.add(nrPartii);
                max = poPrzecinku[nrPartii];
            }
        }
        int wybranaPartia = remisujące.get(rand.nextInt(remisujące.size()));
        zebraneMandaty[wybranaPartia]++;
        this.istniejącePartie()[wybranaPartia].oddajMandat();
        poPrzecinku[wybranaPartia] = 0;
    }
}
