import java.util.ArrayList;

/*
 * Hajautustaulua kuvaava luokka.
 *
 * Hajautustaulu on toteutettu 10000 kokoisena int[]-taulukkona. Alkion arvo tallennetaan
 * alkion avainta (eli numeroa itseään) vastaavaan indeksiin.
 *
 * Tommi Kivinen
 * tommi.kivinen@tuni.fi
 */

public class Hashtable {
   
   /*
    * Muuttujat.
    */
    
   int[] Hashtable;
   
   /*
    * Rakentaja.
    */
    
   public Hashtable() {
      Hashtable = new int[10000];
      
      // Alustetaan kaikki arvot 0.
      for(int i = 0; i < 1000; i++) {
         this.add(i, 0);
      }
   }
   
   /*
    * Metodit.
    */
   
   // Lisäysmetodi.
   // Saa parametrina avaimen, jonka perusteella lisää arvon hajautustauluun.
   // Palauttaa true, jos lisäys onnistui.
   public void add(int key, int value) throws IllegalArgumentException {
      if(key >= 0 && key < 10000) {
         Hashtable[key] = value;
      }

      else {
         // Avain oli virheellinen.
         throw new IllegalArgumentException();
      }
   }
   
   // Arvonpalautusmetodi.
   // Saa parametrina avaimen, ja palauttaa avainta vastaavan arvon.
   public int get(int key) {
      return Hashtable[key];
   }
   
   // Kokometodi.
   // Palauttaa alkioiden lukumäärän hajautustaulussa.
   public int size() {
      int counter = 0;
      
      for(int i = 0; i < this.capacity(); i++) {
         if(this.get(i) != 0) {
            counter++;
         }
      }
      
      return counter;
   }
   
   // Kapasiteettimetodi.
   // Palauttaa hajautustaulun kapasiteetin, eli alkioiden teoreettisen maksimimäärän.
   public int capacity() {
      return Hashtable.length;   // Pitäisi olla 10000 aina.
   }
   
   // Poistometodi.
   // Saa parametrina avaimen, jota vastaavan arvon poistaa hajautustaulusta.
   // Palauttaa true, jos onnistui. False, jos poisto epäonnistui.
   public boolean remove(int key) {
      if(key >= 0 && key < 10000) {
         Hashtable[key] = 0;
         return true;
      }
      else {
         return false;
      }
   }
}