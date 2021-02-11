import java.util.ArrayList;
import java.util.Collections;

public class Rodzina {
    private ArrayList<Zbior> zbiory;

    public void dodajZbior(Zbior Z){
        zbiory.add(Z);
    }

    public Rodzina(){
        this.zbiory = new ArrayList<Zbior>();
    }


    public void sprawdz_pokrycie(int instancja, int zakres) {
        boolean[] pokryte_elementy = new boolean[zakres];
        //tablica elementow, ktore trzeba pokryc, pozycja i odpowiada liczbie i+1
        switch (instancja){
            case 1:
                dokladny(pokryte_elementy);
                break;
            case 2:
                zachlanny(pokryte_elementy);
                break;
            case 3:
                naiwny(pokryte_elementy);
                break;
            default:
                System.out.print("Niepoprawny sposob");
        }
    }

    private void dokladny(boolean[] pokryte_elementy) {

        //lista indeksów zbiorów uzywanych do pokrycia
        ArrayList<Integer> obecne_pokrycie = new ArrayList<Integer>();
        obecne_pokrycie.add(0);
        while(obecne_pokrycie.get(0) >= 0) {
            if(czy_zbiory_pokryja(pokryte_elementy, obecne_pokrycie)) {
                wypisz_pokrycie(obecne_pokrycie);
                return;
            }
            else {
                obecne_pokrycie = nastepne_pokrycie(obecne_pokrycie);
            }
        }
        //Przypadek gdy nie ma dobrego pokrycia
        System.out.print("0\n");

    }

    //metoda zwraca nastepne pokrycie w porzadku leksykograficznym
    private ArrayList<Integer> nastepne_pokrycie(ArrayList<Integer> obecne_pokrycie) {
        //Sprawdzamy czy obecne pokrycie nie jest ostatnim
        if(obecne_pokrycie.size() >= zbiory.size()) {
            obecne_pokrycie.set(0, -1);
            return obecne_pokrycie;
        }

        int n = obecne_pokrycie.size() - 1;//indeks ostatniej pozucji w obecnej liscie pokryc
        int last = obecne_pokrycie.get(n);
        //Gdy ostatni poprzedniego pokrycia element nie jest ostatnim elementem
        if(last < zbiory.size() - 1) {
            obecne_pokrycie.set(n, last + 1);
            return obecne_pokrycie;
        }

        int i = zbiory.size() - 1;//liczba na ostatniej pozycji
        int k = 0;
        //Dochodzimy do pierwszego indeksu, na ktorym sa niekolejne elementy
        while(obecne_pokrycie.get(n) == i) {
            i--;
            n--;
            k++;
            if(n < 0){
                break;
            }
        }
        if(n >= 0) {//istnieje element, ktory mozemy zwiekszyc
            int obecny = obecne_pokrycie.get(n);
            obecne_pokrycie.set(n, obecny + 1);
            for(int j = 1; j <= k; j++) {
                obecne_pokrycie.set(n + j, obecny + j + 1);
            }
        }
        //ustawiamy pierwsze pokrycie wiekszej dlugosci
        else{
            int max = obecne_pokrycie.size();
            obecne_pokrycie = new ArrayList<Integer>();
            for(int nr = 0; nr < max + 1; nr++){
                obecne_pokrycie.add(nr);
            }
        }
        return obecne_pokrycie;
    }

    //metoda do sprawdzania kolejnych kombinacji zbiorow do dokladnego algorytmu
    private boolean czy_zbiory_pokryja(boolean[] pokryte_elementy,
                                       ArrayList<Integer> obecne_pokrycie){
        pokryte_elementy = new boolean[pokryte_elementy.length];
        for(int i : obecne_pokrycie){
            //oznaczamy indeksy elementow ze zbioru do pokrycia, ktore sa w zbiorze
            zbiory.get(i).oznacz_zawarte_elementy(pokryte_elementy);
        }
        return czy_wszystkie_pokryte(pokryte_elementy);
    }


    private void zachlanny(boolean[] pokryte_elementy) {
        int index_pierwszego;
        int najw_wspolnych = -1;
        int ile_pokrywa_obecny;
        int obecny_index;
        ArrayList<Integer> pokrycie = new ArrayList<Integer>();
        boolean[] pokryte_przez_aktualny = new boolean[pokryte_elementy.length];

        while (najw_wspolnych != 0) {
            najw_wspolnych = 0;
            obecny_index = 0;
            index_pierwszego = -1;

            for (Zbior obecny : this.zbiory) {
                //sprawdzamy tylko zbiory, ktorych jeszcze nie dodalismy
                if (!pokrycie.contains(obecny_index)) {
                    System.arraycopy(pokryte_elementy, 0, pokryte_przez_aktualny, 0, pokryte_elementy.length);
                    ile_pokrywa_obecny = obecny.ile_zawiera_elementow(pokryte_przez_aktualny);
                    if (ile_pokrywa_obecny > najw_wspolnych) {
                        najw_wspolnych = ile_pokrywa_obecny;
                        index_pierwszego = obecny_index;
                        //System.out.print(index_pierwszego+" najw pokrywa \n");
                    }
                }
                obecny_index++;
            }
            if (index_pierwszego >= 0 && najw_wspolnych > 0) {
                pokrycie.add(index_pierwszego);
                zbiory.get(index_pierwszego).oznacz_zawarte_elementy(pokryte_elementy);
            }
            else {
                najw_wspolnych = 0;
            }
        }

        boolean czy_pokryty = czy_wszystkie_pokryte(pokryte_elementy);
        //jezeli wszystkie elementy do pokrycia byly zaznaczone sortujemy i wypisujemy pokrycie
        if(czy_pokryty){
            Collections.sort(pokrycie);
            wypisz_pokrycie(pokrycie);
        }
        else{
            //Przypadek gdy nie ma dobrego pokrycia
            System.out.print("0\n");
        }
    }

    private void naiwny(boolean[] pokryte_elementy) {
        ArrayList<Integer> pokrycie = new ArrayList<Integer>();
        Integer nr_zbioru = 0;
        for(Zbior obecny : this.zbiory){
            if(obecny.czy_zawiera_nowe_elementy(pokryte_elementy)){
                pokrycie.add(nr_zbioru);
            }
            nr_zbioru++;
        }
        boolean czy_pokryty = czy_wszystkie_pokryte(pokryte_elementy);
        if(czy_pokryty){
            wypisz_pokrycie(pokrycie);
        }
        else{
            //Przypadek gdy nie ma dobrego pokrycia
            System.out.print("0\n");
        }
    }

    private boolean czy_wszystkie_pokryte(boolean[] pokryte_elementy) {
        for (boolean i : pokryte_elementy) {
            if (!i) {
                return false;
            }
        }
        return true;
    }

    private void wypisz_pokrycie(ArrayList<Integer> pokrycie){
	int i = 0;        
	for(Integer nr_zbioru : pokrycie){
            System.out.print((nr_zbioru+1));
            i++;
            if (i < pokrycie.size()) {
                System.out.print(" ");
                }
        }
        System.out.print("\n");
    }
}
