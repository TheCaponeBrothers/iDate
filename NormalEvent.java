//Concrete Product
class NormalEvent extends Event{

	public NormalEvent(PDate s,PDate e,String n,String p,boolean b){
		super(s,e,n,p,b);
	}

	public int compareEvent(Event e){
		if(this.getStart().compareTo(e.getStart()) != -1)
			return (e.getEnd().compareTo(this.getStart()) == 1) ? 0 : 1;
		else
			return (e.getStart().compareTo(this.getEnd()) != -1) ? 2 : 0;
	}
	
	public void notifyEvent(){
		super.notifyEvent(new PDate(0,0,0,0,15),"fra 15 minuti.");
	}
	
}