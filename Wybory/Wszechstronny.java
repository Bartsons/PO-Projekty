package wybory;

import java.util.ArrayList;

public class Wszechstronny extends Wyborca {

    final static int minCecha = -100;
    final static int maxCecha = 100;
    final private int[] pierwotnywektorWag;
    private int[] wektorWag;

    public Wszechstronny (String imię, String nazwisko, int[] wektorWag) {
        super(imię, nazwisko);
        this.wektorWag = wektorWag;
        this.pierwotnywektorWag = wektorWag;
    }

    @Override
    public void zmieńCechę(int nrCechy, int zmiana) {
        wektorWag[nrCechy] += zmiana;
        if (wektorWag[nrCechy] > maxCecha) {
            wektorWag[nrCechy] = maxCecha;
        }
        if (wektorWag[nrCechy] < minCecha) {
            wektorWag[nrCechy] = minCecha;
        }
    }

    @Override
    public Kandydat głosuj(Kandydat[][] wszyscyKandydaci) {
        ArrayList<Kandydat> najlepsi = new ArrayList<>();//lista kandydatów o największej wartości danej cechy
        int najwyższaWartość = wartoscKandydata(wszyscyKandydaci[0][0]);
        int wartość;

        for (int nrPartii = 0; nrPartii < wszyscyKandydaci.length; nrPartii++) {
            for (int nrKandydata = 0; nrKandydata < wszyscyKandydaci[nrPartii].length; nrKandydata++) {
                wartość = wartoscKandydata(wszyscyKandydaci[nrPartii][nrKandydata]);
                if (wartość == najwyższaWartość) {
                    najlepsi.add(wszyscyKandydaci[nrPartii][nrKandydata]);
                }
                if (wartość > najwyższaWartość) {
                    najlepsi = new ArrayList<>();
                    najlepsi.add(wszyscyKandydaci[nrPartii][nrKandydata]);
                    najwyższaWartość = wartość;
                }
            }
        }
        return najlepsi.get(rand.nextInt(najlepsi.size()));
    }

    //Metoda licząca jak duża będzie zmiana sumy ważonej dla danego kandydata
    @Override
    public int policzZmianę(Kandydat kandydat, Działanie d) {
        d.działajnaWyborcę(this);
        int nowaSuma=wartoscKandydata(kandydat);
        d.cofnijdziałanie(this);

        return nowaSuma - wartoscKandydata(kandydat);
    }

    private int wartoscKandydata(Kandydat kandydat) {
        int[] cechy = kandydat.dajCechy();

        int suma = 0;
        int index = 0;
        for (int c : cechy) {
            suma += c*wektorWag[index];
            index++;
        }

        return suma;
    }

    //Metoda przywracająca wektor wag do początkowego stanu
    public void resetujCechy() {
        wektorWag = pierwotnywektorWag.clone();
    }
}
