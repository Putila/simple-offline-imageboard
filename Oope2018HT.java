import oope2018ht.Kayttoliittyma;

/**
 * Harjoitustyön ajoluokka
 * <p>
 * OOPE harjoitustyö kevät 2018
 * <p>
 * @author Roope Putila
 * 422600
 * putila.roope.o@student.uta.fi
 */

public class Oope2018HT{
   public static void main(String[] args){
      //luodaan uusi käyttöliittymä ja kutsutaan sen käynnistä-metodia
      Kayttoliittyma kayttis = new Kayttoliittyma();
      kayttis.kaynnista();
   }
}