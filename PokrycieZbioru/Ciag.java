public abstract class Ciag {

    public Ciag() {}

    protected abstract boolean czy_zawiera(int element);

    public boolean czy_zawiera_nowy_element(boolean[] ktore_elementy) {
        boolean czy_zawiera = false;
        for (int i = 0; i < ktore_elementy.length; i++) {
            if (!ktore_elementy[i]) {
                if(czy_zawiera(i+1)) {
                    ktore_elementy[i] = true;
                    czy_zawiera = true;
                }
            }
        }
        return czy_zawiera;
    }

    public void oznacz_zawarte_elementy(boolean[] ktore_elementy) {
        for (int i = 0; i < ktore_elementy.length; i++) {
            if (!ktore_elementy[i]) {
                if(czy_zawiera(i+1)) {
                    ktore_elementy[i] = true;
                }
            }
        }
    }

}