package oope2018ht.viestit;
import oope2018ht.apulaiset.Getteri;
import oope2018ht.apulaiset.Setteri;
import oope2018ht.omalista.OmaLista;
import oope2018ht.tiedostot.Tiedosto;

/** Ketju-luokka jota tarvitaan alueen mallintamisessa
 * <p>
 * OOPE harjoitustyö kevät 2018
 * <p>
 * @author Roope Putila
 * 422600
 * putila.roope.o@student.uta.fi
 */
public class Ketju {
   //atribuutit
    private OmaLista viestiLista;
    private OmaLista oksaLista;
    private int tunniste;
    private String aihe;

    //aksessorit
   @Getteri
   public OmaLista hankiOksaLista(){
      return oksaLista;
   }
    @Getteri
    public OmaLista hankiViestiLista(){
       return viestiLista;
    }

    @Getteri
    public int hankiTunniste(){
       return tunniste;
    }

    @Getteri
    public String hankiAihe(){
       return aihe;
    }

   @Setteri
   public void asetaOksaLista(OmaLista l){
      oksaLista = l;
   }
    @Setteri
    public void asetaViestiLista(OmaLista l){
       viestiLista = l;
    }

    @Setteri
    public void asetaTunniste(int t){
       tunniste = t;
    }

    @Setteri
    public void asetaAihe(String a){
       aihe = a;
    }

    //rakentaja
    public Ketju(int t, String s){
       asetaTunniste(t);
       asetaAihe(s);
       viestiLista = new OmaLista();
       oksaLista = new OmaLista();
    }
    @Override
   public String toString(){
       String paluuarvo = "#" + tunniste + " " + aihe + ' ' + '(' + viestiLista.koko() + " messages)";
       return paluuarvo;
    }

}
