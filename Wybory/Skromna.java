package wybory;

//Klasa reprezetująca strategię skromną
public class Skromna extends Strategia {

    public Skromna(Partia partia, Działanie[] dostępneDziałania, Wyborca listaWyborców[][]) {
        super(partia, dostępneDziałania, listaWyborców);
    }

    @Override
    public int zakupDziałanie(int budżet) {
        if (!czyStarczaBudżetu(budżet)) {
            //Komunikat że partii nie stać już na żadne działanie
            return 0;
        }
        else {
            Działanie d = najtańszeDziałanie;
            int nrOkręgu = nrnajmniejszychOkręgów.get(rand.nextInt(nrnajmniejszychOkręgów.size()));
            działajwOkręgu(nrOkręgu, d);

            return  listaWyborców[nrOkręgu].length * d.koszt;
        }
    }

}
