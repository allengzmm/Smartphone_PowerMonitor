import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Timers extends Thread{
	static ArrayList<Timers> Timers;
	boolean on;
	boolean armed;
	int act;
	int pos;
	int grouppos;
	Date time;
	Date currenttime;
	Date triggertime;
	Date countdowntime;
	SimpleDateFormat sdf;
	
	SimpleDateFormat sdf1;
	String timetotrig;
	
	Timers(){
		time=new Date();
		countdowntime=new Date();
		sdf=new SimpleDateFormat("HH:mm:ss:dd:MM:yyyy:Z");//:Z
		try {
			triggertime=sdf.parse(GUI.getTriggtime(false)+":"+time.getDate()+":"+(time.getMonth()+1)+":"+(time.getYear()+1900)+":+0100");
			countdowntime.setTime(triggertime.getTime()-time.getTime());//=sdf.parse(Integer.toString(triggertime.getHours()-time.getHours())+":"+Integer.toString(triggertime.getMinutes()-time.getMinutes())+":"+Integer.toString(triggertime.getSeconds()-time.getSeconds()) +":"+Integer.toString(triggertime.getDate()-time.getDate())+":"+Integer.toString((triggertime.getMonth()+1)-(time.getMonth()+1))+":"+Integer.toString((triggertime.getYear()+1900)-(time.getYear()+1900))+":+0100");
			countdowntime.setTime(countdowntime.getTime()+(long)(time.getTimezoneOffset()*60000));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		pos=Timers.size();
	}
	
	static void init_timers(){
		Timers=new ArrayList<Timers>();
	}
	
	static void add_timer(Timers ti){
		Timers.add(ti);
	}
	
	static int getNumberOfTimers(){
		return Timers.size();
	}
	
	static Timers getTimer(int i){
		return Timers.get(i);
	}
	
	static void refreshList(){
		int j;
		for(j=0;j<Timers.size();j++){
			if(!Timers.get(j).getStatus()){remove_timer(j);}
		}
	}
	
	static void remove_timer(int i){
		Timers.get(i).deactivate();
		Timers.remove(i);
		while(i<Timers.size()){
			Timers.get(i).updatePos();
			i++;
		}
	}
	
	static void clear_timers(){
		Timers.clear();
	}
	
	void updatePos(){
		pos--;
	}
	
	void deactivate(){
		armed=false;
		on=false;
	}
	
	void setTrigTime(){
		try{
			triggertime=sdf.parse(GUI.getTriggtime(true)+":"+time.getDate()+":"+(time.getMonth()+1)+":"+(time.getYear()+1900)+":+0100");
		}catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	void setPos(int p){
		pos=p;
	}
	
	void setGroup(boolean tf){
		grouppos=0;
		if(tf){
			int ct=0, g=0, gmax=0;
			while(ct<Timers.size()){
				if(gmax<Timers.get(ct).getGroupPos()){
					gmax=Timers.get(ct).getGroupPos();
				}
				ct++;
			}if(gmax!=0){
				for(ct=0;ct<Timers.size();ct++){
					if(gmax==Timers.get(ct).getGroupPos()){g++;}
				}
				switch(g){
					case 1 : grouppos=gmax; break;
					case 2 : grouppos=gmax+1; break;
				}
			}else{grouppos++;}
		}
	}
	
	void updateGroupPos(){
		grouppos--;
	}
	
	public void run(){
		on=true;
		armed=true;
		while(on){
			if(getCountDown()>=-3600000L){
				countdown();
				try{
					sleep(1000);
				}catch (InterruptedException e) {
					e.printStackTrace();
				}
			}else{
				on=false;
				if(armed){
					switch(act){
						case 0 : GUI.tStart(); break;
						case 1 : GUI.tPauseResume(); break;
						case 2 : GUI.tStop(); break;
					}
				}
				refreshList();
				GUI.refresh_tasks();
			}
		}
	}
	
	void countdown(){
		currenttime=new Date();
		countdowntime.setTime(triggertime.getTime()-currenttime.getTime());
		countdowntime.setTime(countdowntime.getTime()+(long)(time.getTimezoneOffset()*60000));
	}
	
	void setTriggerAct(int i){
		act=i;
	}
	
	long getCountDown(){
		return countdowntime.getTime();
	}
	
	int getPos(){
		return pos;
	}
	
	int getGroupPos(){
		return grouppos;
	}
	
	String getNature(){
		String nat=null;
		switch(act){
			case 0 : nat="Start"; break;
			case 1 : nat="Pause/Resume"; break;
			case 2 : nat="Stop"; break;
		}
		return nat;
	}
	
	String getTriggerTime(){
		return sdf.format(triggertime);
	}
	
	boolean getStatus(){
		return on;
	}
	
	String getAll(){
		return Integer.toBinaryString(pos)+" : ";
	}
	
	void toTriggerOrNot(){
	}
}
