package oope2018ht.tiedostot;
import oope2018ht.apulaiset.*;

/**
 * Abstrakti Tiedosto-luokka joka sisältää tiedostojen pääpiirteet
 * <p>
 * OOPE harjoitustyö kevät 2018
 * <p>
 * @author Roope Putila
 * 422600
 * putila.roope.o@student.uta.fi
 */
public abstract class Tiedosto {

    //atribuutit
    private String nimi;
    private int koko;
    protected final char BITTI = 'B';
    protected final char EROTIN = ' ';

    //rakentaja
    public Tiedosto(String tNimi, int tKoko) throws IllegalArgumentException{
       asetaNimi(tNimi);
       asetaKoko(tKoko);
    }
    //oletusrakentaja
   public Tiedosto(){
       nimi = "haha";
       koko = 10;
   }

    //aksessorit
    @Getteri
    public String hankiNimi() {
        return nimi;
    }

    @Getteri
    public int hankiKoko() {
        return koko;
    }
   @Setteri
   public void asetaNimi(String n) throws IllegalArgumentException{
      if (n != null) {
         if(n.length() >= 1){
            nimi = n;
         }
         else throw new IllegalArgumentException("Error!");
      }
      else throw new IllegalArgumentException("Error!");
   }
    @Setteri
    public void asetaKoko(int k) throws IllegalArgumentException{
        if (k >= 1) {
            koko = k;
        }
        else throw new IllegalArgumentException("Error!");
    }
    @Override
    public String toString(){
       return hankiNimi() + EROTIN + hankiKoko() + EROTIN + BITTI;
    }

}
