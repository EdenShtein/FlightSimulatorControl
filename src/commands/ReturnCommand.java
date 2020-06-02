package commands;

import expressions.ShuntingYard;
import interpreter.CompParser;

public class ReturnCommand implements Command {

    @Override
    public void executeCommand(String[] array) {

        StringBuilder exp = new StringBuilder();
        for (int i = 1; i < array.length; i++)
            exp.append(array[i]);
        CompParser.returnValue = ShuntingYard.calc(exp.toString());
    }

}
