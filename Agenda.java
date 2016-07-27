//Singleton
import java.io.File;
import java.util.*;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

public class Agenda extends Observable{

	private static Agenda agenda = null;
	private EventCreator creator = null;
	private List<Event> list;
	private static final String xmlFilePath = "Agenda.xml";

	private Agenda(){
		creator = Creator.getInstance();
		list = new LinkedList<Event>();
		this.upload();
	}

	public static Agenda getInstance(){
		if(agenda == null) agenda = new Agenda();
		return agenda;
	}

	private int checkEvent(Event e){
		for(int i=0;i<list.size();i++){
			int aux = list.get(i).compareEvent(e);
			if(aux == 1) return i;// L'evento Ã¨ compatibile con l'i-esimo e viene prima di esso
			else if(aux == 0){  
				System.out.println("L'evento è in conflitto con un altro precedente e non è stato inserito.");
				return -1;//L'evento Ã¨ incompatibile con l'i-esimo
			}
			// Se l'evento Ã¨ compatibile ma viene dopo l'i-esimo (aux == 2) lo confronto con l'i+1-esimo.
		}
		return list.size();//Se l'evento Ã¨ compatibile con tutti ma successivo ad ognuno Ã¨ posto al termine della lista
	}

	public EventCreator getCreator(){
		return creator;
	}

	/*public Event addEvent(int type,PDate start,PDate end,String name,String place,boolean notify){
		return addEvent(creator.getEvent(start,end,name,place,type,notify));
	}
	
	public Event addEvent(PDate start,PDate end,String name,String place,boolean notify,int d,int m,int y){
		return addEvent(creator.getEvent(start,end,name,place,notify,d,m,y));
	}*/

	public Event addEvent(Event e){
		int pos = 0;
		if(e != null && (pos = checkEvent(e)) != -1){
			list.add(pos,e);
			this.setChanged();
			this.notifyObservers(e);
		}
		return e;
	}

	public Event getEvent(int i){
		return list.get(i);
	}
	
	public int getNumEvent(){
		return list.size();
	}

	public void delEvent(Event e){
		list.remove(e);
	}

	public void printEvent(){
		if(this.getNumEvent() == 0) System.out.println("Non ci sono eventi in agenda.");
		for(Event event : list) event.print();
	}

	public void notifyEvent(){
		for(Event event : list) event.notifyEvent();
	}
	
	public void search(String text,int i){
		int j = 0;
		for(Event event : list){
			if((i == 0 && event.getName().equals(text)) || (i == 1 && event.getPlace().equals(text))){
				j++;
				event.print();
			}
		}
		if(j == 0) System.out.println("Non sono stati trovati eventi con la seguente parola chiave: "+text+" come "+((i==0) ? "nome" : "luogo")+".");
		else System.out.println(((j > 1) ? "Sono stati trovati " : "È stato trovato ")+j+" event"+((j > 1) ? "i" : "o")+" con la seguente parola chiave: "+text+" come "+((i==0) ? "nome" : "luogo")+".");
	}
	
	public ArrayList<Event> getEvent(String text){
		ArrayList<Event> arrei = new ArrayList<Event>();
		for(Event event : list){
			if( (event.getName().equals(text)) || (event.getPlace().equals(text)) ){
				arrei.add(event);
			}
		}
		int j = arrei.size();
		if(j == 0) System.out.println("Non sono stati trovati eventi con la seguente parola chiave: "+text+" nel nome o nel luogo di svolgimento");
		else System.out.println(((j > 1) ? "Sono stati trovati " : "È stato trovato ")+j+" event"+((j > 1) ? "i" : "o"));
		return arrei;
	}
	
	public void search(int d,int m,int y){
		int j = 0;
		for(Event event : list){
			if(event.getStart().getDay() == d && event.getStart().getMonth() == m && event.getStart().getYear() == y){
				j++;
				event.print();
			}
		}
		if(j == 0) System.out.println("Non sono stati trovati eventi per la seguente data: "+d+"/"+m+"/"+y);
		else System.out.println(((j > 1) ? "Sono stati trovati " : "È stato trovato ")+j+" event"+((j > 1) ? "i" : "o")+" per la seguente data: "+d+"/"+m+"/"+y);
	}
	
