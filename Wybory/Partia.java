package wybory;

public class Partia {

    final String nazwa;
    final protected int numerListy;
    private int budżet;
    private int początkowyBudżet;
    private Strategia strategia;
    private int zebraneGłosy;
    private int zebraneMandaty;

    public Partia(String nazwa, int numerListy) {
        this.nazwa = nazwa;
        this.numerListy = numerListy;
        this.zebraneGłosy = 0;
        this.zebraneMandaty = 0;
    }

    public void ustawBudżet(int budżet) {
        this.budżet = budżet;
        this.początkowyBudżet = budżet;
    }

    public void ustawStrategię(Strategia strategia) {
        this.strategia = strategia;
    }

    public void oddajGłos() {
        this.zebraneGłosy++;
    }

    //Metoda używana przy powtarzaniu symulacji
    public void resetujSymulacjęPartii() {
        zebraneGłosy = 0;
        zebraneMandaty = 0;
        budżet = początkowyBudżet;
    }

    //Metoda zgłaszająca czy partię stać na dalsze prowadzenie kampani
    public boolean czyprowadziKampanię() {
        return strategia.czyStarczaBudżetu(budżet);
    }

    //Metoda odpowiadająca za podjęcie działań wyborczych
    public void podejmijDziałaniaWyborcze() {
        budżet -= strategia.zakupDziałanie(budżet);;

    }

    public int ileMandatów() {
        return zebraneMandaty;
    }

    public void oddajMandat() {
        zebraneMandaty++;
    }

    public int ileGłosów() {
        return zebraneGłosy;
    }
}
