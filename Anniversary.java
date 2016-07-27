//Concrete Product
public class Anniversary extends RecurringEvent{

	public Anniversary(PDate s,PDate e,String n,String p,boolean b){
		super(s,e,n,p,b,0,0,1);
	}
	
	public void notifyEvent(){
		super.notifyEvent(new PDate(4,0,0,0,0),"fra 4 giorni.");
	}

}
