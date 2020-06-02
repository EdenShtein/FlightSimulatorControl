package view;



import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import viewmodel.ViewModel;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.net.URL;
import java.util.*;

public class FlightController implements Initializable, Observer {


    @FXML
    private Canvas airplane;
    @FXML
    private Canvas markX;
    @FXML
    private  TextArea TextArea;
    @FXML
    private TextField port;
    @FXML
    private TextField ip;
    @FXML
    private Button submit;
    private Stage stage=new Stage();
    @FXML
    private Slider throttle;
    @FXML
    private Slider rudder;
    @FXML
    private RadioButton auto;
    @FXML
    private MapDisplayer map;
    @FXML
    private RadioButton manual;
    @FXML
    private Circle border;
    @FXML
    private Circle Joystick;
    @FXML
    private TitledPane background;

    private static int identifier;//this variable is to identify which button was pressed
    double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;
    public DoubleProperty markSceneX, markSceneY;
    public DoubleProperty aileron;
    public DoubleProperty elevator;
    public DoubleProperty airplaneX;
    public DoubleProperty airplaneY;
    public DoubleProperty startX;
    public DoubleProperty startY;
    public DoubleProperty offset;
    public DoubleProperty heading;
    private BooleanProperty path;
    public double lastX;
    public double lastY;
    public int mapData[][];
    private Image plane[];
    private Image mark;
    private ViewModel viewModel;
    private String[] solution;

