//Creator
interface EventCreator{

	public Event getEvent(PDate s,PDate e,String n,String p,boolean b,int d,int m,int y);
	public Event getEvent(PDate s,PDate e,String n,String p,int type,boolean b);
	public static EventCreator getInstance(){ return null; }
	
}