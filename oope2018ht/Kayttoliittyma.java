package oope2018ht;
import oope2018ht.apulaiset.In;
import oope2018ht.omalista.OmaLista;
import oope2018ht.tiedostot.Kuva;
import oope2018ht.tiedostot.Tiedosto;
import oope2018ht.tiedostot.Video;
import oope2018ht.viestit.Alue;
import oope2018ht.viestit.Viesti;
import java.io.*;

/**
 * Käyttöliittymä-luokka jossa tapahtuu vuorovaikutus käyttäjän
 * ja ohjelman kanssa.
 * <p>
 * OOPE harjoitustyö kevät 2018
 * <p>
 * @author Roope Putila
 * 422600
 * putila.roope.o@student.uta.fi
 */

public class Kayttoliittyma{

   //Esitellään vakiot
   private static final String LOPETA = "exit";
   private static final String LISAA = "add";
   private static final String VASTAA = "reply";
   private static final String KATALOGI = "catalog";
   private static final String VALITSE = "select";
   private static final String UUSI = "new";
   private static final String PUU = "tree";
   private static final String LISTAA = "list";
   private static final String PAA = "head";
   private static final String HANTA = "tail";
   private static final String TYHJENNA = "empty";
   private static final String ETSI = "find";

   //luodaan keskustelualue
   Alue alue = new Alue();


   /**
    * Ohjelman pää-silmukka
    */
   public void kaynnista(){

      boolean jatkuu = false;

      System.out.println("Welcome to S.O.B.");
      while(!jatkuu){
         System.out.print(">");

         //luetaan komento In-luokalla
         String komento = In.readString();

         //jaetaan komento osiin string.splitillä
         String[] komennot = komento.split(" ");


         if(komennot[0].equals(LISAA)){
            lisaa(komento, komennot);
         }
         if(komento.equals(KATALOGI)){
            katalogi();
         }
         if(komennot[0].equals(VALITSE)){
            valitse(komennot);
         }
         if(komennot[0].equals(UUSI)) {
            uusi(komento, komennot);
         }
         if(komennot[0].equals(VASTAA)){
            vastaa(komento, komennot);
         }
         if(komennot[0].equals(PUU)) {
            puu(komennot);
         }
         if(komento.equals(LISTAA)){
            listaa();
         }
         if(komennot[0].equals(PAA)){
            paa(komennot);
         }
         if(komennot[0].equals(HANTA)){
            hanta(komennot);
         }
         if(komennot[0].equals(TYHJENNA)){
            tyhjenna(komennot);
         }
         if(komennot[0].equals(ETSI)){
            etsi(komento, komennot);
         }
         if(komento.equals(LOPETA)){
            jatkuu = true;
            System.out.println("Bye! See you soon.");
         }
      }
   }

   /**Lisää uuden ketjun alueelle
    *
    * @param komento Koko käyttäjän antama komento String-tyyppisenä
    * @param komennot Käyttäjän antama komento String-tyyppisessä taulukossa
    */
   public void lisaa(String komento, String[] komennot){

      //tarkastetaan komennon pituus
      if(komennot.length != 1){
         //eritellään komennosta pois "add" ja lisätään ketju alueella
         String otsikko = komento.substring(4);
         alue.lisaaKetju(otsikko);
      }
      else System.out.println("Error!");
   }

   /**Listaa alueen ketjut
    *
    */
   public void katalogi(){
      if(alue.hankiAlueenKetjut().onkoTyhja()){
      }
      else{

         //kutsutaan tulosta-metodia
         tulosta(alue.hankiAlueenKetjut());
      }
   }

   /** Valitsee jonkin alueen ketjuista aktiiviseksi
    *
    * @param komennot Käyttäjän antama komento String-tyyppisessä taulukossa
    */
   public void valitse(String[] komennot){
      try {
         if (!alue.valitaanKetju(Integer.parseInt(komennot[1])) || (komennot.length>2)) {
            System.out.println("Error!");
         }
      } catch (Exception e){
         System.out.println("Error!");
      }
   }

   /** Lisää aktiiviselle alueelle uuden viestin
    *
    * @param komento Koko käyttäjän antama komento String-tyyppisenä
    * @param komennot Käyttäjän antama komento String-tyyppisessä taulukossa
    */
   public void uusi(String komento, String[] komennot){
      if((alue.hankiValittu() == null) || (komennot.length == 1)){
         System.out.println("Error!");
      }
      else{
         //jaetaan komento kahteen osaan jos viesti sisältää tiedoston
         String eriteltyKomento[] = komento.split(" &");

         //luodaan tiedosto
         Tiedosto tied = null;

         //eritellään komennon ensimmäisestä osasta pois "new"
         String viestinSisalto = eriteltyKomento[0].substring(4);

         //viesti jossa ei ole tiedostoa
         if(eriteltyKomento.length == 1){
            alue.lisaaViesti(viestinSisalto, tied);
         }
         else{
            //yritetään luoda tiedostoa ja sen jälkeen viestiä
            try {
               tied = kasitteleTiedosto(eriteltyKomento[1]);
               alue.lisaaViesti(viestinSisalto, tied);
            } catch (IOException e) {
               System.out.println("Error!");
            }
         }
      }
   }

