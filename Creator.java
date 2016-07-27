//Concrete Creator
public class Creator implements EventCreator{

	private static Creator creator = null;

	private Creator(){}

	public static EventCreator getInstance(){
		if(creator == null) creator = new Creator();
		return creator;
	}
	
	public Event getEvent(PDate s,PDate e,String n,String p,boolean b,int d,int m,int y){
		if(s.compareTo(e) == 1 || e.compareTo(PDate.getNow()) == -1) return null;
    	return new RecurringEvent(s,e,n,p,b,d,m,y);
	}

	public Event getEvent(PDate s,PDate e,String n,String p,int type,boolean b){
		if(s.compareTo(e) == 1 || e.compareTo(PDate.getNow()) == -1) return null;
		switch(type){
			case 1: return new Anniversary(s,e,n,p,b);
			default: return new NormalEvent(s,e,n,p,b);
		}
	}
	
}