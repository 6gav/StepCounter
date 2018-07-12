import java.util.Timer;
import java.util.TimerTask;

public class Animator {

    Timer time,countdown;

    public Animator(TimerTask t,int delay,int lifetime){
        time = new Timer();
        time.schedule(t,delay);

    }
}
