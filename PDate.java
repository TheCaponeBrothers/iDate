import java.util.*;

public class PDate{

	private int [] arrei = new int[5];

	public PDate(int day,int month,int year,int hour,int minute){
		setDay(day);
		setMonth(month);
		setYear(year);
		setHour(hour);
		setMinute(minute);
	}

	public void setDay(int d){
		arrei[2] = d;
	}

	public void setMonth(int m){
		arrei[3] = m;
	}

	public void setYear(int y){
		arrei[4] = y;
	}

	public void setHour(int h){
		arrei[1] = h;
	}

	public void setMinute(int m){
		arrei[0] = m;
	}

	public int getDay(){
		return arrei[2];
	}

	public int getMonth(){
		return arrei[3];
	}

	public int getYear(){
		return arrei[4];
	}

	public int getHour(){
		return arrei[1];
	}

	public int getMinute(){
		return arrei[0];
	}
	
	public void print(){
		System.out.print(getDay()+"/"+getMonth()+"/"+getYear()+" "+getHour()+":"+getMinute());
	}

	public int compareTo(PDate d){
		if(this.getYear() != d.getYear())
			return (this.getYear() > d.getYear()) ? 1 : -1;
		else if(this.getMonth() != d.getMonth())
			return (this.getMonth() > d.getMonth()) ? 1 : -1;
		else if(this.getDay() != d.getDay())
			return (this.getDay() > d.getDay()) ? 1 : -1;
		else if(this.getHour() != d.getHour())
			return (this.getHour() > d.getHour()) ? 1 : -1;
		else if(this.getMinute() != d.getMinute())
			return (this.getMinute() > d.getMinute()) ? 1 : -1;
		else
			return 0;
	}

	public PDate add(PDate d){
		PDate r = new PDate(getDay(),getMonth(),getYear(),getHour(),getMinute());
		r.setMinute(r.getMinute()+d.getMinute());
		r.riporta(0,59);
		r.setHour(r.getHour()+d.getHour());
		r.riporta(1,23);
		r.setDay(r.getDay()+d.getDay());
		if(r.getMonth() == 6 || r.getMonth() == 11 || r.getMonth() == 4 || r.getMonth() == 9)  r.riporta(2,30);
		else if(r.getMonth() == 2 && r.getYear()%4 != 0)  r.riporta(2,28);
		else r.riporta(2,31);
		r.setMonth(r.getMonth()+d.getMonth());
		riporta(3,12);
		int aux = r.getYear();
		r.setYear(r.getYear()+d.getYear());
		aux = (r.getYear()-aux)-(3-aux%4);
		while(aux > 0){
			r.setDay(r.getDay()-1);
			if(r.getDay() < 1){
				if(r.getMonth() == 7 || r.getMonth() == 12 || r.getMonth() == 5 || r.getMonth() == 10)  r.setDay(r.getDay()+30);
				else if(r.getMonth() == 3 && r.getYear()%4 != 0)  r.setDay(r.getDay()+28);
				else if(r.getMonth() == 3 && r.getYear()%4 == 0)  r.setDay(r.getDay()+29);
				else 								r.setDay(r.getDay()+31);
				r.setMonth(r.getMonth()-1);
			}
			aux -= 4;
		}
		return r;
	}

	private void riporta(int i, int limit){
		if(arrei[i] > limit){
			arrei[i] = arrei[i]%limit;
			arrei[i+1]++;
		}
	}

	public PDate subtract(PDate d){
		PDate r = new PDate(getDay(),getMonth(),getYear(),getHour(),getMinute());
		r.setMinute(r.getMinute()-d.getMinute());
		r.prestito(0,0,59);
		r.setHour(r.getHour()-d.getHour());
		r.prestito(1,0,23);
		r.setDay(r.getDay()-d.getDay());
		if(r.getDay() < 0){
			if(r.getMonth() == 7 || r.getMonth() == 12 || r.getMonth() == 5 || r.getMonth() == 10)  r.setDay(r.getDay()+30);
			else if(r.getMonth() == 3 && r.getYear()%4 != 0)  r.setDay(r.getDay()+28);
			else 								r.setDay(r.getDay()+31);
			r.setMonth(r.getMonth()-1);
		}
		r.setMonth(r.getMonth()-d.getMonth());
		r.prestito(3,0,11);
		int aux = r.getYear();
		r.setYear(r.getYear()-d.getYear());
		aux = (aux-r.getYear())-aux%4;
		while(aux > 0){
			r.setDay(r.getDay()+1);
			if(r.getMonth() == 6 || r.getMonth() == 11 || r.getMonth() == 4 || r.getMonth() == 9)  r.riporta(2,30);
			else if(r.getMonth() == 2 && r.getYear()%4 != 0)  r.riporta(2,28);
			else if(r.getMonth() == 2 && r.getYear()%4 == 0)  r.riporta(2,29);
			else r.riporta(2,31);
			aux -= 4;
		}
		return r;
	}
	
	private void prestito(int i, int limit, int max){
		if(arrei[i] < limit){
			arrei[i] += max;
			arrei[i+1]--;
		}
	}

	public static PDate getNow(){
		GregorianCalendar gc = new GregorianCalendar();
		return new PDate(gc.get(Calendar.DAY_OF_MONTH), gc.get(Calendar.MONTH)+1, gc.get(Calendar.YEAR), gc.get(Calendar.HOUR_OF_DAY), gc.get(Calendar.MINUTE));
	}
	
}