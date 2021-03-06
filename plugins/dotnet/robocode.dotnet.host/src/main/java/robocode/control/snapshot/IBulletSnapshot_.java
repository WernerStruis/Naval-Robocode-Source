/**
 * Copyright (c) 2001-2017 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/epl-v10.html
 */
// ------------------------------------------------------------------------------
//  <autogenerated>
//      This code was generated by jni4net. See http://jni4net.sourceforge.net/ 
// 
//      Changes to this file may cause incorrect behavior and will be lost if 
//      the code is regenerated.
//  </autogenerated>
// ------------------------------------------------------------------------------

package robocode.control.snapshot;

@net.sf.jni4net.attributes.ClrTypeInfo
public final class IBulletSnapshot_ {
    
    //<generated-static>
    private static system.Type staticType;
    
    public static system.Type typeof() {
        return robocode.control.snapshot.IBulletSnapshot_.staticType;
    }
    
    private static void InitJNI(net.sf.jni4net.inj.INJEnv env, system.Type staticType) {
        robocode.control.snapshot.IBulletSnapshot_.staticType = staticType;
    }
    //</generated-static>
}

//<generated-proxy>
@net.sf.jni4net.attributes.ClrProxy
class __IBulletSnapshot extends system.Object implements robocode.control.snapshot.IBulletSnapshot {
    
    protected __IBulletSnapshot(net.sf.jni4net.inj.INJEnv __env, long __handle) {
            super(__env, __handle);
    }
    
    @net.sf.jni4net.attributes.ClrMethod("()Lrobocode/control/snapshot/BulletState;")
    public native robocode.control.snapshot.BulletState getState();
    
    @net.sf.jni4net.attributes.ClrMethod("()D")
    public native double getX();
    
    @net.sf.jni4net.attributes.ClrMethod("()D")
    public native double getY();
    
    @net.sf.jni4net.attributes.ClrMethod("()D")
    public native double getHeading();
    
    @net.sf.jni4net.attributes.ClrMethod("()D")
    public native double getPower();
    
    @net.sf.jni4net.attributes.ClrMethod("()D")
    public native double getPaintX();
    
    @net.sf.jni4net.attributes.ClrMethod("()D")
    public native double getPaintY();
    
    @net.sf.jni4net.attributes.ClrMethod("()I")
    public native int getColor();
    
    @net.sf.jni4net.attributes.ClrMethod("()I")
    public native int getFrame();
    
    @net.sf.jni4net.attributes.ClrMethod("()Z")
    public native boolean isExplosion();
    
    @net.sf.jni4net.attributes.ClrMethod("()I")
    public native int getExplosionImageIndex();
    
    @net.sf.jni4net.attributes.ClrMethod("()I")
    public native int getBulletId();
    
    @net.sf.jni4net.attributes.ClrMethod("()I")
    public native int getVictimIndex();
    
    @net.sf.jni4net.attributes.ClrMethod("()I")
    public native int getOwnerIndex();
}
//</generated-proxy>
