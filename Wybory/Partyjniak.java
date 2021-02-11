package wybory;

//Interfejs dla wyborców  ograniczających się do jednej partii.
public interface Partyjniak {

    //Każdy partyjnak patrzy tylko na listę kandydatów ze swojej partii
    //Metoda odpowiada za ograniczenie tablicy kandydatów tylko do jednej partii
    default Kandydat[][] swoiKandydaci(Kandydat[][] wszyscyKandydaci, Partia partia) {
        Kandydat[][] dostępniKandydaci = new Kandydat[1][];
        dostępniKandydaci[0] = wszyscyKandydaci[partia.numerListy];
        return dostępniKandydaci;
    }
}
