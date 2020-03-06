package oope2018ht.tiedostot;

import oope2018ht.apulaiset.Getteri;
import oope2018ht.apulaiset.Setteri;

/**
 * Video-luokka joka sisältää video-tiedoston piirteet
 * <p>
 * OOPE harjoitustyö kevät 2018
 * <p>
 * @author Roope Putila
 * 422600
 * putila.roope.o@student.uta.fi
 */
public class Video extends Tiedosto{
   //attribuutit
   private double vPituus;
   protected final char SEKUNTTI = 's';

   //rakentaja
   public Video(String vNimi, int vKoko, double vPituus) throws IllegalArgumentException{
      super(vNimi, vKoko);
      asetaVPituus(vPituus);
   }
   
   //akksessorit
   @Getteri
   public double hankiVPituus() {
      return vPituus;
   }
   @Setteri
   public void asetaVPituus(double p) throws IllegalArgumentException{
      if(p > 0){
         vPituus = p;
      }
      else throw new IllegalArgumentException("Error!");
   }
   @Override
   public String toString(){
      return super.toString() + EROTIN + hankiVPituus() + EROTIN + SEKUNTTI;
   }
}
