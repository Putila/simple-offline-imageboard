package oope2018ht.viestit;
import oope2018ht.apulaiset.Getteri;
import oope2018ht.apulaiset.Setteri;
import oope2018ht.omalista.OmaLista;
import oope2018ht.tiedostot.Tiedosto;

/**
 * Alue-luokka joka sisältää suurimman osan ketjujen ja viestien
 * toiminnalisuudesta
 * <p>
 * OOPE harjoitustyö kevät 2018
 * <p>
 * @author Roope Putila
 * 422600
 * putila.roope.o@student.uta.fi
 */
public class Alue {

   //sisennys-vakio
   protected static final String sisennys = "   ";

   //attribuutit
   private OmaLista alueenKetjut;
   private Ketju valittu;
   private int tunniste = 1;
   private int viestinTunniste = 1;
   private OmaLista kaikkiViestit;


   @Getteri
   public OmaLista hankiKaikkiViestit(){
      return kaikkiViestit;
   }
   @Getteri
   public int hankiViestinTunniste(){
      return viestinTunniste;
   }
   @Getteri
   public Ketju hankiValittu(){
      return valittu;
   }
   @Getteri
   public OmaLista hankiAlueenKetjut() {
      return alueenKetjut;
   }
   @Setteri
   public void asetaViestinTunniste(int i){
      viestinTunniste = i;
   }
   @Setteri
   public void asetaValittu(Ketju k){
      valittu = k;
   }
   @Setteri
   public void asetaKaikkiViestit(OmaLista o){
      if(o!=null){
         kaikkiViestit = o;
      }
      else System.out.println("Error!");
   }

   //rakentaja
   public Alue(){
      alueenKetjut = new OmaLista();
      valittu = null;
      kaikkiViestit = new OmaLista();
   }

   /**
    * Lisää uuden ketjun alueelle
    *
    * @param s Ketjun aihe Stringinä
    */
   public void lisaaKetju(String s){
      Ketju uusi = new Ketju(tunniste, s);

      //jos ketju on ensimmäinen, asetetaan se valituksi
      if(tunniste == 1){
         asetaValittu(uusi);
      }

      //plussataan ketjujen juoksevaa tunnistelukua
      tunniste++;

      //lisätään ketju alueen ketjulistalle
      alueenKetjut.lisaaLoppuun(uusi);
   }

   /** Asettaa halutun ketjun aktiiviseksi
    *
    * @param i halutun ketjun tunniste int-tyyppisenä
    * @return palauttaa true jos aktivointi onnistui
    */
   public boolean valitaanKetju(int i){

      //tarkistaa onko haluttua ketjua olemassa
      if(alueenKetjut.alkio(i-1)==null){
         return false;
      }
      else{
         asetaValittu((Ketju)alueenKetjut.alkio(i-1));
         return true;
      }
   }

   /**Lisää viestin ketjuun
    *
    * @param s Viestin sisältö String-muodossa
    * @param t Viestiin liitetty tiedosto, voi olla null
    */
   public void lisaaViesti(String s, Tiedosto t){

      //luodaan uusi viesti
      Viesti uusi = new Viesti(viestinTunniste, s, null, t);
      //plussataan viestien juoksevaa tunnistetta
      viestinTunniste++;

      //lisätään viesti ketjun oksa-ja viestilistalle, sekä alueen listalle
      //jossa on kaikki alueen viestit
      hankiValittu().hankiViestiLista().lisaaLoppuun(uusi);
      hankiValittu().hankiOksaLista().lisaaLoppuun(uusi);
      kaikkiViestit.lisaaLoppuun(uusi);
   }

   /**
    * Lisää viestin joka on vastaus aiempaan viestiin
    *
    * @param i Viestin tunniste johon vastataan int-tyyppisenä
    * @param s Viestin sisältö String-tyyppisenä
    * @param t Viestin tiedosto, voi olla null
    */
   public void lisaaVastaus(int i, String s, Tiedosto t){

      //luodaan uusi viesti
      Viesti vastaus = new Viesti(viestinTunniste, s, (Viesti)kaikkiViestit.alkio(i-1), t);

      //lisätään viesti alueen ja ketjun viestilistalle
      hankiValittu().hankiViestiLista().lisaaLoppuun(vastaus);
      kaikkiViestit.lisaaLoppuun(vastaus);

      //Lisätään vastausviesti vastattavan viestin vastauslistalle
      ((Viesti) kaikkiViestit.alkio(i-1)).hankiVastaukset().lisaaLoppuun(vastaus);

      //plussataan viestien juoksevaa tunnistetta
      viestinTunniste++;
      }

   /**
    * Hakee viestiä joltain listalta
    *
    * @param haettavaTunniste tunniste jota haetaan
    * @param lista OmaLista josta haetaan
    * @return true jos viesti jonka tunnistetta etsitään löytyy listalta
    */
   public boolean haeViestiListasta(int haettavaTunniste, OmaLista lista){

      //käydään lista läpi alkio kerrallaan
      for(int i = 0; i < lista.koko();i++){

         //luodaan apumuuttuja ja vertaillaan sen tunnistetta haettavaan
         Viesti temp =(Viesti) lista.alkio(i);
         int tempTunniste = temp.hankiTunniste();
         if(tempTunniste == haettavaTunniste){
            return true;
         }
      }
      return false;
   }

   /** Tulostaa ketjun ja kutsuu toista metodia
    *
    */
   public void tulostaPuuna(){
      int i = 0;

      //tulostetaan ketju
      System.out.println("=");
      System.out.print("== ");
      System.out.println(hankiValittu());
      System.out.println("===");


      while(i < hankiValittu().hankiOksaLista().koko()){
         //kutsutaan metodia jokaisen oksaviestin kohdalla ja viesti sen parametriksi
         tulostaPuuna((Viesti)hankiValittu().hankiOksaLista().alkio(i),0);
         i++;
      }
   }

   /** Tulostaa tarvittavan määrän sisennyksiä ja viestin
    *  ja kutsuu itseään jos oksaviestillä on vastauksia
    * @param tulostettava viesti joka halutaan tulostaa
    * @param i tämänhetkinen sisennyksen syvyys
    */
   public void tulostaPuuna(Viesti tulostettava, int i){
      //tarkistetaan vielä onko viestiä olemassa
      if(tulostettava != null) {
         //tulostetaan oikeansyvyinen sisennys
         for(int ind = 0; ind < i;ind++){
            System.out.print(sisennys);
         }
         System.out.println(tulostettava);

         //jos viestillä on vastauksia
         if (!tulostettava.hankiVastaukset().onkoTyhja()) {

            //plussataan syvyyslaskuria
            i++;
            int j = 0;

            //kutsutaan metodia jokaisen viestin vastauksen kohdalla
            while (j < tulostettava.hankiVastaukset().koko()) {
               tulostaPuuna((Viesti) tulostettava.hankiVastaukset().alkio(j), i);
               j++;
            }
         }
      }
   }


}
