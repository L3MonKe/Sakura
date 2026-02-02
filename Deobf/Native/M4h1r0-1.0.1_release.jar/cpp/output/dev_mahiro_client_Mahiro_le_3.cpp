#include "../native_jvm.hpp"
#include "../string_pool.hpp"
#include "dev_mahiro_client_Mahiro_le_3.hpp"

// dev/mahiro/client/Mahiro_le
namespace native_jvm::classes::__ngen_dev_mahiro_client_Mahiro_le_3 {

    char *string_pool;

    jstring cstrings[25];
    std::mutex cclasses_mtx[19];
    jclass cclasses[19];
    jmethodID cmethods[11];
    jfieldID cfields[1];

    // Mahiro_P([Ljava/lang/Object;)Ljava/lang/String;
    jobject JNICALL __ngen_native_Mahiro_P1(JNIEnv *env, jclass clazz, jarray arg0) {
        jobject classloader = utils::get_classloader_from_class(env, clazz);
        if (env->ExceptionCheck()) { return (jobject) 0; }
        if (classloader == nullptr) { env->FatalError(((char *)(string_pool + 71LL))); return (jobject) 0; }
    
        jobject lookup = nullptr;
        // try-catch-class java/lang/Throwable
        if (!cclasses[0] || env->IsSameObject(cclasses[0], NULL)) { cclasses_mtx[0].lock(); if (!cclasses[0] || env->IsSameObject(cclasses[0], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[0]))) { cclasses[0] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[0].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } }
        jvalue cstack0 = {}, cstack1 = {}, cstack2 = {}, cstack3 = {}, cstack4 = {}, cstack5 = {}, cstack6 = {}, cstack7 = {}, cstack8 = {}, cstack9 = {}, cstack10 = {}, cstack11 = {}, cstack12 = {}, cstack13 = {};
        jvalue clocal0 = {}, clocal1 = {}, clocal2 = {}, clocal3 = {};
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
        // CHECKCAST java/lang/Long; Stack: 2
        if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { cclasses_mtx[1].lock(); if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[1]))) { cclasses[1] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[1].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (cstack1.l != nullptr && !env->IsInstanceOf(cstack1.l, (cclasses[1]))) { utils::throw_re(env, ((char *)(string_pool + 133LL)), (std::string(((char *)(string_pool + 162LL))) + std::string(((char *)(string_pool + 195LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (jobject) 0; } } 
        // New stack: 2
        // INVOKEVIRTUAL java/lang/Long.longValue()J; Stack: 2
        if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { cclasses_mtx[1].lock(); if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[1]))) { cclasses[1] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[1].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[0]) { cmethods[0] = env->GetMethodID((cclasses[1]), ((char *)(string_pool + 210LL)), ((char *)(string_pool + 220LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 224LL)), -1); else cstack1.j = env->CallLongMethod(cstack1.l, (cmethods[0])); 
        if (env->ExceptionCheck()) { return (jobject) 0; }
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
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 2
        // CHECKCAST [B; Stack: 2
        if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { cclasses_mtx[2].lock(); if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { if (jclass clazz = env->FindClass(((char *)(string_pool + 2920LL)))) { cclasses[2] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[2].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (cstack1.l != nullptr && !env->IsInstanceOf(cstack1.l, (cclasses[2]))) { utils::throw_re(env, ((char *)(string_pool + 133LL)), (std::string(((char *)(string_pool + 162LL))) + std::string(((char *)(string_pool + 2920LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (jobject) 0; } } 
        // New stack: 2
        // ASTORE 3; Stack: 2
        clocal3.l = cstack1.l; refs.insert(cstack1.l);
        // New stack: 1
        // POP; Stack: 1
        ;
        // New stack: 0
        // GETSTATIC dev/mahiro/client/Mahiro_le.a J; Stack: 0
        if (!cclasses[3]  || env->IsSameObject(cclasses[3], NULL)) { cclasses_mtx[3].lock(); if (!cclasses[3] || env->IsSameObject(cclasses[3], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[2]))) { cclasses[3] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[3].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cfields[0]) { cfields[0] = env->GetStaticFieldID((cclasses[3]), ((char *)(string_pool + 247LL)), ((char *)(string_pool + 249LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } cstack0.j = env->GetStaticLongField((cclasses[3]), (cfields[0])); 
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
        // LDC 6975200094952203840; Stack: 0
        cstack0.j = 6975200094952203840LL;
        // New stack: 2
        // LLOAD 1; Stack: 2
        cstack2.j = clocal1.j;
        // New stack: 4
        // LABEL L3; Stack: 4
        L3: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 4
        // ICONST_1; Stack: 4
        cstack4.i = 1;
        // New stack: 5
        // ANEWARRAY java/lang/Object; Stack: 5
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack4.i < 0) utils::throw_re(env, ((char *)(string_pool + 251LL)), ((char *)(string_pool + 288LL)), -1); else { cstack4.l = env->NewObjectArray(cstack4.i, (cclasses[4]), nullptr); refs.insert(cstack4.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 5
        // DUP; Stack: 5
        cstack5 = cstack4;
        // New stack: 6
        // LDC Ldev/mahiro/client/Mahiro_le;; Stack: 6
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[2]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack6.l = (cclasses[5]);
        // New stack: 7
        // SWAP; Stack: 7
        std::swap(cstack6, cstack5);
        // New stack: 7
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.127771650959966.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 7
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack7.l = lookup;
        // New stack: 8
        // LDC Ldev/mahiro/client/Mahiro_yh;; Stack: 8
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack8.l = (cclasses[6]);
        // New stack: 9
        // LDC a; Stack: 9
        cstack9.l = (cstrings[5]);
        // New stack: 10
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;; Stack: 10
        cstack10.l = (cstrings[6]);
        // New stack: 11
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.127771650959966.a()Ljava/lang/ClassLoader;; Stack: 11
        cstack11.l = classloader;
        // New stack: 12
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 12
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[7]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[1]) { cmethods[1] = env->GetStaticMethodID((cclasses[7]), ((char *)(string_pool + 313LL)), ((char *)(string_pool + 340LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack10.l = env->CallStaticObjectMethod((cclasses[7]), (cmethods[1]), cstack10.l, cstack11.l); refs.insert(cstack10.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 11
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 11
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[2]) { cmethods[2] = env->GetMethodID((cclasses[8]), ((char *)(string_pool + 413LL)), ((char *)(string_pool + 424LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack7.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 522LL)), -1); else { cstack7.l = env->CallObjectMethod(cstack7.l, (cmethods[2]), cstack8.l, cstack9.l, cstack10.l); refs.insert(cstack7.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 8
        // SWAP; Stack: 8
        std::swap(cstack7, cstack6);
        // New stack: 8
        // LDC \u00c9; Stack: 8
        cstack8.l = (cstrings[9]);
        // New stack: 9
        // SWAP; Stack: 9
        std::swap(cstack8, cstack7);
        // New stack: 9
        // LDC (JJ)Ljava/util/Base64$Encoder;; Stack: 9
        cstack9.l = (cstrings[10]);
        // New stack: 10
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.127771650959966.a()Ljava/lang/ClassLoader;; Stack: 10
        cstack10.l = classloader;
        // New stack: 11
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 11
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[7]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[1]) { cmethods[1] = env->GetStaticMethodID((cclasses[7]), ((char *)(string_pool + 313LL)), ((char *)(string_pool + 340LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack9.l = env->CallStaticObjectMethod((cclasses[7]), (cmethods[1]), cstack9.l, cstack10.l); refs.insert(cstack9.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 10
        // SWAP; Stack: 10
        std::swap(cstack9, cstack8);
        // New stack: 10
        // LDC 0; Stack: 10
        cstack10.i = 0;
        // New stack: 11
        // ANEWARRAY java/lang/Object; Stack: 11
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack10.i < 0) utils::throw_re(env, ((char *)(string_pool + 251LL)), ((char *)(string_pool + 288LL)), -1); else { cstack10.l = env->NewObjectArray(cstack10.i, (cclasses[4]), nullptr); refs.insert(cstack10.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 11
        // CHECKCAST java/lang/Object; Stack: 11
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack10.l != nullptr && !env->IsInstanceOf(cstack10.l, (cclasses[4]))) { utils::throw_re(env, ((char *)(string_pool + 133LL)), (std::string(((char *)(string_pool + 162LL))) + std::string(((char *)(string_pool + 547LL)))).c_str(), -1); 
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
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[11]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack5.i = cstack5.l == nullptr ? false : env->IsInstanceOf(cstack5.l, (cclasses[9]));
        // New stack: 6
        // IFEQ L5; Stack: 6
        if (cstack5.i == 0) goto L5;
        // New stack: 5
        // INVOKEINTERFACE java/lang/invoke/CallSite.getTarget()Ljava/lang/invoke/MethodHandle;; Stack: 5
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[11]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[3]) { cmethods[3] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 564LL)), ((char *)(string_pool + 574LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 608LL)), -1); else { cstack4.l = env->CallObjectMethod(cstack4.l, (cmethods[3])); refs.insert(cstack4.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 5
        // LABEL L5; Stack: 5
        L5: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 5
        // FRAME FULL L: [[Ljava/lang/Object;, 4, [B] S: [4, 4, java/lang/Object]; Stack: 5
        refs.erase(cstack4.l); 
        refs.erase(clocal0.l); refs.erase(clocal3.l); 
        utils::clear_refs(env, refs);
        // New stack: 5
        // CHECKCAST java/lang/invoke/MethodHandle; Stack: 5
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack4.l != nullptr && !env->IsInstanceOf(cstack4.l, (cclasses[10]))) { utils::throw_re(env, ((char *)(string_pool + 133LL)), (std::string(((char *)(string_pool + 162LL))) + std::string(((char *)(string_pool + 635LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } 
        // New stack: 5
        // GOTO L6; Stack: 5
        goto L6;
        // New stack: 5
        // LABEL L4; Stack: 5
        L4: if (env->ExceptionCheck()) { return (jobject) 0; }
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
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } cstack1.i = cstack1.l == nullptr ? false : env->IsInstanceOf(cstack1.l, (cclasses[11]));
        // New stack: 2
        // IFNE L7; Stack: 2
        if (cstack1.i != 0) goto L7;
        // New stack: 1
        // NEW java/lang/BootstrapMethodError; Stack: 1
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (jobject obj = env->AllocObject((cclasses[11]))) { cstack1.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
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
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[11]), ((char *)(string_pool + 665LL)), ((char *)(string_pool + 672LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 697LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[11]), (cmethods[4]), cstack2.l); 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // LABEL L7; Stack: 1
        L7: if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/Object]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal3.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 720LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 0
        // LABEL L6; Stack: 0
        L6: if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 0
        // FRAME FULL L: [[Ljava/lang/Object;, 4, [B] S: [4, 4, java/lang/invoke/MethodHandle]; Stack: 0
        refs.erase(cstack4.l); 
        refs.erase(clocal0.l); refs.erase(clocal3.l); 
        utils::clear_refs(env, refs);
        // New stack: 5
        // INVOKESTATIC native/magic/1/invoke/obfuscator0.127771650959966.a(JJLjava/lang/invoke/MethodHandle;)Ljava/util/Base64$Encoder;; Stack: 5
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[5]) { cmethods[5] = env->GetStaticMethodID((cclasses[12]), ((char *)(string_pool + 2157LL)), ((char *)(string_pool + 2173LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } cstack0.l = env->CallStaticObjectMethod((cclasses[12]), (cmethods[5]), cstack0.j, cstack2.j, cstack4.l); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // CHECKCAST java/util/Base64$Encoder; Stack: 1
        if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { cclasses_mtx[13].lock(); if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[13] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[13].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (cstack0.l != nullptr && !env->IsInstanceOf(cstack0.l, (cclasses[13]))) { utils::throw_re(env, ((char *)(string_pool + 133LL)), (std::string(((char *)(string_pool + 162LL))) + std::string(((char *)(string_pool + 19672LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (jobject) 0; } } 
        // New stack: 1
        // ALOAD 3; Stack: 1
        cstack1.l = clocal3.l; refs.insert(cstack1.l);
        // New stack: 2
        // LDC 6963780006153902158; Stack: 2
        cstack2.j = 6963780006153902158LL;
        // New stack: 4
        // LLOAD 1; Stack: 4
        cstack4.j = clocal1.j;
        // New stack: 6
        // LABEL L1; Stack: 6
        L1: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 6
        // ICONST_1; Stack: 6
        cstack6.i = 1;
        // New stack: 7
        // ANEWARRAY java/lang/Object; Stack: 7
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (cstack6.i < 0) utils::throw_re(env, ((char *)(string_pool + 251LL)), ((char *)(string_pool + 288LL)), -1); else { cstack6.l = env->NewObjectArray(cstack6.i, (cclasses[4]), nullptr); refs.insert(cstack6.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 7
        // DUP; Stack: 7
        cstack7 = cstack6;
        // New stack: 8
        // LDC Ldev/mahiro/client/Mahiro_le;; Stack: 8
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[2]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } cstack8.l = (cclasses[5]);
        // New stack: 9
        // SWAP; Stack: 9
        std::swap(cstack8, cstack7);
        // New stack: 9
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.127771650959966.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 9
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } cstack9.l = lookup;
        // New stack: 10
        // LDC Ldev/mahiro/client/Mahiro_yh;; Stack: 10
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } cstack10.l = (cclasses[6]);
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
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[7]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (!cmethods[1]) { cmethods[1] = env->GetStaticMethodID((cclasses[7]), ((char *)(string_pool + 313LL)), ((char *)(string_pool + 340LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }  } cstack12.l = env->CallStaticObjectMethod((cclasses[7]), (cmethods[1]), cstack12.l, cstack13.l); refs.insert(cstack12.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 13
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 13
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (!cmethods[2]) { cmethods[2] = env->GetMethodID((cclasses[8]), ((char *)(string_pool + 413LL)), ((char *)(string_pool + 424LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }  } if (cstack9.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 522LL)), -1); else { cstack9.l = env->CallObjectMethod(cstack9.l, (cmethods[2]), cstack10.l, cstack11.l, cstack12.l); refs.insert(cstack9.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 10
        // SWAP; Stack: 10
        std::swap(cstack9, cstack8);
        // New stack: 10
        // LDC v; Stack: 10
        cstack10.l = (cstrings[16]);
        // New stack: 11
        // SWAP; Stack: 11
        std::swap(cstack10, cstack9);
        // New stack: 11
        // LDC (Ljava/lang/Object;Ljava/lang/Object;JJ)Ljava/lang/String;; Stack: 11
        cstack11.l = (cstrings[17]);
        // New stack: 12
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.127771650959966.a()Ljava/lang/ClassLoader;; Stack: 12
        cstack12.l = classloader;
        // New stack: 13
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 13
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[7]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (!cmethods[1]) { cmethods[1] = env->GetStaticMethodID((cclasses[7]), ((char *)(string_pool + 313LL)), ((char *)(string_pool + 340LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }  } cstack11.l = env->CallStaticObjectMethod((cclasses[7]), (cmethods[1]), cstack11.l, cstack12.l); refs.insert(cstack11.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 12
        // SWAP; Stack: 12
        std::swap(cstack11, cstack10);
        // New stack: 12
        // LDC 0; Stack: 12
        cstack12.i = 0;
        // New stack: 13
        // ANEWARRAY java/lang/Object; Stack: 13
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (cstack12.i < 0) utils::throw_re(env, ((char *)(string_pool + 251LL)), ((char *)(string_pool + 288LL)), -1); else { cstack12.l = env->NewObjectArray(cstack12.i, (cclasses[4]), nullptr); refs.insert(cstack12.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 13
        // CHECKCAST java/lang/Object; Stack: 13
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (cstack12.l != nullptr && !env->IsInstanceOf(cstack12.l, (cclasses[4]))) { utils::throw_re(env, ((char *)(string_pool + 133LL)), (std::string(((char *)(string_pool + 162LL))) + std::string(((char *)(string_pool + 547LL)))).c_str(), -1); 
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
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[11]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } cstack7.i = cstack7.l == nullptr ? false : env->IsInstanceOf(cstack7.l, (cclasses[9]));
        // New stack: 8
        // IFEQ L8; Stack: 8
        if (cstack7.i == 0) goto L8;
        // New stack: 7
        // INVOKEINTERFACE java/lang/invoke/CallSite.getTarget()Ljava/lang/invoke/MethodHandle;; Stack: 7
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[11]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (!cmethods[3]) { cmethods[3] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 564LL)), ((char *)(string_pool + 574LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }  } if (cstack6.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 608LL)), -1); else { cstack6.l = env->CallObjectMethod(cstack6.l, (cmethods[3])); refs.insert(cstack6.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 7
        // LABEL L8; Stack: 7
        L8: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 7
        // FRAME FULL L: [[Ljava/lang/Object;, 4, [B] S: [java/util/Base64$Encoder, [B, 4, 4, java/lang/Object]; Stack: 7
        refs.erase(cstack0.l); refs.erase(cstack1.l); refs.erase(cstack6.l); 
        refs.erase(clocal0.l); refs.erase(clocal3.l); 
        utils::clear_refs(env, refs);
        // New stack: 7
        // CHECKCAST java/lang/invoke/MethodHandle; Stack: 7
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (cstack6.l != nullptr && !env->IsInstanceOf(cstack6.l, (cclasses[10]))) { utils::throw_re(env, ((char *)(string_pool + 133LL)), (std::string(((char *)(string_pool + 162LL))) + std::string(((char *)(string_pool + 635LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } 
        // New stack: 7
        // GOTO L9; Stack: 7
        goto L9;
        // New stack: 7
        // LABEL L2; Stack: 7
        L2: if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 7
        // FRAME SAME1 L: null S: [java/lang/Throwable]; Stack: 7
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal3.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // INSTANCEOF java/lang/BootstrapMethodError; Stack: 2
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } cstack1.i = cstack1.l == nullptr ? false : env->IsInstanceOf(cstack1.l, (cclasses[11]));
        // New stack: 2
        // IFNE L10; Stack: 2
        if (cstack1.i != 0) goto L10;
        // New stack: 1
        // NEW java/lang/BootstrapMethodError; Stack: 1
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (jobject obj = env->AllocObject((cclasses[11]))) { cstack1.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
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
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[11]), ((char *)(string_pool + 665LL)), ((char *)(string_pool + 672LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 697LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[11]), (cmethods[4]), cstack2.l); 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // LABEL L10; Stack: 1
        L10: if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/Object]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal3.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 720LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 0
        // LABEL L9; Stack: 0
        L9: if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 0
        // FRAME FULL L: [[Ljava/lang/Object;, 4, [B] S: [java/util/Base64$Encoder, [B, 4, 4, java/lang/invoke/MethodHandle]; Stack: 0
        refs.erase(cstack0.l); refs.erase(cstack1.l); refs.erase(cstack6.l); 
        refs.erase(clocal0.l); refs.erase(clocal3.l); 
        utils::clear_refs(env, refs);
        // New stack: 7
        // INVOKESTATIC native/magic/1/invoke/obfuscator0.127771650959966.a(Ljava/lang/Object;Ljava/lang/Object;JJLjava/lang/invoke/MethodHandle;)Ljava/lang/String;; Stack: 7
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[6]) { cmethods[6] = env->GetStaticMethodID((cclasses[12]), ((char *)(string_pool + 1119LL)), ((char *)(string_pool + 1134LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } cstack0.l = env->CallStaticObjectMethod((cclasses[12]), (cmethods[6]), cstack0.l, cstack1.l, cstack2.j, cstack4.j, cstack6.l); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // CHECKCAST java/lang/String; Stack: 1
        if (!cclasses[14] || env->IsSameObject(cclasses[14], NULL)) { cclasses_mtx[14].lock(); if (!cclasses[14] || env->IsSameObject(cclasses[14], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[18]))) { cclasses[14] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[14].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (cstack0.l != nullptr && !env->IsInstanceOf(cstack0.l, (cclasses[14]))) { utils::throw_re(env, ((char *)(string_pool + 133LL)), (std::string(((char *)(string_pool + 162LL))) + std::string(((char *)(string_pool + 178LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (jobject) 0; } } 
        // New stack: 1
        // ARETURN; Stack: 1
        return (jobject) cstack0.l;
        // New stack: 0
        return (jobject) 0;
        L_CATCH_1: if (env->IsInstanceOf(cstack0.l, (cclasses[0]))) { goto L2; }
        env->Throw((jthrowable) cstack0.l); return (jobject) 0;
        L_CATCH_0: if (env->IsInstanceOf(cstack0.l, (cclasses[0]))) { goto L4; }
        env->Throw((jthrowable) cstack0.l); return (jobject) 0;
    }
    
    // Mahiro_a([Ljava/lang/Object;)[B
    jarray JNICALL __ngen_native_Mahiro_a2(JNIEnv *env, jclass clazz, jarray arg0) {
        jobject classloader = utils::get_classloader_from_class(env, clazz);
        if (env->ExceptionCheck()) { return (jarray) 0; }
        if (classloader == nullptr) { env->FatalError(((char *)(string_pool + 71LL))); return (jarray) 0; }
    
        jobject lookup = nullptr;
        // try-catch-class java/lang/Throwable
        if (!cclasses[0] || env->IsSameObject(cclasses[0], NULL)) { cclasses_mtx[0].lock(); if (!cclasses[0] || env->IsSameObject(cclasses[0], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[0]))) { cclasses[0] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[0].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } }
        jvalue cstack0 = {}, cstack1 = {}, cstack2 = {}, cstack3 = {}, cstack4 = {}, cstack5 = {}, cstack6 = {}, cstack7 = {}, cstack8 = {}, cstack9 = {}, cstack10 = {}, cstack11 = {}, cstack12 = {}, cstack13 = {};
        jvalue clocal0 = {}, clocal1 = {}, clocal2 = {}, clocal3 = {};
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
        // CHECKCAST java/lang/String; Stack: 2
        if (!cclasses[14] || env->IsSameObject(cclasses[14], NULL)) { cclasses_mtx[14].lock(); if (!cclasses[14] || env->IsSameObject(cclasses[14], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[18]))) { cclasses[14] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[14].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (cstack1.l != nullptr && !env->IsInstanceOf(cstack1.l, (cclasses[14]))) { utils::throw_re(env, ((char *)(string_pool + 133LL)), (std::string(((char *)(string_pool + 162LL))) + std::string(((char *)(string_pool + 178LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (jarray) 0; } } 
        // New stack: 2
        // ASTORE 1; Stack: 2
        clocal1.l = cstack1.l; refs.insert(cstack1.l);
        // New stack: 1
        // POP; Stack: 1
        ;
        // New stack: 0
        // GETSTATIC dev/mahiro/client/Mahiro_le.a J; Stack: 0
        if (!cclasses[3]  || env->IsSameObject(cclasses[3], NULL)) { cclasses_mtx[3].lock(); if (!cclasses[3] || env->IsSameObject(cclasses[3], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[2]))) { cclasses[3] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[3].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (!cfields[0]) { cfields[0] = env->GetStaticFieldID((cclasses[3]), ((char *)(string_pool + 247LL)), ((char *)(string_pool + 249LL))); if (env->ExceptionCheck()) { return (jarray) 0; }  } cstack0.j = env->GetStaticLongField((cclasses[3]), (cfields[0])); 
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
        // LDC -6851351399413939738; Stack: 0
        cstack0.j = -6851351399413939738LL;
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
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack4.i < 0) utils::throw_re(env, ((char *)(string_pool + 251LL)), ((char *)(string_pool + 288LL)), -1); else { cstack4.l = env->NewObjectArray(cstack4.i, (cclasses[4]), nullptr); refs.insert(cstack4.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 5
        // DUP; Stack: 5
        cstack5 = cstack4;
        // New stack: 6
        // LDC Ldev/mahiro/client/Mahiro_le;; Stack: 6
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[2]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack6.l = (cclasses[5]);
        // New stack: 7
        // SWAP; Stack: 7
        std::swap(cstack6, cstack5);
        // New stack: 7
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.127771650959966.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 7
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack7.l = lookup;
        // New stack: 8
        // LDC Ldev/mahiro/client/Mahiro_yh;; Stack: 8
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack8.l = (cclasses[6]);
        // New stack: 9
        // LDC a; Stack: 9
        cstack9.l = (cstrings[5]);
        // New stack: 10
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;; Stack: 10
        cstack10.l = (cstrings[6]);
        // New stack: 11
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.127771650959966.a()Ljava/lang/ClassLoader;; Stack: 11
        cstack11.l = classloader;
        // New stack: 12
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 12
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[7]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[1]) { cmethods[1] = env->GetStaticMethodID((cclasses[7]), ((char *)(string_pool + 313LL)), ((char *)(string_pool + 340LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack10.l = env->CallStaticObjectMethod((cclasses[7]), (cmethods[1]), cstack10.l, cstack11.l); refs.insert(cstack10.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 11
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 11
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[2]) { cmethods[2] = env->GetMethodID((cclasses[8]), ((char *)(string_pool + 413LL)), ((char *)(string_pool + 424LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack7.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 522LL)), -1); else { cstack7.l = env->CallObjectMethod(cstack7.l, (cmethods[2]), cstack8.l, cstack9.l, cstack10.l); refs.insert(cstack7.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 8
        // SWAP; Stack: 8
        std::swap(cstack7, cstack6);
        // New stack: 8
        // LDC \u00c9; Stack: 8
        cstack8.l = (cstrings[9]);
        // New stack: 9
        // SWAP; Stack: 9
        std::swap(cstack8, cstack7);
        // New stack: 9
        // LDC (JJ)Ljava/util/Base64$Decoder;; Stack: 9
        cstack9.l = (cstrings[19]);
        // New stack: 10
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.127771650959966.a()Ljava/lang/ClassLoader;; Stack: 10
        cstack10.l = classloader;
        // New stack: 11
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 11
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[7]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[1]) { cmethods[1] = env->GetStaticMethodID((cclasses[7]), ((char *)(string_pool + 313LL)), ((char *)(string_pool + 340LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack9.l = env->CallStaticObjectMethod((cclasses[7]), (cmethods[1]), cstack9.l, cstack10.l); refs.insert(cstack9.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 10
        // SWAP; Stack: 10
        std::swap(cstack9, cstack8);
        // New stack: 10
        // LDC 0; Stack: 10
        cstack10.i = 0;
        // New stack: 11
        // ANEWARRAY java/lang/Object; Stack: 11
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack10.i < 0) utils::throw_re(env, ((char *)(string_pool + 251LL)), ((char *)(string_pool + 288LL)), -1); else { cstack10.l = env->NewObjectArray(cstack10.i, (cclasses[4]), nullptr); refs.insert(cstack10.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 11
        // CHECKCAST java/lang/Object; Stack: 11
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack10.l != nullptr && !env->IsInstanceOf(cstack10.l, (cclasses[4]))) { utils::throw_re(env, ((char *)(string_pool + 133LL)), (std::string(((char *)(string_pool + 162LL))) + std::string(((char *)(string_pool + 547LL)))).c_str(), -1); 
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
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[11]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack5.i = cstack5.l == nullptr ? false : env->IsInstanceOf(cstack5.l, (cclasses[9]));
        // New stack: 6
        // IFEQ L5; Stack: 6
        if (cstack5.i == 0) goto L5;
        // New stack: 5
        // INVOKEINTERFACE java/lang/invoke/CallSite.getTarget()Ljava/lang/invoke/MethodHandle;; Stack: 5
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[11]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[3]) { cmethods[3] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 564LL)), ((char *)(string_pool + 574LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 608LL)), -1); else { cstack4.l = env->CallObjectMethod(cstack4.l, (cmethods[3])); refs.insert(cstack4.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 5
        // LABEL L5; Stack: 5
        L5: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 5
        // FRAME FULL L: [[Ljava/lang/Object;, java/lang/String, 4] S: [4, 4, java/lang/Object]; Stack: 5
        refs.erase(cstack4.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); 
        utils::clear_refs(env, refs);
        // New stack: 5
        // CHECKCAST java/lang/invoke/MethodHandle; Stack: 5
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack4.l != nullptr && !env->IsInstanceOf(cstack4.l, (cclasses[10]))) { utils::throw_re(env, ((char *)(string_pool + 133LL)), (std::string(((char *)(string_pool + 162LL))) + std::string(((char *)(string_pool + 635LL)))).c_str(), -1); 
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
        refs.erase(clocal0.l); refs.erase(clocal1.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // INSTANCEOF java/lang/BootstrapMethodError; Stack: 2
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } cstack1.i = cstack1.l == nullptr ? false : env->IsInstanceOf(cstack1.l, (cclasses[11]));
        // New stack: 2
        // IFNE L7; Stack: 2
        if (cstack1.i != 0) goto L7;
        // New stack: 1
        // NEW java/lang/BootstrapMethodError; Stack: 1
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (jobject obj = env->AllocObject((cclasses[11]))) { cstack1.l = obj; refs.insert(obj); } 
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
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[11]), ((char *)(string_pool + 665LL)), ((char *)(string_pool + 672LL))); if (env->ExceptionCheck()) { return (jarray) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 697LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[11]), (cmethods[4]), cstack2.l); 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // LABEL L7; Stack: 1
        L7: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/Object]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 720LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 0
        // LABEL L6; Stack: 0
        L6: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 0
        // FRAME FULL L: [[Ljava/lang/Object;, java/lang/String, 4] S: [4, 4, java/lang/invoke/MethodHandle]; Stack: 0
        refs.erase(cstack4.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); 
        utils::clear_refs(env, refs);
        // New stack: 5
        // INVOKESTATIC native/magic/1/invoke/obfuscator0.127771650959966.a(JJLjava/lang/invoke/MethodHandle;)Ljava/util/Base64$Decoder;; Stack: 5
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (!cmethods[5]) { cmethods[5] = env->GetStaticMethodID((cclasses[12]), ((char *)(string_pool + 2157LL)), ((char *)(string_pool + 2173LL))); if (env->ExceptionCheck()) { return (jarray) 0; }  } cstack0.l = env->CallStaticObjectMethod((cclasses[12]), (cmethods[5]), cstack0.j, cstack2.j, cstack4.l); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // CHECKCAST java/util/Base64$Decoder; Stack: 1
        if (!cclasses[15] || env->IsSameObject(cclasses[15], NULL)) { cclasses_mtx[15].lock(); if (!cclasses[15] || env->IsSameObject(cclasses[15], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[20]))) { cclasses[15] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[15].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (cstack0.l != nullptr && !env->IsInstanceOf(cstack0.l, (cclasses[15]))) { utils::throw_re(env, ((char *)(string_pool + 133LL)), (std::string(((char *)(string_pool + 162LL))) + std::string(((char *)(string_pool + 19721LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (jarray) 0; } } 
        // New stack: 1
        // ALOAD 1; Stack: 1
        cstack1.l = clocal1.l; refs.insert(cstack1.l);
        // New stack: 2
        // LDC -6864893730149394845; Stack: 2
        cstack2.j = -6864893730149394845LL;
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
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (cstack6.i < 0) utils::throw_re(env, ((char *)(string_pool + 251LL)), ((char *)(string_pool + 288LL)), -1); else { cstack6.l = env->NewObjectArray(cstack6.i, (cclasses[4]), nullptr); refs.insert(cstack6.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 7
        // DUP; Stack: 7
        cstack7 = cstack6;
        // New stack: 8
        // LDC Ldev/mahiro/client/Mahiro_le;; Stack: 8
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[2]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } cstack8.l = (cclasses[5]);
        // New stack: 9
        // SWAP; Stack: 9
        std::swap(cstack8, cstack7);
        // New stack: 9
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.127771650959966.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 9
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } cstack9.l = lookup;
        // New stack: 10
        // LDC Ldev/mahiro/client/Mahiro_yh;; Stack: 10
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } cstack10.l = (cclasses[6]);
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
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[7]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (!cmethods[1]) { cmethods[1] = env->GetStaticMethodID((cclasses[7]), ((char *)(string_pool + 313LL)), ((char *)(string_pool + 340LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }  } cstack12.l = env->CallStaticObjectMethod((cclasses[7]), (cmethods[1]), cstack12.l, cstack13.l); refs.insert(cstack12.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 13
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 13
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (!cmethods[2]) { cmethods[2] = env->GetMethodID((cclasses[8]), ((char *)(string_pool + 413LL)), ((char *)(string_pool + 424LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }  } if (cstack9.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 522LL)), -1); else { cstack9.l = env->CallObjectMethod(cstack9.l, (cmethods[2]), cstack10.l, cstack11.l, cstack12.l); refs.insert(cstack9.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 10
        // SWAP; Stack: 10
        std::swap(cstack9, cstack8);
        // New stack: 10
        // LDC v; Stack: 10
        cstack10.l = (cstrings[16]);
        // New stack: 11
        // SWAP; Stack: 11
        std::swap(cstack10, cstack9);
        // New stack: 11
        // LDC (Ljava/lang/Object;Ljava/lang/Object;JJ)[B; Stack: 11
        cstack11.l = (cstrings[21]);
        // New stack: 12
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.127771650959966.a()Ljava/lang/ClassLoader;; Stack: 12
        cstack12.l = classloader;
        // New stack: 13
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 13
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[7]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (!cmethods[1]) { cmethods[1] = env->GetStaticMethodID((cclasses[7]), ((char *)(string_pool + 313LL)), ((char *)(string_pool + 340LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }  } cstack11.l = env->CallStaticObjectMethod((cclasses[7]), (cmethods[1]), cstack11.l, cstack12.l); refs.insert(cstack11.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 12
        // SWAP; Stack: 12
        std::swap(cstack11, cstack10);
        // New stack: 12
        // LDC 0; Stack: 12
        cstack12.i = 0;
        // New stack: 13
        // ANEWARRAY java/lang/Object; Stack: 13
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (cstack12.i < 0) utils::throw_re(env, ((char *)(string_pool + 251LL)), ((char *)(string_pool + 288LL)), -1); else { cstack12.l = env->NewObjectArray(cstack12.i, (cclasses[4]), nullptr); refs.insert(cstack12.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 13
        // CHECKCAST java/lang/Object; Stack: 13
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (cstack12.l != nullptr && !env->IsInstanceOf(cstack12.l, (cclasses[4]))) { utils::throw_re(env, ((char *)(string_pool + 133LL)), (std::string(((char *)(string_pool + 162LL))) + std::string(((char *)(string_pool + 547LL)))).c_str(), -1); 
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
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[11]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } cstack7.i = cstack7.l == nullptr ? false : env->IsInstanceOf(cstack7.l, (cclasses[9]));
        // New stack: 8
        // IFEQ L8; Stack: 8
        if (cstack7.i == 0) goto L8;
        // New stack: 7
        // INVOKEINTERFACE java/lang/invoke/CallSite.getTarget()Ljava/lang/invoke/MethodHandle;; Stack: 7
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[11]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (!cmethods[3]) { cmethods[3] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 564LL)), ((char *)(string_pool + 574LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }  } if (cstack6.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 608LL)), -1); else { cstack6.l = env->CallObjectMethod(cstack6.l, (cmethods[3])); refs.insert(cstack6.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 7
        // LABEL L8; Stack: 7
        L8: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 7
        // FRAME FULL L: [[Ljava/lang/Object;, java/lang/String, 4] S: [java/util/Base64$Decoder, java/lang/String, 4, 4, java/lang/Object]; Stack: 7
        refs.erase(cstack0.l); refs.erase(cstack1.l); refs.erase(cstack6.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); 
        utils::clear_refs(env, refs);
        // New stack: 7
        // CHECKCAST java/lang/invoke/MethodHandle; Stack: 7
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (cstack6.l != nullptr && !env->IsInstanceOf(cstack6.l, (cclasses[10]))) { utils::throw_re(env, ((char *)(string_pool + 133LL)), (std::string(((char *)(string_pool + 162LL))) + std::string(((char *)(string_pool + 635LL)))).c_str(), -1); 
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
        refs.erase(clocal0.l); refs.erase(clocal1.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // INSTANCEOF java/lang/BootstrapMethodError; Stack: 2
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } cstack1.i = cstack1.l == nullptr ? false : env->IsInstanceOf(cstack1.l, (cclasses[11]));
        // New stack: 2
        // IFNE L10; Stack: 2
        if (cstack1.i != 0) goto L10;
        // New stack: 1
        // NEW java/lang/BootstrapMethodError; Stack: 1
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (jobject obj = env->AllocObject((cclasses[11]))) { cstack1.l = obj; refs.insert(obj); } 
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
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[11]), ((char *)(string_pool + 665LL)), ((char *)(string_pool + 672LL))); if (env->ExceptionCheck()) { return (jarray) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 697LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[11]), (cmethods[4]), cstack2.l); 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // LABEL L10; Stack: 1
        L10: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/Object]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 720LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 0
        // LABEL L9; Stack: 0
        L9: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 0
        // FRAME FULL L: [[Ljava/lang/Object;, java/lang/String, 4] S: [java/util/Base64$Decoder, java/lang/String, 4, 4, java/lang/invoke/MethodHandle]; Stack: 0
        refs.erase(cstack0.l); refs.erase(cstack1.l); refs.erase(cstack6.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); 
        utils::clear_refs(env, refs);
        // New stack: 7
        // INVOKESTATIC native/magic/1/invoke/obfuscator0.127771650959966.a(Ljava/lang/Object;Ljava/lang/Object;JJLjava/lang/invoke/MethodHandle;)[B; Stack: 7
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (!cmethods[6]) { cmethods[6] = env->GetStaticMethodID((cclasses[12]), ((char *)(string_pool + 1119LL)), ((char *)(string_pool + 1134LL))); if (env->ExceptionCheck()) { return (jarray) 0; }  } cstack0.l = env->CallStaticObjectMethod((cclasses[12]), (cmethods[6]), cstack0.l, cstack1.l, cstack2.j, cstack4.j, cstack6.l); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // CHECKCAST [B; Stack: 1
        if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { cclasses_mtx[2].lock(); if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { if (jclass clazz = env->FindClass(((char *)(string_pool + 2920LL)))) { cclasses[2] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[2].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (cstack0.l != nullptr && !env->IsInstanceOf(cstack0.l, (cclasses[2]))) { utils::throw_re(env, ((char *)(string_pool + 133LL)), (std::string(((char *)(string_pool + 162LL))) + std::string(((char *)(string_pool + 2920LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (jarray) 0; } } 
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
    
    // <clinit>()V
    void JNICALL __ngen_special_clinit_3_3(JNIEnv *env, jobject ignored_hidden, jclass clazz) {
        env->DeleteLocalRef(ignored_hidden);
        jobject classloader = utils::get_classloader_from_class(env, clazz);
        if (env->ExceptionCheck()) { return (void) 0; }
        if (classloader == nullptr) { env->FatalError(((char *)(string_pool + 71LL))); return (void) 0; }
    
        jobject lookup = nullptr;
        jvalue cstack0 = {}, cstack1 = {}, cstack2 = {}, cstack3 = {}, cstack4 = {};
        std::unordered_set<jobject> refs;
    
    
        // LDC 7442466068709946127; Stack: 0
        cstack0.j = 7442466068709946127LL;
        // New stack: 2
        // LDC 3734676267167786238; Stack: 2
        cstack2.j = 3734676267167786238LL;
        // New stack: 4
        // INVOKESTATIC java/lang/invoke/MethodHandles.lookup()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 4
        if (!cclasses[16] || env->IsSameObject(cclasses[16], NULL)) { cclasses_mtx[16].lock(); if (!cclasses[16] || env->IsSameObject(cclasses[16], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[22]))) { cclasses[16] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[16].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[7]) { cmethods[7] = env->GetStaticMethodID((cclasses[16]), ((char *)(string_pool + 5142LL)), ((char *)(string_pool + 5149LL))); if (env->ExceptionCheck()) { return (void) 0; }  } cstack4.l = env->CallStaticObjectMethod((cclasses[16]), (cmethods[7])); refs.insert(cstack4.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 5
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.lookupClass()Ljava/lang/Class;; Stack: 5
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[8]) { cmethods[8] = env->GetMethodID((cclasses[8]), ((char *)(string_pool + 5191LL)), ((char *)(string_pool + 3163LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 522LL)), -1); else { cstack4.l = env->CallObjectMethod(cstack4.l, (cmethods[8])); refs.insert(cstack4.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 5
        // INVOKESTATIC dev/mahiro/client/Mahiro__i.a(JJLjava/lang/Object;)Ldev/mahiro/client/Mahiro__g;; Stack: 5
        if (!cclasses[17] || env->IsSameObject(cclasses[17], NULL)) { cclasses_mtx[17].lock(); if (!cclasses[17] || env->IsSameObject(cclasses[17], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[23]))) { cclasses[17] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[17].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[9]) { cmethods[9] = env->GetStaticMethodID((cclasses[17]), ((char *)(string_pool + 247LL)), ((char *)(string_pool + 5203LL))); if (env->ExceptionCheck()) { return (void) 0; }  } cstack0.l = env->CallStaticObjectMethod((cclasses[17]), (cmethods[9]), cstack0.j, cstack2.j, cstack4.l); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // LDC 210744523947296; Stack: 1
        cstack1.j = 210744523947296LL;
        // New stack: 3
        // INVOKEINTERFACE dev/mahiro/client/Mahiro__g.a(J)J; Stack: 3
        if (!cclasses[18] || env->IsSameObject(cclasses[18], NULL)) { cclasses_mtx[18].lock(); if (!cclasses[18] || env->IsSameObject(cclasses[18], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[24]))) { cclasses[18] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[18].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[10]) { cmethods[10] = env->GetMethodID((cclasses[18]), ((char *)(string_pool + 247LL)), ((char *)(string_pool + 5255LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 91LL)), ((char *)(string_pool + 5260LL)), -1); else cstack0.j = env->CallLongMethod(cstack0.l, (cmethods[10]), cstack1.j); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 2
        // PUTSTATIC dev/mahiro/client/Mahiro_le.a J; Stack: 2
        if (!cclasses[3]  || env->IsSameObject(cclasses[3], NULL)) { cclasses_mtx[3].lock(); if (!cclasses[3] || env->IsSameObject(cclasses[3], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[2]))) { cclasses[3] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[3].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cfields[0]) { cfields[0] = env->GetStaticFieldID((cclasses[3]), ((char *)(string_pool + 247LL)), ((char *)(string_pool + 249LL))); if (env->ExceptionCheck()) { return (void) 0; }  } env->SetStaticLongField((cclasses[3]), (cfields[0]), cstack0.j); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // RETURN; Stack: 0
        return;
        // New stack: 0
        return (void) 0;
    }
    
    
    void __ngen_register_methods(JNIEnv *env, jclass clazz) {
        string_pool = string_pool::get_pool();

        if (jstring str = env->NewStringUTF(((char *)(string_pool + 19746LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[2] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 8244LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[1] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 19774LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[10] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 6784LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[6] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 8418LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[8] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 8508LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[22] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 8714LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[11] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 9052LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[0] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 9118LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[12] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 9148LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[14] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 19805LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[20] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 9621LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[18] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 247LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[5] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 19830LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[15] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 9926LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[21] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 10032LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[16] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 19855LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[19] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 10690LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[4] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 11502LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[13] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 11858LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[17] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 14931LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[9] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 15813LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[7] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 15869LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[3] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 15990LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[23] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 16085LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[24] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }

        JNINativeMethod __ngen_methods[] = {
            { ((char *)(string_pool + 19663LL)), ((char *)(string_pool + 2514LL)), (void *)&__ngen_native_Mahiro_P1 },
            { ((char *)(string_pool + 3885LL)), ((char *)(string_pool + 19697LL)), (void *)&__ngen_native_Mahiro_a2 },
        };

        if (clazz) env->RegisterNatives(clazz, __ngen_methods, sizeof(__ngen_methods) / sizeof(__ngen_methods[0]));
        if (env->ExceptionCheck()) { fprintf(stderr, "Exception occured while registering native_jvm for %s\n", ((char *)(string_pool + 19746LL))); fflush(stderr); env->ExceptionDescribe(); env->ExceptionClear(); }

        {
            jclass hidden_class = env->FindClass(((char *)(string_pool + 16113LL)));
            JNINativeMethod __ngen_hidden_methods[] = {
                { ((char *)(string_pool + 19886LL)), ((char *)(string_pool + 16157LL)), (void *)&__ngen_special_clinit_3_3 },
            };
            if (hidden_class) env->RegisterNatives(hidden_class, __ngen_hidden_methods, sizeof(__ngen_hidden_methods) / sizeof(__ngen_hidden_methods[0]));
            if (env->ExceptionCheck()) { fprintf(stderr, "Exception occured while registering native_jvm for %s\n", ((char *)(string_pool + 9148LL))); fflush(stderr); env->ExceptionDescribe(); env->ExceptionClear(); }
            env->DeleteLocalRef(hidden_class);
        }
    }
}