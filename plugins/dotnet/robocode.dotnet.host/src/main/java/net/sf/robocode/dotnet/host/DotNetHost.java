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

package net.sf.robocode.dotnet.host;

@net.sf.jni4net.attributes.ClrType
public class DotNetHost extends system.Object implements net.sf.robocode.host.IHost {
    
    //<generated-proxy>
    private static system.Type staticType;
    
    protected DotNetHost(net.sf.jni4net.inj.INJEnv __env, long __handle) {
            super(__env, __handle);
    }
    
    @net.sf.jni4net.attributes.ClrConstructor("()V")
    public DotNetHost() {
            super(((net.sf.jni4net.inj.INJEnv)(null)), 0);
        net.sf.robocode.dotnet.host.DotNetHost.__ctorDotNetHost0(this);
    }
    
    @net.sf.jni4net.attributes.ClrMethod("()V")
    private native static void __ctorDotNetHost0(net.sf.jni4net.inj.IClrProxy thiz);
    
    @net.sf.jni4net.attributes.ClrMethod("(Lnet/sf/robocode/host/IHostManager;Lrobocode/control/RobotSpecification;Lnet/sf/robocode/peer/IRobotStatics;Lnet/sf/robocode/peer/IRobotPeer;)Lnet/sf/robocode/host/proxies/IHostingRobotProxy;")
    public native net.sf.robocode.host.proxies.IHostingRobotProxy createRobotProxy(net.sf.robocode.host.IHostManager par0, robocode.control.RobotSpecification par1, net.sf.robocode.peer.IRobotStatics par2, net.sf.robocode.peer.IRobotPeer par3);
    
    @net.sf.jni4net.attributes.ClrMethod("(Lnet/sf/robocode/repository/IRobotItem;)[Ljava/lang/String;")
    public native java.lang.String[] getReferencedClasses(net.sf.robocode.repository.IRobotItem par0);
    
    @net.sf.jni4net.attributes.ClrMethod("(Lnet/sf/robocode/repository/IRobotItem;ZZ)Lnet/sf/robocode/repository/RobotType;")
    public native net.sf.robocode.repository.RobotType getRobotType(net.sf.robocode.repository.IRobotItem par0, boolean par1, boolean par2);
    
    @net.sf.jni4net.attributes.ClrMethod("(Lnet/sf/robocode/host/IHostManager;Ljava/lang/Object;Lnet/sf/robocode/peer/IRobotStatics;Lnet/sf/robocode/peer/IRobotPeer;)Lnet/sf/robocode/host/proxies/IHostingRobotProxy;")
    public native net.sf.robocode.host.proxies.IHostingRobotProxy createRobotProxy(net.sf.robocode.host.IHostManager hostManager, java.lang.Object robotSpecification, net.sf.robocode.peer.IRobotStatics statics, net.sf.robocode.peer.IRobotPeer peer);
    
    public static system.Type typeof() {
        return net.sf.robocode.dotnet.host.DotNetHost.staticType;
    }
    
    private static void InitJNI(net.sf.jni4net.inj.INJEnv env, system.Type staticType) {
        net.sf.robocode.dotnet.host.DotNetHost.staticType = staticType;
    }
    //</generated-proxy>
}
