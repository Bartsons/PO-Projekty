package wybory;

import java.util.Scanner;

//Klasa mająca na celu przetworzenie wejścia i zwrcócenie na podstawie wejścia obiektu przeprowadzającego symulację na podstawie wczytanych danych
public class Input {

    private final Scanner scanner;
    private Wybory wybory;
    private int[] doScalenia; //tablica zawierający numery pierwszych z  dwóch w parze okręgów do scalenia
    private int liczbaCech;
    private int liczbaDziałań;

    public Input() {
        this.scanner = new Scanner(System.in);
    }

    //Metoda zwracająca utworzony obiekt z gotowym środowiskiem wyborów po wczytaniu
    public Wybory utwórzWybory() {
        czytajRozmiar();
        czytajScalanie();
        czytajPartie();
        czytajBudżety();
        czytajStrategie();
        czytajOkręgi();
        czytajKandydatów();
        czytajWyborców();
        czytajDziałania();

        wybory.scalOkręgi(doScalenia);
        wybory.ustawStrategie();

        return wybory;
    }


    //Metoda wczytująca pierwszą linię wejścia
    public void czytajRozmiar() {
        int n;
        int p;
        int d;
        int c;
        n = scanner.nextInt();
        p = scanner.nextInt();
        d = scanner.nextInt();
        c = scanner.nextInt();
        wybory = new Wybory(n, p, d, c);
        liczbaCech = c;
        liczbaDziałań = d;
    }

    //Metoda wczytująca drugą linię wejścia
    private void czytajScalanie() {
        int ilość = scanner.nextInt();
        this.doScalenia = new int[ilość];
        String para;
        for (int i = 0; i < ilość; i++) {
            para = scanner.next();
            if (para.charAt(2) == ',') {
                doScalenia[i] = Character.getNumericValue(para.charAt(1))-1;
            }
            else {//Przypadek gdy numer okręg do scalenia jest dwucyfrowy
                doScalenia[i] = 10*Character.getNumericValue(para.charAt(1))+
                            Character.getNumericValue(para.charAt(2))-1;
            }
        }
    }

    //Metoda wczytująca trzecią linię wejścia
    private void czytajPartie() {
        for (int i = 0; i < wybory.partie.length; i++) {
            wybory.dodajPartię(i, new Partia(scanner.next(), i));
        }
    }

    //Metoda wczytująca czwartą linię wejścia
    private void czytajBudżety() {
        for (int i = 0; i < wybory.partie.length; i++) {
            wybory.ustawBudżet(i, scanner.nextInt());
        }
    }

    //Metoda wczytująca piątą linię wejścia
    private void czytajStrategie() {
        for (int i = 0; i < wybory.partie.length; i++) {
            wybory.zapiszNazwęStrategii(i, scanner.next());
        }
    }

    //Metoda wczytująca szóstą linię wejścia
    private void czytajOkręgi() {
        for (int i = 0; i < wybory.podstawoweOkręgi.length; i++) {
            wybory.dodajOkrąg(i, scanner.nextInt());
        }
    }

    //Metoda wczytująca wszystkich kandydatów po kolei po partii, korzystamy z tego, ze kandydaci
    // na wejściu są po kolei okręgami, partiami i numerami
    private void czytajKandydatów() {
        for (int nrOkręgu = 0; nrOkręgu < wybory.podstawoweOkręgi.length; nrOkręgu++) {
            czytajKandydatówwOkręgu(nrOkręgu);
        }
    }

    private void czytajKandydatówwOkręgu(int nrOkręgu) {
        for (int nrPartii = 0; nrPartii < wybory.partie.length; nrPartii++) {
            czytajKandydatówPartii(nrOkręgu, nrPartii);
        }
    }

    private void czytajKandydatówPartii(int nrOkręgu, int nrPartii) {
        for (int nrnaLiście = 0; nrnaLiście < wybory.podstawoweOkręgi[nrOkręgu].liczbaKandydatów; nrnaLiście++) {
            //wybory.podstawoweOkręgi[nrOkręgu].dodajKandydata(stwórzKandydata(nrPartii), nrPartii, nrnaLiście);
            dodajKandydata(nrPartii);
        }
    }

    //Metoda dodająca kandydata na podstawie danych w jednej linijce
    private void dodajKandydata(int nrPartii) {
        String imię = scanner.next();
        String nazwisko = scanner.next();
        int nrOkręgu = scanner.nextInt() - 1;
        String nazwaPartii = scanner.next();
        int nrnaLiście = scanner.nextInt() - 1;

        int[] cechy = wczytajCechy();
        Partia p = wybierzPartię(nazwaPartii);

        wybory.podstawoweOkręgi[nrOkręgu].dodajKandydata(new Kandydat(imię, nazwisko, cechy, p), nrPartii, nrnaLiście);
    }

    //Metoda pomocnicza do wczytywania wetkora cech kandydata, wagi u wszechstronnych wyborców
    // oraz do wczytywania działań
    private int[] wczytajCechy() {
        int[] cechy = new int[liczbaCech];
        for (int i = 0; i < liczbaCech; i++) {
            cechy[i] = scanner.nextInt();
        }
        return cechy;
    }

    //Metoda wczytująca wszystkich wyborców po kolei okręgami
    private void czytajWyborców() {
        for (int nrOkręgu = 0; nrOkręgu < wybory.podstawoweOkręgi.length; nrOkręgu++) {
            czytajWyborcówwOkręgu(nrOkręgu);
        }
    }
    //Metoda wczytująca wszystkich wyborców w okręgu
    private void czytajWyborcówwOkręgu(int nrOkręgu) {
        for (int nrWyborcy = 0; nrWyborcy < 10*wybory.podstawoweOkręgi[nrOkręgu].liczbaKandydatów; nrWyborcy++) {
            czytajWyborcę(nrOkręgu, nrWyborcy);
        }
    }

