#include "../native_jvm.hpp"
#include "../string_pool.hpp"
#include "dev_sakura_client_Mahiro_i3_7.hpp"

// dev/sakura/client/Mahiro_i3
namespace native_jvm::classes::__ngen_dev_sakura_client_Mahiro_i3_7 {

    char *string_pool;

    jstring cstrings[64];
    std::mutex cclasses_mtx[38];
    jclass cclasses[38];
    jmethodID cmethods[56];
    jfieldID cfields[7];

    // Mahiro_Q([Ljava/lang/Object;)Z
    jboolean JNICALL __ngen_native_Mahiro_Q1(JNIEnv *env, jclass clazz, jarray arg0) {
        jobject classloader = utils::get_classloader_from_class(env, clazz);
        if (env->ExceptionCheck()) { return (jboolean) 0; }
        if (classloader == nullptr) { env->FatalError(((char *)(string_pool + 32LL))); return (jboolean) 0; }
    
        jobject lookup = nullptr;
        // try-catch-class java/lang/Exception
        if (!cclasses[0] || env->IsSameObject(cclasses[0], NULL)) { cclasses_mtx[0].lock(); if (!cclasses[0] || env->IsSameObject(cclasses[0], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[0]))) { cclasses[0] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[0].unlock(); if (env->ExceptionCheck()) { return (jboolean) 0; } }
        // try-catch-class java/lang/Throwable
        if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { cclasses_mtx[1].lock(); if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[1]))) { cclasses[1] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[1].unlock(); if (env->ExceptionCheck()) { return (jboolean) 0; } }
        jvalue cstack0 = {}, cstack1 = {}, cstack2 = {}, cstack3 = {}, cstack4 = {}, cstack5 = {}, cstack6 = {}, cstack7 = {}, cstack8 = {}, cstack9 = {}, cstack10 = {}, cstack11 = {}, cstack12 = {};
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
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 83LL)), -1); else { cstack1.l = env->GetObjectArrayElement((jobjectArray) cstack1.l, cstack2.i); refs.insert(cstack1.l); } 
        if (env->ExceptionCheck()) { return (jboolean) 0; }
        // New stack: 2
        // CHECKCAST java/lang/Long; Stack: 2
        if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { cclasses_mtx[2].lock(); if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[2]))) { cclasses[2] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[2].unlock(); if (env->ExceptionCheck()) { return (jboolean) 0; } } if (cstack1.l != nullptr && !env->IsInstanceOf(cstack1.l, (cclasses[2]))) { utils::throw_re(env, ((char *)(string_pool + 94LL)), (std::string(((char *)(string_pool + 123LL))) + std::string(((char *)(string_pool + 139LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (jboolean) 0; } } 
        // New stack: 2
        // INVOKEVIRTUAL java/lang/Long.longValue()J; Stack: 2
        if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { cclasses_mtx[2].lock(); if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[2]))) { cclasses[2] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[2].unlock(); if (env->ExceptionCheck()) { return (jboolean) 0; } } if (!cmethods[0]) { cmethods[0] = env->GetMethodID((cclasses[2]), ((char *)(string_pool + 154LL)), ((char *)(string_pool + 164LL))); if (env->ExceptionCheck()) { return (jboolean) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 168LL)), -1); else cstack1.j = env->CallLongMethod(cstack1.l, (cmethods[0])); 
        if (env->ExceptionCheck()) { return (jboolean) 0; }
        // New stack: 3
        // LSTORE 1; Stack: 3
        clocal1.j = cstack1.j;
        // New stack: 1
        // POP; Stack: 1
        ;
        // New stack: 0
        // GETSTATIC dev/sakura/client/Mahiro_i3.a J; Stack: 0
        if (!cclasses[3]  || env->IsSameObject(cclasses[3], NULL)) { cclasses_mtx[3].lock(); if (!cclasses[3] || env->IsSameObject(cclasses[3], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[3] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[3].unlock(); if (env->ExceptionCheck()) { return (jboolean) 0; } } if (!cfields[0]) { cfields[0] = env->GetStaticFieldID((cclasses[3]), ((char *)(string_pool + 210LL)), ((char *)(string_pool + 212LL))); if (env->ExceptionCheck()) { return (jboolean) 0; }  } cstack0.j = env->GetStaticLongField((cclasses[3]), (cfields[0])); 
        if (env->ExceptionCheck()) { return (jboolean) 0; }
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
        // LLOAD 1; Stack: 0
        cstack0.j = clocal1.j;
        // New stack: 2
        // DUP2; Stack: 2
        cstack2 = cstack0; cstack3 = cstack1;
        // New stack: 4
        // LDC 69644414850138; Stack: 4
        cstack4.j = 69644414850138LL;
        // New stack: 6
        // LXOR; Stack: 6
        cstack2.j = cstack2.j ^ cstack4.j;
        // New stack: 4
        // LSTORE 3; Stack: 4
        clocal3.j = cstack2.j;
        // New stack: 2
        // POP2; Stack: 2
        ;
        // New stack: 0
        // LDC 3045725612467982738; Stack: 0
        cstack0.j = 3045725612467982738LL;
        // New stack: 2
        // LLOAD 1; Stack: 2
        cstack2.j = clocal1.j;
        // New stack: 4
        // LABEL L9; Stack: 4
        L9: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 4
        // ICONST_1; Stack: 4
        cstack4.i = 1;
        // New stack: 5
        // ANEWARRAY java/lang/Object; Stack: 5
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack4.i < 0) utils::throw_re(env, ((char *)(string_pool + 214LL)), ((char *)(string_pool + 251LL)), -1); else { cstack4.l = env->NewObjectArray(cstack4.i, (cclasses[4]), nullptr); refs.insert(cstack4.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 5
        // DUP; Stack: 5
        cstack5 = cstack4;
        // New stack: 6
        // LDC Ldev/sakura/client/Mahiro_i3;; Stack: 6
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack6.l = (cclasses[5]);
        // New stack: 7
        // SWAP; Stack: 7
        std::swap(cstack6, cstack5);
        // New stack: 7
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.8175695498474079.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 7
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack7.l = lookup;
        // New stack: 8
        // LDC Ldev/sakura/client/Mahiro_zr;; Stack: 8
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack8.l = (cclasses[6]);
        // New stack: 9
        // LDC a; Stack: 9
        cstack9.l = (cstrings[6]);
        // New stack: 10
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;; Stack: 10
        cstack10.l = (cstrings[7]);
        // New stack: 11
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.8175695498474079.a()Ljava/lang/ClassLoader;; Stack: 11
        cstack11.l = classloader;
        // New stack: 12
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 12
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[1]) { cmethods[1] = env->GetStaticMethodID((cclasses[7]), ((char *)(string_pool + 276LL)), ((char *)(string_pool + 303LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack10.l = env->CallStaticObjectMethod((cclasses[7]), (cmethods[1]), cstack10.l, cstack11.l); refs.insert(cstack10.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 11
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 11
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[2]) { cmethods[2] = env->GetMethodID((cclasses[8]), ((char *)(string_pool + 376LL)), ((char *)(string_pool + 387LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack7.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 485LL)), -1); else { cstack7.l = env->CallObjectMethod(cstack7.l, (cmethods[2]), cstack8.l, cstack9.l, cstack10.l); refs.insert(cstack7.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 8
        // SWAP; Stack: 8
        std::swap(cstack7, cstack6);
        // New stack: 8
        // LDC \u00d4; Stack: 8
        cstack8.l = (cstrings[10]);
        // New stack: 9
        // SWAP; Stack: 9
        std::swap(cstack8, cstack7);
        // New stack: 9
        // LDC (JJ)Ljava/lang/String;; Stack: 9
        cstack9.l = (cstrings[11]);
        // New stack: 10
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.8175695498474079.a()Ljava/lang/ClassLoader;; Stack: 10
        cstack10.l = classloader;
        // New stack: 11
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 11
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[1]) { cmethods[1] = env->GetStaticMethodID((cclasses[7]), ((char *)(string_pool + 276LL)), ((char *)(string_pool + 303LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack9.l = env->CallStaticObjectMethod((cclasses[7]), (cmethods[1]), cstack9.l, cstack10.l); refs.insert(cstack9.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 10
        // SWAP; Stack: 10
        std::swap(cstack9, cstack8);
        // New stack: 10
        // LDC 0; Stack: 10
        cstack10.i = 0;
        // New stack: 11
        // ANEWARRAY java/lang/Object; Stack: 11
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack10.i < 0) utils::throw_re(env, ((char *)(string_pool + 214LL)), ((char *)(string_pool + 251LL)), -1); else { cstack10.l = env->NewObjectArray(cstack10.i, (cclasses[4]), nullptr); refs.insert(cstack10.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 11
        // CHECKCAST java/lang/Object; Stack: 11
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack10.l != nullptr && !env->IsInstanceOf(cstack10.l, (cclasses[4]))) { utils::throw_re(env, ((char *)(string_pool + 94LL)), (std::string(((char *)(string_pool + 123LL))) + std::string(((char *)(string_pool + 510LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } 
        // New stack: 11
        // SWAP; Stack: 11
        std::swap(cstack10, cstack9);
        // New stack: 11
        // INVOKESTATIC native/magic/1/linkcallsite/obfuscator0.8175695498474079.a(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/invoke/MemberName;; Stack: 11
        cstack5.l = utils::link_call_site(env, cstack5.l, cstack6.l, cstack7.l, cstack8.l, cstack9.l, cstack10.l); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 6
        // POP; Stack: 6
        ;
        // New stack: 5
        // ICONST_0; Stack: 5
        cstack5.i = 0;
        // New stack: 6
        // AALOAD; Stack: 6
        if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 83LL)), -1); else { cstack4.l = env->GetObjectArrayElement((jobjectArray) cstack4.l, cstack5.i); refs.insert(cstack4.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 5
        // DUP; Stack: 5
        cstack5 = cstack4;
        // New stack: 6
        // INSTANCEOF java/lang/invoke/CallSite; Stack: 6
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack5.i = cstack5.l == nullptr ? false : env->IsInstanceOf(cstack5.l, (cclasses[9]));
        // New stack: 6
        // IFEQ L14; Stack: 6
        if (cstack5.i == 0) goto L14;
        // New stack: 5
        // INVOKEINTERFACE java/lang/invoke/CallSite.getTarget()Ljava/lang/invoke/MethodHandle;; Stack: 5
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[3]) { cmethods[3] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 527LL)), ((char *)(string_pool + 537LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 571LL)), -1); else { cstack4.l = env->CallObjectMethod(cstack4.l, (cmethods[3])); refs.insert(cstack4.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 5
        // LABEL L14; Stack: 5
        L14: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 5
        // FRAME FULL L: [[Ljava/lang/Object;, 4, 4] S: [4, 4, java/lang/Object]; Stack: 5
        refs.erase(cstack4.l); 
        refs.erase(clocal0.l); 
        utils::clear_refs(env, refs);
        // New stack: 5
        // CHECKCAST java/lang/invoke/MethodHandle; Stack: 5
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack4.l != nullptr && !env->IsInstanceOf(cstack4.l, (cclasses[10]))) { utils::throw_re(env, ((char *)(string_pool + 94LL)), (std::string(((char *)(string_pool + 123LL))) + std::string(((char *)(string_pool + 598LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } 
        // New stack: 5
        // GOTO L15; Stack: 5
        goto L15;
        // New stack: 5
        // LABEL L10; Stack: 5
        L10: if (env->ExceptionCheck()) { return (jboolean) 0; }
        // New stack: 5
        // FRAME SAME1 L: null S: [java/lang/Throwable]; Stack: 5
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // INSTANCEOF java/lang/BootstrapMethodError; Stack: 2
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { return (jboolean) 0; } } cstack1.i = cstack1.l == nullptr ? false : env->IsInstanceOf(cstack1.l, (cclasses[11]));
        // New stack: 2
        // IFNE L16; Stack: 2
        if (cstack1.i != 0) goto L16;
        // New stack: 1
        // NEW java/lang/BootstrapMethodError; Stack: 1
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { return (jboolean) 0; } } if (jobject obj = env->AllocObject((cclasses[11]))) { cstack1.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { return (jboolean) 0; }
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
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { return (jboolean) 0; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[11]), ((char *)(string_pool + 628LL)), ((char *)(string_pool + 635LL))); if (env->ExceptionCheck()) { return (jboolean) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 660LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[11]), (cmethods[4]), cstack2.l); 
        if (env->ExceptionCheck()) { return (jboolean) 0; }
        // New stack: 1
        // LABEL L16; Stack: 1
        L16: if (env->ExceptionCheck()) { return (jboolean) 0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/Object]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 683LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { return (jboolean) 0; }
        // New stack: 0
        // LABEL L15; Stack: 0
        L15: if (env->ExceptionCheck()) { return (jboolean) 0; }
        // New stack: 0
        // FRAME FULL L: [[Ljava/lang/Object;, 4, 4] S: [4, 4, java/lang/invoke/MethodHandle]; Stack: 0
        refs.erase(cstack4.l); 
        refs.erase(clocal0.l); 
        utils::clear_refs(env, refs);
        // New stack: 5
        // INVOKESTATIC native/magic/1/invoke/obfuscator0.8175695498474079.a(JJLjava/lang/invoke/MethodHandle;)Ljava/lang/String;; Stack: 5
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (jboolean) 0; } } if (!cmethods[5]) { cmethods[5] = env->GetStaticMethodID((cclasses[12]), ((char *)(string_pool + 694LL)), ((char *)(string_pool + 709LL))); if (env->ExceptionCheck()) { return (jboolean) 0; }  } cstack0.l = env->CallStaticObjectMethod((cclasses[12]), (cmethods[5]), cstack0.j, cstack2.j, cstack4.l); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { return (jboolean) 0; }
        // New stack: 1
        // CHECKCAST java/lang/String; Stack: 1
        if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { cclasses_mtx[13].lock(); if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[16]))) { cclasses[13] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[13].unlock(); if (env->ExceptionCheck()) { return (jboolean) 0; } } if (cstack0.l != nullptr && !env->IsInstanceOf(cstack0.l, (cclasses[13]))) { utils::throw_re(env, ((char *)(string_pool + 94LL)), (std::string(((char *)(string_pool + 123LL))) + std::string(((char *)(string_pool + 1391LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (jboolean) 0; } } 
        // New stack: 1
        // ASTORE 5; Stack: 1
        clocal5.l = cstack0.l; refs.insert(cstack0.l);
        // New stack: 0
        // LABEL L11; Stack: 0
        L11: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 0
        // LLOAD 3; Stack: 0
        cstack0.j = clocal3.j;
        // New stack: 2
        // ICONST_1; Stack: 2
        cstack2.i = 1;
        // New stack: 3
        // ANEWARRAY java/lang/Object; Stack: 3
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (cstack2.i < 0) utils::throw_re(env, ((char *)(string_pool + 214LL)), ((char *)(string_pool + 251LL)), -1); else { cstack2.l = env->NewObjectArray(cstack2.i, (cclasses[4]), nullptr); refs.insert(cstack2.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 3
        // DUP_X2; Stack: 3
        cstack3 = cstack2; cstack2 = cstack1; cstack1 = cstack0; cstack0 = cstack3;
        // New stack: 4
        // DUP_X2; Stack: 4
        cstack4 = cstack3; cstack3 = cstack2; cstack2 = cstack1; cstack1 = cstack4;
        // New stack: 5
        // POP; Stack: 5
        ;
        // New stack: 4
        // INVOKESTATIC java/lang/Long.valueOf(J)Ljava/lang/Long;; Stack: 4
        if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { cclasses_mtx[2].lock(); if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[2]))) { cclasses[2] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[2].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (!cmethods[6]) { cmethods[6] = env->GetStaticMethodID((cclasses[2]), ((char *)(string_pool + 1629LL)), ((char *)(string_pool + 1637LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }  } cstack2.l = env->CallStaticObjectMethod((cclasses[2]), (cmethods[6]), cstack2.j); refs.insert(cstack2.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 3
        // ICONST_0; Stack: 3
        cstack3.i = 0;
        // New stack: 4
        // SWAP; Stack: 4
        std::swap(cstack3, cstack2);
        // New stack: 4
        // AASTORE; Stack: 4
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 1657LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack1.l, cstack2.i, cstack3.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 1
        // LDC 3029529582641927083; Stack: 1
        cstack1.j = 3029529582641927083LL;
        // New stack: 3
        // LLOAD 1; Stack: 3
        cstack3.j = clocal1.j;
        // New stack: 5
        // LABEL L7; Stack: 5
        L7: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 5
        // ICONST_1; Stack: 5
        cstack5.i = 1;
        // New stack: 6
        // ANEWARRAY java/lang/Object; Stack: 6
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } if (cstack5.i < 0) utils::throw_re(env, ((char *)(string_pool + 214LL)), ((char *)(string_pool + 251LL)), -1); else { cstack5.l = env->NewObjectArray(cstack5.i, (cclasses[4]), nullptr); refs.insert(cstack5.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 6
        // DUP; Stack: 6
        cstack6 = cstack5;
        // New stack: 7
        // LDC Ldev/sakura/client/Mahiro_i3;; Stack: 7
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } cstack7.l = (cclasses[5]);
        // New stack: 8
        // SWAP; Stack: 8
        std::swap(cstack7, cstack6);
        // New stack: 8
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.8175695498474079.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 8
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } cstack8.l = lookup;
        // New stack: 9
        // LDC Ldev/sakura/client/Mahiro_zr;; Stack: 9
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } cstack9.l = (cclasses[6]);
        // New stack: 10
        // LDC a; Stack: 10
        cstack10.l = (cstrings[6]);
        // New stack: 11
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;; Stack: 11
        cstack11.l = (cstrings[7]);
        // New stack: 12
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.8175695498474079.a()Ljava/lang/ClassLoader;; Stack: 12
        cstack12.l = classloader;
        // New stack: 13
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 13
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } if (!cmethods[1]) { cmethods[1] = env->GetStaticMethodID((cclasses[7]), ((char *)(string_pool + 276LL)), ((char *)(string_pool + 303LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }  } cstack11.l = env->CallStaticObjectMethod((cclasses[7]), (cmethods[1]), cstack11.l, cstack12.l); refs.insert(cstack11.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 12
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 12
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } if (!cmethods[2]) { cmethods[2] = env->GetMethodID((cclasses[8]), ((char *)(string_pool + 376LL)), ((char *)(string_pool + 387LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }  } if (cstack8.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 485LL)), -1); else { cstack8.l = env->CallObjectMethod(cstack8.l, (cmethods[2]), cstack9.l, cstack10.l, cstack11.l); refs.insert(cstack8.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 9
        // SWAP; Stack: 9
        std::swap(cstack8, cstack7);
        // New stack: 9
        // LDC \u00d4; Stack: 9
        cstack9.l = (cstrings[10]);
        // New stack: 10
        // SWAP; Stack: 10
        std::swap(cstack9, cstack8);
        // New stack: 10
        // LDC (Ljava/lang/Object;JJ)Ldev/sakura/client/Mahiro_iK;; Stack: 10
        cstack10.l = (cstrings[17]);
        // New stack: 11
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.8175695498474079.a()Ljava/lang/ClassLoader;; Stack: 11
        cstack11.l = classloader;
        // New stack: 12
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 12
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } if (!cmethods[1]) { cmethods[1] = env->GetStaticMethodID((cclasses[7]), ((char *)(string_pool + 276LL)), ((char *)(string_pool + 303LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }  } cstack10.l = env->CallStaticObjectMethod((cclasses[7]), (cmethods[1]), cstack10.l, cstack11.l); refs.insert(cstack10.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 11
        // SWAP; Stack: 11
        std::swap(cstack10, cstack9);
        // New stack: 11
        // LDC 0; Stack: 11
        cstack11.i = 0;
        // New stack: 12
        // ANEWARRAY java/lang/Object; Stack: 12
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } if (cstack11.i < 0) utils::throw_re(env, ((char *)(string_pool + 214LL)), ((char *)(string_pool + 251LL)), -1); else { cstack11.l = env->NewObjectArray(cstack11.i, (cclasses[4]), nullptr); refs.insert(cstack11.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 12
        // CHECKCAST java/lang/Object; Stack: 12
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } if (cstack11.l != nullptr && !env->IsInstanceOf(cstack11.l, (cclasses[4]))) { utils::throw_re(env, ((char *)(string_pool + 94LL)), (std::string(((char *)(string_pool + 123LL))) + std::string(((char *)(string_pool + 510LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } 
        // New stack: 12
        // SWAP; Stack: 12
        std::swap(cstack11, cstack10);
        // New stack: 12
        // INVOKESTATIC native/magic/1/linkcallsite/obfuscator0.8175695498474079.a(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/invoke/MemberName;; Stack: 12
        cstack6.l = utils::link_call_site(env, cstack6.l, cstack7.l, cstack8.l, cstack9.l, cstack10.l, cstack11.l); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 7
        // POP; Stack: 7
        ;
        // New stack: 6
        // ICONST_0; Stack: 6
        cstack6.i = 0;
        // New stack: 7
        // AALOAD; Stack: 7
        if (cstack5.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 83LL)), -1); else { cstack5.l = env->GetObjectArrayElement((jobjectArray) cstack5.l, cstack6.i); refs.insert(cstack5.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 6
        // DUP; Stack: 6
        cstack6 = cstack5;
        // New stack: 7
        // INSTANCEOF java/lang/invoke/CallSite; Stack: 7
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } cstack6.i = cstack6.l == nullptr ? false : env->IsInstanceOf(cstack6.l, (cclasses[9]));
        // New stack: 7
        // IFEQ L17; Stack: 7
        if (cstack6.i == 0) goto L17;
        // New stack: 6
        // INVOKEINTERFACE java/lang/invoke/CallSite.getTarget()Ljava/lang/invoke/MethodHandle;; Stack: 6
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } if (!cmethods[3]) { cmethods[3] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 527LL)), ((char *)(string_pool + 537LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }  } if (cstack5.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 571LL)), -1); else { cstack5.l = env->CallObjectMethod(cstack5.l, (cmethods[3])); refs.insert(cstack5.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 6
        // LABEL L17; Stack: 6
        L17: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 6
        // FRAME FULL L: [[Ljava/lang/Object;, 4, 4, java/lang/String] S: [[Ljava/lang/Object;, 4, 4, java/lang/Object]; Stack: 6
        refs.erase(cstack0.l); refs.erase(cstack5.l); 
        refs.erase(clocal0.l); refs.erase(clocal5.l); 
        utils::clear_refs(env, refs);
        // New stack: 6
        // CHECKCAST java/lang/invoke/MethodHandle; Stack: 6
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } if (cstack5.l != nullptr && !env->IsInstanceOf(cstack5.l, (cclasses[10]))) { utils::throw_re(env, ((char *)(string_pool + 94LL)), (std::string(((char *)(string_pool + 123LL))) + std::string(((char *)(string_pool + 598LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } 
        // New stack: 6
        // GOTO L18; Stack: 6
        goto L18;
        // New stack: 6
        // LABEL L8; Stack: 6
        L8: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 6
        // FRAME SAME1 L: null S: [java/lang/Throwable]; Stack: 6
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal5.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // INSTANCEOF java/lang/BootstrapMethodError; Stack: 2
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } cstack1.i = cstack1.l == nullptr ? false : env->IsInstanceOf(cstack1.l, (cclasses[11]));
        // New stack: 2
        // IFNE L19; Stack: 2
        if (cstack1.i != 0) goto L19;
        // New stack: 1
        // NEW java/lang/BootstrapMethodError; Stack: 1
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (jobject obj = env->AllocObject((cclasses[11]))) { cstack1.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
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
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[11]), ((char *)(string_pool + 628LL)), ((char *)(string_pool + 635LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 660LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[11]), (cmethods[4]), cstack2.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 1
        // LABEL L19; Stack: 1
        L19: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/Object]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal5.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 683LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 0
        // LABEL L18; Stack: 0
        L18: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 0
        // FRAME FULL L: [[Ljava/lang/Object;, 4, 4, java/lang/String] S: [[Ljava/lang/Object;, 4, 4, java/lang/invoke/MethodHandle]; Stack: 0
        refs.erase(cstack0.l); refs.erase(cstack5.l); 
        refs.erase(clocal0.l); refs.erase(clocal5.l); 
        utils::clear_refs(env, refs);
        // New stack: 6
        // INVOKESTATIC native/magic/1/invoke/obfuscator0.8175695498474079.a(Ljava/lang/Object;JJLjava/lang/invoke/MethodHandle;)Ldev/sakura/client/Mahiro_iK;; Stack: 6
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (!cmethods[7]) { cmethods[7] = env->GetStaticMethodID((cclasses[12]), ((char *)(string_pool + 782LL)), ((char *)(string_pool + 797LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }  } cstack0.l = env->CallStaticObjectMethod((cclasses[12]), (cmethods[7]), cstack0.l, cstack1.j, cstack3.j, cstack5.l); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 1
        // CHECKCAST dev/sakura/client/Mahiro_iK; Stack: 1
        if (!cclasses[14] || env->IsSameObject(cclasses[14], NULL)) { cclasses_mtx[14].lock(); if (!cclasses[14] || env->IsSameObject(cclasses[14], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[18]))) { cclasses[14] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[14].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (cstack0.l != nullptr && !env->IsInstanceOf(cstack0.l, (cclasses[14]))) { utils::throw_re(env, ((char *)(string_pool + 94LL)), (std::string(((char *)(string_pool + 123LL))) + std::string(((char *)(string_pool + 14780LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } 
        // New stack: 1
        // ALOAD 5; Stack: 1
        cstack1.l = clocal5.l; refs.insert(cstack1.l);
        // New stack: 2
        // IFNULL L20; Stack: 2
        if (env->IsSameObject(cstack1.l, nullptr)) goto L20;
        // New stack: 1
        // IFNULL L21; Stack: 1
        if (env->IsSameObject(cstack0.l, nullptr)) goto L21;
        // New stack: 0
        // LDC 3040743123140331159; Stack: 0
        cstack0.j = 3040743123140331159LL;
        // New stack: 2
        // LLOAD 1; Stack: 2
        cstack2.j = clocal1.j;
        // New stack: 4
        // LABEL L5; Stack: 4
        L5: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 4
        // ICONST_1; Stack: 4
        cstack4.i = 1;
        // New stack: 5
        // ANEWARRAY java/lang/Object; Stack: 5
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } if (cstack4.i < 0) utils::throw_re(env, ((char *)(string_pool + 214LL)), ((char *)(string_pool + 251LL)), -1); else { cstack4.l = env->NewObjectArray(cstack4.i, (cclasses[4]), nullptr); refs.insert(cstack4.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 5
        // DUP; Stack: 5
        cstack5 = cstack4;
        // New stack: 6
        // LDC Ldev/sakura/client/Mahiro_i3;; Stack: 6
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } cstack6.l = (cclasses[5]);
        // New stack: 7
        // SWAP; Stack: 7
        std::swap(cstack6, cstack5);
        // New stack: 7
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.8175695498474079.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 7
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } cstack7.l = lookup;
        // New stack: 8
        // LDC Ldev/sakura/client/Mahiro_zr;; Stack: 8
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } cstack8.l = (cclasses[6]);
        // New stack: 9
        // LDC a; Stack: 9
        cstack9.l = (cstrings[6]);
        // New stack: 10
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;; Stack: 10
        cstack10.l = (cstrings[7]);
        // New stack: 11
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.8175695498474079.a()Ljava/lang/ClassLoader;; Stack: 11
        cstack11.l = classloader;
        // New stack: 12
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 12
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } if (!cmethods[1]) { cmethods[1] = env->GetStaticMethodID((cclasses[7]), ((char *)(string_pool + 276LL)), ((char *)(string_pool + 303LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }  } cstack10.l = env->CallStaticObjectMethod((cclasses[7]), (cmethods[1]), cstack10.l, cstack11.l); refs.insert(cstack10.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 11
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 11
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } if (!cmethods[2]) { cmethods[2] = env->GetMethodID((cclasses[8]), ((char *)(string_pool + 376LL)), ((char *)(string_pool + 387LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }  } if (cstack7.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 485LL)), -1); else { cstack7.l = env->CallObjectMethod(cstack7.l, (cmethods[2]), cstack8.l, cstack9.l, cstack10.l); refs.insert(cstack7.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 8
        // SWAP; Stack: 8
        std::swap(cstack7, cstack6);
        // New stack: 8
        // LDC \u00c3; Stack: 8
        cstack8.l = (cstrings[19]);
        // New stack: 9
        // SWAP; Stack: 9
        std::swap(cstack8, cstack7);
        // New stack: 9
        // LDC (JJ)Ljava/util/concurrent/atomic/AtomicReference;; Stack: 9
        cstack9.l = (cstrings[20]);
        // New stack: 10
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.8175695498474079.a()Ljava/lang/ClassLoader;; Stack: 10
        cstack10.l = classloader;
        // New stack: 11
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 11
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } if (!cmethods[1]) { cmethods[1] = env->GetStaticMethodID((cclasses[7]), ((char *)(string_pool + 276LL)), ((char *)(string_pool + 303LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }  } cstack9.l = env->CallStaticObjectMethod((cclasses[7]), (cmethods[1]), cstack9.l, cstack10.l); refs.insert(cstack9.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 10
        // SWAP; Stack: 10
        std::swap(cstack9, cstack8);
        // New stack: 10
        // LDC 0; Stack: 10
        cstack10.i = 0;
        // New stack: 11
        // ANEWARRAY java/lang/Object; Stack: 11
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } if (cstack10.i < 0) utils::throw_re(env, ((char *)(string_pool + 214LL)), ((char *)(string_pool + 251LL)), -1); else { cstack10.l = env->NewObjectArray(cstack10.i, (cclasses[4]), nullptr); refs.insert(cstack10.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 11
        // CHECKCAST java/lang/Object; Stack: 11
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } if (cstack10.l != nullptr && !env->IsInstanceOf(cstack10.l, (cclasses[4]))) { utils::throw_re(env, ((char *)(string_pool + 94LL)), (std::string(((char *)(string_pool + 123LL))) + std::string(((char *)(string_pool + 510LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } 
        // New stack: 11
        // SWAP; Stack: 11
        std::swap(cstack10, cstack9);
        // New stack: 11
        // INVOKESTATIC native/magic/1/linkcallsite/obfuscator0.8175695498474079.a(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/invoke/MemberName;; Stack: 11
        cstack5.l = utils::link_call_site(env, cstack5.l, cstack6.l, cstack7.l, cstack8.l, cstack9.l, cstack10.l); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 6
        // POP; Stack: 6
        ;
        // New stack: 5
        // ICONST_0; Stack: 5
        cstack5.i = 0;
        // New stack: 6
        // AALOAD; Stack: 6
        if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 83LL)), -1); else { cstack4.l = env->GetObjectArrayElement((jobjectArray) cstack4.l, cstack5.i); refs.insert(cstack4.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 5
        // DUP; Stack: 5
        cstack5 = cstack4;
        // New stack: 6
        // INSTANCEOF java/lang/invoke/CallSite; Stack: 6
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } cstack5.i = cstack5.l == nullptr ? false : env->IsInstanceOf(cstack5.l, (cclasses[9]));
        // New stack: 6
        // IFEQ L22; Stack: 6
        if (cstack5.i == 0) goto L22;
        // New stack: 5
        // INVOKEINTERFACE java/lang/invoke/CallSite.getTarget()Ljava/lang/invoke/MethodHandle;; Stack: 5
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } if (!cmethods[3]) { cmethods[3] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 527LL)), ((char *)(string_pool + 537LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }  } if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 571LL)), -1); else { cstack4.l = env->CallObjectMethod(cstack4.l, (cmethods[3])); refs.insert(cstack4.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 5
        // LABEL L22; Stack: 5
        L22: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 5
        // FRAME FULL L: [[Ljava/lang/Object;, 4, 4, java/lang/String] S: [4, 4, java/lang/Object]; Stack: 5
        refs.erase(cstack4.l); 
        refs.erase(clocal0.l); refs.erase(clocal5.l); 
        utils::clear_refs(env, refs);
        // New stack: 5
        // CHECKCAST java/lang/invoke/MethodHandle; Stack: 5
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } if (cstack4.l != nullptr && !env->IsInstanceOf(cstack4.l, (cclasses[10]))) { utils::throw_re(env, ((char *)(string_pool + 94LL)), (std::string(((char *)(string_pool + 123LL))) + std::string(((char *)(string_pool + 598LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } 
        // New stack: 5
        // GOTO L23; Stack: 5
        goto L23;
        // New stack: 5
        // LABEL L6; Stack: 5
        L6: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 5
        // FRAME SAME1 L: null S: [java/lang/Throwable]; Stack: 5
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal5.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // INSTANCEOF java/lang/BootstrapMethodError; Stack: 2
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } cstack1.i = cstack1.l == nullptr ? false : env->IsInstanceOf(cstack1.l, (cclasses[11]));
        // New stack: 2
        // IFNE L24; Stack: 2
        if (cstack1.i != 0) goto L24;
        // New stack: 1
        // NEW java/lang/BootstrapMethodError; Stack: 1
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (jobject obj = env->AllocObject((cclasses[11]))) { cstack1.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
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
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[11]), ((char *)(string_pool + 628LL)), ((char *)(string_pool + 635LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 660LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[11]), (cmethods[4]), cstack2.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 1
        // LABEL L24; Stack: 1
        L24: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/Object]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal5.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 683LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 0
        // LABEL L23; Stack: 0
        L23: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 0
        // FRAME FULL L: [[Ljava/lang/Object;, 4, 4, java/lang/String] S: [4, 4, java/lang/invoke/MethodHandle]; Stack: 0
        refs.erase(cstack4.l); 
        refs.erase(clocal0.l); refs.erase(clocal5.l); 
        utils::clear_refs(env, refs);
        // New stack: 5
        // INVOKESTATIC native/magic/1/invoke/obfuscator0.8175695498474079.a(JJLjava/lang/invoke/MethodHandle;)Ljava/util/concurrent/atomic/AtomicReference;; Stack: 5
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (!cmethods[5]) { cmethods[5] = env->GetStaticMethodID((cclasses[12]), ((char *)(string_pool + 694LL)), ((char *)(string_pool + 709LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }  } cstack0.l = env->CallStaticObjectMethod((cclasses[12]), (cmethods[5]), cstack0.j, cstack2.j, cstack4.l); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 1
        // CHECKCAST java/util/concurrent/atomic/AtomicReference; Stack: 1
        if (!cclasses[15] || env->IsSameObject(cclasses[15], NULL)) { cclasses_mtx[15].lock(); if (!cclasses[15] || env->IsSameObject(cclasses[15], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[21]))) { cclasses[15] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[15].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (cstack0.l != nullptr && !env->IsInstanceOf(cstack0.l, (cclasses[15]))) { utils::throw_re(env, ((char *)(string_pool + 94LL)), (std::string(((char *)(string_pool + 123LL))) + std::string(((char *)(string_pool + 6758LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } 
        // New stack: 1
        // LDC 3031193259343338664; Stack: 1
        cstack1.j = 3031193259343338664LL;
        // New stack: 3
        // LLOAD 1; Stack: 3
        cstack3.j = clocal1.j;
        // New stack: 5
        // LABEL L3; Stack: 5
        L3: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }
        // New stack: 5
        // ICONST_1; Stack: 5
        cstack5.i = 1;
        // New stack: 6
        // ANEWARRAY java/lang/Object; Stack: 6
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } if (cstack5.i < 0) utils::throw_re(env, ((char *)(string_pool + 214LL)), ((char *)(string_pool + 251LL)), -1); else { cstack5.l = env->NewObjectArray(cstack5.i, (cclasses[4]), nullptr); refs.insert(cstack5.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }
        // New stack: 6
        // DUP; Stack: 6
        cstack6 = cstack5;
        // New stack: 7
        // LDC Ldev/sakura/client/Mahiro_i3;; Stack: 7
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } cstack7.l = (cclasses[5]);
        // New stack: 8
        // SWAP; Stack: 8
        std::swap(cstack7, cstack6);
        // New stack: 8
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.8175695498474079.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 8
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } cstack8.l = lookup;
        // New stack: 9
        // LDC Ldev/sakura/client/Mahiro_zr;; Stack: 9
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } cstack9.l = (cclasses[6]);
        // New stack: 10
        // LDC a; Stack: 10
        cstack10.l = (cstrings[6]);
        // New stack: 11
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;; Stack: 11
        cstack11.l = (cstrings[7]);
        // New stack: 12
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.8175695498474079.a()Ljava/lang/ClassLoader;; Stack: 12
        cstack12.l = classloader;
        // New stack: 13
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 13
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } if (!cmethods[1]) { cmethods[1] = env->GetStaticMethodID((cclasses[7]), ((char *)(string_pool + 276LL)), ((char *)(string_pool + 303LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }  } cstack11.l = env->CallStaticObjectMethod((cclasses[7]), (cmethods[1]), cstack11.l, cstack12.l); refs.insert(cstack11.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }
        // New stack: 12
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 12
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } if (!cmethods[2]) { cmethods[2] = env->GetMethodID((cclasses[8]), ((char *)(string_pool + 376LL)), ((char *)(string_pool + 387LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }  } if (cstack8.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 485LL)), -1); else { cstack8.l = env->CallObjectMethod(cstack8.l, (cmethods[2]), cstack9.l, cstack10.l, cstack11.l); refs.insert(cstack8.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }
        // New stack: 9
        // SWAP; Stack: 9
        std::swap(cstack8, cstack7);
        // New stack: 9
        // LDC L; Stack: 9
        cstack9.l = (cstrings[22]);
        // New stack: 10
        // SWAP; Stack: 10
        std::swap(cstack9, cstack8);
        // New stack: 10
        // LDC (Ljava/lang/Object;JJ)Ljava/lang/Object;; Stack: 10
        cstack10.l = (cstrings[23]);
        // New stack: 11
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.8175695498474079.a()Ljava/lang/ClassLoader;; Stack: 11
        cstack11.l = classloader;
        // New stack: 12
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 12
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } if (!cmethods[1]) { cmethods[1] = env->GetStaticMethodID((cclasses[7]), ((char *)(string_pool + 276LL)), ((char *)(string_pool + 303LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }  } cstack10.l = env->CallStaticObjectMethod((cclasses[7]), (cmethods[1]), cstack10.l, cstack11.l); refs.insert(cstack10.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }
        // New stack: 11
        // SWAP; Stack: 11
        std::swap(cstack10, cstack9);
        // New stack: 11
        // LDC 0; Stack: 11
        cstack11.i = 0;
        // New stack: 12
        // ANEWARRAY java/lang/Object; Stack: 12
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } if (cstack11.i < 0) utils::throw_re(env, ((char *)(string_pool + 214LL)), ((char *)(string_pool + 251LL)), -1); else { cstack11.l = env->NewObjectArray(cstack11.i, (cclasses[4]), nullptr); refs.insert(cstack11.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }
        // New stack: 12
        // CHECKCAST java/lang/Object; Stack: 12
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } if (cstack11.l != nullptr && !env->IsInstanceOf(cstack11.l, (cclasses[4]))) { utils::throw_re(env, ((char *)(string_pool + 94LL)), (std::string(((char *)(string_pool + 123LL))) + std::string(((char *)(string_pool + 510LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } 
        // New stack: 12
        // SWAP; Stack: 12
        std::swap(cstack11, cstack10);
        // New stack: 12
        // INVOKESTATIC native/magic/1/linkcallsite/obfuscator0.8175695498474079.a(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/invoke/MemberName;; Stack: 12
        cstack6.l = utils::link_call_site(env, cstack6.l, cstack7.l, cstack8.l, cstack9.l, cstack10.l, cstack11.l); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }
        // New stack: 7
        // POP; Stack: 7
        ;
        // New stack: 6
        // ICONST_0; Stack: 6
        cstack6.i = 0;
        // New stack: 7
        // AALOAD; Stack: 7
        if (cstack5.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 83LL)), -1); else { cstack5.l = env->GetObjectArrayElement((jobjectArray) cstack5.l, cstack6.i); refs.insert(cstack5.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }
        // New stack: 6
        // DUP; Stack: 6
        cstack6 = cstack5;
        // New stack: 7
        // INSTANCEOF java/lang/invoke/CallSite; Stack: 7
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } cstack6.i = cstack6.l == nullptr ? false : env->IsInstanceOf(cstack6.l, (cclasses[9]));
        // New stack: 7
        // IFEQ L25; Stack: 7
        if (cstack6.i == 0) goto L25;
        // New stack: 6
        // INVOKEINTERFACE java/lang/invoke/CallSite.getTarget()Ljava/lang/invoke/MethodHandle;; Stack: 6
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } if (!cmethods[3]) { cmethods[3] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 527LL)), ((char *)(string_pool + 537LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }  } if (cstack5.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 571LL)), -1); else { cstack5.l = env->CallObjectMethod(cstack5.l, (cmethods[3])); refs.insert(cstack5.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }
        // New stack: 6
        // LABEL L25; Stack: 6
        L25: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }
        // New stack: 6
        // FRAME FULL L: [[Ljava/lang/Object;, 4, 4, java/lang/String] S: [java/util/concurrent/atomic/AtomicReference, 4, 4, java/lang/Object]; Stack: 6
        refs.erase(cstack0.l); refs.erase(cstack5.l); 
        refs.erase(clocal0.l); refs.erase(clocal5.l); 
        utils::clear_refs(env, refs);
        // New stack: 6
        // CHECKCAST java/lang/invoke/MethodHandle; Stack: 6
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } if (cstack5.l != nullptr && !env->IsInstanceOf(cstack5.l, (cclasses[10]))) { utils::throw_re(env, ((char *)(string_pool + 94LL)), (std::string(((char *)(string_pool + 123LL))) + std::string(((char *)(string_pool + 598LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } 
        // New stack: 6
        // GOTO L26; Stack: 6
        goto L26;
        // New stack: 6
        // LABEL L4; Stack: 6
        L4: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 6
        // FRAME SAME1 L: null S: [java/lang/Throwable]; Stack: 6
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal5.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // INSTANCEOF java/lang/BootstrapMethodError; Stack: 2
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } cstack1.i = cstack1.l == nullptr ? false : env->IsInstanceOf(cstack1.l, (cclasses[11]));
        // New stack: 2
        // IFNE L27; Stack: 2
        if (cstack1.i != 0) goto L27;
        // New stack: 1
        // NEW java/lang/BootstrapMethodError; Stack: 1
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (jobject obj = env->AllocObject((cclasses[11]))) { cstack1.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
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
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[11]), ((char *)(string_pool + 628LL)), ((char *)(string_pool + 635LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 660LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[11]), (cmethods[4]), cstack2.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 1
        // LABEL L27; Stack: 1
        L27: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/Object]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal5.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 683LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 0
        // LABEL L26; Stack: 0
        L26: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 0
        // FRAME FULL L: [[Ljava/lang/Object;, 4, 4, java/lang/String] S: [java/util/concurrent/atomic/AtomicReference, 4, 4, java/lang/invoke/MethodHandle]; Stack: 0
        refs.erase(cstack0.l); refs.erase(cstack5.l); 
        refs.erase(clocal0.l); refs.erase(clocal5.l); 
        utils::clear_refs(env, refs);
        // New stack: 6
        // INVOKESTATIC native/magic/1/invoke/obfuscator0.8175695498474079.a(Ljava/lang/Object;JJLjava/lang/invoke/MethodHandle;)Ljava/lang/Object;; Stack: 6
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (!cmethods[7]) { cmethods[7] = env->GetStaticMethodID((cclasses[12]), ((char *)(string_pool + 782LL)), ((char *)(string_pool + 797LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }  } cstack0.l = env->CallStaticObjectMethod((cclasses[12]), (cmethods[7]), cstack0.l, cstack1.j, cstack3.j, cstack5.l); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 1
        // CHECKCAST java/lang/Object; Stack: 1
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (cstack0.l != nullptr && !env->IsInstanceOf(cstack0.l, (cclasses[4]))) { utils::throw_re(env, ((char *)(string_pool + 94LL)), (std::string(((char *)(string_pool + 123LL))) + std::string(((char *)(string_pool + 510LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } 
        // New stack: 1
        // LABEL L20; Stack: 1
        L20: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/Object]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal5.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // CHECKCAST java/lang/String; Stack: 1
        if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { cclasses_mtx[13].lock(); if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[16]))) { cclasses[13] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[13].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (cstack0.l != nullptr && !env->IsInstanceOf(cstack0.l, (cclasses[13]))) { utils::throw_re(env, ((char *)(string_pool + 94LL)), (std::string(((char *)(string_pool + 123LL))) + std::string(((char *)(string_pool + 1391LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } 
        // New stack: 1
        // LDC 3035273959537926489; Stack: 1
        cstack1.j = 3035273959537926489LL;
        // New stack: 3
        // LLOAD 1; Stack: 3
        cstack3.j = clocal1.j;
        // New stack: 5
        // LABEL L1; Stack: 5
        L1: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }
        // New stack: 5
        // ICONST_1; Stack: 5
        cstack5.i = 1;
        // New stack: 6
        // ANEWARRAY java/lang/Object; Stack: 6
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } if (cstack5.i < 0) utils::throw_re(env, ((char *)(string_pool + 214LL)), ((char *)(string_pool + 251LL)), -1); else { cstack5.l = env->NewObjectArray(cstack5.i, (cclasses[4]), nullptr); refs.insert(cstack5.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }
        // New stack: 6
        // DUP; Stack: 6
        cstack6 = cstack5;
        // New stack: 7
        // LDC Ldev/sakura/client/Mahiro_i3;; Stack: 7
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } cstack7.l = (cclasses[5]);
        // New stack: 8
        // SWAP; Stack: 8
        std::swap(cstack7, cstack6);
        // New stack: 8
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.8175695498474079.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 8
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } cstack8.l = lookup;
        // New stack: 9
        // LDC Ldev/sakura/client/Mahiro_zr;; Stack: 9
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } cstack9.l = (cclasses[6]);
        // New stack: 10
        // LDC a; Stack: 10
        cstack10.l = (cstrings[6]);
        // New stack: 11
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;; Stack: 11
        cstack11.l = (cstrings[7]);
        // New stack: 12
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.8175695498474079.a()Ljava/lang/ClassLoader;; Stack: 12
        cstack12.l = classloader;
        // New stack: 13
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 13
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } if (!cmethods[1]) { cmethods[1] = env->GetStaticMethodID((cclasses[7]), ((char *)(string_pool + 276LL)), ((char *)(string_pool + 303LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }  } cstack11.l = env->CallStaticObjectMethod((cclasses[7]), (cmethods[1]), cstack11.l, cstack12.l); refs.insert(cstack11.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }
        // New stack: 12
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 12
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } if (!cmethods[2]) { cmethods[2] = env->GetMethodID((cclasses[8]), ((char *)(string_pool + 376LL)), ((char *)(string_pool + 387LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }  } if (cstack8.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 485LL)), -1); else { cstack8.l = env->CallObjectMethod(cstack8.l, (cmethods[2]), cstack9.l, cstack10.l, cstack11.l); refs.insert(cstack8.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }
        // New stack: 9
        // SWAP; Stack: 9
        std::swap(cstack8, cstack7);
        // New stack: 9
        // LDC L; Stack: 9
        cstack9.l = (cstrings[22]);
        // New stack: 10
        // SWAP; Stack: 10
        std::swap(cstack9, cstack8);
        // New stack: 10
        // LDC (Ljava/lang/Object;JJ)I; Stack: 10
        cstack10.l = (cstrings[24]);
        // New stack: 11
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.8175695498474079.a()Ljava/lang/ClassLoader;; Stack: 11
        cstack11.l = classloader;
        // New stack: 12
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 12
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } if (!cmethods[1]) { cmethods[1] = env->GetStaticMethodID((cclasses[7]), ((char *)(string_pool + 276LL)), ((char *)(string_pool + 303LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }  } cstack10.l = env->CallStaticObjectMethod((cclasses[7]), (cmethods[1]), cstack10.l, cstack11.l); refs.insert(cstack10.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }
        // New stack: 11
        // SWAP; Stack: 11
        std::swap(cstack10, cstack9);
        // New stack: 11
        // LDC 0; Stack: 11
        cstack11.i = 0;
        // New stack: 12
        // ANEWARRAY java/lang/Object; Stack: 12
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } if (cstack11.i < 0) utils::throw_re(env, ((char *)(string_pool + 214LL)), ((char *)(string_pool + 251LL)), -1); else { cstack11.l = env->NewObjectArray(cstack11.i, (cclasses[4]), nullptr); refs.insert(cstack11.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }
        // New stack: 12
        // CHECKCAST java/lang/Object; Stack: 12
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } if (cstack11.l != nullptr && !env->IsInstanceOf(cstack11.l, (cclasses[4]))) { utils::throw_re(env, ((char *)(string_pool + 94LL)), (std::string(((char *)(string_pool + 123LL))) + std::string(((char *)(string_pool + 510LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } 
        // New stack: 12
        // SWAP; Stack: 12
        std::swap(cstack11, cstack10);
        // New stack: 12
        // INVOKESTATIC native/magic/1/linkcallsite/obfuscator0.8175695498474079.a(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/invoke/MemberName;; Stack: 12
        cstack6.l = utils::link_call_site(env, cstack6.l, cstack7.l, cstack8.l, cstack9.l, cstack10.l, cstack11.l); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }
        // New stack: 7
        // POP; Stack: 7
        ;
        // New stack: 6
        // ICONST_0; Stack: 6
        cstack6.i = 0;
        // New stack: 7
        // AALOAD; Stack: 7
        if (cstack5.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 83LL)), -1); else { cstack5.l = env->GetObjectArrayElement((jobjectArray) cstack5.l, cstack6.i); refs.insert(cstack5.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }
        // New stack: 6
        // DUP; Stack: 6
        cstack6 = cstack5;
        // New stack: 7
        // INSTANCEOF java/lang/invoke/CallSite; Stack: 7
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } cstack6.i = cstack6.l == nullptr ? false : env->IsInstanceOf(cstack6.l, (cclasses[9]));
        // New stack: 7
        // IFEQ L28; Stack: 7
        if (cstack6.i == 0) goto L28;
        // New stack: 6
        // INVOKEINTERFACE java/lang/invoke/CallSite.getTarget()Ljava/lang/invoke/MethodHandle;; Stack: 6
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } if (!cmethods[3]) { cmethods[3] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 527LL)), ((char *)(string_pool + 537LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }  } if (cstack5.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 571LL)), -1); else { cstack5.l = env->CallObjectMethod(cstack5.l, (cmethods[3])); refs.insert(cstack5.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }
        // New stack: 6
        // LABEL L28; Stack: 6
        L28: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }
        // New stack: 6
        // FRAME FULL L: [[Ljava/lang/Object;, 4, 4, java/lang/String] S: [java/lang/String, 4, 4, java/lang/Object]; Stack: 6
        refs.erase(cstack0.l); refs.erase(cstack5.l); 
        refs.erase(clocal0.l); refs.erase(clocal5.l); 
        utils::clear_refs(env, refs);
        // New stack: 6
        // CHECKCAST java/lang/invoke/MethodHandle; Stack: 6
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } if (cstack5.l != nullptr && !env->IsInstanceOf(cstack5.l, (cclasses[10]))) { utils::throw_re(env, ((char *)(string_pool + 94LL)), (std::string(((char *)(string_pool + 123LL))) + std::string(((char *)(string_pool + 598LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } 
        // New stack: 6
        // GOTO L29; Stack: 6
        goto L29;
        // New stack: 6
        // LABEL L2; Stack: 6
        L2: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 6
        // FRAME SAME1 L: null S: [java/lang/Throwable]; Stack: 6
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal5.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // INSTANCEOF java/lang/BootstrapMethodError; Stack: 2
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } cstack1.i = cstack1.l == nullptr ? false : env->IsInstanceOf(cstack1.l, (cclasses[11]));
        // New stack: 2
        // IFNE L30; Stack: 2
        if (cstack1.i != 0) goto L30;
        // New stack: 1
        // NEW java/lang/BootstrapMethodError; Stack: 1
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (jobject obj = env->AllocObject((cclasses[11]))) { cstack1.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
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
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[11]), ((char *)(string_pool + 628LL)), ((char *)(string_pool + 635LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 660LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[11]), (cmethods[4]), cstack2.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 1
        // LABEL L30; Stack: 1
        L30: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/Object]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal5.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 683LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 0
        // LABEL L29; Stack: 0
        L29: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 0
        // FRAME FULL L: [[Ljava/lang/Object;, 4, 4, java/lang/String] S: [java/lang/String, 4, 4, java/lang/invoke/MethodHandle]; Stack: 0
        refs.erase(cstack0.l); refs.erase(cstack5.l); 
        refs.erase(clocal0.l); refs.erase(clocal5.l); 
        utils::clear_refs(env, refs);
        // New stack: 6
        // INVOKESTATIC native/magic/1/invoke/obfuscator0.8175695498474079.a(Ljava/lang/Object;JJLjava/lang/invoke/MethodHandle;)I; Stack: 6
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (!cmethods[8]) { cmethods[8] = env->GetStaticMethodID((cclasses[12]), ((char *)(string_pool + 883LL)), ((char *)(string_pool + 898LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }  } cstack0.i = env->CallStaticIntMethod((cclasses[12]), (cmethods[8]), cstack0.l, cstack1.j, cstack3.j, cstack5.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 1
        // ALOAD 5; Stack: 1
        cstack1.l = clocal5.l; refs.insert(cstack1.l);
        // New stack: 2
        // IFNULL L31; Stack: 2
        if (env->IsSameObject(cstack1.l, nullptr)) goto L31;
        // New stack: 1
        // GETSTATIC dev/sakura/client/Mahiro_i3.e J; Stack: 1
        if (!cclasses[3]  || env->IsSameObject(cclasses[3], NULL)) { cclasses_mtx[3].lock(); if (!cclasses[3] || env->IsSameObject(cclasses[3], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[3] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[3].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (!cfields[1]) { cfields[1] = env->GetStaticFieldID((cclasses[3]), ((char *)(string_pool + 13868LL)), ((char *)(string_pool + 212LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }  } cstack1.j = env->GetStaticLongField((cclasses[3]), (cfields[1])); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 3
        // L2I; Stack: 3
        cstack1.i = (jint) cstack1.j;
        // New stack: 2
        // IF_ICMPNE L21; Stack: 2
        if (cstack0.i != cstack1.i) goto L21;
        // New stack: 0
        // ICONST_1; Stack: 0
        cstack0.i = 1;
        // New stack: 1
        // LABEL L31; Stack: 1
        L31: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 1
        // FRAME SAME1 L: null S: [1]; Stack: 1
        refs.erase(clocal0.l); refs.erase(clocal5.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // GOTO L12; Stack: 1
        goto L12;
        // New stack: 1
        // LABEL L21; Stack: 1
        L21: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 1
        // FRAME SAME L: null S: null; Stack: 1
        refs.erase(clocal0.l); refs.erase(clocal5.l); 
        utils::clear_refs(env, refs);
        // New stack: 0
        // ICONST_0; Stack: 0
        cstack0.i = 0;
        // New stack: 1
        // LABEL L12; Stack: 1
        L12: if (env->ExceptionCheck()) { return (jboolean) 0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [1]; Stack: 1
        refs.erase(clocal0.l); refs.erase(clocal5.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // IRETURN; Stack: 1
        return (jboolean) cstack0.i;
        // New stack: 0
        // LABEL L13; Stack: 0
        L13: if (env->ExceptionCheck()) { return (jboolean) 0; }
        // New stack: 0
        // FRAME SAME1 L: null S: [java/lang/Exception]; Stack: 0
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal5.l); 
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
        L_CATCH_3: if (env->IsInstanceOf(cstack0.l, (cclasses[1]))) { goto L6; }
        goto L_CATCH_1;
        L_CATCH_0: if (env->IsInstanceOf(cstack0.l, (cclasses[1]))) { goto L10; }
        env->Throw((jthrowable) cstack0.l); return (jboolean) 0;
        L_CATCH_5: if (env->IsInstanceOf(cstack0.l, (cclasses[1]))) { goto L2; }
        goto L_CATCH_1;
        L_CATCH_4: if (env->IsInstanceOf(cstack0.l, (cclasses[1]))) { goto L4; }
        goto L_CATCH_1;
        L_CATCH_1: if (env->IsInstanceOf(cstack0.l, (cclasses[0]))) { goto L13; }
        env->Throw((jthrowable) cstack0.l); return (jboolean) 0;
        L_CATCH_2: if (env->IsInstanceOf(cstack0.l, (cclasses[1]))) { goto L8; }
        goto L_CATCH_1;
    }
    
    // Mahiro_J([Ljava/lang/Object;)V
    void JNICALL __ngen_native_Mahiro_J2(JNIEnv *env, jclass clazz, jarray arg0) {
        jobject classloader = utils::get_classloader_from_class(env, clazz);
        if (env->ExceptionCheck()) { return (void) 0; }
        if (classloader == nullptr) { env->FatalError(((char *)(string_pool + 32LL))); return (void) 0; }
    
        jobject lookup = nullptr;
        // try-catch-class java/lang/Throwable
        if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { cclasses_mtx[1].lock(); if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[1]))) { cclasses[1] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[1].unlock(); if (env->ExceptionCheck()) { return (void) 0; } }
        jvalue cstack0 = {}, cstack1 = {}, cstack2 = {}, cstack3 = {}, cstack4 = {}, cstack5 = {}, cstack6 = {}, cstack7 = {}, cstack8 = {}, cstack9 = {}, cstack10 = {}, cstack11 = {}, cstack12 = {};
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
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 83LL)), -1); else { cstack1.l = env->GetObjectArrayElement((jobjectArray) cstack1.l, cstack2.i); refs.insert(cstack1.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 2
        // CHECKCAST java/lang/Long; Stack: 2
        if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { cclasses_mtx[2].lock(); if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[2]))) { cclasses[2] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[2].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (cstack1.l != nullptr && !env->IsInstanceOf(cstack1.l, (cclasses[2]))) { utils::throw_re(env, ((char *)(string_pool + 94LL)), (std::string(((char *)(string_pool + 123LL))) + std::string(((char *)(string_pool + 139LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (void) 0; } } 
        // New stack: 2
        // INVOKEVIRTUAL java/lang/Long.longValue()J; Stack: 2
        if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { cclasses_mtx[2].lock(); if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[2]))) { cclasses[2] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[2].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[0]) { cmethods[0] = env->GetMethodID((cclasses[2]), ((char *)(string_pool + 154LL)), ((char *)(string_pool + 164LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 168LL)), -1); else cstack1.j = env->CallLongMethod(cstack1.l, (cmethods[0])); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 3
        // LSTORE 1; Stack: 3
        clocal1.j = cstack1.j;
        // New stack: 1
        // POP; Stack: 1
        ;
        // New stack: 0
        // GETSTATIC dev/sakura/client/Mahiro_i3.a J; Stack: 0
        if (!cclasses[3]  || env->IsSameObject(cclasses[3], NULL)) { cclasses_mtx[3].lock(); if (!cclasses[3] || env->IsSameObject(cclasses[3], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[3] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[3].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cfields[0]) { cfields[0] = env->GetStaticFieldID((cclasses[3]), ((char *)(string_pool + 210LL)), ((char *)(string_pool + 212LL))); if (env->ExceptionCheck()) { return (void) 0; }  } cstack0.j = env->GetStaticLongField((cclasses[3]), (cfields[0])); 
        if (env->ExceptionCheck()) { return (void) 0; }
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
        // LLOAD 1; Stack: 0
        cstack0.j = clocal1.j;
        // New stack: 2
        // DUP2; Stack: 2
        cstack2 = cstack0; cstack3 = cstack1;
        // New stack: 4
        // LDC 137388769144857; Stack: 4
        cstack4.j = 137388769144857LL;
        // New stack: 6
        // LXOR; Stack: 6
        cstack2.j = cstack2.j ^ cstack4.j;
        // New stack: 4
        // LSTORE 3; Stack: 4
        clocal3.j = cstack2.j;
        // New stack: 2
        // DUP2; Stack: 2
        cstack2 = cstack0; cstack3 = cstack1;
        // New stack: 4
        // LDC 32186950962382; Stack: 4
        cstack4.j = 32186950962382LL;
        // New stack: 6
        // LXOR; Stack: 6
        cstack2.j = cstack2.j ^ cstack4.j;
        // New stack: 4
        // LSTORE 5; Stack: 4
        clocal5.j = cstack2.j;
        // New stack: 2
        // POP2; Stack: 2
        ;
        // New stack: 0
        // LLOAD 3; Stack: 0
        cstack0.j = clocal3.j;
        // New stack: 2
        // ICONST_1; Stack: 2
        cstack2.i = 1;
        // New stack: 3
        // ANEWARRAY java/lang/Object; Stack: 3
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (cstack2.i < 0) utils::throw_re(env, ((char *)(string_pool + 214LL)), ((char *)(string_pool + 251LL)), -1); else { cstack2.l = env->NewObjectArray(cstack2.i, (cclasses[4]), nullptr); refs.insert(cstack2.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 3
        // DUP_X2; Stack: 3
        cstack3 = cstack2; cstack2 = cstack1; cstack1 = cstack0; cstack0 = cstack3;
        // New stack: 4
        // DUP_X2; Stack: 4
        cstack4 = cstack3; cstack3 = cstack2; cstack2 = cstack1; cstack1 = cstack4;
        // New stack: 5
        // POP; Stack: 5
        ;
        // New stack: 4
        // INVOKESTATIC java/lang/Long.valueOf(J)Ljava/lang/Long;; Stack: 4
        if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { cclasses_mtx[2].lock(); if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[2]))) { cclasses[2] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[2].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[6]) { cmethods[6] = env->GetStaticMethodID((cclasses[2]), ((char *)(string_pool + 1629LL)), ((char *)(string_pool + 1637LL))); if (env->ExceptionCheck()) { return (void) 0; }  } cstack2.l = env->CallStaticObjectMethod((cclasses[2]), (cmethods[6]), cstack2.j); refs.insert(cstack2.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 3
        // ICONST_0; Stack: 3
        cstack3.i = 0;
        // New stack: 4
        // SWAP; Stack: 4
        std::swap(cstack3, cstack2);
        // New stack: 4
        // AASTORE; Stack: 4
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 1657LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack1.l, cstack2.i, cstack3.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // LDC 6543371501302754010; Stack: 1
        cstack1.j = 6543371501302754010LL;
        // New stack: 3
        // LLOAD 1; Stack: 3
        cstack3.j = clocal1.j;
        // New stack: 5
        // LABEL L3; Stack: 5
        L3: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 5
        // ICONST_1; Stack: 5
        cstack5.i = 1;
        // New stack: 6
        // ANEWARRAY java/lang/Object; Stack: 6
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack5.i < 0) utils::throw_re(env, ((char *)(string_pool + 214LL)), ((char *)(string_pool + 251LL)), -1); else { cstack5.l = env->NewObjectArray(cstack5.i, (cclasses[4]), nullptr); refs.insert(cstack5.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 6
        // DUP; Stack: 6
        cstack6 = cstack5;
        // New stack: 7
        // LDC Ldev/sakura/client/Mahiro_i3;; Stack: 7
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack7.l = (cclasses[5]);
        // New stack: 8
        // SWAP; Stack: 8
        std::swap(cstack7, cstack6);
        // New stack: 8
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.8175695498474079.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 8
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack8.l = lookup;
        // New stack: 9
        // LDC Ldev/sakura/client/Mahiro_zr;; Stack: 9
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack9.l = (cclasses[6]);
        // New stack: 10
        // LDC a; Stack: 10
        cstack10.l = (cstrings[6]);
        // New stack: 11
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;; Stack: 11
        cstack11.l = (cstrings[7]);
        // New stack: 12
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.8175695498474079.a()Ljava/lang/ClassLoader;; Stack: 12
        cstack12.l = classloader;
        // New stack: 13
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 13
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[1]) { cmethods[1] = env->GetStaticMethodID((cclasses[7]), ((char *)(string_pool + 276LL)), ((char *)(string_pool + 303LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack11.l = env->CallStaticObjectMethod((cclasses[7]), (cmethods[1]), cstack11.l, cstack12.l); refs.insert(cstack11.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 12
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 12
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[2]) { cmethods[2] = env->GetMethodID((cclasses[8]), ((char *)(string_pool + 376LL)), ((char *)(string_pool + 387LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack8.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 485LL)), -1); else { cstack8.l = env->CallObjectMethod(cstack8.l, (cmethods[2]), cstack9.l, cstack10.l, cstack11.l); refs.insert(cstack8.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 9
        // SWAP; Stack: 9
        std::swap(cstack8, cstack7);
        // New stack: 9
        // LDC \u00d4; Stack: 9
        cstack9.l = (cstrings[10]);
        // New stack: 10
        // SWAP; Stack: 10
        std::swap(cstack9, cstack8);
        // New stack: 10
        // LDC (Ljava/lang/Object;JJ)Z; Stack: 10
        cstack10.l = (cstrings[25]);
        // New stack: 11
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.8175695498474079.a()Ljava/lang/ClassLoader;; Stack: 11
        cstack11.l = classloader;
        // New stack: 12
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 12
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[1]) { cmethods[1] = env->GetStaticMethodID((cclasses[7]), ((char *)(string_pool + 276LL)), ((char *)(string_pool + 303LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack10.l = env->CallStaticObjectMethod((cclasses[7]), (cmethods[1]), cstack10.l, cstack11.l); refs.insert(cstack10.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 11
        // SWAP; Stack: 11
        std::swap(cstack10, cstack9);
        // New stack: 11
        // LDC 0; Stack: 11
        cstack11.i = 0;
        // New stack: 12
        // ANEWARRAY java/lang/Object; Stack: 12
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack11.i < 0) utils::throw_re(env, ((char *)(string_pool + 214LL)), ((char *)(string_pool + 251LL)), -1); else { cstack11.l = env->NewObjectArray(cstack11.i, (cclasses[4]), nullptr); refs.insert(cstack11.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 12
        // CHECKCAST java/lang/Object; Stack: 12
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack11.l != nullptr && !env->IsInstanceOf(cstack11.l, (cclasses[4]))) { utils::throw_re(env, ((char *)(string_pool + 94LL)), (std::string(((char *)(string_pool + 123LL))) + std::string(((char *)(string_pool + 510LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } 
        // New stack: 12
        // SWAP; Stack: 12
        std::swap(cstack11, cstack10);
        // New stack: 12
        // INVOKESTATIC native/magic/1/linkcallsite/obfuscator0.8175695498474079.a(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/invoke/MemberName;; Stack: 12
        cstack6.l = utils::link_call_site(env, cstack6.l, cstack7.l, cstack8.l, cstack9.l, cstack10.l, cstack11.l); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 7
        // POP; Stack: 7
        ;
        // New stack: 6
        // ICONST_0; Stack: 6
        cstack6.i = 0;
        // New stack: 7
        // AALOAD; Stack: 7
        if (cstack5.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 83LL)), -1); else { cstack5.l = env->GetObjectArrayElement((jobjectArray) cstack5.l, cstack6.i); refs.insert(cstack5.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 6
        // DUP; Stack: 6
        cstack6 = cstack5;
        // New stack: 7
        // INSTANCEOF java/lang/invoke/CallSite; Stack: 7
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack6.i = cstack6.l == nullptr ? false : env->IsInstanceOf(cstack6.l, (cclasses[9]));
        // New stack: 7
        // IFEQ L5; Stack: 7
        if (cstack6.i == 0) goto L5;
        // New stack: 6
        // INVOKEINTERFACE java/lang/invoke/CallSite.getTarget()Ljava/lang/invoke/MethodHandle;; Stack: 6
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[3]) { cmethods[3] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 527LL)), ((char *)(string_pool + 537LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack5.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 571LL)), -1); else { cstack5.l = env->CallObjectMethod(cstack5.l, (cmethods[3])); refs.insert(cstack5.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 6
        // LABEL L5; Stack: 6
        L5: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 6
        // FRAME FULL L: [[Ljava/lang/Object;, 4, 4, 4] S: [[Ljava/lang/Object;, 4, 4, java/lang/Object]; Stack: 6
        refs.erase(cstack0.l); refs.erase(cstack5.l); 
        refs.erase(clocal0.l); 
        utils::clear_refs(env, refs);
        // New stack: 6
        // CHECKCAST java/lang/invoke/MethodHandle; Stack: 6
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack5.l != nullptr && !env->IsInstanceOf(cstack5.l, (cclasses[10]))) { utils::throw_re(env, ((char *)(string_pool + 94LL)), (std::string(((char *)(string_pool + 123LL))) + std::string(((char *)(string_pool + 598LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } 
        // New stack: 6
        // GOTO L6; Stack: 6
        goto L6;
        // New stack: 6
        // LABEL L4; Stack: 6
        L4: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 6
        // FRAME SAME1 L: null S: [java/lang/Throwable]; Stack: 6
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // INSTANCEOF java/lang/BootstrapMethodError; Stack: 2
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } cstack1.i = cstack1.l == nullptr ? false : env->IsInstanceOf(cstack1.l, (cclasses[11]));
        // New stack: 2
        // IFNE L7; Stack: 2
        if (cstack1.i != 0) goto L7;
        // New stack: 1
        // NEW java/lang/BootstrapMethodError; Stack: 1
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (jobject obj = env->AllocObject((cclasses[11]))) { cstack1.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { return (void) 0; }
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
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[11]), ((char *)(string_pool + 628LL)), ((char *)(string_pool + 635LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 660LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[11]), (cmethods[4]), cstack2.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // LABEL L7; Stack: 1
        L7: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/Object]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 683LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // LABEL L6; Stack: 0
        L6: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // FRAME FULL L: [[Ljava/lang/Object;, 4, 4, 4] S: [[Ljava/lang/Object;, 4, 4, java/lang/invoke/MethodHandle]; Stack: 0
        refs.erase(cstack0.l); refs.erase(cstack5.l); 
        refs.erase(clocal0.l); 
        utils::clear_refs(env, refs);
        // New stack: 6
        // INVOKESTATIC native/magic/1/invoke/obfuscator0.8175695498474079.a(Ljava/lang/Object;JJLjava/lang/invoke/MethodHandle;)Z; Stack: 6
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[9]) { cmethods[9] = env->GetStaticMethodID((cclasses[12]), ((char *)(string_pool + 6638LL)), ((char *)(string_pool + 6654LL))); if (env->ExceptionCheck()) { return (void) 0; }  } cstack0.i = (jint) env->CallStaticBooleanMethod((cclasses[12]), (cmethods[9]), cstack0.l, cstack1.j, cstack3.j, cstack5.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // IFNE L8; Stack: 1
        if (cstack0.i != 0) goto L8;
        // New stack: 0
        // LLOAD 5; Stack: 0
        cstack0.j = clocal5.j;
        // New stack: 2
        // ICONST_1; Stack: 2
        cstack2.i = 1;
        // New stack: 3
        // ANEWARRAY java/lang/Object; Stack: 3
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (cstack2.i < 0) utils::throw_re(env, ((char *)(string_pool + 214LL)), ((char *)(string_pool + 251LL)), -1); else { cstack2.l = env->NewObjectArray(cstack2.i, (cclasses[4]), nullptr); refs.insert(cstack2.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 3
        // DUP_X2; Stack: 3
        cstack3 = cstack2; cstack2 = cstack1; cstack1 = cstack0; cstack0 = cstack3;
        // New stack: 4
        // DUP_X2; Stack: 4
        cstack4 = cstack3; cstack3 = cstack2; cstack2 = cstack1; cstack1 = cstack4;
        // New stack: 5
        // POP; Stack: 5
        ;
        // New stack: 4
        // INVOKESTATIC java/lang/Long.valueOf(J)Ljava/lang/Long;; Stack: 4
        if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { cclasses_mtx[2].lock(); if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[2]))) { cclasses[2] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[2].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[6]) { cmethods[6] = env->GetStaticMethodID((cclasses[2]), ((char *)(string_pool + 1629LL)), ((char *)(string_pool + 1637LL))); if (env->ExceptionCheck()) { return (void) 0; }  } cstack2.l = env->CallStaticObjectMethod((cclasses[2]), (cmethods[6]), cstack2.j); refs.insert(cstack2.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 3
        // ICONST_0; Stack: 3
        cstack3.i = 0;
        // New stack: 4
        // SWAP; Stack: 4
        std::swap(cstack3, cstack2);
        // New stack: 4
        // AASTORE; Stack: 4
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 1657LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack1.l, cstack2.i, cstack3.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // LDC 6537923973856392775; Stack: 1
        cstack1.j = 6537923973856392775LL;
        // New stack: 3
        // LLOAD 1; Stack: 3
        cstack3.j = clocal1.j;
        // New stack: 5
        // LABEL L1; Stack: 5
        L1: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 5
        // ICONST_1; Stack: 5
        cstack5.i = 1;
        // New stack: 6
        // ANEWARRAY java/lang/Object; Stack: 6
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (cstack5.i < 0) utils::throw_re(env, ((char *)(string_pool + 214LL)), ((char *)(string_pool + 251LL)), -1); else { cstack5.l = env->NewObjectArray(cstack5.i, (cclasses[4]), nullptr); refs.insert(cstack5.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 6
        // DUP; Stack: 6
        cstack6 = cstack5;
        // New stack: 7
        // LDC Ldev/sakura/client/Mahiro_i3;; Stack: 7
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } cstack7.l = (cclasses[5]);
        // New stack: 8
        // SWAP; Stack: 8
        std::swap(cstack7, cstack6);
        // New stack: 8
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.8175695498474079.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 8
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } cstack8.l = lookup;
        // New stack: 9
        // LDC Ldev/sakura/client/Mahiro_zr;; Stack: 9
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } cstack9.l = (cclasses[6]);
        // New stack: 10
        // LDC a; Stack: 10
        cstack10.l = (cstrings[6]);
        // New stack: 11
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;; Stack: 11
        cstack11.l = (cstrings[7]);
        // New stack: 12
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.8175695498474079.a()Ljava/lang/ClassLoader;; Stack: 12
        cstack12.l = classloader;
        // New stack: 13
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 13
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (!cmethods[1]) { cmethods[1] = env->GetStaticMethodID((cclasses[7]), ((char *)(string_pool + 276LL)), ((char *)(string_pool + 303LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }  } cstack11.l = env->CallStaticObjectMethod((cclasses[7]), (cmethods[1]), cstack11.l, cstack12.l); refs.insert(cstack11.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 12
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 12
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (!cmethods[2]) { cmethods[2] = env->GetMethodID((cclasses[8]), ((char *)(string_pool + 376LL)), ((char *)(string_pool + 387LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }  } if (cstack8.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 485LL)), -1); else { cstack8.l = env->CallObjectMethod(cstack8.l, (cmethods[2]), cstack9.l, cstack10.l, cstack11.l); refs.insert(cstack8.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 9
        // SWAP; Stack: 9
        std::swap(cstack8, cstack7);
        // New stack: 9
        // LDC \u00d4; Stack: 9
        cstack9.l = (cstrings[10]);
        // New stack: 10
        // SWAP; Stack: 10
        std::swap(cstack9, cstack8);
        // New stack: 10
        // LDC (Ljava/lang/Object;JJ)V; Stack: 10
        cstack10.l = (cstrings[26]);
        // New stack: 11
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.8175695498474079.a()Ljava/lang/ClassLoader;; Stack: 11
        cstack11.l = classloader;
        // New stack: 12
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 12
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (!cmethods[1]) { cmethods[1] = env->GetStaticMethodID((cclasses[7]), ((char *)(string_pool + 276LL)), ((char *)(string_pool + 303LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }  } cstack10.l = env->CallStaticObjectMethod((cclasses[7]), (cmethods[1]), cstack10.l, cstack11.l); refs.insert(cstack10.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 11
        // SWAP; Stack: 11
        std::swap(cstack10, cstack9);
        // New stack: 11
        // LDC 0; Stack: 11
        cstack11.i = 0;
        // New stack: 12
        // ANEWARRAY java/lang/Object; Stack: 12
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (cstack11.i < 0) utils::throw_re(env, ((char *)(string_pool + 214LL)), ((char *)(string_pool + 251LL)), -1); else { cstack11.l = env->NewObjectArray(cstack11.i, (cclasses[4]), nullptr); refs.insert(cstack11.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 12
        // CHECKCAST java/lang/Object; Stack: 12
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (cstack11.l != nullptr && !env->IsInstanceOf(cstack11.l, (cclasses[4]))) { utils::throw_re(env, ((char *)(string_pool + 94LL)), (std::string(((char *)(string_pool + 123LL))) + std::string(((char *)(string_pool + 510LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } 
        // New stack: 12
        // SWAP; Stack: 12
        std::swap(cstack11, cstack10);
        // New stack: 12
        // INVOKESTATIC native/magic/1/linkcallsite/obfuscator0.8175695498474079.a(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/invoke/MemberName;; Stack: 12
        cstack6.l = utils::link_call_site(env, cstack6.l, cstack7.l, cstack8.l, cstack9.l, cstack10.l, cstack11.l); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 7
        // POP; Stack: 7
        ;
        // New stack: 6
        // ICONST_0; Stack: 6
        cstack6.i = 0;
        // New stack: 7
        // AALOAD; Stack: 7
        if (cstack5.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 83LL)), -1); else { cstack5.l = env->GetObjectArrayElement((jobjectArray) cstack5.l, cstack6.i); refs.insert(cstack5.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 6
        // DUP; Stack: 6
        cstack6 = cstack5;
        // New stack: 7
        // INSTANCEOF java/lang/invoke/CallSite; Stack: 7
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } cstack6.i = cstack6.l == nullptr ? false : env->IsInstanceOf(cstack6.l, (cclasses[9]));
        // New stack: 7
        // IFEQ L9; Stack: 7
        if (cstack6.i == 0) goto L9;
        // New stack: 6
        // INVOKEINTERFACE java/lang/invoke/CallSite.getTarget()Ljava/lang/invoke/MethodHandle;; Stack: 6
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (!cmethods[3]) { cmethods[3] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 527LL)), ((char *)(string_pool + 537LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }  } if (cstack5.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 571LL)), -1); else { cstack5.l = env->CallObjectMethod(cstack5.l, (cmethods[3])); refs.insert(cstack5.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 6
        // LABEL L9; Stack: 6
        L9: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 6
        // FRAME FULL L: [[Ljava/lang/Object;, 4, 4, 4] S: [[Ljava/lang/Object;, 4, 4, java/lang/Object]; Stack: 6
        refs.erase(cstack0.l); refs.erase(cstack5.l); 
        refs.erase(clocal0.l); 
        utils::clear_refs(env, refs);
        // New stack: 6
        // CHECKCAST java/lang/invoke/MethodHandle; Stack: 6
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (cstack5.l != nullptr && !env->IsInstanceOf(cstack5.l, (cclasses[10]))) { utils::throw_re(env, ((char *)(string_pool + 94LL)), (std::string(((char *)(string_pool + 123LL))) + std::string(((char *)(string_pool + 598LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } 
        // New stack: 6
        // GOTO L10; Stack: 6
        goto L10;
        // New stack: 6
        // LABEL L2; Stack: 6
        L2: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 6
        // FRAME SAME1 L: null S: [java/lang/Throwable]; Stack: 6
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // INSTANCEOF java/lang/BootstrapMethodError; Stack: 2
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } cstack1.i = cstack1.l == nullptr ? false : env->IsInstanceOf(cstack1.l, (cclasses[11]));
        // New stack: 2
        // IFNE L11; Stack: 2
        if (cstack1.i != 0) goto L11;
        // New stack: 1
        // NEW java/lang/BootstrapMethodError; Stack: 1
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (jobject obj = env->AllocObject((cclasses[11]))) { cstack1.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { return (void) 0; }
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
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[11]), ((char *)(string_pool + 628LL)), ((char *)(string_pool + 635LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 660LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[11]), (cmethods[4]), cstack2.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // LABEL L11; Stack: 1
        L11: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/Object]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 683LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // LABEL L10; Stack: 0
        L10: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // FRAME FULL L: [[Ljava/lang/Object;, 4, 4, 4] S: [[Ljava/lang/Object;, 4, 4, java/lang/invoke/MethodHandle]; Stack: 0
        refs.erase(cstack0.l); refs.erase(cstack5.l); 
        refs.erase(clocal0.l); 
        utils::clear_refs(env, refs);
        // New stack: 6
        // INVOKESTATIC native/magic/1/invoke/obfuscator0.8175695498474079.a(Ljava/lang/Object;JJLjava/lang/invoke/MethodHandle;)V; Stack: 6
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[10]) { cmethods[10] = env->GetStaticMethodID((cclasses[12]), ((char *)(string_pool + 1867LL)), ((char *)(string_pool + 1883LL))); if (env->ExceptionCheck()) { return (void) 0; }  } env->CallStaticVoidMethod((cclasses[12]), (cmethods[10]), cstack0.l, cstack1.j, cstack3.j, cstack5.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // LABEL L8; Stack: 0
        L8: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // FRAME SAME L: null S: null; Stack: 0
        refs.erase(clocal0.l); 
        utils::clear_refs(env, refs);
        // New stack: 0
        // RETURN; Stack: 0
        return;
        // New stack: 0
        return (void) 0;
        L_CATCH_0: if (env->IsInstanceOf(cstack0.l, (cclasses[1]))) { goto L4; }
        env->Throw((jthrowable) cstack0.l); return (void) 0;
        L_CATCH_1: if (env->IsInstanceOf(cstack0.l, (cclasses[1]))) { goto L2; }
        env->Throw((jthrowable) cstack0.l); return (void) 0;
    }
    
    // Mahiro_E([Ljava/lang/Object;)V
    void JNICALL __ngen_native_Mahiro_E3(JNIEnv *env, jclass clazz, jarray arg0) {
        jobject classloader = utils::get_classloader_from_class(env, clazz);
        if (env->ExceptionCheck()) { return (void) 0; }
        if (classloader == nullptr) { env->FatalError(((char *)(string_pool + 32LL))); return (void) 0; }
    
        jobject lookup = nullptr;
        // try-catch-class java/lang/Exception
        if (!cclasses[0] || env->IsSameObject(cclasses[0], NULL)) { cclasses_mtx[0].lock(); if (!cclasses[0] || env->IsSameObject(cclasses[0], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[0]))) { cclasses[0] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[0].unlock(); if (env->ExceptionCheck()) { return (void) 0; } }
        // try-catch-class java/lang/Throwable
        if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { cclasses_mtx[1].lock(); if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[1]))) { cclasses[1] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[1].unlock(); if (env->ExceptionCheck()) { return (void) 0; } }
        jvalue cstack0 = {}, cstack1 = {}, cstack2 = {}, cstack3 = {}, cstack4 = {}, cstack5 = {}, cstack6 = {}, cstack7 = {}, cstack8 = {}, cstack9 = {}, cstack10 = {}, cstack11 = {}, cstack12 = {}, cstack13 = {}, cstack14 = {}, cstack15 = {}, cstack16 = {}, cstack17 = {};
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
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 83LL)), -1); else { cstack1.l = env->GetObjectArrayElement((jobjectArray) cstack1.l, cstack2.i); refs.insert(cstack1.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 2
        // CHECKCAST java/lang/Long; Stack: 2
        if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { cclasses_mtx[2].lock(); if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[2]))) { cclasses[2] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[2].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (cstack1.l != nullptr && !env->IsInstanceOf(cstack1.l, (cclasses[2]))) { utils::throw_re(env, ((char *)(string_pool + 94LL)), (std::string(((char *)(string_pool + 123LL))) + std::string(((char *)(string_pool + 139LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (void) 0; } } 
        // New stack: 2
        // INVOKEVIRTUAL java/lang/Long.longValue()J; Stack: 2
        if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { cclasses_mtx[2].lock(); if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[2]))) { cclasses[2] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[2].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[0]) { cmethods[0] = env->GetMethodID((cclasses[2]), ((char *)(string_pool + 154LL)), ((char *)(string_pool + 164LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 168LL)), -1); else cstack1.j = env->CallLongMethod(cstack1.l, (cmethods[0])); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 3
        // LSTORE 1; Stack: 3
        clocal1.j = cstack1.j;
        // New stack: 1
        // POP; Stack: 1
        ;
        // New stack: 0
        // GETSTATIC dev/sakura/client/Mahiro_i3.a J; Stack: 0
        if (!cclasses[3]  || env->IsSameObject(cclasses[3], NULL)) { cclasses_mtx[3].lock(); if (!cclasses[3] || env->IsSameObject(cclasses[3], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[3] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[3].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cfields[0]) { cfields[0] = env->GetStaticFieldID((cclasses[3]), ((char *)(string_pool + 210LL)), ((char *)(string_pool + 212LL))); if (env->ExceptionCheck()) { return (void) 0; }  } cstack0.j = env->GetStaticLongField((cclasses[3]), (cfields[0])); 
        if (env->ExceptionCheck()) { return (void) 0; }
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
        // LABEL L25; Stack: 0
        L25: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 0
        // LDC Ldev/sakura/client/Mahiro_i3;; Stack: 0
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack0.l = (cclasses[5]);
        // New stack: 1
        // LDC -7596714495579020794; Stack: 1
        cstack1.j = -7596714495579020794LL;
        // New stack: 3
        // LLOAD 1; Stack: 3
        cstack3.j = clocal1.j;
        // New stack: 5
        // LABEL L23; Stack: 5
        L23: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 5
        // ICONST_1; Stack: 5
        cstack5.i = 1;
        // New stack: 6
        // ANEWARRAY java/lang/Object; Stack: 6
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (cstack5.i < 0) utils::throw_re(env, ((char *)(string_pool + 214LL)), ((char *)(string_pool + 251LL)), -1); else { cstack5.l = env->NewObjectArray(cstack5.i, (cclasses[4]), nullptr); refs.insert(cstack5.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 6
        // DUP; Stack: 6
        cstack6 = cstack5;
        // New stack: 7
        // LDC Ldev/sakura/client/Mahiro_i3;; Stack: 7
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } cstack7.l = (cclasses[5]);
        // New stack: 8
        // SWAP; Stack: 8
        std::swap(cstack7, cstack6);
        // New stack: 8
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.8175695498474079.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 8
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } cstack8.l = lookup;
        // New stack: 9
        // LDC Ldev/sakura/client/Mahiro_zr;; Stack: 9
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } cstack9.l = (cclasses[6]);
        // New stack: 10
        // LDC a; Stack: 10
        cstack10.l = (cstrings[6]);
        // New stack: 11
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;; Stack: 11
        cstack11.l = (cstrings[7]);
        // New stack: 12
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.8175695498474079.a()Ljava/lang/ClassLoader;; Stack: 12
        cstack12.l = classloader;
        // New stack: 13
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 13
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (!cmethods[1]) { cmethods[1] = env->GetStaticMethodID((cclasses[7]), ((char *)(string_pool + 276LL)), ((char *)(string_pool + 303LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }  } cstack11.l = env->CallStaticObjectMethod((cclasses[7]), (cmethods[1]), cstack11.l, cstack12.l); refs.insert(cstack11.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 12
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 12
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (!cmethods[2]) { cmethods[2] = env->GetMethodID((cclasses[8]), ((char *)(string_pool + 376LL)), ((char *)(string_pool + 387LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }  } if (cstack8.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 485LL)), -1); else { cstack8.l = env->CallObjectMethod(cstack8.l, (cmethods[2]), cstack9.l, cstack10.l, cstack11.l); refs.insert(cstack8.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 9
        // SWAP; Stack: 9
        std::swap(cstack8, cstack7);
        // New stack: 9
        // LDC L; Stack: 9
        cstack9.l = (cstrings[22]);
        // New stack: 10
        // SWAP; Stack: 10
        std::swap(cstack9, cstack8);
        // New stack: 10
        // LDC (Ljava/lang/Object;JJ)Ljava/lang/ClassLoader;; Stack: 10
        cstack10.l = (cstrings[27]);
        // New stack: 11
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.8175695498474079.a()Ljava/lang/ClassLoader;; Stack: 11
        cstack11.l = classloader;
        // New stack: 12
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 12
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (!cmethods[1]) { cmethods[1] = env->GetStaticMethodID((cclasses[7]), ((char *)(string_pool + 276LL)), ((char *)(string_pool + 303LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }  } cstack10.l = env->CallStaticObjectMethod((cclasses[7]), (cmethods[1]), cstack10.l, cstack11.l); refs.insert(cstack10.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 11
        // SWAP; Stack: 11
        std::swap(cstack10, cstack9);
        // New stack: 11
        // LDC 0; Stack: 11
        cstack11.i = 0;
        // New stack: 12
        // ANEWARRAY java/lang/Object; Stack: 12
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (cstack11.i < 0) utils::throw_re(env, ((char *)(string_pool + 214LL)), ((char *)(string_pool + 251LL)), -1); else { cstack11.l = env->NewObjectArray(cstack11.i, (cclasses[4]), nullptr); refs.insert(cstack11.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 12
        // CHECKCAST java/lang/Object; Stack: 12
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (cstack11.l != nullptr && !env->IsInstanceOf(cstack11.l, (cclasses[4]))) { utils::throw_re(env, ((char *)(string_pool + 94LL)), (std::string(((char *)(string_pool + 123LL))) + std::string(((char *)(string_pool + 510LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } 
        // New stack: 12
        // SWAP; Stack: 12
        std::swap(cstack11, cstack10);
        // New stack: 12
        // INVOKESTATIC native/magic/1/linkcallsite/obfuscator0.8175695498474079.a(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/invoke/MemberName;; Stack: 12
        cstack6.l = utils::link_call_site(env, cstack6.l, cstack7.l, cstack8.l, cstack9.l, cstack10.l, cstack11.l); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 7
        // POP; Stack: 7
        ;
        // New stack: 6
        // ICONST_0; Stack: 6
        cstack6.i = 0;
        // New stack: 7
        // AALOAD; Stack: 7
        if (cstack5.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 83LL)), -1); else { cstack5.l = env->GetObjectArrayElement((jobjectArray) cstack5.l, cstack6.i); refs.insert(cstack5.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 6
        // DUP; Stack: 6
        cstack6 = cstack5;
        // New stack: 7
        // INSTANCEOF java/lang/invoke/CallSite; Stack: 7
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } cstack6.i = cstack6.l == nullptr ? false : env->IsInstanceOf(cstack6.l, (cclasses[9]));
        // New stack: 7
        // IFEQ L28; Stack: 7
        if (cstack6.i == 0) goto L28;
        // New stack: 6
        // INVOKEINTERFACE java/lang/invoke/CallSite.getTarget()Ljava/lang/invoke/MethodHandle;; Stack: 6
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (!cmethods[3]) { cmethods[3] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 527LL)), ((char *)(string_pool + 537LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }  } if (cstack5.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 571LL)), -1); else { cstack5.l = env->CallObjectMethod(cstack5.l, (cmethods[3])); refs.insert(cstack5.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 6
        // LABEL L28; Stack: 6
        L28: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 6
        // FRAME FULL L: [[Ljava/lang/Object;, 4] S: [java/lang/Class, 4, 4, java/lang/Object]; Stack: 6
        refs.erase(cstack0.l); refs.erase(cstack5.l); 
        refs.erase(clocal0.l); 
        utils::clear_refs(env, refs);
        // New stack: 6
        // CHECKCAST java/lang/invoke/MethodHandle; Stack: 6
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (cstack5.l != nullptr && !env->IsInstanceOf(cstack5.l, (cclasses[10]))) { utils::throw_re(env, ((char *)(string_pool + 94LL)), (std::string(((char *)(string_pool + 123LL))) + std::string(((char *)(string_pool + 598LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } 
        // New stack: 6
        // GOTO L29; Stack: 6
        goto L29;
        // New stack: 6
        // LABEL L24; Stack: 6
        L24: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 6
        // FRAME SAME1 L: null S: [java/lang/Throwable]; Stack: 6
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // INSTANCEOF java/lang/BootstrapMethodError; Stack: 2
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack1.i = cstack1.l == nullptr ? false : env->IsInstanceOf(cstack1.l, (cclasses[11]));
        // New stack: 2
        // IFNE L30; Stack: 2
        if (cstack1.i != 0) goto L30;
        // New stack: 1
        // NEW java/lang/BootstrapMethodError; Stack: 1
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (jobject obj = env->AllocObject((cclasses[11]))) { cstack1.l = obj; refs.insert(obj); } 
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
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[11]), ((char *)(string_pool + 628LL)), ((char *)(string_pool + 635LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 660LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[11]), (cmethods[4]), cstack2.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 1
        // LABEL L30; Stack: 1
        L30: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/Object]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 683LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 0
        // LABEL L29; Stack: 0
        L29: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 0
        // FRAME FULL L: [[Ljava/lang/Object;, 4] S: [java/lang/Class, 4, 4, java/lang/invoke/MethodHandle]; Stack: 0
        refs.erase(cstack0.l); refs.erase(cstack5.l); 
        refs.erase(clocal0.l); 
        utils::clear_refs(env, refs);
        // New stack: 6
        // INVOKESTATIC native/magic/1/invoke/obfuscator0.8175695498474079.a(Ljava/lang/Object;JJLjava/lang/invoke/MethodHandle;)Ljava/lang/ClassLoader;; Stack: 6
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[7]) { cmethods[7] = env->GetStaticMethodID((cclasses[12]), ((char *)(string_pool + 782LL)), ((char *)(string_pool + 797LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack0.l = env->CallStaticObjectMethod((cclasses[12]), (cmethods[7]), cstack0.l, cstack1.j, cstack3.j, cstack5.l); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 1
        // CHECKCAST java/lang/ClassLoader; Stack: 1
        if (!cclasses[16] || env->IsSameObject(cclasses[16], NULL)) { cclasses_mtx[16].lock(); if (!cclasses[16] || env->IsSameObject(cclasses[16], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[28]))) { cclasses[16] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[16].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack0.l != nullptr && !env->IsInstanceOf(cstack0.l, (cclasses[16]))) { utils::throw_re(env, ((char *)(string_pool + 94LL)), (std::string(((char *)(string_pool + 123LL))) + std::string(((char *)(string_pool + 16595LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } 
        // New stack: 1
        // LABEL L31; Stack: 1
        L31: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 1
        // NEW java/lang/String; Stack: 1
        if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { cclasses_mtx[13].lock(); if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[16]))) { cclasses[13] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[13].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (jobject obj = env->AllocObject((cclasses[13]))) { cstack1.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 2
        // DUP; Stack: 2
        cstack2 = cstack1;
        // New stack: 3
        // LDC -7567672345588810436; Stack: 3
        cstack3.j = -7567672345588810436LL;
        // New stack: 5
        // LLOAD 1; Stack: 5
        cstack5.j = clocal1.j;
        // New stack: 7
        // LABEL L21; Stack: 7
        L21: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 7
        // ICONST_1; Stack: 7
        cstack7.i = 1;
        // New stack: 8
        // ANEWARRAY java/lang/Object; Stack: 8
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } if (cstack7.i < 0) utils::throw_re(env, ((char *)(string_pool + 214LL)), ((char *)(string_pool + 251LL)), -1); else { cstack7.l = env->NewObjectArray(cstack7.i, (cclasses[4]), nullptr); refs.insert(cstack7.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 8
        // DUP; Stack: 8
        cstack8 = cstack7;
        // New stack: 9
        // LDC Ldev/sakura/client/Mahiro_i3;; Stack: 9
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } cstack9.l = (cclasses[5]);
        // New stack: 10
        // SWAP; Stack: 10
        std::swap(cstack9, cstack8);
        // New stack: 10
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.8175695498474079.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 10
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } cstack10.l = lookup;
        // New stack: 11
        // LDC Ldev/sakura/client/Mahiro_zr;; Stack: 11
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } cstack11.l = (cclasses[6]);
        // New stack: 12
        // LDC a; Stack: 12
        cstack12.l = (cstrings[6]);
        // New stack: 13
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;; Stack: 13
        cstack13.l = (cstrings[7]);
        // New stack: 14
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.8175695498474079.a()Ljava/lang/ClassLoader;; Stack: 14
        cstack14.l = classloader;
        // New stack: 15
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 15
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } if (!cmethods[1]) { cmethods[1] = env->GetStaticMethodID((cclasses[7]), ((char *)(string_pool + 276LL)), ((char *)(string_pool + 303LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }  } cstack13.l = env->CallStaticObjectMethod((cclasses[7]), (cmethods[1]), cstack13.l, cstack14.l); refs.insert(cstack13.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 14
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 14
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } if (!cmethods[2]) { cmethods[2] = env->GetMethodID((cclasses[8]), ((char *)(string_pool + 376LL)), ((char *)(string_pool + 387LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }  } if (cstack10.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 485LL)), -1); else { cstack10.l = env->CallObjectMethod(cstack10.l, (cmethods[2]), cstack11.l, cstack12.l, cstack13.l); refs.insert(cstack10.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 11
        // SWAP; Stack: 11
        std::swap(cstack10, cstack9);
        // New stack: 11
        // LDC \u00d4; Stack: 11
        cstack11.l = (cstrings[10]);
        // New stack: 12
        // SWAP; Stack: 12
        std::swap(cstack11, cstack10);
        // New stack: 12
        // LDC (JJ)Ljava/util/Base64$Decoder;; Stack: 12
        cstack12.l = (cstrings[29]);
        // New stack: 13
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.8175695498474079.a()Ljava/lang/ClassLoader;; Stack: 13
        cstack13.l = classloader;
        // New stack: 14
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 14
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } if (!cmethods[1]) { cmethods[1] = env->GetStaticMethodID((cclasses[7]), ((char *)(string_pool + 276LL)), ((char *)(string_pool + 303LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }  } cstack12.l = env->CallStaticObjectMethod((cclasses[7]), (cmethods[1]), cstack12.l, cstack13.l); refs.insert(cstack12.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 13
        // SWAP; Stack: 13
        std::swap(cstack12, cstack11);
        // New stack: 13
        // LDC 0; Stack: 13
        cstack13.i = 0;
        // New stack: 14
        // ANEWARRAY java/lang/Object; Stack: 14
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } if (cstack13.i < 0) utils::throw_re(env, ((char *)(string_pool + 214LL)), ((char *)(string_pool + 251LL)), -1); else { cstack13.l = env->NewObjectArray(cstack13.i, (cclasses[4]), nullptr); refs.insert(cstack13.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 14
        // CHECKCAST java/lang/Object; Stack: 14
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } if (cstack13.l != nullptr && !env->IsInstanceOf(cstack13.l, (cclasses[4]))) { utils::throw_re(env, ((char *)(string_pool + 94LL)), (std::string(((char *)(string_pool + 123LL))) + std::string(((char *)(string_pool + 510LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } 
        // New stack: 14
        // SWAP; Stack: 14
        std::swap(cstack13, cstack12);
        // New stack: 14
        // INVOKESTATIC native/magic/1/linkcallsite/obfuscator0.8175695498474079.a(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/invoke/MemberName;; Stack: 14
        cstack8.l = utils::link_call_site(env, cstack8.l, cstack9.l, cstack10.l, cstack11.l, cstack12.l, cstack13.l); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 9
        // POP; Stack: 9
        ;
        // New stack: 8
        // ICONST_0; Stack: 8
        cstack8.i = 0;
        // New stack: 9
        // AALOAD; Stack: 9
        if (cstack7.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 83LL)), -1); else { cstack7.l = env->GetObjectArrayElement((jobjectArray) cstack7.l, cstack8.i); refs.insert(cstack7.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 8
        // DUP; Stack: 8
        cstack8 = cstack7;
        // New stack: 9
        // INSTANCEOF java/lang/invoke/CallSite; Stack: 9
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } cstack8.i = cstack8.l == nullptr ? false : env->IsInstanceOf(cstack8.l, (cclasses[9]));
        // New stack: 9
        // IFEQ L32; Stack: 9
        if (cstack8.i == 0) goto L32;
        // New stack: 8
        // INVOKEINTERFACE java/lang/invoke/CallSite.getTarget()Ljava/lang/invoke/MethodHandle;; Stack: 8
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } if (!cmethods[3]) { cmethods[3] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 527LL)), ((char *)(string_pool + 537LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }  } if (cstack7.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 571LL)), -1); else { cstack7.l = env->CallObjectMethod(cstack7.l, (cmethods[3])); refs.insert(cstack7.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 8
        // LABEL L32; Stack: 8
        L32: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 8
        // FRAME FULL L: [[Ljava/lang/Object;, 4] S: [java/lang/ClassLoader, org.objectweb.asm.tree.LabelNode@602c4656, org.objectweb.asm.tree.LabelNode@602c4656, 4, 4, java/lang/Object]; Stack: 8
        refs.erase(cstack0.l); refs.erase(cstack1.l); refs.erase(cstack2.l); refs.erase(cstack7.l); 
        refs.erase(clocal0.l); 
        utils::clear_refs(env, refs);
        // New stack: 8
        // CHECKCAST java/lang/invoke/MethodHandle; Stack: 8
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } if (cstack7.l != nullptr && !env->IsInstanceOf(cstack7.l, (cclasses[10]))) { utils::throw_re(env, ((char *)(string_pool + 94LL)), (std::string(((char *)(string_pool + 123LL))) + std::string(((char *)(string_pool + 598LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } 
        // New stack: 8
        // GOTO L33; Stack: 8
        goto L33;
        // New stack: 8
        // LABEL L22; Stack: 8
        L22: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 8
        // FRAME SAME1 L: null S: [java/lang/Throwable]; Stack: 8
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // INSTANCEOF java/lang/BootstrapMethodError; Stack: 2
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack1.i = cstack1.l == nullptr ? false : env->IsInstanceOf(cstack1.l, (cclasses[11]));
        // New stack: 2
        // IFNE L34; Stack: 2
        if (cstack1.i != 0) goto L34;
        // New stack: 1
        // NEW java/lang/BootstrapMethodError; Stack: 1
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (jobject obj = env->AllocObject((cclasses[11]))) { cstack1.l = obj; refs.insert(obj); } 
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
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[11]), ((char *)(string_pool + 628LL)), ((char *)(string_pool + 635LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 660LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[11]), (cmethods[4]), cstack2.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 1
        // LABEL L34; Stack: 1
        L34: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/Object]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 683LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 0
        // LABEL L33; Stack: 0
        L33: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 0
        // FRAME FULL L: [[Ljava/lang/Object;, 4] S: [java/lang/ClassLoader, org.objectweb.asm.tree.LabelNode@602c4656, org.objectweb.asm.tree.LabelNode@602c4656, 4, 4, java/lang/invoke/MethodHandle]; Stack: 0
        refs.erase(cstack0.l); refs.erase(cstack1.l); refs.erase(cstack2.l); refs.erase(cstack7.l); 
        refs.erase(clocal0.l); 
        utils::clear_refs(env, refs);
        // New stack: 8
        // INVOKESTATIC native/magic/1/invoke/obfuscator0.8175695498474079.a(JJLjava/lang/invoke/MethodHandle;)Ljava/util/Base64$Decoder;; Stack: 8
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[5]) { cmethods[5] = env->GetStaticMethodID((cclasses[12]), ((char *)(string_pool + 694LL)), ((char *)(string_pool + 709LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack3.l = env->CallStaticObjectMethod((cclasses[12]), (cmethods[5]), cstack3.j, cstack5.j, cstack7.l); refs.insert(cstack3.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 4
        // CHECKCAST java/util/Base64$Decoder; Stack: 4
        if (!cclasses[17] || env->IsSameObject(cclasses[17], NULL)) { cclasses_mtx[17].lock(); if (!cclasses[17] || env->IsSameObject(cclasses[17], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[30]))) { cclasses[17] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[17].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack3.l != nullptr && !env->IsInstanceOf(cstack3.l, (cclasses[17]))) { utils::throw_re(env, ((char *)(string_pool + 94LL)), (std::string(((char *)(string_pool + 123LL))) + std::string(((char *)(string_pool + 15668LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } 
        // New stack: 4
        // SIPUSH 14842; Stack: 4
        cstack4.i = (jint) 14842;
        // New stack: 5
        // LDC 8411205426197194864; Stack: 5
        cstack5.j = 8411205426197194864LL;
        // New stack: 7
        // LLOAD 1; Stack: 7
        cstack7.j = clocal1.j;
        // New stack: 9
        // LXOR; Stack: 9
        cstack5.j = cstack5.j ^ cstack7.j;
        // New stack: 7
        // LABEL L19; Stack: 7
        L19: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 7
        // ICONST_1; Stack: 7
        cstack7.i = 1;
        // New stack: 8
        // ANEWARRAY java/lang/Object; Stack: 8
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } if (cstack7.i < 0) utils::throw_re(env, ((char *)(string_pool + 214LL)), ((char *)(string_pool + 251LL)), -1); else { cstack7.l = env->NewObjectArray(cstack7.i, (cclasses[4]), nullptr); refs.insert(cstack7.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 8
        // DUP; Stack: 8
        cstack8 = cstack7;
        // New stack: 9
        // LDC Ldev/sakura/client/Mahiro_i3;; Stack: 9
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } cstack9.l = (cclasses[5]);
        // New stack: 10
        // SWAP; Stack: 10
        std::swap(cstack9, cstack8);
        // New stack: 10
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.8175695498474079.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 10
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } cstack10.l = lookup;
        // New stack: 11
        // LDC Ldev/sakura/client/Mahiro_i3;; Stack: 11
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } cstack11.l = (cclasses[5]);
        // New stack: 12
        // LDC a; Stack: 12
        cstack12.l = (cstrings[6]);
        // New stack: 13
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;; Stack: 13
        cstack13.l = (cstrings[7]);
        // New stack: 14
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.8175695498474079.a()Ljava/lang/ClassLoader;; Stack: 14
        cstack14.l = classloader;
        // New stack: 15
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 15
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } if (!cmethods[1]) { cmethods[1] = env->GetStaticMethodID((cclasses[7]), ((char *)(string_pool + 276LL)), ((char *)(string_pool + 303LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }  } cstack13.l = env->CallStaticObjectMethod((cclasses[7]), (cmethods[1]), cstack13.l, cstack14.l); refs.insert(cstack13.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 14
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 14
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } if (!cmethods[2]) { cmethods[2] = env->GetMethodID((cclasses[8]), ((char *)(string_pool + 376LL)), ((char *)(string_pool + 387LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }  } if (cstack10.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 485LL)), -1); else { cstack10.l = env->CallObjectMethod(cstack10.l, (cmethods[2]), cstack11.l, cstack12.l, cstack13.l); refs.insert(cstack10.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 11
        // SWAP; Stack: 11
        std::swap(cstack10, cstack9);
        // New stack: 11
        // LDC l; Stack: 11
        cstack11.l = (cstrings[31]);
        // New stack: 12
        // SWAP; Stack: 12
        std::swap(cstack11, cstack10);
        // New stack: 12
        // LDC (IJ)Ljava/lang/String;; Stack: 12
        cstack12.l = (cstrings[32]);
        // New stack: 13
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.8175695498474079.a()Ljava/lang/ClassLoader;; Stack: 13
        cstack13.l = classloader;
        // New stack: 14
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 14
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } if (!cmethods[1]) { cmethods[1] = env->GetStaticMethodID((cclasses[7]), ((char *)(string_pool + 276LL)), ((char *)(string_pool + 303LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }  } cstack12.l = env->CallStaticObjectMethod((cclasses[7]), (cmethods[1]), cstack12.l, cstack13.l); refs.insert(cstack12.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 13
        // SWAP; Stack: 13
        std::swap(cstack12, cstack11);
        // New stack: 13
        // LDC 0; Stack: 13
        cstack13.i = 0;
        // New stack: 14
        // ANEWARRAY java/lang/Object; Stack: 14
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } if (cstack13.i < 0) utils::throw_re(env, ((char *)(string_pool + 214LL)), ((char *)(string_pool + 251LL)), -1); else { cstack13.l = env->NewObjectArray(cstack13.i, (cclasses[4]), nullptr); refs.insert(cstack13.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 14
        // CHECKCAST java/lang/Object; Stack: 14
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } if (cstack13.l != nullptr && !env->IsInstanceOf(cstack13.l, (cclasses[4]))) { utils::throw_re(env, ((char *)(string_pool + 94LL)), (std::string(((char *)(string_pool + 123LL))) + std::string(((char *)(string_pool + 510LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } 
        // New stack: 14
        // SWAP; Stack: 14
        std::swap(cstack13, cstack12);
        // New stack: 14
        // INVOKESTATIC native/magic/1/linkcallsite/obfuscator0.8175695498474079.a(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/invoke/MemberName;; Stack: 14
        cstack8.l = utils::link_call_site(env, cstack8.l, cstack9.l, cstack10.l, cstack11.l, cstack12.l, cstack13.l); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 9
        // POP; Stack: 9
        ;
        // New stack: 8
        // ICONST_0; Stack: 8
        cstack8.i = 0;
        // New stack: 9
        // AALOAD; Stack: 9
        if (cstack7.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 83LL)), -1); else { cstack7.l = env->GetObjectArrayElement((jobjectArray) cstack7.l, cstack8.i); refs.insert(cstack7.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 8
        // DUP; Stack: 8
        cstack8 = cstack7;
        // New stack: 9
        // INSTANCEOF java/lang/invoke/CallSite; Stack: 9
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } cstack8.i = cstack8.l == nullptr ? false : env->IsInstanceOf(cstack8.l, (cclasses[9]));
        // New stack: 9
        // IFEQ L35; Stack: 9
        if (cstack8.i == 0) goto L35;
        // New stack: 8
        // INVOKEINTERFACE java/lang/invoke/CallSite.getTarget()Ljava/lang/invoke/MethodHandle;; Stack: 8
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } if (!cmethods[3]) { cmethods[3] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 527LL)), ((char *)(string_pool + 537LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }  } if (cstack7.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 571LL)), -1); else { cstack7.l = env->CallObjectMethod(cstack7.l, (cmethods[3])); refs.insert(cstack7.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 8
        // LABEL L35; Stack: 8
        L35: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 8
        // FRAME FULL L: [[Ljava/lang/Object;, 4] S: [java/lang/ClassLoader, org.objectweb.asm.tree.LabelNode@602c4656, org.objectweb.asm.tree.LabelNode@602c4656, java/util/Base64$Decoder, 1, 4, java/lang/Object]; Stack: 8
        refs.erase(cstack0.l); refs.erase(cstack1.l); refs.erase(cstack2.l); refs.erase(cstack3.l); refs.erase(cstack7.l); 
        refs.erase(clocal0.l); 
        utils::clear_refs(env, refs);
        // New stack: 8
        // CHECKCAST java/lang/invoke/MethodHandle; Stack: 8
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } if (cstack7.l != nullptr && !env->IsInstanceOf(cstack7.l, (cclasses[10]))) { utils::throw_re(env, ((char *)(string_pool + 94LL)), (std::string(((char *)(string_pool + 123LL))) + std::string(((char *)(string_pool + 598LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } 
        // New stack: 8
        // GOTO L36; Stack: 8
        goto L36;
        // New stack: 8
        // LABEL L20; Stack: 8
        L20: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 8
        // FRAME SAME1 L: null S: [java/lang/Throwable]; Stack: 8
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // INSTANCEOF java/lang/BootstrapMethodError; Stack: 2
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack1.i = cstack1.l == nullptr ? false : env->IsInstanceOf(cstack1.l, (cclasses[11]));
        // New stack: 2
        // IFNE L37; Stack: 2
        if (cstack1.i != 0) goto L37;
        // New stack: 1
        // NEW java/lang/BootstrapMethodError; Stack: 1
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (jobject obj = env->AllocObject((cclasses[11]))) { cstack1.l = obj; refs.insert(obj); } 
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
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[11]), ((char *)(string_pool + 628LL)), ((char *)(string_pool + 635LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 660LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[11]), (cmethods[4]), cstack2.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 1
        // LABEL L37; Stack: 1
        L37: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/Object]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 683LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 0
        // LABEL L36; Stack: 0
        L36: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 0
        // FRAME FULL L: [[Ljava/lang/Object;, 4] S: [java/lang/ClassLoader, org.objectweb.asm.tree.LabelNode@602c4656, org.objectweb.asm.tree.LabelNode@602c4656, java/util/Base64$Decoder, 1, 4, java/lang/invoke/MethodHandle]; Stack: 0
        refs.erase(cstack0.l); refs.erase(cstack1.l); refs.erase(cstack2.l); refs.erase(cstack3.l); refs.erase(cstack7.l); 
        refs.erase(clocal0.l); 
        utils::clear_refs(env, refs);
        // New stack: 8
        // INVOKESTATIC native/magic/1/invoke/obfuscator0.8175695498474079.a(IJLjava/lang/invoke/MethodHandle;)Ljava/lang/String;; Stack: 8
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[11]) { cmethods[11] = env->GetStaticMethodID((cclasses[12]), ((char *)(string_pool + 1322LL)), ((char *)(string_pool + 1337LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack4.l = env->CallStaticObjectMethod((cclasses[12]), (cmethods[11]), cstack4.i, cstack5.j, cstack7.l); refs.insert(cstack4.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 5
        // CHECKCAST java/lang/String; Stack: 5
        if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { cclasses_mtx[13].lock(); if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[16]))) { cclasses[13] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[13].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack4.l != nullptr && !env->IsInstanceOf(cstack4.l, (cclasses[13]))) { utils::throw_re(env, ((char *)(string_pool + 94LL)), (std::string(((char *)(string_pool + 123LL))) + std::string(((char *)(string_pool + 1391LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } 
        // New stack: 5
        // LDC -7588789410987816482; Stack: 5
        cstack5.j = -7588789410987816482LL;
        // New stack: 7
        // LLOAD 1; Stack: 7
        cstack7.j = clocal1.j;
        // New stack: 9
        // LABEL L17; Stack: 9
        L17: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }
        // New stack: 9
        // ICONST_1; Stack: 9
        cstack9.i = 1;
        // New stack: 10
        // ANEWARRAY java/lang/Object; Stack: 10
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } if (cstack9.i < 0) utils::throw_re(env, ((char *)(string_pool + 214LL)), ((char *)(string_pool + 251LL)), -1); else { cstack9.l = env->NewObjectArray(cstack9.i, (cclasses[4]), nullptr); refs.insert(cstack9.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }
        // New stack: 10
        // DUP; Stack: 10
        cstack10 = cstack9;
        // New stack: 11
        // LDC Ldev/sakura/client/Mahiro_i3;; Stack: 11
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } cstack11.l = (cclasses[5]);
        // New stack: 12
        // SWAP; Stack: 12
        std::swap(cstack11, cstack10);
        // New stack: 12
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.8175695498474079.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 12
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } cstack12.l = lookup;
        // New stack: 13
        // LDC Ldev/sakura/client/Mahiro_zr;; Stack: 13
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } cstack13.l = (cclasses[6]);
        // New stack: 14
        // LDC a; Stack: 14
        cstack14.l = (cstrings[6]);
        // New stack: 15
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;; Stack: 15
        cstack15.l = (cstrings[7]);
        // New stack: 16
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.8175695498474079.a()Ljava/lang/ClassLoader;; Stack: 16
        cstack16.l = classloader;
        // New stack: 17
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 17
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } if (!cmethods[1]) { cmethods[1] = env->GetStaticMethodID((cclasses[7]), ((char *)(string_pool + 276LL)), ((char *)(string_pool + 303LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }  } cstack15.l = env->CallStaticObjectMethod((cclasses[7]), (cmethods[1]), cstack15.l, cstack16.l); refs.insert(cstack15.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }
        // New stack: 16
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 16
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } if (!cmethods[2]) { cmethods[2] = env->GetMethodID((cclasses[8]), ((char *)(string_pool + 376LL)), ((char *)(string_pool + 387LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }  } if (cstack12.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 485LL)), -1); else { cstack12.l = env->CallObjectMethod(cstack12.l, (cmethods[2]), cstack13.l, cstack14.l, cstack15.l); refs.insert(cstack12.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }
        // New stack: 13
        // SWAP; Stack: 13
        std::swap(cstack12, cstack11);
        // New stack: 13
        // LDC L; Stack: 13
        cstack13.l = (cstrings[22]);
        // New stack: 14
        // SWAP; Stack: 14
        std::swap(cstack13, cstack12);
        // New stack: 14
        // LDC (Ljava/lang/Object;Ljava/lang/Object;JJ)[B; Stack: 14
        cstack14.l = (cstrings[33]);
        // New stack: 15
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.8175695498474079.a()Ljava/lang/ClassLoader;; Stack: 15
        cstack15.l = classloader;
        // New stack: 16
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 16
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } if (!cmethods[1]) { cmethods[1] = env->GetStaticMethodID((cclasses[7]), ((char *)(string_pool + 276LL)), ((char *)(string_pool + 303LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }  } cstack14.l = env->CallStaticObjectMethod((cclasses[7]), (cmethods[1]), cstack14.l, cstack15.l); refs.insert(cstack14.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }
        // New stack: 15
        // SWAP; Stack: 15
        std::swap(cstack14, cstack13);
        // New stack: 15
        // LDC 0; Stack: 15
        cstack15.i = 0;
        // New stack: 16
        // ANEWARRAY java/lang/Object; Stack: 16
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } if (cstack15.i < 0) utils::throw_re(env, ((char *)(string_pool + 214LL)), ((char *)(string_pool + 251LL)), -1); else { cstack15.l = env->NewObjectArray(cstack15.i, (cclasses[4]), nullptr); refs.insert(cstack15.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }
        // New stack: 16
        // CHECKCAST java/lang/Object; Stack: 16
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } if (cstack15.l != nullptr && !env->IsInstanceOf(cstack15.l, (cclasses[4]))) { utils::throw_re(env, ((char *)(string_pool + 94LL)), (std::string(((char *)(string_pool + 123LL))) + std::string(((char *)(string_pool + 510LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } 
        // New stack: 16
        // SWAP; Stack: 16
        std::swap(cstack15, cstack14);
        // New stack: 16
        // INVOKESTATIC native/magic/1/linkcallsite/obfuscator0.8175695498474079.a(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/invoke/MemberName;; Stack: 16
        cstack10.l = utils::link_call_site(env, cstack10.l, cstack11.l, cstack12.l, cstack13.l, cstack14.l, cstack15.l); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }
        // New stack: 11
        // POP; Stack: 11
        ;
        // New stack: 10
        // ICONST_0; Stack: 10
        cstack10.i = 0;
        // New stack: 11
        // AALOAD; Stack: 11
        if (cstack9.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 83LL)), -1); else { cstack9.l = env->GetObjectArrayElement((jobjectArray) cstack9.l, cstack10.i); refs.insert(cstack9.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }
        // New stack: 10
        // DUP; Stack: 10
        cstack10 = cstack9;
        // New stack: 11
        // INSTANCEOF java/lang/invoke/CallSite; Stack: 11
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } cstack10.i = cstack10.l == nullptr ? false : env->IsInstanceOf(cstack10.l, (cclasses[9]));
        // New stack: 11
        // IFEQ L38; Stack: 11
        if (cstack10.i == 0) goto L38;
        // New stack: 10
        // INVOKEINTERFACE java/lang/invoke/CallSite.getTarget()Ljava/lang/invoke/MethodHandle;; Stack: 10
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } if (!cmethods[3]) { cmethods[3] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 527LL)), ((char *)(string_pool + 537LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }  } if (cstack9.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 571LL)), -1); else { cstack9.l = env->CallObjectMethod(cstack9.l, (cmethods[3])); refs.insert(cstack9.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }
        // New stack: 10
        // LABEL L38; Stack: 10
        L38: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }
        // New stack: 10
        // FRAME FULL L: [[Ljava/lang/Object;, 4] S: [java/lang/ClassLoader, org.objectweb.asm.tree.LabelNode@602c4656, org.objectweb.asm.tree.LabelNode@602c4656, java/util/Base64$Decoder, java/lang/String, 4, 4, java/lang/Object]; Stack: 10
        refs.erase(cstack0.l); refs.erase(cstack1.l); refs.erase(cstack2.l); refs.erase(cstack3.l); refs.erase(cstack4.l); refs.erase(cstack9.l); 
        refs.erase(clocal0.l); 
        utils::clear_refs(env, refs);
        // New stack: 10
        // CHECKCAST java/lang/invoke/MethodHandle; Stack: 10
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } if (cstack9.l != nullptr && !env->IsInstanceOf(cstack9.l, (cclasses[10]))) { utils::throw_re(env, ((char *)(string_pool + 94LL)), (std::string(((char *)(string_pool + 123LL))) + std::string(((char *)(string_pool + 598LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } 
        // New stack: 10
        // GOTO L39; Stack: 10
        goto L39;
        // New stack: 10
        // LABEL L18; Stack: 10
        L18: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 10
        // FRAME SAME1 L: null S: [java/lang/Throwable]; Stack: 10
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // INSTANCEOF java/lang/BootstrapMethodError; Stack: 2
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack1.i = cstack1.l == nullptr ? false : env->IsInstanceOf(cstack1.l, (cclasses[11]));
        // New stack: 2
        // IFNE L40; Stack: 2
        if (cstack1.i != 0) goto L40;
        // New stack: 1
        // NEW java/lang/BootstrapMethodError; Stack: 1
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (jobject obj = env->AllocObject((cclasses[11]))) { cstack1.l = obj; refs.insert(obj); } 
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
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[11]), ((char *)(string_pool + 628LL)), ((char *)(string_pool + 635LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 660LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[11]), (cmethods[4]), cstack2.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 1
        // LABEL L40; Stack: 1
        L40: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/Object]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 683LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 0
        // LABEL L39; Stack: 0
        L39: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 0
        // FRAME FULL L: [[Ljava/lang/Object;, 4] S: [java/lang/ClassLoader, org.objectweb.asm.tree.LabelNode@602c4656, org.objectweb.asm.tree.LabelNode@602c4656, java/util/Base64$Decoder, java/lang/String, 4, 4, java/lang/invoke/MethodHandle]; Stack: 0
        refs.erase(cstack0.l); refs.erase(cstack1.l); refs.erase(cstack2.l); refs.erase(cstack3.l); refs.erase(cstack4.l); refs.erase(cstack9.l); 
        refs.erase(clocal0.l); 
        utils::clear_refs(env, refs);
        // New stack: 10
        // INVOKESTATIC native/magic/1/invoke/obfuscator0.8175695498474079.a(Ljava/lang/Object;Ljava/lang/Object;JJLjava/lang/invoke/MethodHandle;)[B; Stack: 10
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[12]) { cmethods[12] = env->GetStaticMethodID((cclasses[12]), ((char *)(string_pool + 1496LL)), ((char *)(string_pool + 1511LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack3.l = env->CallStaticObjectMethod((cclasses[12]), (cmethods[12]), cstack3.l, cstack4.l, cstack5.j, cstack7.j, cstack9.l); refs.insert(cstack3.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 4
        // CHECKCAST [B; Stack: 4
        if (!cclasses[18] || env->IsSameObject(cclasses[18], NULL)) { cclasses_mtx[18].lock(); if (!cclasses[18] || env->IsSameObject(cclasses[18], NULL)) { if (jclass clazz = env->FindClass(((char *)(string_pool + 8413LL)))) { cclasses[18] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[18].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack3.l != nullptr && !env->IsInstanceOf(cstack3.l, (cclasses[18]))) { utils::throw_re(env, ((char *)(string_pool + 94LL)), (std::string(((char *)(string_pool + 123LL))) + std::string(((char *)(string_pool + 8413LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } 
        // New stack: 4
        // INVOKESPECIAL java/lang/String.<init>([B)V; Stack: 4
        if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { cclasses_mtx[13].lock(); if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[16]))) { cclasses[13] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[13].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[13]) { cmethods[13] = env->GetMethodID((cclasses[13]), ((char *)(string_pool + 628LL)), ((char *)(string_pool + 2695LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack2.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 660LL)), -1); else env->CallNonvirtualVoidMethod(cstack2.l, (cclasses[13]), (cmethods[13]), cstack3.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 2
        // INVOKESTATIC dev/sakura/client/Mahiro_hc.a(Ljava/lang/String;)Ljava/lang/String;; Stack: 2
        if (!cclasses[19] || env->IsSameObject(cclasses[19], NULL)) { cclasses_mtx[19].lock(); if (!cclasses[19] || env->IsSameObject(cclasses[19], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[34]))) { cclasses[19] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[19].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[14]) { cmethods[14] = env->GetStaticMethodID((cclasses[19]), ((char *)(string_pool + 210LL)), ((char *)(string_pool + 8583LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack1.l = env->CallStaticObjectMethod((cclasses[19]), (cmethods[14]), cstack1.l); refs.insert(cstack1.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 2
        // LDC -7568751362457486232; Stack: 2
        cstack2.j = -7568751362457486232LL;
        // New stack: 4
        // LLOAD 1; Stack: 4
        cstack4.j = clocal1.j;
        // New stack: 6
        // LABEL L15; Stack: 6
        L15: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }
        // New stack: 6
        // ICONST_1; Stack: 6
        cstack6.i = 1;
        // New stack: 7
        // ANEWARRAY java/lang/Object; Stack: 7
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } if (cstack6.i < 0) utils::throw_re(env, ((char *)(string_pool + 214LL)), ((char *)(string_pool + 251LL)), -1); else { cstack6.l = env->NewObjectArray(cstack6.i, (cclasses[4]), nullptr); refs.insert(cstack6.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }
        // New stack: 7
        // DUP; Stack: 7
        cstack7 = cstack6;
        // New stack: 8
        // LDC Ldev/sakura/client/Mahiro_i3;; Stack: 8
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } cstack8.l = (cclasses[5]);
        // New stack: 9
        // SWAP; Stack: 9
        std::swap(cstack8, cstack7);
        // New stack: 9
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.8175695498474079.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 9
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } cstack9.l = lookup;
        // New stack: 10
        // LDC Ldev/sakura/client/Mahiro_zr;; Stack: 10
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } cstack10.l = (cclasses[6]);
        // New stack: 11
        // LDC a; Stack: 11
        cstack11.l = (cstrings[6]);
        // New stack: 12
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;; Stack: 12
        cstack12.l = (cstrings[7]);
        // New stack: 13
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.8175695498474079.a()Ljava/lang/ClassLoader;; Stack: 13
        cstack13.l = classloader;
        // New stack: 14
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 14
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } if (!cmethods[1]) { cmethods[1] = env->GetStaticMethodID((cclasses[7]), ((char *)(string_pool + 276LL)), ((char *)(string_pool + 303LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }  } cstack12.l = env->CallStaticObjectMethod((cclasses[7]), (cmethods[1]), cstack12.l, cstack13.l); refs.insert(cstack12.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }
        // New stack: 13
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 13
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } if (!cmethods[2]) { cmethods[2] = env->GetMethodID((cclasses[8]), ((char *)(string_pool + 376LL)), ((char *)(string_pool + 387LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }  } if (cstack9.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 485LL)), -1); else { cstack9.l = env->CallObjectMethod(cstack9.l, (cmethods[2]), cstack10.l, cstack11.l, cstack12.l); refs.insert(cstack9.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }
        // New stack: 10
        // SWAP; Stack: 10
        std::swap(cstack9, cstack8);
        // New stack: 10
        // LDC L; Stack: 10
        cstack10.l = (cstrings[22]);
        // New stack: 11
        // SWAP; Stack: 11
        std::swap(cstack10, cstack9);
        // New stack: 11
        // LDC (Ljava/lang/Object;Ljava/lang/Object;JJ)Ljava/lang/Class;; Stack: 11
        cstack11.l = (cstrings[35]);
        // New stack: 12
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.8175695498474079.a()Ljava/lang/ClassLoader;; Stack: 12
        cstack12.l = classloader;
        // New stack: 13
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 13
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } if (!cmethods[1]) { cmethods[1] = env->GetStaticMethodID((cclasses[7]), ((char *)(string_pool + 276LL)), ((char *)(string_pool + 303LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }  } cstack11.l = env->CallStaticObjectMethod((cclasses[7]), (cmethods[1]), cstack11.l, cstack12.l); refs.insert(cstack11.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }
        // New stack: 12
        // SWAP; Stack: 12
        std::swap(cstack11, cstack10);
        // New stack: 12
        // LDC 0; Stack: 12
        cstack12.i = 0;
        // New stack: 13
        // ANEWARRAY java/lang/Object; Stack: 13
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } if (cstack12.i < 0) utils::throw_re(env, ((char *)(string_pool + 214LL)), ((char *)(string_pool + 251LL)), -1); else { cstack12.l = env->NewObjectArray(cstack12.i, (cclasses[4]), nullptr); refs.insert(cstack12.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }
        // New stack: 13
        // CHECKCAST java/lang/Object; Stack: 13
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } if (cstack12.l != nullptr && !env->IsInstanceOf(cstack12.l, (cclasses[4]))) { utils::throw_re(env, ((char *)(string_pool + 94LL)), (std::string(((char *)(string_pool + 123LL))) + std::string(((char *)(string_pool + 510LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } 
        // New stack: 13
        // SWAP; Stack: 13
        std::swap(cstack12, cstack11);
        // New stack: 13
        // INVOKESTATIC native/magic/1/linkcallsite/obfuscator0.8175695498474079.a(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/invoke/MemberName;; Stack: 13
        cstack7.l = utils::link_call_site(env, cstack7.l, cstack8.l, cstack9.l, cstack10.l, cstack11.l, cstack12.l); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }
        // New stack: 8
        // POP; Stack: 8
        ;
        // New stack: 7
        // ICONST_0; Stack: 7
        cstack7.i = 0;
        // New stack: 8
        // AALOAD; Stack: 8
        if (cstack6.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 83LL)), -1); else { cstack6.l = env->GetObjectArrayElement((jobjectArray) cstack6.l, cstack7.i); refs.insert(cstack6.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }
        // New stack: 7
        // DUP; Stack: 7
        cstack7 = cstack6;
        // New stack: 8
        // INSTANCEOF java/lang/invoke/CallSite; Stack: 8
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } cstack7.i = cstack7.l == nullptr ? false : env->IsInstanceOf(cstack7.l, (cclasses[9]));
        // New stack: 8
        // IFEQ L41; Stack: 8
        if (cstack7.i == 0) goto L41;
        // New stack: 7
        // INVOKEINTERFACE java/lang/invoke/CallSite.getTarget()Ljava/lang/invoke/MethodHandle;; Stack: 7
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } if (!cmethods[3]) { cmethods[3] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 527LL)), ((char *)(string_pool + 537LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }  } if (cstack6.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 571LL)), -1); else { cstack6.l = env->CallObjectMethod(cstack6.l, (cmethods[3])); refs.insert(cstack6.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }
        // New stack: 7
        // LABEL L41; Stack: 7
        L41: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }
        // New stack: 7
        // FRAME FULL L: [[Ljava/lang/Object;, 4] S: [java/lang/ClassLoader, java/lang/String, 4, 4, java/lang/Object]; Stack: 7
        refs.erase(cstack0.l); refs.erase(cstack1.l); refs.erase(cstack6.l); 
        refs.erase(clocal0.l); 
        utils::clear_refs(env, refs);
        // New stack: 7
        // CHECKCAST java/lang/invoke/MethodHandle; Stack: 7
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } if (cstack6.l != nullptr && !env->IsInstanceOf(cstack6.l, (cclasses[10]))) { utils::throw_re(env, ((char *)(string_pool + 94LL)), (std::string(((char *)(string_pool + 123LL))) + std::string(((char *)(string_pool + 598LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } 
        // New stack: 7
        // GOTO L42; Stack: 7
        goto L42;
        // New stack: 7
        // LABEL L16; Stack: 7
        L16: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 7
        // FRAME SAME1 L: null S: [java/lang/Throwable]; Stack: 7
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // INSTANCEOF java/lang/BootstrapMethodError; Stack: 2
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack1.i = cstack1.l == nullptr ? false : env->IsInstanceOf(cstack1.l, (cclasses[11]));
        // New stack: 2
        // IFNE L43; Stack: 2
        if (cstack1.i != 0) goto L43;
        // New stack: 1
        // NEW java/lang/BootstrapMethodError; Stack: 1
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (jobject obj = env->AllocObject((cclasses[11]))) { cstack1.l = obj; refs.insert(obj); } 
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
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[11]), ((char *)(string_pool + 628LL)), ((char *)(string_pool + 635LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 660LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[11]), (cmethods[4]), cstack2.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 1
        // LABEL L43; Stack: 1
        L43: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/Object]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 683LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 0
        // LABEL L42; Stack: 0
        L42: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 0
        // FRAME FULL L: [[Ljava/lang/Object;, 4] S: [java/lang/ClassLoader, java/lang/String, 4, 4, java/lang/invoke/MethodHandle]; Stack: 0
        refs.erase(cstack0.l); refs.erase(cstack1.l); refs.erase(cstack6.l); 
        refs.erase(clocal0.l); 
        utils::clear_refs(env, refs);
        // New stack: 7
        // INVOKESTATIC native/magic/1/invoke/obfuscator0.8175695498474079.a(Ljava/lang/Object;Ljava/lang/Object;JJLjava/lang/invoke/MethodHandle;)Ljava/lang/Class;; Stack: 7
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[12]) { cmethods[12] = env->GetStaticMethodID((cclasses[12]), ((char *)(string_pool + 1496LL)), ((char *)(string_pool + 1511LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack0.l = env->CallStaticObjectMethod((cclasses[12]), (cmethods[12]), cstack0.l, cstack1.l, cstack2.j, cstack4.j, cstack6.l); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 1
        // CHECKCAST java/lang/Class; Stack: 1
        if (!cclasses[20] || env->IsSameObject(cclasses[20], NULL)) { cclasses_mtx[20].lock(); if (!cclasses[20] || env->IsSameObject(cclasses[20], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[36]))) { cclasses[20] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[20].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack0.l != nullptr && !env->IsInstanceOf(cstack0.l, (cclasses[20]))) { utils::throw_re(env, ((char *)(string_pool + 94LL)), (std::string(((char *)(string_pool + 123LL))) + std::string(((char *)(string_pool + 2211LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } 
        // New stack: 1
        // ASTORE 3; Stack: 1
        clocal3.l = cstack0.l; refs.insert(cstack0.l);
        // New stack: 0
        // ALOAD 3; Stack: 0
        cstack0.l = clocal3.l; refs.insert(cstack0.l);
        // New stack: 1
        // LABEL L44; Stack: 1
        L44: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 1
        // NEW java/lang/String; Stack: 1
        if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { cclasses_mtx[13].lock(); if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[16]))) { cclasses[13] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[13].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (jobject obj = env->AllocObject((cclasses[13]))) { cstack1.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 2
        // DUP; Stack: 2
        cstack2 = cstack1;
        // New stack: 3
        // LDC -7567672345588810436; Stack: 3
        cstack3.j = -7567672345588810436LL;
        // New stack: 5
        // LLOAD 1; Stack: 5
        cstack5.j = clocal1.j;
        // New stack: 7
        // LABEL L13; Stack: 7
        L13: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; }
        // New stack: 7
        // ICONST_1; Stack: 7
        cstack7.i = 1;
        // New stack: 8
        // ANEWARRAY java/lang/Object; Stack: 8
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; } } if (cstack7.i < 0) utils::throw_re(env, ((char *)(string_pool + 214LL)), ((char *)(string_pool + 251LL)), -1); else { cstack7.l = env->NewObjectArray(cstack7.i, (cclasses[4]), nullptr); refs.insert(cstack7.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; }
        // New stack: 8
        // DUP; Stack: 8
        cstack8 = cstack7;
        // New stack: 9
        // LDC Ldev/sakura/client/Mahiro_i3;; Stack: 9
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; } } cstack9.l = (cclasses[5]);
        // New stack: 10
        // SWAP; Stack: 10
        std::swap(cstack9, cstack8);
        // New stack: 10
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.8175695498474079.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 10
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; } } cstack10.l = lookup;
        // New stack: 11
        // LDC Ldev/sakura/client/Mahiro_zr;; Stack: 11
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; } } cstack11.l = (cclasses[6]);
        // New stack: 12
        // LDC a; Stack: 12
        cstack12.l = (cstrings[6]);
        // New stack: 13
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;; Stack: 13
        cstack13.l = (cstrings[7]);
        // New stack: 14
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.8175695498474079.a()Ljava/lang/ClassLoader;; Stack: 14
        cstack14.l = classloader;
        // New stack: 15
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 15
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; } } if (!cmethods[1]) { cmethods[1] = env->GetStaticMethodID((cclasses[7]), ((char *)(string_pool + 276LL)), ((char *)(string_pool + 303LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; }  } cstack13.l = env->CallStaticObjectMethod((cclasses[7]), (cmethods[1]), cstack13.l, cstack14.l); refs.insert(cstack13.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; }
        // New stack: 14
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 14
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; } } if (!cmethods[2]) { cmethods[2] = env->GetMethodID((cclasses[8]), ((char *)(string_pool + 376LL)), ((char *)(string_pool + 387LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; }  } if (cstack10.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 485LL)), -1); else { cstack10.l = env->CallObjectMethod(cstack10.l, (cmethods[2]), cstack11.l, cstack12.l, cstack13.l); refs.insert(cstack10.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; }
        // New stack: 11
        // SWAP; Stack: 11
        std::swap(cstack10, cstack9);
        // New stack: 11
        // LDC \u00d4; Stack: 11
        cstack11.l = (cstrings[10]);
        // New stack: 12
        // SWAP; Stack: 12
        std::swap(cstack11, cstack10);
        // New stack: 12
        // LDC (JJ)Ljava/util/Base64$Decoder;; Stack: 12
        cstack12.l = (cstrings[29]);
        // New stack: 13
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.8175695498474079.a()Ljava/lang/ClassLoader;; Stack: 13
        cstack13.l = classloader;
        // New stack: 14
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 14
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; } } if (!cmethods[1]) { cmethods[1] = env->GetStaticMethodID((cclasses[7]), ((char *)(string_pool + 276LL)), ((char *)(string_pool + 303LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; }  } cstack12.l = env->CallStaticObjectMethod((cclasses[7]), (cmethods[1]), cstack12.l, cstack13.l); refs.insert(cstack12.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; }
        // New stack: 13
        // SWAP; Stack: 13
        std::swap(cstack12, cstack11);
        // New stack: 13
        // LDC 0; Stack: 13
        cstack13.i = 0;
        // New stack: 14
        // ANEWARRAY java/lang/Object; Stack: 14
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; } } if (cstack13.i < 0) utils::throw_re(env, ((char *)(string_pool + 214LL)), ((char *)(string_pool + 251LL)), -1); else { cstack13.l = env->NewObjectArray(cstack13.i, (cclasses[4]), nullptr); refs.insert(cstack13.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; }
        // New stack: 14
        // CHECKCAST java/lang/Object; Stack: 14
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; } } if (cstack13.l != nullptr && !env->IsInstanceOf(cstack13.l, (cclasses[4]))) { utils::throw_re(env, ((char *)(string_pool + 94LL)), (std::string(((char *)(string_pool + 123LL))) + std::string(((char *)(string_pool + 510LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; } } 
        // New stack: 14
        // SWAP; Stack: 14
        std::swap(cstack13, cstack12);
        // New stack: 14
        // INVOKESTATIC native/magic/1/linkcallsite/obfuscator0.8175695498474079.a(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/invoke/MemberName;; Stack: 14
        cstack8.l = utils::link_call_site(env, cstack8.l, cstack9.l, cstack10.l, cstack11.l, cstack12.l, cstack13.l); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; }
        // New stack: 9
        // POP; Stack: 9
        ;
        // New stack: 8
        // ICONST_0; Stack: 8
        cstack8.i = 0;
        // New stack: 9
        // AALOAD; Stack: 9
        if (cstack7.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 83LL)), -1); else { cstack7.l = env->GetObjectArrayElement((jobjectArray) cstack7.l, cstack8.i); refs.insert(cstack7.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; }
        // New stack: 8
        // DUP; Stack: 8
        cstack8 = cstack7;
        // New stack: 9
        // INSTANCEOF java/lang/invoke/CallSite; Stack: 9
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; } } cstack8.i = cstack8.l == nullptr ? false : env->IsInstanceOf(cstack8.l, (cclasses[9]));
        // New stack: 9
        // IFEQ L45; Stack: 9
        if (cstack8.i == 0) goto L45;
        // New stack: 8
        // INVOKEINTERFACE java/lang/invoke/CallSite.getTarget()Ljava/lang/invoke/MethodHandle;; Stack: 8
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; } } if (!cmethods[3]) { cmethods[3] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 527LL)), ((char *)(string_pool + 537LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; }  } if (cstack7.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 571LL)), -1); else { cstack7.l = env->CallObjectMethod(cstack7.l, (cmethods[3])); refs.insert(cstack7.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; }
        // New stack: 8
        // LABEL L45; Stack: 8
        L45: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; }
        // New stack: 8
        // FRAME FULL L: [[Ljava/lang/Object;, 4, java/lang/Class] S: [java/lang/Class, org.objectweb.asm.tree.LabelNode@4a60ee36, org.objectweb.asm.tree.LabelNode@4a60ee36, 4, 4, java/lang/Object]; Stack: 8
        refs.erase(cstack0.l); refs.erase(cstack1.l); refs.erase(cstack2.l); refs.erase(cstack7.l); 
        refs.erase(clocal0.l); refs.erase(clocal3.l); 
        utils::clear_refs(env, refs);
        // New stack: 8
        // CHECKCAST java/lang/invoke/MethodHandle; Stack: 8
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; } } if (cstack7.l != nullptr && !env->IsInstanceOf(cstack7.l, (cclasses[10]))) { utils::throw_re(env, ((char *)(string_pool + 94LL)), (std::string(((char *)(string_pool + 123LL))) + std::string(((char *)(string_pool + 598LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; } } 
        // New stack: 8
        // GOTO L46; Stack: 8
        goto L46;
        // New stack: 8
        // LABEL L14; Stack: 8
        L14: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 8
        // FRAME SAME1 L: null S: [java/lang/Throwable]; Stack: 8
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal3.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // INSTANCEOF java/lang/BootstrapMethodError; Stack: 2
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack1.i = cstack1.l == nullptr ? false : env->IsInstanceOf(cstack1.l, (cclasses[11]));
        // New stack: 2
        // IFNE L47; Stack: 2
        if (cstack1.i != 0) goto L47;
        // New stack: 1
        // NEW java/lang/BootstrapMethodError; Stack: 1
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (jobject obj = env->AllocObject((cclasses[11]))) { cstack1.l = obj; refs.insert(obj); } 
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
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[11]), ((char *)(string_pool + 628LL)), ((char *)(string_pool + 635LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 660LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[11]), (cmethods[4]), cstack2.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 1
        // LABEL L47; Stack: 1
        L47: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/Object]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal3.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 683LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 0
        // LABEL L46; Stack: 0
        L46: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 0
        // FRAME FULL L: [[Ljava/lang/Object;, 4, java/lang/Class] S: [java/lang/Class, org.objectweb.asm.tree.LabelNode@4a60ee36, org.objectweb.asm.tree.LabelNode@4a60ee36, 4, 4, java/lang/invoke/MethodHandle]; Stack: 0
        refs.erase(cstack0.l); refs.erase(cstack1.l); refs.erase(cstack2.l); refs.erase(cstack7.l); 
        refs.erase(clocal0.l); refs.erase(clocal3.l); 
        utils::clear_refs(env, refs);
        // New stack: 8
        // INVOKESTATIC native/magic/1/invoke/obfuscator0.8175695498474079.a(JJLjava/lang/invoke/MethodHandle;)Ljava/util/Base64$Decoder;; Stack: 8
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[5]) { cmethods[5] = env->GetStaticMethodID((cclasses[12]), ((char *)(string_pool + 694LL)), ((char *)(string_pool + 709LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack3.l = env->CallStaticObjectMethod((cclasses[12]), (cmethods[5]), cstack3.j, cstack5.j, cstack7.l); refs.insert(cstack3.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 4
        // CHECKCAST java/util/Base64$Decoder; Stack: 4
        if (!cclasses[17] || env->IsSameObject(cclasses[17], NULL)) { cclasses_mtx[17].lock(); if (!cclasses[17] || env->IsSameObject(cclasses[17], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[30]))) { cclasses[17] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[17].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack3.l != nullptr && !env->IsInstanceOf(cstack3.l, (cclasses[17]))) { utils::throw_re(env, ((char *)(string_pool + 94LL)), (std::string(((char *)(string_pool + 123LL))) + std::string(((char *)(string_pool + 15668LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } 
        // New stack: 4
        // SIPUSH 5560; Stack: 4
        cstack4.i = (jint) 5560;
        // New stack: 5
        // LDC 7892683080006157363; Stack: 5
        cstack5.j = 7892683080006157363LL;
        // New stack: 7
        // LLOAD 1; Stack: 7
        cstack7.j = clocal1.j;
        // New stack: 9
        // LXOR; Stack: 9
        cstack5.j = cstack5.j ^ cstack7.j;
        // New stack: 7
        // LABEL L11; Stack: 7
        L11: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; }
        // New stack: 7
        // ICONST_1; Stack: 7
        cstack7.i = 1;
        // New stack: 8
        // ANEWARRAY java/lang/Object; Stack: 8
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; } } if (cstack7.i < 0) utils::throw_re(env, ((char *)(string_pool + 214LL)), ((char *)(string_pool + 251LL)), -1); else { cstack7.l = env->NewObjectArray(cstack7.i, (cclasses[4]), nullptr); refs.insert(cstack7.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; }
        // New stack: 8
        // DUP; Stack: 8
        cstack8 = cstack7;
        // New stack: 9
        // LDC Ldev/sakura/client/Mahiro_i3;; Stack: 9
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; } } cstack9.l = (cclasses[5]);
        // New stack: 10
        // SWAP; Stack: 10
        std::swap(cstack9, cstack8);
        // New stack: 10
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.8175695498474079.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 10
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; } } cstack10.l = lookup;
        // New stack: 11
        // LDC Ldev/sakura/client/Mahiro_i3;; Stack: 11
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; } } cstack11.l = (cclasses[5]);
        // New stack: 12
        // LDC a; Stack: 12
        cstack12.l = (cstrings[6]);
        // New stack: 13
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;; Stack: 13
        cstack13.l = (cstrings[7]);
        // New stack: 14
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.8175695498474079.a()Ljava/lang/ClassLoader;; Stack: 14
        cstack14.l = classloader;
        // New stack: 15
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 15
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; } } if (!cmethods[1]) { cmethods[1] = env->GetStaticMethodID((cclasses[7]), ((char *)(string_pool + 276LL)), ((char *)(string_pool + 303LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; }  } cstack13.l = env->CallStaticObjectMethod((cclasses[7]), (cmethods[1]), cstack13.l, cstack14.l); refs.insert(cstack13.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; }
        // New stack: 14
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 14
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; } } if (!cmethods[2]) { cmethods[2] = env->GetMethodID((cclasses[8]), ((char *)(string_pool + 376LL)), ((char *)(string_pool + 387LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; }  } if (cstack10.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 485LL)), -1); else { cstack10.l = env->CallObjectMethod(cstack10.l, (cmethods[2]), cstack11.l, cstack12.l, cstack13.l); refs.insert(cstack10.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; }
        // New stack: 11
        // SWAP; Stack: 11
        std::swap(cstack10, cstack9);
        // New stack: 11
        // LDC l; Stack: 11
        cstack11.l = (cstrings[31]);
        // New stack: 12
        // SWAP; Stack: 12
        std::swap(cstack11, cstack10);
        // New stack: 12
        // LDC (IJ)Ljava/lang/String;; Stack: 12
        cstack12.l = (cstrings[32]);
        // New stack: 13
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.8175695498474079.a()Ljava/lang/ClassLoader;; Stack: 13
        cstack13.l = classloader;
        // New stack: 14
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 14
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; } } if (!cmethods[1]) { cmethods[1] = env->GetStaticMethodID((cclasses[7]), ((char *)(string_pool + 276LL)), ((char *)(string_pool + 303LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; }  } cstack12.l = env->CallStaticObjectMethod((cclasses[7]), (cmethods[1]), cstack12.l, cstack13.l); refs.insert(cstack12.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; }
        // New stack: 13
        // SWAP; Stack: 13
        std::swap(cstack12, cstack11);
        // New stack: 13
        // LDC 0; Stack: 13
        cstack13.i = 0;
        // New stack: 14
        // ANEWARRAY java/lang/Object; Stack: 14
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; } } if (cstack13.i < 0) utils::throw_re(env, ((char *)(string_pool + 214LL)), ((char *)(string_pool + 251LL)), -1); else { cstack13.l = env->NewObjectArray(cstack13.i, (cclasses[4]), nullptr); refs.insert(cstack13.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; }
        // New stack: 14
        // CHECKCAST java/lang/Object; Stack: 14
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; } } if (cstack13.l != nullptr && !env->IsInstanceOf(cstack13.l, (cclasses[4]))) { utils::throw_re(env, ((char *)(string_pool + 94LL)), (std::string(((char *)(string_pool + 123LL))) + std::string(((char *)(string_pool + 510LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; } } 
        // New stack: 14
        // SWAP; Stack: 14
        std::swap(cstack13, cstack12);
        // New stack: 14
        // INVOKESTATIC native/magic/1/linkcallsite/obfuscator0.8175695498474079.a(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/invoke/MemberName;; Stack: 14
        cstack8.l = utils::link_call_site(env, cstack8.l, cstack9.l, cstack10.l, cstack11.l, cstack12.l, cstack13.l); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; }
        // New stack: 9
        // POP; Stack: 9
        ;
        // New stack: 8
        // ICONST_0; Stack: 8
        cstack8.i = 0;
        // New stack: 9
        // AALOAD; Stack: 9
        if (cstack7.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 83LL)), -1); else { cstack7.l = env->GetObjectArrayElement((jobjectArray) cstack7.l, cstack8.i); refs.insert(cstack7.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; }
        // New stack: 8
        // DUP; Stack: 8
        cstack8 = cstack7;
        // New stack: 9
        // INSTANCEOF java/lang/invoke/CallSite; Stack: 9
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; } } cstack8.i = cstack8.l == nullptr ? false : env->IsInstanceOf(cstack8.l, (cclasses[9]));
        // New stack: 9
        // IFEQ L48; Stack: 9
        if (cstack8.i == 0) goto L48;
        // New stack: 8
        // INVOKEINTERFACE java/lang/invoke/CallSite.getTarget()Ljava/lang/invoke/MethodHandle;; Stack: 8
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; } } if (!cmethods[3]) { cmethods[3] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 527LL)), ((char *)(string_pool + 537LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; }  } if (cstack7.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 571LL)), -1); else { cstack7.l = env->CallObjectMethod(cstack7.l, (cmethods[3])); refs.insert(cstack7.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; }
        // New stack: 8
        // LABEL L48; Stack: 8
        L48: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; }
        // New stack: 8
        // FRAME FULL L: [[Ljava/lang/Object;, 4, java/lang/Class] S: [java/lang/Class, org.objectweb.asm.tree.LabelNode@4a60ee36, org.objectweb.asm.tree.LabelNode@4a60ee36, java/util/Base64$Decoder, 1, 4, java/lang/Object]; Stack: 8
        refs.erase(cstack0.l); refs.erase(cstack1.l); refs.erase(cstack2.l); refs.erase(cstack3.l); refs.erase(cstack7.l); 
        refs.erase(clocal0.l); refs.erase(clocal3.l); 
        utils::clear_refs(env, refs);
        // New stack: 8
        // CHECKCAST java/lang/invoke/MethodHandle; Stack: 8
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; } } if (cstack7.l != nullptr && !env->IsInstanceOf(cstack7.l, (cclasses[10]))) { utils::throw_re(env, ((char *)(string_pool + 94LL)), (std::string(((char *)(string_pool + 123LL))) + std::string(((char *)(string_pool + 598LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; } } 
        // New stack: 8
        // GOTO L49; Stack: 8
        goto L49;
        // New stack: 8
        // LABEL L12; Stack: 8
        L12: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 8
        // FRAME SAME1 L: null S: [java/lang/Throwable]; Stack: 8
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal3.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // INSTANCEOF java/lang/BootstrapMethodError; Stack: 2
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack1.i = cstack1.l == nullptr ? false : env->IsInstanceOf(cstack1.l, (cclasses[11]));
        // New stack: 2
        // IFNE L50; Stack: 2
        if (cstack1.i != 0) goto L50;
        // New stack: 1
        // NEW java/lang/BootstrapMethodError; Stack: 1
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (jobject obj = env->AllocObject((cclasses[11]))) { cstack1.l = obj; refs.insert(obj); } 
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
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[11]), ((char *)(string_pool + 628LL)), ((char *)(string_pool + 635LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 660LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[11]), (cmethods[4]), cstack2.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 1
        // LABEL L50; Stack: 1
        L50: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/Object]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal3.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 683LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 0
        // LABEL L49; Stack: 0
        L49: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 0
        // FRAME FULL L: [[Ljava/lang/Object;, 4, java/lang/Class] S: [java/lang/Class, org.objectweb.asm.tree.LabelNode@4a60ee36, org.objectweb.asm.tree.LabelNode@4a60ee36, java/util/Base64$Decoder, 1, 4, java/lang/invoke/MethodHandle]; Stack: 0
        refs.erase(cstack0.l); refs.erase(cstack1.l); refs.erase(cstack2.l); refs.erase(cstack3.l); refs.erase(cstack7.l); 
        refs.erase(clocal0.l); refs.erase(clocal3.l); 
        utils::clear_refs(env, refs);
        // New stack: 8
        // INVOKESTATIC native/magic/1/invoke/obfuscator0.8175695498474079.a(IJLjava/lang/invoke/MethodHandle;)Ljava/lang/String;; Stack: 8
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[11]) { cmethods[11] = env->GetStaticMethodID((cclasses[12]), ((char *)(string_pool + 1322LL)), ((char *)(string_pool + 1337LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack4.l = env->CallStaticObjectMethod((cclasses[12]), (cmethods[11]), cstack4.i, cstack5.j, cstack7.l); refs.insert(cstack4.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 5
        // CHECKCAST java/lang/String; Stack: 5
        if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { cclasses_mtx[13].lock(); if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[16]))) { cclasses[13] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[13].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack4.l != nullptr && !env->IsInstanceOf(cstack4.l, (cclasses[13]))) { utils::throw_re(env, ((char *)(string_pool + 94LL)), (std::string(((char *)(string_pool + 123LL))) + std::string(((char *)(string_pool + 1391LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } 
        // New stack: 5
        // LDC -7588789410987816482; Stack: 5
        cstack5.j = -7588789410987816482LL;
        // New stack: 7
        // LLOAD 1; Stack: 7
        cstack7.j = clocal1.j;
        // New stack: 9
        // LABEL L9; Stack: 9
        L9: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; }
        // New stack: 9
        // ICONST_1; Stack: 9
        cstack9.i = 1;
        // New stack: 10
        // ANEWARRAY java/lang/Object; Stack: 10
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; } } if (cstack9.i < 0) utils::throw_re(env, ((char *)(string_pool + 214LL)), ((char *)(string_pool + 251LL)), -1); else { cstack9.l = env->NewObjectArray(cstack9.i, (cclasses[4]), nullptr); refs.insert(cstack9.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; }
        // New stack: 10
        // DUP; Stack: 10
        cstack10 = cstack9;
        // New stack: 11
        // LDC Ldev/sakura/client/Mahiro_i3;; Stack: 11
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; } } cstack11.l = (cclasses[5]);
        // New stack: 12
        // SWAP; Stack: 12
        std::swap(cstack11, cstack10);
        // New stack: 12
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.8175695498474079.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 12
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; } } cstack12.l = lookup;
        // New stack: 13
        // LDC Ldev/sakura/client/Mahiro_zr;; Stack: 13
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; } } cstack13.l = (cclasses[6]);
        // New stack: 14
        // LDC a; Stack: 14
        cstack14.l = (cstrings[6]);
        // New stack: 15
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;; Stack: 15
        cstack15.l = (cstrings[7]);
        // New stack: 16
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.8175695498474079.a()Ljava/lang/ClassLoader;; Stack: 16
        cstack16.l = classloader;
        // New stack: 17
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 17
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; } } if (!cmethods[1]) { cmethods[1] = env->GetStaticMethodID((cclasses[7]), ((char *)(string_pool + 276LL)), ((char *)(string_pool + 303LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; }  } cstack15.l = env->CallStaticObjectMethod((cclasses[7]), (cmethods[1]), cstack15.l, cstack16.l); refs.insert(cstack15.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; }
        // New stack: 16
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 16
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; } } if (!cmethods[2]) { cmethods[2] = env->GetMethodID((cclasses[8]), ((char *)(string_pool + 376LL)), ((char *)(string_pool + 387LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; }  } if (cstack12.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 485LL)), -1); else { cstack12.l = env->CallObjectMethod(cstack12.l, (cmethods[2]), cstack13.l, cstack14.l, cstack15.l); refs.insert(cstack12.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; }
        // New stack: 13
        // SWAP; Stack: 13
        std::swap(cstack12, cstack11);
        // New stack: 13
        // LDC L; Stack: 13
        cstack13.l = (cstrings[22]);
        // New stack: 14
        // SWAP; Stack: 14
        std::swap(cstack13, cstack12);
        // New stack: 14
        // LDC (Ljava/lang/Object;Ljava/lang/Object;JJ)[B; Stack: 14
        cstack14.l = (cstrings[33]);
        // New stack: 15
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.8175695498474079.a()Ljava/lang/ClassLoader;; Stack: 15
        cstack15.l = classloader;
        // New stack: 16
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 16
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; } } if (!cmethods[1]) { cmethods[1] = env->GetStaticMethodID((cclasses[7]), ((char *)(string_pool + 276LL)), ((char *)(string_pool + 303LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; }  } cstack14.l = env->CallStaticObjectMethod((cclasses[7]), (cmethods[1]), cstack14.l, cstack15.l); refs.insert(cstack14.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; }
        // New stack: 15
        // SWAP; Stack: 15
        std::swap(cstack14, cstack13);
        // New stack: 15
        // LDC 0; Stack: 15
        cstack15.i = 0;
        // New stack: 16
        // ANEWARRAY java/lang/Object; Stack: 16
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; } } if (cstack15.i < 0) utils::throw_re(env, ((char *)(string_pool + 214LL)), ((char *)(string_pool + 251LL)), -1); else { cstack15.l = env->NewObjectArray(cstack15.i, (cclasses[4]), nullptr); refs.insert(cstack15.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; }
        // New stack: 16
        // CHECKCAST java/lang/Object; Stack: 16
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; } } if (cstack15.l != nullptr && !env->IsInstanceOf(cstack15.l, (cclasses[4]))) { utils::throw_re(env, ((char *)(string_pool + 94LL)), (std::string(((char *)(string_pool + 123LL))) + std::string(((char *)(string_pool + 510LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; } } 
        // New stack: 16
        // SWAP; Stack: 16
        std::swap(cstack15, cstack14);
        // New stack: 16
        // INVOKESTATIC native/magic/1/linkcallsite/obfuscator0.8175695498474079.a(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/invoke/MemberName;; Stack: 16
        cstack10.l = utils::link_call_site(env, cstack10.l, cstack11.l, cstack12.l, cstack13.l, cstack14.l, cstack15.l); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; }
        // New stack: 11
        // POP; Stack: 11
        ;
        // New stack: 10
        // ICONST_0; Stack: 10
        cstack10.i = 0;
        // New stack: 11
        // AALOAD; Stack: 11
        if (cstack9.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 83LL)), -1); else { cstack9.l = env->GetObjectArrayElement((jobjectArray) cstack9.l, cstack10.i); refs.insert(cstack9.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; }
        // New stack: 10
        // DUP; Stack: 10
        cstack10 = cstack9;
        // New stack: 11
        // INSTANCEOF java/lang/invoke/CallSite; Stack: 11
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; } } cstack10.i = cstack10.l == nullptr ? false : env->IsInstanceOf(cstack10.l, (cclasses[9]));
        // New stack: 11
        // IFEQ L51; Stack: 11
        if (cstack10.i == 0) goto L51;
        // New stack: 10
        // INVOKEINTERFACE java/lang/invoke/CallSite.getTarget()Ljava/lang/invoke/MethodHandle;; Stack: 10
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; } } if (!cmethods[3]) { cmethods[3] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 527LL)), ((char *)(string_pool + 537LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; }  } if (cstack9.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 571LL)), -1); else { cstack9.l = env->CallObjectMethod(cstack9.l, (cmethods[3])); refs.insert(cstack9.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; }
        // New stack: 10
        // LABEL L51; Stack: 10
        L51: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; }
        // New stack: 10
        // FRAME FULL L: [[Ljava/lang/Object;, 4, java/lang/Class] S: [java/lang/Class, org.objectweb.asm.tree.LabelNode@4a60ee36, org.objectweb.asm.tree.LabelNode@4a60ee36, java/util/Base64$Decoder, java/lang/String, 4, 4, java/lang/Object]; Stack: 10
        refs.erase(cstack0.l); refs.erase(cstack1.l); refs.erase(cstack2.l); refs.erase(cstack3.l); refs.erase(cstack4.l); refs.erase(cstack9.l); 
        refs.erase(clocal0.l); refs.erase(clocal3.l); 
        utils::clear_refs(env, refs);
        // New stack: 10
        // CHECKCAST java/lang/invoke/MethodHandle; Stack: 10
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; } } if (cstack9.l != nullptr && !env->IsInstanceOf(cstack9.l, (cclasses[10]))) { utils::throw_re(env, ((char *)(string_pool + 94LL)), (std::string(((char *)(string_pool + 123LL))) + std::string(((char *)(string_pool + 598LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; } } 
        // New stack: 10
        // GOTO L52; Stack: 10
        goto L52;
        // New stack: 10
        // LABEL L10; Stack: 10
        L10: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 10
        // FRAME SAME1 L: null S: [java/lang/Throwable]; Stack: 10
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal3.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // INSTANCEOF java/lang/BootstrapMethodError; Stack: 2
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack1.i = cstack1.l == nullptr ? false : env->IsInstanceOf(cstack1.l, (cclasses[11]));
        // New stack: 2
        // IFNE L53; Stack: 2
        if (cstack1.i != 0) goto L53;
        // New stack: 1
        // NEW java/lang/BootstrapMethodError; Stack: 1
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (jobject obj = env->AllocObject((cclasses[11]))) { cstack1.l = obj; refs.insert(obj); } 
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
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[11]), ((char *)(string_pool + 628LL)), ((char *)(string_pool + 635LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 660LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[11]), (cmethods[4]), cstack2.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 1
        // LABEL L53; Stack: 1
        L53: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/Object]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal3.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 683LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 0
        // LABEL L52; Stack: 0
        L52: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 0
        // FRAME FULL L: [[Ljava/lang/Object;, 4, java/lang/Class] S: [java/lang/Class, org.objectweb.asm.tree.LabelNode@4a60ee36, org.objectweb.asm.tree.LabelNode@4a60ee36, java/util/Base64$Decoder, java/lang/String, 4, 4, java/lang/invoke/MethodHandle]; Stack: 0
        refs.erase(cstack0.l); refs.erase(cstack1.l); refs.erase(cstack2.l); refs.erase(cstack3.l); refs.erase(cstack4.l); refs.erase(cstack9.l); 
        refs.erase(clocal0.l); refs.erase(clocal3.l); 
        utils::clear_refs(env, refs);
        // New stack: 10
        // INVOKESTATIC native/magic/1/invoke/obfuscator0.8175695498474079.a(Ljava/lang/Object;Ljava/lang/Object;JJLjava/lang/invoke/MethodHandle;)[B; Stack: 10
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[12]) { cmethods[12] = env->GetStaticMethodID((cclasses[12]), ((char *)(string_pool + 1496LL)), ((char *)(string_pool + 1511LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack3.l = env->CallStaticObjectMethod((cclasses[12]), (cmethods[12]), cstack3.l, cstack4.l, cstack5.j, cstack7.j, cstack9.l); refs.insert(cstack3.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 4
        // CHECKCAST [B; Stack: 4
        if (!cclasses[18] || env->IsSameObject(cclasses[18], NULL)) { cclasses_mtx[18].lock(); if (!cclasses[18] || env->IsSameObject(cclasses[18], NULL)) { if (jclass clazz = env->FindClass(((char *)(string_pool + 8413LL)))) { cclasses[18] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[18].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack3.l != nullptr && !env->IsInstanceOf(cstack3.l, (cclasses[18]))) { utils::throw_re(env, ((char *)(string_pool + 94LL)), (std::string(((char *)(string_pool + 123LL))) + std::string(((char *)(string_pool + 8413LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } 
        // New stack: 4
        // INVOKESPECIAL java/lang/String.<init>([B)V; Stack: 4
        if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { cclasses_mtx[13].lock(); if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[16]))) { cclasses[13] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[13].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[13]) { cmethods[13] = env->GetMethodID((cclasses[13]), ((char *)(string_pool + 628LL)), ((char *)(string_pool + 2695LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack2.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 660LL)), -1); else env->CallNonvirtualVoidMethod(cstack2.l, (cclasses[13]), (cmethods[13]), cstack3.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 2
        // ICONST_1; Stack: 2
        cstack2.i = 1;
        // New stack: 3
        // ANEWARRAY java/lang/Class; Stack: 3
        if (!cclasses[20] || env->IsSameObject(cclasses[20], NULL)) { cclasses_mtx[20].lock(); if (!cclasses[20] || env->IsSameObject(cclasses[20], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[36]))) { cclasses[20] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[20].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack2.i < 0) utils::throw_re(env, ((char *)(string_pool + 214LL)), ((char *)(string_pool + 251LL)), -1); else { cstack2.l = env->NewObjectArray(cstack2.i, (cclasses[20]), nullptr); refs.insert(cstack2.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 3
        // DUP; Stack: 3
        cstack3 = cstack2;
        // New stack: 4
        // ICONST_0; Stack: 4
        cstack4.i = 0;
        // New stack: 5
        // LDC -7576774903887330443; Stack: 5
        cstack5.j = -7576774903887330443LL;
        // New stack: 7
        // LLOAD 1; Stack: 7
        cstack7.j = clocal1.j;
        // New stack: 9
        // LABEL L7; Stack: 9
        L7: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; }
        // New stack: 9
        // ICONST_1; Stack: 9
        cstack9.i = 1;
        // New stack: 10
        // ANEWARRAY java/lang/Object; Stack: 10
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; } } if (cstack9.i < 0) utils::throw_re(env, ((char *)(string_pool + 214LL)), ((char *)(string_pool + 251LL)), -1); else { cstack9.l = env->NewObjectArray(cstack9.i, (cclasses[4]), nullptr); refs.insert(cstack9.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; }
        // New stack: 10
        // DUP; Stack: 10
        cstack10 = cstack9;
        // New stack: 11
        // LDC Ldev/sakura/client/Mahiro_i3;; Stack: 11
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; } } cstack11.l = (cclasses[5]);
        // New stack: 12
        // SWAP; Stack: 12
        std::swap(cstack11, cstack10);
        // New stack: 12
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.8175695498474079.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 12
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; } } cstack12.l = lookup;
        // New stack: 13
        // LDC Ldev/sakura/client/Mahiro_zr;; Stack: 13
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; } } cstack13.l = (cclasses[6]);
        // New stack: 14
        // LDC a; Stack: 14
        cstack14.l = (cstrings[6]);
        // New stack: 15
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;; Stack: 15
        cstack15.l = (cstrings[7]);
        // New stack: 16
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.8175695498474079.a()Ljava/lang/ClassLoader;; Stack: 16
        cstack16.l = classloader;
        // New stack: 17
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 17
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; } } if (!cmethods[1]) { cmethods[1] = env->GetStaticMethodID((cclasses[7]), ((char *)(string_pool + 276LL)), ((char *)(string_pool + 303LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; }  } cstack15.l = env->CallStaticObjectMethod((cclasses[7]), (cmethods[1]), cstack15.l, cstack16.l); refs.insert(cstack15.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; }
        // New stack: 16
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 16
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; } } if (!cmethods[2]) { cmethods[2] = env->GetMethodID((cclasses[8]), ((char *)(string_pool + 376LL)), ((char *)(string_pool + 387LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; }  } if (cstack12.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 485LL)), -1); else { cstack12.l = env->CallObjectMethod(cstack12.l, (cmethods[2]), cstack13.l, cstack14.l, cstack15.l); refs.insert(cstack12.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; }
        // New stack: 13
        // SWAP; Stack: 13
        std::swap(cstack12, cstack11);
        // New stack: 13
        // LDC \u00c3; Stack: 13
        cstack13.l = (cstrings[19]);
        // New stack: 14
        // SWAP; Stack: 14
        std::swap(cstack13, cstack12);
        // New stack: 14
        // LDC (JJ)Ljava/lang/Class;; Stack: 14
        cstack14.l = (cstrings[37]);
        // New stack: 15
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.8175695498474079.a()Ljava/lang/ClassLoader;; Stack: 15
        cstack15.l = classloader;
        // New stack: 16
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 16
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; } } if (!cmethods[1]) { cmethods[1] = env->GetStaticMethodID((cclasses[7]), ((char *)(string_pool + 276LL)), ((char *)(string_pool + 303LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; }  } cstack14.l = env->CallStaticObjectMethod((cclasses[7]), (cmethods[1]), cstack14.l, cstack15.l); refs.insert(cstack14.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; }
        // New stack: 15
        // SWAP; Stack: 15
        std::swap(cstack14, cstack13);
        // New stack: 15
        // LDC 0; Stack: 15
        cstack15.i = 0;
        // New stack: 16
        // ANEWARRAY java/lang/Object; Stack: 16
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; } } if (cstack15.i < 0) utils::throw_re(env, ((char *)(string_pool + 214LL)), ((char *)(string_pool + 251LL)), -1); else { cstack15.l = env->NewObjectArray(cstack15.i, (cclasses[4]), nullptr); refs.insert(cstack15.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; }
        // New stack: 16
        // CHECKCAST java/lang/Object; Stack: 16
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; } } if (cstack15.l != nullptr && !env->IsInstanceOf(cstack15.l, (cclasses[4]))) { utils::throw_re(env, ((char *)(string_pool + 94LL)), (std::string(((char *)(string_pool + 123LL))) + std::string(((char *)(string_pool + 510LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; } } 
        // New stack: 16
        // SWAP; Stack: 16
        std::swap(cstack15, cstack14);
        // New stack: 16
        // INVOKESTATIC native/magic/1/linkcallsite/obfuscator0.8175695498474079.a(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/invoke/MemberName;; Stack: 16
        cstack10.l = utils::link_call_site(env, cstack10.l, cstack11.l, cstack12.l, cstack13.l, cstack14.l, cstack15.l); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; }
        // New stack: 11
        // POP; Stack: 11
        ;
        // New stack: 10
        // ICONST_0; Stack: 10
        cstack10.i = 0;
        // New stack: 11
        // AALOAD; Stack: 11
        if (cstack9.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 83LL)), -1); else { cstack9.l = env->GetObjectArrayElement((jobjectArray) cstack9.l, cstack10.i); refs.insert(cstack9.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; }
        // New stack: 10
        // DUP; Stack: 10
        cstack10 = cstack9;
        // New stack: 11
        // INSTANCEOF java/lang/invoke/CallSite; Stack: 11
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; } } cstack10.i = cstack10.l == nullptr ? false : env->IsInstanceOf(cstack10.l, (cclasses[9]));
        // New stack: 11
        // IFEQ L54; Stack: 11
        if (cstack10.i == 0) goto L54;
        // New stack: 10
        // INVOKEINTERFACE java/lang/invoke/CallSite.getTarget()Ljava/lang/invoke/MethodHandle;; Stack: 10
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; } } if (!cmethods[3]) { cmethods[3] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 527LL)), ((char *)(string_pool + 537LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; }  } if (cstack9.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 571LL)), -1); else { cstack9.l = env->CallObjectMethod(cstack9.l, (cmethods[3])); refs.insert(cstack9.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; }
        // New stack: 10
        // LABEL L54; Stack: 10
        L54: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; }
        // New stack: 10
        // FRAME FULL L: [[Ljava/lang/Object;, 4, java/lang/Class] S: [java/lang/Class, java/lang/String, [Ljava/lang/Class;, [Ljava/lang/Class;, 1, 4, 4, java/lang/Object]; Stack: 10
        refs.erase(cstack0.l); refs.erase(cstack1.l); refs.erase(cstack2.l); refs.erase(cstack3.l); refs.erase(cstack9.l); 
        refs.erase(clocal0.l); refs.erase(clocal3.l); 
        utils::clear_refs(env, refs);
        // New stack: 10
        // CHECKCAST java/lang/invoke/MethodHandle; Stack: 10
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; } } if (cstack9.l != nullptr && !env->IsInstanceOf(cstack9.l, (cclasses[10]))) { utils::throw_re(env, ((char *)(string_pool + 94LL)), (std::string(((char *)(string_pool + 123LL))) + std::string(((char *)(string_pool + 598LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; } } 
        // New stack: 10
        // GOTO L55; Stack: 10
        goto L55;
        // New stack: 10
        // LABEL L8; Stack: 10
        L8: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 10
        // FRAME SAME1 L: null S: [java/lang/Throwable]; Stack: 10
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal3.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // INSTANCEOF java/lang/BootstrapMethodError; Stack: 2
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack1.i = cstack1.l == nullptr ? false : env->IsInstanceOf(cstack1.l, (cclasses[11]));
        // New stack: 2
        // IFNE L56; Stack: 2
        if (cstack1.i != 0) goto L56;
        // New stack: 1
        // NEW java/lang/BootstrapMethodError; Stack: 1
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (jobject obj = env->AllocObject((cclasses[11]))) { cstack1.l = obj; refs.insert(obj); } 
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
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[11]), ((char *)(string_pool + 628LL)), ((char *)(string_pool + 635LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 660LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[11]), (cmethods[4]), cstack2.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 1
        // LABEL L56; Stack: 1
        L56: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/Object]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal3.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 683LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 0
        // LABEL L55; Stack: 0
        L55: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 0
        // FRAME FULL L: [[Ljava/lang/Object;, 4, java/lang/Class] S: [java/lang/Class, java/lang/String, [Ljava/lang/Class;, [Ljava/lang/Class;, 1, 4, 4, java/lang/invoke/MethodHandle]; Stack: 0
        refs.erase(cstack0.l); refs.erase(cstack1.l); refs.erase(cstack2.l); refs.erase(cstack3.l); refs.erase(cstack9.l); 
        refs.erase(clocal0.l); refs.erase(clocal3.l); 
        utils::clear_refs(env, refs);
        // New stack: 10
        // INVOKESTATIC native/magic/1/invoke/obfuscator0.8175695498474079.a(JJLjava/lang/invoke/MethodHandle;)Ljava/lang/Class;; Stack: 10
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[5]) { cmethods[5] = env->GetStaticMethodID((cclasses[12]), ((char *)(string_pool + 694LL)), ((char *)(string_pool + 709LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack5.l = env->CallStaticObjectMethod((cclasses[12]), (cmethods[5]), cstack5.j, cstack7.j, cstack9.l); refs.insert(cstack5.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 6
        // CHECKCAST java/lang/Class; Stack: 6
        if (!cclasses[20] || env->IsSameObject(cclasses[20], NULL)) { cclasses_mtx[20].lock(); if (!cclasses[20] || env->IsSameObject(cclasses[20], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[36]))) { cclasses[20] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[20].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack5.l != nullptr && !env->IsInstanceOf(cstack5.l, (cclasses[20]))) { utils::throw_re(env, ((char *)(string_pool + 94LL)), (std::string(((char *)(string_pool + 123LL))) + std::string(((char *)(string_pool + 2211LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } 
        // New stack: 6
        // AASTORE; Stack: 6
        if (cstack3.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 1657LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack3.l, cstack4.i, cstack5.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 3
        // SWAP; Stack: 3
        std::swap(cstack2, cstack1);
        // New stack: 3
        // DUP_X2; Stack: 3
        cstack3 = cstack2; cstack2 = cstack1; cstack1 = cstack0; cstack0 = cstack3;
        // New stack: 4
        // POP; Stack: 4
        ;
        // New stack: 3
        // DUP2_X1; Stack: 3
        cstack3 = cstack1; cstack4 = cstack2; cstack2 = cstack0; cstack1 = cstack4; cstack0 = cstack3;
        // New stack: 5
        // INVOKESTATIC dev/sakura/client/Mahiro_hc.b(Ljava/lang/String;Ljava/lang/Class;[Ljava/lang/Class;)Ljava/lang/String;; Stack: 5
        if (!cclasses[19] || env->IsSameObject(cclasses[19], NULL)) { cclasses_mtx[19].lock(); if (!cclasses[19] || env->IsSameObject(cclasses[19], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[34]))) { cclasses[19] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[19].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[15]) { cmethods[15] = env->GetStaticMethodID((cclasses[19]), ((char *)(string_pool + 2941LL)), ((char *)(string_pool + 16617LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack2.l = env->CallStaticObjectMethod((cclasses[19]), (cmethods[15]), cstack2.l, cstack3.l, cstack4.l); refs.insert(cstack2.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 3
        // SWAP; Stack: 3
        std::swap(cstack2, cstack1);
        // New stack: 3
        // LDC -7569874231949087402; Stack: 3
        cstack3.j = -7569874231949087402LL;
        // New stack: 5
        // LLOAD 1; Stack: 5
        cstack5.j = clocal1.j;
        // New stack: 7
        // LABEL L5; Stack: 7
        L5: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; }
        // New stack: 7
        // ICONST_1; Stack: 7
        cstack7.i = 1;
        // New stack: 8
        // ANEWARRAY java/lang/Object; Stack: 8
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; } } if (cstack7.i < 0) utils::throw_re(env, ((char *)(string_pool + 214LL)), ((char *)(string_pool + 251LL)), -1); else { cstack7.l = env->NewObjectArray(cstack7.i, (cclasses[4]), nullptr); refs.insert(cstack7.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; }
        // New stack: 8
        // DUP; Stack: 8
        cstack8 = cstack7;
        // New stack: 9
        // LDC Ldev/sakura/client/Mahiro_i3;; Stack: 9
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; } } cstack9.l = (cclasses[5]);
        // New stack: 10
        // SWAP; Stack: 10
        std::swap(cstack9, cstack8);
        // New stack: 10
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.8175695498474079.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 10
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; } } cstack10.l = lookup;
        // New stack: 11
        // LDC Ldev/sakura/client/Mahiro_zr;; Stack: 11
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; } } cstack11.l = (cclasses[6]);
        // New stack: 12
        // LDC a; Stack: 12
        cstack12.l = (cstrings[6]);
        // New stack: 13
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;; Stack: 13
        cstack13.l = (cstrings[7]);
        // New stack: 14
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.8175695498474079.a()Ljava/lang/ClassLoader;; Stack: 14
        cstack14.l = classloader;
        // New stack: 15
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 15
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; } } if (!cmethods[1]) { cmethods[1] = env->GetStaticMethodID((cclasses[7]), ((char *)(string_pool + 276LL)), ((char *)(string_pool + 303LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; }  } cstack13.l = env->CallStaticObjectMethod((cclasses[7]), (cmethods[1]), cstack13.l, cstack14.l); refs.insert(cstack13.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; }
        // New stack: 14
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 14
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; } } if (!cmethods[2]) { cmethods[2] = env->GetMethodID((cclasses[8]), ((char *)(string_pool + 376LL)), ((char *)(string_pool + 387LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; }  } if (cstack10.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 485LL)), -1); else { cstack10.l = env->CallObjectMethod(cstack10.l, (cmethods[2]), cstack11.l, cstack12.l, cstack13.l); refs.insert(cstack10.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; }
        // New stack: 11
        // SWAP; Stack: 11
        std::swap(cstack10, cstack9);
        // New stack: 11
        // LDC L; Stack: 11
        cstack11.l = (cstrings[22]);
        // New stack: 12
        // SWAP; Stack: 12
        std::swap(cstack11, cstack10);
        // New stack: 12
        // LDC (Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;JJ)Ljava/lang/reflect/Method;; Stack: 12
        cstack12.l = (cstrings[38]);
        // New stack: 13
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.8175695498474079.a()Ljava/lang/ClassLoader;; Stack: 13
        cstack13.l = classloader;
        // New stack: 14
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 14
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; } } if (!cmethods[1]) { cmethods[1] = env->GetStaticMethodID((cclasses[7]), ((char *)(string_pool + 276LL)), ((char *)(string_pool + 303LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; }  } cstack12.l = env->CallStaticObjectMethod((cclasses[7]), (cmethods[1]), cstack12.l, cstack13.l); refs.insert(cstack12.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; }
        // New stack: 13
        // SWAP; Stack: 13
        std::swap(cstack12, cstack11);
        // New stack: 13
        // LDC 0; Stack: 13
        cstack13.i = 0;
        // New stack: 14
        // ANEWARRAY java/lang/Object; Stack: 14
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; } } if (cstack13.i < 0) utils::throw_re(env, ((char *)(string_pool + 214LL)), ((char *)(string_pool + 251LL)), -1); else { cstack13.l = env->NewObjectArray(cstack13.i, (cclasses[4]), nullptr); refs.insert(cstack13.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; }
        // New stack: 14
        // CHECKCAST java/lang/Object; Stack: 14
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; } } if (cstack13.l != nullptr && !env->IsInstanceOf(cstack13.l, (cclasses[4]))) { utils::throw_re(env, ((char *)(string_pool + 94LL)), (std::string(((char *)(string_pool + 123LL))) + std::string(((char *)(string_pool + 510LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; } } 
        // New stack: 14
        // SWAP; Stack: 14
        std::swap(cstack13, cstack12);
        // New stack: 14
        // INVOKESTATIC native/magic/1/linkcallsite/obfuscator0.8175695498474079.a(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/invoke/MemberName;; Stack: 14
        cstack8.l = utils::link_call_site(env, cstack8.l, cstack9.l, cstack10.l, cstack11.l, cstack12.l, cstack13.l); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; }
        // New stack: 9
        // POP; Stack: 9
        ;
        // New stack: 8
        // ICONST_0; Stack: 8
        cstack8.i = 0;
        // New stack: 9
        // AALOAD; Stack: 9
        if (cstack7.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 83LL)), -1); else { cstack7.l = env->GetObjectArrayElement((jobjectArray) cstack7.l, cstack8.i); refs.insert(cstack7.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; }
        // New stack: 8
        // DUP; Stack: 8
        cstack8 = cstack7;
        // New stack: 9
        // INSTANCEOF java/lang/invoke/CallSite; Stack: 9
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; } } cstack8.i = cstack8.l == nullptr ? false : env->IsInstanceOf(cstack8.l, (cclasses[9]));
        // New stack: 9
        // IFEQ L57; Stack: 9
        if (cstack8.i == 0) goto L57;
        // New stack: 8
        // INVOKEINTERFACE java/lang/invoke/CallSite.getTarget()Ljava/lang/invoke/MethodHandle;; Stack: 8
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; } } if (!cmethods[3]) { cmethods[3] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 527LL)), ((char *)(string_pool + 537LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; }  } if (cstack7.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 571LL)), -1); else { cstack7.l = env->CallObjectMethod(cstack7.l, (cmethods[3])); refs.insert(cstack7.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; }
        // New stack: 8
        // LABEL L57; Stack: 8
        L57: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; }
        // New stack: 8
        // FRAME FULL L: [[Ljava/lang/Object;, 4, java/lang/Class] S: [java/lang/Class, java/lang/String, [Ljava/lang/Class;, 4, 4, java/lang/Object]; Stack: 8
        refs.erase(cstack0.l); refs.erase(cstack1.l); refs.erase(cstack2.l); refs.erase(cstack7.l); 
        refs.erase(clocal0.l); refs.erase(clocal3.l); 
        utils::clear_refs(env, refs);
        // New stack: 8
        // CHECKCAST java/lang/invoke/MethodHandle; Stack: 8
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; } } if (cstack7.l != nullptr && !env->IsInstanceOf(cstack7.l, (cclasses[10]))) { utils::throw_re(env, ((char *)(string_pool + 94LL)), (std::string(((char *)(string_pool + 123LL))) + std::string(((char *)(string_pool + 598LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; } } 
        // New stack: 8
        // GOTO L58; Stack: 8
        goto L58;
        // New stack: 8
        // LABEL L6; Stack: 8
        L6: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 8
        // FRAME SAME1 L: null S: [java/lang/Throwable]; Stack: 8
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal3.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // INSTANCEOF java/lang/BootstrapMethodError; Stack: 2
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack1.i = cstack1.l == nullptr ? false : env->IsInstanceOf(cstack1.l, (cclasses[11]));
        // New stack: 2
        // IFNE L59; Stack: 2
        if (cstack1.i != 0) goto L59;
        // New stack: 1
        // NEW java/lang/BootstrapMethodError; Stack: 1
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (jobject obj = env->AllocObject((cclasses[11]))) { cstack1.l = obj; refs.insert(obj); } 
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
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[11]), ((char *)(string_pool + 628LL)), ((char *)(string_pool + 635LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 660LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[11]), (cmethods[4]), cstack2.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 1
        // LABEL L59; Stack: 1
        L59: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/Object]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal3.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 683LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 0
        // LABEL L58; Stack: 0
        L58: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 0
        // FRAME FULL L: [[Ljava/lang/Object;, 4, java/lang/Class] S: [java/lang/Class, java/lang/String, [Ljava/lang/Class;, 4, 4, java/lang/invoke/MethodHandle]; Stack: 0
        refs.erase(cstack0.l); refs.erase(cstack1.l); refs.erase(cstack2.l); refs.erase(cstack7.l); 
        refs.erase(clocal0.l); refs.erase(clocal3.l); 
        utils::clear_refs(env, refs);
        // New stack: 8
        // INVOKESTATIC native/magic/1/invoke/obfuscator0.8175695498474079.a(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;JJLjava/lang/invoke/MethodHandle;)Ljava/lang/reflect/Method;; Stack: 8
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[16]) { cmethods[16] = env->GetStaticMethodID((cclasses[12]), ((char *)(string_pool + 1041LL)), ((char *)(string_pool + 1056LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack0.l = env->CallStaticObjectMethod((cclasses[12]), (cmethods[16]), cstack0.l, cstack1.l, cstack2.l, cstack3.j, cstack5.j, cstack7.l); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 1
        // CHECKCAST java/lang/reflect/Method; Stack: 1
        if (!cclasses[21] || env->IsSameObject(cclasses[21], NULL)) { cclasses_mtx[21].lock(); if (!cclasses[21] || env->IsSameObject(cclasses[21], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[39]))) { cclasses[21] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[21].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack0.l != nullptr && !env->IsInstanceOf(cstack0.l, (cclasses[21]))) { utils::throw_re(env, ((char *)(string_pool + 94LL)), (std::string(((char *)(string_pool + 123LL))) + std::string(((char *)(string_pool + 16691LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } 
        // New stack: 1
        // ASTORE 4; Stack: 1
        clocal4.l = cstack0.l; refs.insert(cstack0.l);
        // New stack: 0
        // ALOAD 4; Stack: 0
        cstack0.l = clocal4.l; refs.insert(cstack0.l);
        // New stack: 1
        // ACONST_NULL; Stack: 1
        cstack1.l = nullptr;
        // New stack: 2
        // ICONST_1; Stack: 2
        cstack2.i = 1;
        // New stack: 3
        // ANEWARRAY java/lang/Object; Stack: 3
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack2.i < 0) utils::throw_re(env, ((char *)(string_pool + 214LL)), ((char *)(string_pool + 251LL)), -1); else { cstack2.l = env->NewObjectArray(cstack2.i, (cclasses[4]), nullptr); refs.insert(cstack2.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 3
        // DUP; Stack: 3
        cstack3 = cstack2;
        // New stack: 4
        // ICONST_0; Stack: 4
        cstack4.i = 0;
        // New stack: 5
        // ICONST_0; Stack: 5
        cstack5.i = 0;
        // New stack: 6
        // LDC -7594712381357191962; Stack: 6
        cstack6.j = -7594712381357191962LL;
        // New stack: 8
        // LLOAD 1; Stack: 8
        cstack8.j = clocal1.j;
        // New stack: 10
        // LABEL L3; Stack: 10
        L3: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_11; }
        // New stack: 10
        // ICONST_1; Stack: 10
        cstack10.i = 1;
        // New stack: 11
        // ANEWARRAY java/lang/Object; Stack: 11
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_11; } } if (cstack10.i < 0) utils::throw_re(env, ((char *)(string_pool + 214LL)), ((char *)(string_pool + 251LL)), -1); else { cstack10.l = env->NewObjectArray(cstack10.i, (cclasses[4]), nullptr); refs.insert(cstack10.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_11; }
        // New stack: 11
        // DUP; Stack: 11
        cstack11 = cstack10;
        // New stack: 12
        // LDC Ldev/sakura/client/Mahiro_i3;; Stack: 12
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_11; } } cstack12.l = (cclasses[5]);
        // New stack: 13
        // SWAP; Stack: 13
        std::swap(cstack12, cstack11);
        // New stack: 13
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.8175695498474079.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 13
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_11; } } cstack13.l = lookup;
        // New stack: 14
        // LDC Ldev/sakura/client/Mahiro_zr;; Stack: 14
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_11; } } cstack14.l = (cclasses[6]);
        // New stack: 15
        // LDC a; Stack: 15
        cstack15.l = (cstrings[6]);
        // New stack: 16
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;; Stack: 16
        cstack16.l = (cstrings[7]);
        // New stack: 17
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.8175695498474079.a()Ljava/lang/ClassLoader;; Stack: 17
        cstack17.l = classloader;
        // New stack: 18
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 18
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_11; } } if (!cmethods[1]) { cmethods[1] = env->GetStaticMethodID((cclasses[7]), ((char *)(string_pool + 276LL)), ((char *)(string_pool + 303LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_11; }  } cstack16.l = env->CallStaticObjectMethod((cclasses[7]), (cmethods[1]), cstack16.l, cstack17.l); refs.insert(cstack16.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_11; }
        // New stack: 17
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 17
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_11; } } if (!cmethods[2]) { cmethods[2] = env->GetMethodID((cclasses[8]), ((char *)(string_pool + 376LL)), ((char *)(string_pool + 387LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_11; }  } if (cstack13.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 485LL)), -1); else { cstack13.l = env->CallObjectMethod(cstack13.l, (cmethods[2]), cstack14.l, cstack15.l, cstack16.l); refs.insert(cstack13.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_11; }
        // New stack: 14
        // SWAP; Stack: 14
        std::swap(cstack13, cstack12);
        // New stack: 14
        // LDC \u00d4; Stack: 14
        cstack14.l = (cstrings[10]);
        // New stack: 15
        // SWAP; Stack: 15
        std::swap(cstack14, cstack13);
        // New stack: 15
        // LDC (IJJ)Ljava/lang/Integer;; Stack: 15
        cstack15.l = (cstrings[40]);
        // New stack: 16
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.8175695498474079.a()Ljava/lang/ClassLoader;; Stack: 16
        cstack16.l = classloader;
        // New stack: 17
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 17
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_11; } } if (!cmethods[1]) { cmethods[1] = env->GetStaticMethodID((cclasses[7]), ((char *)(string_pool + 276LL)), ((char *)(string_pool + 303LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_11; }  } cstack15.l = env->CallStaticObjectMethod((cclasses[7]), (cmethods[1]), cstack15.l, cstack16.l); refs.insert(cstack15.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_11; }
        // New stack: 16
        // SWAP; Stack: 16
        std::swap(cstack15, cstack14);
        // New stack: 16
        // LDC 0; Stack: 16
        cstack16.i = 0;
        // New stack: 17
        // ANEWARRAY java/lang/Object; Stack: 17
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_11; } } if (cstack16.i < 0) utils::throw_re(env, ((char *)(string_pool + 214LL)), ((char *)(string_pool + 251LL)), -1); else { cstack16.l = env->NewObjectArray(cstack16.i, (cclasses[4]), nullptr); refs.insert(cstack16.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_11; }
        // New stack: 17
        // CHECKCAST java/lang/Object; Stack: 17
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_11; } } if (cstack16.l != nullptr && !env->IsInstanceOf(cstack16.l, (cclasses[4]))) { utils::throw_re(env, ((char *)(string_pool + 94LL)), (std::string(((char *)(string_pool + 123LL))) + std::string(((char *)(string_pool + 510LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_11; } } 
        // New stack: 17
        // SWAP; Stack: 17
        std::swap(cstack16, cstack15);
        // New stack: 17
        // INVOKESTATIC native/magic/1/linkcallsite/obfuscator0.8175695498474079.a(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/invoke/MemberName;; Stack: 17
        cstack11.l = utils::link_call_site(env, cstack11.l, cstack12.l, cstack13.l, cstack14.l, cstack15.l, cstack16.l); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_11; }
        // New stack: 12
        // POP; Stack: 12
        ;
        // New stack: 11
        // ICONST_0; Stack: 11
        cstack11.i = 0;
        // New stack: 12
        // AALOAD; Stack: 12
        if (cstack10.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 83LL)), -1); else { cstack10.l = env->GetObjectArrayElement((jobjectArray) cstack10.l, cstack11.i); refs.insert(cstack10.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_11; }
        // New stack: 11
        // DUP; Stack: 11
        cstack11 = cstack10;
        // New stack: 12
        // INSTANCEOF java/lang/invoke/CallSite; Stack: 12
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_11; } } cstack11.i = cstack11.l == nullptr ? false : env->IsInstanceOf(cstack11.l, (cclasses[9]));
        // New stack: 12
        // IFEQ L60; Stack: 12
        if (cstack11.i == 0) goto L60;
        // New stack: 11
        // INVOKEINTERFACE java/lang/invoke/CallSite.getTarget()Ljava/lang/invoke/MethodHandle;; Stack: 11
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_11; } } if (!cmethods[3]) { cmethods[3] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 527LL)), ((char *)(string_pool + 537LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_11; }  } if (cstack10.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 571LL)), -1); else { cstack10.l = env->CallObjectMethod(cstack10.l, (cmethods[3])); refs.insert(cstack10.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_11; }
        // New stack: 11
        // LABEL L60; Stack: 11
        L60: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_11; }
        // New stack: 11
        // FRAME FULL L: [[Ljava/lang/Object;, 4, java/lang/Class, java/lang/reflect/Method] S: [java/lang/reflect/Method, 5, [Ljava/lang/Object;, [Ljava/lang/Object;, 1, 1, 4, 4, java/lang/Object]; Stack: 11
        refs.erase(cstack0.l); refs.erase(cstack1.l); refs.erase(cstack2.l); refs.erase(cstack3.l); refs.erase(cstack10.l); 
        refs.erase(clocal0.l); refs.erase(clocal3.l); refs.erase(clocal4.l); 
        utils::clear_refs(env, refs);
        // New stack: 11
        // CHECKCAST java/lang/invoke/MethodHandle; Stack: 11
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_11; } } if (cstack10.l != nullptr && !env->IsInstanceOf(cstack10.l, (cclasses[10]))) { utils::throw_re(env, ((char *)(string_pool + 94LL)), (std::string(((char *)(string_pool + 123LL))) + std::string(((char *)(string_pool + 598LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_11; } } 
        // New stack: 11
        // GOTO L61; Stack: 11
        goto L61;
        // New stack: 11
        // LABEL L4; Stack: 11
        L4: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 11
        // FRAME SAME1 L: null S: [java/lang/Throwable]; Stack: 11
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal3.l); refs.erase(clocal4.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // INSTANCEOF java/lang/BootstrapMethodError; Stack: 2
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack1.i = cstack1.l == nullptr ? false : env->IsInstanceOf(cstack1.l, (cclasses[11]));
        // New stack: 2
        // IFNE L62; Stack: 2
        if (cstack1.i != 0) goto L62;
        // New stack: 1
        // NEW java/lang/BootstrapMethodError; Stack: 1
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (jobject obj = env->AllocObject((cclasses[11]))) { cstack1.l = obj; refs.insert(obj); } 
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
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[11]), ((char *)(string_pool + 628LL)), ((char *)(string_pool + 635LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 660LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[11]), (cmethods[4]), cstack2.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 1
        // LABEL L62; Stack: 1
        L62: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/Object]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal3.l); refs.erase(clocal4.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 683LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 0
        // LABEL L61; Stack: 0
        L61: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 0
        // FRAME FULL L: [[Ljava/lang/Object;, 4, java/lang/Class, java/lang/reflect/Method] S: [java/lang/reflect/Method, 5, [Ljava/lang/Object;, [Ljava/lang/Object;, 1, 1, 4, 4, java/lang/invoke/MethodHandle]; Stack: 0
        refs.erase(cstack0.l); refs.erase(cstack1.l); refs.erase(cstack2.l); refs.erase(cstack3.l); refs.erase(cstack10.l); 
        refs.erase(clocal0.l); refs.erase(clocal3.l); refs.erase(clocal4.l); 
        utils::clear_refs(env, refs);
        // New stack: 11
        // INVOKESTATIC native/magic/1/invoke/obfuscator0.8175695498474079.a(IJJLjava/lang/invoke/MethodHandle;)Ljava/lang/Integer;; Stack: 11
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[17]) { cmethods[17] = env->GetStaticMethodID((cclasses[12]), ((char *)(string_pool + 953LL)), ((char *)(string_pool + 968LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack5.l = env->CallStaticObjectMethod((cclasses[12]), (cmethods[17]), cstack5.i, cstack6.j, cstack8.j, cstack10.l); refs.insert(cstack5.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 6
        // CHECKCAST java/lang/Integer; Stack: 6
        if (!cclasses[22] || env->IsSameObject(cclasses[22], NULL)) { cclasses_mtx[22].lock(); if (!cclasses[22] || env->IsSameObject(cclasses[22], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[41]))) { cclasses[22] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[22].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack5.l != nullptr && !env->IsInstanceOf(cstack5.l, (cclasses[22]))) { utils::throw_re(env, ((char *)(string_pool + 94LL)), (std::string(((char *)(string_pool + 123LL))) + std::string(((char *)(string_pool + 1023LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } 
        // New stack: 6
        // AASTORE; Stack: 6
        if (cstack3.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 1657LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack3.l, cstack4.i, cstack5.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 3
        // LDC -7620857232011610124; Stack: 3
        cstack3.j = -7620857232011610124LL;
        // New stack: 5
        // LLOAD 1; Stack: 5
        cstack5.j = clocal1.j;
        // New stack: 7
        // LABEL L1; Stack: 7
        L1: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_12; }
        // New stack: 7
        // ICONST_1; Stack: 7
        cstack7.i = 1;
        // New stack: 8
        // ANEWARRAY java/lang/Object; Stack: 8
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_12; } } if (cstack7.i < 0) utils::throw_re(env, ((char *)(string_pool + 214LL)), ((char *)(string_pool + 251LL)), -1); else { cstack7.l = env->NewObjectArray(cstack7.i, (cclasses[4]), nullptr); refs.insert(cstack7.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_12; }
        // New stack: 8
        // DUP; Stack: 8
        cstack8 = cstack7;
        // New stack: 9
        // LDC Ldev/sakura/client/Mahiro_i3;; Stack: 9
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_12; } } cstack9.l = (cclasses[5]);
        // New stack: 10
        // SWAP; Stack: 10
        std::swap(cstack9, cstack8);
        // New stack: 10
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.8175695498474079.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 10
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_12; } } cstack10.l = lookup;
        // New stack: 11
        // LDC Ldev/sakura/client/Mahiro_zr;; Stack: 11
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_12; } } cstack11.l = (cclasses[6]);
        // New stack: 12
        // LDC a; Stack: 12
        cstack12.l = (cstrings[6]);
        // New stack: 13
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;; Stack: 13
        cstack13.l = (cstrings[7]);
        // New stack: 14
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.8175695498474079.a()Ljava/lang/ClassLoader;; Stack: 14
        cstack14.l = classloader;
        // New stack: 15
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 15
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_12; } } if (!cmethods[1]) { cmethods[1] = env->GetStaticMethodID((cclasses[7]), ((char *)(string_pool + 276LL)), ((char *)(string_pool + 303LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_12; }  } cstack13.l = env->CallStaticObjectMethod((cclasses[7]), (cmethods[1]), cstack13.l, cstack14.l); refs.insert(cstack13.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_12; }
        // New stack: 14
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 14
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_12; } } if (!cmethods[2]) { cmethods[2] = env->GetMethodID((cclasses[8]), ((char *)(string_pool + 376LL)), ((char *)(string_pool + 387LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_12; }  } if (cstack10.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 485LL)), -1); else { cstack10.l = env->CallObjectMethod(cstack10.l, (cmethods[2]), cstack11.l, cstack12.l, cstack13.l); refs.insert(cstack10.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_12; }
        // New stack: 11
        // SWAP; Stack: 11
        std::swap(cstack10, cstack9);
        // New stack: 11
        // LDC L; Stack: 11
        cstack11.l = (cstrings[22]);
        // New stack: 12
        // SWAP; Stack: 12
        std::swap(cstack11, cstack10);
        // New stack: 12
        // LDC (Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;JJ)Ljava/lang/Object;; Stack: 12
        cstack12.l = (cstrings[42]);
        // New stack: 13
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.8175695498474079.a()Ljava/lang/ClassLoader;; Stack: 13
        cstack13.l = classloader;
        // New stack: 14
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 14
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_12; } } if (!cmethods[1]) { cmethods[1] = env->GetStaticMethodID((cclasses[7]), ((char *)(string_pool + 276LL)), ((char *)(string_pool + 303LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_12; }  } cstack12.l = env->CallStaticObjectMethod((cclasses[7]), (cmethods[1]), cstack12.l, cstack13.l); refs.insert(cstack12.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_12; }
        // New stack: 13
        // SWAP; Stack: 13
        std::swap(cstack12, cstack11);
        // New stack: 13
        // LDC 0; Stack: 13
        cstack13.i = 0;
        // New stack: 14
        // ANEWARRAY java/lang/Object; Stack: 14
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_12; } } if (cstack13.i < 0) utils::throw_re(env, ((char *)(string_pool + 214LL)), ((char *)(string_pool + 251LL)), -1); else { cstack13.l = env->NewObjectArray(cstack13.i, (cclasses[4]), nullptr); refs.insert(cstack13.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_12; }
        // New stack: 14
        // CHECKCAST java/lang/Object; Stack: 14
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_12; } } if (cstack13.l != nullptr && !env->IsInstanceOf(cstack13.l, (cclasses[4]))) { utils::throw_re(env, ((char *)(string_pool + 94LL)), (std::string(((char *)(string_pool + 123LL))) + std::string(((char *)(string_pool + 510LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_12; } } 
        // New stack: 14
        // SWAP; Stack: 14
        std::swap(cstack13, cstack12);
        // New stack: 14
        // INVOKESTATIC native/magic/1/linkcallsite/obfuscator0.8175695498474079.a(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/invoke/MemberName;; Stack: 14
        cstack8.l = utils::link_call_site(env, cstack8.l, cstack9.l, cstack10.l, cstack11.l, cstack12.l, cstack13.l); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_12; }
        // New stack: 9
        // POP; Stack: 9
        ;
        // New stack: 8
        // ICONST_0; Stack: 8
        cstack8.i = 0;
        // New stack: 9
        // AALOAD; Stack: 9
        if (cstack7.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 83LL)), -1); else { cstack7.l = env->GetObjectArrayElement((jobjectArray) cstack7.l, cstack8.i); refs.insert(cstack7.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_12; }
        // New stack: 8
        // DUP; Stack: 8
        cstack8 = cstack7;
        // New stack: 9
        // INSTANCEOF java/lang/invoke/CallSite; Stack: 9
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_12; } } cstack8.i = cstack8.l == nullptr ? false : env->IsInstanceOf(cstack8.l, (cclasses[9]));
        // New stack: 9
        // IFEQ L63; Stack: 9
        if (cstack8.i == 0) goto L63;
        // New stack: 8
        // INVOKEINTERFACE java/lang/invoke/CallSite.getTarget()Ljava/lang/invoke/MethodHandle;; Stack: 8
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_12; } } if (!cmethods[3]) { cmethods[3] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 527LL)), ((char *)(string_pool + 537LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_12; }  } if (cstack7.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 571LL)), -1); else { cstack7.l = env->CallObjectMethod(cstack7.l, (cmethods[3])); refs.insert(cstack7.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_12; }
        // New stack: 8
        // LABEL L63; Stack: 8
        L63: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_12; }
        // New stack: 8
        // FRAME FULL L: [[Ljava/lang/Object;, 4, java/lang/Class, java/lang/reflect/Method] S: [java/lang/reflect/Method, 5, [Ljava/lang/Object;, 4, 4, java/lang/Object]; Stack: 8
        refs.erase(cstack0.l); refs.erase(cstack1.l); refs.erase(cstack2.l); refs.erase(cstack7.l); 
        refs.erase(clocal0.l); refs.erase(clocal3.l); refs.erase(clocal4.l); 
        utils::clear_refs(env, refs);
        // New stack: 8
        // CHECKCAST java/lang/invoke/MethodHandle; Stack: 8
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_12; } } if (cstack7.l != nullptr && !env->IsInstanceOf(cstack7.l, (cclasses[10]))) { utils::throw_re(env, ((char *)(string_pool + 94LL)), (std::string(((char *)(string_pool + 123LL))) + std::string(((char *)(string_pool + 598LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_12; } } 
        // New stack: 8
        // GOTO L64; Stack: 8
        goto L64;
        // New stack: 8
        // LABEL L2; Stack: 8
        L2: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 8
        // FRAME SAME1 L: null S: [java/lang/Throwable]; Stack: 8
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal3.l); refs.erase(clocal4.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // INSTANCEOF java/lang/BootstrapMethodError; Stack: 2
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack1.i = cstack1.l == nullptr ? false : env->IsInstanceOf(cstack1.l, (cclasses[11]));
        // New stack: 2
        // IFNE L65; Stack: 2
        if (cstack1.i != 0) goto L65;
        // New stack: 1
        // NEW java/lang/BootstrapMethodError; Stack: 1
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (jobject obj = env->AllocObject((cclasses[11]))) { cstack1.l = obj; refs.insert(obj); } 
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
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[11]), ((char *)(string_pool + 628LL)), ((char *)(string_pool + 635LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 660LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[11]), (cmethods[4]), cstack2.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 1
        // LABEL L65; Stack: 1
        L65: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/Object]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal3.l); refs.erase(clocal4.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 683LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 0
        // LABEL L64; Stack: 0
        L64: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 0
        // FRAME FULL L: [[Ljava/lang/Object;, 4, java/lang/Class, java/lang/reflect/Method] S: [java/lang/reflect/Method, 5, [Ljava/lang/Object;, 4, 4, java/lang/invoke/MethodHandle]; Stack: 0
        refs.erase(cstack0.l); refs.erase(cstack1.l); refs.erase(cstack2.l); refs.erase(cstack7.l); 
        refs.erase(clocal0.l); refs.erase(clocal3.l); refs.erase(clocal4.l); 
        utils::clear_refs(env, refs);
        // New stack: 8
        // INVOKESTATIC native/magic/1/invoke/obfuscator0.8175695498474079.a(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;JJLjava/lang/invoke/MethodHandle;)Ljava/lang/Object;; Stack: 8
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[16]) { cmethods[16] = env->GetStaticMethodID((cclasses[12]), ((char *)(string_pool + 1041LL)), ((char *)(string_pool + 1056LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack0.l = env->CallStaticObjectMethod((cclasses[12]), (cmethods[16]), cstack0.l, cstack1.l, cstack2.l, cstack3.j, cstack5.j, cstack7.l); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 1
        // CHECKCAST java/lang/Object; Stack: 1
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack0.l != nullptr && !env->IsInstanceOf(cstack0.l, (cclasses[4]))) { utils::throw_re(env, ((char *)(string_pool + 94LL)), (std::string(((char *)(string_pool + 123LL))) + std::string(((char *)(string_pool + 510LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } 
        // New stack: 1
        // POP; Stack: 1
        ;
        // New stack: 0
        // LABEL L26; Stack: 0
        L26: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // GOTO L66; Stack: 0
        goto L66;
        // New stack: 0
        // LABEL L27; Stack: 0
        L27: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // FRAME FULL L: [[Ljava/lang/Object;, 4] S: [java/lang/Exception]; Stack: 0
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ASTORE 3; Stack: 1
        clocal3.l = cstack0.l; refs.insert(cstack0.l);
        // New stack: 0
        // LABEL L66; Stack: 0
        L66: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // FRAME APPEND L: [java/lang/Object] S: null; Stack: 0
        refs.erase(clocal0.l); refs.erase(clocal3.l); 
        utils::clear_refs(env, refs);
        // New stack: 0
        // RETURN; Stack: 0
        return;
        // New stack: 0
        return (void) 0;
        L_CATCH_10: if (env->IsInstanceOf(cstack0.l, (cclasses[1]))) { goto L6; }
        goto L_CATCH_0;
        L_CATCH_9: if (env->IsInstanceOf(cstack0.l, (cclasses[1]))) { goto L8; }
        goto L_CATCH_0;
        L_CATCH_8: if (env->IsInstanceOf(cstack0.l, (cclasses[1]))) { goto L10; }
        goto L_CATCH_0;
        L_CATCH_7: if (env->IsInstanceOf(cstack0.l, (cclasses[1]))) { goto L12; }
        goto L_CATCH_0;
        L_CATCH_12: if (env->IsInstanceOf(cstack0.l, (cclasses[1]))) { goto L2; }
        goto L_CATCH_0;
        L_CATCH_0: if (env->IsInstanceOf(cstack0.l, (cclasses[0]))) { goto L27; }
        env->Throw((jthrowable) cstack0.l); return (void) 0;
        L_CATCH_2: if (env->IsInstanceOf(cstack0.l, (cclasses[1]))) { goto L22; }
        goto L_CATCH_0;
        L_CATCH_6: if (env->IsInstanceOf(cstack0.l, (cclasses[1]))) { goto L14; }
        goto L_CATCH_0;
        L_CATCH_11: if (env->IsInstanceOf(cstack0.l, (cclasses[1]))) { goto L4; }
        goto L_CATCH_0;
        L_CATCH_1: if (env->IsInstanceOf(cstack0.l, (cclasses[1]))) { goto L24; }
        goto L_CATCH_0;
        L_CATCH_5: if (env->IsInstanceOf(cstack0.l, (cclasses[1]))) { goto L16; }
        goto L_CATCH_0;
        L_CATCH_3: if (env->IsInstanceOf(cstack0.l, (cclasses[1]))) { goto L20; }
        goto L_CATCH_0;
        L_CATCH_4: if (env->IsInstanceOf(cstack0.l, (cclasses[1]))) { goto L18; }
        goto L_CATCH_0;
    }
    
    // <clinit>()V
    void JNICALL __ngen_special_clinit_7_4(JNIEnv *env, jobject ignored_hidden, jclass clazz) {
        env->DeleteLocalRef(ignored_hidden);
        jobject classloader = utils::get_classloader_from_class(env, clazz);
        if (env->ExceptionCheck()) { return (void) 0; }
        if (classloader == nullptr) { env->FatalError(((char *)(string_pool + 32LL))); return (void) 0; }
    
        jobject lookup = nullptr;
        jvalue cstack0 = {}, cstack1 = {}, cstack2 = {}, cstack3 = {}, cstack4 = {}, cstack5 = {}, cstack6 = {}, cstack7 = {}, cstack8 = {}, cstack9 = {};
        jvalue clocal0 = {}, clocal1 = {}, clocal2 = {}, clocal3 = {}, clocal4 = {}, clocal5 = {}, clocal6 = {}, clocal7 = {}, clocal8 = {}, clocal9 = {}, clocal10 = {}, clocal11 = {}, clocal12 = {}, clocal13 = {}, clocal14 = {}, clocal15 = {};
        std::unordered_set<jobject> refs;
    
    
        // LDC -6490356016246719937; Stack: 0
        cstack0.j = -6490356016246719937LL;
        // New stack: 2
        // LDC -1255010095625461898; Stack: 2
        cstack2.j = -1255010095625461898LL;
        // New stack: 4
        // INVOKESTATIC java/lang/invoke/MethodHandles.lookup()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 4
        if (!cclasses[23] || env->IsSameObject(cclasses[23], NULL)) { cclasses_mtx[23].lock(); if (!cclasses[23] || env->IsSameObject(cclasses[23], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[43]))) { cclasses[23] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[23].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[18]) { cmethods[18] = env->GetStaticMethodID((cclasses[23]), ((char *)(string_pool + 2382LL)), ((char *)(string_pool + 2389LL))); if (env->ExceptionCheck()) { return (void) 0; }  } cstack4.l = env->CallStaticObjectMethod((cclasses[23]), (cmethods[18])); refs.insert(cstack4.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 5
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.lookupClass()Ljava/lang/Class;; Stack: 5
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[19]) { cmethods[19] = env->GetMethodID((cclasses[8]), ((char *)(string_pool + 2431LL)), ((char *)(string_pool + 2023LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 485LL)), -1); else { cstack4.l = env->CallObjectMethod(cstack4.l, (cmethods[19])); refs.insert(cstack4.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 5
        // INVOKESTATIC dev/sakura/client/Mahiro_nq.a(JJLjava/lang/Object;)Ldev/sakura/client/Mahiro_ee;; Stack: 5
        if (!cclasses[24] || env->IsSameObject(cclasses[24], NULL)) { cclasses_mtx[24].lock(); if (!cclasses[24] || env->IsSameObject(cclasses[24], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[44]))) { cclasses[24] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[24].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[20]) { cmethods[20] = env->GetStaticMethodID((cclasses[24]), ((char *)(string_pool + 210LL)), ((char *)(string_pool + 2443LL))); if (env->ExceptionCheck()) { return (void) 0; }  } cstack0.l = env->CallStaticObjectMethod((cclasses[24]), (cmethods[20]), cstack0.j, cstack2.j, cstack4.l); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // LDC 203388598731778; Stack: 1
        cstack1.j = 203388598731778LL;
        // New stack: 3
        // INVOKEINTERFACE dev/sakura/client/Mahiro_ee.a(J)J; Stack: 3
        if (!cclasses[25] || env->IsSameObject(cclasses[25], NULL)) { cclasses_mtx[25].lock(); if (!cclasses[25] || env->IsSameObject(cclasses[25], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[45]))) { cclasses[25] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[25].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[21]) { cmethods[21] = env->GetMethodID((cclasses[25]), ((char *)(string_pool + 210LL)), ((char *)(string_pool + 2495LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 2500LL)), -1); else cstack0.j = env->CallLongMethod(cstack0.l, (cmethods[21]), cstack1.j); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 2
        // PUTSTATIC dev/sakura/client/Mahiro_i3.a J; Stack: 2
        if (!cclasses[3]  || env->IsSameObject(cclasses[3], NULL)) { cclasses_mtx[3].lock(); if (!cclasses[3] || env->IsSameObject(cclasses[3], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[3] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[3].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cfields[0]) { cfields[0] = env->GetStaticFieldID((cclasses[3]), ((char *)(string_pool + 210LL)), ((char *)(string_pool + 212LL))); if (env->ExceptionCheck()) { return (void) 0; }  } env->SetStaticLongField((cclasses[3]), (cfields[0]), cstack0.j); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // NEW java/util/HashMap; Stack: 0
        if (!cclasses[26] || env->IsSameObject(cclasses[26], NULL)) { cclasses_mtx[26].lock(); if (!cclasses[26] || env->IsSameObject(cclasses[26], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[46]))) { cclasses[26] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[26].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (jobject obj = env->AllocObject((cclasses[26]))) { cstack0.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // BIPUSH 13; Stack: 2
        cstack2.i = (jint) 13;
        // New stack: 3
        // INVOKESPECIAL java/util/HashMap.<init>(I)V; Stack: 3
        if (!cclasses[26] || env->IsSameObject(cclasses[26], NULL)) { cclasses_mtx[26].lock(); if (!cclasses[26] || env->IsSameObject(cclasses[26], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[46]))) { cclasses[26] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[26].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[22]) { cmethods[22] = env->GetMethodID((cclasses[26]), ((char *)(string_pool + 628LL)), ((char *)(string_pool + 2525LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 660LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[26]), (cmethods[22]), cstack2.i); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // PUTSTATIC dev/sakura/client/Mahiro_i3.d Ljava/util/Map;; Stack: 1
        if (!cclasses[3]  || env->IsSameObject(cclasses[3], NULL)) { cclasses_mtx[3].lock(); if (!cclasses[3] || env->IsSameObject(cclasses[3], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[3] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[3].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cfields[2]) { cfields[2] = env->GetStaticFieldID((cclasses[3]), ((char *)(string_pool + 2530LL)), ((char *)(string_pool + 2532LL))); if (env->ExceptionCheck()) { return (void) 0; }  } env->SetStaticObjectField((cclasses[3]), (cfields[2]), cstack0.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // GETSTATIC dev/sakura/client/Mahiro_i3.a J; Stack: 0
        if (!cclasses[3]  || env->IsSameObject(cclasses[3], NULL)) { cclasses_mtx[3].lock(); if (!cclasses[3] || env->IsSameObject(cclasses[3], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[3] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[3].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cfields[0]) { cfields[0] = env->GetStaticFieldID((cclasses[3]), ((char *)(string_pool + 210LL)), ((char *)(string_pool + 212LL))); if (env->ExceptionCheck()) { return (void) 0; }  } cstack0.j = env->GetStaticLongField((cclasses[3]), (cfields[0])); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 2
        // LDC 57665947224886; Stack: 2
        cstack2.j = 57665947224886LL;
        // New stack: 4
        // LXOR; Stack: 4
        cstack0.j = cstack0.j ^ cstack2.j;
        // New stack: 2
        // LSTORE 5; Stack: 2
        clocal5.j = cstack0.j;
        // New stack: 0
        // LDC DES/CBC/PKCS5Padding; Stack: 0
        cstack0.l = (cstrings[47]);
        // New stack: 1
        // INVOKESTATIC javax/crypto/Cipher.getInstance(Ljava/lang/String;)Ljavax/crypto/Cipher;; Stack: 1
        if (!cclasses[27] || env->IsSameObject(cclasses[27], NULL)) { cclasses_mtx[27].lock(); if (!cclasses[27] || env->IsSameObject(cclasses[27], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[48]))) { cclasses[27] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[27].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[23]) { cmethods[23] = env->GetStaticMethodID((cclasses[27]), ((char *)(string_pool + 2548LL)), ((char *)(string_pool + 2560LL))); if (env->ExceptionCheck()) { return (void) 0; }  } cstack0.l = env->CallStaticObjectMethod((cclasses[27]), (cmethods[23]), cstack0.l); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // ASTORE 7; Stack: 2
        clocal7.l = cstack1.l; refs.insert(cstack1.l);
        // New stack: 1
        // ICONST_2; Stack: 1
        cstack1.i = 2;
        // New stack: 2
        // LDC DES; Stack: 2
        cstack2.l = (cstrings[49]);
        // New stack: 3
        // INVOKESTATIC javax/crypto/SecretKeyFactory.getInstance(Ljava/lang/String;)Ljavax/crypto/SecretKeyFactory;; Stack: 3
        if (!cclasses[28] || env->IsSameObject(cclasses[28], NULL)) { cclasses_mtx[28].lock(); if (!cclasses[28] || env->IsSameObject(cclasses[28], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[50]))) { cclasses[28] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[28].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[24]) { cmethods[24] = env->GetStaticMethodID((cclasses[28]), ((char *)(string_pool + 2548LL)), ((char *)(string_pool + 2602LL))); if (env->ExceptionCheck()) { return (void) 0; }  } cstack2.l = env->CallStaticObjectMethod((cclasses[28]), (cmethods[24]), cstack2.l); refs.insert(cstack2.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 3
        // BIPUSH 8; Stack: 3
        cstack3.i = (jint) 8;
        // New stack: 4
        // NEWARRAY 8; Stack: 4
        if (cstack3.i < 0) utils::throw_re(env, ((char *)(string_pool + 214LL)), ((char *)(string_pool + 2654LL)), -1); else { cstack3.l = env->NewByteArray(cstack3.i); refs.insert(cstack3.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 4
        // DUP; Stack: 4
        cstack4 = cstack3;
        // New stack: 5
        // ICONST_0; Stack: 5
        cstack5.i = 0;
        // New stack: 6
        // LLOAD 5; Stack: 6
        cstack6.j = clocal5.j;
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
        if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 2683LL)), -1); else { utils::bastore(env, (jarray) cstack4.l, cstack5.i, cstack6.i); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 4
        // ICONST_1; Stack: 4
        cstack4.i = 1;
        // New stack: 5
        // ISTORE 8; Stack: 5
        clocal8.i = cstack4.i;
        // New stack: 4
        // LABEL L1; Stack: 4
        L1: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 4
        // FRAME FULL L: [0, 0, 0, 0, 0, 4, javax/crypto/Cipher, 1] S: [javax/crypto/Cipher, 1, javax/crypto/SecretKeyFactory, [B]; Stack: 4
        refs.erase(cstack0.l); refs.erase(cstack2.l); refs.erase(cstack3.l); 
        refs.erase(clocal7.l); 
        utils::clear_refs(env, refs);
        // New stack: 4
        // ILOAD 8; Stack: 4
        cstack4.i = clocal8.i;
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
        // ILOAD 8; Stack: 5
        cstack5.i = clocal8.i;
        // New stack: 6
        // LLOAD 5; Stack: 6
        cstack6.j = clocal5.j;
        // New stack: 8
        // ILOAD 8; Stack: 8
        cstack8.i = clocal8.i;
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
        if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 2683LL)), -1); else { utils::bastore(env, (jarray) cstack4.l, cstack5.i, cstack6.i); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 4
        // IINC 8 1; Stack: 4
        clocal8.i += 1;
        // New stack: 4
        // GOTO L1; Stack: 4
        goto L1;
        // New stack: 4
        // LABEL L2; Stack: 4
        L2: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 4
        // FRAME FULL L: [0, 0, 0, 0, 0, 4, javax/crypto/Cipher, 1] S: [javax/crypto/Cipher, 1, javax/crypto/SecretKeyFactory, [B]; Stack: 4
        refs.erase(cstack0.l); refs.erase(cstack2.l); refs.erase(cstack3.l); 
        refs.erase(clocal7.l); 
        utils::clear_refs(env, refs);
        // New stack: 4
        // NEW javax/crypto/spec/DESKeySpec; Stack: 4
        if (!cclasses[29] || env->IsSameObject(cclasses[29], NULL)) { cclasses_mtx[29].lock(); if (!cclasses[29] || env->IsSameObject(cclasses[29], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[51]))) { cclasses[29] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[29].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (jobject obj = env->AllocObject((cclasses[29]))) { cstack4.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 5
        // DUP_X1; Stack: 5
        cstack5 = cstack4; cstack4 = cstack3; cstack3 = cstack5;
        // New stack: 6
        // SWAP; Stack: 6
        std::swap(cstack5, cstack4);
        // New stack: 6
        // INVOKESPECIAL javax/crypto/spec/DESKeySpec.<init>([B)V; Stack: 6
        if (!cclasses[29] || env->IsSameObject(cclasses[29], NULL)) { cclasses_mtx[29].lock(); if (!cclasses[29] || env->IsSameObject(cclasses[29], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[51]))) { cclasses[29] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[29].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[25]) { cmethods[25] = env->GetMethodID((cclasses[29]), ((char *)(string_pool + 628LL)), ((char *)(string_pool + 2695LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 660LL)), -1); else env->CallNonvirtualVoidMethod(cstack4.l, (cclasses[29]), (cmethods[25]), cstack5.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 4
        // INVOKEVIRTUAL javax/crypto/SecretKeyFactory.generateSecret(Ljava/security/spec/KeySpec;)Ljavax/crypto/SecretKey;; Stack: 4
        if (!cclasses[28] || env->IsSameObject(cclasses[28], NULL)) { cclasses_mtx[28].lock(); if (!cclasses[28] || env->IsSameObject(cclasses[28], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[50]))) { cclasses[28] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[28].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[26]) { cmethods[26] = env->GetMethodID((cclasses[28]), ((char *)(string_pool + 2701LL)), ((char *)(string_pool + 2716LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack2.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 485LL)), -1); else { cstack2.l = env->CallObjectMethod(cstack2.l, (cmethods[26]), cstack3.l); refs.insert(cstack2.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 3
        // NEW javax/crypto/spec/IvParameterSpec; Stack: 3
        if (!cclasses[30] || env->IsSameObject(cclasses[30], NULL)) { cclasses_mtx[30].lock(); if (!cclasses[30] || env->IsSameObject(cclasses[30], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[52]))) { cclasses[30] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[30].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (jobject obj = env->AllocObject((cclasses[30]))) { cstack3.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 4
        // DUP; Stack: 4
        cstack4 = cstack3;
        // New stack: 5
        // BIPUSH 8; Stack: 5
        cstack5.i = (jint) 8;
        // New stack: 6
        // NEWARRAY 8; Stack: 6
        if (cstack5.i < 0) utils::throw_re(env, ((char *)(string_pool + 214LL)), ((char *)(string_pool + 2654LL)), -1); else { cstack5.l = env->NewByteArray(cstack5.i); refs.insert(cstack5.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 6
        // INVOKESPECIAL javax/crypto/spec/IvParameterSpec.<init>([B)V; Stack: 6
        if (!cclasses[30] || env->IsSameObject(cclasses[30], NULL)) { cclasses_mtx[30].lock(); if (!cclasses[30] || env->IsSameObject(cclasses[30], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[52]))) { cclasses[30] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[30].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[27]) { cmethods[27] = env->GetMethodID((cclasses[30]), ((char *)(string_pool + 628LL)), ((char *)(string_pool + 2695LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 660LL)), -1); else env->CallNonvirtualVoidMethod(cstack4.l, (cclasses[30]), (cmethods[27]), cstack5.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 4
        // INVOKEVIRTUAL javax/crypto/Cipher.init(ILjava/security/Key;Ljava/security/spec/AlgorithmParameterSpec;)V; Stack: 4
        if (!cclasses[27] || env->IsSameObject(cclasses[27], NULL)) { cclasses_mtx[27].lock(); if (!cclasses[27] || env->IsSameObject(cclasses[27], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[48]))) { cclasses[27] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[27].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[28]) { cmethods[28] = env->GetMethodID((cclasses[27]), ((char *)(string_pool + 2771LL)), ((char *)(string_pool + 2776LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 2843LL)), -1); else env->CallVoidMethod(cstack0.l, (cmethods[28]), cstack1.i, cstack2.l, cstack3.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // ICONST_2; Stack: 0
        cstack0.i = 2;
        // New stack: 1
        // ANEWARRAY java/lang/String; Stack: 1
        if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { cclasses_mtx[13].lock(); if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[16]))) { cclasses[13] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[13].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (cstack0.i < 0) utils::throw_re(env, ((char *)(string_pool + 214LL)), ((char *)(string_pool + 251LL)), -1); else { cstack0.l = env->NewObjectArray(cstack0.i, (cclasses[13]), nullptr); refs.insert(cstack0.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // ASTORE 14; Stack: 1
        clocal14.l = cstack0.l; refs.insert(cstack0.l);
        // New stack: 0
        // ICONST_0; Stack: 0
        cstack0.i = 0;
        // New stack: 1
        // ISTORE 12; Stack: 1
        clocal12.i = cstack0.i;
        // New stack: 0
        // LDC |[\u000a\u00d3Z\u00c6k\u0014\u00e3\u008e\u00a1\u00e9\u008aRK[=\u001c\u00f2$'N\u0087[\u00d2\u0015%~U\u00ca%J\u0011\u00b1\u00af$\u00d0%~\u00e5$\u00bd_\u0090\u00e8\u00abcu\u0018\u00f3/j\u0005\u00e4\u00d6$Tr\u00ce\u0008\u00d0\u0012\u00ae\u00d7#(\u009a\u00f6\u0009m\u008a%\u00fb; Stack: 0
        cstack0.l = (cstrings[53]);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // ASTORE 11; Stack: 2
        clocal11.l = cstack1.l; refs.insert(cstack1.l);
        // New stack: 1
        // INVOKEVIRTUAL java/lang/String.length()I; Stack: 1
        if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { cclasses_mtx[13].lock(); if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[16]))) { cclasses[13] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[13].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[29]) { cmethods[29] = env->GetMethodID((cclasses[13]), ((char *)(string_pool + 2866LL)), ((char *)(string_pool + 2185LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 2189LL)), -1); else cstack0.i = env->CallIntMethod(cstack0.l, (cmethods[29])); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // ISTORE 13; Stack: 1
        clocal13.i = cstack0.i;
        // New stack: 0
        // BIPUSH 48; Stack: 0
        cstack0.i = (jint) 48;
        // New stack: 1
        // ISTORE 10; Stack: 1
        clocal10.i = cstack0.i;
        // New stack: 0
        // ICONST_M1; Stack: 0
        cstack0.i = -1;
        // New stack: 1
        // ISTORE 9; Stack: 1
        clocal9.i = cstack0.i;
        // New stack: 0
        // LABEL L3; Stack: 0
        L3: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // FRAME FULL L: [0, 0, 0, 0, 0, 4, javax/crypto/Cipher, 1, 1, 1, java/lang/String, 1, 1, [Ljava/lang/String;] S: []; Stack: 0
        refs.erase(clocal7.l); refs.erase(clocal11.l); refs.erase(clocal14.l); 
        utils::clear_refs(env, refs);
        // New stack: 0
        // IINC 9 1; Stack: 0
        clocal9.i += 1;
        // New stack: 0
        // ALOAD 11; Stack: 0
        cstack0.l = clocal11.l; refs.insert(cstack0.l);
        // New stack: 1
        // ILOAD 9; Stack: 1
        cstack1.i = clocal9.i;
        // New stack: 2
        // DUP; Stack: 2
        cstack2 = cstack1;
        // New stack: 3
        // ILOAD 10; Stack: 3
        cstack3.i = clocal10.i;
        // New stack: 4
        // IADD; Stack: 4
        cstack2.i = cstack2.i + cstack3.i;
        // New stack: 3
        // INVOKEVIRTUAL java/lang/String.substring(II)Ljava/lang/String;; Stack: 3
        if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { cclasses_mtx[13].lock(); if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[16]))) { cclasses[13] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[13].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[30]) { cmethods[30] = env->GetMethodID((cclasses[13]), ((char *)(string_pool + 2873LL)), ((char *)(string_pool + 2883LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 485LL)), -1); else { cstack0.l = env->CallObjectMethod(cstack0.l, (cmethods[30]), cstack1.i, cstack2.i); refs.insert(cstack0.l); } 
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
        // FRAME FULL L: [0, 0, 0, 0, 0, 4, javax/crypto/Cipher, 1, 1, 1, java/lang/String, 1, 1, [Ljava/lang/String;, [B] S: [java/lang/String]; Stack: 2
        refs.erase(cstack0.l); 
        refs.erase(clocal7.l); refs.erase(clocal11.l); refs.erase(clocal14.l); refs.erase(clocal15.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ALOAD 14; Stack: 1
        cstack1.l = clocal14.l; refs.insert(cstack1.l);
        // New stack: 2
        // SWAP; Stack: 2
        std::swap(cstack1, cstack0);
        // New stack: 2
        // ILOAD 12; Stack: 2
        cstack2.i = clocal12.i;
        // New stack: 3
        // IINC 12 1; Stack: 3
        clocal12.i += 1;
        // New stack: 3
        // SWAP; Stack: 3
        std::swap(cstack2, cstack1);
        // New stack: 3
        // AASTORE; Stack: 3
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 1657LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack0.l, cstack1.i, cstack2.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // ILOAD 9; Stack: 0
        cstack0.i = clocal9.i;
        // New stack: 1
        // ILOAD 10; Stack: 1
        cstack1.i = clocal10.i;
        // New stack: 2
        // IADD; Stack: 2
        cstack0.i = cstack0.i + cstack1.i;
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // ISTORE 9; Stack: 2
        clocal9.i = cstack1.i;
        // New stack: 1
        // ILOAD 13; Stack: 1
        cstack1.i = clocal13.i;
        // New stack: 2
        // IF_ICMPGE L6; Stack: 2
        if (cstack0.i >= cstack1.i) goto L6;
        // New stack: 0
        // ALOAD 11; Stack: 0
        cstack0.l = clocal11.l; refs.insert(cstack0.l);
        // New stack: 1
        // ILOAD 9; Stack: 1
        cstack1.i = clocal9.i;
        // New stack: 2
        // INVOKEVIRTUAL java/lang/String.charAt(I)C; Stack: 2
        if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { cclasses_mtx[13].lock(); if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[16]))) { cclasses[13] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[13].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[31]) { cmethods[31] = env->GetMethodID((cclasses[13]), ((char *)(string_pool + 2906LL)), ((char *)(string_pool + 2913LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 2918LL)), -1); else cstack0.i = (jint) env->CallCharMethod(cstack0.l, (cmethods[31]), cstack1.i); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // ISTORE 10; Stack: 1
        clocal10.i = cstack0.i;
        // New stack: 0
        // GOTO L3; Stack: 0
        goto L3;
        // New stack: 0
        // LABEL L6; Stack: 0
        L6: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // FRAME SAME L: null S: null; Stack: 0
        refs.erase(clocal7.l); refs.erase(clocal11.l); refs.erase(clocal14.l); refs.erase(clocal15.l); 
        utils::clear_refs(env, refs);
        // New stack: 0
        // ALOAD 14; Stack: 0
        cstack0.l = clocal14.l; refs.insert(cstack0.l);
        // New stack: 1
        // PUTSTATIC dev/sakura/client/Mahiro_i3.b [Ljava/lang/String;; Stack: 1
        if (!cclasses[3]  || env->IsSameObject(cclasses[3], NULL)) { cclasses_mtx[3].lock(); if (!cclasses[3] || env->IsSameObject(cclasses[3], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[3] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[3].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cfields[3]) { cfields[3] = env->GetStaticFieldID((cclasses[3]), ((char *)(string_pool + 2941LL)), ((char *)(string_pool + 2943LL))); if (env->ExceptionCheck()) { return (void) 0; }  } env->SetStaticObjectField((cclasses[3]), (cfields[3]), cstack0.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // ICONST_2; Stack: 0
        cstack0.i = 2;
        // New stack: 1
        // ANEWARRAY java/lang/String; Stack: 1
        if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { cclasses_mtx[13].lock(); if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[16]))) { cclasses[13] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[13].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (cstack0.i < 0) utils::throw_re(env, ((char *)(string_pool + 214LL)), ((char *)(string_pool + 251LL)), -1); else { cstack0.l = env->NewObjectArray(cstack0.i, (cclasses[13]), nullptr); refs.insert(cstack0.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // PUTSTATIC dev/sakura/client/Mahiro_i3.c [Ljava/lang/String;; Stack: 1
        if (!cclasses[3]  || env->IsSameObject(cclasses[3], NULL)) { cclasses_mtx[3].lock(); if (!cclasses[3] || env->IsSameObject(cclasses[3], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[3] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[3].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cfields[4]) { cfields[4] = env->GetStaticFieldID((cclasses[3]), ((char *)(string_pool + 2963LL)), ((char *)(string_pool + 2943LL))); if (env->ExceptionCheck()) { return (void) 0; }  } env->SetStaticObjectField((cclasses[3]), (cfields[4]), cstack0.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // GOTO L7; Stack: 0
        goto L7;
        // New stack: 0
        // LABEL L4; Stack: 0
        L4: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // FRAME FULL L: [0, 0, 0, 0, 0, 4, javax/crypto/Cipher, 1, 1, 1, java/lang/String, 1, 1, [Ljava/lang/String;] S: [java/lang/String, 1]; Stack: 0
        refs.erase(cstack0.l); 
        refs.erase(clocal7.l); refs.erase(clocal11.l); refs.erase(clocal14.l); 
        utils::clear_refs(env, refs);
        // New stack: 2
        // SWAP; Stack: 2
        std::swap(cstack1, cstack0);
        // New stack: 2
        // LDC ISO-8859-1; Stack: 2
        cstack2.l = (cstrings[54]);
        // New stack: 3
        // INVOKEVIRTUAL java/lang/String.getBytes(Ljava/lang/String;)[B; Stack: 3
        if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { cclasses_mtx[13].lock(); if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[16]))) { cclasses[13] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[13].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[32]) { cmethods[32] = env->GetMethodID((cclasses[13]), ((char *)(string_pool + 2965LL)), ((char *)(string_pool + 2974LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 485LL)), -1); else { cstack1.l = env->CallObjectMethod(cstack1.l, (cmethods[32]), cstack2.l); refs.insert(cstack1.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 2
        // ALOAD 7; Stack: 2
        cstack2.l = clocal7.l; refs.insert(cstack2.l);
        // New stack: 3
        // SWAP; Stack: 3
        std::swap(cstack2, cstack1);
        // New stack: 3
        // INVOKEVIRTUAL javax/crypto/Cipher.doFinal([B)[B; Stack: 3
        if (!cclasses[27] || env->IsSameObject(cclasses[27], NULL)) { cclasses_mtx[27].lock(); if (!cclasses[27] || env->IsSameObject(cclasses[27], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[48]))) { cclasses[27] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[27].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[33]) { cmethods[33] = env->GetMethodID((cclasses[27]), ((char *)(string_pool + 2997LL)), ((char *)(string_pool + 3005LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 485LL)), -1); else { cstack1.l = env->CallObjectMethod(cstack1.l, (cmethods[33]), cstack2.l); refs.insert(cstack1.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 2
        // ASTORE 15; Stack: 2
        clocal15.l = cstack1.l; refs.insert(cstack1.l);
        // New stack: 1
        // ALOAD 15; Stack: 1
        cstack1.l = clocal15.l; refs.insert(cstack1.l);
        // New stack: 2
        // INVOKESTATIC dev/sakura/client/Mahiro_i3.a([B)Ljava/lang/String;; Stack: 2
        if (!cclasses[3] || env->IsSameObject(cclasses[3], NULL)) { cclasses_mtx[3].lock(); if (!cclasses[3] || env->IsSameObject(cclasses[3], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[3] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[3].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[34]) { cmethods[34] = env->GetStaticMethodID((cclasses[3]), ((char *)(string_pool + 210LL)), ((char *)(string_pool + 3012LL))); if (env->ExceptionCheck()) { return (void) 0; }  } cstack1.l = env->CallStaticObjectMethod((cclasses[3]), (cmethods[34]), cstack1.l); refs.insert(cstack1.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 2
        // INVOKEVIRTUAL java/lang/String.intern()Ljava/lang/String;; Stack: 2
        if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { cclasses_mtx[13].lock(); if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[16]))) { cclasses[13] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[13].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[35]) { cmethods[35] = env->GetMethodID((cclasses[13]), ((char *)(string_pool + 3035LL)), ((char *)(string_pool + 3042LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 485LL)), -1); else { cstack1.l = env->CallObjectMethod(cstack1.l, (cmethods[35])); refs.insert(cstack1.l); } 
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
        refs.erase(clocal7.l); refs.erase(clocal11.l); refs.erase(clocal14.l); refs.erase(clocal15.l); 
        utils::clear_refs(env, refs);
        // New stack: 0
        // LDC DES/CBC/NoPadding; Stack: 0
        cstack0.l = (cstrings[55]);
        // New stack: 1
        // INVOKESTATIC javax/crypto/Cipher.getInstance(Ljava/lang/String;)Ljavax/crypto/Cipher;; Stack: 1
        if (!cclasses[27] || env->IsSameObject(cclasses[27], NULL)) { cclasses_mtx[27].lock(); if (!cclasses[27] || env->IsSameObject(cclasses[27], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[48]))) { cclasses[27] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[27].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[23]) { cmethods[23] = env->GetStaticMethodID((cclasses[27]), ((char *)(string_pool + 2548LL)), ((char *)(string_pool + 2560LL))); if (env->ExceptionCheck()) { return (void) 0; }  } cstack0.l = env->CallStaticObjectMethod((cclasses[27]), (cmethods[23]), cstack0.l); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // ASTORE 0; Stack: 2
        clocal0.l = cstack1.l; refs.insert(cstack1.l);
        // New stack: 1
        // ICONST_2; Stack: 1
        cstack1.i = 2;
        // New stack: 2
        // LDC DES; Stack: 2
        cstack2.l = (cstrings[49]);
        // New stack: 3
        // INVOKESTATIC javax/crypto/SecretKeyFactory.getInstance(Ljava/lang/String;)Ljavax/crypto/SecretKeyFactory;; Stack: 3
        if (!cclasses[28] || env->IsSameObject(cclasses[28], NULL)) { cclasses_mtx[28].lock(); if (!cclasses[28] || env->IsSameObject(cclasses[28], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[50]))) { cclasses[28] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[28].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[24]) { cmethods[24] = env->GetStaticMethodID((cclasses[28]), ((char *)(string_pool + 2548LL)), ((char *)(string_pool + 2602LL))); if (env->ExceptionCheck()) { return (void) 0; }  } cstack2.l = env->CallStaticObjectMethod((cclasses[28]), (cmethods[24]), cstack2.l); refs.insert(cstack2.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 3
        // BIPUSH 8; Stack: 3
        cstack3.i = (jint) 8;
        // New stack: 4
        // NEWARRAY 8; Stack: 4
        if (cstack3.i < 0) utils::throw_re(env, ((char *)(string_pool + 214LL)), ((char *)(string_pool + 2654LL)), -1); else { cstack3.l = env->NewByteArray(cstack3.i); refs.insert(cstack3.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 4
        // DUP; Stack: 4
        cstack4 = cstack3;
        // New stack: 5
        // ICONST_0; Stack: 5
        cstack5.i = 0;
        // New stack: 6
        // LLOAD 5; Stack: 6
        cstack6.j = clocal5.j;
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
        if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 2683LL)), -1); else { utils::bastore(env, (jarray) cstack4.l, cstack5.i, cstack6.i); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 4
        // ICONST_1; Stack: 4
        cstack4.i = 1;
        // New stack: 5
        // ISTORE 1; Stack: 5
        clocal1.i = cstack4.i;
        // New stack: 4
        // LABEL L8; Stack: 4
        L8: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 4
        // FRAME FULL L: [javax/crypto/Cipher, 1, 0, 0, 0, 4, javax/crypto/Cipher, 1, 1, 1, java/lang/String, 1, 1, [Ljava/lang/String;, [B] S: [javax/crypto/Cipher, 1, javax/crypto/SecretKeyFactory, [B]; Stack: 4
        refs.erase(cstack0.l); refs.erase(cstack2.l); refs.erase(cstack3.l); 
        refs.erase(clocal0.l); refs.erase(clocal7.l); refs.erase(clocal11.l); refs.erase(clocal14.l); refs.erase(clocal15.l); 
        utils::clear_refs(env, refs);
        // New stack: 4
        // ILOAD 1; Stack: 4
        cstack4.i = clocal1.i;
        // New stack: 5
        // BIPUSH 8; Stack: 5
        cstack5.i = (jint) 8;
        // New stack: 6
        // IF_ICMPGE L9; Stack: 6
        if (cstack4.i >= cstack5.i) goto L9;
        // New stack: 4
        // DUP; Stack: 4
        cstack4 = cstack3;
        // New stack: 5
        // ILOAD 1; Stack: 5
        cstack5.i = clocal1.i;
        // New stack: 6
        // LLOAD 5; Stack: 6
        cstack6.j = clocal5.j;
        // New stack: 8
        // ILOAD 1; Stack: 8
        cstack8.i = clocal1.i;
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
        if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 2683LL)), -1); else { utils::bastore(env, (jarray) cstack4.l, cstack5.i, cstack6.i); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 4
        // IINC 1 1; Stack: 4
        clocal1.i += 1;
        // New stack: 4
        // GOTO L8; Stack: 4
        goto L8;
        // New stack: 4
        // LABEL L9; Stack: 4
        L9: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 4
        // FRAME FULL L: [javax/crypto/Cipher, 1, 0, 0, 0, 4, javax/crypto/Cipher, 1, 1, 1, java/lang/String, 1, 1, [Ljava/lang/String;, [B] S: [javax/crypto/Cipher, 1, javax/crypto/SecretKeyFactory, [B]; Stack: 4
        refs.erase(cstack0.l); refs.erase(cstack2.l); refs.erase(cstack3.l); 
        refs.erase(clocal0.l); refs.erase(clocal7.l); refs.erase(clocal11.l); refs.erase(clocal14.l); refs.erase(clocal15.l); 
        utils::clear_refs(env, refs);
        // New stack: 4
        // NEW javax/crypto/spec/DESKeySpec; Stack: 4
        if (!cclasses[29] || env->IsSameObject(cclasses[29], NULL)) { cclasses_mtx[29].lock(); if (!cclasses[29] || env->IsSameObject(cclasses[29], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[51]))) { cclasses[29] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[29].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (jobject obj = env->AllocObject((cclasses[29]))) { cstack4.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 5
        // DUP_X1; Stack: 5
        cstack5 = cstack4; cstack4 = cstack3; cstack3 = cstack5;
        // New stack: 6
        // SWAP; Stack: 6
        std::swap(cstack5, cstack4);
        // New stack: 6
        // INVOKESPECIAL javax/crypto/spec/DESKeySpec.<init>([B)V; Stack: 6
        if (!cclasses[29] || env->IsSameObject(cclasses[29], NULL)) { cclasses_mtx[29].lock(); if (!cclasses[29] || env->IsSameObject(cclasses[29], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[51]))) { cclasses[29] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[29].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[25]) { cmethods[25] = env->GetMethodID((cclasses[29]), ((char *)(string_pool + 628LL)), ((char *)(string_pool + 2695LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 660LL)), -1); else env->CallNonvirtualVoidMethod(cstack4.l, (cclasses[29]), (cmethods[25]), cstack5.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 4
        // INVOKEVIRTUAL javax/crypto/SecretKeyFactory.generateSecret(Ljava/security/spec/KeySpec;)Ljavax/crypto/SecretKey;; Stack: 4
        if (!cclasses[28] || env->IsSameObject(cclasses[28], NULL)) { cclasses_mtx[28].lock(); if (!cclasses[28] || env->IsSameObject(cclasses[28], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[50]))) { cclasses[28] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[28].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[26]) { cmethods[26] = env->GetMethodID((cclasses[28]), ((char *)(string_pool + 2701LL)), ((char *)(string_pool + 2716LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack2.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 485LL)), -1); else { cstack2.l = env->CallObjectMethod(cstack2.l, (cmethods[26]), cstack3.l); refs.insert(cstack2.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 3
        // NEW javax/crypto/spec/IvParameterSpec; Stack: 3
        if (!cclasses[30] || env->IsSameObject(cclasses[30], NULL)) { cclasses_mtx[30].lock(); if (!cclasses[30] || env->IsSameObject(cclasses[30], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[52]))) { cclasses[30] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[30].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (jobject obj = env->AllocObject((cclasses[30]))) { cstack3.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 4
        // DUP; Stack: 4
        cstack4 = cstack3;
        // New stack: 5
        // BIPUSH 8; Stack: 5
        cstack5.i = (jint) 8;
        // New stack: 6
        // NEWARRAY 8; Stack: 6
        if (cstack5.i < 0) utils::throw_re(env, ((char *)(string_pool + 214LL)), ((char *)(string_pool + 2654LL)), -1); else { cstack5.l = env->NewByteArray(cstack5.i); refs.insert(cstack5.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 6
        // INVOKESPECIAL javax/crypto/spec/IvParameterSpec.<init>([B)V; Stack: 6
        if (!cclasses[30] || env->IsSameObject(cclasses[30], NULL)) { cclasses_mtx[30].lock(); if (!cclasses[30] || env->IsSameObject(cclasses[30], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[52]))) { cclasses[30] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[30].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[27]) { cmethods[27] = env->GetMethodID((cclasses[30]), ((char *)(string_pool + 628LL)), ((char *)(string_pool + 2695LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 660LL)), -1); else env->CallNonvirtualVoidMethod(cstack4.l, (cclasses[30]), (cmethods[27]), cstack5.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 4
        // INVOKEVIRTUAL javax/crypto/Cipher.init(ILjava/security/Key;Ljava/security/spec/AlgorithmParameterSpec;)V; Stack: 4
        if (!cclasses[27] || env->IsSameObject(cclasses[27], NULL)) { cclasses_mtx[27].lock(); if (!cclasses[27] || env->IsSameObject(cclasses[27], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[48]))) { cclasses[27] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[27].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[28]) { cmethods[28] = env->GetMethodID((cclasses[27]), ((char *)(string_pool + 2771LL)), ((char *)(string_pool + 2776LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 2843LL)), -1); else env->CallVoidMethod(cstack0.l, (cmethods[28]), cstack1.i, cstack2.l, cstack3.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // LDC -2102616790153574257; Stack: 0
        cstack0.j = -2102616790153574257LL;
        // New stack: 2
        // ICONST_M1; Stack: 2
        cstack2.i = -1;
        // New stack: 3
        // GOTO L10; Stack: 3
        goto L10;
        // New stack: 3
        // LABEL L11; Stack: 3
        L11: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 3
        // FRAME FULL L: [javax/crypto/Cipher, 1, 4, [B, 4, javax/crypto/Cipher, 1, 1, 1, java/lang/String, 1, 1, [Ljava/lang/String;, [B] S: [4]; Stack: 3
        refs.erase(clocal0.l); refs.erase(clocal4.l); refs.erase(clocal7.l); refs.erase(clocal11.l); refs.erase(clocal14.l); refs.erase(clocal15.l); 
        utils::clear_refs(env, refs);
        // New stack: 2
        // PUTSTATIC dev/sakura/client/Mahiro_i3.e J; Stack: 2
        if (!cclasses[3]  || env->IsSameObject(cclasses[3], NULL)) { cclasses_mtx[3].lock(); if (!cclasses[3] || env->IsSameObject(cclasses[3], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[3] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[3].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cfields[1]) { cfields[1] = env->GetStaticFieldID((cclasses[3]), ((char *)(string_pool + 13868LL)), ((char *)(string_pool + 212LL))); if (env->ExceptionCheck()) { return (void) 0; }  } env->SetStaticLongField((cclasses[3]), (cfields[1]), cstack0.j); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // GOTO L12; Stack: 0
        goto L12;
        // New stack: 0
        // LABEL L10; Stack: 0
        L10: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // FRAME FULL L: [javax/crypto/Cipher, 1, 0, 0, 0, 4, javax/crypto/Cipher, 1, 1, 1, java/lang/String, 1, 1, [Ljava/lang/String;, [B] S: [4, 1]; Stack: 0
        refs.erase(clocal0.l); refs.erase(clocal7.l); refs.erase(clocal11.l); refs.erase(clocal14.l); refs.erase(clocal15.l); 
        utils::clear_refs(env, refs);
        // New stack: 3
        // DUP_X2; Stack: 3
        cstack3 = cstack2; cstack2 = cstack1; cstack1 = cstack0; cstack0 = cstack3;
        // New stack: 4
        // POP; Stack: 4
        ;
        // New stack: 3
        // LSTORE 2; Stack: 3
        clocal2.j = cstack1.j;
        // New stack: 1
        // BIPUSH 8; Stack: 1
        cstack1.i = (jint) 8;
        // New stack: 2
        // NEWARRAY 8; Stack: 2
        if (cstack1.i < 0) utils::throw_re(env, ((char *)(string_pool + 214LL)), ((char *)(string_pool + 2654LL)), -1); else { cstack1.l = env->NewByteArray(cstack1.i); refs.insert(cstack1.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 2
        // DUP; Stack: 2
        cstack2 = cstack1;
        // New stack: 3
        // ICONST_0; Stack: 3
        cstack3.i = 0;
        // New stack: 4
        // LLOAD 2; Stack: 4
        cstack4.j = clocal2.j;
        // New stack: 6
        // BIPUSH 56; Stack: 6
        cstack6.i = (jint) 56;
        // New stack: 7
        // LUSHR; Stack: 7
        cstack4.j = (jlong) (((uint64_t) cstack4.j) >> (((uint64_t) cstack6.i) & 0x3f));
        // New stack: 6
        // L2I; Stack: 6
        cstack4.i = (jint) cstack4.j;
        // New stack: 5
        // I2B; Stack: 5
        cstack4.i = (jint) (jbyte) cstack4.i;
        // New stack: 5
        // BASTORE; Stack: 5
        if (cstack2.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 2683LL)), -1); else { utils::bastore(env, (jarray) cstack2.l, cstack3.i, cstack4.i); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 2
        // DUP; Stack: 2
        cstack2 = cstack1;
        // New stack: 3
        // ICONST_1; Stack: 3
        cstack3.i = 1;
        // New stack: 4
        // LLOAD 2; Stack: 4
        cstack4.j = clocal2.j;
        // New stack: 6
        // BIPUSH 48; Stack: 6
        cstack6.i = (jint) 48;
        // New stack: 7
        // LUSHR; Stack: 7
        cstack4.j = (jlong) (((uint64_t) cstack4.j) >> (((uint64_t) cstack6.i) & 0x3f));
        // New stack: 6
        // L2I; Stack: 6
        cstack4.i = (jint) cstack4.j;
        // New stack: 5
        // I2B; Stack: 5
        cstack4.i = (jint) (jbyte) cstack4.i;
        // New stack: 5
        // BASTORE; Stack: 5
        if (cstack2.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 2683LL)), -1); else { utils::bastore(env, (jarray) cstack2.l, cstack3.i, cstack4.i); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 2
        // DUP; Stack: 2
        cstack2 = cstack1;
        // New stack: 3
        // ICONST_2; Stack: 3
        cstack3.i = 2;
        // New stack: 4
        // LLOAD 2; Stack: 4
        cstack4.j = clocal2.j;
        // New stack: 6
        // BIPUSH 40; Stack: 6
        cstack6.i = (jint) 40;
        // New stack: 7
        // LUSHR; Stack: 7
        cstack4.j = (jlong) (((uint64_t) cstack4.j) >> (((uint64_t) cstack6.i) & 0x3f));
        // New stack: 6
        // L2I; Stack: 6
        cstack4.i = (jint) cstack4.j;
        // New stack: 5
        // I2B; Stack: 5
        cstack4.i = (jint) (jbyte) cstack4.i;
        // New stack: 5
        // BASTORE; Stack: 5
        if (cstack2.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 2683LL)), -1); else { utils::bastore(env, (jarray) cstack2.l, cstack3.i, cstack4.i); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 2
        // DUP; Stack: 2
        cstack2 = cstack1;
        // New stack: 3
        // ICONST_3; Stack: 3
        cstack3.i = 3;
        // New stack: 4
        // LLOAD 2; Stack: 4
        cstack4.j = clocal2.j;
        // New stack: 6
        // BIPUSH 32; Stack: 6
        cstack6.i = (jint) 32;
        // New stack: 7
        // LUSHR; Stack: 7
        cstack4.j = (jlong) (((uint64_t) cstack4.j) >> (((uint64_t) cstack6.i) & 0x3f));
        // New stack: 6
        // L2I; Stack: 6
        cstack4.i = (jint) cstack4.j;
        // New stack: 5
        // I2B; Stack: 5
        cstack4.i = (jint) (jbyte) cstack4.i;
        // New stack: 5
        // BASTORE; Stack: 5
        if (cstack2.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 2683LL)), -1); else { utils::bastore(env, (jarray) cstack2.l, cstack3.i, cstack4.i); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 2
        // DUP; Stack: 2
        cstack2 = cstack1;
        // New stack: 3
        // ICONST_4; Stack: 3
        cstack3.i = 4;
        // New stack: 4
        // LLOAD 2; Stack: 4
        cstack4.j = clocal2.j;
        // New stack: 6
        // BIPUSH 24; Stack: 6
        cstack6.i = (jint) 24;
        // New stack: 7
        // LUSHR; Stack: 7
        cstack4.j = (jlong) (((uint64_t) cstack4.j) >> (((uint64_t) cstack6.i) & 0x3f));
        // New stack: 6
        // L2I; Stack: 6
        cstack4.i = (jint) cstack4.j;
        // New stack: 5
        // I2B; Stack: 5
        cstack4.i = (jint) (jbyte) cstack4.i;
        // New stack: 5
        // BASTORE; Stack: 5
        if (cstack2.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 2683LL)), -1); else { utils::bastore(env, (jarray) cstack2.l, cstack3.i, cstack4.i); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 2
        // DUP; Stack: 2
        cstack2 = cstack1;
        // New stack: 3
        // ICONST_5; Stack: 3
        cstack3.i = 5;
        // New stack: 4
        // LLOAD 2; Stack: 4
        cstack4.j = clocal2.j;
        // New stack: 6
        // BIPUSH 16; Stack: 6
        cstack6.i = (jint) 16;
        // New stack: 7
        // LUSHR; Stack: 7
        cstack4.j = (jlong) (((uint64_t) cstack4.j) >> (((uint64_t) cstack6.i) & 0x3f));
        // New stack: 6
        // L2I; Stack: 6
        cstack4.i = (jint) cstack4.j;
        // New stack: 5
        // I2B; Stack: 5
        cstack4.i = (jint) (jbyte) cstack4.i;
        // New stack: 5
        // BASTORE; Stack: 5
        if (cstack2.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 2683LL)), -1); else { utils::bastore(env, (jarray) cstack2.l, cstack3.i, cstack4.i); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 2
        // DUP; Stack: 2
        cstack2 = cstack1;
        // New stack: 3
        // BIPUSH 6; Stack: 3
        cstack3.i = (jint) 6;
        // New stack: 4
        // LLOAD 2; Stack: 4
        cstack4.j = clocal2.j;
        // New stack: 6
        // BIPUSH 8; Stack: 6
        cstack6.i = (jint) 8;
        // New stack: 7
        // LUSHR; Stack: 7
        cstack4.j = (jlong) (((uint64_t) cstack4.j) >> (((uint64_t) cstack6.i) & 0x3f));
        // New stack: 6
        // L2I; Stack: 6
        cstack4.i = (jint) cstack4.j;
        // New stack: 5
        // I2B; Stack: 5
        cstack4.i = (jint) (jbyte) cstack4.i;
        // New stack: 5
        // BASTORE; Stack: 5
        if (cstack2.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 2683LL)), -1); else { utils::bastore(env, (jarray) cstack2.l, cstack3.i, cstack4.i); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 2
        // DUP; Stack: 2
        cstack2 = cstack1;
        // New stack: 3
        // BIPUSH 7; Stack: 3
        cstack3.i = (jint) 7;
        // New stack: 4
        // LLOAD 2; Stack: 4
        cstack4.j = clocal2.j;
        // New stack: 6
        // L2I; Stack: 6
        cstack4.i = (jint) cstack4.j;
        // New stack: 5
        // I2B; Stack: 5
        cstack4.i = (jint) (jbyte) cstack4.i;
        // New stack: 5
        // BASTORE; Stack: 5
        if (cstack2.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 2683LL)), -1); else { utils::bastore(env, (jarray) cstack2.l, cstack3.i, cstack4.i); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 2
        // ALOAD 0; Stack: 2
        cstack2.l = clocal0.l; refs.insert(cstack2.l);
        // New stack: 3
        // SWAP; Stack: 3
        std::swap(cstack2, cstack1);
        // New stack: 3
        // INVOKEVIRTUAL javax/crypto/Cipher.doFinal([B)[B; Stack: 3
        if (!cclasses[27] || env->IsSameObject(cclasses[27], NULL)) { cclasses_mtx[27].lock(); if (!cclasses[27] || env->IsSameObject(cclasses[27], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[48]))) { cclasses[27] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[27].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[33]) { cmethods[33] = env->GetMethodID((cclasses[27]), ((char *)(string_pool + 2997LL)), ((char *)(string_pool + 3005LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 485LL)), -1); else { cstack1.l = env->CallObjectMethod(cstack1.l, (cmethods[33]), cstack2.l); refs.insert(cstack1.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 2
        // ASTORE 4; Stack: 2
        clocal4.l = cstack1.l; refs.insert(cstack1.l);
        // New stack: 1
        // ALOAD 4; Stack: 1
        cstack1.l = clocal4.l; refs.insert(cstack1.l);
        // New stack: 2
        // ICONST_0; Stack: 2
        cstack2.i = 0;
        // New stack: 3
        // BALOAD; Stack: 3
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 3092LL)), -1); else { cstack1.i = (jint) utils::baload(env, (jarray) cstack1.l, cstack2.i); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 2
        // I2L; Stack: 2
        cstack1.j = cstack1.i;
        // New stack: 3
        // LDC 255; Stack: 3
        cstack3.j = 255LL;
        // New stack: 5
        // LAND; Stack: 5
        cstack1.j = cstack1.j & cstack3.j;
        // New stack: 3
        // BIPUSH 56; Stack: 3
        cstack3.i = (jint) 56;
        // New stack: 4
        // LSHL; Stack: 4
        cstack1.j = cstack1.j << (0x3f & cstack3.i);
        // New stack: 3
        // ALOAD 4; Stack: 3
        cstack3.l = clocal4.l; refs.insert(cstack3.l);
        // New stack: 4
        // ICONST_1; Stack: 4
        cstack4.i = 1;
        // New stack: 5
        // BALOAD; Stack: 5
        if (cstack3.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 3092LL)), -1); else { cstack3.i = (jint) utils::baload(env, (jarray) cstack3.l, cstack4.i); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 4
        // I2L; Stack: 4
        cstack3.j = cstack3.i;
        // New stack: 5
        // LDC 255; Stack: 5
        cstack5.j = 255LL;
        // New stack: 7
        // LAND; Stack: 7
        cstack3.j = cstack3.j & cstack5.j;
        // New stack: 5
        // BIPUSH 48; Stack: 5
        cstack5.i = (jint) 48;
        // New stack: 6
        // LSHL; Stack: 6
        cstack3.j = cstack3.j << (0x3f & cstack5.i);
        // New stack: 5
        // LOR; Stack: 5
        cstack1.j = cstack1.j | cstack3.j;
        // New stack: 3
        // ALOAD 4; Stack: 3
        cstack3.l = clocal4.l; refs.insert(cstack3.l);
        // New stack: 4
        // ICONST_2; Stack: 4
        cstack4.i = 2;
        // New stack: 5
        // BALOAD; Stack: 5
        if (cstack3.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 3092LL)), -1); else { cstack3.i = (jint) utils::baload(env, (jarray) cstack3.l, cstack4.i); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 4
        // I2L; Stack: 4
        cstack3.j = cstack3.i;
        // New stack: 5
        // LDC 255; Stack: 5
        cstack5.j = 255LL;
        // New stack: 7
        // LAND; Stack: 7
        cstack3.j = cstack3.j & cstack5.j;
        // New stack: 5
        // BIPUSH 40; Stack: 5
        cstack5.i = (jint) 40;
        // New stack: 6
        // LSHL; Stack: 6
        cstack3.j = cstack3.j << (0x3f & cstack5.i);
        // New stack: 5
        // LOR; Stack: 5
        cstack1.j = cstack1.j | cstack3.j;
        // New stack: 3
        // ALOAD 4; Stack: 3
        cstack3.l = clocal4.l; refs.insert(cstack3.l);
        // New stack: 4
        // ICONST_3; Stack: 4
        cstack4.i = 3;
        // New stack: 5
        // BALOAD; Stack: 5
        if (cstack3.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 3092LL)), -1); else { cstack3.i = (jint) utils::baload(env, (jarray) cstack3.l, cstack4.i); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 4
        // I2L; Stack: 4
        cstack3.j = cstack3.i;
        // New stack: 5
        // LDC 255; Stack: 5
        cstack5.j = 255LL;
        // New stack: 7
        // LAND; Stack: 7
        cstack3.j = cstack3.j & cstack5.j;
        // New stack: 5
        // BIPUSH 32; Stack: 5
        cstack5.i = (jint) 32;
        // New stack: 6
        // LSHL; Stack: 6
        cstack3.j = cstack3.j << (0x3f & cstack5.i);
        // New stack: 5
        // LOR; Stack: 5
        cstack1.j = cstack1.j | cstack3.j;
        // New stack: 3
        // ALOAD 4; Stack: 3
        cstack3.l = clocal4.l; refs.insert(cstack3.l);
        // New stack: 4
        // ICONST_4; Stack: 4
        cstack4.i = 4;
        // New stack: 5
        // BALOAD; Stack: 5
        if (cstack3.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 3092LL)), -1); else { cstack3.i = (jint) utils::baload(env, (jarray) cstack3.l, cstack4.i); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 4
        // I2L; Stack: 4
        cstack3.j = cstack3.i;
        // New stack: 5
        // LDC 255; Stack: 5
        cstack5.j = 255LL;
        // New stack: 7
        // LAND; Stack: 7
        cstack3.j = cstack3.j & cstack5.j;
        // New stack: 5
        // BIPUSH 24; Stack: 5
        cstack5.i = (jint) 24;
        // New stack: 6
        // LSHL; Stack: 6
        cstack3.j = cstack3.j << (0x3f & cstack5.i);
        // New stack: 5
        // LOR; Stack: 5
        cstack1.j = cstack1.j | cstack3.j;
        // New stack: 3
        // ALOAD 4; Stack: 3
        cstack3.l = clocal4.l; refs.insert(cstack3.l);
        // New stack: 4
        // ICONST_5; Stack: 4
        cstack4.i = 5;
        // New stack: 5
        // BALOAD; Stack: 5
        if (cstack3.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 3092LL)), -1); else { cstack3.i = (jint) utils::baload(env, (jarray) cstack3.l, cstack4.i); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 4
        // I2L; Stack: 4
        cstack3.j = cstack3.i;
        // New stack: 5
        // LDC 255; Stack: 5
        cstack5.j = 255LL;
        // New stack: 7
        // LAND; Stack: 7
        cstack3.j = cstack3.j & cstack5.j;
        // New stack: 5
        // BIPUSH 16; Stack: 5
        cstack5.i = (jint) 16;
        // New stack: 6
        // LSHL; Stack: 6
        cstack3.j = cstack3.j << (0x3f & cstack5.i);
        // New stack: 5
        // LOR; Stack: 5
        cstack1.j = cstack1.j | cstack3.j;
        // New stack: 3
        // ALOAD 4; Stack: 3
        cstack3.l = clocal4.l; refs.insert(cstack3.l);
        // New stack: 4
        // BIPUSH 6; Stack: 4
        cstack4.i = (jint) 6;
        // New stack: 5
        // BALOAD; Stack: 5
        if (cstack3.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 3092LL)), -1); else { cstack3.i = (jint) utils::baload(env, (jarray) cstack3.l, cstack4.i); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 4
        // I2L; Stack: 4
        cstack3.j = cstack3.i;
        // New stack: 5
        // LDC 255; Stack: 5
        cstack5.j = 255LL;
        // New stack: 7
        // LAND; Stack: 7
        cstack3.j = cstack3.j & cstack5.j;
        // New stack: 5
        // BIPUSH 8; Stack: 5
        cstack5.i = (jint) 8;
        // New stack: 6
        // LSHL; Stack: 6
        cstack3.j = cstack3.j << (0x3f & cstack5.i);
        // New stack: 5
        // LOR; Stack: 5
        cstack1.j = cstack1.j | cstack3.j;
        // New stack: 3
        // ALOAD 4; Stack: 3
        cstack3.l = clocal4.l; refs.insert(cstack3.l);
        // New stack: 4
        // BIPUSH 7; Stack: 4
        cstack4.i = (jint) 7;
        // New stack: 5
        // BALOAD; Stack: 5
        if (cstack3.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 3092LL)), -1); else { cstack3.i = (jint) utils::baload(env, (jarray) cstack3.l, cstack4.i); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 4
        // I2L; Stack: 4
        cstack3.j = cstack3.i;
        // New stack: 5
        // LDC 255; Stack: 5
        cstack5.j = 255LL;
        // New stack: 7
        // LAND; Stack: 7
        cstack3.j = cstack3.j & cstack5.j;
        // New stack: 5
        // LOR; Stack: 5
        cstack1.j = cstack1.j | cstack3.j;
        // New stack: 3
        // DUP2_X1; Stack: 3
        cstack3 = cstack1; cstack4 = cstack2; cstack2 = cstack0; cstack1 = cstack4; cstack0 = cstack3;
        // New stack: 5
        // POP2; Stack: 5
        ;
        // New stack: 3
        // POP; Stack: 3
        ;
        // New stack: 2
        // GOTO L11; Stack: 2
        goto L11;
        // New stack: 2
        // LABEL L12; Stack: 2
        L12: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 2
        // FRAME FULL L: [javax/crypto/Cipher, 1, 4, [B, 4, javax/crypto/Cipher, 1, 1, 1, java/lang/String, 1, 1, [Ljava/lang/String;, [B] S: []; Stack: 2
        refs.erase(clocal0.l); refs.erase(clocal4.l); refs.erase(clocal7.l); refs.erase(clocal11.l); refs.erase(clocal14.l); refs.erase(clocal15.l); 
        utils::clear_refs(env, refs);
        // New stack: 0
        // RETURN; Stack: 0
        return;
        // New stack: 0
        return (void) 0;
    }
    
    // a([B)Ljava/lang/String;
    jobject JNICALL __ngen_native_a5(JNIEnv *env, jclass clazz, jarray arg0) {
        jobject classloader = utils::get_classloader_from_class(env, clazz);
        if (env->ExceptionCheck()) { return (jobject) 0; }
        if (classloader == nullptr) { env->FatalError(((char *)(string_pool + 32LL))); return (jobject) 0; }
    
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
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 766LL)), -1); else cstack0.i = env->GetArrayLength((jarray) cstack0.l); 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // ISTORE 2; Stack: 2
        clocal2.i = cstack1.i;
        // New stack: 1
        // NEWARRAY 5; Stack: 1
        if (cstack0.i < 0) utils::throw_re(env, ((char *)(string_pool + 214LL)), ((char *)(string_pool + 3063LL)), -1); else { cstack0.l = env->NewCharArray(cstack0.i); refs.insert(cstack0.l); } 
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
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 3092LL)), -1); else { cstack1.i = (jint) utils::baload(env, (jarray) cstack1.l, cstack2.i); } 
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
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 3103LL)), -1); else { jchar temp = (jchar) cstack2.i; env->SetCharArrayRegion((jcharArray) cstack0.l, cstack1.i, 1, &temp); } 
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
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 3092LL)), -1); else { cstack0.i = (jint) utils::baload(env, (jarray) cstack0.l, cstack1.i); } 
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
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 3103LL)), -1); else { jchar temp = (jchar) cstack2.i; env->SetCharArrayRegion((jcharArray) cstack0.l, cstack1.i, 1, &temp); } 
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
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 3092LL)), -1); else { cstack0.i = (jint) utils::baload(env, (jarray) cstack0.l, cstack1.i); } 
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
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 3092LL)), -1); else { cstack0.i = (jint) utils::baload(env, (jarray) cstack0.l, cstack1.i); } 
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
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 3103LL)), -1); else { jchar temp = (jchar) cstack2.i; env->SetCharArrayRegion((jcharArray) cstack0.l, cstack1.i, 1, &temp); } 
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
        if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { cclasses_mtx[13].lock(); if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[16]))) { cclasses[13] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[13].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (jobject obj = env->AllocObject((cclasses[13]))) { cstack0.l = obj; refs.insert(obj); } 
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
        if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { cclasses_mtx[13].lock(); if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[16]))) { cclasses[13] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[13].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[36]) { cmethods[36] = env->GetMethodID((cclasses[13]), ((char *)(string_pool + 628LL)), ((char *)(string_pool + 3115LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 660LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[13]), (cmethods[36]), cstack2.l, cstack3.i, cstack4.i); 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // ARETURN; Stack: 1
        return (jobject) cstack0.l;
        // New stack: 0
        return (jobject) 0;
    }
    
    // a(IJ)Ljava/lang/String;
    jobject JNICALL __ngen_native_a6(JNIEnv *env, jclass clazz, jint arg0, jlong arg1) {
        jobject classloader = utils::get_classloader_from_class(env, clazz);
        if (env->ExceptionCheck()) { return (jobject) 0; }
        if (classloader == nullptr) { env->FatalError(((char *)(string_pool + 32LL))); return (jobject) 0; }
    
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
        // SIPUSH 18368; Stack: 1
        cstack1.i = (jint) 18368;
        // New stack: 2
        // IXOR; Stack: 2
        cstack0.i = cstack0.i ^ cstack1.i;
        // New stack: 1
        // ISTORE 5; Stack: 1
        clocal5.i = cstack0.i;
        // New stack: 0
        // GETSTATIC dev/sakura/client/Mahiro_i3.c [Ljava/lang/String;; Stack: 0
        if (!cclasses[3]  || env->IsSameObject(cclasses[3], NULL)) { cclasses_mtx[3].lock(); if (!cclasses[3] || env->IsSameObject(cclasses[3], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[3] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[3].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cfields[4]) { cfields[4] = env->GetStaticFieldID((cclasses[3]), ((char *)(string_pool + 2963LL)), ((char *)(string_pool + 2943LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } cstack0.l = env->GetStaticObjectField((cclasses[3]), (cfields[4])); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // ILOAD 5; Stack: 1
        cstack1.i = clocal5.i;
        // New stack: 2
        // AALOAD; Stack: 2
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 83LL)), -1); else { cstack0.l = env->GetObjectArrayElement((jobjectArray) cstack0.l, cstack1.i); refs.insert(cstack0.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // IFNONNULL L4; Stack: 1
        if (!env->IsSameObject(cstack0.l, nullptr)) goto L4;
        // New stack: 0
        // LABEL L1; Stack: 0
        L1: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 0
        // INVOKESTATIC java/lang/Thread.currentThread()Ljava/lang/Thread;; Stack: 0
        if (!cclasses[31] || env->IsSameObject(cclasses[31], NULL)) { cclasses_mtx[31].lock(); if (!cclasses[31] || env->IsSameObject(cclasses[31], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[56]))) { cclasses[31] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[31].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[37]) { cmethods[37] = env->GetStaticMethodID((cclasses[31]), ((char *)(string_pool + 3146LL)), ((char *)(string_pool + 3160LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack0.l = env->CallStaticObjectMethod((cclasses[31]), (cmethods[37])); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 1
        // INVOKEVIRTUAL java/lang/Thread.threadId()J; Stack: 1
        if (!cclasses[31] || env->IsSameObject(cclasses[31], NULL)) { cclasses_mtx[31].lock(); if (!cclasses[31] || env->IsSameObject(cclasses[31], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[56]))) { cclasses[31] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[31].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[38]) { cmethods[38] = env->GetMethodID((cclasses[31]), ((char *)(string_pool + 3181LL)), ((char *)(string_pool + 164LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 168LL)), -1); else cstack0.j = env->CallLongMethod(cstack0.l, (cmethods[38])); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 2
        // INVOKESTATIC java/lang/Long.valueOf(J)Ljava/lang/Long;; Stack: 2
        if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { cclasses_mtx[2].lock(); if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[2]))) { cclasses[2] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[2].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[6]) { cmethods[6] = env->GetStaticMethodID((cclasses[2]), ((char *)(string_pool + 1629LL)), ((char *)(string_pool + 1637LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack0.l = env->CallStaticObjectMethod((cclasses[2]), (cmethods[6]), cstack0.j); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 1
        // ASTORE 3; Stack: 1
        clocal3.l = cstack0.l; refs.insert(cstack0.l);
        // New stack: 0
        // GETSTATIC dev/sakura/client/Mahiro_i3.d Ljava/util/Map;; Stack: 0
        if (!cclasses[3]  || env->IsSameObject(cclasses[3], NULL)) { cclasses_mtx[3].lock(); if (!cclasses[3] || env->IsSameObject(cclasses[3], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[3] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[3].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cfields[2]) { cfields[2] = env->GetStaticFieldID((cclasses[3]), ((char *)(string_pool + 2530LL)), ((char *)(string_pool + 2532LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack0.l = env->GetStaticObjectField((cclasses[3]), (cfields[2])); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 1
        // ALOAD 3; Stack: 1
        cstack1.l = clocal3.l; refs.insert(cstack1.l);
        // New stack: 2
        // INVOKEINTERFACE java/util/Map.get(Ljava/lang/Object;)Ljava/lang/Object;; Stack: 2
        if (!cclasses[32] || env->IsSameObject(cclasses[32], NULL)) { cclasses_mtx[32].lock(); if (!cclasses[32] || env->IsSameObject(cclasses[32], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[57]))) { cclasses[32] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[32].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[39]) { cmethods[39] = env->GetMethodID((cclasses[32]), ((char *)(string_pool + 3190LL)), ((char *)(string_pool + 3194LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 571LL)), -1); else { cstack0.l = env->CallObjectMethod(cstack0.l, (cmethods[39]), cstack1.l); refs.insert(cstack0.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 1
        // CHECKCAST [Ljava/lang/Object;; Stack: 1
        if (!cclasses[33] || env->IsSameObject(cclasses[33], NULL)) { cclasses_mtx[33].lock(); if (!cclasses[33] || env->IsSameObject(cclasses[33], NULL)) { if (jclass clazz = env->FindClass(((char *)(string_pool + 3233LL)))) { cclasses[33] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[33].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack0.l != nullptr && !env->IsInstanceOf(cstack0.l, (cclasses[33]))) { utils::throw_re(env, ((char *)(string_pool + 94LL)), (std::string(((char *)(string_pool + 123LL))) + std::string(((char *)(string_pool + 3233LL)))).c_str(), -1); 
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
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack0.i < 0) utils::throw_re(env, ((char *)(string_pool + 214LL)), ((char *)(string_pool + 251LL)), -1); else { cstack0.l = env->NewObjectArray(cstack0.i, (cclasses[4]), nullptr); refs.insert(cstack0.l); } 
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
        cstack2.l = (cstrings[47]);
        // New stack: 3
        // INVOKESTATIC javax/crypto/Cipher.getInstance(Ljava/lang/String;)Ljavax/crypto/Cipher;; Stack: 3
        if (!cclasses[27] || env->IsSameObject(cclasses[27], NULL)) { cclasses_mtx[27].lock(); if (!cclasses[27] || env->IsSameObject(cclasses[27], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[48]))) { cclasses[27] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[27].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[23]) { cmethods[23] = env->GetStaticMethodID((cclasses[27]), ((char *)(string_pool + 2548LL)), ((char *)(string_pool + 2560LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack2.l = env->CallStaticObjectMethod((cclasses[27]), (cmethods[23]), cstack2.l); refs.insert(cstack2.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 3
        // AASTORE; Stack: 3
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 1657LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack0.l, cstack1.i, cstack2.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 0
        // ALOAD 4; Stack: 0
        cstack0.l = clocal4.l; refs.insert(cstack0.l);
        // New stack: 1
        // ICONST_1; Stack: 1
        cstack1.i = 1;
        // New stack: 2
        // LDC DES; Stack: 2
        cstack2.l = (cstrings[49]);
        // New stack: 3
        // INVOKESTATIC javax/crypto/SecretKeyFactory.getInstance(Ljava/lang/String;)Ljavax/crypto/SecretKeyFactory;; Stack: 3
        if (!cclasses[28] || env->IsSameObject(cclasses[28], NULL)) { cclasses_mtx[28].lock(); if (!cclasses[28] || env->IsSameObject(cclasses[28], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[50]))) { cclasses[28] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[28].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[24]) { cmethods[24] = env->GetStaticMethodID((cclasses[28]), ((char *)(string_pool + 2548LL)), ((char *)(string_pool + 2602LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack2.l = env->CallStaticObjectMethod((cclasses[28]), (cmethods[24]), cstack2.l); refs.insert(cstack2.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 3
        // AASTORE; Stack: 3
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 1657LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack0.l, cstack1.i, cstack2.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 0
        // ALOAD 4; Stack: 0
        cstack0.l = clocal4.l; refs.insert(cstack0.l);
        // New stack: 1
        // ICONST_2; Stack: 1
        cstack1.i = 2;
        // New stack: 2
        // NEW javax/crypto/spec/IvParameterSpec; Stack: 2
        if (!cclasses[30] || env->IsSameObject(cclasses[30], NULL)) { cclasses_mtx[30].lock(); if (!cclasses[30] || env->IsSameObject(cclasses[30], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[52]))) { cclasses[30] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[30].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (jobject obj = env->AllocObject((cclasses[30]))) { cstack2.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 3
        // DUP; Stack: 3
        cstack3 = cstack2;
        // New stack: 4
        // BIPUSH 8; Stack: 4
        cstack4.i = (jint) 8;
        // New stack: 5
        // NEWARRAY 8; Stack: 5
        if (cstack4.i < 0) utils::throw_re(env, ((char *)(string_pool + 214LL)), ((char *)(string_pool + 2654LL)), -1); else { cstack4.l = env->NewByteArray(cstack4.i); refs.insert(cstack4.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 5
        // INVOKESPECIAL javax/crypto/spec/IvParameterSpec.<init>([B)V; Stack: 5
        if (!cclasses[30] || env->IsSameObject(cclasses[30], NULL)) { cclasses_mtx[30].lock(); if (!cclasses[30] || env->IsSameObject(cclasses[30], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[52]))) { cclasses[30] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[30].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[27]) { cmethods[27] = env->GetMethodID((cclasses[30]), ((char *)(string_pool + 628LL)), ((char *)(string_pool + 2695LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack3.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 660LL)), -1); else env->CallNonvirtualVoidMethod(cstack3.l, (cclasses[30]), (cmethods[27]), cstack4.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 3
        // AASTORE; Stack: 3
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 1657LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack0.l, cstack1.i, cstack2.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 0
        // GETSTATIC dev/sakura/client/Mahiro_i3.d Ljava/util/Map;; Stack: 0
        if (!cclasses[3]  || env->IsSameObject(cclasses[3], NULL)) { cclasses_mtx[3].lock(); if (!cclasses[3] || env->IsSameObject(cclasses[3], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[3] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[3].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cfields[2]) { cfields[2] = env->GetStaticFieldID((cclasses[3]), ((char *)(string_pool + 2530LL)), ((char *)(string_pool + 2532LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack0.l = env->GetStaticObjectField((cclasses[3]), (cfields[2])); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 1
        // ALOAD 3; Stack: 1
        cstack1.l = clocal3.l; refs.insert(cstack1.l);
        // New stack: 2
        // ALOAD 4; Stack: 2
        cstack2.l = clocal4.l; refs.insert(cstack2.l);
        // New stack: 3
        // INVOKEINTERFACE java/util/Map.put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;; Stack: 3
        if (!cclasses[32] || env->IsSameObject(cclasses[32], NULL)) { cclasses_mtx[32].lock(); if (!cclasses[32] || env->IsSameObject(cclasses[32], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[57]))) { cclasses[32] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[32].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[40]) { cmethods[40] = env->GetMethodID((cclasses[32]), ((char *)(string_pool + 3253LL)), ((char *)(string_pool + 3257LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 571LL)), -1); else { cstack0.l = env->CallObjectMethod(cstack0.l, (cmethods[40]), cstack1.l, cstack2.l); refs.insert(cstack0.l); } 
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
        if (!cclasses[34] || env->IsSameObject(cclasses[34], NULL)) { cclasses_mtx[34].lock(); if (!cclasses[34] || env->IsSameObject(cclasses[34], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[58]))) { cclasses[34] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[34].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (jobject obj = env->AllocObject((cclasses[34]))) { cstack0.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // LDC dev/sakura/client/Mahiro_i3; Stack: 2
        cstack2.l = (cstrings[59]);
        // New stack: 3
        // ALOAD 9; Stack: 3
        cstack3.l = clocal9.l; refs.insert(cstack3.l);
        // New stack: 4
        // INVOKESPECIAL java/lang/RuntimeException.<init>(Ljava/lang/String;Ljava/lang/Throwable;)V; Stack: 4
        if (!cclasses[34] || env->IsSameObject(cclasses[34], NULL)) { cclasses_mtx[34].lock(); if (!cclasses[34] || env->IsSameObject(cclasses[34], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[58]))) { cclasses[34] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[34].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[41]) { cmethods[41] = env->GetMethodID((cclasses[34]), ((char *)(string_pool + 628LL)), ((char *)(string_pool + 3314LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 660LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[34]), (cmethods[41]), cstack2.l, cstack3.l); 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 683LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
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
        if (cstack0.i < 0) utils::throw_re(env, ((char *)(string_pool + 214LL)), ((char *)(string_pool + 2654LL)), -1); else { cstack0.l = env->NewByteArray(cstack0.i); refs.insert(cstack0.l); } 
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
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 2683LL)), -1); else { utils::bastore(env, (jarray) cstack0.l, cstack1.i, cstack2.i); } 
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
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 2683LL)), -1); else { utils::bastore(env, (jarray) cstack0.l, cstack1.i, cstack2.i); } 
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
        if (!cclasses[29] || env->IsSameObject(cclasses[29], NULL)) { cclasses_mtx[29].lock(); if (!cclasses[29] || env->IsSameObject(cclasses[29], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[51]))) { cclasses[29] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[29].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (jobject obj = env->AllocObject((cclasses[29]))) { cstack0.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // ALOAD 6; Stack: 2
        cstack2.l = clocal6.l; refs.insert(cstack2.l);
        // New stack: 3
        // INVOKESPECIAL javax/crypto/spec/DESKeySpec.<init>([B)V; Stack: 3
        if (!cclasses[29] || env->IsSameObject(cclasses[29], NULL)) { cclasses_mtx[29].lock(); if (!cclasses[29] || env->IsSameObject(cclasses[29], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[51]))) { cclasses[29] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[29].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[25]) { cmethods[25] = env->GetMethodID((cclasses[29]), ((char *)(string_pool + 628LL)), ((char *)(string_pool + 2695LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 660LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[29]), (cmethods[25]), cstack2.l); 
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
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 83LL)), -1); else { cstack0.l = env->GetObjectArrayElement((jobjectArray) cstack0.l, cstack1.i); refs.insert(cstack0.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // CHECKCAST javax/crypto/SecretKeyFactory; Stack: 1
        if (!cclasses[28] || env->IsSameObject(cclasses[28], NULL)) { cclasses_mtx[28].lock(); if (!cclasses[28] || env->IsSameObject(cclasses[28], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[50]))) { cclasses[28] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[28].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (cstack0.l != nullptr && !env->IsInstanceOf(cstack0.l, (cclasses[28]))) { utils::throw_re(env, ((char *)(string_pool + 94LL)), (std::string(((char *)(string_pool + 123LL))) + std::string(((char *)(string_pool + 3357LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (jobject) 0; } } 
        // New stack: 1
        // ALOAD 7; Stack: 1
        cstack1.l = clocal7.l; refs.insert(cstack1.l);
        // New stack: 2
        // INVOKEVIRTUAL javax/crypto/SecretKeyFactory.generateSecret(Ljava/security/spec/KeySpec;)Ljavax/crypto/SecretKey;; Stack: 2
        if (!cclasses[28] || env->IsSameObject(cclasses[28], NULL)) { cclasses_mtx[28].lock(); if (!cclasses[28] || env->IsSameObject(cclasses[28], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[50]))) { cclasses[28] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[28].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[26]) { cmethods[26] = env->GetMethodID((cclasses[28]), ((char *)(string_pool + 2701LL)), ((char *)(string_pool + 2716LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 485LL)), -1); else { cstack0.l = env->CallObjectMethod(cstack0.l, (cmethods[26]), cstack1.l); refs.insert(cstack0.l); } 
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
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 83LL)), -1); else { cstack0.l = env->GetObjectArrayElement((jobjectArray) cstack0.l, cstack1.i); refs.insert(cstack0.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // CHECKCAST javax/crypto/Cipher; Stack: 1
        if (!cclasses[27] || env->IsSameObject(cclasses[27], NULL)) { cclasses_mtx[27].lock(); if (!cclasses[27] || env->IsSameObject(cclasses[27], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[48]))) { cclasses[27] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[27].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (cstack0.l != nullptr && !env->IsInstanceOf(cstack0.l, (cclasses[27]))) { utils::throw_re(env, ((char *)(string_pool + 94LL)), (std::string(((char *)(string_pool + 123LL))) + std::string(((char *)(string_pool + 3387LL)))).c_str(), -1); 
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
        if (cstack3.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 83LL)), -1); else { cstack3.l = env->GetObjectArrayElement((jobjectArray) cstack3.l, cstack4.i); refs.insert(cstack3.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 4
        // CHECKCAST javax/crypto/spec/IvParameterSpec; Stack: 4
        if (!cclasses[30] || env->IsSameObject(cclasses[30], NULL)) { cclasses_mtx[30].lock(); if (!cclasses[30] || env->IsSameObject(cclasses[30], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[52]))) { cclasses[30] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[30].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (cstack3.l != nullptr && !env->IsInstanceOf(cstack3.l, (cclasses[30]))) { utils::throw_re(env, ((char *)(string_pool + 94LL)), (std::string(((char *)(string_pool + 123LL))) + std::string(((char *)(string_pool + 3407LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (jobject) 0; } } 
        // New stack: 4
        // INVOKEVIRTUAL javax/crypto/Cipher.init(ILjava/security/Key;Ljava/security/spec/AlgorithmParameterSpec;)V; Stack: 4
        if (!cclasses[27] || env->IsSameObject(cclasses[27], NULL)) { cclasses_mtx[27].lock(); if (!cclasses[27] || env->IsSameObject(cclasses[27], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[48]))) { cclasses[27] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[27].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[28]) { cmethods[28] = env->GetMethodID((cclasses[27]), ((char *)(string_pool + 2771LL)), ((char *)(string_pool + 2776LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 2843LL)), -1); else env->CallVoidMethod(cstack0.l, (cmethods[28]), cstack1.i, cstack2.l, cstack3.l); 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 0
        // GETSTATIC dev/sakura/client/Mahiro_i3.b [Ljava/lang/String;; Stack: 0
        if (!cclasses[3]  || env->IsSameObject(cclasses[3], NULL)) { cclasses_mtx[3].lock(); if (!cclasses[3] || env->IsSameObject(cclasses[3], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[3] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[3].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cfields[3]) { cfields[3] = env->GetStaticFieldID((cclasses[3]), ((char *)(string_pool + 2941LL)), ((char *)(string_pool + 2943LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } cstack0.l = env->GetStaticObjectField((cclasses[3]), (cfields[3])); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // ILOAD 5; Stack: 1
        cstack1.i = clocal5.i;
        // New stack: 2
        // AALOAD; Stack: 2
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 83LL)), -1); else { cstack0.l = env->GetObjectArrayElement((jobjectArray) cstack0.l, cstack1.i); refs.insert(cstack0.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // LDC ISO-8859-1; Stack: 1
        cstack1.l = (cstrings[54]);
        // New stack: 2
        // INVOKEVIRTUAL java/lang/String.getBytes(Ljava/lang/String;)[B; Stack: 2
        if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { cclasses_mtx[13].lock(); if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[16]))) { cclasses[13] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[13].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[32]) { cmethods[32] = env->GetMethodID((cclasses[13]), ((char *)(string_pool + 2965LL)), ((char *)(string_pool + 2974LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 485LL)), -1); else { cstack0.l = env->CallObjectMethod(cstack0.l, (cmethods[32]), cstack1.l); refs.insert(cstack0.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // ASTORE 9; Stack: 1
        clocal9.l = cstack0.l; refs.insert(cstack0.l);
        // New stack: 0
        // GETSTATIC dev/sakura/client/Mahiro_i3.c [Ljava/lang/String;; Stack: 0
        if (!cclasses[3]  || env->IsSameObject(cclasses[3], NULL)) { cclasses_mtx[3].lock(); if (!cclasses[3] || env->IsSameObject(cclasses[3], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[3] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[3].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cfields[4]) { cfields[4] = env->GetStaticFieldID((cclasses[3]), ((char *)(string_pool + 2963LL)), ((char *)(string_pool + 2943LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } cstack0.l = env->GetStaticObjectField((cclasses[3]), (cfields[4])); refs.insert(cstack0.l); 
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
        if (cstack2.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 83LL)), -1); else { cstack2.l = env->GetObjectArrayElement((jobjectArray) cstack2.l, cstack3.i); refs.insert(cstack2.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 3
        // CHECKCAST javax/crypto/Cipher; Stack: 3
        if (!cclasses[27] || env->IsSameObject(cclasses[27], NULL)) { cclasses_mtx[27].lock(); if (!cclasses[27] || env->IsSameObject(cclasses[27], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[48]))) { cclasses[27] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[27].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (cstack2.l != nullptr && !env->IsInstanceOf(cstack2.l, (cclasses[27]))) { utils::throw_re(env, ((char *)(string_pool + 94LL)), (std::string(((char *)(string_pool + 123LL))) + std::string(((char *)(string_pool + 3387LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (jobject) 0; } } 
        // New stack: 3
        // ALOAD 9; Stack: 3
        cstack3.l = clocal9.l; refs.insert(cstack3.l);
        // New stack: 4
        // INVOKEVIRTUAL javax/crypto/Cipher.doFinal([B)[B; Stack: 4
        if (!cclasses[27] || env->IsSameObject(cclasses[27], NULL)) { cclasses_mtx[27].lock(); if (!cclasses[27] || env->IsSameObject(cclasses[27], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[48]))) { cclasses[27] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[27].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[33]) { cmethods[33] = env->GetMethodID((cclasses[27]), ((char *)(string_pool + 2997LL)), ((char *)(string_pool + 3005LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack2.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 485LL)), -1); else { cstack2.l = env->CallObjectMethod(cstack2.l, (cmethods[33]), cstack3.l); refs.insert(cstack2.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 3
        // INVOKESTATIC dev/sakura/client/Mahiro_i3.a([B)Ljava/lang/String;; Stack: 3
        if (!cclasses[3] || env->IsSameObject(cclasses[3], NULL)) { cclasses_mtx[3].lock(); if (!cclasses[3] || env->IsSameObject(cclasses[3], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[3] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[3].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[34]) { cmethods[34] = env->GetStaticMethodID((cclasses[3]), ((char *)(string_pool + 210LL)), ((char *)(string_pool + 3012LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } cstack2.l = env->CallStaticObjectMethod((cclasses[3]), (cmethods[34]), cstack2.l); refs.insert(cstack2.l); 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 3
        // AASTORE; Stack: 3
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 1657LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack0.l, cstack1.i, cstack2.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 0
        // LABEL L4; Stack: 0
        L4: if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 0
        // FRAME FULL L: [1, 4, 0, 0, 1] S: []; Stack: 0
        utils::clear_refs(env, refs);
        // New stack: 0
        // GETSTATIC dev/sakura/client/Mahiro_i3.c [Ljava/lang/String;; Stack: 0
        if (!cclasses[3]  || env->IsSameObject(cclasses[3], NULL)) { cclasses_mtx[3].lock(); if (!cclasses[3] || env->IsSameObject(cclasses[3], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[3] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[3].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cfields[4]) { cfields[4] = env->GetStaticFieldID((cclasses[3]), ((char *)(string_pool + 2963LL)), ((char *)(string_pool + 2943LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } cstack0.l = env->GetStaticObjectField((cclasses[3]), (cfields[4])); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // ILOAD 5; Stack: 1
        cstack1.i = clocal5.i;
        // New stack: 2
        // AALOAD; Stack: 2
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 83LL)), -1); else { cstack0.l = env->GetObjectArrayElement((jobjectArray) cstack0.l, cstack1.i); refs.insert(cstack0.l); } 
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
    jobject JNICALL __ngen_native_a7(JNIEnv *env, jclass clazz, jobject arg0, jobject arg1, jobject arg2, jarray arg3) {
        jobject classloader = utils::get_classloader_from_class(env, clazz);
        if (env->ExceptionCheck()) { return (jobject) 0; }
        if (classloader == nullptr) { env->FatalError(((char *)(string_pool + 32LL))); return (jobject) 0; }
    
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
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 83LL)), -1); else { cstack0.l = env->GetObjectArrayElement((jobjectArray) cstack0.l, cstack1.i); refs.insert(cstack0.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // CHECKCAST java/lang/Integer; Stack: 1
        if (!cclasses[22] || env->IsSameObject(cclasses[22], NULL)) { cclasses_mtx[22].lock(); if (!cclasses[22] || env->IsSameObject(cclasses[22], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[41]))) { cclasses[22] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[22].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (cstack0.l != nullptr && !env->IsInstanceOf(cstack0.l, (cclasses[22]))) { utils::throw_re(env, ((char *)(string_pool + 94LL)), (std::string(((char *)(string_pool + 123LL))) + std::string(((char *)(string_pool + 1023LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (jobject) 0; } } 
        // New stack: 1
        // INVOKEVIRTUAL java/lang/Integer.intValue()I; Stack: 1
        if (!cclasses[22] || env->IsSameObject(cclasses[22], NULL)) { cclasses_mtx[22].lock(); if (!cclasses[22] || env->IsSameObject(cclasses[22], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[41]))) { cclasses[22] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[22].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[42]) { cmethods[42] = env->GetMethodID((cclasses[22]), ((char *)(string_pool + 2176LL)), ((char *)(string_pool + 2185LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 2189LL)), -1); else cstack0.i = env->CallIntMethod(cstack0.l, (cmethods[42])); 
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
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 83LL)), -1); else { cstack0.l = env->GetObjectArrayElement((jobjectArray) cstack0.l, cstack1.i); refs.insert(cstack0.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // CHECKCAST java/lang/Long; Stack: 1
        if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { cclasses_mtx[2].lock(); if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[2]))) { cclasses[2] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[2].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (cstack0.l != nullptr && !env->IsInstanceOf(cstack0.l, (cclasses[2]))) { utils::throw_re(env, ((char *)(string_pool + 94LL)), (std::string(((char *)(string_pool + 123LL))) + std::string(((char *)(string_pool + 139LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (jobject) 0; } } 
        // New stack: 1
        // INVOKEVIRTUAL java/lang/Long.longValue()J; Stack: 1
        if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { cclasses_mtx[2].lock(); if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[2]))) { cclasses[2] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[2].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[0]) { cmethods[0] = env->GetMethodID((cclasses[2]), ((char *)(string_pool + 154LL)), ((char *)(string_pool + 164LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 168LL)), -1); else cstack0.j = env->CallLongMethod(cstack0.l, (cmethods[0])); 
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
        // INVOKESTATIC dev/sakura/client/Mahiro_i3.a(IJ)Ljava/lang/String;; Stack: 3
        if (!cclasses[3] || env->IsSameObject(cclasses[3], NULL)) { cclasses_mtx[3].lock(); if (!cclasses[3] || env->IsSameObject(cclasses[3], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[3] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[3].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[43]) { cmethods[43] = env->GetStaticMethodID((cclasses[3]), ((char *)(string_pool + 210LL)), ((char *)(string_pool + 3123LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } cstack0.l = env->CallStaticObjectMethod((cclasses[3]), (cmethods[43]), cstack0.i, cstack1.j); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // ASTORE 7; Stack: 1
        clocal7.l = cstack0.l; refs.insert(cstack0.l);
        // New stack: 0
        // LDC Ljava/lang/String;; Stack: 0
        if (!cclasses[35] || env->IsSameObject(cclasses[35], NULL)) { cclasses_mtx[35].lock(); if (!cclasses[35] || env->IsSameObject(cclasses[35], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[16]))) { cclasses[35] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[35].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } cstack0.l = (cclasses[35]);
        // New stack: 1
        // ALOAD 7; Stack: 1
        cstack1.l = clocal7.l; refs.insert(cstack1.l);
        // New stack: 2
        // INVOKESTATIC java/lang/invoke/MethodHandles.constant(Ljava/lang/Class;Ljava/lang/Object;)Ljava/lang/invoke/MethodHandle;; Stack: 2
        if (!cclasses[23] || env->IsSameObject(cclasses[23], NULL)) { cclasses_mtx[23].lock(); if (!cclasses[23] || env->IsSameObject(cclasses[23], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[43]))) { cclasses[23] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[23].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[44]) { cmethods[44] = env->GetStaticMethodID((cclasses[23]), ((char *)(string_pool + 3572LL)), ((char *)(string_pool + 3581LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } cstack0.l = env->CallStaticObjectMethod((cclasses[23]), (cmethods[44]), cstack0.l, cstack1.l); refs.insert(cstack0.l); 
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
        if (!cclasses[20] || env->IsSameObject(cclasses[20], NULL)) { cclasses_mtx[20].lock(); if (!cclasses[20] || env->IsSameObject(cclasses[20], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[36]))) { cclasses[20] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[20].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (cstack3.i < 0) utils::throw_re(env, ((char *)(string_pool + 214LL)), ((char *)(string_pool + 251LL)), -1); else { cstack3.l = env->NewObjectArray(cstack3.i, (cclasses[20]), nullptr); refs.insert(cstack3.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 4
        // DUP; Stack: 4
        cstack4 = cstack3;
        // New stack: 5
        // ICONST_0; Stack: 5
        cstack5.i = 0;
        // New stack: 6
        // GETSTATIC java/lang/Integer.TYPE Ljava/lang/Class;; Stack: 6
        if (!cclasses[22]  || env->IsSameObject(cclasses[22], NULL)) { cclasses_mtx[22].lock(); if (!cclasses[22] || env->IsSameObject(cclasses[22], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[41]))) { cclasses[22] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[22].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cfields[5]) { cfields[5] = env->GetStaticFieldID((cclasses[22]), ((char *)(string_pool + 3650LL)), ((char *)(string_pool + 3655LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } cstack6.l = env->GetStaticObjectField((cclasses[22]), (cfields[5])); refs.insert(cstack6.l); 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 7
        // AASTORE; Stack: 7
        if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 1657LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack4.l, cstack5.i, cstack6.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 4
        // DUP; Stack: 4
        cstack4 = cstack3;
        // New stack: 5
        // ICONST_1; Stack: 5
        cstack5.i = 1;
        // New stack: 6
        // GETSTATIC java/lang/Long.TYPE Ljava/lang/Class;; Stack: 6
        if (!cclasses[2]  || env->IsSameObject(cclasses[2], NULL)) { cclasses_mtx[2].lock(); if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[2]))) { cclasses[2] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[2].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cfields[6]) { cfields[6] = env->GetStaticFieldID((cclasses[2]), ((char *)(string_pool + 3650LL)), ((char *)(string_pool + 3655LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } cstack6.l = env->GetStaticObjectField((cclasses[2]), (cfields[6])); refs.insert(cstack6.l); 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 7
        // AASTORE; Stack: 7
        if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 1657LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack4.l, cstack5.i, cstack6.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 4
        // INVOKESTATIC java/lang/invoke/MethodHandles.dropArguments(Ljava/lang/invoke/MethodHandle;I[Ljava/lang/Class;)Ljava/lang/invoke/MethodHandle;; Stack: 4
        if (!cclasses[23] || env->IsSameObject(cclasses[23], NULL)) { cclasses_mtx[23].lock(); if (!cclasses[23] || env->IsSameObject(cclasses[23], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[43]))) { cclasses[23] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[23].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[45]) { cmethods[45] = env->GetStaticMethodID((cclasses[23]), ((char *)(string_pool + 3673LL)), ((char *)(string_pool + 3687LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } cstack1.l = env->CallStaticObjectMethod((cclasses[23]), (cmethods[45]), cstack1.l, cstack2.i, cstack3.l); refs.insert(cstack1.l); 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 2
        // INVOKEVIRTUAL java/lang/invoke/MutableCallSite.setTarget(Ljava/lang/invoke/MethodHandle;)V; Stack: 2
        if (!cclasses[36] || env->IsSameObject(cclasses[36], NULL)) { cclasses_mtx[36].lock(); if (!cclasses[36] || env->IsSameObject(cclasses[36], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[60]))) { cclasses[36] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[36].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[46]) { cmethods[46] = env->GetMethodID((cclasses[36]), ((char *)(string_pool + 3771LL)), ((char *)(string_pool + 3781LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 2843LL)), -1); else env->CallVoidMethod(cstack0.l, (cmethods[46]), cstack1.l); 
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
    jobject JNICALL __ngen_native_a8(JNIEnv *env, jclass clazz, jobject arg0, jobject arg1, jobject arg2) {
        jobject classloader = utils::get_classloader_from_class(env, clazz);
        if (env->ExceptionCheck()) { return (jobject) 0; }
        if (classloader == nullptr) { env->FatalError(((char *)(string_pool + 32LL))); return (jobject) 0; }
    
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
        if (!cclasses[36] || env->IsSameObject(cclasses[36], NULL)) { cclasses_mtx[36].lock(); if (!cclasses[36] || env->IsSameObject(cclasses[36], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[60]))) { cclasses[36] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[36].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (jobject obj = env->AllocObject((cclasses[36]))) { cstack0.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // ALOAD 2; Stack: 2
        cstack2.l = clocal2.l; refs.insert(cstack2.l);
        // New stack: 3
        // INVOKESPECIAL java/lang/invoke/MutableCallSite.<init>(Ljava/lang/invoke/MethodType;)V; Stack: 3
        if (!cclasses[36] || env->IsSameObject(cclasses[36], NULL)) { cclasses_mtx[36].lock(); if (!cclasses[36] || env->IsSameObject(cclasses[36], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[60]))) { cclasses[36] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[36].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[47]) { cmethods[47] = env->GetMethodID((cclasses[36]), ((char *)(string_pool + 628LL)), ((char *)(string_pool + 3932LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 660LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[36]), (cmethods[47]), cstack2.l); 
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
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.8175695498474079.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 1
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack1.l = lookup;
        // New stack: 2
        // LDC Ldev/sakura/client/Mahiro_i3;; Stack: 2
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack2.l = (cclasses[5]);
        // New stack: 3
        // LDC a; Stack: 3
        cstack3.l = (cstrings[6]);
        // New stack: 4
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/invoke/MutableCallSite;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;; Stack: 4
        cstack4.l = (cstrings[61]);
        // New stack: 5
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.8175695498474079.a()Ljava/lang/ClassLoader;; Stack: 5
        cstack5.l = classloader;
        // New stack: 6
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 6
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[1]) { cmethods[1] = env->GetStaticMethodID((cclasses[7]), ((char *)(string_pool + 276LL)), ((char *)(string_pool + 303LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack4.l = env->CallStaticObjectMethod((cclasses[7]), (cmethods[1]), cstack4.l, cstack5.l); refs.insert(cstack4.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 5
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 5
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[2]) { cmethods[2] = env->GetMethodID((cclasses[8]), ((char *)(string_pool + 376LL)), ((char *)(string_pool + 387LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 485LL)), -1); else { cstack1.l = env->CallObjectMethod(cstack1.l, (cmethods[2]), cstack2.l, cstack3.l, cstack4.l); refs.insert(cstack1.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 2
        // LDC [Ljava/lang/Object;; Stack: 2
        if (!cclasses[33] || env->IsSameObject(cclasses[33], NULL)) { cclasses_mtx[33].lock(); if (!cclasses[33] || env->IsSameObject(cclasses[33], NULL)) { if (jclass clazz = env->FindClass(((char *)(string_pool + 3233LL)))) { cclasses[33] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[33].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack2.l = (cclasses[33]);
        // New stack: 3
        // ALOAD 2; Stack: 3
        cstack3.l = clocal2.l; refs.insert(cstack3.l);
        // New stack: 4
        // INVOKEVIRTUAL java/lang/invoke/MethodType.parameterCount()I; Stack: 4
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[48]) { cmethods[48] = env->GetMethodID((cclasses[7]), ((char *)(string_pool + 3965LL)), ((char *)(string_pool + 2185LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack3.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 2189LL)), -1); else cstack3.i = env->CallIntMethod(cstack3.l, (cmethods[48])); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 4
        // INVOKEVIRTUAL java/lang/invoke/MethodHandle.asCollector(Ljava/lang/Class;I)Ljava/lang/invoke/MethodHandle;; Stack: 4
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[49]) { cmethods[49] = env->GetMethodID((cclasses[10]), ((char *)(string_pool + 3980LL)), ((char *)(string_pool + 3992LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 485LL)), -1); else { cstack1.l = env->CallObjectMethod(cstack1.l, (cmethods[49]), cstack2.l, cstack3.i); refs.insert(cstack1.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 2
        // ICONST_0; Stack: 2
        cstack2.i = 0;
        // New stack: 3
        // ICONST_3; Stack: 3
        cstack3.i = 3;
        // New stack: 4
        // ANEWARRAY java/lang/Object; Stack: 4
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack3.i < 0) utils::throw_re(env, ((char *)(string_pool + 214LL)), ((char *)(string_pool + 251LL)), -1); else { cstack3.l = env->NewObjectArray(cstack3.i, (cclasses[4]), nullptr); refs.insert(cstack3.l); } 
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
        if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 1657LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack4.l, cstack5.i, cstack6.l); } 
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
        if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 1657LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack4.l, cstack5.i, cstack6.l); } 
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
        if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 1657LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack4.l, cstack5.i, cstack6.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 4
        // INVOKESTATIC java/lang/invoke/MethodHandles.insertArguments(Ljava/lang/invoke/MethodHandle;I[Ljava/lang/Object;)Ljava/lang/invoke/MethodHandle;; Stack: 4
        if (!cclasses[23] || env->IsSameObject(cclasses[23], NULL)) { cclasses_mtx[23].lock(); if (!cclasses[23] || env->IsSameObject(cclasses[23], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[43]))) { cclasses[23] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[23].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[50]) { cmethods[50] = env->GetStaticMethodID((cclasses[23]), ((char *)(string_pool + 4044LL)), ((char *)(string_pool + 4060LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack1.l = env->CallStaticObjectMethod((cclasses[23]), (cmethods[50]), cstack1.l, cstack2.i, cstack3.l); refs.insert(cstack1.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 2
        // ALOAD 2; Stack: 2
        cstack2.l = clocal2.l; refs.insert(cstack2.l);
        // New stack: 3
        // INVOKESTATIC java/lang/invoke/MethodHandles.explicitCastArguments(Ljava/lang/invoke/MethodHandle;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 3
        if (!cclasses[23] || env->IsSameObject(cclasses[23], NULL)) { cclasses_mtx[23].lock(); if (!cclasses[23] || env->IsSameObject(cclasses[23], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[43]))) { cclasses[23] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[23].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[51]) { cmethods[51] = env->GetStaticMethodID((cclasses[23]), ((char *)(string_pool + 4145LL)), ((char *)(string_pool + 4167LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack1.l = env->CallStaticObjectMethod((cclasses[23]), (cmethods[51]), cstack1.l, cstack2.l); refs.insert(cstack1.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 2
        // INVOKEVIRTUAL java/lang/invoke/MutableCallSite.setTarget(Ljava/lang/invoke/MethodHandle;)V; Stack: 2
        if (!cclasses[36] || env->IsSameObject(cclasses[36], NULL)) { cclasses_mtx[36].lock(); if (!cclasses[36] || env->IsSameObject(cclasses[36], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[60]))) { cclasses[36] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[36].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[46]) { cmethods[46] = env->GetMethodID((cclasses[36]), ((char *)(string_pool + 3771LL)), ((char *)(string_pool + 3781LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 2843LL)), -1); else env->CallVoidMethod(cstack0.l, (cmethods[46]), cstack1.l); 
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
        if (!cclasses[34] || env->IsSameObject(cclasses[34], NULL)) { cclasses_mtx[34].lock(); if (!cclasses[34] || env->IsSameObject(cclasses[34], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[58]))) { cclasses[34] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[34].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (jobject obj = env->AllocObject((cclasses[34]))) { cstack0.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // NEW java/lang/StringBuilder; Stack: 2
        if (!cclasses[37] || env->IsSameObject(cclasses[37], NULL)) { cclasses_mtx[37].lock(); if (!cclasses[37] || env->IsSameObject(cclasses[37], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[62]))) { cclasses[37] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[37].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (jobject obj = env->AllocObject((cclasses[37]))) { cstack2.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 3
        // DUP; Stack: 3
        cstack3 = cstack2;
        // New stack: 4
        // INVOKESPECIAL java/lang/StringBuilder.<init>()V; Stack: 4
        if (!cclasses[37] || env->IsSameObject(cclasses[37], NULL)) { cclasses_mtx[37].lock(); if (!cclasses[37] || env->IsSameObject(cclasses[37], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[62]))) { cclasses[37] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[37].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[52]) { cmethods[52] = env->GetMethodID((cclasses[37]), ((char *)(string_pool + 628LL)), ((char *)(string_pool + 2010LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack3.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 660LL)), -1); else env->CallNonvirtualVoidMethod(cstack3.l, (cclasses[37]), (cmethods[52])); 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 3
        // LDC dev/sakura/client/Mahiro_i3; Stack: 3
        cstack3.l = (cstrings[59]);
        // New stack: 4
        // INVOKEVIRTUAL java/lang/StringBuilder.append(Ljava/lang/String;)Ljava/lang/StringBuilder;; Stack: 4
        if (!cclasses[37] || env->IsSameObject(cclasses[37], NULL)) { cclasses_mtx[37].lock(); if (!cclasses[37] || env->IsSameObject(cclasses[37], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[62]))) { cclasses[37] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[37].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[53]) { cmethods[53] = env->GetMethodID((cclasses[37]), ((char *)(string_pool + 4261LL)), ((char *)(string_pool + 4268LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack2.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 485LL)), -1); else { cstack2.l = env->CallObjectMethod(cstack2.l, (cmethods[53]), cstack3.l); refs.insert(cstack2.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 3
        // LDC  : ; Stack: 3
        cstack3.l = (cstrings[63]);
        // New stack: 4
        // INVOKEVIRTUAL java/lang/StringBuilder.append(Ljava/lang/String;)Ljava/lang/StringBuilder;; Stack: 4
        if (!cclasses[37] || env->IsSameObject(cclasses[37], NULL)) { cclasses_mtx[37].lock(); if (!cclasses[37] || env->IsSameObject(cclasses[37], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[62]))) { cclasses[37] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[37].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[53]) { cmethods[53] = env->GetMethodID((cclasses[37]), ((char *)(string_pool + 4261LL)), ((char *)(string_pool + 4268LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack2.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 485LL)), -1); else { cstack2.l = env->CallObjectMethod(cstack2.l, (cmethods[53]), cstack3.l); refs.insert(cstack2.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 3
        // ALOAD 1; Stack: 3
        cstack3.l = clocal1.l; refs.insert(cstack3.l);
        // New stack: 4
        // INVOKEVIRTUAL java/lang/StringBuilder.append(Ljava/lang/String;)Ljava/lang/StringBuilder;; Stack: 4
        if (!cclasses[37] || env->IsSameObject(cclasses[37], NULL)) { cclasses_mtx[37].lock(); if (!cclasses[37] || env->IsSameObject(cclasses[37], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[62]))) { cclasses[37] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[37].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[53]) { cmethods[53] = env->GetMethodID((cclasses[37]), ((char *)(string_pool + 4261LL)), ((char *)(string_pool + 4268LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack2.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 485LL)), -1); else { cstack2.l = env->CallObjectMethod(cstack2.l, (cmethods[53]), cstack3.l); refs.insert(cstack2.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 3
        // LDC  : ; Stack: 3
        cstack3.l = (cstrings[63]);
        // New stack: 4
        // INVOKEVIRTUAL java/lang/StringBuilder.append(Ljava/lang/String;)Ljava/lang/StringBuilder;; Stack: 4
        if (!cclasses[37] || env->IsSameObject(cclasses[37], NULL)) { cclasses_mtx[37].lock(); if (!cclasses[37] || env->IsSameObject(cclasses[37], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[62]))) { cclasses[37] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[37].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[53]) { cmethods[53] = env->GetMethodID((cclasses[37]), ((char *)(string_pool + 4261LL)), ((char *)(string_pool + 4268LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack2.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 485LL)), -1); else { cstack2.l = env->CallObjectMethod(cstack2.l, (cmethods[53]), cstack3.l); refs.insert(cstack2.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 3
        // ALOAD 2; Stack: 3
        cstack3.l = clocal2.l; refs.insert(cstack3.l);
        // New stack: 4
        // INVOKEVIRTUAL java/lang/invoke/MethodType.toString()Ljava/lang/String;; Stack: 4
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[54]) { cmethods[54] = env->GetMethodID((cclasses[7]), ((char *)(string_pool + 4314LL)), ((char *)(string_pool + 3042LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack3.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 485LL)), -1); else { cstack3.l = env->CallObjectMethod(cstack3.l, (cmethods[54])); refs.insert(cstack3.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 4
        // INVOKEVIRTUAL java/lang/StringBuilder.append(Ljava/lang/String;)Ljava/lang/StringBuilder;; Stack: 4
        if (!cclasses[37] || env->IsSameObject(cclasses[37], NULL)) { cclasses_mtx[37].lock(); if (!cclasses[37] || env->IsSameObject(cclasses[37], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[62]))) { cclasses[37] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[37].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[53]) { cmethods[53] = env->GetMethodID((cclasses[37]), ((char *)(string_pool + 4261LL)), ((char *)(string_pool + 4268LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack2.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 485LL)), -1); else { cstack2.l = env->CallObjectMethod(cstack2.l, (cmethods[53]), cstack3.l); refs.insert(cstack2.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 3
        // INVOKEVIRTUAL java/lang/StringBuilder.toString()Ljava/lang/String;; Stack: 3
        if (!cclasses[37] || env->IsSameObject(cclasses[37], NULL)) { cclasses_mtx[37].lock(); if (!cclasses[37] || env->IsSameObject(cclasses[37], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[62]))) { cclasses[37] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[37].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[55]) { cmethods[55] = env->GetMethodID((cclasses[37]), ((char *)(string_pool + 4314LL)), ((char *)(string_pool + 3042LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack2.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 485LL)), -1); else { cstack2.l = env->CallObjectMethod(cstack2.l, (cmethods[55])); refs.insert(cstack2.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 3
        // ALOAD 4; Stack: 3
        cstack3.l = clocal4.l; refs.insert(cstack3.l);
        // New stack: 4
        // INVOKESPECIAL java/lang/RuntimeException.<init>(Ljava/lang/String;Ljava/lang/Throwable;)V; Stack: 4
        if (!cclasses[34] || env->IsSameObject(cclasses[34], NULL)) { cclasses_mtx[34].lock(); if (!cclasses[34] || env->IsSameObject(cclasses[34], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[58]))) { cclasses[34] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[34].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[41]) { cmethods[41] = env->GetMethodID((cclasses[34]), ((char *)(string_pool + 628LL)), ((char *)(string_pool + 3314LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 660LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[34]), (cmethods[41]), cstack2.l, cstack3.l); 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 52LL)), ((char *)(string_pool + 683LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
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

        if (jstring str = env->NewStringUTF(((char *)(string_pool + 4443LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[5] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 4471LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[41] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 16716LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[35] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 4489LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[2] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 16774LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[38] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 4518LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[56] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 4504LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[57] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 3816LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[7] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 4538LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[9] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 4576LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[43] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 4697LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[0] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 4998LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[12] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 5062LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[62] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 16859LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[28] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 5218LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[1] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 15092LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[18] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 5298LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[13] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 5328LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[15] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 16161LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[30] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 5351LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[51] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 5380LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[63] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 16881LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[34] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 5444LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[22] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 16909LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[59] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 5653LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[50] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 5753LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[52] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 14363LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[55] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 16937LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[3] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 5827LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[16] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 5861LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[47] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 210LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[6] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 6030LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[54] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 6065LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[42] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 10525LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[33] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 6142LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[58] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 16965LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[31] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 16265LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[29] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 6467LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[60] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 16967LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[17] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 4415LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[44] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 17019LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[53] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 3441LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[61] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 7343LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[23] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 12579LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[39] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 7492LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[20] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 17123LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[37] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 5024LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[14] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 5123LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[40] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 7542LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[19] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 5426LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[46] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 7632LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[21] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 5511LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[48] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 5708LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[10] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 3123LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[32] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 10320LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[11] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 5951LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[45] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 6041LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[24] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 17145LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[27] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 6278LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[26] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 6302LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[8] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 6333LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[49] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 7792LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[25] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 6434LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[4] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 6451LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[36] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }

        JNINativeMethod __ngen_methods[] = {
            { ((char *)(string_pool + 1235LL)), ((char *)(string_pool + 6573LL)), (void *)&__ngen_native_Mahiro_Q1 },
            { ((char *)(string_pool + 11395LL)), ((char *)(string_pool + 9LL)), (void *)&__ngen_native_Mahiro_J2 },
            { ((char *)(string_pool + 15584LL)), ((char *)(string_pool + 9LL)), (void *)&__ngen_native_Mahiro_E3 },
            { ((char *)(string_pool + 210LL)), ((char *)(string_pool + 3012LL)), (void *)&__ngen_native_a5 },
            { ((char *)(string_pool + 210LL)), ((char *)(string_pool + 3123LL)), (void *)&__ngen_native_a6 },
            { ((char *)(string_pool + 210LL)), ((char *)(string_pool + 3441LL)), (void *)&__ngen_native_a7 },
            { ((char *)(string_pool + 210LL)), ((char *)(string_pool + 3816LL)), (void *)&__ngen_native_a8 },
        };

        if (clazz) env->RegisterNatives(clazz, __ngen_methods, sizeof(__ngen_methods) / sizeof(__ngen_methods[0]));
        if (env->ExceptionCheck()) { fprintf(stderr, "Exception occured while registering native_jvm for %s\n", ((char *)(string_pool + 16937LL))); fflush(stderr); env->ExceptionDescribe(); env->ExceptionClear(); }

        {
            jclass hidden_class = env->FindClass(((char *)(string_pool + 6500LL)));
            JNINativeMethod __ngen_hidden_methods[] = {
                { ((char *)(string_pool + 17191LL)), ((char *)(string_pool + 6543LL)), (void *)&__ngen_special_clinit_7_4 },
            };
            if (hidden_class) env->RegisterNatives(hidden_class, __ngen_hidden_methods, sizeof(__ngen_hidden_methods) / sizeof(__ngen_hidden_methods[0]));
            if (env->ExceptionCheck()) { fprintf(stderr, "Exception occured while registering native_jvm for %s\n", ((char *)(string_pool + 5328LL))); fflush(stderr); env->ExceptionDescribe(); env->ExceptionClear(); }
            env->DeleteLocalRef(hidden_class);
        }
    }
}