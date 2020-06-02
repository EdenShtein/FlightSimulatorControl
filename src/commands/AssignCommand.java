package commands;

import expressions.ShuntingYard;
import interpreter.CompParser;

public class AssignCommand implements Command {
    @Override
    public void executeCommand(String[] array) {
        if (array[2].equals("bind")) {
            if(CompParser.symbolTable.get(array[0]).getV()!=CompParser.symbolTable.get(array[3]).getV())
                CompParser.symbolTable.get(array[0]).setV(CompParser.symbolTable.get(array[3]).getV());
            CompParser.symbolTable.get(array[3]).addObserver(CompParser.symbolTable.get(array[0]));
            CompParser.symbolTable.get(array[0]).addObserver(CompParser.symbolTable.get(array[3]));

        }
        else {
            StringBuilder exp = new StringBuilder();
            for (int i = 2; i < array.length; i++)
                exp.append(array[i]);
            double tmp=ShuntingYard.calc(exp.toString());
            if(CompParser.symbolTable.get(array[0]).getLocation()!=null) {
                ConnectCommand.out.println("set " + CompParser.symbolTable.get(array[0]).getLocation() + " " + tmp);
                ConnectCommand.out.flush();
                System.out.println("set " + CompParser.symbolTable.get(array[0]).getLocation() + " " + tmp);
            }
            else
                CompParser.symbolTable.get(array[0]).setV(tmp);
        }
    }
}
