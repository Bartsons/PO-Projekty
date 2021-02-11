package wybory;

public abstract class Jednocechowy extends Wyborca {

    protected int porządanaCecha;

    public Jednocechowy(String imię, String nazwisko, int nrCechy) {
        super(imię, nazwisko);
        this.porządanaCecha = nrCechy - 1;
    }
}
