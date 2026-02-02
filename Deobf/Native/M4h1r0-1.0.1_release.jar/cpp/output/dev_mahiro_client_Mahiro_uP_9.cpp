#include "../native_jvm.hpp"
#include "../string_pool.hpp"
#include "dev_mahiro_client_Mahiro_uP_9.hpp"

// dev/mahiro/client/Mahiro_uP
namespace native_jvm::classes::__ngen_dev_mahiro_client_Mahiro_uP_9 {

    char *string_pool;

    jstring cstrings[51];
    std::mutex cclasses_mtx[37];
    jclass cclasses[37];
    jmethodID cmethods[51];
    jfieldID cfields[6];

    // Mahiro_e([Ljava/lang/Object;)Ljava/security/PublicKey;
    jobject JNICALL __ngen_native_Mahiro_e1(JNIEnv *env, jclass clazz, jarray arg0) {
        jobject classloader = utils::get_classloader_from_class(env, clazz);
        if (env->ExceptionCheck()) { return (jobject) 0; }
        if (classloader == nullptr) { env->FatalError(((char *)(string_pool + 71LL))); return (jobject) 0; }
    
        jobject lookup = nullptr;
        // try-catch-class java/lang/Exception
        if (!cclasses[0] || env->IsSameObject(cclasses[0], NULL)) { cclasses_mtx[0].lock(); if (!cclasses[0] || env->IsSameObject(cclasses[0], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[0]))) { cclasses[0] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[0].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } }
        // try-catch-class java/lang/Throwable
        if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { cclasses_mtx[1].lock(); if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[1]))) { cclasses[1] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[1].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } }
        jvalue cstack0 = {}, cstack1 = {}, cstack2 = {}, cstack3 = {}, cstack4 = {}, cstack5 = {}, cstack6 = {}, cstack7 = {}, cstack8 = {}, cstack9 = {}, cstack10 = {}, cstack11 = {}, cstack12 = {}, cstack13 = {};
        jvalue clocal0 = {}, clocal1 = {}, clocal2 = {}, clocal3 = {}, clocal4 = {};
        std::unordered_set<jobject> refs;
    
        clocal0.l = arg0; refs.insert(clocal0.l);
    
        // ALOAD 0; Stack: 0
        cstack0.l = clocal0.l; refs.insert(cstack0.l);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // ICONST_0; Stack: 2
        cstack2.i = 0;
        // New stack: 3
        // AALOAD; Stack: 3
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 122LL)), -1); else { cstack1.l = env->GetObjectArrayElement((jobjectArray) cstack1.l, cstack2.i); refs.insert(cstack1.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 2
        // CHECKCAST [B; Stack: 2
        if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { cclasses_mtx[2].lock(); if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { if (jclass clazz = env->FindClass(((char *)(string_pool + 2920LL)))) { cclasses[2] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[2].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (cstack1.l != nullptr && !env->IsInstanceOf(cstack1.l, (cclasses[2]))) { utils::throw_re(env, ((char *)(string_pool + 133LL)), (std::string(((char *)(string_pool + 162LL))) + std::string(((char *)(string_pool + 2920LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (jobject) 0; } } 
        // New stack: 2
        // ASTORE 3; Stack: 2
        clocal3.l = cstack1.l; refs.insert(cstack1.l);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // ICONST_1; Stack: 2
        cstack2.i = 1;
        // New stack: 3
        // AALOAD; Stack: 3
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 122LL)), -1); else { cstack1.l = env->GetObjectArrayElement((jobjectArray) cstack1.l, cstack2.i); refs.insert(cstack1.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 2
        // CHECKCAST java/lang/Long; Stack: 2
        if (!cclasses[3] || env->IsSameObject(cclasses[3], NULL)) { cclasses_mtx[3].lock(); if (!cclasses[3] || env->IsSameObject(cclasses[3], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[2]))) { cclasses[3] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[3].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (cstack1.l != nullptr && !env->IsInstanceOf(cstack1.l, (cclasses[3]))) { utils::throw_re(env, ((char *)(string_pool + 133LL)), (std::string(((char *)(string_pool + 162LL))) + std::string(((char *)(string_pool + 195LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (jobject) 0; } } 
        // New stack: 2
        // INVOKEVIRTUAL java/lang/Long.longValue()J; Stack: 2
        if (!cclasses[3] || env->IsSameObject(cclasses[3], NULL)) { cclasses_mtx[3].lock(); if (!cclasses[3] || env->IsSameObject(cclasses[3], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[2]))) { cclasses[3] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[3].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[0]) { cmethods[0] = env->GetMethodID((cclasses[3]), ((char *)(string_pool + 210LL)), ((char *)(string_pool + 220LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 224LL)), -1); else cstack1.j = env->CallLongMethod(cstack1.l, (cmethods[0])); 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 3
        // LSTORE 1; Stack: 3
        clocal1.j = cstack1.j;
        // New stack: 1
        // POP; Stack: 1
        ;
        // New stack: 0
        // GETSTATIC dev/mahiro/client/Mahiro_uP.a J; Stack: 0
        if (!cclasses[4]  || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cfields[0]) { cfields[0] = env->GetStaticFieldID((cclasses[4]), ((char *)(string_pool + 247LL)), ((char *)(string_pool + 249LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } cstack0.j = env->GetStaticLongField((cclasses[4]), (cfields[0])); 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 2
        // LLOAD 1; Stack: 2
        cstack2.j = clocal1.j;
        // New stack: 4
        // LXOR; Stack: 4
        cstack0.j = cstack0.j ^ cstack2.j;
        // New stack: 2
        // LSTORE 1; Stack: 2
        clocal1.j = cstack0.j;
        // New stack: 0
        // LABEL L7; Stack: 0
        L7: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 0
        // SIPUSH 13304; Stack: 0
        cstack0.i = (jint) 13304;
        // New stack: 1
        // LDC 1538295883885155844; Stack: 1
        cstack1.j = 1538295883885155844LL;
        // New stack: 3
        // LLOAD 1; Stack: 3
        cstack3.j = clocal1.j;
        // New stack: 5
        // LXOR; Stack: 5
        cstack1.j = cstack1.j ^ cstack3.j;
        // New stack: 3
        // LABEL L5; Stack: 3
        L5: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 3
        // ICONST_1; Stack: 3
        cstack3.i = 1;
        // New stack: 4
        // ANEWARRAY java/lang/Object; Stack: 4
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (cstack3.i < 0) utils::throw_re(env, ((char *)(string_pool + 251LL)), ((char *)(string_pool + 288LL)), -1); else { cstack3.l = env->NewObjectArray(cstack3.i, (cclasses[5]), nullptr); refs.insert(cstack3.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 4
        // DUP; Stack: 4
        cstack4 = cstack3;
        // New stack: 5
        // LDC Ldev/mahiro/client/Mahiro_uP;; Stack: 5
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } cstack5.l = (cclasses[6]);
        // New stack: 6
        // SWAP; Stack: 6
        std::swap(cstack5, cstack4);
        // New stack: 6
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.127771650959966.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 6
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } cstack6.l = lookup;
        // New stack: 7
        // LDC Ldev/mahiro/client/Mahiro_uP;; Stack: 7
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } cstack7.l = (cclasses[6]);
        // New stack: 8
        // LDC a; Stack: 8
        cstack8.l = (cstrings[5]);
        // New stack: 9
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;; Stack: 9
        cstack9.l = (cstrings[6]);
        // New stack: 10
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.127771650959966.a()Ljava/lang/ClassLoader;; Stack: 10
        cstack10.l = classloader;
        // New stack: 11
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 11
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[7]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (!cmethods[1]) { cmethods[1] = env->GetStaticMethodID((cclasses[7]), ((char *)(string_pool + 313LL)), ((char *)(string_pool + 340LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }  } cstack9.l = env->CallStaticObjectMethod((cclasses[7]), (cmethods[1]), cstack9.l, cstack10.l); refs.insert(cstack9.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 10
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 10
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (!cmethods[2]) { cmethods[2] = env->GetMethodID((cclasses[8]), ((char *)(string_pool + 413LL)), ((char *)(string_pool + 424LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }  } if (cstack6.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 522LL)), -1); else { cstack6.l = env->CallObjectMethod(cstack6.l, (cmethods[2]), cstack7.l, cstack8.l, cstack9.l); refs.insert(cstack6.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 7
        // SWAP; Stack: 7
        std::swap(cstack6, cstack5);
        // New stack: 7
        // LDC h; Stack: 7
        cstack7.l = (cstrings[9]);
        // New stack: 8
        // SWAP; Stack: 8
        std::swap(cstack7, cstack6);
        // New stack: 8
        // LDC (IJ)Ljava/lang/String;; Stack: 8
        cstack8.l = (cstrings[10]);
        // New stack: 9
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.127771650959966.a()Ljava/lang/ClassLoader;; Stack: 9
        cstack9.l = classloader;
        // New stack: 10
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 10
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[7]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (!cmethods[1]) { cmethods[1] = env->GetStaticMethodID((cclasses[7]), ((char *)(string_pool + 313LL)), ((char *)(string_pool + 340LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }  } cstack8.l = env->CallStaticObjectMethod((cclasses[7]), (cmethods[1]), cstack8.l, cstack9.l); refs.insert(cstack8.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 9
        // SWAP; Stack: 9
        std::swap(cstack8, cstack7);
        // New stack: 9
        // LDC 0; Stack: 9
        cstack9.i = 0;
        // New stack: 10
        // ANEWARRAY java/lang/Object; Stack: 10
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (cstack9.i < 0) utils::throw_re(env, ((char *)(string_pool + 251LL)), ((char *)(string_pool + 288LL)), -1); else { cstack9.l = env->NewObjectArray(cstack9.i, (cclasses[5]), nullptr); refs.insert(cstack9.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 10
        // CHECKCAST java/lang/Object; Stack: 10
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (cstack9.l != nullptr && !env->IsInstanceOf(cstack9.l, (cclasses[5]))) { utils::throw_re(env, ((char *)(string_pool + 133LL)), (std::string(((char *)(string_pool + 162LL))) + std::string(((char *)(string_pool + 547LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } 
        // New stack: 10
        // SWAP; Stack: 10
        std::swap(cstack9, cstack8);
        // New stack: 10
        // INVOKESTATIC native/magic/1/linkcallsite/obfuscator0.127771650959966.a(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/invoke/MemberName;; Stack: 10
        cstack4.l = utils::link_call_site(env, cstack4.l, cstack5.l, cstack6.l, cstack7.l, cstack8.l, cstack9.l); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 5
        // POP; Stack: 5
        ;
        // New stack: 4
        // ICONST_0; Stack: 4
        cstack4.i = 0;
        // New stack: 5
        // AALOAD; Stack: 5
        if (cstack3.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 122LL)), -1); else { cstack3.l = env->GetObjectArrayElement((jobjectArray) cstack3.l, cstack4.i); refs.insert(cstack3.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 4
        // DUP; Stack: 4
        cstack4 = cstack3;
        // New stack: 5
        // INSTANCEOF java/lang/invoke/CallSite; Stack: 5
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[11]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } cstack4.i = cstack4.l == nullptr ? false : env->IsInstanceOf(cstack4.l, (cclasses[9]));
        // New stack: 5
        // IFEQ L10; Stack: 5
        if (cstack4.i == 0) goto L10;
        // New stack: 4
        // INVOKEINTERFACE java/lang/invoke/CallSite.getTarget()Ljava/lang/invoke/MethodHandle;; Stack: 4
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[11]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (!cmethods[3]) { cmethods[3] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 564LL)), ((char *)(string_pool + 574LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }  } if (cstack3.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 608LL)), -1); else { cstack3.l = env->CallObjectMethod(cstack3.l, (cmethods[3])); refs.insert(cstack3.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 4
        // LABEL L10; Stack: 4
        L10: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 4
        // FRAME FULL L: [[Ljava/lang/Object;, 4, [B] S: [1, 4, java/lang/Object]; Stack: 4
        refs.erase(cstack3.l); 
        refs.erase(clocal0.l); refs.erase(clocal3.l); 
        utils::clear_refs(env, refs);
        // New stack: 4
        // CHECKCAST java/lang/invoke/MethodHandle; Stack: 4
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (cstack3.l != nullptr && !env->IsInstanceOf(cstack3.l, (cclasses[10]))) { utils::throw_re(env, ((char *)(string_pool + 133LL)), (std::string(((char *)(string_pool + 162LL))) + std::string(((char *)(string_pool + 635LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } 
        // New stack: 4
        // GOTO L11; Stack: 4
        goto L11;
        // New stack: 4
        // LABEL L6; Stack: 4
        L6: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 4
        // FRAME SAME1 L: null S: [java/lang/Throwable]; Stack: 4
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal3.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // INSTANCEOF java/lang/BootstrapMethodError; Stack: 2
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack1.i = cstack1.l == nullptr ? false : env->IsInstanceOf(cstack1.l, (cclasses[11]));
        // New stack: 2
        // IFNE L12; Stack: 2
        if (cstack1.i != 0) goto L12;
        // New stack: 1
        // NEW java/lang/BootstrapMethodError; Stack: 1
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (jobject obj = env->AllocObject((cclasses[11]))) { cstack1.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 2
        // DUP; Stack: 2
        cstack2 = cstack1;
        // New stack: 3
        // DUP2_X1; Stack: 3
        cstack3 = cstack1; cstack4 = cstack2; cstack2 = cstack0; cstack1 = cstack4; cstack0 = cstack3;
        // New stack: 5
        // POP2; Stack: 5
        ;
        // New stack: 3
        // INVOKESPECIAL java/lang/BootstrapMethodError.<init>(Ljava/lang/Throwable;)V; Stack: 3
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[11]), ((char *)(string_pool + 665LL)), ((char *)(string_pool + 672LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 697LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[11]), (cmethods[4]), cstack2.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 1
        // LABEL L12; Stack: 1
        L12: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/Object]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal3.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 720LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 0
        // LABEL L11; Stack: 0
        L11: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 0
        // FRAME FULL L: [[Ljava/lang/Object;, 4, [B] S: [1, 4, java/lang/invoke/MethodHandle]; Stack: 0
        refs.erase(cstack3.l); 
        refs.erase(clocal0.l); refs.erase(clocal3.l); 
        utils::clear_refs(env, refs);
        // New stack: 4
        // INVOKESTATIC native/magic/1/invoke/obfuscator0.127771650959966.a(IJLjava/lang/invoke/MethodHandle;)Ljava/lang/String;; Stack: 4
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[5]) { cmethods[5] = env->GetStaticMethodID((cclasses[12]), ((char *)(string_pool + 853LL)), ((char *)(string_pool + 868LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack0.l = env->CallStaticObjectMethod((cclasses[12]), (cmethods[5]), cstack0.i, cstack1.j, cstack3.l); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 1
        // CHECKCAST java/lang/String; Stack: 1
        if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { cclasses_mtx[13].lock(); if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[13] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[13].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack0.l != nullptr && !env->IsInstanceOf(cstack0.l, (cclasses[13]))) { utils::throw_re(env, ((char *)(string_pool + 133LL)), (std::string(((char *)(string_pool + 162LL))) + std::string(((char *)(string_pool + 178LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } 
        // New stack: 1
        // LDC 6113908081598049888; Stack: 1
        cstack1.j = 6113908081598049888LL;
        // New stack: 3
        // LLOAD 1; Stack: 3
        cstack3.j = clocal1.j;
        // New stack: 5
        // LABEL L3; Stack: 5
        L3: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 5
        // ICONST_1; Stack: 5
        cstack5.i = 1;
        // New stack: 6
        // ANEWARRAY java/lang/Object; Stack: 6
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } if (cstack5.i < 0) utils::throw_re(env, ((char *)(string_pool + 251LL)), ((char *)(string_pool + 288LL)), -1); else { cstack5.l = env->NewObjectArray(cstack5.i, (cclasses[5]), nullptr); refs.insert(cstack5.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 6
        // DUP; Stack: 6
        cstack6 = cstack5;
        // New stack: 7
        // LDC Ldev/mahiro/client/Mahiro_uP;; Stack: 7
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } cstack7.l = (cclasses[6]);
        // New stack: 8
        // SWAP; Stack: 8
        std::swap(cstack7, cstack6);
        // New stack: 8
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.127771650959966.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 8
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } cstack8.l = lookup;
        // New stack: 9
        // LDC Ldev/mahiro/client/Mahiro_yh;; Stack: 9
        if (!cclasses[14] || env->IsSameObject(cclasses[14], NULL)) { cclasses_mtx[14].lock(); if (!cclasses[14] || env->IsSameObject(cclasses[14], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[16]))) { cclasses[14] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[14].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } cstack9.l = (cclasses[14]);
        // New stack: 10
        // LDC a; Stack: 10
        cstack10.l = (cstrings[5]);
        // New stack: 11
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;; Stack: 11
        cstack11.l = (cstrings[6]);
        // New stack: 12
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.127771650959966.a()Ljava/lang/ClassLoader;; Stack: 12
        cstack12.l = classloader;
        // New stack: 13
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 13
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[7]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } if (!cmethods[1]) { cmethods[1] = env->GetStaticMethodID((cclasses[7]), ((char *)(string_pool + 313LL)), ((char *)(string_pool + 340LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }  } cstack11.l = env->CallStaticObjectMethod((cclasses[7]), (cmethods[1]), cstack11.l, cstack12.l); refs.insert(cstack11.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 12
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 12
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } if (!cmethods[2]) { cmethods[2] = env->GetMethodID((cclasses[8]), ((char *)(string_pool + 413LL)), ((char *)(string_pool + 424LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }  } if (cstack8.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 522LL)), -1); else { cstack8.l = env->CallObjectMethod(cstack8.l, (cmethods[2]), cstack9.l, cstack10.l, cstack11.l); refs.insert(cstack8.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 9
        // SWAP; Stack: 9
        std::swap(cstack8, cstack7);
        // New stack: 9
        // LDC \u00c9; Stack: 9
        cstack9.l = (cstrings[17]);
        // New stack: 10
        // SWAP; Stack: 10
        std::swap(cstack9, cstack8);
        // New stack: 10
        // LDC (Ljava/lang/Object;JJ)Ljava/security/KeyFactory;; Stack: 10
        cstack10.l = (cstrings[18]);
        // New stack: 11
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.127771650959966.a()Ljava/lang/ClassLoader;; Stack: 11
        cstack11.l = classloader;
        // New stack: 12
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 12
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[7]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } if (!cmethods[1]) { cmethods[1] = env->GetStaticMethodID((cclasses[7]), ((char *)(string_pool + 313LL)), ((char *)(string_pool + 340LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }  } cstack10.l = env->CallStaticObjectMethod((cclasses[7]), (cmethods[1]), cstack10.l, cstack11.l); refs.insert(cstack10.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 11
        // SWAP; Stack: 11
        std::swap(cstack10, cstack9);
        // New stack: 11
        // LDC 0; Stack: 11
        cstack11.i = 0;
        // New stack: 12
        // ANEWARRAY java/lang/Object; Stack: 12
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } if (cstack11.i < 0) utils::throw_re(env, ((char *)(string_pool + 251LL)), ((char *)(string_pool + 288LL)), -1); else { cstack11.l = env->NewObjectArray(cstack11.i, (cclasses[5]), nullptr); refs.insert(cstack11.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 12
        // CHECKCAST java/lang/Object; Stack: 12
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } if (cstack11.l != nullptr && !env->IsInstanceOf(cstack11.l, (cclasses[5]))) { utils::throw_re(env, ((char *)(string_pool + 133LL)), (std::string(((char *)(string_pool + 162LL))) + std::string(((char *)(string_pool + 547LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } 
        // New stack: 12
        // SWAP; Stack: 12
        std::swap(cstack11, cstack10);
        // New stack: 12
        // INVOKESTATIC native/magic/1/linkcallsite/obfuscator0.127771650959966.a(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/invoke/MemberName;; Stack: 12
        cstack6.l = utils::link_call_site(env, cstack6.l, cstack7.l, cstack8.l, cstack9.l, cstack10.l, cstack11.l); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 7
        // POP; Stack: 7
        ;
        // New stack: 6
        // ICONST_0; Stack: 6
        cstack6.i = 0;
        // New stack: 7
        // AALOAD; Stack: 7
        if (cstack5.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 122LL)), -1); else { cstack5.l = env->GetObjectArrayElement((jobjectArray) cstack5.l, cstack6.i); refs.insert(cstack5.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 6
        // DUP; Stack: 6
        cstack6 = cstack5;
        // New stack: 7
        // INSTANCEOF java/lang/invoke/CallSite; Stack: 7
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[11]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } cstack6.i = cstack6.l == nullptr ? false : env->IsInstanceOf(cstack6.l, (cclasses[9]));
        // New stack: 7
        // IFEQ L13; Stack: 7
        if (cstack6.i == 0) goto L13;
        // New stack: 6
        // INVOKEINTERFACE java/lang/invoke/CallSite.getTarget()Ljava/lang/invoke/MethodHandle;; Stack: 6
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[11]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } if (!cmethods[3]) { cmethods[3] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 564LL)), ((char *)(string_pool + 574LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }  } if (cstack5.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 608LL)), -1); else { cstack5.l = env->CallObjectMethod(cstack5.l, (cmethods[3])); refs.insert(cstack5.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 6
        // LABEL L13; Stack: 6
        L13: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 6
        // FRAME FULL L: [[Ljava/lang/Object;, 4, [B] S: [java/lang/String, 4, 4, java/lang/Object]; Stack: 6
        refs.erase(cstack0.l); refs.erase(cstack5.l); 
        refs.erase(clocal0.l); refs.erase(clocal3.l); 
        utils::clear_refs(env, refs);
        // New stack: 6
        // CHECKCAST java/lang/invoke/MethodHandle; Stack: 6
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } if (cstack5.l != nullptr && !env->IsInstanceOf(cstack5.l, (cclasses[10]))) { utils::throw_re(env, ((char *)(string_pool + 133LL)), (std::string(((char *)(string_pool + 162LL))) + std::string(((char *)(string_pool + 635LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } 
        // New stack: 6
        // GOTO L14; Stack: 6
        goto L14;
        // New stack: 6
        // LABEL L4; Stack: 6
        L4: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 6
        // FRAME SAME1 L: null S: [java/lang/Throwable]; Stack: 6
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal3.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // INSTANCEOF java/lang/BootstrapMethodError; Stack: 2
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack1.i = cstack1.l == nullptr ? false : env->IsInstanceOf(cstack1.l, (cclasses[11]));
        // New stack: 2
        // IFNE L15; Stack: 2
        if (cstack1.i != 0) goto L15;
        // New stack: 1
        // NEW java/lang/BootstrapMethodError; Stack: 1
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (jobject obj = env->AllocObject((cclasses[11]))) { cstack1.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 2
        // DUP; Stack: 2
        cstack2 = cstack1;
        // New stack: 3
        // DUP2_X1; Stack: 3
        cstack3 = cstack1; cstack4 = cstack2; cstack2 = cstack0; cstack1 = cstack4; cstack0 = cstack3;
        // New stack: 5
        // POP2; Stack: 5
        ;
        // New stack: 3
        // INVOKESPECIAL java/lang/BootstrapMethodError.<init>(Ljava/lang/Throwable;)V; Stack: 3
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[11]), ((char *)(string_pool + 665LL)), ((char *)(string_pool + 672LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 697LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[11]), (cmethods[4]), cstack2.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 1
        // LABEL L15; Stack: 1
        L15: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/Object]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal3.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 720LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 0
        // LABEL L14; Stack: 0
        L14: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 0
        // FRAME FULL L: [[Ljava/lang/Object;, 4, [B] S: [java/lang/String, 4, 4, java/lang/invoke/MethodHandle]; Stack: 0
        refs.erase(cstack0.l); refs.erase(cstack5.l); 
        refs.erase(clocal0.l); refs.erase(clocal3.l); 
        utils::clear_refs(env, refs);
        // New stack: 6
        // INVOKESTATIC native/magic/1/invoke/obfuscator0.127771650959966.a(Ljava/lang/Object;JJLjava/lang/invoke/MethodHandle;)Ljava/security/KeyFactory;; Stack: 6
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[6]) { cmethods[6] = env->GetStaticMethodID((cclasses[12]), ((char *)(string_pool + 922LL)), ((char *)(string_pool + 937LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack0.l = env->CallStaticObjectMethod((cclasses[12]), (cmethods[6]), cstack0.l, cstack1.j, cstack3.j, cstack5.l); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 1
        // CHECKCAST java/security/KeyFactory; Stack: 1
        if (!cclasses[15] || env->IsSameObject(cclasses[15], NULL)) { cclasses_mtx[15].lock(); if (!cclasses[15] || env->IsSameObject(cclasses[15], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[19]))) { cclasses[15] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[15].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack0.l != nullptr && !env->IsInstanceOf(cstack0.l, (cclasses[15]))) { utils::throw_re(env, ((char *)(string_pool + 133LL)), (std::string(((char *)(string_pool + 162LL))) + std::string(((char *)(string_pool + 30294LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } 
        // New stack: 1
        // ASTORE 4; Stack: 1
        clocal4.l = cstack0.l; refs.insert(cstack0.l);
        // New stack: 0
        // ALOAD 4; Stack: 0
        cstack0.l = clocal4.l; refs.insert(cstack0.l);
        // New stack: 1
        // NEW java/security/spec/X509EncodedKeySpec; Stack: 1
        if (!cclasses[16] || env->IsSameObject(cclasses[16], NULL)) { cclasses_mtx[16].lock(); if (!cclasses[16] || env->IsSameObject(cclasses[16], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[20]))) { cclasses[16] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[16].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (jobject obj = env->AllocObject((cclasses[16]))) { cstack1.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 2
        // DUP; Stack: 2
        cstack2 = cstack1;
        // New stack: 3
        // ALOAD 3; Stack: 3
        cstack3.l = clocal3.l; refs.insert(cstack3.l);
        // New stack: 4
        // INVOKESPECIAL java/security/spec/X509EncodedKeySpec.<init>([B)V; Stack: 4
        if (!cclasses[16] || env->IsSameObject(cclasses[16], NULL)) { cclasses_mtx[16].lock(); if (!cclasses[16] || env->IsSameObject(cclasses[16], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[20]))) { cclasses[16] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[16].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[7]) { cmethods[7] = env->GetMethodID((cclasses[16]), ((char *)(string_pool + 665LL)), ((char *)(string_pool + 5455LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack2.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 697LL)), -1); else env->CallNonvirtualVoidMethod(cstack2.l, (cclasses[16]), (cmethods[7]), cstack3.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 2
        // LDC 6108960919172349970; Stack: 2
        cstack2.j = 6108960919172349970LL;
        // New stack: 4
        // LLOAD 1; Stack: 4
        cstack4.j = clocal1.j;
        // New stack: 6
        // LABEL L1; Stack: 6
        L1: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 6
        // ICONST_1; Stack: 6
        cstack6.i = 1;
        // New stack: 7
        // ANEWARRAY java/lang/Object; Stack: 7
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } if (cstack6.i < 0) utils::throw_re(env, ((char *)(string_pool + 251LL)), ((char *)(string_pool + 288LL)), -1); else { cstack6.l = env->NewObjectArray(cstack6.i, (cclasses[5]), nullptr); refs.insert(cstack6.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 7
        // DUP; Stack: 7
        cstack7 = cstack6;
        // New stack: 8
        // LDC Ldev/mahiro/client/Mahiro_uP;; Stack: 8
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } cstack8.l = (cclasses[6]);
        // New stack: 9
        // SWAP; Stack: 9
        std::swap(cstack8, cstack7);
        // New stack: 9
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.127771650959966.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 9
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } cstack9.l = lookup;
        // New stack: 10
        // LDC Ldev/mahiro/client/Mahiro_yh;; Stack: 10
        if (!cclasses[14] || env->IsSameObject(cclasses[14], NULL)) { cclasses_mtx[14].lock(); if (!cclasses[14] || env->IsSameObject(cclasses[14], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[16]))) { cclasses[14] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[14].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } cstack10.l = (cclasses[14]);
        // New stack: 11
        // LDC a; Stack: 11
        cstack11.l = (cstrings[5]);
        // New stack: 12
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;; Stack: 12
        cstack12.l = (cstrings[6]);
        // New stack: 13
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.127771650959966.a()Ljava/lang/ClassLoader;; Stack: 13
        cstack13.l = classloader;
        // New stack: 14
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 14
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[7]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } if (!cmethods[1]) { cmethods[1] = env->GetStaticMethodID((cclasses[7]), ((char *)(string_pool + 313LL)), ((char *)(string_pool + 340LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }  } cstack12.l = env->CallStaticObjectMethod((cclasses[7]), (cmethods[1]), cstack12.l, cstack13.l); refs.insert(cstack12.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 13
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 13
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } if (!cmethods[2]) { cmethods[2] = env->GetMethodID((cclasses[8]), ((char *)(string_pool + 413LL)), ((char *)(string_pool + 424LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }  } if (cstack9.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 522LL)), -1); else { cstack9.l = env->CallObjectMethod(cstack9.l, (cmethods[2]), cstack10.l, cstack11.l, cstack12.l); refs.insert(cstack9.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 10
        // SWAP; Stack: 10
        std::swap(cstack9, cstack8);
        // New stack: 10
        // LDC v; Stack: 10
        cstack10.l = (cstrings[21]);
        // New stack: 11
        // SWAP; Stack: 11
        std::swap(cstack10, cstack9);
        // New stack: 11
        // LDC (Ljava/lang/Object;Ljava/lang/Object;JJ)Ljava/security/PublicKey;; Stack: 11
        cstack11.l = (cstrings[22]);
        // New stack: 12
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.127771650959966.a()Ljava/lang/ClassLoader;; Stack: 12
        cstack12.l = classloader;
        // New stack: 13
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 13
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[7]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } if (!cmethods[1]) { cmethods[1] = env->GetStaticMethodID((cclasses[7]), ((char *)(string_pool + 313LL)), ((char *)(string_pool + 340LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }  } cstack11.l = env->CallStaticObjectMethod((cclasses[7]), (cmethods[1]), cstack11.l, cstack12.l); refs.insert(cstack11.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 12
        // SWAP; Stack: 12
        std::swap(cstack11, cstack10);
        // New stack: 12
        // LDC 0; Stack: 12
        cstack12.i = 0;
        // New stack: 13
        // ANEWARRAY java/lang/Object; Stack: 13
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } if (cstack12.i < 0) utils::throw_re(env, ((char *)(string_pool + 251LL)), ((char *)(string_pool + 288LL)), -1); else { cstack12.l = env->NewObjectArray(cstack12.i, (cclasses[5]), nullptr); refs.insert(cstack12.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 13
        // CHECKCAST java/lang/Object; Stack: 13
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } if (cstack12.l != nullptr && !env->IsInstanceOf(cstack12.l, (cclasses[5]))) { utils::throw_re(env, ((char *)(string_pool + 133LL)), (std::string(((char *)(string_pool + 162LL))) + std::string(((char *)(string_pool + 547LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } 
        // New stack: 13
        // SWAP; Stack: 13
        std::swap(cstack12, cstack11);
        // New stack: 13
        // INVOKESTATIC native/magic/1/linkcallsite/obfuscator0.127771650959966.a(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/invoke/MemberName;; Stack: 13
        cstack7.l = utils::link_call_site(env, cstack7.l, cstack8.l, cstack9.l, cstack10.l, cstack11.l, cstack12.l); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 8
        // POP; Stack: 8
        ;
        // New stack: 7
        // ICONST_0; Stack: 7
        cstack7.i = 0;
        // New stack: 8
        // AALOAD; Stack: 8
        if (cstack6.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 122LL)), -1); else { cstack6.l = env->GetObjectArrayElement((jobjectArray) cstack6.l, cstack7.i); refs.insert(cstack6.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 7
        // DUP; Stack: 7
        cstack7 = cstack6;
        // New stack: 8
        // INSTANCEOF java/lang/invoke/CallSite; Stack: 8
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[11]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } cstack7.i = cstack7.l == nullptr ? false : env->IsInstanceOf(cstack7.l, (cclasses[9]));
        // New stack: 8
        // IFEQ L16; Stack: 8
        if (cstack7.i == 0) goto L16;
        // New stack: 7
        // INVOKEINTERFACE java/lang/invoke/CallSite.getTarget()Ljava/lang/invoke/MethodHandle;; Stack: 7
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[11]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } if (!cmethods[3]) { cmethods[3] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 564LL)), ((char *)(string_pool + 574LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }  } if (cstack6.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 608LL)), -1); else { cstack6.l = env->CallObjectMethod(cstack6.l, (cmethods[3])); refs.insert(cstack6.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 7
        // LABEL L16; Stack: 7
        L16: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 7
        // FRAME FULL L: [[Ljava/lang/Object;, 4, [B, java/security/KeyFactory] S: [java/security/KeyFactory, java/security/spec/X509EncodedKeySpec, 4, 4, java/lang/Object]; Stack: 7
        refs.erase(cstack0.l); refs.erase(cstack1.l); refs.erase(cstack6.l); 
        refs.erase(clocal0.l); refs.erase(clocal3.l); refs.erase(clocal4.l); 
        utils::clear_refs(env, refs);
        // New stack: 7
        // CHECKCAST java/lang/invoke/MethodHandle; Stack: 7
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } if (cstack6.l != nullptr && !env->IsInstanceOf(cstack6.l, (cclasses[10]))) { utils::throw_re(env, ((char *)(string_pool + 133LL)), (std::string(((char *)(string_pool + 162LL))) + std::string(((char *)(string_pool + 635LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } 
        // New stack: 7
        // GOTO L17; Stack: 7
        goto L17;
        // New stack: 7
        // LABEL L2; Stack: 7
        L2: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 7
        // FRAME SAME1 L: null S: [java/lang/Throwable]; Stack: 7
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal3.l); refs.erase(clocal4.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // INSTANCEOF java/lang/BootstrapMethodError; Stack: 2
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack1.i = cstack1.l == nullptr ? false : env->IsInstanceOf(cstack1.l, (cclasses[11]));
        // New stack: 2
        // IFNE L18; Stack: 2
        if (cstack1.i != 0) goto L18;
        // New stack: 1
        // NEW java/lang/BootstrapMethodError; Stack: 1
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (jobject obj = env->AllocObject((cclasses[11]))) { cstack1.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 2
        // DUP; Stack: 2
        cstack2 = cstack1;
        // New stack: 3
        // DUP2_X1; Stack: 3
        cstack3 = cstack1; cstack4 = cstack2; cstack2 = cstack0; cstack1 = cstack4; cstack0 = cstack3;
        // New stack: 5
        // POP2; Stack: 5
        ;
        // New stack: 3
        // INVOKESPECIAL java/lang/BootstrapMethodError.<init>(Ljava/lang/Throwable;)V; Stack: 3
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[11]), ((char *)(string_pool + 665LL)), ((char *)(string_pool + 672LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 697LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[11]), (cmethods[4]), cstack2.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 1
        // LABEL L18; Stack: 1
        L18: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/Object]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal3.l); refs.erase(clocal4.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 720LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 0
        // LABEL L17; Stack: 0
        L17: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 0
        // FRAME FULL L: [[Ljava/lang/Object;, 4, [B, java/security/KeyFactory] S: [java/security/KeyFactory, java/security/spec/X509EncodedKeySpec, 4, 4, java/lang/invoke/MethodHandle]; Stack: 0
        refs.erase(cstack0.l); refs.erase(cstack1.l); refs.erase(cstack6.l); 
        refs.erase(clocal0.l); refs.erase(clocal3.l); refs.erase(clocal4.l); 
        utils::clear_refs(env, refs);
        // New stack: 7
        // INVOKESTATIC native/magic/1/invoke/obfuscator0.127771650959966.a(Ljava/lang/Object;Ljava/lang/Object;JJLjava/lang/invoke/MethodHandle;)Ljava/security/PublicKey;; Stack: 7
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[8]) { cmethods[8] = env->GetStaticMethodID((cclasses[12]), ((char *)(string_pool + 1119LL)), ((char *)(string_pool + 1134LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack0.l = env->CallStaticObjectMethod((cclasses[12]), (cmethods[8]), cstack0.l, cstack1.l, cstack2.j, cstack4.j, cstack6.l); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 1
        // CHECKCAST java/security/PublicKey; Stack: 1
        if (!cclasses[17] || env->IsSameObject(cclasses[17], NULL)) { cclasses_mtx[17].lock(); if (!cclasses[17] || env->IsSameObject(cclasses[17], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[23]))) { cclasses[17] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[17].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack0.l != nullptr && !env->IsInstanceOf(cstack0.l, (cclasses[17]))) { utils::throw_re(env, ((char *)(string_pool + 133LL)), (std::string(((char *)(string_pool + 162LL))) + std::string(((char *)(string_pool + 3267LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } 
        // New stack: 1
        // LABEL L8; Stack: 1
        L8: if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // ARETURN; Stack: 1
        return (jobject) cstack0.l;
        // New stack: 0
        // LABEL L9; Stack: 0
        L9: if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 0
        // FRAME FULL L: [[Ljava/lang/Object;, 4, [B] S: [java/lang/Exception]; Stack: 0
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal3.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ASTORE 4; Stack: 1
        clocal4.l = cstack0.l; refs.insert(cstack0.l);
        // New stack: 0
        // NEW java/lang/IllegalStateException; Stack: 0
        if (!cclasses[18] || env->IsSameObject(cclasses[18], NULL)) { cclasses_mtx[18].lock(); if (!cclasses[18] || env->IsSameObject(cclasses[18], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[24]))) { cclasses[18] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[18].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (jobject obj = env->AllocObject((cclasses[18]))) { cstack0.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // ALOAD 4; Stack: 2
        cstack2.l = clocal4.l; refs.insert(cstack2.l);
        // New stack: 3
        // INVOKESPECIAL java/lang/IllegalStateException.<init>(Ljava/lang/Throwable;)V; Stack: 3
        if (!cclasses[18] || env->IsSameObject(cclasses[18], NULL)) { cclasses_mtx[18].lock(); if (!cclasses[18] || env->IsSameObject(cclasses[18], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[24]))) { cclasses[18] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[18].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[9]) { cmethods[9] = env->GetMethodID((cclasses[18]), ((char *)(string_pool + 665LL)), ((char *)(string_pool + 672LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 697LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[18]), (cmethods[9]), cstack2.l); 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 720LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 0
        return (jobject) 0;
        L_CATCH_0: if (env->IsInstanceOf(cstack0.l, (cclasses[0]))) { goto L9; }
        env->Throw((jthrowable) cstack0.l); return (jobject) 0;
        L_CATCH_3: if (env->IsInstanceOf(cstack0.l, (cclasses[1]))) { goto L2; }
        goto L_CATCH_0;
        L_CATCH_2: if (env->IsInstanceOf(cstack0.l, (cclasses[1]))) { goto L4; }
        goto L_CATCH_0;
        L_CATCH_1: if (env->IsInstanceOf(cstack0.l, (cclasses[1]))) { goto L6; }
        goto L_CATCH_0;
    }
    
    // Mahiro_R([Ljava/lang/Object;)Z
    jboolean JNICALL __ngen_native_Mahiro_R2(JNIEnv *env, jclass clazz, jarray arg0) {
        jobject classloader = utils::get_classloader_from_class(env, clazz);
        if (env->ExceptionCheck()) { return (jboolean) 0; }
        if (classloader == nullptr) { env->FatalError(((char *)(string_pool + 71LL))); return (jboolean) 0; }
    
        jobject lookup = nullptr;
        // try-catch-class java/lang/Exception
        if (!cclasses[0] || env->IsSameObject(cclasses[0], NULL)) { cclasses_mtx[0].lock(); if (!cclasses[0] || env->IsSameObject(cclasses[0], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[0]))) { cclasses[0] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[0].unlock(); if (env->ExceptionCheck()) { return (jboolean) 0; } }
        // try-catch-class java/lang/Throwable
        if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { cclasses_mtx[1].lock(); if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[1]))) { cclasses[1] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[1].unlock(); if (env->ExceptionCheck()) { return (jboolean) 0; } }
        jvalue cstack0 = {}, cstack1 = {}, cstack2 = {}, cstack3 = {}, cstack4 = {}, cstack5 = {}, cstack6 = {}, cstack7 = {}, cstack8 = {}, cstack9 = {}, cstack10 = {}, cstack11 = {}, cstack12 = {}, cstack13 = {};
        jvalue clocal0 = {}, clocal1 = {}, clocal2 = {}, clocal3 = {}, clocal4 = {}, clocal5 = {}, clocal6 = {};
        std::unordered_set<jobject> refs;
    
        clocal0.l = arg0; refs.insert(clocal0.l);
    
        // ALOAD 0; Stack: 0
        cstack0.l = clocal0.l; refs.insert(cstack0.l);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // ICONST_0; Stack: 2
        cstack2.i = 0;
        // New stack: 3
        // AALOAD; Stack: 3
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 122LL)), -1); else { cstack1.l = env->GetObjectArrayElement((jobjectArray) cstack1.l, cstack2.i); refs.insert(cstack1.l); } 
        if (env->ExceptionCheck()) { return (jboolean) 0; }
        // New stack: 2
        // CHECKCAST java/security/PublicKey; Stack: 2
        if (!cclasses[17] || env->IsSameObject(cclasses[17], NULL)) { cclasses_mtx[17].lock(); if (!cclasses[17] || env->IsSameObject(cclasses[17], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[23]))) { cclasses[17] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[17].unlock(); if (env->ExceptionCheck()) { return (jboolean) 0; } } if (cstack1.l != nullptr && !env->IsInstanceOf(cstack1.l, (cclasses[17]))) { utils::throw_re(env, ((char *)(string_pool + 133LL)), (std::string(((char *)(string_pool + 162LL))) + std::string(((char *)(string_pool + 3267LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (jboolean) 0; } } 
        // New stack: 2
        // ASTORE 2; Stack: 2
        clocal2.l = cstack1.l; refs.insert(cstack1.l);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // ICONST_1; Stack: 2
        cstack2.i = 1;
        // New stack: 3
        // AALOAD; Stack: 3
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 122LL)), -1); else { cstack1.l = env->GetObjectArrayElement((jobjectArray) cstack1.l, cstack2.i); refs.insert(cstack1.l); } 
        if (env->ExceptionCheck()) { return (jboolean) 0; }
        // New stack: 2
        // CHECKCAST java/lang/Long; Stack: 2
        if (!cclasses[3] || env->IsSameObject(cclasses[3], NULL)) { cclasses_mtx[3].lock(); if (!cclasses[3] || env->IsSameObject(cclasses[3], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[2]))) { cclasses[3] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[3].unlock(); if (env->ExceptionCheck()) { return (jboolean) 0; } } if (cstack1.l != nullptr && !env->IsInstanceOf(cstack1.l, (cclasses[3]))) { utils::throw_re(env, ((char *)(string_pool + 133LL)), (std::string(((char *)(string_pool + 162LL))) + std::string(((char *)(string_pool + 195LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (jboolean) 0; } } 
        // New stack: 2
        // INVOKEVIRTUAL java/lang/Long.longValue()J; Stack: 2
        if (!cclasses[3] || env->IsSameObject(cclasses[3], NULL)) { cclasses_mtx[3].lock(); if (!cclasses[3] || env->IsSameObject(cclasses[3], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[2]))) { cclasses[3] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[3].unlock(); if (env->ExceptionCheck()) { return (jboolean) 0; } } if (!cmethods[0]) { cmethods[0] = env->GetMethodID((cclasses[3]), ((char *)(string_pool + 210LL)), ((char *)(string_pool + 220LL))); if (env->ExceptionCheck()) { return (jboolean) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 224LL)), -1); else cstack1.j = env->CallLongMethod(cstack1.l, (cmethods[0])); 
        if (env->ExceptionCheck()) { return (jboolean) 0; }
        // New stack: 3
        // LSTORE 4; Stack: 3
        clocal4.j = cstack1.j;
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // ICONST_2; Stack: 2
        cstack2.i = 2;
        // New stack: 3
        // AALOAD; Stack: 3
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 122LL)), -1); else { cstack1.l = env->GetObjectArrayElement((jobjectArray) cstack1.l, cstack2.i); refs.insert(cstack1.l); } 
        if (env->ExceptionCheck()) { return (jboolean) 0; }
        // New stack: 2
        // CHECKCAST [B; Stack: 2
        if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { cclasses_mtx[2].lock(); if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { if (jclass clazz = env->FindClass(((char *)(string_pool + 2920LL)))) { cclasses[2] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[2].unlock(); if (env->ExceptionCheck()) { return (jboolean) 0; } } if (cstack1.l != nullptr && !env->IsInstanceOf(cstack1.l, (cclasses[2]))) { utils::throw_re(env, ((char *)(string_pool + 133LL)), (std::string(((char *)(string_pool + 162LL))) + std::string(((char *)(string_pool + 2920LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (jboolean) 0; } } 
        // New stack: 2
        // ASTORE 3; Stack: 2
        clocal3.l = cstack1.l; refs.insert(cstack1.l);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // ICONST_3; Stack: 2
        cstack2.i = 3;
        // New stack: 3
        // AALOAD; Stack: 3
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 122LL)), -1); else { cstack1.l = env->GetObjectArrayElement((jobjectArray) cstack1.l, cstack2.i); refs.insert(cstack1.l); } 
        if (env->ExceptionCheck()) { return (jboolean) 0; }
        // New stack: 2
        // CHECKCAST [B; Stack: 2
        if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { cclasses_mtx[2].lock(); if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { if (jclass clazz = env->FindClass(((char *)(string_pool + 2920LL)))) { cclasses[2] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[2].unlock(); if (env->ExceptionCheck()) { return (jboolean) 0; } } if (cstack1.l != nullptr && !env->IsInstanceOf(cstack1.l, (cclasses[2]))) { utils::throw_re(env, ((char *)(string_pool + 133LL)), (std::string(((char *)(string_pool + 162LL))) + std::string(((char *)(string_pool + 2920LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (jboolean) 0; } } 
        // New stack: 2
        // ASTORE 1; Stack: 2
        clocal1.l = cstack1.l; refs.insert(cstack1.l);
        // New stack: 1
        // POP; Stack: 1
        ;
        // New stack: 0
        // GETSTATIC dev/mahiro/client/Mahiro_uP.a J; Stack: 0
        if (!cclasses[4]  || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { return (jboolean) 0; } } if (!cfields[0]) { cfields[0] = env->GetStaticFieldID((cclasses[4]), ((char *)(string_pool + 247LL)), ((char *)(string_pool + 249LL))); if (env->ExceptionCheck()) { return (jboolean) 0; }  } cstack0.j = env->GetStaticLongField((cclasses[4]), (cfields[0])); 
        if (env->ExceptionCheck()) { return (jboolean) 0; }
        // New stack: 2
        // LLOAD 4; Stack: 2
        cstack2.j = clocal4.j;
        // New stack: 4
        // LXOR; Stack: 4
        cstack0.j = cstack0.j ^ cstack2.j;
        // New stack: 2
        // LSTORE 4; Stack: 2
        clocal4.j = cstack0.j;
        // New stack: 0
        // LABEL L11; Stack: 0
        L11: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 0
        // SIPUSH 1114; Stack: 0
        cstack0.i = (jint) 1114;
        // New stack: 1
        // LDC 2434705168393367442; Stack: 1
        cstack1.j = 2434705168393367442LL;
        // New stack: 3
        // LLOAD 4; Stack: 3
        cstack3.j = clocal4.j;
        // New stack: 5
        // LXOR; Stack: 5
        cstack1.j = cstack1.j ^ cstack3.j;
        // New stack: 3
        // LABEL L9; Stack: 3
        L9: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 3
        // ICONST_1; Stack: 3
        cstack3.i = 1;
        // New stack: 4
        // ANEWARRAY java/lang/Object; Stack: 4
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (cstack3.i < 0) utils::throw_re(env, ((char *)(string_pool + 251LL)), ((char *)(string_pool + 288LL)), -1); else { cstack3.l = env->NewObjectArray(cstack3.i, (cclasses[5]), nullptr); refs.insert(cstack3.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 4
        // DUP; Stack: 4
        cstack4 = cstack3;
        // New stack: 5
        // LDC Ldev/mahiro/client/Mahiro_uP;; Stack: 5
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } cstack5.l = (cclasses[6]);
        // New stack: 6
        // SWAP; Stack: 6
        std::swap(cstack5, cstack4);
        // New stack: 6
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.127771650959966.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 6
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } cstack6.l = lookup;
        // New stack: 7
        // LDC Ldev/mahiro/client/Mahiro_uP;; Stack: 7
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } cstack7.l = (cclasses[6]);
        // New stack: 8
        // LDC a; Stack: 8
        cstack8.l = (cstrings[5]);
        // New stack: 9
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;; Stack: 9
        cstack9.l = (cstrings[6]);
        // New stack: 10
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.127771650959966.a()Ljava/lang/ClassLoader;; Stack: 10
        cstack10.l = classloader;
        // New stack: 11
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 11
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[7]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (!cmethods[1]) { cmethods[1] = env->GetStaticMethodID((cclasses[7]), ((char *)(string_pool + 313LL)), ((char *)(string_pool + 340LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }  } cstack9.l = env->CallStaticObjectMethod((cclasses[7]), (cmethods[1]), cstack9.l, cstack10.l); refs.insert(cstack9.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 10
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 10
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (!cmethods[2]) { cmethods[2] = env->GetMethodID((cclasses[8]), ((char *)(string_pool + 413LL)), ((char *)(string_pool + 424LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }  } if (cstack6.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 522LL)), -1); else { cstack6.l = env->CallObjectMethod(cstack6.l, (cmethods[2]), cstack7.l, cstack8.l, cstack9.l); refs.insert(cstack6.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 7
        // SWAP; Stack: 7
        std::swap(cstack6, cstack5);
        // New stack: 7
        // LDC h; Stack: 7
        cstack7.l = (cstrings[9]);
        // New stack: 8
        // SWAP; Stack: 8
        std::swap(cstack7, cstack6);
        // New stack: 8
        // LDC (IJ)Ljava/lang/String;; Stack: 8
        cstack8.l = (cstrings[10]);
        // New stack: 9
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.127771650959966.a()Ljava/lang/ClassLoader;; Stack: 9
        cstack9.l = classloader;
        // New stack: 10
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 10
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[7]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (!cmethods[1]) { cmethods[1] = env->GetStaticMethodID((cclasses[7]), ((char *)(string_pool + 313LL)), ((char *)(string_pool + 340LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }  } cstack8.l = env->CallStaticObjectMethod((cclasses[7]), (cmethods[1]), cstack8.l, cstack9.l); refs.insert(cstack8.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 9
        // SWAP; Stack: 9
        std::swap(cstack8, cstack7);
        // New stack: 9
        // LDC 0; Stack: 9
        cstack9.i = 0;
        // New stack: 10
        // ANEWARRAY java/lang/Object; Stack: 10
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (cstack9.i < 0) utils::throw_re(env, ((char *)(string_pool + 251LL)), ((char *)(string_pool + 288LL)), -1); else { cstack9.l = env->NewObjectArray(cstack9.i, (cclasses[5]), nullptr); refs.insert(cstack9.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 10
        // CHECKCAST java/lang/Object; Stack: 10
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (cstack9.l != nullptr && !env->IsInstanceOf(cstack9.l, (cclasses[5]))) { utils::throw_re(env, ((char *)(string_pool + 133LL)), (std::string(((char *)(string_pool + 162LL))) + std::string(((char *)(string_pool + 547LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } 
        // New stack: 10
        // SWAP; Stack: 10
        std::swap(cstack9, cstack8);
        // New stack: 10
        // INVOKESTATIC native/magic/1/linkcallsite/obfuscator0.127771650959966.a(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/invoke/MemberName;; Stack: 10
        cstack4.l = utils::link_call_site(env, cstack4.l, cstack5.l, cstack6.l, cstack7.l, cstack8.l, cstack9.l); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 5
        // POP; Stack: 5
        ;
        // New stack: 4
        // ICONST_0; Stack: 4
        cstack4.i = 0;
        // New stack: 5
        // AALOAD; Stack: 5
        if (cstack3.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 122LL)), -1); else { cstack3.l = env->GetObjectArrayElement((jobjectArray) cstack3.l, cstack4.i); refs.insert(cstack3.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 4
        // DUP; Stack: 4
        cstack4 = cstack3;
        // New stack: 5
        // INSTANCEOF java/lang/invoke/CallSite; Stack: 5
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[11]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } cstack4.i = cstack4.l == nullptr ? false : env->IsInstanceOf(cstack4.l, (cclasses[9]));
        // New stack: 5
        // IFEQ L14; Stack: 5
        if (cstack4.i == 0) goto L14;
        // New stack: 4
        // INVOKEINTERFACE java/lang/invoke/CallSite.getTarget()Ljava/lang/invoke/MethodHandle;; Stack: 4
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[11]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (!cmethods[3]) { cmethods[3] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 564LL)), ((char *)(string_pool + 574LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }  } if (cstack3.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 608LL)), -1); else { cstack3.l = env->CallObjectMethod(cstack3.l, (cmethods[3])); refs.insert(cstack3.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 4
        // LABEL L14; Stack: 4
        L14: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 4
        // FRAME FULL L: [[Ljava/lang/Object;, [B, java/security/PublicKey, [B, 4] S: [1, 4, java/lang/Object]; Stack: 4
        refs.erase(cstack3.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal3.l); 
        utils::clear_refs(env, refs);
        // New stack: 4
        // CHECKCAST java/lang/invoke/MethodHandle; Stack: 4
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (cstack3.l != nullptr && !env->IsInstanceOf(cstack3.l, (cclasses[10]))) { utils::throw_re(env, ((char *)(string_pool + 133LL)), (std::string(((char *)(string_pool + 162LL))) + std::string(((char *)(string_pool + 635LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } 
        // New stack: 4
        // GOTO L15; Stack: 4
        goto L15;
        // New stack: 4
        // LABEL L10; Stack: 4
        L10: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 4
        // FRAME SAME1 L: null S: [java/lang/Throwable]; Stack: 4
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal3.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // INSTANCEOF java/lang/BootstrapMethodError; Stack: 2
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack1.i = cstack1.l == nullptr ? false : env->IsInstanceOf(cstack1.l, (cclasses[11]));
        // New stack: 2
        // IFNE L16; Stack: 2
        if (cstack1.i != 0) goto L16;
        // New stack: 1
        // NEW java/lang/BootstrapMethodError; Stack: 1
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (jobject obj = env->AllocObject((cclasses[11]))) { cstack1.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 2
        // DUP; Stack: 2
        cstack2 = cstack1;
        // New stack: 3
        // DUP2_X1; Stack: 3
        cstack3 = cstack1; cstack4 = cstack2; cstack2 = cstack0; cstack1 = cstack4; cstack0 = cstack3;
        // New stack: 5
        // POP2; Stack: 5
        ;
        // New stack: 3
        // INVOKESPECIAL java/lang/BootstrapMethodError.<init>(Ljava/lang/Throwable;)V; Stack: 3
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[11]), ((char *)(string_pool + 665LL)), ((char *)(string_pool + 672LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 697LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[11]), (cmethods[4]), cstack2.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 1
        // LABEL L16; Stack: 1
        L16: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/Object]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal3.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 720LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 0
        // LABEL L15; Stack: 0
        L15: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 0
        // FRAME FULL L: [[Ljava/lang/Object;, [B, java/security/PublicKey, [B, 4] S: [1, 4, java/lang/invoke/MethodHandle]; Stack: 0
        refs.erase(cstack3.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal3.l); 
        utils::clear_refs(env, refs);
        // New stack: 4
        // INVOKESTATIC native/magic/1/invoke/obfuscator0.127771650959966.a(IJLjava/lang/invoke/MethodHandle;)Ljava/lang/String;; Stack: 4
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[5]) { cmethods[5] = env->GetStaticMethodID((cclasses[12]), ((char *)(string_pool + 853LL)), ((char *)(string_pool + 868LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack0.l = env->CallStaticObjectMethod((cclasses[12]), (cmethods[5]), cstack0.i, cstack1.j, cstack3.l); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 1
        // CHECKCAST java/lang/String; Stack: 1
        if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { cclasses_mtx[13].lock(); if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[13] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[13].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack0.l != nullptr && !env->IsInstanceOf(cstack0.l, (cclasses[13]))) { utils::throw_re(env, ((char *)(string_pool + 133LL)), (std::string(((char *)(string_pool + 162LL))) + std::string(((char *)(string_pool + 178LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } 
        // New stack: 1
        // LDC 1929585820316039728; Stack: 1
        cstack1.j = 1929585820316039728LL;
        // New stack: 3
        // LLOAD 4; Stack: 3
        cstack3.j = clocal4.j;
        // New stack: 5
        // LABEL L7; Stack: 5
        L7: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 5
        // ICONST_1; Stack: 5
        cstack5.i = 1;
        // New stack: 6
        // ANEWARRAY java/lang/Object; Stack: 6
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } if (cstack5.i < 0) utils::throw_re(env, ((char *)(string_pool + 251LL)), ((char *)(string_pool + 288LL)), -1); else { cstack5.l = env->NewObjectArray(cstack5.i, (cclasses[5]), nullptr); refs.insert(cstack5.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 6
        // DUP; Stack: 6
        cstack6 = cstack5;
        // New stack: 7
        // LDC Ldev/mahiro/client/Mahiro_uP;; Stack: 7
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } cstack7.l = (cclasses[6]);
        // New stack: 8
        // SWAP; Stack: 8
        std::swap(cstack7, cstack6);
        // New stack: 8
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.127771650959966.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 8
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } cstack8.l = lookup;
        // New stack: 9
        // LDC Ldev/mahiro/client/Mahiro_yh;; Stack: 9
        if (!cclasses[14] || env->IsSameObject(cclasses[14], NULL)) { cclasses_mtx[14].lock(); if (!cclasses[14] || env->IsSameObject(cclasses[14], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[16]))) { cclasses[14] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[14].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } cstack9.l = (cclasses[14]);
        // New stack: 10
        // LDC a; Stack: 10
        cstack10.l = (cstrings[5]);
        // New stack: 11
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;; Stack: 11
        cstack11.l = (cstrings[6]);
        // New stack: 12
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.127771650959966.a()Ljava/lang/ClassLoader;; Stack: 12
        cstack12.l = classloader;
        // New stack: 13
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 13
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[7]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } if (!cmethods[1]) { cmethods[1] = env->GetStaticMethodID((cclasses[7]), ((char *)(string_pool + 313LL)), ((char *)(string_pool + 340LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }  } cstack11.l = env->CallStaticObjectMethod((cclasses[7]), (cmethods[1]), cstack11.l, cstack12.l); refs.insert(cstack11.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 12
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 12
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } if (!cmethods[2]) { cmethods[2] = env->GetMethodID((cclasses[8]), ((char *)(string_pool + 413LL)), ((char *)(string_pool + 424LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }  } if (cstack8.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 522LL)), -1); else { cstack8.l = env->CallObjectMethod(cstack8.l, (cmethods[2]), cstack9.l, cstack10.l, cstack11.l); refs.insert(cstack8.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 9
        // SWAP; Stack: 9
        std::swap(cstack8, cstack7);
        // New stack: 9
        // LDC \u00c9; Stack: 9
        cstack9.l = (cstrings[17]);
        // New stack: 10
        // SWAP; Stack: 10
        std::swap(cstack9, cstack8);
        // New stack: 10
        // LDC (Ljava/lang/Object;JJ)Ljava/security/Signature;; Stack: 10
        cstack10.l = (cstrings[25]);
        // New stack: 11
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.127771650959966.a()Ljava/lang/ClassLoader;; Stack: 11
        cstack11.l = classloader;
        // New stack: 12
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 12
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[7]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } if (!cmethods[1]) { cmethods[1] = env->GetStaticMethodID((cclasses[7]), ((char *)(string_pool + 313LL)), ((char *)(string_pool + 340LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }  } cstack10.l = env->CallStaticObjectMethod((cclasses[7]), (cmethods[1]), cstack10.l, cstack11.l); refs.insert(cstack10.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 11
        // SWAP; Stack: 11
        std::swap(cstack10, cstack9);
        // New stack: 11
        // LDC 0; Stack: 11
        cstack11.i = 0;
        // New stack: 12
        // ANEWARRAY java/lang/Object; Stack: 12
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } if (cstack11.i < 0) utils::throw_re(env, ((char *)(string_pool + 251LL)), ((char *)(string_pool + 288LL)), -1); else { cstack11.l = env->NewObjectArray(cstack11.i, (cclasses[5]), nullptr); refs.insert(cstack11.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 12
        // CHECKCAST java/lang/Object; Stack: 12
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } if (cstack11.l != nullptr && !env->IsInstanceOf(cstack11.l, (cclasses[5]))) { utils::throw_re(env, ((char *)(string_pool + 133LL)), (std::string(((char *)(string_pool + 162LL))) + std::string(((char *)(string_pool + 547LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } 
        // New stack: 12
        // SWAP; Stack: 12
        std::swap(cstack11, cstack10);
        // New stack: 12
        // INVOKESTATIC native/magic/1/linkcallsite/obfuscator0.127771650959966.a(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/invoke/MemberName;; Stack: 12
        cstack6.l = utils::link_call_site(env, cstack6.l, cstack7.l, cstack8.l, cstack9.l, cstack10.l, cstack11.l); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 7
        // POP; Stack: 7
        ;
        // New stack: 6
        // ICONST_0; Stack: 6
        cstack6.i = 0;
        // New stack: 7
        // AALOAD; Stack: 7
        if (cstack5.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 122LL)), -1); else { cstack5.l = env->GetObjectArrayElement((jobjectArray) cstack5.l, cstack6.i); refs.insert(cstack5.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 6
        // DUP; Stack: 6
        cstack6 = cstack5;
        // New stack: 7
        // INSTANCEOF java/lang/invoke/CallSite; Stack: 7
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[11]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } cstack6.i = cstack6.l == nullptr ? false : env->IsInstanceOf(cstack6.l, (cclasses[9]));
        // New stack: 7
        // IFEQ L17; Stack: 7
        if (cstack6.i == 0) goto L17;
        // New stack: 6
        // INVOKEINTERFACE java/lang/invoke/CallSite.getTarget()Ljava/lang/invoke/MethodHandle;; Stack: 6
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[11]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } if (!cmethods[3]) { cmethods[3] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 564LL)), ((char *)(string_pool + 574LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }  } if (cstack5.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 608LL)), -1); else { cstack5.l = env->CallObjectMethod(cstack5.l, (cmethods[3])); refs.insert(cstack5.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 6
        // LABEL L17; Stack: 6
        L17: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 6
        // FRAME FULL L: [[Ljava/lang/Object;, [B, java/security/PublicKey, [B, 4] S: [java/lang/String, 4, 4, java/lang/Object]; Stack: 6
        refs.erase(cstack0.l); refs.erase(cstack5.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal3.l); 
        utils::clear_refs(env, refs);
        // New stack: 6
        // CHECKCAST java/lang/invoke/MethodHandle; Stack: 6
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } if (cstack5.l != nullptr && !env->IsInstanceOf(cstack5.l, (cclasses[10]))) { utils::throw_re(env, ((char *)(string_pool + 133LL)), (std::string(((char *)(string_pool + 162LL))) + std::string(((char *)(string_pool + 635LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } 
        // New stack: 6
        // GOTO L18; Stack: 6
        goto L18;
        // New stack: 6
        // LABEL L8; Stack: 6
        L8: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 6
        // FRAME SAME1 L: null S: [java/lang/Throwable]; Stack: 6
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal3.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // INSTANCEOF java/lang/BootstrapMethodError; Stack: 2
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack1.i = cstack1.l == nullptr ? false : env->IsInstanceOf(cstack1.l, (cclasses[11]));
        // New stack: 2
        // IFNE L19; Stack: 2
        if (cstack1.i != 0) goto L19;
        // New stack: 1
        // NEW java/lang/BootstrapMethodError; Stack: 1
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (jobject obj = env->AllocObject((cclasses[11]))) { cstack1.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 2
        // DUP; Stack: 2
        cstack2 = cstack1;
        // New stack: 3
        // DUP2_X1; Stack: 3
        cstack3 = cstack1; cstack4 = cstack2; cstack2 = cstack0; cstack1 = cstack4; cstack0 = cstack3;
        // New stack: 5
        // POP2; Stack: 5
        ;
        // New stack: 3
        // INVOKESPECIAL java/lang/BootstrapMethodError.<init>(Ljava/lang/Throwable;)V; Stack: 3
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[11]), ((char *)(string_pool + 665LL)), ((char *)(string_pool + 672LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 697LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[11]), (cmethods[4]), cstack2.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 1
        // LABEL L19; Stack: 1
        L19: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/Object]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal3.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 720LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 0
        // LABEL L18; Stack: 0
        L18: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 0
        // FRAME FULL L: [[Ljava/lang/Object;, [B, java/security/PublicKey, [B, 4] S: [java/lang/String, 4, 4, java/lang/invoke/MethodHandle]; Stack: 0
        refs.erase(cstack0.l); refs.erase(cstack5.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal3.l); 
        utils::clear_refs(env, refs);
        // New stack: 6
        // INVOKESTATIC native/magic/1/invoke/obfuscator0.127771650959966.a(Ljava/lang/Object;JJLjava/lang/invoke/MethodHandle;)Ljava/security/Signature;; Stack: 6
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[6]) { cmethods[6] = env->GetStaticMethodID((cclasses[12]), ((char *)(string_pool + 922LL)), ((char *)(string_pool + 937LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack0.l = env->CallStaticObjectMethod((cclasses[12]), (cmethods[6]), cstack0.l, cstack1.j, cstack3.j, cstack5.l); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 1
        // CHECKCAST java/security/Signature; Stack: 1
        if (!cclasses[19] || env->IsSameObject(cclasses[19], NULL)) { cclasses_mtx[19].lock(); if (!cclasses[19] || env->IsSameObject(cclasses[19], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[26]))) { cclasses[19] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[19].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack0.l != nullptr && !env->IsInstanceOf(cstack0.l, (cclasses[19]))) { utils::throw_re(env, ((char *)(string_pool + 133LL)), (std::string(((char *)(string_pool + 162LL))) + std::string(((char *)(string_pool + 30319LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } 
        // New stack: 1
        // ASTORE 6; Stack: 1
        clocal6.l = cstack0.l; refs.insert(cstack0.l);
        // New stack: 0
        // ALOAD 6; Stack: 0
        cstack0.l = clocal6.l; refs.insert(cstack0.l);
        // New stack: 1
        // ALOAD 2; Stack: 1
        cstack1.l = clocal2.l; refs.insert(cstack1.l);
        // New stack: 2
        // LDC 1931033394413679099; Stack: 2
        cstack2.j = 1931033394413679099LL;
        // New stack: 4
        // LLOAD 4; Stack: 4
        cstack4.j = clocal4.j;
        // New stack: 6
        // LABEL L5; Stack: 6
        L5: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 6
        // ICONST_1; Stack: 6
        cstack6.i = 1;
        // New stack: 7
        // ANEWARRAY java/lang/Object; Stack: 7
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } if (cstack6.i < 0) utils::throw_re(env, ((char *)(string_pool + 251LL)), ((char *)(string_pool + 288LL)), -1); else { cstack6.l = env->NewObjectArray(cstack6.i, (cclasses[5]), nullptr); refs.insert(cstack6.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 7
        // DUP; Stack: 7
        cstack7 = cstack6;
        // New stack: 8
        // LDC Ldev/mahiro/client/Mahiro_uP;; Stack: 8
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } cstack8.l = (cclasses[6]);
        // New stack: 9
        // SWAP; Stack: 9
        std::swap(cstack8, cstack7);
        // New stack: 9
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.127771650959966.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 9
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } cstack9.l = lookup;
        // New stack: 10
        // LDC Ldev/mahiro/client/Mahiro_yh;; Stack: 10
        if (!cclasses[14] || env->IsSameObject(cclasses[14], NULL)) { cclasses_mtx[14].lock(); if (!cclasses[14] || env->IsSameObject(cclasses[14], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[16]))) { cclasses[14] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[14].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } cstack10.l = (cclasses[14]);
        // New stack: 11
        // LDC a; Stack: 11
        cstack11.l = (cstrings[5]);
        // New stack: 12
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;; Stack: 12
        cstack12.l = (cstrings[6]);
        // New stack: 13
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.127771650959966.a()Ljava/lang/ClassLoader;; Stack: 13
        cstack13.l = classloader;
        // New stack: 14
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 14
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[7]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } if (!cmethods[1]) { cmethods[1] = env->GetStaticMethodID((cclasses[7]), ((char *)(string_pool + 313LL)), ((char *)(string_pool + 340LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }  } cstack12.l = env->CallStaticObjectMethod((cclasses[7]), (cmethods[1]), cstack12.l, cstack13.l); refs.insert(cstack12.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 13
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 13
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } if (!cmethods[2]) { cmethods[2] = env->GetMethodID((cclasses[8]), ((char *)(string_pool + 413LL)), ((char *)(string_pool + 424LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }  } if (cstack9.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 522LL)), -1); else { cstack9.l = env->CallObjectMethod(cstack9.l, (cmethods[2]), cstack10.l, cstack11.l, cstack12.l); refs.insert(cstack9.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 10
        // SWAP; Stack: 10
        std::swap(cstack9, cstack8);
        // New stack: 10
        // LDC v; Stack: 10
        cstack10.l = (cstrings[21]);
        // New stack: 11
        // SWAP; Stack: 11
        std::swap(cstack10, cstack9);
        // New stack: 11
        // LDC (Ljava/lang/Object;Ljava/lang/Object;JJ)V; Stack: 11
        cstack11.l = (cstrings[27]);
        // New stack: 12
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.127771650959966.a()Ljava/lang/ClassLoader;; Stack: 12
        cstack12.l = classloader;
        // New stack: 13
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 13
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[7]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } if (!cmethods[1]) { cmethods[1] = env->GetStaticMethodID((cclasses[7]), ((char *)(string_pool + 313LL)), ((char *)(string_pool + 340LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }  } cstack11.l = env->CallStaticObjectMethod((cclasses[7]), (cmethods[1]), cstack11.l, cstack12.l); refs.insert(cstack11.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 12
        // SWAP; Stack: 12
        std::swap(cstack11, cstack10);
        // New stack: 12
        // LDC 0; Stack: 12
        cstack12.i = 0;
        // New stack: 13
        // ANEWARRAY java/lang/Object; Stack: 13
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } if (cstack12.i < 0) utils::throw_re(env, ((char *)(string_pool + 251LL)), ((char *)(string_pool + 288LL)), -1); else { cstack12.l = env->NewObjectArray(cstack12.i, (cclasses[5]), nullptr); refs.insert(cstack12.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 13
        // CHECKCAST java/lang/Object; Stack: 13
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } if (cstack12.l != nullptr && !env->IsInstanceOf(cstack12.l, (cclasses[5]))) { utils::throw_re(env, ((char *)(string_pool + 133LL)), (std::string(((char *)(string_pool + 162LL))) + std::string(((char *)(string_pool + 547LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } 
        // New stack: 13
        // SWAP; Stack: 13
        std::swap(cstack12, cstack11);
        // New stack: 13
        // INVOKESTATIC native/magic/1/linkcallsite/obfuscator0.127771650959966.a(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/invoke/MemberName;; Stack: 13
        cstack7.l = utils::link_call_site(env, cstack7.l, cstack8.l, cstack9.l, cstack10.l, cstack11.l, cstack12.l); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 8
        // POP; Stack: 8
        ;
        // New stack: 7
        // ICONST_0; Stack: 7
        cstack7.i = 0;
        // New stack: 8
        // AALOAD; Stack: 8
        if (cstack6.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 122LL)), -1); else { cstack6.l = env->GetObjectArrayElement((jobjectArray) cstack6.l, cstack7.i); refs.insert(cstack6.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 7
        // DUP; Stack: 7
        cstack7 = cstack6;
        // New stack: 8
        // INSTANCEOF java/lang/invoke/CallSite; Stack: 8
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[11]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } cstack7.i = cstack7.l == nullptr ? false : env->IsInstanceOf(cstack7.l, (cclasses[9]));
        // New stack: 8
        // IFEQ L20; Stack: 8
        if (cstack7.i == 0) goto L20;
        // New stack: 7
        // INVOKEINTERFACE java/lang/invoke/CallSite.getTarget()Ljava/lang/invoke/MethodHandle;; Stack: 7
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[11]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } if (!cmethods[3]) { cmethods[3] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 564LL)), ((char *)(string_pool + 574LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }  } if (cstack6.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 608LL)), -1); else { cstack6.l = env->CallObjectMethod(cstack6.l, (cmethods[3])); refs.insert(cstack6.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 7
        // LABEL L20; Stack: 7
        L20: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 7
        // FRAME FULL L: [[Ljava/lang/Object;, [B, java/security/PublicKey, [B, 4, java/security/Signature] S: [java/security/Signature, java/security/PublicKey, 4, 4, java/lang/Object]; Stack: 7
        refs.erase(cstack0.l); refs.erase(cstack1.l); refs.erase(cstack6.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal3.l); refs.erase(clocal6.l); 
        utils::clear_refs(env, refs);
        // New stack: 7
        // CHECKCAST java/lang/invoke/MethodHandle; Stack: 7
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } if (cstack6.l != nullptr && !env->IsInstanceOf(cstack6.l, (cclasses[10]))) { utils::throw_re(env, ((char *)(string_pool + 133LL)), (std::string(((char *)(string_pool + 162LL))) + std::string(((char *)(string_pool + 635LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } 
        // New stack: 7
        // GOTO L21; Stack: 7
        goto L21;
        // New stack: 7
        // LABEL L6; Stack: 7
        L6: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 7
        // FRAME SAME1 L: null S: [java/lang/Throwable]; Stack: 7
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal3.l); refs.erase(clocal6.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // INSTANCEOF java/lang/BootstrapMethodError; Stack: 2
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack1.i = cstack1.l == nullptr ? false : env->IsInstanceOf(cstack1.l, (cclasses[11]));
        // New stack: 2
        // IFNE L22; Stack: 2
        if (cstack1.i != 0) goto L22;
        // New stack: 1
        // NEW java/lang/BootstrapMethodError; Stack: 1
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (jobject obj = env->AllocObject((cclasses[11]))) { cstack1.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 2
        // DUP; Stack: 2
        cstack2 = cstack1;
        // New stack: 3
        // DUP2_X1; Stack: 3
        cstack3 = cstack1; cstack4 = cstack2; cstack2 = cstack0; cstack1 = cstack4; cstack0 = cstack3;
        // New stack: 5
        // POP2; Stack: 5
        ;
        // New stack: 3
        // INVOKESPECIAL java/lang/BootstrapMethodError.<init>(Ljava/lang/Throwable;)V; Stack: 3
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[11]), ((char *)(string_pool + 665LL)), ((char *)(string_pool + 672LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 697LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[11]), (cmethods[4]), cstack2.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 1
        // LABEL L22; Stack: 1
        L22: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/Object]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal3.l); refs.erase(clocal6.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 720LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 0
        // LABEL L21; Stack: 0
        L21: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 0
        // FRAME FULL L: [[Ljava/lang/Object;, [B, java/security/PublicKey, [B, 4, java/security/Signature] S: [java/security/Signature, java/security/PublicKey, 4, 4, java/lang/invoke/MethodHandle]; Stack: 0
        refs.erase(cstack0.l); refs.erase(cstack1.l); refs.erase(cstack6.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal3.l); refs.erase(clocal6.l); 
        utils::clear_refs(env, refs);
        // New stack: 7
        // INVOKESTATIC native/magic/1/invoke/obfuscator0.127771650959966.a(Ljava/lang/Object;Ljava/lang/Object;JJLjava/lang/invoke/MethodHandle;)V; Stack: 7
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[10]) { cmethods[10] = env->GetStaticMethodID((cclasses[12]), ((char *)(string_pool + 2068LL)), ((char *)(string_pool + 2084LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } env->CallStaticVoidMethod((cclasses[12]), (cmethods[10]), cstack0.l, cstack1.l, cstack2.j, cstack4.j, cstack6.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 0
        // ALOAD 6; Stack: 0
        cstack0.l = clocal6.l; refs.insert(cstack0.l);
        // New stack: 1
        // ALOAD 3; Stack: 1
        cstack1.l = clocal3.l; refs.insert(cstack1.l);
        // New stack: 2
        // LDC 1943225667421675171; Stack: 2
        cstack2.j = 1943225667421675171LL;
        // New stack: 4
        // LLOAD 4; Stack: 4
        cstack4.j = clocal4.j;
        // New stack: 6
        // LABEL L3; Stack: 6
        L3: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }
        // New stack: 6
        // ICONST_1; Stack: 6
        cstack6.i = 1;
        // New stack: 7
        // ANEWARRAY java/lang/Object; Stack: 7
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } if (cstack6.i < 0) utils::throw_re(env, ((char *)(string_pool + 251LL)), ((char *)(string_pool + 288LL)), -1); else { cstack6.l = env->NewObjectArray(cstack6.i, (cclasses[5]), nullptr); refs.insert(cstack6.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }
        // New stack: 7
        // DUP; Stack: 7
        cstack7 = cstack6;
        // New stack: 8
        // LDC Ldev/mahiro/client/Mahiro_uP;; Stack: 8
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } cstack8.l = (cclasses[6]);
        // New stack: 9
        // SWAP; Stack: 9
        std::swap(cstack8, cstack7);
        // New stack: 9
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.127771650959966.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 9
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } cstack9.l = lookup;
        // New stack: 10
        // LDC Ldev/mahiro/client/Mahiro_yh;; Stack: 10
        if (!cclasses[14] || env->IsSameObject(cclasses[14], NULL)) { cclasses_mtx[14].lock(); if (!cclasses[14] || env->IsSameObject(cclasses[14], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[16]))) { cclasses[14] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[14].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } cstack10.l = (cclasses[14]);
        // New stack: 11
        // LDC a; Stack: 11
        cstack11.l = (cstrings[5]);
        // New stack: 12
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;; Stack: 12
        cstack12.l = (cstrings[6]);
        // New stack: 13
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.127771650959966.a()Ljava/lang/ClassLoader;; Stack: 13
        cstack13.l = classloader;
        // New stack: 14
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 14
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[7]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } if (!cmethods[1]) { cmethods[1] = env->GetStaticMethodID((cclasses[7]), ((char *)(string_pool + 313LL)), ((char *)(string_pool + 340LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }  } cstack12.l = env->CallStaticObjectMethod((cclasses[7]), (cmethods[1]), cstack12.l, cstack13.l); refs.insert(cstack12.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }
        // New stack: 13
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 13
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } if (!cmethods[2]) { cmethods[2] = env->GetMethodID((cclasses[8]), ((char *)(string_pool + 413LL)), ((char *)(string_pool + 424LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }  } if (cstack9.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 522LL)), -1); else { cstack9.l = env->CallObjectMethod(cstack9.l, (cmethods[2]), cstack10.l, cstack11.l, cstack12.l); refs.insert(cstack9.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }
        // New stack: 10
        // SWAP; Stack: 10
        std::swap(cstack9, cstack8);
        // New stack: 10
        // LDC v; Stack: 10
        cstack10.l = (cstrings[21]);
        // New stack: 11
        // SWAP; Stack: 11
        std::swap(cstack10, cstack9);
        // New stack: 11
        // LDC (Ljava/lang/Object;Ljava/lang/Object;JJ)V; Stack: 11
        cstack11.l = (cstrings[27]);
        // New stack: 12
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.127771650959966.a()Ljava/lang/ClassLoader;; Stack: 12
        cstack12.l = classloader;
        // New stack: 13
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 13
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[7]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } if (!cmethods[1]) { cmethods[1] = env->GetStaticMethodID((cclasses[7]), ((char *)(string_pool + 313LL)), ((char *)(string_pool + 340LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }  } cstack11.l = env->CallStaticObjectMethod((cclasses[7]), (cmethods[1]), cstack11.l, cstack12.l); refs.insert(cstack11.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }
        // New stack: 12
        // SWAP; Stack: 12
        std::swap(cstack11, cstack10);
        // New stack: 12
        // LDC 0; Stack: 12
        cstack12.i = 0;
        // New stack: 13
        // ANEWARRAY java/lang/Object; Stack: 13
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } if (cstack12.i < 0) utils::throw_re(env, ((char *)(string_pool + 251LL)), ((char *)(string_pool + 288LL)), -1); else { cstack12.l = env->NewObjectArray(cstack12.i, (cclasses[5]), nullptr); refs.insert(cstack12.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }
        // New stack: 13
        // CHECKCAST java/lang/Object; Stack: 13
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } if (cstack12.l != nullptr && !env->IsInstanceOf(cstack12.l, (cclasses[5]))) { utils::throw_re(env, ((char *)(string_pool + 133LL)), (std::string(((char *)(string_pool + 162LL))) + std::string(((char *)(string_pool + 547LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } 
        // New stack: 13
        // SWAP; Stack: 13
        std::swap(cstack12, cstack11);
        // New stack: 13
        // INVOKESTATIC native/magic/1/linkcallsite/obfuscator0.127771650959966.a(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/invoke/MemberName;; Stack: 13
        cstack7.l = utils::link_call_site(env, cstack7.l, cstack8.l, cstack9.l, cstack10.l, cstack11.l, cstack12.l); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }
        // New stack: 8
        // POP; Stack: 8
        ;
        // New stack: 7
        // ICONST_0; Stack: 7
        cstack7.i = 0;
        // New stack: 8
        // AALOAD; Stack: 8
        if (cstack6.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 122LL)), -1); else { cstack6.l = env->GetObjectArrayElement((jobjectArray) cstack6.l, cstack7.i); refs.insert(cstack6.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }
        // New stack: 7
        // DUP; Stack: 7
        cstack7 = cstack6;
        // New stack: 8
        // INSTANCEOF java/lang/invoke/CallSite; Stack: 8
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[11]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } cstack7.i = cstack7.l == nullptr ? false : env->IsInstanceOf(cstack7.l, (cclasses[9]));
        // New stack: 8
        // IFEQ L23; Stack: 8
        if (cstack7.i == 0) goto L23;
        // New stack: 7
        // INVOKEINTERFACE java/lang/invoke/CallSite.getTarget()Ljava/lang/invoke/MethodHandle;; Stack: 7
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[11]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } if (!cmethods[3]) { cmethods[3] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 564LL)), ((char *)(string_pool + 574LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }  } if (cstack6.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 608LL)), -1); else { cstack6.l = env->CallObjectMethod(cstack6.l, (cmethods[3])); refs.insert(cstack6.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }
        // New stack: 7
        // LABEL L23; Stack: 7
        L23: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }
        // New stack: 7
        // FRAME FULL L: [[Ljava/lang/Object;, [B, java/security/PublicKey, [B, 4, java/security/Signature] S: [java/security/Signature, [B, 4, 4, java/lang/Object]; Stack: 7
        refs.erase(cstack0.l); refs.erase(cstack1.l); refs.erase(cstack6.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal3.l); refs.erase(clocal6.l); 
        utils::clear_refs(env, refs);
        // New stack: 7
        // CHECKCAST java/lang/invoke/MethodHandle; Stack: 7
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } if (cstack6.l != nullptr && !env->IsInstanceOf(cstack6.l, (cclasses[10]))) { utils::throw_re(env, ((char *)(string_pool + 133LL)), (std::string(((char *)(string_pool + 162LL))) + std::string(((char *)(string_pool + 635LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } 
        // New stack: 7
        // GOTO L24; Stack: 7
        goto L24;
        // New stack: 7
        // LABEL L4; Stack: 7
        L4: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 7
        // FRAME SAME1 L: null S: [java/lang/Throwable]; Stack: 7
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal3.l); refs.erase(clocal6.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // INSTANCEOF java/lang/BootstrapMethodError; Stack: 2
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack1.i = cstack1.l == nullptr ? false : env->IsInstanceOf(cstack1.l, (cclasses[11]));
        // New stack: 2
        // IFNE L25; Stack: 2
        if (cstack1.i != 0) goto L25;
        // New stack: 1
        // NEW java/lang/BootstrapMethodError; Stack: 1
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (jobject obj = env->AllocObject((cclasses[11]))) { cstack1.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 2
        // DUP; Stack: 2
        cstack2 = cstack1;
        // New stack: 3
        // DUP2_X1; Stack: 3
        cstack3 = cstack1; cstack4 = cstack2; cstack2 = cstack0; cstack1 = cstack4; cstack0 = cstack3;
        // New stack: 5
        // POP2; Stack: 5
        ;
        // New stack: 3
        // INVOKESPECIAL java/lang/BootstrapMethodError.<init>(Ljava/lang/Throwable;)V; Stack: 3
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[11]), ((char *)(string_pool + 665LL)), ((char *)(string_pool + 672LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 697LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[11]), (cmethods[4]), cstack2.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 1
        // LABEL L25; Stack: 1
        L25: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/Object]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal3.l); refs.erase(clocal6.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 720LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 0
        // LABEL L24; Stack: 0
        L24: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 0
        // FRAME FULL L: [[Ljava/lang/Object;, [B, java/security/PublicKey, [B, 4, java/security/Signature] S: [java/security/Signature, [B, 4, 4, java/lang/invoke/MethodHandle]; Stack: 0
        refs.erase(cstack0.l); refs.erase(cstack1.l); refs.erase(cstack6.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal3.l); refs.erase(clocal6.l); 
        utils::clear_refs(env, refs);
        // New stack: 7
        // INVOKESTATIC native/magic/1/invoke/obfuscator0.127771650959966.a(Ljava/lang/Object;Ljava/lang/Object;JJLjava/lang/invoke/MethodHandle;)V; Stack: 7
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[10]) { cmethods[10] = env->GetStaticMethodID((cclasses[12]), ((char *)(string_pool + 2068LL)), ((char *)(string_pool + 2084LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } env->CallStaticVoidMethod((cclasses[12]), (cmethods[10]), cstack0.l, cstack1.l, cstack2.j, cstack4.j, cstack6.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 0
        // ALOAD 6; Stack: 0
        cstack0.l = clocal6.l; refs.insert(cstack0.l);
        // New stack: 1
        // ALOAD 1; Stack: 1
        cstack1.l = clocal1.l; refs.insert(cstack1.l);
        // New stack: 2
        // LDC 1932709879700982125; Stack: 2
        cstack2.j = 1932709879700982125LL;
        // New stack: 4
        // LLOAD 4; Stack: 4
        cstack4.j = clocal4.j;
        // New stack: 6
        // LABEL L1; Stack: 6
        L1: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }
        // New stack: 6
        // ICONST_1; Stack: 6
        cstack6.i = 1;
        // New stack: 7
        // ANEWARRAY java/lang/Object; Stack: 7
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } if (cstack6.i < 0) utils::throw_re(env, ((char *)(string_pool + 251LL)), ((char *)(string_pool + 288LL)), -1); else { cstack6.l = env->NewObjectArray(cstack6.i, (cclasses[5]), nullptr); refs.insert(cstack6.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }
        // New stack: 7
        // DUP; Stack: 7
        cstack7 = cstack6;
        // New stack: 8
        // LDC Ldev/mahiro/client/Mahiro_uP;; Stack: 8
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } cstack8.l = (cclasses[6]);
        // New stack: 9
        // SWAP; Stack: 9
        std::swap(cstack8, cstack7);
        // New stack: 9
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.127771650959966.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 9
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } cstack9.l = lookup;
        // New stack: 10
        // LDC Ldev/mahiro/client/Mahiro_yh;; Stack: 10
        if (!cclasses[14] || env->IsSameObject(cclasses[14], NULL)) { cclasses_mtx[14].lock(); if (!cclasses[14] || env->IsSameObject(cclasses[14], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[16]))) { cclasses[14] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[14].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } cstack10.l = (cclasses[14]);
        // New stack: 11
        // LDC a; Stack: 11
        cstack11.l = (cstrings[5]);
        // New stack: 12
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;; Stack: 12
        cstack12.l = (cstrings[6]);
        // New stack: 13
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.127771650959966.a()Ljava/lang/ClassLoader;; Stack: 13
        cstack13.l = classloader;
        // New stack: 14
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 14
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[7]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } if (!cmethods[1]) { cmethods[1] = env->GetStaticMethodID((cclasses[7]), ((char *)(string_pool + 313LL)), ((char *)(string_pool + 340LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }  } cstack12.l = env->CallStaticObjectMethod((cclasses[7]), (cmethods[1]), cstack12.l, cstack13.l); refs.insert(cstack12.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }
        // New stack: 13
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 13
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } if (!cmethods[2]) { cmethods[2] = env->GetMethodID((cclasses[8]), ((char *)(string_pool + 413LL)), ((char *)(string_pool + 424LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }  } if (cstack9.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 522LL)), -1); else { cstack9.l = env->CallObjectMethod(cstack9.l, (cmethods[2]), cstack10.l, cstack11.l, cstack12.l); refs.insert(cstack9.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }
        // New stack: 10
        // SWAP; Stack: 10
        std::swap(cstack9, cstack8);
        // New stack: 10
        // LDC v; Stack: 10
        cstack10.l = (cstrings[21]);
        // New stack: 11
        // SWAP; Stack: 11
        std::swap(cstack10, cstack9);
        // New stack: 11
        // LDC (Ljava/lang/Object;Ljava/lang/Object;JJ)Z; Stack: 11
        cstack11.l = (cstrings[28]);
        // New stack: 12
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.127771650959966.a()Ljava/lang/ClassLoader;; Stack: 12
        cstack12.l = classloader;
        // New stack: 13
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 13
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[7]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } if (!cmethods[1]) { cmethods[1] = env->GetStaticMethodID((cclasses[7]), ((char *)(string_pool + 313LL)), ((char *)(string_pool + 340LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }  } cstack11.l = env->CallStaticObjectMethod((cclasses[7]), (cmethods[1]), cstack11.l, cstack12.l); refs.insert(cstack11.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }
        // New stack: 12
        // SWAP; Stack: 12
        std::swap(cstack11, cstack10);
        // New stack: 12
        // LDC 0; Stack: 12
        cstack12.i = 0;
        // New stack: 13
        // ANEWARRAY java/lang/Object; Stack: 13
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } if (cstack12.i < 0) utils::throw_re(env, ((char *)(string_pool + 251LL)), ((char *)(string_pool + 288LL)), -1); else { cstack12.l = env->NewObjectArray(cstack12.i, (cclasses[5]), nullptr); refs.insert(cstack12.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }
        // New stack: 13
        // CHECKCAST java/lang/Object; Stack: 13
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } if (cstack12.l != nullptr && !env->IsInstanceOf(cstack12.l, (cclasses[5]))) { utils::throw_re(env, ((char *)(string_pool + 133LL)), (std::string(((char *)(string_pool + 162LL))) + std::string(((char *)(string_pool + 547LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } 
        // New stack: 13
        // SWAP; Stack: 13
        std::swap(cstack12, cstack11);
        // New stack: 13
        // INVOKESTATIC native/magic/1/linkcallsite/obfuscator0.127771650959966.a(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/invoke/MemberName;; Stack: 13
        cstack7.l = utils::link_call_site(env, cstack7.l, cstack8.l, cstack9.l, cstack10.l, cstack11.l, cstack12.l); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }
        // New stack: 8
        // POP; Stack: 8
        ;
        // New stack: 7
        // ICONST_0; Stack: 7
        cstack7.i = 0;
        // New stack: 8
        // AALOAD; Stack: 8
        if (cstack6.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 122LL)), -1); else { cstack6.l = env->GetObjectArrayElement((jobjectArray) cstack6.l, cstack7.i); refs.insert(cstack6.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }
        // New stack: 7
        // DUP; Stack: 7
        cstack7 = cstack6;
        // New stack: 8
        // INSTANCEOF java/lang/invoke/CallSite; Stack: 8
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[11]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } cstack7.i = cstack7.l == nullptr ? false : env->IsInstanceOf(cstack7.l, (cclasses[9]));
        // New stack: 8
        // IFEQ L26; Stack: 8
        if (cstack7.i == 0) goto L26;
        // New stack: 7
        // INVOKEINTERFACE java/lang/invoke/CallSite.getTarget()Ljava/lang/invoke/MethodHandle;; Stack: 7
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[11]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } if (!cmethods[3]) { cmethods[3] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 564LL)), ((char *)(string_pool + 574LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }  } if (cstack6.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 608LL)), -1); else { cstack6.l = env->CallObjectMethod(cstack6.l, (cmethods[3])); refs.insert(cstack6.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }
        // New stack: 7
        // LABEL L26; Stack: 7
        L26: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }
        // New stack: 7
        // FRAME FULL L: [[Ljava/lang/Object;, [B, java/security/PublicKey, [B, 4, java/security/Signature] S: [java/security/Signature, [B, 4, 4, java/lang/Object]; Stack: 7
        refs.erase(cstack0.l); refs.erase(cstack1.l); refs.erase(cstack6.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal3.l); refs.erase(clocal6.l); 
        utils::clear_refs(env, refs);
        // New stack: 7
        // CHECKCAST java/lang/invoke/MethodHandle; Stack: 7
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } if (cstack6.l != nullptr && !env->IsInstanceOf(cstack6.l, (cclasses[10]))) { utils::throw_re(env, ((char *)(string_pool + 133LL)), (std::string(((char *)(string_pool + 162LL))) + std::string(((char *)(string_pool + 635LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } 
        // New stack: 7
        // GOTO L27; Stack: 7
        goto L27;
        // New stack: 7
        // LABEL L2; Stack: 7
        L2: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 7
        // FRAME SAME1 L: null S: [java/lang/Throwable]; Stack: 7
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal3.l); refs.erase(clocal6.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // INSTANCEOF java/lang/BootstrapMethodError; Stack: 2
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack1.i = cstack1.l == nullptr ? false : env->IsInstanceOf(cstack1.l, (cclasses[11]));
        // New stack: 2
        // IFNE L28; Stack: 2
        if (cstack1.i != 0) goto L28;
        // New stack: 1
        // NEW java/lang/BootstrapMethodError; Stack: 1
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (jobject obj = env->AllocObject((cclasses[11]))) { cstack1.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 2
        // DUP; Stack: 2
        cstack2 = cstack1;
        // New stack: 3
        // DUP2_X1; Stack: 3
        cstack3 = cstack1; cstack4 = cstack2; cstack2 = cstack0; cstack1 = cstack4; cstack0 = cstack3;
        // New stack: 5
        // POP2; Stack: 5
        ;
        // New stack: 3
        // INVOKESPECIAL java/lang/BootstrapMethodError.<init>(Ljava/lang/Throwable;)V; Stack: 3
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[11]), ((char *)(string_pool + 665LL)), ((char *)(string_pool + 672LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 697LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[11]), (cmethods[4]), cstack2.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 1
        // LABEL L28; Stack: 1
        L28: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/Object]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal3.l); refs.erase(clocal6.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 720LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 0
        // LABEL L27; Stack: 0
        L27: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 0
        // FRAME FULL L: [[Ljava/lang/Object;, [B, java/security/PublicKey, [B, 4, java/security/Signature] S: [java/security/Signature, [B, 4, 4, java/lang/invoke/MethodHandle]; Stack: 0
        refs.erase(cstack0.l); refs.erase(cstack1.l); refs.erase(cstack6.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal3.l); refs.erase(clocal6.l); 
        utils::clear_refs(env, refs);
        // New stack: 7
        // INVOKESTATIC native/magic/1/invoke/obfuscator0.127771650959966.a(Ljava/lang/Object;Ljava/lang/Object;JJLjava/lang/invoke/MethodHandle;)Z; Stack: 7
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[11]) { cmethods[11] = env->GetStaticMethodID((cclasses[12]), ((char *)(string_pool + 2803LL)), ((char *)(string_pool + 2819LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack0.i = (jint) env->CallStaticBooleanMethod((cclasses[12]), (cmethods[11]), cstack0.l, cstack1.l, cstack2.j, cstack4.j, cstack6.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 1
        // LABEL L12; Stack: 1
        L12: if (env->ExceptionCheck()) { return (jboolean) 0; }
        // New stack: 1
        // IRETURN; Stack: 1
        return (jboolean) cstack0.i;
        // New stack: 0
        // LABEL L13; Stack: 0
        L13: if (env->ExceptionCheck()) { return (jboolean) 0; }
        // New stack: 0
        // FRAME FULL L: [[Ljava/lang/Object;, [B, java/security/PublicKey, [B, 4] S: [java/lang/Exception]; Stack: 0
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal3.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ASTORE 6; Stack: 1
        clocal6.l = cstack0.l; refs.insert(cstack0.l);
        // New stack: 0
        // ICONST_0; Stack: 0
        cstack0.i = 0;
        // New stack: 1
        // IRETURN; Stack: 1
        return (jboolean) cstack0.i;
        // New stack: 0
        return (jboolean) 0;
        L_CATCH_2: if (env->IsInstanceOf(cstack0.l, (cclasses[1]))) { goto L8; }
        goto L_CATCH_0;
        L_CATCH_1: if (env->IsInstanceOf(cstack0.l, (cclasses[1]))) { goto L10; }
        goto L_CATCH_0;
        L_CATCH_0: if (env->IsInstanceOf(cstack0.l, (cclasses[0]))) { goto L13; }
        env->Throw((jthrowable) cstack0.l); return (jboolean) 0;
        L_CATCH_5: if (env->IsInstanceOf(cstack0.l, (cclasses[1]))) { goto L2; }
        goto L_CATCH_0;
        L_CATCH_4: if (env->IsInstanceOf(cstack0.l, (cclasses[1]))) { goto L4; }
        goto L_CATCH_0;
        L_CATCH_3: if (env->IsInstanceOf(cstack0.l, (cclasses[1]))) { goto L6; }
        goto L_CATCH_0;
    }
    
    // <clinit>()V
    void JNICALL __ngen_special_clinit_9_3(JNIEnv *env, jobject ignored_hidden, jclass clazz) {
        env->DeleteLocalRef(ignored_hidden);
        jobject classloader = utils::get_classloader_from_class(env, clazz);
        if (env->ExceptionCheck()) { return (void) 0; }
        if (classloader == nullptr) { env->FatalError(((char *)(string_pool + 71LL))); return (void) 0; }
    
        jobject lookup = nullptr;
        jvalue cstack0 = {}, cstack1 = {}, cstack2 = {}, cstack3 = {}, cstack4 = {}, cstack5 = {}, cstack6 = {}, cstack7 = {}, cstack8 = {}, cstack9 = {};
        jvalue clocal0 = {}, clocal1 = {}, clocal2 = {}, clocal3 = {}, clocal4 = {}, clocal5 = {}, clocal6 = {}, clocal7 = {}, clocal8 = {}, clocal9 = {}, clocal10 = {};
        std::unordered_set<jobject> refs;
    
    
        // LDC -4502675576698389491; Stack: 0
        cstack0.j = -4502675576698389491LL;
        // New stack: 2
        // LDC -7456678700223481167; Stack: 2
        cstack2.j = -7456678700223481167LL;
        // New stack: 4
        // INVOKESTATIC java/lang/invoke/MethodHandles.lookup()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 4
        if (!cclasses[20] || env->IsSameObject(cclasses[20], NULL)) { cclasses_mtx[20].lock(); if (!cclasses[20] || env->IsSameObject(cclasses[20], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[29]))) { cclasses[20] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[20].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[12]) { cmethods[12] = env->GetStaticMethodID((cclasses[20]), ((char *)(string_pool + 5142LL)), ((char *)(string_pool + 5149LL))); if (env->ExceptionCheck()) { return (void) 0; }  } cstack4.l = env->CallStaticObjectMethod((cclasses[20]), (cmethods[12])); refs.insert(cstack4.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 5
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.lookupClass()Ljava/lang/Class;; Stack: 5
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[13]) { cmethods[13] = env->GetMethodID((cclasses[8]), ((char *)(string_pool + 5191LL)), ((char *)(string_pool + 3163LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 522LL)), -1); else { cstack4.l = env->CallObjectMethod(cstack4.l, (cmethods[13])); refs.insert(cstack4.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 5
        // INVOKESTATIC dev/mahiro/client/Mahiro__i.a(JJLjava/lang/Object;)Ldev/mahiro/client/Mahiro__g;; Stack: 5
        if (!cclasses[21] || env->IsSameObject(cclasses[21], NULL)) { cclasses_mtx[21].lock(); if (!cclasses[21] || env->IsSameObject(cclasses[21], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[30]))) { cclasses[21] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[21].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[14]) { cmethods[14] = env->GetStaticMethodID((cclasses[21]), ((char *)(string_pool + 247LL)), ((char *)(string_pool + 5203LL))); if (env->ExceptionCheck()) { return (void) 0; }  } cstack0.l = env->CallStaticObjectMethod((cclasses[21]), (cmethods[14]), cstack0.j, cstack2.j, cstack4.l); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // LDC 8868702224983; Stack: 1
        cstack1.j = 8868702224983LL;
        // New stack: 3
        // INVOKEINTERFACE dev/mahiro/client/Mahiro__g.a(J)J; Stack: 3
        if (!cclasses[22] || env->IsSameObject(cclasses[22], NULL)) { cclasses_mtx[22].lock(); if (!cclasses[22] || env->IsSameObject(cclasses[22], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[31]))) { cclasses[22] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[22].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[15]) { cmethods[15] = env->GetMethodID((cclasses[22]), ((char *)(string_pool + 247LL)), ((char *)(string_pool + 5255LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 5260LL)), -1); else cstack0.j = env->CallLongMethod(cstack0.l, (cmethods[15]), cstack1.j); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 2
        // PUTSTATIC dev/mahiro/client/Mahiro_uP.a J; Stack: 2
        if (!cclasses[4]  || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cfields[0]) { cfields[0] = env->GetStaticFieldID((cclasses[4]), ((char *)(string_pool + 247LL)), ((char *)(string_pool + 249LL))); if (env->ExceptionCheck()) { return (void) 0; }  } env->SetStaticLongField((cclasses[4]), (cfields[0]), cstack0.j); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // NEW java/util/HashMap; Stack: 0
        if (!cclasses[23] || env->IsSameObject(cclasses[23], NULL)) { cclasses_mtx[23].lock(); if (!cclasses[23] || env->IsSameObject(cclasses[23], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[32]))) { cclasses[23] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[23].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (jobject obj = env->AllocObject((cclasses[23]))) { cstack0.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // BIPUSH 13; Stack: 2
        cstack2.i = (jint) 13;
        // New stack: 3
        // INVOKESPECIAL java/util/HashMap.<init>(I)V; Stack: 3
        if (!cclasses[23] || env->IsSameObject(cclasses[23], NULL)) { cclasses_mtx[23].lock(); if (!cclasses[23] || env->IsSameObject(cclasses[23], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[32]))) { cclasses[23] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[23].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[16]) { cmethods[16] = env->GetMethodID((cclasses[23]), ((char *)(string_pool + 665LL)), ((char *)(string_pool + 5285LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 697LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[23]), (cmethods[16]), cstack2.i); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // PUTSTATIC dev/mahiro/client/Mahiro_uP.d Ljava/util/Map;; Stack: 1
        if (!cclasses[4]  || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cfields[1]) { cfields[1] = env->GetStaticFieldID((cclasses[4]), ((char *)(string_pool + 5290LL)), ((char *)(string_pool + 5292LL))); if (env->ExceptionCheck()) { return (void) 0; }  } env->SetStaticObjectField((cclasses[4]), (cfields[1]), cstack0.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // GETSTATIC dev/mahiro/client/Mahiro_uP.a J; Stack: 0
        if (!cclasses[4]  || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cfields[0]) { cfields[0] = env->GetStaticFieldID((cclasses[4]), ((char *)(string_pool + 247LL)), ((char *)(string_pool + 249LL))); if (env->ExceptionCheck()) { return (void) 0; }  } cstack0.j = env->GetStaticLongField((cclasses[4]), (cfields[0])); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 2
        // LDC 124824435851651; Stack: 2
        cstack2.j = 124824435851651LL;
        // New stack: 4
        // LXOR; Stack: 4
        cstack0.j = cstack0.j ^ cstack2.j;
        // New stack: 2
        // LSTORE 0; Stack: 2
        clocal0.j = cstack0.j;
        // New stack: 0
        // LDC DES/CBC/PKCS5Padding; Stack: 0
        cstack0.l = (cstrings[33]);
        // New stack: 1
        // INVOKESTATIC javax/crypto/Cipher.getInstance(Ljava/lang/String;)Ljavax/crypto/Cipher;; Stack: 1
        if (!cclasses[24] || env->IsSameObject(cclasses[24], NULL)) { cclasses_mtx[24].lock(); if (!cclasses[24] || env->IsSameObject(cclasses[24], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[34]))) { cclasses[24] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[24].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[17]) { cmethods[17] = env->GetStaticMethodID((cclasses[24]), ((char *)(string_pool + 5308LL)), ((char *)(string_pool + 5320LL))); if (env->ExceptionCheck()) { return (void) 0; }  } cstack0.l = env->CallStaticObjectMethod((cclasses[24]), (cmethods[17]), cstack0.l); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // ASTORE 2; Stack: 2
        clocal2.l = cstack1.l; refs.insert(cstack1.l);
        // New stack: 1
        // ICONST_2; Stack: 1
        cstack1.i = 2;
        // New stack: 2
        // LDC DES; Stack: 2
        cstack2.l = (cstrings[35]);
        // New stack: 3
        // INVOKESTATIC javax/crypto/SecretKeyFactory.getInstance(Ljava/lang/String;)Ljavax/crypto/SecretKeyFactory;; Stack: 3
        if (!cclasses[25] || env->IsSameObject(cclasses[25], NULL)) { cclasses_mtx[25].lock(); if (!cclasses[25] || env->IsSameObject(cclasses[25], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[36]))) { cclasses[25] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[25].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[18]) { cmethods[18] = env->GetStaticMethodID((cclasses[25]), ((char *)(string_pool + 5308LL)), ((char *)(string_pool + 5362LL))); if (env->ExceptionCheck()) { return (void) 0; }  } cstack2.l = env->CallStaticObjectMethod((cclasses[25]), (cmethods[18]), cstack2.l); refs.insert(cstack2.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 3
        // BIPUSH 8; Stack: 3
        cstack3.i = (jint) 8;
        // New stack: 4
        // NEWARRAY 8; Stack: 4
        if (cstack3.i < 0) utils::throw_re(env, ((char *)(string_pool + 251LL)), ((char *)(string_pool + 5414LL)), -1); else { cstack3.l = env->NewByteArray(cstack3.i); refs.insert(cstack3.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 4
        // DUP; Stack: 4
        cstack4 = cstack3;
        // New stack: 5
        // ICONST_0; Stack: 5
        cstack5.i = 0;
        // New stack: 6
        // LLOAD 0; Stack: 6
        cstack6.j = clocal0.j;
        // New stack: 8
        // BIPUSH 56; Stack: 8
        cstack8.i = (jint) 56;
        // New stack: 9
        // LUSHR; Stack: 9
        cstack6.j = (jlong) (((uint64_t) cstack6.j) >> (((uint64_t) cstack8.i) & 0x3f));
        // New stack: 8
        // L2I; Stack: 8
        cstack6.i = (jint) cstack6.j;
        // New stack: 7
        // I2B; Stack: 7
        cstack6.i = (jint) (jbyte) cstack6.i;
        // New stack: 7
        // BASTORE; Stack: 7
        if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 5443LL)), -1); else { utils::bastore(env, (jarray) cstack4.l, cstack5.i, cstack6.i); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 4
        // ICONST_1; Stack: 4
        cstack4.i = 1;
        // New stack: 5
        // ISTORE 3; Stack: 5
        clocal3.i = cstack4.i;
        // New stack: 4
        // LABEL L1; Stack: 4
        L1: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 4
        // FRAME FULL L: [4, javax/crypto/Cipher, 1] S: [javax/crypto/Cipher, 1, javax/crypto/SecretKeyFactory, [B]; Stack: 4
        refs.erase(cstack0.l); refs.erase(cstack2.l); refs.erase(cstack3.l); 
        refs.erase(clocal2.l); 
        utils::clear_refs(env, refs);
        // New stack: 4
        // ILOAD 3; Stack: 4
        cstack4.i = clocal3.i;
        // New stack: 5
        // BIPUSH 8; Stack: 5
        cstack5.i = (jint) 8;
        // New stack: 6
        // IF_ICMPGE L2; Stack: 6
        if (cstack4.i >= cstack5.i) goto L2;
        // New stack: 4
        // DUP; Stack: 4
        cstack4 = cstack3;
        // New stack: 5
        // ILOAD 3; Stack: 5
        cstack5.i = clocal3.i;
        // New stack: 6
        // LLOAD 0; Stack: 6
        cstack6.j = clocal0.j;
        // New stack: 8
        // ILOAD 3; Stack: 8
        cstack8.i = clocal3.i;
        // New stack: 9
        // BIPUSH 8; Stack: 9
        cstack9.i = (jint) 8;
        // New stack: 10
        // IMUL; Stack: 10
        cstack8.i = cstack8.i * cstack9.i;
        // New stack: 9
        // LSHL; Stack: 9
        cstack6.j = cstack6.j << (0x3f & cstack8.i);
        // New stack: 8
        // BIPUSH 56; Stack: 8
        cstack8.i = (jint) 56;
        // New stack: 9
        // LUSHR; Stack: 9
        cstack6.j = (jlong) (((uint64_t) cstack6.j) >> (((uint64_t) cstack8.i) & 0x3f));
        // New stack: 8
        // L2I; Stack: 8
        cstack6.i = (jint) cstack6.j;
        // New stack: 7
        // I2B; Stack: 7
        cstack6.i = (jint) (jbyte) cstack6.i;
        // New stack: 7
        // BASTORE; Stack: 7
        if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 5443LL)), -1); else { utils::bastore(env, (jarray) cstack4.l, cstack5.i, cstack6.i); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 4
        // IINC 3 1; Stack: 4
        clocal3.i += 1;
        // New stack: 4
        // GOTO L1; Stack: 4
        goto L1;
        // New stack: 4
        // LABEL L2; Stack: 4
        L2: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 4
        // FRAME FULL L: [4, javax/crypto/Cipher, 1] S: [javax/crypto/Cipher, 1, javax/crypto/SecretKeyFactory, [B]; Stack: 4
        refs.erase(cstack0.l); refs.erase(cstack2.l); refs.erase(cstack3.l); 
        refs.erase(clocal2.l); 
        utils::clear_refs(env, refs);
        // New stack: 4
        // NEW javax/crypto/spec/DESKeySpec; Stack: 4
        if (!cclasses[26] || env->IsSameObject(cclasses[26], NULL)) { cclasses_mtx[26].lock(); if (!cclasses[26] || env->IsSameObject(cclasses[26], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[37]))) { cclasses[26] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[26].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (jobject obj = env->AllocObject((cclasses[26]))) { cstack4.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 5
        // DUP_X1; Stack: 5
        cstack5 = cstack4; cstack4 = cstack3; cstack3 = cstack5;
        // New stack: 6
        // SWAP; Stack: 6
        std::swap(cstack5, cstack4);
        // New stack: 6
        // INVOKESPECIAL javax/crypto/spec/DESKeySpec.<init>([B)V; Stack: 6
        if (!cclasses[26] || env->IsSameObject(cclasses[26], NULL)) { cclasses_mtx[26].lock(); if (!cclasses[26] || env->IsSameObject(cclasses[26], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[37]))) { cclasses[26] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[26].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[19]) { cmethods[19] = env->GetMethodID((cclasses[26]), ((char *)(string_pool + 665LL)), ((char *)(string_pool + 5455LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 697LL)), -1); else env->CallNonvirtualVoidMethod(cstack4.l, (cclasses[26]), (cmethods[19]), cstack5.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 4
        // INVOKEVIRTUAL javax/crypto/SecretKeyFactory.generateSecret(Ljava/security/spec/KeySpec;)Ljavax/crypto/SecretKey;; Stack: 4
        if (!cclasses[25] || env->IsSameObject(cclasses[25], NULL)) { cclasses_mtx[25].lock(); if (!cclasses[25] || env->IsSameObject(cclasses[25], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[36]))) { cclasses[25] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[25].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[20]) { cmethods[20] = env->GetMethodID((cclasses[25]), ((char *)(string_pool + 5461LL)), ((char *)(string_pool + 5476LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack2.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 522LL)), -1); else { cstack2.l = env->CallObjectMethod(cstack2.l, (cmethods[20]), cstack3.l); refs.insert(cstack2.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 3
        // NEW javax/crypto/spec/IvParameterSpec; Stack: 3
        if (!cclasses[27] || env->IsSameObject(cclasses[27], NULL)) { cclasses_mtx[27].lock(); if (!cclasses[27] || env->IsSameObject(cclasses[27], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[38]))) { cclasses[27] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[27].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (jobject obj = env->AllocObject((cclasses[27]))) { cstack3.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 4
        // DUP; Stack: 4
        cstack4 = cstack3;
        // New stack: 5
        // BIPUSH 8; Stack: 5
        cstack5.i = (jint) 8;
        // New stack: 6
        // NEWARRAY 8; Stack: 6
        if (cstack5.i < 0) utils::throw_re(env, ((char *)(string_pool + 251LL)), ((char *)(string_pool + 5414LL)), -1); else { cstack5.l = env->NewByteArray(cstack5.i); refs.insert(cstack5.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 6
        // INVOKESPECIAL javax/crypto/spec/IvParameterSpec.<init>([B)V; Stack: 6
        if (!cclasses[27] || env->IsSameObject(cclasses[27], NULL)) { cclasses_mtx[27].lock(); if (!cclasses[27] || env->IsSameObject(cclasses[27], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[38]))) { cclasses[27] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[27].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[21]) { cmethods[21] = env->GetMethodID((cclasses[27]), ((char *)(string_pool + 665LL)), ((char *)(string_pool + 5455LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 697LL)), -1); else env->CallNonvirtualVoidMethod(cstack4.l, (cclasses[27]), (cmethods[21]), cstack5.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 4
        // INVOKEVIRTUAL javax/crypto/Cipher.init(ILjava/security/Key;Ljava/security/spec/AlgorithmParameterSpec;)V; Stack: 4
        if (!cclasses[24] || env->IsSameObject(cclasses[24], NULL)) { cclasses_mtx[24].lock(); if (!cclasses[24] || env->IsSameObject(cclasses[24], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[34]))) { cclasses[24] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[24].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[22]) { cmethods[22] = env->GetMethodID((cclasses[24]), ((char *)(string_pool + 5531LL)), ((char *)(string_pool + 5536LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 5603LL)), -1); else env->CallVoidMethod(cstack0.l, (cmethods[22]), cstack1.i, cstack2.l, cstack3.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // ICONST_2; Stack: 0
        cstack0.i = 2;
        // New stack: 1
        // ANEWARRAY java/lang/String; Stack: 1
        if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { cclasses_mtx[13].lock(); if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[13] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[13].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (cstack0.i < 0) utils::throw_re(env, ((char *)(string_pool + 251LL)), ((char *)(string_pool + 288LL)), -1); else { cstack0.l = env->NewObjectArray(cstack0.i, (cclasses[13]), nullptr); refs.insert(cstack0.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // ASTORE 9; Stack: 1
        clocal9.l = cstack0.l; refs.insert(cstack0.l);
        // New stack: 0
        // ICONST_0; Stack: 0
        cstack0.i = 0;
        // New stack: 1
        // ISTORE 7; Stack: 1
        clocal7.i = cstack0.i;
        // New stack: 0
        // LDC gW:\u00aev\u009eHAT\u00a3\u0099\u0092\u0015t\u0093\u0012\u0010\u000d\u00ebz'\u00a3\u00072\u00f1o\u00d2\u00d8\u00c9\u00098\u0091k; Stack: 0
        cstack0.l = (cstrings[39]);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // ASTORE 6; Stack: 2
        clocal6.l = cstack1.l; refs.insert(cstack1.l);
        // New stack: 1
        // INVOKEVIRTUAL java/lang/String.length()I; Stack: 1
        if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { cclasses_mtx[13].lock(); if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[13] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[13].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[23]) { cmethods[23] = env->GetMethodID((cclasses[13]), ((char *)(string_pool + 5626LL)), ((char *)(string_pool + 3219LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 3223LL)), -1); else cstack0.i = env->CallIntMethod(cstack0.l, (cmethods[23])); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // ISTORE 8; Stack: 1
        clocal8.i = cstack0.i;
        // New stack: 0
        // BIPUSH 16; Stack: 0
        cstack0.i = (jint) 16;
        // New stack: 1
        // ISTORE 5; Stack: 1
        clocal5.i = cstack0.i;
        // New stack: 0
        // ICONST_M1; Stack: 0
        cstack0.i = -1;
        // New stack: 1
        // ISTORE 4; Stack: 1
        clocal4.i = cstack0.i;
        // New stack: 0
        // LABEL L3; Stack: 0
        L3: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // FRAME FULL L: [4, javax/crypto/Cipher, 1, 1, 1, java/lang/String, 1, 1, [Ljava/lang/String;] S: []; Stack: 0
        refs.erase(clocal2.l); refs.erase(clocal6.l); refs.erase(clocal9.l); 
        utils::clear_refs(env, refs);
        // New stack: 0
        // IINC 4 1; Stack: 0
        clocal4.i += 1;
        // New stack: 0
        // ALOAD 6; Stack: 0
        cstack0.l = clocal6.l; refs.insert(cstack0.l);
        // New stack: 1
        // ILOAD 4; Stack: 1
        cstack1.i = clocal4.i;
        // New stack: 2
        // DUP; Stack: 2
        cstack2 = cstack1;
        // New stack: 3
        // ILOAD 5; Stack: 3
        cstack3.i = clocal5.i;
        // New stack: 4
        // IADD; Stack: 4
        cstack2.i = cstack2.i + cstack3.i;
        // New stack: 3
        // INVOKEVIRTUAL java/lang/String.substring(II)Ljava/lang/String;; Stack: 3
        if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { cclasses_mtx[13].lock(); if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[13] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[13].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[24]) { cmethods[24] = env->GetMethodID((cclasses[13]), ((char *)(string_pool + 5633LL)), ((char *)(string_pool + 5643LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 522LL)), -1); else { cstack0.l = env->CallObjectMethod(cstack0.l, (cmethods[24]), cstack1.i, cstack2.i); refs.insert(cstack0.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // ICONST_M1; Stack: 1
        cstack1.i = -1;
        // New stack: 2
        // GOTO L4; Stack: 2
        goto L4;
        // New stack: 2
        // LABEL L5; Stack: 2
        L5: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 2
        // FRAME FULL L: [4, javax/crypto/Cipher, 1, 1, 1, java/lang/String, 1, 1, [Ljava/lang/String;, [B] S: [java/lang/String]; Stack: 2
        refs.erase(cstack0.l); 
        refs.erase(clocal2.l); refs.erase(clocal6.l); refs.erase(clocal9.l); refs.erase(clocal10.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ALOAD 9; Stack: 1
        cstack1.l = clocal9.l; refs.insert(cstack1.l);
        // New stack: 2
        // SWAP; Stack: 2
        std::swap(cstack1, cstack0);
        // New stack: 2
        // ILOAD 7; Stack: 2
        cstack2.i = clocal7.i;
        // New stack: 3
        // IINC 7 1; Stack: 3
        clocal7.i += 1;
        // New stack: 3
        // SWAP; Stack: 3
        std::swap(cstack2, cstack1);
        // New stack: 3
        // AASTORE; Stack: 3
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 1107LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack0.l, cstack1.i, cstack2.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // ILOAD 4; Stack: 0
        cstack0.i = clocal4.i;
        // New stack: 1
        // ILOAD 5; Stack: 1
        cstack1.i = clocal5.i;
        // New stack: 2
        // IADD; Stack: 2
        cstack0.i = cstack0.i + cstack1.i;
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // ISTORE 4; Stack: 2
        clocal4.i = cstack1.i;
        // New stack: 1
        // ILOAD 8; Stack: 1
        cstack1.i = clocal8.i;
        // New stack: 2
        // IF_ICMPGE L6; Stack: 2
        if (cstack0.i >= cstack1.i) goto L6;
        // New stack: 0
        // ALOAD 6; Stack: 0
        cstack0.l = clocal6.l; refs.insert(cstack0.l);
        // New stack: 1
        // ILOAD 4; Stack: 1
        cstack1.i = clocal4.i;
        // New stack: 2
        // INVOKEVIRTUAL java/lang/String.charAt(I)C; Stack: 2
        if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { cclasses_mtx[13].lock(); if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[13] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[13].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[25]) { cmethods[25] = env->GetMethodID((cclasses[13]), ((char *)(string_pool + 5666LL)), ((char *)(string_pool + 5673LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 5678LL)), -1); else cstack0.i = (jint) env->CallCharMethod(cstack0.l, (cmethods[25]), cstack1.i); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // ISTORE 5; Stack: 1
        clocal5.i = cstack0.i;
        // New stack: 0
        // GOTO L3; Stack: 0
        goto L3;
        // New stack: 0
        // LABEL L6; Stack: 0
        L6: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // FRAME SAME L: null S: null; Stack: 0
        refs.erase(clocal2.l); refs.erase(clocal6.l); refs.erase(clocal9.l); refs.erase(clocal10.l); 
        utils::clear_refs(env, refs);
        // New stack: 0
        // ALOAD 9; Stack: 0
        cstack0.l = clocal9.l; refs.insert(cstack0.l);
        // New stack: 1
        // PUTSTATIC dev/mahiro/client/Mahiro_uP.b [Ljava/lang/String;; Stack: 1
        if (!cclasses[4]  || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cfields[2]) { cfields[2] = env->GetStaticFieldID((cclasses[4]), ((char *)(string_pool + 5701LL)), ((char *)(string_pool + 5703LL))); if (env->ExceptionCheck()) { return (void) 0; }  } env->SetStaticObjectField((cclasses[4]), (cfields[2]), cstack0.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // ICONST_2; Stack: 0
        cstack0.i = 2;
        // New stack: 1
        // ANEWARRAY java/lang/String; Stack: 1
        if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { cclasses_mtx[13].lock(); if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[13] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[13].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (cstack0.i < 0) utils::throw_re(env, ((char *)(string_pool + 251LL)), ((char *)(string_pool + 288LL)), -1); else { cstack0.l = env->NewObjectArray(cstack0.i, (cclasses[13]), nullptr); refs.insert(cstack0.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // PUTSTATIC dev/mahiro/client/Mahiro_uP.c [Ljava/lang/String;; Stack: 1
        if (!cclasses[4]  || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cfields[3]) { cfields[3] = env->GetStaticFieldID((cclasses[4]), ((char *)(string_pool + 5723LL)), ((char *)(string_pool + 5703LL))); if (env->ExceptionCheck()) { return (void) 0; }  } env->SetStaticObjectField((cclasses[4]), (cfields[3]), cstack0.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // GOTO L7; Stack: 0
        goto L7;
        // New stack: 0
        // LABEL L4; Stack: 0
        L4: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // FRAME FULL L: [4, javax/crypto/Cipher, 1, 1, 1, java/lang/String, 1, 1, [Ljava/lang/String;] S: [java/lang/String, 1]; Stack: 0
        refs.erase(cstack0.l); 
        refs.erase(clocal2.l); refs.erase(clocal6.l); refs.erase(clocal9.l); 
        utils::clear_refs(env, refs);
        // New stack: 2
        // SWAP; Stack: 2
        std::swap(cstack1, cstack0);
        // New stack: 2
        // LDC ISO-8859-1; Stack: 2
        cstack2.l = (cstrings[40]);
        // New stack: 3
        // INVOKEVIRTUAL java/lang/String.getBytes(Ljava/lang/String;)[B; Stack: 3
        if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { cclasses_mtx[13].lock(); if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[13] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[13].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[26]) { cmethods[26] = env->GetMethodID((cclasses[13]), ((char *)(string_pool + 5753LL)), ((char *)(string_pool + 5762LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 522LL)), -1); else { cstack1.l = env->CallObjectMethod(cstack1.l, (cmethods[26]), cstack2.l); refs.insert(cstack1.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 2
        // ALOAD 2; Stack: 2
        cstack2.l = clocal2.l; refs.insert(cstack2.l);
        // New stack: 3
        // SWAP; Stack: 3
        std::swap(cstack2, cstack1);
        // New stack: 3
        // INVOKEVIRTUAL javax/crypto/Cipher.doFinal([B)[B; Stack: 3
        if (!cclasses[24] || env->IsSameObject(cclasses[24], NULL)) { cclasses_mtx[24].lock(); if (!cclasses[24] || env->IsSameObject(cclasses[24], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[34]))) { cclasses[24] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[24].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[27]) { cmethods[27] = env->GetMethodID((cclasses[24]), ((char *)(string_pool + 5785LL)), ((char *)(string_pool + 5793LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 522LL)), -1); else { cstack1.l = env->CallObjectMethod(cstack1.l, (cmethods[27]), cstack2.l); refs.insert(cstack1.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 2
        // ASTORE 10; Stack: 2
        clocal10.l = cstack1.l; refs.insert(cstack1.l);
        // New stack: 1
        // ALOAD 10; Stack: 1
        cstack1.l = clocal10.l; refs.insert(cstack1.l);
        // New stack: 2
        // INVOKESTATIC dev/mahiro/client/Mahiro_uP.a([B)Ljava/lang/String;; Stack: 2
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[28]) { cmethods[28] = env->GetStaticMethodID((cclasses[4]), ((char *)(string_pool + 247LL)), ((char *)(string_pool + 5800LL))); if (env->ExceptionCheck()) { return (void) 0; }  } cstack1.l = env->CallStaticObjectMethod((cclasses[4]), (cmethods[28]), cstack1.l); refs.insert(cstack1.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 2
        // INVOKEVIRTUAL java/lang/String.intern()Ljava/lang/String;; Stack: 2
        if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { cclasses_mtx[13].lock(); if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[13] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[13].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[29]) { cmethods[29] = env->GetMethodID((cclasses[13]), ((char *)(string_pool + 5823LL)), ((char *)(string_pool + 5830LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 522LL)), -1); else { cstack1.l = env->CallObjectMethod(cstack1.l, (cmethods[29])); refs.insert(cstack1.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 2
        // SWAP; Stack: 2
        std::swap(cstack1, cstack0);
        // New stack: 2
        // POP; Stack: 2
        ;
        // New stack: 1
        // GOTO L5; Stack: 1
        goto L5;
        // New stack: 1
        // LABEL L7; Stack: 1
        L7: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // FRAME APPEND L: [[B] S: null; Stack: 1
        refs.erase(clocal2.l); refs.erase(clocal6.l); refs.erase(clocal9.l); refs.erase(clocal10.l); 
        utils::clear_refs(env, refs);
        // New stack: 0
        // RETURN; Stack: 0
        return;
        // New stack: 0
        return (void) 0;
    }
    
    // a([B)Ljava/lang/String;
    jobject JNICALL __ngen_native_a4(JNIEnv *env, jclass clazz, jarray arg0) {
        jobject classloader = utils::get_classloader_from_class(env, clazz);
        if (env->ExceptionCheck()) { return (jobject) 0; }
        if (classloader == nullptr) { env->FatalError(((char *)(string_pool + 71LL))); return (jobject) 0; }
    
        jobject lookup = nullptr;
        jvalue cstack0 = {}, cstack1 = {}, cstack2 = {}, cstack3 = {}, cstack4 = {};
        jvalue clocal0 = {}, clocal1 = {}, clocal2 = {}, clocal3 = {}, clocal4 = {}, clocal5 = {}, clocal6 = {};
        std::unordered_set<jobject> refs;
    
        clocal0.l = arg0; refs.insert(clocal0.l);
    
        // ICONST_0; Stack: 0
        cstack0.i = 0;
        // New stack: 1
        // ISTORE 1; Stack: 1
        clocal1.i = cstack0.i;
        // New stack: 0
        // ALOAD 0; Stack: 0
        cstack0.l = clocal0.l; refs.insert(cstack0.l);
        // New stack: 1
        // ARRAYLENGTH; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 6026LL)), -1); else cstack0.i = env->GetArrayLength((jarray) cstack0.l); 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // ISTORE 2; Stack: 2
        clocal2.i = cstack1.i;
        // New stack: 1
        // NEWARRAY 5; Stack: 1
        if (cstack0.i < 0) utils::throw_re(env, ((char *)(string_pool + 251LL)), ((char *)(string_pool + 6042LL)), -1); else { cstack0.l = env->NewCharArray(cstack0.i); refs.insert(cstack0.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // ASTORE 3; Stack: 1
        clocal3.l = cstack0.l; refs.insert(cstack0.l);
        // New stack: 0
        // ICONST_0; Stack: 0
        cstack0.i = 0;
        // New stack: 1
        // ISTORE 4; Stack: 1
        clocal4.i = cstack0.i;
        // New stack: 0
        // LABEL L1; Stack: 0
        L1: if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 0
        // FRAME FULL L: [[B, 1, 1, [C, 1] S: []; Stack: 0
        refs.erase(clocal0.l); refs.erase(clocal3.l); 
        utils::clear_refs(env, refs);
        // New stack: 0
        // ILOAD 4; Stack: 0
        cstack0.i = clocal4.i;
        // New stack: 1
        // ILOAD 2; Stack: 1
        cstack1.i = clocal2.i;
        // New stack: 2
        // IF_ICMPGE L2; Stack: 2
        if (cstack0.i >= cstack1.i) goto L2;
        // New stack: 0
        // SIPUSH 255; Stack: 0
        cstack0.i = (jint) 255;
        // New stack: 1
        // ALOAD 0; Stack: 1
        cstack1.l = clocal0.l; refs.insert(cstack1.l);
        // New stack: 2
        // ILOAD 4; Stack: 2
        cstack2.i = clocal4.i;
        // New stack: 3
        // BALOAD; Stack: 3
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 5882LL)), -1); else { cstack1.i = (jint) utils::baload(env, (jarray) cstack1.l, cstack2.i); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 2
        // IAND; Stack: 2
        cstack0.i = cstack0.i & cstack1.i;
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // ISTORE 5; Stack: 2
        clocal5.i = cstack1.i;
        // New stack: 1
        // SIPUSH 192; Stack: 1
        cstack1.i = (jint) 192;
        // New stack: 2
        // IF_ICMPGE L3; Stack: 2
        if (cstack0.i >= cstack1.i) goto L3;
        // New stack: 0
        // ALOAD 3; Stack: 0
        cstack0.l = clocal3.l; refs.insert(cstack0.l);
        // New stack: 1
        // ILOAD 1; Stack: 1
        cstack1.i = clocal1.i;
        // New stack: 2
        // IINC 1 1; Stack: 2
        clocal1.i += 1;
        // New stack: 2
        // ILOAD 5; Stack: 2
        cstack2.i = clocal5.i;
        // New stack: 3
        // I2C; Stack: 3
        cstack2.i = (jint) (jchar) cstack2.i;
        // New stack: 3
        // CASTORE; Stack: 3
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 6071LL)), -1); else { jchar temp = (jchar) cstack2.i; env->SetCharArrayRegion((jcharArray) cstack0.l, cstack1.i, 1, &temp); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 0
        // GOTO L4; Stack: 0
        goto L4;
        // New stack: 0
        // LABEL L3; Stack: 0
        L3: if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 0
        // FRAME APPEND L: [1] S: null; Stack: 0
        refs.erase(clocal0.l); refs.erase(clocal3.l); 
        utils::clear_refs(env, refs);
        // New stack: 0
        // ILOAD 5; Stack: 0
        cstack0.i = clocal5.i;
        // New stack: 1
        // SIPUSH 224; Stack: 1
        cstack1.i = (jint) 224;
        // New stack: 2
        // IF_ICMPGE L5; Stack: 2
        if (cstack0.i >= cstack1.i) goto L5;
        // New stack: 0
        // ILOAD 5; Stack: 0
        cstack0.i = clocal5.i;
        // New stack: 1
        // BIPUSH 31; Stack: 1
        cstack1.i = (jint) 31;
        // New stack: 2
        // IAND; Stack: 2
        cstack0.i = cstack0.i & cstack1.i;
        // New stack: 1
        // I2C; Stack: 1
        cstack0.i = (jint) (jchar) cstack0.i;
        // New stack: 1
        // BIPUSH 6; Stack: 1
        cstack1.i = (jint) 6;
        // New stack: 2
        // ISHL; Stack: 2
        cstack0.i = cstack0.i << (0x1f & cstack1.i);
        // New stack: 1
        // I2C; Stack: 1
        cstack0.i = (jint) (jchar) cstack0.i;
        // New stack: 1
        // ISTORE 6; Stack: 1
        clocal6.i = cstack0.i;
        // New stack: 0
        // ALOAD 0; Stack: 0
        cstack0.l = clocal0.l; refs.insert(cstack0.l);
        // New stack: 1
        // IINC 4 1; Stack: 1
        clocal4.i += 1;
        // New stack: 1
        // ILOAD 4; Stack: 1
        cstack1.i = clocal4.i;
        // New stack: 2
        // BALOAD; Stack: 2
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 5882LL)), -1); else { cstack0.i = (jint) utils::baload(env, (jarray) cstack0.l, cstack1.i); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // ISTORE 5; Stack: 1
        clocal5.i = cstack0.i;
        // New stack: 0
        // ILOAD 6; Stack: 0
        cstack0.i = clocal6.i;
        // New stack: 1
        // ILOAD 5; Stack: 1
        cstack1.i = clocal5.i;
        // New stack: 2
        // BIPUSH 63; Stack: 2
        cstack2.i = (jint) 63;
        // New stack: 3
        // IAND; Stack: 3
        cstack1.i = cstack1.i & cstack2.i;
        // New stack: 2
        // I2C; Stack: 2
        cstack1.i = (jint) (jchar) cstack1.i;
        // New stack: 2
        // IOR; Stack: 2
        cstack0.i = cstack0.i | cstack1.i;
        // New stack: 1
        // I2C; Stack: 1
        cstack0.i = (jint) (jchar) cstack0.i;
        // New stack: 1
        // ISTORE 6; Stack: 1
        clocal6.i = cstack0.i;
        // New stack: 0
        // ALOAD 3; Stack: 0
        cstack0.l = clocal3.l; refs.insert(cstack0.l);
        // New stack: 1
        // ILOAD 1; Stack: 1
        cstack1.i = clocal1.i;
        // New stack: 2
        // IINC 1 1; Stack: 2
        clocal1.i += 1;
        // New stack: 2
        // ILOAD 6; Stack: 2
        cstack2.i = clocal6.i;
        // New stack: 3
        // CASTORE; Stack: 3
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 6071LL)), -1); else { jchar temp = (jchar) cstack2.i; env->SetCharArrayRegion((jcharArray) cstack0.l, cstack1.i, 1, &temp); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 0
        // GOTO L4; Stack: 0
        goto L4;
        // New stack: 0
        // LABEL L5; Stack: 0
        L5: if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 0
        // FRAME SAME L: null S: null; Stack: 0
        refs.erase(clocal0.l); refs.erase(clocal3.l); 
        utils::clear_refs(env, refs);
        // New stack: 0
        // ILOAD 4; Stack: 0
        cstack0.i = clocal4.i;
        // New stack: 1
        // ILOAD 2; Stack: 1
        cstack1.i = clocal2.i;
        // New stack: 2
        // ICONST_2; Stack: 2
        cstack2.i = 2;
        // New stack: 3
        // ISUB; Stack: 3
        cstack1.i = cstack1.i - cstack2.i;
        // New stack: 2
        // IF_ICMPGE L4; Stack: 2
        if (cstack0.i >= cstack1.i) goto L4;
        // New stack: 0
        // ILOAD 5; Stack: 0
        cstack0.i = clocal5.i;
        // New stack: 1
        // BIPUSH 15; Stack: 1
        cstack1.i = (jint) 15;
        // New stack: 2
        // IAND; Stack: 2
        cstack0.i = cstack0.i & cstack1.i;
        // New stack: 1
        // I2C; Stack: 1
        cstack0.i = (jint) (jchar) cstack0.i;
        // New stack: 1
        // BIPUSH 12; Stack: 1
        cstack1.i = (jint) 12;
        // New stack: 2
        // ISHL; Stack: 2
        cstack0.i = cstack0.i << (0x1f & cstack1.i);
        // New stack: 1
        // I2C; Stack: 1
        cstack0.i = (jint) (jchar) cstack0.i;
        // New stack: 1
        // ISTORE 6; Stack: 1
        clocal6.i = cstack0.i;
        // New stack: 0
        // ALOAD 0; Stack: 0
        cstack0.l = clocal0.l; refs.insert(cstack0.l);
        // New stack: 1
        // IINC 4 1; Stack: 1
        clocal4.i += 1;
        // New stack: 1
        // ILOAD 4; Stack: 1
        cstack1.i = clocal4.i;
        // New stack: 2
        // BALOAD; Stack: 2
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 5882LL)), -1); else { cstack0.i = (jint) utils::baload(env, (jarray) cstack0.l, cstack1.i); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // ISTORE 5; Stack: 1
        clocal5.i = cstack0.i;
        // New stack: 0
        // ILOAD 6; Stack: 0
        cstack0.i = clocal6.i;
        // New stack: 1
        // ILOAD 5; Stack: 1
        cstack1.i = clocal5.i;
        // New stack: 2
        // BIPUSH 63; Stack: 2
        cstack2.i = (jint) 63;
        // New stack: 3
        // IAND; Stack: 3
        cstack1.i = cstack1.i & cstack2.i;
        // New stack: 2
        // I2C; Stack: 2
        cstack1.i = (jint) (jchar) cstack1.i;
        // New stack: 2
        // BIPUSH 6; Stack: 2
        cstack2.i = (jint) 6;
        // New stack: 3
        // ISHL; Stack: 3
        cstack1.i = cstack1.i << (0x1f & cstack2.i);
        // New stack: 2
        // IOR; Stack: 2
        cstack0.i = cstack0.i | cstack1.i;
        // New stack: 1
        // I2C; Stack: 1
        cstack0.i = (jint) (jchar) cstack0.i;
        // New stack: 1
        // ISTORE 6; Stack: 1
        clocal6.i = cstack0.i;
        // New stack: 0
        // ALOAD 0; Stack: 0
        cstack0.l = clocal0.l; refs.insert(cstack0.l);
        // New stack: 1
        // IINC 4 1; Stack: 1
        clocal4.i += 1;
        // New stack: 1
        // ILOAD 4; Stack: 1
        cstack1.i = clocal4.i;
        // New stack: 2
        // BALOAD; Stack: 2
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 5882LL)), -1); else { cstack0.i = (jint) utils::baload(env, (jarray) cstack0.l, cstack1.i); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // ISTORE 5; Stack: 1
        clocal5.i = cstack0.i;
        // New stack: 0
        // ILOAD 6; Stack: 0
        cstack0.i = clocal6.i;
        // New stack: 1
        // ILOAD 5; Stack: 1
        cstack1.i = clocal5.i;
        // New stack: 2
        // BIPUSH 63; Stack: 2
        cstack2.i = (jint) 63;
        // New stack: 3
        // IAND; Stack: 3
        cstack1.i = cstack1.i & cstack2.i;
        // New stack: 2
        // I2C; Stack: 2
        cstack1.i = (jint) (jchar) cstack1.i;
        // New stack: 2
        // IOR; Stack: 2
        cstack0.i = cstack0.i | cstack1.i;
        // New stack: 1
        // I2C; Stack: 1
        cstack0.i = (jint) (jchar) cstack0.i;
        // New stack: 1
        // ISTORE 6; Stack: 1
        clocal6.i = cstack0.i;
        // New stack: 0
        // ALOAD 3; Stack: 0
        cstack0.l = clocal3.l; refs.insert(cstack0.l);
        // New stack: 1
        // ILOAD 1; Stack: 1
        cstack1.i = clocal1.i;
        // New stack: 2
        // IINC 1 1; Stack: 2
        clocal1.i += 1;
        // New stack: 2
        // ILOAD 6; Stack: 2
        cstack2.i = clocal6.i;
        // New stack: 3
        // CASTORE; Stack: 3
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 6071LL)), -1); else { jchar temp = (jchar) cstack2.i; env->SetCharArrayRegion((jcharArray) cstack0.l, cstack1.i, 1, &temp); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 0
        // LABEL L4; Stack: 0
        L4: if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 0
        // FRAME SAME L: null S: null; Stack: 0
        refs.erase(clocal0.l); refs.erase(clocal3.l); 
        utils::clear_refs(env, refs);
        // New stack: 0
        // IINC 4 1; Stack: 0
        clocal4.i += 1;
        // New stack: 0
        // GOTO L1; Stack: 0
        goto L1;
        // New stack: 0
        // LABEL L2; Stack: 0
        L2: if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 0
        // FRAME CHOP L: [null] S: null; Stack: 0
        refs.erase(clocal0.l); refs.erase(clocal3.l); 
        utils::clear_refs(env, refs);
        // New stack: 0
        // NEW java/lang/String; Stack: 0
        if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { cclasses_mtx[13].lock(); if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[13] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[13].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (jobject obj = env->AllocObject((cclasses[13]))) { cstack0.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // ALOAD 3; Stack: 2
        cstack2.l = clocal3.l; refs.insert(cstack2.l);
        // New stack: 3
        // ICONST_0; Stack: 3
        cstack3.i = 0;
        // New stack: 4
        // ILOAD 1; Stack: 4
        cstack4.i = clocal1.i;
        // New stack: 5
        // INVOKESPECIAL java/lang/String.<init>([CII)V; Stack: 5
        if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { cclasses_mtx[13].lock(); if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[13] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[13].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[30]) { cmethods[30] = env->GetMethodID((cclasses[13]), ((char *)(string_pool + 665LL)), ((char *)(string_pool + 6083LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 697LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[13]), (cmethods[30]), cstack2.l, cstack3.i, cstack4.i); 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // ARETURN; Stack: 1
        return (jobject) cstack0.l;
        // New stack: 0
        return (jobject) 0;
    }
    
    // a(IJ)Ljava/lang/String;
    jobject JNICALL __ngen_native_a5(JNIEnv *env, jclass clazz, jint arg0, jlong arg1) {
        jobject classloader = utils::get_classloader_from_class(env, clazz);
        if (env->ExceptionCheck()) { return (jobject) 0; }
        if (classloader == nullptr) { env->FatalError(((char *)(string_pool + 71LL))); return (jobject) 0; }
    
        jobject lookup = nullptr;
        // try-catch-class java/lang/Exception
        if (!cclasses[0] || env->IsSameObject(cclasses[0], NULL)) { cclasses_mtx[0].lock(); if (!cclasses[0] || env->IsSameObject(cclasses[0], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[0]))) { cclasses[0] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[0].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } }
        jvalue cstack0 = {}, cstack1 = {}, cstack2 = {}, cstack3 = {}, cstack4 = {}, cstack5 = {};
        jvalue clocal0 = {}, clocal1 = {}, clocal2 = {}, clocal3 = {}, clocal4 = {}, clocal5 = {}, clocal6 = {}, clocal7 = {}, clocal8 = {}, clocal9 = {};
        std::unordered_set<jobject> refs;
    
        clocal0.i = arg0;
        clocal1.j = arg1;
    
        // ILOAD 0; Stack: 0
        cstack0.i = clocal0.i;
        // New stack: 1
        // LLOAD 1; Stack: 1
        cstack1.j = clocal1.j;
        // New stack: 3
        // LDC 32767; Stack: 3
        cstack3.j = 32767LL;
        // New stack: 5
        // LAND; Stack: 5
        cstack1.j = cstack1.j & cstack3.j;
        // New stack: 3
        // L2I; Stack: 3
        cstack1.i = (jint) cstack1.j;
        // New stack: 2
        // IXOR; Stack: 2
        cstack0.i = cstack0.i ^ cstack1.i;
        // New stack: 1
        // SIPUSH 29070; Stack: 1
        cstack1.i = (jint) 29070;
        // New stack: 2
        // IXOR; Stack: 2
        cstack0.i = cstack0.i ^ cstack1.i;
        // New stack: 1
        // ISTORE 5; Stack: 1
        clocal5.i = cstack0.i;
        // New stack: 0
        // GETSTATIC dev/mahiro/client/Mahiro_uP.c [Ljava/lang/String;; Stack: 0
        if (!cclasses[4]  || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cfields[3]) { cfields[3] = env->GetStaticFieldID((cclasses[4]), ((char *)(string_pool + 5723LL)), ((char *)(string_pool + 5703LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } cstack0.l = env->GetStaticObjectField((cclasses[4]), (cfields[3])); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // ILOAD 5; Stack: 1
        cstack1.i = clocal5.i;
        // New stack: 2
        // AALOAD; Stack: 2
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 122LL)), -1); else { cstack0.l = env->GetObjectArrayElement((jobjectArray) cstack0.l, cstack1.i); refs.insert(cstack0.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // IFNONNULL L4; Stack: 1
        if (!env->IsSameObject(cstack0.l, nullptr)) goto L4;
        // New stack: 0
        // LABEL L1; Stack: 0
        L1: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 0
        // INVOKESTATIC java/lang/Thread.currentThread()Ljava/lang/Thread;; Stack: 0
        if (!cclasses[28] || env->IsSameObject(cclasses[28], NULL)) { cclasses_mtx[28].lock(); if (!cclasses[28] || env->IsSameObject(cclasses[28], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[41]))) { cclasses[28] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[28].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[31]) { cmethods[31] = env->GetStaticMethodID((cclasses[28]), ((char *)(string_pool + 6114LL)), ((char *)(string_pool + 6128LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack0.l = env->CallStaticObjectMethod((cclasses[28]), (cmethods[31])); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 1
        // INVOKEVIRTUAL java/lang/Thread.threadId()J; Stack: 1
        if (!cclasses[28] || env->IsSameObject(cclasses[28], NULL)) { cclasses_mtx[28].lock(); if (!cclasses[28] || env->IsSameObject(cclasses[28], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[41]))) { cclasses[28] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[28].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[32]) { cmethods[32] = env->GetMethodID((cclasses[28]), ((char *)(string_pool + 6149LL)), ((char *)(string_pool + 220LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 224LL)), -1); else cstack0.j = env->CallLongMethod(cstack0.l, (cmethods[32])); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 2
        // INVOKESTATIC java/lang/Long.valueOf(J)Ljava/lang/Long;; Stack: 2
        if (!cclasses[3] || env->IsSameObject(cclasses[3], NULL)) { cclasses_mtx[3].lock(); if (!cclasses[3] || env->IsSameObject(cclasses[3], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[2]))) { cclasses[3] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[3].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[33]) { cmethods[33] = env->GetStaticMethodID((cclasses[3]), ((char *)(string_pool + 1076LL)), ((char *)(string_pool + 1823LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack0.l = env->CallStaticObjectMethod((cclasses[3]), (cmethods[33]), cstack0.j); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 1
        // ASTORE 3; Stack: 1
        clocal3.l = cstack0.l; refs.insert(cstack0.l);
        // New stack: 0
        // GETSTATIC dev/mahiro/client/Mahiro_uP.d Ljava/util/Map;; Stack: 0
        if (!cclasses[4]  || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cfields[1]) { cfields[1] = env->GetStaticFieldID((cclasses[4]), ((char *)(string_pool + 5290LL)), ((char *)(string_pool + 5292LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack0.l = env->GetStaticObjectField((cclasses[4]), (cfields[1])); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 1
        // ALOAD 3; Stack: 1
        cstack1.l = clocal3.l; refs.insert(cstack1.l);
        // New stack: 2
        // INVOKEINTERFACE java/util/Map.get(Ljava/lang/Object;)Ljava/lang/Object;; Stack: 2
        if (!cclasses[29] || env->IsSameObject(cclasses[29], NULL)) { cclasses_mtx[29].lock(); if (!cclasses[29] || env->IsSameObject(cclasses[29], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[42]))) { cclasses[29] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[29].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[34]) { cmethods[34] = env->GetMethodID((cclasses[29]), ((char *)(string_pool + 6158LL)), ((char *)(string_pool + 6162LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 608LL)), -1); else { cstack0.l = env->CallObjectMethod(cstack0.l, (cmethods[34]), cstack1.l); refs.insert(cstack0.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 1
        // CHECKCAST [Ljava/lang/Object;; Stack: 1
        if (!cclasses[30] || env->IsSameObject(cclasses[30], NULL)) { cclasses_mtx[30].lock(); if (!cclasses[30] || env->IsSameObject(cclasses[30], NULL)) { if (jclass clazz = env->FindClass(((char *)(string_pool + 6201LL)))) { cclasses[30] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[30].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack0.l != nullptr && !env->IsInstanceOf(cstack0.l, (cclasses[30]))) { utils::throw_re(env, ((char *)(string_pool + 133LL)), (std::string(((char *)(string_pool + 162LL))) + std::string(((char *)(string_pool + 6201LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } 
        // New stack: 1
        // ASTORE 4; Stack: 1
        clocal4.l = cstack0.l; refs.insert(cstack0.l);
        // New stack: 0
        // ALOAD 4; Stack: 0
        cstack0.l = clocal4.l; refs.insert(cstack0.l);
        // New stack: 1
        // IFNONNULL L5; Stack: 1
        if (!env->IsSameObject(cstack0.l, nullptr)) goto L5;
        // New stack: 0
        // ICONST_3; Stack: 0
        cstack0.i = 3;
        // New stack: 1
        // ANEWARRAY java/lang/Object; Stack: 1
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack0.i < 0) utils::throw_re(env, ((char *)(string_pool + 251LL)), ((char *)(string_pool + 288LL)), -1); else { cstack0.l = env->NewObjectArray(cstack0.i, (cclasses[5]), nullptr); refs.insert(cstack0.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 1
        // ASTORE 4; Stack: 1
        clocal4.l = cstack0.l; refs.insert(cstack0.l);
        // New stack: 0
        // ALOAD 4; Stack: 0
        cstack0.l = clocal4.l; refs.insert(cstack0.l);
        // New stack: 1
        // ICONST_0; Stack: 1
        cstack1.i = 0;
        // New stack: 2
        // LDC DES/CBC/PKCS5Padding; Stack: 2
        cstack2.l = (cstrings[33]);
        // New stack: 3
        // INVOKESTATIC javax/crypto/Cipher.getInstance(Ljava/lang/String;)Ljavax/crypto/Cipher;; Stack: 3
        if (!cclasses[24] || env->IsSameObject(cclasses[24], NULL)) { cclasses_mtx[24].lock(); if (!cclasses[24] || env->IsSameObject(cclasses[24], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[34]))) { cclasses[24] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[24].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[17]) { cmethods[17] = env->GetStaticMethodID((cclasses[24]), ((char *)(string_pool + 5308LL)), ((char *)(string_pool + 5320LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack2.l = env->CallStaticObjectMethod((cclasses[24]), (cmethods[17]), cstack2.l); refs.insert(cstack2.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 3
        // AASTORE; Stack: 3
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 1107LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack0.l, cstack1.i, cstack2.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 0
        // ALOAD 4; Stack: 0
        cstack0.l = clocal4.l; refs.insert(cstack0.l);
        // New stack: 1
        // ICONST_1; Stack: 1
        cstack1.i = 1;
        // New stack: 2
        // LDC DES; Stack: 2
        cstack2.l = (cstrings[35]);
        // New stack: 3
        // INVOKESTATIC javax/crypto/SecretKeyFactory.getInstance(Ljava/lang/String;)Ljavax/crypto/SecretKeyFactory;; Stack: 3
        if (!cclasses[25] || env->IsSameObject(cclasses[25], NULL)) { cclasses_mtx[25].lock(); if (!cclasses[25] || env->IsSameObject(cclasses[25], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[36]))) { cclasses[25] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[25].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[18]) { cmethods[18] = env->GetStaticMethodID((cclasses[25]), ((char *)(string_pool + 5308LL)), ((char *)(string_pool + 5362LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack2.l = env->CallStaticObjectMethod((cclasses[25]), (cmethods[18]), cstack2.l); refs.insert(cstack2.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 3
        // AASTORE; Stack: 3
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 1107LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack0.l, cstack1.i, cstack2.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 0
        // ALOAD 4; Stack: 0
        cstack0.l = clocal4.l; refs.insert(cstack0.l);
        // New stack: 1
        // ICONST_2; Stack: 1
        cstack1.i = 2;
        // New stack: 2
        // NEW javax/crypto/spec/IvParameterSpec; Stack: 2
        if (!cclasses[27] || env->IsSameObject(cclasses[27], NULL)) { cclasses_mtx[27].lock(); if (!cclasses[27] || env->IsSameObject(cclasses[27], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[38]))) { cclasses[27] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[27].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (jobject obj = env->AllocObject((cclasses[27]))) { cstack2.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 3
        // DUP; Stack: 3
        cstack3 = cstack2;
        // New stack: 4
        // BIPUSH 8; Stack: 4
        cstack4.i = (jint) 8;
        // New stack: 5
        // NEWARRAY 8; Stack: 5
        if (cstack4.i < 0) utils::throw_re(env, ((char *)(string_pool + 251LL)), ((char *)(string_pool + 5414LL)), -1); else { cstack4.l = env->NewByteArray(cstack4.i); refs.insert(cstack4.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 5
        // INVOKESPECIAL javax/crypto/spec/IvParameterSpec.<init>([B)V; Stack: 5
        if (!cclasses[27] || env->IsSameObject(cclasses[27], NULL)) { cclasses_mtx[27].lock(); if (!cclasses[27] || env->IsSameObject(cclasses[27], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[38]))) { cclasses[27] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[27].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[21]) { cmethods[21] = env->GetMethodID((cclasses[27]), ((char *)(string_pool + 665LL)), ((char *)(string_pool + 5455LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack3.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 697LL)), -1); else env->CallNonvirtualVoidMethod(cstack3.l, (cclasses[27]), (cmethods[21]), cstack4.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 3
        // AASTORE; Stack: 3
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 1107LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack0.l, cstack1.i, cstack2.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 0
        // GETSTATIC dev/mahiro/client/Mahiro_uP.d Ljava/util/Map;; Stack: 0
        if (!cclasses[4]  || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cfields[1]) { cfields[1] = env->GetStaticFieldID((cclasses[4]), ((char *)(string_pool + 5290LL)), ((char *)(string_pool + 5292LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack0.l = env->GetStaticObjectField((cclasses[4]), (cfields[1])); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 1
        // ALOAD 3; Stack: 1
        cstack1.l = clocal3.l; refs.insert(cstack1.l);
        // New stack: 2
        // ALOAD 4; Stack: 2
        cstack2.l = clocal4.l; refs.insert(cstack2.l);
        // New stack: 3
        // INVOKEINTERFACE java/util/Map.put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;; Stack: 3
        if (!cclasses[29] || env->IsSameObject(cclasses[29], NULL)) { cclasses_mtx[29].lock(); if (!cclasses[29] || env->IsSameObject(cclasses[29], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[42]))) { cclasses[29] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[29].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[35]) { cmethods[35] = env->GetMethodID((cclasses[29]), ((char *)(string_pool + 6221LL)), ((char *)(string_pool + 6225LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 608LL)), -1); else { cstack0.l = env->CallObjectMethod(cstack0.l, (cmethods[35]), cstack1.l, cstack2.l); refs.insert(cstack0.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 1
        // POP; Stack: 1
        ;
        // New stack: 0
        // LABEL L2; Stack: 0
        L2: if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 0
        // GOTO L5; Stack: 0
        goto L5;
        // New stack: 0
        // LABEL L3; Stack: 0
        L3: if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 0
        // FRAME FULL L: [1, 4, 0, 0, 1] S: [java/lang/Exception]; Stack: 0
        refs.erase(cstack0.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ASTORE 9; Stack: 1
        clocal9.l = cstack0.l; refs.insert(cstack0.l);
        // New stack: 0
        // NEW java/lang/RuntimeException; Stack: 0
        if (!cclasses[31] || env->IsSameObject(cclasses[31], NULL)) { cclasses_mtx[31].lock(); if (!cclasses[31] || env->IsSameObject(cclasses[31], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[43]))) { cclasses[31] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[31].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (jobject obj = env->AllocObject((cclasses[31]))) { cstack0.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // LDC dev/mahiro/client/Mahiro_uP; Stack: 2
        cstack2.l = (cstrings[44]);
        // New stack: 3
        // ALOAD 9; Stack: 3
        cstack3.l = clocal9.l; refs.insert(cstack3.l);
        // New stack: 4
        // INVOKESPECIAL java/lang/RuntimeException.<init>(Ljava/lang/String;Ljava/lang/Throwable;)V; Stack: 4
        if (!cclasses[31] || env->IsSameObject(cclasses[31], NULL)) { cclasses_mtx[31].lock(); if (!cclasses[31] || env->IsSameObject(cclasses[31], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[43]))) { cclasses[31] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[31].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[36]) { cmethods[36] = env->GetMethodID((cclasses[31]), ((char *)(string_pool + 665LL)), ((char *)(string_pool + 6282LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 697LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[31]), (cmethods[36]), cstack2.l, cstack3.l); 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 720LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 0
        // LABEL L5; Stack: 0
        L5: if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 0
        // FRAME FULL L: [1, 4, java/lang/Long, [Ljava/lang/Object;, 1] S: []; Stack: 0
        refs.erase(clocal3.l); refs.erase(clocal4.l); 
        utils::clear_refs(env, refs);
        // New stack: 0
        // BIPUSH 8; Stack: 0
        cstack0.i = (jint) 8;
        // New stack: 1
        // NEWARRAY 8; Stack: 1
        if (cstack0.i < 0) utils::throw_re(env, ((char *)(string_pool + 251LL)), ((char *)(string_pool + 5414LL)), -1); else { cstack0.l = env->NewByteArray(cstack0.i); refs.insert(cstack0.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // ASTORE 6; Stack: 1
        clocal6.l = cstack0.l; refs.insert(cstack0.l);
        // New stack: 0
        // ALOAD 6; Stack: 0
        cstack0.l = clocal6.l; refs.insert(cstack0.l);
        // New stack: 1
        // ICONST_0; Stack: 1
        cstack1.i = 0;
        // New stack: 2
        // LLOAD 1; Stack: 2
        cstack2.j = clocal1.j;
        // New stack: 4
        // BIPUSH 56; Stack: 4
        cstack4.i = (jint) 56;
        // New stack: 5
        // LUSHR; Stack: 5
        cstack2.j = (jlong) (((uint64_t) cstack2.j) >> (((uint64_t) cstack4.i) & 0x3f));
        // New stack: 4
        // L2I; Stack: 4
        cstack2.i = (jint) cstack2.j;
        // New stack: 3
        // I2B; Stack: 3
        cstack2.i = (jint) (jbyte) cstack2.i;
        // New stack: 3
        // BASTORE; Stack: 3
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 5443LL)), -1); else { utils::bastore(env, (jarray) cstack0.l, cstack1.i, cstack2.i); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 0
        // ICONST_1; Stack: 0
        cstack0.i = 1;
        // New stack: 1
        // ISTORE 7; Stack: 1
        clocal7.i = cstack0.i;
        // New stack: 0
        // LABEL L6; Stack: 0
        L6: if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 0
        // FRAME APPEND L: [[B, 1] S: null; Stack: 0
        refs.erase(clocal3.l); refs.erase(clocal4.l); refs.erase(clocal6.l); 
        utils::clear_refs(env, refs);
        // New stack: 0
        // ILOAD 7; Stack: 0
        cstack0.i = clocal7.i;
        // New stack: 1
        // BIPUSH 8; Stack: 1
        cstack1.i = (jint) 8;
        // New stack: 2
        // IF_ICMPGE L7; Stack: 2
        if (cstack0.i >= cstack1.i) goto L7;
        // New stack: 0
        // ALOAD 6; Stack: 0
        cstack0.l = clocal6.l; refs.insert(cstack0.l);
        // New stack: 1
        // ILOAD 7; Stack: 1
        cstack1.i = clocal7.i;
        // New stack: 2
        // LLOAD 1; Stack: 2
        cstack2.j = clocal1.j;
        // New stack: 4
        // ILOAD 7; Stack: 4
        cstack4.i = clocal7.i;
        // New stack: 5
        // BIPUSH 8; Stack: 5
        cstack5.i = (jint) 8;
        // New stack: 6
        // IMUL; Stack: 6
        cstack4.i = cstack4.i * cstack5.i;
        // New stack: 5
        // LSHL; Stack: 5
        cstack2.j = cstack2.j << (0x3f & cstack4.i);
        // New stack: 4
        // BIPUSH 56; Stack: 4
        cstack4.i = (jint) 56;
        // New stack: 5
        // LUSHR; Stack: 5
        cstack2.j = (jlong) (((uint64_t) cstack2.j) >> (((uint64_t) cstack4.i) & 0x3f));
        // New stack: 4
        // L2I; Stack: 4
        cstack2.i = (jint) cstack2.j;
        // New stack: 3
        // I2B; Stack: 3
        cstack2.i = (jint) (jbyte) cstack2.i;
        // New stack: 3
        // BASTORE; Stack: 3
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 5443LL)), -1); else { utils::bastore(env, (jarray) cstack0.l, cstack1.i, cstack2.i); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 0
        // IINC 7 1; Stack: 0
        clocal7.i += 1;
        // New stack: 0
        // GOTO L6; Stack: 0
        goto L6;
        // New stack: 0
        // LABEL L7; Stack: 0
        L7: if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 0
        // FRAME SAME L: null S: null; Stack: 0
        refs.erase(clocal3.l); refs.erase(clocal4.l); refs.erase(clocal6.l); 
        utils::clear_refs(env, refs);
        // New stack: 0
        // NEW javax/crypto/spec/DESKeySpec; Stack: 0
        if (!cclasses[26] || env->IsSameObject(cclasses[26], NULL)) { cclasses_mtx[26].lock(); if (!cclasses[26] || env->IsSameObject(cclasses[26], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[37]))) { cclasses[26] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[26].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (jobject obj = env->AllocObject((cclasses[26]))) { cstack0.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // ALOAD 6; Stack: 2
        cstack2.l = clocal6.l; refs.insert(cstack2.l);
        // New stack: 3
        // INVOKESPECIAL javax/crypto/spec/DESKeySpec.<init>([B)V; Stack: 3
        if (!cclasses[26] || env->IsSameObject(cclasses[26], NULL)) { cclasses_mtx[26].lock(); if (!cclasses[26] || env->IsSameObject(cclasses[26], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[37]))) { cclasses[26] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[26].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[19]) { cmethods[19] = env->GetMethodID((cclasses[26]), ((char *)(string_pool + 665LL)), ((char *)(string_pool + 5455LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 697LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[26]), (cmethods[19]), cstack2.l); 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // ASTORE 7; Stack: 1
        clocal7.l = cstack0.l; refs.insert(cstack0.l);
        // New stack: 0
        // ALOAD 4; Stack: 0
        cstack0.l = clocal4.l; refs.insert(cstack0.l);
        // New stack: 1
        // ICONST_1; Stack: 1
        cstack1.i = 1;
        // New stack: 2
        // AALOAD; Stack: 2
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 122LL)), -1); else { cstack0.l = env->GetObjectArrayElement((jobjectArray) cstack0.l, cstack1.i); refs.insert(cstack0.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // CHECKCAST javax/crypto/SecretKeyFactory; Stack: 1
        if (!cclasses[25] || env->IsSameObject(cclasses[25], NULL)) { cclasses_mtx[25].lock(); if (!cclasses[25] || env->IsSameObject(cclasses[25], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[36]))) { cclasses[25] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[25].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (cstack0.l != nullptr && !env->IsInstanceOf(cstack0.l, (cclasses[25]))) { utils::throw_re(env, ((char *)(string_pool + 133LL)), (std::string(((char *)(string_pool + 162LL))) + std::string(((char *)(string_pool + 6325LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (jobject) 0; } } 
        // New stack: 1
        // ALOAD 7; Stack: 1
        cstack1.l = clocal7.l; refs.insert(cstack1.l);
        // New stack: 2
        // INVOKEVIRTUAL javax/crypto/SecretKeyFactory.generateSecret(Ljava/security/spec/KeySpec;)Ljavax/crypto/SecretKey;; Stack: 2
        if (!cclasses[25] || env->IsSameObject(cclasses[25], NULL)) { cclasses_mtx[25].lock(); if (!cclasses[25] || env->IsSameObject(cclasses[25], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[36]))) { cclasses[25] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[25].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[20]) { cmethods[20] = env->GetMethodID((cclasses[25]), ((char *)(string_pool + 5461LL)), ((char *)(string_pool + 5476LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 522LL)), -1); else { cstack0.l = env->CallObjectMethod(cstack0.l, (cmethods[20]), cstack1.l); refs.insert(cstack0.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // ASTORE 8; Stack: 1
        clocal8.l = cstack0.l; refs.insert(cstack0.l);
        // New stack: 0
        // ALOAD 4; Stack: 0
        cstack0.l = clocal4.l; refs.insert(cstack0.l);
        // New stack: 1
        // ICONST_0; Stack: 1
        cstack1.i = 0;
        // New stack: 2
        // AALOAD; Stack: 2
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 122LL)), -1); else { cstack0.l = env->GetObjectArrayElement((jobjectArray) cstack0.l, cstack1.i); refs.insert(cstack0.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // CHECKCAST javax/crypto/Cipher; Stack: 1
        if (!cclasses[24] || env->IsSameObject(cclasses[24], NULL)) { cclasses_mtx[24].lock(); if (!cclasses[24] || env->IsSameObject(cclasses[24], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[34]))) { cclasses[24] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[24].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (cstack0.l != nullptr && !env->IsInstanceOf(cstack0.l, (cclasses[24]))) { utils::throw_re(env, ((char *)(string_pool + 133LL)), (std::string(((char *)(string_pool + 162LL))) + std::string(((char *)(string_pool + 6355LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (jobject) 0; } } 
        // New stack: 1
        // ICONST_2; Stack: 1
        cstack1.i = 2;
        // New stack: 2
        // ALOAD 8; Stack: 2
        cstack2.l = clocal8.l; refs.insert(cstack2.l);
        // New stack: 3
        // ALOAD 4; Stack: 3
        cstack3.l = clocal4.l; refs.insert(cstack3.l);
        // New stack: 4
        // ICONST_2; Stack: 4
        cstack4.i = 2;
        // New stack: 5
        // AALOAD; Stack: 5
        if (cstack3.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 122LL)), -1); else { cstack3.l = env->GetObjectArrayElement((jobjectArray) cstack3.l, cstack4.i); refs.insert(cstack3.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 4
        // CHECKCAST javax/crypto/spec/IvParameterSpec; Stack: 4
        if (!cclasses[27] || env->IsSameObject(cclasses[27], NULL)) { cclasses_mtx[27].lock(); if (!cclasses[27] || env->IsSameObject(cclasses[27], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[38]))) { cclasses[27] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[27].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (cstack3.l != nullptr && !env->IsInstanceOf(cstack3.l, (cclasses[27]))) { utils::throw_re(env, ((char *)(string_pool + 133LL)), (std::string(((char *)(string_pool + 162LL))) + std::string(((char *)(string_pool + 6375LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (jobject) 0; } } 
        // New stack: 4
        // INVOKEVIRTUAL javax/crypto/Cipher.init(ILjava/security/Key;Ljava/security/spec/AlgorithmParameterSpec;)V; Stack: 4
        if (!cclasses[24] || env->IsSameObject(cclasses[24], NULL)) { cclasses_mtx[24].lock(); if (!cclasses[24] || env->IsSameObject(cclasses[24], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[34]))) { cclasses[24] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[24].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[22]) { cmethods[22] = env->GetMethodID((cclasses[24]), ((char *)(string_pool + 5531LL)), ((char *)(string_pool + 5536LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 5603LL)), -1); else env->CallVoidMethod(cstack0.l, (cmethods[22]), cstack1.i, cstack2.l, cstack3.l); 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 0
        // GETSTATIC dev/mahiro/client/Mahiro_uP.b [Ljava/lang/String;; Stack: 0
        if (!cclasses[4]  || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cfields[2]) { cfields[2] = env->GetStaticFieldID((cclasses[4]), ((char *)(string_pool + 5701LL)), ((char *)(string_pool + 5703LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } cstack0.l = env->GetStaticObjectField((cclasses[4]), (cfields[2])); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // ILOAD 5; Stack: 1
        cstack1.i = clocal5.i;
        // New stack: 2
        // AALOAD; Stack: 2
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 122LL)), -1); else { cstack0.l = env->GetObjectArrayElement((jobjectArray) cstack0.l, cstack1.i); refs.insert(cstack0.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // LDC ISO-8859-1; Stack: 1
        cstack1.l = (cstrings[40]);
        // New stack: 2
        // INVOKEVIRTUAL java/lang/String.getBytes(Ljava/lang/String;)[B; Stack: 2
        if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { cclasses_mtx[13].lock(); if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[13] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[13].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[26]) { cmethods[26] = env->GetMethodID((cclasses[13]), ((char *)(string_pool + 5753LL)), ((char *)(string_pool + 5762LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 522LL)), -1); else { cstack0.l = env->CallObjectMethod(cstack0.l, (cmethods[26]), cstack1.l); refs.insert(cstack0.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // ASTORE 9; Stack: 1
        clocal9.l = cstack0.l; refs.insert(cstack0.l);
        // New stack: 0
        // GETSTATIC dev/mahiro/client/Mahiro_uP.c [Ljava/lang/String;; Stack: 0
        if (!cclasses[4]  || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cfields[3]) { cfields[3] = env->GetStaticFieldID((cclasses[4]), ((char *)(string_pool + 5723LL)), ((char *)(string_pool + 5703LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } cstack0.l = env->GetStaticObjectField((cclasses[4]), (cfields[3])); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // ILOAD 5; Stack: 1
        cstack1.i = clocal5.i;
        // New stack: 2
        // ALOAD 4; Stack: 2
        cstack2.l = clocal4.l; refs.insert(cstack2.l);
        // New stack: 3
        // ICONST_0; Stack: 3
        cstack3.i = 0;
        // New stack: 4
        // AALOAD; Stack: 4
        if (cstack2.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 122LL)), -1); else { cstack2.l = env->GetObjectArrayElement((jobjectArray) cstack2.l, cstack3.i); refs.insert(cstack2.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 3
        // CHECKCAST javax/crypto/Cipher; Stack: 3
        if (!cclasses[24] || env->IsSameObject(cclasses[24], NULL)) { cclasses_mtx[24].lock(); if (!cclasses[24] || env->IsSameObject(cclasses[24], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[34]))) { cclasses[24] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[24].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (cstack2.l != nullptr && !env->IsInstanceOf(cstack2.l, (cclasses[24]))) { utils::throw_re(env, ((char *)(string_pool + 133LL)), (std::string(((char *)(string_pool + 162LL))) + std::string(((char *)(string_pool + 6355LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (jobject) 0; } } 
        // New stack: 3
        // ALOAD 9; Stack: 3
        cstack3.l = clocal9.l; refs.insert(cstack3.l);
        // New stack: 4
        // INVOKEVIRTUAL javax/crypto/Cipher.doFinal([B)[B; Stack: 4
        if (!cclasses[24] || env->IsSameObject(cclasses[24], NULL)) { cclasses_mtx[24].lock(); if (!cclasses[24] || env->IsSameObject(cclasses[24], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[34]))) { cclasses[24] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[24].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[27]) { cmethods[27] = env->GetMethodID((cclasses[24]), ((char *)(string_pool + 5785LL)), ((char *)(string_pool + 5793LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack2.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 522LL)), -1); else { cstack2.l = env->CallObjectMethod(cstack2.l, (cmethods[27]), cstack3.l); refs.insert(cstack2.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 3
        // INVOKESTATIC dev/mahiro/client/Mahiro_uP.a([B)Ljava/lang/String;; Stack: 3
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[28]) { cmethods[28] = env->GetStaticMethodID((cclasses[4]), ((char *)(string_pool + 247LL)), ((char *)(string_pool + 5800LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } cstack2.l = env->CallStaticObjectMethod((cclasses[4]), (cmethods[28]), cstack2.l); refs.insert(cstack2.l); 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 3
        // AASTORE; Stack: 3
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 1107LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack0.l, cstack1.i, cstack2.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 0
        // LABEL L4; Stack: 0
        L4: if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 0
        // FRAME FULL L: [1, 4, 0, 0, 1] S: []; Stack: 0
        utils::clear_refs(env, refs);
        // New stack: 0
        // GETSTATIC dev/mahiro/client/Mahiro_uP.c [Ljava/lang/String;; Stack: 0
        if (!cclasses[4]  || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cfields[3]) { cfields[3] = env->GetStaticFieldID((cclasses[4]), ((char *)(string_pool + 5723LL)), ((char *)(string_pool + 5703LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } cstack0.l = env->GetStaticObjectField((cclasses[4]), (cfields[3])); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // ILOAD 5; Stack: 1
        cstack1.i = clocal5.i;
        // New stack: 2
        // AALOAD; Stack: 2
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 122LL)), -1); else { cstack0.l = env->GetObjectArrayElement((jobjectArray) cstack0.l, cstack1.i); refs.insert(cstack0.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // ARETURN; Stack: 1
        return (jobject) cstack0.l;
        // New stack: 0
        return (jobject) 0;
        L_CATCH_0: if (env->IsInstanceOf(cstack0.l, (cclasses[0]))) { goto L3; }
        env->Throw((jthrowable) cstack0.l); return (jobject) 0;
    }
    
    // a(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/invoke/MutableCallSite;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;
    jobject JNICALL __ngen_native_a6(JNIEnv *env, jclass clazz, jobject arg0, jobject arg1, jobject arg2, jarray arg3) {
        jobject classloader = utils::get_classloader_from_class(env, clazz);
        if (env->ExceptionCheck()) { return (jobject) 0; }
        if (classloader == nullptr) { env->FatalError(((char *)(string_pool + 71LL))); return (jobject) 0; }
    
        jobject lookup = nullptr;
        jvalue cstack0 = {}, cstack1 = {}, cstack2 = {}, cstack3 = {}, cstack4 = {}, cstack5 = {}, cstack6 = {};
        jvalue clocal0 = {}, clocal1 = {}, clocal2 = {}, clocal3 = {}, clocal4 = {}, clocal5 = {}, clocal6 = {}, clocal7 = {}, clocal8 = {};
        std::unordered_set<jobject> refs;
    
        clocal0.l = arg0; refs.insert(clocal0.l);
        clocal1.l = arg1; refs.insert(clocal1.l);
        clocal2.l = arg2; refs.insert(clocal2.l);
        clocal3.l = arg3; refs.insert(clocal3.l);
    
        // ALOAD 3; Stack: 0
        cstack0.l = clocal3.l; refs.insert(cstack0.l);
        // New stack: 1
        // ICONST_0; Stack: 1
        cstack1.i = 0;
        // New stack: 2
        // AALOAD; Stack: 2
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 122LL)), -1); else { cstack0.l = env->GetObjectArrayElement((jobjectArray) cstack0.l, cstack1.i); refs.insert(cstack0.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // CHECKCAST java/lang/Integer; Stack: 1
        if (!cclasses[32] || env->IsSameObject(cclasses[32], NULL)) { cclasses_mtx[32].lock(); if (!cclasses[32] || env->IsSameObject(cclasses[32], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[45]))) { cclasses[32] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[32].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (cstack0.l != nullptr && !env->IsInstanceOf(cstack0.l, (cclasses[32]))) { utils::throw_re(env, ((char *)(string_pool + 133LL)), (std::string(((char *)(string_pool + 162LL))) + std::string(((char *)(string_pool + 3192LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (jobject) 0; } } 
        // New stack: 1
        // INVOKEVIRTUAL java/lang/Integer.intValue()I; Stack: 1
        if (!cclasses[32] || env->IsSameObject(cclasses[32], NULL)) { cclasses_mtx[32].lock(); if (!cclasses[32] || env->IsSameObject(cclasses[32], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[45]))) { cclasses[32] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[32].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[37]) { cmethods[37] = env->GetMethodID((cclasses[32]), ((char *)(string_pool + 3210LL)), ((char *)(string_pool + 3219LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 3223LL)), -1); else cstack0.i = env->CallIntMethod(cstack0.l, (cmethods[37])); 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // ISTORE 4; Stack: 1
        clocal4.i = cstack0.i;
        // New stack: 0
        // ALOAD 3; Stack: 0
        cstack0.l = clocal3.l; refs.insert(cstack0.l);
        // New stack: 1
        // ICONST_1; Stack: 1
        cstack1.i = 1;
        // New stack: 2
        // AALOAD; Stack: 2
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 122LL)), -1); else { cstack0.l = env->GetObjectArrayElement((jobjectArray) cstack0.l, cstack1.i); refs.insert(cstack0.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // CHECKCAST java/lang/Long; Stack: 1
        if (!cclasses[3] || env->IsSameObject(cclasses[3], NULL)) { cclasses_mtx[3].lock(); if (!cclasses[3] || env->IsSameObject(cclasses[3], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[2]))) { cclasses[3] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[3].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (cstack0.l != nullptr && !env->IsInstanceOf(cstack0.l, (cclasses[3]))) { utils::throw_re(env, ((char *)(string_pool + 133LL)), (std::string(((char *)(string_pool + 162LL))) + std::string(((char *)(string_pool + 195LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (jobject) 0; } } 
        // New stack: 1
        // INVOKEVIRTUAL java/lang/Long.longValue()J; Stack: 1
        if (!cclasses[3] || env->IsSameObject(cclasses[3], NULL)) { cclasses_mtx[3].lock(); if (!cclasses[3] || env->IsSameObject(cclasses[3], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[2]))) { cclasses[3] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[3].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[0]) { cmethods[0] = env->GetMethodID((cclasses[3]), ((char *)(string_pool + 210LL)), ((char *)(string_pool + 220LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 224LL)), -1); else cstack0.j = env->CallLongMethod(cstack0.l, (cmethods[0])); 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 2
        // LSTORE 5; Stack: 2
        clocal5.j = cstack0.j;
        // New stack: 0
        // ILOAD 4; Stack: 0
        cstack0.i = clocal4.i;
        // New stack: 1
        // LLOAD 5; Stack: 1
        cstack1.j = clocal5.j;
        // New stack: 3
        // INVOKESTATIC dev/mahiro/client/Mahiro_uP.a(IJ)Ljava/lang/String;; Stack: 3
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[38]) { cmethods[38] = env->GetStaticMethodID((cclasses[4]), ((char *)(string_pool + 247LL)), ((char *)(string_pool + 6091LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } cstack0.l = env->CallStaticObjectMethod((cclasses[4]), (cmethods[38]), cstack0.i, cstack1.j); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // ASTORE 7; Stack: 1
        clocal7.l = cstack0.l; refs.insert(cstack0.l);
        // New stack: 0
        // LDC Ljava/lang/String;; Stack: 0
        if (!cclasses[33] || env->IsSameObject(cclasses[33], NULL)) { cclasses_mtx[33].lock(); if (!cclasses[33] || env->IsSameObject(cclasses[33], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[33] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[33].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } cstack0.l = (cclasses[33]);
        // New stack: 1
        // ALOAD 7; Stack: 1
        cstack1.l = clocal7.l; refs.insert(cstack1.l);
        // New stack: 2
        // INVOKESTATIC java/lang/invoke/MethodHandles.constant(Ljava/lang/Class;Ljava/lang/Object;)Ljava/lang/invoke/MethodHandle;; Stack: 2
        if (!cclasses[20] || env->IsSameObject(cclasses[20], NULL)) { cclasses_mtx[20].lock(); if (!cclasses[20] || env->IsSameObject(cclasses[20], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[29]))) { cclasses[20] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[20].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[39]) { cmethods[39] = env->GetStaticMethodID((cclasses[20]), ((char *)(string_pool + 6540LL)), ((char *)(string_pool + 6549LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } cstack0.l = env->CallStaticObjectMethod((cclasses[20]), (cmethods[39]), cstack0.l, cstack1.l); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // ASTORE 8; Stack: 1
        clocal8.l = cstack0.l; refs.insert(cstack0.l);
        // New stack: 0
        // ALOAD 1; Stack: 0
        cstack0.l = clocal1.l; refs.insert(cstack0.l);
        // New stack: 1
        // ALOAD 8; Stack: 1
        cstack1.l = clocal8.l; refs.insert(cstack1.l);
        // New stack: 2
        // ICONST_0; Stack: 2
        cstack2.i = 0;
        // New stack: 3
        // ICONST_2; Stack: 3
        cstack3.i = 2;
        // New stack: 4
        // ANEWARRAY java/lang/Class; Stack: 4
        if (!cclasses[34] || env->IsSameObject(cclasses[34], NULL)) { cclasses_mtx[34].lock(); if (!cclasses[34] || env->IsSameObject(cclasses[34], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[46]))) { cclasses[34] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[34].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (cstack3.i < 0) utils::throw_re(env, ((char *)(string_pool + 251LL)), ((char *)(string_pool + 288LL)), -1); else { cstack3.l = env->NewObjectArray(cstack3.i, (cclasses[34]), nullptr); refs.insert(cstack3.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 4
        // DUP; Stack: 4
        cstack4 = cstack3;
        // New stack: 5
        // ICONST_0; Stack: 5
        cstack5.i = 0;
        // New stack: 6
        // GETSTATIC java/lang/Integer.TYPE Ljava/lang/Class;; Stack: 6
        if (!cclasses[32]  || env->IsSameObject(cclasses[32], NULL)) { cclasses_mtx[32].lock(); if (!cclasses[32] || env->IsSameObject(cclasses[32], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[45]))) { cclasses[32] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[32].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cfields[4]) { cfields[4] = env->GetStaticFieldID((cclasses[32]), ((char *)(string_pool + 6618LL)), ((char *)(string_pool + 6623LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } cstack6.l = env->GetStaticObjectField((cclasses[32]), (cfields[4])); refs.insert(cstack6.l); 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 7
        // AASTORE; Stack: 7
        if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 1107LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack4.l, cstack5.i, cstack6.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 4
        // DUP; Stack: 4
        cstack4 = cstack3;
        // New stack: 5
        // ICONST_1; Stack: 5
        cstack5.i = 1;
        // New stack: 6
        // GETSTATIC java/lang/Long.TYPE Ljava/lang/Class;; Stack: 6
        if (!cclasses[3]  || env->IsSameObject(cclasses[3], NULL)) { cclasses_mtx[3].lock(); if (!cclasses[3] || env->IsSameObject(cclasses[3], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[2]))) { cclasses[3] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[3].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cfields[5]) { cfields[5] = env->GetStaticFieldID((cclasses[3]), ((char *)(string_pool + 6618LL)), ((char *)(string_pool + 6623LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } cstack6.l = env->GetStaticObjectField((cclasses[3]), (cfields[5])); refs.insert(cstack6.l); 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 7
        // AASTORE; Stack: 7
        if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 1107LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack4.l, cstack5.i, cstack6.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 4
        // INVOKESTATIC java/lang/invoke/MethodHandles.dropArguments(Ljava/lang/invoke/MethodHandle;I[Ljava/lang/Class;)Ljava/lang/invoke/MethodHandle;; Stack: 4
        if (!cclasses[20] || env->IsSameObject(cclasses[20], NULL)) { cclasses_mtx[20].lock(); if (!cclasses[20] || env->IsSameObject(cclasses[20], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[29]))) { cclasses[20] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[20].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[40]) { cmethods[40] = env->GetStaticMethodID((cclasses[20]), ((char *)(string_pool + 6641LL)), ((char *)(string_pool + 6655LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } cstack1.l = env->CallStaticObjectMethod((cclasses[20]), (cmethods[40]), cstack1.l, cstack2.i, cstack3.l); refs.insert(cstack1.l); 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 2
        // INVOKEVIRTUAL java/lang/invoke/MutableCallSite.setTarget(Ljava/lang/invoke/MethodHandle;)V; Stack: 2
        if (!cclasses[35] || env->IsSameObject(cclasses[35], NULL)) { cclasses_mtx[35].lock(); if (!cclasses[35] || env->IsSameObject(cclasses[35], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[47]))) { cclasses[35] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[35].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[41]) { cmethods[41] = env->GetMethodID((cclasses[35]), ((char *)(string_pool + 6739LL)), ((char *)(string_pool + 6749LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 5603LL)), -1); else env->CallVoidMethod(cstack0.l, (cmethods[41]), cstack1.l); 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 0
        // ALOAD 7; Stack: 0
        cstack0.l = clocal7.l; refs.insert(cstack0.l);
        // New stack: 1
        // ARETURN; Stack: 1
        return (jobject) cstack0.l;
        // New stack: 0
        return (jobject) 0;
    }
    
    // a(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;
    jobject JNICALL __ngen_native_a7(JNIEnv *env, jclass clazz, jobject arg0, jobject arg1, jobject arg2) {
        jobject classloader = utils::get_classloader_from_class(env, clazz);
        if (env->ExceptionCheck()) { return (jobject) 0; }
        if (classloader == nullptr) { env->FatalError(((char *)(string_pool + 71LL))); return (jobject) 0; }
    
        jobject lookup = nullptr;
        // try-catch-class java/lang/Exception
        if (!cclasses[0] || env->IsSameObject(cclasses[0], NULL)) { cclasses_mtx[0].lock(); if (!cclasses[0] || env->IsSameObject(cclasses[0], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[0]))) { cclasses[0] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[0].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } }
        jvalue cstack0 = {}, cstack1 = {}, cstack2 = {}, cstack3 = {}, cstack4 = {}, cstack5 = {}, cstack6 = {};
        jvalue clocal0 = {}, clocal1 = {}, clocal2 = {}, clocal3 = {}, clocal4 = {};
        std::unordered_set<jobject> refs;
    
        clocal0.l = arg0; refs.insert(clocal0.l);
        clocal1.l = arg1; refs.insert(clocal1.l);
        clocal2.l = arg2; refs.insert(clocal2.l);
    
        // NEW java/lang/invoke/MutableCallSite; Stack: 0
        if (!cclasses[35] || env->IsSameObject(cclasses[35], NULL)) { cclasses_mtx[35].lock(); if (!cclasses[35] || env->IsSameObject(cclasses[35], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[47]))) { cclasses[35] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[35].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (jobject obj = env->AllocObject((cclasses[35]))) { cstack0.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // ALOAD 2; Stack: 2
        cstack2.l = clocal2.l; refs.insert(cstack2.l);
        // New stack: 3
        // INVOKESPECIAL java/lang/invoke/MutableCallSite.<init>(Ljava/lang/invoke/MethodType;)V; Stack: 3
        if (!cclasses[35] || env->IsSameObject(cclasses[35], NULL)) { cclasses_mtx[35].lock(); if (!cclasses[35] || env->IsSameObject(cclasses[35], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[47]))) { cclasses[35] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[35].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[42]) { cmethods[42] = env->GetMethodID((cclasses[35]), ((char *)(string_pool + 665LL)), ((char *)(string_pool + 6900LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 697LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[35]), (cmethods[42]), cstack2.l); 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // ASTORE 3; Stack: 1
        clocal3.l = cstack0.l; refs.insert(cstack0.l);
        // New stack: 0
        // LABEL L1; Stack: 0
        L1: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 0
        // ALOAD 3; Stack: 0
        cstack0.l = clocal3.l; refs.insert(cstack0.l);
        // New stack: 1
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.127771650959966.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 1
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack1.l = lookup;
        // New stack: 2
        // LDC Ldev/mahiro/client/Mahiro_uP;; Stack: 2
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack2.l = (cclasses[6]);
        // New stack: 3
        // LDC a; Stack: 3
        cstack3.l = (cstrings[5]);
        // New stack: 4
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/invoke/MutableCallSite;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;; Stack: 4
        cstack4.l = (cstrings[48]);
        // New stack: 5
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.127771650959966.a()Ljava/lang/ClassLoader;; Stack: 5
        cstack5.l = classloader;
        // New stack: 6
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 6
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[7]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[1]) { cmethods[1] = env->GetStaticMethodID((cclasses[7]), ((char *)(string_pool + 313LL)), ((char *)(string_pool + 340LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack4.l = env->CallStaticObjectMethod((cclasses[7]), (cmethods[1]), cstack4.l, cstack5.l); refs.insert(cstack4.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 5
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 5
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[2]) { cmethods[2] = env->GetMethodID((cclasses[8]), ((char *)(string_pool + 413LL)), ((char *)(string_pool + 424LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 522LL)), -1); else { cstack1.l = env->CallObjectMethod(cstack1.l, (cmethods[2]), cstack2.l, cstack3.l, cstack4.l); refs.insert(cstack1.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 2
        // LDC [Ljava/lang/Object;; Stack: 2
        if (!cclasses[30] || env->IsSameObject(cclasses[30], NULL)) { cclasses_mtx[30].lock(); if (!cclasses[30] || env->IsSameObject(cclasses[30], NULL)) { if (jclass clazz = env->FindClass(((char *)(string_pool + 6201LL)))) { cclasses[30] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[30].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack2.l = (cclasses[30]);
        // New stack: 3
        // ALOAD 2; Stack: 3
        cstack3.l = clocal2.l; refs.insert(cstack3.l);
        // New stack: 4
        // INVOKEVIRTUAL java/lang/invoke/MethodType.parameterCount()I; Stack: 4
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[7]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[43]) { cmethods[43] = env->GetMethodID((cclasses[7]), ((char *)(string_pool + 6933LL)), ((char *)(string_pool + 3219LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack3.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 3223LL)), -1); else cstack3.i = env->CallIntMethod(cstack3.l, (cmethods[43])); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 4
        // INVOKEVIRTUAL java/lang/invoke/MethodHandle.asCollector(Ljava/lang/Class;I)Ljava/lang/invoke/MethodHandle;; Stack: 4
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[44]) { cmethods[44] = env->GetMethodID((cclasses[10]), ((char *)(string_pool + 6948LL)), ((char *)(string_pool + 6960LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 522LL)), -1); else { cstack1.l = env->CallObjectMethod(cstack1.l, (cmethods[44]), cstack2.l, cstack3.i); refs.insert(cstack1.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 2
        // ICONST_0; Stack: 2
        cstack2.i = 0;
        // New stack: 3
        // ICONST_3; Stack: 3
        cstack3.i = 3;
        // New stack: 4
        // ANEWARRAY java/lang/Object; Stack: 4
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack3.i < 0) utils::throw_re(env, ((char *)(string_pool + 251LL)), ((char *)(string_pool + 288LL)), -1); else { cstack3.l = env->NewObjectArray(cstack3.i, (cclasses[5]), nullptr); refs.insert(cstack3.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 4
        // DUP; Stack: 4
        cstack4 = cstack3;
        // New stack: 5
        // ICONST_0; Stack: 5
        cstack5.i = 0;
        // New stack: 6
        // ALOAD 0; Stack: 6
        cstack6.l = clocal0.l; refs.insert(cstack6.l);
        // New stack: 7
        // AASTORE; Stack: 7
        if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 1107LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack4.l, cstack5.i, cstack6.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 4
        // DUP; Stack: 4
        cstack4 = cstack3;
        // New stack: 5
        // ICONST_1; Stack: 5
        cstack5.i = 1;
        // New stack: 6
        // ALOAD 3; Stack: 6
        cstack6.l = clocal3.l; refs.insert(cstack6.l);
        // New stack: 7
        // AASTORE; Stack: 7
        if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 1107LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack4.l, cstack5.i, cstack6.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 4
        // DUP; Stack: 4
        cstack4 = cstack3;
        // New stack: 5
        // ICONST_2; Stack: 5
        cstack5.i = 2;
        // New stack: 6
        // ALOAD 1; Stack: 6
        cstack6.l = clocal1.l; refs.insert(cstack6.l);
        // New stack: 7
        // AASTORE; Stack: 7
        if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 1107LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack4.l, cstack5.i, cstack6.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 4
        // INVOKESTATIC java/lang/invoke/MethodHandles.insertArguments(Ljava/lang/invoke/MethodHandle;I[Ljava/lang/Object;)Ljava/lang/invoke/MethodHandle;; Stack: 4
        if (!cclasses[20] || env->IsSameObject(cclasses[20], NULL)) { cclasses_mtx[20].lock(); if (!cclasses[20] || env->IsSameObject(cclasses[20], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[29]))) { cclasses[20] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[20].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[45]) { cmethods[45] = env->GetStaticMethodID((cclasses[20]), ((char *)(string_pool + 7012LL)), ((char *)(string_pool + 7028LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack1.l = env->CallStaticObjectMethod((cclasses[20]), (cmethods[45]), cstack1.l, cstack2.i, cstack3.l); refs.insert(cstack1.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 2
        // ALOAD 2; Stack: 2
        cstack2.l = clocal2.l; refs.insert(cstack2.l);
        // New stack: 3
        // INVOKESTATIC java/lang/invoke/MethodHandles.explicitCastArguments(Ljava/lang/invoke/MethodHandle;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 3
        if (!cclasses[20] || env->IsSameObject(cclasses[20], NULL)) { cclasses_mtx[20].lock(); if (!cclasses[20] || env->IsSameObject(cclasses[20], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[29]))) { cclasses[20] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[20].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[46]) { cmethods[46] = env->GetStaticMethodID((cclasses[20]), ((char *)(string_pool + 7113LL)), ((char *)(string_pool + 7135LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack1.l = env->CallStaticObjectMethod((cclasses[20]), (cmethods[46]), cstack1.l, cstack2.l); refs.insert(cstack1.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 2
        // INVOKEVIRTUAL java/lang/invoke/MutableCallSite.setTarget(Ljava/lang/invoke/MethodHandle;)V; Stack: 2
        if (!cclasses[35] || env->IsSameObject(cclasses[35], NULL)) { cclasses_mtx[35].lock(); if (!cclasses[35] || env->IsSameObject(cclasses[35], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[47]))) { cclasses[35] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[35].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[41]) { cmethods[41] = env->GetMethodID((cclasses[35]), ((char *)(string_pool + 6739LL)), ((char *)(string_pool + 6749LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 5603LL)), -1); else env->CallVoidMethod(cstack0.l, (cmethods[41]), cstack1.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 0
        // LABEL L2; Stack: 0
        L2: if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 0
        // GOTO L4; Stack: 0
        goto L4;
        // New stack: 0
        // LABEL L3; Stack: 0
        L3: if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 0
        // FRAME FULL L: [java/lang/invoke/MethodHandles$Lookup, java/lang/String, java/lang/invoke/MethodType, java/lang/invoke/MutableCallSite] S: [java/lang/Exception]; Stack: 0
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal3.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ASTORE 4; Stack: 1
        clocal4.l = cstack0.l; refs.insert(cstack0.l);
        // New stack: 0
        // NEW java/lang/RuntimeException; Stack: 0
        if (!cclasses[31] || env->IsSameObject(cclasses[31], NULL)) { cclasses_mtx[31].lock(); if (!cclasses[31] || env->IsSameObject(cclasses[31], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[43]))) { cclasses[31] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[31].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (jobject obj = env->AllocObject((cclasses[31]))) { cstack0.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // NEW java/lang/StringBuilder; Stack: 2
        if (!cclasses[36] || env->IsSameObject(cclasses[36], NULL)) { cclasses_mtx[36].lock(); if (!cclasses[36] || env->IsSameObject(cclasses[36], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[49]))) { cclasses[36] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[36].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (jobject obj = env->AllocObject((cclasses[36]))) { cstack2.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 3
        // DUP; Stack: 3
        cstack3 = cstack2;
        // New stack: 4
        // INVOKESPECIAL java/lang/StringBuilder.<init>()V; Stack: 4
        if (!cclasses[36] || env->IsSameObject(cclasses[36], NULL)) { cclasses_mtx[36].lock(); if (!cclasses[36] || env->IsSameObject(cclasses[36], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[49]))) { cclasses[36] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[36].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[47]) { cmethods[47] = env->GetMethodID((cclasses[36]), ((char *)(string_pool + 665LL)), ((char *)(string_pool + 2376LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack3.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 697LL)), -1); else env->CallNonvirtualVoidMethod(cstack3.l, (cclasses[36]), (cmethods[47])); 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 3
        // LDC dev/mahiro/client/Mahiro_uP; Stack: 3
        cstack3.l = (cstrings[44]);
        // New stack: 4
        // INVOKEVIRTUAL java/lang/StringBuilder.append(Ljava/lang/String;)Ljava/lang/StringBuilder;; Stack: 4
        if (!cclasses[36] || env->IsSameObject(cclasses[36], NULL)) { cclasses_mtx[36].lock(); if (!cclasses[36] || env->IsSameObject(cclasses[36], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[49]))) { cclasses[36] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[36].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[48]) { cmethods[48] = env->GetMethodID((cclasses[36]), ((char *)(string_pool + 7229LL)), ((char *)(string_pool + 7236LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack2.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 522LL)), -1); else { cstack2.l = env->CallObjectMethod(cstack2.l, (cmethods[48]), cstack3.l); refs.insert(cstack2.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 3
        // LDC  : ; Stack: 3
        cstack3.l = (cstrings[50]);
        // New stack: 4
        // INVOKEVIRTUAL java/lang/StringBuilder.append(Ljava/lang/String;)Ljava/lang/StringBuilder;; Stack: 4
        if (!cclasses[36] || env->IsSameObject(cclasses[36], NULL)) { cclasses_mtx[36].lock(); if (!cclasses[36] || env->IsSameObject(cclasses[36], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[49]))) { cclasses[36] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[36].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[48]) { cmethods[48] = env->GetMethodID((cclasses[36]), ((char *)(string_pool + 7229LL)), ((char *)(string_pool + 7236LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack2.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 522LL)), -1); else { cstack2.l = env->CallObjectMethod(cstack2.l, (cmethods[48]), cstack3.l); refs.insert(cstack2.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 3
        // ALOAD 1; Stack: 3
        cstack3.l = clocal1.l; refs.insert(cstack3.l);
        // New stack: 4
        // INVOKEVIRTUAL java/lang/StringBuilder.append(Ljava/lang/String;)Ljava/lang/StringBuilder;; Stack: 4
        if (!cclasses[36] || env->IsSameObject(cclasses[36], NULL)) { cclasses_mtx[36].lock(); if (!cclasses[36] || env->IsSameObject(cclasses[36], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[49]))) { cclasses[36] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[36].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[48]) { cmethods[48] = env->GetMethodID((cclasses[36]), ((char *)(string_pool + 7229LL)), ((char *)(string_pool + 7236LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack2.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 522LL)), -1); else { cstack2.l = env->CallObjectMethod(cstack2.l, (cmethods[48]), cstack3.l); refs.insert(cstack2.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 3
        // LDC  : ; Stack: 3
        cstack3.l = (cstrings[50]);
        // New stack: 4
        // INVOKEVIRTUAL java/lang/StringBuilder.append(Ljava/lang/String;)Ljava/lang/StringBuilder;; Stack: 4
        if (!cclasses[36] || env->IsSameObject(cclasses[36], NULL)) { cclasses_mtx[36].lock(); if (!cclasses[36] || env->IsSameObject(cclasses[36], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[49]))) { cclasses[36] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[36].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[48]) { cmethods[48] = env->GetMethodID((cclasses[36]), ((char *)(string_pool + 7229LL)), ((char *)(string_pool + 7236LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack2.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 522LL)), -1); else { cstack2.l = env->CallObjectMethod(cstack2.l, (cmethods[48]), cstack3.l); refs.insert(cstack2.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 3
        // ALOAD 2; Stack: 3
        cstack3.l = clocal2.l; refs.insert(cstack3.l);
        // New stack: 4
        // INVOKEVIRTUAL java/lang/invoke/MethodType.toString()Ljava/lang/String;; Stack: 4
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[7]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[49]) { cmethods[49] = env->GetMethodID((cclasses[7]), ((char *)(string_pool + 7282LL)), ((char *)(string_pool + 5830LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack3.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 522LL)), -1); else { cstack3.l = env->CallObjectMethod(cstack3.l, (cmethods[49])); refs.insert(cstack3.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 4
        // INVOKEVIRTUAL java/lang/StringBuilder.append(Ljava/lang/String;)Ljava/lang/StringBuilder;; Stack: 4
        if (!cclasses[36] || env->IsSameObject(cclasses[36], NULL)) { cclasses_mtx[36].lock(); if (!cclasses[36] || env->IsSameObject(cclasses[36], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[49]))) { cclasses[36] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[36].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[48]) { cmethods[48] = env->GetMethodID((cclasses[36]), ((char *)(string_pool + 7229LL)), ((char *)(string_pool + 7236LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack2.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 522LL)), -1); else { cstack2.l = env->CallObjectMethod(cstack2.l, (cmethods[48]), cstack3.l); refs.insert(cstack2.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 3
        // INVOKEVIRTUAL java/lang/StringBuilder.toString()Ljava/lang/String;; Stack: 3
        if (!cclasses[36] || env->IsSameObject(cclasses[36], NULL)) { cclasses_mtx[36].lock(); if (!cclasses[36] || env->IsSameObject(cclasses[36], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[49]))) { cclasses[36] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[36].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[50]) { cmethods[50] = env->GetMethodID((cclasses[36]), ((char *)(string_pool + 7282LL)), ((char *)(string_pool + 5830LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack2.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 522LL)), -1); else { cstack2.l = env->CallObjectMethod(cstack2.l, (cmethods[50])); refs.insert(cstack2.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 3
        // ALOAD 4; Stack: 3
        cstack3.l = clocal4.l; refs.insert(cstack3.l);
        // New stack: 4
        // INVOKESPECIAL java/lang/RuntimeException.<init>(Ljava/lang/String;Ljava/lang/Throwable;)V; Stack: 4
        if (!cclasses[31] || env->IsSameObject(cclasses[31], NULL)) { cclasses_mtx[31].lock(); if (!cclasses[31] || env->IsSameObject(cclasses[31], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[43]))) { cclasses[31] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[31].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[36]) { cmethods[36] = env->GetMethodID((cclasses[31]), ((char *)(string_pool + 665LL)), ((char *)(string_pool + 6282LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 697LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[31]), (cmethods[36]), cstack2.l, cstack3.l); 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 720LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 0
        // LABEL L4; Stack: 0
        L4: if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 0
        // FRAME SAME L: null S: null; Stack: 0
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal3.l); 
        utils::clear_refs(env, refs);
        // New stack: 0
        // ALOAD 3; Stack: 0
        cstack0.l = clocal3.l; refs.insert(cstack0.l);
        // New stack: 1
        // ARETURN; Stack: 1
        return (jobject) cstack0.l;
        // New stack: 0
        return (jobject) 0;
        L_CATCH_0: if (env->IsInstanceOf(cstack0.l, (cclasses[0]))) { goto L3; }
        env->Throw((jthrowable) cstack0.l); return (jobject) 0;
    }
    
    
    void __ngen_register_methods(JNIEnv *env, jclass clazz) {
        string_pool = string_pool::get_pool();

        if (jstring str = env->NewStringUTF(((char *)(string_pool + 30343LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[3] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 8045LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[45] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 8244LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[2] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 8278LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[41] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 8295LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[42] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 6784LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[6] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 8418LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[8] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 8508LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[29] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 30371LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[26] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 30395LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[20] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 8595LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[0] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 8714LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[11] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 8917LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[49] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 9052LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[1] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 9118LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[12] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 9148LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[14] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 30433LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[22] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 9203LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[37] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 9232LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[50] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 9299LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[27] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 9363LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[36] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 9393LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[28] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 9435LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[38] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 9621LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[15] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 9687LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[33] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 247LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[5] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 9758LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[40] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 5935LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[9] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 9969LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[43] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 30499LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[18] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 10032LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[21] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 30548LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[44] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 10429LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[47] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 10534LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[24] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 6409LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[48] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 10690LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[16] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 30576LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[19] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 11502LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[13] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 30601LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[25] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 14931LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[17] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 15058LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[32] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 30649LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[39] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 15228LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[34] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 6091LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[10] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 15644LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[23] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 15813LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[7] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 15841LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[35] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 15869LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[4] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 15886LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[46] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 15990LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[30] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 16085LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[31] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }

        JNINativeMethod __ngen_methods[] = {
            { ((char *)(string_pool + 21142LL)), ((char *)(string_pool + 3894LL)), (void *)&__ngen_native_Mahiro_e1 },
            { ((char *)(string_pool + 1470LL)), ((char *)(string_pool + 20339LL)), (void *)&__ngen_native_Mahiro_R2 },
            { ((char *)(string_pool + 247LL)), ((char *)(string_pool + 5800LL)), (void *)&__ngen_native_a4 },
            { ((char *)(string_pool + 247LL)), ((char *)(string_pool + 6091LL)), (void *)&__ngen_native_a5 },
            { ((char *)(string_pool + 247LL)), ((char *)(string_pool + 6409LL)), (void *)&__ngen_native_a6 },
            { ((char *)(string_pool + 247LL)), ((char *)(string_pool + 6784LL)), (void *)&__ngen_native_a7 },
        };

        if (clazz) env->RegisterNatives(clazz, __ngen_methods, sizeof(__ngen_methods) / sizeof(__ngen_methods[0]));
        if (env->ExceptionCheck()) { fprintf(stderr, "Exception occured while registering native_jvm for %s\n", ((char *)(string_pool + 30343LL))); fflush(stderr); env->ExceptionDescribe(); env->ExceptionClear(); }

        {
            jclass hidden_class = env->FindClass(((char *)(string_pool + 16113LL)));
            JNINativeMethod __ngen_hidden_methods[] = {
                { ((char *)(string_pool + 30696LL)), ((char *)(string_pool + 16157LL)), (void *)&__ngen_special_clinit_9_3 },
            };
            if (hidden_class) env->RegisterNatives(hidden_class, __ngen_hidden_methods, sizeof(__ngen_hidden_methods) / sizeof(__ngen_hidden_methods[0]));
            if (env->ExceptionCheck()) { fprintf(stderr, "Exception occured while registering native_jvm for %s\n", ((char *)(string_pool + 9148LL))); fflush(stderr); env->ExceptionDescribe(); env->ExceptionClear(); }
            env->DeleteLocalRef(hidden_class);
        }
    }
}