package wybory;

import java.util.Random;

//Klasa abstrakcyjna odpowiadająca za podzielenie mandatów w danych okręgach oraz na koniec podsumowanie przydziału dla każdej partii
public abstract class PodziałMandatów {

    protected String nazwaMetody;
    protected Random rand;
    private Partia[] partie;

    public PodziałMandatów (Partia[] partie) {

        this.partie = partie;
        rand = new Random();
    }

    //Metoda przydzielająca mandaty w danym okręgu
    public abstract void przydzielMandatyOkręgom(Okrąg okrąg);


    //Metoda wypisująca całkowite wynik całego przydziału
    public void podsumujPrzydział() {
        System.out.print( "Wyniki symulacji z metodą " + nazwaMetody + " przeliczania mandatów:\n");
        for (int i = 0; i < partie.length; i ++) {
            System.out.print(partie[i].nazwa + " otrzymała " + partie[i].ileMandatów() + " mandatów we wszystkich okręgach\n");
        }
        System.out.print( "\n");
    }

    //Metoda wypisująca wynik przydziału w jednym okręgu
    public void podsumujPrzydziałwOkręgu(int[] zebraneMandaty, int nrOkręgu) {
        System.out.print("Przydział mandatów w okegu nr " + nrOkręgu + ":\n");
        for (int i = 0; i < partie.length; i ++) {
            System.out.print(partie[i].nazwa + " otrzymała " + zebraneMandaty[i] + " mandatów.\n");
        }
    }

    public  void przedstaw() {
        System.out.print( "Metoda przelicznia głosów " + nazwaMetody + "\n");
    }

    protected Partia[] istniejącePartie() {
        return partie;
    }
}