	public ArrayList<Event> getEvent(int d,int m,int y){
		ArrayList<Event> arrei = new ArrayList<Event>();
		for(Event event : list){
			if(event.getStart().getDay() == d && event.getStart().getMonth() == m && event.getStart().getYear() == y){
				arrei.add(event);
			}
		}
		int j = arrei.size();
		if(j == 0) System.out.println("Non sono stati trovati eventi per la seguente data: "+d+"/"+m+"/"+y);
		else System.out.println(((j > 1) ? "Sono stati trovati " : "È stato trovato ")+j+" event"+((j > 1) ? "i" : "o")+" per la seguente data: "+d+"/"+m+"/"+y);

		return arrei;
	}
	
	public void delete(){
		PDate aDay = new PDate(1,0,0,0,0);
		for(Event event : list){
			if(PDate.getNow().compareTo(event.getEnd().add(aDay)) == 1){
				if((event.getImportant() != true || event instanceof NormalEvent))
					list.remove(event);
				else ((RecurringEvent)event).modifice();
			}else return;
		}
	}
	
	public void save(){
		this.delete();
		this.setChanged();
		this.notifyObservers();
	}
	
	public void upload(){
		File f = new File(Agenda.getXmlFilePath());
		if(!f.exists() || f.isDirectory()) return;
		try{ 

			SAXBuilder builder = new SAXBuilder();
			Document document = builder.build(new File(getXmlFilePath()));
			Element root = document.getRootElement();
			List<Element> children = root.getChildren(); 
			Iterator<Element> iterator = children.iterator(); 

			while(iterator.hasNext()){
				Element item = (Element)iterator.next();
				Element start = item.getChild("Start");
				Element end = item.getChild("End");
				Element period = item.getChild("Period");
				PDate dataStart = new PDate(Integer.parseInt(start.getChild("Day").getText()),Integer.parseInt(start.getChild("Month").getText()),Integer.parseInt(start.getChild("Year").getText()),Integer.parseInt(start.getChild("Hour").getText()),Integer.parseInt(start.getChild("Minute").getText()));
				PDate dataEnd = new PDate(Integer.parseInt(end.getChild("Day").getText()),Integer.parseInt(end.getChild("Month").getText()),Integer.parseInt(end.getChild("Year").getText()),Integer.parseInt(end.getChild("Hour").getText()),Integer.parseInt(end.getChild("Minute").getText()));
				Event e;
				if(item.getAttributeValue("type").equals("0") || item.getAttributeValue("type").equals("1"))
					e = this.addEvent(Integer.parseInt(item.getAttributeValue("type")),dataStart,dataEnd,item.getChild("Name").getText(),item.getChild("Place").getText(),Boolean.parseBoolean(item.getChild("Notify").getText()));
				else
					e = this.addEvent(dataStart,dataEnd,item.getChild("Name").getText(),item.getChild("Place").getText(),Boolean.parseBoolean(item.getChild("Notify").getText()),Integer.parseInt(period.getChild("Day").getText()),Integer.parseInt(period.getChild("Month").getText()),Integer.parseInt(period.getChild("Year").getText()));

				if(item.getChild("Composite") != null){
				    try{
						e.setComponent(compone(item.getChild("Composite")));
				    }catch(Exception exc){
				        System.out.println(exc);
				    }
				}
			}
					      
		}catch(Exception e){ 
			System.err.println("Errore durante la lettura dal file "+getXmlFilePath()+"."); 
			e.printStackTrace(); 
		}
	}
	
	private Composite compone(Element e){
		Composite composite = new Composite();
		List<Element> children = e.getChildren(); 
		Iterator<Element> iterator = children.iterator();
		while(iterator.hasNext()){
			Element item = (Element)iterator.next();
			if(item.getName().equals("Composite"))
				composite.add(composite(item),false);
			else if(item.getName().equals("Text"))
				composite.add(new Text(),false);
			else if(item.getName().equals("Picture"))
				composite.add(new Picture(),false);
			else if(item.getName().equals("Sound"))
				composite.add(new Sound(),false);
			else if(item.getName().equals("Movie"))
				composite.add(new Movie(),false);
		}
		return composite;
	}
	
	public static String getXmlFilePath(){
		return xmlFilePath;
	}
}