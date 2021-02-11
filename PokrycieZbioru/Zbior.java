import java.util.ArrayList;

public class Zbior {
    private ArrayList<Ciag> składniki;

    public Zbior(){
        this.składniki = new ArrayList<Ciag>();
    }

    public void dodajCiag(Ciag C){
        składniki.add(C);
    }

    public void oznacz_zawarte_elementy(boolean[] ktore_elementy) {
        for(Ciag obecny : this.składniki){
            obecny.oznacz_zawarte_elementy(ktore_elementy);
        }
    }

    public boolean czy_zawiera_nowe_elementy(boolean[] ktore_elementy) {
        boolean czy_dodajemy = false;
        for(Ciag obecny : this.składniki){
            if(obecny.czy_zawiera_nowy_element(ktore_elementy)){
                czy_dodajemy = true;
            }
        }
        return czy_dodajemy;
    }


    public int ile_zawiera_elementow(boolean[] pokryte_przez_aktualny) {
        if(czy_zawiera_nowe_elementy(pokryte_przez_aktualny)){
            return zlicz_zawarte(pokryte_przez_aktualny);
        }
        else {
            return 0;
        }
    }

    private int zlicz_zawarte(boolean[] ktore_elementy){
        int suma = 0;
        for(int i =0; i < ktore_elementy.length; i++){
            if(ktore_elementy[i]) {
                suma++;
            }
        }
        return suma;
    }
}
