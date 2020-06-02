package commands;

import java.util.ArrayList;


public class ConditionCommand implements Command {

	protected ArrayList<CommandExpression> commands;

	public ArrayList<CommandExpression> getCommands() {
		return commands;
	}
	public void setCommands(ArrayList<CommandExpression> commands) {
		this.commands = commands;
	}

	@Override
	public void executeCommand(String[] array) {
		
	}

}
