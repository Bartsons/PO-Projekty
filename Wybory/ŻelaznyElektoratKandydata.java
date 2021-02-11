package wybory;

public class ŻelaznyElektoratKandydata extends Wyborca {
    private Kandydat ulubionyKandydat;

    public ŻelaznyElektoratKandydata(String imię, String nazwisko, Kandydat ulubionyKandydat) {
        super(imię, nazwisko);
        this.ulubionyKandydat = ulubionyKandydat;
    }

    @Override
    public Kandydat głosuj(Kandydat[][] wszyscyKandydaci) {
        return ulubionyKandydat;
    }
}
