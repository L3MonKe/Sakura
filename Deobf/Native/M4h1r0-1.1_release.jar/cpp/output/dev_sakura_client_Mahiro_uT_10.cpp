#include "../native_jvm.hpp"
#include "../string_pool.hpp"
#include "dev_sakura_client_Mahiro_uT_10.hpp"

// dev/sakura/client/Mahiro_uT
namespace native_jvm::classes::__ngen_dev_sakura_client_Mahiro_uT_10 {

    char *string_pool;

    jstring cstrings[61];
    std::mutex cclasses_mtx[37];
    jclass cclasses[37];
    jmethodID cmethods[59];
    jfieldID cfields[9];

    // Mahiro__([Ljava/lang/Object;)[B
    jarray JNICALL __ngen_native_Mahiro__1(JNIEnv *env, jclass clazz, jarray arg0) {
        jobject classloader = utils::get_classloader_from_class(env, clazz);
        if (env->ExceptionCheck()) { return (jarray) 0; }
        if (classloader == nullptr) { env->FatalError(((char *)(string_pool + 54LL))); return (jarray) 0; }
    
        jobject lookup = nullptr;
        // try-catch-class java/lang/Throwable
        if (!cclasses[0] || env->IsSameObject(cclasses[0], NULL)) { cclasses_mtx[0].lock(); if (!cclasses[0] || env->IsSameObject(cclasses[0], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[0]))) { cclasses[0] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[0].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } }
        jvalue cstack0 = {}, cstack1 = {}, cstack2 = {}, cstack3 = {}, cstack4 = {}, cstack5 = {}, cstack6 = {}, cstack7 = {}, cstack8 = {}, cstack9 = {}, cstack10 = {}, cstack11 = {}, cstack12 = {};
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
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 105LL)), -1); else { cstack1.l = env->GetObjectArrayElement((jobjectArray) cstack1.l, cstack2.i); refs.insert(cstack1.l); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 2
        // CHECKCAST [B; Stack: 2
        if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { cclasses_mtx[1].lock(); if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { if (jclass clazz = env->FindClass(((char *)(string_pool + 979LL)))) { cclasses[1] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[1].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (cstack1.l != nullptr && !env->IsInstanceOf(cstack1.l, (cclasses[1]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 979LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (jarray) 0; } } 
        // New stack: 2
        // ASTORE 5; Stack: 2
        clocal5.l = cstack1.l; refs.insert(cstack1.l);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // ICONST_1; Stack: 2
        cstack2.i = 1;
        // New stack: 3
        // AALOAD; Stack: 3
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 105LL)), -1); else { cstack1.l = env->GetObjectArrayElement((jobjectArray) cstack1.l, cstack2.i); refs.insert(cstack1.l); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 2
        // CHECKCAST [B; Stack: 2
        if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { cclasses_mtx[1].lock(); if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { if (jclass clazz = env->FindClass(((char *)(string_pool + 979LL)))) { cclasses[1] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[1].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (cstack1.l != nullptr && !env->IsInstanceOf(cstack1.l, (cclasses[1]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 979LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (jarray) 0; } } 
        // New stack: 2
        // ASTORE 6; Stack: 2
        clocal6.l = cstack1.l; refs.insert(cstack1.l);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // ICONST_2; Stack: 2
        cstack2.i = 2;
        // New stack: 3
        // AALOAD; Stack: 3
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 105LL)), -1); else { cstack1.l = env->GetObjectArrayElement((jobjectArray) cstack1.l, cstack2.i); refs.insert(cstack1.l); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 2
        // CHECKCAST [B; Stack: 2
        if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { cclasses_mtx[1].lock(); if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { if (jclass clazz = env->FindClass(((char *)(string_pool + 979LL)))) { cclasses[1] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[1].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (cstack1.l != nullptr && !env->IsInstanceOf(cstack1.l, (cclasses[1]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 979LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (jarray) 0; } } 
        // New stack: 2
        // ASTORE 1; Stack: 2
        clocal1.l = cstack1.l; refs.insert(cstack1.l);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // ICONST_3; Stack: 2
        cstack2.i = 3;
        // New stack: 3
        // AALOAD; Stack: 3
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 105LL)), -1); else { cstack1.l = env->GetObjectArrayElement((jobjectArray) cstack1.l, cstack2.i); refs.insert(cstack1.l); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 2
        // CHECKCAST java/lang/Long; Stack: 2
        if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { cclasses_mtx[2].lock(); if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[1]))) { cclasses[2] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[2].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (cstack1.l != nullptr && !env->IsInstanceOf(cstack1.l, (cclasses[2]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 161LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (jarray) 0; } } 
        // New stack: 2
        // INVOKEVIRTUAL java/lang/Long.longValue()J; Stack: 2
        if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { cclasses_mtx[2].lock(); if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[1]))) { cclasses[2] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[2].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (!cmethods[0]) { cmethods[0] = env->GetMethodID((cclasses[2]), ((char *)(string_pool + 176LL)), ((char *)(string_pool + 186LL))); if (env->ExceptionCheck()) { return (jarray) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 190LL)), -1); else cstack1.j = env->CallLongMethod(cstack1.l, (cmethods[0])); 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 3
        // LSTORE 3; Stack: 3
        clocal3.j = cstack1.j;
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // ICONST_4; Stack: 2
        cstack2.i = 4;
        // New stack: 3
        // AALOAD; Stack: 3
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 105LL)), -1); else { cstack1.l = env->GetObjectArrayElement((jobjectArray) cstack1.l, cstack2.i); refs.insert(cstack1.l); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 2
        // CHECKCAST java/lang/Integer; Stack: 2
        if (!cclasses[3] || env->IsSameObject(cclasses[3], NULL)) { cclasses_mtx[3].lock(); if (!cclasses[3] || env->IsSameObject(cclasses[3], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[2]))) { cclasses[3] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[3].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (cstack1.l != nullptr && !env->IsInstanceOf(cstack1.l, (cclasses[3]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 2812LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (jarray) 0; } } 
        // New stack: 2
        // INVOKEVIRTUAL java/lang/Integer.intValue()I; Stack: 2
        if (!cclasses[3] || env->IsSameObject(cclasses[3], NULL)) { cclasses_mtx[3].lock(); if (!cclasses[3] || env->IsSameObject(cclasses[3], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[2]))) { cclasses[3] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[3].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (!cmethods[1]) { cmethods[1] = env->GetMethodID((cclasses[3]), ((char *)(string_pool + 2830LL)), ((char *)(string_pool + 2047LL))); if (env->ExceptionCheck()) { return (jarray) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2051LL)), -1); else cstack1.i = env->CallIntMethod(cstack1.l, (cmethods[1])); 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 2
        // ISTORE 2; Stack: 2
        clocal2.i = cstack1.i;
        // New stack: 1
        // POP; Stack: 1
        ;
        // New stack: 0
        // GETSTATIC dev/sakura/client/Mahiro_uT.a J; Stack: 0
        if (!cclasses[4]  || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (!cfields[0]) { cfields[0] = env->GetStaticFieldID((cclasses[4]), ((char *)(string_pool + 213LL)), ((char *)(string_pool + 215LL))); if (env->ExceptionCheck()) { return (jarray) 0; }  } cstack0.j = env->GetStaticLongField((cclasses[4]), (cfields[0])); 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 2
        // LLOAD 3; Stack: 2
        cstack2.j = clocal3.j;
        // New stack: 4
        // LXOR; Stack: 4
        cstack0.j = cstack0.j ^ cstack2.j;
        // New stack: 2
        // LSTORE 3; Stack: 2
        clocal3.j = cstack0.j;
        // New stack: 0
        // LLOAD 3; Stack: 0
        cstack0.j = clocal3.j;
        // New stack: 2
        // DUP2; Stack: 2
        cstack2 = cstack0; cstack3 = cstack1;
        // New stack: 4
        // LDC 107422651029421; Stack: 4
        cstack4.j = 107422651029421LL;
        // New stack: 6
        // LXOR; Stack: 6
        cstack2.j = cstack2.j ^ cstack4.j;
        // New stack: 4
        // LSTORE 7; Stack: 4
        clocal7.j = cstack2.j;
        // New stack: 2
        // DUP2; Stack: 2
        cstack2 = cstack0; cstack3 = cstack1;
        // New stack: 4
        // LDC 68845029797604; Stack: 4
        cstack4.j = 68845029797604LL;
        // New stack: 6
        // LXOR; Stack: 6
        cstack2.j = cstack2.j ^ cstack4.j;
        // New stack: 4
        // LSTORE 9; Stack: 4
        clocal9.j = cstack2.j;
        // New stack: 2
        // POP2; Stack: 2
        ;
        // New stack: 0
        // ALOAD 5; Stack: 0
        cstack0.l = clocal5.l; refs.insert(cstack0.l);
        // New stack: 1
        // LLOAD 7; Stack: 1
        cstack1.j = clocal7.j;
        // New stack: 3
        // ALOAD 6; Stack: 3
        cstack3.l = clocal6.l; refs.insert(cstack3.l);
        // New stack: 4
        // ICONST_3; Stack: 4
        cstack4.i = 3;
        // New stack: 5
        // ANEWARRAY java/lang/Object; Stack: 5
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (cstack4.i < 0) utils::throw_re(env, ((char *)(string_pool + 217LL)), ((char *)(string_pool + 254LL)), -1); else { cstack4.l = env->NewObjectArray(cstack4.i, (cclasses[5]), nullptr); refs.insert(cstack4.l); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 5
        // DUP_X1; Stack: 5
        cstack5 = cstack4; cstack4 = cstack3; cstack3 = cstack5;
        // New stack: 6
        // SWAP; Stack: 6
        std::swap(cstack5, cstack4);
        // New stack: 6
        // ICONST_2; Stack: 6
        cstack6.i = 2;
        // New stack: 7
        // SWAP; Stack: 7
        std::swap(cstack6, cstack5);
        // New stack: 7
        // AASTORE; Stack: 7
        if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2106LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack4.l, cstack5.i, cstack6.l); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 4
        // DUP_X2; Stack: 4
        cstack4 = cstack3; cstack3 = cstack2; cstack2 = cstack1; cstack1 = cstack4;
        // New stack: 5
        // DUP_X2; Stack: 5
        cstack5 = cstack4; cstack4 = cstack3; cstack3 = cstack2; cstack2 = cstack5;
        // New stack: 6
        // POP; Stack: 6
        ;
        // New stack: 5
        // INVOKESTATIC java/lang/Long.valueOf(J)Ljava/lang/Long;; Stack: 5
        if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { cclasses_mtx[2].lock(); if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[1]))) { cclasses[2] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[2].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (!cmethods[2]) { cmethods[2] = env->GetStaticMethodID((cclasses[2]), ((char *)(string_pool + 2402LL)), ((char *)(string_pool + 2410LL))); if (env->ExceptionCheck()) { return (jarray) 0; }  } cstack3.l = env->CallStaticObjectMethod((cclasses[2]), (cmethods[2]), cstack3.j); refs.insert(cstack3.l); 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 4
        // ICONST_1; Stack: 4
        cstack4.i = 1;
        // New stack: 5
        // SWAP; Stack: 5
        std::swap(cstack4, cstack3);
        // New stack: 5
        // AASTORE; Stack: 5
        if (cstack2.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2106LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack2.l, cstack3.i, cstack4.l); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 2
        // DUP_X1; Stack: 2
        cstack2 = cstack1; cstack1 = cstack0; cstack0 = cstack2;
        // New stack: 3
        // SWAP; Stack: 3
        std::swap(cstack2, cstack1);
        // New stack: 3
        // ICONST_0; Stack: 3
        cstack3.i = 0;
        // New stack: 4
        // SWAP; Stack: 4
        std::swap(cstack3, cstack2);
        // New stack: 4
        // AASTORE; Stack: 4
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2106LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack1.l, cstack2.i, cstack3.l); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // LDC -3787002501123637053; Stack: 1
        cstack1.j = -3787002501123637053LL;
        // New stack: 3
        // LLOAD 3; Stack: 3
        cstack3.j = clocal3.j;
        // New stack: 5
        // LABEL L3; Stack: 5
        L3: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 5
        // ICONST_1; Stack: 5
        cstack5.i = 1;
        // New stack: 6
        // ANEWARRAY java/lang/Object; Stack: 6
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack5.i < 0) utils::throw_re(env, ((char *)(string_pool + 217LL)), ((char *)(string_pool + 254LL)), -1); else { cstack5.l = env->NewObjectArray(cstack5.i, (cclasses[5]), nullptr); refs.insert(cstack5.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 6
        // DUP; Stack: 6
        cstack6 = cstack5;
        // New stack: 7
        // LDC Ldev/sakura/client/Mahiro_uT;; Stack: 7
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack7.l = (cclasses[6]);
        // New stack: 8
        // SWAP; Stack: 8
        std::swap(cstack7, cstack6);
        // New stack: 8
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.11716030067971395.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 8
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack8.l = lookup;
        // New stack: 9
        // LDC Ldev/sakura/client/Mahiro_hd;; Stack: 9
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack9.l = (cclasses[7]);
        // New stack: 10
        // LDC a; Stack: 10
        cstack10.l = (cstrings[6]);
        // New stack: 11
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;; Stack: 11
        cstack11.l = (cstrings[7]);
        // New stack: 12
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.11716030067971395.a()Ljava/lang/ClassLoader;; Stack: 12
        cstack12.l = classloader;
        // New stack: 13
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 13
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[3]) { cmethods[3] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 279LL)), ((char *)(string_pool + 306LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack11.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[3]), cstack11.l, cstack12.l); refs.insert(cstack11.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 12
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 12
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 379LL)), ((char *)(string_pool + 390LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack8.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 488LL)), -1); else { cstack8.l = env->CallObjectMethod(cstack8.l, (cmethods[4]), cstack9.l, cstack10.l, cstack11.l); refs.insert(cstack8.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 9
        // SWAP; Stack: 9
        std::swap(cstack8, cstack7);
        // New stack: 9
        // LDC \u00c9; Stack: 9
        cstack9.l = (cstrings[10]);
        // New stack: 10
        // SWAP; Stack: 10
        std::swap(cstack9, cstack8);
        // New stack: 10
        // LDC (Ljava/lang/Object;JJ)[B; Stack: 10
        cstack10.l = (cstrings[11]);
        // New stack: 11
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.11716030067971395.a()Ljava/lang/ClassLoader;; Stack: 11
        cstack11.l = classloader;
        // New stack: 12
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 12
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[3]) { cmethods[3] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 279LL)), ((char *)(string_pool + 306LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack10.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[3]), cstack10.l, cstack11.l); refs.insert(cstack10.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 11
        // SWAP; Stack: 11
        std::swap(cstack10, cstack9);
        // New stack: 11
        // LDC 0; Stack: 11
        cstack11.i = 0;
        // New stack: 12
        // ANEWARRAY java/lang/Object; Stack: 12
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack11.i < 0) utils::throw_re(env, ((char *)(string_pool + 217LL)), ((char *)(string_pool + 254LL)), -1); else { cstack11.l = env->NewObjectArray(cstack11.i, (cclasses[5]), nullptr); refs.insert(cstack11.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 12
        // CHECKCAST java/lang/Object; Stack: 12
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack11.l != nullptr && !env->IsInstanceOf(cstack11.l, (cclasses[5]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 513LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } 
        // New stack: 12
        // SWAP; Stack: 12
        std::swap(cstack11, cstack10);
        // New stack: 12
        // INVOKESTATIC native/magic/1/linkcallsite/obfuscator0.11716030067971395.a(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/invoke/MemberName;; Stack: 12
        cstack6.l = utils::link_call_site(env, cstack6.l, cstack7.l, cstack8.l, cstack9.l, cstack10.l, cstack11.l); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 7
        // POP; Stack: 7
        ;
        // New stack: 6
        // ICONST_0; Stack: 6
        cstack6.i = 0;
        // New stack: 7
        // AALOAD; Stack: 7
        if (cstack5.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 105LL)), -1); else { cstack5.l = env->GetObjectArrayElement((jobjectArray) cstack5.l, cstack6.i); refs.insert(cstack5.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 6
        // DUP; Stack: 6
        cstack6 = cstack5;
        // New stack: 7
        // INSTANCEOF java/lang/invoke/CallSite; Stack: 7
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack6.i = cstack6.l == nullptr ? false : env->IsInstanceOf(cstack6.l, (cclasses[10]));
        // New stack: 7
        // IFEQ L5; Stack: 7
        if (cstack6.i == 0) goto L5;
        // New stack: 6
        // INVOKEINTERFACE java/lang/invoke/CallSite.getTarget()Ljava/lang/invoke/MethodHandle;; Stack: 6
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[5]) { cmethods[5] = env->GetMethodID((cclasses[10]), ((char *)(string_pool + 530LL)), ((char *)(string_pool + 540LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack5.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 574LL)), -1); else { cstack5.l = env->CallObjectMethod(cstack5.l, (cmethods[5])); refs.insert(cstack5.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 6
        // LABEL L5; Stack: 6
        L5: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 6
        // FRAME FULL L: [[Ljava/lang/Object;, [B, 1, 4, [B, [B, 4, 4] S: [[Ljava/lang/Object;, 4, 4, java/lang/Object]; Stack: 6
        refs.erase(cstack0.l); refs.erase(cstack5.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal5.l); refs.erase(clocal6.l); 
        utils::clear_refs(env, refs);
        // New stack: 6
        // CHECKCAST java/lang/invoke/MethodHandle; Stack: 6
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack5.l != nullptr && !env->IsInstanceOf(cstack5.l, (cclasses[11]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 601LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } 
        // New stack: 6
        // GOTO L6; Stack: 6
        goto L6;
        // New stack: 6
        // LABEL L4; Stack: 6
        L4: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 6
        // FRAME SAME1 L: null S: [java/lang/Throwable]; Stack: 6
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal5.l); refs.erase(clocal6.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // INSTANCEOF java/lang/BootstrapMethodError; Stack: 2
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } cstack1.i = cstack1.l == nullptr ? false : env->IsInstanceOf(cstack1.l, (cclasses[12]));
        // New stack: 2
        // IFNE L7; Stack: 2
        if (cstack1.i != 0) goto L7;
        // New stack: 1
        // NEW java/lang/BootstrapMethodError; Stack: 1
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (jobject obj = env->AllocObject((cclasses[12]))) { cstack1.l = obj; refs.insert(obj); } 
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
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (!cmethods[6]) { cmethods[6] = env->GetMethodID((cclasses[12]), ((char *)(string_pool + 631LL)), ((char *)(string_pool + 638LL))); if (env->ExceptionCheck()) { return (jarray) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 663LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[12]), (cmethods[6]), cstack2.l); 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // LABEL L7; Stack: 1
        L7: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/Object]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal5.l); refs.erase(clocal6.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 686LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 0
        // LABEL L6; Stack: 0
        L6: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 0
        // FRAME FULL L: [[Ljava/lang/Object;, [B, 1, 4, [B, [B, 4, 4] S: [[Ljava/lang/Object;, 4, 4, java/lang/invoke/MethodHandle]; Stack: 0
        refs.erase(cstack0.l); refs.erase(cstack5.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal5.l); refs.erase(clocal6.l); 
        utils::clear_refs(env, refs);
        // New stack: 6
        // INVOKESTATIC native/magic/1/invoke/obfuscator0.11716030067971395.a(Ljava/lang/Object;JJLjava/lang/invoke/MethodHandle;)[B; Stack: 6
        if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { cclasses_mtx[13].lock(); if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[13] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[13].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (!cmethods[7]) { cmethods[7] = env->GetStaticMethodID((cclasses[13]), ((char *)(string_pool + 783LL)), ((char *)(string_pool + 798LL))); if (env->ExceptionCheck()) { return (jarray) 0; }  } cstack0.l = env->CallStaticObjectMethod((cclasses[13]), (cmethods[7]), cstack0.l, cstack1.j, cstack3.j, cstack5.l); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // CHECKCAST [B; Stack: 1
        if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { cclasses_mtx[1].lock(); if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { if (jclass clazz = env->FindClass(((char *)(string_pool + 979LL)))) { cclasses[1] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[1].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (cstack0.l != nullptr && !env->IsInstanceOf(cstack0.l, (cclasses[1]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 979LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (jarray) 0; } } 
        // New stack: 1
        // ASTORE 11; Stack: 1
        clocal11.l = cstack0.l; refs.insert(cstack0.l);
        // New stack: 0
        // ALOAD 11; Stack: 0
        cstack0.l = clocal11.l; refs.insert(cstack0.l);
        // New stack: 1
        // ALOAD 1; Stack: 1
        cstack1.l = clocal1.l; refs.insert(cstack1.l);
        // New stack: 2
        // ILOAD 2; Stack: 2
        cstack2.i = clocal2.i;
        // New stack: 3
        // LLOAD 9; Stack: 3
        cstack3.j = clocal9.j;
        // New stack: 5
        // ICONST_4; Stack: 5
        cstack5.i = 4;
        // New stack: 6
        // ANEWARRAY java/lang/Object; Stack: 6
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (cstack5.i < 0) utils::throw_re(env, ((char *)(string_pool + 217LL)), ((char *)(string_pool + 254LL)), -1); else { cstack5.l = env->NewObjectArray(cstack5.i, (cclasses[5]), nullptr); refs.insert(cstack5.l); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 6
        // DUP_X2; Stack: 6
        cstack6 = cstack5; cstack5 = cstack4; cstack4 = cstack3; cstack3 = cstack6;
        // New stack: 7
        // DUP_X2; Stack: 7
        cstack7 = cstack6; cstack6 = cstack5; cstack5 = cstack4; cstack4 = cstack7;
        // New stack: 8
        // POP; Stack: 8
        ;
        // New stack: 7
        // INVOKESTATIC java/lang/Long.valueOf(J)Ljava/lang/Long;; Stack: 7
        if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { cclasses_mtx[2].lock(); if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[1]))) { cclasses[2] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[2].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (!cmethods[2]) { cmethods[2] = env->GetStaticMethodID((cclasses[2]), ((char *)(string_pool + 2402LL)), ((char *)(string_pool + 2410LL))); if (env->ExceptionCheck()) { return (jarray) 0; }  } cstack5.l = env->CallStaticObjectMethod((cclasses[2]), (cmethods[2]), cstack5.j); refs.insert(cstack5.l); 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 6
        // ICONST_3; Stack: 6
        cstack6.i = 3;
        // New stack: 7
        // SWAP; Stack: 7
        std::swap(cstack6, cstack5);
        // New stack: 7
        // AASTORE; Stack: 7
        if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2106LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack4.l, cstack5.i, cstack6.l); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 4
        // DUP_X1; Stack: 4
        cstack4 = cstack3; cstack3 = cstack2; cstack2 = cstack4;
        // New stack: 5
        // SWAP; Stack: 5
        std::swap(cstack4, cstack3);
        // New stack: 5
        // INVOKESTATIC java/lang/Integer.valueOf(I)Ljava/lang/Integer;; Stack: 5
        if (!cclasses[3] || env->IsSameObject(cclasses[3], NULL)) { cclasses_mtx[3].lock(); if (!cclasses[3] || env->IsSameObject(cclasses[3], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[2]))) { cclasses[3] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[3].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (!cmethods[8]) { cmethods[8] = env->GetStaticMethodID((cclasses[3]), ((char *)(string_pool + 2402LL)), ((char *)(string_pool + 8488LL))); if (env->ExceptionCheck()) { return (jarray) 0; }  } cstack4.l = env->CallStaticObjectMethod((cclasses[3]), (cmethods[8]), cstack4.i); refs.insert(cstack4.l); 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 5
        // ICONST_2; Stack: 5
        cstack5.i = 2;
        // New stack: 6
        // SWAP; Stack: 6
        std::swap(cstack5, cstack4);
        // New stack: 6
        // AASTORE; Stack: 6
        if (cstack3.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2106LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack3.l, cstack4.i, cstack5.l); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 3
        // DUP_X1; Stack: 3
        cstack3 = cstack2; cstack2 = cstack1; cstack1 = cstack3;
        // New stack: 4
        // SWAP; Stack: 4
        std::swap(cstack3, cstack2);
        // New stack: 4
        // ICONST_1; Stack: 4
        cstack4.i = 1;
        // New stack: 5
        // SWAP; Stack: 5
        std::swap(cstack4, cstack3);
        // New stack: 5
        // AASTORE; Stack: 5
        if (cstack2.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2106LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack2.l, cstack3.i, cstack4.l); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 2
        // DUP_X1; Stack: 2
        cstack2 = cstack1; cstack1 = cstack0; cstack0 = cstack2;
        // New stack: 3
        // SWAP; Stack: 3
        std::swap(cstack2, cstack1);
        // New stack: 3
        // ICONST_0; Stack: 3
        cstack3.i = 0;
        // New stack: 4
        // SWAP; Stack: 4
        std::swap(cstack3, cstack2);
        // New stack: 4
        // AASTORE; Stack: 4
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2106LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack1.l, cstack2.i, cstack3.l); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // LDC -3782149718213977558; Stack: 1
        cstack1.j = -3782149718213977558LL;
        // New stack: 3
        // LLOAD 3; Stack: 3
        cstack3.j = clocal3.j;
        // New stack: 5
        // LABEL L1; Stack: 5
        L1: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 5
        // ICONST_1; Stack: 5
        cstack5.i = 1;
        // New stack: 6
        // ANEWARRAY java/lang/Object; Stack: 6
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (cstack5.i < 0) utils::throw_re(env, ((char *)(string_pool + 217LL)), ((char *)(string_pool + 254LL)), -1); else { cstack5.l = env->NewObjectArray(cstack5.i, (cclasses[5]), nullptr); refs.insert(cstack5.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 6
        // DUP; Stack: 6
        cstack6 = cstack5;
        // New stack: 7
        // LDC Ldev/sakura/client/Mahiro_uT;; Stack: 7
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } cstack7.l = (cclasses[6]);
        // New stack: 8
        // SWAP; Stack: 8
        std::swap(cstack7, cstack6);
        // New stack: 8
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.11716030067971395.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 8
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } cstack8.l = lookup;
        // New stack: 9
        // LDC Ldev/sakura/client/Mahiro_hd;; Stack: 9
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } cstack9.l = (cclasses[7]);
        // New stack: 10
        // LDC a; Stack: 10
        cstack10.l = (cstrings[6]);
        // New stack: 11
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;; Stack: 11
        cstack11.l = (cstrings[7]);
        // New stack: 12
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.11716030067971395.a()Ljava/lang/ClassLoader;; Stack: 12
        cstack12.l = classloader;
        // New stack: 13
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 13
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (!cmethods[3]) { cmethods[3] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 279LL)), ((char *)(string_pool + 306LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }  } cstack11.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[3]), cstack11.l, cstack12.l); refs.insert(cstack11.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 12
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 12
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 379LL)), ((char *)(string_pool + 390LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }  } if (cstack8.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 488LL)), -1); else { cstack8.l = env->CallObjectMethod(cstack8.l, (cmethods[4]), cstack9.l, cstack10.l, cstack11.l); refs.insert(cstack8.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 9
        // SWAP; Stack: 9
        std::swap(cstack8, cstack7);
        // New stack: 9
        // LDC \u00c9; Stack: 9
        cstack9.l = (cstrings[10]);
        // New stack: 10
        // SWAP; Stack: 10
        std::swap(cstack9, cstack8);
        // New stack: 10
        // LDC (Ljava/lang/Object;JJ)[B; Stack: 10
        cstack10.l = (cstrings[11]);
        // New stack: 11
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.11716030067971395.a()Ljava/lang/ClassLoader;; Stack: 11
        cstack11.l = classloader;
        // New stack: 12
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 12
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (!cmethods[3]) { cmethods[3] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 279LL)), ((char *)(string_pool + 306LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }  } cstack10.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[3]), cstack10.l, cstack11.l); refs.insert(cstack10.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 11
        // SWAP; Stack: 11
        std::swap(cstack10, cstack9);
        // New stack: 11
        // LDC 0; Stack: 11
        cstack11.i = 0;
        // New stack: 12
        // ANEWARRAY java/lang/Object; Stack: 12
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (cstack11.i < 0) utils::throw_re(env, ((char *)(string_pool + 217LL)), ((char *)(string_pool + 254LL)), -1); else { cstack11.l = env->NewObjectArray(cstack11.i, (cclasses[5]), nullptr); refs.insert(cstack11.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 12
        // CHECKCAST java/lang/Object; Stack: 12
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (cstack11.l != nullptr && !env->IsInstanceOf(cstack11.l, (cclasses[5]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 513LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } 
        // New stack: 12
        // SWAP; Stack: 12
        std::swap(cstack11, cstack10);
        // New stack: 12
        // INVOKESTATIC native/magic/1/linkcallsite/obfuscator0.11716030067971395.a(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/invoke/MemberName;; Stack: 12
        cstack6.l = utils::link_call_site(env, cstack6.l, cstack7.l, cstack8.l, cstack9.l, cstack10.l, cstack11.l); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 7
        // POP; Stack: 7
        ;
        // New stack: 6
        // ICONST_0; Stack: 6
        cstack6.i = 0;
        // New stack: 7
        // AALOAD; Stack: 7
        if (cstack5.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 105LL)), -1); else { cstack5.l = env->GetObjectArrayElement((jobjectArray) cstack5.l, cstack6.i); refs.insert(cstack5.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 6
        // DUP; Stack: 6
        cstack6 = cstack5;
        // New stack: 7
        // INSTANCEOF java/lang/invoke/CallSite; Stack: 7
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } cstack6.i = cstack6.l == nullptr ? false : env->IsInstanceOf(cstack6.l, (cclasses[10]));
        // New stack: 7
        // IFEQ L8; Stack: 7
        if (cstack6.i == 0) goto L8;
        // New stack: 6
        // INVOKEINTERFACE java/lang/invoke/CallSite.getTarget()Ljava/lang/invoke/MethodHandle;; Stack: 6
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (!cmethods[5]) { cmethods[5] = env->GetMethodID((cclasses[10]), ((char *)(string_pool + 530LL)), ((char *)(string_pool + 540LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }  } if (cstack5.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 574LL)), -1); else { cstack5.l = env->CallObjectMethod(cstack5.l, (cmethods[5])); refs.insert(cstack5.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 6
        // LABEL L8; Stack: 6
        L8: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 6
        // FRAME FULL L: [[Ljava/lang/Object;, [B, 1, 4, [B, [B, 4, 4, [B] S: [[Ljava/lang/Object;, 4, 4, java/lang/Object]; Stack: 6
        refs.erase(cstack0.l); refs.erase(cstack5.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal5.l); refs.erase(clocal6.l); refs.erase(clocal11.l); 
        utils::clear_refs(env, refs);
        // New stack: 6
        // CHECKCAST java/lang/invoke/MethodHandle; Stack: 6
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (cstack5.l != nullptr && !env->IsInstanceOf(cstack5.l, (cclasses[11]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 601LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } 
        // New stack: 6
        // GOTO L9; Stack: 6
        goto L9;
        // New stack: 6
        // LABEL L2; Stack: 6
        L2: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 6
        // FRAME SAME1 L: null S: [java/lang/Throwable]; Stack: 6
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal5.l); refs.erase(clocal6.l); refs.erase(clocal11.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // INSTANCEOF java/lang/BootstrapMethodError; Stack: 2
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } cstack1.i = cstack1.l == nullptr ? false : env->IsInstanceOf(cstack1.l, (cclasses[12]));
        // New stack: 2
        // IFNE L10; Stack: 2
        if (cstack1.i != 0) goto L10;
        // New stack: 1
        // NEW java/lang/BootstrapMethodError; Stack: 1
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (jobject obj = env->AllocObject((cclasses[12]))) { cstack1.l = obj; refs.insert(obj); } 
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
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (!cmethods[6]) { cmethods[6] = env->GetMethodID((cclasses[12]), ((char *)(string_pool + 631LL)), ((char *)(string_pool + 638LL))); if (env->ExceptionCheck()) { return (jarray) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 663LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[12]), (cmethods[6]), cstack2.l); 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // LABEL L10; Stack: 1
        L10: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/Object]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal5.l); refs.erase(clocal6.l); refs.erase(clocal11.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 686LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 0
        // LABEL L9; Stack: 0
        L9: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 0
        // FRAME FULL L: [[Ljava/lang/Object;, [B, 1, 4, [B, [B, 4, 4, [B] S: [[Ljava/lang/Object;, 4, 4, java/lang/invoke/MethodHandle]; Stack: 0
        refs.erase(cstack0.l); refs.erase(cstack5.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal5.l); refs.erase(clocal6.l); refs.erase(clocal11.l); 
        utils::clear_refs(env, refs);
        // New stack: 6
        // INVOKESTATIC native/magic/1/invoke/obfuscator0.11716030067971395.a(Ljava/lang/Object;JJLjava/lang/invoke/MethodHandle;)[B; Stack: 6
        if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { cclasses_mtx[13].lock(); if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[13] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[13].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (!cmethods[7]) { cmethods[7] = env->GetStaticMethodID((cclasses[13]), ((char *)(string_pool + 783LL)), ((char *)(string_pool + 798LL))); if (env->ExceptionCheck()) { return (jarray) 0; }  } cstack0.l = env->CallStaticObjectMethod((cclasses[13]), (cmethods[7]), cstack0.l, cstack1.j, cstack3.j, cstack5.l); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // CHECKCAST [B; Stack: 1
        if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { cclasses_mtx[1].lock(); if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { if (jclass clazz = env->FindClass(((char *)(string_pool + 979LL)))) { cclasses[1] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[1].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (cstack0.l != nullptr && !env->IsInstanceOf(cstack0.l, (cclasses[1]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 979LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (jarray) 0; } } 
        // New stack: 1
        // ARETURN; Stack: 1
        return (jarray) cstack0.l;
        // New stack: 0
        return (jarray) 0;
        L_CATCH_0: if (env->IsInstanceOf(cstack0.l, (cclasses[0]))) { goto L4; }
        env->Throw((jthrowable) cstack0.l); return (jarray) 0;
        L_CATCH_1: if (env->IsInstanceOf(cstack0.l, (cclasses[0]))) { goto L2; }
        env->Throw((jthrowable) cstack0.l); return (jarray) 0;
    }
    
    // Mahiro_E([Ljava/lang/Object;)[B
    jarray JNICALL __ngen_native_Mahiro_E2(JNIEnv *env, jclass clazz, jarray arg0) {
        jobject classloader = utils::get_classloader_from_class(env, clazz);
        if (env->ExceptionCheck()) { return (jarray) 0; }
        if (classloader == nullptr) { env->FatalError(((char *)(string_pool + 54LL))); return (jarray) 0; }
    
        jobject lookup = nullptr;
        // try-catch-class java/lang/Throwable
        if (!cclasses[0] || env->IsSameObject(cclasses[0], NULL)) { cclasses_mtx[0].lock(); if (!cclasses[0] || env->IsSameObject(cclasses[0], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[0]))) { cclasses[0] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[0].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } }
        jvalue cstack0 = {}, cstack1 = {}, cstack2 = {}, cstack3 = {}, cstack4 = {}, cstack5 = {}, cstack6 = {}, cstack7 = {}, cstack8 = {}, cstack9 = {}, cstack10 = {}, cstack11 = {}, cstack12 = {};
        jvalue clocal0 = {}, clocal1 = {}, clocal2 = {}, clocal3 = {}, clocal4 = {}, clocal5 = {}, clocal6 = {}, clocal7 = {}, clocal8 = {};
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
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 105LL)), -1); else { cstack1.l = env->GetObjectArrayElement((jobjectArray) cstack1.l, cstack2.i); refs.insert(cstack1.l); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 2
        // CHECKCAST [B; Stack: 2
        if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { cclasses_mtx[1].lock(); if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { if (jclass clazz = env->FindClass(((char *)(string_pool + 979LL)))) { cclasses[1] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[1].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (cstack1.l != nullptr && !env->IsInstanceOf(cstack1.l, (cclasses[1]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 979LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (jarray) 0; } } 
        // New stack: 2
        // ASTORE 1; Stack: 2
        clocal1.l = cstack1.l; refs.insert(cstack1.l);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // ICONST_1; Stack: 2
        cstack2.i = 1;
        // New stack: 3
        // AALOAD; Stack: 3
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 105LL)), -1); else { cstack1.l = env->GetObjectArrayElement((jobjectArray) cstack1.l, cstack2.i); refs.insert(cstack1.l); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 2
        // CHECKCAST java/lang/Long; Stack: 2
        if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { cclasses_mtx[2].lock(); if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[1]))) { cclasses[2] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[2].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (cstack1.l != nullptr && !env->IsInstanceOf(cstack1.l, (cclasses[2]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 161LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (jarray) 0; } } 
        // New stack: 2
        // INVOKEVIRTUAL java/lang/Long.longValue()J; Stack: 2
        if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { cclasses_mtx[2].lock(); if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[1]))) { cclasses[2] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[2].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (!cmethods[0]) { cmethods[0] = env->GetMethodID((cclasses[2]), ((char *)(string_pool + 176LL)), ((char *)(string_pool + 186LL))); if (env->ExceptionCheck()) { return (jarray) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 190LL)), -1); else cstack1.j = env->CallLongMethod(cstack1.l, (cmethods[0])); 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 3
        // LSTORE 3; Stack: 3
        clocal3.j = cstack1.j;
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // ICONST_2; Stack: 2
        cstack2.i = 2;
        // New stack: 3
        // AALOAD; Stack: 3
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 105LL)), -1); else { cstack1.l = env->GetObjectArrayElement((jobjectArray) cstack1.l, cstack2.i); refs.insert(cstack1.l); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 2
        // CHECKCAST [B; Stack: 2
        if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { cclasses_mtx[1].lock(); if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { if (jclass clazz = env->FindClass(((char *)(string_pool + 979LL)))) { cclasses[1] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[1].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (cstack1.l != nullptr && !env->IsInstanceOf(cstack1.l, (cclasses[1]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 979LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (jarray) 0; } } 
        // New stack: 2
        // ASTORE 2; Stack: 2
        clocal2.l = cstack1.l; refs.insert(cstack1.l);
        // New stack: 1
        // POP; Stack: 1
        ;
        // New stack: 0
        // GETSTATIC dev/sakura/client/Mahiro_uT.a J; Stack: 0
        if (!cclasses[4]  || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (!cfields[0]) { cfields[0] = env->GetStaticFieldID((cclasses[4]), ((char *)(string_pool + 213LL)), ((char *)(string_pool + 215LL))); if (env->ExceptionCheck()) { return (jarray) 0; }  } cstack0.j = env->GetStaticLongField((cclasses[4]), (cfields[0])); 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 2
        // LLOAD 3; Stack: 2
        cstack2.j = clocal3.j;
        // New stack: 4
        // LXOR; Stack: 4
        cstack0.j = cstack0.j ^ cstack2.j;
        // New stack: 2
        // LSTORE 3; Stack: 2
        clocal3.j = cstack0.j;
        // New stack: 0
        // LLOAD 3; Stack: 0
        cstack0.j = clocal3.j;
        // New stack: 2
        // DUP2; Stack: 2
        cstack2 = cstack0; cstack3 = cstack1;
        // New stack: 4
        // LDC 140096203280702; Stack: 4
        cstack4.j = 140096203280702LL;
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
        // LDC -1545104056215936796; Stack: 0
        cstack0.j = -1545104056215936796LL;
        // New stack: 2
        // LLOAD 3; Stack: 2
        cstack2.j = clocal3.j;
        // New stack: 4
        // LABEL L5; Stack: 4
        L5: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 4
        // ICONST_1; Stack: 4
        cstack4.i = 1;
        // New stack: 5
        // ANEWARRAY java/lang/Object; Stack: 5
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack4.i < 0) utils::throw_re(env, ((char *)(string_pool + 217LL)), ((char *)(string_pool + 254LL)), -1); else { cstack4.l = env->NewObjectArray(cstack4.i, (cclasses[5]), nullptr); refs.insert(cstack4.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 5
        // DUP; Stack: 5
        cstack5 = cstack4;
        // New stack: 6
        // LDC Ldev/sakura/client/Mahiro_uT;; Stack: 6
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack6.l = (cclasses[6]);
        // New stack: 7
        // SWAP; Stack: 7
        std::swap(cstack6, cstack5);
        // New stack: 7
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.11716030067971395.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 7
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack7.l = lookup;
        // New stack: 8
        // LDC Ldev/sakura/client/Mahiro_hd;; Stack: 8
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack8.l = (cclasses[7]);
        // New stack: 9
        // LDC a; Stack: 9
        cstack9.l = (cstrings[6]);
        // New stack: 10
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;; Stack: 10
        cstack10.l = (cstrings[7]);
        // New stack: 11
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.11716030067971395.a()Ljava/lang/ClassLoader;; Stack: 11
        cstack11.l = classloader;
        // New stack: 12
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 12
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[3]) { cmethods[3] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 279LL)), ((char *)(string_pool + 306LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack10.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[3]), cstack10.l, cstack11.l); refs.insert(cstack10.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 11
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 11
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 379LL)), ((char *)(string_pool + 390LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack7.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 488LL)), -1); else { cstack7.l = env->CallObjectMethod(cstack7.l, (cmethods[4]), cstack8.l, cstack9.l, cstack10.l); refs.insert(cstack7.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 8
        // SWAP; Stack: 8
        std::swap(cstack7, cstack6);
        // New stack: 8
        // LDC \u00c9; Stack: 8
        cstack8.l = (cstrings[10]);
        // New stack: 9
        // SWAP; Stack: 9
        std::swap(cstack8, cstack7);
        // New stack: 9
        // LDC (JJ)[Ldev/sakura/client/Mahiro_m;; Stack: 9
        cstack9.l = (cstrings[16]);
        // New stack: 10
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.11716030067971395.a()Ljava/lang/ClassLoader;; Stack: 10
        cstack10.l = classloader;
        // New stack: 11
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 11
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[3]) { cmethods[3] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 279LL)), ((char *)(string_pool + 306LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack9.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[3]), cstack9.l, cstack10.l); refs.insert(cstack9.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 10
        // SWAP; Stack: 10
        std::swap(cstack9, cstack8);
        // New stack: 10
        // LDC 0; Stack: 10
        cstack10.i = 0;
        // New stack: 11
        // ANEWARRAY java/lang/Object; Stack: 11
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack10.i < 0) utils::throw_re(env, ((char *)(string_pool + 217LL)), ((char *)(string_pool + 254LL)), -1); else { cstack10.l = env->NewObjectArray(cstack10.i, (cclasses[5]), nullptr); refs.insert(cstack10.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 11
        // CHECKCAST java/lang/Object; Stack: 11
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack10.l != nullptr && !env->IsInstanceOf(cstack10.l, (cclasses[5]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 513LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } 
        // New stack: 11
        // SWAP; Stack: 11
        std::swap(cstack10, cstack9);
        // New stack: 11
        // INVOKESTATIC native/magic/1/linkcallsite/obfuscator0.11716030067971395.a(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/invoke/MemberName;; Stack: 11
        cstack5.l = utils::link_call_site(env, cstack5.l, cstack6.l, cstack7.l, cstack8.l, cstack9.l, cstack10.l); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 6
        // POP; Stack: 6
        ;
        // New stack: 5
        // ICONST_0; Stack: 5
        cstack5.i = 0;
        // New stack: 6
        // AALOAD; Stack: 6
        if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 105LL)), -1); else { cstack4.l = env->GetObjectArrayElement((jobjectArray) cstack4.l, cstack5.i); refs.insert(cstack4.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 5
        // DUP; Stack: 5
        cstack5 = cstack4;
        // New stack: 6
        // INSTANCEOF java/lang/invoke/CallSite; Stack: 6
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack5.i = cstack5.l == nullptr ? false : env->IsInstanceOf(cstack5.l, (cclasses[10]));
        // New stack: 6
        // IFEQ L7; Stack: 6
        if (cstack5.i == 0) goto L7;
        // New stack: 5
        // INVOKEINTERFACE java/lang/invoke/CallSite.getTarget()Ljava/lang/invoke/MethodHandle;; Stack: 5
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[5]) { cmethods[5] = env->GetMethodID((cclasses[10]), ((char *)(string_pool + 530LL)), ((char *)(string_pool + 540LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 574LL)), -1); else { cstack4.l = env->CallObjectMethod(cstack4.l, (cmethods[5])); refs.insert(cstack4.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 5
        // LABEL L7; Stack: 5
        L7: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 5
        // FRAME FULL L: [[Ljava/lang/Object;, [B, [B, 4, 4] S: [4, 4, java/lang/Object]; Stack: 5
        refs.erase(cstack4.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); 
        utils::clear_refs(env, refs);
        // New stack: 5
        // CHECKCAST java/lang/invoke/MethodHandle; Stack: 5
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack4.l != nullptr && !env->IsInstanceOf(cstack4.l, (cclasses[11]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 601LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } 
        // New stack: 5
        // GOTO L8; Stack: 5
        goto L8;
        // New stack: 5
        // LABEL L6; Stack: 5
        L6: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 5
        // FRAME SAME1 L: null S: [java/lang/Throwable]; Stack: 5
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // INSTANCEOF java/lang/BootstrapMethodError; Stack: 2
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } cstack1.i = cstack1.l == nullptr ? false : env->IsInstanceOf(cstack1.l, (cclasses[12]));
        // New stack: 2
        // IFNE L9; Stack: 2
        if (cstack1.i != 0) goto L9;
        // New stack: 1
        // NEW java/lang/BootstrapMethodError; Stack: 1
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (jobject obj = env->AllocObject((cclasses[12]))) { cstack1.l = obj; refs.insert(obj); } 
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
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (!cmethods[6]) { cmethods[6] = env->GetMethodID((cclasses[12]), ((char *)(string_pool + 631LL)), ((char *)(string_pool + 638LL))); if (env->ExceptionCheck()) { return (jarray) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 663LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[12]), (cmethods[6]), cstack2.l); 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // LABEL L9; Stack: 1
        L9: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/Object]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 686LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 0
        // LABEL L8; Stack: 0
        L8: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 0
        // FRAME FULL L: [[Ljava/lang/Object;, [B, [B, 4, 4] S: [4, 4, java/lang/invoke/MethodHandle]; Stack: 0
        refs.erase(cstack4.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); 
        utils::clear_refs(env, refs);
        // New stack: 5
        // INVOKESTATIC native/magic/1/invoke/obfuscator0.11716030067971395.a(JJLjava/lang/invoke/MethodHandle;)[Ldev/sakura/client/Mahiro_m;; Stack: 5
        if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { cclasses_mtx[13].lock(); if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[13] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[13].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (!cmethods[9]) { cmethods[9] = env->GetStaticMethodID((cclasses[13]), ((char *)(string_pool + 6370LL)), ((char *)(string_pool + 6386LL))); if (env->ExceptionCheck()) { return (jarray) 0; }  } cstack0.l = env->CallStaticObjectMethod((cclasses[13]), (cmethods[9]), cstack0.j, cstack2.j, cstack4.l); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // CHECKCAST [Ldev/sakura/client/Mahiro_m;; Stack: 1
        if (!cclasses[14] || env->IsSameObject(cclasses[14], NULL)) { cclasses_mtx[14].lock(); if (!cclasses[14] || env->IsSameObject(cclasses[14], NULL)) { if (jclass clazz = env->FindClass(((char *)(string_pool + 11390LL)))) { cclasses[14] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[14].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (cstack0.l != nullptr && !env->IsInstanceOf(cstack0.l, (cclasses[14]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 11390LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (jarray) 0; } } 
        // New stack: 1
        // ASTORE 7; Stack: 1
        clocal7.l = cstack0.l; refs.insert(cstack0.l);
        // New stack: 0
        // ALOAD 2; Stack: 0
        cstack0.l = clocal2.l; refs.insert(cstack0.l);
        // New stack: 1
        // ALOAD 7; Stack: 1
        cstack1.l = clocal7.l; refs.insert(cstack1.l);
        // New stack: 2
        // IFNULL L10; Stack: 2
        if (env->IsSameObject(cstack1.l, nullptr)) goto L10;
        // New stack: 1
        // IFNONNULL L11; Stack: 1
        if (!env->IsSameObject(cstack0.l, nullptr)) goto L11;
        // New stack: 0
        // SIPUSH 10782; Stack: 0
        cstack0.i = (jint) 10782;
        // New stack: 1
        // LDC 2885904915599865231; Stack: 1
        cstack1.j = 2885904915599865231LL;
        // New stack: 3
        // LLOAD 3; Stack: 3
        cstack3.j = clocal3.j;
        // New stack: 5
        // LXOR; Stack: 5
        cstack1.j = cstack1.j ^ cstack3.j;
        // New stack: 3
        // LABEL L3; Stack: 3
        L3: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 3
        // ICONST_1; Stack: 3
        cstack3.i = 1;
        // New stack: 4
        // ANEWARRAY java/lang/Object; Stack: 4
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (cstack3.i < 0) utils::throw_re(env, ((char *)(string_pool + 217LL)), ((char *)(string_pool + 254LL)), -1); else { cstack3.l = env->NewObjectArray(cstack3.i, (cclasses[5]), nullptr); refs.insert(cstack3.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 4
        // DUP; Stack: 4
        cstack4 = cstack3;
        // New stack: 5
        // LDC Ldev/sakura/client/Mahiro_uT;; Stack: 5
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } cstack5.l = (cclasses[6]);
        // New stack: 6
        // SWAP; Stack: 6
        std::swap(cstack5, cstack4);
        // New stack: 6
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.11716030067971395.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 6
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } cstack6.l = lookup;
        // New stack: 7
        // LDC Ldev/sakura/client/Mahiro_uT;; Stack: 7
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } cstack7.l = (cclasses[6]);
        // New stack: 8
        // LDC b; Stack: 8
        cstack8.l = (cstrings[17]);
        // New stack: 9
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;; Stack: 9
        cstack9.l = (cstrings[7]);
        // New stack: 10
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.11716030067971395.a()Ljava/lang/ClassLoader;; Stack: 10
        cstack10.l = classloader;
        // New stack: 11
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 11
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (!cmethods[3]) { cmethods[3] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 279LL)), ((char *)(string_pool + 306LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }  } cstack9.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[3]), cstack9.l, cstack10.l); refs.insert(cstack9.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 10
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 10
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 379LL)), ((char *)(string_pool + 390LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }  } if (cstack6.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 488LL)), -1); else { cstack6.l = env->CallObjectMethod(cstack6.l, (cmethods[4]), cstack7.l, cstack8.l, cstack9.l); refs.insert(cstack6.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 7
        // SWAP; Stack: 7
        std::swap(cstack6, cstack5);
        // New stack: 7
        // LDC u; Stack: 7
        cstack7.l = (cstrings[18]);
        // New stack: 8
        // SWAP; Stack: 8
        std::swap(cstack7, cstack6);
        // New stack: 8
        // LDC (IJ)I; Stack: 8
        cstack8.l = (cstrings[19]);
        // New stack: 9
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.11716030067971395.a()Ljava/lang/ClassLoader;; Stack: 9
        cstack9.l = classloader;
        // New stack: 10
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 10
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (!cmethods[3]) { cmethods[3] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 279LL)), ((char *)(string_pool + 306LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }  } cstack8.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[3]), cstack8.l, cstack9.l); refs.insert(cstack8.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 9
        // SWAP; Stack: 9
        std::swap(cstack8, cstack7);
        // New stack: 9
        // LDC 0; Stack: 9
        cstack9.i = 0;
        // New stack: 10
        // ANEWARRAY java/lang/Object; Stack: 10
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (cstack9.i < 0) utils::throw_re(env, ((char *)(string_pool + 217LL)), ((char *)(string_pool + 254LL)), -1); else { cstack9.l = env->NewObjectArray(cstack9.i, (cclasses[5]), nullptr); refs.insert(cstack9.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 10
        // CHECKCAST java/lang/Object; Stack: 10
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (cstack9.l != nullptr && !env->IsInstanceOf(cstack9.l, (cclasses[5]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 513LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } 
        // New stack: 10
        // SWAP; Stack: 10
        std::swap(cstack9, cstack8);
        // New stack: 10
        // INVOKESTATIC native/magic/1/linkcallsite/obfuscator0.11716030067971395.a(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/invoke/MemberName;; Stack: 10
        cstack4.l = utils::link_call_site(env, cstack4.l, cstack5.l, cstack6.l, cstack7.l, cstack8.l, cstack9.l); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 5
        // POP; Stack: 5
        ;
        // New stack: 4
        // ICONST_0; Stack: 4
        cstack4.i = 0;
        // New stack: 5
        // AALOAD; Stack: 5
        if (cstack3.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 105LL)), -1); else { cstack3.l = env->GetObjectArrayElement((jobjectArray) cstack3.l, cstack4.i); refs.insert(cstack3.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 4
        // DUP; Stack: 4
        cstack4 = cstack3;
        // New stack: 5
        // INSTANCEOF java/lang/invoke/CallSite; Stack: 5
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } cstack4.i = cstack4.l == nullptr ? false : env->IsInstanceOf(cstack4.l, (cclasses[10]));
        // New stack: 5
        // IFEQ L12; Stack: 5
        if (cstack4.i == 0) goto L12;
        // New stack: 4
        // INVOKEINTERFACE java/lang/invoke/CallSite.getTarget()Ljava/lang/invoke/MethodHandle;; Stack: 4
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (!cmethods[5]) { cmethods[5] = env->GetMethodID((cclasses[10]), ((char *)(string_pool + 530LL)), ((char *)(string_pool + 540LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }  } if (cstack3.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 574LL)), -1); else { cstack3.l = env->CallObjectMethod(cstack3.l, (cmethods[5])); refs.insert(cstack3.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 4
        // LABEL L12; Stack: 4
        L12: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 4
        // FRAME FULL L: [[Ljava/lang/Object;, [B, [B, 4, 4, [Ldev/sakura/client/Mahiro_m;] S: [1, 4, java/lang/Object]; Stack: 4
        refs.erase(cstack3.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal7.l); 
        utils::clear_refs(env, refs);
        // New stack: 4
        // CHECKCAST java/lang/invoke/MethodHandle; Stack: 4
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (cstack3.l != nullptr && !env->IsInstanceOf(cstack3.l, (cclasses[11]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 601LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } 
        // New stack: 4
        // GOTO L13; Stack: 4
        goto L13;
        // New stack: 4
        // LABEL L4; Stack: 4
        L4: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 4
        // FRAME SAME1 L: null S: [java/lang/Throwable]; Stack: 4
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal7.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // INSTANCEOF java/lang/BootstrapMethodError; Stack: 2
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } cstack1.i = cstack1.l == nullptr ? false : env->IsInstanceOf(cstack1.l, (cclasses[12]));
        // New stack: 2
        // IFNE L14; Stack: 2
        if (cstack1.i != 0) goto L14;
        // New stack: 1
        // NEW java/lang/BootstrapMethodError; Stack: 1
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (jobject obj = env->AllocObject((cclasses[12]))) { cstack1.l = obj; refs.insert(obj); } 
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
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (!cmethods[6]) { cmethods[6] = env->GetMethodID((cclasses[12]), ((char *)(string_pool + 631LL)), ((char *)(string_pool + 638LL))); if (env->ExceptionCheck()) { return (jarray) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 663LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[12]), (cmethods[6]), cstack2.l); 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // LABEL L14; Stack: 1
        L14: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/Object]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal7.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 686LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 0
        // LABEL L13; Stack: 0
        L13: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 0
        // FRAME FULL L: [[Ljava/lang/Object;, [B, [B, 4, 4, [Ldev/sakura/client/Mahiro_m;] S: [1, 4, java/lang/invoke/MethodHandle]; Stack: 0
        refs.erase(cstack3.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal7.l); 
        utils::clear_refs(env, refs);
        // New stack: 4
        // INVOKESTATIC native/magic/1/invoke/obfuscator0.11716030067971395.a(IJLjava/lang/invoke/MethodHandle;)I; Stack: 4
        if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { cclasses_mtx[13].lock(); if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[13] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[13].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (!cmethods[10]) { cmethods[10] = env->GetStaticMethodID((cclasses[13]), ((char *)(string_pool + 7610LL)), ((char *)(string_pool + 7626LL))); if (env->ExceptionCheck()) { return (jarray) 0; }  } cstack0.i = env->CallStaticIntMethod((cclasses[13]), (cmethods[10]), cstack0.i, cstack1.j, cstack3.l); 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // NEWARRAY 8; Stack: 1
        if (cstack0.i < 0) utils::throw_re(env, ((char *)(string_pool + 217LL)), ((char *)(string_pool + 1834LL)), -1); else { cstack0.l = env->NewByteArray(cstack0.i); refs.insert(cstack0.l); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // LABEL L10; Stack: 1
        L10: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [[B]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal7.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // GOTO L15; Stack: 1
        goto L15;
        // New stack: 1
        // LABEL L11; Stack: 1
        L11: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // FRAME SAME L: null S: null; Stack: 1
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal7.l); 
        utils::clear_refs(env, refs);
        // New stack: 0
        // ALOAD 2; Stack: 0
        cstack0.l = clocal2.l; refs.insert(cstack0.l);
        // New stack: 1
        // LABEL L15; Stack: 1
        L15: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [[B]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal7.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ASTORE 8; Stack: 1
        clocal8.l = cstack0.l; refs.insert(cstack0.l);
        // New stack: 0
        // ALOAD 8; Stack: 0
        cstack0.l = clocal8.l; refs.insert(cstack0.l);
        // New stack: 1
        // ALOAD 1; Stack: 1
        cstack1.l = clocal1.l; refs.insert(cstack1.l);
        // New stack: 2
        // ALOAD 7; Stack: 2
        cstack2.l = clocal7.l; refs.insert(cstack2.l);
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
        // NEWARRAY 8; Stack: 2
        if (cstack1.i < 0) utils::throw_re(env, ((char *)(string_pool + 217LL)), ((char *)(string_pool + 1834LL)), -1); else { cstack1.l = env->NewByteArray(cstack1.i); refs.insert(cstack1.l); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 2
        // LABEL L16; Stack: 2
        L16: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 2
        // FRAME FULL L: [[Ljava/lang/Object;, [B, [B, 4, 4, [Ldev/sakura/client/Mahiro_m;, [B] S: [[B, [B]; Stack: 2
        refs.erase(cstack0.l); refs.erase(cstack1.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal7.l); refs.erase(clocal8.l); 
        utils::clear_refs(env, refs);
        // New stack: 2
        // GOTO L18; Stack: 2
        goto L18;
        // New stack: 2
        // LABEL L17; Stack: 2
        L17: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 2
        // FRAME SAME1 L: null S: [[B]; Stack: 2
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal7.l); refs.erase(clocal8.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ALOAD 1; Stack: 1
        cstack1.l = clocal1.l; refs.insert(cstack1.l);
        // New stack: 2
        // LABEL L18; Stack: 2
        L18: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 2
        // FRAME FULL L: [[Ljava/lang/Object;, [B, [B, 4, 4, [Ldev/sakura/client/Mahiro_m;, [B] S: [[B, [B]; Stack: 2
        refs.erase(cstack0.l); refs.erase(cstack1.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal7.l); refs.erase(clocal8.l); 
        utils::clear_refs(env, refs);
        // New stack: 2
        // LLOAD 5; Stack: 2
        cstack2.j = clocal5.j;
        // New stack: 4
        // ICONST_3; Stack: 4
        cstack4.i = 3;
        // New stack: 5
        // ANEWARRAY java/lang/Object; Stack: 5
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (cstack4.i < 0) utils::throw_re(env, ((char *)(string_pool + 217LL)), ((char *)(string_pool + 254LL)), -1); else { cstack4.l = env->NewObjectArray(cstack4.i, (cclasses[5]), nullptr); refs.insert(cstack4.l); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 5
        // DUP_X2; Stack: 5
        cstack5 = cstack4; cstack4 = cstack3; cstack3 = cstack2; cstack2 = cstack5;
        // New stack: 6
        // DUP_X2; Stack: 6
        cstack6 = cstack5; cstack5 = cstack4; cstack4 = cstack3; cstack3 = cstack6;
        // New stack: 7
        // POP; Stack: 7
        ;
        // New stack: 6
        // INVOKESTATIC java/lang/Long.valueOf(J)Ljava/lang/Long;; Stack: 6
        if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { cclasses_mtx[2].lock(); if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[1]))) { cclasses[2] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[2].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (!cmethods[2]) { cmethods[2] = env->GetStaticMethodID((cclasses[2]), ((char *)(string_pool + 2402LL)), ((char *)(string_pool + 2410LL))); if (env->ExceptionCheck()) { return (jarray) 0; }  } cstack4.l = env->CallStaticObjectMethod((cclasses[2]), (cmethods[2]), cstack4.j); refs.insert(cstack4.l); 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 5
        // ICONST_2; Stack: 5
        cstack5.i = 2;
        // New stack: 6
        // SWAP; Stack: 6
        std::swap(cstack5, cstack4);
        // New stack: 6
        // AASTORE; Stack: 6
        if (cstack3.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2106LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack3.l, cstack4.i, cstack5.l); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 3
        // DUP_X1; Stack: 3
        cstack3 = cstack2; cstack2 = cstack1; cstack1 = cstack3;
        // New stack: 4
        // SWAP; Stack: 4
        std::swap(cstack3, cstack2);
        // New stack: 4
        // ICONST_1; Stack: 4
        cstack4.i = 1;
        // New stack: 5
        // SWAP; Stack: 5
        std::swap(cstack4, cstack3);
        // New stack: 5
        // AASTORE; Stack: 5
        if (cstack2.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2106LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack2.l, cstack3.i, cstack4.l); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 2
        // DUP_X1; Stack: 2
        cstack2 = cstack1; cstack1 = cstack0; cstack0 = cstack2;
        // New stack: 3
        // SWAP; Stack: 3
        std::swap(cstack2, cstack1);
        // New stack: 3
        // ICONST_0; Stack: 3
        cstack3.i = 0;
        // New stack: 4
        // SWAP; Stack: 4
        std::swap(cstack3, cstack2);
        // New stack: 4
        // AASTORE; Stack: 4
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2106LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack1.l, cstack2.i, cstack3.l); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // LDC -1549353158970677154; Stack: 1
        cstack1.j = -1549353158970677154LL;
        // New stack: 3
        // LLOAD 3; Stack: 3
        cstack3.j = clocal3.j;
        // New stack: 5
        // LABEL L1; Stack: 5
        L1: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 5
        // ICONST_1; Stack: 5
        cstack5.i = 1;
        // New stack: 6
        // ANEWARRAY java/lang/Object; Stack: 6
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } if (cstack5.i < 0) utils::throw_re(env, ((char *)(string_pool + 217LL)), ((char *)(string_pool + 254LL)), -1); else { cstack5.l = env->NewObjectArray(cstack5.i, (cclasses[5]), nullptr); refs.insert(cstack5.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 6
        // DUP; Stack: 6
        cstack6 = cstack5;
        // New stack: 7
        // LDC Ldev/sakura/client/Mahiro_uT;; Stack: 7
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } cstack7.l = (cclasses[6]);
        // New stack: 8
        // SWAP; Stack: 8
        std::swap(cstack7, cstack6);
        // New stack: 8
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.11716030067971395.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 8
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } cstack8.l = lookup;
        // New stack: 9
        // LDC Ldev/sakura/client/Mahiro_hd;; Stack: 9
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } cstack9.l = (cclasses[7]);
        // New stack: 10
        // LDC a; Stack: 10
        cstack10.l = (cstrings[6]);
        // New stack: 11
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;; Stack: 11
        cstack11.l = (cstrings[7]);
        // New stack: 12
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.11716030067971395.a()Ljava/lang/ClassLoader;; Stack: 12
        cstack12.l = classloader;
        // New stack: 13
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 13
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } if (!cmethods[3]) { cmethods[3] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 279LL)), ((char *)(string_pool + 306LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }  } cstack11.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[3]), cstack11.l, cstack12.l); refs.insert(cstack11.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 12
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 12
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 379LL)), ((char *)(string_pool + 390LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }  } if (cstack8.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 488LL)), -1); else { cstack8.l = env->CallObjectMethod(cstack8.l, (cmethods[4]), cstack9.l, cstack10.l, cstack11.l); refs.insert(cstack8.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 9
        // SWAP; Stack: 9
        std::swap(cstack8, cstack7);
        // New stack: 9
        // LDC \u00c9; Stack: 9
        cstack9.l = (cstrings[10]);
        // New stack: 10
        // SWAP; Stack: 10
        std::swap(cstack9, cstack8);
        // New stack: 10
        // LDC (Ljava/lang/Object;JJ)[B; Stack: 10
        cstack10.l = (cstrings[11]);
        // New stack: 11
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.11716030067971395.a()Ljava/lang/ClassLoader;; Stack: 11
        cstack11.l = classloader;
        // New stack: 12
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 12
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } if (!cmethods[3]) { cmethods[3] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 279LL)), ((char *)(string_pool + 306LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }  } cstack10.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[3]), cstack10.l, cstack11.l); refs.insert(cstack10.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 11
        // SWAP; Stack: 11
        std::swap(cstack10, cstack9);
        // New stack: 11
        // LDC 0; Stack: 11
        cstack11.i = 0;
        // New stack: 12
        // ANEWARRAY java/lang/Object; Stack: 12
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } if (cstack11.i < 0) utils::throw_re(env, ((char *)(string_pool + 217LL)), ((char *)(string_pool + 254LL)), -1); else { cstack11.l = env->NewObjectArray(cstack11.i, (cclasses[5]), nullptr); refs.insert(cstack11.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 12
        // CHECKCAST java/lang/Object; Stack: 12
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } if (cstack11.l != nullptr && !env->IsInstanceOf(cstack11.l, (cclasses[5]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 513LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } 
        // New stack: 12
        // SWAP; Stack: 12
        std::swap(cstack11, cstack10);
        // New stack: 12
        // INVOKESTATIC native/magic/1/linkcallsite/obfuscator0.11716030067971395.a(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/invoke/MemberName;; Stack: 12
        cstack6.l = utils::link_call_site(env, cstack6.l, cstack7.l, cstack8.l, cstack9.l, cstack10.l, cstack11.l); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 7
        // POP; Stack: 7
        ;
        // New stack: 6
        // ICONST_0; Stack: 6
        cstack6.i = 0;
        // New stack: 7
        // AALOAD; Stack: 7
        if (cstack5.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 105LL)), -1); else { cstack5.l = env->GetObjectArrayElement((jobjectArray) cstack5.l, cstack6.i); refs.insert(cstack5.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 6
        // DUP; Stack: 6
        cstack6 = cstack5;
        // New stack: 7
        // INSTANCEOF java/lang/invoke/CallSite; Stack: 7
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } cstack6.i = cstack6.l == nullptr ? false : env->IsInstanceOf(cstack6.l, (cclasses[10]));
        // New stack: 7
        // IFEQ L19; Stack: 7
        if (cstack6.i == 0) goto L19;
        // New stack: 6
        // INVOKEINTERFACE java/lang/invoke/CallSite.getTarget()Ljava/lang/invoke/MethodHandle;; Stack: 6
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } if (!cmethods[5]) { cmethods[5] = env->GetMethodID((cclasses[10]), ((char *)(string_pool + 530LL)), ((char *)(string_pool + 540LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }  } if (cstack5.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 574LL)), -1); else { cstack5.l = env->CallObjectMethod(cstack5.l, (cmethods[5])); refs.insert(cstack5.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 6
        // LABEL L19; Stack: 6
        L19: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 6
        // FRAME FULL L: [[Ljava/lang/Object;, [B, [B, 4, 4, [Ldev/sakura/client/Mahiro_m;, [B] S: [[Ljava/lang/Object;, 4, 4, java/lang/Object]; Stack: 6
        refs.erase(cstack0.l); refs.erase(cstack5.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal7.l); refs.erase(clocal8.l); 
        utils::clear_refs(env, refs);
        // New stack: 6
        // CHECKCAST java/lang/invoke/MethodHandle; Stack: 6
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } if (cstack5.l != nullptr && !env->IsInstanceOf(cstack5.l, (cclasses[11]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 601LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } 
        // New stack: 6
        // GOTO L20; Stack: 6
        goto L20;
        // New stack: 6
        // LABEL L2; Stack: 6
        L2: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 6
        // FRAME SAME1 L: null S: [java/lang/Throwable]; Stack: 6
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal7.l); refs.erase(clocal8.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // INSTANCEOF java/lang/BootstrapMethodError; Stack: 2
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } cstack1.i = cstack1.l == nullptr ? false : env->IsInstanceOf(cstack1.l, (cclasses[12]));
        // New stack: 2
        // IFNE L21; Stack: 2
        if (cstack1.i != 0) goto L21;
        // New stack: 1
        // NEW java/lang/BootstrapMethodError; Stack: 1
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (jobject obj = env->AllocObject((cclasses[12]))) { cstack1.l = obj; refs.insert(obj); } 
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
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (!cmethods[6]) { cmethods[6] = env->GetMethodID((cclasses[12]), ((char *)(string_pool + 631LL)), ((char *)(string_pool + 638LL))); if (env->ExceptionCheck()) { return (jarray) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 663LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[12]), (cmethods[6]), cstack2.l); 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // LABEL L21; Stack: 1
        L21: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/Object]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal7.l); refs.erase(clocal8.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 686LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 0
        // LABEL L20; Stack: 0
        L20: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 0
        // FRAME FULL L: [[Ljava/lang/Object;, [B, [B, 4, 4, [Ldev/sakura/client/Mahiro_m;, [B] S: [[Ljava/lang/Object;, 4, 4, java/lang/invoke/MethodHandle]; Stack: 0
        refs.erase(cstack0.l); refs.erase(cstack5.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal7.l); refs.erase(clocal8.l); 
        utils::clear_refs(env, refs);
        // New stack: 6
        // INVOKESTATIC native/magic/1/invoke/obfuscator0.11716030067971395.a(Ljava/lang/Object;JJLjava/lang/invoke/MethodHandle;)[B; Stack: 6
        if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { cclasses_mtx[13].lock(); if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[13] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[13].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (!cmethods[7]) { cmethods[7] = env->GetStaticMethodID((cclasses[13]), ((char *)(string_pool + 783LL)), ((char *)(string_pool + 798LL))); if (env->ExceptionCheck()) { return (jarray) 0; }  } cstack0.l = env->CallStaticObjectMethod((cclasses[13]), (cmethods[7]), cstack0.l, cstack1.j, cstack3.j, cstack5.l); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // CHECKCAST [B; Stack: 1
        if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { cclasses_mtx[1].lock(); if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { if (jclass clazz = env->FindClass(((char *)(string_pool + 979LL)))) { cclasses[1] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[1].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (cstack0.l != nullptr && !env->IsInstanceOf(cstack0.l, (cclasses[1]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 979LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (jarray) 0; } } 
        // New stack: 1
        // ARETURN; Stack: 1
        return (jarray) cstack0.l;
        // New stack: 0
        return (jarray) 0;
        L_CATCH_1: if (env->IsInstanceOf(cstack0.l, (cclasses[0]))) { goto L4; }
        env->Throw((jthrowable) cstack0.l); return (jarray) 0;
        L_CATCH_0: if (env->IsInstanceOf(cstack0.l, (cclasses[0]))) { goto L6; }
        env->Throw((jthrowable) cstack0.l); return (jarray) 0;
        L_CATCH_2: if (env->IsInstanceOf(cstack0.l, (cclasses[0]))) { goto L2; }
        env->Throw((jthrowable) cstack0.l); return (jarray) 0;
    }
    
    // Mahiro_d([Ljava/lang/Object;)[B
    jarray JNICALL __ngen_native_Mahiro_d3(JNIEnv *env, jclass clazz, jarray arg0) {
        jobject classloader = utils::get_classloader_from_class(env, clazz);
        if (env->ExceptionCheck()) { return (jarray) 0; }
        if (classloader == nullptr) { env->FatalError(((char *)(string_pool + 54LL))); return (jarray) 0; }
    
        jobject lookup = nullptr;
        // try-catch-class java/lang/Throwable
        if (!cclasses[0] || env->IsSameObject(cclasses[0], NULL)) { cclasses_mtx[0].lock(); if (!cclasses[0] || env->IsSameObject(cclasses[0], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[0]))) { cclasses[0] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[0].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } }
        jvalue cstack0 = {}, cstack1 = {}, cstack2 = {}, cstack3 = {}, cstack4 = {}, cstack5 = {}, cstack6 = {}, cstack7 = {}, cstack8 = {}, cstack9 = {}, cstack10 = {}, cstack11 = {}, cstack12 = {}, cstack13 = {}, cstack14 = {}, cstack15 = {}, cstack16 = {};
        jvalue clocal0 = {}, clocal1 = {}, clocal2 = {}, clocal3 = {}, clocal4 = {}, clocal5 = {}, clocal6 = {}, clocal7 = {}, clocal8 = {}, clocal9 = {}, clocal10 = {}, clocal11 = {}, clocal12 = {}, clocal13 = {}, clocal14 = {}, clocal15 = {}, clocal16 = {}, clocal17 = {}, clocal18 = {};
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
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 105LL)), -1); else { cstack1.l = env->GetObjectArrayElement((jobjectArray) cstack1.l, cstack2.i); refs.insert(cstack1.l); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 2
        // CHECKCAST [B; Stack: 2
        if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { cclasses_mtx[1].lock(); if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { if (jclass clazz = env->FindClass(((char *)(string_pool + 979LL)))) { cclasses[1] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[1].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (cstack1.l != nullptr && !env->IsInstanceOf(cstack1.l, (cclasses[1]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 979LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (jarray) 0; } } 
        // New stack: 2
        // ASTORE 1; Stack: 2
        clocal1.l = cstack1.l; refs.insert(cstack1.l);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // ICONST_1; Stack: 2
        cstack2.i = 1;
        // New stack: 3
        // AALOAD; Stack: 3
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 105LL)), -1); else { cstack1.l = env->GetObjectArrayElement((jobjectArray) cstack1.l, cstack2.i); refs.insert(cstack1.l); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 2
        // CHECKCAST [B; Stack: 2
        if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { cclasses_mtx[1].lock(); if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { if (jclass clazz = env->FindClass(((char *)(string_pool + 979LL)))) { cclasses[1] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[1].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (cstack1.l != nullptr && !env->IsInstanceOf(cstack1.l, (cclasses[1]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 979LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (jarray) 0; } } 
        // New stack: 2
        // ASTORE 5; Stack: 2
        clocal5.l = cstack1.l; refs.insert(cstack1.l);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // ICONST_2; Stack: 2
        cstack2.i = 2;
        // New stack: 3
        // AALOAD; Stack: 3
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 105LL)), -1); else { cstack1.l = env->GetObjectArrayElement((jobjectArray) cstack1.l, cstack2.i); refs.insert(cstack1.l); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 2
        // CHECKCAST java/lang/Integer; Stack: 2
        if (!cclasses[3] || env->IsSameObject(cclasses[3], NULL)) { cclasses_mtx[3].lock(); if (!cclasses[3] || env->IsSameObject(cclasses[3], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[2]))) { cclasses[3] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[3].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (cstack1.l != nullptr && !env->IsInstanceOf(cstack1.l, (cclasses[3]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 2812LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (jarray) 0; } } 
        // New stack: 2
        // INVOKEVIRTUAL java/lang/Integer.intValue()I; Stack: 2
        if (!cclasses[3] || env->IsSameObject(cclasses[3], NULL)) { cclasses_mtx[3].lock(); if (!cclasses[3] || env->IsSameObject(cclasses[3], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[2]))) { cclasses[3] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[3].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (!cmethods[1]) { cmethods[1] = env->GetMethodID((cclasses[3]), ((char *)(string_pool + 2830LL)), ((char *)(string_pool + 2047LL))); if (env->ExceptionCheck()) { return (jarray) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2051LL)), -1); else cstack1.i = env->CallIntMethod(cstack1.l, (cmethods[1])); 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 2
        // ISTORE 2; Stack: 2
        clocal2.i = cstack1.i;
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // ICONST_3; Stack: 2
        cstack2.i = 3;
        // New stack: 3
        // AALOAD; Stack: 3
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 105LL)), -1); else { cstack1.l = env->GetObjectArrayElement((jobjectArray) cstack1.l, cstack2.i); refs.insert(cstack1.l); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 2
        // CHECKCAST java/lang/Long; Stack: 2
        if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { cclasses_mtx[2].lock(); if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[1]))) { cclasses[2] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[2].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (cstack1.l != nullptr && !env->IsInstanceOf(cstack1.l, (cclasses[2]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 161LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (jarray) 0; } } 
        // New stack: 2
        // INVOKEVIRTUAL java/lang/Long.longValue()J; Stack: 2
        if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { cclasses_mtx[2].lock(); if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[1]))) { cclasses[2] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[2].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (!cmethods[0]) { cmethods[0] = env->GetMethodID((cclasses[2]), ((char *)(string_pool + 176LL)), ((char *)(string_pool + 186LL))); if (env->ExceptionCheck()) { return (jarray) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 190LL)), -1); else cstack1.j = env->CallLongMethod(cstack1.l, (cmethods[0])); 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 3
        // LSTORE 3; Stack: 3
        clocal3.j = cstack1.j;
        // New stack: 1
        // POP; Stack: 1
        ;
        // New stack: 0
        // GETSTATIC dev/sakura/client/Mahiro_uT.a J; Stack: 0
        if (!cclasses[4]  || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (!cfields[0]) { cfields[0] = env->GetStaticFieldID((cclasses[4]), ((char *)(string_pool + 213LL)), ((char *)(string_pool + 215LL))); if (env->ExceptionCheck()) { return (jarray) 0; }  } cstack0.j = env->GetStaticLongField((cclasses[4]), (cfields[0])); 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 2
        // LLOAD 3; Stack: 2
        cstack2.j = clocal3.j;
        // New stack: 4
        // LXOR; Stack: 4
        cstack0.j = cstack0.j ^ cstack2.j;
        // New stack: 2
        // LSTORE 3; Stack: 2
        clocal3.j = cstack0.j;
        // New stack: 0
        // LLOAD 3; Stack: 0
        cstack0.j = clocal3.j;
        // New stack: 2
        // DUP2; Stack: 2
        cstack2 = cstack0; cstack3 = cstack1;
        // New stack: 4
        // LDC 106194324459957; Stack: 4
        cstack4.j = 106194324459957LL;
        // New stack: 6
        // LXOR; Stack: 6
        cstack2.j = cstack2.j ^ cstack4.j;
        // New stack: 4
        // LSTORE 6; Stack: 4
        clocal6.j = cstack2.j;
        // New stack: 2
        // DUP2; Stack: 2
        cstack2 = cstack0; cstack3 = cstack1;
        // New stack: 4
        // LDC 35479702562935; Stack: 4
        cstack4.j = 35479702562935LL;
        // New stack: 6
        // LXOR; Stack: 6
        cstack2.j = cstack2.j ^ cstack4.j;
        // New stack: 4
        // LSTORE 8; Stack: 4
        clocal8.j = cstack2.j;
        // New stack: 2
        // POP2; Stack: 2
        ;
        // New stack: 0
        // LDC -3186315280300844627; Stack: 0
        cstack0.j = -3186315280300844627LL;
        // New stack: 2
        // LLOAD 3; Stack: 2
        cstack2.j = clocal3.j;
        // New stack: 4
        // LABEL L21; Stack: 4
        L21: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 4
        // ICONST_1; Stack: 4
        cstack4.i = 1;
        // New stack: 5
        // ANEWARRAY java/lang/Object; Stack: 5
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack4.i < 0) utils::throw_re(env, ((char *)(string_pool + 217LL)), ((char *)(string_pool + 254LL)), -1); else { cstack4.l = env->NewObjectArray(cstack4.i, (cclasses[5]), nullptr); refs.insert(cstack4.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 5
        // DUP; Stack: 5
        cstack5 = cstack4;
        // New stack: 6
        // LDC Ldev/sakura/client/Mahiro_uT;; Stack: 6
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack6.l = (cclasses[6]);
        // New stack: 7
        // SWAP; Stack: 7
        std::swap(cstack6, cstack5);
        // New stack: 7
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.11716030067971395.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 7
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack7.l = lookup;
        // New stack: 8
        // LDC Ldev/sakura/client/Mahiro_hd;; Stack: 8
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack8.l = (cclasses[7]);
        // New stack: 9
        // LDC a; Stack: 9
        cstack9.l = (cstrings[6]);
        // New stack: 10
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;; Stack: 10
        cstack10.l = (cstrings[7]);
        // New stack: 11
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.11716030067971395.a()Ljava/lang/ClassLoader;; Stack: 11
        cstack11.l = classloader;
        // New stack: 12
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 12
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[3]) { cmethods[3] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 279LL)), ((char *)(string_pool + 306LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack10.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[3]), cstack10.l, cstack11.l); refs.insert(cstack10.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 11
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 11
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 379LL)), ((char *)(string_pool + 390LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack7.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 488LL)), -1); else { cstack7.l = env->CallObjectMethod(cstack7.l, (cmethods[4]), cstack8.l, cstack9.l, cstack10.l); refs.insert(cstack7.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 8
        // SWAP; Stack: 8
        std::swap(cstack7, cstack6);
        // New stack: 8
        // LDC \u00c9; Stack: 8
        cstack8.l = (cstrings[10]);
        // New stack: 9
        // SWAP; Stack: 9
        std::swap(cstack8, cstack7);
        // New stack: 9
        // LDC (JJ)[Ldev/sakura/client/Mahiro_m;; Stack: 9
        cstack9.l = (cstrings[16]);
        // New stack: 10
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.11716030067971395.a()Ljava/lang/ClassLoader;; Stack: 10
        cstack10.l = classloader;
        // New stack: 11
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 11
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[3]) { cmethods[3] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 279LL)), ((char *)(string_pool + 306LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack9.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[3]), cstack9.l, cstack10.l); refs.insert(cstack9.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 10
        // SWAP; Stack: 10
        std::swap(cstack9, cstack8);
        // New stack: 10
        // LDC 0; Stack: 10
        cstack10.i = 0;
        // New stack: 11
        // ANEWARRAY java/lang/Object; Stack: 11
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack10.i < 0) utils::throw_re(env, ((char *)(string_pool + 217LL)), ((char *)(string_pool + 254LL)), -1); else { cstack10.l = env->NewObjectArray(cstack10.i, (cclasses[5]), nullptr); refs.insert(cstack10.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 11
        // CHECKCAST java/lang/Object; Stack: 11
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack10.l != nullptr && !env->IsInstanceOf(cstack10.l, (cclasses[5]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 513LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } 
        // New stack: 11
        // SWAP; Stack: 11
        std::swap(cstack10, cstack9);
        // New stack: 11
        // INVOKESTATIC native/magic/1/linkcallsite/obfuscator0.11716030067971395.a(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/invoke/MemberName;; Stack: 11
        cstack5.l = utils::link_call_site(env, cstack5.l, cstack6.l, cstack7.l, cstack8.l, cstack9.l, cstack10.l); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 6
        // POP; Stack: 6
        ;
        // New stack: 5
        // ICONST_0; Stack: 5
        cstack5.i = 0;
        // New stack: 6
        // AALOAD; Stack: 6
        if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 105LL)), -1); else { cstack4.l = env->GetObjectArrayElement((jobjectArray) cstack4.l, cstack5.i); refs.insert(cstack4.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 5
        // DUP; Stack: 5
        cstack5 = cstack4;
        // New stack: 6
        // INSTANCEOF java/lang/invoke/CallSite; Stack: 6
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack5.i = cstack5.l == nullptr ? false : env->IsInstanceOf(cstack5.l, (cclasses[10]));
        // New stack: 6
        // IFEQ L23; Stack: 6
        if (cstack5.i == 0) goto L23;
        // New stack: 5
        // INVOKEINTERFACE java/lang/invoke/CallSite.getTarget()Ljava/lang/invoke/MethodHandle;; Stack: 5
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[5]) { cmethods[5] = env->GetMethodID((cclasses[10]), ((char *)(string_pool + 530LL)), ((char *)(string_pool + 540LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 574LL)), -1); else { cstack4.l = env->CallObjectMethod(cstack4.l, (cmethods[5])); refs.insert(cstack4.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 5
        // LABEL L23; Stack: 5
        L23: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 5
        // FRAME FULL L: [[Ljava/lang/Object;, [B, 1, 4, [B, 4, 4] S: [4, 4, java/lang/Object]; Stack: 5
        refs.erase(cstack4.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal5.l); 
        utils::clear_refs(env, refs);
        // New stack: 5
        // CHECKCAST java/lang/invoke/MethodHandle; Stack: 5
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack4.l != nullptr && !env->IsInstanceOf(cstack4.l, (cclasses[11]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 601LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } 
        // New stack: 5
        // GOTO L24; Stack: 5
        goto L24;
        // New stack: 5
        // LABEL L22; Stack: 5
        L22: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 5
        // FRAME SAME1 L: null S: [java/lang/Throwable]; Stack: 5
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal5.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // INSTANCEOF java/lang/BootstrapMethodError; Stack: 2
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } cstack1.i = cstack1.l == nullptr ? false : env->IsInstanceOf(cstack1.l, (cclasses[12]));
        // New stack: 2
        // IFNE L25; Stack: 2
        if (cstack1.i != 0) goto L25;
        // New stack: 1
        // NEW java/lang/BootstrapMethodError; Stack: 1
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (jobject obj = env->AllocObject((cclasses[12]))) { cstack1.l = obj; refs.insert(obj); } 
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
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (!cmethods[6]) { cmethods[6] = env->GetMethodID((cclasses[12]), ((char *)(string_pool + 631LL)), ((char *)(string_pool + 638LL))); if (env->ExceptionCheck()) { return (jarray) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 663LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[12]), (cmethods[6]), cstack2.l); 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // LABEL L25; Stack: 1
        L25: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/Object]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal5.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 686LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 0
        // LABEL L24; Stack: 0
        L24: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 0
        // FRAME FULL L: [[Ljava/lang/Object;, [B, 1, 4, [B, 4, 4] S: [4, 4, java/lang/invoke/MethodHandle]; Stack: 0
        refs.erase(cstack4.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal5.l); 
        utils::clear_refs(env, refs);
        // New stack: 5
        // INVOKESTATIC native/magic/1/invoke/obfuscator0.11716030067971395.a(JJLjava/lang/invoke/MethodHandle;)[Ldev/sakura/client/Mahiro_m;; Stack: 5
        if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { cclasses_mtx[13].lock(); if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[13] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[13].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (!cmethods[9]) { cmethods[9] = env->GetStaticMethodID((cclasses[13]), ((char *)(string_pool + 6370LL)), ((char *)(string_pool + 6386LL))); if (env->ExceptionCheck()) { return (jarray) 0; }  } cstack0.l = env->CallStaticObjectMethod((cclasses[13]), (cmethods[9]), cstack0.j, cstack2.j, cstack4.l); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // CHECKCAST [Ldev/sakura/client/Mahiro_m;; Stack: 1
        if (!cclasses[14] || env->IsSameObject(cclasses[14], NULL)) { cclasses_mtx[14].lock(); if (!cclasses[14] || env->IsSameObject(cclasses[14], NULL)) { if (jclass clazz = env->FindClass(((char *)(string_pool + 11390LL)))) { cclasses[14] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[14].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (cstack0.l != nullptr && !env->IsInstanceOf(cstack0.l, (cclasses[14]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 11390LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (jarray) 0; } } 
        // New stack: 1
        // ASTORE 10; Stack: 1
        clocal10.l = cstack0.l; refs.insert(cstack0.l);
        // New stack: 0
        // ILOAD 2; Stack: 0
        cstack0.i = clocal2.i;
        // New stack: 1
        // ALOAD 10; Stack: 1
        cstack1.l = clocal10.l; refs.insert(cstack1.l);
        // New stack: 2
        // IFNULL L26; Stack: 2
        if (env->IsSameObject(cstack1.l, nullptr)) goto L26;
        // New stack: 1
        // IFGT L27; Stack: 1
        if (cstack0.i > 0) goto L27;
        // New stack: 0
        // LABEL L28; Stack: 0
        L28: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 0
        // NEW java/lang/IllegalArgumentException; Stack: 0
        if (!cclasses[15] || env->IsSameObject(cclasses[15], NULL)) { cclasses_mtx[15].lock(); if (!cclasses[15] || env->IsSameObject(cclasses[15], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[20]))) { cclasses[15] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[15].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (jobject obj = env->AllocObject((cclasses[15]))) { cstack0.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // SIPUSH 22872; Stack: 2
        cstack2.i = (jint) 22872;
        // New stack: 3
        // LDC 3282449155444153819; Stack: 3
        cstack3.j = 3282449155444153819LL;
        // New stack: 5
        // LLOAD 3; Stack: 5
        cstack5.j = clocal3.j;
        // New stack: 7
        // LXOR; Stack: 7
        cstack3.j = cstack3.j ^ cstack5.j;
        // New stack: 5
        // LABEL L19; Stack: 5
        L19: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 5
        // ICONST_1; Stack: 5
        cstack5.i = 1;
        // New stack: 6
        // ANEWARRAY java/lang/Object; Stack: 6
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (cstack5.i < 0) utils::throw_re(env, ((char *)(string_pool + 217LL)), ((char *)(string_pool + 254LL)), -1); else { cstack5.l = env->NewObjectArray(cstack5.i, (cclasses[5]), nullptr); refs.insert(cstack5.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 6
        // DUP; Stack: 6
        cstack6 = cstack5;
        // New stack: 7
        // LDC Ldev/sakura/client/Mahiro_uT;; Stack: 7
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } cstack7.l = (cclasses[6]);
        // New stack: 8
        // SWAP; Stack: 8
        std::swap(cstack7, cstack6);
        // New stack: 8
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.11716030067971395.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 8
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } cstack8.l = lookup;
        // New stack: 9
        // LDC Ldev/sakura/client/Mahiro_uT;; Stack: 9
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } cstack9.l = (cclasses[6]);
        // New stack: 10
        // LDC a; Stack: 10
        cstack10.l = (cstrings[6]);
        // New stack: 11
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;; Stack: 11
        cstack11.l = (cstrings[7]);
        // New stack: 12
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.11716030067971395.a()Ljava/lang/ClassLoader;; Stack: 12
        cstack12.l = classloader;
        // New stack: 13
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 13
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (!cmethods[3]) { cmethods[3] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 279LL)), ((char *)(string_pool + 306LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }  } cstack11.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[3]), cstack11.l, cstack12.l); refs.insert(cstack11.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 12
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 12
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 379LL)), ((char *)(string_pool + 390LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }  } if (cstack8.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 488LL)), -1); else { cstack8.l = env->CallObjectMethod(cstack8.l, (cmethods[4]), cstack9.l, cstack10.l, cstack11.l); refs.insert(cstack8.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 9
        // SWAP; Stack: 9
        std::swap(cstack8, cstack7);
        // New stack: 9
        // LDC i; Stack: 9
        cstack9.l = (cstrings[21]);
        // New stack: 10
        // SWAP; Stack: 10
        std::swap(cstack9, cstack8);
        // New stack: 10
        // LDC (IJ)Ljava/lang/String;; Stack: 10
        cstack10.l = (cstrings[22]);
        // New stack: 11
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.11716030067971395.a()Ljava/lang/ClassLoader;; Stack: 11
        cstack11.l = classloader;
        // New stack: 12
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 12
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (!cmethods[3]) { cmethods[3] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 279LL)), ((char *)(string_pool + 306LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }  } cstack10.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[3]), cstack10.l, cstack11.l); refs.insert(cstack10.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 11
        // SWAP; Stack: 11
        std::swap(cstack10, cstack9);
        // New stack: 11
        // LDC 0; Stack: 11
        cstack11.i = 0;
        // New stack: 12
        // ANEWARRAY java/lang/Object; Stack: 12
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (cstack11.i < 0) utils::throw_re(env, ((char *)(string_pool + 217LL)), ((char *)(string_pool + 254LL)), -1); else { cstack11.l = env->NewObjectArray(cstack11.i, (cclasses[5]), nullptr); refs.insert(cstack11.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 12
        // CHECKCAST java/lang/Object; Stack: 12
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (cstack11.l != nullptr && !env->IsInstanceOf(cstack11.l, (cclasses[5]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 513LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } 
        // New stack: 12
        // SWAP; Stack: 12
        std::swap(cstack11, cstack10);
        // New stack: 12
        // INVOKESTATIC native/magic/1/linkcallsite/obfuscator0.11716030067971395.a(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/invoke/MemberName;; Stack: 12
        cstack6.l = utils::link_call_site(env, cstack6.l, cstack7.l, cstack8.l, cstack9.l, cstack10.l, cstack11.l); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 7
        // POP; Stack: 7
        ;
        // New stack: 6
        // ICONST_0; Stack: 6
        cstack6.i = 0;
        // New stack: 7
        // AALOAD; Stack: 7
        if (cstack5.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 105LL)), -1); else { cstack5.l = env->GetObjectArrayElement((jobjectArray) cstack5.l, cstack6.i); refs.insert(cstack5.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 6
        // DUP; Stack: 6
        cstack6 = cstack5;
        // New stack: 7
        // INSTANCEOF java/lang/invoke/CallSite; Stack: 7
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } cstack6.i = cstack6.l == nullptr ? false : env->IsInstanceOf(cstack6.l, (cclasses[10]));
        // New stack: 7
        // IFEQ L29; Stack: 7
        if (cstack6.i == 0) goto L29;
        // New stack: 6
        // INVOKEINTERFACE java/lang/invoke/CallSite.getTarget()Ljava/lang/invoke/MethodHandle;; Stack: 6
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (!cmethods[5]) { cmethods[5] = env->GetMethodID((cclasses[10]), ((char *)(string_pool + 530LL)), ((char *)(string_pool + 540LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }  } if (cstack5.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 574LL)), -1); else { cstack5.l = env->CallObjectMethod(cstack5.l, (cmethods[5])); refs.insert(cstack5.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 6
        // LABEL L29; Stack: 6
        L29: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 6
        // FRAME FULL L: [[Ljava/lang/Object;, [B, 1, 4, [B, 4, 4, [Ldev/sakura/client/Mahiro_m;] S: [org.objectweb.asm.tree.LabelNode@31c2affc, org.objectweb.asm.tree.LabelNode@31c2affc, 1, 4, java/lang/Object]; Stack: 6
        refs.erase(cstack0.l); refs.erase(cstack1.l); refs.erase(cstack5.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal5.l); refs.erase(clocal10.l); 
        utils::clear_refs(env, refs);
        // New stack: 6
        // CHECKCAST java/lang/invoke/MethodHandle; Stack: 6
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (cstack5.l != nullptr && !env->IsInstanceOf(cstack5.l, (cclasses[11]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 601LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } 
        // New stack: 6
        // GOTO L30; Stack: 6
        goto L30;
        // New stack: 6
        // LABEL L20; Stack: 6
        L20: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 6
        // FRAME SAME1 L: null S: [java/lang/Throwable]; Stack: 6
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal5.l); refs.erase(clocal10.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // INSTANCEOF java/lang/BootstrapMethodError; Stack: 2
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } cstack1.i = cstack1.l == nullptr ? false : env->IsInstanceOf(cstack1.l, (cclasses[12]));
        // New stack: 2
        // IFNE L31; Stack: 2
        if (cstack1.i != 0) goto L31;
        // New stack: 1
        // NEW java/lang/BootstrapMethodError; Stack: 1
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (jobject obj = env->AllocObject((cclasses[12]))) { cstack1.l = obj; refs.insert(obj); } 
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
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (!cmethods[6]) { cmethods[6] = env->GetMethodID((cclasses[12]), ((char *)(string_pool + 631LL)), ((char *)(string_pool + 638LL))); if (env->ExceptionCheck()) { return (jarray) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 663LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[12]), (cmethods[6]), cstack2.l); 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // LABEL L31; Stack: 1
        L31: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/Object]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal5.l); refs.erase(clocal10.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 686LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 0
        // LABEL L30; Stack: 0
        L30: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 0
        // FRAME FULL L: [[Ljava/lang/Object;, [B, 1, 4, [B, 4, 4, [Ldev/sakura/client/Mahiro_m;] S: [org.objectweb.asm.tree.LabelNode@31c2affc, org.objectweb.asm.tree.LabelNode@31c2affc, 1, 4, java/lang/invoke/MethodHandle]; Stack: 0
        refs.erase(cstack0.l); refs.erase(cstack1.l); refs.erase(cstack5.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal5.l); refs.erase(clocal10.l); 
        utils::clear_refs(env, refs);
        // New stack: 6
        // INVOKESTATIC native/magic/1/invoke/obfuscator0.11716030067971395.a(IJLjava/lang/invoke/MethodHandle;)Ljava/lang/String;; Stack: 6
        if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { cclasses_mtx[13].lock(); if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[13] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[13].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (!cmethods[11]) { cmethods[11] = env->GetStaticMethodID((cclasses[13]), ((char *)(string_pool + 697LL)), ((char *)(string_pool + 712LL))); if (env->ExceptionCheck()) { return (jarray) 0; }  } cstack2.l = env->CallStaticObjectMethod((cclasses[13]), (cmethods[11]), cstack2.i, cstack3.j, cstack5.l); refs.insert(cstack2.l); 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 3
        // CHECKCAST java/lang/String; Stack: 3
        if (!cclasses[16] || env->IsSameObject(cclasses[16], NULL)) { cclasses_mtx[16].lock(); if (!cclasses[16] || env->IsSameObject(cclasses[16], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[23]))) { cclasses[16] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[16].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (cstack2.l != nullptr && !env->IsInstanceOf(cstack2.l, (cclasses[16]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 766LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (jarray) 0; } } 
        // New stack: 3
        // INVOKESPECIAL java/lang/IllegalArgumentException.<init>(Ljava/lang/String;)V; Stack: 3
        if (!cclasses[15] || env->IsSameObject(cclasses[15], NULL)) { cclasses_mtx[15].lock(); if (!cclasses[15] || env->IsSameObject(cclasses[15], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[20]))) { cclasses[15] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[15].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (!cmethods[12]) { cmethods[12] = env->GetMethodID((cclasses[15]), ((char *)(string_pool + 631LL)), ((char *)(string_pool + 6330LL))); if (env->ExceptionCheck()) { return (jarray) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 663LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[15]), (cmethods[12]), cstack2.l); 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 686LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 0
        // LABEL L27; Stack: 0
        L27: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 0
        // FRAME SAME L: null S: null; Stack: 0
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal5.l); refs.erase(clocal10.l); 
        utils::clear_refs(env, refs);
        // New stack: 0
        // SIPUSH 9761; Stack: 0
        cstack0.i = (jint) 9761;
        // New stack: 1
        // LDC 7377324203555979514; Stack: 1
        cstack1.j = 7377324203555979514LL;
        // New stack: 3
        // LLOAD 3; Stack: 3
        cstack3.j = clocal3.j;
        // New stack: 5
        // LXOR; Stack: 5
        cstack1.j = cstack1.j ^ cstack3.j;
        // New stack: 3
        // LABEL L17; Stack: 3
        L17: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 3
        // ICONST_1; Stack: 3
        cstack3.i = 1;
        // New stack: 4
        // ANEWARRAY java/lang/Object; Stack: 4
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } if (cstack3.i < 0) utils::throw_re(env, ((char *)(string_pool + 217LL)), ((char *)(string_pool + 254LL)), -1); else { cstack3.l = env->NewObjectArray(cstack3.i, (cclasses[5]), nullptr); refs.insert(cstack3.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 4
        // DUP; Stack: 4
        cstack4 = cstack3;
        // New stack: 5
        // LDC Ldev/sakura/client/Mahiro_uT;; Stack: 5
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } cstack5.l = (cclasses[6]);
        // New stack: 6
        // SWAP; Stack: 6
        std::swap(cstack5, cstack4);
        // New stack: 6
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.11716030067971395.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 6
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } cstack6.l = lookup;
        // New stack: 7
        // LDC Ldev/sakura/client/Mahiro_uT;; Stack: 7
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } cstack7.l = (cclasses[6]);
        // New stack: 8
        // LDC b; Stack: 8
        cstack8.l = (cstrings[17]);
        // New stack: 9
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;; Stack: 9
        cstack9.l = (cstrings[7]);
        // New stack: 10
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.11716030067971395.a()Ljava/lang/ClassLoader;; Stack: 10
        cstack10.l = classloader;
        // New stack: 11
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 11
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } if (!cmethods[3]) { cmethods[3] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 279LL)), ((char *)(string_pool + 306LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }  } cstack9.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[3]), cstack9.l, cstack10.l); refs.insert(cstack9.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 10
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 10
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 379LL)), ((char *)(string_pool + 390LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }  } if (cstack6.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 488LL)), -1); else { cstack6.l = env->CallObjectMethod(cstack6.l, (cmethods[4]), cstack7.l, cstack8.l, cstack9.l); refs.insert(cstack6.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 7
        // SWAP; Stack: 7
        std::swap(cstack6, cstack5);
        // New stack: 7
        // LDC u; Stack: 7
        cstack7.l = (cstrings[18]);
        // New stack: 8
        // SWAP; Stack: 8
        std::swap(cstack7, cstack6);
        // New stack: 8
        // LDC (IJ)I; Stack: 8
        cstack8.l = (cstrings[19]);
        // New stack: 9
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.11716030067971395.a()Ljava/lang/ClassLoader;; Stack: 9
        cstack9.l = classloader;
        // New stack: 10
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 10
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } if (!cmethods[3]) { cmethods[3] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 279LL)), ((char *)(string_pool + 306LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }  } cstack8.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[3]), cstack8.l, cstack9.l); refs.insert(cstack8.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 9
        // SWAP; Stack: 9
        std::swap(cstack8, cstack7);
        // New stack: 9
        // LDC 0; Stack: 9
        cstack9.i = 0;
        // New stack: 10
        // ANEWARRAY java/lang/Object; Stack: 10
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } if (cstack9.i < 0) utils::throw_re(env, ((char *)(string_pool + 217LL)), ((char *)(string_pool + 254LL)), -1); else { cstack9.l = env->NewObjectArray(cstack9.i, (cclasses[5]), nullptr); refs.insert(cstack9.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 10
        // CHECKCAST java/lang/Object; Stack: 10
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } if (cstack9.l != nullptr && !env->IsInstanceOf(cstack9.l, (cclasses[5]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 513LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } 
        // New stack: 10
        // SWAP; Stack: 10
        std::swap(cstack9, cstack8);
        // New stack: 10
        // INVOKESTATIC native/magic/1/linkcallsite/obfuscator0.11716030067971395.a(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/invoke/MemberName;; Stack: 10
        cstack4.l = utils::link_call_site(env, cstack4.l, cstack5.l, cstack6.l, cstack7.l, cstack8.l, cstack9.l); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 5
        // POP; Stack: 5
        ;
        // New stack: 4
        // ICONST_0; Stack: 4
        cstack4.i = 0;
        // New stack: 5
        // AALOAD; Stack: 5
        if (cstack3.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 105LL)), -1); else { cstack3.l = env->GetObjectArrayElement((jobjectArray) cstack3.l, cstack4.i); refs.insert(cstack3.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 4
        // DUP; Stack: 4
        cstack4 = cstack3;
        // New stack: 5
        // INSTANCEOF java/lang/invoke/CallSite; Stack: 5
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } cstack4.i = cstack4.l == nullptr ? false : env->IsInstanceOf(cstack4.l, (cclasses[10]));
        // New stack: 5
        // IFEQ L32; Stack: 5
        if (cstack4.i == 0) goto L32;
        // New stack: 4
        // INVOKEINTERFACE java/lang/invoke/CallSite.getTarget()Ljava/lang/invoke/MethodHandle;; Stack: 4
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } if (!cmethods[5]) { cmethods[5] = env->GetMethodID((cclasses[10]), ((char *)(string_pool + 530LL)), ((char *)(string_pool + 540LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }  } if (cstack3.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 574LL)), -1); else { cstack3.l = env->CallObjectMethod(cstack3.l, (cmethods[5])); refs.insert(cstack3.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 4
        // LABEL L32; Stack: 4
        L32: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 4
        // FRAME FULL L: [[Ljava/lang/Object;, [B, 1, 4, [B, 4, 4, [Ldev/sakura/client/Mahiro_m;] S: [1, 4, java/lang/Object]; Stack: 4
        refs.erase(cstack3.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal5.l); refs.erase(clocal10.l); 
        utils::clear_refs(env, refs);
        // New stack: 4
        // CHECKCAST java/lang/invoke/MethodHandle; Stack: 4
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } if (cstack3.l != nullptr && !env->IsInstanceOf(cstack3.l, (cclasses[11]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 601LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } 
        // New stack: 4
        // GOTO L33; Stack: 4
        goto L33;
        // New stack: 4
        // LABEL L18; Stack: 4
        L18: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 4
        // FRAME SAME1 L: null S: [java/lang/Throwable]; Stack: 4
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal5.l); refs.erase(clocal10.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // INSTANCEOF java/lang/BootstrapMethodError; Stack: 2
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } cstack1.i = cstack1.l == nullptr ? false : env->IsInstanceOf(cstack1.l, (cclasses[12]));
        // New stack: 2
        // IFNE L34; Stack: 2
        if (cstack1.i != 0) goto L34;
        // New stack: 1
        // NEW java/lang/BootstrapMethodError; Stack: 1
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (jobject obj = env->AllocObject((cclasses[12]))) { cstack1.l = obj; refs.insert(obj); } 
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
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (!cmethods[6]) { cmethods[6] = env->GetMethodID((cclasses[12]), ((char *)(string_pool + 631LL)), ((char *)(string_pool + 638LL))); if (env->ExceptionCheck()) { return (jarray) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 663LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[12]), (cmethods[6]), cstack2.l); 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // LABEL L34; Stack: 1
        L34: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/Object]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal5.l); refs.erase(clocal10.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 686LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 0
        // LABEL L33; Stack: 0
        L33: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 0
        // FRAME FULL L: [[Ljava/lang/Object;, [B, 1, 4, [B, 4, 4, [Ldev/sakura/client/Mahiro_m;] S: [1, 4, java/lang/invoke/MethodHandle]; Stack: 0
        refs.erase(cstack3.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal5.l); refs.erase(clocal10.l); 
        utils::clear_refs(env, refs);
        // New stack: 4
        // INVOKESTATIC native/magic/1/invoke/obfuscator0.11716030067971395.a(IJLjava/lang/invoke/MethodHandle;)I; Stack: 4
        if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { cclasses_mtx[13].lock(); if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[13] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[13].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (!cmethods[10]) { cmethods[10] = env->GetStaticMethodID((cclasses[13]), ((char *)(string_pool + 7610LL)), ((char *)(string_pool + 7626LL))); if (env->ExceptionCheck()) { return (jarray) 0; }  } cstack0.i = env->CallStaticIntMethod((cclasses[13]), (cmethods[10]), cstack0.i, cstack1.j, cstack3.l); 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // LABEL L26; Stack: 1
        L26: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [1]; Stack: 1
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal5.l); refs.erase(clocal10.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ISTORE 11; Stack: 1
        clocal11.i = cstack0.i;
        // New stack: 0
        // ILOAD 2; Stack: 0
        cstack0.i = clocal2.i;
        // New stack: 1
        // I2D; Stack: 1
        cstack0.d = (jdouble) cstack0.i;
        // New stack: 2
        // ILOAD 11; Stack: 2
        cstack2.i = clocal11.i;
        // New stack: 3
        // I2D; Stack: 3
        cstack2.d = (jdouble) cstack2.i;
        // New stack: 4
        // DDIV; Stack: 4
        cstack0.d = cstack0.d / cstack2.d;
        // New stack: 2
        // LDC -3225313376527409007; Stack: 2
        cstack2.j = -3225313376527409007LL;
        // New stack: 4
        // LLOAD 3; Stack: 4
        cstack4.j = clocal3.j;
        // New stack: 6
        // LABEL L15; Stack: 6
        L15: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 6
        // ICONST_1; Stack: 6
        cstack6.i = 1;
        // New stack: 7
        // ANEWARRAY java/lang/Object; Stack: 7
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } if (cstack6.i < 0) utils::throw_re(env, ((char *)(string_pool + 217LL)), ((char *)(string_pool + 254LL)), -1); else { cstack6.l = env->NewObjectArray(cstack6.i, (cclasses[5]), nullptr); refs.insert(cstack6.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 7
        // DUP; Stack: 7
        cstack7 = cstack6;
        // New stack: 8
        // LDC Ldev/sakura/client/Mahiro_uT;; Stack: 8
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } cstack8.l = (cclasses[6]);
        // New stack: 9
        // SWAP; Stack: 9
        std::swap(cstack8, cstack7);
        // New stack: 9
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.11716030067971395.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 9
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } cstack9.l = lookup;
        // New stack: 10
        // LDC Ldev/sakura/client/Mahiro_hd;; Stack: 10
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } cstack10.l = (cclasses[7]);
        // New stack: 11
        // LDC a; Stack: 11
        cstack11.l = (cstrings[6]);
        // New stack: 12
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;; Stack: 12
        cstack12.l = (cstrings[7]);
        // New stack: 13
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.11716030067971395.a()Ljava/lang/ClassLoader;; Stack: 13
        cstack13.l = classloader;
        // New stack: 14
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 14
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } if (!cmethods[3]) { cmethods[3] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 279LL)), ((char *)(string_pool + 306LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }  } cstack12.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[3]), cstack12.l, cstack13.l); refs.insert(cstack12.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 13
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 13
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 379LL)), ((char *)(string_pool + 390LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }  } if (cstack9.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 488LL)), -1); else { cstack9.l = env->CallObjectMethod(cstack9.l, (cmethods[4]), cstack10.l, cstack11.l, cstack12.l); refs.insert(cstack9.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 10
        // SWAP; Stack: 10
        std::swap(cstack9, cstack8);
        // New stack: 10
        // LDC \u00c9; Stack: 10
        cstack10.l = (cstrings[10]);
        // New stack: 11
        // SWAP; Stack: 11
        std::swap(cstack10, cstack9);
        // New stack: 11
        // LDC (DJJ)D; Stack: 11
        cstack11.l = (cstrings[24]);
        // New stack: 12
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.11716030067971395.a()Ljava/lang/ClassLoader;; Stack: 12
        cstack12.l = classloader;
        // New stack: 13
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 13
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } if (!cmethods[3]) { cmethods[3] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 279LL)), ((char *)(string_pool + 306LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }  } cstack11.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[3]), cstack11.l, cstack12.l); refs.insert(cstack11.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 12
        // SWAP; Stack: 12
        std::swap(cstack11, cstack10);
        // New stack: 12
        // LDC 0; Stack: 12
        cstack12.i = 0;
        // New stack: 13
        // ANEWARRAY java/lang/Object; Stack: 13
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } if (cstack12.i < 0) utils::throw_re(env, ((char *)(string_pool + 217LL)), ((char *)(string_pool + 254LL)), -1); else { cstack12.l = env->NewObjectArray(cstack12.i, (cclasses[5]), nullptr); refs.insert(cstack12.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 13
        // CHECKCAST java/lang/Object; Stack: 13
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } if (cstack12.l != nullptr && !env->IsInstanceOf(cstack12.l, (cclasses[5]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 513LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } 
        // New stack: 13
        // SWAP; Stack: 13
        std::swap(cstack12, cstack11);
        // New stack: 13
        // INVOKESTATIC native/magic/1/linkcallsite/obfuscator0.11716030067971395.a(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/invoke/MemberName;; Stack: 13
        cstack7.l = utils::link_call_site(env, cstack7.l, cstack8.l, cstack9.l, cstack10.l, cstack11.l, cstack12.l); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 8
        // POP; Stack: 8
        ;
        // New stack: 7
        // ICONST_0; Stack: 7
        cstack7.i = 0;
        // New stack: 8
        // AALOAD; Stack: 8
        if (cstack6.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 105LL)), -1); else { cstack6.l = env->GetObjectArrayElement((jobjectArray) cstack6.l, cstack7.i); refs.insert(cstack6.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 7
        // DUP; Stack: 7
        cstack7 = cstack6;
        // New stack: 8
        // INSTANCEOF java/lang/invoke/CallSite; Stack: 8
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } cstack7.i = cstack7.l == nullptr ? false : env->IsInstanceOf(cstack7.l, (cclasses[10]));
        // New stack: 8
        // IFEQ L35; Stack: 8
        if (cstack7.i == 0) goto L35;
        // New stack: 7
        // INVOKEINTERFACE java/lang/invoke/CallSite.getTarget()Ljava/lang/invoke/MethodHandle;; Stack: 7
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } if (!cmethods[5]) { cmethods[5] = env->GetMethodID((cclasses[10]), ((char *)(string_pool + 530LL)), ((char *)(string_pool + 540LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }  } if (cstack6.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 574LL)), -1); else { cstack6.l = env->CallObjectMethod(cstack6.l, (cmethods[5])); refs.insert(cstack6.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 7
        // LABEL L35; Stack: 7
        L35: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 7
        // FRAME FULL L: [[Ljava/lang/Object;, [B, 1, 4, [B, 4, 4, [Ldev/sakura/client/Mahiro_m;, 1] S: [3, 4, 4, java/lang/Object]; Stack: 7
        refs.erase(cstack6.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal5.l); refs.erase(clocal10.l); 
        utils::clear_refs(env, refs);
        // New stack: 7
        // CHECKCAST java/lang/invoke/MethodHandle; Stack: 7
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } if (cstack6.l != nullptr && !env->IsInstanceOf(cstack6.l, (cclasses[11]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 601LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } 
        // New stack: 7
        // GOTO L36; Stack: 7
        goto L36;
        // New stack: 7
        // LABEL L16; Stack: 7
        L16: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 7
        // FRAME SAME1 L: null S: [java/lang/Throwable]; Stack: 7
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal5.l); refs.erase(clocal10.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // INSTANCEOF java/lang/BootstrapMethodError; Stack: 2
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } cstack1.i = cstack1.l == nullptr ? false : env->IsInstanceOf(cstack1.l, (cclasses[12]));
        // New stack: 2
        // IFNE L37; Stack: 2
        if (cstack1.i != 0) goto L37;
        // New stack: 1
        // NEW java/lang/BootstrapMethodError; Stack: 1
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (jobject obj = env->AllocObject((cclasses[12]))) { cstack1.l = obj; refs.insert(obj); } 
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
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (!cmethods[6]) { cmethods[6] = env->GetMethodID((cclasses[12]), ((char *)(string_pool + 631LL)), ((char *)(string_pool + 638LL))); if (env->ExceptionCheck()) { return (jarray) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 663LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[12]), (cmethods[6]), cstack2.l); 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // LABEL L37; Stack: 1
        L37: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/Object]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal5.l); refs.erase(clocal10.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 686LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 0
        // LABEL L36; Stack: 0
        L36: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 0
        // FRAME FULL L: [[Ljava/lang/Object;, [B, 1, 4, [B, 4, 4, [Ldev/sakura/client/Mahiro_m;, 1] S: [3, 4, 4, java/lang/invoke/MethodHandle]; Stack: 0
        refs.erase(cstack6.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal5.l); refs.erase(clocal10.l); 
        utils::clear_refs(env, refs);
        // New stack: 7
        // INVOKESTATIC native/magic/1/invoke/obfuscator0.11716030067971395.a(DJJLjava/lang/invoke/MethodHandle;)D; Stack: 7
        if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { cclasses_mtx[13].lock(); if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[13] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[13].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (!cmethods[13]) { cmethods[13] = env->GetStaticMethodID((cclasses[13]), ((char *)(string_pool + 18659LL)), ((char *)(string_pool + 18675LL))); if (env->ExceptionCheck()) { return (jarray) 0; }  } cstack0.d = env->CallStaticDoubleMethod((cclasses[13]), (cmethods[13]), cstack0.d, cstack2.j, cstack4.j, cstack6.l); 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 2
        // D2I; Stack: 2
        cstack0.i = (jint) cstack0.d;
        // New stack: 1
        // ISTORE 12; Stack: 1
        clocal12.i = cstack0.i;
        // New stack: 0
        // ILOAD 12; Stack: 0
        cstack0.i = clocal12.i;
        // New stack: 1
        // ALOAD 10; Stack: 1
        cstack1.l = clocal10.l; refs.insert(cstack1.l);
        // New stack: 2
        // IFNULL L38; Stack: 2
        if (env->IsSameObject(cstack1.l, nullptr)) goto L38;
        // New stack: 1
        // SIPUSH 30051; Stack: 1
        cstack1.i = (jint) 30051;
        // New stack: 2
        // LDC 7465806611361289146; Stack: 2
        cstack2.j = 7465806611361289146LL;
        // New stack: 4
        // LLOAD 3; Stack: 4
        cstack4.j = clocal3.j;
        // New stack: 6
        // LXOR; Stack: 6
        cstack2.j = cstack2.j ^ cstack4.j;
        // New stack: 4
        // LABEL L13; Stack: 4
        L13: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }
        // New stack: 4
        // ICONST_1; Stack: 4
        cstack4.i = 1;
        // New stack: 5
        // ANEWARRAY java/lang/Object; Stack: 5
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } if (cstack4.i < 0) utils::throw_re(env, ((char *)(string_pool + 217LL)), ((char *)(string_pool + 254LL)), -1); else { cstack4.l = env->NewObjectArray(cstack4.i, (cclasses[5]), nullptr); refs.insert(cstack4.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }
        // New stack: 5
        // DUP; Stack: 5
        cstack5 = cstack4;
        // New stack: 6
        // LDC Ldev/sakura/client/Mahiro_uT;; Stack: 6
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } cstack6.l = (cclasses[6]);
        // New stack: 7
        // SWAP; Stack: 7
        std::swap(cstack6, cstack5);
        // New stack: 7
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.11716030067971395.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 7
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } cstack7.l = lookup;
        // New stack: 8
        // LDC Ldev/sakura/client/Mahiro_uT;; Stack: 8
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } cstack8.l = (cclasses[6]);
        // New stack: 9
        // LDC b; Stack: 9
        cstack9.l = (cstrings[17]);
        // New stack: 10
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;; Stack: 10
        cstack10.l = (cstrings[7]);
        // New stack: 11
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.11716030067971395.a()Ljava/lang/ClassLoader;; Stack: 11
        cstack11.l = classloader;
        // New stack: 12
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 12
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } if (!cmethods[3]) { cmethods[3] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 279LL)), ((char *)(string_pool + 306LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }  } cstack10.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[3]), cstack10.l, cstack11.l); refs.insert(cstack10.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }
        // New stack: 11
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 11
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 379LL)), ((char *)(string_pool + 390LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }  } if (cstack7.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 488LL)), -1); else { cstack7.l = env->CallObjectMethod(cstack7.l, (cmethods[4]), cstack8.l, cstack9.l, cstack10.l); refs.insert(cstack7.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }
        // New stack: 8
        // SWAP; Stack: 8
        std::swap(cstack7, cstack6);
        // New stack: 8
        // LDC u; Stack: 8
        cstack8.l = (cstrings[18]);
        // New stack: 9
        // SWAP; Stack: 9
        std::swap(cstack8, cstack7);
        // New stack: 9
        // LDC (IJ)I; Stack: 9
        cstack9.l = (cstrings[19]);
        // New stack: 10
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.11716030067971395.a()Ljava/lang/ClassLoader;; Stack: 10
        cstack10.l = classloader;
        // New stack: 11
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 11
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } if (!cmethods[3]) { cmethods[3] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 279LL)), ((char *)(string_pool + 306LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }  } cstack9.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[3]), cstack9.l, cstack10.l); refs.insert(cstack9.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }
        // New stack: 10
        // SWAP; Stack: 10
        std::swap(cstack9, cstack8);
        // New stack: 10
        // LDC 0; Stack: 10
        cstack10.i = 0;
        // New stack: 11
        // ANEWARRAY java/lang/Object; Stack: 11
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } if (cstack10.i < 0) utils::throw_re(env, ((char *)(string_pool + 217LL)), ((char *)(string_pool + 254LL)), -1); else { cstack10.l = env->NewObjectArray(cstack10.i, (cclasses[5]), nullptr); refs.insert(cstack10.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }
        // New stack: 11
        // CHECKCAST java/lang/Object; Stack: 11
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } if (cstack10.l != nullptr && !env->IsInstanceOf(cstack10.l, (cclasses[5]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 513LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } 
        // New stack: 11
        // SWAP; Stack: 11
        std::swap(cstack10, cstack9);
        // New stack: 11
        // INVOKESTATIC native/magic/1/linkcallsite/obfuscator0.11716030067971395.a(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/invoke/MemberName;; Stack: 11
        cstack5.l = utils::link_call_site(env, cstack5.l, cstack6.l, cstack7.l, cstack8.l, cstack9.l, cstack10.l); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }
        // New stack: 6
        // POP; Stack: 6
        ;
        // New stack: 5
        // ICONST_0; Stack: 5
        cstack5.i = 0;
        // New stack: 6
        // AALOAD; Stack: 6
        if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 105LL)), -1); else { cstack4.l = env->GetObjectArrayElement((jobjectArray) cstack4.l, cstack5.i); refs.insert(cstack4.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }
        // New stack: 5
        // DUP; Stack: 5
        cstack5 = cstack4;
        // New stack: 6
        // INSTANCEOF java/lang/invoke/CallSite; Stack: 6
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } cstack5.i = cstack5.l == nullptr ? false : env->IsInstanceOf(cstack5.l, (cclasses[10]));
        // New stack: 6
        // IFEQ L39; Stack: 6
        if (cstack5.i == 0) goto L39;
        // New stack: 5
        // INVOKEINTERFACE java/lang/invoke/CallSite.getTarget()Ljava/lang/invoke/MethodHandle;; Stack: 5
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } if (!cmethods[5]) { cmethods[5] = env->GetMethodID((cclasses[10]), ((char *)(string_pool + 530LL)), ((char *)(string_pool + 540LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }  } if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 574LL)), -1); else { cstack4.l = env->CallObjectMethod(cstack4.l, (cmethods[5])); refs.insert(cstack4.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }
        // New stack: 5
        // LABEL L39; Stack: 5
        L39: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }
        // New stack: 5
        // FRAME FULL L: [[Ljava/lang/Object;, [B, 1, 4, [B, 4, 4, [Ldev/sakura/client/Mahiro_m;, 1, 1] S: [1, 1, 4, java/lang/Object]; Stack: 5
        refs.erase(cstack4.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal5.l); refs.erase(clocal10.l); 
        utils::clear_refs(env, refs);
        // New stack: 5
        // CHECKCAST java/lang/invoke/MethodHandle; Stack: 5
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } if (cstack4.l != nullptr && !env->IsInstanceOf(cstack4.l, (cclasses[11]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 601LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } 
        // New stack: 5
        // GOTO L40; Stack: 5
        goto L40;
        // New stack: 5
        // LABEL L14; Stack: 5
        L14: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 5
        // FRAME SAME1 L: null S: [java/lang/Throwable]; Stack: 5
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal5.l); refs.erase(clocal10.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // INSTANCEOF java/lang/BootstrapMethodError; Stack: 2
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } cstack1.i = cstack1.l == nullptr ? false : env->IsInstanceOf(cstack1.l, (cclasses[12]));
        // New stack: 2
        // IFNE L41; Stack: 2
        if (cstack1.i != 0) goto L41;
        // New stack: 1
        // NEW java/lang/BootstrapMethodError; Stack: 1
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (jobject obj = env->AllocObject((cclasses[12]))) { cstack1.l = obj; refs.insert(obj); } 
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
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (!cmethods[6]) { cmethods[6] = env->GetMethodID((cclasses[12]), ((char *)(string_pool + 631LL)), ((char *)(string_pool + 638LL))); if (env->ExceptionCheck()) { return (jarray) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 663LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[12]), (cmethods[6]), cstack2.l); 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // LABEL L41; Stack: 1
        L41: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/Object]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal5.l); refs.erase(clocal10.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 686LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 0
        // LABEL L40; Stack: 0
        L40: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 0
        // FRAME FULL L: [[Ljava/lang/Object;, [B, 1, 4, [B, 4, 4, [Ldev/sakura/client/Mahiro_m;, 1, 1] S: [1, 1, 4, java/lang/invoke/MethodHandle]; Stack: 0
        refs.erase(cstack4.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal5.l); refs.erase(clocal10.l); 
        utils::clear_refs(env, refs);
        // New stack: 5
        // INVOKESTATIC native/magic/1/invoke/obfuscator0.11716030067971395.a(IJLjava/lang/invoke/MethodHandle;)I; Stack: 5
        if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { cclasses_mtx[13].lock(); if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[13] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[13].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (!cmethods[10]) { cmethods[10] = env->GetStaticMethodID((cclasses[13]), ((char *)(string_pool + 7610LL)), ((char *)(string_pool + 7626LL))); if (env->ExceptionCheck()) { return (jarray) 0; }  } cstack1.i = env->CallStaticIntMethod((cclasses[13]), (cmethods[10]), cstack1.i, cstack2.j, cstack4.l); 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 2
        // IF_ICMPLE L42; Stack: 2
        if (cstack0.i <= cstack1.i) goto L42;
        // New stack: 0
        // LABEL L43; Stack: 0
        L43: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 0
        // NEW java/lang/IllegalArgumentException; Stack: 0
        if (!cclasses[15] || env->IsSameObject(cclasses[15], NULL)) { cclasses_mtx[15].lock(); if (!cclasses[15] || env->IsSameObject(cclasses[15], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[20]))) { cclasses[15] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[15].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (jobject obj = env->AllocObject((cclasses[15]))) { cstack0.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // SIPUSH 7909; Stack: 2
        cstack2.i = (jint) 7909;
        // New stack: 3
        // LDC 463863532904962660; Stack: 3
        cstack3.j = 463863532904962660LL;
        // New stack: 5
        // LLOAD 3; Stack: 5
        cstack5.j = clocal3.j;
        // New stack: 7
        // LXOR; Stack: 7
        cstack3.j = cstack3.j ^ cstack5.j;
        // New stack: 5
        // LABEL L11; Stack: 5
        L11: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }
        // New stack: 5
        // ICONST_1; Stack: 5
        cstack5.i = 1;
        // New stack: 6
        // ANEWARRAY java/lang/Object; Stack: 6
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } if (cstack5.i < 0) utils::throw_re(env, ((char *)(string_pool + 217LL)), ((char *)(string_pool + 254LL)), -1); else { cstack5.l = env->NewObjectArray(cstack5.i, (cclasses[5]), nullptr); refs.insert(cstack5.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }
        // New stack: 6
        // DUP; Stack: 6
        cstack6 = cstack5;
        // New stack: 7
        // LDC Ldev/sakura/client/Mahiro_uT;; Stack: 7
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } cstack7.l = (cclasses[6]);
        // New stack: 8
        // SWAP; Stack: 8
        std::swap(cstack7, cstack6);
        // New stack: 8
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.11716030067971395.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 8
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } cstack8.l = lookup;
        // New stack: 9
        // LDC Ldev/sakura/client/Mahiro_uT;; Stack: 9
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } cstack9.l = (cclasses[6]);
        // New stack: 10
        // LDC a; Stack: 10
        cstack10.l = (cstrings[6]);
        // New stack: 11
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;; Stack: 11
        cstack11.l = (cstrings[7]);
        // New stack: 12
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.11716030067971395.a()Ljava/lang/ClassLoader;; Stack: 12
        cstack12.l = classloader;
        // New stack: 13
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 13
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } if (!cmethods[3]) { cmethods[3] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 279LL)), ((char *)(string_pool + 306LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }  } cstack11.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[3]), cstack11.l, cstack12.l); refs.insert(cstack11.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }
        // New stack: 12
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 12
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 379LL)), ((char *)(string_pool + 390LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }  } if (cstack8.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 488LL)), -1); else { cstack8.l = env->CallObjectMethod(cstack8.l, (cmethods[4]), cstack9.l, cstack10.l, cstack11.l); refs.insert(cstack8.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }
        // New stack: 9
        // SWAP; Stack: 9
        std::swap(cstack8, cstack7);
        // New stack: 9
        // LDC i; Stack: 9
        cstack9.l = (cstrings[21]);
        // New stack: 10
        // SWAP; Stack: 10
        std::swap(cstack9, cstack8);
        // New stack: 10
        // LDC (IJ)Ljava/lang/String;; Stack: 10
        cstack10.l = (cstrings[22]);
        // New stack: 11
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.11716030067971395.a()Ljava/lang/ClassLoader;; Stack: 11
        cstack11.l = classloader;
        // New stack: 12
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 12
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } if (!cmethods[3]) { cmethods[3] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 279LL)), ((char *)(string_pool + 306LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }  } cstack10.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[3]), cstack10.l, cstack11.l); refs.insert(cstack10.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }
        // New stack: 11
        // SWAP; Stack: 11
        std::swap(cstack10, cstack9);
        // New stack: 11
        // LDC 0; Stack: 11
        cstack11.i = 0;
        // New stack: 12
        // ANEWARRAY java/lang/Object; Stack: 12
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } if (cstack11.i < 0) utils::throw_re(env, ((char *)(string_pool + 217LL)), ((char *)(string_pool + 254LL)), -1); else { cstack11.l = env->NewObjectArray(cstack11.i, (cclasses[5]), nullptr); refs.insert(cstack11.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }
        // New stack: 12
        // CHECKCAST java/lang/Object; Stack: 12
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } if (cstack11.l != nullptr && !env->IsInstanceOf(cstack11.l, (cclasses[5]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 513LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } 
        // New stack: 12
        // SWAP; Stack: 12
        std::swap(cstack11, cstack10);
        // New stack: 12
        // INVOKESTATIC native/magic/1/linkcallsite/obfuscator0.11716030067971395.a(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/invoke/MemberName;; Stack: 12
        cstack6.l = utils::link_call_site(env, cstack6.l, cstack7.l, cstack8.l, cstack9.l, cstack10.l, cstack11.l); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }
        // New stack: 7
        // POP; Stack: 7
        ;
        // New stack: 6
        // ICONST_0; Stack: 6
        cstack6.i = 0;
        // New stack: 7
        // AALOAD; Stack: 7
        if (cstack5.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 105LL)), -1); else { cstack5.l = env->GetObjectArrayElement((jobjectArray) cstack5.l, cstack6.i); refs.insert(cstack5.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }
        // New stack: 6
        // DUP; Stack: 6
        cstack6 = cstack5;
        // New stack: 7
        // INSTANCEOF java/lang/invoke/CallSite; Stack: 7
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } cstack6.i = cstack6.l == nullptr ? false : env->IsInstanceOf(cstack6.l, (cclasses[10]));
        // New stack: 7
        // IFEQ L44; Stack: 7
        if (cstack6.i == 0) goto L44;
        // New stack: 6
        // INVOKEINTERFACE java/lang/invoke/CallSite.getTarget()Ljava/lang/invoke/MethodHandle;; Stack: 6
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } if (!cmethods[5]) { cmethods[5] = env->GetMethodID((cclasses[10]), ((char *)(string_pool + 530LL)), ((char *)(string_pool + 540LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }  } if (cstack5.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 574LL)), -1); else { cstack5.l = env->CallObjectMethod(cstack5.l, (cmethods[5])); refs.insert(cstack5.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }
        // New stack: 6
        // LABEL L44; Stack: 6
        L44: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }
        // New stack: 6
        // FRAME FULL L: [[Ljava/lang/Object;, [B, 1, 4, [B, 4, 4, [Ldev/sakura/client/Mahiro_m;, 1, 1] S: [org.objectweb.asm.tree.LabelNode@3162743f, org.objectweb.asm.tree.LabelNode@3162743f, 1, 4, java/lang/Object]; Stack: 6
        refs.erase(cstack0.l); refs.erase(cstack1.l); refs.erase(cstack5.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal5.l); refs.erase(clocal10.l); 
        utils::clear_refs(env, refs);
        // New stack: 6
        // CHECKCAST java/lang/invoke/MethodHandle; Stack: 6
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } if (cstack5.l != nullptr && !env->IsInstanceOf(cstack5.l, (cclasses[11]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 601LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } 
        // New stack: 6
        // GOTO L45; Stack: 6
        goto L45;
        // New stack: 6
        // LABEL L12; Stack: 6
        L12: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 6
        // FRAME SAME1 L: null S: [java/lang/Throwable]; Stack: 6
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal5.l); refs.erase(clocal10.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // INSTANCEOF java/lang/BootstrapMethodError; Stack: 2
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } cstack1.i = cstack1.l == nullptr ? false : env->IsInstanceOf(cstack1.l, (cclasses[12]));
        // New stack: 2
        // IFNE L46; Stack: 2
        if (cstack1.i != 0) goto L46;
        // New stack: 1
        // NEW java/lang/BootstrapMethodError; Stack: 1
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (jobject obj = env->AllocObject((cclasses[12]))) { cstack1.l = obj; refs.insert(obj); } 
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
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (!cmethods[6]) { cmethods[6] = env->GetMethodID((cclasses[12]), ((char *)(string_pool + 631LL)), ((char *)(string_pool + 638LL))); if (env->ExceptionCheck()) { return (jarray) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 663LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[12]), (cmethods[6]), cstack2.l); 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // LABEL L46; Stack: 1
        L46: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/Object]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal5.l); refs.erase(clocal10.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 686LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 0
        // LABEL L45; Stack: 0
        L45: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 0
        // FRAME FULL L: [[Ljava/lang/Object;, [B, 1, 4, [B, 4, 4, [Ldev/sakura/client/Mahiro_m;, 1, 1] S: [org.objectweb.asm.tree.LabelNode@3162743f, org.objectweb.asm.tree.LabelNode@3162743f, 1, 4, java/lang/invoke/MethodHandle]; Stack: 0
        refs.erase(cstack0.l); refs.erase(cstack1.l); refs.erase(cstack5.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal5.l); refs.erase(clocal10.l); 
        utils::clear_refs(env, refs);
        // New stack: 6
        // INVOKESTATIC native/magic/1/invoke/obfuscator0.11716030067971395.a(IJLjava/lang/invoke/MethodHandle;)Ljava/lang/String;; Stack: 6
        if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { cclasses_mtx[13].lock(); if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[13] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[13].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (!cmethods[11]) { cmethods[11] = env->GetStaticMethodID((cclasses[13]), ((char *)(string_pool + 697LL)), ((char *)(string_pool + 712LL))); if (env->ExceptionCheck()) { return (jarray) 0; }  } cstack2.l = env->CallStaticObjectMethod((cclasses[13]), (cmethods[11]), cstack2.i, cstack3.j, cstack5.l); refs.insert(cstack2.l); 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 3
        // CHECKCAST java/lang/String; Stack: 3
        if (!cclasses[16] || env->IsSameObject(cclasses[16], NULL)) { cclasses_mtx[16].lock(); if (!cclasses[16] || env->IsSameObject(cclasses[16], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[23]))) { cclasses[16] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[16].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (cstack2.l != nullptr && !env->IsInstanceOf(cstack2.l, (cclasses[16]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 766LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (jarray) 0; } } 
        // New stack: 3
        // INVOKESPECIAL java/lang/IllegalArgumentException.<init>(Ljava/lang/String;)V; Stack: 3
        if (!cclasses[15] || env->IsSameObject(cclasses[15], NULL)) { cclasses_mtx[15].lock(); if (!cclasses[15] || env->IsSameObject(cclasses[15], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[20]))) { cclasses[15] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[15].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (!cmethods[12]) { cmethods[12] = env->GetMethodID((cclasses[15]), ((char *)(string_pool + 631LL)), ((char *)(string_pool + 6330LL))); if (env->ExceptionCheck()) { return (jarray) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 663LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[15]), (cmethods[12]), cstack2.l); 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 686LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 0
        // LABEL L42; Stack: 0
        L42: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 0
        // FRAME SAME L: null S: null; Stack: 0
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal5.l); refs.erase(clocal10.l); 
        utils::clear_refs(env, refs);
        // New stack: 0
        // ILOAD 2; Stack: 0
        cstack0.i = clocal2.i;
        // New stack: 1
        // LABEL L38; Stack: 1
        L38: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [1]; Stack: 1
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal5.l); refs.erase(clocal10.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // NEWARRAY 8; Stack: 1
        if (cstack0.i < 0) utils::throw_re(env, ((char *)(string_pool + 217LL)), ((char *)(string_pool + 1834LL)), -1); else { cstack0.l = env->NewByteArray(cstack0.i); refs.insert(cstack0.l); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // ASTORE 13; Stack: 1
        clocal13.l = cstack0.l; refs.insert(cstack0.l);
        // New stack: 0
        // ICONST_0; Stack: 0
        cstack0.i = 0;
        // New stack: 1
        // NEWARRAY 8; Stack: 1
        if (cstack0.i < 0) utils::throw_re(env, ((char *)(string_pool + 217LL)), ((char *)(string_pool + 1834LL)), -1); else { cstack0.l = env->NewByteArray(cstack0.i); refs.insert(cstack0.l); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // ASTORE 14; Stack: 1
        clocal14.l = cstack0.l; refs.insert(cstack0.l);
        // New stack: 0
        // ICONST_0; Stack: 0
        cstack0.i = 0;
        // New stack: 1
        // ISTORE 15; Stack: 1
        clocal15.i = cstack0.i;
        // New stack: 0
        // ICONST_1; Stack: 0
        cstack0.i = 1;
        // New stack: 1
        // ISTORE 16; Stack: 1
        clocal16.i = cstack0.i;
        // New stack: 0
        // LABEL L47; Stack: 0
        L47: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 0
        // FRAME FULL L: [[Ljava/lang/Object;, [B, 1, 4, [B, 4, 4, [Ldev/sakura/client/Mahiro_m;, 1, 1, [B, [B, 1, 1] S: []; Stack: 0
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal5.l); refs.erase(clocal10.l); refs.erase(clocal13.l); refs.erase(clocal14.l); 
        utils::clear_refs(env, refs);
        // New stack: 0
        // ILOAD 16; Stack: 0
        cstack0.i = clocal16.i;
        // New stack: 1
        // ILOAD 12; Stack: 1
        cstack1.i = clocal12.i;
        // New stack: 2
        // IF_ICMPGT L48; Stack: 2
        if (cstack0.i > cstack1.i) goto L48;
        // New stack: 0
        // ALOAD 10; Stack: 0
        cstack0.l = clocal10.l; refs.insert(cstack0.l);
        // New stack: 1
        // IFNULL L49; Stack: 1
        if (env->IsSameObject(cstack0.l, nullptr)) goto L49;
        // New stack: 0
        // ICONST_3; Stack: 0
        cstack0.i = 3;
        // New stack: 1
        // ANEWARRAY [B; Stack: 1
        if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { cclasses_mtx[1].lock(); if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { if (jclass clazz = env->FindClass(((char *)(string_pool + 979LL)))) { cclasses[1] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[1].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (cstack0.i < 0) utils::throw_re(env, ((char *)(string_pool + 217LL)), ((char *)(string_pool + 254LL)), -1); else { cstack0.l = env->NewObjectArray(cstack0.i, (cclasses[1]), nullptr); refs.insert(cstack0.l); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // ICONST_0; Stack: 2
        cstack2.i = 0;
        // New stack: 3
        // ALOAD 14; Stack: 3
        cstack3.l = clocal14.l; refs.insert(cstack3.l);
        // New stack: 4
        // AASTORE; Stack: 4
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2106LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack1.l, cstack2.i, cstack3.l); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // ICONST_1; Stack: 2
        cstack2.i = 1;
        // New stack: 3
        // ALOAD 5; Stack: 3
        cstack3.l = clocal5.l; refs.insert(cstack3.l);
        // New stack: 4
        // ALOAD 10; Stack: 4
        cstack4.l = clocal10.l; refs.insert(cstack4.l);
        // New stack: 5
        // IFNULL L50; Stack: 5
        if (env->IsSameObject(cstack4.l, nullptr)) goto L50;
        // New stack: 4
        // IFNONNULL L51; Stack: 4
        if (!env->IsSameObject(cstack3.l, nullptr)) goto L51;
        // New stack: 3
        // ICONST_0; Stack: 3
        cstack3.i = 0;
        // New stack: 4
        // NEWARRAY 8; Stack: 4
        if (cstack3.i < 0) utils::throw_re(env, ((char *)(string_pool + 217LL)), ((char *)(string_pool + 1834LL)), -1); else { cstack3.l = env->NewByteArray(cstack3.i); refs.insert(cstack3.l); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 4
        // LABEL L50; Stack: 4
        L50: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 4
        // FRAME FULL L: [[Ljava/lang/Object;, [B, 1, 4, [B, 4, 4, [Ldev/sakura/client/Mahiro_m;, 1, 1, [B, [B, 1, 1] S: [[[B, [[B, 1, [B]; Stack: 4
        refs.erase(cstack0.l); refs.erase(cstack1.l); refs.erase(cstack3.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal5.l); refs.erase(clocal10.l); refs.erase(clocal13.l); refs.erase(clocal14.l); 
        utils::clear_refs(env, refs);
        // New stack: 4
        // GOTO L52; Stack: 4
        goto L52;
        // New stack: 4
        // LABEL L51; Stack: 4
        L51: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 4
        // FRAME FULL L: [[Ljava/lang/Object;, [B, 1, 4, [B, 4, 4, [Ldev/sakura/client/Mahiro_m;, 1, 1, [B, [B, 1, 1] S: [[[B, [[B, 1]; Stack: 4
        refs.erase(cstack0.l); refs.erase(cstack1.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal5.l); refs.erase(clocal10.l); refs.erase(clocal13.l); refs.erase(clocal14.l); 
        utils::clear_refs(env, refs);
        // New stack: 3
        // ALOAD 5; Stack: 3
        cstack3.l = clocal5.l; refs.insert(cstack3.l);
        // New stack: 4
        // LABEL L52; Stack: 4
        L52: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 4
        // FRAME FULL L: [[Ljava/lang/Object;, [B, 1, 4, [B, 4, 4, [Ldev/sakura/client/Mahiro_m;, 1, 1, [B, [B, 1, 1] S: [[[B, [[B, 1, [B]; Stack: 4
        refs.erase(cstack0.l); refs.erase(cstack1.l); refs.erase(cstack3.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal5.l); refs.erase(clocal10.l); refs.erase(clocal13.l); refs.erase(clocal14.l); 
        utils::clear_refs(env, refs);
        // New stack: 4
        // AASTORE; Stack: 4
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2106LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack1.l, cstack2.i, cstack3.l); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // ICONST_2; Stack: 2
        cstack2.i = 2;
        // New stack: 3
        // ICONST_1; Stack: 3
        cstack3.i = 1;
        // New stack: 4
        // NEWARRAY 8; Stack: 4
        if (cstack3.i < 0) utils::throw_re(env, ((char *)(string_pool + 217LL)), ((char *)(string_pool + 1834LL)), -1); else { cstack3.l = env->NewByteArray(cstack3.i); refs.insert(cstack3.l); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 4
        // DUP; Stack: 4
        cstack4 = cstack3;
        // New stack: 5
        // ICONST_0; Stack: 5
        cstack5.i = 0;
        // New stack: 6
        // ILOAD 16; Stack: 6
        cstack6.i = clocal16.i;
        // New stack: 7
        // I2B; Stack: 7
        cstack6.i = (jint) (jbyte) cstack6.i;
        // New stack: 7
        // BASTORE; Stack: 7
        if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 1863LL)), -1); else { utils::bastore(env, (jarray) cstack4.l, cstack5.i, cstack6.i); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 4
        // AASTORE; Stack: 4
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2106LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack1.l, cstack2.i, cstack3.l); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // LLOAD 6; Stack: 1
        cstack1.j = clocal6.j;
        // New stack: 3
        // DUP2_X1; Stack: 3
        cstack3 = cstack1; cstack4 = cstack2; cstack2 = cstack0; cstack1 = cstack4; cstack0 = cstack3;
        // New stack: 5
        // POP2; Stack: 5
        ;
        // New stack: 3
        // ICONST_2; Stack: 3
        cstack3.i = 2;
        // New stack: 4
        // ANEWARRAY java/lang/Object; Stack: 4
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (cstack3.i < 0) utils::throw_re(env, ((char *)(string_pool + 217LL)), ((char *)(string_pool + 254LL)), -1); else { cstack3.l = env->NewObjectArray(cstack3.i, (cclasses[5]), nullptr); refs.insert(cstack3.l); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 4
        // DUP_X1; Stack: 4
        cstack4 = cstack3; cstack3 = cstack2; cstack2 = cstack4;
        // New stack: 5
        // SWAP; Stack: 5
        std::swap(cstack4, cstack3);
        // New stack: 5
        // ICONST_1; Stack: 5
        cstack5.i = 1;
        // New stack: 6
        // SWAP; Stack: 6
        std::swap(cstack5, cstack4);
        // New stack: 6
        // AASTORE; Stack: 6
        if (cstack3.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2106LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack3.l, cstack4.i, cstack5.l); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
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
        if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { cclasses_mtx[2].lock(); if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[1]))) { cclasses[2] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[2].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (!cmethods[2]) { cmethods[2] = env->GetStaticMethodID((cclasses[2]), ((char *)(string_pool + 2402LL)), ((char *)(string_pool + 2410LL))); if (env->ExceptionCheck()) { return (jarray) 0; }  } cstack2.l = env->CallStaticObjectMethod((cclasses[2]), (cmethods[2]), cstack2.j); refs.insert(cstack2.l); 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 3
        // ICONST_0; Stack: 3
        cstack3.i = 0;
        // New stack: 4
        // SWAP; Stack: 4
        std::swap(cstack3, cstack2);
        // New stack: 4
        // AASTORE; Stack: 4
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2106LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack1.l, cstack2.i, cstack3.l); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // LDC -3182745486839202739; Stack: 1
        cstack1.j = -3182745486839202739LL;
        // New stack: 3
        // LLOAD 3; Stack: 3
        cstack3.j = clocal3.j;
        // New stack: 5
        // LABEL L9; Stack: 5
        L9: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; }
        // New stack: 5
        // ICONST_1; Stack: 5
        cstack5.i = 1;
        // New stack: 6
        // ANEWARRAY java/lang/Object; Stack: 6
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; } } if (cstack5.i < 0) utils::throw_re(env, ((char *)(string_pool + 217LL)), ((char *)(string_pool + 254LL)), -1); else { cstack5.l = env->NewObjectArray(cstack5.i, (cclasses[5]), nullptr); refs.insert(cstack5.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; }
        // New stack: 6
        // DUP; Stack: 6
        cstack6 = cstack5;
        // New stack: 7
        // LDC Ldev/sakura/client/Mahiro_uT;; Stack: 7
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; } } cstack7.l = (cclasses[6]);
        // New stack: 8
        // SWAP; Stack: 8
        std::swap(cstack7, cstack6);
        // New stack: 8
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.11716030067971395.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 8
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; } } cstack8.l = lookup;
        // New stack: 9
        // LDC Ldev/sakura/client/Mahiro_hd;; Stack: 9
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; } } cstack9.l = (cclasses[7]);
        // New stack: 10
        // LDC a; Stack: 10
        cstack10.l = (cstrings[6]);
        // New stack: 11
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;; Stack: 11
        cstack11.l = (cstrings[7]);
        // New stack: 12
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.11716030067971395.a()Ljava/lang/ClassLoader;; Stack: 12
        cstack12.l = classloader;
        // New stack: 13
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 13
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; } } if (!cmethods[3]) { cmethods[3] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 279LL)), ((char *)(string_pool + 306LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; }  } cstack11.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[3]), cstack11.l, cstack12.l); refs.insert(cstack11.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; }
        // New stack: 12
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 12
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 379LL)), ((char *)(string_pool + 390LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; }  } if (cstack8.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 488LL)), -1); else { cstack8.l = env->CallObjectMethod(cstack8.l, (cmethods[4]), cstack9.l, cstack10.l, cstack11.l); refs.insert(cstack8.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; }
        // New stack: 9
        // SWAP; Stack: 9
        std::swap(cstack8, cstack7);
        // New stack: 9
        // LDC \u00c9; Stack: 9
        cstack9.l = (cstrings[10]);
        // New stack: 10
        // SWAP; Stack: 10
        std::swap(cstack9, cstack8);
        // New stack: 10
        // LDC (Ljava/lang/Object;JJ)[B; Stack: 10
        cstack10.l = (cstrings[11]);
        // New stack: 11
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.11716030067971395.a()Ljava/lang/ClassLoader;; Stack: 11
        cstack11.l = classloader;
        // New stack: 12
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 12
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; } } if (!cmethods[3]) { cmethods[3] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 279LL)), ((char *)(string_pool + 306LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; }  } cstack10.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[3]), cstack10.l, cstack11.l); refs.insert(cstack10.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; }
        // New stack: 11
        // SWAP; Stack: 11
        std::swap(cstack10, cstack9);
        // New stack: 11
        // LDC 0; Stack: 11
        cstack11.i = 0;
        // New stack: 12
        // ANEWARRAY java/lang/Object; Stack: 12
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; } } if (cstack11.i < 0) utils::throw_re(env, ((char *)(string_pool + 217LL)), ((char *)(string_pool + 254LL)), -1); else { cstack11.l = env->NewObjectArray(cstack11.i, (cclasses[5]), nullptr); refs.insert(cstack11.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; }
        // New stack: 12
        // CHECKCAST java/lang/Object; Stack: 12
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; } } if (cstack11.l != nullptr && !env->IsInstanceOf(cstack11.l, (cclasses[5]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 513LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; } } 
        // New stack: 12
        // SWAP; Stack: 12
        std::swap(cstack11, cstack10);
        // New stack: 12
        // INVOKESTATIC native/magic/1/linkcallsite/obfuscator0.11716030067971395.a(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/invoke/MemberName;; Stack: 12
        cstack6.l = utils::link_call_site(env, cstack6.l, cstack7.l, cstack8.l, cstack9.l, cstack10.l, cstack11.l); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; }
        // New stack: 7
        // POP; Stack: 7
        ;
        // New stack: 6
        // ICONST_0; Stack: 6
        cstack6.i = 0;
        // New stack: 7
        // AALOAD; Stack: 7
        if (cstack5.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 105LL)), -1); else { cstack5.l = env->GetObjectArrayElement((jobjectArray) cstack5.l, cstack6.i); refs.insert(cstack5.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; }
        // New stack: 6
        // DUP; Stack: 6
        cstack6 = cstack5;
        // New stack: 7
        // INSTANCEOF java/lang/invoke/CallSite; Stack: 7
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; } } cstack6.i = cstack6.l == nullptr ? false : env->IsInstanceOf(cstack6.l, (cclasses[10]));
        // New stack: 7
        // IFEQ L53; Stack: 7
        if (cstack6.i == 0) goto L53;
        // New stack: 6
        // INVOKEINTERFACE java/lang/invoke/CallSite.getTarget()Ljava/lang/invoke/MethodHandle;; Stack: 6
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; } } if (!cmethods[5]) { cmethods[5] = env->GetMethodID((cclasses[10]), ((char *)(string_pool + 530LL)), ((char *)(string_pool + 540LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; }  } if (cstack5.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 574LL)), -1); else { cstack5.l = env->CallObjectMethod(cstack5.l, (cmethods[5])); refs.insert(cstack5.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; }
        // New stack: 6
        // LABEL L53; Stack: 6
        L53: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; }
        // New stack: 6
        // FRAME FULL L: [[Ljava/lang/Object;, [B, 1, 4, [B, 4, 4, [Ldev/sakura/client/Mahiro_m;, 1, 1, [B, [B, 1, 1] S: [[Ljava/lang/Object;, 4, 4, java/lang/Object]; Stack: 6
        refs.erase(cstack0.l); refs.erase(cstack5.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal5.l); refs.erase(clocal10.l); refs.erase(clocal13.l); refs.erase(clocal14.l); 
        utils::clear_refs(env, refs);
        // New stack: 6
        // CHECKCAST java/lang/invoke/MethodHandle; Stack: 6
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; } } if (cstack5.l != nullptr && !env->IsInstanceOf(cstack5.l, (cclasses[11]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 601LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; } } 
        // New stack: 6
        // GOTO L54; Stack: 6
        goto L54;
        // New stack: 6
        // LABEL L10; Stack: 6
        L10: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 6
        // FRAME SAME1 L: null S: [java/lang/Throwable]; Stack: 6
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal5.l); refs.erase(clocal10.l); refs.erase(clocal13.l); refs.erase(clocal14.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // INSTANCEOF java/lang/BootstrapMethodError; Stack: 2
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } cstack1.i = cstack1.l == nullptr ? false : env->IsInstanceOf(cstack1.l, (cclasses[12]));
        // New stack: 2
        // IFNE L55; Stack: 2
        if (cstack1.i != 0) goto L55;
        // New stack: 1
        // NEW java/lang/BootstrapMethodError; Stack: 1
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (jobject obj = env->AllocObject((cclasses[12]))) { cstack1.l = obj; refs.insert(obj); } 
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
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (!cmethods[6]) { cmethods[6] = env->GetMethodID((cclasses[12]), ((char *)(string_pool + 631LL)), ((char *)(string_pool + 638LL))); if (env->ExceptionCheck()) { return (jarray) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 663LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[12]), (cmethods[6]), cstack2.l); 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // LABEL L55; Stack: 1
        L55: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/Object]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal5.l); refs.erase(clocal10.l); refs.erase(clocal13.l); refs.erase(clocal14.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 686LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 0
        // LABEL L54; Stack: 0
        L54: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 0
        // FRAME FULL L: [[Ljava/lang/Object;, [B, 1, 4, [B, 4, 4, [Ldev/sakura/client/Mahiro_m;, 1, 1, [B, [B, 1, 1] S: [[Ljava/lang/Object;, 4, 4, java/lang/invoke/MethodHandle]; Stack: 0
        refs.erase(cstack0.l); refs.erase(cstack5.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal5.l); refs.erase(clocal10.l); refs.erase(clocal13.l); refs.erase(clocal14.l); 
        utils::clear_refs(env, refs);
        // New stack: 6
        // INVOKESTATIC native/magic/1/invoke/obfuscator0.11716030067971395.a(Ljava/lang/Object;JJLjava/lang/invoke/MethodHandle;)[B; Stack: 6
        if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { cclasses_mtx[13].lock(); if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[13] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[13].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (!cmethods[7]) { cmethods[7] = env->GetStaticMethodID((cclasses[13]), ((char *)(string_pool + 783LL)), ((char *)(string_pool + 798LL))); if (env->ExceptionCheck()) { return (jarray) 0; }  } cstack0.l = env->CallStaticObjectMethod((cclasses[13]), (cmethods[7]), cstack0.l, cstack1.j, cstack3.j, cstack5.l); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // CHECKCAST [B; Stack: 1
        if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { cclasses_mtx[1].lock(); if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { if (jclass clazz = env->FindClass(((char *)(string_pool + 979LL)))) { cclasses[1] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[1].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (cstack0.l != nullptr && !env->IsInstanceOf(cstack0.l, (cclasses[1]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 979LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (jarray) 0; } } 
        // New stack: 1
        // ASTORE 17; Stack: 1
        clocal17.l = cstack0.l; refs.insert(cstack0.l);
        // New stack: 0
        // ALOAD 1; Stack: 0
        cstack0.l = clocal1.l; refs.insert(cstack0.l);
        // New stack: 1
        // ALOAD 17; Stack: 1
        cstack1.l = clocal17.l; refs.insert(cstack1.l);
        // New stack: 2
        // LLOAD 8; Stack: 2
        cstack2.j = clocal8.j;
        // New stack: 4
        // ICONST_3; Stack: 4
        cstack4.i = 3;
        // New stack: 5
        // ANEWARRAY java/lang/Object; Stack: 5
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (cstack4.i < 0) utils::throw_re(env, ((char *)(string_pool + 217LL)), ((char *)(string_pool + 254LL)), -1); else { cstack4.l = env->NewObjectArray(cstack4.i, (cclasses[5]), nullptr); refs.insert(cstack4.l); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 5
        // DUP_X2; Stack: 5
        cstack5 = cstack4; cstack4 = cstack3; cstack3 = cstack2; cstack2 = cstack5;
        // New stack: 6
        // DUP_X2; Stack: 6
        cstack6 = cstack5; cstack5 = cstack4; cstack4 = cstack3; cstack3 = cstack6;
        // New stack: 7
        // POP; Stack: 7
        ;
        // New stack: 6
        // INVOKESTATIC java/lang/Long.valueOf(J)Ljava/lang/Long;; Stack: 6
        if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { cclasses_mtx[2].lock(); if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[1]))) { cclasses[2] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[2].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (!cmethods[2]) { cmethods[2] = env->GetStaticMethodID((cclasses[2]), ((char *)(string_pool + 2402LL)), ((char *)(string_pool + 2410LL))); if (env->ExceptionCheck()) { return (jarray) 0; }  } cstack4.l = env->CallStaticObjectMethod((cclasses[2]), (cmethods[2]), cstack4.j); refs.insert(cstack4.l); 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 5
        // ICONST_2; Stack: 5
        cstack5.i = 2;
        // New stack: 6
        // SWAP; Stack: 6
        std::swap(cstack5, cstack4);
        // New stack: 6
        // AASTORE; Stack: 6
        if (cstack3.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2106LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack3.l, cstack4.i, cstack5.l); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 3
        // DUP_X1; Stack: 3
        cstack3 = cstack2; cstack2 = cstack1; cstack1 = cstack3;
        // New stack: 4
        // SWAP; Stack: 4
        std::swap(cstack3, cstack2);
        // New stack: 4
        // ICONST_1; Stack: 4
        cstack4.i = 1;
        // New stack: 5
        // SWAP; Stack: 5
        std::swap(cstack4, cstack3);
        // New stack: 5
        // AASTORE; Stack: 5
        if (cstack2.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2106LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack2.l, cstack3.i, cstack4.l); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 2
        // DUP_X1; Stack: 2
        cstack2 = cstack1; cstack1 = cstack0; cstack0 = cstack2;
        // New stack: 3
        // SWAP; Stack: 3
        std::swap(cstack2, cstack1);
        // New stack: 3
        // ICONST_0; Stack: 3
        cstack3.i = 0;
        // New stack: 4
        // SWAP; Stack: 4
        std::swap(cstack3, cstack2);
        // New stack: 4
        // AASTORE; Stack: 4
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2106LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack1.l, cstack2.i, cstack3.l); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // LDC -3227171438472244969; Stack: 1
        cstack1.j = -3227171438472244969LL;
        // New stack: 3
        // LLOAD 3; Stack: 3
        cstack3.j = clocal3.j;
        // New stack: 5
        // LABEL L7; Stack: 5
        L7: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; }
        // New stack: 5
        // ICONST_1; Stack: 5
        cstack5.i = 1;
        // New stack: 6
        // ANEWARRAY java/lang/Object; Stack: 6
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; } } if (cstack5.i < 0) utils::throw_re(env, ((char *)(string_pool + 217LL)), ((char *)(string_pool + 254LL)), -1); else { cstack5.l = env->NewObjectArray(cstack5.i, (cclasses[5]), nullptr); refs.insert(cstack5.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; }
        // New stack: 6
        // DUP; Stack: 6
        cstack6 = cstack5;
        // New stack: 7
        // LDC Ldev/sakura/client/Mahiro_uT;; Stack: 7
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; } } cstack7.l = (cclasses[6]);
        // New stack: 8
        // SWAP; Stack: 8
        std::swap(cstack7, cstack6);
        // New stack: 8
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.11716030067971395.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 8
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; } } cstack8.l = lookup;
        // New stack: 9
        // LDC Ldev/sakura/client/Mahiro_hd;; Stack: 9
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; } } cstack9.l = (cclasses[7]);
        // New stack: 10
        // LDC a; Stack: 10
        cstack10.l = (cstrings[6]);
        // New stack: 11
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;; Stack: 11
        cstack11.l = (cstrings[7]);
        // New stack: 12
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.11716030067971395.a()Ljava/lang/ClassLoader;; Stack: 12
        cstack12.l = classloader;
        // New stack: 13
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 13
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; } } if (!cmethods[3]) { cmethods[3] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 279LL)), ((char *)(string_pool + 306LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; }  } cstack11.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[3]), cstack11.l, cstack12.l); refs.insert(cstack11.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; }
        // New stack: 12
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 12
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 379LL)), ((char *)(string_pool + 390LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; }  } if (cstack8.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 488LL)), -1); else { cstack8.l = env->CallObjectMethod(cstack8.l, (cmethods[4]), cstack9.l, cstack10.l, cstack11.l); refs.insert(cstack8.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; }
        // New stack: 9
        // SWAP; Stack: 9
        std::swap(cstack8, cstack7);
        // New stack: 9
        // LDC \u00c9; Stack: 9
        cstack9.l = (cstrings[10]);
        // New stack: 10
        // SWAP; Stack: 10
        std::swap(cstack9, cstack8);
        // New stack: 10
        // LDC (Ljava/lang/Object;JJ)[B; Stack: 10
        cstack10.l = (cstrings[11]);
        // New stack: 11
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.11716030067971395.a()Ljava/lang/ClassLoader;; Stack: 11
        cstack11.l = classloader;
        // New stack: 12
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 12
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; } } if (!cmethods[3]) { cmethods[3] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 279LL)), ((char *)(string_pool + 306LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; }  } cstack10.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[3]), cstack10.l, cstack11.l); refs.insert(cstack10.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; }
        // New stack: 11
        // SWAP; Stack: 11
        std::swap(cstack10, cstack9);
        // New stack: 11
        // LDC 0; Stack: 11
        cstack11.i = 0;
        // New stack: 12
        // ANEWARRAY java/lang/Object; Stack: 12
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; } } if (cstack11.i < 0) utils::throw_re(env, ((char *)(string_pool + 217LL)), ((char *)(string_pool + 254LL)), -1); else { cstack11.l = env->NewObjectArray(cstack11.i, (cclasses[5]), nullptr); refs.insert(cstack11.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; }
        // New stack: 12
        // CHECKCAST java/lang/Object; Stack: 12
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; } } if (cstack11.l != nullptr && !env->IsInstanceOf(cstack11.l, (cclasses[5]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 513LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; } } 
        // New stack: 12
        // SWAP; Stack: 12
        std::swap(cstack11, cstack10);
        // New stack: 12
        // INVOKESTATIC native/magic/1/linkcallsite/obfuscator0.11716030067971395.a(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/invoke/MemberName;; Stack: 12
        cstack6.l = utils::link_call_site(env, cstack6.l, cstack7.l, cstack8.l, cstack9.l, cstack10.l, cstack11.l); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; }
        // New stack: 7
        // POP; Stack: 7
        ;
        // New stack: 6
        // ICONST_0; Stack: 6
        cstack6.i = 0;
        // New stack: 7
        // AALOAD; Stack: 7
        if (cstack5.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 105LL)), -1); else { cstack5.l = env->GetObjectArrayElement((jobjectArray) cstack5.l, cstack6.i); refs.insert(cstack5.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; }
        // New stack: 6
        // DUP; Stack: 6
        cstack6 = cstack5;
        // New stack: 7
        // INSTANCEOF java/lang/invoke/CallSite; Stack: 7
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; } } cstack6.i = cstack6.l == nullptr ? false : env->IsInstanceOf(cstack6.l, (cclasses[10]));
        // New stack: 7
        // IFEQ L56; Stack: 7
        if (cstack6.i == 0) goto L56;
        // New stack: 6
        // INVOKEINTERFACE java/lang/invoke/CallSite.getTarget()Ljava/lang/invoke/MethodHandle;; Stack: 6
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; } } if (!cmethods[5]) { cmethods[5] = env->GetMethodID((cclasses[10]), ((char *)(string_pool + 530LL)), ((char *)(string_pool + 540LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; }  } if (cstack5.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 574LL)), -1); else { cstack5.l = env->CallObjectMethod(cstack5.l, (cmethods[5])); refs.insert(cstack5.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; }
        // New stack: 6
        // LABEL L56; Stack: 6
        L56: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; }
        // New stack: 6
        // FRAME FULL L: [[Ljava/lang/Object;, [B, 1, 4, [B, 4, 4, [Ldev/sakura/client/Mahiro_m;, 1, 1, [B, [B, 1, 1, [B] S: [[Ljava/lang/Object;, 4, 4, java/lang/Object]; Stack: 6
        refs.erase(cstack0.l); refs.erase(cstack5.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal5.l); refs.erase(clocal10.l); refs.erase(clocal13.l); refs.erase(clocal14.l); refs.erase(clocal17.l); 
        utils::clear_refs(env, refs);
        // New stack: 6
        // CHECKCAST java/lang/invoke/MethodHandle; Stack: 6
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; } } if (cstack5.l != nullptr && !env->IsInstanceOf(cstack5.l, (cclasses[11]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 601LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; } } 
        // New stack: 6
        // GOTO L57; Stack: 6
        goto L57;
        // New stack: 6
        // LABEL L8; Stack: 6
        L8: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 6
        // FRAME SAME1 L: null S: [java/lang/Throwable]; Stack: 6
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal5.l); refs.erase(clocal10.l); refs.erase(clocal13.l); refs.erase(clocal14.l); refs.erase(clocal17.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // INSTANCEOF java/lang/BootstrapMethodError; Stack: 2
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } cstack1.i = cstack1.l == nullptr ? false : env->IsInstanceOf(cstack1.l, (cclasses[12]));
        // New stack: 2
        // IFNE L58; Stack: 2
        if (cstack1.i != 0) goto L58;
        // New stack: 1
        // NEW java/lang/BootstrapMethodError; Stack: 1
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (jobject obj = env->AllocObject((cclasses[12]))) { cstack1.l = obj; refs.insert(obj); } 
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
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (!cmethods[6]) { cmethods[6] = env->GetMethodID((cclasses[12]), ((char *)(string_pool + 631LL)), ((char *)(string_pool + 638LL))); if (env->ExceptionCheck()) { return (jarray) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 663LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[12]), (cmethods[6]), cstack2.l); 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // LABEL L58; Stack: 1
        L58: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/Object]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal5.l); refs.erase(clocal10.l); refs.erase(clocal13.l); refs.erase(clocal14.l); refs.erase(clocal17.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 686LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 0
        // LABEL L57; Stack: 0
        L57: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 0
        // FRAME FULL L: [[Ljava/lang/Object;, [B, 1, 4, [B, 4, 4, [Ldev/sakura/client/Mahiro_m;, 1, 1, [B, [B, 1, 1, [B] S: [[Ljava/lang/Object;, 4, 4, java/lang/invoke/MethodHandle]; Stack: 0
        refs.erase(cstack0.l); refs.erase(cstack5.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal5.l); refs.erase(clocal10.l); refs.erase(clocal13.l); refs.erase(clocal14.l); refs.erase(clocal17.l); 
        utils::clear_refs(env, refs);
        // New stack: 6
        // INVOKESTATIC native/magic/1/invoke/obfuscator0.11716030067971395.a(Ljava/lang/Object;JJLjava/lang/invoke/MethodHandle;)[B; Stack: 6
        if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { cclasses_mtx[13].lock(); if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[13] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[13].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (!cmethods[7]) { cmethods[7] = env->GetStaticMethodID((cclasses[13]), ((char *)(string_pool + 783LL)), ((char *)(string_pool + 798LL))); if (env->ExceptionCheck()) { return (jarray) 0; }  } cstack0.l = env->CallStaticObjectMethod((cclasses[13]), (cmethods[7]), cstack0.l, cstack1.j, cstack3.j, cstack5.l); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // CHECKCAST [B; Stack: 1
        if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { cclasses_mtx[1].lock(); if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { if (jclass clazz = env->FindClass(((char *)(string_pool + 979LL)))) { cclasses[1] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[1].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (cstack0.l != nullptr && !env->IsInstanceOf(cstack0.l, (cclasses[1]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 979LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (jarray) 0; } } 
        // New stack: 1
        // ASTORE 14; Stack: 1
        clocal14.l = cstack0.l; refs.insert(cstack0.l);
        // New stack: 0
        // ILOAD 11; Stack: 0
        cstack0.i = clocal11.i;
        // New stack: 1
        // ILOAD 2; Stack: 1
        cstack1.i = clocal2.i;
        // New stack: 2
        // ILOAD 15; Stack: 2
        cstack2.i = clocal15.i;
        // New stack: 3
        // ISUB; Stack: 3
        cstack1.i = cstack1.i - cstack2.i;
        // New stack: 2
        // LDC -3184205530881394662; Stack: 2
        cstack2.j = -3184205530881394662LL;
        // New stack: 4
        // LLOAD 3; Stack: 4
        cstack4.j = clocal3.j;
        // New stack: 6
        // LABEL L5; Stack: 6
        L5: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; }
        // New stack: 6
        // ICONST_1; Stack: 6
        cstack6.i = 1;
        // New stack: 7
        // ANEWARRAY java/lang/Object; Stack: 7
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; } } if (cstack6.i < 0) utils::throw_re(env, ((char *)(string_pool + 217LL)), ((char *)(string_pool + 254LL)), -1); else { cstack6.l = env->NewObjectArray(cstack6.i, (cclasses[5]), nullptr); refs.insert(cstack6.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; }
        // New stack: 7
        // DUP; Stack: 7
        cstack7 = cstack6;
        // New stack: 8
        // LDC Ldev/sakura/client/Mahiro_uT;; Stack: 8
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; } } cstack8.l = (cclasses[6]);
        // New stack: 9
        // SWAP; Stack: 9
        std::swap(cstack8, cstack7);
        // New stack: 9
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.11716030067971395.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 9
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; } } cstack9.l = lookup;
        // New stack: 10
        // LDC Ldev/sakura/client/Mahiro_hd;; Stack: 10
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; } } cstack10.l = (cclasses[7]);
        // New stack: 11
        // LDC a; Stack: 11
        cstack11.l = (cstrings[6]);
        // New stack: 12
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;; Stack: 12
        cstack12.l = (cstrings[7]);
        // New stack: 13
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.11716030067971395.a()Ljava/lang/ClassLoader;; Stack: 13
        cstack13.l = classloader;
        // New stack: 14
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 14
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; } } if (!cmethods[3]) { cmethods[3] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 279LL)), ((char *)(string_pool + 306LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; }  } cstack12.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[3]), cstack12.l, cstack13.l); refs.insert(cstack12.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; }
        // New stack: 13
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 13
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 379LL)), ((char *)(string_pool + 390LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; }  } if (cstack9.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 488LL)), -1); else { cstack9.l = env->CallObjectMethod(cstack9.l, (cmethods[4]), cstack10.l, cstack11.l, cstack12.l); refs.insert(cstack9.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; }
        // New stack: 10
        // SWAP; Stack: 10
        std::swap(cstack9, cstack8);
        // New stack: 10
        // LDC \u00c9; Stack: 10
        cstack10.l = (cstrings[10]);
        // New stack: 11
        // SWAP; Stack: 11
        std::swap(cstack10, cstack9);
        // New stack: 11
        // LDC (IIJJ)I; Stack: 11
        cstack11.l = (cstrings[25]);
        // New stack: 12
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.11716030067971395.a()Ljava/lang/ClassLoader;; Stack: 12
        cstack12.l = classloader;
        // New stack: 13
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 13
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; } } if (!cmethods[3]) { cmethods[3] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 279LL)), ((char *)(string_pool + 306LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; }  } cstack11.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[3]), cstack11.l, cstack12.l); refs.insert(cstack11.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; }
        // New stack: 12
        // SWAP; Stack: 12
        std::swap(cstack11, cstack10);
        // New stack: 12
        // LDC 0; Stack: 12
        cstack12.i = 0;
        // New stack: 13
        // ANEWARRAY java/lang/Object; Stack: 13
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; } } if (cstack12.i < 0) utils::throw_re(env, ((char *)(string_pool + 217LL)), ((char *)(string_pool + 254LL)), -1); else { cstack12.l = env->NewObjectArray(cstack12.i, (cclasses[5]), nullptr); refs.insert(cstack12.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; }
        // New stack: 13
        // CHECKCAST java/lang/Object; Stack: 13
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; } } if (cstack12.l != nullptr && !env->IsInstanceOf(cstack12.l, (cclasses[5]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 513LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; } } 
        // New stack: 13
        // SWAP; Stack: 13
        std::swap(cstack12, cstack11);
        // New stack: 13
        // INVOKESTATIC native/magic/1/linkcallsite/obfuscator0.11716030067971395.a(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/invoke/MemberName;; Stack: 13
        cstack7.l = utils::link_call_site(env, cstack7.l, cstack8.l, cstack9.l, cstack10.l, cstack11.l, cstack12.l); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; }
        // New stack: 8
        // POP; Stack: 8
        ;
        // New stack: 7
        // ICONST_0; Stack: 7
        cstack7.i = 0;
        // New stack: 8
        // AALOAD; Stack: 8
        if (cstack6.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 105LL)), -1); else { cstack6.l = env->GetObjectArrayElement((jobjectArray) cstack6.l, cstack7.i); refs.insert(cstack6.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; }
        // New stack: 7
        // DUP; Stack: 7
        cstack7 = cstack6;
        // New stack: 8
        // INSTANCEOF java/lang/invoke/CallSite; Stack: 8
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; } } cstack7.i = cstack7.l == nullptr ? false : env->IsInstanceOf(cstack7.l, (cclasses[10]));
        // New stack: 8
        // IFEQ L59; Stack: 8
        if (cstack7.i == 0) goto L59;
        // New stack: 7
        // INVOKEINTERFACE java/lang/invoke/CallSite.getTarget()Ljava/lang/invoke/MethodHandle;; Stack: 7
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; } } if (!cmethods[5]) { cmethods[5] = env->GetMethodID((cclasses[10]), ((char *)(string_pool + 530LL)), ((char *)(string_pool + 540LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; }  } if (cstack6.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 574LL)), -1); else { cstack6.l = env->CallObjectMethod(cstack6.l, (cmethods[5])); refs.insert(cstack6.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; }
        // New stack: 7
        // LABEL L59; Stack: 7
        L59: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; }
        // New stack: 7
        // FRAME FULL L: [[Ljava/lang/Object;, [B, 1, 4, [B, 4, 4, [Ldev/sakura/client/Mahiro_m;, 1, 1, [B, [B, 1, 1, [B] S: [1, 1, 4, 4, java/lang/Object]; Stack: 7
        refs.erase(cstack6.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal5.l); refs.erase(clocal10.l); refs.erase(clocal13.l); refs.erase(clocal14.l); refs.erase(clocal17.l); 
        utils::clear_refs(env, refs);
        // New stack: 7
        // CHECKCAST java/lang/invoke/MethodHandle; Stack: 7
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; } } if (cstack6.l != nullptr && !env->IsInstanceOf(cstack6.l, (cclasses[11]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 601LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; } } 
        // New stack: 7
        // GOTO L60; Stack: 7
        goto L60;
        // New stack: 7
        // LABEL L6; Stack: 7
        L6: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 7
        // FRAME SAME1 L: null S: [java/lang/Throwable]; Stack: 7
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal5.l); refs.erase(clocal10.l); refs.erase(clocal13.l); refs.erase(clocal14.l); refs.erase(clocal17.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // INSTANCEOF java/lang/BootstrapMethodError; Stack: 2
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } cstack1.i = cstack1.l == nullptr ? false : env->IsInstanceOf(cstack1.l, (cclasses[12]));
        // New stack: 2
        // IFNE L61; Stack: 2
        if (cstack1.i != 0) goto L61;
        // New stack: 1
        // NEW java/lang/BootstrapMethodError; Stack: 1
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (jobject obj = env->AllocObject((cclasses[12]))) { cstack1.l = obj; refs.insert(obj); } 
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
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (!cmethods[6]) { cmethods[6] = env->GetMethodID((cclasses[12]), ((char *)(string_pool + 631LL)), ((char *)(string_pool + 638LL))); if (env->ExceptionCheck()) { return (jarray) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 663LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[12]), (cmethods[6]), cstack2.l); 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // LABEL L61; Stack: 1
        L61: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/Object]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal5.l); refs.erase(clocal10.l); refs.erase(clocal13.l); refs.erase(clocal14.l); refs.erase(clocal17.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 686LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 0
        // LABEL L60; Stack: 0
        L60: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 0
        // FRAME FULL L: [[Ljava/lang/Object;, [B, 1, 4, [B, 4, 4, [Ldev/sakura/client/Mahiro_m;, 1, 1, [B, [B, 1, 1, [B] S: [1, 1, 4, 4, java/lang/invoke/MethodHandle]; Stack: 0
        refs.erase(cstack6.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal5.l); refs.erase(clocal10.l); refs.erase(clocal13.l); refs.erase(clocal14.l); refs.erase(clocal17.l); 
        utils::clear_refs(env, refs);
        // New stack: 7
        // INVOKESTATIC native/magic/1/invoke/obfuscator0.11716030067971395.a(IIJJLjava/lang/invoke/MethodHandle;)I; Stack: 7
        if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { cclasses_mtx[13].lock(); if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[13] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[13].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (!cmethods[14]) { cmethods[14] = env->GetStaticMethodID((cclasses[13]), ((char *)(string_pool + 18713LL)), ((char *)(string_pool + 18729LL))); if (env->ExceptionCheck()) { return (jarray) 0; }  } cstack0.i = env->CallStaticIntMethod((cclasses[13]), (cmethods[14]), cstack0.i, cstack1.i, cstack2.j, cstack4.j, cstack6.l); 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // ISTORE 18; Stack: 1
        clocal18.i = cstack0.i;
        // New stack: 0
        // ALOAD 14; Stack: 0
        cstack0.l = clocal14.l; refs.insert(cstack0.l);
        // New stack: 1
        // ICONST_0; Stack: 1
        cstack1.i = 0;
        // New stack: 2
        // ALOAD 13; Stack: 2
        cstack2.l = clocal13.l; refs.insert(cstack2.l);
        // New stack: 3
        // ILOAD 15; Stack: 3
        cstack3.i = clocal15.i;
        // New stack: 4
        // ILOAD 18; Stack: 4
        cstack4.i = clocal18.i;
        // New stack: 5
        // LDC -3187520680186415985; Stack: 5
        cstack5.j = -3187520680186415985LL;
        // New stack: 7
        // LLOAD 3; Stack: 7
        cstack7.j = clocal3.j;
        // New stack: 9
        // LABEL L3; Stack: 9
        L3: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; }
        // New stack: 9
        // ICONST_1; Stack: 9
        cstack9.i = 1;
        // New stack: 10
        // ANEWARRAY java/lang/Object; Stack: 10
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; } } if (cstack9.i < 0) utils::throw_re(env, ((char *)(string_pool + 217LL)), ((char *)(string_pool + 254LL)), -1); else { cstack9.l = env->NewObjectArray(cstack9.i, (cclasses[5]), nullptr); refs.insert(cstack9.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; }
        // New stack: 10
        // DUP; Stack: 10
        cstack10 = cstack9;
        // New stack: 11
        // LDC Ldev/sakura/client/Mahiro_uT;; Stack: 11
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; } } cstack11.l = (cclasses[6]);
        // New stack: 12
        // SWAP; Stack: 12
        std::swap(cstack11, cstack10);
        // New stack: 12
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.11716030067971395.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 12
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; } } cstack12.l = lookup;
        // New stack: 13
        // LDC Ldev/sakura/client/Mahiro_hd;; Stack: 13
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; } } cstack13.l = (cclasses[7]);
        // New stack: 14
        // LDC a; Stack: 14
        cstack14.l = (cstrings[6]);
        // New stack: 15
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;; Stack: 15
        cstack15.l = (cstrings[7]);
        // New stack: 16
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.11716030067971395.a()Ljava/lang/ClassLoader;; Stack: 16
        cstack16.l = classloader;
        // New stack: 17
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 17
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; } } if (!cmethods[3]) { cmethods[3] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 279LL)), ((char *)(string_pool + 306LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; }  } cstack15.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[3]), cstack15.l, cstack16.l); refs.insert(cstack15.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; }
        // New stack: 16
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 16
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 379LL)), ((char *)(string_pool + 390LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; }  } if (cstack12.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 488LL)), -1); else { cstack12.l = env->CallObjectMethod(cstack12.l, (cmethods[4]), cstack13.l, cstack14.l, cstack15.l); refs.insert(cstack12.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; }
        // New stack: 13
        // SWAP; Stack: 13
        std::swap(cstack12, cstack11);
        // New stack: 13
        // LDC \u00c9; Stack: 13
        cstack13.l = (cstrings[10]);
        // New stack: 14
        // SWAP; Stack: 14
        std::swap(cstack13, cstack12);
        // New stack: 14
        // LDC (Ljava/lang/Object;ILjava/lang/Object;IIJJ)V; Stack: 14
        cstack14.l = (cstrings[26]);
        // New stack: 15
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.11716030067971395.a()Ljava/lang/ClassLoader;; Stack: 15
        cstack15.l = classloader;
        // New stack: 16
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 16
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; } } if (!cmethods[3]) { cmethods[3] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 279LL)), ((char *)(string_pool + 306LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; }  } cstack14.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[3]), cstack14.l, cstack15.l); refs.insert(cstack14.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; }
        // New stack: 15
        // SWAP; Stack: 15
        std::swap(cstack14, cstack13);
        // New stack: 15
        // LDC 0; Stack: 15
        cstack15.i = 0;
        // New stack: 16
        // ANEWARRAY java/lang/Object; Stack: 16
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; } } if (cstack15.i < 0) utils::throw_re(env, ((char *)(string_pool + 217LL)), ((char *)(string_pool + 254LL)), -1); else { cstack15.l = env->NewObjectArray(cstack15.i, (cclasses[5]), nullptr); refs.insert(cstack15.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; }
        // New stack: 16
        // CHECKCAST java/lang/Object; Stack: 16
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; } } if (cstack15.l != nullptr && !env->IsInstanceOf(cstack15.l, (cclasses[5]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 513LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; } } 
        // New stack: 16
        // SWAP; Stack: 16
        std::swap(cstack15, cstack14);
        // New stack: 16
        // INVOKESTATIC native/magic/1/linkcallsite/obfuscator0.11716030067971395.a(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/invoke/MemberName;; Stack: 16
        cstack10.l = utils::link_call_site(env, cstack10.l, cstack11.l, cstack12.l, cstack13.l, cstack14.l, cstack15.l); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; }
        // New stack: 11
        // POP; Stack: 11
        ;
        // New stack: 10
        // ICONST_0; Stack: 10
        cstack10.i = 0;
        // New stack: 11
        // AALOAD; Stack: 11
        if (cstack9.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 105LL)), -1); else { cstack9.l = env->GetObjectArrayElement((jobjectArray) cstack9.l, cstack10.i); refs.insert(cstack9.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; }
        // New stack: 10
        // DUP; Stack: 10
        cstack10 = cstack9;
        // New stack: 11
        // INSTANCEOF java/lang/invoke/CallSite; Stack: 11
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; } } cstack10.i = cstack10.l == nullptr ? false : env->IsInstanceOf(cstack10.l, (cclasses[10]));
        // New stack: 11
        // IFEQ L62; Stack: 11
        if (cstack10.i == 0) goto L62;
        // New stack: 10
        // INVOKEINTERFACE java/lang/invoke/CallSite.getTarget()Ljava/lang/invoke/MethodHandle;; Stack: 10
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; } } if (!cmethods[5]) { cmethods[5] = env->GetMethodID((cclasses[10]), ((char *)(string_pool + 530LL)), ((char *)(string_pool + 540LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; }  } if (cstack9.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 574LL)), -1); else { cstack9.l = env->CallObjectMethod(cstack9.l, (cmethods[5])); refs.insert(cstack9.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; }
        // New stack: 10
        // LABEL L62; Stack: 10
        L62: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; }
        // New stack: 10
        // FRAME FULL L: [[Ljava/lang/Object;, [B, 1, 4, [B, 4, 4, [Ldev/sakura/client/Mahiro_m;, 1, 1, [B, [B, 1, 1, [B, 1] S: [[B, 1, [B, 1, 1, 4, 4, java/lang/Object]; Stack: 10
        refs.erase(cstack0.l); refs.erase(cstack2.l); refs.erase(cstack9.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal5.l); refs.erase(clocal10.l); refs.erase(clocal13.l); refs.erase(clocal14.l); refs.erase(clocal17.l); 
        utils::clear_refs(env, refs);
        // New stack: 10
        // CHECKCAST java/lang/invoke/MethodHandle; Stack: 10
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; } } if (cstack9.l != nullptr && !env->IsInstanceOf(cstack9.l, (cclasses[11]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 601LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; } } 
        // New stack: 10
        // GOTO L63; Stack: 10
        goto L63;
        // New stack: 10
        // LABEL L4; Stack: 10
        L4: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 10
        // FRAME SAME1 L: null S: [java/lang/Throwable]; Stack: 10
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal5.l); refs.erase(clocal10.l); refs.erase(clocal13.l); refs.erase(clocal14.l); refs.erase(clocal17.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // INSTANCEOF java/lang/BootstrapMethodError; Stack: 2
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } cstack1.i = cstack1.l == nullptr ? false : env->IsInstanceOf(cstack1.l, (cclasses[12]));
        // New stack: 2
        // IFNE L64; Stack: 2
        if (cstack1.i != 0) goto L64;
        // New stack: 1
        // NEW java/lang/BootstrapMethodError; Stack: 1
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (jobject obj = env->AllocObject((cclasses[12]))) { cstack1.l = obj; refs.insert(obj); } 
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
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (!cmethods[6]) { cmethods[6] = env->GetMethodID((cclasses[12]), ((char *)(string_pool + 631LL)), ((char *)(string_pool + 638LL))); if (env->ExceptionCheck()) { return (jarray) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 663LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[12]), (cmethods[6]), cstack2.l); 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // LABEL L64; Stack: 1
        L64: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/Object]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal5.l); refs.erase(clocal10.l); refs.erase(clocal13.l); refs.erase(clocal14.l); refs.erase(clocal17.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 686LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 0
        // LABEL L63; Stack: 0
        L63: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 0
        // FRAME FULL L: [[Ljava/lang/Object;, [B, 1, 4, [B, 4, 4, [Ldev/sakura/client/Mahiro_m;, 1, 1, [B, [B, 1, 1, [B, 1] S: [[B, 1, [B, 1, 1, 4, 4, java/lang/invoke/MethodHandle]; Stack: 0
        refs.erase(cstack0.l); refs.erase(cstack2.l); refs.erase(cstack9.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal5.l); refs.erase(clocal10.l); refs.erase(clocal13.l); refs.erase(clocal14.l); refs.erase(clocal17.l); 
        utils::clear_refs(env, refs);
        // New stack: 10
        // INVOKESTATIC native/magic/1/invoke/obfuscator0.11716030067971395.a(Ljava/lang/Object;ILjava/lang/Object;IIJJLjava/lang/invoke/MethodHandle;)V; Stack: 10
        if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { cclasses_mtx[13].lock(); if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[13] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[13].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (!cmethods[15]) { cmethods[15] = env->GetStaticMethodID((cclasses[13]), ((char *)(string_pool + 11420LL)), ((char *)(string_pool + 11436LL))); if (env->ExceptionCheck()) { return (jarray) 0; }  } env->CallStaticVoidMethod((cclasses[13]), (cmethods[15]), cstack0.l, cstack1.i, cstack2.l, cstack3.i, cstack4.i, cstack5.j, cstack7.j, cstack9.l); 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 0
        // ILOAD 15; Stack: 0
        cstack0.i = clocal15.i;
        // New stack: 1
        // ILOAD 18; Stack: 1
        cstack1.i = clocal18.i;
        // New stack: 2
        // IADD; Stack: 2
        cstack0.i = cstack0.i + cstack1.i;
        // New stack: 1
        // ISTORE 15; Stack: 1
        clocal15.i = cstack0.i;
        // New stack: 0
        // IINC 16 1; Stack: 0
        clocal16.i += 1;
        // New stack: 0
        // ALOAD 10; Stack: 0
        cstack0.l = clocal10.l; refs.insert(cstack0.l);
        // New stack: 1
        // IFNONNULL L47; Stack: 1
        if (!env->IsSameObject(cstack0.l, nullptr)) goto L47;
        // New stack: 0
        // LABEL L48; Stack: 0
        L48: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 0
        // FRAME CHOP L: [null, null] S: null; Stack: 0
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal5.l); refs.erase(clocal10.l); refs.erase(clocal13.l); refs.erase(clocal14.l); 
        utils::clear_refs(env, refs);
        // New stack: 0
        // ALOAD 1; Stack: 0
        cstack0.l = clocal1.l; refs.insert(cstack0.l);
        // New stack: 1
        // ICONST_0; Stack: 1
        cstack1.i = 0;
        // New stack: 2
        // LDC -3237660339021901404; Stack: 2
        cstack2.j = -3237660339021901404LL;
        // New stack: 4
        // LLOAD 3; Stack: 4
        cstack4.j = clocal3.j;
        // New stack: 6
        // LABEL L1; Stack: 6
        L1: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; }
        // New stack: 6
        // ICONST_1; Stack: 6
        cstack6.i = 1;
        // New stack: 7
        // ANEWARRAY java/lang/Object; Stack: 7
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; } } if (cstack6.i < 0) utils::throw_re(env, ((char *)(string_pool + 217LL)), ((char *)(string_pool + 254LL)), -1); else { cstack6.l = env->NewObjectArray(cstack6.i, (cclasses[5]), nullptr); refs.insert(cstack6.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; }
        // New stack: 7
        // DUP; Stack: 7
        cstack7 = cstack6;
        // New stack: 8
        // LDC Ldev/sakura/client/Mahiro_uT;; Stack: 8
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; } } cstack8.l = (cclasses[6]);
        // New stack: 9
        // SWAP; Stack: 9
        std::swap(cstack8, cstack7);
        // New stack: 9
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.11716030067971395.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 9
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; } } cstack9.l = lookup;
        // New stack: 10
        // LDC Ldev/sakura/client/Mahiro_hd;; Stack: 10
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; } } cstack10.l = (cclasses[7]);
        // New stack: 11
        // LDC a; Stack: 11
        cstack11.l = (cstrings[6]);
        // New stack: 12
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;; Stack: 12
        cstack12.l = (cstrings[7]);
        // New stack: 13
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.11716030067971395.a()Ljava/lang/ClassLoader;; Stack: 13
        cstack13.l = classloader;
        // New stack: 14
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 14
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; } } if (!cmethods[3]) { cmethods[3] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 279LL)), ((char *)(string_pool + 306LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; }  } cstack12.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[3]), cstack12.l, cstack13.l); refs.insert(cstack12.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; }
        // New stack: 13
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 13
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 379LL)), ((char *)(string_pool + 390LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; }  } if (cstack9.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 488LL)), -1); else { cstack9.l = env->CallObjectMethod(cstack9.l, (cmethods[4]), cstack10.l, cstack11.l, cstack12.l); refs.insert(cstack9.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; }
        // New stack: 10
        // SWAP; Stack: 10
        std::swap(cstack9, cstack8);
        // New stack: 10
        // LDC \u00c9; Stack: 10
        cstack10.l = (cstrings[10]);
        // New stack: 11
        // SWAP; Stack: 11
        std::swap(cstack10, cstack9);
        // New stack: 11
        // LDC (Ljava/lang/Object;BJJ)V; Stack: 11
        cstack11.l = (cstrings[27]);
        // New stack: 12
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.11716030067971395.a()Ljava/lang/ClassLoader;; Stack: 12
        cstack12.l = classloader;
        // New stack: 13
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 13
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; } } if (!cmethods[3]) { cmethods[3] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 279LL)), ((char *)(string_pool + 306LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; }  } cstack11.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[3]), cstack11.l, cstack12.l); refs.insert(cstack11.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; }
        // New stack: 12
        // SWAP; Stack: 12
        std::swap(cstack11, cstack10);
        // New stack: 12
        // LDC 0; Stack: 12
        cstack12.i = 0;
        // New stack: 13
        // ANEWARRAY java/lang/Object; Stack: 13
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; } } if (cstack12.i < 0) utils::throw_re(env, ((char *)(string_pool + 217LL)), ((char *)(string_pool + 254LL)), -1); else { cstack12.l = env->NewObjectArray(cstack12.i, (cclasses[5]), nullptr); refs.insert(cstack12.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; }
        // New stack: 13
        // CHECKCAST java/lang/Object; Stack: 13
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; } } if (cstack12.l != nullptr && !env->IsInstanceOf(cstack12.l, (cclasses[5]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 513LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; } } 
        // New stack: 13
        // SWAP; Stack: 13
        std::swap(cstack12, cstack11);
        // New stack: 13
        // INVOKESTATIC native/magic/1/linkcallsite/obfuscator0.11716030067971395.a(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/invoke/MemberName;; Stack: 13
        cstack7.l = utils::link_call_site(env, cstack7.l, cstack8.l, cstack9.l, cstack10.l, cstack11.l, cstack12.l); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; }
        // New stack: 8
        // POP; Stack: 8
        ;
        // New stack: 7
        // ICONST_0; Stack: 7
        cstack7.i = 0;
        // New stack: 8
        // AALOAD; Stack: 8
        if (cstack6.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 105LL)), -1); else { cstack6.l = env->GetObjectArrayElement((jobjectArray) cstack6.l, cstack7.i); refs.insert(cstack6.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; }
        // New stack: 7
        // DUP; Stack: 7
        cstack7 = cstack6;
        // New stack: 8
        // INSTANCEOF java/lang/invoke/CallSite; Stack: 8
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; } } cstack7.i = cstack7.l == nullptr ? false : env->IsInstanceOf(cstack7.l, (cclasses[10]));
        // New stack: 8
        // IFEQ L65; Stack: 8
        if (cstack7.i == 0) goto L65;
        // New stack: 7
        // INVOKEINTERFACE java/lang/invoke/CallSite.getTarget()Ljava/lang/invoke/MethodHandle;; Stack: 7
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; } } if (!cmethods[5]) { cmethods[5] = env->GetMethodID((cclasses[10]), ((char *)(string_pool + 530LL)), ((char *)(string_pool + 540LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; }  } if (cstack6.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 574LL)), -1); else { cstack6.l = env->CallObjectMethod(cstack6.l, (cmethods[5])); refs.insert(cstack6.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; }
        // New stack: 7
        // LABEL L65; Stack: 7
        L65: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; }
        // New stack: 7
        // FRAME FULL L: [[Ljava/lang/Object;, [B, 1, 4, [B, 4, 4, [Ldev/sakura/client/Mahiro_m;, 1, 1, [B, [B, 1, 1] S: [[B, 1, 4, 4, java/lang/Object]; Stack: 7
        refs.erase(cstack0.l); refs.erase(cstack6.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal5.l); refs.erase(clocal10.l); refs.erase(clocal13.l); refs.erase(clocal14.l); 
        utils::clear_refs(env, refs);
        // New stack: 7
        // CHECKCAST java/lang/invoke/MethodHandle; Stack: 7
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; } } if (cstack6.l != nullptr && !env->IsInstanceOf(cstack6.l, (cclasses[11]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 601LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; } } 
        // New stack: 7
        // GOTO L66; Stack: 7
        goto L66;
        // New stack: 7
        // LABEL L2; Stack: 7
        L2: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 7
        // FRAME SAME1 L: null S: [java/lang/Throwable]; Stack: 7
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal5.l); refs.erase(clocal10.l); refs.erase(clocal13.l); refs.erase(clocal14.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // INSTANCEOF java/lang/BootstrapMethodError; Stack: 2
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } cstack1.i = cstack1.l == nullptr ? false : env->IsInstanceOf(cstack1.l, (cclasses[12]));
        // New stack: 2
        // IFNE L67; Stack: 2
        if (cstack1.i != 0) goto L67;
        // New stack: 1
        // NEW java/lang/BootstrapMethodError; Stack: 1
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (jobject obj = env->AllocObject((cclasses[12]))) { cstack1.l = obj; refs.insert(obj); } 
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
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (!cmethods[6]) { cmethods[6] = env->GetMethodID((cclasses[12]), ((char *)(string_pool + 631LL)), ((char *)(string_pool + 638LL))); if (env->ExceptionCheck()) { return (jarray) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 663LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[12]), (cmethods[6]), cstack2.l); 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // LABEL L67; Stack: 1
        L67: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/Object]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal5.l); refs.erase(clocal10.l); refs.erase(clocal13.l); refs.erase(clocal14.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 686LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 0
        // LABEL L66; Stack: 0
        L66: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 0
        // FRAME FULL L: [[Ljava/lang/Object;, [B, 1, 4, [B, 4, 4, [Ldev/sakura/client/Mahiro_m;, 1, 1, [B, [B, 1, 1] S: [[B, 1, 4, 4, java/lang/invoke/MethodHandle]; Stack: 0
        refs.erase(cstack0.l); refs.erase(cstack6.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal5.l); refs.erase(clocal10.l); refs.erase(clocal13.l); refs.erase(clocal14.l); 
        utils::clear_refs(env, refs);
        // New stack: 7
        // INVOKESTATIC native/magic/1/invoke/obfuscator0.11716030067971395.a(Ljava/lang/Object;BJJLjava/lang/invoke/MethodHandle;)V; Stack: 7
        if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { cclasses_mtx[13].lock(); if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[13] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[13].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (!cmethods[16]) { cmethods[16] = env->GetStaticMethodID((cclasses[13]), ((char *)(string_pool + 18768LL)), ((char *)(string_pool + 18784LL))); if (env->ExceptionCheck()) { return (jarray) 0; }  } env->CallStaticVoidMethod((cclasses[13]), (cmethods[16]), cstack0.l, cstack1.i, cstack2.j, cstack4.j, cstack6.l); 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 0
        // LLOAD 3; Stack: 0
        cstack0.j = clocal3.j;
        // New stack: 2
        // LCONST_0; Stack: 2
        cstack2.j = 0;
        // New stack: 4
        // LCMP; Stack: 4
        cstack0.i = (cstack0.j == cstack2.j) ? 0 : (cstack0.j > cstack2.j ? 1 : -1);
        // New stack: 1
        // IFLT L49; Stack: 1
        if (cstack0.i < 0) goto L49;
        // New stack: 0
        // LABEL L49; Stack: 0
        L49: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 0
        // FRAME SAME L: null S: null; Stack: 0
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal5.l); refs.erase(clocal10.l); refs.erase(clocal13.l); refs.erase(clocal14.l); 
        utils::clear_refs(env, refs);
        // New stack: 0
        // ALOAD 13; Stack: 0
        cstack0.l = clocal13.l; refs.insert(cstack0.l);
        // New stack: 1
        // ARETURN; Stack: 1
        return (jarray) cstack0.l;
        // New stack: 0
        return (jarray) 0;
        L_CATCH_10: if (env->IsInstanceOf(cstack0.l, (cclasses[0]))) { goto L2; }
        env->Throw((jthrowable) cstack0.l); return (jarray) 0;
        L_CATCH_9: if (env->IsInstanceOf(cstack0.l, (cclasses[0]))) { goto L4; }
        env->Throw((jthrowable) cstack0.l); return (jarray) 0;
        L_CATCH_5: if (env->IsInstanceOf(cstack0.l, (cclasses[0]))) { goto L12; }
        env->Throw((jthrowable) cstack0.l); return (jarray) 0;
        L_CATCH_0: if (env->IsInstanceOf(cstack0.l, (cclasses[0]))) { goto L22; }
        env->Throw((jthrowable) cstack0.l); return (jarray) 0;
        L_CATCH_4: if (env->IsInstanceOf(cstack0.l, (cclasses[0]))) { goto L14; }
        env->Throw((jthrowable) cstack0.l); return (jarray) 0;
        L_CATCH_3: if (env->IsInstanceOf(cstack0.l, (cclasses[0]))) { goto L16; }
        env->Throw((jthrowable) cstack0.l); return (jarray) 0;
        L_CATCH_2: if (env->IsInstanceOf(cstack0.l, (cclasses[0]))) { goto L18; }
        env->Throw((jthrowable) cstack0.l); return (jarray) 0;
        L_CATCH_1: if (env->IsInstanceOf(cstack0.l, (cclasses[0]))) { goto L20; }
        env->Throw((jthrowable) cstack0.l); return (jarray) 0;
        L_CATCH_8: if (env->IsInstanceOf(cstack0.l, (cclasses[0]))) { goto L6; }
        env->Throw((jthrowable) cstack0.l); return (jarray) 0;
        L_CATCH_6: if (env->IsInstanceOf(cstack0.l, (cclasses[0]))) { goto L10; }
        env->Throw((jthrowable) cstack0.l); return (jarray) 0;
        L_CATCH_7: if (env->IsInstanceOf(cstack0.l, (cclasses[0]))) { goto L8; }
        env->Throw((jthrowable) cstack0.l); return (jarray) 0;
    }
    
    // Mahiro_S([Ljava/lang/Object;)[B
    jarray JNICALL __ngen_native_Mahiro_S4(JNIEnv *env, jclass clazz, jarray arg0) {
        jobject classloader = utils::get_classloader_from_class(env, clazz);
        if (env->ExceptionCheck()) { return (jarray) 0; }
        if (classloader == nullptr) { env->FatalError(((char *)(string_pool + 54LL))); return (jarray) 0; }
    
        jobject lookup = nullptr;
        // try-catch-class java/lang/Exception
        if (!cclasses[17] || env->IsSameObject(cclasses[17], NULL)) { cclasses_mtx[17].lock(); if (!cclasses[17] || env->IsSameObject(cclasses[17], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[28]))) { cclasses[17] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[17].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } }
        // try-catch-class java/lang/Throwable
        if (!cclasses[0] || env->IsSameObject(cclasses[0], NULL)) { cclasses_mtx[0].lock(); if (!cclasses[0] || env->IsSameObject(cclasses[0], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[0]))) { cclasses[0] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[0].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } }
        jvalue cstack0 = {}, cstack1 = {}, cstack2 = {}, cstack3 = {}, cstack4 = {}, cstack5 = {}, cstack6 = {}, cstack7 = {}, cstack8 = {}, cstack9 = {}, cstack10 = {}, cstack11 = {}, cstack12 = {}, cstack13 = {}, cstack14 = {};
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
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 105LL)), -1); else { cstack1.l = env->GetObjectArrayElement((jobjectArray) cstack1.l, cstack2.i); refs.insert(cstack1.l); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 2
        // CHECKCAST [B; Stack: 2
        if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { cclasses_mtx[1].lock(); if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { if (jclass clazz = env->FindClass(((char *)(string_pool + 979LL)))) { cclasses[1] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[1].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (cstack1.l != nullptr && !env->IsInstanceOf(cstack1.l, (cclasses[1]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 979LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (jarray) 0; } } 
        // New stack: 2
        // ASTORE 4; Stack: 2
        clocal4.l = cstack1.l; refs.insert(cstack1.l);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // ICONST_1; Stack: 2
        cstack2.i = 1;
        // New stack: 3
        // AALOAD; Stack: 3
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 105LL)), -1); else { cstack1.l = env->GetObjectArrayElement((jobjectArray) cstack1.l, cstack2.i); refs.insert(cstack1.l); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 2
        // CHECKCAST [B; Stack: 2
        if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { cclasses_mtx[1].lock(); if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { if (jclass clazz = env->FindClass(((char *)(string_pool + 979LL)))) { cclasses[1] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[1].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (cstack1.l != nullptr && !env->IsInstanceOf(cstack1.l, (cclasses[1]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 979LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (jarray) 0; } } 
        // New stack: 2
        // ASTORE 1; Stack: 2
        clocal1.l = cstack1.l; refs.insert(cstack1.l);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // ICONST_2; Stack: 2
        cstack2.i = 2;
        // New stack: 3
        // AALOAD; Stack: 3
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 105LL)), -1); else { cstack1.l = env->GetObjectArrayElement((jobjectArray) cstack1.l, cstack2.i); refs.insert(cstack1.l); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 2
        // CHECKCAST java/lang/Long; Stack: 2
        if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { cclasses_mtx[2].lock(); if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[1]))) { cclasses[2] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[2].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (cstack1.l != nullptr && !env->IsInstanceOf(cstack1.l, (cclasses[2]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 161LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (jarray) 0; } } 
        // New stack: 2
        // INVOKEVIRTUAL java/lang/Long.longValue()J; Stack: 2
        if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { cclasses_mtx[2].lock(); if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[1]))) { cclasses[2] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[2].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (!cmethods[0]) { cmethods[0] = env->GetMethodID((cclasses[2]), ((char *)(string_pool + 176LL)), ((char *)(string_pool + 186LL))); if (env->ExceptionCheck()) { return (jarray) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 190LL)), -1); else cstack1.j = env->CallLongMethod(cstack1.l, (cmethods[0])); 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 3
        // LSTORE 2; Stack: 3
        clocal2.j = cstack1.j;
        // New stack: 1
        // POP; Stack: 1
        ;
        // New stack: 0
        // GETSTATIC dev/sakura/client/Mahiro_uT.a J; Stack: 0
        if (!cclasses[4]  || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (!cfields[0]) { cfields[0] = env->GetStaticFieldID((cclasses[4]), ((char *)(string_pool + 213LL)), ((char *)(string_pool + 215LL))); if (env->ExceptionCheck()) { return (jarray) 0; }  } cstack0.j = env->GetStaticLongField((cclasses[4]), (cfields[0])); 
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
        // LDC 996978841486231487; Stack: 0
        cstack0.j = 996978841486231487LL;
        // New stack: 2
        // LLOAD 2; Stack: 2
        cstack2.j = clocal2.j;
        // New stack: 4
        // LABEL L11; Stack: 4
        L11: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 4
        // ICONST_1; Stack: 4
        cstack4.i = 1;
        // New stack: 5
        // ANEWARRAY java/lang/Object; Stack: 5
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack4.i < 0) utils::throw_re(env, ((char *)(string_pool + 217LL)), ((char *)(string_pool + 254LL)), -1); else { cstack4.l = env->NewObjectArray(cstack4.i, (cclasses[5]), nullptr); refs.insert(cstack4.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 5
        // DUP; Stack: 5
        cstack5 = cstack4;
        // New stack: 6
        // LDC Ldev/sakura/client/Mahiro_uT;; Stack: 6
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack6.l = (cclasses[6]);
        // New stack: 7
        // SWAP; Stack: 7
        std::swap(cstack6, cstack5);
        // New stack: 7
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.11716030067971395.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 7
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack7.l = lookup;
        // New stack: 8
        // LDC Ldev/sakura/client/Mahiro_hd;; Stack: 8
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack8.l = (cclasses[7]);
        // New stack: 9
        // LDC a; Stack: 9
        cstack9.l = (cstrings[6]);
        // New stack: 10
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;; Stack: 10
        cstack10.l = (cstrings[7]);
        // New stack: 11
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.11716030067971395.a()Ljava/lang/ClassLoader;; Stack: 11
        cstack11.l = classloader;
        // New stack: 12
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 12
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[3]) { cmethods[3] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 279LL)), ((char *)(string_pool + 306LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack10.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[3]), cstack10.l, cstack11.l); refs.insert(cstack10.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 11
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 11
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 379LL)), ((char *)(string_pool + 390LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack7.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 488LL)), -1); else { cstack7.l = env->CallObjectMethod(cstack7.l, (cmethods[4]), cstack8.l, cstack9.l, cstack10.l); refs.insert(cstack7.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 8
        // SWAP; Stack: 8
        std::swap(cstack7, cstack6);
        // New stack: 8
        // LDC \u00c9; Stack: 8
        cstack8.l = (cstrings[10]);
        // New stack: 9
        // SWAP; Stack: 9
        std::swap(cstack8, cstack7);
        // New stack: 9
        // LDC (JJ)[Ldev/sakura/client/Mahiro_m;; Stack: 9
        cstack9.l = (cstrings[16]);
        // New stack: 10
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.11716030067971395.a()Ljava/lang/ClassLoader;; Stack: 10
        cstack10.l = classloader;
        // New stack: 11
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 11
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[3]) { cmethods[3] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 279LL)), ((char *)(string_pool + 306LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack9.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[3]), cstack9.l, cstack10.l); refs.insert(cstack9.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 10
        // SWAP; Stack: 10
        std::swap(cstack9, cstack8);
        // New stack: 10
        // LDC 0; Stack: 10
        cstack10.i = 0;
        // New stack: 11
        // ANEWARRAY java/lang/Object; Stack: 11
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack10.i < 0) utils::throw_re(env, ((char *)(string_pool + 217LL)), ((char *)(string_pool + 254LL)), -1); else { cstack10.l = env->NewObjectArray(cstack10.i, (cclasses[5]), nullptr); refs.insert(cstack10.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 11
        // CHECKCAST java/lang/Object; Stack: 11
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack10.l != nullptr && !env->IsInstanceOf(cstack10.l, (cclasses[5]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 513LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } 
        // New stack: 11
        // SWAP; Stack: 11
        std::swap(cstack10, cstack9);
        // New stack: 11
        // INVOKESTATIC native/magic/1/linkcallsite/obfuscator0.11716030067971395.a(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/invoke/MemberName;; Stack: 11
        cstack5.l = utils::link_call_site(env, cstack5.l, cstack6.l, cstack7.l, cstack8.l, cstack9.l, cstack10.l); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 6
        // POP; Stack: 6
        ;
        // New stack: 5
        // ICONST_0; Stack: 5
        cstack5.i = 0;
        // New stack: 6
        // AALOAD; Stack: 6
        if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 105LL)), -1); else { cstack4.l = env->GetObjectArrayElement((jobjectArray) cstack4.l, cstack5.i); refs.insert(cstack4.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 5
        // DUP; Stack: 5
        cstack5 = cstack4;
        // New stack: 6
        // INSTANCEOF java/lang/invoke/CallSite; Stack: 6
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack5.i = cstack5.l == nullptr ? false : env->IsInstanceOf(cstack5.l, (cclasses[10]));
        // New stack: 6
        // IFEQ L16; Stack: 6
        if (cstack5.i == 0) goto L16;
        // New stack: 5
        // INVOKEINTERFACE java/lang/invoke/CallSite.getTarget()Ljava/lang/invoke/MethodHandle;; Stack: 5
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[5]) { cmethods[5] = env->GetMethodID((cclasses[10]), ((char *)(string_pool + 530LL)), ((char *)(string_pool + 540LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 574LL)), -1); else { cstack4.l = env->CallObjectMethod(cstack4.l, (cmethods[5])); refs.insert(cstack4.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 5
        // LABEL L16; Stack: 5
        L16: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 5
        // FRAME FULL L: [[Ljava/lang/Object;, [B, 4, [B] S: [4, 4, java/lang/Object]; Stack: 5
        refs.erase(cstack4.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal4.l); 
        utils::clear_refs(env, refs);
        // New stack: 5
        // CHECKCAST java/lang/invoke/MethodHandle; Stack: 5
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack4.l != nullptr && !env->IsInstanceOf(cstack4.l, (cclasses[11]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 601LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } 
        // New stack: 5
        // GOTO L17; Stack: 5
        goto L17;
        // New stack: 5
        // LABEL L12; Stack: 5
        L12: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 5
        // FRAME SAME1 L: null S: [java/lang/Throwable]; Stack: 5
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal4.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // INSTANCEOF java/lang/BootstrapMethodError; Stack: 2
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } cstack1.i = cstack1.l == nullptr ? false : env->IsInstanceOf(cstack1.l, (cclasses[12]));
        // New stack: 2
        // IFNE L18; Stack: 2
        if (cstack1.i != 0) goto L18;
        // New stack: 1
        // NEW java/lang/BootstrapMethodError; Stack: 1
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (jobject obj = env->AllocObject((cclasses[12]))) { cstack1.l = obj; refs.insert(obj); } 
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
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (!cmethods[6]) { cmethods[6] = env->GetMethodID((cclasses[12]), ((char *)(string_pool + 631LL)), ((char *)(string_pool + 638LL))); if (env->ExceptionCheck()) { return (jarray) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 663LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[12]), (cmethods[6]), cstack2.l); 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // LABEL L18; Stack: 1
        L18: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/Object]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal4.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 686LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 0
        // LABEL L17; Stack: 0
        L17: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 0
        // FRAME FULL L: [[Ljava/lang/Object;, [B, 4, [B] S: [4, 4, java/lang/invoke/MethodHandle]; Stack: 0
        refs.erase(cstack4.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal4.l); 
        utils::clear_refs(env, refs);
        // New stack: 5
        // INVOKESTATIC native/magic/1/invoke/obfuscator0.11716030067971395.a(JJLjava/lang/invoke/MethodHandle;)[Ldev/sakura/client/Mahiro_m;; Stack: 5
        if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { cclasses_mtx[13].lock(); if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[13] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[13].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (!cmethods[9]) { cmethods[9] = env->GetStaticMethodID((cclasses[13]), ((char *)(string_pool + 6370LL)), ((char *)(string_pool + 6386LL))); if (env->ExceptionCheck()) { return (jarray) 0; }  } cstack0.l = env->CallStaticObjectMethod((cclasses[13]), (cmethods[9]), cstack0.j, cstack2.j, cstack4.l); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // CHECKCAST [Ldev/sakura/client/Mahiro_m;; Stack: 1
        if (!cclasses[14] || env->IsSameObject(cclasses[14], NULL)) { cclasses_mtx[14].lock(); if (!cclasses[14] || env->IsSameObject(cclasses[14], NULL)) { if (jclass clazz = env->FindClass(((char *)(string_pool + 11390LL)))) { cclasses[14] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[14].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (cstack0.l != nullptr && !env->IsInstanceOf(cstack0.l, (cclasses[14]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 11390LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (jarray) 0; } } 
        // New stack: 1
        // ASTORE 5; Stack: 1
        clocal5.l = cstack0.l; refs.insert(cstack0.l);
        // New stack: 0
        // LABEL L13; Stack: 0
        L13: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 0
        // SIPUSH 1681; Stack: 0
        cstack0.i = (jint) 1681;
        // New stack: 1
        // LDC 1245858330715069441; Stack: 1
        cstack1.j = 1245858330715069441LL;
        // New stack: 3
        // LLOAD 2; Stack: 3
        cstack3.j = clocal2.j;
        // New stack: 5
        // LXOR; Stack: 5
        cstack1.j = cstack1.j ^ cstack3.j;
        // New stack: 3
        // LABEL L9; Stack: 3
        L9: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 3
        // ICONST_1; Stack: 3
        cstack3.i = 1;
        // New stack: 4
        // ANEWARRAY java/lang/Object; Stack: 4
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } if (cstack3.i < 0) utils::throw_re(env, ((char *)(string_pool + 217LL)), ((char *)(string_pool + 254LL)), -1); else { cstack3.l = env->NewObjectArray(cstack3.i, (cclasses[5]), nullptr); refs.insert(cstack3.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 4
        // DUP; Stack: 4
        cstack4 = cstack3;
        // New stack: 5
        // LDC Ldev/sakura/client/Mahiro_uT;; Stack: 5
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } cstack5.l = (cclasses[6]);
        // New stack: 6
        // SWAP; Stack: 6
        std::swap(cstack5, cstack4);
        // New stack: 6
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.11716030067971395.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 6
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } cstack6.l = lookup;
        // New stack: 7
        // LDC Ldev/sakura/client/Mahiro_uT;; Stack: 7
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } cstack7.l = (cclasses[6]);
        // New stack: 8
        // LDC a; Stack: 8
        cstack8.l = (cstrings[6]);
        // New stack: 9
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;; Stack: 9
        cstack9.l = (cstrings[7]);
        // New stack: 10
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.11716030067971395.a()Ljava/lang/ClassLoader;; Stack: 10
        cstack10.l = classloader;
        // New stack: 11
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 11
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } if (!cmethods[3]) { cmethods[3] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 279LL)), ((char *)(string_pool + 306LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }  } cstack9.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[3]), cstack9.l, cstack10.l); refs.insert(cstack9.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 10
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 10
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 379LL)), ((char *)(string_pool + 390LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }  } if (cstack6.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 488LL)), -1); else { cstack6.l = env->CallObjectMethod(cstack6.l, (cmethods[4]), cstack7.l, cstack8.l, cstack9.l); refs.insert(cstack6.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 7
        // SWAP; Stack: 7
        std::swap(cstack6, cstack5);
        // New stack: 7
        // LDC i; Stack: 7
        cstack7.l = (cstrings[21]);
        // New stack: 8
        // SWAP; Stack: 8
        std::swap(cstack7, cstack6);
        // New stack: 8
        // LDC (IJ)Ljava/lang/String;; Stack: 8
        cstack8.l = (cstrings[22]);
        // New stack: 9
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.11716030067971395.a()Ljava/lang/ClassLoader;; Stack: 9
        cstack9.l = classloader;
        // New stack: 10
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 10
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } if (!cmethods[3]) { cmethods[3] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 279LL)), ((char *)(string_pool + 306LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }  } cstack8.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[3]), cstack8.l, cstack9.l); refs.insert(cstack8.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 9
        // SWAP; Stack: 9
        std::swap(cstack8, cstack7);
        // New stack: 9
        // LDC 0; Stack: 9
        cstack9.i = 0;
        // New stack: 10
        // ANEWARRAY java/lang/Object; Stack: 10
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } if (cstack9.i < 0) utils::throw_re(env, ((char *)(string_pool + 217LL)), ((char *)(string_pool + 254LL)), -1); else { cstack9.l = env->NewObjectArray(cstack9.i, (cclasses[5]), nullptr); refs.insert(cstack9.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 10
        // CHECKCAST java/lang/Object; Stack: 10
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } if (cstack9.l != nullptr && !env->IsInstanceOf(cstack9.l, (cclasses[5]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 513LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } 
        // New stack: 10
        // SWAP; Stack: 10
        std::swap(cstack9, cstack8);
        // New stack: 10
        // INVOKESTATIC native/magic/1/linkcallsite/obfuscator0.11716030067971395.a(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/invoke/MemberName;; Stack: 10
        cstack4.l = utils::link_call_site(env, cstack4.l, cstack5.l, cstack6.l, cstack7.l, cstack8.l, cstack9.l); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 5
        // POP; Stack: 5
        ;
        // New stack: 4
        // ICONST_0; Stack: 4
        cstack4.i = 0;
        // New stack: 5
        // AALOAD; Stack: 5
        if (cstack3.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 105LL)), -1); else { cstack3.l = env->GetObjectArrayElement((jobjectArray) cstack3.l, cstack4.i); refs.insert(cstack3.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 4
        // DUP; Stack: 4
        cstack4 = cstack3;
        // New stack: 5
        // INSTANCEOF java/lang/invoke/CallSite; Stack: 5
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } cstack4.i = cstack4.l == nullptr ? false : env->IsInstanceOf(cstack4.l, (cclasses[10]));
        // New stack: 5
        // IFEQ L19; Stack: 5
        if (cstack4.i == 0) goto L19;
        // New stack: 4
        // INVOKEINTERFACE java/lang/invoke/CallSite.getTarget()Ljava/lang/invoke/MethodHandle;; Stack: 4
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } if (!cmethods[5]) { cmethods[5] = env->GetMethodID((cclasses[10]), ((char *)(string_pool + 530LL)), ((char *)(string_pool + 540LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }  } if (cstack3.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 574LL)), -1); else { cstack3.l = env->CallObjectMethod(cstack3.l, (cmethods[5])); refs.insert(cstack3.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 4
        // LABEL L19; Stack: 4
        L19: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 4
        // FRAME FULL L: [[Ljava/lang/Object;, [B, 4, [B, [Ldev/sakura/client/Mahiro_m;] S: [1, 4, java/lang/Object]; Stack: 4
        refs.erase(cstack3.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal4.l); refs.erase(clocal5.l); 
        utils::clear_refs(env, refs);
        // New stack: 4
        // CHECKCAST java/lang/invoke/MethodHandle; Stack: 4
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } if (cstack3.l != nullptr && !env->IsInstanceOf(cstack3.l, (cclasses[11]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 601LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } 
        // New stack: 4
        // GOTO L20; Stack: 4
        goto L20;
        // New stack: 4
        // LABEL L10; Stack: 4
        L10: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 4
        // FRAME SAME1 L: null S: [java/lang/Throwable]; Stack: 4
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal4.l); refs.erase(clocal5.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // INSTANCEOF java/lang/BootstrapMethodError; Stack: 2
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } cstack1.i = cstack1.l == nullptr ? false : env->IsInstanceOf(cstack1.l, (cclasses[12]));
        // New stack: 2
        // IFNE L21; Stack: 2
        if (cstack1.i != 0) goto L21;
        // New stack: 1
        // NEW java/lang/BootstrapMethodError; Stack: 1
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (jobject obj = env->AllocObject((cclasses[12]))) { cstack1.l = obj; refs.insert(obj); } 
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
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (!cmethods[6]) { cmethods[6] = env->GetMethodID((cclasses[12]), ((char *)(string_pool + 631LL)), ((char *)(string_pool + 638LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 663LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[12]), (cmethods[6]), cstack2.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 1
        // LABEL L21; Stack: 1
        L21: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/Object]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal4.l); refs.erase(clocal5.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 686LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 0
        // LABEL L20; Stack: 0
        L20: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 0
        // FRAME FULL L: [[Ljava/lang/Object;, [B, 4, [B, [Ldev/sakura/client/Mahiro_m;] S: [1, 4, java/lang/invoke/MethodHandle]; Stack: 0
        refs.erase(cstack3.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal4.l); refs.erase(clocal5.l); 
        utils::clear_refs(env, refs);
        // New stack: 4
        // INVOKESTATIC native/magic/1/invoke/obfuscator0.11716030067971395.a(IJLjava/lang/invoke/MethodHandle;)Ljava/lang/String;; Stack: 4
        if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { cclasses_mtx[13].lock(); if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[13] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[13].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (!cmethods[11]) { cmethods[11] = env->GetStaticMethodID((cclasses[13]), ((char *)(string_pool + 697LL)), ((char *)(string_pool + 712LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }  } cstack0.l = env->CallStaticObjectMethod((cclasses[13]), (cmethods[11]), cstack0.i, cstack1.j, cstack3.l); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 1
        // CHECKCAST java/lang/String; Stack: 1
        if (!cclasses[16] || env->IsSameObject(cclasses[16], NULL)) { cclasses_mtx[16].lock(); if (!cclasses[16] || env->IsSameObject(cclasses[16], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[23]))) { cclasses[16] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[16].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (cstack0.l != nullptr && !env->IsInstanceOf(cstack0.l, (cclasses[16]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 766LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } 
        // New stack: 1
        // LDC 954393249112561887; Stack: 1
        cstack1.j = 954393249112561887LL;
        // New stack: 3
        // LLOAD 2; Stack: 3
        cstack3.j = clocal2.j;
        // New stack: 5
        // LABEL L7; Stack: 5
        L7: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 5
        // ICONST_1; Stack: 5
        cstack5.i = 1;
        // New stack: 6
        // ANEWARRAY java/lang/Object; Stack: 6
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } if (cstack5.i < 0) utils::throw_re(env, ((char *)(string_pool + 217LL)), ((char *)(string_pool + 254LL)), -1); else { cstack5.l = env->NewObjectArray(cstack5.i, (cclasses[5]), nullptr); refs.insert(cstack5.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 6
        // DUP; Stack: 6
        cstack6 = cstack5;
        // New stack: 7
        // LDC Ldev/sakura/client/Mahiro_uT;; Stack: 7
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } cstack7.l = (cclasses[6]);
        // New stack: 8
        // SWAP; Stack: 8
        std::swap(cstack7, cstack6);
        // New stack: 8
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.11716030067971395.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 8
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } cstack8.l = lookup;
        // New stack: 9
        // LDC Ldev/sakura/client/Mahiro_hd;; Stack: 9
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } cstack9.l = (cclasses[7]);
        // New stack: 10
        // LDC a; Stack: 10
        cstack10.l = (cstrings[6]);
        // New stack: 11
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;; Stack: 11
        cstack11.l = (cstrings[7]);
        // New stack: 12
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.11716030067971395.a()Ljava/lang/ClassLoader;; Stack: 12
        cstack12.l = classloader;
        // New stack: 13
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 13
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } if (!cmethods[3]) { cmethods[3] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 279LL)), ((char *)(string_pool + 306LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }  } cstack11.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[3]), cstack11.l, cstack12.l); refs.insert(cstack11.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 12
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 12
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 379LL)), ((char *)(string_pool + 390LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }  } if (cstack8.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 488LL)), -1); else { cstack8.l = env->CallObjectMethod(cstack8.l, (cmethods[4]), cstack9.l, cstack10.l, cstack11.l); refs.insert(cstack8.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 9
        // SWAP; Stack: 9
        std::swap(cstack8, cstack7);
        // New stack: 9
        // LDC \u00c9; Stack: 9
        cstack9.l = (cstrings[10]);
        // New stack: 10
        // SWAP; Stack: 10
        std::swap(cstack9, cstack8);
        // New stack: 10
        // LDC (Ljava/lang/Object;JJ)Ljavax/crypto/Mac;; Stack: 10
        cstack10.l = (cstrings[29]);
        // New stack: 11
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.11716030067971395.a()Ljava/lang/ClassLoader;; Stack: 11
        cstack11.l = classloader;
        // New stack: 12
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 12
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } if (!cmethods[3]) { cmethods[3] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 279LL)), ((char *)(string_pool + 306LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }  } cstack10.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[3]), cstack10.l, cstack11.l); refs.insert(cstack10.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 11
        // SWAP; Stack: 11
        std::swap(cstack10, cstack9);
        // New stack: 11
        // LDC 0; Stack: 11
        cstack11.i = 0;
        // New stack: 12
        // ANEWARRAY java/lang/Object; Stack: 12
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } if (cstack11.i < 0) utils::throw_re(env, ((char *)(string_pool + 217LL)), ((char *)(string_pool + 254LL)), -1); else { cstack11.l = env->NewObjectArray(cstack11.i, (cclasses[5]), nullptr); refs.insert(cstack11.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 12
        // CHECKCAST java/lang/Object; Stack: 12
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } if (cstack11.l != nullptr && !env->IsInstanceOf(cstack11.l, (cclasses[5]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 513LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } 
        // New stack: 12
        // SWAP; Stack: 12
        std::swap(cstack11, cstack10);
        // New stack: 12
        // INVOKESTATIC native/magic/1/linkcallsite/obfuscator0.11716030067971395.a(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/invoke/MemberName;; Stack: 12
        cstack6.l = utils::link_call_site(env, cstack6.l, cstack7.l, cstack8.l, cstack9.l, cstack10.l, cstack11.l); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 7
        // POP; Stack: 7
        ;
        // New stack: 6
        // ICONST_0; Stack: 6
        cstack6.i = 0;
        // New stack: 7
        // AALOAD; Stack: 7
        if (cstack5.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 105LL)), -1); else { cstack5.l = env->GetObjectArrayElement((jobjectArray) cstack5.l, cstack6.i); refs.insert(cstack5.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 6
        // DUP; Stack: 6
        cstack6 = cstack5;
        // New stack: 7
        // INSTANCEOF java/lang/invoke/CallSite; Stack: 7
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } cstack6.i = cstack6.l == nullptr ? false : env->IsInstanceOf(cstack6.l, (cclasses[10]));
        // New stack: 7
        // IFEQ L22; Stack: 7
        if (cstack6.i == 0) goto L22;
        // New stack: 6
        // INVOKEINTERFACE java/lang/invoke/CallSite.getTarget()Ljava/lang/invoke/MethodHandle;; Stack: 6
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } if (!cmethods[5]) { cmethods[5] = env->GetMethodID((cclasses[10]), ((char *)(string_pool + 530LL)), ((char *)(string_pool + 540LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }  } if (cstack5.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 574LL)), -1); else { cstack5.l = env->CallObjectMethod(cstack5.l, (cmethods[5])); refs.insert(cstack5.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 6
        // LABEL L22; Stack: 6
        L22: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 6
        // FRAME FULL L: [[Ljava/lang/Object;, [B, 4, [B, [Ldev/sakura/client/Mahiro_m;] S: [java/lang/String, 4, 4, java/lang/Object]; Stack: 6
        refs.erase(cstack0.l); refs.erase(cstack5.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal4.l); refs.erase(clocal5.l); 
        utils::clear_refs(env, refs);
        // New stack: 6
        // CHECKCAST java/lang/invoke/MethodHandle; Stack: 6
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } if (cstack5.l != nullptr && !env->IsInstanceOf(cstack5.l, (cclasses[11]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 601LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } 
        // New stack: 6
        // GOTO L23; Stack: 6
        goto L23;
        // New stack: 6
        // LABEL L8; Stack: 6
        L8: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 6
        // FRAME SAME1 L: null S: [java/lang/Throwable]; Stack: 6
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal4.l); refs.erase(clocal5.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // INSTANCEOF java/lang/BootstrapMethodError; Stack: 2
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } cstack1.i = cstack1.l == nullptr ? false : env->IsInstanceOf(cstack1.l, (cclasses[12]));
        // New stack: 2
        // IFNE L24; Stack: 2
        if (cstack1.i != 0) goto L24;
        // New stack: 1
        // NEW java/lang/BootstrapMethodError; Stack: 1
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (jobject obj = env->AllocObject((cclasses[12]))) { cstack1.l = obj; refs.insert(obj); } 
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
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (!cmethods[6]) { cmethods[6] = env->GetMethodID((cclasses[12]), ((char *)(string_pool + 631LL)), ((char *)(string_pool + 638LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 663LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[12]), (cmethods[6]), cstack2.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 1
        // LABEL L24; Stack: 1
        L24: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/Object]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal4.l); refs.erase(clocal5.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 686LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 0
        // LABEL L23; Stack: 0
        L23: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 0
        // FRAME FULL L: [[Ljava/lang/Object;, [B, 4, [B, [Ldev/sakura/client/Mahiro_m;] S: [java/lang/String, 4, 4, java/lang/invoke/MethodHandle]; Stack: 0
        refs.erase(cstack0.l); refs.erase(cstack5.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal4.l); refs.erase(clocal5.l); 
        utils::clear_refs(env, refs);
        // New stack: 6
        // INVOKESTATIC native/magic/1/invoke/obfuscator0.11716030067971395.a(Ljava/lang/Object;JJLjava/lang/invoke/MethodHandle;)Ljavax/crypto/Mac;; Stack: 6
        if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { cclasses_mtx[13].lock(); if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[13] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[13].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (!cmethods[7]) { cmethods[7] = env->GetStaticMethodID((cclasses[13]), ((char *)(string_pool + 783LL)), ((char *)(string_pool + 798LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }  } cstack0.l = env->CallStaticObjectMethod((cclasses[13]), (cmethods[7]), cstack0.l, cstack1.j, cstack3.j, cstack5.l); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 1
        // CHECKCAST javax/crypto/Mac; Stack: 1
        if (!cclasses[18] || env->IsSameObject(cclasses[18], NULL)) { cclasses_mtx[18].lock(); if (!cclasses[18] || env->IsSameObject(cclasses[18], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[30]))) { cclasses[18] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[18].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (cstack0.l != nullptr && !env->IsInstanceOf(cstack0.l, (cclasses[18]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 18840LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } 
        // New stack: 1
        // ASTORE 6; Stack: 1
        clocal6.l = cstack0.l; refs.insert(cstack0.l);
        // New stack: 0
        // ALOAD 6; Stack: 0
        cstack0.l = clocal6.l; refs.insert(cstack0.l);
        // New stack: 1
        // LABEL L25; Stack: 1
        L25: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 1
        // NEW javax/crypto/spec/SecretKeySpec; Stack: 1
        if (!cclasses[19] || env->IsSameObject(cclasses[19], NULL)) { cclasses_mtx[19].lock(); if (!cclasses[19] || env->IsSameObject(cclasses[19], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[31]))) { cclasses[19] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[19].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (jobject obj = env->AllocObject((cclasses[19]))) { cstack1.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 2
        // DUP; Stack: 2
        cstack2 = cstack1;
        // New stack: 3
        // ALOAD 4; Stack: 3
        cstack3.l = clocal4.l; refs.insert(cstack3.l);
        // New stack: 4
        // SIPUSH 21035; Stack: 4
        cstack4.i = (jint) 21035;
        // New stack: 5
        // LDC 2036401438240557241; Stack: 5
        cstack5.j = 2036401438240557241LL;
        // New stack: 7
        // LLOAD 2; Stack: 7
        cstack7.j = clocal2.j;
        // New stack: 9
        // LXOR; Stack: 9
        cstack5.j = cstack5.j ^ cstack7.j;
        // New stack: 7
        // LABEL L5; Stack: 7
        L5: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }
        // New stack: 7
        // ICONST_1; Stack: 7
        cstack7.i = 1;
        // New stack: 8
        // ANEWARRAY java/lang/Object; Stack: 8
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } if (cstack7.i < 0) utils::throw_re(env, ((char *)(string_pool + 217LL)), ((char *)(string_pool + 254LL)), -1); else { cstack7.l = env->NewObjectArray(cstack7.i, (cclasses[5]), nullptr); refs.insert(cstack7.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }
        // New stack: 8
        // DUP; Stack: 8
        cstack8 = cstack7;
        // New stack: 9
        // LDC Ldev/sakura/client/Mahiro_uT;; Stack: 9
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } cstack9.l = (cclasses[6]);
        // New stack: 10
        // SWAP; Stack: 10
        std::swap(cstack9, cstack8);
        // New stack: 10
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.11716030067971395.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 10
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } cstack10.l = lookup;
        // New stack: 11
        // LDC Ldev/sakura/client/Mahiro_uT;; Stack: 11
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } cstack11.l = (cclasses[6]);
        // New stack: 12
        // LDC a; Stack: 12
        cstack12.l = (cstrings[6]);
        // New stack: 13
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;; Stack: 13
        cstack13.l = (cstrings[7]);
        // New stack: 14
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.11716030067971395.a()Ljava/lang/ClassLoader;; Stack: 14
        cstack14.l = classloader;
        // New stack: 15
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 15
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } if (!cmethods[3]) { cmethods[3] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 279LL)), ((char *)(string_pool + 306LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }  } cstack13.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[3]), cstack13.l, cstack14.l); refs.insert(cstack13.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }
        // New stack: 14
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 14
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 379LL)), ((char *)(string_pool + 390LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }  } if (cstack10.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 488LL)), -1); else { cstack10.l = env->CallObjectMethod(cstack10.l, (cmethods[4]), cstack11.l, cstack12.l, cstack13.l); refs.insert(cstack10.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }
        // New stack: 11
        // SWAP; Stack: 11
        std::swap(cstack10, cstack9);
        // New stack: 11
        // LDC i; Stack: 11
        cstack11.l = (cstrings[21]);
        // New stack: 12
        // SWAP; Stack: 12
        std::swap(cstack11, cstack10);
        // New stack: 12
        // LDC (IJ)Ljava/lang/String;; Stack: 12
        cstack12.l = (cstrings[22]);
        // New stack: 13
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.11716030067971395.a()Ljava/lang/ClassLoader;; Stack: 13
        cstack13.l = classloader;
        // New stack: 14
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 14
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } if (!cmethods[3]) { cmethods[3] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 279LL)), ((char *)(string_pool + 306LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }  } cstack12.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[3]), cstack12.l, cstack13.l); refs.insert(cstack12.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }
        // New stack: 13
        // SWAP; Stack: 13
        std::swap(cstack12, cstack11);
        // New stack: 13
        // LDC 0; Stack: 13
        cstack13.i = 0;
        // New stack: 14
        // ANEWARRAY java/lang/Object; Stack: 14
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } if (cstack13.i < 0) utils::throw_re(env, ((char *)(string_pool + 217LL)), ((char *)(string_pool + 254LL)), -1); else { cstack13.l = env->NewObjectArray(cstack13.i, (cclasses[5]), nullptr); refs.insert(cstack13.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }
        // New stack: 14
        // CHECKCAST java/lang/Object; Stack: 14
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } if (cstack13.l != nullptr && !env->IsInstanceOf(cstack13.l, (cclasses[5]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 513LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } 
        // New stack: 14
        // SWAP; Stack: 14
        std::swap(cstack13, cstack12);
        // New stack: 14
        // INVOKESTATIC native/magic/1/linkcallsite/obfuscator0.11716030067971395.a(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/invoke/MemberName;; Stack: 14
        cstack8.l = utils::link_call_site(env, cstack8.l, cstack9.l, cstack10.l, cstack11.l, cstack12.l, cstack13.l); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }
        // New stack: 9
        // POP; Stack: 9
        ;
        // New stack: 8
        // ICONST_0; Stack: 8
        cstack8.i = 0;
        // New stack: 9
        // AALOAD; Stack: 9
        if (cstack7.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 105LL)), -1); else { cstack7.l = env->GetObjectArrayElement((jobjectArray) cstack7.l, cstack8.i); refs.insert(cstack7.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }
        // New stack: 8
        // DUP; Stack: 8
        cstack8 = cstack7;
        // New stack: 9
        // INSTANCEOF java/lang/invoke/CallSite; Stack: 9
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } cstack8.i = cstack8.l == nullptr ? false : env->IsInstanceOf(cstack8.l, (cclasses[10]));
        // New stack: 9
        // IFEQ L26; Stack: 9
        if (cstack8.i == 0) goto L26;
        // New stack: 8
        // INVOKEINTERFACE java/lang/invoke/CallSite.getTarget()Ljava/lang/invoke/MethodHandle;; Stack: 8
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } if (!cmethods[5]) { cmethods[5] = env->GetMethodID((cclasses[10]), ((char *)(string_pool + 530LL)), ((char *)(string_pool + 540LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }  } if (cstack7.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 574LL)), -1); else { cstack7.l = env->CallObjectMethod(cstack7.l, (cmethods[5])); refs.insert(cstack7.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }
        // New stack: 8
        // LABEL L26; Stack: 8
        L26: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }
        // New stack: 8
        // FRAME FULL L: [[Ljava/lang/Object;, [B, 4, [B, [Ldev/sakura/client/Mahiro_m;, javax/crypto/Mac] S: [javax/crypto/Mac, org.objectweb.asm.tree.LabelNode@b8e246c, org.objectweb.asm.tree.LabelNode@b8e246c, [B, 1, 4, java/lang/Object]; Stack: 8
        refs.erase(cstack0.l); refs.erase(cstack1.l); refs.erase(cstack2.l); refs.erase(cstack3.l); refs.erase(cstack7.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal4.l); refs.erase(clocal5.l); refs.erase(clocal6.l); 
        utils::clear_refs(env, refs);
        // New stack: 8
        // CHECKCAST java/lang/invoke/MethodHandle; Stack: 8
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } if (cstack7.l != nullptr && !env->IsInstanceOf(cstack7.l, (cclasses[11]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 601LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } 
        // New stack: 8
        // GOTO L27; Stack: 8
        goto L27;
        // New stack: 8
        // LABEL L6; Stack: 8
        L6: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 8
        // FRAME SAME1 L: null S: [java/lang/Throwable]; Stack: 8
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal4.l); refs.erase(clocal5.l); refs.erase(clocal6.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // INSTANCEOF java/lang/BootstrapMethodError; Stack: 2
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } cstack1.i = cstack1.l == nullptr ? false : env->IsInstanceOf(cstack1.l, (cclasses[12]));
        // New stack: 2
        // IFNE L28; Stack: 2
        if (cstack1.i != 0) goto L28;
        // New stack: 1
        // NEW java/lang/BootstrapMethodError; Stack: 1
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (jobject obj = env->AllocObject((cclasses[12]))) { cstack1.l = obj; refs.insert(obj); } 
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
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (!cmethods[6]) { cmethods[6] = env->GetMethodID((cclasses[12]), ((char *)(string_pool + 631LL)), ((char *)(string_pool + 638LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 663LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[12]), (cmethods[6]), cstack2.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 1
        // LABEL L28; Stack: 1
        L28: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/Object]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal4.l); refs.erase(clocal5.l); refs.erase(clocal6.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 686LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 0
        // LABEL L27; Stack: 0
        L27: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 0
        // FRAME FULL L: [[Ljava/lang/Object;, [B, 4, [B, [Ldev/sakura/client/Mahiro_m;, javax/crypto/Mac] S: [javax/crypto/Mac, org.objectweb.asm.tree.LabelNode@b8e246c, org.objectweb.asm.tree.LabelNode@b8e246c, [B, 1, 4, java/lang/invoke/MethodHandle]; Stack: 0
        refs.erase(cstack0.l); refs.erase(cstack1.l); refs.erase(cstack2.l); refs.erase(cstack3.l); refs.erase(cstack7.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal4.l); refs.erase(clocal5.l); refs.erase(clocal6.l); 
        utils::clear_refs(env, refs);
        // New stack: 8
        // INVOKESTATIC native/magic/1/invoke/obfuscator0.11716030067971395.a(IJLjava/lang/invoke/MethodHandle;)Ljava/lang/String;; Stack: 8
        if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { cclasses_mtx[13].lock(); if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[13] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[13].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (!cmethods[11]) { cmethods[11] = env->GetStaticMethodID((cclasses[13]), ((char *)(string_pool + 697LL)), ((char *)(string_pool + 712LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }  } cstack4.l = env->CallStaticObjectMethod((cclasses[13]), (cmethods[11]), cstack4.i, cstack5.j, cstack7.l); refs.insert(cstack4.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 5
        // CHECKCAST java/lang/String; Stack: 5
        if (!cclasses[16] || env->IsSameObject(cclasses[16], NULL)) { cclasses_mtx[16].lock(); if (!cclasses[16] || env->IsSameObject(cclasses[16], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[23]))) { cclasses[16] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[16].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (cstack4.l != nullptr && !env->IsInstanceOf(cstack4.l, (cclasses[16]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 766LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } 
        // New stack: 5
        // INVOKESPECIAL javax/crypto/spec/SecretKeySpec.<init>([BLjava/lang/String;)V; Stack: 5
        if (!cclasses[19] || env->IsSameObject(cclasses[19], NULL)) { cclasses_mtx[19].lock(); if (!cclasses[19] || env->IsSameObject(cclasses[19], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[31]))) { cclasses[19] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[19].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (!cmethods[17]) { cmethods[17] = env->GetMethodID((cclasses[19]), ((char *)(string_pool + 631LL)), ((char *)(string_pool + 18857LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }  } if (cstack2.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 663LL)), -1); else env->CallNonvirtualVoidMethod(cstack2.l, (cclasses[19]), (cmethods[17]), cstack3.l, cstack4.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 2
        // LDC 999753292884590537; Stack: 2
        cstack2.j = 999753292884590537LL;
        // New stack: 4
        // LLOAD 2; Stack: 4
        cstack4.j = clocal2.j;
        // New stack: 6
        // LABEL L3; Stack: 6
        L3: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }
        // New stack: 6
        // ICONST_1; Stack: 6
        cstack6.i = 1;
        // New stack: 7
        // ANEWARRAY java/lang/Object; Stack: 7
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } if (cstack6.i < 0) utils::throw_re(env, ((char *)(string_pool + 217LL)), ((char *)(string_pool + 254LL)), -1); else { cstack6.l = env->NewObjectArray(cstack6.i, (cclasses[5]), nullptr); refs.insert(cstack6.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }
        // New stack: 7
        // DUP; Stack: 7
        cstack7 = cstack6;
        // New stack: 8
        // LDC Ldev/sakura/client/Mahiro_uT;; Stack: 8
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } cstack8.l = (cclasses[6]);
        // New stack: 9
        // SWAP; Stack: 9
        std::swap(cstack8, cstack7);
        // New stack: 9
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.11716030067971395.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 9
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } cstack9.l = lookup;
        // New stack: 10
        // LDC Ldev/sakura/client/Mahiro_hd;; Stack: 10
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } cstack10.l = (cclasses[7]);
        // New stack: 11
        // LDC a; Stack: 11
        cstack11.l = (cstrings[6]);
        // New stack: 12
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;; Stack: 12
        cstack12.l = (cstrings[7]);
        // New stack: 13
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.11716030067971395.a()Ljava/lang/ClassLoader;; Stack: 13
        cstack13.l = classloader;
        // New stack: 14
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 14
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } if (!cmethods[3]) { cmethods[3] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 279LL)), ((char *)(string_pool + 306LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }  } cstack12.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[3]), cstack12.l, cstack13.l); refs.insert(cstack12.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }
        // New stack: 13
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 13
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 379LL)), ((char *)(string_pool + 390LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }  } if (cstack9.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 488LL)), -1); else { cstack9.l = env->CallObjectMethod(cstack9.l, (cmethods[4]), cstack10.l, cstack11.l, cstack12.l); refs.insert(cstack9.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }
        // New stack: 10
        // SWAP; Stack: 10
        std::swap(cstack9, cstack8);
        // New stack: 10
        // LDC \u00ce; Stack: 10
        cstack10.l = (cstrings[32]);
        // New stack: 11
        // SWAP; Stack: 11
        std::swap(cstack10, cstack9);
        // New stack: 11
        // LDC (Ljava/lang/Object;Ljava/lang/Object;JJ)V; Stack: 11
        cstack11.l = (cstrings[33]);
        // New stack: 12
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.11716030067971395.a()Ljava/lang/ClassLoader;; Stack: 12
        cstack12.l = classloader;
        // New stack: 13
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 13
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } if (!cmethods[3]) { cmethods[3] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 279LL)), ((char *)(string_pool + 306LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }  } cstack11.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[3]), cstack11.l, cstack12.l); refs.insert(cstack11.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }
        // New stack: 12
        // SWAP; Stack: 12
        std::swap(cstack11, cstack10);
        // New stack: 12
        // LDC 0; Stack: 12
        cstack12.i = 0;
        // New stack: 13
        // ANEWARRAY java/lang/Object; Stack: 13
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } if (cstack12.i < 0) utils::throw_re(env, ((char *)(string_pool + 217LL)), ((char *)(string_pool + 254LL)), -1); else { cstack12.l = env->NewObjectArray(cstack12.i, (cclasses[5]), nullptr); refs.insert(cstack12.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }
        // New stack: 13
        // CHECKCAST java/lang/Object; Stack: 13
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } if (cstack12.l != nullptr && !env->IsInstanceOf(cstack12.l, (cclasses[5]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 513LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } 
        // New stack: 13
        // SWAP; Stack: 13
        std::swap(cstack12, cstack11);
        // New stack: 13
        // INVOKESTATIC native/magic/1/linkcallsite/obfuscator0.11716030067971395.a(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/invoke/MemberName;; Stack: 13
        cstack7.l = utils::link_call_site(env, cstack7.l, cstack8.l, cstack9.l, cstack10.l, cstack11.l, cstack12.l); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }
        // New stack: 8
        // POP; Stack: 8
        ;
        // New stack: 7
        // ICONST_0; Stack: 7
        cstack7.i = 0;
        // New stack: 8
        // AALOAD; Stack: 8
        if (cstack6.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 105LL)), -1); else { cstack6.l = env->GetObjectArrayElement((jobjectArray) cstack6.l, cstack7.i); refs.insert(cstack6.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }
        // New stack: 7
        // DUP; Stack: 7
        cstack7 = cstack6;
        // New stack: 8
        // INSTANCEOF java/lang/invoke/CallSite; Stack: 8
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } cstack7.i = cstack7.l == nullptr ? false : env->IsInstanceOf(cstack7.l, (cclasses[10]));
        // New stack: 8
        // IFEQ L29; Stack: 8
        if (cstack7.i == 0) goto L29;
        // New stack: 7
        // INVOKEINTERFACE java/lang/invoke/CallSite.getTarget()Ljava/lang/invoke/MethodHandle;; Stack: 7
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } if (!cmethods[5]) { cmethods[5] = env->GetMethodID((cclasses[10]), ((char *)(string_pool + 530LL)), ((char *)(string_pool + 540LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }  } if (cstack6.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 574LL)), -1); else { cstack6.l = env->CallObjectMethod(cstack6.l, (cmethods[5])); refs.insert(cstack6.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }
        // New stack: 7
        // LABEL L29; Stack: 7
        L29: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }
        // New stack: 7
        // FRAME FULL L: [[Ljava/lang/Object;, [B, 4, [B, [Ldev/sakura/client/Mahiro_m;, javax/crypto/Mac] S: [javax/crypto/Mac, javax/crypto/spec/SecretKeySpec, 4, 4, java/lang/Object]; Stack: 7
        refs.erase(cstack0.l); refs.erase(cstack1.l); refs.erase(cstack6.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal4.l); refs.erase(clocal5.l); refs.erase(clocal6.l); 
        utils::clear_refs(env, refs);
        // New stack: 7
        // CHECKCAST java/lang/invoke/MethodHandle; Stack: 7
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } if (cstack6.l != nullptr && !env->IsInstanceOf(cstack6.l, (cclasses[11]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 601LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } 
        // New stack: 7
        // GOTO L30; Stack: 7
        goto L30;
        // New stack: 7
        // LABEL L4; Stack: 7
        L4: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 7
        // FRAME SAME1 L: null S: [java/lang/Throwable]; Stack: 7
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal4.l); refs.erase(clocal5.l); refs.erase(clocal6.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // INSTANCEOF java/lang/BootstrapMethodError; Stack: 2
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } cstack1.i = cstack1.l == nullptr ? false : env->IsInstanceOf(cstack1.l, (cclasses[12]));
        // New stack: 2
        // IFNE L31; Stack: 2
        if (cstack1.i != 0) goto L31;
        // New stack: 1
        // NEW java/lang/BootstrapMethodError; Stack: 1
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (jobject obj = env->AllocObject((cclasses[12]))) { cstack1.l = obj; refs.insert(obj); } 
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
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (!cmethods[6]) { cmethods[6] = env->GetMethodID((cclasses[12]), ((char *)(string_pool + 631LL)), ((char *)(string_pool + 638LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 663LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[12]), (cmethods[6]), cstack2.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 1
        // LABEL L31; Stack: 1
        L31: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/Object]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal4.l); refs.erase(clocal5.l); refs.erase(clocal6.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 686LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 0
        // LABEL L30; Stack: 0
        L30: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 0
        // FRAME FULL L: [[Ljava/lang/Object;, [B, 4, [B, [Ldev/sakura/client/Mahiro_m;, javax/crypto/Mac] S: [javax/crypto/Mac, javax/crypto/spec/SecretKeySpec, 4, 4, java/lang/invoke/MethodHandle]; Stack: 0
        refs.erase(cstack0.l); refs.erase(cstack1.l); refs.erase(cstack6.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal4.l); refs.erase(clocal5.l); refs.erase(clocal6.l); 
        utils::clear_refs(env, refs);
        // New stack: 7
        // INVOKESTATIC native/magic/1/invoke/obfuscator0.11716030067971395.a(Ljava/lang/Object;Ljava/lang/Object;JJLjava/lang/invoke/MethodHandle;)V; Stack: 7
        if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { cclasses_mtx[13].lock(); if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[13] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[13].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (!cmethods[18]) { cmethods[18] = env->GetStaticMethodID((cclasses[13]), ((char *)(string_pool + 1226LL)), ((char *)(string_pool + 1241LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }  } env->CallStaticVoidMethod((cclasses[13]), (cmethods[18]), cstack0.l, cstack1.l, cstack2.j, cstack4.j, cstack6.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 0
        // ALOAD 6; Stack: 0
        cstack0.l = clocal6.l; refs.insert(cstack0.l);
        // New stack: 1
        // ALOAD 1; Stack: 1
        cstack1.l = clocal1.l; refs.insert(cstack1.l);
        // New stack: 2
        // ALOAD 5; Stack: 2
        cstack2.l = clocal5.l; refs.insert(cstack2.l);
        // New stack: 3
        // IFNULL L32; Stack: 3
        if (env->IsSameObject(cstack2.l, nullptr)) goto L32;
        // New stack: 2
        // IFNONNULL L33; Stack: 2
        if (!env->IsSameObject(cstack1.l, nullptr)) goto L33;
        // New stack: 1
        // ICONST_0; Stack: 1
        cstack1.i = 0;
        // New stack: 2
        // NEWARRAY 8; Stack: 2
        if (cstack1.i < 0) utils::throw_re(env, ((char *)(string_pool + 217LL)), ((char *)(string_pool + 1834LL)), -1); else { cstack1.l = env->NewByteArray(cstack1.i); refs.insert(cstack1.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 2
        // LABEL L32; Stack: 2
        L32: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 2
        // FRAME FULL L: [[Ljava/lang/Object;, [B, 4, [B, [Ldev/sakura/client/Mahiro_m;, javax/crypto/Mac] S: [javax/crypto/Mac, [B]; Stack: 2
        refs.erase(cstack0.l); refs.erase(cstack1.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal4.l); refs.erase(clocal5.l); refs.erase(clocal6.l); 
        utils::clear_refs(env, refs);
        // New stack: 2
        // GOTO L34; Stack: 2
        goto L34;
        // New stack: 2
        // LABEL L33; Stack: 2
        L33: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 2
        // FRAME SAME1 L: null S: [javax/crypto/Mac]; Stack: 2
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal4.l); refs.erase(clocal5.l); refs.erase(clocal6.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ALOAD 1; Stack: 1
        cstack1.l = clocal1.l; refs.insert(cstack1.l);
        // New stack: 2
        // LABEL L34; Stack: 2
        L34: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 2
        // FRAME FULL L: [[Ljava/lang/Object;, [B, 4, [B, [Ldev/sakura/client/Mahiro_m;, javax/crypto/Mac] S: [javax/crypto/Mac, [B]; Stack: 2
        refs.erase(cstack0.l); refs.erase(cstack1.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal4.l); refs.erase(clocal5.l); refs.erase(clocal6.l); 
        utils::clear_refs(env, refs);
        // New stack: 2
        // LDC 993206651202030429; Stack: 2
        cstack2.j = 993206651202030429LL;
        // New stack: 4
        // LLOAD 2; Stack: 4
        cstack4.j = clocal2.j;
        // New stack: 6
        // LABEL L1; Stack: 6
        L1: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; }
        // New stack: 6
        // ICONST_1; Stack: 6
        cstack6.i = 1;
        // New stack: 7
        // ANEWARRAY java/lang/Object; Stack: 7
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; } } if (cstack6.i < 0) utils::throw_re(env, ((char *)(string_pool + 217LL)), ((char *)(string_pool + 254LL)), -1); else { cstack6.l = env->NewObjectArray(cstack6.i, (cclasses[5]), nullptr); refs.insert(cstack6.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; }
        // New stack: 7
        // DUP; Stack: 7
        cstack7 = cstack6;
        // New stack: 8
        // LDC Ldev/sakura/client/Mahiro_uT;; Stack: 8
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; } } cstack8.l = (cclasses[6]);
        // New stack: 9
        // SWAP; Stack: 9
        std::swap(cstack8, cstack7);
        // New stack: 9
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.11716030067971395.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 9
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; } } cstack9.l = lookup;
        // New stack: 10
        // LDC Ldev/sakura/client/Mahiro_hd;; Stack: 10
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; } } cstack10.l = (cclasses[7]);
        // New stack: 11
        // LDC a; Stack: 11
        cstack11.l = (cstrings[6]);
        // New stack: 12
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;; Stack: 12
        cstack12.l = (cstrings[7]);
        // New stack: 13
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.11716030067971395.a()Ljava/lang/ClassLoader;; Stack: 13
        cstack13.l = classloader;
        // New stack: 14
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 14
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; } } if (!cmethods[3]) { cmethods[3] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 279LL)), ((char *)(string_pool + 306LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; }  } cstack12.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[3]), cstack12.l, cstack13.l); refs.insert(cstack12.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; }
        // New stack: 13
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 13
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 379LL)), ((char *)(string_pool + 390LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; }  } if (cstack9.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 488LL)), -1); else { cstack9.l = env->CallObjectMethod(cstack9.l, (cmethods[4]), cstack10.l, cstack11.l, cstack12.l); refs.insert(cstack9.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; }
        // New stack: 10
        // SWAP; Stack: 10
        std::swap(cstack9, cstack8);
        // New stack: 10
        // LDC \u00ce; Stack: 10
        cstack10.l = (cstrings[32]);
        // New stack: 11
        // SWAP; Stack: 11
        std::swap(cstack10, cstack9);
        // New stack: 11
        // LDC (Ljava/lang/Object;Ljava/lang/Object;JJ)[B; Stack: 11
        cstack11.l = (cstrings[34]);
        // New stack: 12
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.11716030067971395.a()Ljava/lang/ClassLoader;; Stack: 12
        cstack12.l = classloader;
        // New stack: 13
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 13
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; } } if (!cmethods[3]) { cmethods[3] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 279LL)), ((char *)(string_pool + 306LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; }  } cstack11.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[3]), cstack11.l, cstack12.l); refs.insert(cstack11.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; }
        // New stack: 12
        // SWAP; Stack: 12
        std::swap(cstack11, cstack10);
        // New stack: 12
        // LDC 0; Stack: 12
        cstack12.i = 0;
        // New stack: 13
        // ANEWARRAY java/lang/Object; Stack: 13
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; } } if (cstack12.i < 0) utils::throw_re(env, ((char *)(string_pool + 217LL)), ((char *)(string_pool + 254LL)), -1); else { cstack12.l = env->NewObjectArray(cstack12.i, (cclasses[5]), nullptr); refs.insert(cstack12.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; }
        // New stack: 13
        // CHECKCAST java/lang/Object; Stack: 13
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; } } if (cstack12.l != nullptr && !env->IsInstanceOf(cstack12.l, (cclasses[5]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 513LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; } } 
        // New stack: 13
        // SWAP; Stack: 13
        std::swap(cstack12, cstack11);
        // New stack: 13
        // INVOKESTATIC native/magic/1/linkcallsite/obfuscator0.11716030067971395.a(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/invoke/MemberName;; Stack: 13
        cstack7.l = utils::link_call_site(env, cstack7.l, cstack8.l, cstack9.l, cstack10.l, cstack11.l, cstack12.l); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; }
        // New stack: 8
        // POP; Stack: 8
        ;
        // New stack: 7
        // ICONST_0; Stack: 7
        cstack7.i = 0;
        // New stack: 8
        // AALOAD; Stack: 8
        if (cstack6.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 105LL)), -1); else { cstack6.l = env->GetObjectArrayElement((jobjectArray) cstack6.l, cstack7.i); refs.insert(cstack6.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; }
        // New stack: 7
        // DUP; Stack: 7
        cstack7 = cstack6;
        // New stack: 8
        // INSTANCEOF java/lang/invoke/CallSite; Stack: 8
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; } } cstack7.i = cstack7.l == nullptr ? false : env->IsInstanceOf(cstack7.l, (cclasses[10]));
        // New stack: 8
        // IFEQ L35; Stack: 8
        if (cstack7.i == 0) goto L35;
        // New stack: 7
        // INVOKEINTERFACE java/lang/invoke/CallSite.getTarget()Ljava/lang/invoke/MethodHandle;; Stack: 7
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[12]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; } } if (!cmethods[5]) { cmethods[5] = env->GetMethodID((cclasses[10]), ((char *)(string_pool + 530LL)), ((char *)(string_pool + 540LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; }  } if (cstack6.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 574LL)), -1); else { cstack6.l = env->CallObjectMethod(cstack6.l, (cmethods[5])); refs.insert(cstack6.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; }
        // New stack: 7
        // LABEL L35; Stack: 7
        L35: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; }
        // New stack: 7
        // FRAME FULL L: [[Ljava/lang/Object;, [B, 4, [B, [Ldev/sakura/client/Mahiro_m;, javax/crypto/Mac] S: [javax/crypto/Mac, [B, 4, 4, java/lang/Object]; Stack: 7
        refs.erase(cstack0.l); refs.erase(cstack1.l); refs.erase(cstack6.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal4.l); refs.erase(clocal5.l); refs.erase(clocal6.l); 
        utils::clear_refs(env, refs);
        // New stack: 7
        // CHECKCAST java/lang/invoke/MethodHandle; Stack: 7
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; } } if (cstack6.l != nullptr && !env->IsInstanceOf(cstack6.l, (cclasses[11]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 601LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; } } 
        // New stack: 7
        // GOTO L36; Stack: 7
        goto L36;
        // New stack: 7
        // LABEL L2; Stack: 7
        L2: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 7
        // FRAME SAME1 L: null S: [java/lang/Throwable]; Stack: 7
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal4.l); refs.erase(clocal5.l); refs.erase(clocal6.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // INSTANCEOF java/lang/BootstrapMethodError; Stack: 2
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } cstack1.i = cstack1.l == nullptr ? false : env->IsInstanceOf(cstack1.l, (cclasses[12]));
        // New stack: 2
        // IFNE L37; Stack: 2
        if (cstack1.i != 0) goto L37;
        // New stack: 1
        // NEW java/lang/BootstrapMethodError; Stack: 1
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (jobject obj = env->AllocObject((cclasses[12]))) { cstack1.l = obj; refs.insert(obj); } 
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
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (!cmethods[6]) { cmethods[6] = env->GetMethodID((cclasses[12]), ((char *)(string_pool + 631LL)), ((char *)(string_pool + 638LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 663LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[12]), (cmethods[6]), cstack2.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 1
        // LABEL L37; Stack: 1
        L37: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/Object]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal4.l); refs.erase(clocal5.l); refs.erase(clocal6.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 686LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 0
        // LABEL L36; Stack: 0
        L36: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 0
        // FRAME FULL L: [[Ljava/lang/Object;, [B, 4, [B, [Ldev/sakura/client/Mahiro_m;, javax/crypto/Mac] S: [javax/crypto/Mac, [B, 4, 4, java/lang/invoke/MethodHandle]; Stack: 0
        refs.erase(cstack0.l); refs.erase(cstack1.l); refs.erase(cstack6.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal4.l); refs.erase(clocal5.l); refs.erase(clocal6.l); 
        utils::clear_refs(env, refs);
        // New stack: 7
        // INVOKESTATIC native/magic/1/invoke/obfuscator0.11716030067971395.a(Ljava/lang/Object;Ljava/lang/Object;JJLjava/lang/invoke/MethodHandle;)[B; Stack: 7
        if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { cclasses_mtx[13].lock(); if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[13] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[13].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (!cmethods[19]) { cmethods[19] = env->GetStaticMethodID((cclasses[13]), ((char *)(string_pool + 1013LL)), ((char *)(string_pool + 1028LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }  } cstack0.l = env->CallStaticObjectMethod((cclasses[13]), (cmethods[19]), cstack0.l, cstack1.l, cstack2.j, cstack4.j, cstack6.l); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 1
        // CHECKCAST [B; Stack: 1
        if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { cclasses_mtx[1].lock(); if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { if (jclass clazz = env->FindClass(((char *)(string_pool + 979LL)))) { cclasses[1] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[1].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (cstack0.l != nullptr && !env->IsInstanceOf(cstack0.l, (cclasses[1]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 979LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } 
        // New stack: 1
        // LABEL L14; Stack: 1
        L14: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // ARETURN; Stack: 1
        return (jarray) cstack0.l;
        // New stack: 0
        // LABEL L15; Stack: 0
        L15: if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 0
        // FRAME FULL L: [[Ljava/lang/Object;, [B, 4, [B, [Ldev/sakura/client/Mahiro_m;] S: [java/lang/Exception]; Stack: 0
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal4.l); refs.erase(clocal5.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ASTORE 6; Stack: 1
        clocal6.l = cstack0.l; refs.insert(cstack0.l);
        // New stack: 0
        // NEW java/lang/IllegalStateException; Stack: 0
        if (!cclasses[20] || env->IsSameObject(cclasses[20], NULL)) { cclasses_mtx[20].lock(); if (!cclasses[20] || env->IsSameObject(cclasses[20], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[35]))) { cclasses[20] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[20].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (jobject obj = env->AllocObject((cclasses[20]))) { cstack0.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // ALOAD 6; Stack: 2
        cstack2.l = clocal6.l; refs.insert(cstack2.l);
        // New stack: 3
        // INVOKESPECIAL java/lang/IllegalStateException.<init>(Ljava/lang/Throwable;)V; Stack: 3
        if (!cclasses[20] || env->IsSameObject(cclasses[20], NULL)) { cclasses_mtx[20].lock(); if (!cclasses[20] || env->IsSameObject(cclasses[20], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[35]))) { cclasses[20] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[20].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (!cmethods[20]) { cmethods[20] = env->GetMethodID((cclasses[20]), ((char *)(string_pool + 631LL)), ((char *)(string_pool + 638LL))); if (env->ExceptionCheck()) { return (jarray) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 663LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[20]), (cmethods[20]), cstack2.l); 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 686LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 0
        return (jarray) 0;
        L_CATCH_1: if (env->IsInstanceOf(cstack0.l, (cclasses[17]))) { goto L15; }
        env->Throw((jthrowable) cstack0.l); return (jarray) 0;
        L_CATCH_4: if (env->IsInstanceOf(cstack0.l, (cclasses[0]))) { goto L6; }
        goto L_CATCH_1;
        L_CATCH_0: if (env->IsInstanceOf(cstack0.l, (cclasses[0]))) { goto L12; }
        env->Throw((jthrowable) cstack0.l); return (jarray) 0;
        L_CATCH_6: if (env->IsInstanceOf(cstack0.l, (cclasses[0]))) { goto L2; }
        goto L_CATCH_1;
        L_CATCH_3: if (env->IsInstanceOf(cstack0.l, (cclasses[0]))) { goto L8; }
        goto L_CATCH_1;
        L_CATCH_2: if (env->IsInstanceOf(cstack0.l, (cclasses[0]))) { goto L10; }
        goto L_CATCH_1;
        L_CATCH_5: if (env->IsInstanceOf(cstack0.l, (cclasses[0]))) { goto L4; }
        goto L_CATCH_1;
    }
    
    // <clinit>()V
    void JNICALL __ngen_special_clinit_10_5(JNIEnv *env, jobject ignored_hidden, jclass clazz) {
        env->DeleteLocalRef(ignored_hidden);
        jobject classloader = utils::get_classloader_from_class(env, clazz);
        if (env->ExceptionCheck()) { return (void) 0; }
        if (classloader == nullptr) { env->FatalError(((char *)(string_pool + 54LL))); return (void) 0; }
    
        jobject lookup = nullptr;
        jvalue cstack0 = {}, cstack1 = {}, cstack2 = {}, cstack3 = {}, cstack4 = {}, cstack5 = {}, cstack6 = {}, cstack7 = {}, cstack8 = {}, cstack9 = {};
        jvalue clocal0 = {}, clocal1 = {}, clocal2 = {}, clocal3 = {}, clocal4 = {}, clocal5 = {}, clocal6 = {}, clocal7 = {}, clocal8 = {}, clocal9 = {}, clocal10 = {}, clocal11 = {}, clocal12 = {}, clocal13 = {}, clocal14 = {}, clocal15 = {}, clocal16 = {}, clocal17 = {}, clocal18 = {}, clocal19 = {}, clocal20 = {}, clocal21 = {};
        std::unordered_set<jobject> refs;
    
    
        // LDC -8130595710016774463; Stack: 0
        cstack0.j = -8130595710016774463LL;
        // New stack: 2
        // LDC 8498788108198692767; Stack: 2
        cstack2.j = 8498788108198692767LL;
        // New stack: 4
        // INVOKESTATIC java/lang/invoke/MethodHandles.lookup()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 4
        if (!cclasses[21] || env->IsSameObject(cclasses[21], NULL)) { cclasses_mtx[21].lock(); if (!cclasses[21] || env->IsSameObject(cclasses[21], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[36]))) { cclasses[21] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[21].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[21]) { cmethods[21] = env->GetStaticMethodID((cclasses[21]), ((char *)(string_pool + 1542LL)), ((char *)(string_pool + 1549LL))); if (env->ExceptionCheck()) { return (void) 0; }  } cstack4.l = env->CallStaticObjectMethod((cclasses[21]), (cmethods[21])); refs.insert(cstack4.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 5
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.lookupClass()Ljava/lang/Class;; Stack: 5
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[22]) { cmethods[22] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 1591LL)), ((char *)(string_pool + 1603LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 488LL)), -1); else { cstack4.l = env->CallObjectMethod(cstack4.l, (cmethods[22])); refs.insert(cstack4.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 5
        // INVOKESTATIC dev/sakura/client/Mahiro_o4.a(JJLjava/lang/Object;)Ldev/sakura/client/Mahiro_n6;; Stack: 5
        if (!cclasses[22] || env->IsSameObject(cclasses[22], NULL)) { cclasses_mtx[22].lock(); if (!cclasses[22] || env->IsSameObject(cclasses[22], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[37]))) { cclasses[22] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[22].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[23]) { cmethods[23] = env->GetStaticMethodID((cclasses[22]), ((char *)(string_pool + 213LL)), ((char *)(string_pool + 1623LL))); if (env->ExceptionCheck()) { return (void) 0; }  } cstack0.l = env->CallStaticObjectMethod((cclasses[22]), (cmethods[23]), cstack0.j, cstack2.j, cstack4.l); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // LDC 129281065394288; Stack: 1
        cstack1.j = 129281065394288LL;
        // New stack: 3
        // INVOKEINTERFACE dev/sakura/client/Mahiro_n6.a(J)J; Stack: 3
        if (!cclasses[23] || env->IsSameObject(cclasses[23], NULL)) { cclasses_mtx[23].lock(); if (!cclasses[23] || env->IsSameObject(cclasses[23], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[38]))) { cclasses[23] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[23].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[24]) { cmethods[24] = env->GetMethodID((cclasses[23]), ((char *)(string_pool + 213LL)), ((char *)(string_pool + 1675LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 1680LL)), -1); else cstack0.j = env->CallLongMethod(cstack0.l, (cmethods[24]), cstack1.j); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 2
        // PUTSTATIC dev/sakura/client/Mahiro_uT.a J; Stack: 2
        if (!cclasses[4]  || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cfields[0]) { cfields[0] = env->GetStaticFieldID((cclasses[4]), ((char *)(string_pool + 213LL)), ((char *)(string_pool + 215LL))); if (env->ExceptionCheck()) { return (void) 0; }  } env->SetStaticLongField((cclasses[4]), (cfields[0]), cstack0.j); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // NEW java/util/HashMap; Stack: 0
        if (!cclasses[24] || env->IsSameObject(cclasses[24], NULL)) { cclasses_mtx[24].lock(); if (!cclasses[24] || env->IsSameObject(cclasses[24], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[39]))) { cclasses[24] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[24].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (jobject obj = env->AllocObject((cclasses[24]))) { cstack0.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // BIPUSH 13; Stack: 2
        cstack2.i = (jint) 13;
        // New stack: 3
        // INVOKESPECIAL java/util/HashMap.<init>(I)V; Stack: 3
        if (!cclasses[24] || env->IsSameObject(cclasses[24], NULL)) { cclasses_mtx[24].lock(); if (!cclasses[24] || env->IsSameObject(cclasses[24], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[39]))) { cclasses[24] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[24].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[25]) { cmethods[25] = env->GetMethodID((cclasses[24]), ((char *)(string_pool + 631LL)), ((char *)(string_pool + 1705LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 663LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[24]), (cmethods[25]), cstack2.i); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // PUTSTATIC dev/sakura/client/Mahiro_uT.d Ljava/util/Map;; Stack: 1
        if (!cclasses[4]  || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cfields[1]) { cfields[1] = env->GetStaticFieldID((cclasses[4]), ((char *)(string_pool + 1710LL)), ((char *)(string_pool + 1712LL))); if (env->ExceptionCheck()) { return (void) 0; }  } env->SetStaticObjectField((cclasses[4]), (cfields[1]), cstack0.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // GETSTATIC dev/sakura/client/Mahiro_uT.a J; Stack: 0
        if (!cclasses[4]  || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cfields[0]) { cfields[0] = env->GetStaticFieldID((cclasses[4]), ((char *)(string_pool + 213LL)), ((char *)(string_pool + 215LL))); if (env->ExceptionCheck()) { return (void) 0; }  } cstack0.j = env->GetStaticLongField((cclasses[4]), (cfields[0])); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 2
        // LDC 51436358352100; Stack: 2
        cstack2.j = 51436358352100LL;
        // New stack: 4
        // LXOR; Stack: 4
        cstack0.j = cstack0.j ^ cstack2.j;
        // New stack: 2
        // LSTORE 11; Stack: 2
        clocal11.j = cstack0.j;
        // New stack: 0
        // LDC DES/CBC/PKCS5Padding; Stack: 0
        cstack0.l = (cstrings[40]);
        // New stack: 1
        // INVOKESTATIC javax/crypto/Cipher.getInstance(Ljava/lang/String;)Ljavax/crypto/Cipher;; Stack: 1
        if (!cclasses[25] || env->IsSameObject(cclasses[25], NULL)) { cclasses_mtx[25].lock(); if (!cclasses[25] || env->IsSameObject(cclasses[25], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[41]))) { cclasses[25] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[25].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[26]) { cmethods[26] = env->GetStaticMethodID((cclasses[25]), ((char *)(string_pool + 1728LL)), ((char *)(string_pool + 1740LL))); if (env->ExceptionCheck()) { return (void) 0; }  } cstack0.l = env->CallStaticObjectMethod((cclasses[25]), (cmethods[26]), cstack0.l); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // ASTORE 13; Stack: 2
        clocal13.l = cstack1.l; refs.insert(cstack1.l);
        // New stack: 1
        // ICONST_2; Stack: 1
        cstack1.i = 2;
        // New stack: 2
        // LDC DES; Stack: 2
        cstack2.l = (cstrings[42]);
        // New stack: 3
        // INVOKESTATIC javax/crypto/SecretKeyFactory.getInstance(Ljava/lang/String;)Ljavax/crypto/SecretKeyFactory;; Stack: 3
        if (!cclasses[26] || env->IsSameObject(cclasses[26], NULL)) { cclasses_mtx[26].lock(); if (!cclasses[26] || env->IsSameObject(cclasses[26], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[43]))) { cclasses[26] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[26].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[27]) { cmethods[27] = env->GetStaticMethodID((cclasses[26]), ((char *)(string_pool + 1728LL)), ((char *)(string_pool + 1782LL))); if (env->ExceptionCheck()) { return (void) 0; }  } cstack2.l = env->CallStaticObjectMethod((cclasses[26]), (cmethods[27]), cstack2.l); refs.insert(cstack2.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 3
        // BIPUSH 8; Stack: 3
        cstack3.i = (jint) 8;
        // New stack: 4
        // NEWARRAY 8; Stack: 4
        if (cstack3.i < 0) utils::throw_re(env, ((char *)(string_pool + 217LL)), ((char *)(string_pool + 1834LL)), -1); else { cstack3.l = env->NewByteArray(cstack3.i); refs.insert(cstack3.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 4
        // DUP; Stack: 4
        cstack4 = cstack3;
        // New stack: 5
        // ICONST_0; Stack: 5
        cstack5.i = 0;
        // New stack: 6
        // LLOAD 11; Stack: 6
        cstack6.j = clocal11.j;
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
        if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 1863LL)), -1); else { utils::bastore(env, (jarray) cstack4.l, cstack5.i, cstack6.i); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 4
        // ICONST_1; Stack: 4
        cstack4.i = 1;
        // New stack: 5
        // ISTORE 14; Stack: 5
        clocal14.i = cstack4.i;
        // New stack: 4
        // LABEL L1; Stack: 4
        L1: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 4
        // FRAME FULL L: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, javax/crypto/Cipher, 1] S: [javax/crypto/Cipher, 1, javax/crypto/SecretKeyFactory, [B]; Stack: 4
        refs.erase(cstack0.l); refs.erase(cstack2.l); refs.erase(cstack3.l); 
        refs.erase(clocal13.l); 
        utils::clear_refs(env, refs);
        // New stack: 4
        // ILOAD 14; Stack: 4
        cstack4.i = clocal14.i;
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
        // ILOAD 14; Stack: 5
        cstack5.i = clocal14.i;
        // New stack: 6
        // LLOAD 11; Stack: 6
        cstack6.j = clocal11.j;
        // New stack: 8
        // ILOAD 14; Stack: 8
        cstack8.i = clocal14.i;
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
        if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 1863LL)), -1); else { utils::bastore(env, (jarray) cstack4.l, cstack5.i, cstack6.i); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 4
        // IINC 14 1; Stack: 4
        clocal14.i += 1;
        // New stack: 4
        // GOTO L1; Stack: 4
        goto L1;
        // New stack: 4
        // LABEL L2; Stack: 4
        L2: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 4
        // FRAME FULL L: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, javax/crypto/Cipher, 1] S: [javax/crypto/Cipher, 1, javax/crypto/SecretKeyFactory, [B]; Stack: 4
        refs.erase(cstack0.l); refs.erase(cstack2.l); refs.erase(cstack3.l); 
        refs.erase(clocal13.l); 
        utils::clear_refs(env, refs);
        // New stack: 4
        // NEW javax/crypto/spec/DESKeySpec; Stack: 4
        if (!cclasses[27] || env->IsSameObject(cclasses[27], NULL)) { cclasses_mtx[27].lock(); if (!cclasses[27] || env->IsSameObject(cclasses[27], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[44]))) { cclasses[27] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[27].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (jobject obj = env->AllocObject((cclasses[27]))) { cstack4.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 5
        // DUP_X1; Stack: 5
        cstack5 = cstack4; cstack4 = cstack3; cstack3 = cstack5;
        // New stack: 6
        // SWAP; Stack: 6
        std::swap(cstack5, cstack4);
        // New stack: 6
        // INVOKESPECIAL javax/crypto/spec/DESKeySpec.<init>([B)V; Stack: 6
        if (!cclasses[27] || env->IsSameObject(cclasses[27], NULL)) { cclasses_mtx[27].lock(); if (!cclasses[27] || env->IsSameObject(cclasses[27], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[44]))) { cclasses[27] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[27].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[28]) { cmethods[28] = env->GetMethodID((cclasses[27]), ((char *)(string_pool + 631LL)), ((char *)(string_pool + 1007LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 663LL)), -1); else env->CallNonvirtualVoidMethod(cstack4.l, (cclasses[27]), (cmethods[28]), cstack5.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 4
        // INVOKEVIRTUAL javax/crypto/SecretKeyFactory.generateSecret(Ljava/security/spec/KeySpec;)Ljavax/crypto/SecretKey;; Stack: 4
        if (!cclasses[26] || env->IsSameObject(cclasses[26], NULL)) { cclasses_mtx[26].lock(); if (!cclasses[26] || env->IsSameObject(cclasses[26], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[43]))) { cclasses[26] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[26].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[29]) { cmethods[29] = env->GetMethodID((cclasses[26]), ((char *)(string_pool + 1875LL)), ((char *)(string_pool + 1890LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack2.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 488LL)), -1); else { cstack2.l = env->CallObjectMethod(cstack2.l, (cmethods[29]), cstack3.l); refs.insert(cstack2.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 3
        // NEW javax/crypto/spec/IvParameterSpec; Stack: 3
        if (!cclasses[28] || env->IsSameObject(cclasses[28], NULL)) { cclasses_mtx[28].lock(); if (!cclasses[28] || env->IsSameObject(cclasses[28], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[45]))) { cclasses[28] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[28].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (jobject obj = env->AllocObject((cclasses[28]))) { cstack3.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 4
        // DUP; Stack: 4
        cstack4 = cstack3;
        // New stack: 5
        // BIPUSH 8; Stack: 5
        cstack5.i = (jint) 8;
        // New stack: 6
        // NEWARRAY 8; Stack: 6
        if (cstack5.i < 0) utils::throw_re(env, ((char *)(string_pool + 217LL)), ((char *)(string_pool + 1834LL)), -1); else { cstack5.l = env->NewByteArray(cstack5.i); refs.insert(cstack5.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 6
        // INVOKESPECIAL javax/crypto/spec/IvParameterSpec.<init>([B)V; Stack: 6
        if (!cclasses[28] || env->IsSameObject(cclasses[28], NULL)) { cclasses_mtx[28].lock(); if (!cclasses[28] || env->IsSameObject(cclasses[28], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[45]))) { cclasses[28] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[28].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[30]) { cmethods[30] = env->GetMethodID((cclasses[28]), ((char *)(string_pool + 631LL)), ((char *)(string_pool + 1007LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 663LL)), -1); else env->CallNonvirtualVoidMethod(cstack4.l, (cclasses[28]), (cmethods[30]), cstack5.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 4
        // INVOKEVIRTUAL javax/crypto/Cipher.init(ILjava/security/Key;Ljava/security/spec/AlgorithmParameterSpec;)V; Stack: 4
        if (!cclasses[25] || env->IsSameObject(cclasses[25], NULL)) { cclasses_mtx[25].lock(); if (!cclasses[25] || env->IsSameObject(cclasses[25], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[41]))) { cclasses[25] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[25].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[31]) { cmethods[31] = env->GetMethodID((cclasses[25]), ((char *)(string_pool + 1945LL)), ((char *)(string_pool + 1950LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2017LL)), -1); else env->CallVoidMethod(cstack0.l, (cmethods[31]), cstack1.i, cstack2.l, cstack3.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // ICONST_4; Stack: 0
        cstack0.i = 4;
        // New stack: 1
        // ANEWARRAY java/lang/String; Stack: 1
        if (!cclasses[16] || env->IsSameObject(cclasses[16], NULL)) { cclasses_mtx[16].lock(); if (!cclasses[16] || env->IsSameObject(cclasses[16], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[23]))) { cclasses[16] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[16].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (cstack0.i < 0) utils::throw_re(env, ((char *)(string_pool + 217LL)), ((char *)(string_pool + 254LL)), -1); else { cstack0.l = env->NewObjectArray(cstack0.i, (cclasses[16]), nullptr); refs.insert(cstack0.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // ASTORE 20; Stack: 1
        clocal20.l = cstack0.l; refs.insert(cstack0.l);
        // New stack: 0
        // ICONST_0; Stack: 0
        cstack0.i = 0;
        // New stack: 1
        // ISTORE 18; Stack: 1
        clocal18.i = cstack0.i;
        // New stack: 0
        // LDC \u009a\u00ba\u00c7\u008b\u00e7)h\u0017\u00f4\u0097\u009a^D.\u0083\u00d4\u001c\u00ebzp\u009eq\u00fe\u00f4\u0018k\u001bN\u000a\u00b1\u00e7\u00d1\u00b8=\u0007\u00c5\u007f\u00e3\u00edQ\u001bw\u008a\u00fb \u00b2"\u0006O; Stack: 0
        cstack0.l = (cstrings[46]);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // ASTORE 17; Stack: 2
        clocal17.l = cstack1.l; refs.insert(cstack1.l);
        // New stack: 1
        // INVOKEVIRTUAL java/lang/String.length()I; Stack: 1
        if (!cclasses[16] || env->IsSameObject(cclasses[16], NULL)) { cclasses_mtx[16].lock(); if (!cclasses[16] || env->IsSameObject(cclasses[16], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[23]))) { cclasses[16] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[16].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[32]) { cmethods[32] = env->GetMethodID((cclasses[16]), ((char *)(string_pool + 2040LL)), ((char *)(string_pool + 2047LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2051LL)), -1); else cstack0.i = env->CallIntMethod(cstack0.l, (cmethods[32])); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // ISTORE 19; Stack: 1
        clocal19.i = cstack0.i;
        // New stack: 0
        // BIPUSH 24; Stack: 0
        cstack0.i = (jint) 24;
        // New stack: 1
        // ISTORE 16; Stack: 1
        clocal16.i = cstack0.i;
        // New stack: 0
        // ICONST_M1; Stack: 0
        cstack0.i = -1;
        // New stack: 1
        // ISTORE 15; Stack: 1
        clocal15.i = cstack0.i;
        // New stack: 0
        // LABEL L3; Stack: 0
        L3: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // FRAME FULL L: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, javax/crypto/Cipher, 1, 1, 1, java/lang/String, 1, 1, [Ljava/lang/String;] S: []; Stack: 0
        refs.erase(clocal13.l); refs.erase(clocal17.l); refs.erase(clocal20.l); 
        utils::clear_refs(env, refs);
        // New stack: 0
        // IINC 15 1; Stack: 0
        clocal15.i += 1;
        // New stack: 0
        // ALOAD 17; Stack: 0
        cstack0.l = clocal17.l; refs.insert(cstack0.l);
        // New stack: 1
        // ILOAD 15; Stack: 1
        cstack1.i = clocal15.i;
        // New stack: 2
        // DUP; Stack: 2
        cstack2 = cstack1;
        // New stack: 3
        // ILOAD 16; Stack: 3
        cstack3.i = clocal16.i;
        // New stack: 4
        // IADD; Stack: 4
        cstack2.i = cstack2.i + cstack3.i;
        // New stack: 3
        // INVOKEVIRTUAL java/lang/String.substring(II)Ljava/lang/String;; Stack: 3
        if (!cclasses[16] || env->IsSameObject(cclasses[16], NULL)) { cclasses_mtx[16].lock(); if (!cclasses[16] || env->IsSameObject(cclasses[16], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[23]))) { cclasses[16] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[16].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[33]) { cmethods[33] = env->GetMethodID((cclasses[16]), ((char *)(string_pool + 2073LL)), ((char *)(string_pool + 2083LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 488LL)), -1); else { cstack0.l = env->CallObjectMethod(cstack0.l, (cmethods[33]), cstack1.i, cstack2.i); refs.insert(cstack0.l); } 
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
        // FRAME FULL L: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, javax/crypto/Cipher, 1, 1, 1, java/lang/String, 1, 1, [Ljava/lang/String;, [B] S: [java/lang/String]; Stack: 2
        refs.erase(cstack0.l); 
        refs.erase(clocal13.l); refs.erase(clocal17.l); refs.erase(clocal20.l); refs.erase(clocal21.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ALOAD 20; Stack: 1
        cstack1.l = clocal20.l; refs.insert(cstack1.l);
        // New stack: 2
        // SWAP; Stack: 2
        std::swap(cstack1, cstack0);
        // New stack: 2
        // ILOAD 18; Stack: 2
        cstack2.i = clocal18.i;
        // New stack: 3
        // IINC 18 1; Stack: 3
        clocal18.i += 1;
        // New stack: 3
        // SWAP; Stack: 3
        std::swap(cstack2, cstack1);
        // New stack: 3
        // AASTORE; Stack: 3
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2106LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack0.l, cstack1.i, cstack2.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // ILOAD 15; Stack: 0
        cstack0.i = clocal15.i;
        // New stack: 1
        // ILOAD 16; Stack: 1
        cstack1.i = clocal16.i;
        // New stack: 2
        // IADD; Stack: 2
        cstack0.i = cstack0.i + cstack1.i;
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // ISTORE 15; Stack: 2
        clocal15.i = cstack1.i;
        // New stack: 1
        // ILOAD 19; Stack: 1
        cstack1.i = clocal19.i;
        // New stack: 2
        // IF_ICMPGE L6; Stack: 2
        if (cstack0.i >= cstack1.i) goto L6;
        // New stack: 0
        // ALOAD 17; Stack: 0
        cstack0.l = clocal17.l; refs.insert(cstack0.l);
        // New stack: 1
        // ILOAD 15; Stack: 1
        cstack1.i = clocal15.i;
        // New stack: 2
        // INVOKEVIRTUAL java/lang/String.charAt(I)C; Stack: 2
        if (!cclasses[16] || env->IsSameObject(cclasses[16], NULL)) { cclasses_mtx[16].lock(); if (!cclasses[16] || env->IsSameObject(cclasses[16], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[23]))) { cclasses[16] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[16].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[34]) { cmethods[34] = env->GetMethodID((cclasses[16]), ((char *)(string_pool + 2118LL)), ((char *)(string_pool + 2125LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2130LL)), -1); else cstack0.i = (jint) env->CallCharMethod(cstack0.l, (cmethods[34]), cstack1.i); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // ISTORE 16; Stack: 1
        clocal16.i = cstack0.i;
        // New stack: 0
        // GOTO L3; Stack: 0
        goto L3;
        // New stack: 0
        // LABEL L6; Stack: 0
        L6: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // FRAME SAME L: null S: null; Stack: 0
        refs.erase(clocal13.l); refs.erase(clocal17.l); refs.erase(clocal20.l); refs.erase(clocal21.l); 
        utils::clear_refs(env, refs);
        // New stack: 0
        // LDC hC+\u001c\u00cc\u00b4\u00f6\u00d5\u0015\u000e\u00d6\u001f\u0005 \u000eS\u00a1\u00b9\u00dcu\u00bb\u00f4\u0098P\u00f8\u00eb\u000a\u00ea{j\u00b7J\u0010\u00e1\u00b5@[\u00f7\u0000\u000e\u00e1U<kl\u00b1\u00f2H=; Stack: 0
        cstack0.l = (cstrings[47]);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // ASTORE 17; Stack: 2
        clocal17.l = cstack1.l; refs.insert(cstack1.l);
        // New stack: 1
        // INVOKEVIRTUAL java/lang/String.length()I; Stack: 1
        if (!cclasses[16] || env->IsSameObject(cclasses[16], NULL)) { cclasses_mtx[16].lock(); if (!cclasses[16] || env->IsSameObject(cclasses[16], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[23]))) { cclasses[16] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[16].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[32]) { cmethods[32] = env->GetMethodID((cclasses[16]), ((char *)(string_pool + 2040LL)), ((char *)(string_pool + 2047LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2051LL)), -1); else cstack0.i = env->CallIntMethod(cstack0.l, (cmethods[32])); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // ISTORE 19; Stack: 1
        clocal19.i = cstack0.i;
        // New stack: 0
        // BIPUSH 32; Stack: 0
        cstack0.i = (jint) 32;
        // New stack: 1
        // ISTORE 16; Stack: 1
        clocal16.i = cstack0.i;
        // New stack: 0
        // ICONST_M1; Stack: 0
        cstack0.i = -1;
        // New stack: 1
        // ISTORE 15; Stack: 1
        clocal15.i = cstack0.i;
        // New stack: 0
        // LABEL L7; Stack: 0
        L7: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // FRAME SAME L: null S: null; Stack: 0
        refs.erase(clocal13.l); refs.erase(clocal17.l); refs.erase(clocal20.l); refs.erase(clocal21.l); 
        utils::clear_refs(env, refs);
        // New stack: 0
        // IINC 15 1; Stack: 0
        clocal15.i += 1;
        // New stack: 0
        // ALOAD 17; Stack: 0
        cstack0.l = clocal17.l; refs.insert(cstack0.l);
        // New stack: 1
        // ILOAD 15; Stack: 1
        cstack1.i = clocal15.i;
        // New stack: 2
        // DUP; Stack: 2
        cstack2 = cstack1;
        // New stack: 3
        // ILOAD 16; Stack: 3
        cstack3.i = clocal16.i;
        // New stack: 4
        // IADD; Stack: 4
        cstack2.i = cstack2.i + cstack3.i;
        // New stack: 3
        // INVOKEVIRTUAL java/lang/String.substring(II)Ljava/lang/String;; Stack: 3
        if (!cclasses[16] || env->IsSameObject(cclasses[16], NULL)) { cclasses_mtx[16].lock(); if (!cclasses[16] || env->IsSameObject(cclasses[16], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[23]))) { cclasses[16] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[16].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[33]) { cmethods[33] = env->GetMethodID((cclasses[16]), ((char *)(string_pool + 2073LL)), ((char *)(string_pool + 2083LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 488LL)), -1); else { cstack0.l = env->CallObjectMethod(cstack0.l, (cmethods[33]), cstack1.i, cstack2.i); refs.insert(cstack0.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // ICONST_0; Stack: 1
        cstack1.i = 0;
        // New stack: 2
        // GOTO L4; Stack: 2
        goto L4;
        // New stack: 2
        // LABEL L8; Stack: 2
        L8: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 2
        // FRAME SAME1 L: null S: [java/lang/String]; Stack: 2
        refs.erase(cstack0.l); 
        refs.erase(clocal13.l); refs.erase(clocal17.l); refs.erase(clocal20.l); refs.erase(clocal21.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ALOAD 20; Stack: 1
        cstack1.l = clocal20.l; refs.insert(cstack1.l);
        // New stack: 2
        // SWAP; Stack: 2
        std::swap(cstack1, cstack0);
        // New stack: 2
        // ILOAD 18; Stack: 2
        cstack2.i = clocal18.i;
        // New stack: 3
        // IINC 18 1; Stack: 3
        clocal18.i += 1;
        // New stack: 3
        // SWAP; Stack: 3
        std::swap(cstack2, cstack1);
        // New stack: 3
        // AASTORE; Stack: 3
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2106LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack0.l, cstack1.i, cstack2.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // ILOAD 15; Stack: 0
        cstack0.i = clocal15.i;
        // New stack: 1
        // ILOAD 16; Stack: 1
        cstack1.i = clocal16.i;
        // New stack: 2
        // IADD; Stack: 2
        cstack0.i = cstack0.i + cstack1.i;
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // ISTORE 15; Stack: 2
        clocal15.i = cstack1.i;
        // New stack: 1
        // ILOAD 19; Stack: 1
        cstack1.i = clocal19.i;
        // New stack: 2
        // IF_ICMPGE L9; Stack: 2
        if (cstack0.i >= cstack1.i) goto L9;
        // New stack: 0
        // ALOAD 17; Stack: 0
        cstack0.l = clocal17.l; refs.insert(cstack0.l);
        // New stack: 1
        // ILOAD 15; Stack: 1
        cstack1.i = clocal15.i;
        // New stack: 2
        // INVOKEVIRTUAL java/lang/String.charAt(I)C; Stack: 2
        if (!cclasses[16] || env->IsSameObject(cclasses[16], NULL)) { cclasses_mtx[16].lock(); if (!cclasses[16] || env->IsSameObject(cclasses[16], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[23]))) { cclasses[16] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[16].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[34]) { cmethods[34] = env->GetMethodID((cclasses[16]), ((char *)(string_pool + 2118LL)), ((char *)(string_pool + 2125LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2130LL)), -1); else cstack0.i = (jint) env->CallCharMethod(cstack0.l, (cmethods[34]), cstack1.i); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // ISTORE 16; Stack: 1
        clocal16.i = cstack0.i;
        // New stack: 0
        // GOTO L7; Stack: 0
        goto L7;
        // New stack: 0
        // LABEL L9; Stack: 0
        L9: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // FRAME SAME L: null S: null; Stack: 0
        refs.erase(clocal13.l); refs.erase(clocal17.l); refs.erase(clocal20.l); refs.erase(clocal21.l); 
        utils::clear_refs(env, refs);
        // New stack: 0
        // ALOAD 20; Stack: 0
        cstack0.l = clocal20.l; refs.insert(cstack0.l);
        // New stack: 1
        // PUTSTATIC dev/sakura/client/Mahiro_uT.b [Ljava/lang/String;; Stack: 1
        if (!cclasses[4]  || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cfields[2]) { cfields[2] = env->GetStaticFieldID((cclasses[4]), ((char *)(string_pool + 2153LL)), ((char *)(string_pool + 2155LL))); if (env->ExceptionCheck()) { return (void) 0; }  } env->SetStaticObjectField((cclasses[4]), (cfields[2]), cstack0.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // ICONST_4; Stack: 0
        cstack0.i = 4;
        // New stack: 1
        // ANEWARRAY java/lang/String; Stack: 1
        if (!cclasses[16] || env->IsSameObject(cclasses[16], NULL)) { cclasses_mtx[16].lock(); if (!cclasses[16] || env->IsSameObject(cclasses[16], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[23]))) { cclasses[16] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[16].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (cstack0.i < 0) utils::throw_re(env, ((char *)(string_pool + 217LL)), ((char *)(string_pool + 254LL)), -1); else { cstack0.l = env->NewObjectArray(cstack0.i, (cclasses[16]), nullptr); refs.insert(cstack0.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // PUTSTATIC dev/sakura/client/Mahiro_uT.c [Ljava/lang/String;; Stack: 1
        if (!cclasses[4]  || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cfields[3]) { cfields[3] = env->GetStaticFieldID((cclasses[4]), ((char *)(string_pool + 2175LL)), ((char *)(string_pool + 2155LL))); if (env->ExceptionCheck()) { return (void) 0; }  } env->SetStaticObjectField((cclasses[4]), (cfields[3]), cstack0.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // GOTO L10; Stack: 0
        goto L10;
        // New stack: 0
        // LABEL L4; Stack: 0
        L4: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // FRAME FULL L: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, javax/crypto/Cipher, 1, 1, 1, java/lang/String, 1, 1, [Ljava/lang/String;] S: [java/lang/String, 1]; Stack: 0
        refs.erase(cstack0.l); 
        refs.erase(clocal13.l); refs.erase(clocal17.l); refs.erase(clocal20.l); 
        utils::clear_refs(env, refs);
        // New stack: 2
        // SWAP; Stack: 2
        std::swap(cstack1, cstack0);
        // New stack: 2
        // LDC ISO-8859-1; Stack: 2
        cstack2.l = (cstrings[48]);
        // New stack: 3
        // INVOKEVIRTUAL java/lang/String.getBytes(Ljava/lang/String;)[B; Stack: 3
        if (!cclasses[16] || env->IsSameObject(cclasses[16], NULL)) { cclasses_mtx[16].lock(); if (!cclasses[16] || env->IsSameObject(cclasses[16], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[23]))) { cclasses[16] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[16].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[35]) { cmethods[35] = env->GetMethodID((cclasses[16]), ((char *)(string_pool + 2177LL)), ((char *)(string_pool + 2186LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 488LL)), -1); else { cstack1.l = env->CallObjectMethod(cstack1.l, (cmethods[35]), cstack2.l); refs.insert(cstack1.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 2
        // ALOAD 13; Stack: 2
        cstack2.l = clocal13.l; refs.insert(cstack2.l);
        // New stack: 3
        // SWAP; Stack: 3
        std::swap(cstack2, cstack1);
        // New stack: 3
        // INVOKEVIRTUAL javax/crypto/Cipher.doFinal([B)[B; Stack: 3
        if (!cclasses[25] || env->IsSameObject(cclasses[25], NULL)) { cclasses_mtx[25].lock(); if (!cclasses[25] || env->IsSameObject(cclasses[25], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[41]))) { cclasses[25] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[25].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[36]) { cmethods[36] = env->GetMethodID((cclasses[25]), ((char *)(string_pool + 2209LL)), ((char *)(string_pool + 2217LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 488LL)), -1); else { cstack1.l = env->CallObjectMethod(cstack1.l, (cmethods[36]), cstack2.l); refs.insert(cstack1.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 2
        // ASTORE 21; Stack: 2
        clocal21.l = cstack1.l; refs.insert(cstack1.l);
        // New stack: 1
        // ALOAD 21; Stack: 1
        cstack1.l = clocal21.l; refs.insert(cstack1.l);
        // New stack: 2
        // INVOKESTATIC dev/sakura/client/Mahiro_uT.a([B)Ljava/lang/String;; Stack: 2
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[37]) { cmethods[37] = env->GetStaticMethodID((cclasses[4]), ((char *)(string_pool + 213LL)), ((char *)(string_pool + 2224LL))); if (env->ExceptionCheck()) { return (void) 0; }  } cstack1.l = env->CallStaticObjectMethod((cclasses[4]), (cmethods[37]), cstack1.l); refs.insert(cstack1.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 2
        // INVOKEVIRTUAL java/lang/String.intern()Ljava/lang/String;; Stack: 2
        if (!cclasses[16] || env->IsSameObject(cclasses[16], NULL)) { cclasses_mtx[16].lock(); if (!cclasses[16] || env->IsSameObject(cclasses[16], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[23]))) { cclasses[16] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[16].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[38]) { cmethods[38] = env->GetMethodID((cclasses[16]), ((char *)(string_pool + 2247LL)), ((char *)(string_pool + 2254LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 488LL)), -1); else { cstack1.l = env->CallObjectMethod(cstack1.l, (cmethods[38])); refs.insert(cstack1.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 2
        // SWAP; Stack: 2
        std::swap(cstack1, cstack0);
        // New stack: 2
        // TABLESWITCH; Stack: 2
        switch (cstack1.i) {
            case 0: goto L8; break;
            default: goto L5; break;
        }
        // New stack: 1
        // LABEL L10; Stack: 1
        L10: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // FRAME APPEND L: [[B] S: null; Stack: 1
        refs.erase(clocal13.l); refs.erase(clocal17.l); refs.erase(clocal20.l); refs.erase(clocal21.l); 
        utils::clear_refs(env, refs);
        // New stack: 0
        // NEW java/util/HashMap; Stack: 0
        if (!cclasses[24] || env->IsSameObject(cclasses[24], NULL)) { cclasses_mtx[24].lock(); if (!cclasses[24] || env->IsSameObject(cclasses[24], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[39]))) { cclasses[24] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[24].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (jobject obj = env->AllocObject((cclasses[24]))) { cstack0.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // BIPUSH 13; Stack: 2
        cstack2.i = (jint) 13;
        // New stack: 3
        // INVOKESPECIAL java/util/HashMap.<init>(I)V; Stack: 3
        if (!cclasses[24] || env->IsSameObject(cclasses[24], NULL)) { cclasses_mtx[24].lock(); if (!cclasses[24] || env->IsSameObject(cclasses[24], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[39]))) { cclasses[24] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[24].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[25]) { cmethods[25] = env->GetMethodID((cclasses[24]), ((char *)(string_pool + 631LL)), ((char *)(string_pool + 1705LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 663LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[24]), (cmethods[25]), cstack2.i); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // PUTSTATIC dev/sakura/client/Mahiro_uT.g Ljava/util/Map;; Stack: 1
        if (!cclasses[4]  || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cfields[4]) { cfields[4] = env->GetStaticFieldID((cclasses[4]), ((char *)(string_pool + 8400LL)), ((char *)(string_pool + 1712LL))); if (env->ExceptionCheck()) { return (void) 0; }  } env->SetStaticObjectField((cclasses[4]), (cfields[4]), cstack0.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // LDC DES/CBC/NoPadding; Stack: 0
        cstack0.l = (cstrings[49]);
        // New stack: 1
        // INVOKESTATIC javax/crypto/Cipher.getInstance(Ljava/lang/String;)Ljavax/crypto/Cipher;; Stack: 1
        if (!cclasses[25] || env->IsSameObject(cclasses[25], NULL)) { cclasses_mtx[25].lock(); if (!cclasses[25] || env->IsSameObject(cclasses[25], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[41]))) { cclasses[25] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[25].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[26]) { cmethods[26] = env->GetStaticMethodID((cclasses[25]), ((char *)(string_pool + 1728LL)), ((char *)(string_pool + 1740LL))); if (env->ExceptionCheck()) { return (void) 0; }  } cstack0.l = env->CallStaticObjectMethod((cclasses[25]), (cmethods[26]), cstack0.l); refs.insert(cstack0.l); 
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
        cstack2.l = (cstrings[42]);
        // New stack: 3
        // INVOKESTATIC javax/crypto/SecretKeyFactory.getInstance(Ljava/lang/String;)Ljavax/crypto/SecretKeyFactory;; Stack: 3
        if (!cclasses[26] || env->IsSameObject(cclasses[26], NULL)) { cclasses_mtx[26].lock(); if (!cclasses[26] || env->IsSameObject(cclasses[26], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[43]))) { cclasses[26] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[26].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[27]) { cmethods[27] = env->GetStaticMethodID((cclasses[26]), ((char *)(string_pool + 1728LL)), ((char *)(string_pool + 1782LL))); if (env->ExceptionCheck()) { return (void) 0; }  } cstack2.l = env->CallStaticObjectMethod((cclasses[26]), (cmethods[27]), cstack2.l); refs.insert(cstack2.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 3
        // BIPUSH 8; Stack: 3
        cstack3.i = (jint) 8;
        // New stack: 4
        // NEWARRAY 8; Stack: 4
        if (cstack3.i < 0) utils::throw_re(env, ((char *)(string_pool + 217LL)), ((char *)(string_pool + 1834LL)), -1); else { cstack3.l = env->NewByteArray(cstack3.i); refs.insert(cstack3.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 4
        // DUP; Stack: 4
        cstack4 = cstack3;
        // New stack: 5
        // ICONST_0; Stack: 5
        cstack5.i = 0;
        // New stack: 6
        // LLOAD 11; Stack: 6
        cstack6.j = clocal11.j;
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
        if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 1863LL)), -1); else { utils::bastore(env, (jarray) cstack4.l, cstack5.i, cstack6.i); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 4
        // ICONST_1; Stack: 4
        cstack4.i = 1;
        // New stack: 5
        // ISTORE 1; Stack: 5
        clocal1.i = cstack4.i;
        // New stack: 4
        // LABEL L11; Stack: 4
        L11: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 4
        // FRAME FULL L: [javax/crypto/Cipher, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, javax/crypto/Cipher, 1, 1, 1, java/lang/String, 1, 1, [Ljava/lang/String;, [B] S: [javax/crypto/Cipher, 1, javax/crypto/SecretKeyFactory, [B]; Stack: 4
        refs.erase(cstack0.l); refs.erase(cstack2.l); refs.erase(cstack3.l); 
        refs.erase(clocal0.l); refs.erase(clocal13.l); refs.erase(clocal17.l); refs.erase(clocal20.l); refs.erase(clocal21.l); 
        utils::clear_refs(env, refs);
        // New stack: 4
        // ILOAD 1; Stack: 4
        cstack4.i = clocal1.i;
        // New stack: 5
        // BIPUSH 8; Stack: 5
        cstack5.i = (jint) 8;
        // New stack: 6
        // IF_ICMPGE L12; Stack: 6
        if (cstack4.i >= cstack5.i) goto L12;
        // New stack: 4
        // DUP; Stack: 4
        cstack4 = cstack3;
        // New stack: 5
        // ILOAD 1; Stack: 5
        cstack5.i = clocal1.i;
        // New stack: 6
        // LLOAD 11; Stack: 6
        cstack6.j = clocal11.j;
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
        if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 1863LL)), -1); else { utils::bastore(env, (jarray) cstack4.l, cstack5.i, cstack6.i); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 4
        // IINC 1 1; Stack: 4
        clocal1.i += 1;
        // New stack: 4
        // GOTO L11; Stack: 4
        goto L11;
        // New stack: 4
        // LABEL L12; Stack: 4
        L12: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 4
        // FRAME FULL L: [javax/crypto/Cipher, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, javax/crypto/Cipher, 1, 1, 1, java/lang/String, 1, 1, [Ljava/lang/String;, [B] S: [javax/crypto/Cipher, 1, javax/crypto/SecretKeyFactory, [B]; Stack: 4
        refs.erase(cstack0.l); refs.erase(cstack2.l); refs.erase(cstack3.l); 
        refs.erase(clocal0.l); refs.erase(clocal13.l); refs.erase(clocal17.l); refs.erase(clocal20.l); refs.erase(clocal21.l); 
        utils::clear_refs(env, refs);
        // New stack: 4
        // NEW javax/crypto/spec/DESKeySpec; Stack: 4
        if (!cclasses[27] || env->IsSameObject(cclasses[27], NULL)) { cclasses_mtx[27].lock(); if (!cclasses[27] || env->IsSameObject(cclasses[27], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[44]))) { cclasses[27] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[27].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (jobject obj = env->AllocObject((cclasses[27]))) { cstack4.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 5
        // DUP_X1; Stack: 5
        cstack5 = cstack4; cstack4 = cstack3; cstack3 = cstack5;
        // New stack: 6
        // SWAP; Stack: 6
        std::swap(cstack5, cstack4);
        // New stack: 6
        // INVOKESPECIAL javax/crypto/spec/DESKeySpec.<init>([B)V; Stack: 6
        if (!cclasses[27] || env->IsSameObject(cclasses[27], NULL)) { cclasses_mtx[27].lock(); if (!cclasses[27] || env->IsSameObject(cclasses[27], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[44]))) { cclasses[27] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[27].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[28]) { cmethods[28] = env->GetMethodID((cclasses[27]), ((char *)(string_pool + 631LL)), ((char *)(string_pool + 1007LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 663LL)), -1); else env->CallNonvirtualVoidMethod(cstack4.l, (cclasses[27]), (cmethods[28]), cstack5.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 4
        // INVOKEVIRTUAL javax/crypto/SecretKeyFactory.generateSecret(Ljava/security/spec/KeySpec;)Ljavax/crypto/SecretKey;; Stack: 4
        if (!cclasses[26] || env->IsSameObject(cclasses[26], NULL)) { cclasses_mtx[26].lock(); if (!cclasses[26] || env->IsSameObject(cclasses[26], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[43]))) { cclasses[26] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[26].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[29]) { cmethods[29] = env->GetMethodID((cclasses[26]), ((char *)(string_pool + 1875LL)), ((char *)(string_pool + 1890LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack2.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 488LL)), -1); else { cstack2.l = env->CallObjectMethod(cstack2.l, (cmethods[29]), cstack3.l); refs.insert(cstack2.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 3
        // NEW javax/crypto/spec/IvParameterSpec; Stack: 3
        if (!cclasses[28] || env->IsSameObject(cclasses[28], NULL)) { cclasses_mtx[28].lock(); if (!cclasses[28] || env->IsSameObject(cclasses[28], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[45]))) { cclasses[28] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[28].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (jobject obj = env->AllocObject((cclasses[28]))) { cstack3.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 4
        // DUP; Stack: 4
        cstack4 = cstack3;
        // New stack: 5
        // BIPUSH 8; Stack: 5
        cstack5.i = (jint) 8;
        // New stack: 6
        // NEWARRAY 8; Stack: 6
        if (cstack5.i < 0) utils::throw_re(env, ((char *)(string_pool + 217LL)), ((char *)(string_pool + 1834LL)), -1); else { cstack5.l = env->NewByteArray(cstack5.i); refs.insert(cstack5.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 6
        // INVOKESPECIAL javax/crypto/spec/IvParameterSpec.<init>([B)V; Stack: 6
        if (!cclasses[28] || env->IsSameObject(cclasses[28], NULL)) { cclasses_mtx[28].lock(); if (!cclasses[28] || env->IsSameObject(cclasses[28], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[45]))) { cclasses[28] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[28].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[30]) { cmethods[30] = env->GetMethodID((cclasses[28]), ((char *)(string_pool + 631LL)), ((char *)(string_pool + 1007LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 663LL)), -1); else env->CallNonvirtualVoidMethod(cstack4.l, (cclasses[28]), (cmethods[30]), cstack5.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 4
        // INVOKEVIRTUAL javax/crypto/Cipher.init(ILjava/security/Key;Ljava/security/spec/AlgorithmParameterSpec;)V; Stack: 4
        if (!cclasses[25] || env->IsSameObject(cclasses[25], NULL)) { cclasses_mtx[25].lock(); if (!cclasses[25] || env->IsSameObject(cclasses[25], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[41]))) { cclasses[25] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[25].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[31]) { cmethods[31] = env->GetMethodID((cclasses[25]), ((char *)(string_pool + 1945LL)), ((char *)(string_pool + 1950LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2017LL)), -1); else env->CallVoidMethod(cstack0.l, (cmethods[31]), cstack1.i, cstack2.l, cstack3.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // ICONST_3; Stack: 0
        cstack0.i = 3;
        // New stack: 1
        // NEWARRAY 11; Stack: 1
        if (cstack0.i < 0) utils::throw_re(env, ((char *)(string_pool + 217LL)), ((char *)(string_pool + 8402LL)), -1); else { cstack0.l = env->NewLongArray(cstack0.i); refs.insert(cstack0.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // ASTORE 6; Stack: 1
        clocal6.l = cstack0.l; refs.insert(cstack0.l);
        // New stack: 0
        // ICONST_0; Stack: 0
        cstack0.i = 0;
        // New stack: 1
        // ISTORE 3; Stack: 1
        clocal3.i = cstack0.i;
        // New stack: 0
        // LDC P]\u0010\u0092[\u00e3\u00b6\u00e5\u0018\u0011#\u00a3\u00b1\u008a\u008d\u00a9\u00d6\u00de\u00e4\u0014\u00e6\u0012\u0001z; Stack: 0
        cstack0.l = (cstrings[50]);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // ASTORE 4; Stack: 2
        clocal4.l = cstack1.l; refs.insert(cstack1.l);
        // New stack: 1
        // INVOKEVIRTUAL java/lang/String.length()I; Stack: 1
        if (!cclasses[16] || env->IsSameObject(cclasses[16], NULL)) { cclasses_mtx[16].lock(); if (!cclasses[16] || env->IsSameObject(cclasses[16], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[23]))) { cclasses[16] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[16].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[32]) { cmethods[32] = env->GetMethodID((cclasses[16]), ((char *)(string_pool + 2040LL)), ((char *)(string_pool + 2047LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2051LL)), -1); else cstack0.i = env->CallIntMethod(cstack0.l, (cmethods[32])); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // ISTORE 5; Stack: 1
        clocal5.i = cstack0.i;
        // New stack: 0
        // ICONST_0; Stack: 0
        cstack0.i = 0;
        // New stack: 1
        // ISTORE 2; Stack: 1
        clocal2.i = cstack0.i;
        // New stack: 0
        // LABEL L13; Stack: 0
        L13: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // FRAME FULL L: [javax/crypto/Cipher, 1, 1, 1, java/lang/String, 1, [J, 0, 0, 0, 0, 4, javax/crypto/Cipher, 1, 1, 1, java/lang/String, 1, 1, [Ljava/lang/String;, [B] S: []; Stack: 0
        refs.erase(clocal0.l); refs.erase(clocal4.l); refs.erase(clocal6.l); refs.erase(clocal13.l); refs.erase(clocal17.l); refs.erase(clocal20.l); refs.erase(clocal21.l); 
        utils::clear_refs(env, refs);
        // New stack: 0
        // ALOAD 4; Stack: 0
        cstack0.l = clocal4.l; refs.insert(cstack0.l);
        // New stack: 1
        // ILOAD 2; Stack: 1
        cstack1.i = clocal2.i;
        // New stack: 2
        // IINC 2 8; Stack: 2
        clocal2.i += 8;
        // New stack: 2
        // ILOAD 2; Stack: 2
        cstack2.i = clocal2.i;
        // New stack: 3
        // INVOKEVIRTUAL java/lang/String.substring(II)Ljava/lang/String;; Stack: 3
        if (!cclasses[16] || env->IsSameObject(cclasses[16], NULL)) { cclasses_mtx[16].lock(); if (!cclasses[16] || env->IsSameObject(cclasses[16], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[23]))) { cclasses[16] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[16].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[33]) { cmethods[33] = env->GetMethodID((cclasses[16]), ((char *)(string_pool + 2073LL)), ((char *)(string_pool + 2083LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 488LL)), -1); else { cstack0.l = env->CallObjectMethod(cstack0.l, (cmethods[33]), cstack1.i, cstack2.i); refs.insert(cstack0.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // LDC ISO-8859-1; Stack: 1
        cstack1.l = (cstrings[48]);
        // New stack: 2
        // INVOKEVIRTUAL java/lang/String.getBytes(Ljava/lang/String;)[B; Stack: 2
        if (!cclasses[16] || env->IsSameObject(cclasses[16], NULL)) { cclasses_mtx[16].lock(); if (!cclasses[16] || env->IsSameObject(cclasses[16], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[23]))) { cclasses[16] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[16].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[35]) { cmethods[35] = env->GetMethodID((cclasses[16]), ((char *)(string_pool + 2177LL)), ((char *)(string_pool + 2186LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 488LL)), -1); else { cstack0.l = env->CallObjectMethod(cstack0.l, (cmethods[35]), cstack1.l); refs.insert(cstack0.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // ASTORE 7; Stack: 1
        clocal7.l = cstack0.l; refs.insert(cstack0.l);
        // New stack: 0
        // ALOAD 6; Stack: 0
        cstack0.l = clocal6.l; refs.insert(cstack0.l);
        // New stack: 1
        // ILOAD 3; Stack: 1
        cstack1.i = clocal3.i;
        // New stack: 2
        // IINC 3 1; Stack: 2
        clocal3.i += 1;
        // New stack: 2
        // ALOAD 7; Stack: 2
        cstack2.l = clocal7.l; refs.insert(cstack2.l);
        // New stack: 3
        // ICONST_0; Stack: 3
        cstack3.i = 0;
        // New stack: 4
        // BALOAD; Stack: 4
        if (cstack2.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2304LL)), -1); else { cstack2.i = (jint) utils::baload(env, (jarray) cstack2.l, cstack3.i); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 3
        // I2L; Stack: 3
        cstack2.j = cstack2.i;
        // New stack: 4
        // LDC 255; Stack: 4
        cstack4.j = 255LL;
        // New stack: 6
        // LAND; Stack: 6
        cstack2.j = cstack2.j & cstack4.j;
        // New stack: 4
        // BIPUSH 56; Stack: 4
        cstack4.i = (jint) 56;
        // New stack: 5
        // LSHL; Stack: 5
        cstack2.j = cstack2.j << (0x3f & cstack4.i);
        // New stack: 4
        // ALOAD 7; Stack: 4
        cstack4.l = clocal7.l; refs.insert(cstack4.l);
        // New stack: 5
        // ICONST_1; Stack: 5
        cstack5.i = 1;
        // New stack: 6
        // BALOAD; Stack: 6
        if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2304LL)), -1); else { cstack4.i = (jint) utils::baload(env, (jarray) cstack4.l, cstack5.i); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 5
        // I2L; Stack: 5
        cstack4.j = cstack4.i;
        // New stack: 6
        // LDC 255; Stack: 6
        cstack6.j = 255LL;
        // New stack: 8
        // LAND; Stack: 8
        cstack4.j = cstack4.j & cstack6.j;
        // New stack: 6
        // BIPUSH 48; Stack: 6
        cstack6.i = (jint) 48;
        // New stack: 7
        // LSHL; Stack: 7
        cstack4.j = cstack4.j << (0x3f & cstack6.i);
        // New stack: 6
        // LOR; Stack: 6
        cstack2.j = cstack2.j | cstack4.j;
        // New stack: 4
        // ALOAD 7; Stack: 4
        cstack4.l = clocal7.l; refs.insert(cstack4.l);
        // New stack: 5
        // ICONST_2; Stack: 5
        cstack5.i = 2;
        // New stack: 6
        // BALOAD; Stack: 6
        if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2304LL)), -1); else { cstack4.i = (jint) utils::baload(env, (jarray) cstack4.l, cstack5.i); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 5
        // I2L; Stack: 5
        cstack4.j = cstack4.i;
        // New stack: 6
        // LDC 255; Stack: 6
        cstack6.j = 255LL;
        // New stack: 8
        // LAND; Stack: 8
        cstack4.j = cstack4.j & cstack6.j;
        // New stack: 6
        // BIPUSH 40; Stack: 6
        cstack6.i = (jint) 40;
        // New stack: 7
        // LSHL; Stack: 7
        cstack4.j = cstack4.j << (0x3f & cstack6.i);
        // New stack: 6
        // LOR; Stack: 6
        cstack2.j = cstack2.j | cstack4.j;
        // New stack: 4
        // ALOAD 7; Stack: 4
        cstack4.l = clocal7.l; refs.insert(cstack4.l);
        // New stack: 5
        // ICONST_3; Stack: 5
        cstack5.i = 3;
        // New stack: 6
        // BALOAD; Stack: 6
        if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2304LL)), -1); else { cstack4.i = (jint) utils::baload(env, (jarray) cstack4.l, cstack5.i); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 5
        // I2L; Stack: 5
        cstack4.j = cstack4.i;
        // New stack: 6
        // LDC 255; Stack: 6
        cstack6.j = 255LL;
        // New stack: 8
        // LAND; Stack: 8
        cstack4.j = cstack4.j & cstack6.j;
        // New stack: 6
        // BIPUSH 32; Stack: 6
        cstack6.i = (jint) 32;
        // New stack: 7
        // LSHL; Stack: 7
        cstack4.j = cstack4.j << (0x3f & cstack6.i);
        // New stack: 6
        // LOR; Stack: 6
        cstack2.j = cstack2.j | cstack4.j;
        // New stack: 4
        // ALOAD 7; Stack: 4
        cstack4.l = clocal7.l; refs.insert(cstack4.l);
        // New stack: 5
        // ICONST_4; Stack: 5
        cstack5.i = 4;
        // New stack: 6
        // BALOAD; Stack: 6
        if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2304LL)), -1); else { cstack4.i = (jint) utils::baload(env, (jarray) cstack4.l, cstack5.i); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 5
        // I2L; Stack: 5
        cstack4.j = cstack4.i;
        // New stack: 6
        // LDC 255; Stack: 6
        cstack6.j = 255LL;
        // New stack: 8
        // LAND; Stack: 8
        cstack4.j = cstack4.j & cstack6.j;
        // New stack: 6
        // BIPUSH 24; Stack: 6
        cstack6.i = (jint) 24;
        // New stack: 7
        // LSHL; Stack: 7
        cstack4.j = cstack4.j << (0x3f & cstack6.i);
        // New stack: 6
        // LOR; Stack: 6
        cstack2.j = cstack2.j | cstack4.j;
        // New stack: 4
        // ALOAD 7; Stack: 4
        cstack4.l = clocal7.l; refs.insert(cstack4.l);
        // New stack: 5
        // ICONST_5; Stack: 5
        cstack5.i = 5;
        // New stack: 6
        // BALOAD; Stack: 6
        if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2304LL)), -1); else { cstack4.i = (jint) utils::baload(env, (jarray) cstack4.l, cstack5.i); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 5
        // I2L; Stack: 5
        cstack4.j = cstack4.i;
        // New stack: 6
        // LDC 255; Stack: 6
        cstack6.j = 255LL;
        // New stack: 8
        // LAND; Stack: 8
        cstack4.j = cstack4.j & cstack6.j;
        // New stack: 6
        // BIPUSH 16; Stack: 6
        cstack6.i = (jint) 16;
        // New stack: 7
        // LSHL; Stack: 7
        cstack4.j = cstack4.j << (0x3f & cstack6.i);
        // New stack: 6
        // LOR; Stack: 6
        cstack2.j = cstack2.j | cstack4.j;
        // New stack: 4
        // ALOAD 7; Stack: 4
        cstack4.l = clocal7.l; refs.insert(cstack4.l);
        // New stack: 5
        // BIPUSH 6; Stack: 5
        cstack5.i = (jint) 6;
        // New stack: 6
        // BALOAD; Stack: 6
        if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2304LL)), -1); else { cstack4.i = (jint) utils::baload(env, (jarray) cstack4.l, cstack5.i); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 5
        // I2L; Stack: 5
        cstack4.j = cstack4.i;
        // New stack: 6
        // LDC 255; Stack: 6
        cstack6.j = 255LL;
        // New stack: 8
        // LAND; Stack: 8
        cstack4.j = cstack4.j & cstack6.j;
        // New stack: 6
        // BIPUSH 8; Stack: 6
        cstack6.i = (jint) 8;
        // New stack: 7
        // LSHL; Stack: 7
        cstack4.j = cstack4.j << (0x3f & cstack6.i);
        // New stack: 6
        // LOR; Stack: 6
        cstack2.j = cstack2.j | cstack4.j;
        // New stack: 4
        // ALOAD 7; Stack: 4
        cstack4.l = clocal7.l; refs.insert(cstack4.l);
        // New stack: 5
        // BIPUSH 7; Stack: 5
        cstack5.i = (jint) 7;
        // New stack: 6
        // BALOAD; Stack: 6
        if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2304LL)), -1); else { cstack4.i = (jint) utils::baload(env, (jarray) cstack4.l, cstack5.i); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 5
        // I2L; Stack: 5
        cstack4.j = cstack4.i;
        // New stack: 6
        // LDC 255; Stack: 6
        cstack6.j = 255LL;
        // New stack: 8
        // LAND; Stack: 8
        cstack4.j = cstack4.j & cstack6.j;
        // New stack: 6
        // LOR; Stack: 6
        cstack2.j = cstack2.j | cstack4.j;
        // New stack: 4
        // ICONST_M1; Stack: 4
        cstack4.i = -1;
        // New stack: 5
        // GOTO L14; Stack: 5
        goto L14;
        // New stack: 5
        // LABEL L15; Stack: 5
        L15: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 5
        // FRAME FULL L: [javax/crypto/Cipher, 1, 1, 1, java/lang/String, 1, [J, [B, 4, [B, 4, javax/crypto/Cipher, 1, 1, 1, java/lang/String, 1, 1, [Ljava/lang/String;, [B] S: [[J, 1, 4]; Stack: 5
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal4.l); refs.erase(clocal6.l); refs.erase(clocal7.l); refs.erase(clocal10.l); refs.erase(clocal13.l); refs.erase(clocal17.l); refs.erase(clocal20.l); refs.erase(clocal21.l); 
        utils::clear_refs(env, refs);
        // New stack: 4
        // LASTORE; Stack: 4
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 8431LL)), -1); else { env->SetLongArrayRegion((jlongArray) cstack0.l, cstack1.i, 1, &cstack2.j); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // ILOAD 2; Stack: 0
        cstack0.i = clocal2.i;
        // New stack: 1
        // ILOAD 5; Stack: 1
        cstack1.i = clocal5.i;
        // New stack: 2
        // IF_ICMPLT L13; Stack: 2
        if (cstack0.i < cstack1.i) goto L13;
        // New stack: 0
        // ALOAD 6; Stack: 0
        cstack0.l = clocal6.l; refs.insert(cstack0.l);
        // New stack: 1
        // PUTSTATIC dev/sakura/client/Mahiro_uT.e [J; Stack: 1
        if (!cclasses[4]  || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cfields[5]) { cfields[5] = env->GetStaticFieldID((cclasses[4]), ((char *)(string_pool + 8443LL)), ((char *)(string_pool + 8445LL))); if (env->ExceptionCheck()) { return (void) 0; }  } env->SetStaticObjectField((cclasses[4]), (cfields[5]), cstack0.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // ICONST_3; Stack: 0
        cstack0.i = 3;
        // New stack: 1
        // ANEWARRAY java/lang/Integer; Stack: 1
        if (!cclasses[3] || env->IsSameObject(cclasses[3], NULL)) { cclasses_mtx[3].lock(); if (!cclasses[3] || env->IsSameObject(cclasses[3], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[2]))) { cclasses[3] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[3].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (cstack0.i < 0) utils::throw_re(env, ((char *)(string_pool + 217LL)), ((char *)(string_pool + 254LL)), -1); else { cstack0.l = env->NewObjectArray(cstack0.i, (cclasses[3]), nullptr); refs.insert(cstack0.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // PUTSTATIC dev/sakura/client/Mahiro_uT.f [Ljava/lang/Integer;; Stack: 1
        if (!cclasses[4]  || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cfields[6]) { cfields[6] = env->GetStaticFieldID((cclasses[4]), ((char *)(string_pool + 8448LL)), ((char *)(string_pool + 8450LL))); if (env->ExceptionCheck()) { return (void) 0; }  } env->SetStaticObjectField((cclasses[4]), (cfields[6]), cstack0.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // GOTO L16; Stack: 0
        goto L16;
        // New stack: 0
        // LABEL L14; Stack: 0
        L14: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // FRAME FULL L: [javax/crypto/Cipher, 1, 1, 1, java/lang/String, 1, [J, [B, 0, 0, 0, 4, javax/crypto/Cipher, 1, 1, 1, java/lang/String, 1, 1, [Ljava/lang/String;, [B] S: [[J, 1, 4, 1]; Stack: 0
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal4.l); refs.erase(clocal6.l); refs.erase(clocal7.l); refs.erase(clocal13.l); refs.erase(clocal17.l); refs.erase(clocal20.l); refs.erase(clocal21.l); 
        utils::clear_refs(env, refs);
        // New stack: 5
        // DUP_X2; Stack: 5
        cstack5 = cstack4; cstack4 = cstack3; cstack3 = cstack2; cstack2 = cstack5;
        // New stack: 6
        // POP; Stack: 6
        ;
        // New stack: 5
        // LSTORE 8; Stack: 5
        clocal8.j = cstack3.j;
        // New stack: 3
        // BIPUSH 8; Stack: 3
        cstack3.i = (jint) 8;
        // New stack: 4
        // NEWARRAY 8; Stack: 4
        if (cstack3.i < 0) utils::throw_re(env, ((char *)(string_pool + 217LL)), ((char *)(string_pool + 1834LL)), -1); else { cstack3.l = env->NewByteArray(cstack3.i); refs.insert(cstack3.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 4
        // DUP; Stack: 4
        cstack4 = cstack3;
        // New stack: 5
        // ICONST_0; Stack: 5
        cstack5.i = 0;
        // New stack: 6
        // LLOAD 8; Stack: 6
        cstack6.j = clocal8.j;
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
        if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 1863LL)), -1); else { utils::bastore(env, (jarray) cstack4.l, cstack5.i, cstack6.i); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 4
        // DUP; Stack: 4
        cstack4 = cstack3;
        // New stack: 5
        // ICONST_1; Stack: 5
        cstack5.i = 1;
        // New stack: 6
        // LLOAD 8; Stack: 6
        cstack6.j = clocal8.j;
        // New stack: 8
        // BIPUSH 48; Stack: 8
        cstack8.i = (jint) 48;
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
        if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 1863LL)), -1); else { utils::bastore(env, (jarray) cstack4.l, cstack5.i, cstack6.i); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 4
        // DUP; Stack: 4
        cstack4 = cstack3;
        // New stack: 5
        // ICONST_2; Stack: 5
        cstack5.i = 2;
        // New stack: 6
        // LLOAD 8; Stack: 6
        cstack6.j = clocal8.j;
        // New stack: 8
        // BIPUSH 40; Stack: 8
        cstack8.i = (jint) 40;
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
        if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 1863LL)), -1); else { utils::bastore(env, (jarray) cstack4.l, cstack5.i, cstack6.i); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 4
        // DUP; Stack: 4
        cstack4 = cstack3;
        // New stack: 5
        // ICONST_3; Stack: 5
        cstack5.i = 3;
        // New stack: 6
        // LLOAD 8; Stack: 6
        cstack6.j = clocal8.j;
        // New stack: 8
        // BIPUSH 32; Stack: 8
        cstack8.i = (jint) 32;
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
        if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 1863LL)), -1); else { utils::bastore(env, (jarray) cstack4.l, cstack5.i, cstack6.i); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 4
        // DUP; Stack: 4
        cstack4 = cstack3;
        // New stack: 5
        // ICONST_4; Stack: 5
        cstack5.i = 4;
        // New stack: 6
        // LLOAD 8; Stack: 6
        cstack6.j = clocal8.j;
        // New stack: 8
        // BIPUSH 24; Stack: 8
        cstack8.i = (jint) 24;
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
        if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 1863LL)), -1); else { utils::bastore(env, (jarray) cstack4.l, cstack5.i, cstack6.i); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 4
        // DUP; Stack: 4
        cstack4 = cstack3;
        // New stack: 5
        // ICONST_5; Stack: 5
        cstack5.i = 5;
        // New stack: 6
        // LLOAD 8; Stack: 6
        cstack6.j = clocal8.j;
        // New stack: 8
        // BIPUSH 16; Stack: 8
        cstack8.i = (jint) 16;
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
        if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 1863LL)), -1); else { utils::bastore(env, (jarray) cstack4.l, cstack5.i, cstack6.i); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 4
        // DUP; Stack: 4
        cstack4 = cstack3;
        // New stack: 5
        // BIPUSH 6; Stack: 5
        cstack5.i = (jint) 6;
        // New stack: 6
        // LLOAD 8; Stack: 6
        cstack6.j = clocal8.j;
        // New stack: 8
        // BIPUSH 8; Stack: 8
        cstack8.i = (jint) 8;
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
        if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 1863LL)), -1); else { utils::bastore(env, (jarray) cstack4.l, cstack5.i, cstack6.i); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 4
        // DUP; Stack: 4
        cstack4 = cstack3;
        // New stack: 5
        // BIPUSH 7; Stack: 5
        cstack5.i = (jint) 7;
        // New stack: 6
        // LLOAD 8; Stack: 6
        cstack6.j = clocal8.j;
        // New stack: 8
        // L2I; Stack: 8
        cstack6.i = (jint) cstack6.j;
        // New stack: 7
        // I2B; Stack: 7
        cstack6.i = (jint) (jbyte) cstack6.i;
        // New stack: 7
        // BASTORE; Stack: 7
        if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 1863LL)), -1); else { utils::bastore(env, (jarray) cstack4.l, cstack5.i, cstack6.i); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 4
        // ALOAD 0; Stack: 4
        cstack4.l = clocal0.l; refs.insert(cstack4.l);
        // New stack: 5
        // SWAP; Stack: 5
        std::swap(cstack4, cstack3);
        // New stack: 5
        // INVOKEVIRTUAL javax/crypto/Cipher.doFinal([B)[B; Stack: 5
        if (!cclasses[25] || env->IsSameObject(cclasses[25], NULL)) { cclasses_mtx[25].lock(); if (!cclasses[25] || env->IsSameObject(cclasses[25], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[41]))) { cclasses[25] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[25].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[36]) { cmethods[36] = env->GetMethodID((cclasses[25]), ((char *)(string_pool + 2209LL)), ((char *)(string_pool + 2217LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack3.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 488LL)), -1); else { cstack3.l = env->CallObjectMethod(cstack3.l, (cmethods[36]), cstack4.l); refs.insert(cstack3.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 4
        // ASTORE 10; Stack: 4
        clocal10.l = cstack3.l; refs.insert(cstack3.l);
        // New stack: 3
        // ALOAD 10; Stack: 3
        cstack3.l = clocal10.l; refs.insert(cstack3.l);
        // New stack: 4
        // ICONST_0; Stack: 4
        cstack4.i = 0;
        // New stack: 5
        // BALOAD; Stack: 5
        if (cstack3.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2304LL)), -1); else { cstack3.i = (jint) utils::baload(env, (jarray) cstack3.l, cstack4.i); } 
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
        // BIPUSH 56; Stack: 5
        cstack5.i = (jint) 56;
        // New stack: 6
        // LSHL; Stack: 6
        cstack3.j = cstack3.j << (0x3f & cstack5.i);
        // New stack: 5
        // ALOAD 10; Stack: 5
        cstack5.l = clocal10.l; refs.insert(cstack5.l);
        // New stack: 6
        // ICONST_1; Stack: 6
        cstack6.i = 1;
        // New stack: 7
        // BALOAD; Stack: 7
        if (cstack5.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2304LL)), -1); else { cstack5.i = (jint) utils::baload(env, (jarray) cstack5.l, cstack6.i); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 6
        // I2L; Stack: 6
        cstack5.j = cstack5.i;
        // New stack: 7
        // LDC 255; Stack: 7
        cstack7.j = 255LL;
        // New stack: 9
        // LAND; Stack: 9
        cstack5.j = cstack5.j & cstack7.j;
        // New stack: 7
        // BIPUSH 48; Stack: 7
        cstack7.i = (jint) 48;
        // New stack: 8
        // LSHL; Stack: 8
        cstack5.j = cstack5.j << (0x3f & cstack7.i);
        // New stack: 7
        // LOR; Stack: 7
        cstack3.j = cstack3.j | cstack5.j;
        // New stack: 5
        // ALOAD 10; Stack: 5
        cstack5.l = clocal10.l; refs.insert(cstack5.l);
        // New stack: 6
        // ICONST_2; Stack: 6
        cstack6.i = 2;
        // New stack: 7
        // BALOAD; Stack: 7
        if (cstack5.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2304LL)), -1); else { cstack5.i = (jint) utils::baload(env, (jarray) cstack5.l, cstack6.i); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 6
        // I2L; Stack: 6
        cstack5.j = cstack5.i;
        // New stack: 7
        // LDC 255; Stack: 7
        cstack7.j = 255LL;
        // New stack: 9
        // LAND; Stack: 9
        cstack5.j = cstack5.j & cstack7.j;
        // New stack: 7
        // BIPUSH 40; Stack: 7
        cstack7.i = (jint) 40;
        // New stack: 8
        // LSHL; Stack: 8
        cstack5.j = cstack5.j << (0x3f & cstack7.i);
        // New stack: 7
        // LOR; Stack: 7
        cstack3.j = cstack3.j | cstack5.j;
        // New stack: 5
        // ALOAD 10; Stack: 5
        cstack5.l = clocal10.l; refs.insert(cstack5.l);
        // New stack: 6
        // ICONST_3; Stack: 6
        cstack6.i = 3;
        // New stack: 7
        // BALOAD; Stack: 7
        if (cstack5.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2304LL)), -1); else { cstack5.i = (jint) utils::baload(env, (jarray) cstack5.l, cstack6.i); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 6
        // I2L; Stack: 6
        cstack5.j = cstack5.i;
        // New stack: 7
        // LDC 255; Stack: 7
        cstack7.j = 255LL;
        // New stack: 9
        // LAND; Stack: 9
        cstack5.j = cstack5.j & cstack7.j;
        // New stack: 7
        // BIPUSH 32; Stack: 7
        cstack7.i = (jint) 32;
        // New stack: 8
        // LSHL; Stack: 8
        cstack5.j = cstack5.j << (0x3f & cstack7.i);
        // New stack: 7
        // LOR; Stack: 7
        cstack3.j = cstack3.j | cstack5.j;
        // New stack: 5
        // ALOAD 10; Stack: 5
        cstack5.l = clocal10.l; refs.insert(cstack5.l);
        // New stack: 6
        // ICONST_4; Stack: 6
        cstack6.i = 4;
        // New stack: 7
        // BALOAD; Stack: 7
        if (cstack5.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2304LL)), -1); else { cstack5.i = (jint) utils::baload(env, (jarray) cstack5.l, cstack6.i); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 6
        // I2L; Stack: 6
        cstack5.j = cstack5.i;
        // New stack: 7
        // LDC 255; Stack: 7
        cstack7.j = 255LL;
        // New stack: 9
        // LAND; Stack: 9
        cstack5.j = cstack5.j & cstack7.j;
        // New stack: 7
        // BIPUSH 24; Stack: 7
        cstack7.i = (jint) 24;
        // New stack: 8
        // LSHL; Stack: 8
        cstack5.j = cstack5.j << (0x3f & cstack7.i);
        // New stack: 7
        // LOR; Stack: 7
        cstack3.j = cstack3.j | cstack5.j;
        // New stack: 5
        // ALOAD 10; Stack: 5
        cstack5.l = clocal10.l; refs.insert(cstack5.l);
        // New stack: 6
        // ICONST_5; Stack: 6
        cstack6.i = 5;
        // New stack: 7
        // BALOAD; Stack: 7
        if (cstack5.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2304LL)), -1); else { cstack5.i = (jint) utils::baload(env, (jarray) cstack5.l, cstack6.i); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 6
        // I2L; Stack: 6
        cstack5.j = cstack5.i;
        // New stack: 7
        // LDC 255; Stack: 7
        cstack7.j = 255LL;
        // New stack: 9
        // LAND; Stack: 9
        cstack5.j = cstack5.j & cstack7.j;
        // New stack: 7
        // BIPUSH 16; Stack: 7
        cstack7.i = (jint) 16;
        // New stack: 8
        // LSHL; Stack: 8
        cstack5.j = cstack5.j << (0x3f & cstack7.i);
        // New stack: 7
        // LOR; Stack: 7
        cstack3.j = cstack3.j | cstack5.j;
        // New stack: 5
        // ALOAD 10; Stack: 5
        cstack5.l = clocal10.l; refs.insert(cstack5.l);
        // New stack: 6
        // BIPUSH 6; Stack: 6
        cstack6.i = (jint) 6;
        // New stack: 7
        // BALOAD; Stack: 7
        if (cstack5.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2304LL)), -1); else { cstack5.i = (jint) utils::baload(env, (jarray) cstack5.l, cstack6.i); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 6
        // I2L; Stack: 6
        cstack5.j = cstack5.i;
        // New stack: 7
        // LDC 255; Stack: 7
        cstack7.j = 255LL;
        // New stack: 9
        // LAND; Stack: 9
        cstack5.j = cstack5.j & cstack7.j;
        // New stack: 7
        // BIPUSH 8; Stack: 7
        cstack7.i = (jint) 8;
        // New stack: 8
        // LSHL; Stack: 8
        cstack5.j = cstack5.j << (0x3f & cstack7.i);
        // New stack: 7
        // LOR; Stack: 7
        cstack3.j = cstack3.j | cstack5.j;
        // New stack: 5
        // ALOAD 10; Stack: 5
        cstack5.l = clocal10.l; refs.insert(cstack5.l);
        // New stack: 6
        // BIPUSH 7; Stack: 6
        cstack6.i = (jint) 7;
        // New stack: 7
        // BALOAD; Stack: 7
        if (cstack5.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2304LL)), -1); else { cstack5.i = (jint) utils::baload(env, (jarray) cstack5.l, cstack6.i); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 6
        // I2L; Stack: 6
        cstack5.j = cstack5.i;
        // New stack: 7
        // LDC 255; Stack: 7
        cstack7.j = 255LL;
        // New stack: 9
        // LAND; Stack: 9
        cstack5.j = cstack5.j & cstack7.j;
        // New stack: 7
        // LOR; Stack: 7
        cstack3.j = cstack3.j | cstack5.j;
        // New stack: 5
        // DUP2_X1; Stack: 5
        cstack5 = cstack3; cstack6 = cstack4; cstack4 = cstack2; cstack3 = cstack6; cstack2 = cstack5;
        // New stack: 7
        // POP2; Stack: 7
        ;
        // New stack: 5
        // POP; Stack: 5
        ;
        // New stack: 4
        // GOTO L15; Stack: 4
        goto L15;
        // New stack: 4
        // LABEL L16; Stack: 4
        L16: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 4
        // FRAME FULL L: [javax/crypto/Cipher, 1, 1, 1, java/lang/String, 1, [J, [B, 4, [B, 4, javax/crypto/Cipher, 1, 1, 1, java/lang/String, 1, 1, [Ljava/lang/String;, [B] S: []; Stack: 4
        refs.erase(clocal0.l); refs.erase(clocal4.l); refs.erase(clocal6.l); refs.erase(clocal7.l); refs.erase(clocal10.l); refs.erase(clocal13.l); refs.erase(clocal17.l); refs.erase(clocal20.l); refs.erase(clocal21.l); 
        utils::clear_refs(env, refs);
        // New stack: 0
        // RETURN; Stack: 0
        return;
        // New stack: 0
        return (void) 0;
    }
    
    // a([B)Ljava/lang/String;
    jobject JNICALL __ngen_native_a6(JNIEnv *env, jclass clazz, jarray arg0) {
        jobject classloader = utils::get_classloader_from_class(env, clazz);
        if (env->ExceptionCheck()) { return (jobject) 0; }
        if (classloader == nullptr) { env->FatalError(((char *)(string_pool + 54LL))); return (jobject) 0; }
    
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
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 1438LL)), -1); else cstack0.i = env->GetArrayLength((jarray) cstack0.l); 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // ISTORE 2; Stack: 2
        clocal2.i = cstack1.i;
        // New stack: 1
        // NEWARRAY 5; Stack: 1
        if (cstack0.i < 0) utils::throw_re(env, ((char *)(string_pool + 217LL)), ((char *)(string_pool + 2275LL)), -1); else { cstack0.l = env->NewCharArray(cstack0.i); refs.insert(cstack0.l); } 
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
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2304LL)), -1); else { cstack1.i = (jint) utils::baload(env, (jarray) cstack1.l, cstack2.i); } 
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
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2315LL)), -1); else { jchar temp = (jchar) cstack2.i; env->SetCharArrayRegion((jcharArray) cstack0.l, cstack1.i, 1, &temp); } 
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
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2304LL)), -1); else { cstack0.i = (jint) utils::baload(env, (jarray) cstack0.l, cstack1.i); } 
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
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2315LL)), -1); else { jchar temp = (jchar) cstack2.i; env->SetCharArrayRegion((jcharArray) cstack0.l, cstack1.i, 1, &temp); } 
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
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2304LL)), -1); else { cstack0.i = (jint) utils::baload(env, (jarray) cstack0.l, cstack1.i); } 
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
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2304LL)), -1); else { cstack0.i = (jint) utils::baload(env, (jarray) cstack0.l, cstack1.i); } 
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
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2315LL)), -1); else { jchar temp = (jchar) cstack2.i; env->SetCharArrayRegion((jcharArray) cstack0.l, cstack1.i, 1, &temp); } 
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
        if (!cclasses[16] || env->IsSameObject(cclasses[16], NULL)) { cclasses_mtx[16].lock(); if (!cclasses[16] || env->IsSameObject(cclasses[16], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[23]))) { cclasses[16] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[16].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (jobject obj = env->AllocObject((cclasses[16]))) { cstack0.l = obj; refs.insert(obj); } 
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
        if (!cclasses[16] || env->IsSameObject(cclasses[16], NULL)) { cclasses_mtx[16].lock(); if (!cclasses[16] || env->IsSameObject(cclasses[16], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[23]))) { cclasses[16] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[16].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[39]) { cmethods[39] = env->GetMethodID((cclasses[16]), ((char *)(string_pool + 631LL)), ((char *)(string_pool + 2327LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 663LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[16]), (cmethods[39]), cstack2.l, cstack3.i, cstack4.i); 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // ARETURN; Stack: 1
        return (jobject) cstack0.l;
        // New stack: 0
        return (jobject) 0;
    }
    
    // a(IJ)Ljava/lang/String;
    jobject JNICALL __ngen_native_a7(JNIEnv *env, jclass clazz, jint arg0, jlong arg1) {
        jobject classloader = utils::get_classloader_from_class(env, clazz);
        if (env->ExceptionCheck()) { return (jobject) 0; }
        if (classloader == nullptr) { env->FatalError(((char *)(string_pool + 54LL))); return (jobject) 0; }
    
        jobject lookup = nullptr;
        // try-catch-class java/lang/Exception
        if (!cclasses[17] || env->IsSameObject(cclasses[17], NULL)) { cclasses_mtx[17].lock(); if (!cclasses[17] || env->IsSameObject(cclasses[17], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[28]))) { cclasses[17] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[17].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } }
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
        // SIPUSH 5932; Stack: 1
        cstack1.i = (jint) 5932;
        // New stack: 2
        // IXOR; Stack: 2
        cstack0.i = cstack0.i ^ cstack1.i;
        // New stack: 1
        // ISTORE 5; Stack: 1
        clocal5.i = cstack0.i;
        // New stack: 0
        // GETSTATIC dev/sakura/client/Mahiro_uT.c [Ljava/lang/String;; Stack: 0
        if (!cclasses[4]  || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cfields[3]) { cfields[3] = env->GetStaticFieldID((cclasses[4]), ((char *)(string_pool + 2175LL)), ((char *)(string_pool + 2155LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } cstack0.l = env->GetStaticObjectField((cclasses[4]), (cfields[3])); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // ILOAD 5; Stack: 1
        cstack1.i = clocal5.i;
        // New stack: 2
        // AALOAD; Stack: 2
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 105LL)), -1); else { cstack0.l = env->GetObjectArrayElement((jobjectArray) cstack0.l, cstack1.i); refs.insert(cstack0.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // IFNONNULL L4; Stack: 1
        if (!env->IsSameObject(cstack0.l, nullptr)) goto L4;
        // New stack: 0
        // LABEL L1; Stack: 0
        L1: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 0
        // INVOKESTATIC java/lang/Thread.currentThread()Ljava/lang/Thread;; Stack: 0
        if (!cclasses[29] || env->IsSameObject(cclasses[29], NULL)) { cclasses_mtx[29].lock(); if (!cclasses[29] || env->IsSameObject(cclasses[29], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[51]))) { cclasses[29] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[29].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[40]) { cmethods[40] = env->GetStaticMethodID((cclasses[29]), ((char *)(string_pool + 2358LL)), ((char *)(string_pool + 2372LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack0.l = env->CallStaticObjectMethod((cclasses[29]), (cmethods[40])); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 1
        // INVOKEVIRTUAL java/lang/Thread.threadId()J; Stack: 1
        if (!cclasses[29] || env->IsSameObject(cclasses[29], NULL)) { cclasses_mtx[29].lock(); if (!cclasses[29] || env->IsSameObject(cclasses[29], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[51]))) { cclasses[29] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[29].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[41]) { cmethods[41] = env->GetMethodID((cclasses[29]), ((char *)(string_pool + 2393LL)), ((char *)(string_pool + 186LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 190LL)), -1); else cstack0.j = env->CallLongMethod(cstack0.l, (cmethods[41])); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 2
        // INVOKESTATIC java/lang/Long.valueOf(J)Ljava/lang/Long;; Stack: 2
        if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { cclasses_mtx[2].lock(); if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[1]))) { cclasses[2] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[2].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[2]) { cmethods[2] = env->GetStaticMethodID((cclasses[2]), ((char *)(string_pool + 2402LL)), ((char *)(string_pool + 2410LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack0.l = env->CallStaticObjectMethod((cclasses[2]), (cmethods[2]), cstack0.j); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 1
        // ASTORE 3; Stack: 1
        clocal3.l = cstack0.l; refs.insert(cstack0.l);
        // New stack: 0
        // GETSTATIC dev/sakura/client/Mahiro_uT.d Ljava/util/Map;; Stack: 0
        if (!cclasses[4]  || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cfields[1]) { cfields[1] = env->GetStaticFieldID((cclasses[4]), ((char *)(string_pool + 1710LL)), ((char *)(string_pool + 1712LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack0.l = env->GetStaticObjectField((cclasses[4]), (cfields[1])); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 1
        // ALOAD 3; Stack: 1
        cstack1.l = clocal3.l; refs.insert(cstack1.l);
        // New stack: 2
        // INVOKEINTERFACE java/util/Map.get(Ljava/lang/Object;)Ljava/lang/Object;; Stack: 2
        if (!cclasses[30] || env->IsSameObject(cclasses[30], NULL)) { cclasses_mtx[30].lock(); if (!cclasses[30] || env->IsSameObject(cclasses[30], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[52]))) { cclasses[30] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[30].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[42]) { cmethods[42] = env->GetMethodID((cclasses[30]), ((char *)(string_pool + 2430LL)), ((char *)(string_pool + 2434LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 574LL)), -1); else { cstack0.l = env->CallObjectMethod(cstack0.l, (cmethods[42]), cstack1.l); refs.insert(cstack0.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 1
        // CHECKCAST [Ljava/lang/Object;; Stack: 1
        if (!cclasses[31] || env->IsSameObject(cclasses[31], NULL)) { cclasses_mtx[31].lock(); if (!cclasses[31] || env->IsSameObject(cclasses[31], NULL)) { if (jclass clazz = env->FindClass(((char *)(string_pool + 2473LL)))) { cclasses[31] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[31].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack0.l != nullptr && !env->IsInstanceOf(cstack0.l, (cclasses[31]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 2473LL)))).c_str(), -1); 
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
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack0.i < 0) utils::throw_re(env, ((char *)(string_pool + 217LL)), ((char *)(string_pool + 254LL)), -1); else { cstack0.l = env->NewObjectArray(cstack0.i, (cclasses[5]), nullptr); refs.insert(cstack0.l); } 
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
        cstack2.l = (cstrings[40]);
        // New stack: 3
        // INVOKESTATIC javax/crypto/Cipher.getInstance(Ljava/lang/String;)Ljavax/crypto/Cipher;; Stack: 3
        if (!cclasses[25] || env->IsSameObject(cclasses[25], NULL)) { cclasses_mtx[25].lock(); if (!cclasses[25] || env->IsSameObject(cclasses[25], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[41]))) { cclasses[25] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[25].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[26]) { cmethods[26] = env->GetStaticMethodID((cclasses[25]), ((char *)(string_pool + 1728LL)), ((char *)(string_pool + 1740LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack2.l = env->CallStaticObjectMethod((cclasses[25]), (cmethods[26]), cstack2.l); refs.insert(cstack2.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 3
        // AASTORE; Stack: 3
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2106LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack0.l, cstack1.i, cstack2.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 0
        // ALOAD 4; Stack: 0
        cstack0.l = clocal4.l; refs.insert(cstack0.l);
        // New stack: 1
        // ICONST_1; Stack: 1
        cstack1.i = 1;
        // New stack: 2
        // LDC DES; Stack: 2
        cstack2.l = (cstrings[42]);
        // New stack: 3
        // INVOKESTATIC javax/crypto/SecretKeyFactory.getInstance(Ljava/lang/String;)Ljavax/crypto/SecretKeyFactory;; Stack: 3
        if (!cclasses[26] || env->IsSameObject(cclasses[26], NULL)) { cclasses_mtx[26].lock(); if (!cclasses[26] || env->IsSameObject(cclasses[26], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[43]))) { cclasses[26] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[26].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[27]) { cmethods[27] = env->GetStaticMethodID((cclasses[26]), ((char *)(string_pool + 1728LL)), ((char *)(string_pool + 1782LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack2.l = env->CallStaticObjectMethod((cclasses[26]), (cmethods[27]), cstack2.l); refs.insert(cstack2.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 3
        // AASTORE; Stack: 3
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2106LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack0.l, cstack1.i, cstack2.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 0
        // ALOAD 4; Stack: 0
        cstack0.l = clocal4.l; refs.insert(cstack0.l);
        // New stack: 1
        // ICONST_2; Stack: 1
        cstack1.i = 2;
        // New stack: 2
        // NEW javax/crypto/spec/IvParameterSpec; Stack: 2
        if (!cclasses[28] || env->IsSameObject(cclasses[28], NULL)) { cclasses_mtx[28].lock(); if (!cclasses[28] || env->IsSameObject(cclasses[28], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[45]))) { cclasses[28] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[28].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (jobject obj = env->AllocObject((cclasses[28]))) { cstack2.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 3
        // DUP; Stack: 3
        cstack3 = cstack2;
        // New stack: 4
        // BIPUSH 8; Stack: 4
        cstack4.i = (jint) 8;
        // New stack: 5
        // NEWARRAY 8; Stack: 5
        if (cstack4.i < 0) utils::throw_re(env, ((char *)(string_pool + 217LL)), ((char *)(string_pool + 1834LL)), -1); else { cstack4.l = env->NewByteArray(cstack4.i); refs.insert(cstack4.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 5
        // INVOKESPECIAL javax/crypto/spec/IvParameterSpec.<init>([B)V; Stack: 5
        if (!cclasses[28] || env->IsSameObject(cclasses[28], NULL)) { cclasses_mtx[28].lock(); if (!cclasses[28] || env->IsSameObject(cclasses[28], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[45]))) { cclasses[28] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[28].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[30]) { cmethods[30] = env->GetMethodID((cclasses[28]), ((char *)(string_pool + 631LL)), ((char *)(string_pool + 1007LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack3.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 663LL)), -1); else env->CallNonvirtualVoidMethod(cstack3.l, (cclasses[28]), (cmethods[30]), cstack4.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 3
        // AASTORE; Stack: 3
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2106LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack0.l, cstack1.i, cstack2.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 0
        // GETSTATIC dev/sakura/client/Mahiro_uT.d Ljava/util/Map;; Stack: 0
        if (!cclasses[4]  || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cfields[1]) { cfields[1] = env->GetStaticFieldID((cclasses[4]), ((char *)(string_pool + 1710LL)), ((char *)(string_pool + 1712LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack0.l = env->GetStaticObjectField((cclasses[4]), (cfields[1])); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 1
        // ALOAD 3; Stack: 1
        cstack1.l = clocal3.l; refs.insert(cstack1.l);
        // New stack: 2
        // ALOAD 4; Stack: 2
        cstack2.l = clocal4.l; refs.insert(cstack2.l);
        // New stack: 3
        // INVOKEINTERFACE java/util/Map.put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;; Stack: 3
        if (!cclasses[30] || env->IsSameObject(cclasses[30], NULL)) { cclasses_mtx[30].lock(); if (!cclasses[30] || env->IsSameObject(cclasses[30], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[52]))) { cclasses[30] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[30].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[43]) { cmethods[43] = env->GetMethodID((cclasses[30]), ((char *)(string_pool + 2493LL)), ((char *)(string_pool + 2497LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 574LL)), -1); else { cstack0.l = env->CallObjectMethod(cstack0.l, (cmethods[43]), cstack1.l, cstack2.l); refs.insert(cstack0.l); } 
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
        if (!cclasses[32] || env->IsSameObject(cclasses[32], NULL)) { cclasses_mtx[32].lock(); if (!cclasses[32] || env->IsSameObject(cclasses[32], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[53]))) { cclasses[32] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[32].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (jobject obj = env->AllocObject((cclasses[32]))) { cstack0.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // LDC dev/sakura/client/Mahiro_uT; Stack: 2
        cstack2.l = (cstrings[54]);
        // New stack: 3
        // ALOAD 9; Stack: 3
        cstack3.l = clocal9.l; refs.insert(cstack3.l);
        // New stack: 4
        // INVOKESPECIAL java/lang/RuntimeException.<init>(Ljava/lang/String;Ljava/lang/Throwable;)V; Stack: 4
        if (!cclasses[32] || env->IsSameObject(cclasses[32], NULL)) { cclasses_mtx[32].lock(); if (!cclasses[32] || env->IsSameObject(cclasses[32], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[53]))) { cclasses[32] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[32].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[44]) { cmethods[44] = env->GetMethodID((cclasses[32]), ((char *)(string_pool + 631LL)), ((char *)(string_pool + 2554LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 663LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[32]), (cmethods[44]), cstack2.l, cstack3.l); 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 686LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
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
        if (cstack0.i < 0) utils::throw_re(env, ((char *)(string_pool + 217LL)), ((char *)(string_pool + 1834LL)), -1); else { cstack0.l = env->NewByteArray(cstack0.i); refs.insert(cstack0.l); } 
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
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 1863LL)), -1); else { utils::bastore(env, (jarray) cstack0.l, cstack1.i, cstack2.i); } 
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
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 1863LL)), -1); else { utils::bastore(env, (jarray) cstack0.l, cstack1.i, cstack2.i); } 
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
        if (!cclasses[27] || env->IsSameObject(cclasses[27], NULL)) { cclasses_mtx[27].lock(); if (!cclasses[27] || env->IsSameObject(cclasses[27], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[44]))) { cclasses[27] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[27].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (jobject obj = env->AllocObject((cclasses[27]))) { cstack0.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // ALOAD 6; Stack: 2
        cstack2.l = clocal6.l; refs.insert(cstack2.l);
        // New stack: 3
        // INVOKESPECIAL javax/crypto/spec/DESKeySpec.<init>([B)V; Stack: 3
        if (!cclasses[27] || env->IsSameObject(cclasses[27], NULL)) { cclasses_mtx[27].lock(); if (!cclasses[27] || env->IsSameObject(cclasses[27], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[44]))) { cclasses[27] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[27].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[28]) { cmethods[28] = env->GetMethodID((cclasses[27]), ((char *)(string_pool + 631LL)), ((char *)(string_pool + 1007LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 663LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[27]), (cmethods[28]), cstack2.l); 
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
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 105LL)), -1); else { cstack0.l = env->GetObjectArrayElement((jobjectArray) cstack0.l, cstack1.i); refs.insert(cstack0.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // CHECKCAST javax/crypto/SecretKeyFactory; Stack: 1
        if (!cclasses[26] || env->IsSameObject(cclasses[26], NULL)) { cclasses_mtx[26].lock(); if (!cclasses[26] || env->IsSameObject(cclasses[26], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[43]))) { cclasses[26] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[26].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (cstack0.l != nullptr && !env->IsInstanceOf(cstack0.l, (cclasses[26]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 2597LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (jobject) 0; } } 
        // New stack: 1
        // ALOAD 7; Stack: 1
        cstack1.l = clocal7.l; refs.insert(cstack1.l);
        // New stack: 2
        // INVOKEVIRTUAL javax/crypto/SecretKeyFactory.generateSecret(Ljava/security/spec/KeySpec;)Ljavax/crypto/SecretKey;; Stack: 2
        if (!cclasses[26] || env->IsSameObject(cclasses[26], NULL)) { cclasses_mtx[26].lock(); if (!cclasses[26] || env->IsSameObject(cclasses[26], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[43]))) { cclasses[26] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[26].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[29]) { cmethods[29] = env->GetMethodID((cclasses[26]), ((char *)(string_pool + 1875LL)), ((char *)(string_pool + 1890LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 488LL)), -1); else { cstack0.l = env->CallObjectMethod(cstack0.l, (cmethods[29]), cstack1.l); refs.insert(cstack0.l); } 
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
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 105LL)), -1); else { cstack0.l = env->GetObjectArrayElement((jobjectArray) cstack0.l, cstack1.i); refs.insert(cstack0.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // CHECKCAST javax/crypto/Cipher; Stack: 1
        if (!cclasses[25] || env->IsSameObject(cclasses[25], NULL)) { cclasses_mtx[25].lock(); if (!cclasses[25] || env->IsSameObject(cclasses[25], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[41]))) { cclasses[25] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[25].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (cstack0.l != nullptr && !env->IsInstanceOf(cstack0.l, (cclasses[25]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 2627LL)))).c_str(), -1); 
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
        if (cstack3.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 105LL)), -1); else { cstack3.l = env->GetObjectArrayElement((jobjectArray) cstack3.l, cstack4.i); refs.insert(cstack3.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 4
        // CHECKCAST javax/crypto/spec/IvParameterSpec; Stack: 4
        if (!cclasses[28] || env->IsSameObject(cclasses[28], NULL)) { cclasses_mtx[28].lock(); if (!cclasses[28] || env->IsSameObject(cclasses[28], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[45]))) { cclasses[28] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[28].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (cstack3.l != nullptr && !env->IsInstanceOf(cstack3.l, (cclasses[28]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 2647LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (jobject) 0; } } 
        // New stack: 4
        // INVOKEVIRTUAL javax/crypto/Cipher.init(ILjava/security/Key;Ljava/security/spec/AlgorithmParameterSpec;)V; Stack: 4
        if (!cclasses[25] || env->IsSameObject(cclasses[25], NULL)) { cclasses_mtx[25].lock(); if (!cclasses[25] || env->IsSameObject(cclasses[25], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[41]))) { cclasses[25] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[25].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[31]) { cmethods[31] = env->GetMethodID((cclasses[25]), ((char *)(string_pool + 1945LL)), ((char *)(string_pool + 1950LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2017LL)), -1); else env->CallVoidMethod(cstack0.l, (cmethods[31]), cstack1.i, cstack2.l, cstack3.l); 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 0
        // GETSTATIC dev/sakura/client/Mahiro_uT.b [Ljava/lang/String;; Stack: 0
        if (!cclasses[4]  || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cfields[2]) { cfields[2] = env->GetStaticFieldID((cclasses[4]), ((char *)(string_pool + 2153LL)), ((char *)(string_pool + 2155LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } cstack0.l = env->GetStaticObjectField((cclasses[4]), (cfields[2])); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // ILOAD 5; Stack: 1
        cstack1.i = clocal5.i;
        // New stack: 2
        // AALOAD; Stack: 2
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 105LL)), -1); else { cstack0.l = env->GetObjectArrayElement((jobjectArray) cstack0.l, cstack1.i); refs.insert(cstack0.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // LDC ISO-8859-1; Stack: 1
        cstack1.l = (cstrings[48]);
        // New stack: 2
        // INVOKEVIRTUAL java/lang/String.getBytes(Ljava/lang/String;)[B; Stack: 2
        if (!cclasses[16] || env->IsSameObject(cclasses[16], NULL)) { cclasses_mtx[16].lock(); if (!cclasses[16] || env->IsSameObject(cclasses[16], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[23]))) { cclasses[16] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[16].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[35]) { cmethods[35] = env->GetMethodID((cclasses[16]), ((char *)(string_pool + 2177LL)), ((char *)(string_pool + 2186LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 488LL)), -1); else { cstack0.l = env->CallObjectMethod(cstack0.l, (cmethods[35]), cstack1.l); refs.insert(cstack0.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // ASTORE 9; Stack: 1
        clocal9.l = cstack0.l; refs.insert(cstack0.l);
        // New stack: 0
        // GETSTATIC dev/sakura/client/Mahiro_uT.c [Ljava/lang/String;; Stack: 0
        if (!cclasses[4]  || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cfields[3]) { cfields[3] = env->GetStaticFieldID((cclasses[4]), ((char *)(string_pool + 2175LL)), ((char *)(string_pool + 2155LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } cstack0.l = env->GetStaticObjectField((cclasses[4]), (cfields[3])); refs.insert(cstack0.l); 
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
        if (cstack2.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 105LL)), -1); else { cstack2.l = env->GetObjectArrayElement((jobjectArray) cstack2.l, cstack3.i); refs.insert(cstack2.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 3
        // CHECKCAST javax/crypto/Cipher; Stack: 3
        if (!cclasses[25] || env->IsSameObject(cclasses[25], NULL)) { cclasses_mtx[25].lock(); if (!cclasses[25] || env->IsSameObject(cclasses[25], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[41]))) { cclasses[25] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[25].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (cstack2.l != nullptr && !env->IsInstanceOf(cstack2.l, (cclasses[25]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 2627LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (jobject) 0; } } 
        // New stack: 3
        // ALOAD 9; Stack: 3
        cstack3.l = clocal9.l; refs.insert(cstack3.l);
        // New stack: 4
        // INVOKEVIRTUAL javax/crypto/Cipher.doFinal([B)[B; Stack: 4
        if (!cclasses[25] || env->IsSameObject(cclasses[25], NULL)) { cclasses_mtx[25].lock(); if (!cclasses[25] || env->IsSameObject(cclasses[25], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[41]))) { cclasses[25] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[25].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[36]) { cmethods[36] = env->GetMethodID((cclasses[25]), ((char *)(string_pool + 2209LL)), ((char *)(string_pool + 2217LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack2.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 488LL)), -1); else { cstack2.l = env->CallObjectMethod(cstack2.l, (cmethods[36]), cstack3.l); refs.insert(cstack2.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 3
        // INVOKESTATIC dev/sakura/client/Mahiro_uT.a([B)Ljava/lang/String;; Stack: 3
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[37]) { cmethods[37] = env->GetStaticMethodID((cclasses[4]), ((char *)(string_pool + 213LL)), ((char *)(string_pool + 2224LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } cstack2.l = env->CallStaticObjectMethod((cclasses[4]), (cmethods[37]), cstack2.l); refs.insert(cstack2.l); 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 3
        // AASTORE; Stack: 3
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2106LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack0.l, cstack1.i, cstack2.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 0
        // LABEL L4; Stack: 0
        L4: if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 0
        // FRAME FULL L: [1, 4, 0, 0, 1] S: []; Stack: 0
        utils::clear_refs(env, refs);
        // New stack: 0
        // GETSTATIC dev/sakura/client/Mahiro_uT.c [Ljava/lang/String;; Stack: 0
        if (!cclasses[4]  || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cfields[3]) { cfields[3] = env->GetStaticFieldID((cclasses[4]), ((char *)(string_pool + 2175LL)), ((char *)(string_pool + 2155LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } cstack0.l = env->GetStaticObjectField((cclasses[4]), (cfields[3])); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // ILOAD 5; Stack: 1
        cstack1.i = clocal5.i;
        // New stack: 2
        // AALOAD; Stack: 2
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 105LL)), -1); else { cstack0.l = env->GetObjectArrayElement((jobjectArray) cstack0.l, cstack1.i); refs.insert(cstack0.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // ARETURN; Stack: 1
        return (jobject) cstack0.l;
        // New stack: 0
        return (jobject) 0;
        L_CATCH_0: if (env->IsInstanceOf(cstack0.l, (cclasses[17]))) { goto L3; }
        env->Throw((jthrowable) cstack0.l); return (jobject) 0;
    }
    
    // a(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/invoke/MutableCallSite;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;
    jobject JNICALL __ngen_native_a8(JNIEnv *env, jclass clazz, jobject arg0, jobject arg1, jobject arg2, jarray arg3) {
        jobject classloader = utils::get_classloader_from_class(env, clazz);
        if (env->ExceptionCheck()) { return (jobject) 0; }
        if (classloader == nullptr) { env->FatalError(((char *)(string_pool + 54LL))); return (jobject) 0; }
    
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
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 105LL)), -1); else { cstack0.l = env->GetObjectArrayElement((jobjectArray) cstack0.l, cstack1.i); refs.insert(cstack0.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // CHECKCAST java/lang/Integer; Stack: 1
        if (!cclasses[3] || env->IsSameObject(cclasses[3], NULL)) { cclasses_mtx[3].lock(); if (!cclasses[3] || env->IsSameObject(cclasses[3], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[2]))) { cclasses[3] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[3].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (cstack0.l != nullptr && !env->IsInstanceOf(cstack0.l, (cclasses[3]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 2812LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (jobject) 0; } } 
        // New stack: 1
        // INVOKEVIRTUAL java/lang/Integer.intValue()I; Stack: 1
        if (!cclasses[3] || env->IsSameObject(cclasses[3], NULL)) { cclasses_mtx[3].lock(); if (!cclasses[3] || env->IsSameObject(cclasses[3], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[2]))) { cclasses[3] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[3].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[1]) { cmethods[1] = env->GetMethodID((cclasses[3]), ((char *)(string_pool + 2830LL)), ((char *)(string_pool + 2047LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2051LL)), -1); else cstack0.i = env->CallIntMethod(cstack0.l, (cmethods[1])); 
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
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 105LL)), -1); else { cstack0.l = env->GetObjectArrayElement((jobjectArray) cstack0.l, cstack1.i); refs.insert(cstack0.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // CHECKCAST java/lang/Long; Stack: 1
        if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { cclasses_mtx[2].lock(); if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[1]))) { cclasses[2] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[2].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (cstack0.l != nullptr && !env->IsInstanceOf(cstack0.l, (cclasses[2]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 161LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (jobject) 0; } } 
        // New stack: 1
        // INVOKEVIRTUAL java/lang/Long.longValue()J; Stack: 1
        if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { cclasses_mtx[2].lock(); if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[1]))) { cclasses[2] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[2].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[0]) { cmethods[0] = env->GetMethodID((cclasses[2]), ((char *)(string_pool + 176LL)), ((char *)(string_pool + 186LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 190LL)), -1); else cstack0.j = env->CallLongMethod(cstack0.l, (cmethods[0])); 
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
        // INVOKESTATIC dev/sakura/client/Mahiro_uT.a(IJ)Ljava/lang/String;; Stack: 3
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[45]) { cmethods[45] = env->GetStaticMethodID((cclasses[4]), ((char *)(string_pool + 213LL)), ((char *)(string_pool + 2335LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } cstack0.l = env->CallStaticObjectMethod((cclasses[4]), (cmethods[45]), cstack0.i, cstack1.j); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // ASTORE 7; Stack: 1
        clocal7.l = cstack0.l; refs.insert(cstack0.l);
        // New stack: 0
        // LDC Ljava/lang/String;; Stack: 0
        if (!cclasses[33] || env->IsSameObject(cclasses[33], NULL)) { cclasses_mtx[33].lock(); if (!cclasses[33] || env->IsSameObject(cclasses[33], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[23]))) { cclasses[33] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[33].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } cstack0.l = (cclasses[33]);
        // New stack: 1
        // ALOAD 7; Stack: 1
        cstack1.l = clocal7.l; refs.insert(cstack1.l);
        // New stack: 2
        // INVOKESTATIC java/lang/invoke/MethodHandles.constant(Ljava/lang/Class;Ljava/lang/Object;)Ljava/lang/invoke/MethodHandle;; Stack: 2
        if (!cclasses[21] || env->IsSameObject(cclasses[21], NULL)) { cclasses_mtx[21].lock(); if (!cclasses[21] || env->IsSameObject(cclasses[21], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[36]))) { cclasses[21] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[21].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[46]) { cmethods[46] = env->GetStaticMethodID((cclasses[21]), ((char *)(string_pool + 2839LL)), ((char *)(string_pool + 2848LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } cstack0.l = env->CallStaticObjectMethod((cclasses[21]), (cmethods[46]), cstack0.l, cstack1.l); refs.insert(cstack0.l); 
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
        if (!cclasses[34] || env->IsSameObject(cclasses[34], NULL)) { cclasses_mtx[34].lock(); if (!cclasses[34] || env->IsSameObject(cclasses[34], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[55]))) { cclasses[34] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[34].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (cstack3.i < 0) utils::throw_re(env, ((char *)(string_pool + 217LL)), ((char *)(string_pool + 254LL)), -1); else { cstack3.l = env->NewObjectArray(cstack3.i, (cclasses[34]), nullptr); refs.insert(cstack3.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 4
        // DUP; Stack: 4
        cstack4 = cstack3;
        // New stack: 5
        // ICONST_0; Stack: 5
        cstack5.i = 0;
        // New stack: 6
        // GETSTATIC java/lang/Integer.TYPE Ljava/lang/Class;; Stack: 6
        if (!cclasses[3]  || env->IsSameObject(cclasses[3], NULL)) { cclasses_mtx[3].lock(); if (!cclasses[3] || env->IsSameObject(cclasses[3], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[2]))) { cclasses[3] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[3].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cfields[7]) { cfields[7] = env->GetStaticFieldID((cclasses[3]), ((char *)(string_pool + 2917LL)), ((char *)(string_pool + 2922LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } cstack6.l = env->GetStaticObjectField((cclasses[3]), (cfields[7])); refs.insert(cstack6.l); 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 7
        // AASTORE; Stack: 7
        if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2106LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack4.l, cstack5.i, cstack6.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 4
        // DUP; Stack: 4
        cstack4 = cstack3;
        // New stack: 5
        // ICONST_1; Stack: 5
        cstack5.i = 1;
        // New stack: 6
        // GETSTATIC java/lang/Long.TYPE Ljava/lang/Class;; Stack: 6
        if (!cclasses[2]  || env->IsSameObject(cclasses[2], NULL)) { cclasses_mtx[2].lock(); if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[1]))) { cclasses[2] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[2].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cfields[8]) { cfields[8] = env->GetStaticFieldID((cclasses[2]), ((char *)(string_pool + 2917LL)), ((char *)(string_pool + 2922LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } cstack6.l = env->GetStaticObjectField((cclasses[2]), (cfields[8])); refs.insert(cstack6.l); 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 7
        // AASTORE; Stack: 7
        if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2106LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack4.l, cstack5.i, cstack6.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 4
        // INVOKESTATIC java/lang/invoke/MethodHandles.dropArguments(Ljava/lang/invoke/MethodHandle;I[Ljava/lang/Class;)Ljava/lang/invoke/MethodHandle;; Stack: 4
        if (!cclasses[21] || env->IsSameObject(cclasses[21], NULL)) { cclasses_mtx[21].lock(); if (!cclasses[21] || env->IsSameObject(cclasses[21], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[36]))) { cclasses[21] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[21].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[47]) { cmethods[47] = env->GetStaticMethodID((cclasses[21]), ((char *)(string_pool + 2940LL)), ((char *)(string_pool + 2954LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } cstack1.l = env->CallStaticObjectMethod((cclasses[21]), (cmethods[47]), cstack1.l, cstack2.i, cstack3.l); refs.insert(cstack1.l); 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 2
        // INVOKEVIRTUAL java/lang/invoke/MutableCallSite.setTarget(Ljava/lang/invoke/MethodHandle;)V; Stack: 2
        if (!cclasses[35] || env->IsSameObject(cclasses[35], NULL)) { cclasses_mtx[35].lock(); if (!cclasses[35] || env->IsSameObject(cclasses[35], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[56]))) { cclasses[35] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[35].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[48]) { cmethods[48] = env->GetMethodID((cclasses[35]), ((char *)(string_pool + 3038LL)), ((char *)(string_pool + 3048LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2017LL)), -1); else env->CallVoidMethod(cstack0.l, (cmethods[48]), cstack1.l); 
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
    jobject JNICALL __ngen_native_a9(JNIEnv *env, jclass clazz, jobject arg0, jobject arg1, jobject arg2) {
        jobject classloader = utils::get_classloader_from_class(env, clazz);
        if (env->ExceptionCheck()) { return (jobject) 0; }
        if (classloader == nullptr) { env->FatalError(((char *)(string_pool + 54LL))); return (jobject) 0; }
    
        jobject lookup = nullptr;
        // try-catch-class java/lang/Exception
        if (!cclasses[17] || env->IsSameObject(cclasses[17], NULL)) { cclasses_mtx[17].lock(); if (!cclasses[17] || env->IsSameObject(cclasses[17], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[28]))) { cclasses[17] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[17].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } }
        jvalue cstack0 = {}, cstack1 = {}, cstack2 = {}, cstack3 = {}, cstack4 = {}, cstack5 = {}, cstack6 = {};
        jvalue clocal0 = {}, clocal1 = {}, clocal2 = {}, clocal3 = {}, clocal4 = {};
        std::unordered_set<jobject> refs;
    
        clocal0.l = arg0; refs.insert(clocal0.l);
        clocal1.l = arg1; refs.insert(clocal1.l);
        clocal2.l = arg2; refs.insert(clocal2.l);
    
        // NEW java/lang/invoke/MutableCallSite; Stack: 0
        if (!cclasses[35] || env->IsSameObject(cclasses[35], NULL)) { cclasses_mtx[35].lock(); if (!cclasses[35] || env->IsSameObject(cclasses[35], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[56]))) { cclasses[35] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[35].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (jobject obj = env->AllocObject((cclasses[35]))) { cstack0.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // ALOAD 2; Stack: 2
        cstack2.l = clocal2.l; refs.insert(cstack2.l);
        // New stack: 3
        // INVOKESPECIAL java/lang/invoke/MutableCallSite.<init>(Ljava/lang/invoke/MethodType;)V; Stack: 3
        if (!cclasses[35] || env->IsSameObject(cclasses[35], NULL)) { cclasses_mtx[35].lock(); if (!cclasses[35] || env->IsSameObject(cclasses[35], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[56]))) { cclasses[35] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[35].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[49]) { cmethods[49] = env->GetMethodID((cclasses[35]), ((char *)(string_pool + 631LL)), ((char *)(string_pool + 3199LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 663LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[35]), (cmethods[49]), cstack2.l); 
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
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.11716030067971395.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 1
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack1.l = lookup;
        // New stack: 2
        // LDC Ldev/sakura/client/Mahiro_uT;; Stack: 2
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack2.l = (cclasses[6]);
        // New stack: 3
        // LDC a; Stack: 3
        cstack3.l = (cstrings[6]);
        // New stack: 4
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/invoke/MutableCallSite;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;; Stack: 4
        cstack4.l = (cstrings[57]);
        // New stack: 5
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.11716030067971395.a()Ljava/lang/ClassLoader;; Stack: 5
        cstack5.l = classloader;
        // New stack: 6
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 6
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[3]) { cmethods[3] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 279LL)), ((char *)(string_pool + 306LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack4.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[3]), cstack4.l, cstack5.l); refs.insert(cstack4.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 5
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 5
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 379LL)), ((char *)(string_pool + 390LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 488LL)), -1); else { cstack1.l = env->CallObjectMethod(cstack1.l, (cmethods[4]), cstack2.l, cstack3.l, cstack4.l); refs.insert(cstack1.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 2
        // LDC [Ljava/lang/Object;; Stack: 2
        if (!cclasses[31] || env->IsSameObject(cclasses[31], NULL)) { cclasses_mtx[31].lock(); if (!cclasses[31] || env->IsSameObject(cclasses[31], NULL)) { if (jclass clazz = env->FindClass(((char *)(string_pool + 2473LL)))) { cclasses[31] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[31].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack2.l = (cclasses[31]);
        // New stack: 3
        // ALOAD 2; Stack: 3
        cstack3.l = clocal2.l; refs.insert(cstack3.l);
        // New stack: 4
        // INVOKEVIRTUAL java/lang/invoke/MethodType.parameterCount()I; Stack: 4
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[50]) { cmethods[50] = env->GetMethodID((cclasses[8]), ((char *)(string_pool + 3232LL)), ((char *)(string_pool + 2047LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack3.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2051LL)), -1); else cstack3.i = env->CallIntMethod(cstack3.l, (cmethods[50])); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 4
        // INVOKEVIRTUAL java/lang/invoke/MethodHandle.asCollector(Ljava/lang/Class;I)Ljava/lang/invoke/MethodHandle;; Stack: 4
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[51]) { cmethods[51] = env->GetMethodID((cclasses[11]), ((char *)(string_pool + 3247LL)), ((char *)(string_pool + 3259LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 488LL)), -1); else { cstack1.l = env->CallObjectMethod(cstack1.l, (cmethods[51]), cstack2.l, cstack3.i); refs.insert(cstack1.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 2
        // ICONST_0; Stack: 2
        cstack2.i = 0;
        // New stack: 3
        // ICONST_3; Stack: 3
        cstack3.i = 3;
        // New stack: 4
        // ANEWARRAY java/lang/Object; Stack: 4
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack3.i < 0) utils::throw_re(env, ((char *)(string_pool + 217LL)), ((char *)(string_pool + 254LL)), -1); else { cstack3.l = env->NewObjectArray(cstack3.i, (cclasses[5]), nullptr); refs.insert(cstack3.l); } 
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
        if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2106LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack4.l, cstack5.i, cstack6.l); } 
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
        if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2106LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack4.l, cstack5.i, cstack6.l); } 
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
        if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2106LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack4.l, cstack5.i, cstack6.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 4
        // INVOKESTATIC java/lang/invoke/MethodHandles.insertArguments(Ljava/lang/invoke/MethodHandle;I[Ljava/lang/Object;)Ljava/lang/invoke/MethodHandle;; Stack: 4
        if (!cclasses[21] || env->IsSameObject(cclasses[21], NULL)) { cclasses_mtx[21].lock(); if (!cclasses[21] || env->IsSameObject(cclasses[21], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[36]))) { cclasses[21] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[21].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[52]) { cmethods[52] = env->GetStaticMethodID((cclasses[21]), ((char *)(string_pool + 3311LL)), ((char *)(string_pool + 3327LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack1.l = env->CallStaticObjectMethod((cclasses[21]), (cmethods[52]), cstack1.l, cstack2.i, cstack3.l); refs.insert(cstack1.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 2
        // ALOAD 2; Stack: 2
        cstack2.l = clocal2.l; refs.insert(cstack2.l);
        // New stack: 3
        // INVOKESTATIC java/lang/invoke/MethodHandles.explicitCastArguments(Ljava/lang/invoke/MethodHandle;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 3
        if (!cclasses[21] || env->IsSameObject(cclasses[21], NULL)) { cclasses_mtx[21].lock(); if (!cclasses[21] || env->IsSameObject(cclasses[21], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[36]))) { cclasses[21] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[21].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[53]) { cmethods[53] = env->GetStaticMethodID((cclasses[21]), ((char *)(string_pool + 3412LL)), ((char *)(string_pool + 3434LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack1.l = env->CallStaticObjectMethod((cclasses[21]), (cmethods[53]), cstack1.l, cstack2.l); refs.insert(cstack1.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 2
        // INVOKEVIRTUAL java/lang/invoke/MutableCallSite.setTarget(Ljava/lang/invoke/MethodHandle;)V; Stack: 2
        if (!cclasses[35] || env->IsSameObject(cclasses[35], NULL)) { cclasses_mtx[35].lock(); if (!cclasses[35] || env->IsSameObject(cclasses[35], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[56]))) { cclasses[35] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[35].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[48]) { cmethods[48] = env->GetMethodID((cclasses[35]), ((char *)(string_pool + 3038LL)), ((char *)(string_pool + 3048LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2017LL)), -1); else env->CallVoidMethod(cstack0.l, (cmethods[48]), cstack1.l); 
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
        if (!cclasses[32] || env->IsSameObject(cclasses[32], NULL)) { cclasses_mtx[32].lock(); if (!cclasses[32] || env->IsSameObject(cclasses[32], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[53]))) { cclasses[32] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[32].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (jobject obj = env->AllocObject((cclasses[32]))) { cstack0.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // NEW java/lang/StringBuilder; Stack: 2
        if (!cclasses[36] || env->IsSameObject(cclasses[36], NULL)) { cclasses_mtx[36].lock(); if (!cclasses[36] || env->IsSameObject(cclasses[36], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[58]))) { cclasses[36] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[36].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (jobject obj = env->AllocObject((cclasses[36]))) { cstack2.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 3
        // DUP; Stack: 3
        cstack3 = cstack2;
        // New stack: 4
        // INVOKESPECIAL java/lang/StringBuilder.<init>()V; Stack: 4
        if (!cclasses[36] || env->IsSameObject(cclasses[36], NULL)) { cclasses_mtx[36].lock(); if (!cclasses[36] || env->IsSameObject(cclasses[36], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[58]))) { cclasses[36] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[36].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[54]) { cmethods[54] = env->GetMethodID((cclasses[36]), ((char *)(string_pool + 631LL)), ((char *)(string_pool + 3528LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack3.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 663LL)), -1); else env->CallNonvirtualVoidMethod(cstack3.l, (cclasses[36]), (cmethods[54])); 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 3
        // LDC dev/sakura/client/Mahiro_uT; Stack: 3
        cstack3.l = (cstrings[54]);
        // New stack: 4
        // INVOKEVIRTUAL java/lang/StringBuilder.append(Ljava/lang/String;)Ljava/lang/StringBuilder;; Stack: 4
        if (!cclasses[36] || env->IsSameObject(cclasses[36], NULL)) { cclasses_mtx[36].lock(); if (!cclasses[36] || env->IsSameObject(cclasses[36], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[58]))) { cclasses[36] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[36].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[55]) { cmethods[55] = env->GetMethodID((cclasses[36]), ((char *)(string_pool + 3532LL)), ((char *)(string_pool + 3539LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack2.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 488LL)), -1); else { cstack2.l = env->CallObjectMethod(cstack2.l, (cmethods[55]), cstack3.l); refs.insert(cstack2.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 3
        // LDC  : ; Stack: 3
        cstack3.l = (cstrings[59]);
        // New stack: 4
        // INVOKEVIRTUAL java/lang/StringBuilder.append(Ljava/lang/String;)Ljava/lang/StringBuilder;; Stack: 4
        if (!cclasses[36] || env->IsSameObject(cclasses[36], NULL)) { cclasses_mtx[36].lock(); if (!cclasses[36] || env->IsSameObject(cclasses[36], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[58]))) { cclasses[36] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[36].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[55]) { cmethods[55] = env->GetMethodID((cclasses[36]), ((char *)(string_pool + 3532LL)), ((char *)(string_pool + 3539LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack2.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 488LL)), -1); else { cstack2.l = env->CallObjectMethod(cstack2.l, (cmethods[55]), cstack3.l); refs.insert(cstack2.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 3
        // ALOAD 1; Stack: 3
        cstack3.l = clocal1.l; refs.insert(cstack3.l);
        // New stack: 4
        // INVOKEVIRTUAL java/lang/StringBuilder.append(Ljava/lang/String;)Ljava/lang/StringBuilder;; Stack: 4
        if (!cclasses[36] || env->IsSameObject(cclasses[36], NULL)) { cclasses_mtx[36].lock(); if (!cclasses[36] || env->IsSameObject(cclasses[36], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[58]))) { cclasses[36] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[36].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[55]) { cmethods[55] = env->GetMethodID((cclasses[36]), ((char *)(string_pool + 3532LL)), ((char *)(string_pool + 3539LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack2.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 488LL)), -1); else { cstack2.l = env->CallObjectMethod(cstack2.l, (cmethods[55]), cstack3.l); refs.insert(cstack2.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 3
        // LDC  : ; Stack: 3
        cstack3.l = (cstrings[59]);
        // New stack: 4
        // INVOKEVIRTUAL java/lang/StringBuilder.append(Ljava/lang/String;)Ljava/lang/StringBuilder;; Stack: 4
        if (!cclasses[36] || env->IsSameObject(cclasses[36], NULL)) { cclasses_mtx[36].lock(); if (!cclasses[36] || env->IsSameObject(cclasses[36], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[58]))) { cclasses[36] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[36].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[55]) { cmethods[55] = env->GetMethodID((cclasses[36]), ((char *)(string_pool + 3532LL)), ((char *)(string_pool + 3539LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack2.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 488LL)), -1); else { cstack2.l = env->CallObjectMethod(cstack2.l, (cmethods[55]), cstack3.l); refs.insert(cstack2.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 3
        // ALOAD 2; Stack: 3
        cstack3.l = clocal2.l; refs.insert(cstack3.l);
        // New stack: 4
        // INVOKEVIRTUAL java/lang/invoke/MethodType.toString()Ljava/lang/String;; Stack: 4
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[56]) { cmethods[56] = env->GetMethodID((cclasses[8]), ((char *)(string_pool + 3585LL)), ((char *)(string_pool + 2254LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack3.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 488LL)), -1); else { cstack3.l = env->CallObjectMethod(cstack3.l, (cmethods[56])); refs.insert(cstack3.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 4
        // INVOKEVIRTUAL java/lang/StringBuilder.append(Ljava/lang/String;)Ljava/lang/StringBuilder;; Stack: 4
        if (!cclasses[36] || env->IsSameObject(cclasses[36], NULL)) { cclasses_mtx[36].lock(); if (!cclasses[36] || env->IsSameObject(cclasses[36], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[58]))) { cclasses[36] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[36].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[55]) { cmethods[55] = env->GetMethodID((cclasses[36]), ((char *)(string_pool + 3532LL)), ((char *)(string_pool + 3539LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack2.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 488LL)), -1); else { cstack2.l = env->CallObjectMethod(cstack2.l, (cmethods[55]), cstack3.l); refs.insert(cstack2.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 3
        // INVOKEVIRTUAL java/lang/StringBuilder.toString()Ljava/lang/String;; Stack: 3
        if (!cclasses[36] || env->IsSameObject(cclasses[36], NULL)) { cclasses_mtx[36].lock(); if (!cclasses[36] || env->IsSameObject(cclasses[36], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[58]))) { cclasses[36] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[36].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[57]) { cmethods[57] = env->GetMethodID((cclasses[36]), ((char *)(string_pool + 3585LL)), ((char *)(string_pool + 2254LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack2.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 488LL)), -1); else { cstack2.l = env->CallObjectMethod(cstack2.l, (cmethods[57])); refs.insert(cstack2.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 3
        // ALOAD 4; Stack: 3
        cstack3.l = clocal4.l; refs.insert(cstack3.l);
        // New stack: 4
        // INVOKESPECIAL java/lang/RuntimeException.<init>(Ljava/lang/String;Ljava/lang/Throwable;)V; Stack: 4
        if (!cclasses[32] || env->IsSameObject(cclasses[32], NULL)) { cclasses_mtx[32].lock(); if (!cclasses[32] || env->IsSameObject(cclasses[32], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[53]))) { cclasses[32] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[32].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[44]) { cmethods[44] = env->GetMethodID((cclasses[32]), ((char *)(string_pool + 631LL)), ((char *)(string_pool + 2554LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 663LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[32]), (cmethods[44]), cstack2.l, cstack3.l); 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 686LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
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
        L_CATCH_0: if (env->IsInstanceOf(cstack0.l, (cclasses[17]))) { goto L3; }
        env->Throw((jthrowable) cstack0.l); return (jobject) 0;
    }
    
    // b(IJ)I
    jint JNICALL __ngen_native_b10(JNIEnv *env, jclass clazz, jint arg0, jlong arg1) {
        jobject classloader = utils::get_classloader_from_class(env, clazz);
        if (env->ExceptionCheck()) { return (jint) 0; }
        if (classloader == nullptr) { env->FatalError(((char *)(string_pool + 54LL))); return (jint) 0; }
    
        jobject lookup = nullptr;
        // try-catch-class java/lang/Exception
        if (!cclasses[17] || env->IsSameObject(cclasses[17], NULL)) { cclasses_mtx[17].lock(); if (!cclasses[17] || env->IsSameObject(cclasses[17], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[28]))) { cclasses[17] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[17].unlock(); if (env->ExceptionCheck()) { return (jint) 0; } }
        jvalue cstack0 = {}, cstack1 = {}, cstack2 = {}, cstack3 = {}, cstack4 = {}, cstack5 = {};
        jvalue clocal0 = {}, clocal1 = {}, clocal2 = {}, clocal3 = {}, clocal4 = {}, clocal5 = {}, clocal6 = {}, clocal7 = {}, clocal8 = {}, clocal9 = {}, clocal10 = {}, clocal11 = {}, clocal12 = {}, clocal13 = {};
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
        // SIPUSH 30069; Stack: 1
        cstack1.i = (jint) 30069;
        // New stack: 2
        // IXOR; Stack: 2
        cstack0.i = cstack0.i ^ cstack1.i;
        // New stack: 1
        // ISTORE 3; Stack: 1
        clocal3.i = cstack0.i;
        // New stack: 0
        // GETSTATIC dev/sakura/client/Mahiro_uT.f [Ljava/lang/Integer;; Stack: 0
        if (!cclasses[4]  || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { return (jint) 0; } } if (!cfields[6]) { cfields[6] = env->GetStaticFieldID((cclasses[4]), ((char *)(string_pool + 8448LL)), ((char *)(string_pool + 8450LL))); if (env->ExceptionCheck()) { return (jint) 0; }  } cstack0.l = env->GetStaticObjectField((cclasses[4]), (cfields[6])); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { return (jint) 0; }
        // New stack: 1
        // ILOAD 3; Stack: 1
        cstack1.i = clocal3.i;
        // New stack: 2
        // AALOAD; Stack: 2
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 105LL)), -1); else { cstack0.l = env->GetObjectArrayElement((jobjectArray) cstack0.l, cstack1.i); refs.insert(cstack0.l); } 
        if (env->ExceptionCheck()) { return (jint) 0; }
        // New stack: 1
        // IFNONNULL L4; Stack: 1
        if (!env->IsSameObject(cstack0.l, nullptr)) goto L4;
        // New stack: 0
        // BIPUSH 8; Stack: 0
        cstack0.i = (jint) 8;
        // New stack: 1
        // NEWARRAY 8; Stack: 1
        if (cstack0.i < 0) utils::throw_re(env, ((char *)(string_pool + 217LL)), ((char *)(string_pool + 1834LL)), -1); else { cstack0.l = env->NewByteArray(cstack0.i); refs.insert(cstack0.l); } 
        if (env->ExceptionCheck()) { return (jint) 0; }
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // ICONST_0; Stack: 2
        cstack2.i = 0;
        // New stack: 3
        // LLOAD 1; Stack: 3
        cstack3.j = clocal1.j;
        // New stack: 5
        // BIPUSH 56; Stack: 5
        cstack5.i = (jint) 56;
        // New stack: 6
        // LUSHR; Stack: 6
        cstack3.j = (jlong) (((uint64_t) cstack3.j) >> (((uint64_t) cstack5.i) & 0x3f));
        // New stack: 5
        // L2I; Stack: 5
        cstack3.i = (jint) cstack3.j;
        // New stack: 4
        // I2B; Stack: 4
        cstack3.i = (jint) (jbyte) cstack3.i;
        // New stack: 4
        // BASTORE; Stack: 4
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 1863LL)), -1); else { utils::bastore(env, (jarray) cstack1.l, cstack2.i, cstack3.i); } 
        if (env->ExceptionCheck()) { return (jint) 0; }
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // ICONST_1; Stack: 2
        cstack2.i = 1;
        // New stack: 3
        // LLOAD 1; Stack: 3
        cstack3.j = clocal1.j;
        // New stack: 5
        // BIPUSH 48; Stack: 5
        cstack5.i = (jint) 48;
        // New stack: 6
        // LUSHR; Stack: 6
        cstack3.j = (jlong) (((uint64_t) cstack3.j) >> (((uint64_t) cstack5.i) & 0x3f));
        // New stack: 5
        // L2I; Stack: 5
        cstack3.i = (jint) cstack3.j;
        // New stack: 4
        // I2B; Stack: 4
        cstack3.i = (jint) (jbyte) cstack3.i;
        // New stack: 4
        // BASTORE; Stack: 4
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 1863LL)), -1); else { utils::bastore(env, (jarray) cstack1.l, cstack2.i, cstack3.i); } 
        if (env->ExceptionCheck()) { return (jint) 0; }
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // ICONST_2; Stack: 2
        cstack2.i = 2;
        // New stack: 3
        // LLOAD 1; Stack: 3
        cstack3.j = clocal1.j;
        // New stack: 5
        // BIPUSH 40; Stack: 5
        cstack5.i = (jint) 40;
        // New stack: 6
        // LUSHR; Stack: 6
        cstack3.j = (jlong) (((uint64_t) cstack3.j) >> (((uint64_t) cstack5.i) & 0x3f));
        // New stack: 5
        // L2I; Stack: 5
        cstack3.i = (jint) cstack3.j;
        // New stack: 4
        // I2B; Stack: 4
        cstack3.i = (jint) (jbyte) cstack3.i;
        // New stack: 4
        // BASTORE; Stack: 4
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 1863LL)), -1); else { utils::bastore(env, (jarray) cstack1.l, cstack2.i, cstack3.i); } 
        if (env->ExceptionCheck()) { return (jint) 0; }
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // ICONST_3; Stack: 2
        cstack2.i = 3;
        // New stack: 3
        // LLOAD 1; Stack: 3
        cstack3.j = clocal1.j;
        // New stack: 5
        // BIPUSH 32; Stack: 5
        cstack5.i = (jint) 32;
        // New stack: 6
        // LUSHR; Stack: 6
        cstack3.j = (jlong) (((uint64_t) cstack3.j) >> (((uint64_t) cstack5.i) & 0x3f));
        // New stack: 5
        // L2I; Stack: 5
        cstack3.i = (jint) cstack3.j;
        // New stack: 4
        // I2B; Stack: 4
        cstack3.i = (jint) (jbyte) cstack3.i;
        // New stack: 4
        // BASTORE; Stack: 4
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 1863LL)), -1); else { utils::bastore(env, (jarray) cstack1.l, cstack2.i, cstack3.i); } 
        if (env->ExceptionCheck()) { return (jint) 0; }
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // ICONST_4; Stack: 2
        cstack2.i = 4;
        // New stack: 3
        // LLOAD 1; Stack: 3
        cstack3.j = clocal1.j;
        // New stack: 5
        // BIPUSH 24; Stack: 5
        cstack5.i = (jint) 24;
        // New stack: 6
        // LUSHR; Stack: 6
        cstack3.j = (jlong) (((uint64_t) cstack3.j) >> (((uint64_t) cstack5.i) & 0x3f));
        // New stack: 5
        // L2I; Stack: 5
        cstack3.i = (jint) cstack3.j;
        // New stack: 4
        // I2B; Stack: 4
        cstack3.i = (jint) (jbyte) cstack3.i;
        // New stack: 4
        // BASTORE; Stack: 4
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 1863LL)), -1); else { utils::bastore(env, (jarray) cstack1.l, cstack2.i, cstack3.i); } 
        if (env->ExceptionCheck()) { return (jint) 0; }
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // ICONST_5; Stack: 2
        cstack2.i = 5;
        // New stack: 3
        // LLOAD 1; Stack: 3
        cstack3.j = clocal1.j;
        // New stack: 5
        // BIPUSH 16; Stack: 5
        cstack5.i = (jint) 16;
        // New stack: 6
        // LUSHR; Stack: 6
        cstack3.j = (jlong) (((uint64_t) cstack3.j) >> (((uint64_t) cstack5.i) & 0x3f));
        // New stack: 5
        // L2I; Stack: 5
        cstack3.i = (jint) cstack3.j;
        // New stack: 4
        // I2B; Stack: 4
        cstack3.i = (jint) (jbyte) cstack3.i;
        // New stack: 4
        // BASTORE; Stack: 4
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 1863LL)), -1); else { utils::bastore(env, (jarray) cstack1.l, cstack2.i, cstack3.i); } 
        if (env->ExceptionCheck()) { return (jint) 0; }
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // BIPUSH 6; Stack: 2
        cstack2.i = (jint) 6;
        // New stack: 3
        // LLOAD 1; Stack: 3
        cstack3.j = clocal1.j;
        // New stack: 5
        // BIPUSH 8; Stack: 5
        cstack5.i = (jint) 8;
        // New stack: 6
        // LUSHR; Stack: 6
        cstack3.j = (jlong) (((uint64_t) cstack3.j) >> (((uint64_t) cstack5.i) & 0x3f));
        // New stack: 5
        // L2I; Stack: 5
        cstack3.i = (jint) cstack3.j;
        // New stack: 4
        // I2B; Stack: 4
        cstack3.i = (jint) (jbyte) cstack3.i;
        // New stack: 4
        // BASTORE; Stack: 4
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 1863LL)), -1); else { utils::bastore(env, (jarray) cstack1.l, cstack2.i, cstack3.i); } 
        if (env->ExceptionCheck()) { return (jint) 0; }
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // BIPUSH 7; Stack: 2
        cstack2.i = (jint) 7;
        // New stack: 3
        // LLOAD 1; Stack: 3
        cstack3.j = clocal1.j;
        // New stack: 5
        // L2I; Stack: 5
        cstack3.i = (jint) cstack3.j;
        // New stack: 4
        // I2B; Stack: 4
        cstack3.i = (jint) (jbyte) cstack3.i;
        // New stack: 4
        // BASTORE; Stack: 4
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 1863LL)), -1); else { utils::bastore(env, (jarray) cstack1.l, cstack2.i, cstack3.i); } 
        if (env->ExceptionCheck()) { return (jint) 0; }
        // New stack: 1
        // ASTORE 4; Stack: 1
        clocal4.l = cstack0.l; refs.insert(cstack0.l);
        // New stack: 0
        // GETSTATIC dev/sakura/client/Mahiro_uT.e [J; Stack: 0
        if (!cclasses[4]  || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { return (jint) 0; } } if (!cfields[5]) { cfields[5] = env->GetStaticFieldID((cclasses[4]), ((char *)(string_pool + 8443LL)), ((char *)(string_pool + 8445LL))); if (env->ExceptionCheck()) { return (jint) 0; }  } cstack0.l = env->GetStaticObjectField((cclasses[4]), (cfields[5])); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { return (jint) 0; }
        // New stack: 1
        // ILOAD 3; Stack: 1
        cstack1.i = clocal3.i;
        // New stack: 2
        // LALOAD; Stack: 2
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 8477LL)), -1); else { env->GetLongArrayRegion((jlongArray) cstack0.l, cstack1.i, 1, &cstack0.j); } 
        if (env->ExceptionCheck()) { return (jint) 0; }
        // New stack: 2
        // LSTORE 5; Stack: 2
        clocal5.j = cstack0.j;
        // New stack: 0
        // BIPUSH 8; Stack: 0
        cstack0.i = (jint) 8;
        // New stack: 1
        // NEWARRAY 8; Stack: 1
        if (cstack0.i < 0) utils::throw_re(env, ((char *)(string_pool + 217LL)), ((char *)(string_pool + 1834LL)), -1); else { cstack0.l = env->NewByteArray(cstack0.i); refs.insert(cstack0.l); } 
        if (env->ExceptionCheck()) { return (jint) 0; }
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // ICONST_0; Stack: 2
        cstack2.i = 0;
        // New stack: 3
        // LLOAD 5; Stack: 3
        cstack3.j = clocal5.j;
        // New stack: 5
        // BIPUSH 56; Stack: 5
        cstack5.i = (jint) 56;
        // New stack: 6
        // LUSHR; Stack: 6
        cstack3.j = (jlong) (((uint64_t) cstack3.j) >> (((uint64_t) cstack5.i) & 0x3f));
        // New stack: 5
        // L2I; Stack: 5
        cstack3.i = (jint) cstack3.j;
        // New stack: 4
        // I2B; Stack: 4
        cstack3.i = (jint) (jbyte) cstack3.i;
        // New stack: 4
        // BASTORE; Stack: 4
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 1863LL)), -1); else { utils::bastore(env, (jarray) cstack1.l, cstack2.i, cstack3.i); } 
        if (env->ExceptionCheck()) { return (jint) 0; }
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // ICONST_1; Stack: 2
        cstack2.i = 1;
        // New stack: 3
        // LLOAD 5; Stack: 3
        cstack3.j = clocal5.j;
        // New stack: 5
        // BIPUSH 48; Stack: 5
        cstack5.i = (jint) 48;
        // New stack: 6
        // LUSHR; Stack: 6
        cstack3.j = (jlong) (((uint64_t) cstack3.j) >> (((uint64_t) cstack5.i) & 0x3f));
        // New stack: 5
        // L2I; Stack: 5
        cstack3.i = (jint) cstack3.j;
        // New stack: 4
        // I2B; Stack: 4
        cstack3.i = (jint) (jbyte) cstack3.i;
        // New stack: 4
        // BASTORE; Stack: 4
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 1863LL)), -1); else { utils::bastore(env, (jarray) cstack1.l, cstack2.i, cstack3.i); } 
        if (env->ExceptionCheck()) { return (jint) 0; }
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // ICONST_2; Stack: 2
        cstack2.i = 2;
        // New stack: 3
        // LLOAD 5; Stack: 3
        cstack3.j = clocal5.j;
        // New stack: 5
        // BIPUSH 40; Stack: 5
        cstack5.i = (jint) 40;
        // New stack: 6
        // LUSHR; Stack: 6
        cstack3.j = (jlong) (((uint64_t) cstack3.j) >> (((uint64_t) cstack5.i) & 0x3f));
        // New stack: 5
        // L2I; Stack: 5
        cstack3.i = (jint) cstack3.j;
        // New stack: 4
        // I2B; Stack: 4
        cstack3.i = (jint) (jbyte) cstack3.i;
        // New stack: 4
        // BASTORE; Stack: 4
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 1863LL)), -1); else { utils::bastore(env, (jarray) cstack1.l, cstack2.i, cstack3.i); } 
        if (env->ExceptionCheck()) { return (jint) 0; }
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // ICONST_3; Stack: 2
        cstack2.i = 3;
        // New stack: 3
        // LLOAD 5; Stack: 3
        cstack3.j = clocal5.j;
        // New stack: 5
        // BIPUSH 32; Stack: 5
        cstack5.i = (jint) 32;
        // New stack: 6
        // LUSHR; Stack: 6
        cstack3.j = (jlong) (((uint64_t) cstack3.j) >> (((uint64_t) cstack5.i) & 0x3f));
        // New stack: 5
        // L2I; Stack: 5
        cstack3.i = (jint) cstack3.j;
        // New stack: 4
        // I2B; Stack: 4
        cstack3.i = (jint) (jbyte) cstack3.i;
        // New stack: 4
        // BASTORE; Stack: 4
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 1863LL)), -1); else { utils::bastore(env, (jarray) cstack1.l, cstack2.i, cstack3.i); } 
        if (env->ExceptionCheck()) { return (jint) 0; }
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // ICONST_4; Stack: 2
        cstack2.i = 4;
        // New stack: 3
        // LLOAD 5; Stack: 3
        cstack3.j = clocal5.j;
        // New stack: 5
        // BIPUSH 24; Stack: 5
        cstack5.i = (jint) 24;
        // New stack: 6
        // LUSHR; Stack: 6
        cstack3.j = (jlong) (((uint64_t) cstack3.j) >> (((uint64_t) cstack5.i) & 0x3f));
        // New stack: 5
        // L2I; Stack: 5
        cstack3.i = (jint) cstack3.j;
        // New stack: 4
        // I2B; Stack: 4
        cstack3.i = (jint) (jbyte) cstack3.i;
        // New stack: 4
        // BASTORE; Stack: 4
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 1863LL)), -1); else { utils::bastore(env, (jarray) cstack1.l, cstack2.i, cstack3.i); } 
        if (env->ExceptionCheck()) { return (jint) 0; }
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // ICONST_5; Stack: 2
        cstack2.i = 5;
        // New stack: 3
        // LLOAD 5; Stack: 3
        cstack3.j = clocal5.j;
        // New stack: 5
        // BIPUSH 16; Stack: 5
        cstack5.i = (jint) 16;
        // New stack: 6
        // LUSHR; Stack: 6
        cstack3.j = (jlong) (((uint64_t) cstack3.j) >> (((uint64_t) cstack5.i) & 0x3f));
        // New stack: 5
        // L2I; Stack: 5
        cstack3.i = (jint) cstack3.j;
        // New stack: 4
        // I2B; Stack: 4
        cstack3.i = (jint) (jbyte) cstack3.i;
        // New stack: 4
        // BASTORE; Stack: 4
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 1863LL)), -1); else { utils::bastore(env, (jarray) cstack1.l, cstack2.i, cstack3.i); } 
        if (env->ExceptionCheck()) { return (jint) 0; }
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // BIPUSH 6; Stack: 2
        cstack2.i = (jint) 6;
        // New stack: 3
        // LLOAD 5; Stack: 3
        cstack3.j = clocal5.j;
        // New stack: 5
        // BIPUSH 8; Stack: 5
        cstack5.i = (jint) 8;
        // New stack: 6
        // LUSHR; Stack: 6
        cstack3.j = (jlong) (((uint64_t) cstack3.j) >> (((uint64_t) cstack5.i) & 0x3f));
        // New stack: 5
        // L2I; Stack: 5
        cstack3.i = (jint) cstack3.j;
        // New stack: 4
        // I2B; Stack: 4
        cstack3.i = (jint) (jbyte) cstack3.i;
        // New stack: 4
        // BASTORE; Stack: 4
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 1863LL)), -1); else { utils::bastore(env, (jarray) cstack1.l, cstack2.i, cstack3.i); } 
        if (env->ExceptionCheck()) { return (jint) 0; }
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // BIPUSH 7; Stack: 2
        cstack2.i = (jint) 7;
        // New stack: 3
        // LLOAD 5; Stack: 3
        cstack3.j = clocal5.j;
        // New stack: 5
        // L2I; Stack: 5
        cstack3.i = (jint) cstack3.j;
        // New stack: 4
        // I2B; Stack: 4
        cstack3.i = (jint) (jbyte) cstack3.i;
        // New stack: 4
        // BASTORE; Stack: 4
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 1863LL)), -1); else { utils::bastore(env, (jarray) cstack1.l, cstack2.i, cstack3.i); } 
        if (env->ExceptionCheck()) { return (jint) 0; }
        // New stack: 1
        // ASTORE 7; Stack: 1
        clocal7.l = cstack0.l; refs.insert(cstack0.l);
        // New stack: 0
        // INVOKESTATIC java/lang/Thread.currentThread()Ljava/lang/Thread;; Stack: 0
        if (!cclasses[29] || env->IsSameObject(cclasses[29], NULL)) { cclasses_mtx[29].lock(); if (!cclasses[29] || env->IsSameObject(cclasses[29], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[51]))) { cclasses[29] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[29].unlock(); if (env->ExceptionCheck()) { return (jint) 0; } } if (!cmethods[40]) { cmethods[40] = env->GetStaticMethodID((cclasses[29]), ((char *)(string_pool + 2358LL)), ((char *)(string_pool + 2372LL))); if (env->ExceptionCheck()) { return (jint) 0; }  } cstack0.l = env->CallStaticObjectMethod((cclasses[29]), (cmethods[40])); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { return (jint) 0; }
        // New stack: 1
        // INVOKEVIRTUAL java/lang/Thread.threadId()J; Stack: 1
        if (!cclasses[29] || env->IsSameObject(cclasses[29], NULL)) { cclasses_mtx[29].lock(); if (!cclasses[29] || env->IsSameObject(cclasses[29], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[51]))) { cclasses[29] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[29].unlock(); if (env->ExceptionCheck()) { return (jint) 0; } } if (!cmethods[41]) { cmethods[41] = env->GetMethodID((cclasses[29]), ((char *)(string_pool + 2393LL)), ((char *)(string_pool + 186LL))); if (env->ExceptionCheck()) { return (jint) 0; }  } if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 190LL)), -1); else cstack0.j = env->CallLongMethod(cstack0.l, (cmethods[41])); 
        if (env->ExceptionCheck()) { return (jint) 0; }
        // New stack: 2
        // INVOKESTATIC java/lang/Long.valueOf(J)Ljava/lang/Long;; Stack: 2
        if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { cclasses_mtx[2].lock(); if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[1]))) { cclasses[2] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[2].unlock(); if (env->ExceptionCheck()) { return (jint) 0; } } if (!cmethods[2]) { cmethods[2] = env->GetStaticMethodID((cclasses[2]), ((char *)(string_pool + 2402LL)), ((char *)(string_pool + 2410LL))); if (env->ExceptionCheck()) { return (jint) 0; }  } cstack0.l = env->CallStaticObjectMethod((cclasses[2]), (cmethods[2]), cstack0.j); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { return (jint) 0; }
        // New stack: 1
        // ASTORE 8; Stack: 1
        clocal8.l = cstack0.l; refs.insert(cstack0.l);
        // New stack: 0
        // GETSTATIC dev/sakura/client/Mahiro_uT.g Ljava/util/Map;; Stack: 0
        if (!cclasses[4]  || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { return (jint) 0; } } if (!cfields[4]) { cfields[4] = env->GetStaticFieldID((cclasses[4]), ((char *)(string_pool + 8400LL)), ((char *)(string_pool + 1712LL))); if (env->ExceptionCheck()) { return (jint) 0; }  } cstack0.l = env->GetStaticObjectField((cclasses[4]), (cfields[4])); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { return (jint) 0; }
        // New stack: 1
        // ALOAD 8; Stack: 1
        cstack1.l = clocal8.l; refs.insert(cstack1.l);
        // New stack: 2
        // INVOKEINTERFACE java/util/Map.get(Ljava/lang/Object;)Ljava/lang/Object;; Stack: 2
        if (!cclasses[30] || env->IsSameObject(cclasses[30], NULL)) { cclasses_mtx[30].lock(); if (!cclasses[30] || env->IsSameObject(cclasses[30], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[52]))) { cclasses[30] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[30].unlock(); if (env->ExceptionCheck()) { return (jint) 0; } } if (!cmethods[42]) { cmethods[42] = env->GetMethodID((cclasses[30]), ((char *)(string_pool + 2430LL)), ((char *)(string_pool + 2434LL))); if (env->ExceptionCheck()) { return (jint) 0; }  } if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 574LL)), -1); else { cstack0.l = env->CallObjectMethod(cstack0.l, (cmethods[42]), cstack1.l); refs.insert(cstack0.l); } 
        if (env->ExceptionCheck()) { return (jint) 0; }
        // New stack: 1
        // CHECKCAST [Ljava/lang/Object;; Stack: 1
        if (!cclasses[31] || env->IsSameObject(cclasses[31], NULL)) { cclasses_mtx[31].lock(); if (!cclasses[31] || env->IsSameObject(cclasses[31], NULL)) { if (jclass clazz = env->FindClass(((char *)(string_pool + 2473LL)))) { cclasses[31] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[31].unlock(); if (env->ExceptionCheck()) { return (jint) 0; } } if (cstack0.l != nullptr && !env->IsInstanceOf(cstack0.l, (cclasses[31]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 2473LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (jint) 0; } } 
        // New stack: 1
        // ASTORE 9; Stack: 1
        clocal9.l = cstack0.l; refs.insert(cstack0.l);
        // New stack: 0
        // LABEL L1; Stack: 0
        L1: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 0
        // ALOAD 9; Stack: 0
        cstack0.l = clocal9.l; refs.insert(cstack0.l);
        // New stack: 1
        // IFNONNULL L5; Stack: 1
        if (!env->IsSameObject(cstack0.l, nullptr)) goto L5;
        // New stack: 0
        // ICONST_3; Stack: 0
        cstack0.i = 3;
        // New stack: 1
        // ANEWARRAY java/lang/Object; Stack: 1
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack0.i < 0) utils::throw_re(env, ((char *)(string_pool + 217LL)), ((char *)(string_pool + 254LL)), -1); else { cstack0.l = env->NewObjectArray(cstack0.i, (cclasses[5]), nullptr); refs.insert(cstack0.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 1
        // ASTORE 9; Stack: 1
        clocal9.l = cstack0.l; refs.insert(cstack0.l);
        // New stack: 0
        // ALOAD 9; Stack: 0
        cstack0.l = clocal9.l; refs.insert(cstack0.l);
        // New stack: 1
        // ICONST_0; Stack: 1
        cstack1.i = 0;
        // New stack: 2
        // LDC DES/CBC/NoPadding; Stack: 2
        cstack2.l = (cstrings[49]);
        // New stack: 3
        // INVOKESTATIC javax/crypto/Cipher.getInstance(Ljava/lang/String;)Ljavax/crypto/Cipher;; Stack: 3
        if (!cclasses[25] || env->IsSameObject(cclasses[25], NULL)) { cclasses_mtx[25].lock(); if (!cclasses[25] || env->IsSameObject(cclasses[25], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[41]))) { cclasses[25] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[25].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[26]) { cmethods[26] = env->GetStaticMethodID((cclasses[25]), ((char *)(string_pool + 1728LL)), ((char *)(string_pool + 1740LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack2.l = env->CallStaticObjectMethod((cclasses[25]), (cmethods[26]), cstack2.l); refs.insert(cstack2.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 3
        // AASTORE; Stack: 3
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2106LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack0.l, cstack1.i, cstack2.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 0
        // ALOAD 9; Stack: 0
        cstack0.l = clocal9.l; refs.insert(cstack0.l);
        // New stack: 1
        // ICONST_1; Stack: 1
        cstack1.i = 1;
        // New stack: 2
        // LDC DES; Stack: 2
        cstack2.l = (cstrings[42]);
        // New stack: 3
        // INVOKESTATIC javax/crypto/SecretKeyFactory.getInstance(Ljava/lang/String;)Ljavax/crypto/SecretKeyFactory;; Stack: 3
        if (!cclasses[26] || env->IsSameObject(cclasses[26], NULL)) { cclasses_mtx[26].lock(); if (!cclasses[26] || env->IsSameObject(cclasses[26], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[43]))) { cclasses[26] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[26].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[27]) { cmethods[27] = env->GetStaticMethodID((cclasses[26]), ((char *)(string_pool + 1728LL)), ((char *)(string_pool + 1782LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack2.l = env->CallStaticObjectMethod((cclasses[26]), (cmethods[27]), cstack2.l); refs.insert(cstack2.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 3
        // AASTORE; Stack: 3
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2106LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack0.l, cstack1.i, cstack2.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 0
        // ALOAD 9; Stack: 0
        cstack0.l = clocal9.l; refs.insert(cstack0.l);
        // New stack: 1
        // ICONST_2; Stack: 1
        cstack1.i = 2;
        // New stack: 2
        // NEW javax/crypto/spec/IvParameterSpec; Stack: 2
        if (!cclasses[28] || env->IsSameObject(cclasses[28], NULL)) { cclasses_mtx[28].lock(); if (!cclasses[28] || env->IsSameObject(cclasses[28], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[45]))) { cclasses[28] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[28].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (jobject obj = env->AllocObject((cclasses[28]))) { cstack2.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 3
        // DUP; Stack: 3
        cstack3 = cstack2;
        // New stack: 4
        // BIPUSH 8; Stack: 4
        cstack4.i = (jint) 8;
        // New stack: 5
        // NEWARRAY 8; Stack: 5
        if (cstack4.i < 0) utils::throw_re(env, ((char *)(string_pool + 217LL)), ((char *)(string_pool + 1834LL)), -1); else { cstack4.l = env->NewByteArray(cstack4.i); refs.insert(cstack4.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 5
        // INVOKESPECIAL javax/crypto/spec/IvParameterSpec.<init>([B)V; Stack: 5
        if (!cclasses[28] || env->IsSameObject(cclasses[28], NULL)) { cclasses_mtx[28].lock(); if (!cclasses[28] || env->IsSameObject(cclasses[28], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[45]))) { cclasses[28] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[28].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[30]) { cmethods[30] = env->GetMethodID((cclasses[28]), ((char *)(string_pool + 631LL)), ((char *)(string_pool + 1007LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack3.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 663LL)), -1); else env->CallNonvirtualVoidMethod(cstack3.l, (cclasses[28]), (cmethods[30]), cstack4.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 3
        // AASTORE; Stack: 3
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2106LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack0.l, cstack1.i, cstack2.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 0
        // GETSTATIC dev/sakura/client/Mahiro_uT.g Ljava/util/Map;; Stack: 0
        if (!cclasses[4]  || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cfields[4]) { cfields[4] = env->GetStaticFieldID((cclasses[4]), ((char *)(string_pool + 8400LL)), ((char *)(string_pool + 1712LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack0.l = env->GetStaticObjectField((cclasses[4]), (cfields[4])); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 1
        // ALOAD 8; Stack: 1
        cstack1.l = clocal8.l; refs.insert(cstack1.l);
        // New stack: 2
        // ALOAD 9; Stack: 2
        cstack2.l = clocal9.l; refs.insert(cstack2.l);
        // New stack: 3
        // INVOKEINTERFACE java/util/Map.put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;; Stack: 3
        if (!cclasses[30] || env->IsSameObject(cclasses[30], NULL)) { cclasses_mtx[30].lock(); if (!cclasses[30] || env->IsSameObject(cclasses[30], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[52]))) { cclasses[30] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[30].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[43]) { cmethods[43] = env->GetMethodID((cclasses[30]), ((char *)(string_pool + 2493LL)), ((char *)(string_pool + 2497LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 574LL)), -1); else { cstack0.l = env->CallObjectMethod(cstack0.l, (cmethods[43]), cstack1.l, cstack2.l); refs.insert(cstack0.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 1
        // POP; Stack: 1
        ;
        // New stack: 0
        // LABEL L5; Stack: 0
        L5: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 0
        // FRAME FULL L: [1, 4, 1, [B, 4, [B, java/lang/Long, [Ljava/lang/Object;] S: []; Stack: 0
        refs.erase(clocal4.l); refs.erase(clocal7.l); refs.erase(clocal8.l); refs.erase(clocal9.l); 
        utils::clear_refs(env, refs);
        // New stack: 0
        // NEW javax/crypto/spec/DESKeySpec; Stack: 0
        if (!cclasses[27] || env->IsSameObject(cclasses[27], NULL)) { cclasses_mtx[27].lock(); if (!cclasses[27] || env->IsSameObject(cclasses[27], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[44]))) { cclasses[27] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[27].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (jobject obj = env->AllocObject((cclasses[27]))) { cstack0.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // ALOAD 4; Stack: 2
        cstack2.l = clocal4.l; refs.insert(cstack2.l);
        // New stack: 3
        // INVOKESPECIAL javax/crypto/spec/DESKeySpec.<init>([B)V; Stack: 3
        if (!cclasses[27] || env->IsSameObject(cclasses[27], NULL)) { cclasses_mtx[27].lock(); if (!cclasses[27] || env->IsSameObject(cclasses[27], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[44]))) { cclasses[27] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[27].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[28]) { cmethods[28] = env->GetMethodID((cclasses[27]), ((char *)(string_pool + 631LL)), ((char *)(string_pool + 1007LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 663LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[27]), (cmethods[28]), cstack2.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 1
        // ASTORE 11; Stack: 1
        clocal11.l = cstack0.l; refs.insert(cstack0.l);
        // New stack: 0
        // ALOAD 9; Stack: 0
        cstack0.l = clocal9.l; refs.insert(cstack0.l);
        // New stack: 1
        // ICONST_1; Stack: 1
        cstack1.i = 1;
        // New stack: 2
        // AALOAD; Stack: 2
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 105LL)), -1); else { cstack0.l = env->GetObjectArrayElement((jobjectArray) cstack0.l, cstack1.i); refs.insert(cstack0.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 1
        // CHECKCAST javax/crypto/SecretKeyFactory; Stack: 1
        if (!cclasses[26] || env->IsSameObject(cclasses[26], NULL)) { cclasses_mtx[26].lock(); if (!cclasses[26] || env->IsSameObject(cclasses[26], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[43]))) { cclasses[26] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[26].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack0.l != nullptr && !env->IsInstanceOf(cstack0.l, (cclasses[26]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 2597LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } 
        // New stack: 1
        // ALOAD 11; Stack: 1
        cstack1.l = clocal11.l; refs.insert(cstack1.l);
        // New stack: 2
        // INVOKEVIRTUAL javax/crypto/SecretKeyFactory.generateSecret(Ljava/security/spec/KeySpec;)Ljavax/crypto/SecretKey;; Stack: 2
        if (!cclasses[26] || env->IsSameObject(cclasses[26], NULL)) { cclasses_mtx[26].lock(); if (!cclasses[26] || env->IsSameObject(cclasses[26], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[43]))) { cclasses[26] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[26].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[29]) { cmethods[29] = env->GetMethodID((cclasses[26]), ((char *)(string_pool + 1875LL)), ((char *)(string_pool + 1890LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 488LL)), -1); else { cstack0.l = env->CallObjectMethod(cstack0.l, (cmethods[29]), cstack1.l); refs.insert(cstack0.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 1
        // ASTORE 12; Stack: 1
        clocal12.l = cstack0.l; refs.insert(cstack0.l);
        // New stack: 0
        // ALOAD 9; Stack: 0
        cstack0.l = clocal9.l; refs.insert(cstack0.l);
        // New stack: 1
        // ICONST_0; Stack: 1
        cstack1.i = 0;
        // New stack: 2
        // AALOAD; Stack: 2
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 105LL)), -1); else { cstack0.l = env->GetObjectArrayElement((jobjectArray) cstack0.l, cstack1.i); refs.insert(cstack0.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 1
        // CHECKCAST javax/crypto/Cipher; Stack: 1
        if (!cclasses[25] || env->IsSameObject(cclasses[25], NULL)) { cclasses_mtx[25].lock(); if (!cclasses[25] || env->IsSameObject(cclasses[25], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[41]))) { cclasses[25] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[25].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack0.l != nullptr && !env->IsInstanceOf(cstack0.l, (cclasses[25]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 2627LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } 
        // New stack: 1
        // ASTORE 13; Stack: 1
        clocal13.l = cstack0.l; refs.insert(cstack0.l);
        // New stack: 0
        // ALOAD 13; Stack: 0
        cstack0.l = clocal13.l; refs.insert(cstack0.l);
        // New stack: 1
        // ICONST_2; Stack: 1
        cstack1.i = 2;
        // New stack: 2
        // ALOAD 12; Stack: 2
        cstack2.l = clocal12.l; refs.insert(cstack2.l);
        // New stack: 3
        // ALOAD 9; Stack: 3
        cstack3.l = clocal9.l; refs.insert(cstack3.l);
        // New stack: 4
        // ICONST_2; Stack: 4
        cstack4.i = 2;
        // New stack: 5
        // AALOAD; Stack: 5
        if (cstack3.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 105LL)), -1); else { cstack3.l = env->GetObjectArrayElement((jobjectArray) cstack3.l, cstack4.i); refs.insert(cstack3.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 4
        // CHECKCAST javax/crypto/spec/IvParameterSpec; Stack: 4
        if (!cclasses[28] || env->IsSameObject(cclasses[28], NULL)) { cclasses_mtx[28].lock(); if (!cclasses[28] || env->IsSameObject(cclasses[28], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[45]))) { cclasses[28] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[28].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack3.l != nullptr && !env->IsInstanceOf(cstack3.l, (cclasses[28]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 2647LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } 
        // New stack: 4
        // INVOKEVIRTUAL javax/crypto/Cipher.init(ILjava/security/Key;Ljava/security/spec/AlgorithmParameterSpec;)V; Stack: 4
        if (!cclasses[25] || env->IsSameObject(cclasses[25], NULL)) { cclasses_mtx[25].lock(); if (!cclasses[25] || env->IsSameObject(cclasses[25], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[41]))) { cclasses[25] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[25].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[31]) { cmethods[31] = env->GetMethodID((cclasses[25]), ((char *)(string_pool + 1945LL)), ((char *)(string_pool + 1950LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2017LL)), -1); else env->CallVoidMethod(cstack0.l, (cmethods[31]), cstack1.i, cstack2.l, cstack3.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 0
        // ALOAD 13; Stack: 0
        cstack0.l = clocal13.l; refs.insert(cstack0.l);
        // New stack: 1
        // ALOAD 7; Stack: 1
        cstack1.l = clocal7.l; refs.insert(cstack1.l);
        // New stack: 2
        // INVOKEVIRTUAL javax/crypto/Cipher.doFinal([B)[B; Stack: 2
        if (!cclasses[25] || env->IsSameObject(cclasses[25], NULL)) { cclasses_mtx[25].lock(); if (!cclasses[25] || env->IsSameObject(cclasses[25], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[41]))) { cclasses[25] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[25].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[36]) { cmethods[36] = env->GetMethodID((cclasses[25]), ((char *)(string_pool + 2209LL)), ((char *)(string_pool + 2217LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 488LL)), -1); else { cstack0.l = env->CallObjectMethod(cstack0.l, (cmethods[36]), cstack1.l); refs.insert(cstack0.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 1
        // ASTORE 10; Stack: 1
        clocal10.l = cstack0.l; refs.insert(cstack0.l);
        // New stack: 0
        // LABEL L2; Stack: 0
        L2: if (env->ExceptionCheck()) { return (jint) 0; }
        // New stack: 0
        // GOTO L6; Stack: 0
        goto L6;
        // New stack: 0
        // LABEL L3; Stack: 0
        L3: if (env->ExceptionCheck()) { return (jint) 0; }
        // New stack: 0
        // FRAME SAME1 L: null S: [java/lang/Exception]; Stack: 0
        refs.erase(cstack0.l); 
        refs.erase(clocal4.l); refs.erase(clocal7.l); refs.erase(clocal8.l); refs.erase(clocal9.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ASTORE 11; Stack: 1
        clocal11.l = cstack0.l; refs.insert(cstack0.l);
        // New stack: 0
        // NEW java/lang/RuntimeException; Stack: 0
        if (!cclasses[32] || env->IsSameObject(cclasses[32], NULL)) { cclasses_mtx[32].lock(); if (!cclasses[32] || env->IsSameObject(cclasses[32], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[53]))) { cclasses[32] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[32].unlock(); if (env->ExceptionCheck()) { return (jint) 0; } } if (jobject obj = env->AllocObject((cclasses[32]))) { cstack0.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { return (jint) 0; }
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // LDC dev/sakura/client/Mahiro_uT; Stack: 2
        cstack2.l = (cstrings[54]);
        // New stack: 3
        // ALOAD 11; Stack: 3
        cstack3.l = clocal11.l; refs.insert(cstack3.l);
        // New stack: 4
        // INVOKESPECIAL java/lang/RuntimeException.<init>(Ljava/lang/String;Ljava/lang/Throwable;)V; Stack: 4
        if (!cclasses[32] || env->IsSameObject(cclasses[32], NULL)) { cclasses_mtx[32].lock(); if (!cclasses[32] || env->IsSameObject(cclasses[32], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[53]))) { cclasses[32] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[32].unlock(); if (env->ExceptionCheck()) { return (jint) 0; } } if (!cmethods[44]) { cmethods[44] = env->GetMethodID((cclasses[32]), ((char *)(string_pool + 631LL)), ((char *)(string_pool + 2554LL))); if (env->ExceptionCheck()) { return (jint) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 663LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[32]), (cmethods[44]), cstack2.l, cstack3.l); 
        if (env->ExceptionCheck()) { return (jint) 0; }
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 686LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { return (jint) 0; }
        // New stack: 0
        // LABEL L6; Stack: 0
        L6: if (env->ExceptionCheck()) { return (jint) 0; }
        // New stack: 0
        // FRAME FULL L: [1, 4, 1, [B, 4, [B, java/lang/Long, [Ljava/lang/Object;, [B, javax/crypto/spec/DESKeySpec, javax/crypto/SecretKey, javax/crypto/Cipher] S: []; Stack: 0
        refs.erase(clocal4.l); refs.erase(clocal7.l); refs.erase(clocal8.l); refs.erase(clocal9.l); refs.erase(clocal10.l); refs.erase(clocal11.l); refs.erase(clocal12.l); refs.erase(clocal13.l); 
        utils::clear_refs(env, refs);
        // New stack: 0
        // ALOAD 10; Stack: 0
        cstack0.l = clocal10.l; refs.insert(cstack0.l);
        // New stack: 1
        // ICONST_4; Stack: 1
        cstack1.i = 4;
        // New stack: 2
        // BALOAD; Stack: 2
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2304LL)), -1); else { cstack0.i = (jint) utils::baload(env, (jarray) cstack0.l, cstack1.i); } 
        if (env->ExceptionCheck()) { return (jint) 0; }
        // New stack: 1
        // SIPUSH 255; Stack: 1
        cstack1.i = (jint) 255;
        // New stack: 2
        // IAND; Stack: 2
        cstack0.i = cstack0.i & cstack1.i;
        // New stack: 1
        // BIPUSH 24; Stack: 1
        cstack1.i = (jint) 24;
        // New stack: 2
        // ISHL; Stack: 2
        cstack0.i = cstack0.i << (0x1f & cstack1.i);
        // New stack: 1
        // ALOAD 10; Stack: 1
        cstack1.l = clocal10.l; refs.insert(cstack1.l);
        // New stack: 2
        // ICONST_5; Stack: 2
        cstack2.i = 5;
        // New stack: 3
        // BALOAD; Stack: 3
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2304LL)), -1); else { cstack1.i = (jint) utils::baload(env, (jarray) cstack1.l, cstack2.i); } 
        if (env->ExceptionCheck()) { return (jint) 0; }
        // New stack: 2
        // SIPUSH 255; Stack: 2
        cstack2.i = (jint) 255;
        // New stack: 3
        // IAND; Stack: 3
        cstack1.i = cstack1.i & cstack2.i;
        // New stack: 2
        // BIPUSH 16; Stack: 2
        cstack2.i = (jint) 16;
        // New stack: 3
        // ISHL; Stack: 3
        cstack1.i = cstack1.i << (0x1f & cstack2.i);
        // New stack: 2
        // IOR; Stack: 2
        cstack0.i = cstack0.i | cstack1.i;
        // New stack: 1
        // ALOAD 10; Stack: 1
        cstack1.l = clocal10.l; refs.insert(cstack1.l);
        // New stack: 2
        // BIPUSH 6; Stack: 2
        cstack2.i = (jint) 6;
        // New stack: 3
        // BALOAD; Stack: 3
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2304LL)), -1); else { cstack1.i = (jint) utils::baload(env, (jarray) cstack1.l, cstack2.i); } 
        if (env->ExceptionCheck()) { return (jint) 0; }
        // New stack: 2
        // SIPUSH 255; Stack: 2
        cstack2.i = (jint) 255;
        // New stack: 3
        // IAND; Stack: 3
        cstack1.i = cstack1.i & cstack2.i;
        // New stack: 2
        // BIPUSH 8; Stack: 2
        cstack2.i = (jint) 8;
        // New stack: 3
        // ISHL; Stack: 3
        cstack1.i = cstack1.i << (0x1f & cstack2.i);
        // New stack: 2
        // IOR; Stack: 2
        cstack0.i = cstack0.i | cstack1.i;
        // New stack: 1
        // ALOAD 10; Stack: 1
        cstack1.l = clocal10.l; refs.insert(cstack1.l);
        // New stack: 2
        // BIPUSH 7; Stack: 2
        cstack2.i = (jint) 7;
        // New stack: 3
        // BALOAD; Stack: 3
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2304LL)), -1); else { cstack1.i = (jint) utils::baload(env, (jarray) cstack1.l, cstack2.i); } 
        if (env->ExceptionCheck()) { return (jint) 0; }
        // New stack: 2
        // SIPUSH 255; Stack: 2
        cstack2.i = (jint) 255;
        // New stack: 3
        // IAND; Stack: 3
        cstack1.i = cstack1.i & cstack2.i;
        // New stack: 2
        // IOR; Stack: 2
        cstack0.i = cstack0.i | cstack1.i;
        // New stack: 1
        // ISTORE 11; Stack: 1
        clocal11.i = cstack0.i;
        // New stack: 0
        // GETSTATIC dev/sakura/client/Mahiro_uT.f [Ljava/lang/Integer;; Stack: 0
        if (!cclasses[4]  || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { return (jint) 0; } } if (!cfields[6]) { cfields[6] = env->GetStaticFieldID((cclasses[4]), ((char *)(string_pool + 8448LL)), ((char *)(string_pool + 8450LL))); if (env->ExceptionCheck()) { return (jint) 0; }  } cstack0.l = env->GetStaticObjectField((cclasses[4]), (cfields[6])); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { return (jint) 0; }
        // New stack: 1
        // ILOAD 3; Stack: 1
        cstack1.i = clocal3.i;
        // New stack: 2
        // ILOAD 11; Stack: 2
        cstack2.i = clocal11.i;
        // New stack: 3
        // INVOKESTATIC java/lang/Integer.valueOf(I)Ljava/lang/Integer;; Stack: 3
        if (!cclasses[3] || env->IsSameObject(cclasses[3], NULL)) { cclasses_mtx[3].lock(); if (!cclasses[3] || env->IsSameObject(cclasses[3], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[2]))) { cclasses[3] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[3].unlock(); if (env->ExceptionCheck()) { return (jint) 0; } } if (!cmethods[8]) { cmethods[8] = env->GetStaticMethodID((cclasses[3]), ((char *)(string_pool + 2402LL)), ((char *)(string_pool + 8488LL))); if (env->ExceptionCheck()) { return (jint) 0; }  } cstack2.l = env->CallStaticObjectMethod((cclasses[3]), (cmethods[8]), cstack2.i); refs.insert(cstack2.l); 
        if (env->ExceptionCheck()) { return (jint) 0; }
        // New stack: 3
        // AASTORE; Stack: 3
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2106LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack0.l, cstack1.i, cstack2.l); } 
        if (env->ExceptionCheck()) { return (jint) 0; }
        // New stack: 0
        // LABEL L4; Stack: 0
        L4: if (env->ExceptionCheck()) { return (jint) 0; }
        // New stack: 0
        // FRAME FULL L: [1, 4, 1] S: []; Stack: 0
        utils::clear_refs(env, refs);
        // New stack: 0
        // GETSTATIC dev/sakura/client/Mahiro_uT.f [Ljava/lang/Integer;; Stack: 0
        if (!cclasses[4]  || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { return (jint) 0; } } if (!cfields[6]) { cfields[6] = env->GetStaticFieldID((cclasses[4]), ((char *)(string_pool + 8448LL)), ((char *)(string_pool + 8450LL))); if (env->ExceptionCheck()) { return (jint) 0; }  } cstack0.l = env->GetStaticObjectField((cclasses[4]), (cfields[6])); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { return (jint) 0; }
        // New stack: 1
        // ILOAD 3; Stack: 1
        cstack1.i = clocal3.i;
        // New stack: 2
        // AALOAD; Stack: 2
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 105LL)), -1); else { cstack0.l = env->GetObjectArrayElement((jobjectArray) cstack0.l, cstack1.i); refs.insert(cstack0.l); } 
        if (env->ExceptionCheck()) { return (jint) 0; }
        // New stack: 1
        // INVOKEVIRTUAL java/lang/Integer.intValue()I; Stack: 1
        if (!cclasses[3] || env->IsSameObject(cclasses[3], NULL)) { cclasses_mtx[3].lock(); if (!cclasses[3] || env->IsSameObject(cclasses[3], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[2]))) { cclasses[3] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[3].unlock(); if (env->ExceptionCheck()) { return (jint) 0; } } if (!cmethods[1]) { cmethods[1] = env->GetMethodID((cclasses[3]), ((char *)(string_pool + 2830LL)), ((char *)(string_pool + 2047LL))); if (env->ExceptionCheck()) { return (jint) 0; }  } if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2051LL)), -1); else cstack0.i = env->CallIntMethod(cstack0.l, (cmethods[1])); 
        if (env->ExceptionCheck()) { return (jint) 0; }
        // New stack: 1
        // IRETURN; Stack: 1
        return (jint) cstack0.i;
        // New stack: 0
        return (jint) 0;
        L_CATCH_0: if (env->IsInstanceOf(cstack0.l, (cclasses[17]))) { goto L3; }
        env->Throw((jthrowable) cstack0.l); return (jint) 0;
    }
    
    // b(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/invoke/MutableCallSite;Ljava/lang/String;[Ljava/lang/Object;)I
    jint JNICALL __ngen_native_b11(JNIEnv *env, jclass clazz, jobject arg0, jobject arg1, jobject arg2, jarray arg3) {
        jobject classloader = utils::get_classloader_from_class(env, clazz);
        if (env->ExceptionCheck()) { return (jint) 0; }
        if (classloader == nullptr) { env->FatalError(((char *)(string_pool + 54LL))); return (jint) 0; }
    
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
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 105LL)), -1); else { cstack0.l = env->GetObjectArrayElement((jobjectArray) cstack0.l, cstack1.i); refs.insert(cstack0.l); } 
        if (env->ExceptionCheck()) { return (jint) 0; }
        // New stack: 1
        // CHECKCAST java/lang/Integer; Stack: 1
        if (!cclasses[3] || env->IsSameObject(cclasses[3], NULL)) { cclasses_mtx[3].lock(); if (!cclasses[3] || env->IsSameObject(cclasses[3], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[2]))) { cclasses[3] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[3].unlock(); if (env->ExceptionCheck()) { return (jint) 0; } } if (cstack0.l != nullptr && !env->IsInstanceOf(cstack0.l, (cclasses[3]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 2812LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (jint) 0; } } 
        // New stack: 1
        // INVOKEVIRTUAL java/lang/Integer.intValue()I; Stack: 1
        if (!cclasses[3] || env->IsSameObject(cclasses[3], NULL)) { cclasses_mtx[3].lock(); if (!cclasses[3] || env->IsSameObject(cclasses[3], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[2]))) { cclasses[3] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[3].unlock(); if (env->ExceptionCheck()) { return (jint) 0; } } if (!cmethods[1]) { cmethods[1] = env->GetMethodID((cclasses[3]), ((char *)(string_pool + 2830LL)), ((char *)(string_pool + 2047LL))); if (env->ExceptionCheck()) { return (jint) 0; }  } if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2051LL)), -1); else cstack0.i = env->CallIntMethod(cstack0.l, (cmethods[1])); 
        if (env->ExceptionCheck()) { return (jint) 0; }
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
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 105LL)), -1); else { cstack0.l = env->GetObjectArrayElement((jobjectArray) cstack0.l, cstack1.i); refs.insert(cstack0.l); } 
        if (env->ExceptionCheck()) { return (jint) 0; }
        // New stack: 1
        // CHECKCAST java/lang/Long; Stack: 1
        if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { cclasses_mtx[2].lock(); if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[1]))) { cclasses[2] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[2].unlock(); if (env->ExceptionCheck()) { return (jint) 0; } } if (cstack0.l != nullptr && !env->IsInstanceOf(cstack0.l, (cclasses[2]))) { utils::throw_re(env, ((char *)(string_pool + 116LL)), (std::string(((char *)(string_pool + 145LL))) + std::string(((char *)(string_pool + 161LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (jint) 0; } } 
        // New stack: 1
        // INVOKEVIRTUAL java/lang/Long.longValue()J; Stack: 1
        if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { cclasses_mtx[2].lock(); if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[1]))) { cclasses[2] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[2].unlock(); if (env->ExceptionCheck()) { return (jint) 0; } } if (!cmethods[0]) { cmethods[0] = env->GetMethodID((cclasses[2]), ((char *)(string_pool + 176LL)), ((char *)(string_pool + 186LL))); if (env->ExceptionCheck()) { return (jint) 0; }  } if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 190LL)), -1); else cstack0.j = env->CallLongMethod(cstack0.l, (cmethods[0])); 
        if (env->ExceptionCheck()) { return (jint) 0; }
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
        // INVOKESTATIC dev/sakura/client/Mahiro_uT.b(IJ)I; Stack: 3
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { return (jint) 0; } } if (!cmethods[58]) { cmethods[58] = env->GetStaticMethodID((cclasses[4]), ((char *)(string_pool + 2153LL)), ((char *)(string_pool + 8471LL))); if (env->ExceptionCheck()) { return (jint) 0; }  } cstack0.i = env->CallStaticIntMethod((cclasses[4]), (cmethods[58]), cstack0.i, cstack1.j); 
        if (env->ExceptionCheck()) { return (jint) 0; }
        // New stack: 1
        // ISTORE 7; Stack: 1
        clocal7.i = cstack0.i;
        // New stack: 0
        // GETSTATIC java/lang/Integer.TYPE Ljava/lang/Class;; Stack: 0
        if (!cclasses[3]  || env->IsSameObject(cclasses[3], NULL)) { cclasses_mtx[3].lock(); if (!cclasses[3] || env->IsSameObject(cclasses[3], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[2]))) { cclasses[3] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[3].unlock(); if (env->ExceptionCheck()) { return (jint) 0; } } if (!cfields[7]) { cfields[7] = env->GetStaticFieldID((cclasses[3]), ((char *)(string_pool + 2917LL)), ((char *)(string_pool + 2922LL))); if (env->ExceptionCheck()) { return (jint) 0; }  } cstack0.l = env->GetStaticObjectField((cclasses[3]), (cfields[7])); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { return (jint) 0; }
        // New stack: 1
        // ILOAD 7; Stack: 1
        cstack1.i = clocal7.i;
        // New stack: 2
        // INVOKESTATIC java/lang/Integer.valueOf(I)Ljava/lang/Integer;; Stack: 2
        if (!cclasses[3] || env->IsSameObject(cclasses[3], NULL)) { cclasses_mtx[3].lock(); if (!cclasses[3] || env->IsSameObject(cclasses[3], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[2]))) { cclasses[3] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[3].unlock(); if (env->ExceptionCheck()) { return (jint) 0; } } if (!cmethods[8]) { cmethods[8] = env->GetStaticMethodID((cclasses[3]), ((char *)(string_pool + 2402LL)), ((char *)(string_pool + 8488LL))); if (env->ExceptionCheck()) { return (jint) 0; }  } cstack1.l = env->CallStaticObjectMethod((cclasses[3]), (cmethods[8]), cstack1.i); refs.insert(cstack1.l); 
        if (env->ExceptionCheck()) { return (jint) 0; }
        // New stack: 2
        // INVOKESTATIC java/lang/invoke/MethodHandles.constant(Ljava/lang/Class;Ljava/lang/Object;)Ljava/lang/invoke/MethodHandle;; Stack: 2
        if (!cclasses[21] || env->IsSameObject(cclasses[21], NULL)) { cclasses_mtx[21].lock(); if (!cclasses[21] || env->IsSameObject(cclasses[21], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[36]))) { cclasses[21] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[21].unlock(); if (env->ExceptionCheck()) { return (jint) 0; } } if (!cmethods[46]) { cmethods[46] = env->GetStaticMethodID((cclasses[21]), ((char *)(string_pool + 2839LL)), ((char *)(string_pool + 2848LL))); if (env->ExceptionCheck()) { return (jint) 0; }  } cstack0.l = env->CallStaticObjectMethod((cclasses[21]), (cmethods[46]), cstack0.l, cstack1.l); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { return (jint) 0; }
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
        if (!cclasses[34] || env->IsSameObject(cclasses[34], NULL)) { cclasses_mtx[34].lock(); if (!cclasses[34] || env->IsSameObject(cclasses[34], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[55]))) { cclasses[34] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[34].unlock(); if (env->ExceptionCheck()) { return (jint) 0; } } if (cstack3.i < 0) utils::throw_re(env, ((char *)(string_pool + 217LL)), ((char *)(string_pool + 254LL)), -1); else { cstack3.l = env->NewObjectArray(cstack3.i, (cclasses[34]), nullptr); refs.insert(cstack3.l); } 
        if (env->ExceptionCheck()) { return (jint) 0; }
        // New stack: 4
        // DUP; Stack: 4
        cstack4 = cstack3;
        // New stack: 5
        // ICONST_0; Stack: 5
        cstack5.i = 0;
        // New stack: 6
        // GETSTATIC java/lang/Integer.TYPE Ljava/lang/Class;; Stack: 6
        if (!cclasses[3]  || env->IsSameObject(cclasses[3], NULL)) { cclasses_mtx[3].lock(); if (!cclasses[3] || env->IsSameObject(cclasses[3], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[2]))) { cclasses[3] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[3].unlock(); if (env->ExceptionCheck()) { return (jint) 0; } } if (!cfields[7]) { cfields[7] = env->GetStaticFieldID((cclasses[3]), ((char *)(string_pool + 2917LL)), ((char *)(string_pool + 2922LL))); if (env->ExceptionCheck()) { return (jint) 0; }  } cstack6.l = env->GetStaticObjectField((cclasses[3]), (cfields[7])); refs.insert(cstack6.l); 
        if (env->ExceptionCheck()) { return (jint) 0; }
        // New stack: 7
        // AASTORE; Stack: 7
        if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2106LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack4.l, cstack5.i, cstack6.l); } 
        if (env->ExceptionCheck()) { return (jint) 0; }
        // New stack: 4
        // DUP; Stack: 4
        cstack4 = cstack3;
        // New stack: 5
        // ICONST_1; Stack: 5
        cstack5.i = 1;
        // New stack: 6
        // GETSTATIC java/lang/Long.TYPE Ljava/lang/Class;; Stack: 6
        if (!cclasses[2]  || env->IsSameObject(cclasses[2], NULL)) { cclasses_mtx[2].lock(); if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[1]))) { cclasses[2] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[2].unlock(); if (env->ExceptionCheck()) { return (jint) 0; } } if (!cfields[8]) { cfields[8] = env->GetStaticFieldID((cclasses[2]), ((char *)(string_pool + 2917LL)), ((char *)(string_pool + 2922LL))); if (env->ExceptionCheck()) { return (jint) 0; }  } cstack6.l = env->GetStaticObjectField((cclasses[2]), (cfields[8])); refs.insert(cstack6.l); 
        if (env->ExceptionCheck()) { return (jint) 0; }
        // New stack: 7
        // AASTORE; Stack: 7
        if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2106LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack4.l, cstack5.i, cstack6.l); } 
        if (env->ExceptionCheck()) { return (jint) 0; }
        // New stack: 4
        // INVOKESTATIC java/lang/invoke/MethodHandles.dropArguments(Ljava/lang/invoke/MethodHandle;I[Ljava/lang/Class;)Ljava/lang/invoke/MethodHandle;; Stack: 4
        if (!cclasses[21] || env->IsSameObject(cclasses[21], NULL)) { cclasses_mtx[21].lock(); if (!cclasses[21] || env->IsSameObject(cclasses[21], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[36]))) { cclasses[21] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[21].unlock(); if (env->ExceptionCheck()) { return (jint) 0; } } if (!cmethods[47]) { cmethods[47] = env->GetStaticMethodID((cclasses[21]), ((char *)(string_pool + 2940LL)), ((char *)(string_pool + 2954LL))); if (env->ExceptionCheck()) { return (jint) 0; }  } cstack1.l = env->CallStaticObjectMethod((cclasses[21]), (cmethods[47]), cstack1.l, cstack2.i, cstack3.l); refs.insert(cstack1.l); 
        if (env->ExceptionCheck()) { return (jint) 0; }
        // New stack: 2
        // INVOKEVIRTUAL java/lang/invoke/MutableCallSite.setTarget(Ljava/lang/invoke/MethodHandle;)V; Stack: 2
        if (!cclasses[35] || env->IsSameObject(cclasses[35], NULL)) { cclasses_mtx[35].lock(); if (!cclasses[35] || env->IsSameObject(cclasses[35], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[56]))) { cclasses[35] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[35].unlock(); if (env->ExceptionCheck()) { return (jint) 0; } } if (!cmethods[48]) { cmethods[48] = env->GetMethodID((cclasses[35]), ((char *)(string_pool + 3038LL)), ((char *)(string_pool + 3048LL))); if (env->ExceptionCheck()) { return (jint) 0; }  } if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2017LL)), -1); else env->CallVoidMethod(cstack0.l, (cmethods[48]), cstack1.l); 
        if (env->ExceptionCheck()) { return (jint) 0; }
        // New stack: 0
        // ILOAD 7; Stack: 0
        cstack0.i = clocal7.i;
        // New stack: 1
        // IRETURN; Stack: 1
        return (jint) cstack0.i;
        // New stack: 0
        return (jint) 0;
    }
    
    // b(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;
    jobject JNICALL __ngen_native_b12(JNIEnv *env, jclass clazz, jobject arg0, jobject arg1, jobject arg2) {
        jobject classloader = utils::get_classloader_from_class(env, clazz);
        if (env->ExceptionCheck()) { return (jobject) 0; }
        if (classloader == nullptr) { env->FatalError(((char *)(string_pool + 54LL))); return (jobject) 0; }
    
        jobject lookup = nullptr;
        // try-catch-class java/lang/Exception
        if (!cclasses[17] || env->IsSameObject(cclasses[17], NULL)) { cclasses_mtx[17].lock(); if (!cclasses[17] || env->IsSameObject(cclasses[17], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[28]))) { cclasses[17] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[17].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } }
        jvalue cstack0 = {}, cstack1 = {}, cstack2 = {}, cstack3 = {}, cstack4 = {}, cstack5 = {}, cstack6 = {};
        jvalue clocal0 = {}, clocal1 = {}, clocal2 = {}, clocal3 = {}, clocal4 = {};
        std::unordered_set<jobject> refs;
    
        clocal0.l = arg0; refs.insert(clocal0.l);
        clocal1.l = arg1; refs.insert(clocal1.l);
        clocal2.l = arg2; refs.insert(clocal2.l);
    
        // NEW java/lang/invoke/MutableCallSite; Stack: 0
        if (!cclasses[35] || env->IsSameObject(cclasses[35], NULL)) { cclasses_mtx[35].lock(); if (!cclasses[35] || env->IsSameObject(cclasses[35], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[56]))) { cclasses[35] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[35].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (jobject obj = env->AllocObject((cclasses[35]))) { cstack0.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // ALOAD 2; Stack: 2
        cstack2.l = clocal2.l; refs.insert(cstack2.l);
        // New stack: 3
        // INVOKESPECIAL java/lang/invoke/MutableCallSite.<init>(Ljava/lang/invoke/MethodType;)V; Stack: 3
        if (!cclasses[35] || env->IsSameObject(cclasses[35], NULL)) { cclasses_mtx[35].lock(); if (!cclasses[35] || env->IsSameObject(cclasses[35], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[56]))) { cclasses[35] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[35].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[49]) { cmethods[49] = env->GetMethodID((cclasses[35]), ((char *)(string_pool + 631LL)), ((char *)(string_pool + 3199LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 663LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[35]), (cmethods[49]), cstack2.l); 
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
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.11716030067971395.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 1
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack1.l = lookup;
        // New stack: 2
        // LDC Ldev/sakura/client/Mahiro_uT;; Stack: 2
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack2.l = (cclasses[6]);
        // New stack: 3
        // LDC b; Stack: 3
        cstack3.l = (cstrings[17]);
        // New stack: 4
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/invoke/MutableCallSite;Ljava/lang/String;[Ljava/lang/Object;)I; Stack: 4
        cstack4.l = (cstrings[60]);
        // New stack: 5
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.11716030067971395.a()Ljava/lang/ClassLoader;; Stack: 5
        cstack5.l = classloader;
        // New stack: 6
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 6
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[3]) { cmethods[3] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 279LL)), ((char *)(string_pool + 306LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack4.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[3]), cstack4.l, cstack5.l); refs.insert(cstack4.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 5
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 5
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 379LL)), ((char *)(string_pool + 390LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 488LL)), -1); else { cstack1.l = env->CallObjectMethod(cstack1.l, (cmethods[4]), cstack2.l, cstack3.l, cstack4.l); refs.insert(cstack1.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 2
        // LDC [Ljava/lang/Object;; Stack: 2
        if (!cclasses[31] || env->IsSameObject(cclasses[31], NULL)) { cclasses_mtx[31].lock(); if (!cclasses[31] || env->IsSameObject(cclasses[31], NULL)) { if (jclass clazz = env->FindClass(((char *)(string_pool + 2473LL)))) { cclasses[31] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[31].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack2.l = (cclasses[31]);
        // New stack: 3
        // ALOAD 2; Stack: 3
        cstack3.l = clocal2.l; refs.insert(cstack3.l);
        // New stack: 4
        // INVOKEVIRTUAL java/lang/invoke/MethodType.parameterCount()I; Stack: 4
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[50]) { cmethods[50] = env->GetMethodID((cclasses[8]), ((char *)(string_pool + 3232LL)), ((char *)(string_pool + 2047LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack3.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2051LL)), -1); else cstack3.i = env->CallIntMethod(cstack3.l, (cmethods[50])); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 4
        // INVOKEVIRTUAL java/lang/invoke/MethodHandle.asCollector(Ljava/lang/Class;I)Ljava/lang/invoke/MethodHandle;; Stack: 4
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[51]) { cmethods[51] = env->GetMethodID((cclasses[11]), ((char *)(string_pool + 3247LL)), ((char *)(string_pool + 3259LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 488LL)), -1); else { cstack1.l = env->CallObjectMethod(cstack1.l, (cmethods[51]), cstack2.l, cstack3.i); refs.insert(cstack1.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 2
        // ICONST_0; Stack: 2
        cstack2.i = 0;
        // New stack: 3
        // ICONST_3; Stack: 3
        cstack3.i = 3;
        // New stack: 4
        // ANEWARRAY java/lang/Object; Stack: 4
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack3.i < 0) utils::throw_re(env, ((char *)(string_pool + 217LL)), ((char *)(string_pool + 254LL)), -1); else { cstack3.l = env->NewObjectArray(cstack3.i, (cclasses[5]), nullptr); refs.insert(cstack3.l); } 
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
        if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2106LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack4.l, cstack5.i, cstack6.l); } 
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
        if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2106LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack4.l, cstack5.i, cstack6.l); } 
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
        if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2106LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack4.l, cstack5.i, cstack6.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 4
        // INVOKESTATIC java/lang/invoke/MethodHandles.insertArguments(Ljava/lang/invoke/MethodHandle;I[Ljava/lang/Object;)Ljava/lang/invoke/MethodHandle;; Stack: 4
        if (!cclasses[21] || env->IsSameObject(cclasses[21], NULL)) { cclasses_mtx[21].lock(); if (!cclasses[21] || env->IsSameObject(cclasses[21], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[36]))) { cclasses[21] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[21].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[52]) { cmethods[52] = env->GetStaticMethodID((cclasses[21]), ((char *)(string_pool + 3311LL)), ((char *)(string_pool + 3327LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack1.l = env->CallStaticObjectMethod((cclasses[21]), (cmethods[52]), cstack1.l, cstack2.i, cstack3.l); refs.insert(cstack1.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 2
        // ALOAD 2; Stack: 2
        cstack2.l = clocal2.l; refs.insert(cstack2.l);
        // New stack: 3
        // INVOKESTATIC java/lang/invoke/MethodHandles.explicitCastArguments(Ljava/lang/invoke/MethodHandle;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 3
        if (!cclasses[21] || env->IsSameObject(cclasses[21], NULL)) { cclasses_mtx[21].lock(); if (!cclasses[21] || env->IsSameObject(cclasses[21], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[36]))) { cclasses[21] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[21].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[53]) { cmethods[53] = env->GetStaticMethodID((cclasses[21]), ((char *)(string_pool + 3412LL)), ((char *)(string_pool + 3434LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack1.l = env->CallStaticObjectMethod((cclasses[21]), (cmethods[53]), cstack1.l, cstack2.l); refs.insert(cstack1.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 2
        // INVOKEVIRTUAL java/lang/invoke/MutableCallSite.setTarget(Ljava/lang/invoke/MethodHandle;)V; Stack: 2
        if (!cclasses[35] || env->IsSameObject(cclasses[35], NULL)) { cclasses_mtx[35].lock(); if (!cclasses[35] || env->IsSameObject(cclasses[35], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[56]))) { cclasses[35] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[35].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[48]) { cmethods[48] = env->GetMethodID((cclasses[35]), ((char *)(string_pool + 3038LL)), ((char *)(string_pool + 3048LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 2017LL)), -1); else env->CallVoidMethod(cstack0.l, (cmethods[48]), cstack1.l); 
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
        if (!cclasses[32] || env->IsSameObject(cclasses[32], NULL)) { cclasses_mtx[32].lock(); if (!cclasses[32] || env->IsSameObject(cclasses[32], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[53]))) { cclasses[32] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[32].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (jobject obj = env->AllocObject((cclasses[32]))) { cstack0.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // NEW java/lang/StringBuilder; Stack: 2
        if (!cclasses[36] || env->IsSameObject(cclasses[36], NULL)) { cclasses_mtx[36].lock(); if (!cclasses[36] || env->IsSameObject(cclasses[36], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[58]))) { cclasses[36] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[36].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (jobject obj = env->AllocObject((cclasses[36]))) { cstack2.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 3
        // DUP; Stack: 3
        cstack3 = cstack2;
        // New stack: 4
        // INVOKESPECIAL java/lang/StringBuilder.<init>()V; Stack: 4
        if (!cclasses[36] || env->IsSameObject(cclasses[36], NULL)) { cclasses_mtx[36].lock(); if (!cclasses[36] || env->IsSameObject(cclasses[36], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[58]))) { cclasses[36] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[36].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[54]) { cmethods[54] = env->GetMethodID((cclasses[36]), ((char *)(string_pool + 631LL)), ((char *)(string_pool + 3528LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack3.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 663LL)), -1); else env->CallNonvirtualVoidMethod(cstack3.l, (cclasses[36]), (cmethods[54])); 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 3
        // LDC dev/sakura/client/Mahiro_uT; Stack: 3
        cstack3.l = (cstrings[54]);
        // New stack: 4
        // INVOKEVIRTUAL java/lang/StringBuilder.append(Ljava/lang/String;)Ljava/lang/StringBuilder;; Stack: 4
        if (!cclasses[36] || env->IsSameObject(cclasses[36], NULL)) { cclasses_mtx[36].lock(); if (!cclasses[36] || env->IsSameObject(cclasses[36], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[58]))) { cclasses[36] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[36].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[55]) { cmethods[55] = env->GetMethodID((cclasses[36]), ((char *)(string_pool + 3532LL)), ((char *)(string_pool + 3539LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack2.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 488LL)), -1); else { cstack2.l = env->CallObjectMethod(cstack2.l, (cmethods[55]), cstack3.l); refs.insert(cstack2.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 3
        // LDC  : ; Stack: 3
        cstack3.l = (cstrings[59]);
        // New stack: 4
        // INVOKEVIRTUAL java/lang/StringBuilder.append(Ljava/lang/String;)Ljava/lang/StringBuilder;; Stack: 4
        if (!cclasses[36] || env->IsSameObject(cclasses[36], NULL)) { cclasses_mtx[36].lock(); if (!cclasses[36] || env->IsSameObject(cclasses[36], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[58]))) { cclasses[36] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[36].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[55]) { cmethods[55] = env->GetMethodID((cclasses[36]), ((char *)(string_pool + 3532LL)), ((char *)(string_pool + 3539LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack2.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 488LL)), -1); else { cstack2.l = env->CallObjectMethod(cstack2.l, (cmethods[55]), cstack3.l); refs.insert(cstack2.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 3
        // ALOAD 1; Stack: 3
        cstack3.l = clocal1.l; refs.insert(cstack3.l);
        // New stack: 4
        // INVOKEVIRTUAL java/lang/StringBuilder.append(Ljava/lang/String;)Ljava/lang/StringBuilder;; Stack: 4
        if (!cclasses[36] || env->IsSameObject(cclasses[36], NULL)) { cclasses_mtx[36].lock(); if (!cclasses[36] || env->IsSameObject(cclasses[36], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[58]))) { cclasses[36] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[36].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[55]) { cmethods[55] = env->GetMethodID((cclasses[36]), ((char *)(string_pool + 3532LL)), ((char *)(string_pool + 3539LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack2.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 488LL)), -1); else { cstack2.l = env->CallObjectMethod(cstack2.l, (cmethods[55]), cstack3.l); refs.insert(cstack2.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 3
        // LDC  : ; Stack: 3
        cstack3.l = (cstrings[59]);
        // New stack: 4
        // INVOKEVIRTUAL java/lang/StringBuilder.append(Ljava/lang/String;)Ljava/lang/StringBuilder;; Stack: 4
        if (!cclasses[36] || env->IsSameObject(cclasses[36], NULL)) { cclasses_mtx[36].lock(); if (!cclasses[36] || env->IsSameObject(cclasses[36], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[58]))) { cclasses[36] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[36].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[55]) { cmethods[55] = env->GetMethodID((cclasses[36]), ((char *)(string_pool + 3532LL)), ((char *)(string_pool + 3539LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack2.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 488LL)), -1); else { cstack2.l = env->CallObjectMethod(cstack2.l, (cmethods[55]), cstack3.l); refs.insert(cstack2.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 3
        // ALOAD 2; Stack: 3
        cstack3.l = clocal2.l; refs.insert(cstack3.l);
        // New stack: 4
        // INVOKEVIRTUAL java/lang/invoke/MethodType.toString()Ljava/lang/String;; Stack: 4
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[8]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[56]) { cmethods[56] = env->GetMethodID((cclasses[8]), ((char *)(string_pool + 3585LL)), ((char *)(string_pool + 2254LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack3.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 488LL)), -1); else { cstack3.l = env->CallObjectMethod(cstack3.l, (cmethods[56])); refs.insert(cstack3.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 4
        // INVOKEVIRTUAL java/lang/StringBuilder.append(Ljava/lang/String;)Ljava/lang/StringBuilder;; Stack: 4
        if (!cclasses[36] || env->IsSameObject(cclasses[36], NULL)) { cclasses_mtx[36].lock(); if (!cclasses[36] || env->IsSameObject(cclasses[36], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[58]))) { cclasses[36] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[36].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[55]) { cmethods[55] = env->GetMethodID((cclasses[36]), ((char *)(string_pool + 3532LL)), ((char *)(string_pool + 3539LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack2.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 488LL)), -1); else { cstack2.l = env->CallObjectMethod(cstack2.l, (cmethods[55]), cstack3.l); refs.insert(cstack2.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 3
        // INVOKEVIRTUAL java/lang/StringBuilder.toString()Ljava/lang/String;; Stack: 3
        if (!cclasses[36] || env->IsSameObject(cclasses[36], NULL)) { cclasses_mtx[36].lock(); if (!cclasses[36] || env->IsSameObject(cclasses[36], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[58]))) { cclasses[36] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[36].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[57]) { cmethods[57] = env->GetMethodID((cclasses[36]), ((char *)(string_pool + 3585LL)), ((char *)(string_pool + 2254LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack2.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 488LL)), -1); else { cstack2.l = env->CallObjectMethod(cstack2.l, (cmethods[57])); refs.insert(cstack2.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 3
        // ALOAD 4; Stack: 3
        cstack3.l = clocal4.l; refs.insert(cstack3.l);
        // New stack: 4
        // INVOKESPECIAL java/lang/RuntimeException.<init>(Ljava/lang/String;Ljava/lang/Throwable;)V; Stack: 4
        if (!cclasses[32] || env->IsSameObject(cclasses[32], NULL)) { cclasses_mtx[32].lock(); if (!cclasses[32] || env->IsSameObject(cclasses[32], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[53]))) { cclasses[32] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[32].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[44]) { cmethods[44] = env->GetMethodID((cclasses[32]), ((char *)(string_pool + 631LL)), ((char *)(string_pool + 2554LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 663LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[32]), (cmethods[44]), cstack2.l, cstack3.l); 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 74LL)), ((char *)(string_pool + 686LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
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
        L_CATCH_0: if (env->IsInstanceOf(cstack0.l, (cclasses[17]))) { goto L3; }
        env->Throw((jthrowable) cstack0.l); return (jobject) 0;
    }
    
    
    void __ngen_register_methods(JNIEnv *env, jclass clazz) {
        string_pool = string_pool::get_pool();

        if (jstring str = env->NewStringUTF(((char *)(string_pool + 18881LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[31] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 3648LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[2] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 3666LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[1] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 3681LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[51] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 3698LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[52] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 8471LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[19] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 3083LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[7] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 3798LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[9] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 3836LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[36] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 18913LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[30] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 11651LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[16] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 3905LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[28] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 3993LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[12] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 4050LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[58] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 4074LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[0] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 4140LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[13] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 4170LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[15] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 4259LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[44] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 4288LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[59] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 4292LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[33] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 4425LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[5] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 4482LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[43] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 4512LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[45] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 10029LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[49] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 4622LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[23] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 4639LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[40] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 213LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[6] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 4660LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[11] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 2153LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[17] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 4685LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[48] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 8511LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[60] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 18930LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[21] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 10196LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[34] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 4776LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[53] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 18932LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[18] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 18934LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[25] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 5036LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[56] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 3616LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[35] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 2681LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[57] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 11773LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[26] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 18942LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[50] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 18980LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[46] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 19054LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[24] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 19061LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[29] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 4019LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[14] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 4334LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[10] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 4337LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[39] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 19102LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[3] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 4405LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[41] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 4453LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[32] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 19130LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[27] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 19155LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[54] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 2335LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[22] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 4724LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[37] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 19183LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[20] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 19218LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[47] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 4915LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[8] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 4943LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[42] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 4975LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[4] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 4992LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[38] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 5020LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[55] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }

        JNINativeMethod __ngen_methods[] = {
            { ((char *)(string_pool + 18650LL)), ((char *)(string_pool + 1151LL)), (void *)&__ngen_native_Mahiro__1 },
            { ((char *)(string_pool + 12744LL)), ((char *)(string_pool + 1151LL)), (void *)&__ngen_native_Mahiro_E2 },
            { ((char *)(string_pool + 12239LL)), ((char *)(string_pool + 1151LL)), (void *)&__ngen_native_Mahiro_d3 },
            { ((char *)(string_pool + 11583LL)), ((char *)(string_pool + 1151LL)), (void *)&__ngen_native_Mahiro_S4 },
            { ((char *)(string_pool + 213LL)), ((char *)(string_pool + 2224LL)), (void *)&__ngen_native_a6 },
            { ((char *)(string_pool + 213LL)), ((char *)(string_pool + 2335LL)), (void *)&__ngen_native_a7 },
            { ((char *)(string_pool + 213LL)), ((char *)(string_pool + 2681LL)), (void *)&__ngen_native_a8 },
            { ((char *)(string_pool + 213LL)), ((char *)(string_pool + 3083LL)), (void *)&__ngen_native_a9 },
            { ((char *)(string_pool + 2153LL)), ((char *)(string_pool + 8471LL)), (void *)&__ngen_native_b10 },
            { ((char *)(string_pool + 2153LL)), ((char *)(string_pool + 8511LL)), (void *)&__ngen_native_b11 },
            { ((char *)(string_pool + 2153LL)), ((char *)(string_pool + 3083LL)), (void *)&__ngen_native_b12 },
        };

        if (clazz) env->RegisterNatives(clazz, __ngen_methods, sizeof(__ngen_methods) / sizeof(__ngen_methods[0]));
        if (env->ExceptionCheck()) { fprintf(stderr, "Exception occured while registering native_jvm for %s\n", ((char *)(string_pool + 19102LL))); fflush(stderr); env->ExceptionDescribe(); env->ExceptionClear(); }

        {
            jclass hidden_class = env->FindClass(((char *)(string_pool + 5069LL)));
            JNINativeMethod __ngen_hidden_methods[] = {
                { ((char *)(string_pool + 19290LL)), ((char *)(string_pool + 5112LL)), (void *)&__ngen_special_clinit_10_5 },
            };
            if (hidden_class) env->RegisterNatives(hidden_class, __ngen_hidden_methods, sizeof(__ngen_hidden_methods) / sizeof(__ngen_hidden_methods[0]));
            if (env->ExceptionCheck()) { fprintf(stderr, "Exception occured while registering native_jvm for %s\n", ((char *)(string_pool + 4170LL))); fflush(stderr); env->ExceptionDescribe(); env->ExceptionClear(); }
            env->DeleteLocalRef(hidden_class);
        }
    }
}