    public void LoadDate() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("CSV Files","csv"));
        fileChooser.setCurrentDirectory(new File("./"));
        int v = fileChooser.showOpenDialog(null);
        if (v == JFileChooser.APPROVE_OPTION) {
            BufferedReader br = null;
            String line = "";
            String cvsSplitBy = ",";

            ArrayList<String[]> numbers = new ArrayList<>();
            try {

                br = new BufferedReader(new FileReader(fileChooser.getSelectedFile()));
                String[] start=br.readLine().split(cvsSplitBy);
                startX.setValue(Double.parseDouble(start[0]));
                startY.setValue(Double.parseDouble(start[1]));
                start=br.readLine().split(cvsSplitBy);
                offset.setValue(Double.parseDouble(start[0]));
                while ((line = br.readLine()) != null) {
                    numbers.add(line.split(cvsSplitBy));
                }
                mapData = new int[numbers.size()][];

                for (int i = 0; i < numbers.size(); i++) {
                    mapData[i] = new int[numbers.get(i).length];

                    for (int j = 0; j < numbers.get(i).length; j++) {
                        String tmp=numbers.get(i)[j];
                        mapData[i][j] = Integer.parseInt(tmp);

                    }
                }
                this.viewModel.setData(mapData);
                this.drawAirplane();
                map.setMapData(mapData);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public  void LoadText(){
        TextArea.clear();
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("./"));
        int v=fileChooser.showOpenDialog(null);
        ArrayList<String> list=new ArrayList<>();
        if(v==JFileChooser.APPROVE_OPTION) {
            try {
                Scanner s=new Scanner(new BufferedReader(new FileReader(fileChooser.getSelectedFile())));
                while(s.hasNextLine()) {
                    TextArea.appendText(s.nextLine());
                    TextArea.appendText("\n");

                }
                viewModel.parse();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
//Opens the popup window to the connect button
    public void Connect(){
        Parent root = null;
        try {
            FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("Popup.fxml"));
            root = fxmlLoader.load();
            FlightController fc=fxmlLoader.getController();
            fc.viewModel=this.viewModel;
            stage.setTitle("Connect");
            stage.setScene(new Scene(root));
            if(!stage.isShowing()) {
                stage.show();
                this.identifier=0;

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //Opens the popup window to the calculate path button
    public void Calc(){
        Parent root = null;

        try {
            FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("Popup.fxml"));
            root = fxmlLoader.load();
            FlightController fc=fxmlLoader.getController();
            fc.viewModel=this.viewModel;
            fc.mapData=this.mapData;
            fc.markX=this.markX;
            fc.path=new SimpleBooleanProperty();
            fc.path.bindBidirectional(this.path);
            stage.setTitle("Calculate Path");
            stage.setScene(new Scene(root));
            if(!stage.isShowing()) {
                this.identifier=1;
                stage.show();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Submit(){
        this.viewModel.ip.bindBidirectional(ip.textProperty());
        this.viewModel.port.bindBidirectional(port.textProperty());
        if(this.identifier==0) {
            viewModel.connect();

            Stage stage = (Stage) submit.getScene().getWindow();

            stage.close();
        }
        if(this.identifier==1)
        {
            double H = markX.getHeight();
            double W = markX.getWidth();
            double h = H / mapData.length;
            double w = W / mapData[0].length;
            viewModel.findPath(h,w);
            //boolean variable which indicates if this the first time you needed to find the shortest path
            path.setValue(true);
            Stage stage = (Stage) submit.getScene().getWindow();
            stage.close();
        }
        ip.clear();
        port.clear();
    }

    public void AutoPilot(){
        Select("auto");
    }

    public void Manual()
    {
        Select("manual");
    }

    //Help function to select the right button
    public void Select(String s){
        if(s.equals("auto"))
        {
            if(manual.isSelected())
            {
                manual.setSelected(false);
                auto.setSelected(true);

            }
            viewModel.execute();
        }
        else if(s.equals("manual"))
        {
            if(auto.isSelected())
            {
                auto.setSelected(false);
                manual.setSelected(true);
                viewModel.stopAutoPilot();
            }

        }
    }
//Draws an airplane on the map according to its position of flight
    public void drawAirplane(){
        if(airplaneX.getValue()!=null&&airplaneY.getValue()!=null)
        {

            double H = airplane.getHeight();
            double W = airplane.getWidth();
            double h = H / mapData.length;
            double w = W / mapData[0].length;
            GraphicsContext gc = airplane.getGraphicsContext2D();
            lastX=airplaneX.getValue();
            lastY=airplaneY.getValue()*-1;
            gc.clearRect(0,0,W,H);

            if(heading.getValue()>=0&&heading.getValue()<39)
                gc.drawImage(plane[0], w*lastX, lastY*h, 25, 25);
            if(heading.getValue()>=39&&heading.getValue()<80)
                gc.drawImage(plane[1], w*lastX, lastY*h, 25, 25);
            if(heading.getValue()>=80&&heading.getValue()<129)
                gc.drawImage(plane[2], w*lastX, lastY*h, 25, 25);
            if(heading.getValue()>=129&&heading.getValue()<170)
                gc.drawImage(plane[3], w*lastX, lastY*h, 25, 25);
            if(heading.getValue()>=170&&heading.getValue()<219)
                gc.drawImage(plane[4], w*lastX, lastY*h, 25, 25);
            if(heading.getValue()>=219&&heading.getValue()<260)
                gc.drawImage(plane[5], w*lastX, lastY*h, 25, 25);
            if(heading.getValue()>=260&&heading.getValue()<309)
                gc.drawImage(plane[6], w*lastX, lastY*h, 25, 25);
            if(heading.getValue()>=309)
                gc.drawImage(plane[7], w*lastX, lastY*h, 25, 25);
        }

    }
//Draw the shortest path 
    public void drawMark(){
        double H = markX.getHeight();
        double W = markX.getWidth();
        double h = H / mapData.length;
        double w = W / mapData[0].length;
        GraphicsContext gc = markX.getGraphicsContext2D();
        gc.clearRect(0,0,W,H);
        gc.drawImage(mark, markSceneX.getValue()-13,markSceneY.getValue() , 25, 25);
        if(path.getValue())
            viewModel.findPath(h,w);
    }

    public void drawLine(){
        double H = markX.getHeight();
        double W = markX.getWidth();
        double h = H / mapData.length;
        double w = W / mapData[0].length;
        GraphicsContext gc=markX.getGraphicsContext2D();
        String move=solution[1];
        double x= airplaneX.getValue()*w+10*w;
        double y=airplaneY.getValue()*-h+6*h;
        for(int i=2;i<solution.length;i++)
        {
            switch (move) {
                case "Right":
                    gc.setStroke(Color.BLACK.darker());
                    gc.strokeLine(x, y, x + w, y);
                    x +=  w;
                    break;
                case "Left":
                    gc.setStroke(Color.BLACK.darker());
                    gc.strokeLine(x, y, x -  w, y);
                    x -=  w;
                    break;
                case "Up":
                    gc.setStroke(Color.BLACK.darker());
                    gc.strokeLine(x, y, x, y - h);
                    y -=  h;
                    break;
                case "Down":
                    gc.setStroke(Color.BLACK.darker());
                    gc.strokeLine(x, y, x, y +  h);
                    y += h;
            }
            move=solution[i];
        }



    }
//Event - press on the map
    EventHandler<MouseEvent> mapClick = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            markSceneX.setValue(e.getX());
            markSceneY.setValue(e.getY());
            drawMark();
        }
    };

//Event - Press on the joystick
    EventHandler<MouseEvent> joystickClick =
            new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent t) {
                    orgSceneX = t.getSceneX();
                    orgSceneY = t.getSceneY();
                    orgTranslateX = ((Circle)(t.getSource())).getTranslateX();
                    orgTranslateY = ((Circle)(t.getSource())).getTranslateY();
                }
            };

    EventHandler<MouseEvent> joystickMove =
            new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent t) {
                    double offsetX = t.getSceneX() - orgSceneX;
                    double offsetY = t.getSceneY() - orgSceneY;
                    double newTranslateX = orgTranslateX + offsetX;
                    double newTranslateY = orgTranslateY + offsetY;
                    if(isInCircle(newTranslateX,newTranslateY)) {
                        ((Circle) (t.getSource())).setTranslateX(newTranslateX);
                        ((Circle) (t.getSource())).setTranslateY(newTranslateY);
                        if(manual.isSelected()) {
                            aileron.setValue(normalizationX(newTranslateX));
                            elevator.setValue(normalizationY(newTranslateY));
                            viewModel.setJoystick();
                        }
                    }
                }
            };

    //these functions normalizes the data from the coordinates that are received from the mouse to the flight gear's values
    private double normalizationX(double num){
        double max=(border.getRadius()-Joystick.getRadius())+border.getCenterX();
        double min=border.getCenterX()-(border.getRadius()-Joystick.getRadius());
        double new_max=1;
        double new_min=-1;
        return (((num-min)/(max-min)*(new_max-new_min)+new_min));
    }
    private double normalizationY(double num){
        double min=(border.getRadius()-Joystick.getRadius())+border.getCenterY();
        double max=border.getCenterY()-(border.getRadius()-Joystick.getRadius());
        double new_max=1;
        double new_min=-1;
        return (((num-min)/(max-min)*(new_max-new_min)+new_min));
    }
    
    private  boolean isInCircle(double x,double y){
        return (Math.pow((x-border.getCenterX()),2)+Math.pow((y-border.getCenterY()),2))<=Math.pow(border.getRadius()-Joystick.getRadius(),2);
    }

    EventHandler<MouseEvent> joystickRelease = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent t) {

                    ((Circle)(t.getSource())).setTranslateX(orgTranslateX);
                    ((Circle)(t.getSource())).setTranslateY(orgTranslateY);
                }
            };

    //Data binding between View and the ViewModel 
    public void setViewModel(ViewModel viewModel){
        this.viewModel=viewModel;
        throttle.valueProperty().bindBidirectional(viewModel.throttle);
        rudder.valueProperty().bindBidirectional(viewModel.rudder);
        aileron=new SimpleDoubleProperty();
        elevator=new SimpleDoubleProperty();
        aileron.bindBidirectional(viewModel.aileron);
        elevator.bindBidirectional(viewModel.elevator);
        airplaneX=new SimpleDoubleProperty();
        airplaneY=new SimpleDoubleProperty();
        startX=new SimpleDoubleProperty();
        startY=new SimpleDoubleProperty();
        airplaneX.bindBidirectional(viewModel.airplaneX);
        airplaneY.bindBidirectional(viewModel.airplaneY);
        startX.bindBidirectional(viewModel.startX);
        startY.bindBidirectional(viewModel.startY);
        offset=new SimpleDoubleProperty();
        offset.bindBidirectional(viewModel.offset);
        viewModel.script.bindBidirectional(TextArea.textProperty());
        heading=new SimpleDoubleProperty();
        heading.bindBidirectional(viewModel.heading);
        markSceneX=new SimpleDoubleProperty();
        markSceneY=new SimpleDoubleProperty();
        markSceneY.bindBidirectional(viewModel.markSceneY);
        markSceneX.bindBidirectional(viewModel.markSceneX);
        path=new SimpleBooleanProperty();
        path.bindBidirectional(viewModel.path);
        path.setValue(false);
        plane=new Image[8];
        try {
            plane[0]=new Image(new FileInputStream("./resources/plane0.png"));
            plane[1]=new Image(new FileInputStream("./resources/plane45.png"));
            plane[2]=new Image(new FileInputStream("./resources/plane90.png"));
            plane[3]=new Image(new FileInputStream("./resources/plane135.png"));
            plane[4]=new Image(new FileInputStream("./resources/plane180.png"));
            plane[5]=new Image(new FileInputStream("./resources/plane225.png"));
            plane[6]=new Image(new FileInputStream("./resources/plane270.png"));
            plane[7]=new Image(new FileInputStream("./resources/plane315.png"));
            mark=new Image(new FileInputStream("./resources/mark.png"));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

   //Get data from the mouse 
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        if(location.getPath().contains("Flight.fxml")) {

            throttle.valueProperty().addListener((observable, oldValue, newValue) -> {
                if(manual.isSelected())
                    viewModel.setThrottle();
            });

            rudder.valueProperty().addListener((observable, oldValue, newValue) -> {
                if(manual.isSelected())
                    viewModel.setRudder();
            });
            Joystick.setOnMousePressed(joystickClick);
            Joystick.setOnMouseDragged(joystickMove);
            Joystick.setOnMouseReleased(joystickRelease);
            markX.setOnMouseClicked(mapClick);
            identifier=-1;
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if(o==viewModel)
        {
            if(arg==null)
                drawAirplane();
            else
            {
                solution=(String[])arg;
                this.drawLine();
            }
        }
    }
}
