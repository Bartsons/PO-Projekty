public class CiagNieskonczony extends Ciag{
    private int start;
    private int krok;

    public CiagNieskonczony(int start,
                            int krok) {
        this.start = start;
        this.krok = krok;
    }

    @Override
    protected boolean czy_zawiera(int element) {
        if(element < start){
            return false;
        }
        boolean wynik = (element-start) % krok == 0;
        return wynik;
    }
}