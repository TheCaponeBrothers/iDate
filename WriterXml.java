import java.util.*;
import java.io.*;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class WriterXml implements Observer{
	
	public void update(Observable agenda,Object arg){
		if(arg != null) return;
		try{
			Document document = new Document(new Element("Agenda"));
			for(int i=0;i<((Agenda)agenda).getNumEvent();i++){
				Event e = ((Agenda)agenda).getEvent(i);
				int type = ((e instanceof Anniversary) ? 1 : ((e instanceof NormalEvent) ? 0 : 2));
				Element event = new Element("Event").setAttribute("id",""+i).setAttribute("type",""+type);					
				event.addContent(new Element("Name").setText(e.getName()));
				event.addContent(new Element("Place").setText(e.getPlace()));
				event.addContent(this.date("Creation",e.getCreation()));
				event.addContent(this.date("Start",e.getStart()));
				event.addContent(this.date("End",e.getEnd()));
				event.addContent(new Element("Notify").setText(""+e.getNotify()));
				event.addContent(new Element("Alerted").setText(""+e.getAlerted()));
				event.addContent(new Element("Important").setText(""+e.getImportant()));
				if(type == 2 ) event.addContent(period(((RecurringEvent)e).getDays(),((RecurringEvent)e).getMonths(),((RecurringEvent)e).getYears()));
				if(e.getComponent() != null) event.addContent(component(e.getComponent()));
				document.getRootElement().addContent(event);
			}
			XMLOutputter xmlOutputer = new XMLOutputter();
			xmlOutputer.setFormat(Format.getPrettyFormat());
			xmlOutputer.output(document,new FileWriter(Agenda.getXmlFilePath()));
			System.out.println("L'agenda è stata salvata.");
		}catch(IOException ex){
			System.out.println(ex.getMessage());
		}
	}
	
	private Element date(String name,PDate date){
		Element data = new Element(name);
		data.addContent(new Element("Minute").setText(""+date.getMinute()));
		data.addContent(new Element("Hour").setText(""+date.getHour()));
		data.addContent(new Element("Day").setText(""+date.getDay()));
		data.addContent(new Element("Month").setText(""+date.getMonth()));
		data.addContent(new Element("Year").setText(""+date.getYear()));
		return data;
	}
	
	private Element period(int d,int m,int y){
		Element data = new Element("Period");
		data.addContent(new Element("Day").setText(""+d));
		data.addContent(new Element("Month").setText(""+m));
		data.addContent(new Element("Year").setText(""+y));
		return data;
	}
	
	private Element component(Component c){
		if(c instanceof Composite){
			Element data = new Element("Composite");
			for(Component Child : ((Composite) c).getComponents())
				data.addContent(component(Child));
			return data;
		}else return new Element(c.getClass().getSimpleName());
	}	
}