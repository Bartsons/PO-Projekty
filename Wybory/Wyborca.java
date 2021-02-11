package wybory;

import java.util.Random;

public abstract class Wyborca extends Człowiek {
    protected final Random rand;//Wyborcy mający kilku wybranych kandydatów wybierają w sposób losowy
    
    protected Partia ulubionaPartia;//Partyjniacy strzęgą swojej ulubiną partię

    public Wyborca(String imię, String nazwisko) {
        super(imię, nazwisko);
        this.rand = new Random();
    }

    public Kandydat głosuj (Kandydat[][] wszyscyKandydaci) {
        //domyślnie wyborca głosuje losowo
        return wszyscyKandydaci[rand.nextInt(wszyscyKandydaci.length)][rand.nextInt(wszyscyKandydaci[0].length)];
    }

    //Domyślnie działanie nie zmienia poglądów zatwardziałych wyborców mimo iż jest w ich stronę skierowane
    public void zmieńCechę(int nrCechy, int zmiana) {}

    //Metoda licząca jak duża będzie zmiana sumy ważonej dla danego kandydata - domyślnie nic nie zmienia
    public int policzZmianę(Kandydat kandydat, Działanie d) {
        return 0;
    }

    //Metoda używana przy powtarzaniu symulacji-domyślnie dotyczy tylko posiadających preferencie wszechstronnych wyborców
    public void resetujCechy() {}
}