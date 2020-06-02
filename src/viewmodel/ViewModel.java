package viewmodel;

import javafx.beans.property.*;
import model.Model;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

public class ViewModel extends Observable implements Observer {
    public DoubleProperty throttle;
    public DoubleProperty rudder;
    public DoubleProperty aileron;
    public DoubleProperty elevator;
    public StringProperty ip;
    public StringProperty port;
    public DoubleProperty airplaneX;
    public DoubleProperty airplaneY;
    public DoubleProperty startX;
    public DoubleProperty startY;
    public DoubleProperty offset;
    public StringProperty script;
    public DoubleProperty heading;
    public DoubleProperty markSceneX, markSceneY;
    public BooleanProperty path;
    private int data[][];
    private Model model;


    public void setData(int[][] data)
    {
        this.data = data;
        model.GetPlane(startX.getValue(),startY.doubleValue(),offset.getValue());
    }

    public ViewModel() {
        throttle=new SimpleDoubleProperty();
        rudder=new SimpleDoubleProperty();
        aileron=new SimpleDoubleProperty();
        elevator=new SimpleDoubleProperty();
        ip=new SimpleStringProperty();
        port=new SimpleStringProperty();
        airplaneX=new SimpleDoubleProperty();
        airplaneY=new SimpleDoubleProperty();
        startX=new SimpleDoubleProperty();
        startY=new SimpleDoubleProperty();
        offset=new SimpleDoubleProperty();
        script=new SimpleStringProperty();
        heading=new SimpleDoubleProperty();
        markSceneX=new SimpleDoubleProperty();
        markSceneY=new SimpleDoubleProperty();
        path=new SimpleBooleanProperty();

    }


    public void setModel(Model model){
        this.model=model;

    }
    //Bind to the simulator values
    public void setThrottle(){
        String[] data={"set /controls/engines/current-engine/throttle "+throttle.getValue()};
        model.send(data);
    }

    public void setRudder(){
        String[] data={"set /controls/flight/rudder "+rudder.getValue()};
        model.send(data);
    }

    public void setJoystick(){
        String[] data = {
                "set /controls/flight/aileron " + aileron.getValue(),
                "set /controls/flight/elevator " + elevator.getValue(),
        };
        model.send(data);
    }

    public void connect(){
        model.connectManual(ip.getValue(),Integer.parseInt(port.getValue()));
    }

    public void parse(){
        Scanner scanner=new Scanner(script.getValue());
        ArrayList<String> list=new ArrayList<>();
        while(scanner.hasNextLine())
        {
            list.add(scanner.nextLine());
        }
        String[] tmp=new String[list.size()];
        for(int i=0;i<list.size();i++)
        {
            tmp[i]=list.get(i);
        }
        model.parse(tmp);
    }

    public void execute(){
        model.execute();
    }

    public void stopAutoPilot(){
        model.stopAutoPilot();
    }

    public void findPath(double h,double w) {


        if (!path.getValue())
        {
            model.connectPath(ip.getValue(), Integer.parseInt(port.getValue()));
        }
        model.findPath((int) (airplaneY.getValue()/-1), (int) (airplaneX.getValue() +15),Math.abs( (int) (markSceneY.getValue() / h)) ,
               Math.abs((int) (markSceneX.getValue() / w)), data );
    }

    @Override
    public void update(Observable o, Object arg) {
        if(o==model)
        {
            String[] tmp=(String[])arg;
            if(tmp[0].equals("plane")) {
                double x = Double.parseDouble(tmp[1]);
                double y = Double.parseDouble(tmp[2]);
                x = (x - startX.getValue() + offset.getValue());
                x /= offset.getValue();
                y = (y - startY.getValue() + offset.getValue()) / offset.getValue();
                airplaneX.setValue(x);
                airplaneY.setValue(y);
                heading.setValue(Double.parseDouble(tmp[3]));
                setChanged();
                notifyObservers();
            }
            else if(tmp[0].equals("path"))
            {
                setChanged();
                notifyObservers(tmp);
            }
        }
    }
}
