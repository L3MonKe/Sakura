#include "../native_jvm.hpp"
#include "../string_pool.hpp"
#include "dev_mahiro_client_Mahiro_nh_4.hpp"

// dev/mahiro/client/Mahiro_nh
namespace native_jvm::classes::__ngen_dev_mahiro_client_Mahiro_nh_4 {

    char *string_pool;

    jstring cstrings[28];
    std::mutex cclasses_mtx[21];
    jclass cclasses[21];
    jmethodID cmethods[15];
    jfieldID cfields[3];

    // Mahiro_D([Ljava/lang/Object;)[B
    jarray JNICALL __ngen_native_Mahiro_D1(JNIEnv *env, jclass clazz, jarray arg0) {
        jobject classloader = utils::get_classloader_from_class(env, clazz);
        if (env->ExceptionCheck()) { return (jarray) 0; }
        if (classloader == nullptr) { env->FatalError(((char *)(string_pool + 71LL))); return (jarray) 0; }
    
        jobject lookup = nullptr;
        // try-catch-class java/lang/Throwable
        if (!cclasses[0] || env->IsSameObject(cclasses[0], NULL)) { cclasses_mtx[0].lock(); if (!cclasses[0] || env->IsSameObject(cclasses[0], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[0]))) { cclasses[0] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[0].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } }
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
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 2
        // CHECKCAST java/lang/Long; Stack: 2
        if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { cclasses_mtx[1].lock(); if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[1]))) { cclasses[1] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[1].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (cstack1.l != nullptr && !env->IsInstanceOf(cstack1.l, (cclasses[1]))) { utils::throw_re(env, ((char *)(string_pool + 133LL)), (std::string(((char *)(string_pool + 162LL))) + std::string(((char *)(string_pool + 195LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (jarray) 0; } } 
        // New stack: 2
        // INVOKEVIRTUAL java/lang/Long.longValue()J; Stack: 2
        if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { cclasses_mtx[1].lock(); if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[1]))) { cclasses[1] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[1].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (!cmethods[0]) { cmethods[0] = env->GetMethodID((cclasses[1]), ((char *)(string_pool + 210LL)), ((char *)(string_pool + 220LL))); if (env->ExceptionCheck()) { return (jarray) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 224LL)), -1); else cstack1.j = env->CallLongMethod(cstack1.l, (cmethods[0])); 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 3
        // LSTORE 2; Stack: 3
        clocal2.j = cstack1.j;
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // ICONST_1; Stack: 2
        cstack2.i = 1;
        // New stack: 3
        // AALOAD; Stack: 3
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 122LL)), -1); else { cstack1.l = env->GetObjectArrayElement((jobjectArray) cstack1.l, cstack2.i); refs.insert(cstack1.l); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 2
        // CHECKCAST java/lang/Integer; Stack: 2
        if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { cclasses_mtx[2].lock(); if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[2]))) { cclasses[2] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[2].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (cstack1.l != nullptr && !env->IsInstanceOf(cstack1.l, (cclasses[2]))) { utils::throw_re(env, ((char *)(string_pool + 133LL)), (std::string(((char *)(string_pool + 162LL))) + std::string(((char *)(string_pool + 3192LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (jarray) 0; } } 
        // New stack: 2
        // INVOKEVIRTUAL java/lang/Integer.intValue()I; Stack: 2
        if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { cclasses_mtx[2].lock(); if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[2]))) { cclasses[2] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[2].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (!cmethods[1]) { cmethods[1] = env->GetMethodID((cclasses[2]), ((char *)(string_pool + 3210LL)), ((char *)(string_pool + 3219LL))); if (env->ExceptionCheck()) { return (jarray) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 3223LL)), -1); else cstack1.i = env->CallIntMethod(cstack1.l, (cmethods[1])); 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 2
        // ISTORE 1; Stack: 2
        clocal1.i = cstack1.i;
        // New stack: 1
        // POP; Stack: 1
        ;
        // New stack: 0
        // GETSTATIC dev/mahiro/client/Mahiro_nh.a J; Stack: 0
        if (!cclasses[3]  || env->IsSameObject(cclasses[3], NULL)) { cclasses_mtx[3].lock(); if (!cclasses[3] || env->IsSameObject(cclasses[3], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[3] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[3].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (!cfields[0]) { cfields[0] = env->GetStaticFieldID((cclasses[3]), ((char *)(string_pool + 247LL)), ((char *)(string_pool + 249LL))); if (env->ExceptionCheck()) { return (jarray) 0; }  } cstack0.j = env->GetStaticLongField((cclasses[3]), (cfields[0])); 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 2
        // LLOAD 2; Stack: 2
        cstack2.j = clocal2.j;
        // New stack: 4
        // LXOR; Stack: 4
        cstack0.j = cstack0.j ^ cstack2.j;
        // New stack: 2
        // LSTORE 2; Stack: 2
        clocal2.j = cstack0.j;
        // New stack: 0
        // ILOAD 1; Stack: 0
        cstack0.i = clocal1.i;
        // New stack: 1
        // NEWARRAY 8; Stack: 1
        if (cstack0.i < 0) utils::throw_re(env, ((char *)(string_pool + 251LL)), ((char *)(string_pool + 5414LL)), -1); else { cstack0.l = env->NewByteArray(cstack0.i); refs.insert(cstack0.l); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // ASTORE 4; Stack: 1
        clocal4.l = cstack0.l; refs.insert(cstack0.l);
        // New stack: 0
        // LDC 1455335710014635095; Stack: 0
        cstack0.j = 1455335710014635095LL;
        // New stack: 2
        // LLOAD 2; Stack: 2
        cstack2.j = clocal2.j;
        // New stack: 4
        // LABEL L3; Stack: 4
        L3: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 4
        // ICONST_1; Stack: 4
        cstack4.i = 1;
        // New stack: 5
        // ANEWARRAY java/lang/Object; Stack: 5
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack4.i < 0) utils::throw_re(env, ((char *)(string_pool + 251LL)), ((char *)(string_pool + 288LL)), -1); else { cstack4.l = env->NewObjectArray(cstack4.i, (cclasses[4]), nullptr); refs.insert(cstack4.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 5
        // DUP; Stack: 5
        cstack5 = cstack4;
        // New stack: 6
        // LDC Ldev/mahiro/client/Mahiro_nh;; Stack: 6
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack6.l = (cclasses[5]);
        // New stack: 7
        // SWAP; Stack: 7
        std::swap(cstack6, cstack5);
        // New stack: 7
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.127771650959966.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 7
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack7.l = lookup;
        // New stack: 8
        // LDC Ldev/mahiro/client/Mahiro_yh;; Stack: 8
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack8.l = (cclasses[6]);
        // New stack: 9
        // LDC a; Stack: 9
        cstack9.l = (cstrings[6]);
        // New stack: 10
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;; Stack: 10
        cstack10.l = (cstrings[7]);
        // New stack: 11
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.127771650959966.a()Ljava/lang/ClassLoader;; Stack: 11
        cstack11.l = classloader;
        // New stack: 12
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 12
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[2]) { cmethods[2] = env->GetStaticMethodID((cclasses[7]), ((char *)(string_pool + 313LL)), ((char *)(string_pool + 340LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack10.l = env->CallStaticObjectMethod((cclasses[7]), (cmethods[2]), cstack10.l, cstack11.l); refs.insert(cstack10.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 11
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 11
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[3]) { cmethods[3] = env->GetMethodID((cclasses[8]), ((char *)(string_pool + 413LL)), ((char *)(string_pool + 424LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack7.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 522LL)), -1); else { cstack7.l = env->CallObjectMethod(cstack7.l, (cmethods[3]), cstack8.l, cstack9.l, cstack10.l); refs.insert(cstack7.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 8
        // SWAP; Stack: 8
        std::swap(cstack7, cstack6);
        // New stack: 8
        // LDC \u00ea; Stack: 8
        cstack8.l = (cstrings[10]);
        // New stack: 9
        // SWAP; Stack: 9
        std::swap(cstack8, cstack7);
        // New stack: 9
        // LDC (JJ)Ljava/security/SecureRandom;; Stack: 9
        cstack9.l = (cstrings[11]);
        // New stack: 10
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.127771650959966.a()Ljava/lang/ClassLoader;; Stack: 10
        cstack10.l = classloader;
        // New stack: 11
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 11
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[2]) { cmethods[2] = env->GetStaticMethodID((cclasses[7]), ((char *)(string_pool + 313LL)), ((char *)(string_pool + 340LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack9.l = env->CallStaticObjectMethod((cclasses[7]), (cmethods[2]), cstack9.l, cstack10.l); refs.insert(cstack9.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 10
        // SWAP; Stack: 10
        std::swap(cstack9, cstack8);
        // New stack: 10
        // LDC 0; Stack: 10
        cstack10.i = 0;
        // New stack: 11
        // ANEWARRAY java/lang/Object; Stack: 11
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack10.i < 0) utils::throw_re(env, ((char *)(string_pool + 251LL)), ((char *)(string_pool + 288LL)), -1); else { cstack10.l = env->NewObjectArray(cstack10.i, (cclasses[4]), nullptr); refs.insert(cstack10.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 11
        // CHECKCAST java/lang/Object; Stack: 11
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack10.l != nullptr && !env->IsInstanceOf(cstack10.l, (cclasses[4]))) { utils::throw_re(env, ((char *)(string_pool + 133LL)), (std::string(((char *)(string_pool + 162LL))) + std::string(((char *)(string_pool + 547LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } 
        // New stack: 11
        // SWAP; Stack: 11
        std::swap(cstack10, cstack9);
        // New stack: 11
        // INVOKESTATIC native/magic/1/linkcallsite/obfuscator0.127771650959966.a(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/invoke/MemberName;; Stack: 11
        cstack5.l = utils::link_call_site(env, cstack5.l, cstack6.l, cstack7.l, cstack8.l, cstack9.l, cstack10.l); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 6
        // POP; Stack: 6
        ;
        // New stack: 5
        // ICONST_0; Stack: 5
        cstack5.i = 0;
        // New stack: 6
        // AALOAD; Stack: 6
        if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 122LL)), -1); else { cstack4.l = env->GetObjectArrayElement((jobjectArray) cstack4.l, cstack5.i); refs.insert(cstack4.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 5
        // DUP; Stack: 5
        cstack5 = cstack4;
        // New stack: 6
        // INSTANCEOF java/lang/invoke/CallSite; Stack: 6
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack5.i = cstack5.l == nullptr ? false : env->IsInstanceOf(cstack5.l, (cclasses[9]));
        // New stack: 6
        // IFEQ L5; Stack: 6
        if (cstack5.i == 0) goto L5;
        // New stack: 5
        // INVOKEINTERFACE java/lang/invoke/CallSite.getTarget()Ljava/lang/invoke/MethodHandle;; Stack: 5
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 564LL)), ((char *)(string_pool + 574LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 608LL)), -1); else { cstack4.l = env->CallObjectMethod(cstack4.l, (cmethods[4])); refs.insert(cstack4.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 5
        // LABEL L5; Stack: 5
        L5: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 5
        // FRAME FULL L: [[Ljava/lang/Object;, 1, 4, [B] S: [4, 4, java/lang/Object]; Stack: 5
        refs.erase(cstack4.l); 
        refs.erase(clocal0.l); refs.erase(clocal4.l); 
        utils::clear_refs(env, refs);
        // New stack: 5
        // CHECKCAST java/lang/invoke/MethodHandle; Stack: 5
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack4.l != nullptr && !env->IsInstanceOf(cstack4.l, (cclasses[10]))) { utils::throw_re(env, ((char *)(string_pool + 133LL)), (std::string(((char *)(string_pool + 162LL))) + std::string(((char *)(string_pool + 635LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } 
        // New stack: 5
        // GOTO L6; Stack: 5
        goto L6;
        // New stack: 5
        // LABEL L4; Stack: 5
        L4: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 5
        // FRAME SAME1 L: null S: [java/lang/Throwable]; Stack: 5
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal4.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // INSTANCEOF java/lang/BootstrapMethodError; Stack: 2
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } cstack1.i = cstack1.l == nullptr ? false : env->IsInstanceOf(cstack1.l, (cclasses[11]));
        // New stack: 2
        // IFNE L7; Stack: 2
        if (cstack1.i != 0) goto L7;
        // New stack: 1
        // NEW java/lang/BootstrapMethodError; Stack: 1
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (jobject obj = env->AllocObject((cclasses[11]))) { cstack1.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
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
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (!cmethods[5]) { cmethods[5] = env->GetMethodID((cclasses[11]), ((char *)(string_pool + 665LL)), ((char *)(string_pool + 672LL))); if (env->ExceptionCheck()) { return (jarray) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 697LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[11]), (cmethods[5]), cstack2.l); 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // LABEL L7; Stack: 1
        L7: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/Object]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal4.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 720LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 0
        // LABEL L6; Stack: 0
        L6: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 0
        // FRAME FULL L: [[Ljava/lang/Object;, 1, 4, [B] S: [4, 4, java/lang/invoke/MethodHandle]; Stack: 0
        refs.erase(cstack4.l); 
        refs.erase(clocal0.l); refs.erase(clocal4.l); 
        utils::clear_refs(env, refs);
        // New stack: 5
        // INVOKESTATIC native/magic/1/invoke/obfuscator0.127771650959966.a(JJLjava/lang/invoke/MethodHandle;)Ljava/security/SecureRandom;; Stack: 5
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (!cmethods[6]) { cmethods[6] = env->GetStaticMethodID((cclasses[12]), ((char *)(string_pool + 2157LL)), ((char *)(string_pool + 2173LL))); if (env->ExceptionCheck()) { return (jarray) 0; }  } cstack0.l = env->CallStaticObjectMethod((cclasses[12]), (cmethods[6]), cstack0.j, cstack2.j, cstack4.l); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // CHECKCAST java/security/SecureRandom; Stack: 1
        if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { cclasses_mtx[13].lock(); if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[16]))) { cclasses[13] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[13].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (cstack0.l != nullptr && !env->IsInstanceOf(cstack0.l, (cclasses[13]))) { utils::throw_re(env, ((char *)(string_pool + 133LL)), (std::string(((char *)(string_pool + 162LL))) + std::string(((char *)(string_pool + 19915LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (jarray) 0; } } 
        // New stack: 1
        // ALOAD 4; Stack: 1
        cstack1.l = clocal4.l; refs.insert(cstack1.l);
        // New stack: 2
        // LDC 1459402342768951646; Stack: 2
        cstack2.j = 1459402342768951646LL;
        // New stack: 4
        // LLOAD 2; Stack: 4
        cstack4.j = clocal2.j;
        // New stack: 6
        // LABEL L1; Stack: 6
        L1: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 6
        // ICONST_1; Stack: 6
        cstack6.i = 1;
        // New stack: 7
        // ANEWARRAY java/lang/Object; Stack: 7
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (cstack6.i < 0) utils::throw_re(env, ((char *)(string_pool + 251LL)), ((char *)(string_pool + 288LL)), -1); else { cstack6.l = env->NewObjectArray(cstack6.i, (cclasses[4]), nullptr); refs.insert(cstack6.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 7
        // DUP; Stack: 7
        cstack7 = cstack6;
        // New stack: 8
        // LDC Ldev/mahiro/client/Mahiro_nh;; Stack: 8
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } cstack8.l = (cclasses[5]);
        // New stack: 9
        // SWAP; Stack: 9
        std::swap(cstack8, cstack7);
        // New stack: 9
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.127771650959966.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 9
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } cstack9.l = lookup;
        // New stack: 10
        // LDC Ldev/mahiro/client/Mahiro_yh;; Stack: 10
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } cstack10.l = (cclasses[6]);
        // New stack: 11
        // LDC a; Stack: 11
        cstack11.l = (cstrings[6]);
        // New stack: 12
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;; Stack: 12
        cstack12.l = (cstrings[7]);
        // New stack: 13
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.127771650959966.a()Ljava/lang/ClassLoader;; Stack: 13
        cstack13.l = classloader;
        // New stack: 14
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 14
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (!cmethods[2]) { cmethods[2] = env->GetStaticMethodID((cclasses[7]), ((char *)(string_pool + 313LL)), ((char *)(string_pool + 340LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }  } cstack12.l = env->CallStaticObjectMethod((cclasses[7]), (cmethods[2]), cstack12.l, cstack13.l); refs.insert(cstack12.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 13
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 13
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (!cmethods[3]) { cmethods[3] = env->GetMethodID((cclasses[8]), ((char *)(string_pool + 413LL)), ((char *)(string_pool + 424LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }  } if (cstack9.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 522LL)), -1); else { cstack9.l = env->CallObjectMethod(cstack9.l, (cmethods[3]), cstack10.l, cstack11.l, cstack12.l); refs.insert(cstack9.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 10
        // SWAP; Stack: 10
        std::swap(cstack9, cstack8);
        // New stack: 10
        // LDC v; Stack: 10
        cstack10.l = (cstrings[17]);
        // New stack: 11
        // SWAP; Stack: 11
        std::swap(cstack10, cstack9);
        // New stack: 11
        // LDC (Ljava/lang/Object;Ljava/lang/Object;JJ)V; Stack: 11
        cstack11.l = (cstrings[18]);
        // New stack: 12
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.127771650959966.a()Ljava/lang/ClassLoader;; Stack: 12
        cstack12.l = classloader;
        // New stack: 13
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 13
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (!cmethods[2]) { cmethods[2] = env->GetStaticMethodID((cclasses[7]), ((char *)(string_pool + 313LL)), ((char *)(string_pool + 340LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }  } cstack11.l = env->CallStaticObjectMethod((cclasses[7]), (cmethods[2]), cstack11.l, cstack12.l); refs.insert(cstack11.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 12
        // SWAP; Stack: 12
        std::swap(cstack11, cstack10);
        // New stack: 12
        // LDC 0; Stack: 12
        cstack12.i = 0;
        // New stack: 13
        // ANEWARRAY java/lang/Object; Stack: 13
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (cstack12.i < 0) utils::throw_re(env, ((char *)(string_pool + 251LL)), ((char *)(string_pool + 288LL)), -1); else { cstack12.l = env->NewObjectArray(cstack12.i, (cclasses[4]), nullptr); refs.insert(cstack12.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 13
        // CHECKCAST java/lang/Object; Stack: 13
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (cstack12.l != nullptr && !env->IsInstanceOf(cstack12.l, (cclasses[4]))) { utils::throw_re(env, ((char *)(string_pool + 133LL)), (std::string(((char *)(string_pool + 162LL))) + std::string(((char *)(string_pool + 547LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } 
        // New stack: 13
        // SWAP; Stack: 13
        std::swap(cstack12, cstack11);
        // New stack: 13
        // INVOKESTATIC native/magic/1/linkcallsite/obfuscator0.127771650959966.a(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/invoke/MemberName;; Stack: 13
        cstack7.l = utils::link_call_site(env, cstack7.l, cstack8.l, cstack9.l, cstack10.l, cstack11.l, cstack12.l); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 8
        // POP; Stack: 8
        ;
        // New stack: 7
        // ICONST_0; Stack: 7
        cstack7.i = 0;
        // New stack: 8
        // AALOAD; Stack: 8
        if (cstack6.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 122LL)), -1); else { cstack6.l = env->GetObjectArrayElement((jobjectArray) cstack6.l, cstack7.i); refs.insert(cstack6.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 7
        // DUP; Stack: 7
        cstack7 = cstack6;
        // New stack: 8
        // INSTANCEOF java/lang/invoke/CallSite; Stack: 8
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } cstack7.i = cstack7.l == nullptr ? false : env->IsInstanceOf(cstack7.l, (cclasses[9]));
        // New stack: 8
        // IFEQ L8; Stack: 8
        if (cstack7.i == 0) goto L8;
        // New stack: 7
        // INVOKEINTERFACE java/lang/invoke/CallSite.getTarget()Ljava/lang/invoke/MethodHandle;; Stack: 7
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 564LL)), ((char *)(string_pool + 574LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }  } if (cstack6.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 608LL)), -1); else { cstack6.l = env->CallObjectMethod(cstack6.l, (cmethods[4])); refs.insert(cstack6.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 7
        // LABEL L8; Stack: 7
        L8: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 7
        // FRAME FULL L: [[Ljava/lang/Object;, 1, 4, [B] S: [java/security/SecureRandom, [B, 4, 4, java/lang/Object]; Stack: 7
        refs.erase(cstack0.l); refs.erase(cstack1.l); refs.erase(cstack6.l); 
        refs.erase(clocal0.l); refs.erase(clocal4.l); 
        utils::clear_refs(env, refs);
        // New stack: 7
        // CHECKCAST java/lang/invoke/MethodHandle; Stack: 7
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (cstack6.l != nullptr && !env->IsInstanceOf(cstack6.l, (cclasses[10]))) { utils::throw_re(env, ((char *)(string_pool + 133LL)), (std::string(((char *)(string_pool + 162LL))) + std::string(((char *)(string_pool + 635LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } 
        // New stack: 7
        // GOTO L9; Stack: 7
        goto L9;
        // New stack: 7
        // LABEL L2; Stack: 7
        L2: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 7
        // FRAME SAME1 L: null S: [java/lang/Throwable]; Stack: 7
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal4.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // INSTANCEOF java/lang/BootstrapMethodError; Stack: 2
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } cstack1.i = cstack1.l == nullptr ? false : env->IsInstanceOf(cstack1.l, (cclasses[11]));
        // New stack: 2
        // IFNE L10; Stack: 2
        if (cstack1.i != 0) goto L10;
        // New stack: 1
        // NEW java/lang/BootstrapMethodError; Stack: 1
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (jobject obj = env->AllocObject((cclasses[11]))) { cstack1.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
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
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (!cmethods[5]) { cmethods[5] = env->GetMethodID((cclasses[11]), ((char *)(string_pool + 665LL)), ((char *)(string_pool + 672LL))); if (env->ExceptionCheck()) { return (jarray) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 697LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[11]), (cmethods[5]), cstack2.l); 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // LABEL L10; Stack: 1
        L10: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/Object]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal4.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 720LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 0
        // LABEL L9; Stack: 0
        L9: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 0
        // FRAME FULL L: [[Ljava/lang/Object;, 1, 4, [B] S: [java/security/SecureRandom, [B, 4, 4, java/lang/invoke/MethodHandle]; Stack: 0
        refs.erase(cstack0.l); refs.erase(cstack1.l); refs.erase(cstack6.l); 
        refs.erase(clocal0.l); refs.erase(clocal4.l); 
        utils::clear_refs(env, refs);
        // New stack: 7
        // INVOKESTATIC native/magic/1/invoke/obfuscator0.127771650959966.a(Ljava/lang/Object;Ljava/lang/Object;JJLjava/lang/invoke/MethodHandle;)V; Stack: 7
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (!cmethods[7]) { cmethods[7] = env->GetStaticMethodID((cclasses[12]), ((char *)(string_pool + 2068LL)), ((char *)(string_pool + 2084LL))); if (env->ExceptionCheck()) { return (jarray) 0; }  } env->CallStaticVoidMethod((cclasses[12]), (cmethods[7]), cstack0.l, cstack1.l, cstack2.j, cstack4.j, cstack6.l); 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 0
        // ALOAD 4; Stack: 0
        cstack0.l = clocal4.l; refs.insert(cstack0.l);
        // New stack: 1
        // ARETURN; Stack: 1
        return (jarray) cstack0.l;
        // New stack: 0
        return (jarray) 0;
        L_CATCH_1: if (env->IsInstanceOf(cstack0.l, (cclasses[0]))) { goto L2; }
        env->Throw((jthrowable) cstack0.l); return (jarray) 0;
        L_CATCH_0: if (env->IsInstanceOf(cstack0.l, (cclasses[0]))) { goto L4; }
        env->Throw((jthrowable) cstack0.l); return (jarray) 0;
    }
    
    // Mahiro_F([Ljava/lang/Object;)[B
    jarray JNICALL __ngen_native_Mahiro_F2(JNIEnv *env, jclass clazz, jarray arg0) {
        jobject classloader = utils::get_classloader_from_class(env, clazz);
        if (env->ExceptionCheck()) { return (jarray) 0; }
        if (classloader == nullptr) { env->FatalError(((char *)(string_pool + 71LL))); return (jarray) 0; }
    
        jobject lookup = nullptr;
        // try-catch-class java/lang/Throwable
        if (!cclasses[0] || env->IsSameObject(cclasses[0], NULL)) { cclasses_mtx[0].lock(); if (!cclasses[0] || env->IsSameObject(cclasses[0], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[0]))) { cclasses[0] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[0].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } }
        jvalue cstack0 = {}, cstack1 = {}, cstack2 = {}, cstack3 = {}, cstack4 = {}, cstack5 = {}, cstack6 = {}, cstack7 = {}, cstack8 = {}, cstack9 = {}, cstack10 = {}, cstack11 = {}, cstack12 = {}, cstack13 = {}, cstack14 = {}, cstack15 = {}, cstack16 = {};
        jvalue clocal0 = {}, clocal1 = {}, clocal2 = {}, clocal3 = {}, clocal4 = {}, clocal5 = {}, clocal6 = {}, clocal7 = {}, clocal8 = {}, clocal9 = {}, clocal10 = {}, clocal11 = {};
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
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 2
        // CHECKCAST java/lang/Long; Stack: 2
        if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { cclasses_mtx[1].lock(); if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[1]))) { cclasses[1] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[1].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (cstack1.l != nullptr && !env->IsInstanceOf(cstack1.l, (cclasses[1]))) { utils::throw_re(env, ((char *)(string_pool + 133LL)), (std::string(((char *)(string_pool + 162LL))) + std::string(((char *)(string_pool + 195LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (jarray) 0; } } 
        // New stack: 2
        // INVOKEVIRTUAL java/lang/Long.longValue()J; Stack: 2
        if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { cclasses_mtx[1].lock(); if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[1]))) { cclasses[1] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[1].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (!cmethods[0]) { cmethods[0] = env->GetMethodID((cclasses[1]), ((char *)(string_pool + 210LL)), ((char *)(string_pool + 220LL))); if (env->ExceptionCheck()) { return (jarray) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 224LL)), -1); else cstack1.j = env->CallLongMethod(cstack1.l, (cmethods[0])); 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 3
        // LSTORE 1; Stack: 3
        clocal1.j = cstack1.j;
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // ICONST_1; Stack: 2
        cstack2.i = 1;
        // New stack: 3
        // AALOAD; Stack: 3
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 122LL)), -1); else { cstack1.l = env->GetObjectArrayElement((jobjectArray) cstack1.l, cstack2.i); refs.insert(cstack1.l); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 2
        // CHECKCAST [[B; Stack: 2
        if (!cclasses[14] || env->IsSameObject(cclasses[14], NULL)) { cclasses_mtx[14].lock(); if (!cclasses[14] || env->IsSameObject(cclasses[14], NULL)) { if (jclass clazz = env->FindClass(((char *)(string_pool + 19942LL)))) { cclasses[14] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[14].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (cstack1.l != nullptr && !env->IsInstanceOf(cstack1.l, (cclasses[14]))) { utils::throw_re(env, ((char *)(string_pool + 133LL)), (std::string(((char *)(string_pool + 162LL))) + std::string(((char *)(string_pool + 19942LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (jarray) 0; } } 
        // New stack: 2
        // ASTORE 3; Stack: 2
        clocal3.l = cstack1.l; refs.insert(cstack1.l);
        // New stack: 1
        // POP; Stack: 1
        ;
        // New stack: 0
        // GETSTATIC dev/mahiro/client/Mahiro_nh.a J; Stack: 0
        if (!cclasses[3]  || env->IsSameObject(cclasses[3], NULL)) { cclasses_mtx[3].lock(); if (!cclasses[3] || env->IsSameObject(cclasses[3], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[3] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[3].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (!cfields[0]) { cfields[0] = env->GetStaticFieldID((cclasses[3]), ((char *)(string_pool + 247LL)), ((char *)(string_pool + 249LL))); if (env->ExceptionCheck()) { return (jarray) 0; }  } cstack0.j = env->GetStaticLongField((cclasses[3]), (cfields[0])); 
        if (env->ExceptionCheck()) { return (jarray) 0; }
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
        // ICONST_0; Stack: 0
        cstack0.i = 0;
        // New stack: 1
        // ISTORE 5; Stack: 1
        clocal5.i = cstack0.i;
        // New stack: 0
        // LDC 8257292253389971252; Stack: 0
        cstack0.j = 8257292253389971252LL;
        // New stack: 2
        // LLOAD 1; Stack: 2
        cstack2.j = clocal1.j;
        // New stack: 4
        // LABEL L7; Stack: 4
        L7: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 4
        // ICONST_1; Stack: 4
        cstack4.i = 1;
        // New stack: 5
        // ANEWARRAY java/lang/Object; Stack: 5
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack4.i < 0) utils::throw_re(env, ((char *)(string_pool + 251LL)), ((char *)(string_pool + 288LL)), -1); else { cstack4.l = env->NewObjectArray(cstack4.i, (cclasses[4]), nullptr); refs.insert(cstack4.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 5
        // DUP; Stack: 5
        cstack5 = cstack4;
        // New stack: 6
        // LDC Ldev/mahiro/client/Mahiro_nh;; Stack: 6
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack6.l = (cclasses[5]);
        // New stack: 7
        // SWAP; Stack: 7
        std::swap(cstack6, cstack5);
        // New stack: 7
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.127771650959966.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 7
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack7.l = lookup;
        // New stack: 8
        // LDC Ldev/mahiro/client/Mahiro_yh;; Stack: 8
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack8.l = (cclasses[6]);
        // New stack: 9
        // LDC a; Stack: 9
        cstack9.l = (cstrings[6]);
        // New stack: 10
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;; Stack: 10
        cstack10.l = (cstrings[7]);
        // New stack: 11
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.127771650959966.a()Ljava/lang/ClassLoader;; Stack: 11
        cstack11.l = classloader;
        // New stack: 12
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 12
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[2]) { cmethods[2] = env->GetStaticMethodID((cclasses[7]), ((char *)(string_pool + 313LL)), ((char *)(string_pool + 340LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack10.l = env->CallStaticObjectMethod((cclasses[7]), (cmethods[2]), cstack10.l, cstack11.l); refs.insert(cstack10.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 11
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 11
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[3]) { cmethods[3] = env->GetMethodID((cclasses[8]), ((char *)(string_pool + 413LL)), ((char *)(string_pool + 424LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack7.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 522LL)), -1); else { cstack7.l = env->CallObjectMethod(cstack7.l, (cmethods[3]), cstack8.l, cstack9.l, cstack10.l); refs.insert(cstack7.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 8
        // SWAP; Stack: 8
        std::swap(cstack7, cstack6);
        // New stack: 8
        // LDC \u00c9; Stack: 8
        cstack8.l = (cstrings[19]);
        // New stack: 9
        // SWAP; Stack: 9
        std::swap(cstack8, cstack7);
        // New stack: 9
        // LDC (JJ)[Ldev/mahiro/client/Mahiro_qT;; Stack: 9
        cstack9.l = (cstrings[20]);
        // New stack: 10
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.127771650959966.a()Ljava/lang/ClassLoader;; Stack: 10
        cstack10.l = classloader;
        // New stack: 11
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 11
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[2]) { cmethods[2] = env->GetStaticMethodID((cclasses[7]), ((char *)(string_pool + 313LL)), ((char *)(string_pool + 340LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack9.l = env->CallStaticObjectMethod((cclasses[7]), (cmethods[2]), cstack9.l, cstack10.l); refs.insert(cstack9.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 10
        // SWAP; Stack: 10
        std::swap(cstack9, cstack8);
        // New stack: 10
        // LDC 0; Stack: 10
        cstack10.i = 0;
        // New stack: 11
        // ANEWARRAY java/lang/Object; Stack: 11
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack10.i < 0) utils::throw_re(env, ((char *)(string_pool + 251LL)), ((char *)(string_pool + 288LL)), -1); else { cstack10.l = env->NewObjectArray(cstack10.i, (cclasses[4]), nullptr); refs.insert(cstack10.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 11
        // CHECKCAST java/lang/Object; Stack: 11
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack10.l != nullptr && !env->IsInstanceOf(cstack10.l, (cclasses[4]))) { utils::throw_re(env, ((char *)(string_pool + 133LL)), (std::string(((char *)(string_pool + 162LL))) + std::string(((char *)(string_pool + 547LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } 
        // New stack: 11
        // SWAP; Stack: 11
        std::swap(cstack10, cstack9);
        // New stack: 11
        // INVOKESTATIC native/magic/1/linkcallsite/obfuscator0.127771650959966.a(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/invoke/MemberName;; Stack: 11
        cstack5.l = utils::link_call_site(env, cstack5.l, cstack6.l, cstack7.l, cstack8.l, cstack9.l, cstack10.l); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 6
        // POP; Stack: 6
        ;
        // New stack: 5
        // ICONST_0; Stack: 5
        cstack5.i = 0;
        // New stack: 6
        // AALOAD; Stack: 6
        if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 122LL)), -1); else { cstack4.l = env->GetObjectArrayElement((jobjectArray) cstack4.l, cstack5.i); refs.insert(cstack4.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 5
        // DUP; Stack: 5
        cstack5 = cstack4;
        // New stack: 6
        // INSTANCEOF java/lang/invoke/CallSite; Stack: 6
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack5.i = cstack5.l == nullptr ? false : env->IsInstanceOf(cstack5.l, (cclasses[9]));
        // New stack: 6
        // IFEQ L9; Stack: 6
        if (cstack5.i == 0) goto L9;
        // New stack: 5
        // INVOKEINTERFACE java/lang/invoke/CallSite.getTarget()Ljava/lang/invoke/MethodHandle;; Stack: 5
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 564LL)), ((char *)(string_pool + 574LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 608LL)), -1); else { cstack4.l = env->CallObjectMethod(cstack4.l, (cmethods[4])); refs.insert(cstack4.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 5
        // LABEL L9; Stack: 5
        L9: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 5
        // FRAME FULL L: [[Ljava/lang/Object;, 4, [[B, 0, 1] S: [4, 4, java/lang/Object]; Stack: 5
        refs.erase(cstack4.l); 
        refs.erase(clocal0.l); refs.erase(clocal3.l); 
        utils::clear_refs(env, refs);
        // New stack: 5
        // CHECKCAST java/lang/invoke/MethodHandle; Stack: 5
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack4.l != nullptr && !env->IsInstanceOf(cstack4.l, (cclasses[10]))) { utils::throw_re(env, ((char *)(string_pool + 133LL)), (std::string(((char *)(string_pool + 162LL))) + std::string(((char *)(string_pool + 635LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } 
        // New stack: 5
        // GOTO L10; Stack: 5
        goto L10;
        // New stack: 5
        // LABEL L8; Stack: 5
        L8: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 5
        // FRAME SAME1 L: null S: [java/lang/Throwable]; Stack: 5
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal3.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // INSTANCEOF java/lang/BootstrapMethodError; Stack: 2
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } cstack1.i = cstack1.l == nullptr ? false : env->IsInstanceOf(cstack1.l, (cclasses[11]));
        // New stack: 2
        // IFNE L11; Stack: 2
        if (cstack1.i != 0) goto L11;
        // New stack: 1
        // NEW java/lang/BootstrapMethodError; Stack: 1
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (jobject obj = env->AllocObject((cclasses[11]))) { cstack1.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
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
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (!cmethods[5]) { cmethods[5] = env->GetMethodID((cclasses[11]), ((char *)(string_pool + 665LL)), ((char *)(string_pool + 672LL))); if (env->ExceptionCheck()) { return (jarray) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 697LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[11]), (cmethods[5]), cstack2.l); 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // LABEL L11; Stack: 1
        L11: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/Object]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal3.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 720LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 0
        // LABEL L10; Stack: 0
        L10: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 0
        // FRAME FULL L: [[Ljava/lang/Object;, 4, [[B, 0, 1] S: [4, 4, java/lang/invoke/MethodHandle]; Stack: 0
        refs.erase(cstack4.l); 
        refs.erase(clocal0.l); refs.erase(clocal3.l); 
        utils::clear_refs(env, refs);
        // New stack: 5
        // INVOKESTATIC native/magic/1/invoke/obfuscator0.127771650959966.a(JJLjava/lang/invoke/MethodHandle;)[Ldev/mahiro/client/Mahiro_qT;; Stack: 5
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (!cmethods[6]) { cmethods[6] = env->GetStaticMethodID((cclasses[12]), ((char *)(string_pool + 2157LL)), ((char *)(string_pool + 2173LL))); if (env->ExceptionCheck()) { return (jarray) 0; }  } cstack0.l = env->CallStaticObjectMethod((cclasses[12]), (cmethods[6]), cstack0.j, cstack2.j, cstack4.l); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // CHECKCAST [Ldev/mahiro/client/Mahiro_qT;; Stack: 1
        if (!cclasses[15] || env->IsSameObject(cclasses[15], NULL)) { cclasses_mtx[15].lock(); if (!cclasses[15] || env->IsSameObject(cclasses[15], NULL)) { if (jclass clazz = env->FindClass(((char *)(string_pool + 17465LL)))) { cclasses[15] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[15].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (cstack0.l != nullptr && !env->IsInstanceOf(cstack0.l, (cclasses[15]))) { utils::throw_re(env, ((char *)(string_pool + 133LL)), (std::string(((char *)(string_pool + 162LL))) + std::string(((char *)(string_pool + 17465LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (jarray) 0; } } 
        // New stack: 1
        // ALOAD 3; Stack: 1
        cstack1.l = clocal3.l; refs.insert(cstack1.l);
        // New stack: 2
        // ASTORE 6; Stack: 2
        clocal6.l = cstack1.l; refs.insert(cstack1.l);
        // New stack: 1
        // ALOAD 6; Stack: 1
        cstack1.l = clocal6.l; refs.insert(cstack1.l);
        // New stack: 2
        // ARRAYLENGTH; Stack: 2
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 6026LL)), -1); else cstack1.i = env->GetArrayLength((jarray) cstack1.l); 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 2
        // ISTORE 7; Stack: 2
        clocal7.i = cstack1.i;
        // New stack: 1
        // ICONST_0; Stack: 1
        cstack1.i = 0;
        // New stack: 2
        // ISTORE 8; Stack: 2
        clocal8.i = cstack1.i;
        // New stack: 1
        // ASTORE 4; Stack: 1
        clocal4.l = cstack0.l; refs.insert(cstack0.l);
        // New stack: 0
        // LABEL L12; Stack: 0
        L12: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 0
        // FRAME FULL L: [[Ljava/lang/Object;, 4, [[B, [Ldev/mahiro/client/Mahiro_qT;, 1, [[B, 1, 1] S: []; Stack: 0
        refs.erase(clocal0.l); refs.erase(clocal3.l); refs.erase(clocal4.l); refs.erase(clocal6.l); 
        utils::clear_refs(env, refs);
        // New stack: 0
        // ILOAD 8; Stack: 0
        cstack0.i = clocal8.i;
        // New stack: 1
        // ILOAD 7; Stack: 1
        cstack1.i = clocal7.i;
        // New stack: 2
        // IF_ICMPGE L13; Stack: 2
        if (cstack0.i >= cstack1.i) goto L13;
        // New stack: 0
        // ALOAD 6; Stack: 0
        cstack0.l = clocal6.l; refs.insert(cstack0.l);
        // New stack: 1
        // ILOAD 8; Stack: 1
        cstack1.i = clocal8.i;
        // New stack: 2
        // AALOAD; Stack: 2
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 122LL)), -1); else { cstack0.l = env->GetObjectArrayElement((jobjectArray) cstack0.l, cstack1.i); refs.insert(cstack0.l); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // LLOAD 1; Stack: 1
        cstack1.j = clocal1.j;
        // New stack: 3
        // LCONST_0; Stack: 3
        cstack3.j = 0;
        // New stack: 5
        // LCMP; Stack: 5
        cstack1.i = (cstack1.j == cstack3.j) ? 0 : (cstack1.j > cstack3.j ? 1 : -1);
        // New stack: 2
        // IFLT L14; Stack: 2
        if (cstack1.i < 0) goto L14;
        // New stack: 1
        // ASTORE 9; Stack: 1
        clocal9.l = cstack0.l; refs.insert(cstack0.l);
        // New stack: 0
        // ILOAD 5; Stack: 0
        cstack0.i = clocal5.i;
        // New stack: 1
        // ALOAD 4; Stack: 1
        cstack1.l = clocal4.l; refs.insert(cstack1.l);
        // New stack: 2
        // IFNULL L15; Stack: 2
        if (env->IsSameObject(cstack1.l, nullptr)) goto L15;
        // New stack: 1
        // ALOAD 9; Stack: 1
        cstack1.l = clocal9.l; refs.insert(cstack1.l);
        // New stack: 2
        // ALOAD 4; Stack: 2
        cstack2.l = clocal4.l; refs.insert(cstack2.l);
        // New stack: 3
        // IFNULL L16; Stack: 3
        if (env->IsSameObject(cstack2.l, nullptr)) goto L16;
        // New stack: 2
        // IFNONNULL L17; Stack: 2
        if (!env->IsSameObject(cstack1.l, nullptr)) goto L17;
        // New stack: 1
        // ICONST_0; Stack: 1
        cstack1.i = 0;
        // New stack: 2
        // GOTO L18; Stack: 2
        goto L18;
        // New stack: 2
        // LABEL L17; Stack: 2
        L17: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 2
        // FRAME FULL L: [[Ljava/lang/Object;, 4, [[B, [Ldev/mahiro/client/Mahiro_qT;, 1, [[B, 1, 1, [B] S: [1]; Stack: 2
        refs.erase(clocal0.l); refs.erase(clocal3.l); refs.erase(clocal4.l); refs.erase(clocal6.l); refs.erase(clocal9.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ALOAD 9; Stack: 1
        cstack1.l = clocal9.l; refs.insert(cstack1.l);
        // New stack: 2
        // LABEL L16; Stack: 2
        L16: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 2
        // FRAME FULL L: [[Ljava/lang/Object;, 4, [[B, [Ldev/mahiro/client/Mahiro_qT;, 1, [[B, 1, 1, [B] S: [1, [B]; Stack: 2
        refs.erase(cstack1.l); 
        refs.erase(clocal0.l); refs.erase(clocal3.l); refs.erase(clocal4.l); refs.erase(clocal6.l); refs.erase(clocal9.l); 
        utils::clear_refs(env, refs);
        // New stack: 2
        // ARRAYLENGTH; Stack: 2
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 6026LL)), -1); else cstack1.i = env->GetArrayLength((jarray) cstack1.l); 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 2
        // LABEL L18; Stack: 2
        L18: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 2
        // FRAME FULL L: [[Ljava/lang/Object;, 4, [[B, [Ldev/mahiro/client/Mahiro_qT;, 1, [[B, 1, 1, [B] S: [1, 1]; Stack: 2
        refs.erase(clocal0.l); refs.erase(clocal3.l); refs.erase(clocal4.l); refs.erase(clocal6.l); refs.erase(clocal9.l); 
        utils::clear_refs(env, refs);
        // New stack: 2
        // IADD; Stack: 2
        cstack0.i = cstack0.i + cstack1.i;
        // New stack: 1
        // ISTORE 5; Stack: 1
        clocal5.i = cstack0.i;
        // New stack: 0
        // IINC 8 1; Stack: 0
        clocal8.i += 1;
        // New stack: 0
        // ALOAD 4; Stack: 0
        cstack0.l = clocal4.l; refs.insert(cstack0.l);
        // New stack: 1
        // IFNONNULL L12; Stack: 1
        if (!env->IsSameObject(cstack0.l, nullptr)) goto L12;
        // New stack: 0
        // LABEL L13; Stack: 0
        L13: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 0
        // FRAME CHOP L: [null] S: null; Stack: 0
        refs.erase(clocal0.l); refs.erase(clocal3.l); refs.erase(clocal4.l); refs.erase(clocal6.l); 
        utils::clear_refs(env, refs);
        // New stack: 0
        // ILOAD 5; Stack: 0
        cstack0.i = clocal5.i;
        // New stack: 1
        // LABEL L15; Stack: 1
        L15: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [1]; Stack: 1
        refs.erase(clocal0.l); refs.erase(clocal3.l); refs.erase(clocal4.l); refs.erase(clocal6.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // NEWARRAY 8; Stack: 1
        if (cstack0.i < 0) utils::throw_re(env, ((char *)(string_pool + 251LL)), ((char *)(string_pool + 5414LL)), -1); else { cstack0.l = env->NewByteArray(cstack0.i); refs.insert(cstack0.l); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // LABEL L14; Stack: 1
        L14: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [[B]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal3.l); refs.erase(clocal4.l); refs.erase(clocal6.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ASTORE 6; Stack: 1
        clocal6.l = cstack0.l; refs.insert(cstack0.l);
        // New stack: 0
        // ICONST_0; Stack: 0
        cstack0.i = 0;
        // New stack: 1
        // ISTORE 7; Stack: 1
        clocal7.i = cstack0.i;
        // New stack: 0
        // ALOAD 3; Stack: 0
        cstack0.l = clocal3.l; refs.insert(cstack0.l);
        // New stack: 1
        // ASTORE 8; Stack: 1
        clocal8.l = cstack0.l; refs.insert(cstack0.l);
        // New stack: 0
        // ALOAD 8; Stack: 0
        cstack0.l = clocal8.l; refs.insert(cstack0.l);
        // New stack: 1
        // ARRAYLENGTH; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 6026LL)), -1); else cstack0.i = env->GetArrayLength((jarray) cstack0.l); 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // ISTORE 9; Stack: 1
        clocal9.i = cstack0.i;
        // New stack: 0
        // ICONST_0; Stack: 0
        cstack0.i = 0;
        // New stack: 1
        // ISTORE 10; Stack: 1
        clocal10.i = cstack0.i;
        // New stack: 0
        // LABEL L19; Stack: 0
        L19: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 0
        // FRAME FULL L: [[Ljava/lang/Object;, 4, [[B, [Ldev/mahiro/client/Mahiro_qT;, 1, [B, 1, [[B, 1, 1] S: []; Stack: 0
        refs.erase(clocal0.l); refs.erase(clocal3.l); refs.erase(clocal4.l); refs.erase(clocal6.l); refs.erase(clocal8.l); 
        utils::clear_refs(env, refs);
        // New stack: 0
        // ILOAD 10; Stack: 0
        cstack0.i = clocal10.i;
        // New stack: 1
        // ILOAD 9; Stack: 1
        cstack1.i = clocal9.i;
        // New stack: 2
        // IF_ICMPGE L20; Stack: 2
        if (cstack0.i >= cstack1.i) goto L20;
        // New stack: 0
        // ALOAD 8; Stack: 0
        cstack0.l = clocal8.l; refs.insert(cstack0.l);
        // New stack: 1
        // ILOAD 10; Stack: 1
        cstack1.i = clocal10.i;
        // New stack: 2
        // AALOAD; Stack: 2
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 122LL)), -1); else { cstack0.l = env->GetObjectArrayElement((jobjectArray) cstack0.l, cstack1.i); refs.insert(cstack0.l); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // ASTORE 11; Stack: 1
        clocal11.l = cstack0.l; refs.insert(cstack0.l);
        // New stack: 0
        // ALOAD 4; Stack: 0
        cstack0.l = clocal4.l; refs.insert(cstack0.l);
        // New stack: 1
        // LLOAD 1; Stack: 1
        cstack1.j = clocal1.j;
        // New stack: 3
        // LCONST_0; Stack: 3
        cstack3.j = 0;
        // New stack: 5
        // LCMP; Stack: 5
        cstack1.i = (cstack1.j == cstack3.j) ? 0 : (cstack1.j > cstack3.j ? 1 : -1);
        // New stack: 2
        // IFLE L21; Stack: 2
        if (cstack1.i <= 0) goto L21;
        // New stack: 1
        // IFNULL L22; Stack: 1
        if (env->IsSameObject(cstack0.l, nullptr)) goto L22;
        // New stack: 0
        // ALOAD 11; Stack: 0
        cstack0.l = clocal11.l; refs.insert(cstack0.l);
        // New stack: 1
        // ALOAD 4; Stack: 1
        cstack1.l = clocal4.l; refs.insert(cstack1.l);
        // New stack: 2
        // IFNULL L23; Stack: 2
        if (env->IsSameObject(cstack1.l, nullptr)) goto L23;
        // New stack: 1
        // IFNULL L24; Stack: 1
        if (env->IsSameObject(cstack0.l, nullptr)) goto L24;
        // New stack: 0
        // ALOAD 11; Stack: 0
        cstack0.l = clocal11.l; refs.insert(cstack0.l);
        // New stack: 1
        // ARRAYLENGTH; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 6026LL)), -1); else cstack0.i = env->GetArrayLength((jarray) cstack0.l); 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // ALOAD 4; Stack: 1
        cstack1.l = clocal4.l; refs.insert(cstack1.l);
        // New stack: 2
        // IFNULL L25; Stack: 2
        if (env->IsSameObject(cstack1.l, nullptr)) goto L25;
        // New stack: 1
        // IFNE L26; Stack: 1
        if (cstack0.i != 0) goto L26;
        // New stack: 0
        // GOTO L24; Stack: 0
        goto L24;
        // New stack: 0
        // LABEL L26; Stack: 0
        L26: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 0
        // FRAME APPEND L: [[B] S: null; Stack: 0
        refs.erase(clocal0.l); refs.erase(clocal3.l); refs.erase(clocal4.l); refs.erase(clocal6.l); refs.erase(clocal8.l); refs.erase(clocal11.l); 
        utils::clear_refs(env, refs);
        // New stack: 0
        // ALOAD 11; Stack: 0
        cstack0.l = clocal11.l; refs.insert(cstack0.l);
        // New stack: 1
        // ICONST_0; Stack: 1
        cstack1.i = 0;
        // New stack: 2
        // ALOAD 6; Stack: 2
        cstack2.l = clocal6.l; refs.insert(cstack2.l);
        // New stack: 3
        // ILOAD 7; Stack: 3
        cstack3.i = clocal7.i;
        // New stack: 4
        // ALOAD 11; Stack: 4
        cstack4.l = clocal11.l; refs.insert(cstack4.l);
        // New stack: 5
        // ARRAYLENGTH; Stack: 5
        if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 6026LL)), -1); else cstack4.i = env->GetArrayLength((jarray) cstack4.l); 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 5
        // LDC 8260595419082238596; Stack: 5
        cstack5.j = 8260595419082238596LL;
        // New stack: 7
        // LLOAD 1; Stack: 7
        cstack7.j = clocal1.j;
        // New stack: 9
        // LABEL L5; Stack: 9
        L5: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 9
        // ICONST_1; Stack: 9
        cstack9.i = 1;
        // New stack: 10
        // ANEWARRAY java/lang/Object; Stack: 10
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (cstack9.i < 0) utils::throw_re(env, ((char *)(string_pool + 251LL)), ((char *)(string_pool + 288LL)), -1); else { cstack9.l = env->NewObjectArray(cstack9.i, (cclasses[4]), nullptr); refs.insert(cstack9.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 10
        // DUP; Stack: 10
        cstack10 = cstack9;
        // New stack: 11
        // LDC Ldev/mahiro/client/Mahiro_nh;; Stack: 11
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } cstack11.l = (cclasses[5]);
        // New stack: 12
        // SWAP; Stack: 12
        std::swap(cstack11, cstack10);
        // New stack: 12
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.127771650959966.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 12
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } cstack12.l = lookup;
        // New stack: 13
        // LDC Ldev/mahiro/client/Mahiro_yh;; Stack: 13
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } cstack13.l = (cclasses[6]);
        // New stack: 14
        // LDC a; Stack: 14
        cstack14.l = (cstrings[6]);
        // New stack: 15
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;; Stack: 15
        cstack15.l = (cstrings[7]);
        // New stack: 16
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.127771650959966.a()Ljava/lang/ClassLoader;; Stack: 16
        cstack16.l = classloader;
        // New stack: 17
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 17
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (!cmethods[2]) { cmethods[2] = env->GetStaticMethodID((cclasses[7]), ((char *)(string_pool + 313LL)), ((char *)(string_pool + 340LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }  } cstack15.l = env->CallStaticObjectMethod((cclasses[7]), (cmethods[2]), cstack15.l, cstack16.l); refs.insert(cstack15.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 16
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 16
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (!cmethods[3]) { cmethods[3] = env->GetMethodID((cclasses[8]), ((char *)(string_pool + 413LL)), ((char *)(string_pool + 424LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }  } if (cstack12.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 522LL)), -1); else { cstack12.l = env->CallObjectMethod(cstack12.l, (cmethods[3]), cstack13.l, cstack14.l, cstack15.l); refs.insert(cstack12.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 13
        // SWAP; Stack: 13
        std::swap(cstack12, cstack11);
        // New stack: 13
        // LDC \u00c9; Stack: 13
        cstack13.l = (cstrings[19]);
        // New stack: 14
        // SWAP; Stack: 14
        std::swap(cstack13, cstack12);
        // New stack: 14
        // LDC (Ljava/lang/Object;ILjava/lang/Object;IIJJ)V; Stack: 14
        cstack14.l = (cstrings[21]);
        // New stack: 15
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.127771650959966.a()Ljava/lang/ClassLoader;; Stack: 15
        cstack15.l = classloader;
        // New stack: 16
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 16
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (!cmethods[2]) { cmethods[2] = env->GetStaticMethodID((cclasses[7]), ((char *)(string_pool + 313LL)), ((char *)(string_pool + 340LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }  } cstack14.l = env->CallStaticObjectMethod((cclasses[7]), (cmethods[2]), cstack14.l, cstack15.l); refs.insert(cstack14.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 15
        // SWAP; Stack: 15
        std::swap(cstack14, cstack13);
        // New stack: 15
        // LDC 0; Stack: 15
        cstack15.i = 0;
        // New stack: 16
        // ANEWARRAY java/lang/Object; Stack: 16
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (cstack15.i < 0) utils::throw_re(env, ((char *)(string_pool + 251LL)), ((char *)(string_pool + 288LL)), -1); else { cstack15.l = env->NewObjectArray(cstack15.i, (cclasses[4]), nullptr); refs.insert(cstack15.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 16
        // CHECKCAST java/lang/Object; Stack: 16
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (cstack15.l != nullptr && !env->IsInstanceOf(cstack15.l, (cclasses[4]))) { utils::throw_re(env, ((char *)(string_pool + 133LL)), (std::string(((char *)(string_pool + 162LL))) + std::string(((char *)(string_pool + 547LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } 
        // New stack: 16
        // SWAP; Stack: 16
        std::swap(cstack15, cstack14);
        // New stack: 16
        // INVOKESTATIC native/magic/1/linkcallsite/obfuscator0.127771650959966.a(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/invoke/MemberName;; Stack: 16
        cstack10.l = utils::link_call_site(env, cstack10.l, cstack11.l, cstack12.l, cstack13.l, cstack14.l, cstack15.l); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 11
        // POP; Stack: 11
        ;
        // New stack: 10
        // ICONST_0; Stack: 10
        cstack10.i = 0;
        // New stack: 11
        // AALOAD; Stack: 11
        if (cstack9.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 122LL)), -1); else { cstack9.l = env->GetObjectArrayElement((jobjectArray) cstack9.l, cstack10.i); refs.insert(cstack9.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 10
        // DUP; Stack: 10
        cstack10 = cstack9;
        // New stack: 11
        // INSTANCEOF java/lang/invoke/CallSite; Stack: 11
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } cstack10.i = cstack10.l == nullptr ? false : env->IsInstanceOf(cstack10.l, (cclasses[9]));
        // New stack: 11
        // IFEQ L27; Stack: 11
        if (cstack10.i == 0) goto L27;
        // New stack: 10
        // INVOKEINTERFACE java/lang/invoke/CallSite.getTarget()Ljava/lang/invoke/MethodHandle;; Stack: 10
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 564LL)), ((char *)(string_pool + 574LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }  } if (cstack9.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 608LL)), -1); else { cstack9.l = env->CallObjectMethod(cstack9.l, (cmethods[4])); refs.insert(cstack9.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 10
        // LABEL L27; Stack: 10
        L27: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 10
        // FRAME FULL L: [[Ljava/lang/Object;, 4, [[B, [Ldev/mahiro/client/Mahiro_qT;, 1, [B, 1, [[B, 1, 1, [B] S: [[B, 1, [B, 1, 1, 4, 4, java/lang/Object]; Stack: 10
        refs.erase(cstack0.l); refs.erase(cstack2.l); refs.erase(cstack9.l); 
        refs.erase(clocal0.l); refs.erase(clocal3.l); refs.erase(clocal4.l); refs.erase(clocal6.l); refs.erase(clocal8.l); refs.erase(clocal11.l); 
        utils::clear_refs(env, refs);
        // New stack: 10
        // CHECKCAST java/lang/invoke/MethodHandle; Stack: 10
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (cstack9.l != nullptr && !env->IsInstanceOf(cstack9.l, (cclasses[10]))) { utils::throw_re(env, ((char *)(string_pool + 133LL)), (std::string(((char *)(string_pool + 162LL))) + std::string(((char *)(string_pool + 635LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } 
        // New stack: 10
        // GOTO L28; Stack: 10
        goto L28;
        // New stack: 10
        // LABEL L6; Stack: 10
        L6: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 10
        // FRAME SAME1 L: null S: [java/lang/Throwable]; Stack: 10
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal3.l); refs.erase(clocal4.l); refs.erase(clocal6.l); refs.erase(clocal8.l); refs.erase(clocal11.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // INSTANCEOF java/lang/BootstrapMethodError; Stack: 2
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } cstack1.i = cstack1.l == nullptr ? false : env->IsInstanceOf(cstack1.l, (cclasses[11]));
        // New stack: 2
        // IFNE L29; Stack: 2
        if (cstack1.i != 0) goto L29;
        // New stack: 1
        // NEW java/lang/BootstrapMethodError; Stack: 1
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (jobject obj = env->AllocObject((cclasses[11]))) { cstack1.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
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
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (!cmethods[5]) { cmethods[5] = env->GetMethodID((cclasses[11]), ((char *)(string_pool + 665LL)), ((char *)(string_pool + 672LL))); if (env->ExceptionCheck()) { return (jarray) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 697LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[11]), (cmethods[5]), cstack2.l); 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // LABEL L29; Stack: 1
        L29: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/Object]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal3.l); refs.erase(clocal4.l); refs.erase(clocal6.l); refs.erase(clocal8.l); refs.erase(clocal11.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 720LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 0
        // LABEL L28; Stack: 0
        L28: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 0
        // FRAME FULL L: [[Ljava/lang/Object;, 4, [[B, [Ldev/mahiro/client/Mahiro_qT;, 1, [B, 1, [[B, 1, 1, [B] S: [[B, 1, [B, 1, 1, 4, 4, java/lang/invoke/MethodHandle]; Stack: 0
        refs.erase(cstack0.l); refs.erase(cstack2.l); refs.erase(cstack9.l); 
        refs.erase(clocal0.l); refs.erase(clocal3.l); refs.erase(clocal4.l); refs.erase(clocal6.l); refs.erase(clocal8.l); refs.erase(clocal11.l); 
        utils::clear_refs(env, refs);
        // New stack: 10
        // INVOKESTATIC native/magic/1/invoke/obfuscator0.127771650959966.a(Ljava/lang/Object;ILjava/lang/Object;IIJJLjava/lang/invoke/MethodHandle;)V; Stack: 10
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (!cmethods[8]) { cmethods[8] = env->GetStaticMethodID((cclasses[12]), ((char *)(string_pool + 19946LL)), ((char *)(string_pool + 19962LL))); if (env->ExceptionCheck()) { return (jarray) 0; }  } env->CallStaticVoidMethod((cclasses[12]), (cmethods[8]), cstack0.l, cstack1.i, cstack2.l, cstack3.i, cstack4.i, cstack5.j, cstack7.j, cstack9.l); 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 0
        // ILOAD 7; Stack: 0
        cstack0.i = clocal7.i;
        // New stack: 1
        // ALOAD 11; Stack: 1
        cstack1.l = clocal11.l; refs.insert(cstack1.l);
        // New stack: 2
        // ARRAYLENGTH; Stack: 2
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 6026LL)), -1); else cstack1.i = env->GetArrayLength((jarray) cstack1.l); 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 2
        // IADD; Stack: 2
        cstack0.i = cstack0.i + cstack1.i;
        // New stack: 1
        // LABEL L25; Stack: 1
        L25: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [1]; Stack: 1
        refs.erase(clocal0.l); refs.erase(clocal3.l); refs.erase(clocal4.l); refs.erase(clocal6.l); refs.erase(clocal8.l); refs.erase(clocal11.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ISTORE 7; Stack: 1
        clocal7.i = cstack0.i;
        // New stack: 0
        // LABEL L24; Stack: 0
        L24: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 0
        // FRAME SAME L: null S: null; Stack: 0
        refs.erase(clocal0.l); refs.erase(clocal3.l); refs.erase(clocal4.l); refs.erase(clocal6.l); refs.erase(clocal8.l); refs.erase(clocal11.l); 
        utils::clear_refs(env, refs);
        // New stack: 0
        // IINC 10 1; Stack: 0
        clocal10.i += 1;
        // New stack: 0
        // LABEL L22; Stack: 0
        L22: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 0
        // FRAME SAME L: null S: null; Stack: 0
        refs.erase(clocal0.l); refs.erase(clocal3.l); refs.erase(clocal4.l); refs.erase(clocal6.l); refs.erase(clocal8.l); refs.erase(clocal11.l); 
        utils::clear_refs(env, refs);
        // New stack: 0
        // ALOAD 4; Stack: 0
        cstack0.l = clocal4.l; refs.insert(cstack0.l);
        // New stack: 1
        // LABEL L21; Stack: 1
        L21: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [[Ldev/mahiro/client/Mahiro_qT;]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal3.l); refs.erase(clocal4.l); refs.erase(clocal6.l); refs.erase(clocal8.l); refs.erase(clocal11.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // IFNONNULL L19; Stack: 1
        if (!env->IsSameObject(cstack0.l, nullptr)) goto L19;
        // New stack: 0
        // LABEL L20; Stack: 0
        L20: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 0
        // FRAME CHOP L: [null] S: null; Stack: 0
        refs.erase(clocal0.l); refs.erase(clocal3.l); refs.erase(clocal4.l); refs.erase(clocal6.l); refs.erase(clocal8.l); 
        utils::clear_refs(env, refs);
        // New stack: 0
        // ALOAD 6; Stack: 0
        cstack0.l = clocal6.l; refs.insert(cstack0.l);
        // New stack: 1
        // LABEL L23; Stack: 1
        L23: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [[B]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal3.l); refs.erase(clocal4.l); refs.erase(clocal6.l); refs.erase(clocal8.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // LDC 8281956192826594010; Stack: 1
        cstack1.j = 8281956192826594010LL;
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
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } if (cstack5.i < 0) utils::throw_re(env, ((char *)(string_pool + 251LL)), ((char *)(string_pool + 288LL)), -1); else { cstack5.l = env->NewObjectArray(cstack5.i, (cclasses[4]), nullptr); refs.insert(cstack5.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 6
        // DUP; Stack: 6
        cstack6 = cstack5;
        // New stack: 7
        // LDC Ldev/mahiro/client/Mahiro_nh;; Stack: 7
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } cstack7.l = (cclasses[5]);
        // New stack: 8
        // SWAP; Stack: 8
        std::swap(cstack7, cstack6);
        // New stack: 8
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.127771650959966.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 8
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } cstack8.l = lookup;
        // New stack: 9
        // LDC Ldev/mahiro/client/Mahiro_yh;; Stack: 9
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } cstack9.l = (cclasses[6]);
        // New stack: 10
        // LDC a; Stack: 10
        cstack10.l = (cstrings[6]);
        // New stack: 11
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;; Stack: 11
        cstack11.l = (cstrings[7]);
        // New stack: 12
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.127771650959966.a()Ljava/lang/ClassLoader;; Stack: 12
        cstack12.l = classloader;
        // New stack: 13
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 13
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } if (!cmethods[2]) { cmethods[2] = env->GetStaticMethodID((cclasses[7]), ((char *)(string_pool + 313LL)), ((char *)(string_pool + 340LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }  } cstack11.l = env->CallStaticObjectMethod((cclasses[7]), (cmethods[2]), cstack11.l, cstack12.l); refs.insert(cstack11.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 12
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 12
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } if (!cmethods[3]) { cmethods[3] = env->GetMethodID((cclasses[8]), ((char *)(string_pool + 413LL)), ((char *)(string_pool + 424LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }  } if (cstack8.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 522LL)), -1); else { cstack8.l = env->CallObjectMethod(cstack8.l, (cmethods[3]), cstack9.l, cstack10.l, cstack11.l); refs.insert(cstack8.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 9
        // SWAP; Stack: 9
        std::swap(cstack8, cstack7);
        // New stack: 9
        // LDC \u00c9; Stack: 9
        cstack9.l = (cstrings[19]);
        // New stack: 10
        // SWAP; Stack: 10
        std::swap(cstack9, cstack8);
        // New stack: 10
        // LDC (JJ)[Ljava/lang/String;; Stack: 10
        cstack10.l = (cstrings[22]);
        // New stack: 11
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.127771650959966.a()Ljava/lang/ClassLoader;; Stack: 11
        cstack11.l = classloader;
        // New stack: 12
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 12
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } if (!cmethods[2]) { cmethods[2] = env->GetStaticMethodID((cclasses[7]), ((char *)(string_pool + 313LL)), ((char *)(string_pool + 340LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }  } cstack10.l = env->CallStaticObjectMethod((cclasses[7]), (cmethods[2]), cstack10.l, cstack11.l); refs.insert(cstack10.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 11
        // SWAP; Stack: 11
        std::swap(cstack10, cstack9);
        // New stack: 11
        // LDC 0; Stack: 11
        cstack11.i = 0;
        // New stack: 12
        // ANEWARRAY java/lang/Object; Stack: 12
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } if (cstack11.i < 0) utils::throw_re(env, ((char *)(string_pool + 251LL)), ((char *)(string_pool + 288LL)), -1); else { cstack11.l = env->NewObjectArray(cstack11.i, (cclasses[4]), nullptr); refs.insert(cstack11.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 12
        // CHECKCAST java/lang/Object; Stack: 12
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } if (cstack11.l != nullptr && !env->IsInstanceOf(cstack11.l, (cclasses[4]))) { utils::throw_re(env, ((char *)(string_pool + 133LL)), (std::string(((char *)(string_pool + 162LL))) + std::string(((char *)(string_pool + 547LL)))).c_str(), -1); 
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
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } cstack6.i = cstack6.l == nullptr ? false : env->IsInstanceOf(cstack6.l, (cclasses[9]));
        // New stack: 7
        // IFEQ L30; Stack: 7
        if (cstack6.i == 0) goto L30;
        // New stack: 6
        // INVOKEINTERFACE java/lang/invoke/CallSite.getTarget()Ljava/lang/invoke/MethodHandle;; Stack: 6
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 564LL)), ((char *)(string_pool + 574LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }  } if (cstack5.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 608LL)), -1); else { cstack5.l = env->CallObjectMethod(cstack5.l, (cmethods[4])); refs.insert(cstack5.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 6
        // LABEL L30; Stack: 6
        L30: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 6
        // FRAME FULL L: [[Ljava/lang/Object;, 4, [[B, [Ldev/mahiro/client/Mahiro_qT;, 1, [B, 1, [[B, 1, 1] S: [[B, 4, 4, java/lang/Object]; Stack: 6
        refs.erase(cstack0.l); refs.erase(cstack5.l); 
        refs.erase(clocal0.l); refs.erase(clocal3.l); refs.erase(clocal4.l); refs.erase(clocal6.l); refs.erase(clocal8.l); 
        utils::clear_refs(env, refs);
        // New stack: 6
        // CHECKCAST java/lang/invoke/MethodHandle; Stack: 6
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } if (cstack5.l != nullptr && !env->IsInstanceOf(cstack5.l, (cclasses[10]))) { utils::throw_re(env, ((char *)(string_pool + 133LL)), (std::string(((char *)(string_pool + 162LL))) + std::string(((char *)(string_pool + 635LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } 
        // New stack: 6
        // GOTO L31; Stack: 6
        goto L31;
        // New stack: 6
        // LABEL L4; Stack: 6
        L4: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 6
        // FRAME SAME1 L: null S: [java/lang/Throwable]; Stack: 6
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal3.l); refs.erase(clocal4.l); refs.erase(clocal6.l); refs.erase(clocal8.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // INSTANCEOF java/lang/BootstrapMethodError; Stack: 2
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } cstack1.i = cstack1.l == nullptr ? false : env->IsInstanceOf(cstack1.l, (cclasses[11]));
        // New stack: 2
        // IFNE L32; Stack: 2
        if (cstack1.i != 0) goto L32;
        // New stack: 1
        // NEW java/lang/BootstrapMethodError; Stack: 1
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (jobject obj = env->AllocObject((cclasses[11]))) { cstack1.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
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
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (!cmethods[5]) { cmethods[5] = env->GetMethodID((cclasses[11]), ((char *)(string_pool + 665LL)), ((char *)(string_pool + 672LL))); if (env->ExceptionCheck()) { return (jarray) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 697LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[11]), (cmethods[5]), cstack2.l); 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // LABEL L32; Stack: 1
        L32: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/Object]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal3.l); refs.erase(clocal4.l); refs.erase(clocal6.l); refs.erase(clocal8.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 720LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 0
        // LABEL L31; Stack: 0
        L31: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 0
        // FRAME FULL L: [[Ljava/lang/Object;, 4, [[B, [Ldev/mahiro/client/Mahiro_qT;, 1, [B, 1, [[B, 1, 1] S: [[B, 4, 4, java/lang/invoke/MethodHandle]; Stack: 0
        refs.erase(cstack0.l); refs.erase(cstack5.l); 
        refs.erase(clocal0.l); refs.erase(clocal3.l); refs.erase(clocal4.l); refs.erase(clocal6.l); refs.erase(clocal8.l); 
        utils::clear_refs(env, refs);
        // New stack: 6
        // INVOKESTATIC native/magic/1/invoke/obfuscator0.127771650959966.a(JJLjava/lang/invoke/MethodHandle;)[Ljava/lang/String;; Stack: 6
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (!cmethods[6]) { cmethods[6] = env->GetStaticMethodID((cclasses[12]), ((char *)(string_pool + 2157LL)), ((char *)(string_pool + 2173LL))); if (env->ExceptionCheck()) { return (jarray) 0; }  } cstack1.l = env->CallStaticObjectMethod((cclasses[12]), (cmethods[6]), cstack1.j, cstack3.j, cstack5.l); refs.insert(cstack1.l); 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 2
        // CHECKCAST [Ljava/lang/String;; Stack: 2
        if (!cclasses[16] || env->IsSameObject(cclasses[16], NULL)) { cclasses_mtx[16].lock(); if (!cclasses[16] || env->IsSameObject(cclasses[16], NULL)) { if (jclass clazz = env->FindClass(((char *)(string_pool + 5703LL)))) { cclasses[16] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[16].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (cstack1.l != nullptr && !env->IsInstanceOf(cstack1.l, (cclasses[16]))) { utils::throw_re(env, ((char *)(string_pool + 133LL)), (std::string(((char *)(string_pool + 162LL))) + std::string(((char *)(string_pool + 5703LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (jarray) 0; } } 
        // New stack: 2
        // IFNULL L33; Stack: 2
        if (env->IsSameObject(cstack1.l, nullptr)) goto L33;
        // New stack: 1
        // ICONST_4; Stack: 1
        cstack1.i = 4;
        // New stack: 2
        // ANEWARRAY dev/mahiro/client/Mahiro_qT; Stack: 2
        if (!cclasses[17] || env->IsSameObject(cclasses[17], NULL)) { cclasses_mtx[17].lock(); if (!cclasses[17] || env->IsSameObject(cclasses[17], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[23]))) { cclasses[17] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[17].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (cstack1.i < 0) utils::throw_re(env, ((char *)(string_pool + 251LL)), ((char *)(string_pool + 288LL)), -1); else { cstack1.l = env->NewObjectArray(cstack1.i, (cclasses[17]), nullptr); refs.insert(cstack1.l); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 2
        // LDC 8283083946526715770; Stack: 2
        cstack2.j = 8283083946526715770LL;
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
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } if (cstack6.i < 0) utils::throw_re(env, ((char *)(string_pool + 251LL)), ((char *)(string_pool + 288LL)), -1); else { cstack6.l = env->NewObjectArray(cstack6.i, (cclasses[4]), nullptr); refs.insert(cstack6.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 7
        // DUP; Stack: 7
        cstack7 = cstack6;
        // New stack: 8
        // LDC Ldev/mahiro/client/Mahiro_nh;; Stack: 8
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } cstack8.l = (cclasses[5]);
        // New stack: 9
        // SWAP; Stack: 9
        std::swap(cstack8, cstack7);
        // New stack: 9
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.127771650959966.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 9
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } cstack9.l = lookup;
        // New stack: 10
        // LDC Ldev/mahiro/client/Mahiro_yh;; Stack: 10
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } cstack10.l = (cclasses[6]);
        // New stack: 11
        // LDC a; Stack: 11
        cstack11.l = (cstrings[6]);
        // New stack: 12
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;; Stack: 12
        cstack12.l = (cstrings[7]);
        // New stack: 13
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.127771650959966.a()Ljava/lang/ClassLoader;; Stack: 13
        cstack13.l = classloader;
        // New stack: 14
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 14
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } if (!cmethods[2]) { cmethods[2] = env->GetStaticMethodID((cclasses[7]), ((char *)(string_pool + 313LL)), ((char *)(string_pool + 340LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }  } cstack12.l = env->CallStaticObjectMethod((cclasses[7]), (cmethods[2]), cstack12.l, cstack13.l); refs.insert(cstack12.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 13
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 13
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } if (!cmethods[3]) { cmethods[3] = env->GetMethodID((cclasses[8]), ((char *)(string_pool + 413LL)), ((char *)(string_pool + 424LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }  } if (cstack9.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 522LL)), -1); else { cstack9.l = env->CallObjectMethod(cstack9.l, (cmethods[3]), cstack10.l, cstack11.l, cstack12.l); refs.insert(cstack9.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 10
        // SWAP; Stack: 10
        std::swap(cstack9, cstack8);
        // New stack: 10
        // LDC \u00c9; Stack: 10
        cstack10.l = (cstrings[19]);
        // New stack: 11
        // SWAP; Stack: 11
        std::swap(cstack10, cstack9);
        // New stack: 11
        // LDC (Ljava/lang/Object;JJ)V; Stack: 11
        cstack11.l = (cstrings[24]);
        // New stack: 12
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.127771650959966.a()Ljava/lang/ClassLoader;; Stack: 12
        cstack12.l = classloader;
        // New stack: 13
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 13
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } if (!cmethods[2]) { cmethods[2] = env->GetStaticMethodID((cclasses[7]), ((char *)(string_pool + 313LL)), ((char *)(string_pool + 340LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }  } cstack11.l = env->CallStaticObjectMethod((cclasses[7]), (cmethods[2]), cstack11.l, cstack12.l); refs.insert(cstack11.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 12
        // SWAP; Stack: 12
        std::swap(cstack11, cstack10);
        // New stack: 12
        // LDC 0; Stack: 12
        cstack12.i = 0;
        // New stack: 13
        // ANEWARRAY java/lang/Object; Stack: 13
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } if (cstack12.i < 0) utils::throw_re(env, ((char *)(string_pool + 251LL)), ((char *)(string_pool + 288LL)), -1); else { cstack12.l = env->NewObjectArray(cstack12.i, (cclasses[4]), nullptr); refs.insert(cstack12.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 13
        // CHECKCAST java/lang/Object; Stack: 13
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } if (cstack12.l != nullptr && !env->IsInstanceOf(cstack12.l, (cclasses[4]))) { utils::throw_re(env, ((char *)(string_pool + 133LL)), (std::string(((char *)(string_pool + 162LL))) + std::string(((char *)(string_pool + 547LL)))).c_str(), -1); 
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
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } cstack7.i = cstack7.l == nullptr ? false : env->IsInstanceOf(cstack7.l, (cclasses[9]));
        // New stack: 8
        // IFEQ L34; Stack: 8
        if (cstack7.i == 0) goto L34;
        // New stack: 7
        // INVOKEINTERFACE java/lang/invoke/CallSite.getTarget()Ljava/lang/invoke/MethodHandle;; Stack: 7
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 564LL)), ((char *)(string_pool + 574LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }  } if (cstack6.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 608LL)), -1); else { cstack6.l = env->CallObjectMethod(cstack6.l, (cmethods[4])); refs.insert(cstack6.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 7
        // LABEL L34; Stack: 7
        L34: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 7
        // FRAME FULL L: [[Ljava/lang/Object;, 4, [[B, [Ldev/mahiro/client/Mahiro_qT;, 1, [B, 1, [[B, 1, 1] S: [[B, [Ldev/mahiro/client/Mahiro_qT;, 4, 4, java/lang/Object]; Stack: 7
        refs.erase(cstack0.l); refs.erase(cstack1.l); refs.erase(cstack6.l); 
        refs.erase(clocal0.l); refs.erase(clocal3.l); refs.erase(clocal4.l); refs.erase(clocal6.l); refs.erase(clocal8.l); 
        utils::clear_refs(env, refs);
        // New stack: 7
        // CHECKCAST java/lang/invoke/MethodHandle; Stack: 7
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } if (cstack6.l != nullptr && !env->IsInstanceOf(cstack6.l, (cclasses[10]))) { utils::throw_re(env, ((char *)(string_pool + 133LL)), (std::string(((char *)(string_pool + 162LL))) + std::string(((char *)(string_pool + 635LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } 
        // New stack: 7
        // GOTO L35; Stack: 7
        goto L35;
        // New stack: 7
        // LABEL L2; Stack: 7
        L2: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 7
        // FRAME SAME1 L: null S: [java/lang/Throwable]; Stack: 7
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal3.l); refs.erase(clocal4.l); refs.erase(clocal6.l); refs.erase(clocal8.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // INSTANCEOF java/lang/BootstrapMethodError; Stack: 2
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } cstack1.i = cstack1.l == nullptr ? false : env->IsInstanceOf(cstack1.l, (cclasses[11]));
        // New stack: 2
        // IFNE L36; Stack: 2
        if (cstack1.i != 0) goto L36;
        // New stack: 1
        // NEW java/lang/BootstrapMethodError; Stack: 1
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (jobject obj = env->AllocObject((cclasses[11]))) { cstack1.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
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
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (!cmethods[5]) { cmethods[5] = env->GetMethodID((cclasses[11]), ((char *)(string_pool + 665LL)), ((char *)(string_pool + 672LL))); if (env->ExceptionCheck()) { return (jarray) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 697LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[11]), (cmethods[5]), cstack2.l); 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // LABEL L36; Stack: 1
        L36: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/Object]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal3.l); refs.erase(clocal4.l); refs.erase(clocal6.l); refs.erase(clocal8.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 720LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 0
        // LABEL L35; Stack: 0
        L35: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 0
        // FRAME FULL L: [[Ljava/lang/Object;, 4, [[B, [Ldev/mahiro/client/Mahiro_qT;, 1, [B, 1, [[B, 1, 1] S: [[B, [Ldev/mahiro/client/Mahiro_qT;, 4, 4, java/lang/invoke/MethodHandle]; Stack: 0
        refs.erase(cstack0.l); refs.erase(cstack1.l); refs.erase(cstack6.l); 
        refs.erase(clocal0.l); refs.erase(clocal3.l); refs.erase(clocal4.l); refs.erase(clocal6.l); refs.erase(clocal8.l); 
        utils::clear_refs(env, refs);
        // New stack: 7
        // INVOKESTATIC native/magic/1/invoke/obfuscator0.127771650959966.a(Ljava/lang/Object;JJLjava/lang/invoke/MethodHandle;)V; Stack: 7
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (!cmethods[9]) { cmethods[9] = env->GetStaticMethodID((cclasses[12]), ((char *)(string_pool + 1997LL)), ((char *)(string_pool + 2013LL))); if (env->ExceptionCheck()) { return (jarray) 0; }  } env->CallStaticVoidMethod((cclasses[12]), (cmethods[9]), cstack1.l, cstack2.j, cstack4.j, cstack6.l); 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // LABEL L33; Stack: 1
        L33: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [[B]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal3.l); refs.erase(clocal4.l); refs.erase(clocal6.l); refs.erase(clocal8.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ARETURN; Stack: 1
        return (jarray) cstack0.l;
        // New stack: 0
        return (jarray) 0;
        L_CATCH_0: if (env->IsInstanceOf(cstack0.l, (cclasses[0]))) { goto L8; }
        env->Throw((jthrowable) cstack0.l); return (jarray) 0;
        L_CATCH_1: if (env->IsInstanceOf(cstack0.l, (cclasses[0]))) { goto L6; }
        env->Throw((jthrowable) cstack0.l); return (jarray) 0;
        L_CATCH_3: if (env->IsInstanceOf(cstack0.l, (cclasses[0]))) { goto L2; }
        env->Throw((jthrowable) cstack0.l); return (jarray) 0;
        L_CATCH_2: if (env->IsInstanceOf(cstack0.l, (cclasses[0]))) { goto L4; }
        env->Throw((jthrowable) cstack0.l); return (jarray) 0;
    }
    
    // <clinit>()V
    void JNICALL __ngen_special_clinit_4_3(JNIEnv *env, jobject ignored_hidden, jclass clazz) {
        env->DeleteLocalRef(ignored_hidden);
        jobject classloader = utils::get_classloader_from_class(env, clazz);
        if (env->ExceptionCheck()) { return (void) 0; }
        if (classloader == nullptr) { env->FatalError(((char *)(string_pool + 71LL))); return (void) 0; }
    
        jobject lookup = nullptr;
        // try-catch-class java/lang/Throwable
        if (!cclasses[0] || env->IsSameObject(cclasses[0], NULL)) { cclasses_mtx[0].lock(); if (!cclasses[0] || env->IsSameObject(cclasses[0], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[0]))) { cclasses[0] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[0].unlock(); if (env->ExceptionCheck()) { return (void) 0; } }
        jvalue cstack0 = {}, cstack1 = {}, cstack2 = {}, cstack3 = {}, cstack4 = {}, cstack5 = {}, cstack6 = {}, cstack7 = {}, cstack8 = {}, cstack9 = {}, cstack10 = {}, cstack11 = {}, cstack12 = {};
        jvalue clocal0 = {}, clocal1 = {};
        std::unordered_set<jobject> refs;
    
    
        // LDC 3867502470561380638; Stack: 0
        cstack0.j = 3867502470561380638LL;
        // New stack: 2
        // LDC 4896877651845014533; Stack: 2
        cstack2.j = 4896877651845014533LL;
        // New stack: 4
        // INVOKESTATIC java/lang/invoke/MethodHandles.lookup()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 4
        if (!cclasses[18] || env->IsSameObject(cclasses[18], NULL)) { cclasses_mtx[18].lock(); if (!cclasses[18] || env->IsSameObject(cclasses[18], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[25]))) { cclasses[18] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[18].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[10]) { cmethods[10] = env->GetStaticMethodID((cclasses[18]), ((char *)(string_pool + 5142LL)), ((char *)(string_pool + 5149LL))); if (env->ExceptionCheck()) { return (void) 0; }  } cstack4.l = env->CallStaticObjectMethod((cclasses[18]), (cmethods[10])); refs.insert(cstack4.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 5
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.lookupClass()Ljava/lang/Class;; Stack: 5
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[11]) { cmethods[11] = env->GetMethodID((cclasses[8]), ((char *)(string_pool + 5191LL)), ((char *)(string_pool + 3163LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 522LL)), -1); else { cstack4.l = env->CallObjectMethod(cstack4.l, (cmethods[11])); refs.insert(cstack4.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 5
        // INVOKESTATIC dev/mahiro/client/Mahiro__i.a(JJLjava/lang/Object;)Ldev/mahiro/client/Mahiro__g;; Stack: 5
        if (!cclasses[19] || env->IsSameObject(cclasses[19], NULL)) { cclasses_mtx[19].lock(); if (!cclasses[19] || env->IsSameObject(cclasses[19], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[26]))) { cclasses[19] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[19].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[12]) { cmethods[12] = env->GetStaticMethodID((cclasses[19]), ((char *)(string_pool + 247LL)), ((char *)(string_pool + 5203LL))); if (env->ExceptionCheck()) { return (void) 0; }  } cstack0.l = env->CallStaticObjectMethod((cclasses[19]), (cmethods[12]), cstack0.j, cstack2.j, cstack4.l); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // LDC 151477371971995; Stack: 1
        cstack1.j = 151477371971995LL;
        // New stack: 3
        // INVOKEINTERFACE dev/mahiro/client/Mahiro__g.a(J)J; Stack: 3
        if (!cclasses[20] || env->IsSameObject(cclasses[20], NULL)) { cclasses_mtx[20].lock(); if (!cclasses[20] || env->IsSameObject(cclasses[20], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[27]))) { cclasses[20] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[20].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[13]) { cmethods[13] = env->GetMethodID((cclasses[20]), ((char *)(string_pool + 247LL)), ((char *)(string_pool + 5255LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 5260LL)), -1); else cstack0.j = env->CallLongMethod(cstack0.l, (cmethods[13]), cstack1.j); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 2
        // PUTSTATIC dev/mahiro/client/Mahiro_nh.a J; Stack: 2
        if (!cclasses[3]  || env->IsSameObject(cclasses[3], NULL)) { cclasses_mtx[3].lock(); if (!cclasses[3] || env->IsSameObject(cclasses[3], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[3] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[3].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cfields[0]) { cfields[0] = env->GetStaticFieldID((cclasses[3]), ((char *)(string_pool + 247LL)), ((char *)(string_pool + 249LL))); if (env->ExceptionCheck()) { return (void) 0; }  } env->SetStaticLongField((cclasses[3]), (cfields[0]), cstack0.j); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // GETSTATIC dev/mahiro/client/Mahiro_nh.a J; Stack: 0
        if (!cclasses[3]  || env->IsSameObject(cclasses[3], NULL)) { cclasses_mtx[3].lock(); if (!cclasses[3] || env->IsSameObject(cclasses[3], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[3] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[3].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cfields[0]) { cfields[0] = env->GetStaticFieldID((cclasses[3]), ((char *)(string_pool + 247LL)), ((char *)(string_pool + 249LL))); if (env->ExceptionCheck()) { return (void) 0; }  } cstack0.j = env->GetStaticLongField((cclasses[3]), (cfields[0])); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 2
        // LDC 40642916976362; Stack: 2
        cstack2.j = 40642916976362LL;
        // New stack: 4
        // LXOR; Stack: 4
        cstack0.j = cstack0.j ^ cstack2.j;
        // New stack: 2
        // LSTORE 0; Stack: 2
        clocal0.j = cstack0.j;
        // New stack: 0
        // ICONST_1; Stack: 0
        cstack0.i = 1;
        // New stack: 1
        // ANEWARRAY dev/mahiro/client/Mahiro_qT; Stack: 1
        if (!cclasses[17] || env->IsSameObject(cclasses[17], NULL)) { cclasses_mtx[17].lock(); if (!cclasses[17] || env->IsSameObject(cclasses[17], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[23]))) { cclasses[17] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[17].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (cstack0.i < 0) utils::throw_re(env, ((char *)(string_pool + 251LL)), ((char *)(string_pool + 288LL)), -1); else { cstack0.l = env->NewObjectArray(cstack0.i, (cclasses[17]), nullptr); refs.insert(cstack0.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // NEW java/security/SecureRandom; Stack: 1
        if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { cclasses_mtx[13].lock(); if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[16]))) { cclasses[13] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[13].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (jobject obj = env->AllocObject((cclasses[13]))) { cstack1.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 2
        // DUP; Stack: 2
        cstack2 = cstack1;
        // New stack: 3
        // INVOKESPECIAL java/security/SecureRandom.<init>()V; Stack: 3
        if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { cclasses_mtx[13].lock(); if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[16]))) { cclasses[13] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[13].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[14]) { cmethods[14] = env->GetMethodID((cclasses[13]), ((char *)(string_pool + 665LL)), ((char *)(string_pool + 2376LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack2.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 697LL)), -1); else env->CallNonvirtualVoidMethod(cstack2.l, (cclasses[13]), (cmethods[14])); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 2
        // PUTSTATIC dev/mahiro/client/Mahiro_nh.Mahiro_V Ljava/security/SecureRandom;; Stack: 2
        if (!cclasses[3]  || env->IsSameObject(cclasses[3], NULL)) { cclasses_mtx[3].lock(); if (!cclasses[3] || env->IsSameObject(cclasses[3], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[3] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[3].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cfields[1]) { cfields[1] = env->GetStaticFieldID((cclasses[3]), ((char *)(string_pool + 20038LL)), ((char *)(string_pool + 20047LL))); if (env->ExceptionCheck()) { return (void) 0; }  } env->SetStaticObjectField((cclasses[3]), (cfields[1]), cstack1.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // LDC -735478136524396478; Stack: 1
        cstack1.j = -735478136524396478LL;
        // New stack: 3
        // LLOAD 0; Stack: 3
        cstack3.j = clocal0.j;
        // New stack: 5
        // LABEL L1; Stack: 5
        L1: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 5
        // ICONST_1; Stack: 5
        cstack5.i = 1;
        // New stack: 6
        // ANEWARRAY java/lang/Object; Stack: 6
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack5.i < 0) utils::throw_re(env, ((char *)(string_pool + 251LL)), ((char *)(string_pool + 288LL)), -1); else { cstack5.l = env->NewObjectArray(cstack5.i, (cclasses[4]), nullptr); refs.insert(cstack5.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 6
        // DUP; Stack: 6
        cstack6 = cstack5;
        // New stack: 7
        // LDC Ldev/mahiro/client/Mahiro_nh;; Stack: 7
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack7.l = (cclasses[5]);
        // New stack: 8
        // SWAP; Stack: 8
        std::swap(cstack7, cstack6);
        // New stack: 8
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.127771650959966.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 8
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack8.l = lookup;
        // New stack: 9
        // LDC Ldev/mahiro/client/Mahiro_yh;; Stack: 9
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack9.l = (cclasses[6]);
        // New stack: 10
        // LDC a; Stack: 10
        cstack10.l = (cstrings[6]);
        // New stack: 11
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;; Stack: 11
        cstack11.l = (cstrings[7]);
        // New stack: 12
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.127771650959966.a()Ljava/lang/ClassLoader;; Stack: 12
        cstack12.l = classloader;
        // New stack: 13
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 13
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[2]) { cmethods[2] = env->GetStaticMethodID((cclasses[7]), ((char *)(string_pool + 313LL)), ((char *)(string_pool + 340LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack11.l = env->CallStaticObjectMethod((cclasses[7]), (cmethods[2]), cstack11.l, cstack12.l); refs.insert(cstack11.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 12
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 12
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[3]) { cmethods[3] = env->GetMethodID((cclasses[8]), ((char *)(string_pool + 413LL)), ((char *)(string_pool + 424LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack8.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 522LL)), -1); else { cstack8.l = env->CallObjectMethod(cstack8.l, (cmethods[3]), cstack9.l, cstack10.l, cstack11.l); refs.insert(cstack8.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 9
        // SWAP; Stack: 9
        std::swap(cstack8, cstack7);
        // New stack: 9
        // LDC \u00c9; Stack: 9
        cstack9.l = (cstrings[19]);
        // New stack: 10
        // SWAP; Stack: 10
        std::swap(cstack9, cstack8);
        // New stack: 10
        // LDC (Ljava/lang/Object;JJ)V; Stack: 10
        cstack10.l = (cstrings[24]);
        // New stack: 11
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.127771650959966.a()Ljava/lang/ClassLoader;; Stack: 11
        cstack11.l = classloader;
        // New stack: 12
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 12
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[2]) { cmethods[2] = env->GetStaticMethodID((cclasses[7]), ((char *)(string_pool + 313LL)), ((char *)(string_pool + 340LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack10.l = env->CallStaticObjectMethod((cclasses[7]), (cmethods[2]), cstack10.l, cstack11.l); refs.insert(cstack10.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 11
        // SWAP; Stack: 11
        std::swap(cstack10, cstack9);
        // New stack: 11
        // LDC 0; Stack: 11
        cstack11.i = 0;
        // New stack: 12
        // ANEWARRAY java/lang/Object; Stack: 12
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack11.i < 0) utils::throw_re(env, ((char *)(string_pool + 251LL)), ((char *)(string_pool + 288LL)), -1); else { cstack11.l = env->NewObjectArray(cstack11.i, (cclasses[4]), nullptr); refs.insert(cstack11.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 12
        // CHECKCAST java/lang/Object; Stack: 12
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack11.l != nullptr && !env->IsInstanceOf(cstack11.l, (cclasses[4]))) { utils::throw_re(env, ((char *)(string_pool + 133LL)), (std::string(((char *)(string_pool + 162LL))) + std::string(((char *)(string_pool + 547LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } 
        // New stack: 12
        // SWAP; Stack: 12
        std::swap(cstack11, cstack10);
        // New stack: 12
        // INVOKESTATIC native/magic/1/linkcallsite/obfuscator0.127771650959966.a(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/invoke/MemberName;; Stack: 12
        cstack6.l = utils::link_call_site(env, cstack6.l, cstack7.l, cstack8.l, cstack9.l, cstack10.l, cstack11.l); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 7
        // POP; Stack: 7
        ;
        // New stack: 6
        // ICONST_0; Stack: 6
        cstack6.i = 0;
        // New stack: 7
        // AALOAD; Stack: 7
        if (cstack5.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 122LL)), -1); else { cstack5.l = env->GetObjectArrayElement((jobjectArray) cstack5.l, cstack6.i); refs.insert(cstack5.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 6
        // DUP; Stack: 6
        cstack6 = cstack5;
        // New stack: 7
        // INSTANCEOF java/lang/invoke/CallSite; Stack: 7
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack6.i = cstack6.l == nullptr ? false : env->IsInstanceOf(cstack6.l, (cclasses[9]));
        // New stack: 7
        // IFEQ L3; Stack: 7
        if (cstack6.i == 0) goto L3;
        // New stack: 6
        // INVOKEINTERFACE java/lang/invoke/CallSite.getTarget()Ljava/lang/invoke/MethodHandle;; Stack: 6
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 564LL)), ((char *)(string_pool + 574LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack5.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 608LL)), -1); else { cstack5.l = env->CallObjectMethod(cstack5.l, (cmethods[4])); refs.insert(cstack5.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 6
        // LABEL L3; Stack: 6
        L3: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 6
        // FRAME FULL L: [4] S: [[Ldev/mahiro/client/Mahiro_qT;, 4, 4, java/lang/Object]; Stack: 6
        refs.erase(cstack0.l); refs.erase(cstack5.l); 
        utils::clear_refs(env, refs);
        // New stack: 6
        // CHECKCAST java/lang/invoke/MethodHandle; Stack: 6
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack5.l != nullptr && !env->IsInstanceOf(cstack5.l, (cclasses[10]))) { utils::throw_re(env, ((char *)(string_pool + 133LL)), (std::string(((char *)(string_pool + 162LL))) + std::string(((char *)(string_pool + 635LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } 
        // New stack: 6
        // GOTO L4; Stack: 6
        goto L4;
        // New stack: 6
        // LABEL L2; Stack: 6
        L2: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 6
        // FRAME SAME1 L: null S: [java/lang/Throwable]; Stack: 6
        refs.erase(cstack0.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // INSTANCEOF java/lang/BootstrapMethodError; Stack: 2
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } cstack1.i = cstack1.l == nullptr ? false : env->IsInstanceOf(cstack1.l, (cclasses[11]));
        // New stack: 2
        // IFNE L5; Stack: 2
        if (cstack1.i != 0) goto L5;
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
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[5]) { cmethods[5] = env->GetMethodID((cclasses[11]), ((char *)(string_pool + 665LL)), ((char *)(string_pool + 672LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 697LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[11]), (cmethods[5]), cstack2.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // LABEL L5; Stack: 1
        L5: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/Object]; Stack: 1
        refs.erase(cstack0.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 720LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // LABEL L4; Stack: 0
        L4: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // FRAME FULL L: [4] S: [[Ldev/mahiro/client/Mahiro_qT;, 4, 4, java/lang/invoke/MethodHandle]; Stack: 0
        refs.erase(cstack0.l); refs.erase(cstack5.l); 
        utils::clear_refs(env, refs);
        // New stack: 6
        // INVOKESTATIC native/magic/1/invoke/obfuscator0.127771650959966.a(Ljava/lang/Object;JJLjava/lang/invoke/MethodHandle;)V; Stack: 6
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[9]) { cmethods[9] = env->GetStaticMethodID((cclasses[12]), ((char *)(string_pool + 1997LL)), ((char *)(string_pool + 2013LL))); if (env->ExceptionCheck()) { return (void) 0; }  } env->CallStaticVoidMethod((cclasses[12]), (cmethods[9]), cstack0.l, cstack1.j, cstack3.j, cstack5.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // RETURN; Stack: 0
        return;
        // New stack: 0
        return (void) 0;
        L_CATCH_0: if (env->IsInstanceOf(cstack0.l, (cclasses[0]))) { goto L2; }
        env->Throw((jthrowable) cstack0.l); return (void) 0;
    }
    
    // Mahiro_P([Ldev/mahiro/client/Mahiro_qT;)V
    void JNICALL __ngen_native_Mahiro_P4(JNIEnv *env, jclass clazz, jarray arg0) {
        jobject classloader = utils::get_classloader_from_class(env, clazz);
        if (env->ExceptionCheck()) { return (void) 0; }
        if (classloader == nullptr) { env->FatalError(((char *)(string_pool + 71LL))); return (void) 0; }
    
        jobject lookup = nullptr;
        jvalue cstack0 = {};
        jvalue clocal0 = {};
        std::unordered_set<jobject> refs;
    
        clocal0.l = arg0; refs.insert(clocal0.l);
    
        // ALOAD 0; Stack: 0
        cstack0.l = clocal0.l; refs.insert(cstack0.l);
        // New stack: 1
        // PUTSTATIC dev/mahiro/client/Mahiro_nh.Mahiro_H [Ldev/mahiro/client/Mahiro_qT;; Stack: 1
        if (!cclasses[3]  || env->IsSameObject(cclasses[3], NULL)) { cclasses_mtx[3].lock(); if (!cclasses[3] || env->IsSameObject(cclasses[3], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[3] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[3].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cfields[2]) { cfields[2] = env->GetStaticFieldID((cclasses[3]), ((char *)(string_pool + 20110LL)), ((char *)(string_pool + 17465LL))); if (env->ExceptionCheck()) { return (void) 0; }  } env->SetStaticObjectField((cclasses[3]), (cfields[2]), cstack0.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // RETURN; Stack: 0
        return;
        // New stack: 0
        return (void) 0;
    }
    
    // Mahiro_u()[Ldev/mahiro/client/Mahiro_qT;
    jarray JNICALL __ngen_native_Mahiro_u5(JNIEnv *env, jclass clazz) {
        jobject classloader = utils::get_classloader_from_class(env, clazz);
        if (env->ExceptionCheck()) { return (jarray) 0; }
        if (classloader == nullptr) { env->FatalError(((char *)(string_pool + 71LL))); return (jarray) 0; }
    
        jobject lookup = nullptr;
        jvalue cstack0 = {};
        std::unordered_set<jobject> refs;
    
    
        // GETSTATIC dev/mahiro/client/Mahiro_nh.Mahiro_H [Ldev/mahiro/client/Mahiro_qT;; Stack: 0
        if (!cclasses[3]  || env->IsSameObject(cclasses[3], NULL)) { cclasses_mtx[3].lock(); if (!cclasses[3] || env->IsSameObject(cclasses[3], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[3] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[3].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (!cfields[2]) { cfields[2] = env->GetStaticFieldID((cclasses[3]), ((char *)(string_pool + 20110LL)), ((char *)(string_pool + 17465LL))); if (env->ExceptionCheck()) { return (jarray) 0; }  } cstack0.l = env->GetStaticObjectField((cclasses[3]), (cfields[2])); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // ARETURN; Stack: 1
        return (jarray) cstack0.l;
        // New stack: 0
        return (jarray) 0;
    }
    
    
    void __ngen_register_methods(JNIEnv *env, jclass clazz) {
        string_pool = string_pool::get_pool();

        if (jstring str = env->NewStringUTF(((char *)(string_pool + 8045LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[2] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 8217LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[16] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 20161LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[22] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 8244LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[1] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 6784LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[7] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 8418LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[9] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 8508LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[25] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 8714LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[12] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 9052LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[0] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 9118LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[13] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 9148LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[15] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 9299LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[18] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 18383LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[20] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 20185LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[11] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 247LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[6] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 10032LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[17] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 20218LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[21] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 10690LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[5] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 20263LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[23] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 11502LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[14] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 14931LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[19] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 20291LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[3] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 15641LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[10] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 15789LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[24] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 15813LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[8] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 15869LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[4] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 15990LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[26] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 16085LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[27] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }

        JNINativeMethod __ngen_methods[] = {
            { ((char *)(string_pool + 19906LL)), ((char *)(string_pool + 19697LL)), (void *)&__ngen_native_Mahiro_D1 },
            { ((char *)(string_pool + 5985LL)), ((char *)(string_pool + 19697LL)), (void *)&__ngen_native_Mahiro_F2 },
            { ((char *)(string_pool + 19663LL)), ((char *)(string_pool + 20076LL)), (void *)&__ngen_native_Mahiro_P4 },
            { ((char *)(string_pool + 20119LL)), ((char *)(string_pool + 20128LL)), (void *)&__ngen_native_Mahiro_u5 },
        };

        if (clazz) env->RegisterNatives(clazz, __ngen_methods, sizeof(__ngen_methods) / sizeof(__ngen_methods[0]));
        if (env->ExceptionCheck()) { fprintf(stderr, "Exception occured while registering native_jvm for %s\n", ((char *)(string_pool + 20291LL))); fflush(stderr); env->ExceptionDescribe(); env->ExceptionClear(); }

        {
            jclass hidden_class = env->FindClass(((char *)(string_pool + 16113LL)));
            JNINativeMethod __ngen_hidden_methods[] = {
                { ((char *)(string_pool + 20319LL)), ((char *)(string_pool + 16157LL)), (void *)&__ngen_special_clinit_4_3 },
            };
            if (hidden_class) env->RegisterNatives(hidden_class, __ngen_hidden_methods, sizeof(__ngen_hidden_methods) / sizeof(__ngen_hidden_methods[0]));
            if (env->ExceptionCheck()) { fprintf(stderr, "Exception occured while registering native_jvm for %s\n", ((char *)(string_pool + 9148LL))); fflush(stderr); env->ExceptionDescribe(); env->ExceptionClear(); }
            env->DeleteLocalRef(hidden_class);
        }
    }
}