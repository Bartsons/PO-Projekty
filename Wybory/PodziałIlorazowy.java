package wybory;

import java.util.ArrayList;
import java.util.Arrays;

//Klasa odpowiadająca metodom podziału mandatów bazującym na kolejnych ilorazach liczby zebranych głosów
public abstract class PodziałIlorazowy extends PodziałMandatów {

    protected int krok;

    public PodziałIlorazowy (Partia[] partie, int krok) {
        super(partie);
        this.krok = krok;
    }

    @Override
    public void przydzielMandatyOkręgom(Okrąg okrąg) {
        int[] głosynaPartie = okrąg.dajGłosynaPartie();
        int[] zebraneMandaty = new int[this.istniejącePartie().length];
        int[] ilorazy = new int[this.istniejącePartie().length];
        Arrays.fill(ilorazy, 1);
        int wolneMandaty = okrąg.liczbaKandydatów;
        while(wolneMandaty > 0) {
            dajMandat(ilorazy, głosynaPartie, zebraneMandaty);
            wolneMandaty--;
        }
        podsumujPrzydziałwOkręgu(zebraneMandaty, okrąg.numer());
    }


    //Metoda przydzielająca jeden mandat wybranej partii
    private void dajMandat(int[] ilorazy, int[] głosynaPartie, int[] zebraneMandaty) {
        ArrayList<Integer> największe = new ArrayList<>();
        double max = -1;
        double wartość;
        for (int nrPartii = 0; nrPartii < ilorazy.length; nrPartii++) {
            wartość = (1.0 * głosynaPartie[nrPartii]) / (1.0 * ilorazy[nrPartii]);
            if (wartość == max) {
                największe.add(nrPartii);
            }
            if (wartość > max) {
                największe = new ArrayList<>();
                największe.add(nrPartii);
                max = wartość;
            }
        }
        int wybranaPartia = największe.get(rand.nextInt(największe.size()));
        zebraneMandaty[wybranaPartia]++;
        this.istniejącePartie()[wybranaPartia].oddajMandat();
        ilorazy[wybranaPartia] += krok;
    }
}

