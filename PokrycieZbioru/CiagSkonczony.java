public class CiagSkonczony extends Ciag {
    private int start;
    private int krok;
    private int koniec;

    public CiagSkonczony(int start,
                         int krok,
                         int koniec) {
        this.start = start;
        this.koniec = koniec;
        this.krok = krok;
    }

    //konstruktor dla skladnikow jednoelementowych
    public CiagSkonczony(int liczba) {
        this.start = liczba;
        this.koniec = liczba;
        this.krok = 1;
    }

    @Override
    protected boolean czy_zawiera(int element) {
        if (element > koniec || element < start) {
            return false;
        }
        return ((element - start) % krok == 0);
    }
}