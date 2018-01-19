package net.sf.robocode.host.CompetitionServer;

import net.sf.robocode.battle.BattleProperties;
import net.sf.robocode.io.FileUtil;
import net.sf.robocode.io.Logger;

import javax.swing.*;
import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Enumeration;

/**
 * @author Thales B.V. /Werner Struis (contributor naval)
 */
public class CompetitionServer implements Runnable, ServerCallBacks {
    private ServerSocket socket;
    private ManagerCallBacks cb;
    private int port;
    private boolean isAccepting = true;
    private boolean isStarted = false;
    private boolean asking = true;
    private InetAddress hostAdress = null;
    private BattleProperties properties;

    public CompetitionServer(ManagerCallBacks cb, BattleProperties properties) {
        this.cb = cb;
        this.properties = properties;
    }

    public ServerCallBacks getCallBacks(){
        return this;
    }

    private void setupServer() {
        while(!isStarted && asking){
            setHostAdress();
            choosePort("Please specify your port:");
            if(hostAdress != null && port != 0) {
                startServer();
            }
        }
    }
    private void startServer(){
        if(hostAdress != null){
            try {

                socket = new ServerSocket(port, 10, hostAdress);
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        cb.onServerStarted(hostAdress.getHostAddress(), port, properties);
                    }
                });
                isStarted = true;

            } catch (IOException e) {

                cb.onError("Could not start server on port " + port + ".");
                if(asking) {
                    choosePort("Opening server on port " + port + " Failed, Please specify another port:");
                    startServer();
                }
            }
        }else{
            cb.onError("Could not start server on IP " + hostAdress + ".");
            isStarted = false;
        }

    }

    private void choosePort(String message){
        while(true) {
            if(asking) {
                String portString = JOptionPane.showInputDialog(null, message, "Competition setup", JOptionPane.PLAIN_MESSAGE);
                if (portString == null) {
                    asking = false;
                    break;
                } else if (isValidPort(portString)) {
                    port = Integer.parseInt(portString);
                    break;
                }
            }else{
                break;
            }
        }
    }
    public boolean isValidPort(String port) {
        return port.matches("^([0-9]{1,4}|[1-5][0-9]{4}|6[0-4][0-9]{3}|65[0-4][0-9]{2}|655[0-2][0-9]|6553[0-5])$");
    }

    public InetAddress chooseInetAdress(ArrayList<InetAddress> choices) {
        InetAddress s = (InetAddress) JOptionPane.showInputDialog(
                null,
                "Choose specify your hosting adress:",
                "Competition setup",
                JOptionPane.PLAIN_MESSAGE,
                null,
                choices.toArray(),
                choices.toArray()[0]);
        if(s == null){
            asking = false;
        }
        return s;
    }

    private void setHostAdress(){
        if(asking) {
            ArrayList<InetAddress> validAdresses = new ArrayList<InetAddress>();
            try {
                Enumeration<InetAddress> eth0 = NetworkInterface.getByName("eth0").getInetAddresses();
                Enumeration<NetworkInterface> nwi = NetworkInterface.getNetworkInterfaces();
                while (nwi.hasMoreElements()) {
                    NetworkInterface nw = nwi.nextElement();
                    Enumeration<InetAddress> iadd = nw.getInetAddresses();
                    while (iadd.hasMoreElements()){
                        InetAddress add = iadd.nextElement();
                        System.out.println(add);
                        if(isValidIP(add.getHostAddress())){
                            validAdresses.add(add);
                        }
                    }
                }
                while (eth0.hasMoreElements()) {
                    InetAddress add = eth0.nextElement();
                    System.out.println(add);
                    if (isValidIP(add.getHostAddress())) {
                        validAdresses.add(add);
                    }
                }
                hostAdress = chooseInetAdress(validAdresses);
            } catch (SocketException e) {
                e.printStackTrace();
            }
        }
    }

    public String getIp(){
        return hostAdress.getHostAddress();
    }

    public boolean isValidIP(String ip){
        return ip.matches("^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$");
    }

    public void run() {
        setupServer();

        while (isAccepting && socket != null) {
            try {
                System.out.println("Listening on port " + port);
                Socket client = socket.accept();

                System.out.println("Client connected, starting transfer");
                clientThread thread = new clientThread(client, this);
                Thread t = new Thread(thread);
                t.start();
            } catch (IOException e) {
                if(isAccepting) {
//                System.err.println("Accept failed: " + port);
                    cb.onError("Accept failed on port: " + port);
//                e.printStackTrace();
                }

            }
        }
    }

    public boolean closeServer(){
        isAccepting = false;
        if (socket != null && !socket.isClosed()) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return socket.isClosed();
        }
        return true;
    }

    public boolean isStarted(){
        return socket != null && !socket.isClosed();
    }

    public void onRobotTransfered() {
        cb.onRobotTransfered();
    }


    private class clientThread implements Runnable {
        private Socket socket;
        private CompetitionServer server;
        private DataInputStream d;

        public clientThread(Socket socket, CompetitionServer server) {
            this.socket = socket;
            this.server = server;
        }


        @Override
        public void run() {
            try {
                System.out.println("Transfer started");

                BufferedInputStream is = new BufferedInputStream(socket.getInputStream());
//                try{
                d = new DataInputStream(is);
                String packageName = d.readUTF();
                String fileName = d.readUTF();

                System.out.println(fileName + "");
//                System.err.println(FileUtil.getCwd());
//                FileUtil.getRobotsDataDir()
                File competitionDir = FileUtil.createDir(new File(FileUtil.getCompetitionDir().toURI()));


                File destinationDir = FileUtil.createDir(new File(competitionDir + "/" + packageName));
//                if(!packageName.equals("competition")){
//                    destinationDir = FileUtil.createDir(new File(competitionDir + "/" + packageName));
//                }else{
//                    destinationDir = competitionDir;
//                }


//                System.err.println(competitionDir.getPath());

                if (competitionDir.isDirectory()) {
                    Files.copy(d, Paths.get(destinationDir + "/" + fileName), StandardCopyOption.REPLACE_EXISTING);
                    File f = new File(Paths.get(destinationDir + "/" + fileName).toUri());
                    System.out.println("Transfer completed!");
                    server.onRobotTransfered();
                }


            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if(d != null) d.close();
                    if (socket != null) socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}

