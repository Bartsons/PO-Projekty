package wybory;

import java.util.ArrayList;

public class MinimalizującyJednocechowy extends Jednocechowy {

    public MinimalizującyJednocechowy(String imię, String nazwisko, int nrCechy) {
        super(imię, nazwisko, nrCechy);
    }

    @Override
    public Kandydat głosuj(Kandydat[][] wszyscyKandydaci) {
        ArrayList<Kandydat> najlepsi = new ArrayList<>();//lista kandydatów o największej wartości danej cechy
        int najniższaWartość = 101;
        for (Kandydat[] kandydacizPartii : wszyscyKandydaci) {
            for (int i = 0; i < kandydacizPartii.length; i++) {
                if (kandydacizPartii[i].dajCechę(porządanaCecha) == najniższaWartość) {
                    najlepsi.add(kandydacizPartii[i]);
                }
                if (kandydacizPartii[i].dajCechę(porządanaCecha) < najniższaWartość) {
                    najlepsi = new ArrayList<>();
                    najlepsi.add(kandydacizPartii[i]);
                    najniższaWartość = kandydacizPartii[i].dajCechę(porządanaCecha);
                }
            }
        }
        return najlepsi.get(rand.nextInt(najlepsi.size()));
    }
}