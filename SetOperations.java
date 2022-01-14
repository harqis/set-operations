import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.Integer;
import java.util.stream.IntStream;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * Joukko-operaatiot unioni, leikkaus ja eksklusiivinen disjunktio toteuttava ohjelma.
 *
 * Tommi Kivinen
 * tommi.kivinen@tuni.fi
 */

public class SetOperations {
   
   /*
    * Apumetodit.
    */
    
   // Apumetodi tekstitiedostojen lukemiseen.
   // Virheen sattuessa heitet‰‰n poikkeus, joka siepataan p‰‰luokassa.
   private int[] readInput(String fileName) throws IllegalArgumentException {
      String line;
      
      try {
         // Luodaan uusi lukija.
         // Luetaan alkiot ja tallennetaan ne ArrayListiin.
         BufferedReader br = new BufferedReader(new FileReader("" + fileName + ""));
         ArrayList<String> values = new ArrayList<String>();         
         line = br.readLine();
         
         while(line != null){	  
            values.add(line);
            line = br.readLine();
         }
         
         // Siirret‰‰n luetut alkiot int[]-taulukkoon.
         String[] values2 = values.toArray(new String[0]);         
         int[] intvalues = new int[values2.length];
         
         for(int i = 0; i < intvalues.length; i++) {
            intvalues[i] = Integer.parseInt(values2[i]);
         }
         
         br.close();
         
         return intvalues;
	 
      }
      
      catch(IllegalArgumentException e) {
         // Jos luettu alkio on muuta kuin int-tyyppi‰, heitet‰‰n poikkeus
         // ja tulostetaan virheilmoitus.
         System.out.println("File contains other than integers.");
         throw new IllegalArgumentException();
      }
      catch(IOException e) {
         // Jos tiedostoa ei lˆydy, heitet‰‰n poikkeus
         // ja tulostetaan virheilmoitus.
         System.out.println("File not found.");
         throw new IllegalArgumentException();
      }
   }
   
   // Apumetodi tekstiedostojen kirjoittamiseen.
   private void writeOutput(Hashtable printable, String filename) {
      
      try {
         
         BufferedWriter bw = new BufferedWriter(new FileWriter("" + filename + ""));
         
         for(int i = 0; i < printable.capacity(); i++) {
            if(printable.get(i) != 0) {
               
               int key = i;
               int value = printable.get(i);
               
               String outputrow = key+ " "+value;

               bw.write(outputrow);
               bw.newLine();
            }
         }
         
         bw.close();        
      } 
      catch (IOException e) {
         System.err.format("IOException: %s%n", e);
      }
      
      System.out.println("Writing file...");
   }
   
   /*
    * Joukko-operaatiot.
    */
    
   // Joukko-operaation OR muodostava metodi.
   // Palauttaa OR-operaatioon kuuluvat alkiot sis‰lt‰v‰n hajautustaulun.
   public Hashtable createOR(int[] setA, int[] setB) {
      
      // Luodaan uusi tyhj‰ hajautustaulu.
      Hashtable ORtable = new Hashtable();
      
      // Lasketaan alkioiden esiintym‰t joukoissa,
      // ja lis‰t‰‰n alkiot hajautustauluun.
      for(int i = 0; i < setA.length; i++) {
         int newValue = ORtable.get(setA[i]) + 1;
         ORtable.add(setA[i], newValue);
      }
      
      // Tehd‰‰n samoin toiselle joukolle.
      for(int i = 0; i < setB.length; i++) {
         int newValue = ORtable.get(setB[i]) + 1;
         ORtable.add(setB[i], newValue);
      }
      
      // Palautetaan saatu hajautustaulu.
      return ORtable;
   }
   
   // Joukko-operaation AND muodostava metodi.
   // Palauttaa AND-operaatioon kuuluvat alkiot sis‰lt‰v‰n hajautustaulun.
   public Hashtable createAND(int[] setA, int[] setB) {
      
      // Luodaan uusi tyhj‰ hajautustaulu.
      Hashtable ANDtable = new Hashtable();
      
      // K‰yd‰‰n ensimm‰isen joukon alkiot l‰pi tutkien samalla lˆytyykˆ 
      // toisesta joukosta vastaavia alkioita.
      // Jos lˆytyy, lis‰t‰‰n alkio hajautustauluun.
      for(int i = 0; i < setA.length; i++) {        
         int current = setA[i];
         
         if(IntStream.of(setB).anyMatch(j -> j == current) && ANDtable.get(current) == 0) {
            ANDtable.add(current, i + 1);
         }
      }
      
      // Palautetaan saatu hajautustaulu.
      return ANDtable;
   }
   