   /** Vastaa johonkin aiempaan viestiin aktiivisessa ketjussa
    *
    * @param komennot Käyttäjän antama komento String-tyyppisessä taulukossa
    * @param komento Koko käyttäjän antama komento String-tyyppisenä
    */
   public void vastaa(String komento, String[] komennot) {
      try {


         //otetaan komento-taulukon kohdasta 1 vastattavan viestin tunniste
         int vastattava = Integer.parseInt(komennot[1]);
         //kiinnitetään ketjun listan koko muuttujaan, jotta koodista tulee selvempää
         int vastaustenMaara = alue.hankiValittu().hankiViestiLista().koko();

         //tarkistetaanko onko valittua ketjua, onko ketjussa viestejä
         if ((alue.hankiValittu() == null) || (vastaustenMaara == 0)) {
            System.out.println("Error!");
         } else if (!alue.haeViestiListasta(vastattava, alue.hankiValittu().hankiViestiLista())) {
            System.out.println("Error!");
         } else {

            //jaetaan komento kahteen osaan jos vastauksee lisätään tiedosto
            String eriteltyKomento[] = komento.split(" &");

            //luodaan tiedosto
            Tiedosto tied = null;

            //eritellään komennon ensimmäisestä osasta "reply" ja viestin tunnus pois
            String viestinSisalto = "";

            //leikataan viesti kahtia tunnisteen kohdalta
            String enemmanEroteltu[] = eriteltyKomento[0].split(Integer.toString(vastattava )+ " ");
            viestinSisalto = enemmanEroteltu[1];

            //viesti jossa ei ole tiedostoa
            if (eriteltyKomento.length == 1) {
               alue.lisaaVastaus(vastattava, viestinSisalto, tied);

            } else {

               //yritetään luoda tiedostoa ja sen jälkeen viestiä
               try {
                  tied = kasitteleTiedosto(eriteltyKomento[1]);
                  alue.lisaaVastaus(vastattava, viestinSisalto, tied);
               } catch (IOException e) {
                  System.out.println("Error!");
               }
            }
         }
      }catch (Exception e){
         System.out.println("Error!");
      }
   }

   /** Tulostaa aktiivisen ketjun ja sen viestit puu-muodossa
    *
    * @param komennot Käyttäjän antama komento String-tyyppisessä taulukossa
    */
   public void puu(String[] komennot){

      //tarkistetaan onko aktiivista ketjua
      if (alue.hankiValittu() == null) {
         System.out.println("Error!");
      } else {
         //kutsutaan rekursiivista tulostusmetodia
         alue.tulostaPuuna();
      }
   }

   /** Tulostaa aktiivisen ketjun ja sen viestit listana
    *
    *
    */
   public void listaa(){
      if(alue.hankiValittu() == null){
         System.out.println("Error!");
      }
      else{
         System.out.println("=");
         System.out.print("== ");
         System.out.println(alue.hankiValittu());
         System.out.println("===");
         tulosta(alue.hankiValittu().hankiViestiLista());
      }
   }


   /**Tulostaa parametrina saadun määrän viestejä alkaen ketjun
    * vanhimmasta viestistä
    *
    * @param komennot Käyttäjän antama komento String-tyyppisessä taulukossa
    */
   public void paa(String[] komennot){

      //tarkistetaan onko ketjua olemassa ollenkaan
      if((alue.hankiValittu() != null)&&(komennot.length==2)) {

         try{

            //sijoitetaan nämä luvut muuttujiin jotta koodista tulee selvempäää
            int tulostettavaMaara = Integer.parseInt(komennot[1]);
            int viestienMaara = alue.hankiValittu().hankiViestiLista().koko();

            //tarkistetaan onko tulostettavien määrä oikea, jos on, tulostetaan
            if ((tulostettavaMaara <= viestienMaara) && (tulostettavaMaara > 0)) {
               for (int i = 0; i < tulostettavaMaara; i++) {
                  System.out.println(alue.hankiValittu().hankiViestiLista().alkio(i));
               }
            }else System.out.println("Error!");
         }catch (Exception e){
            System.out.println("Error!");
         }
      }
      else System.out.println("Error!");
   }

