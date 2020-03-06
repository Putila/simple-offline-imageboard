package oope2018ht.viestit;
import oope2018ht.apulaiset.*;
import oope2018ht.omalista.OmaLista;
import oope2018ht.tiedostot.Tiedosto;

/**Viesti-luokka toteuttaa komennettava ja comparable
 * rajapintojen metodit
 * <p>
 * OOPE harjoitustyö kevät 2018
 * <p>
 * @author Roope Putila
 * 422600
 * putila.roope.o@student.uta.fi
 */

public class Viesti implements Komennettava<Viesti>, Comparable<Viesti>{

    //atribuutit
    private int tunniste;
    private String merkkiJono;
    private Viesti aiempiViesti;
    private Tiedosto tied;
    private OmaLista vastaukset;


    //vakiot
    protected final String POISTETTUTEKSTI = "----------";
    protected final char EROTIN = ' ';

   //akksessorit

    @Getteri
    public int hankiTunniste(){
        return tunniste;
    }
    @Getteri
    public String hankiMerkkiJono(){
        return merkkiJono;
    }
    @Getteri
    public Viesti hankiAiempiViesti(){
        return aiempiViesti;
    }
    @Getteri
    public Tiedosto hankiTied(){
        return tied;
    }
    @Getteri
    public OmaLista hankiVastaukset(){
        return vastaukset;
    }
    @Setteri
    public void asetaTunniste(int t) throws IllegalArgumentException{
        if(t > 0){
            tunniste = t;
        }
        else throw new IllegalArgumentException("Error!");
    }
    @Setteri
    public void asetaMerkkiJono(String m) throws IllegalArgumentException{
        if(m.length() > 0){
            merkkiJono = m;
        }
        else throw new IllegalArgumentException("Error!");
    }
    @Setteri
    public void asetaAiempiViesti(Viesti v){
       aiempiViesti = v;
    }
    @Setteri
    public void asetaTied(Tiedosto t){
       tied = t;
    }
    @Setteri
    public void asetaVastaukset(OmaLista l) throws IllegalArgumentException{
       if(l != null){
          vastaukset = l;
       }
       else throw new IllegalArgumentException("Error!");
    }


    //rakentaja
    public Viesti (int t, String s, Viesti a, Tiedosto f){
       try{
          asetaTunniste(t);
          asetaMerkkiJono(s);
          asetaAiempiViesti(a);
          asetaTied(f);
          vastaukset = new OmaLista();
       }
       catch (Exception e) {
          throw new IllegalArgumentException("Error!");
       }
    }

   /** Vertailee kahta viestiä keskenään
    *
    * @param v viesti jota vertaillaan
    * @return palauttaa -1 jos vertailtava on isompi,
    * 0 jos saman kokoiset, 1 jos pienempi
    */
   @Override
   public int compareTo(Viesti v) {
      if(tunniste < v.hankiTunniste()){
         return -1;
      }
      else if (tunniste == v.hankiTunniste()){
         return 0;
      }
      else return 1;
   }

   /** Vertailee että onko objectit samat
    *
    * @param obj vertailtava
    * @return palauttaa true jos vertailtavat ovat samat
    */
   @Override
   public boolean equals(Object obj) {
      try {
         Viesti v = (Viesti) obj;
         return (tunniste == v.hankiTunniste());
      }
      catch (Exception e){
         return false;
      }
   }
   @Override
   public String toString(){
      String paluuarvo = "#" + tunniste + " " + merkkiJono;
      if(tied != null){
         paluuarvo += " (" + tied.toString() + ")";
      }
      return paluuarvo;
   }

   /** Hakee viesti� listalta, joka s�il�� viitteet viestiin vastaaviin viesteihin.
    * Hy�dynt�� OmaLista-luokan hae-operaatiota.
    *
    * @param haettava viite haettavaan viestiin.
    * @return viite l�ydettyyn tietoon. Palauttaa null-arvon, jos haettavaa
    * viesti� ei l�ydet�.
    * @throws IllegalArgumentException jos parametri on null-arvoinen.
    */
   @Override
   public Viesti hae(Viesti haettava) throws IllegalArgumentException{
      if(haettava != null){
         //pakoitetaan Omalista luokan hae-metodin paluuarvo viestiksi
         Viesti viite = (Viesti)vastaukset.hae(haettava);
         return viite;
      }
      else throw new IllegalArgumentException("Error!");
   }

   /** Lis�� uuden viitteen listalle, joka s�il�� viitteet viestiin vastaaviin
    * viesteihin. Uusi viite lis�t��n listan loppuun. Viitett� ei lis�t�,
    * jos listalla on jo viite lis�tt�v��n vastaukseen. Hy�dynt�� hae-metodia.
    *
    * @param lisattava viite lis�tt�v��n viestiin.
    * @throws IllegalArgumentException jos lis�ys ep�onnistui, koska parametri
    * on null-arvoinen tai koska vastaus on jo lis�tty listalle.
    */
   @Override
   public void lisaaVastaus(Viesti lisattava) throws IllegalArgumentException {
      if(lisattava != null){
         if(!lisattava.equals(vastaukset.hae(lisattava))){
            vastaukset.lisaa(lisattava);
         }
         else throw new IllegalArgumentException("Error!");
      }
      else throw new IllegalArgumentException("Error!");
   }

   /** Asettaa viestin tekstiksi vakion POISTETTUTEKSTI ja poistaa viestiin
    * mahdollisesti liitetyn tiedoston asettamalla attribuutin arvoksi null-arvon.
    */
   @Override
   public void tyhjenna(){
       merkkiJono = POISTETTUTEKSTI;
       tied = null;
   }
}