package wybory;

import java.util.ArrayList;

public class MaksymalizującyJednocechowy extends Jednocechowy {
    public MaksymalizującyJednocechowy(String imię, String nazwisko, int nrCechy) {
        super(imię, nazwisko, nrCechy);
    }

    @Override
    public Kandydat głosuj(Kandydat[][] wszyscyKandydaci) {
        ArrayList<Kandydat> najlepsi = new ArrayList<>();//lista kandydatów o największej wartości danej cechy
        int najwyższaWartość = -101;
        for (Kandydat[] kandydacizPartii : wszyscyKandydaci) {
            for (int i = 0; i < kandydacizPartii.length; i++) {
                if (kandydacizPartii[i].dajCechę(porządanaCecha) == najwyższaWartość) {
                    najlepsi.add(kandydacizPartii[i]);
                }
                if (kandydacizPartii[i].dajCechę(porządanaCecha) > najwyższaWartość) {
                    najlepsi = new ArrayList<>();
                    najlepsi.add(kandydacizPartii[i]);
                    najwyższaWartość = kandydacizPartii[i].dajCechę(porządanaCecha);
                }
            }
        }
        return najlepsi.get(rand.nextInt(najlepsi.size()));
    }
}

