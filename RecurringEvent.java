//Concrete Product
public class RecurringEvent extends Event{

	private int periodD, periodM, periodY;//Il periodo di ripetizione è indicato in giorni, mesi e anni

	public RecurringEvent(PDate s,PDate e,String n,String p,boolean b,int d,int m,int y){
		super(s,e,n,p,b);
		setDays(d);
		setMonths(m);
		setYears(y);
	}

	public void setDays(int d){
		periodD = d;
	}

	public void setMonths(int m){
		periodM = m;
	}

	public void setYears(int y){
		periodY = y;
	}

	public int getDays(){
		return periodD;
	}

	public int getMonths(){
		return periodM;
	}

	public int getYears(){
		return periodY;
	}

	public int compareEvent(Event e){
		PDate auxS = e.getStart();
		PDate auxE = e.getEnd();
		PDate per = new PDate(periodD, periodM, periodY,0,0);
		int aux = 1;
		while(auxS.compareTo(this.getEnd()) == 1){
			auxS = auxS.subtract(per);
			auxE = auxE.subtract(per);
			aux  = 0;
		}
		if(auxE.compareTo(this.getStart()) == 1){
			auxS = new PDate(this.getStart().getDay(),this.getStart().getMonth(),this.getStart().getYear(),e.getStart().getHour(),e.getStart().getMinute());
			auxE = new PDate(this.getEnd().getDay(),this.getEnd().getMonth(),this.getEnd().getYear(),e.getEnd().getHour(),e.getEnd().getMinute());
			if(this.getStart().compareTo(e.getStart()) != -1)
				return (e.getEnd().compareTo(this.getStart()) == 1) ? 0 : 1;
			else
				return (e.getStart().compareTo(this.getEnd()) != -1) ? 2 : 0;	
		}
		else if(aux == 0) return 2;
		else return 1;
	}
	
	public void notifyEvent(){
		super.notifyEvent(new PDate(periodD,periodM,periodY,0,0),"fra "+periodD+" giorn"+((periodD > 1 || periodD == 0) ? "i " : "o ")+periodM+" mes"+((periodM > 0 || periodM == 0) ? "i " : "e ")+periodY+" ann"+((periodD > 0) ? "i" : "o")+".");
	}
	
	public void modifice(){
		super.setStart(this.getStart().add(new PDate(this.getDays(),this.getMonths(),this.getYears(),0,0)));
		super.setEnd(this.getEnd().add(new PDate(this.getDays(),this.getMonths(),this.getYears(),0,0)));
		super.setAlerted(false);
	}

}