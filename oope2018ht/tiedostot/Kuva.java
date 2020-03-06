package oope2018ht.tiedostot;

import oope2018ht.apulaiset.Getteri;
import oope2018ht.apulaiset.Setteri;

/**
 * Kuva-luokka joka sisältää kuva-tiedoston attribuutit
 * <p>
 * OOPE harjoitustyö kevät 2018
 * <p>
 * @author Roope Putila
 * 422600
 * putila.roope.o@student.uta.fi
 */
public class Kuva extends Tiedosto{

   //attribuutit
   private int kLeveys;
   private int kKorkeus;
   protected final char KERTAA = 'x';

   //rakentaja
   public Kuva(String kNimi, int kKoko, int kLeveys, int kKorkeus) throws IllegalArgumentException{
      super(kNimi, kKoko);
      asetaKLeveys(kLeveys);
      asetaKKorkeus(kKorkeus);

   }
   //oletusrakentaja
   public Kuva(){
      asetaNimi("haha");
      asetaKoko(10);
      asetaKLeveys(100);
      asetaKKorkeus(100);
   }

   //aksessorit
   @Getteri
   public int hankiKLeveys(){
      return kLeveys;
   }
   @Getteri
   public int hankiKKorkeus(){
      return kKorkeus;
   }
   @Setteri
   public void asetaKLeveys(int l) throws IllegalArgumentException{
      if(l >= 1){
         kLeveys = l;
      }
      else throw new IllegalArgumentException("Error!");
   }
   @Setteri
   public void asetaKKorkeus(int k) throws IllegalArgumentException{
      if(k >= 1){
         kKorkeus = k;
      }
      else throw new IllegalArgumentException("Error!");
   }
   @Override
   public String toString(){
      return super.toString() + EROTIN + hankiKLeveys() + KERTAA + hankiKKorkeus();
   }
}
