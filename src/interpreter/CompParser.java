package interpreter;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

import commands.*;

public class CompParser implements Parser {
    private HashMap<String, CommandExpression> commandTable=new HashMap<>();
    private GenericFactory commandFactory=new GenericFactory();
    public static HashMap<String,Var> symbolTable;
    public ArrayList<String[]> lines;
    public ArrayList<CommandExpression> comds;
    public static double returnValue;
    public static ArrayList<String> vars;

    public CompParser(ArrayList<String[]> lines) {

        this.comds=new ArrayList<>();
        this.lines = lines;
        symbolTable=new HashMap<>();
        commandFactory.insertProduct("openDataServer",OpenDataServer.class);
        commandFactory.insertProduct("connect",ConnectCommand.class);
        commandFactory.insertProduct("while",LoopCommand.class);
        commandFactory.insertProduct("var",DefineVarCommand.class);
        commandFactory.insertProduct("return",ReturnCommand.class);
        commandFactory.insertProduct("=",AssignCommand.class);
        commandFactory.insertProduct("disconnect",DisconnectCommand.class);
        commandFactory.insertProduct("print",PrintCommand.class);
        commandFactory.insertProduct("sleep",SleepCommand.class);
        commandFactory.insertProduct("predicate",PredicateCommand.class);
        commandFactory.insertProduct("autoroute",AutoRouteCommand.class);
        commandFactory.insertProduct("if",IfCommand.class);
        commandTable.put("openDataServer", new CommandExpression(new OpenDataServer()));
        commandTable.put("connect",new CommandExpression(new ConnectCommand()));
        commandTable.put("while",new CommandExpression(new LoopCommand()));
        commandTable.put("var",new CommandExpression(new DefineVarCommand()));
        commandTable.put("return",new CommandExpression(new ReturnCommand()));
        commandTable.put("=",new CommandExpression(new AssignCommand()));
        commandTable.put("disconnect",new CommandExpression(new DisconnectCommand()));
        Scanner s= null;
        try {
            s = new Scanner(new BufferedReader(new FileReader("simulator_vars.txt")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        vars=new ArrayList<>();
        while(s.hasNext())
        {
            vars.add(s.nextLine());
        }
        for (String str:vars)
        {
            symbolTable.put(str,new Var(str));
        }

    }
    
    
    private Command parseCondition(ArrayList<String[]> array) {

        ConditionCommand c=(ConditionCommand)commandFactory.getNewProduct(array.get(0)[0]);
        int i=0;
        ArrayList<CommandExpression> tmp=new ArrayList<>();
        CommandExpression cmdtmp=new CommandExpression((Command)commandFactory.getNewProduct("predicate"));
        cmdtmp.setS(array.get(0));
        tmp.add(cmdtmp);
        c.setCommands(tmp);
        c.getCommands().addAll(1,this.parseCommands(new ArrayList<>(array.subList(i+1,array.size()))));
        return c;

    }

    
    private ArrayList<CommandExpression> parseCommands(ArrayList<String[]> array){
        ArrayList<CommandExpression> commands=new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            CommandExpression e=new CommandExpression((Command)commandFactory.getNewProduct(array.get(i)[0]));
            if(e.getC()!=null) {
                if (array.get(i)[0].equals("while") ||array.get(i)[0].equals("if") ) {
                    int index = i;
                    i+=findCloser(new ArrayList<>(array.subList(i+1,array.size())))+1;
                    e.setC(this.parseCondition(new ArrayList<>(array.subList(index, i))));
                }
                e.setS(array.get(i));
            }

            else {
                
                e=new CommandExpression((Command)commandFactory.getNewProduct(array.get(i)[1]));
                e.setS(array.get(i));
            }
            commands.add(e);
        }
        return commands;
    }

    private int findCloser(ArrayList<String[]> array){
        Stack<String> stack=new Stack<>();
        stack.push("{");
        for(int i=0;i<array.size();i++)
        {
            if(array.get(i)[0].equals("while") ||array.get(i)[0].equals("if") )
                stack.push("{");
            if(array.get(i)[0].equals("}"))
            {
                stack.pop();
                if(stack.isEmpty())
                    return i;
            }
        }
        return 0;
    }

  

    public void parse() {
        this.comds=this.parseCommands(lines);

    }

    public double execute(){
        for (CommandExpression e:comds) {
            e.calculate();
        }
        return returnValue;
    }



   

}
