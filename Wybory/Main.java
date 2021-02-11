package wybory;

//Klasa Main tworząca wybory i przeprowadzająca symulację wyborów wraz z każdą z 3 metod przydzielania mandatów
public class Main {

    public static void main(String[] args) {
        Input inp = new Input();
        Wybory w = inp.utwórzWybory();

        w.przeprowadźKampanie();
        for (int nrMetody = 0; nrMetody < 3; nrMetody++) {
            w.przeprowadźWybory(nrMetody);
        }
    }
}