   // Joukko-operaation XOR muodostava metodi.
   // Palauttaa XOR-operaatioon kuuluvat alkiot sis‰lt‰v‰n hajautustaulun.
   public Hashtable createXOR(int[] setA, int[] setB) {
      
      // Luodaan uusi tyhj‰ hajautustaulu.
      Hashtable XORtable = new Hashtable();
      
      // K‰yd‰‰n ensimm‰inen joukko l‰pi.
      // Jos vastaavaa alkiota ei lˆydy toisesta joukosta, lis‰t‰‰n
      // alkio hajautustauluun.
      for(int i = 0; i < setA.length; i++) {
         int current = setA[i];
         if(IntStream.of(setB).anyMatch(j -> j == current) == false) {
            XORtable.add(current, 1);
         }
      }
      
      // K‰yd‰‰n toinen joukko l‰pi.
      // Jos vastaavaa alkiota ei lˆydy ensimm‰isest‰ joukosta, lis‰t‰‰n
      // alkio hajautustauluun.
      for(int i = 0; i < setB.length; i++) {
         int current = setB[i];
         if(IntStream.of(setA).anyMatch(j -> j == current) == false) {
            XORtable.add(current, 2);
         }
      }
      
      // Palautetaan saatu hajautustaulu.
      return XORtable;
   }
   
   public static void main(String[] args) {
      
      Tira2019 ht=new Tira2019();
      
      try {
         // Tervehdit‰‰n k‰ytt‰j‰‰.
         // Luetaan k‰ytt‰j‰lt‰ joukkojen tiedostonimet.
         Scanner reader = new Scanner(System.in);
         
         System.out.println("Hello! I am a set operator.");
         System.out.println("Please note that I only accept values from 0 to 9999.");
         System.out.println("The text files must contain only integers. Each value must be on its own line.");
         System.out.println("Please, give the filename of the first set: (e.g. setA.txt)");
         String fileA = reader.nextLine();
         System.out.println("Please, give the filename of the second set:");
         String fileB = reader.nextLine();
         
         // Luetaan joukot ja tallennetaan ne omiin taulukoihinsa.
         int[] setA = ht.readInput(fileA);
         int[] setB = ht.readInput(fileB);
         
         // Muodostetaan joukko-operaatiot.
         Hashtable ORtable = ht.createOR(setA, setB);
         Hashtable ANDtable = ht.createAND(setA, setB);
         Hashtable XORtable = ht.createXOR(setA, setB);
         
         // Kysyt‰‰n poistosta k‰ytt‰j‰lt‰.
         System.out.println("Do you want to remove a value or values? Write \"y\" or \"yes\" to remove.");
         String answer = reader.nextLine();
         
         // Jos k‰ytt‰j‰ haluaa poistaa alkioita...
         if(answer.equals("y") || answer.equals("yes")) {
            System.out.println("Which values do you want to remove?");
            System.out.println("Please, write values with spaces between them, like this: 1 2 3");
            
            // Luetaan poistettavat alkiot ja tallennetaan ne String[]-taulukkoon.
            String removables = reader.nextLine();
            String[] removableValues = removables.split(" ");
            int[] removeThese = new int[removableValues.length];
            
            // Muutetaan alkiot int-muotoon ja siirret‰‰n ne int[]-taulukkoon.
            for(int i = 0; i < removeThese.length; i++) {
               removeThese[i] = Integer.parseInt(removableValues[i]);
            }
            
            // Poistetaan poistettavat alkiot jokaisesta hajautustaulusta.
            for(int i = 0; i < removableValues.length; i++) {
               ORtable.remove(removeThese[i]);
               ANDtable.remove(removeThese[i]);
               XORtable.remove(removeThese[i]);           
            }
            
            // Ilmoitus k‰ytt‰j‰lle. Poisto tehty.
            System.out.println("Values have been removed from the hashtables.");
         }
         
         // Tulostetaan hajautustaulut omiin tekstitiedostoihinsa ja 
         // alkioiden lukum‰‰r‰t hajautustauluissa n‰ytˆlle.
         ht.writeOutput(ORtable, "or.txt");
         System.out.println("The amount of values in the union: " + ORtable.size() + "");
         ht.writeOutput(ANDtable, "and.txt");
         System.out.println("The amount of values in the intersection: " + ANDtable.size() + "");
         ht.writeOutput(XORtable, "xor.txt");
         System.out.println("The amount of values in the exclusive disjunction: " + XORtable.size() + "");
         
         // Kehotetaan k‰ytt‰j‰‰ katsomaan tehdyt tiedostot.
         System.out.println("Please, check your folder for created text files.");
         
         // Suljetaan lukija.
         reader.close();
      }

      catch(Exception e) {
         // Poikkeuksen tapahtuessa ohjelman suorittaminen lopetetaan ja
         // tulostetaan virheilmoitus.
         System.out.println("Something went wrong. Please, try again.");
      }
	}
}