package NetworkUtil;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketWrapper {
    private Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    //constructor for client
    public SocketWrapper(String address, int port) throws IOException {
        socket = new Socket(address, port);
        openStreams();
    }

    //constructor for server
    public SocketWrapper(Socket clientSocket) throws IOException {
        socket = clientSocket;
        openStreams();
    }

    public void openStreams() throws IOException {
        oos = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());
    }

    public void closeStreams() throws IOException {
        oos.close();
        ois.close();
    }

    public synchronized Object read() throws Exception {
        return ois.readObject();
    }

    public synchronized void write(Object str) throws IOException {
        oos.writeObject(str);
        oos.reset();
        oos.flush();
    }
}
