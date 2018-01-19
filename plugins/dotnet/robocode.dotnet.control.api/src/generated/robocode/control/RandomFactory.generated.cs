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
    public partial class RandomFactory : global::java.lang.Object {
        
        internal new static global::java.lang.Class staticClass;
        
        internal static global::net.sf.jni4net.jni.MethodId j4n_isDeterministic0;
        
        internal static global::net.sf.jni4net.jni.MethodId j4n_getRandom1;
        
        internal static global::net.sf.jni4net.jni.MethodId j4n_setRandom2;
        
        internal static global::net.sf.jni4net.jni.MethodId j4n_resetDeterministic3;
        
        internal static global::net.sf.jni4net.jni.MethodId j4n__ctorRandomFactory4;
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("()V")]
        public RandomFactory() : 
                base(((global::net.sf.jni4net.jni.JNIEnv)(null))) {
            global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.ThreadEnv;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 10)){
            @__env.NewObject(global::robocode.control.RandomFactory.staticClass, global::robocode.control.RandomFactory.j4n__ctorRandomFactory4, this);
            }
        }
        
        protected RandomFactory(global::net.sf.jni4net.jni.JNIEnv @__env) : 
                base(@__env) {
        }
        
        public static global::java.lang.Class _class {
            get {
                return global::robocode.control.RandomFactory.staticClass;
            }
        }
        
        private static void InitJNI(global::net.sf.jni4net.jni.JNIEnv @__env, java.lang.Class @__class) {
            global::robocode.control.RandomFactory.staticClass = @__class;
            global::robocode.control.RandomFactory.j4n_isDeterministic0 = @__env.GetMethodID(global::robocode.control.RandomFactory.staticClass, "isDeterministic", "()Z");
            global::robocode.control.RandomFactory.j4n_getRandom1 = @__env.GetStaticMethodID(global::robocode.control.RandomFactory.staticClass, "getRandom", "()Ljava/util/Random;");
            global::robocode.control.RandomFactory.j4n_setRandom2 = @__env.GetStaticMethodID(global::robocode.control.RandomFactory.staticClass, "setRandom", "(Ljava/util/Random;)V");
            global::robocode.control.RandomFactory.j4n_resetDeterministic3 = @__env.GetStaticMethodID(global::robocode.control.RandomFactory.staticClass, "resetDeterministic", "(J)V");
            global::robocode.control.RandomFactory.j4n__ctorRandomFactory4 = @__env.GetMethodID(global::robocode.control.RandomFactory.staticClass, "<init>", "()V");
        }
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("()Z")]
        public virtual bool isDeterministic() {
            global::net.sf.jni4net.jni.JNIEnv @__env = this.Env;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 10)){
            return ((bool)(@__env.CallBooleanMethod(this, global::robocode.control.RandomFactory.j4n_isDeterministic0)));
            }
        }
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("()Ljava/util/Random;")]
        public static global::java.util.Random getRandom() {
            global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.ThreadEnv;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 10)){
            return global::net.sf.jni4net.utils.Convertor.StrongJ2Cp<global::java.util.Random>(@__env, @__env.CallStaticObjectMethodPtr(global::robocode.control.RandomFactory.staticClass, global::robocode.control.RandomFactory.j4n_getRandom1));
            }
        }
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("(Ljava/util/Random;)V")]
        public static void setRandom(global::java.util.Random par0) {
            global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.ThreadEnv;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 12)){
            @__env.CallStaticVoidMethod(global::robocode.control.RandomFactory.staticClass, global::robocode.control.RandomFactory.j4n_setRandom2, global::net.sf.jni4net.utils.Convertor.ParStrongCp2J(par0));
            }
        }
        
        [global::net.sf.jni4net.attributes.JavaMethodAttribute("(J)V")]
        public static void resetDeterministic(long par0) {
            global::net.sf.jni4net.jni.JNIEnv @__env = global::net.sf.jni4net.jni.JNIEnv.ThreadEnv;
            using(new global::net.sf.jni4net.jni.LocalFrame(@__env, 12)){
            @__env.CallStaticVoidMethod(global::robocode.control.RandomFactory.staticClass, global::robocode.control.RandomFactory.j4n_resetDeterministic3, global::net.sf.jni4net.utils.Convertor.ParPrimC2J(par0));
            }
        }
        
        new internal sealed class ContructionHelper : global::net.sf.jni4net.utils.IConstructionHelper {
            
            public global::net.sf.jni4net.jni.IJvmProxy CreateProxy(global::net.sf.jni4net.jni.JNIEnv @__env) {
                return new global::robocode.control.RandomFactory(@__env);
            }
        }
    }
    #endregion
}
