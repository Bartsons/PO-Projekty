package wybory;

//Klasa abstrakcyjna reprezentująca każdą osobę
public abstract class Człowiek {
    final String imię;
    final String nazwisko;

    protected Człowiek(String imię, String nazwisko) {
        this.imię = imię;
        this.nazwisko = nazwisko;
    }

    public String toString() {
        return imię + " " + nazwisko;
    }

    public void przedstawsię() {
        System.out.print("Nazywam się " + toString() + ".");
    }
}
