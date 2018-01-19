package net.sf.robocode.host.CompetitionServer;

/**
 * @author Thales B.V. /Werner Struis (contributor naval)
 */
public interface ServerCallBacks {
    boolean isStarted();
    boolean closeServer();
    String getIp();
}
