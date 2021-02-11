package wybory;

public class ŻelaznyElektoratPartyjny extends Wyborca implements Partyjniak {

    public ŻelaznyElektoratPartyjny(String imię, String nazwisko, Partia partia) {
        super(imię, nazwisko);
        this.ulubionaPartia = partia;
    }

    @Override
    public Kandydat głosuj(Kandydat[][] wszyscyKandydaci) {
        return super.głosuj(swoiKandydaci(wszyscyKandydaci, ulubionaPartia));
    }
}
