package wybory;

//Klasa odpowiadająca za przeprowadzenie wyborów, przechowuje też partie i niższe w heratchi obiekkty jak okręgi wyborcze, w których z osobna przeprowadzone będą głosowania
public class Wybory {

    protected Działanie[] dostępnedziałania;
    protected Okrąg[] podstawoweOkręgi;
    private int[] ostateczneNumeryOkręgów;
    protected  Partia[] partie;
    private String[] nazwyStrategii;
    private Wyborca listaWyborców[][];
    private Kandydat[][][] listaKandydatów;//Lista kandydatów podzielonych partiami, okręgami, a następnie numerem na liście
    private PodziałMandatów podziałMandatów;

    public Wybory(int n, int p, int d, int c) {
        this.podstawoweOkręgi = new Okrąg[n];
        this.partie = new Partia[p];
        this.dostępnedziałania = new Działanie[d];
        this.listaKandydatów= new Kandydat[p][][];
        this.nazwyStrategii = new String[p];
    }

    public void przeprowadźWybory(int nrMetody) {
        resetujWybory();
        stwórzMetodęPrzydziałuMandatów(nrMetody);
        podziałMandatów.przedstaw();
        //Iterujemy tylko po okręgach, które nie zostały dołączone do swoich poprzednich
        for (int nrOkręgu : ostateczneNumeryOkręgów) {
            podstawoweOkręgi[nrOkręgu].przeprowadźWybory();
            podziałMandatów.przydzielMandatyOkręgom(podstawoweOkręgi[nrOkręgu]);
        }
        podziałMandatów.podsumujPrzydział();
    }

    //Metoda odpowiadająca za podział mandatów przy wybranej metodzie podziału
    public void stwórzMetodęPrzydziałuMandatów(int nrMetody) {
        switch (nrMetody) {
            case 0:
                podziałMandatów = new DHondta(partie);
                break;
            case 1:
                podziałMandatów = new SaintLague(partie);
                break;
            case 2:
                podziałMandatów = new HareNiemeyer(partie);
                break;

            default:
                throw new IllegalArgumentException("Niepoprawny numer metody przydzielania mandatów!\n");
        }
    }

    //Metody dodająca partię do symulacji i ustawiające jej parametry
    public void dodajPartię (int index, Partia partia) {
        partie[index] = partia;
    }

    public void ustawBudżet (int index, int budżet) {
        partie[index].ustawBudżet(budżet);
    }

    //Metoda odpowiadająca za przekazanie partiom wczytanych wcześniej strategii
    public void ustawStrategie() {
        for (int i = 0; i < partie.length; i++) {
            dodajStrategię(i);
        }
    }

    //Metoda zapisująca wczytaną nazwę strategii
    public void zapiszNazwęStrategii(int index, String nazwaStrategii) {
        nazwyStrategii[index] = nazwaStrategii;
    }

    private void dodajStrategię(int index) {
        Strategia strategia;
        String nazwaStrategii = nazwyStrategii[index];
        switch (nazwaStrategii) {
            case "R":
                strategia = new ZRozmachem(partie[index], dostępnedziałania, listaWyborców);
                break;
            case "S":
                strategia = new Skromna(partie[index], dostępnedziałania, listaWyborców);
                break;
            case "W":
                strategia = new UkrywanieWad(partie[index], dostępnedziałania, listaWyborców, listaKandydatów[index]);
                break;
            case "Z":
                strategia = new Zachłanna(partie[index], dostępnedziałania, listaWyborców, listaKandydatów[index]);
                break;

            default:
                throw new IllegalArgumentException("Niepoprawna nazwa strategii!\n");
        }
        partie[index].ustawStrategię(strategia);
    }

    public void dodajOkrąg (int index, int rozmiar) {
        podstawoweOkręgi[index] = new Okrąg(index, rozmiar, partie);
    }

    //Metoda dodająca nowe działanie
    public void dodajDziałanie (int index, Działanie d) {
        dostępnedziałania[index] = d;
    }


    //Metoda scalająca okręgi
    public void scalOkręgi(int[] doScalenia) {
        for (int nrOkręgu : doScalenia) {
            //Scalony okrąg pozostanie na miejscu tego o mnijeszym indeksie
            podstawoweOkręgi[nrOkręgu].scal(podstawoweOkręgi[nrOkręgu+1]);
        }
        zaktualizujIndeksy(doScalenia);
        //Po scalaniu okręgów tworzymy też listę kandydatów
        stwórzListyWyborcze();
    }

    //Metoda do zaznaczenia, które okręgi będziemy sprawdzać po scaleniu - będą to okregi o numerach z ostateczneNumeryOkręgów
    //Przy okazji metoda ta ustawia listę wyborów, która przekazujemy partiom
    private void zaktualizujIndeksy(int[] doScalenia) {
        ostateczneNumeryOkręgów = new int[podstawoweOkręgi.length - doScalenia.length];
        listaWyborców = new Wyborca[ostateczneNumeryOkręgów.length][];
        int nrScalony=0;
        int nowyIndex=0;
        for (int i = 0; i < podstawoweOkręgi.length; i++) {
            ostateczneNumeryOkręgów[nowyIndex] = i;
            listaWyborców[nowyIndex] = podstawoweOkręgi[i].dajListaWyborców();
            if (nrScalony < doScalenia.length && i == doScalenia[nrScalony]) {
                i++;
                nrScalony++;
            }
            nowyIndex++;
        }
    }

    //Metoda ustawiająca tablicę ze wszystkimi kandydatami np list w okręgach
    private void stwórzListyWyborcze() {
        listaKandydatów = new Kandydat[partie.length][ostateczneNumeryOkręgów.length][];

        for (int nrOkręgu = 0; nrOkręgu < ostateczneNumeryOkręgów.length; nrOkręgu++) {
            for (int nrPartii = 0; nrPartii < partie.length; nrPartii++) {
                listaKandydatów[nrPartii][nrOkręgu] = podstawoweOkręgi[ostateczneNumeryOkręgów[nrOkręgu]].kandydaci[nrPartii];
            }
        }
    }

    //Metoda przy rewsetowaniu wyborów - usuwa głosy u partii i kandydatów, a wyborcom resetuje ich cechy
    public void resetujWybory() {
        for (Partia p : partie) {
            p.resetujSymulacjęPartii();
            for (int nrOkręgu = 0; nrOkręgu < ostateczneNumeryOkręgów.length; nrOkręgu++) {
                for (Kandydat k : listaKandydatów[p.numerListy][nrOkręgu]) {
                    k.resetujGłosy();

                }
            }
            for (int nrOkręgu = 0; nrOkręgu < ostateczneNumeryOkręgów.length; nrOkręgu++) {
                for (Wyborca w : listaWyborców[p.numerListy]) {
                    w.resetujCechy();
                }
            }
        }
    }

    //Metoda symulujaca kampanie wyborczą, partie na przemian wykonują działania(ktrórych podjęcie powinno zajmować zbliżony czas, stąd taka implementacja)
    public void przeprowadźKampanie() {
        boolean czyDalej = true;

        while (czyDalej) {
            czyDalej = false;
            for (Partia p : partie) {
                czyDalej = czyDalej || p.czyprowadziKampanię();
            }

            if (czyDalej) {
                for (Partia p : partie) {
                    p.podejmijDziałaniaWyborcze();
                }
            }
        }
    }
}
