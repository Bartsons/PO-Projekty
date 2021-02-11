package wybory;

//Klasa reprezentująca działanie, zawiera koszt działania oraz zmianę wag, którą powoduje
public class Działanie {

    final int[] zmianaWag;
    final int koszt;

    public Działanie(int[] wektorWag) {
        this.zmianaWag = wektorWag;
        this.koszt = wyliczKoszt();
    }

    private int wyliczKoszt() {
        int suma = 0;
        for (int zmiana : zmianaWag) {
            suma += Math.abs(zmiana);
        }
        return suma;
    }

    public void działajnaWyborcę(Wyborca w) {
        for (int i = 0; i < zmianaWag.length; i++) {
            w.zmieńCechę(i, zmianaWag[i]);
        }
    }

    public void cofnijdziałanie(Wszechstronny w) {
        for (int i = 0; i < zmianaWag.length; i++) {
            w.zmieńCechę(i, -zmianaWag[i]);
        }
    }
}
