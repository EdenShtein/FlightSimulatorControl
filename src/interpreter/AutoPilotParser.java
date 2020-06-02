package interpreter;


import java.util.ArrayList;

public class AutoPilotParser {
    CompParser p;
    public static volatile boolean stop=true;
    public static volatile boolean close=false;
    public static Thread  thread1;
    public int i = 0;
    public AutoPilotParser(CompParser p) {
        this.p = p;


    }
    public void parse() {
        p.parse();
        i=0;
    }

    public void execute(){

        thread1=new Thread(()->{
            while(!close) {
                while (!stop && i < p.comds.size()) {
                    p.comds.get(i).calculate();
                    i++;
                }
            }
        });

        thread1.start();

    }

    public void add(ArrayList<String[]> lines){
        p.lines.clear();
        p.lines.addAll(lines);
        CompParser.symbolTable.put("stop",new Var(1));
        for (String[] s:p.lines) {
            if (s[0].equals("while"))
            {
                StringBuilder tmp=new StringBuilder(s[s.length-2]);
                tmp.append("&&stop!=0");
                s[s.length-2]=tmp.toString();
            }
        }
    }
    public void stop(){
        Var v= CompParser.symbolTable.get("stop");
        if(v!=null)
            v.setV(0);
        AutoPilotParser.stop=true;
    }
    public void Continue()
    {
        CompParser.symbolTable.get("stop").setV(1);
    }
}
