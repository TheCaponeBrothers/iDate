import java.util.*;

public class Notifier implements Observer{

	public void update(Observable agenda,Object arg){
		if(arg instanceof Event) System.out.println("L'evento "+((Event)arg).getName()+" è stato inserito.");
	}
	
}
