/**
 * Copyright (c) 2001-2017 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/epl-v10.html
 */
//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated by jni4net. See http://jni4net.sourceforge.net/ 
//     Runtime Version:2.0.50727.8669
//
//     Changes to this file may cause incorrect behavior and will be lost if
//     the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace robocode.control {
    
    
    #region Component Designer generated code 
    [global::net.sf.jni4net.attributes.JavaClassAttribute()]
    public partial class BattleSpecification : global::java.lang.Object, global::java.io.Serializable {
        
        internal new static global::java.lang.Class staticClass;
        
        internal static global::net.sf.jni4net.jni.MethodId j4n_getInactivityTime0;
        
        internal static global::net.sf.jni4net.jni.MethodId j4n_getGunCoolingRate1;
        
        internal static global::net.sf.jni4net.jni.MethodId j4n_getBattlefield2;
        
        internal static global::net.sf.jni4net.jni.MethodId j4n_getNumRounds3;
        
        internal static global::net.sf.jni4net.jni.MethodId j4n_getHideEnemyNames4;
        
        internal static global::net.sf.jni4net.jni.MethodId j4n_getSentryBorderSize5;
        
        internal static global::net.sf.jni4net.jni.MethodId j4n_getRobots6;
        
        internal static global::net.sf.jni4net.jni.MethodId j4n_getInitialSetups7;
        
        internal static global::net.sf.jni4net.jni.MethodId j4n__ctorBattleSpecification8;
        
        internal static global::net.sf.jni4net.jni.MethodId j4n__ctorBattleSpecification9;
        
        internal static global::net.sf.jni4net.jni.MethodId j4n__ctorBattleSpecification10;
        
        internal static global::net.sf.jni4net.jni.MethodId j4n__ctorBattleSpecification11;
        
        internal static global::net.sf.jni4net.jni.MethodId j4n__ctorBattleSpecification12;
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("(IJDLrobocode/control/BattlefieldSpecification;[Lrobocode/control/RobotSpecificat" +
            "ion;)V")]
        public BattleSpecification(int par0, long par1, double par2, global::robocode.control.BattlefieldSpecification par3, robocode.control.RobotSpecification[] par4) : 
                base(((global::net.sf.jni4net.jni.JNIEnv)(null))) {
            global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.ThreadEnv;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 20)){
            @__env.NewObject(global::robocode.control.BattleSpecification.staticClass, global::robocode.control.BattleSpecification.j4n__ctorBattleSpecification8, this, global::net.sf.jni4net.utils.Convertor.ParPrimC2J(par0), global::net.sf.jni4net.utils.Convertor.ParPrimC2J(par1), global::net.sf.jni4net.utils.Convertor.ParPrimC2J(par2), global::net.sf.jni4net.utils.Convertor.ParStrongCp2J(par3), global::net.sf.jni4net.utils.Convertor.ParArrayStrongCp2J(@__env, par4));
            }
        }
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("(IJDZLrobocode/control/BattlefieldSpecification;[Lrobocode/control/RobotSpecifica" +
            "tion;)V")]
        public BattleSpecification(int par0, long par1, double par2, bool par3, global::robocode.control.BattlefieldSpecification par4, robocode.control.RobotSpecification[] par5) : 
                base(((global::net.sf.jni4net.jni.JNIEnv)(null))) {
            global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.ThreadEnv;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 22)){
            @__env.NewObject(global::robocode.control.BattleSpecification.staticClass, global::robocode.control.BattleSpecification.j4n__ctorBattleSpecification9, this, global::net.sf.jni4net.utils.Convertor.ParPrimC2J(par0), global::net.sf.jni4net.utils.Convertor.ParPrimC2J(par1), global::net.sf.jni4net.utils.Convertor.ParPrimC2J(par2), global::net.sf.jni4net.utils.Convertor.ParPrimC2J(par3), global::net.sf.jni4net.utils.Convertor.ParStrongCp2J(par4), global::net.sf.jni4net.utils.Convertor.ParArrayStrongCp2J(@__env, par5));
            }
        }
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("(Lrobocode/control/BattlefieldSpecification;IJDIZ[Lrobocode/control/RobotSpecific" +
            "ation;)V")]
        public BattleSpecification(global::robocode.control.BattlefieldSpecification par0, int par1, long par2, double par3, int par4, bool par5, robocode.control.RobotSpecification[] par6) : 
                base(((global::net.sf.jni4net.jni.JNIEnv)(null))) {
            global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.ThreadEnv;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 24)){
            @__env.NewObject(global::robocode.control.BattleSpecification.staticClass, global::robocode.control.BattleSpecification.j4n__ctorBattleSpecification10, this, global::net.sf.jni4net.utils.Convertor.ParStrongCp2J(par0), global::net.sf.jni4net.utils.Convertor.ParPrimC2J(par1), global::net.sf.jni4net.utils.Convertor.ParPrimC2J(par2), global::net.sf.jni4net.utils.Convertor.ParPrimC2J(par3), global::net.sf.jni4net.utils.Convertor.ParPrimC2J(par4), global::net.sf.jni4net.utils.Convertor.ParPrimC2J(par5), global::net.sf.jni4net.utils.Convertor.ParArrayStrongCp2J(@__env, par6));
            }
        }
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("(Lrobocode/control/BattlefieldSpecification;IJDIZ[Lrobocode/control/RobotSpecific" +
            "ation;[Lrobocode/control/RobotSetup;)V")]
        public BattleSpecification(global::robocode.control.BattlefieldSpecification par0, int par1, long par2, double par3, int par4, bool par5, robocode.control.RobotSpecification[] par6, robocode.control.RobotSetup[] par7) : 
                base(((global::net.sf.jni4net.jni.JNIEnv)(null))) {
            global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.ThreadEnv;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 26)){
            @__env.NewObject(global::robocode.control.BattleSpecification.staticClass, global::robocode.control.BattleSpecification.j4n__ctorBattleSpecification11, this, global::net.sf.jni4net.utils.Convertor.ParStrongCp2J(par0), global::net.sf.jni4net.utils.Convertor.ParPrimC2J(par1), global::net.sf.jni4net.utils.Convertor.ParPrimC2J(par2), global::net.sf.jni4net.utils.Convertor.ParPrimC2J(par3), global::net.sf.jni4net.utils.Convertor.ParPrimC2J(par4), global::net.sf.jni4net.utils.Convertor.ParPrimC2J(par5), global::net.sf.jni4net.utils.Convertor.ParArrayStrongCp2J(@__env, par6), global::net.sf.jni4net.utils.Convertor.ParArrayStrongCp2J(@__env, par7));
            }
        }
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("(ILrobocode/control/BattlefieldSpecification;[Lrobocode/control/RobotSpecificatio" +
            "n;)V")]
        public BattleSpecification(int par0, global::robocode.control.BattlefieldSpecification par1, robocode.control.RobotSpecification[] par2) : 
                base(((global::net.sf.jni4net.jni.JNIEnv)(null))) {
            global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.ThreadEnv;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 16)){
            @__env.NewObject(global::robocode.control.BattleSpecification.staticClass, global::robocode.control.BattleSpecification.j4n__ctorBattleSpecification12, this, global::net.sf.jni4net.utils.Convertor.ParPrimC2J(par0), global::net.sf.jni4net.utils.Convertor.ParStrongCp2J(par1), global::net.sf.jni4net.utils.Convertor.ParArrayStrongCp2J(@__env, par2));
            }
        }
        
        protected BattleSpecification(global::net.sf.jni4net.jni.JNIEnv @__env) : 
                base(@__env) {
        }
        
        public static global::java.lang.Class _class {
            get {
                return global::robocode.control.BattleSpecification.staticClass;
            }
        }
        
        private static void InitJNI(global::net.sf.jni4net.jni.JNIEnv @__env, java.lang.Class @__class) {
            global::robocode.control.BattleSpecification.staticClass = @__class;
            global::robocode.control.BattleSpecification.j4n_getInactivityTime0 = @__env.GetMethodID(global::robocode.control.BattleSpecification.staticClass, "getInactivityTime", "()J");
            global::robocode.control.BattleSpecification.j4n_getGunCoolingRate1 = @__env.GetMethodID(global::robocode.control.BattleSpecification.staticClass, "getGunCoolingRate", "()D");
            global::robocode.control.BattleSpecification.j4n_getBattlefield2 = @__env.GetMethodID(global::robocode.control.BattleSpecification.staticClass, "getBattlefield", "()Lrobocode/control/BattlefieldSpecification;");
            global::robocode.control.BattleSpecification.j4n_getNumRounds3 = @__env.GetMethodID(global::robocode.control.BattleSpecification.staticClass, "getNumRounds", "()I");
            global::robocode.control.BattleSpecification.j4n_getHideEnemyNames4 = @__env.GetMethodID(global::robocode.control.BattleSpecification.staticClass, "getHideEnemyNames", "()Z");
            global::robocode.control.BattleSpecification.j4n_getSentryBorderSize5 = @__env.GetMethodID(global::robocode.control.BattleSpecification.staticClass, "getSentryBorderSize", "()I");
            global::robocode.control.BattleSpecification.j4n_getRobots6 = @__env.GetMethodID(global::robocode.control.BattleSpecification.staticClass, "getRobots", "()[Lrobocode/control/RobotSpecification;");
            global::robocode.control.BattleSpecification.j4n_getInitialSetups7 = @__env.GetMethodID(global::robocode.control.BattleSpecification.staticClass, "getInitialSetups", "()[Lrobocode/control/RobotSetup;");
            global::robocode.control.BattleSpecification.j4n__ctorBattleSpecification8 = @__env.GetMethodID(global::robocode.control.BattleSpecification.staticClass, "<init>", "(IJDLrobocode/control/BattlefieldSpecification;[Lrobocode/control/RobotSpecificat" +
                    "ion;)V");
            global::robocode.control.BattleSpecification.j4n__ctorBattleSpecification9 = @__env.GetMethodID(global::robocode.control.BattleSpecification.staticClass, "<init>", "(IJDZLrobocode/control/BattlefieldSpecification;[Lrobocode/control/RobotSpecifica" +
                    "tion;)V");
            global::robocode.control.BattleSpecification.j4n__ctorBattleSpecification10 = @__env.GetMethodID(global::robocode.control.BattleSpecification.staticClass, "<init>", "(Lrobocode/control/BattlefieldSpecification;IJDIZ[Lrobocode/control/RobotSpecific" +
                    "ation;)V");
            global::robocode.control.BattleSpecification.j4n__ctorBattleSpecification11 = @__env.GetMethodID(global::robocode.control.BattleSpecification.staticClass, "<init>", "(Lrobocode/control/BattlefieldSpecification;IJDIZ[Lrobocode/control/RobotSpecific" +
                    "ation;[Lrobocode/control/RobotSetup;)V");
            global::robocode.control.BattleSpecification.j4n__ctorBattleSpecification12 = @__env.GetMethodID(global::robocode.control.BattleSpecification.staticClass, "<init>", "(ILrobocode/control/BattlefieldSpecification;[Lrobocode/control/RobotSpecificatio" +
                    "n;)V");
        }
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("()J")]
        public virtual long getInactivityTime() {
            global::net.sf.jni4net.jni.JNIEnv @__env = this.Env;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 10)){
            return ((long)(@__env.CallLongMethod(this, global::robocode.control.BattleSpecification.j4n_getInactivityTime0)));
            }
        }
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("()D")]
        public virtual double getGunCoolingRate() {
            global::net.sf.jni4net.jni.JNIEnv @__env = this.Env;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 10)){
            return ((double)(@__env.CallDoubleMethod(this, global::robocode.control.BattleSpecification.j4n_getGunCoolingRate1)));
            }
        }
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("()Lrobocode/control/BattlefieldSpecification;")]
        public virtual global::robocode.control.BattlefieldSpecification getBattlefield() {
            global::net.sf.jni4net.jni.JNIEnv @__env = this.Env;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 10)){
            return global::net.sf.jni4net.utils.Convertor.StrongJ2Cp<global::robocode.control.BattlefieldSpecification>(@__env, @__env.CallObjectMethodPtr(this, global::robocode.control.BattleSpecification.j4n_getBattlefield2));
            }
        }
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("()I")]
        public virtual int getNumRounds() {
            global::net.sf.jni4net.jni.JNIEnv @__env = this.Env;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 10)){
            return ((int)(@__env.CallIntMethod(this, global::robocode.control.BattleSpecification.j4n_getNumRounds3)));
            }
        }
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("()Z")]
        public virtual bool getHideEnemyNames() {
            global::net.sf.jni4net.jni.JNIEnv @__env = this.Env;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 10)){
            return ((bool)(@__env.CallBooleanMethod(this, global::robocode.control.BattleSpecification.j4n_getHideEnemyNames4)));
            }
        }
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("()I")]
        public virtual int getSentryBorderSize() {
            global::net.sf.jni4net.jni.JNIEnv @__env = this.Env;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 10)){
            return ((int)(@__env.CallIntMethod(this, global::robocode.control.BattleSpecification.j4n_getSentryBorderSize5)));
            }
        }
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("()[Lrobocode/control/RobotSpecification;")]
        public virtual robocode.control.RobotSpecification[] getRobots() {
            global::net.sf.jni4net.jni.JNIEnv @__env = this.Env;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 10)){
            return global::net.sf.jni4net.utils.Convertor.ArrayStrongJ2Cp<robocode.control.RobotSpecification[], global::robocode.control.RobotSpecification>(@__env, @__env.CallObjectMethodPtr(this, global::robocode.control.BattleSpecification.j4n_getRobots6));
            }
        }
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("()[Lrobocode/control/RobotSetup;")]
        public virtual robocode.control.RobotSetup[] getInitialSetups() {
            global::net.sf.jni4net.jni.JNIEnv @__env = this.Env;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 10)){
            return global::net.sf.jni4net.utils.Convertor.ArrayStrongJ2Cp<robocode.control.RobotSetup[], global::robocode.control.RobotSetup>(@__env, @__env.CallObjectMethodPtr(this, global::robocode.control.BattleSpecification.j4n_getInitialSetups7));
            }
        }
        
        new internal sealed class ContructionHelper : global::net.sf.jni4net.utils.IConstructionHelper {
            
            public global::net.sf.jni4net.jni.IJvmProxy CreateProxy(global::net.sf.jni4net.jni.JNIEnv @__env) {
                return new global::robocode.control.BattleSpecification(@__env);
            }
        }
    }
    #endregion
}
