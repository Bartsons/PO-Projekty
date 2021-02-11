package wybory;

//Klasa reprezentująca kandydata
public class Kandydat extends Człowiek {

    protected int[] cechy; //Cechy kandydatów reprezentowane jako liczby całkowite
    protected Partia partia;
    private int zebraneGłosy;

    public Kandydat(String imię, String nazwisko, int[] cechy, Partia partia) {
        super(imię, nazwisko);
        this.cechy = cechy;
        this.partia = partia;
        this.zebraneGłosy = 0;
    }

    public int[] dajCechy() {
        return cechy;
    }

    public int dajCechę(int nrCechy) {
        return cechy[nrCechy];
    }

    //Metoda odpowiadająca oddaniu głosu na danego kandydata, przydziela również głos jego partii
    public void oddajGłos() {
        zebraneGłosy++;
        partia.oddajGłos();
    }

    @Override
    public String toString() {
        return super.toString() + " " + " z " + partia.nazwa;
    }

    @Override
    public void przedstawsię() {
        System.out.print("Jestem " + toString() + ". Głosujcie na mnie!");
    }

    public void resetujGłosy() {
        zebraneGłosy = 0;
    }

    public int pokażGłosy() {
        return zebraneGłosy;
    }
}
