package net.sf.robocode.BlocklyCallbacks;

import robocode.control.snapshot.ITurnSnapshot;

/**
 * @author Thales B.V. /Werner Struis (contributor naval)
 */
public interface BlocklyCallBacks {

    void exit();

    void onIncomingSnapshot(ITurnSnapshot turnSnapshot);

    void onBattleEnded();
}
