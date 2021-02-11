package wybory;

public class WszechstronnyPartyjniak extends Wszechstronny implements Partyjniak{

    public WszechstronnyPartyjniak(String imię, String nazwisko, int[] wektorWag, Partia partia) {
        super(imię, nazwisko, wektorWag);
        this.ulubionaPartia = partia;
    }

    @Override
    public Kandydat głosuj(Kandydat[][] wszyscyKandydaci) {
        return super.głosuj(swoiKandydaci(wszyscyKandydaci, ulubionaPartia));
    }

}
