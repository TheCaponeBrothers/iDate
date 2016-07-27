//Main
import java.util.*;
public class Main{

	public static Agenda agenda;
	public static Event event;
	public static PDate start, end;

	public static void main(String[] args) throws java.lang.Exception{
		WriterXml write = new WriterXml();
		Notify notify = new Notify();
		agenda = Agenda.getInstance();
		agenda.addObserver(notify);
		agenda.addObserver(write);
		
		start = new PDate(21,06,2015,16,0);
		end = new PDate(21,06,2015,17,0);
		event = agenda.addEvent(agenda.getCreator().getEvent(1,start,end,"Compleanno di Zappi","221B Baker Street",true));
		
		start = PDate.getNow().add(new PDate(0,0,0,1,10));
		end = PDate.getNow().add(new PDate(0,0,0,1,50));
		agenda.addEvent(agenda.getCreator().getEvent(start,end,"Andare in palestra","NY",true,2,0,0));
		
		ArrayList<Event> a = agenda.getEvent("Compleanno di Zappi");
		for(int i=0; i<a.size(); i++){
			a.get(i).print();
		}
		a.get(0).setName("Compleanno di Pietro").setPlace("A casa mia.").setImportant(true);
		
		/*agenda.search("Compleanno di Zappi",0);
		System.out.println();
		agenda.search("Compleanno di Pietro",1);
		System.out.println();
		agenda.search(21,6,2015);
		System.out.println();*/
		
		/*
		Composite composite = new Composite();
		composite.add(new Text());
		composite.add(new Picture());
		
		Composite composite2 = new Composite();
		composite2.add(new Text());
		composite2.add(new Sound());
		
		composite.add(composite2);
		
	    try{
			event.setComponent(composite);
	    }catch(Exception exc){
	        System.out.println(exc);
	    }
	    */
		
		agenda.printEvent();
		//agenda.save();

		//for(;;)
			//agenda.notifyEvent();
		
	}

}