    private void czytajWyborcę(int nrOkręgu, int nrWyborcy) {
        String imię = scanner.next();
        String nazwisko = scanner.next();
        int nrokręgu = scanner.nextInt()-1;
        int typWyborcy = scanner.nextInt();

        switch (typWyborcy) {
            case 1:
                czytajWyborcętypu1(imię, nazwisko, nrokręgu, nrWyborcy);
                break;
            case 2:
                czytajWyborcętypu2(imię, nazwisko, nrokręgu, nrWyborcy);
                break;
            case 3:
                czytajWyborcętypu3(imię, nazwisko, nrokręgu, nrWyborcy);
                break;
            case 4:
                czytajWyborcętypu4(imię, nazwisko, nrokręgu, nrWyborcy);
                break;
            case 5:
                czytajWyborcętypu5(imię, nazwisko, nrokręgu, nrWyborcy);
                break;
            case 6:
                czytajWyborcętypu6(imię, nazwisko, nrokręgu, nrWyborcy);
                break;
            case 7:
                czytajWyborcętypu7(imię, nazwisko, nrokręgu, nrWyborcy);
                break;
            case 8:
                czytajWyborcętypu8(imię, nazwisko, nrokręgu, nrWyborcy);
                break;
            default:
                System.out.print("Niepoprawny typ wyborcy!\n");
        }
    }


    private void czytajWyborcętypu1(String imię, String nazwisko, int nrokręgu, int nrWyborcy) {
        String nazwaPartii = scanner.next();
        wybory.podstawoweOkręgi[nrokręgu].dodajWyborcę(nrWyborcy,
                new ŻelaznyElektoratPartyjny(imię, nazwisko, wybierzPartię(nazwaPartii)));
    }

    private void czytajWyborcętypu2(String imię,String nazwisko, int nrokręgu, int nrWyborcy) {
        String nazwaPartii = scanner.next();
        int nrKandydata = scanner.nextInt();//-1 dodane w nastepnej funkcji

        wybory.podstawoweOkręgi[nrokręgu].dodajWyborcę(nrWyborcy,
                new ŻelaznyElektoratKandydata(imię, nazwisko,
                        wybory.podstawoweOkręgi[nrokręgu].znajdzKandydata(wybierzPartię(nazwaPartii), nrKandydata)));
    }

    private void czytajWyborcętypu3(String imię, String nazwisko, int nrokręgu, int nrWyborcy) {
        int nrCechy = scanner.nextInt();
        wybory.podstawoweOkręgi[nrokręgu].dodajWyborcę(nrWyborcy,
                new MinimalizującyJednocechowy(imię, nazwisko, nrCechy));
    }

    private void czytajWyborcętypu4(String imię, String nazwisko, int nrokręgu, int nrWyborcy) {
        int nrCechy = scanner.nextInt();
        wybory.podstawoweOkręgi[nrokręgu].dodajWyborcę(nrWyborcy,
                new MaksymalizującyJednocechowy(imię, nazwisko, nrCechy));
    }

    private void czytajWyborcętypu5(String imię, String nazwisko, int nrokręgu, int nrWyborcy) {
        int[] wektorWag = wczytajCechy();
        wybory.podstawoweOkręgi[nrokręgu].dodajWyborcę(nrWyborcy,
                new Wszechstronny(imię, nazwisko, wektorWag));
    }

    private void czytajWyborcętypu6(String imię, String nazwisko, int nrokręgu, int nrWyborcy) {
        int nrCechy = scanner.nextInt();
        String nazwaPartii = scanner.next();
        wybory.podstawoweOkręgi[nrokręgu].dodajWyborcę(nrWyborcy,
                new MinimalizującyJednocechowyPartyjniak(imię, nazwisko, nrCechy, wybierzPartię(nazwaPartii)));
    }

    private void czytajWyborcętypu7(String imię, String nazwisko, int nrokręgu, int nrWyborcy) {
        int nrCechy = scanner.nextInt();
        String nazwaPartii = scanner.next();
        wybory.podstawoweOkręgi[nrokręgu].dodajWyborcę(nrWyborcy,
                new MaksymalizującyJednocechowyPartyjniak(imię, nazwisko, nrCechy, wybierzPartię(nazwaPartii)));
    }
    private void czytajWyborcętypu8(String imię, String nazwisko, int nrokręgu, int nrWyborcy) {
        int[] wektorWag = wczytajCechy();
        String nazwaPartii = scanner.next();
        wybory.podstawoweOkręgi[nrokręgu].dodajWyborcę(nrWyborcy,
                new WszechstronnyPartyjniak(imię, nazwisko, wektorWag, wybierzPartię(nazwaPartii)));
    }

    private Partia wybierzPartię(String nazwaPartii) {
        for (Partia partia: wybory.partie) {
            if (partia.nazwa.equals(nazwaPartii)) {
                return partia;
            }
        }
        System.out.print("Wybrana to " + wybory.partie[0].nazwa + "\n");
        return wybory.partie[0];
    }

    //Metoda wczytująca na wejściu działania
    private void czytajDziałania() {
        int[] wektorWag;
        for (int i = 0; i < liczbaDziałań; i++) {
            wektorWag = wczytajCechy();
            wybory.dodajDziałanie(i, new Działanie(wektorWag));
        }
    }
}
