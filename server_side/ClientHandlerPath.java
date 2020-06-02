package server_side;

import java.io.InputStream;
import java.io.OutputStream;

import viewmodel.ViewModel;

public class ClientHandlerPath implements ClientHandler {
    MyClientHandler ch;
    public static volatile boolean stop=false;

    public ClientHandlerPath(MyClientHandler ch) {
        this.ch = ch;
    }

    @Override
    public void handleClient(InputStream in, OutputStream out) {

            while(!stop)
            {

                ch.handleClient(in,out);
                /*
                synchronized (ViewModel.lock)
                {
                    try {
                        ViewModel.lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                 */
            }


    }
}
