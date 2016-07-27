//Composite
import java.util.*;

public class Composite implements Component{

    private List<Component> Childs;

    //Costruttore
    public Composite(){
        Childs = new LinkedList<Component>();
    }

    //Mostra gli oggetti
    public void show(){
    	System.out.println(this.getClass().getSimpleName()+" ("+this.getSize()+")");
        for(Component Child : Childs) Child.show();
    }

    //Aggiunge l'oggetto alla composizione
    public void add(Component obj,boolean txt){
    	if(txt) System.out.println("È stato aggiunto l'oggetto "+obj.getClass().getSimpleName()+" al oggetto "+this.getClass().getSimpleName()+".");
        Childs.add(obj);
    }
 
    //Rimuove l'oggetto dalla composizione
    public void remove(Component obj){
    	System.out.println("È stato tolto l'oggetto "+obj.getClass().getSimpleName()+" al oggetto "+this.getClass().getSimpleName()+".");
        Childs.remove(obj);
    }

    //Rimuove tutti gli oggetti
    public void removeAll(){
    	while(getSize() > 0){
    		if(getComponent(0) instanceof Composite) ((Composite) getComponent(0)).removeAll();
            this.remove(getComponent(0));
    	} 
    }

    //Ritorna il numero di elementi della lista
    public int getSize(){
        return Childs.size();
    }

    //Ritorna la lista
    public List<Component> getComponents(){
        return Childs;
    }

    //Ritorna i-esimo elemento della lista
    public Component getComponent(int i){
        return Childs.get(i);
    }

}