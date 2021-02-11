package wybory;

public class MaksymalizującyJednocechowyPartyjniak extends Jednocechowy implements Partyjniak{

    public MaksymalizującyJednocechowyPartyjniak(String imię, String nazwisko, int nrCechy, Partia partia) {
        super(imię, nazwisko, nrCechy);
        this.ulubionaPartia = partia;
    }

    @Override
    public Kandydat głosuj(Kandydat[][] wszyscyKandydaci) {
        return super.głosuj(swoiKandydaci(wszyscyKandydaci, ulubionaPartia));
    }


}
