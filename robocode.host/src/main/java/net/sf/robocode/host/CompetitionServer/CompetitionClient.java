package net.sf.robocode.host.CompetitionServer;

import net.sf.robocode.repository.IRobotSpecItem;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.file.Files;

/**
 * @author Thales B.V. /Werner Struis (contributor naval)
 */
public class CompetitionClient implements Runnable{


    private Socket socket = null;
    private OutputStream os = null;

    private String ipString;
    private int portNumber;
    private IRobotSpecItem robot;

    public CompetitionClient(String ipString, int portNumber, IRobotSpecItem robot){
        this.ipString = ipString;
        this.portNumber = portNumber;
        this.robot = robot;
    }

    private void initSocket() {
        try {
            socket = new Socket(ipString, portNumber);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        initSocket();
        try {
            File f = new File(robot.getItemURL().toURI());

            os = new BufferedOutputStream(socket.getOutputStream());

            System.out.println("Sending file...");

            DataOutputStream d = new DataOutputStream(os);
            d.writeUTF(robot.getFullPackage());
            d.writeUTF(robot.getShortClassName() + ".class");
            Files.copy(f.toPath(), d);

            System.out.println("Sending done.");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) os.close();
                if (socket != null) socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