   /**Tulostaa parametrina saadun määrän viestejä alkaen ketjun
    * uusimmasta viestistä
    *
    * @param komennot Käyttäjän antama komento String-tyyppisessä taulukossa
    */
   public void hanta(String[] komennot){

      //tarkistetaan onko ketjua olemassa ollenkaan
      if((alue.hankiValittu() != null)&&(komennot.length==2)) {

         try {


          //sijoitetaan nämä luvut muuttujiin jotta koodista tulee selvempäää
          int tulostettavaMaara = Integer.parseInt(komennot[1]);
          int viestienMaara = alue.hankiValittu().hankiViestiLista().koko();

            //tarkistetaan onko tulostettavien määrä oikea, jos on, tulostetaan
            if ((tulostettavaMaara <= viestienMaara) && (tulostettavaMaara > 0)) {
               for (int i = viestienMaara - tulostettavaMaara; i < viestienMaara; i++) {

                  System.out.println(alue.hankiValittu().hankiViestiLista().alkio(i));
             }
            }else System.out.println("Error!");
         }catch(Exception e){
            System.out.println("Error!");
         }
      }
      else System.out.println("Error!");
   }

   /** Tyhjentää käyttäjän antaman viestin jos se löytyy kyseisestä ketjusta
    *
    * @param komennot Käyttäjän antama komento String-tyyppisessä taulukossa
    */
   public void tyhjenna(String[] komennot){

      //tarkistetaan onko aktiivista aluetta ja onko komento oikean pituinen
      if((alue.hankiValittu() != null)&&(komennot.length == 2)){

         //yritetään ottaa komennosta tunnistetta erilleen
         try {
            int poistettavanViestinTunniste = Integer.parseInt(komennot[1]);

            //jos poistettavan viestin tunniste löytyy aktiviisesta ketjusta, poistetaan viesti
            if (alue.haeViestiListasta(poistettavanViestinTunniste, alue.hankiValittu().hankiViestiLista())) {

               //haetaan viesti listasta kohdasta tunniste-1
               Viesti poistettava = (Viesti)alue.hankiKaikkiViestit().alkio(poistettavanViestinTunniste-1);

               //jos viesti löytyy, poistetaan se
               if(poistettava != null){
                  poistettava.tyhjenna();
               }
               else System.out.println("Error!");
            }
            else System.out.println("Error!");
         }
         catch (Exception e){
            System.out.println("Error!");
         }
      }
      else System.out.println("Error!");
   }

   /** Etsii ja tulostaa viestin joka sisältää merkkijonon mitä haetaan
    *
    * @param komento Koko käyttäjän antama komento String-tyyppisenä
    * @param komennot Käyttäjän antama komento String-tyyppisessä taulukossa
    */
   public void etsi(String komento, String[] komennot){
      if((komennot.length != 1)&&(alue.hankiValittu() != null)){

         String etsittava = komento.substring(5);
         OmaLista etsittavatViestit = alue.hankiValittu().hankiViestiLista();
         for(int i = 0; i < etsittavatViestit.koko(); i++){
            Viesti temp = (Viesti)etsittavatViestit.alkio(i);
            String tied = "";
            try{
               tied = temp.hankiTied().toString();
            }catch (Exception e){

            }
            if(temp!=null){
               if ((temp.hankiMerkkiJono().contains(etsittava))||(tied.contains(etsittava))){
                  System.out.println(temp);
               }
            }
         }
      }
      else System.out.println("Error!");
   }

   /** Tulostaa OmaListan alkiot
    *
    * @param o OmaLista joka halutaan tulostaa
    */
   public static void tulosta(OmaLista o){

      //käydään läpi lista alkio kerrallaan
      for (int i = 0; i < o.koko(); i++){
         Object tulostettava = o.alkio(i);

         //tarkistetaan löytyykö tulostettavaa
         if(tulostettava != null){
            System.out.println(tulostettava.toString());
         }
      }
   }

   private Tiedosto kasitteleTiedosto(String tiedMerkkijono) throws IOException {

      //luodaan tiedosto-olio
      File tiedosto = new File(tiedMerkkijono);
      //luodaan lukija
      BufferedReader buffattuLukija = new BufferedReader(new FileReader(tiedosto));
      //luetaan tiedostosta tiedot ja sijoitetaan ne taulukkoon
      String[] tiedostonTeksti = buffattuLukija.readLine().split(" ");

      // jos tiedosto on kuva,
      if (tiedostonTeksti[0].equals("Kuva")) {

         //kahdella rivillä, sillä muuten rivi olisi liian pitkä
         Kuva uusi = new Kuva(tiedMerkkijono, Integer.parseInt(tiedostonTeksti[1]),
            Integer.parseInt(tiedostonTeksti[2]), Integer.parseInt(tiedostonTeksti[3]));
         return uusi;

         //jos tiedosto on video
      } else if (tiedostonTeksti[0].equals("Video")) {
         
         //videossa pitää käyttää doublea koska pituus ei ole kokonaisluku
         //kahdella rivillä, sillä muuten rivi olisi liian pitkä
         Video uusi = new Video(tiedMerkkijono, Integer.parseInt(tiedostonTeksti[1]),
            Double.parseDouble(tiedostonTeksti[2]));
         return uusi;

      } else return null;


   }
}
