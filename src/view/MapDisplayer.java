package view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;



public class MapDisplayer extends Canvas {
    int[][] mapData;
    double min=Double.MAX_VALUE;
    double max=0;


    public void setMapData(int[][] mapData) {
        this.mapData = mapData;

        for(int i=0;i<mapData.length;i++)
            for (int j=0;j<mapData[i].length;j++)
            {
                if(min>mapData[i][j])
                    min=mapData[i][j];
                if(max<mapData[i][j])
                    max=mapData[i][j];
            }
        double new_max=255;
        double new_min=0;
        for (int i=0;i<mapData.length;i++)
            for (int j=0;j<mapData[i].length;j++)
            {
                mapData[i][j]=(int)((mapData[i][j]-min)/(max-min)*(new_max-new_min)+new_min);
            }
        redraw();
    }

    public void redraw(){
        if(mapData!=null){
            double H=getHeight();
            double W=getWidth();
            double h=H/mapData.length;
            double w=W/mapData[0].length;
            GraphicsContext gc=getGraphicsContext2D();

            for (int i=0;i<mapData.length;i++)
                for (int j=0;j<mapData[i].length;j++)
                {
                    int tmp=mapData[i][j];
                    gc.setFill(Color.rgb(255-tmp,0+tmp,0));
                    gc.fillRect(j*w,i*h,w,h);
                }
        }
    }
}
