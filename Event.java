//Product
abstract class Event{

	private PDate start;
	private PDate end;
	private PDate creation;
	private String name;
	private String place;
	private Component component;
	private boolean notify;//Ti avverte se l'evento deve essere notificato.
	private boolean alerted;//Se l'evento è stato notificato.
	private boolean important;

	public Event(PDate s,PDate e,String n,String p,boolean b){
		setStart(s);
		setEnd(e);
		setCreation(PDate.getNow());
		setName(n);
		setPlace(p);
		setNotify(b);
		setAlerted(false);
		setImportant(false);//Da definire
	}

	public Event setStart(PDate s){
		start = s;
		return this;
	}

	public Event setEnd(PDate e){
		end = e;
		return this;
	}

	public Event setCreation(PDate c){
		creation = c;
		return this;
	}

	public Event setName(String n){
		name = n;
		return this;
	}

	public Event setPlace(String p){
		place = p;
		return this;
	}

	public Event setComponent(Component c){
		component = c;
		return this;
	}
	
	public Event setNotify(boolean n){
		notify = n;
		return this;
	}
	
	public Event setAlerted(boolean n){
		alerted = n;
		return this;
	}
	
	public Event setImportant(boolean n){
		important = n;
		return this;
	}

	public PDate getStart(){
		return start;
	}

	public PDate getEnd(){
		return end;
	}

	public PDate getCreation(){
		return creation;
	}

	public String getName(){
		return name;
	}

	public String getPlace(){
		return place;
	}

	public Component getComponent(){
		return component;
	}
	
	public void removeComponent(){
		if(component instanceof Composite) ((Composite) component).removeAll();
		component = null;
	}
	
	public boolean getNotify(){
		return notify;
	}
	
	public boolean getAlerted(){
		return alerted;
	}
	
	public boolean getImportant(){
		return important;
	}
	
	public void print(){
		System.out.print(getName()+" in "+getPlace()+" da ");
		getStart().print();
		System.out.print(" a ");
		getEnd().print();
		System.out.println(".");
		if(component != null) component.show();
	}
	
	abstract public int compareEvent(Event e);//Verifica se l'evento e Ã¨ compatibile con questo. Ritorna false se sono incompatibili, true altrimenti.
	abstract public void notifyEvent();//Avverte quando l'evento sta per verificarsi
	
	public void notifyEvent(PDate advise,String mex){
		PDate now = PDate.getNow();
		if(this.getNotify() == true && this.getAlerted() == false && now.compareTo(this.getStart().subtract(advise)) != -1 && now.compareTo(this.getStart()) != 1){
			this.setAlerted(true);
			System.out.println("L'evento "+this.getName()+" si verificherà "+mex);
			if(this.getComponent() != null) this.getComponent().show();
		}
	}

}