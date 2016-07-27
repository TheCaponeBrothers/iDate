 import java.io.*; 
import java.util.*; 

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

public class ReaderXml{ 

  public static void main(String[] args){

    try{ 

      SAXBuilder builder = new SAXBuilder(); 
      Document document = builder.build(new File("agenda.xml")); 
      
      Element root = document.getRootElement(); 
      List<Element> children = root.getChildren(); 
      Iterator<Element> iterator = children.iterator(); 
      
      while(iterator.hasNext()){ 
         Element item = (Element)iterator.next();
         Element start = item.getChild("Start"); 
         System.out.println("Id: " +item.getAttributeValue("id"));
         System.out.println("Name: "+item.getChild("Name").getText());
         System.out.println("Place: "+item.getChild("Place").getText());
         System.out.println("Start: "+start.getChild("Day").getText()+"/"+start.getChild("Month").getText()+"/"+start.getChild("Year").getText()+" "+start.getChild("Hour").getText()+":"+start.getChild("Minute").getText());
         System.out.println("Notify: "+item.getChild("Notify").getText());
         System.out.println("Alerted: "+item.getChild("Alerted").getText());
         System.out.println("Important: "+item.getChild("Important").getText());
         System.out.println();
      }
      
    }catch(Exception e){ 
      System.err.println("Errore durante la lettura dal file"); 
      e.printStackTrace(); 
    } 
    
  }
  
}