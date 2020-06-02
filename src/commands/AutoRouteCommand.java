package commands;

import model.Model;

public class AutoRouteCommand implements Command {
    @Override
    public void executeCommand(String[] array) {
        Model.turn=false;
    }
}
