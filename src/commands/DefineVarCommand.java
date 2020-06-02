package commands;

import expressions.ShuntingYard;
import interpreter.CompParser;
import interpreter.Var;

public class DefineVarCommand implements Command {

	@Override
	public void executeCommand(String[] array) {
		Var v=new Var();
		if(array.length>2) {
			if (array[3].equals("bind")) {
				CompParser.symbolTable.put(array[1],CompParser.symbolTable.get(array[4]));
			}
			else {
				StringBuilder exp = new StringBuilder();
				for (int i = 3; i < array.length; i++)
					exp.append(array[i]);
				v.setV(ShuntingYard.calc(exp.toString()));
				CompParser.symbolTable.put(array[1],v);
			}
		}
		else
			CompParser.symbolTable.put(array[1],new Var());

	}

}
