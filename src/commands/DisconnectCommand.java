package commands;

public class DisconnectCommand implements Command {
    @Override
    public void executeCommand(String[] array) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        OpenDataServer.stop=true;
        ConnectCommand.stop=true;
        System.out.println("bye");
    }
}
