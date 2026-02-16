#include "../native_jvm.hpp"
#include "../string_pool.hpp"
#include "dev_sakura_L3MonKe_h_6.hpp"

// dev/sakura/L3MonKe_h
namespace native_jvm::classes::__ngen_dev_sakura_L3MonKe_h_6 {

    char *string_pool;

    jstring cstrings[73];
    std::mutex cclasses_mtx[43];
    jclass cclasses[43];
    jmethodID cmethods[58];
    jfieldID cfields[10];

    // L3MonKe_I([Ljava/lang/Object;)V
    void JNICALL __ngen_native_L3MonKe_I1(JNIEnv *env, jclass clazz, jarray arg0) {
        jobject classloader = utils::get_classloader_from_class(env, clazz);
        if (env->ExceptionCheck()) { return (void) 0; }
        if (classloader == nullptr) { env->FatalError(((char *)(string_pool + 47LL))); return (void) 0; }
    
        jobject lookup = nullptr;
        // try-catch-class java/lang/Throwable
        if (!cclasses[0] || env->IsSameObject(cclasses[0], NULL)) { cclasses_mtx[0].lock(); if (!cclasses[0] || env->IsSameObject(cclasses[0], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[0]))) { cclasses[0] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[0].unlock(); if (env->ExceptionCheck()) { return (void) 0; } }
        jvalue cstack0 = {}, cstack1 = {}, cstack2 = {}, cstack3 = {}, cstack4 = {}, cstack5 = {}, cstack6 = {}, cstack7 = {}, cstack8 = {}, cstack9 = {}, cstack10 = {}, cstack11 = {}, cstack12 = {};
        jvalue clocal0 = {}, clocal1 = {}, clocal2 = {}, clocal3 = {}, clocal4 = {}, clocal5 = {}, clocal6 = {}, clocal7 = {};
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
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 98LL)), -1); else { cstack1.l = env->GetObjectArrayElement((jobjectArray) cstack1.l, cstack2.i); refs.insert(cstack1.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 2
        // CHECKCAST java/lang/String; Stack: 2
        if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { cclasses_mtx[1].lock(); if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[1]))) { cclasses[1] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[1].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (cstack1.l != nullptr && !env->IsInstanceOf(cstack1.l, (cclasses[1]))) { utils::throw_re(env, ((char *)(string_pool + 109LL)), (std::string(((char *)(string_pool + 138LL))) + std::string(((char *)(string_pool + 852LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (void) 0; } } 
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
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 98LL)), -1); else { cstack1.l = env->GetObjectArrayElement((jobjectArray) cstack1.l, cstack2.i); refs.insert(cstack1.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 2
        // CHECKCAST java/lang/Long; Stack: 2
        if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { cclasses_mtx[2].lock(); if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[2]))) { cclasses[2] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[2].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (cstack1.l != nullptr && !env->IsInstanceOf(cstack1.l, (cclasses[2]))) { utils::throw_re(env, ((char *)(string_pool + 109LL)), (std::string(((char *)(string_pool + 138LL))) + std::string(((char *)(string_pool + 154LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (void) 0; } } 
        // New stack: 2
        // INVOKEVIRTUAL java/lang/Long.longValue()J; Stack: 2
        if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { cclasses_mtx[2].lock(); if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[2]))) { cclasses[2] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[2].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[0]) { cmethods[0] = env->GetMethodID((cclasses[2]), ((char *)(string_pool + 169LL)), ((char *)(string_pool + 179LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 183LL)), -1); else cstack1.j = env->CallLongMethod(cstack1.l, (cmethods[0])); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 3
        // LSTORE 2; Stack: 3
        clocal2.j = cstack1.j;
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // ICONST_2; Stack: 2
        cstack2.i = 2;
        // New stack: 3
        // AALOAD; Stack: 3
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 98LL)), -1); else { cstack1.l = env->GetObjectArrayElement((jobjectArray) cstack1.l, cstack2.i); refs.insert(cstack1.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 2
        // CHECKCAST java/lang/String; Stack: 2
        if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { cclasses_mtx[1].lock(); if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[1]))) { cclasses[1] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[1].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (cstack1.l != nullptr && !env->IsInstanceOf(cstack1.l, (cclasses[1]))) { utils::throw_re(env, ((char *)(string_pool + 109LL)), (std::string(((char *)(string_pool + 138LL))) + std::string(((char *)(string_pool + 852LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (void) 0; } } 
        // New stack: 2
        // ASTORE 5; Stack: 2
        clocal5.l = cstack1.l; refs.insert(cstack1.l);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // ICONST_3; Stack: 2
        cstack2.i = 3;
        // New stack: 3
        // AALOAD; Stack: 3
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 98LL)), -1); else { cstack1.l = env->GetObjectArrayElement((jobjectArray) cstack1.l, cstack2.i); refs.insert(cstack1.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 2
        // CHECKCAST dev/sakura/L3MonKe_B; Stack: 2
        if (!cclasses[3] || env->IsSameObject(cclasses[3], NULL)) { cclasses_mtx[3].lock(); if (!cclasses[3] || env->IsSameObject(cclasses[3], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[3] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[3].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (cstack1.l != nullptr && !env->IsInstanceOf(cstack1.l, (cclasses[3]))) { utils::throw_re(env, ((char *)(string_pool + 109LL)), (std::string(((char *)(string_pool + 138LL))) + std::string(((char *)(string_pool + 18285LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (void) 0; } } 
        // New stack: 2
        // ASTORE 1; Stack: 2
        clocal1.l = cstack1.l; refs.insert(cstack1.l);
        // New stack: 1
        // POP; Stack: 1
        ;
        // New stack: 0
        // GETSTATIC dev/sakura/L3MonKe_h.a J; Stack: 0
        if (!cclasses[4]  || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cfields[0]) { cfields[0] = env->GetStaticFieldID((cclasses[4]), ((char *)(string_pool + 206LL)), ((char *)(string_pool + 208LL))); if (env->ExceptionCheck()) { return (void) 0; }  } cstack0.j = env->GetStaticLongField((cclasses[4]), (cfields[0])); 
        if (env->ExceptionCheck()) { return (void) 0; }
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
        // LLOAD 2; Stack: 0
        cstack0.j = clocal2.j;
        // New stack: 2
        // DUP2; Stack: 2
        cstack2 = cstack0; cstack3 = cstack1;
        // New stack: 4
        // LDC 11284627908357; Stack: 4
        cstack4.j = 11284627908357LL;
        // New stack: 6
        // LXOR; Stack: 6
        cstack2.j = cstack2.j ^ cstack4.j;
        // New stack: 4
        // LSTORE 6; Stack: 4
        clocal6.j = cstack2.j;
        // New stack: 2
        // POP2; Stack: 2
        ;
        // New stack: 0
        // ALOAD 4; Stack: 0
        cstack0.l = clocal4.l; refs.insert(cstack0.l);
        // New stack: 1
        // ALOAD 5; Stack: 1
        cstack1.l = clocal5.l; refs.insert(cstack1.l);
        // New stack: 2
        // ACONST_NULL; Stack: 2
        cstack2.l = nullptr;
        // New stack: 3
        // ALOAD 1; Stack: 3
        cstack3.l = clocal1.l; refs.insert(cstack3.l);
        // New stack: 4
        // LLOAD 6; Stack: 4
        cstack4.j = clocal6.j;
        // New stack: 6
        // ICONST_5; Stack: 6
        cstack6.i = 5;
        // New stack: 7
        // ANEWARRAY java/lang/Object; Stack: 7
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (cstack6.i < 0) utils::throw_re(env, ((char *)(string_pool + 210LL)), ((char *)(string_pool + 247LL)), -1); else { cstack6.l = env->NewObjectArray(cstack6.i, (cclasses[5]), nullptr); refs.insert(cstack6.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 7
        // DUP_X2; Stack: 7
        cstack7 = cstack6; cstack6 = cstack5; cstack5 = cstack4; cstack4 = cstack7;
        // New stack: 8
        // DUP_X2; Stack: 8
        cstack8 = cstack7; cstack7 = cstack6; cstack6 = cstack5; cstack5 = cstack8;
        // New stack: 9
        // POP; Stack: 9
        ;
        // New stack: 8
        // INVOKESTATIC java/lang/Long.valueOf(J)Ljava/lang/Long;; Stack: 8
        if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { cclasses_mtx[2].lock(); if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[2]))) { cclasses[2] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[2].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[1]) { cmethods[1] = env->GetStaticMethodID((cclasses[2]), ((char *)(string_pool + 1044LL)), ((char *)(string_pool + 1052LL))); if (env->ExceptionCheck()) { return (void) 0; }  } cstack6.l = env->CallStaticObjectMethod((cclasses[2]), (cmethods[1]), cstack6.j); refs.insert(cstack6.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 7
        // ICONST_4; Stack: 7
        cstack7.i = 4;
        // New stack: 8
        // SWAP; Stack: 8
        std::swap(cstack7, cstack6);
        // New stack: 8
        // AASTORE; Stack: 8
        if (cstack5.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 1072LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack5.l, cstack6.i, cstack7.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 5
        // DUP_X1; Stack: 5
        cstack5 = cstack4; cstack4 = cstack3; cstack3 = cstack5;
        // New stack: 6
        // SWAP; Stack: 6
        std::swap(cstack5, cstack4);
        // New stack: 6
        // ICONST_3; Stack: 6
        cstack6.i = 3;
        // New stack: 7
        // SWAP; Stack: 7
        std::swap(cstack6, cstack5);
        // New stack: 7
        // AASTORE; Stack: 7
        if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 1072LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack4.l, cstack5.i, cstack6.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 4
        // DUP_X1; Stack: 4
        cstack4 = cstack3; cstack3 = cstack2; cstack2 = cstack4;
        // New stack: 5
        // SWAP; Stack: 5
        std::swap(cstack4, cstack3);
        // New stack: 5
        // ICONST_2; Stack: 5
        cstack5.i = 2;
        // New stack: 6
        // SWAP; Stack: 6
        std::swap(cstack5, cstack4);
        // New stack: 6
        // AASTORE; Stack: 6
        if (cstack3.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 1072LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack3.l, cstack4.i, cstack5.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
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
        if (cstack2.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 1072LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack2.l, cstack3.i, cstack4.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
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
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 1072LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack1.l, cstack2.i, cstack3.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // LDC 8047477633553849063; Stack: 1
        cstack1.j = 8047477633553849063LL;
        // New stack: 3
        // LLOAD 2; Stack: 3
        cstack3.j = clocal2.j;
        // New stack: 5
        // LABEL L1; Stack: 5
        L1: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 5
        // ICONST_1; Stack: 5
        cstack5.i = 1;
        // New stack: 6
        // ANEWARRAY java/lang/Object; Stack: 6
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack5.i < 0) utils::throw_re(env, ((char *)(string_pool + 210LL)), ((char *)(string_pool + 247LL)), -1); else { cstack5.l = env->NewObjectArray(cstack5.i, (cclasses[5]), nullptr); refs.insert(cstack5.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 6
        // DUP; Stack: 6
        cstack6 = cstack5;
        // New stack: 7
        // LDC Ldev/sakura/L3MonKe_h;; Stack: 7
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack7.l = (cclasses[6]);
        // New stack: 8
        // SWAP; Stack: 8
        std::swap(cstack7, cstack6);
        // New stack: 8
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.7353569731942222.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 8
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack8.l = lookup;
        // New stack: 9
        // LDC Ldev/sakura/client/L3MonKe_f2;; Stack: 9
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[6]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack9.l = (cclasses[7]);
        // New stack: 10
        // LDC a; Stack: 10
        cstack10.l = (cstrings[7]);
        // New stack: 11
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;; Stack: 11
        cstack11.l = (cstrings[8]);
        // New stack: 12
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.7353569731942222.a()Ljava/lang/ClassLoader;; Stack: 12
        cstack12.l = classloader;
        // New stack: 13
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 13
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[2]) { cmethods[2] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 272LL)), ((char *)(string_pool + 299LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack11.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[2]), cstack11.l, cstack12.l); refs.insert(cstack11.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 12
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 12
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[10]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[3]) { cmethods[3] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 372LL)), ((char *)(string_pool + 383LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack8.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 481LL)), -1); else { cstack8.l = env->CallObjectMethod(cstack8.l, (cmethods[3]), cstack9.l, cstack10.l, cstack11.l); refs.insert(cstack8.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 9
        // SWAP; Stack: 9
        std::swap(cstack8, cstack7);
        // New stack: 9
        // LDC \u00d1; Stack: 9
        cstack9.l = (cstrings[11]);
        // New stack: 10
        // SWAP; Stack: 10
        std::swap(cstack9, cstack8);
        // New stack: 10
        // LDC (Ljava/lang/Object;JJ)V; Stack: 10
        cstack10.l = (cstrings[12]);
        // New stack: 11
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.7353569731942222.a()Ljava/lang/ClassLoader;; Stack: 11
        cstack11.l = classloader;
        // New stack: 12
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 12
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[2]) { cmethods[2] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 272LL)), ((char *)(string_pool + 299LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack10.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[2]), cstack10.l, cstack11.l); refs.insert(cstack10.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 11
        // SWAP; Stack: 11
        std::swap(cstack10, cstack9);
        // New stack: 11
        // LDC 0; Stack: 11
        cstack11.i = 0;
        // New stack: 12
        // ANEWARRAY java/lang/Object; Stack: 12
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack11.i < 0) utils::throw_re(env, ((char *)(string_pool + 210LL)), ((char *)(string_pool + 247LL)), -1); else { cstack11.l = env->NewObjectArray(cstack11.i, (cclasses[5]), nullptr); refs.insert(cstack11.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 12
        // CHECKCAST java/lang/Object; Stack: 12
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack11.l != nullptr && !env->IsInstanceOf(cstack11.l, (cclasses[5]))) { utils::throw_re(env, ((char *)(string_pool + 109LL)), (std::string(((char *)(string_pool + 138LL))) + std::string(((char *)(string_pool + 506LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } 
        // New stack: 12
        // SWAP; Stack: 12
        std::swap(cstack11, cstack10);
        // New stack: 12
        // INVOKESTATIC native/magic/1/linkcallsite/obfuscator0.7353569731942222.a(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/invoke/MemberName;; Stack: 12
        cstack6.l = utils::link_call_site(env, cstack6.l, cstack7.l, cstack8.l, cstack9.l, cstack10.l, cstack11.l); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 7
        // POP; Stack: 7
        ;
        // New stack: 6
        // ICONST_0; Stack: 6
        cstack6.i = 0;
        // New stack: 7
        // AALOAD; Stack: 7
        if (cstack5.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 98LL)), -1); else { cstack5.l = env->GetObjectArrayElement((jobjectArray) cstack5.l, cstack6.i); refs.insert(cstack5.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 6
        // DUP; Stack: 6
        cstack6 = cstack5;
        // New stack: 7
        // INSTANCEOF java/lang/invoke/CallSite; Stack: 7
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack6.i = cstack6.l == nullptr ? false : env->IsInstanceOf(cstack6.l, (cclasses[10]));
        // New stack: 7
        // IFEQ L3; Stack: 7
        if (cstack6.i == 0) goto L3;
        // New stack: 6
        // INVOKEINTERFACE java/lang/invoke/CallSite.getTarget()Ljava/lang/invoke/MethodHandle;; Stack: 6
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[10]), ((char *)(string_pool + 523LL)), ((char *)(string_pool + 533LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack5.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 567LL)), -1); else { cstack5.l = env->CallObjectMethod(cstack5.l, (cmethods[4])); refs.insert(cstack5.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 6
        // LABEL L3; Stack: 6
        L3: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 6
        // FRAME FULL L: [[Ljava/lang/Object;, dev/sakura/L3MonKe_B, 4, java/lang/String, java/lang/String, 4] S: [[Ljava/lang/Object;, 4, 4, java/lang/Object]; Stack: 6
        refs.erase(cstack0.l); refs.erase(cstack5.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal4.l); refs.erase(clocal5.l); 
        utils::clear_refs(env, refs);
        // New stack: 6
        // CHECKCAST java/lang/invoke/MethodHandle; Stack: 6
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack5.l != nullptr && !env->IsInstanceOf(cstack5.l, (cclasses[11]))) { utils::throw_re(env, ((char *)(string_pool + 109LL)), (std::string(((char *)(string_pool + 138LL))) + std::string(((char *)(string_pool + 594LL)))).c_str(), -1); 
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
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal4.l); refs.erase(clocal5.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // INSTANCEOF java/lang/BootstrapMethodError; Stack: 2
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } cstack1.i = cstack1.l == nullptr ? false : env->IsInstanceOf(cstack1.l, (cclasses[12]));
        // New stack: 2
        // IFNE L5; Stack: 2
        if (cstack1.i != 0) goto L5;
        // New stack: 1
        // NEW java/lang/BootstrapMethodError; Stack: 1
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (jobject obj = env->AllocObject((cclasses[12]))) { cstack1.l = obj; refs.insert(obj); } 
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
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[5]) { cmethods[5] = env->GetMethodID((cclasses[12]), ((char *)(string_pool + 624LL)), ((char *)(string_pool + 631LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 656LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[12]), (cmethods[5]), cstack2.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // LABEL L5; Stack: 1
        L5: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/Object]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal4.l); refs.erase(clocal5.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 679LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // LABEL L4; Stack: 0
        L4: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // FRAME FULL L: [[Ljava/lang/Object;, dev/sakura/L3MonKe_B, 4, java/lang/String, java/lang/String, 4] S: [[Ljava/lang/Object;, 4, 4, java/lang/invoke/MethodHandle]; Stack: 0
        refs.erase(cstack0.l); refs.erase(cstack5.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal4.l); refs.erase(clocal5.l); 
        utils::clear_refs(env, refs);
        // New stack: 6
        // INVOKESTATIC native/magic/1/invoke/obfuscator0.7353569731942222.a(Ljava/lang/Object;JJLjava/lang/invoke/MethodHandle;)V; Stack: 6
        if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { cclasses_mtx[13].lock(); if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[16]))) { cclasses[13] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[13].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[6]) { cmethods[6] = env->GetStaticMethodID((cclasses[13]), ((char *)(string_pool + 5836LL)), ((char *)(string_pool + 5852LL))); if (env->ExceptionCheck()) { return (void) 0; }  } env->CallStaticVoidMethod((cclasses[13]), (cmethods[6]), cstack0.l, cstack1.j, cstack3.j, cstack5.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // RETURN; Stack: 0
        return;
        // New stack: 0
        return (void) 0;
        L_CATCH_0: if (env->IsInstanceOf(cstack0.l, (cclasses[0]))) { goto L2; }
        env->Throw((jthrowable) cstack0.l); return (void) 0;
    }
    
    // L3MonKe_A([Ljava/lang/Object;)V
    void JNICALL __ngen_native_L3MonKe_A2(JNIEnv *env, jclass clazz, jarray arg0) {
        jobject classloader = utils::get_classloader_from_class(env, clazz);
        if (env->ExceptionCheck()) { return (void) 0; }
        if (classloader == nullptr) { env->FatalError(((char *)(string_pool + 47LL))); return (void) 0; }
    
        jobject lookup = nullptr;
        // try-catch-class java/lang/Throwable
        if (!cclasses[0] || env->IsSameObject(cclasses[0], NULL)) { cclasses_mtx[0].lock(); if (!cclasses[0] || env->IsSameObject(cclasses[0], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[0]))) { cclasses[0] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[0].unlock(); if (env->ExceptionCheck()) { return (void) 0; } }
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
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 98LL)), -1); else { cstack1.l = env->GetObjectArrayElement((jobjectArray) cstack1.l, cstack2.i); refs.insert(cstack1.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 2
        // CHECKCAST java/lang/Long; Stack: 2
        if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { cclasses_mtx[2].lock(); if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[2]))) { cclasses[2] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[2].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (cstack1.l != nullptr && !env->IsInstanceOf(cstack1.l, (cclasses[2]))) { utils::throw_re(env, ((char *)(string_pool + 109LL)), (std::string(((char *)(string_pool + 138LL))) + std::string(((char *)(string_pool + 154LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (void) 0; } } 
        // New stack: 2
        // INVOKEVIRTUAL java/lang/Long.longValue()J; Stack: 2
        if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { cclasses_mtx[2].lock(); if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[2]))) { cclasses[2] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[2].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[0]) { cmethods[0] = env->GetMethodID((cclasses[2]), ((char *)(string_pool + 169LL)), ((char *)(string_pool + 179LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 183LL)), -1); else cstack1.j = env->CallLongMethod(cstack1.l, (cmethods[0])); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 3
        // LSTORE 3; Stack: 3
        clocal3.j = cstack1.j;
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // ICONST_1; Stack: 2
        cstack2.i = 1;
        // New stack: 3
        // AALOAD; Stack: 3
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 98LL)), -1); else { cstack1.l = env->GetObjectArrayElement((jobjectArray) cstack1.l, cstack2.i); refs.insert(cstack1.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 2
        // CHECKCAST java/lang/String; Stack: 2
        if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { cclasses_mtx[1].lock(); if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[1]))) { cclasses[1] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[1].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (cstack1.l != nullptr && !env->IsInstanceOf(cstack1.l, (cclasses[1]))) { utils::throw_re(env, ((char *)(string_pool + 109LL)), (std::string(((char *)(string_pool + 138LL))) + std::string(((char *)(string_pool + 852LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (void) 0; } } 
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
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 98LL)), -1); else { cstack1.l = env->GetObjectArrayElement((jobjectArray) cstack1.l, cstack2.i); refs.insert(cstack1.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 2
        // CHECKCAST java/lang/String; Stack: 2
        if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { cclasses_mtx[1].lock(); if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[1]))) { cclasses[1] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[1].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (cstack1.l != nullptr && !env->IsInstanceOf(cstack1.l, (cclasses[1]))) { utils::throw_re(env, ((char *)(string_pool + 109LL)), (std::string(((char *)(string_pool + 138LL))) + std::string(((char *)(string_pool + 852LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (void) 0; } } 
        // New stack: 2
        // ASTORE 6; Stack: 2
        clocal6.l = cstack1.l; refs.insert(cstack1.l);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // ICONST_3; Stack: 2
        cstack2.i = 3;
        // New stack: 3
        // AALOAD; Stack: 3
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 98LL)), -1); else { cstack1.l = env->GetObjectArrayElement((jobjectArray) cstack1.l, cstack2.i); refs.insert(cstack1.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 2
        // CHECKCAST java/lang/String; Stack: 2
        if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { cclasses_mtx[1].lock(); if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[1]))) { cclasses[1] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[1].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (cstack1.l != nullptr && !env->IsInstanceOf(cstack1.l, (cclasses[1]))) { utils::throw_re(env, ((char *)(string_pool + 109LL)), (std::string(((char *)(string_pool + 138LL))) + std::string(((char *)(string_pool + 852LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (void) 0; } } 
        // New stack: 2
        // ASTORE 2; Stack: 2
        clocal2.l = cstack1.l; refs.insert(cstack1.l);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // ICONST_4; Stack: 2
        cstack2.i = 4;
        // New stack: 3
        // AALOAD; Stack: 3
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 98LL)), -1); else { cstack1.l = env->GetObjectArrayElement((jobjectArray) cstack1.l, cstack2.i); refs.insert(cstack1.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 2
        // CHECKCAST dev/sakura/L3MonKe_B; Stack: 2
        if (!cclasses[3] || env->IsSameObject(cclasses[3], NULL)) { cclasses_mtx[3].lock(); if (!cclasses[3] || env->IsSameObject(cclasses[3], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[3] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[3].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (cstack1.l != nullptr && !env->IsInstanceOf(cstack1.l, (cclasses[3]))) { utils::throw_re(env, ((char *)(string_pool + 109LL)), (std::string(((char *)(string_pool + 138LL))) + std::string(((char *)(string_pool + 18285LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (void) 0; } } 
        // New stack: 2
        // ASTORE 5; Stack: 2
        clocal5.l = cstack1.l; refs.insert(cstack1.l);
        // New stack: 1
        // POP; Stack: 1
        ;
        // New stack: 0
        // GETSTATIC dev/sakura/L3MonKe_h.a J; Stack: 0
        if (!cclasses[4]  || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cfields[0]) { cfields[0] = env->GetStaticFieldID((cclasses[4]), ((char *)(string_pool + 206LL)), ((char *)(string_pool + 208LL))); if (env->ExceptionCheck()) { return (void) 0; }  } cstack0.j = env->GetStaticLongField((cclasses[4]), (cfields[0])); 
        if (env->ExceptionCheck()) { return (void) 0; }
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
        // LDC 18331064881530; Stack: 4
        cstack4.j = 18331064881530LL;
        // New stack: 6
        // LXOR; Stack: 6
        cstack2.j = cstack2.j ^ cstack4.j;
        // New stack: 4
        // LSTORE 7; Stack: 4
        clocal7.j = cstack2.j;
        // New stack: 2
        // POP2; Stack: 2
        ;
        // New stack: 0
        // ALOAD 1; Stack: 0
        cstack0.l = clocal1.l; refs.insert(cstack0.l);
        // New stack: 1
        // ALOAD 6; Stack: 1
        cstack1.l = clocal6.l; refs.insert(cstack1.l);
        // New stack: 2
        // ALOAD 2; Stack: 2
        cstack2.l = clocal2.l; refs.insert(cstack2.l);
        // New stack: 3
        // ALOAD 5; Stack: 3
        cstack3.l = clocal5.l; refs.insert(cstack3.l);
        // New stack: 4
        // LLOAD 7; Stack: 4
        cstack4.j = clocal7.j;
        // New stack: 6
        // ICONST_5; Stack: 6
        cstack6.i = 5;
        // New stack: 7
        // ANEWARRAY java/lang/Object; Stack: 7
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (cstack6.i < 0) utils::throw_re(env, ((char *)(string_pool + 210LL)), ((char *)(string_pool + 247LL)), -1); else { cstack6.l = env->NewObjectArray(cstack6.i, (cclasses[5]), nullptr); refs.insert(cstack6.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 7
        // DUP_X2; Stack: 7
        cstack7 = cstack6; cstack6 = cstack5; cstack5 = cstack4; cstack4 = cstack7;
        // New stack: 8
        // DUP_X2; Stack: 8
        cstack8 = cstack7; cstack7 = cstack6; cstack6 = cstack5; cstack5 = cstack8;
        // New stack: 9
        // POP; Stack: 9
        ;
        // New stack: 8
        // INVOKESTATIC java/lang/Long.valueOf(J)Ljava/lang/Long;; Stack: 8
        if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { cclasses_mtx[2].lock(); if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[2]))) { cclasses[2] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[2].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[1]) { cmethods[1] = env->GetStaticMethodID((cclasses[2]), ((char *)(string_pool + 1044LL)), ((char *)(string_pool + 1052LL))); if (env->ExceptionCheck()) { return (void) 0; }  } cstack6.l = env->CallStaticObjectMethod((cclasses[2]), (cmethods[1]), cstack6.j); refs.insert(cstack6.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 7
        // ICONST_4; Stack: 7
        cstack7.i = 4;
        // New stack: 8
        // SWAP; Stack: 8
        std::swap(cstack7, cstack6);
        // New stack: 8
        // AASTORE; Stack: 8
        if (cstack5.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 1072LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack5.l, cstack6.i, cstack7.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 5
        // DUP_X1; Stack: 5
        cstack5 = cstack4; cstack4 = cstack3; cstack3 = cstack5;
        // New stack: 6
        // SWAP; Stack: 6
        std::swap(cstack5, cstack4);
        // New stack: 6
        // ICONST_3; Stack: 6
        cstack6.i = 3;
        // New stack: 7
        // SWAP; Stack: 7
        std::swap(cstack6, cstack5);
        // New stack: 7
        // AASTORE; Stack: 7
        if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 1072LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack4.l, cstack5.i, cstack6.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 4
        // DUP_X1; Stack: 4
        cstack4 = cstack3; cstack3 = cstack2; cstack2 = cstack4;
        // New stack: 5
        // SWAP; Stack: 5
        std::swap(cstack4, cstack3);
        // New stack: 5
        // ICONST_2; Stack: 5
        cstack5.i = 2;
        // New stack: 6
        // SWAP; Stack: 6
        std::swap(cstack5, cstack4);
        // New stack: 6
        // AASTORE; Stack: 6
        if (cstack3.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 1072LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack3.l, cstack4.i, cstack5.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
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
        if (cstack2.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 1072LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack2.l, cstack3.i, cstack4.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
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
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 1072LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack1.l, cstack2.i, cstack3.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // LDC -2751285224376603496; Stack: 1
        cstack1.j = -2751285224376603496LL;
        // New stack: 3
        // LLOAD 3; Stack: 3
        cstack3.j = clocal3.j;
        // New stack: 5
        // LABEL L1; Stack: 5
        L1: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 5
        // ICONST_1; Stack: 5
        cstack5.i = 1;
        // New stack: 6
        // ANEWARRAY java/lang/Object; Stack: 6
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack5.i < 0) utils::throw_re(env, ((char *)(string_pool + 210LL)), ((char *)(string_pool + 247LL)), -1); else { cstack5.l = env->NewObjectArray(cstack5.i, (cclasses[5]), nullptr); refs.insert(cstack5.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 6
        // DUP; Stack: 6
        cstack6 = cstack5;
        // New stack: 7
        // LDC Ldev/sakura/L3MonKe_h;; Stack: 7
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack7.l = (cclasses[6]);
        // New stack: 8
        // SWAP; Stack: 8
        std::swap(cstack7, cstack6);
        // New stack: 8
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.7353569731942222.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 8
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack8.l = lookup;
        // New stack: 9
        // LDC Ldev/sakura/client/L3MonKe_f2;; Stack: 9
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[6]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack9.l = (cclasses[7]);
        // New stack: 10
        // LDC a; Stack: 10
        cstack10.l = (cstrings[7]);
        // New stack: 11
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;; Stack: 11
        cstack11.l = (cstrings[8]);
        // New stack: 12
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.7353569731942222.a()Ljava/lang/ClassLoader;; Stack: 12
        cstack12.l = classloader;
        // New stack: 13
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 13
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[2]) { cmethods[2] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 272LL)), ((char *)(string_pool + 299LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack11.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[2]), cstack11.l, cstack12.l); refs.insert(cstack11.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 12
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 12
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[10]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[3]) { cmethods[3] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 372LL)), ((char *)(string_pool + 383LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack8.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 481LL)), -1); else { cstack8.l = env->CallObjectMethod(cstack8.l, (cmethods[3]), cstack9.l, cstack10.l, cstack11.l); refs.insert(cstack8.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 9
        // SWAP; Stack: 9
        std::swap(cstack8, cstack7);
        // New stack: 9
        // LDC \u00d1; Stack: 9
        cstack9.l = (cstrings[11]);
        // New stack: 10
        // SWAP; Stack: 10
        std::swap(cstack9, cstack8);
        // New stack: 10
        // LDC (Ljava/lang/Object;JJ)V; Stack: 10
        cstack10.l = (cstrings[12]);
        // New stack: 11
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.7353569731942222.a()Ljava/lang/ClassLoader;; Stack: 11
        cstack11.l = classloader;
        // New stack: 12
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 12
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[2]) { cmethods[2] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 272LL)), ((char *)(string_pool + 299LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack10.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[2]), cstack10.l, cstack11.l); refs.insert(cstack10.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 11
        // SWAP; Stack: 11
        std::swap(cstack10, cstack9);
        // New stack: 11
        // LDC 0; Stack: 11
        cstack11.i = 0;
        // New stack: 12
        // ANEWARRAY java/lang/Object; Stack: 12
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack11.i < 0) utils::throw_re(env, ((char *)(string_pool + 210LL)), ((char *)(string_pool + 247LL)), -1); else { cstack11.l = env->NewObjectArray(cstack11.i, (cclasses[5]), nullptr); refs.insert(cstack11.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 12
        // CHECKCAST java/lang/Object; Stack: 12
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack11.l != nullptr && !env->IsInstanceOf(cstack11.l, (cclasses[5]))) { utils::throw_re(env, ((char *)(string_pool + 109LL)), (std::string(((char *)(string_pool + 138LL))) + std::string(((char *)(string_pool + 506LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } 
        // New stack: 12
        // SWAP; Stack: 12
        std::swap(cstack11, cstack10);
        // New stack: 12
        // INVOKESTATIC native/magic/1/linkcallsite/obfuscator0.7353569731942222.a(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/invoke/MemberName;; Stack: 12
        cstack6.l = utils::link_call_site(env, cstack6.l, cstack7.l, cstack8.l, cstack9.l, cstack10.l, cstack11.l); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 7
        // POP; Stack: 7
        ;
        // New stack: 6
        // ICONST_0; Stack: 6
        cstack6.i = 0;
        // New stack: 7
        // AALOAD; Stack: 7
        if (cstack5.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 98LL)), -1); else { cstack5.l = env->GetObjectArrayElement((jobjectArray) cstack5.l, cstack6.i); refs.insert(cstack5.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 6
        // DUP; Stack: 6
        cstack6 = cstack5;
        // New stack: 7
        // INSTANCEOF java/lang/invoke/CallSite; Stack: 7
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack6.i = cstack6.l == nullptr ? false : env->IsInstanceOf(cstack6.l, (cclasses[10]));
        // New stack: 7
        // IFEQ L3; Stack: 7
        if (cstack6.i == 0) goto L3;
        // New stack: 6
        // INVOKEINTERFACE java/lang/invoke/CallSite.getTarget()Ljava/lang/invoke/MethodHandle;; Stack: 6
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[10]), ((char *)(string_pool + 523LL)), ((char *)(string_pool + 533LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack5.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 567LL)), -1); else { cstack5.l = env->CallObjectMethod(cstack5.l, (cmethods[4])); refs.insert(cstack5.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 6
        // LABEL L3; Stack: 6
        L3: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 6
        // FRAME FULL L: [[Ljava/lang/Object;, java/lang/String, java/lang/String, 4, dev/sakura/L3MonKe_B, java/lang/String, 4] S: [[Ljava/lang/Object;, 4, 4, java/lang/Object]; Stack: 6
        refs.erase(cstack0.l); refs.erase(cstack5.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal5.l); refs.erase(clocal6.l); 
        utils::clear_refs(env, refs);
        // New stack: 6
        // CHECKCAST java/lang/invoke/MethodHandle; Stack: 6
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack5.l != nullptr && !env->IsInstanceOf(cstack5.l, (cclasses[11]))) { utils::throw_re(env, ((char *)(string_pool + 109LL)), (std::string(((char *)(string_pool + 138LL))) + std::string(((char *)(string_pool + 594LL)))).c_str(), -1); 
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
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal5.l); refs.erase(clocal6.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // INSTANCEOF java/lang/BootstrapMethodError; Stack: 2
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } cstack1.i = cstack1.l == nullptr ? false : env->IsInstanceOf(cstack1.l, (cclasses[12]));
        // New stack: 2
        // IFNE L5; Stack: 2
        if (cstack1.i != 0) goto L5;
        // New stack: 1
        // NEW java/lang/BootstrapMethodError; Stack: 1
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (jobject obj = env->AllocObject((cclasses[12]))) { cstack1.l = obj; refs.insert(obj); } 
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
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[5]) { cmethods[5] = env->GetMethodID((cclasses[12]), ((char *)(string_pool + 624LL)), ((char *)(string_pool + 631LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 656LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[12]), (cmethods[5]), cstack2.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // LABEL L5; Stack: 1
        L5: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/Object]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal5.l); refs.erase(clocal6.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 679LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // LABEL L4; Stack: 0
        L4: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // FRAME FULL L: [[Ljava/lang/Object;, java/lang/String, java/lang/String, 4, dev/sakura/L3MonKe_B, java/lang/String, 4] S: [[Ljava/lang/Object;, 4, 4, java/lang/invoke/MethodHandle]; Stack: 0
        refs.erase(cstack0.l); refs.erase(cstack5.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal5.l); refs.erase(clocal6.l); 
        utils::clear_refs(env, refs);
        // New stack: 6
        // INVOKESTATIC native/magic/1/invoke/obfuscator0.7353569731942222.a(Ljava/lang/Object;JJLjava/lang/invoke/MethodHandle;)V; Stack: 6
        if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { cclasses_mtx[13].lock(); if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[16]))) { cclasses[13] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[13].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[6]) { cmethods[6] = env->GetStaticMethodID((cclasses[13]), ((char *)(string_pool + 5836LL)), ((char *)(string_pool + 5852LL))); if (env->ExceptionCheck()) { return (void) 0; }  } env->CallStaticVoidMethod((cclasses[13]), (cmethods[6]), cstack0.l, cstack1.j, cstack3.j, cstack5.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // RETURN; Stack: 0
        return;
        // New stack: 0
        return (void) 0;
        L_CATCH_0: if (env->IsInstanceOf(cstack0.l, (cclasses[0]))) { goto L2; }
        env->Throw((jthrowable) cstack0.l); return (void) 0;
    }
    
    // L3MonKe_p([Ljava/lang/Object;)V
    void JNICALL __ngen_native_L3MonKe_p3(JNIEnv *env, jclass clazz, jarray arg0) {
        jobject classloader = utils::get_classloader_from_class(env, clazz);
        if (env->ExceptionCheck()) { return (void) 0; }
        if (classloader == nullptr) { env->FatalError(((char *)(string_pool + 47LL))); return (void) 0; }
    
        jobject lookup = nullptr;
        // try-catch-class java/lang/Throwable
        if (!cclasses[0] || env->IsSameObject(cclasses[0], NULL)) { cclasses_mtx[0].lock(); if (!cclasses[0] || env->IsSameObject(cclasses[0], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[0]))) { cclasses[0] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[0].unlock(); if (env->ExceptionCheck()) { return (void) 0; } }
        jvalue cstack0 = {}, cstack1 = {}, cstack2 = {}, cstack3 = {}, cstack4 = {}, cstack5 = {}, cstack6 = {}, cstack7 = {}, cstack8 = {}, cstack9 = {}, cstack10 = {}, cstack11 = {}, cstack12 = {}, cstack13 = {}, cstack14 = {}, cstack15 = {}, cstack16 = {}, cstack17 = {}, cstack18 = {}, cstack19 = {}, cstack20 = {}, cstack21 = {}, cstack22 = {}, cstack23 = {}, cstack24 = {}, cstack25 = {};
        jvalue clocal0 = {}, clocal1 = {}, clocal2 = {}, clocal3 = {}, clocal4 = {}, clocal5 = {}, clocal6 = {}, clocal7 = {}, clocal8 = {}, clocal9 = {}, clocal10 = {}, clocal11 = {}, clocal12 = {}, clocal13 = {}, clocal14 = {}, clocal15 = {}, clocal16 = {}, clocal17 = {}, clocal18 = {}, clocal19 = {}, clocal20 = {}, clocal21 = {};
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
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 98LL)), -1); else { cstack1.l = env->GetObjectArrayElement((jobjectArray) cstack1.l, cstack2.i); refs.insert(cstack1.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 2
        // CHECKCAST java/lang/String; Stack: 2
        if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { cclasses_mtx[1].lock(); if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[1]))) { cclasses[1] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[1].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (cstack1.l != nullptr && !env->IsInstanceOf(cstack1.l, (cclasses[1]))) { utils::throw_re(env, ((char *)(string_pool + 109LL)), (std::string(((char *)(string_pool + 138LL))) + std::string(((char *)(string_pool + 852LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (void) 0; } } 
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
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 98LL)), -1); else { cstack1.l = env->GetObjectArrayElement((jobjectArray) cstack1.l, cstack2.i); refs.insert(cstack1.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 2
        // CHECKCAST java/lang/String; Stack: 2
        if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { cclasses_mtx[1].lock(); if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[1]))) { cclasses[1] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[1].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (cstack1.l != nullptr && !env->IsInstanceOf(cstack1.l, (cclasses[1]))) { utils::throw_re(env, ((char *)(string_pool + 109LL)), (std::string(((char *)(string_pool + 138LL))) + std::string(((char *)(string_pool + 852LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (void) 0; } } 
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
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 98LL)), -1); else { cstack1.l = env->GetObjectArrayElement((jobjectArray) cstack1.l, cstack2.i); refs.insert(cstack1.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 2
        // CHECKCAST java/lang/String; Stack: 2
        if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { cclasses_mtx[1].lock(); if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[1]))) { cclasses[1] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[1].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (cstack1.l != nullptr && !env->IsInstanceOf(cstack1.l, (cclasses[1]))) { utils::throw_re(env, ((char *)(string_pool + 109LL)), (std::string(((char *)(string_pool + 138LL))) + std::string(((char *)(string_pool + 852LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (void) 0; } } 
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
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 98LL)), -1); else { cstack1.l = env->GetObjectArrayElement((jobjectArray) cstack1.l, cstack2.i); refs.insert(cstack1.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 2
        // CHECKCAST dev/sakura/L3MonKe_B; Stack: 2
        if (!cclasses[3] || env->IsSameObject(cclasses[3], NULL)) { cclasses_mtx[3].lock(); if (!cclasses[3] || env->IsSameObject(cclasses[3], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[3]))) { cclasses[3] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[3].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (cstack1.l != nullptr && !env->IsInstanceOf(cstack1.l, (cclasses[3]))) { utils::throw_re(env, ((char *)(string_pool + 109LL)), (std::string(((char *)(string_pool + 138LL))) + std::string(((char *)(string_pool + 18285LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (void) 0; } } 
        // New stack: 2
        // ASTORE 2; Stack: 2
        clocal2.l = cstack1.l; refs.insert(cstack1.l);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // ICONST_4; Stack: 2
        cstack2.i = 4;
        // New stack: 3
        // AALOAD; Stack: 3
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 98LL)), -1); else { cstack1.l = env->GetObjectArrayElement((jobjectArray) cstack1.l, cstack2.i); refs.insert(cstack1.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 2
        // CHECKCAST java/lang/Long; Stack: 2
        if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { cclasses_mtx[2].lock(); if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[2]))) { cclasses[2] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[2].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (cstack1.l != nullptr && !env->IsInstanceOf(cstack1.l, (cclasses[2]))) { utils::throw_re(env, ((char *)(string_pool + 109LL)), (std::string(((char *)(string_pool + 138LL))) + std::string(((char *)(string_pool + 154LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (void) 0; } } 
        // New stack: 2
        // INVOKEVIRTUAL java/lang/Long.longValue()J; Stack: 2
        if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { cclasses_mtx[2].lock(); if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[2]))) { cclasses[2] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[2].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[0]) { cmethods[0] = env->GetMethodID((cclasses[2]), ((char *)(string_pool + 169LL)), ((char *)(string_pool + 179LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 183LL)), -1); else cstack1.j = env->CallLongMethod(cstack1.l, (cmethods[0])); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 3
        // LSTORE 3; Stack: 3
        clocal3.j = cstack1.j;
        // New stack: 1
        // POP; Stack: 1
        ;
        // New stack: 0
        // GETSTATIC dev/sakura/L3MonKe_h.a J; Stack: 0
        if (!cclasses[4]  || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cfields[0]) { cfields[0] = env->GetStaticFieldID((cclasses[4]), ((char *)(string_pool + 206LL)), ((char *)(string_pool + 208LL))); if (env->ExceptionCheck()) { return (void) 0; }  } cstack0.j = env->GetStaticLongField((cclasses[4]), (cfields[0])); 
        if (env->ExceptionCheck()) { return (void) 0; }
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
        // LDC 137543918881980; Stack: 4
        cstack4.j = 137543918881980LL;
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
        // LDC 84639820535159; Stack: 4
        cstack4.j = 84639820535159LL;
        // New stack: 6
        // LXOR; Stack: 6
        cstack2.j = cstack2.j ^ cstack4.j;
        // New stack: 4
        // LSTORE 9; Stack: 4
        clocal9.j = cstack2.j;
        // New stack: 2
        // DUP2; Stack: 2
        cstack2 = cstack0; cstack3 = cstack1;
        // New stack: 4
        // LDC 3404847214796; Stack: 4
        cstack4.j = 3404847214796LL;
        // New stack: 6
        // LXOR; Stack: 6
        cstack2.j = cstack2.j ^ cstack4.j;
        // New stack: 4
        // LSTORE 11; Stack: 4
        clocal11.j = cstack2.j;
        // New stack: 2
        // POP2; Stack: 2
        ;
        // New stack: 0
        // LDC 630931152189739028; Stack: 0
        cstack0.j = 630931152189739028LL;
        // New stack: 2
        // LLOAD 3; Stack: 2
        cstack2.j = clocal3.j;
        // New stack: 4
        // LABEL L15; Stack: 4
        L15: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 4
        // ICONST_1; Stack: 4
        cstack4.i = 1;
        // New stack: 5
        // ANEWARRAY java/lang/Object; Stack: 5
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack4.i < 0) utils::throw_re(env, ((char *)(string_pool + 210LL)), ((char *)(string_pool + 247LL)), -1); else { cstack4.l = env->NewObjectArray(cstack4.i, (cclasses[5]), nullptr); refs.insert(cstack4.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 5
        // DUP; Stack: 5
        cstack5 = cstack4;
        // New stack: 6
        // LDC Ldev/sakura/L3MonKe_h;; Stack: 6
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack6.l = (cclasses[6]);
        // New stack: 7
        // SWAP; Stack: 7
        std::swap(cstack6, cstack5);
        // New stack: 7
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.7353569731942222.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 7
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack7.l = lookup;
        // New stack: 8
        // LDC Ldev/sakura/client/L3MonKe_f2;; Stack: 8
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[6]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack8.l = (cclasses[7]);
        // New stack: 9
        // LDC a; Stack: 9
        cstack9.l = (cstrings[7]);
        // New stack: 10
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;; Stack: 10
        cstack10.l = (cstrings[8]);
        // New stack: 11
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.7353569731942222.a()Ljava/lang/ClassLoader;; Stack: 11
        cstack11.l = classloader;
        // New stack: 12
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 12
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[2]) { cmethods[2] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 272LL)), ((char *)(string_pool + 299LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack10.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[2]), cstack10.l, cstack11.l); refs.insert(cstack10.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 11
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 11
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[10]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[3]) { cmethods[3] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 372LL)), ((char *)(string_pool + 383LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack7.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 481LL)), -1); else { cstack7.l = env->CallObjectMethod(cstack7.l, (cmethods[3]), cstack8.l, cstack9.l, cstack10.l); refs.insert(cstack7.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 8
        // SWAP; Stack: 8
        std::swap(cstack7, cstack6);
        // New stack: 8
        // LDC \u00d1; Stack: 8
        cstack8.l = (cstrings[11]);
        // New stack: 9
        // SWAP; Stack: 9
        std::swap(cstack8, cstack7);
        // New stack: 9
        // LDC (JJ)[Ljava/lang/String;; Stack: 9
        cstack9.l = (cstrings[17]);
        // New stack: 10
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.7353569731942222.a()Ljava/lang/ClassLoader;; Stack: 10
        cstack10.l = classloader;
        // New stack: 11
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 11
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[2]) { cmethods[2] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 272LL)), ((char *)(string_pool + 299LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack9.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[2]), cstack9.l, cstack10.l); refs.insert(cstack9.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 10
        // SWAP; Stack: 10
        std::swap(cstack9, cstack8);
        // New stack: 10
        // LDC 0; Stack: 10
        cstack10.i = 0;
        // New stack: 11
        // ANEWARRAY java/lang/Object; Stack: 11
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack10.i < 0) utils::throw_re(env, ((char *)(string_pool + 210LL)), ((char *)(string_pool + 247LL)), -1); else { cstack10.l = env->NewObjectArray(cstack10.i, (cclasses[5]), nullptr); refs.insert(cstack10.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 11
        // CHECKCAST java/lang/Object; Stack: 11
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack10.l != nullptr && !env->IsInstanceOf(cstack10.l, (cclasses[5]))) { utils::throw_re(env, ((char *)(string_pool + 109LL)), (std::string(((char *)(string_pool + 138LL))) + std::string(((char *)(string_pool + 506LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } 
        // New stack: 11
        // SWAP; Stack: 11
        std::swap(cstack10, cstack9);
        // New stack: 11
        // INVOKESTATIC native/magic/1/linkcallsite/obfuscator0.7353569731942222.a(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/invoke/MemberName;; Stack: 11
        cstack5.l = utils::link_call_site(env, cstack5.l, cstack6.l, cstack7.l, cstack8.l, cstack9.l, cstack10.l); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 6
        // POP; Stack: 6
        ;
        // New stack: 5
        // ICONST_0; Stack: 5
        cstack5.i = 0;
        // New stack: 6
        // AALOAD; Stack: 6
        if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 98LL)), -1); else { cstack4.l = env->GetObjectArrayElement((jobjectArray) cstack4.l, cstack5.i); refs.insert(cstack4.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 5
        // DUP; Stack: 5
        cstack5 = cstack4;
        // New stack: 6
        // INSTANCEOF java/lang/invoke/CallSite; Stack: 6
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack5.i = cstack5.l == nullptr ? false : env->IsInstanceOf(cstack5.l, (cclasses[10]));
        // New stack: 6
        // IFEQ L17; Stack: 6
        if (cstack5.i == 0) goto L17;
        // New stack: 5
        // INVOKEINTERFACE java/lang/invoke/CallSite.getTarget()Ljava/lang/invoke/MethodHandle;; Stack: 5
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[10]), ((char *)(string_pool + 523LL)), ((char *)(string_pool + 533LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 567LL)), -1); else { cstack4.l = env->CallObjectMethod(cstack4.l, (cmethods[4])); refs.insert(cstack4.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 5
        // LABEL L17; Stack: 5
        L17: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 5
        // FRAME FULL L: [[Ljava/lang/Object;, java/lang/String, dev/sakura/L3MonKe_B, 4, java/lang/String, java/lang/String, 4, 4, 4] S: [4, 4, java/lang/Object]; Stack: 5
        refs.erase(cstack4.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal5.l); refs.erase(clocal6.l); 
        utils::clear_refs(env, refs);
        // New stack: 5
        // CHECKCAST java/lang/invoke/MethodHandle; Stack: 5
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack4.l != nullptr && !env->IsInstanceOf(cstack4.l, (cclasses[11]))) { utils::throw_re(env, ((char *)(string_pool + 109LL)), (std::string(((char *)(string_pool + 138LL))) + std::string(((char *)(string_pool + 594LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } 
        // New stack: 5
        // GOTO L18; Stack: 5
        goto L18;
        // New stack: 5
        // LABEL L16; Stack: 5
        L16: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 5
        // FRAME SAME1 L: null S: [java/lang/Throwable]; Stack: 5
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal5.l); refs.erase(clocal6.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // INSTANCEOF java/lang/BootstrapMethodError; Stack: 2
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } cstack1.i = cstack1.l == nullptr ? false : env->IsInstanceOf(cstack1.l, (cclasses[12]));
        // New stack: 2
        // IFNE L19; Stack: 2
        if (cstack1.i != 0) goto L19;
        // New stack: 1
        // NEW java/lang/BootstrapMethodError; Stack: 1
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (jobject obj = env->AllocObject((cclasses[12]))) { cstack1.l = obj; refs.insert(obj); } 
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
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[5]) { cmethods[5] = env->GetMethodID((cclasses[12]), ((char *)(string_pool + 624LL)), ((char *)(string_pool + 631LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 656LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[12]), (cmethods[5]), cstack2.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // LABEL L19; Stack: 1
        L19: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/Object]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal5.l); refs.erase(clocal6.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 679LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // LABEL L18; Stack: 0
        L18: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // FRAME FULL L: [[Ljava/lang/Object;, java/lang/String, dev/sakura/L3MonKe_B, 4, java/lang/String, java/lang/String, 4, 4, 4] S: [4, 4, java/lang/invoke/MethodHandle]; Stack: 0
        refs.erase(cstack4.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal5.l); refs.erase(clocal6.l); 
        utils::clear_refs(env, refs);
        // New stack: 5
        // INVOKESTATIC native/magic/1/invoke/obfuscator0.7353569731942222.a(JJLjava/lang/invoke/MethodHandle;)[Ljava/lang/String;; Stack: 5
        if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { cclasses_mtx[13].lock(); if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[16]))) { cclasses[13] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[13].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[7]) { cmethods[7] = env->GetStaticMethodID((cclasses[13]), ((char *)(string_pool + 690LL)), ((char *)(string_pool + 705LL))); if (env->ExceptionCheck()) { return (void) 0; }  } cstack0.l = env->CallStaticObjectMethod((cclasses[13]), (cmethods[7]), cstack0.j, cstack2.j, cstack4.l); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // CHECKCAST [Ljava/lang/String;; Stack: 1
        if (!cclasses[14] || env->IsSameObject(cclasses[14], NULL)) { cclasses_mtx[14].lock(); if (!cclasses[14] || env->IsSameObject(cclasses[14], NULL)) { if (jclass clazz = env->FindClass(((char *)(string_pool + 759LL)))) { cclasses[14] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[14].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (cstack0.l != nullptr && !env->IsInstanceOf(cstack0.l, (cclasses[14]))) { utils::throw_re(env, ((char *)(string_pool + 109LL)), (std::string(((char *)(string_pool + 138LL))) + std::string(((char *)(string_pool + 759LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (void) 0; } } 
        // New stack: 1
        // LLOAD 11; Stack: 1
        cstack1.j = clocal11.j;
        // New stack: 3
        // ICONST_1; Stack: 3
        cstack3.i = 1;
        // New stack: 4
        // ANEWARRAY java/lang/Object; Stack: 4
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (cstack3.i < 0) utils::throw_re(env, ((char *)(string_pool + 210LL)), ((char *)(string_pool + 247LL)), -1); else { cstack3.l = env->NewObjectArray(cstack3.i, (cclasses[5]), nullptr); refs.insert(cstack3.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
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
        if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { cclasses_mtx[2].lock(); if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[2]))) { cclasses[2] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[2].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[1]) { cmethods[1] = env->GetStaticMethodID((cclasses[2]), ((char *)(string_pool + 1044LL)), ((char *)(string_pool + 1052LL))); if (env->ExceptionCheck()) { return (void) 0; }  } cstack3.l = env->CallStaticObjectMethod((cclasses[2]), (cmethods[1]), cstack3.j); refs.insert(cstack3.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 4
        // ICONST_0; Stack: 4
        cstack4.i = 0;
        // New stack: 5
        // SWAP; Stack: 5
        std::swap(cstack4, cstack3);
        // New stack: 5
        // AASTORE; Stack: 5
        if (cstack2.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 1072LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack2.l, cstack3.i, cstack4.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 2
        // LDC 614622991636432897; Stack: 2
        cstack2.j = 614622991636432897LL;
        // New stack: 4
        // LLOAD 3; Stack: 4
        cstack4.j = clocal3.j;
        // New stack: 6
        // LABEL L13; Stack: 6
        L13: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 6
        // ICONST_1; Stack: 6
        cstack6.i = 1;
        // New stack: 7
        // ANEWARRAY java/lang/Object; Stack: 7
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (cstack6.i < 0) utils::throw_re(env, ((char *)(string_pool + 210LL)), ((char *)(string_pool + 247LL)), -1); else { cstack6.l = env->NewObjectArray(cstack6.i, (cclasses[5]), nullptr); refs.insert(cstack6.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 7
        // DUP; Stack: 7
        cstack7 = cstack6;
        // New stack: 8
        // LDC Ldev/sakura/L3MonKe_h;; Stack: 8
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } cstack8.l = (cclasses[6]);
        // New stack: 9
        // SWAP; Stack: 9
        std::swap(cstack8, cstack7);
        // New stack: 9
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.7353569731942222.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 9
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } cstack9.l = lookup;
        // New stack: 10
        // LDC Ldev/sakura/client/L3MonKe_f2;; Stack: 10
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[6]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } cstack10.l = (cclasses[7]);
        // New stack: 11
        // LDC a; Stack: 11
        cstack11.l = (cstrings[7]);
        // New stack: 12
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;; Stack: 12
        cstack12.l = (cstrings[8]);
        // New stack: 13
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.7353569731942222.a()Ljava/lang/ClassLoader;; Stack: 13
        cstack13.l = classloader;
        // New stack: 14
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 14
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (!cmethods[2]) { cmethods[2] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 272LL)), ((char *)(string_pool + 299LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }  } cstack12.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[2]), cstack12.l, cstack13.l); refs.insert(cstack12.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 13
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 13
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[10]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (!cmethods[3]) { cmethods[3] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 372LL)), ((char *)(string_pool + 383LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }  } if (cstack9.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 481LL)), -1); else { cstack9.l = env->CallObjectMethod(cstack9.l, (cmethods[3]), cstack10.l, cstack11.l, cstack12.l); refs.insert(cstack9.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 10
        // SWAP; Stack: 10
        std::swap(cstack9, cstack8);
        // New stack: 10
        // LDC \u00d1; Stack: 10
        cstack10.l = (cstrings[11]);
        // New stack: 11
        // SWAP; Stack: 11
        std::swap(cstack10, cstack9);
        // New stack: 11
        // LDC (Ljava/lang/Object;JJ)Ljava/lang/String;; Stack: 11
        cstack11.l = (cstrings[18]);
        // New stack: 12
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.7353569731942222.a()Ljava/lang/ClassLoader;; Stack: 12
        cstack12.l = classloader;
        // New stack: 13
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 13
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (!cmethods[2]) { cmethods[2] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 272LL)), ((char *)(string_pool + 299LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }  } cstack11.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[2]), cstack11.l, cstack12.l); refs.insert(cstack11.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 12
        // SWAP; Stack: 12
        std::swap(cstack11, cstack10);
        // New stack: 12
        // LDC 0; Stack: 12
        cstack12.i = 0;
        // New stack: 13
        // ANEWARRAY java/lang/Object; Stack: 13
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (cstack12.i < 0) utils::throw_re(env, ((char *)(string_pool + 210LL)), ((char *)(string_pool + 247LL)), -1); else { cstack12.l = env->NewObjectArray(cstack12.i, (cclasses[5]), nullptr); refs.insert(cstack12.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 13
        // CHECKCAST java/lang/Object; Stack: 13
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (cstack12.l != nullptr && !env->IsInstanceOf(cstack12.l, (cclasses[5]))) { utils::throw_re(env, ((char *)(string_pool + 109LL)), (std::string(((char *)(string_pool + 138LL))) + std::string(((char *)(string_pool + 506LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } 
        // New stack: 13
        // SWAP; Stack: 13
        std::swap(cstack12, cstack11);
        // New stack: 13
        // INVOKESTATIC native/magic/1/linkcallsite/obfuscator0.7353569731942222.a(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/invoke/MemberName;; Stack: 13
        cstack7.l = utils::link_call_site(env, cstack7.l, cstack8.l, cstack9.l, cstack10.l, cstack11.l, cstack12.l); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 8
        // POP; Stack: 8
        ;
        // New stack: 7
        // ICONST_0; Stack: 7
        cstack7.i = 0;
        // New stack: 8
        // AALOAD; Stack: 8
        if (cstack6.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 98LL)), -1); else { cstack6.l = env->GetObjectArrayElement((jobjectArray) cstack6.l, cstack7.i); refs.insert(cstack6.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 7
        // DUP; Stack: 7
        cstack7 = cstack6;
        // New stack: 8
        // INSTANCEOF java/lang/invoke/CallSite; Stack: 8
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } cstack7.i = cstack7.l == nullptr ? false : env->IsInstanceOf(cstack7.l, (cclasses[10]));
        // New stack: 8
        // IFEQ L20; Stack: 8
        if (cstack7.i == 0) goto L20;
        // New stack: 7
        // INVOKEINTERFACE java/lang/invoke/CallSite.getTarget()Ljava/lang/invoke/MethodHandle;; Stack: 7
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[10]), ((char *)(string_pool + 523LL)), ((char *)(string_pool + 533LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }  } if (cstack6.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 567LL)), -1); else { cstack6.l = env->CallObjectMethod(cstack6.l, (cmethods[4])); refs.insert(cstack6.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 7
        // LABEL L20; Stack: 7
        L20: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 7
        // FRAME FULL L: [[Ljava/lang/Object;, java/lang/String, dev/sakura/L3MonKe_B, 4, java/lang/String, java/lang/String, 4, 4, 4] S: [[Ljava/lang/String;, [Ljava/lang/Object;, 4, 4, java/lang/Object]; Stack: 7
        refs.erase(cstack0.l); refs.erase(cstack1.l); refs.erase(cstack6.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal5.l); refs.erase(clocal6.l); 
        utils::clear_refs(env, refs);
        // New stack: 7
        // CHECKCAST java/lang/invoke/MethodHandle; Stack: 7
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (cstack6.l != nullptr && !env->IsInstanceOf(cstack6.l, (cclasses[11]))) { utils::throw_re(env, ((char *)(string_pool + 109LL)), (std::string(((char *)(string_pool + 138LL))) + std::string(((char *)(string_pool + 594LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } 
        // New stack: 7
        // GOTO L21; Stack: 7
        goto L21;
        // New stack: 7
        // LABEL L14; Stack: 7
        L14: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 7
        // FRAME SAME1 L: null S: [java/lang/Throwable]; Stack: 7
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal5.l); refs.erase(clocal6.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // INSTANCEOF java/lang/BootstrapMethodError; Stack: 2
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } cstack1.i = cstack1.l == nullptr ? false : env->IsInstanceOf(cstack1.l, (cclasses[12]));
        // New stack: 2
        // IFNE L22; Stack: 2
        if (cstack1.i != 0) goto L22;
        // New stack: 1
        // NEW java/lang/BootstrapMethodError; Stack: 1
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (jobject obj = env->AllocObject((cclasses[12]))) { cstack1.l = obj; refs.insert(obj); } 
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
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[5]) { cmethods[5] = env->GetMethodID((cclasses[12]), ((char *)(string_pool + 624LL)), ((char *)(string_pool + 631LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 656LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[12]), (cmethods[5]), cstack2.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // LABEL L22; Stack: 1
        L22: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/Object]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal5.l); refs.erase(clocal6.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 679LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // LABEL L21; Stack: 0
        L21: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // FRAME FULL L: [[Ljava/lang/Object;, java/lang/String, dev/sakura/L3MonKe_B, 4, java/lang/String, java/lang/String, 4, 4, 4] S: [[Ljava/lang/String;, [Ljava/lang/Object;, 4, 4, java/lang/invoke/MethodHandle]; Stack: 0
        refs.erase(cstack0.l); refs.erase(cstack1.l); refs.erase(cstack6.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal5.l); refs.erase(clocal6.l); 
        utils::clear_refs(env, refs);
        // New stack: 7
        // INVOKESTATIC native/magic/1/invoke/obfuscator0.7353569731942222.a(Ljava/lang/Object;JJLjava/lang/invoke/MethodHandle;)Ljava/lang/String;; Stack: 7
        if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { cclasses_mtx[13].lock(); if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[16]))) { cclasses[13] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[13].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[8]) { cmethods[8] = env->GetStaticMethodID((cclasses[13]), ((char *)(string_pool + 869LL)), ((char *)(string_pool + 884LL))); if (env->ExceptionCheck()) { return (void) 0; }  } cstack1.l = env->CallStaticObjectMethod((cclasses[13]), (cmethods[8]), cstack1.l, cstack2.j, cstack4.j, cstack6.l); refs.insert(cstack1.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 2
        // CHECKCAST java/lang/String; Stack: 2
        if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { cclasses_mtx[1].lock(); if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[1]))) { cclasses[1] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[1].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (cstack1.l != nullptr && !env->IsInstanceOf(cstack1.l, (cclasses[1]))) { utils::throw_re(env, ((char *)(string_pool + 109LL)), (std::string(((char *)(string_pool + 138LL))) + std::string(((char *)(string_pool + 852LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (void) 0; } } 
        // New stack: 2
        // ASTORE 14; Stack: 2
        clocal14.l = cstack1.l; refs.insert(cstack1.l);
        // New stack: 1
        // ASTORE 13; Stack: 1
        clocal13.l = cstack0.l; refs.insert(cstack0.l);
        // New stack: 0
        // LLOAD 7; Stack: 0
        cstack0.j = clocal7.j;
        // New stack: 2
        // ICONST_1; Stack: 2
        cstack2.i = 1;
        // New stack: 3
        // ANEWARRAY java/lang/Object; Stack: 3
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (cstack2.i < 0) utils::throw_re(env, ((char *)(string_pool + 210LL)), ((char *)(string_pool + 247LL)), -1); else { cstack2.l = env->NewObjectArray(cstack2.i, (cclasses[5]), nullptr); refs.insert(cstack2.l); } 
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
        if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { cclasses_mtx[2].lock(); if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[2]))) { cclasses[2] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[2].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[1]) { cmethods[1] = env->GetStaticMethodID((cclasses[2]), ((char *)(string_pool + 1044LL)), ((char *)(string_pool + 1052LL))); if (env->ExceptionCheck()) { return (void) 0; }  } cstack2.l = env->CallStaticObjectMethod((cclasses[2]), (cmethods[1]), cstack2.j); refs.insert(cstack2.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 3
        // ICONST_0; Stack: 3
        cstack3.i = 0;
        // New stack: 4
        // SWAP; Stack: 4
        std::swap(cstack3, cstack2);
        // New stack: 4
        // AASTORE; Stack: 4
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 1072LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack1.l, cstack2.i, cstack3.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // LDC 632613847176881196; Stack: 1
        cstack1.j = 632613847176881196LL;
        // New stack: 3
        // LLOAD 3; Stack: 3
        cstack3.j = clocal3.j;
        // New stack: 5
        // LABEL L11; Stack: 5
        L11: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 5
        // ICONST_1; Stack: 5
        cstack5.i = 1;
        // New stack: 6
        // ANEWARRAY java/lang/Object; Stack: 6
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } if (cstack5.i < 0) utils::throw_re(env, ((char *)(string_pool + 210LL)), ((char *)(string_pool + 247LL)), -1); else { cstack5.l = env->NewObjectArray(cstack5.i, (cclasses[5]), nullptr); refs.insert(cstack5.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 6
        // DUP; Stack: 6
        cstack6 = cstack5;
        // New stack: 7
        // LDC Ldev/sakura/L3MonKe_h;; Stack: 7
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } cstack7.l = (cclasses[6]);
        // New stack: 8
        // SWAP; Stack: 8
        std::swap(cstack7, cstack6);
        // New stack: 8
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.7353569731942222.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 8
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } cstack8.l = lookup;
        // New stack: 9
        // LDC Ldev/sakura/client/L3MonKe_f2;; Stack: 9
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[6]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } cstack9.l = (cclasses[7]);
        // New stack: 10
        // LDC a; Stack: 10
        cstack10.l = (cstrings[7]);
        // New stack: 11
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;; Stack: 11
        cstack11.l = (cstrings[8]);
        // New stack: 12
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.7353569731942222.a()Ljava/lang/ClassLoader;; Stack: 12
        cstack12.l = classloader;
        // New stack: 13
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 13
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } if (!cmethods[2]) { cmethods[2] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 272LL)), ((char *)(string_pool + 299LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }  } cstack11.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[2]), cstack11.l, cstack12.l); refs.insert(cstack11.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 12
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 12
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[10]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } if (!cmethods[3]) { cmethods[3] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 372LL)), ((char *)(string_pool + 383LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }  } if (cstack8.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 481LL)), -1); else { cstack8.l = env->CallObjectMethod(cstack8.l, (cmethods[3]), cstack9.l, cstack10.l, cstack11.l); refs.insert(cstack8.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 9
        // SWAP; Stack: 9
        std::swap(cstack8, cstack7);
        // New stack: 9
        // LDC \u00d1; Stack: 9
        cstack9.l = (cstrings[11]);
        // New stack: 10
        // SWAP; Stack: 10
        std::swap(cstack9, cstack8);
        // New stack: 10
        // LDC (Ljava/lang/Object;JJ)Ljava/util/Set;; Stack: 10
        cstack10.l = (cstrings[19]);
        // New stack: 11
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.7353569731942222.a()Ljava/lang/ClassLoader;; Stack: 11
        cstack11.l = classloader;
        // New stack: 12
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 12
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } if (!cmethods[2]) { cmethods[2] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 272LL)), ((char *)(string_pool + 299LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }  } cstack10.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[2]), cstack10.l, cstack11.l); refs.insert(cstack10.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 11
        // SWAP; Stack: 11
        std::swap(cstack10, cstack9);
        // New stack: 11
        // LDC 0; Stack: 11
        cstack11.i = 0;
        // New stack: 12
        // ANEWARRAY java/lang/Object; Stack: 12
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } if (cstack11.i < 0) utils::throw_re(env, ((char *)(string_pool + 210LL)), ((char *)(string_pool + 247LL)), -1); else { cstack11.l = env->NewObjectArray(cstack11.i, (cclasses[5]), nullptr); refs.insert(cstack11.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 12
        // CHECKCAST java/lang/Object; Stack: 12
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } if (cstack11.l != nullptr && !env->IsInstanceOf(cstack11.l, (cclasses[5]))) { utils::throw_re(env, ((char *)(string_pool + 109LL)), (std::string(((char *)(string_pool + 138LL))) + std::string(((char *)(string_pool + 506LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } 
        // New stack: 12
        // SWAP; Stack: 12
        std::swap(cstack11, cstack10);
        // New stack: 12
        // INVOKESTATIC native/magic/1/linkcallsite/obfuscator0.7353569731942222.a(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/invoke/MemberName;; Stack: 12
        cstack6.l = utils::link_call_site(env, cstack6.l, cstack7.l, cstack8.l, cstack9.l, cstack10.l, cstack11.l); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 7
        // POP; Stack: 7
        ;
        // New stack: 6
        // ICONST_0; Stack: 6
        cstack6.i = 0;
        // New stack: 7
        // AALOAD; Stack: 7
        if (cstack5.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 98LL)), -1); else { cstack5.l = env->GetObjectArrayElement((jobjectArray) cstack5.l, cstack6.i); refs.insert(cstack5.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 6
        // DUP; Stack: 6
        cstack6 = cstack5;
        // New stack: 7
        // INSTANCEOF java/lang/invoke/CallSite; Stack: 7
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } cstack6.i = cstack6.l == nullptr ? false : env->IsInstanceOf(cstack6.l, (cclasses[10]));
        // New stack: 7
        // IFEQ L23; Stack: 7
        if (cstack6.i == 0) goto L23;
        // New stack: 6
        // INVOKEINTERFACE java/lang/invoke/CallSite.getTarget()Ljava/lang/invoke/MethodHandle;; Stack: 6
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[10]), ((char *)(string_pool + 523LL)), ((char *)(string_pool + 533LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }  } if (cstack5.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 567LL)), -1); else { cstack5.l = env->CallObjectMethod(cstack5.l, (cmethods[4])); refs.insert(cstack5.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 6
        // LABEL L23; Stack: 6
        L23: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 6
        // FRAME FULL L: [[Ljava/lang/Object;, java/lang/String, dev/sakura/L3MonKe_B, 4, java/lang/String, java/lang/String, 4, 4, 4, [Ljava/lang/String;, java/lang/String] S: [[Ljava/lang/Object;, 4, 4, java/lang/Object]; Stack: 6
        refs.erase(cstack0.l); refs.erase(cstack5.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal5.l); refs.erase(clocal6.l); refs.erase(clocal13.l); refs.erase(clocal14.l); 
        utils::clear_refs(env, refs);
        // New stack: 6
        // CHECKCAST java/lang/invoke/MethodHandle; Stack: 6
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } if (cstack5.l != nullptr && !env->IsInstanceOf(cstack5.l, (cclasses[11]))) { utils::throw_re(env, ((char *)(string_pool + 109LL)), (std::string(((char *)(string_pool + 138LL))) + std::string(((char *)(string_pool + 594LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } 
        // New stack: 6
        // GOTO L24; Stack: 6
        goto L24;
        // New stack: 6
        // LABEL L12; Stack: 6
        L12: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 6
        // FRAME SAME1 L: null S: [java/lang/Throwable]; Stack: 6
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal5.l); refs.erase(clocal6.l); refs.erase(clocal13.l); refs.erase(clocal14.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // INSTANCEOF java/lang/BootstrapMethodError; Stack: 2
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } cstack1.i = cstack1.l == nullptr ? false : env->IsInstanceOf(cstack1.l, (cclasses[12]));
        // New stack: 2
        // IFNE L25; Stack: 2
        if (cstack1.i != 0) goto L25;
        // New stack: 1
        // NEW java/lang/BootstrapMethodError; Stack: 1
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (jobject obj = env->AllocObject((cclasses[12]))) { cstack1.l = obj; refs.insert(obj); } 
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
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[5]) { cmethods[5] = env->GetMethodID((cclasses[12]), ((char *)(string_pool + 624LL)), ((char *)(string_pool + 631LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 656LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[12]), (cmethods[5]), cstack2.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // LABEL L25; Stack: 1
        L25: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/Object]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal5.l); refs.erase(clocal6.l); refs.erase(clocal13.l); refs.erase(clocal14.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 679LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // LABEL L24; Stack: 0
        L24: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // FRAME FULL L: [[Ljava/lang/Object;, java/lang/String, dev/sakura/L3MonKe_B, 4, java/lang/String, java/lang/String, 4, 4, 4, [Ljava/lang/String;, java/lang/String] S: [[Ljava/lang/Object;, 4, 4, java/lang/invoke/MethodHandle]; Stack: 0
        refs.erase(cstack0.l); refs.erase(cstack5.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal5.l); refs.erase(clocal6.l); refs.erase(clocal13.l); refs.erase(clocal14.l); 
        utils::clear_refs(env, refs);
        // New stack: 6
        // INVOKESTATIC native/magic/1/invoke/obfuscator0.7353569731942222.a(Ljava/lang/Object;JJLjava/lang/invoke/MethodHandle;)Ljava/util/Set;; Stack: 6
        if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { cclasses_mtx[13].lock(); if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[16]))) { cclasses[13] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[13].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[8]) { cmethods[8] = env->GetStaticMethodID((cclasses[13]), ((char *)(string_pool + 869LL)), ((char *)(string_pool + 884LL))); if (env->ExceptionCheck()) { return (void) 0; }  } cstack0.l = env->CallStaticObjectMethod((cclasses[13]), (cmethods[8]), cstack0.l, cstack1.j, cstack3.j, cstack5.l); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // CHECKCAST java/util/Set; Stack: 1
        if (!cclasses[15] || env->IsSameObject(cclasses[15], NULL)) { cclasses_mtx[15].lock(); if (!cclasses[15] || env->IsSameObject(cclasses[15], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[20]))) { cclasses[15] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[15].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (cstack0.l != nullptr && !env->IsInstanceOf(cstack0.l, (cclasses[15]))) { utils::throw_re(env, ((char *)(string_pool + 109LL)), (std::string(((char *)(string_pool + 138LL))) + std::string(((char *)(string_pool + 18306LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (void) 0; } } 
        // New stack: 1
        // ASTORE 15; Stack: 1
        clocal15.l = cstack0.l; refs.insert(cstack0.l);
        // New stack: 0
        // LLOAD 9; Stack: 0
        cstack0.j = clocal9.j;
        // New stack: 2
        // ICONST_1; Stack: 2
        cstack2.i = 1;
        // New stack: 3
        // ANEWARRAY java/lang/Object; Stack: 3
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (cstack2.i < 0) utils::throw_re(env, ((char *)(string_pool + 210LL)), ((char *)(string_pool + 247LL)), -1); else { cstack2.l = env->NewObjectArray(cstack2.i, (cclasses[5]), nullptr); refs.insert(cstack2.l); } 
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
        if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { cclasses_mtx[2].lock(); if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[2]))) { cclasses[2] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[2].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[1]) { cmethods[1] = env->GetStaticMethodID((cclasses[2]), ((char *)(string_pool + 1044LL)), ((char *)(string_pool + 1052LL))); if (env->ExceptionCheck()) { return (void) 0; }  } cstack2.l = env->CallStaticObjectMethod((cclasses[2]), (cmethods[1]), cstack2.j); refs.insert(cstack2.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 3
        // ICONST_0; Stack: 3
        cstack3.i = 0;
        // New stack: 4
        // SWAP; Stack: 4
        std::swap(cstack3, cstack2);
        // New stack: 4
        // AASTORE; Stack: 4
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 1072LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack1.l, cstack2.i, cstack3.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // LDC 633131674284529270; Stack: 1
        cstack1.j = 633131674284529270LL;
        // New stack: 3
        // LLOAD 3; Stack: 3
        cstack3.j = clocal3.j;
        // New stack: 5
        // LABEL L9; Stack: 5
        L9: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 5
        // ICONST_1; Stack: 5
        cstack5.i = 1;
        // New stack: 6
        // ANEWARRAY java/lang/Object; Stack: 6
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } if (cstack5.i < 0) utils::throw_re(env, ((char *)(string_pool + 210LL)), ((char *)(string_pool + 247LL)), -1); else { cstack5.l = env->NewObjectArray(cstack5.i, (cclasses[5]), nullptr); refs.insert(cstack5.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 6
        // DUP; Stack: 6
        cstack6 = cstack5;
        // New stack: 7
        // LDC Ldev/sakura/L3MonKe_h;; Stack: 7
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } cstack7.l = (cclasses[6]);
        // New stack: 8
        // SWAP; Stack: 8
        std::swap(cstack7, cstack6);
        // New stack: 8
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.7353569731942222.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 8
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } cstack8.l = lookup;
        // New stack: 9
        // LDC Ldev/sakura/client/L3MonKe_f2;; Stack: 9
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[6]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } cstack9.l = (cclasses[7]);
        // New stack: 10
        // LDC a; Stack: 10
        cstack10.l = (cstrings[7]);
        // New stack: 11
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;; Stack: 11
        cstack11.l = (cstrings[8]);
        // New stack: 12
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.7353569731942222.a()Ljava/lang/ClassLoader;; Stack: 12
        cstack12.l = classloader;
        // New stack: 13
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 13
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } if (!cmethods[2]) { cmethods[2] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 272LL)), ((char *)(string_pool + 299LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }  } cstack11.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[2]), cstack11.l, cstack12.l); refs.insert(cstack11.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 12
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 12
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[10]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } if (!cmethods[3]) { cmethods[3] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 372LL)), ((char *)(string_pool + 383LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }  } if (cstack8.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 481LL)), -1); else { cstack8.l = env->CallObjectMethod(cstack8.l, (cmethods[3]), cstack9.l, cstack10.l, cstack11.l); refs.insert(cstack8.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 9
        // SWAP; Stack: 9
        std::swap(cstack8, cstack7);
        // New stack: 9
        // LDC \u00d1; Stack: 9
        cstack9.l = (cstrings[11]);
        // New stack: 10
        // SWAP; Stack: 10
        std::swap(cstack9, cstack8);
        // New stack: 10
        // LDC (Ljava/lang/Object;JJ)Ljava/lang/String;; Stack: 10
        cstack10.l = (cstrings[18]);
        // New stack: 11
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.7353569731942222.a()Ljava/lang/ClassLoader;; Stack: 11
        cstack11.l = classloader;
        // New stack: 12
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 12
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } if (!cmethods[2]) { cmethods[2] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 272LL)), ((char *)(string_pool + 299LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }  } cstack10.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[2]), cstack10.l, cstack11.l); refs.insert(cstack10.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 11
        // SWAP; Stack: 11
        std::swap(cstack10, cstack9);
        // New stack: 11
        // LDC 0; Stack: 11
        cstack11.i = 0;
        // New stack: 12
        // ANEWARRAY java/lang/Object; Stack: 12
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } if (cstack11.i < 0) utils::throw_re(env, ((char *)(string_pool + 210LL)), ((char *)(string_pool + 247LL)), -1); else { cstack11.l = env->NewObjectArray(cstack11.i, (cclasses[5]), nullptr); refs.insert(cstack11.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 12
        // CHECKCAST java/lang/Object; Stack: 12
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } if (cstack11.l != nullptr && !env->IsInstanceOf(cstack11.l, (cclasses[5]))) { utils::throw_re(env, ((char *)(string_pool + 109LL)), (std::string(((char *)(string_pool + 138LL))) + std::string(((char *)(string_pool + 506LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } 
        // New stack: 12
        // SWAP; Stack: 12
        std::swap(cstack11, cstack10);
        // New stack: 12
        // INVOKESTATIC native/magic/1/linkcallsite/obfuscator0.7353569731942222.a(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/invoke/MemberName;; Stack: 12
        cstack6.l = utils::link_call_site(env, cstack6.l, cstack7.l, cstack8.l, cstack9.l, cstack10.l, cstack11.l); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 7
        // POP; Stack: 7
        ;
        // New stack: 6
        // ICONST_0; Stack: 6
        cstack6.i = 0;
        // New stack: 7
        // AALOAD; Stack: 7
        if (cstack5.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 98LL)), -1); else { cstack5.l = env->GetObjectArrayElement((jobjectArray) cstack5.l, cstack6.i); refs.insert(cstack5.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 6
        // DUP; Stack: 6
        cstack6 = cstack5;
        // New stack: 7
        // INSTANCEOF java/lang/invoke/CallSite; Stack: 7
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } cstack6.i = cstack6.l == nullptr ? false : env->IsInstanceOf(cstack6.l, (cclasses[10]));
        // New stack: 7
        // IFEQ L26; Stack: 7
        if (cstack6.i == 0) goto L26;
        // New stack: 6
        // INVOKEINTERFACE java/lang/invoke/CallSite.getTarget()Ljava/lang/invoke/MethodHandle;; Stack: 6
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[10]), ((char *)(string_pool + 523LL)), ((char *)(string_pool + 533LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }  } if (cstack5.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 567LL)), -1); else { cstack5.l = env->CallObjectMethod(cstack5.l, (cmethods[4])); refs.insert(cstack5.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 6
        // LABEL L26; Stack: 6
        L26: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 6
        // FRAME FULL L: [[Ljava/lang/Object;, java/lang/String, dev/sakura/L3MonKe_B, 4, java/lang/String, java/lang/String, 4, 4, 4, [Ljava/lang/String;, java/lang/String, java/util/Set] S: [[Ljava/lang/Object;, 4, 4, java/lang/Object]; Stack: 6
        refs.erase(cstack0.l); refs.erase(cstack5.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal5.l); refs.erase(clocal6.l); refs.erase(clocal13.l); refs.erase(clocal14.l); refs.erase(clocal15.l); 
        utils::clear_refs(env, refs);
        // New stack: 6
        // CHECKCAST java/lang/invoke/MethodHandle; Stack: 6
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } if (cstack5.l != nullptr && !env->IsInstanceOf(cstack5.l, (cclasses[11]))) { utils::throw_re(env, ((char *)(string_pool + 109LL)), (std::string(((char *)(string_pool + 138LL))) + std::string(((char *)(string_pool + 594LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } 
        // New stack: 6
        // GOTO L27; Stack: 6
        goto L27;
        // New stack: 6
        // LABEL L10; Stack: 6
        L10: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 6
        // FRAME SAME1 L: null S: [java/lang/Throwable]; Stack: 6
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal5.l); refs.erase(clocal6.l); refs.erase(clocal13.l); refs.erase(clocal14.l); refs.erase(clocal15.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // INSTANCEOF java/lang/BootstrapMethodError; Stack: 2
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } cstack1.i = cstack1.l == nullptr ? false : env->IsInstanceOf(cstack1.l, (cclasses[12]));
        // New stack: 2
        // IFNE L28; Stack: 2
        if (cstack1.i != 0) goto L28;
        // New stack: 1
        // NEW java/lang/BootstrapMethodError; Stack: 1
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (jobject obj = env->AllocObject((cclasses[12]))) { cstack1.l = obj; refs.insert(obj); } 
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
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[5]) { cmethods[5] = env->GetMethodID((cclasses[12]), ((char *)(string_pool + 624LL)), ((char *)(string_pool + 631LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 656LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[12]), (cmethods[5]), cstack2.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // LABEL L28; Stack: 1
        L28: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/Object]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal5.l); refs.erase(clocal6.l); refs.erase(clocal13.l); refs.erase(clocal14.l); refs.erase(clocal15.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 679LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // LABEL L27; Stack: 0
        L27: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // FRAME FULL L: [[Ljava/lang/Object;, java/lang/String, dev/sakura/L3MonKe_B, 4, java/lang/String, java/lang/String, 4, 4, 4, [Ljava/lang/String;, java/lang/String, java/util/Set] S: [[Ljava/lang/Object;, 4, 4, java/lang/invoke/MethodHandle]; Stack: 0
        refs.erase(cstack0.l); refs.erase(cstack5.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal5.l); refs.erase(clocal6.l); refs.erase(clocal13.l); refs.erase(clocal14.l); refs.erase(clocal15.l); 
        utils::clear_refs(env, refs);
        // New stack: 6
        // INVOKESTATIC native/magic/1/invoke/obfuscator0.7353569731942222.a(Ljava/lang/Object;JJLjava/lang/invoke/MethodHandle;)Ljava/lang/String;; Stack: 6
        if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { cclasses_mtx[13].lock(); if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[16]))) { cclasses[13] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[13].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[8]) { cmethods[8] = env->GetStaticMethodID((cclasses[13]), ((char *)(string_pool + 869LL)), ((char *)(string_pool + 884LL))); if (env->ExceptionCheck()) { return (void) 0; }  } cstack0.l = env->CallStaticObjectMethod((cclasses[13]), (cmethods[8]), cstack0.l, cstack1.j, cstack3.j, cstack5.l); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // CHECKCAST java/lang/String; Stack: 1
        if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { cclasses_mtx[1].lock(); if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[1]))) { cclasses[1] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[1].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (cstack0.l != nullptr && !env->IsInstanceOf(cstack0.l, (cclasses[1]))) { utils::throw_re(env, ((char *)(string_pool + 109LL)), (std::string(((char *)(string_pool + 138LL))) + std::string(((char *)(string_pool + 852LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (void) 0; } } 
        // New stack: 1
        // ASTORE 16; Stack: 1
        clocal16.l = cstack0.l; refs.insert(cstack0.l);
        // New stack: 0
        // ALOAD 16; Stack: 0
        cstack0.l = clocal16.l; refs.insert(cstack0.l);
        // New stack: 1
        // ALOAD 13; Stack: 1
        cstack1.l = clocal13.l; refs.insert(cstack1.l);
        // New stack: 2
        // IFNULL L29; Stack: 2
        if (env->IsSameObject(cstack1.l, nullptr)) goto L29;
        // New stack: 1
        // IFNONNULL L30; Stack: 1
        if (!env->IsSameObject(cstack0.l, nullptr)) goto L30;
        // New stack: 0
        // LDC ; Stack: 0
        cstack0.l = (cstrings[21]);
        // New stack: 1
        // LABEL L29; Stack: 1
        L29: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // FRAME FULL L: [[Ljava/lang/Object;, java/lang/String, dev/sakura/L3MonKe_B, 4, java/lang/String, java/lang/String, 4, 4, 4, [Ljava/lang/String;, java/lang/String, java/util/Set, java/lang/String] S: [java/lang/String]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal5.l); refs.erase(clocal6.l); refs.erase(clocal13.l); refs.erase(clocal14.l); refs.erase(clocal15.l); refs.erase(clocal16.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // GOTO L31; Stack: 1
        goto L31;
        // New stack: 1
        // LABEL L30; Stack: 1
        L30: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // FRAME SAME L: null S: null; Stack: 1
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal5.l); refs.erase(clocal6.l); refs.erase(clocal13.l); refs.erase(clocal14.l); refs.erase(clocal15.l); refs.erase(clocal16.l); 
        utils::clear_refs(env, refs);
        // New stack: 0
        // ALOAD 16; Stack: 0
        cstack0.l = clocal16.l; refs.insert(cstack0.l);
        // New stack: 1
        // LABEL L31; Stack: 1
        L31: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/String]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal5.l); refs.erase(clocal6.l); refs.erase(clocal13.l); refs.erase(clocal14.l); refs.erase(clocal15.l); refs.erase(clocal16.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ASTORE 17; Stack: 1
        clocal17.l = cstack0.l; refs.insert(cstack0.l);
        // New stack: 0
        // NEW java/util/concurrent/atomic/AtomicBoolean; Stack: 0
        if (!cclasses[16] || env->IsSameObject(cclasses[16], NULL)) { cclasses_mtx[16].lock(); if (!cclasses[16] || env->IsSameObject(cclasses[16], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[22]))) { cclasses[16] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[16].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (jobject obj = env->AllocObject((cclasses[16]))) { cstack0.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // ICONST_0; Stack: 2
        cstack2.i = 0;
        // New stack: 3
        // INVOKESPECIAL java/util/concurrent/atomic/AtomicBoolean.<init>(Z)V; Stack: 3
        if (!cclasses[16] || env->IsSameObject(cclasses[16], NULL)) { cclasses_mtx[16].lock(); if (!cclasses[16] || env->IsSameObject(cclasses[16], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[22]))) { cclasses[16] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[16].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[9]) { cmethods[9] = env->GetMethodID((cclasses[16]), ((char *)(string_pool + 624LL)), ((char *)(string_pool + 17947LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 656LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[16]), (cmethods[9]), cstack2.i); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // ASTORE 18; Stack: 1
        clocal18.l = cstack0.l; refs.insert(cstack0.l);
        // New stack: 0
        // ALOAD 1; Stack: 0
        cstack0.l = clocal1.l; refs.insert(cstack0.l);
        // New stack: 1
        // IFNULL L32; Stack: 1
        if (env->IsSameObject(cstack0.l, nullptr)) goto L32;
        // New stack: 0
        // ICONST_1; Stack: 0
        cstack0.i = 1;
        // New stack: 1
        // GOTO L33; Stack: 1
        goto L33;
        // New stack: 1
        // LABEL L32; Stack: 1
        L32: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // FRAME APPEND L: [java/lang/String, java/util/concurrent/atomic/AtomicBoolean] S: null; Stack: 1
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal5.l); refs.erase(clocal6.l); refs.erase(clocal13.l); refs.erase(clocal14.l); refs.erase(clocal15.l); refs.erase(clocal16.l); refs.erase(clocal17.l); refs.erase(clocal18.l); 
        utils::clear_refs(env, refs);
        // New stack: 0
        // ICONST_0; Stack: 0
        cstack0.i = 0;
        // New stack: 1
        // LABEL L33; Stack: 1
        L33: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [1]; Stack: 1
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal5.l); refs.erase(clocal6.l); refs.erase(clocal13.l); refs.erase(clocal14.l); refs.erase(clocal15.l); refs.erase(clocal16.l); refs.erase(clocal17.l); refs.erase(clocal18.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ISTORE 19; Stack: 1
        clocal19.i = cstack0.i;
        // New stack: 0
        // NEW dev/sakura/L3MonKe_M; Stack: 0
        if (!cclasses[17] || env->IsSameObject(cclasses[17], NULL)) { cclasses_mtx[17].lock(); if (!cclasses[17] || env->IsSameObject(cclasses[17], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[23]))) { cclasses[17] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[17].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (jobject obj = env->AllocObject((cclasses[17]))) { cstack0.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // ALOAD 18; Stack: 2
        cstack2.l = clocal18.l; refs.insert(cstack2.l);
        // New stack: 3
        // ALOAD 2; Stack: 3
        cstack3.l = clocal2.l; refs.insert(cstack3.l);
        // New stack: 4
        // ALOAD 5; Stack: 4
        cstack4.l = clocal5.l; refs.insert(cstack4.l);
        // New stack: 5
        // INVOKESPECIAL dev/sakura/L3MonKe_M.<init>(Ljava/util/concurrent/atomic/AtomicBoolean;Ldev/sakura/L3MonKe_B;Ljava/lang/String;)V; Stack: 5
        if (!cclasses[17] || env->IsSameObject(cclasses[17], NULL)) { cclasses_mtx[17].lock(); if (!cclasses[17] || env->IsSameObject(cclasses[17], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[23]))) { cclasses[17] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[17].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[10]) { cmethods[10] = env->GetMethodID((cclasses[17]), ((char *)(string_pool + 624LL)), ((char *)(string_pool + 18320LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 656LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[17]), (cmethods[10]), cstack2.l, cstack3.l, cstack4.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // ASTORE 20; Stack: 1
        clocal20.l = cstack0.l; refs.insert(cstack0.l);
        // New stack: 0
        // LABEL L34; Stack: 0
        L34: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // NEW java/lang/Thread; Stack: 0
        if (!cclasses[18] || env->IsSameObject(cclasses[18], NULL)) { cclasses_mtx[18].lock(); if (!cclasses[18] || env->IsSameObject(cclasses[18], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[24]))) { cclasses[18] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[18].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (jobject obj = env->AllocObject((cclasses[18]))) { cstack0.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // ALOAD 20; Stack: 2
        cstack2.l = clocal20.l; refs.insert(cstack2.l);
        // New stack: 3
        // ALOAD 18; Stack: 3
        cstack3.l = clocal18.l; refs.insert(cstack3.l);
        // New stack: 4
        // ALOAD 2; Stack: 4
        cstack4.l = clocal2.l; refs.insert(cstack4.l);
        // New stack: 5
        // ILOAD 19; Stack: 5
        cstack5.i = clocal19.i;
        // New stack: 6
        // ALOAD 5; Stack: 6
        cstack6.l = clocal5.l; refs.insert(cstack6.l);
        // New stack: 7
        // ALOAD 6; Stack: 7
        cstack7.l = clocal6.l; refs.insert(cstack7.l);
        // New stack: 8
        // ALOAD 14; Stack: 8
        cstack8.l = clocal14.l; refs.insert(cstack8.l);
        // New stack: 9
        // ALOAD 15; Stack: 9
        cstack9.l = clocal15.l; refs.insert(cstack9.l);
        // New stack: 10
        // ALOAD 17; Stack: 10
        cstack10.l = clocal17.l; refs.insert(cstack10.l);
        // New stack: 11
        // ALOAD 1; Stack: 11
        cstack11.l = clocal1.l; refs.insert(cstack11.l);
        // New stack: 12
        // LABEL L7; Stack: 12
        L7: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }
        // New stack: 12
        // ICONST_1; Stack: 12
        cstack12.i = 1;
        // New stack: 13
        // ANEWARRAY java/lang/Object; Stack: 13
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } if (cstack12.i < 0) utils::throw_re(env, ((char *)(string_pool + 210LL)), ((char *)(string_pool + 247LL)), -1); else { cstack12.l = env->NewObjectArray(cstack12.i, (cclasses[5]), nullptr); refs.insert(cstack12.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }
        // New stack: 13
        // DUP; Stack: 13
        cstack13 = cstack12;
        // New stack: 14
        // LDC Ldev/sakura/L3MonKe_h;; Stack: 14
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } cstack14.l = (cclasses[6]);
        // New stack: 15
        // SWAP; Stack: 15
        std::swap(cstack14, cstack13);
        // New stack: 15
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.7353569731942222.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 15
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } cstack15.l = lookup;
        // New stack: 16
        // LDC Ljava/lang/invoke/LambdaMetafactory;; Stack: 16
        if (!cclasses[19] || env->IsSameObject(cclasses[19], NULL)) { cclasses_mtx[19].lock(); if (!cclasses[19] || env->IsSameObject(cclasses[19], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[25]))) { cclasses[19] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[19].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } cstack16.l = (cclasses[19]);
        // New stack: 17
        // LDC metafactory; Stack: 17
        cstack17.l = (cstrings[26]);
        // New stack: 18
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodHandle;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;; Stack: 18
        cstack18.l = (cstrings[27]);
        // New stack: 19
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.7353569731942222.a()Ljava/lang/ClassLoader;; Stack: 19
        cstack19.l = classloader;
        // New stack: 20
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 20
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } if (!cmethods[2]) { cmethods[2] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 272LL)), ((char *)(string_pool + 299LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }  } cstack18.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[2]), cstack18.l, cstack19.l); refs.insert(cstack18.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }
        // New stack: 19
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 19
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[10]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } if (!cmethods[3]) { cmethods[3] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 372LL)), ((char *)(string_pool + 383LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }  } if (cstack15.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 481LL)), -1); else { cstack15.l = env->CallObjectMethod(cstack15.l, (cmethods[3]), cstack16.l, cstack17.l, cstack18.l); refs.insert(cstack15.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }
        // New stack: 16
        // SWAP; Stack: 16
        std::swap(cstack15, cstack14);
        // New stack: 16
        // LDC run; Stack: 16
        cstack16.l = (cstrings[28]);
        // New stack: 17
        // SWAP; Stack: 17
        std::swap(cstack16, cstack15);
        // New stack: 17
        // LDC (Ldev/sakura/L3MonKe_L;Ljava/util/concurrent/atomic/AtomicBoolean;Ldev/sakura/L3MonKe_B;ZLjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Set;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Runnable;; Stack: 17
        cstack17.l = (cstrings[29]);
        // New stack: 18
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.7353569731942222.a()Ljava/lang/ClassLoader;; Stack: 18
        cstack18.l = classloader;
        // New stack: 19
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 19
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } if (!cmethods[2]) { cmethods[2] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 272LL)), ((char *)(string_pool + 299LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }  } cstack17.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[2]), cstack17.l, cstack18.l); refs.insert(cstack17.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }
        // New stack: 18
        // SWAP; Stack: 18
        std::swap(cstack17, cstack16);
        // New stack: 18
        // LDC 3; Stack: 18
        cstack18.i = 3;
        // New stack: 19
        // ANEWARRAY java/lang/Object; Stack: 19
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } if (cstack18.i < 0) utils::throw_re(env, ((char *)(string_pool + 210LL)), ((char *)(string_pool + 247LL)), -1); else { cstack18.l = env->NewObjectArray(cstack18.i, (cclasses[5]), nullptr); refs.insert(cstack18.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }
        // New stack: 19
        // DUP; Stack: 19
        cstack19 = cstack18;
        // New stack: 20
        // LDC 0; Stack: 20
        cstack20.i = 0;
        // New stack: 21
        // LDC ()V; Stack: 21
        cstack21.l = (cstrings[30]);
        // New stack: 22
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.7353569731942222.a()Ljava/lang/ClassLoader;; Stack: 22
        cstack22.l = classloader;
        // New stack: 23
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 23
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } if (!cmethods[2]) { cmethods[2] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 272LL)), ((char *)(string_pool + 299LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }  } cstack21.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[2]), cstack21.l, cstack22.l); refs.insert(cstack21.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }
        // New stack: 22
        // AASTORE; Stack: 22
        if (cstack19.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 1072LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack19.l, cstack20.i, cstack21.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }
        // New stack: 19
        // DUP; Stack: 19
        cstack19 = cstack18;
        // New stack: 20
        // LDC 1; Stack: 20
        cstack20.i = 1;
        // New stack: 21
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.7353569731942222.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 21
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } cstack21.l = lookup;
        // New stack: 22
        // LDC Ldev/sakura/L3MonKe_h;; Stack: 22
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } cstack22.l = (cclasses[6]);
        // New stack: 23
        // LDC lambda$auth$0; Stack: 23
        cstack23.l = (cstrings[31]);
        // New stack: 24
        // LDC (Ldev/sakura/L3MonKe_L;Ljava/util/concurrent/atomic/AtomicBoolean;Ldev/sakura/L3MonKe_B;ZLjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Set;Ljava/lang/String;Ljava/lang/String;)V; Stack: 24
        cstack24.l = (cstrings[32]);
        // New stack: 25
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.7353569731942222.a()Ljava/lang/ClassLoader;; Stack: 25
        cstack25.l = classloader;
        // New stack: 26
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 26
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } if (!cmethods[2]) { cmethods[2] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 272LL)), ((char *)(string_pool + 299LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }  } cstack24.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[2]), cstack24.l, cstack25.l); refs.insert(cstack24.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }
        // New stack: 25
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 25
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[10]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } if (!cmethods[3]) { cmethods[3] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 372LL)), ((char *)(string_pool + 383LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }  } if (cstack21.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 481LL)), -1); else { cstack21.l = env->CallObjectMethod(cstack21.l, (cmethods[3]), cstack22.l, cstack23.l, cstack24.l); refs.insert(cstack21.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }
        // New stack: 22
        // AASTORE; Stack: 22
        if (cstack19.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 1072LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack19.l, cstack20.i, cstack21.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }
        // New stack: 19
        // DUP; Stack: 19
        cstack19 = cstack18;
        // New stack: 20
        // LDC 2; Stack: 20
        cstack20.i = 2;
        // New stack: 21
        // LDC ()V; Stack: 21
        cstack21.l = (cstrings[30]);
        // New stack: 22
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.7353569731942222.a()Ljava/lang/ClassLoader;; Stack: 22
        cstack22.l = classloader;
        // New stack: 23
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 23
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } if (!cmethods[2]) { cmethods[2] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 272LL)), ((char *)(string_pool + 299LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }  } cstack21.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[2]), cstack21.l, cstack22.l); refs.insert(cstack21.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }
        // New stack: 22
        // AASTORE; Stack: 22
        if (cstack19.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 1072LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack19.l, cstack20.i, cstack21.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }
        // New stack: 19
        // CHECKCAST java/lang/Object; Stack: 19
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } if (cstack18.l != nullptr && !env->IsInstanceOf(cstack18.l, (cclasses[5]))) { utils::throw_re(env, ((char *)(string_pool + 109LL)), (std::string(((char *)(string_pool + 138LL))) + std::string(((char *)(string_pool + 506LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } 
        // New stack: 19
        // SWAP; Stack: 19
        std::swap(cstack18, cstack17);
        // New stack: 19
        // INVOKESTATIC native/magic/1/linkcallsite/obfuscator0.7353569731942222.a(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/invoke/MemberName;; Stack: 19
        cstack13.l = utils::link_call_site(env, cstack13.l, cstack14.l, cstack15.l, cstack16.l, cstack17.l, cstack18.l); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }
        // New stack: 14
        // POP; Stack: 14
        ;
        // New stack: 13
        // ICONST_0; Stack: 13
        cstack13.i = 0;
        // New stack: 14
        // AALOAD; Stack: 14
        if (cstack12.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 98LL)), -1); else { cstack12.l = env->GetObjectArrayElement((jobjectArray) cstack12.l, cstack13.i); refs.insert(cstack12.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }
        // New stack: 13
        // DUP; Stack: 13
        cstack13 = cstack12;
        // New stack: 14
        // INSTANCEOF java/lang/invoke/CallSite; Stack: 14
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } cstack13.i = cstack13.l == nullptr ? false : env->IsInstanceOf(cstack13.l, (cclasses[10]));
        // New stack: 14
        // IFEQ L35; Stack: 14
        if (cstack13.i == 0) goto L35;
        // New stack: 13
        // INVOKEINTERFACE java/lang/invoke/CallSite.getTarget()Ljava/lang/invoke/MethodHandle;; Stack: 13
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[10]), ((char *)(string_pool + 523LL)), ((char *)(string_pool + 533LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }  } if (cstack12.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 567LL)), -1); else { cstack12.l = env->CallObjectMethod(cstack12.l, (cmethods[4])); refs.insert(cstack12.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }
        // New stack: 13
        // LABEL L35; Stack: 13
        L35: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }
        // New stack: 13
        // FRAME FULL L: [[Ljava/lang/Object;, java/lang/String, dev/sakura/L3MonKe_B, 4, java/lang/String, java/lang/String, 4, 4, 4, [Ljava/lang/String;, java/lang/String, java/util/Set, java/lang/String, java/lang/String, java/util/concurrent/atomic/AtomicBoolean, 1, dev/sakura/L3MonKe_M] S: [org.objectweb.asm.tree.LabelNode@60222fd8, org.objectweb.asm.tree.LabelNode@60222fd8, dev/sakura/L3MonKe_M, java/util/concurrent/atomic/AtomicBoolean, dev/sakura/L3MonKe_B, 1, java/lang/String, java/lang/String, java/lang/String, java/util/Set, java/lang/String, java/lang/String, java/lang/Object]; Stack: 13
        refs.erase(cstack0.l); refs.erase(cstack1.l); refs.erase(cstack2.l); refs.erase(cstack3.l); refs.erase(cstack4.l); refs.erase(cstack6.l); refs.erase(cstack7.l); refs.erase(cstack8.l); refs.erase(cstack9.l); refs.erase(cstack10.l); refs.erase(cstack11.l); refs.erase(cstack12.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal5.l); refs.erase(clocal6.l); refs.erase(clocal13.l); refs.erase(clocal14.l); refs.erase(clocal15.l); refs.erase(clocal16.l); refs.erase(clocal17.l); refs.erase(clocal18.l); refs.erase(clocal20.l); 
        utils::clear_refs(env, refs);
        // New stack: 13
        // CHECKCAST java/lang/invoke/MethodHandle; Stack: 13
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } if (cstack12.l != nullptr && !env->IsInstanceOf(cstack12.l, (cclasses[11]))) { utils::throw_re(env, ((char *)(string_pool + 109LL)), (std::string(((char *)(string_pool + 138LL))) + std::string(((char *)(string_pool + 594LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } 
        // New stack: 13
        // GOTO L36; Stack: 13
        goto L36;
        // New stack: 13
        // LABEL L8; Stack: 13
        L8: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 13
        // FRAME SAME1 L: null S: [java/lang/Throwable]; Stack: 13
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal5.l); refs.erase(clocal6.l); refs.erase(clocal13.l); refs.erase(clocal14.l); refs.erase(clocal15.l); refs.erase(clocal16.l); refs.erase(clocal17.l); refs.erase(clocal18.l); refs.erase(clocal20.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // INSTANCEOF java/lang/BootstrapMethodError; Stack: 2
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } cstack1.i = cstack1.l == nullptr ? false : env->IsInstanceOf(cstack1.l, (cclasses[12]));
        // New stack: 2
        // IFNE L37; Stack: 2
        if (cstack1.i != 0) goto L37;
        // New stack: 1
        // NEW java/lang/BootstrapMethodError; Stack: 1
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (jobject obj = env->AllocObject((cclasses[12]))) { cstack1.l = obj; refs.insert(obj); } 
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
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[5]) { cmethods[5] = env->GetMethodID((cclasses[12]), ((char *)(string_pool + 624LL)), ((char *)(string_pool + 631LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 656LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[12]), (cmethods[5]), cstack2.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // LABEL L37; Stack: 1
        L37: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/Object]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal5.l); refs.erase(clocal6.l); refs.erase(clocal13.l); refs.erase(clocal14.l); refs.erase(clocal15.l); refs.erase(clocal16.l); refs.erase(clocal17.l); refs.erase(clocal18.l); refs.erase(clocal20.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 679LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // LABEL L36; Stack: 0
        L36: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // FRAME FULL L: [[Ljava/lang/Object;, java/lang/String, dev/sakura/L3MonKe_B, 4, java/lang/String, java/lang/String, 4, 4, 4, [Ljava/lang/String;, java/lang/String, java/util/Set, java/lang/String, java/lang/String, java/util/concurrent/atomic/AtomicBoolean, 1, dev/sakura/L3MonKe_M] S: [org.objectweb.asm.tree.LabelNode@60222fd8, org.objectweb.asm.tree.LabelNode@60222fd8, dev/sakura/L3MonKe_M, java/util/concurrent/atomic/AtomicBoolean, dev/sakura/L3MonKe_B, 1, java/lang/String, java/lang/String, java/lang/String, java/util/Set, java/lang/String, java/lang/String, java/lang/invoke/MethodHandle]; Stack: 0
        refs.erase(cstack0.l); refs.erase(cstack1.l); refs.erase(cstack2.l); refs.erase(cstack3.l); refs.erase(cstack4.l); refs.erase(cstack6.l); refs.erase(cstack7.l); refs.erase(cstack8.l); refs.erase(cstack9.l); refs.erase(cstack10.l); refs.erase(cstack11.l); refs.erase(cstack12.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal5.l); refs.erase(clocal6.l); refs.erase(clocal13.l); refs.erase(clocal14.l); refs.erase(clocal15.l); refs.erase(clocal16.l); refs.erase(clocal17.l); refs.erase(clocal18.l); refs.erase(clocal20.l); 
        utils::clear_refs(env, refs);
        // New stack: 13
        // INVOKESTATIC native/magic/1/invoke/obfuscator0.7353569731942222.a(Ldev/sakura/L3MonKe_L;Ljava/util/concurrent/atomic/AtomicBoolean;Ldev/sakura/L3MonKe_B;ZLjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Set;Ljava/lang/String;Ljava/lang/String;Ljava/lang/invoke/MethodHandle;)Ljava/lang/Runnable;; Stack: 13
        if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { cclasses_mtx[13].lock(); if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[16]))) { cclasses[13] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[13].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[11]) { cmethods[11] = env->GetStaticMethodID((cclasses[13]), ((char *)(string_pool + 18407LL)), ((char *)(string_pool + 18423LL))); if (env->ExceptionCheck()) { return (void) 0; }  } cstack2.l = env->CallStaticObjectMethod((cclasses[13]), (cmethods[11]), cstack2.l, cstack3.l, cstack4.l, cstack5.i, cstack6.l, cstack7.l, cstack8.l, cstack9.l, cstack10.l, cstack11.l, cstack12.l); refs.insert(cstack2.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 3
        // CHECKCAST java/lang/Runnable; Stack: 3
        if (!cclasses[20] || env->IsSameObject(cclasses[20], NULL)) { cclasses_mtx[20].lock(); if (!cclasses[20] || env->IsSameObject(cclasses[20], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[33]))) { cclasses[20] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[20].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (cstack2.l != nullptr && !env->IsInstanceOf(cstack2.l, (cclasses[20]))) { utils::throw_re(env, ((char *)(string_pool + 109LL)), (std::string(((char *)(string_pool + 138LL))) + std::string(((char *)(string_pool + 18638LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (void) 0; } } 
        // New stack: 3
        // SIPUSH 29157; Stack: 3
        cstack3.i = (jint) 29157;
        // New stack: 4
        // LDC 8675372155094692674; Stack: 4
        cstack4.j = 8675372155094692674LL;
        // New stack: 6
        // LLOAD 3; Stack: 6
        cstack6.j = clocal3.j;
        // New stack: 8
        // LXOR; Stack: 8
        cstack4.j = cstack4.j ^ cstack6.j;
        // New stack: 6
        // LABEL L5; Stack: 6
        L5: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }
        // New stack: 6
        // ICONST_1; Stack: 6
        cstack6.i = 1;
        // New stack: 7
        // ANEWARRAY java/lang/Object; Stack: 7
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } if (cstack6.i < 0) utils::throw_re(env, ((char *)(string_pool + 210LL)), ((char *)(string_pool + 247LL)), -1); else { cstack6.l = env->NewObjectArray(cstack6.i, (cclasses[5]), nullptr); refs.insert(cstack6.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }
        // New stack: 7
        // DUP; Stack: 7
        cstack7 = cstack6;
        // New stack: 8
        // LDC Ldev/sakura/L3MonKe_h;; Stack: 8
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } cstack8.l = (cclasses[6]);
        // New stack: 9
        // SWAP; Stack: 9
        std::swap(cstack8, cstack7);
        // New stack: 9
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.7353569731942222.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 9
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } cstack9.l = lookup;
        // New stack: 10
        // LDC Ldev/sakura/L3MonKe_h;; Stack: 10
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } cstack10.l = (cclasses[6]);
        // New stack: 11
        // LDC a; Stack: 11
        cstack11.l = (cstrings[7]);
        // New stack: 12
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;; Stack: 12
        cstack12.l = (cstrings[8]);
        // New stack: 13
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.7353569731942222.a()Ljava/lang/ClassLoader;; Stack: 13
        cstack13.l = classloader;
        // New stack: 14
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 14
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } if (!cmethods[2]) { cmethods[2] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 272LL)), ((char *)(string_pool + 299LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }  } cstack12.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[2]), cstack12.l, cstack13.l); refs.insert(cstack12.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }
        // New stack: 13
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 13
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[10]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } if (!cmethods[3]) { cmethods[3] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 372LL)), ((char *)(string_pool + 383LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }  } if (cstack9.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 481LL)), -1); else { cstack9.l = env->CallObjectMethod(cstack9.l, (cmethods[3]), cstack10.l, cstack11.l, cstack12.l); refs.insert(cstack9.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }
        // New stack: 10
        // SWAP; Stack: 10
        std::swap(cstack9, cstack8);
        // New stack: 10
        // LDC m; Stack: 10
        cstack10.l = (cstrings[34]);
        // New stack: 11
        // SWAP; Stack: 11
        std::swap(cstack10, cstack9);
        // New stack: 11
        // LDC (IJ)Ljava/lang/String;; Stack: 11
        cstack11.l = (cstrings[35]);
        // New stack: 12
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.7353569731942222.a()Ljava/lang/ClassLoader;; Stack: 12
        cstack12.l = classloader;
        // New stack: 13
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 13
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } if (!cmethods[2]) { cmethods[2] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 272LL)), ((char *)(string_pool + 299LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }  } cstack11.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[2]), cstack11.l, cstack12.l); refs.insert(cstack11.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }
        // New stack: 12
        // SWAP; Stack: 12
        std::swap(cstack11, cstack10);
        // New stack: 12
        // LDC 0; Stack: 12
        cstack12.i = 0;
        // New stack: 13
        // ANEWARRAY java/lang/Object; Stack: 13
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } if (cstack12.i < 0) utils::throw_re(env, ((char *)(string_pool + 210LL)), ((char *)(string_pool + 247LL)), -1); else { cstack12.l = env->NewObjectArray(cstack12.i, (cclasses[5]), nullptr); refs.insert(cstack12.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }
        // New stack: 13
        // CHECKCAST java/lang/Object; Stack: 13
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } if (cstack12.l != nullptr && !env->IsInstanceOf(cstack12.l, (cclasses[5]))) { utils::throw_re(env, ((char *)(string_pool + 109LL)), (std::string(((char *)(string_pool + 138LL))) + std::string(((char *)(string_pool + 506LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } 
        // New stack: 13
        // SWAP; Stack: 13
        std::swap(cstack12, cstack11);
        // New stack: 13
        // INVOKESTATIC native/magic/1/linkcallsite/obfuscator0.7353569731942222.a(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/invoke/MemberName;; Stack: 13
        cstack7.l = utils::link_call_site(env, cstack7.l, cstack8.l, cstack9.l, cstack10.l, cstack11.l, cstack12.l); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }
        // New stack: 8
        // POP; Stack: 8
        ;
        // New stack: 7
        // ICONST_0; Stack: 7
        cstack7.i = 0;
        // New stack: 8
        // AALOAD; Stack: 8
        if (cstack6.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 98LL)), -1); else { cstack6.l = env->GetObjectArrayElement((jobjectArray) cstack6.l, cstack7.i); refs.insert(cstack6.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }
        // New stack: 7
        // DUP; Stack: 7
        cstack7 = cstack6;
        // New stack: 8
        // INSTANCEOF java/lang/invoke/CallSite; Stack: 8
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } cstack7.i = cstack7.l == nullptr ? false : env->IsInstanceOf(cstack7.l, (cclasses[10]));
        // New stack: 8
        // IFEQ L38; Stack: 8
        if (cstack7.i == 0) goto L38;
        // New stack: 7
        // INVOKEINTERFACE java/lang/invoke/CallSite.getTarget()Ljava/lang/invoke/MethodHandle;; Stack: 7
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[10]), ((char *)(string_pool + 523LL)), ((char *)(string_pool + 533LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }  } if (cstack6.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 567LL)), -1); else { cstack6.l = env->CallObjectMethod(cstack6.l, (cmethods[4])); refs.insert(cstack6.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }
        // New stack: 7
        // LABEL L38; Stack: 7
        L38: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }
        // New stack: 7
        // FRAME FULL L: [[Ljava/lang/Object;, java/lang/String, dev/sakura/L3MonKe_B, 4, java/lang/String, java/lang/String, 4, 4, 4, [Ljava/lang/String;, java/lang/String, java/util/Set, java/lang/String, java/lang/String, java/util/concurrent/atomic/AtomicBoolean, 1, dev/sakura/L3MonKe_M] S: [org.objectweb.asm.tree.LabelNode@60222fd8, org.objectweb.asm.tree.LabelNode@60222fd8, java/lang/Runnable, 1, 4, java/lang/Object]; Stack: 7
        refs.erase(cstack0.l); refs.erase(cstack1.l); refs.erase(cstack2.l); refs.erase(cstack6.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal5.l); refs.erase(clocal6.l); refs.erase(clocal13.l); refs.erase(clocal14.l); refs.erase(clocal15.l); refs.erase(clocal16.l); refs.erase(clocal17.l); refs.erase(clocal18.l); refs.erase(clocal20.l); 
        utils::clear_refs(env, refs);
        // New stack: 7
        // CHECKCAST java/lang/invoke/MethodHandle; Stack: 7
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } if (cstack6.l != nullptr && !env->IsInstanceOf(cstack6.l, (cclasses[11]))) { utils::throw_re(env, ((char *)(string_pool + 109LL)), (std::string(((char *)(string_pool + 138LL))) + std::string(((char *)(string_pool + 594LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } 
        // New stack: 7
        // GOTO L39; Stack: 7
        goto L39;
        // New stack: 7
        // LABEL L6; Stack: 7
        L6: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 7
        // FRAME SAME1 L: null S: [java/lang/Throwable]; Stack: 7
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal5.l); refs.erase(clocal6.l); refs.erase(clocal13.l); refs.erase(clocal14.l); refs.erase(clocal15.l); refs.erase(clocal16.l); refs.erase(clocal17.l); refs.erase(clocal18.l); refs.erase(clocal20.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // INSTANCEOF java/lang/BootstrapMethodError; Stack: 2
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } cstack1.i = cstack1.l == nullptr ? false : env->IsInstanceOf(cstack1.l, (cclasses[12]));
        // New stack: 2
        // IFNE L40; Stack: 2
        if (cstack1.i != 0) goto L40;
        // New stack: 1
        // NEW java/lang/BootstrapMethodError; Stack: 1
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (jobject obj = env->AllocObject((cclasses[12]))) { cstack1.l = obj; refs.insert(obj); } 
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
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[5]) { cmethods[5] = env->GetMethodID((cclasses[12]), ((char *)(string_pool + 624LL)), ((char *)(string_pool + 631LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 656LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[12]), (cmethods[5]), cstack2.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // LABEL L40; Stack: 1
        L40: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/Object]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal5.l); refs.erase(clocal6.l); refs.erase(clocal13.l); refs.erase(clocal14.l); refs.erase(clocal15.l); refs.erase(clocal16.l); refs.erase(clocal17.l); refs.erase(clocal18.l); refs.erase(clocal20.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 679LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // LABEL L39; Stack: 0
        L39: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // FRAME FULL L: [[Ljava/lang/Object;, java/lang/String, dev/sakura/L3MonKe_B, 4, java/lang/String, java/lang/String, 4, 4, 4, [Ljava/lang/String;, java/lang/String, java/util/Set, java/lang/String, java/lang/String, java/util/concurrent/atomic/AtomicBoolean, 1, dev/sakura/L3MonKe_M] S: [org.objectweb.asm.tree.LabelNode@60222fd8, org.objectweb.asm.tree.LabelNode@60222fd8, java/lang/Runnable, 1, 4, java/lang/invoke/MethodHandle]; Stack: 0
        refs.erase(cstack0.l); refs.erase(cstack1.l); refs.erase(cstack2.l); refs.erase(cstack6.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal5.l); refs.erase(clocal6.l); refs.erase(clocal13.l); refs.erase(clocal14.l); refs.erase(clocal15.l); refs.erase(clocal16.l); refs.erase(clocal17.l); refs.erase(clocal18.l); refs.erase(clocal20.l); 
        utils::clear_refs(env, refs);
        // New stack: 7
        // INVOKESTATIC native/magic/1/invoke/obfuscator0.7353569731942222.a(IJLjava/lang/invoke/MethodHandle;)Ljava/lang/String;; Stack: 7
        if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { cclasses_mtx[13].lock(); if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[16]))) { cclasses[13] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[13].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[12]) { cmethods[12] = env->GetStaticMethodID((cclasses[13]), ((char *)(string_pool + 783LL)), ((char *)(string_pool + 798LL))); if (env->ExceptionCheck()) { return (void) 0; }  } cstack3.l = env->CallStaticObjectMethod((cclasses[13]), (cmethods[12]), cstack3.i, cstack4.j, cstack6.l); refs.insert(cstack3.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 4
        // CHECKCAST java/lang/String; Stack: 4
        if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { cclasses_mtx[1].lock(); if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[1]))) { cclasses[1] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[1].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (cstack3.l != nullptr && !env->IsInstanceOf(cstack3.l, (cclasses[1]))) { utils::throw_re(env, ((char *)(string_pool + 109LL)), (std::string(((char *)(string_pool + 138LL))) + std::string(((char *)(string_pool + 852LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (void) 0; } } 
        // New stack: 4
        // INVOKESPECIAL java/lang/Thread.<init>(Ljava/lang/Runnable;Ljava/lang/String;)V; Stack: 4
        if (!cclasses[18] || env->IsSameObject(cclasses[18], NULL)) { cclasses_mtx[18].lock(); if (!cclasses[18] || env->IsSameObject(cclasses[18], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[24]))) { cclasses[18] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[18].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[13]) { cmethods[13] = env->GetMethodID((cclasses[18]), ((char *)(string_pool + 624LL)), ((char *)(string_pool + 18657LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 656LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[18]), (cmethods[13]), cstack2.l, cstack3.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // ASTORE 21; Stack: 1
        clocal21.l = cstack0.l; refs.insert(cstack0.l);
        // New stack: 0
        // ALOAD 21; Stack: 0
        cstack0.l = clocal21.l; refs.insert(cstack0.l);
        // New stack: 1
        // ICONST_1; Stack: 1
        cstack1.i = 1;
        // New stack: 2
        // LDC 628109068438575364; Stack: 2
        cstack2.j = 628109068438575364LL;
        // New stack: 4
        // LLOAD 3; Stack: 4
        cstack4.j = clocal3.j;
        // New stack: 6
        // LABEL L3; Stack: 6
        L3: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; }
        // New stack: 6
        // ICONST_1; Stack: 6
        cstack6.i = 1;
        // New stack: 7
        // ANEWARRAY java/lang/Object; Stack: 7
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; } } if (cstack6.i < 0) utils::throw_re(env, ((char *)(string_pool + 210LL)), ((char *)(string_pool + 247LL)), -1); else { cstack6.l = env->NewObjectArray(cstack6.i, (cclasses[5]), nullptr); refs.insert(cstack6.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; }
        // New stack: 7
        // DUP; Stack: 7
        cstack7 = cstack6;
        // New stack: 8
        // LDC Ldev/sakura/L3MonKe_h;; Stack: 8
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; } } cstack8.l = (cclasses[6]);
        // New stack: 9
        // SWAP; Stack: 9
        std::swap(cstack8, cstack7);
        // New stack: 9
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.7353569731942222.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 9
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; } } cstack9.l = lookup;
        // New stack: 10
        // LDC Ldev/sakura/client/L3MonKe_f2;; Stack: 10
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[6]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; } } cstack10.l = (cclasses[7]);
        // New stack: 11
        // LDC a; Stack: 11
        cstack11.l = (cstrings[7]);
        // New stack: 12
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;; Stack: 12
        cstack12.l = (cstrings[8]);
        // New stack: 13
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.7353569731942222.a()Ljava/lang/ClassLoader;; Stack: 13
        cstack13.l = classloader;
        // New stack: 14
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 14
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; } } if (!cmethods[2]) { cmethods[2] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 272LL)), ((char *)(string_pool + 299LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; }  } cstack12.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[2]), cstack12.l, cstack13.l); refs.insert(cstack12.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; }
        // New stack: 13
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 13
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[10]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; } } if (!cmethods[3]) { cmethods[3] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 372LL)), ((char *)(string_pool + 383LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; }  } if (cstack9.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 481LL)), -1); else { cstack9.l = env->CallObjectMethod(cstack9.l, (cmethods[3]), cstack10.l, cstack11.l, cstack12.l); refs.insert(cstack9.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; }
        // New stack: 10
        // SWAP; Stack: 10
        std::swap(cstack9, cstack8);
        // New stack: 10
        // LDC \u00de; Stack: 10
        cstack10.l = (cstrings[36]);
        // New stack: 11
        // SWAP; Stack: 11
        std::swap(cstack10, cstack9);
        // New stack: 11
        // LDC (Ljava/lang/Object;ZJJ)V; Stack: 11
        cstack11.l = (cstrings[37]);
        // New stack: 12
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.7353569731942222.a()Ljava/lang/ClassLoader;; Stack: 12
        cstack12.l = classloader;
        // New stack: 13
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 13
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; } } if (!cmethods[2]) { cmethods[2] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 272LL)), ((char *)(string_pool + 299LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; }  } cstack11.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[2]), cstack11.l, cstack12.l); refs.insert(cstack11.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; }
        // New stack: 12
        // SWAP; Stack: 12
        std::swap(cstack11, cstack10);
        // New stack: 12
        // LDC 0; Stack: 12
        cstack12.i = 0;
        // New stack: 13
        // ANEWARRAY java/lang/Object; Stack: 13
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; } } if (cstack12.i < 0) utils::throw_re(env, ((char *)(string_pool + 210LL)), ((char *)(string_pool + 247LL)), -1); else { cstack12.l = env->NewObjectArray(cstack12.i, (cclasses[5]), nullptr); refs.insert(cstack12.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; }
        // New stack: 13
        // CHECKCAST java/lang/Object; Stack: 13
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; } } if (cstack12.l != nullptr && !env->IsInstanceOf(cstack12.l, (cclasses[5]))) { utils::throw_re(env, ((char *)(string_pool + 109LL)), (std::string(((char *)(string_pool + 138LL))) + std::string(((char *)(string_pool + 506LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; } } 
        // New stack: 13
        // SWAP; Stack: 13
        std::swap(cstack12, cstack11);
        // New stack: 13
        // INVOKESTATIC native/magic/1/linkcallsite/obfuscator0.7353569731942222.a(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/invoke/MemberName;; Stack: 13
        cstack7.l = utils::link_call_site(env, cstack7.l, cstack8.l, cstack9.l, cstack10.l, cstack11.l, cstack12.l); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; }
        // New stack: 8
        // POP; Stack: 8
        ;
        // New stack: 7
        // ICONST_0; Stack: 7
        cstack7.i = 0;
        // New stack: 8
        // AALOAD; Stack: 8
        if (cstack6.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 98LL)), -1); else { cstack6.l = env->GetObjectArrayElement((jobjectArray) cstack6.l, cstack7.i); refs.insert(cstack6.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; }
        // New stack: 7
        // DUP; Stack: 7
        cstack7 = cstack6;
        // New stack: 8
        // INSTANCEOF java/lang/invoke/CallSite; Stack: 8
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; } } cstack7.i = cstack7.l == nullptr ? false : env->IsInstanceOf(cstack7.l, (cclasses[10]));
        // New stack: 8
        // IFEQ L41; Stack: 8
        if (cstack7.i == 0) goto L41;
        // New stack: 7
        // INVOKEINTERFACE java/lang/invoke/CallSite.getTarget()Ljava/lang/invoke/MethodHandle;; Stack: 7
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[10]), ((char *)(string_pool + 523LL)), ((char *)(string_pool + 533LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; }  } if (cstack6.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 567LL)), -1); else { cstack6.l = env->CallObjectMethod(cstack6.l, (cmethods[4])); refs.insert(cstack6.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; }
        // New stack: 7
        // LABEL L41; Stack: 7
        L41: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; }
        // New stack: 7
        // FRAME FULL L: [[Ljava/lang/Object;, java/lang/String, dev/sakura/L3MonKe_B, 4, java/lang/String, java/lang/String, 4, 4, 4, [Ljava/lang/String;, java/lang/String, java/util/Set, java/lang/String, java/lang/String, java/util/concurrent/atomic/AtomicBoolean, 1, dev/sakura/L3MonKe_M, java/lang/Thread] S: [java/lang/Thread, 1, 4, 4, java/lang/Object]; Stack: 7
        refs.erase(cstack0.l); refs.erase(cstack6.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal5.l); refs.erase(clocal6.l); refs.erase(clocal13.l); refs.erase(clocal14.l); refs.erase(clocal15.l); refs.erase(clocal16.l); refs.erase(clocal17.l); refs.erase(clocal18.l); refs.erase(clocal20.l); refs.erase(clocal21.l); 
        utils::clear_refs(env, refs);
        // New stack: 7
        // CHECKCAST java/lang/invoke/MethodHandle; Stack: 7
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; } } if (cstack6.l != nullptr && !env->IsInstanceOf(cstack6.l, (cclasses[11]))) { utils::throw_re(env, ((char *)(string_pool + 109LL)), (std::string(((char *)(string_pool + 138LL))) + std::string(((char *)(string_pool + 594LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; } } 
        // New stack: 7
        // GOTO L42; Stack: 7
        goto L42;
        // New stack: 7
        // LABEL L4; Stack: 7
        L4: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 7
        // FRAME SAME1 L: null S: [java/lang/Throwable]; Stack: 7
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal5.l); refs.erase(clocal6.l); refs.erase(clocal13.l); refs.erase(clocal14.l); refs.erase(clocal15.l); refs.erase(clocal16.l); refs.erase(clocal17.l); refs.erase(clocal18.l); refs.erase(clocal20.l); refs.erase(clocal21.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // INSTANCEOF java/lang/BootstrapMethodError; Stack: 2
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } cstack1.i = cstack1.l == nullptr ? false : env->IsInstanceOf(cstack1.l, (cclasses[12]));
        // New stack: 2
        // IFNE L43; Stack: 2
        if (cstack1.i != 0) goto L43;
        // New stack: 1
        // NEW java/lang/BootstrapMethodError; Stack: 1
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (jobject obj = env->AllocObject((cclasses[12]))) { cstack1.l = obj; refs.insert(obj); } 
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
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[5]) { cmethods[5] = env->GetMethodID((cclasses[12]), ((char *)(string_pool + 624LL)), ((char *)(string_pool + 631LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 656LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[12]), (cmethods[5]), cstack2.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // LABEL L43; Stack: 1
        L43: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/Object]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal5.l); refs.erase(clocal6.l); refs.erase(clocal13.l); refs.erase(clocal14.l); refs.erase(clocal15.l); refs.erase(clocal16.l); refs.erase(clocal17.l); refs.erase(clocal18.l); refs.erase(clocal20.l); refs.erase(clocal21.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 679LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // LABEL L42; Stack: 0
        L42: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // FRAME FULL L: [[Ljava/lang/Object;, java/lang/String, dev/sakura/L3MonKe_B, 4, java/lang/String, java/lang/String, 4, 4, 4, [Ljava/lang/String;, java/lang/String, java/util/Set, java/lang/String, java/lang/String, java/util/concurrent/atomic/AtomicBoolean, 1, dev/sakura/L3MonKe_M, java/lang/Thread] S: [java/lang/Thread, 1, 4, 4, java/lang/invoke/MethodHandle]; Stack: 0
        refs.erase(cstack0.l); refs.erase(cstack6.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal5.l); refs.erase(clocal6.l); refs.erase(clocal13.l); refs.erase(clocal14.l); refs.erase(clocal15.l); refs.erase(clocal16.l); refs.erase(clocal17.l); refs.erase(clocal18.l); refs.erase(clocal20.l); refs.erase(clocal21.l); 
        utils::clear_refs(env, refs);
        // New stack: 7
        // INVOKESTATIC native/magic/1/invoke/obfuscator0.7353569731942222.a(Ljava/lang/Object;ZJJLjava/lang/invoke/MethodHandle;)V; Stack: 7
        if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { cclasses_mtx[13].lock(); if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[16]))) { cclasses[13] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[13].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[14]) { cmethods[14] = env->GetStaticMethodID((cclasses[13]), ((char *)(string_pool + 18699LL)), ((char *)(string_pool + 18715LL))); if (env->ExceptionCheck()) { return (void) 0; }  } env->CallStaticVoidMethod((cclasses[13]), (cmethods[14]), cstack0.l, cstack1.i, cstack2.j, cstack4.j, cstack6.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // ALOAD 21; Stack: 0
        cstack0.l = clocal21.l; refs.insert(cstack0.l);
        // New stack: 1
        // LDC 655654423961319189; Stack: 1
        cstack1.j = 655654423961319189LL;
        // New stack: 3
        // LLOAD 3; Stack: 3
        cstack3.j = clocal3.j;
        // New stack: 5
        // LABEL L1; Stack: 5
        L1: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; }
        // New stack: 5
        // ICONST_1; Stack: 5
        cstack5.i = 1;
        // New stack: 6
        // ANEWARRAY java/lang/Object; Stack: 6
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; } } if (cstack5.i < 0) utils::throw_re(env, ((char *)(string_pool + 210LL)), ((char *)(string_pool + 247LL)), -1); else { cstack5.l = env->NewObjectArray(cstack5.i, (cclasses[5]), nullptr); refs.insert(cstack5.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; }
        // New stack: 6
        // DUP; Stack: 6
        cstack6 = cstack5;
        // New stack: 7
        // LDC Ldev/sakura/L3MonKe_h;; Stack: 7
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; } } cstack7.l = (cclasses[6]);
        // New stack: 8
        // SWAP; Stack: 8
        std::swap(cstack7, cstack6);
        // New stack: 8
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.7353569731942222.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 8
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; } } cstack8.l = lookup;
        // New stack: 9
        // LDC Ldev/sakura/client/L3MonKe_f2;; Stack: 9
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[6]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; } } cstack9.l = (cclasses[7]);
        // New stack: 10
        // LDC a; Stack: 10
        cstack10.l = (cstrings[7]);
        // New stack: 11
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;; Stack: 11
        cstack11.l = (cstrings[8]);
        // New stack: 12
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.7353569731942222.a()Ljava/lang/ClassLoader;; Stack: 12
        cstack12.l = classloader;
        // New stack: 13
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 13
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; } } if (!cmethods[2]) { cmethods[2] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 272LL)), ((char *)(string_pool + 299LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; }  } cstack11.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[2]), cstack11.l, cstack12.l); refs.insert(cstack11.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; }
        // New stack: 12
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 12
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[10]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; } } if (!cmethods[3]) { cmethods[3] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 372LL)), ((char *)(string_pool + 383LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; }  } if (cstack8.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 481LL)), -1); else { cstack8.l = env->CallObjectMethod(cstack8.l, (cmethods[3]), cstack9.l, cstack10.l, cstack11.l); refs.insert(cstack8.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; }
        // New stack: 9
        // SWAP; Stack: 9
        std::swap(cstack8, cstack7);
        // New stack: 9
        // LDC \u00de; Stack: 9
        cstack9.l = (cstrings[36]);
        // New stack: 10
        // SWAP; Stack: 10
        std::swap(cstack9, cstack8);
        // New stack: 10
        // LDC (Ljava/lang/Object;JJ)V; Stack: 10
        cstack10.l = (cstrings[12]);
        // New stack: 11
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.7353569731942222.a()Ljava/lang/ClassLoader;; Stack: 11
        cstack11.l = classloader;
        // New stack: 12
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 12
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; } } if (!cmethods[2]) { cmethods[2] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 272LL)), ((char *)(string_pool + 299LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; }  } cstack10.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[2]), cstack10.l, cstack11.l); refs.insert(cstack10.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; }
        // New stack: 11
        // SWAP; Stack: 11
        std::swap(cstack10, cstack9);
        // New stack: 11
        // LDC 0; Stack: 11
        cstack11.i = 0;
        // New stack: 12
        // ANEWARRAY java/lang/Object; Stack: 12
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; } } if (cstack11.i < 0) utils::throw_re(env, ((char *)(string_pool + 210LL)), ((char *)(string_pool + 247LL)), -1); else { cstack11.l = env->NewObjectArray(cstack11.i, (cclasses[5]), nullptr); refs.insert(cstack11.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; }
        // New stack: 12
        // CHECKCAST java/lang/Object; Stack: 12
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; } } if (cstack11.l != nullptr && !env->IsInstanceOf(cstack11.l, (cclasses[5]))) { utils::throw_re(env, ((char *)(string_pool + 109LL)), (std::string(((char *)(string_pool + 138LL))) + std::string(((char *)(string_pool + 506LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; } } 
        // New stack: 12
        // SWAP; Stack: 12
        std::swap(cstack11, cstack10);
        // New stack: 12
        // INVOKESTATIC native/magic/1/linkcallsite/obfuscator0.7353569731942222.a(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/invoke/MemberName;; Stack: 12
        cstack6.l = utils::link_call_site(env, cstack6.l, cstack7.l, cstack8.l, cstack9.l, cstack10.l, cstack11.l); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; }
        // New stack: 7
        // POP; Stack: 7
        ;
        // New stack: 6
        // ICONST_0; Stack: 6
        cstack6.i = 0;
        // New stack: 7
        // AALOAD; Stack: 7
        if (cstack5.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 98LL)), -1); else { cstack5.l = env->GetObjectArrayElement((jobjectArray) cstack5.l, cstack6.i); refs.insert(cstack5.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; }
        // New stack: 6
        // DUP; Stack: 6
        cstack6 = cstack5;
        // New stack: 7
        // INSTANCEOF java/lang/invoke/CallSite; Stack: 7
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; } } cstack6.i = cstack6.l == nullptr ? false : env->IsInstanceOf(cstack6.l, (cclasses[10]));
        // New stack: 7
        // IFEQ L44; Stack: 7
        if (cstack6.i == 0) goto L44;
        // New stack: 6
        // INVOKEINTERFACE java/lang/invoke/CallSite.getTarget()Ljava/lang/invoke/MethodHandle;; Stack: 6
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[10]), ((char *)(string_pool + 523LL)), ((char *)(string_pool + 533LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; }  } if (cstack5.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 567LL)), -1); else { cstack5.l = env->CallObjectMethod(cstack5.l, (cmethods[4])); refs.insert(cstack5.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; }
        // New stack: 6
        // LABEL L44; Stack: 6
        L44: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; }
        // New stack: 6
        // FRAME FULL L: [[Ljava/lang/Object;, java/lang/String, dev/sakura/L3MonKe_B, 4, java/lang/String, java/lang/String, 4, 4, 4, [Ljava/lang/String;, java/lang/String, java/util/Set, java/lang/String, java/lang/String, java/util/concurrent/atomic/AtomicBoolean, 1, dev/sakura/L3MonKe_M, java/lang/Thread] S: [java/lang/Thread, 4, 4, java/lang/Object]; Stack: 6
        refs.erase(cstack0.l); refs.erase(cstack5.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal5.l); refs.erase(clocal6.l); refs.erase(clocal13.l); refs.erase(clocal14.l); refs.erase(clocal15.l); refs.erase(clocal16.l); refs.erase(clocal17.l); refs.erase(clocal18.l); refs.erase(clocal20.l); refs.erase(clocal21.l); 
        utils::clear_refs(env, refs);
        // New stack: 6
        // CHECKCAST java/lang/invoke/MethodHandle; Stack: 6
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; } } if (cstack5.l != nullptr && !env->IsInstanceOf(cstack5.l, (cclasses[11]))) { utils::throw_re(env, ((char *)(string_pool + 109LL)), (std::string(((char *)(string_pool + 138LL))) + std::string(((char *)(string_pool + 594LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; } } 
        // New stack: 6
        // GOTO L45; Stack: 6
        goto L45;
        // New stack: 6
        // LABEL L2; Stack: 6
        L2: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 6
        // FRAME SAME1 L: null S: [java/lang/Throwable]; Stack: 6
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal5.l); refs.erase(clocal6.l); refs.erase(clocal13.l); refs.erase(clocal14.l); refs.erase(clocal15.l); refs.erase(clocal16.l); refs.erase(clocal17.l); refs.erase(clocal18.l); refs.erase(clocal20.l); refs.erase(clocal21.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // INSTANCEOF java/lang/BootstrapMethodError; Stack: 2
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } cstack1.i = cstack1.l == nullptr ? false : env->IsInstanceOf(cstack1.l, (cclasses[12]));
        // New stack: 2
        // IFNE L46; Stack: 2
        if (cstack1.i != 0) goto L46;
        // New stack: 1
        // NEW java/lang/BootstrapMethodError; Stack: 1
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (jobject obj = env->AllocObject((cclasses[12]))) { cstack1.l = obj; refs.insert(obj); } 
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
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[5]) { cmethods[5] = env->GetMethodID((cclasses[12]), ((char *)(string_pool + 624LL)), ((char *)(string_pool + 631LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 656LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[12]), (cmethods[5]), cstack2.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // LABEL L46; Stack: 1
        L46: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/Object]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal5.l); refs.erase(clocal6.l); refs.erase(clocal13.l); refs.erase(clocal14.l); refs.erase(clocal15.l); refs.erase(clocal16.l); refs.erase(clocal17.l); refs.erase(clocal18.l); refs.erase(clocal20.l); refs.erase(clocal21.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 679LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // LABEL L45; Stack: 0
        L45: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // FRAME FULL L: [[Ljava/lang/Object;, java/lang/String, dev/sakura/L3MonKe_B, 4, java/lang/String, java/lang/String, 4, 4, 4, [Ljava/lang/String;, java/lang/String, java/util/Set, java/lang/String, java/lang/String, java/util/concurrent/atomic/AtomicBoolean, 1, dev/sakura/L3MonKe_M, java/lang/Thread] S: [java/lang/Thread, 4, 4, java/lang/invoke/MethodHandle]; Stack: 0
        refs.erase(cstack0.l); refs.erase(cstack5.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal5.l); refs.erase(clocal6.l); refs.erase(clocal13.l); refs.erase(clocal14.l); refs.erase(clocal15.l); refs.erase(clocal16.l); refs.erase(clocal17.l); refs.erase(clocal18.l); refs.erase(clocal20.l); refs.erase(clocal21.l); 
        utils::clear_refs(env, refs);
        // New stack: 6
        // INVOKESTATIC native/magic/1/invoke/obfuscator0.7353569731942222.a(Ljava/lang/Object;JJLjava/lang/invoke/MethodHandle;)V; Stack: 6
        if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { cclasses_mtx[13].lock(); if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[16]))) { cclasses[13] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[13].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[6]) { cmethods[6] = env->GetStaticMethodID((cclasses[13]), ((char *)(string_pool + 5836LL)), ((char *)(string_pool + 5852LL))); if (env->ExceptionCheck()) { return (void) 0; }  } env->CallStaticVoidMethod((cclasses[13]), (cmethods[6]), cstack0.l, cstack1.j, cstack3.j, cstack5.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // RETURN; Stack: 0
        return;
        // New stack: 0
        return (void) 0;
        L_CATCH_7: if (env->IsInstanceOf(cstack0.l, (cclasses[0]))) { goto L2; }
        env->Throw((jthrowable) cstack0.l); return (void) 0;
        L_CATCH_6: if (env->IsInstanceOf(cstack0.l, (cclasses[0]))) { goto L4; }
        env->Throw((jthrowable) cstack0.l); return (void) 0;
        L_CATCH_5: if (env->IsInstanceOf(cstack0.l, (cclasses[0]))) { goto L6; }
        env->Throw((jthrowable) cstack0.l); return (void) 0;
        L_CATCH_4: if (env->IsInstanceOf(cstack0.l, (cclasses[0]))) { goto L8; }
        env->Throw((jthrowable) cstack0.l); return (void) 0;
        L_CATCH_2: if (env->IsInstanceOf(cstack0.l, (cclasses[0]))) { goto L12; }
        env->Throw((jthrowable) cstack0.l); return (void) 0;
        L_CATCH_3: if (env->IsInstanceOf(cstack0.l, (cclasses[0]))) { goto L10; }
        env->Throw((jthrowable) cstack0.l); return (void) 0;
        L_CATCH_1: if (env->IsInstanceOf(cstack0.l, (cclasses[0]))) { goto L14; }
        env->Throw((jthrowable) cstack0.l); return (void) 0;
        L_CATCH_0: if (env->IsInstanceOf(cstack0.l, (cclasses[0]))) { goto L16; }
        env->Throw((jthrowable) cstack0.l); return (void) 0;
    }
    
    // lambda$auth$0(Ldev/sakura/L3MonKe_L;Ljava/util/concurrent/atomic/AtomicBoolean;Ldev/sakura/L3MonKe_B;ZLjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Set;Ljava/lang/String;Ljava/lang/String;)V
    void JNICALL __ngen_native_lambdau36authu3604(JNIEnv *env, jclass clazz, jobject arg0, jobject arg1, jobject arg2, jboolean arg3, jobject arg4, jobject arg5, jobject arg6, jobject arg7, jobject arg8, jobject arg9) {
        jobject classloader = utils::get_classloader_from_class(env, clazz);
        if (env->ExceptionCheck()) { return (void) 0; }
        if (classloader == nullptr) { env->FatalError(((char *)(string_pool + 47LL))); return (void) 0; }
    
        jobject lookup = nullptr;
        // try-catch-class java/io/IOException
        if (!cclasses[21] || env->IsSameObject(cclasses[21], NULL)) { cclasses_mtx[21].lock(); if (!cclasses[21] || env->IsSameObject(cclasses[21], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[38]))) { cclasses[21] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[21].unlock(); if (env->ExceptionCheck()) { return (void) 0; } }
        // try-catch-class java/lang/Throwable
        if (!cclasses[0] || env->IsSameObject(cclasses[0], NULL)) { cclasses_mtx[0].lock(); if (!cclasses[0] || env->IsSameObject(cclasses[0], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[0]))) { cclasses[0] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[0].unlock(); if (env->ExceptionCheck()) { return (void) 0; } }
        jvalue cstack0 = {}, cstack1 = {}, cstack2 = {}, cstack3 = {}, cstack4 = {}, cstack5 = {}, cstack6 = {}, cstack7 = {}, cstack8 = {}, cstack9 = {}, cstack10 = {}, cstack11 = {}, cstack12 = {}, cstack13 = {}, cstack14 = {}, cstack15 = {}, cstack16 = {}, cstack17 = {}, cstack18 = {}, cstack19 = {};
        jvalue clocal0 = {}, clocal1 = {}, clocal2 = {}, clocal3 = {}, clocal4 = {}, clocal5 = {}, clocal6 = {}, clocal7 = {}, clocal8 = {}, clocal9 = {}, clocal10 = {}, clocal11 = {}, clocal12 = {}, clocal13 = {}, clocal14 = {}, clocal15 = {}, clocal16 = {}, clocal17 = {}, clocal18 = {}, clocal19 = {}, clocal20 = {}, clocal21 = {}, clocal22 = {};
        std::unordered_set<jobject> refs;
    
        clocal0.l = arg0; refs.insert(clocal0.l);
        clocal1.l = arg1; refs.insert(clocal1.l);
        clocal2.l = arg2; refs.insert(clocal2.l);
        clocal3.i = (jint) arg3;
        clocal4.l = arg4; refs.insert(clocal4.l);
        clocal5.l = arg5; refs.insert(clocal5.l);
        clocal6.l = arg6; refs.insert(clocal6.l);
        clocal7.l = arg7; refs.insert(clocal7.l);
        clocal8.l = arg8; refs.insert(clocal8.l);
        clocal9.l = arg9; refs.insert(clocal9.l);
    
        // GETSTATIC dev/sakura/L3MonKe_h.a J; Stack: 0
        if (!cclasses[4]  || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cfields[0]) { cfields[0] = env->GetStaticFieldID((cclasses[4]), ((char *)(string_pool + 206LL)), ((char *)(string_pool + 208LL))); if (env->ExceptionCheck()) { return (void) 0; }  } cstack0.j = env->GetStaticLongField((cclasses[4]), (cfields[0])); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 2
        // LDC 7243995750626; Stack: 2
        cstack2.j = 7243995750626LL;
        // New stack: 4
        // LXOR; Stack: 4
        cstack0.j = cstack0.j ^ cstack2.j;
        // New stack: 2
        // LSTORE 10; Stack: 2
        clocal10.j = cstack0.j;
        // New stack: 0
        // LLOAD 10; Stack: 0
        cstack0.j = clocal10.j;
        // New stack: 2
        // DUP2; Stack: 2
        cstack2 = cstack0; cstack3 = cstack1;
        // New stack: 4
        // LDC 19707118392293; Stack: 4
        cstack4.j = 19707118392293LL;
        // New stack: 6
        // LXOR; Stack: 6
        cstack2.j = cstack2.j ^ cstack4.j;
        // New stack: 4
        // LSTORE 12; Stack: 4
        clocal12.j = cstack2.j;
        // New stack: 2
        // DUP2; Stack: 2
        cstack2 = cstack0; cstack3 = cstack1;
        // New stack: 4
        // LDC 63594895333373; Stack: 4
        cstack4.j = 63594895333373LL;
        // New stack: 6
        // LXOR; Stack: 6
        cstack2.j = cstack2.j ^ cstack4.j;
        // New stack: 4
        // LSTORE 14; Stack: 4
        clocal14.j = cstack2.j;
        // New stack: 2
        // DUP2; Stack: 2
        cstack2 = cstack0; cstack3 = cstack1;
        // New stack: 4
        // LDC 103878719894135; Stack: 4
        cstack4.j = 103878719894135LL;
        // New stack: 6
        // LXOR; Stack: 6
        cstack2.j = cstack2.j ^ cstack4.j;
        // New stack: 4
        // LSTORE 16; Stack: 4
        clocal16.j = cstack2.j;
        // New stack: 2
        // DUP2; Stack: 2
        cstack2 = cstack0; cstack3 = cstack1;
        // New stack: 4
        // LDC 111593278275021; Stack: 4
        cstack4.j = 111593278275021LL;
        // New stack: 6
        // LXOR; Stack: 6
        cstack2.j = cstack2.j ^ cstack4.j;
        // New stack: 4
        // LSTORE 18; Stack: 4
        clocal18.j = cstack2.j;
        // New stack: 2
        // POP2; Stack: 2
        ;
        // New stack: 0
        // LDC -6909444816438101815; Stack: 0
        cstack0.j = -6909444816438101815LL;
        // New stack: 2
        // LLOAD 10; Stack: 2
        cstack2.j = clocal10.j;
        // New stack: 4
        // LABEL L19; Stack: 4
        L19: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 4
        // ICONST_1; Stack: 4
        cstack4.i = 1;
        // New stack: 5
        // ANEWARRAY java/lang/Object; Stack: 5
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack4.i < 0) utils::throw_re(env, ((char *)(string_pool + 210LL)), ((char *)(string_pool + 247LL)), -1); else { cstack4.l = env->NewObjectArray(cstack4.i, (cclasses[5]), nullptr); refs.insert(cstack4.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 5
        // DUP; Stack: 5
        cstack5 = cstack4;
        // New stack: 6
        // LDC Ldev/sakura/L3MonKe_h;; Stack: 6
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack6.l = (cclasses[6]);
        // New stack: 7
        // SWAP; Stack: 7
        std::swap(cstack6, cstack5);
        // New stack: 7
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.7353569731942222.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 7
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack7.l = lookup;
        // New stack: 8
        // LDC Ldev/sakura/client/L3MonKe_f2;; Stack: 8
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[6]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack8.l = (cclasses[7]);
        // New stack: 9
        // LDC a; Stack: 9
        cstack9.l = (cstrings[7]);
        // New stack: 10
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;; Stack: 10
        cstack10.l = (cstrings[8]);
        // New stack: 11
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.7353569731942222.a()Ljava/lang/ClassLoader;; Stack: 11
        cstack11.l = classloader;
        // New stack: 12
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 12
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[2]) { cmethods[2] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 272LL)), ((char *)(string_pool + 299LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack10.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[2]), cstack10.l, cstack11.l); refs.insert(cstack10.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 11
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 11
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[10]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[3]) { cmethods[3] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 372LL)), ((char *)(string_pool + 383LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack7.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 481LL)), -1); else { cstack7.l = env->CallObjectMethod(cstack7.l, (cmethods[3]), cstack8.l, cstack9.l, cstack10.l); refs.insert(cstack7.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 8
        // SWAP; Stack: 8
        std::swap(cstack7, cstack6);
        // New stack: 8
        // LDC \u00d1; Stack: 8
        cstack8.l = (cstrings[11]);
        // New stack: 9
        // SWAP; Stack: 9
        std::swap(cstack8, cstack7);
        // New stack: 9
        // LDC (JJ)[Ljava/lang/String;; Stack: 9
        cstack9.l = (cstrings[17]);
        // New stack: 10
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.7353569731942222.a()Ljava/lang/ClassLoader;; Stack: 10
        cstack10.l = classloader;
        // New stack: 11
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 11
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[2]) { cmethods[2] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 272LL)), ((char *)(string_pool + 299LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack9.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[2]), cstack9.l, cstack10.l); refs.insert(cstack9.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 10
        // SWAP; Stack: 10
        std::swap(cstack9, cstack8);
        // New stack: 10
        // LDC 0; Stack: 10
        cstack10.i = 0;
        // New stack: 11
        // ANEWARRAY java/lang/Object; Stack: 11
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack10.i < 0) utils::throw_re(env, ((char *)(string_pool + 210LL)), ((char *)(string_pool + 247LL)), -1); else { cstack10.l = env->NewObjectArray(cstack10.i, (cclasses[5]), nullptr); refs.insert(cstack10.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 11
        // CHECKCAST java/lang/Object; Stack: 11
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack10.l != nullptr && !env->IsInstanceOf(cstack10.l, (cclasses[5]))) { utils::throw_re(env, ((char *)(string_pool + 109LL)), (std::string(((char *)(string_pool + 138LL))) + std::string(((char *)(string_pool + 506LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } 
        // New stack: 11
        // SWAP; Stack: 11
        std::swap(cstack10, cstack9);
        // New stack: 11
        // INVOKESTATIC native/magic/1/linkcallsite/obfuscator0.7353569731942222.a(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/invoke/MemberName;; Stack: 11
        cstack5.l = utils::link_call_site(env, cstack5.l, cstack6.l, cstack7.l, cstack8.l, cstack9.l, cstack10.l); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 6
        // POP; Stack: 6
        ;
        // New stack: 5
        // ICONST_0; Stack: 5
        cstack5.i = 0;
        // New stack: 6
        // AALOAD; Stack: 6
        if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 98LL)), -1); else { cstack4.l = env->GetObjectArrayElement((jobjectArray) cstack4.l, cstack5.i); refs.insert(cstack4.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 5
        // DUP; Stack: 5
        cstack5 = cstack4;
        // New stack: 6
        // INSTANCEOF java/lang/invoke/CallSite; Stack: 6
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack5.i = cstack5.l == nullptr ? false : env->IsInstanceOf(cstack5.l, (cclasses[10]));
        // New stack: 6
        // IFEQ L24; Stack: 6
        if (cstack5.i == 0) goto L24;
        // New stack: 5
        // INVOKEINTERFACE java/lang/invoke/CallSite.getTarget()Ljava/lang/invoke/MethodHandle;; Stack: 5
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[10]), ((char *)(string_pool + 523LL)), ((char *)(string_pool + 533LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 567LL)), -1); else { cstack4.l = env->CallObjectMethod(cstack4.l, (cmethods[4])); refs.insert(cstack4.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 5
        // LABEL L24; Stack: 5
        L24: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 5
        // FRAME FULL L: [dev/sakura/L3MonKe_L, java/util/concurrent/atomic/AtomicBoolean, dev/sakura/L3MonKe_B, 1, java/lang/String, java/lang/String, java/lang/String, java/util/Set, java/lang/String, java/lang/String, 4, 4, 4, 4, 4] S: [4, 4, java/lang/Object]; Stack: 5
        refs.erase(cstack4.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal4.l); refs.erase(clocal5.l); refs.erase(clocal6.l); refs.erase(clocal7.l); refs.erase(clocal8.l); refs.erase(clocal9.l); 
        utils::clear_refs(env, refs);
        // New stack: 5
        // CHECKCAST java/lang/invoke/MethodHandle; Stack: 5
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack4.l != nullptr && !env->IsInstanceOf(cstack4.l, (cclasses[11]))) { utils::throw_re(env, ((char *)(string_pool + 109LL)), (std::string(((char *)(string_pool + 138LL))) + std::string(((char *)(string_pool + 594LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } 
        // New stack: 5
        // GOTO L25; Stack: 5
        goto L25;
        // New stack: 5
        // LABEL L20; Stack: 5
        L20: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 5
        // FRAME SAME1 L: null S: [java/lang/Throwable]; Stack: 5
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal4.l); refs.erase(clocal5.l); refs.erase(clocal6.l); refs.erase(clocal7.l); refs.erase(clocal8.l); refs.erase(clocal9.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // INSTANCEOF java/lang/BootstrapMethodError; Stack: 2
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } cstack1.i = cstack1.l == nullptr ? false : env->IsInstanceOf(cstack1.l, (cclasses[12]));
        // New stack: 2
        // IFNE L26; Stack: 2
        if (cstack1.i != 0) goto L26;
        // New stack: 1
        // NEW java/lang/BootstrapMethodError; Stack: 1
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (jobject obj = env->AllocObject((cclasses[12]))) { cstack1.l = obj; refs.insert(obj); } 
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
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[5]) { cmethods[5] = env->GetMethodID((cclasses[12]), ((char *)(string_pool + 624LL)), ((char *)(string_pool + 631LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 656LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[12]), (cmethods[5]), cstack2.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // LABEL L26; Stack: 1
        L26: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/Object]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal4.l); refs.erase(clocal5.l); refs.erase(clocal6.l); refs.erase(clocal7.l); refs.erase(clocal8.l); refs.erase(clocal9.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 679LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // LABEL L25; Stack: 0
        L25: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // FRAME FULL L: [dev/sakura/L3MonKe_L, java/util/concurrent/atomic/AtomicBoolean, dev/sakura/L3MonKe_B, 1, java/lang/String, java/lang/String, java/lang/String, java/util/Set, java/lang/String, java/lang/String, 4, 4, 4, 4, 4] S: [4, 4, java/lang/invoke/MethodHandle]; Stack: 0
        refs.erase(cstack4.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal4.l); refs.erase(clocal5.l); refs.erase(clocal6.l); refs.erase(clocal7.l); refs.erase(clocal8.l); refs.erase(clocal9.l); 
        utils::clear_refs(env, refs);
        // New stack: 5
        // INVOKESTATIC native/magic/1/invoke/obfuscator0.7353569731942222.a(JJLjava/lang/invoke/MethodHandle;)[Ljava/lang/String;; Stack: 5
        if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { cclasses_mtx[13].lock(); if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[16]))) { cclasses[13] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[13].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[7]) { cmethods[7] = env->GetStaticMethodID((cclasses[13]), ((char *)(string_pool + 690LL)), ((char *)(string_pool + 705LL))); if (env->ExceptionCheck()) { return (void) 0; }  } cstack0.l = env->CallStaticObjectMethod((cclasses[13]), (cmethods[7]), cstack0.j, cstack2.j, cstack4.l); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // CHECKCAST [Ljava/lang/String;; Stack: 1
        if (!cclasses[14] || env->IsSameObject(cclasses[14], NULL)) { cclasses_mtx[14].lock(); if (!cclasses[14] || env->IsSameObject(cclasses[14], NULL)) { if (jclass clazz = env->FindClass(((char *)(string_pool + 759LL)))) { cclasses[14] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[14].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (cstack0.l != nullptr && !env->IsInstanceOf(cstack0.l, (cclasses[14]))) { utils::throw_re(env, ((char *)(string_pool + 109LL)), (std::string(((char *)(string_pool + 138LL))) + std::string(((char *)(string_pool + 759LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (void) 0; } } 
        // New stack: 1
        // ASTORE 20; Stack: 1
        clocal20.l = cstack0.l; refs.insert(cstack0.l);
        // New stack: 0
        // LABEL L21; Stack: 0
        L21: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 0
        // ALOAD 0; Stack: 0
        cstack0.l = clocal0.l; refs.insert(cstack0.l);
        // New stack: 1
        // LLOAD 16; Stack: 1
        cstack1.j = clocal16.j;
        // New stack: 3
        // ICONST_2; Stack: 3
        cstack3.i = 2;
        // New stack: 4
        // ANEWARRAY java/lang/Object; Stack: 4
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (cstack3.i < 0) utils::throw_re(env, ((char *)(string_pool + 210LL)), ((char *)(string_pool + 247LL)), -1); else { cstack3.l = env->NewObjectArray(cstack3.i, (cclasses[5]), nullptr); refs.insert(cstack3.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
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
        if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { cclasses_mtx[2].lock(); if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[2]))) { cclasses[2] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[2].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (!cmethods[1]) { cmethods[1] = env->GetStaticMethodID((cclasses[2]), ((char *)(string_pool + 1044LL)), ((char *)(string_pool + 1052LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }  } cstack3.l = env->CallStaticObjectMethod((cclasses[2]), (cmethods[1]), cstack3.j); refs.insert(cstack3.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 4
        // ICONST_1; Stack: 4
        cstack4.i = 1;
        // New stack: 5
        // SWAP; Stack: 5
        std::swap(cstack4, cstack3);
        // New stack: 5
        // AASTORE; Stack: 5
        if (cstack2.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 1072LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack2.l, cstack3.i, cstack4.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
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
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 1072LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack1.l, cstack2.i, cstack3.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 1
        // LDC -6882549151180923033; Stack: 1
        cstack1.j = -6882549151180923033LL;
        // New stack: 3
        // LLOAD 10; Stack: 3
        cstack3.j = clocal10.j;
        // New stack: 5
        // LABEL L17; Stack: 5
        L17: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 5
        // ICONST_1; Stack: 5
        cstack5.i = 1;
        // New stack: 6
        // ANEWARRAY java/lang/Object; Stack: 6
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } if (cstack5.i < 0) utils::throw_re(env, ((char *)(string_pool + 210LL)), ((char *)(string_pool + 247LL)), -1); else { cstack5.l = env->NewObjectArray(cstack5.i, (cclasses[5]), nullptr); refs.insert(cstack5.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 6
        // DUP; Stack: 6
        cstack6 = cstack5;
        // New stack: 7
        // LDC Ldev/sakura/L3MonKe_h;; Stack: 7
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } cstack7.l = (cclasses[6]);
        // New stack: 8
        // SWAP; Stack: 8
        std::swap(cstack7, cstack6);
        // New stack: 8
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.7353569731942222.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 8
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } cstack8.l = lookup;
        // New stack: 9
        // LDC Ldev/sakura/client/L3MonKe_f2;; Stack: 9
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[6]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } cstack9.l = (cclasses[7]);
        // New stack: 10
        // LDC a; Stack: 10
        cstack10.l = (cstrings[7]);
        // New stack: 11
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;; Stack: 11
        cstack11.l = (cstrings[8]);
        // New stack: 12
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.7353569731942222.a()Ljava/lang/ClassLoader;; Stack: 12
        cstack12.l = classloader;
        // New stack: 13
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 13
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } if (!cmethods[2]) { cmethods[2] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 272LL)), ((char *)(string_pool + 299LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }  } cstack11.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[2]), cstack11.l, cstack12.l); refs.insert(cstack11.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 12
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 12
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[10]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } if (!cmethods[3]) { cmethods[3] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 372LL)), ((char *)(string_pool + 383LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }  } if (cstack8.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 481LL)), -1); else { cstack8.l = env->CallObjectMethod(cstack8.l, (cmethods[3]), cstack9.l, cstack10.l, cstack11.l); refs.insert(cstack8.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 9
        // SWAP; Stack: 9
        std::swap(cstack8, cstack7);
        // New stack: 9
        // LDC \u00d1; Stack: 9
        cstack9.l = (cstrings[11]);
        // New stack: 10
        // SWAP; Stack: 10
        std::swap(cstack9, cstack8);
        // New stack: 10
        // LDC (Ljava/lang/Object;JJ)Ldev/sakura/L3MonKe_e;; Stack: 10
        cstack10.l = (cstrings[39]);
        // New stack: 11
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.7353569731942222.a()Ljava/lang/ClassLoader;; Stack: 11
        cstack11.l = classloader;
        // New stack: 12
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 12
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } if (!cmethods[2]) { cmethods[2] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 272LL)), ((char *)(string_pool + 299LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }  } cstack10.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[2]), cstack10.l, cstack11.l); refs.insert(cstack10.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 11
        // SWAP; Stack: 11
        std::swap(cstack10, cstack9);
        // New stack: 11
        // LDC 0; Stack: 11
        cstack11.i = 0;
        // New stack: 12
        // ANEWARRAY java/lang/Object; Stack: 12
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } if (cstack11.i < 0) utils::throw_re(env, ((char *)(string_pool + 210LL)), ((char *)(string_pool + 247LL)), -1); else { cstack11.l = env->NewObjectArray(cstack11.i, (cclasses[5]), nullptr); refs.insert(cstack11.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 12
        // CHECKCAST java/lang/Object; Stack: 12
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } if (cstack11.l != nullptr && !env->IsInstanceOf(cstack11.l, (cclasses[5]))) { utils::throw_re(env, ((char *)(string_pool + 109LL)), (std::string(((char *)(string_pool + 138LL))) + std::string(((char *)(string_pool + 506LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } 
        // New stack: 12
        // SWAP; Stack: 12
        std::swap(cstack11, cstack10);
        // New stack: 12
        // INVOKESTATIC native/magic/1/linkcallsite/obfuscator0.7353569731942222.a(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/invoke/MemberName;; Stack: 12
        cstack6.l = utils::link_call_site(env, cstack6.l, cstack7.l, cstack8.l, cstack9.l, cstack10.l, cstack11.l); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 7
        // POP; Stack: 7
        ;
        // New stack: 6
        // ICONST_0; Stack: 6
        cstack6.i = 0;
        // New stack: 7
        // AALOAD; Stack: 7
        if (cstack5.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 98LL)), -1); else { cstack5.l = env->GetObjectArrayElement((jobjectArray) cstack5.l, cstack6.i); refs.insert(cstack5.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 6
        // DUP; Stack: 6
        cstack6 = cstack5;
        // New stack: 7
        // INSTANCEOF java/lang/invoke/CallSite; Stack: 7
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } cstack6.i = cstack6.l == nullptr ? false : env->IsInstanceOf(cstack6.l, (cclasses[10]));
        // New stack: 7
        // IFEQ L27; Stack: 7
        if (cstack6.i == 0) goto L27;
        // New stack: 6
        // INVOKEINTERFACE java/lang/invoke/CallSite.getTarget()Ljava/lang/invoke/MethodHandle;; Stack: 6
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[10]), ((char *)(string_pool + 523LL)), ((char *)(string_pool + 533LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }  } if (cstack5.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 567LL)), -1); else { cstack5.l = env->CallObjectMethod(cstack5.l, (cmethods[4])); refs.insert(cstack5.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 6
        // LABEL L27; Stack: 6
        L27: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; }
        // New stack: 6
        // FRAME FULL L: [dev/sakura/L3MonKe_L, java/util/concurrent/atomic/AtomicBoolean, dev/sakura/L3MonKe_B, 1, java/lang/String, java/lang/String, java/lang/String, java/util/Set, java/lang/String, java/lang/String, 4, 4, 4, 4, 4, [Ljava/lang/String;] S: [[Ljava/lang/Object;, 4, 4, java/lang/Object]; Stack: 6
        refs.erase(cstack0.l); refs.erase(cstack5.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal4.l); refs.erase(clocal5.l); refs.erase(clocal6.l); refs.erase(clocal7.l); refs.erase(clocal8.l); refs.erase(clocal9.l); refs.erase(clocal20.l); 
        utils::clear_refs(env, refs);
        // New stack: 6
        // CHECKCAST java/lang/invoke/MethodHandle; Stack: 6
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } if (cstack5.l != nullptr && !env->IsInstanceOf(cstack5.l, (cclasses[11]))) { utils::throw_re(env, ((char *)(string_pool + 109LL)), (std::string(((char *)(string_pool + 138LL))) + std::string(((char *)(string_pool + 594LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_2; } } 
        // New stack: 6
        // GOTO L28; Stack: 6
        goto L28;
        // New stack: 6
        // LABEL L18; Stack: 6
        L18: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 6
        // FRAME SAME1 L: null S: [java/lang/Throwable]; Stack: 6
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal4.l); refs.erase(clocal5.l); refs.erase(clocal6.l); refs.erase(clocal7.l); refs.erase(clocal8.l); refs.erase(clocal9.l); refs.erase(clocal20.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // INSTANCEOF java/lang/BootstrapMethodError; Stack: 2
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } cstack1.i = cstack1.l == nullptr ? false : env->IsInstanceOf(cstack1.l, (cclasses[12]));
        // New stack: 2
        // IFNE L29; Stack: 2
        if (cstack1.i != 0) goto L29;
        // New stack: 1
        // NEW java/lang/BootstrapMethodError; Stack: 1
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (jobject obj = env->AllocObject((cclasses[12]))) { cstack1.l = obj; refs.insert(obj); } 
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
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (!cmethods[5]) { cmethods[5] = env->GetMethodID((cclasses[12]), ((char *)(string_pool + 624LL)), ((char *)(string_pool + 631LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 656LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[12]), (cmethods[5]), cstack2.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 1
        // LABEL L29; Stack: 1
        L29: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/Object]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal4.l); refs.erase(clocal5.l); refs.erase(clocal6.l); refs.erase(clocal7.l); refs.erase(clocal8.l); refs.erase(clocal9.l); refs.erase(clocal20.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 679LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 0
        // LABEL L28; Stack: 0
        L28: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 0
        // FRAME FULL L: [dev/sakura/L3MonKe_L, java/util/concurrent/atomic/AtomicBoolean, dev/sakura/L3MonKe_B, 1, java/lang/String, java/lang/String, java/lang/String, java/util/Set, java/lang/String, java/lang/String, 4, 4, 4, 4, 4, [Ljava/lang/String;] S: [[Ljava/lang/Object;, 4, 4, java/lang/invoke/MethodHandle]; Stack: 0
        refs.erase(cstack0.l); refs.erase(cstack5.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal4.l); refs.erase(clocal5.l); refs.erase(clocal6.l); refs.erase(clocal7.l); refs.erase(clocal8.l); refs.erase(clocal9.l); refs.erase(clocal20.l); 
        utils::clear_refs(env, refs);
        // New stack: 6
        // INVOKESTATIC native/magic/1/invoke/obfuscator0.7353569731942222.a(Ljava/lang/Object;JJLjava/lang/invoke/MethodHandle;)Ldev/sakura/L3MonKe_e;; Stack: 6
        if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { cclasses_mtx[13].lock(); if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[16]))) { cclasses[13] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[13].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (!cmethods[8]) { cmethods[8] = env->GetStaticMethodID((cclasses[13]), ((char *)(string_pool + 869LL)), ((char *)(string_pool + 884LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }  } cstack0.l = env->CallStaticObjectMethod((cclasses[13]), (cmethods[8]), cstack0.l, cstack1.j, cstack3.j, cstack5.l); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 1
        // CHECKCAST dev/sakura/L3MonKe_e; Stack: 1
        if (!cclasses[22] || env->IsSameObject(cclasses[22], NULL)) { cclasses_mtx[22].lock(); if (!cclasses[22] || env->IsSameObject(cclasses[22], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[40]))) { cclasses[22] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[22].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (cstack0.l != nullptr && !env->IsInstanceOf(cstack0.l, (cclasses[22]))) { utils::throw_re(env, ((char *)(string_pool + 109LL)), (std::string(((char *)(string_pool + 138LL))) + std::string(((char *)(string_pool + 5738LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } 
        // New stack: 1
        // ASTORE 21; Stack: 1
        clocal21.l = cstack0.l; refs.insert(cstack0.l);
        // New stack: 0
        // LABEL L22; Stack: 0
        L22: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // GOTO L30; Stack: 0
        goto L30;
        // New stack: 0
        // LABEL L23; Stack: 0
        L23: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // FRAME SAME1 L: null S: [java/io/IOException]; Stack: 0
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal4.l); refs.erase(clocal5.l); refs.erase(clocal6.l); refs.erase(clocal7.l); refs.erase(clocal8.l); refs.erase(clocal9.l); refs.erase(clocal20.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ASTORE 22; Stack: 1
        clocal22.l = cstack0.l; refs.insert(cstack0.l);
        // New stack: 0
        // ALOAD 1; Stack: 0
        cstack0.l = clocal1.l; refs.insert(cstack0.l);
        // New stack: 1
        // ICONST_0; Stack: 1
        cstack1.i = 0;
        // New stack: 2
        // ICONST_1; Stack: 2
        cstack2.i = 1;
        // New stack: 3
        // LDC -6900300614278671558; Stack: 3
        cstack3.j = -6900300614278671558LL;
        // New stack: 5
        // LLOAD 10; Stack: 5
        cstack5.j = clocal10.j;
        // New stack: 7
        // LABEL L15; Stack: 7
        L15: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 7
        // ICONST_1; Stack: 7
        cstack7.i = 1;
        // New stack: 8
        // ANEWARRAY java/lang/Object; Stack: 8
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } if (cstack7.i < 0) utils::throw_re(env, ((char *)(string_pool + 210LL)), ((char *)(string_pool + 247LL)), -1); else { cstack7.l = env->NewObjectArray(cstack7.i, (cclasses[5]), nullptr); refs.insert(cstack7.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 8
        // DUP; Stack: 8
        cstack8 = cstack7;
        // New stack: 9
        // LDC Ldev/sakura/L3MonKe_h;; Stack: 9
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } cstack9.l = (cclasses[6]);
        // New stack: 10
        // SWAP; Stack: 10
        std::swap(cstack9, cstack8);
        // New stack: 10
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.7353569731942222.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 10
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } cstack10.l = lookup;
        // New stack: 11
        // LDC Ldev/sakura/client/L3MonKe_f2;; Stack: 11
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[6]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } cstack11.l = (cclasses[7]);
        // New stack: 12
        // LDC a; Stack: 12
        cstack12.l = (cstrings[7]);
        // New stack: 13
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;; Stack: 13
        cstack13.l = (cstrings[8]);
        // New stack: 14
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.7353569731942222.a()Ljava/lang/ClassLoader;; Stack: 14
        cstack14.l = classloader;
        // New stack: 15
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 15
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } if (!cmethods[2]) { cmethods[2] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 272LL)), ((char *)(string_pool + 299LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }  } cstack13.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[2]), cstack13.l, cstack14.l); refs.insert(cstack13.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 14
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 14
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[10]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } if (!cmethods[3]) { cmethods[3] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 372LL)), ((char *)(string_pool + 383LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }  } if (cstack10.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 481LL)), -1); else { cstack10.l = env->CallObjectMethod(cstack10.l, (cmethods[3]), cstack11.l, cstack12.l, cstack13.l); refs.insert(cstack10.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 11
        // SWAP; Stack: 11
        std::swap(cstack10, cstack9);
        // New stack: 11
        // LDC \u00de; Stack: 11
        cstack11.l = (cstrings[36]);
        // New stack: 12
        // SWAP; Stack: 12
        std::swap(cstack11, cstack10);
        // New stack: 12
        // LDC (Ljava/lang/Object;ZZJJ)Z; Stack: 12
        cstack12.l = (cstrings[41]);
        // New stack: 13
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.7353569731942222.a()Ljava/lang/ClassLoader;; Stack: 13
        cstack13.l = classloader;
        // New stack: 14
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 14
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } if (!cmethods[2]) { cmethods[2] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 272LL)), ((char *)(string_pool + 299LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }  } cstack12.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[2]), cstack12.l, cstack13.l); refs.insert(cstack12.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 13
        // SWAP; Stack: 13
        std::swap(cstack12, cstack11);
        // New stack: 13
        // LDC 0; Stack: 13
        cstack13.i = 0;
        // New stack: 14
        // ANEWARRAY java/lang/Object; Stack: 14
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } if (cstack13.i < 0) utils::throw_re(env, ((char *)(string_pool + 210LL)), ((char *)(string_pool + 247LL)), -1); else { cstack13.l = env->NewObjectArray(cstack13.i, (cclasses[5]), nullptr); refs.insert(cstack13.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 14
        // CHECKCAST java/lang/Object; Stack: 14
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } if (cstack13.l != nullptr && !env->IsInstanceOf(cstack13.l, (cclasses[5]))) { utils::throw_re(env, ((char *)(string_pool + 109LL)), (std::string(((char *)(string_pool + 138LL))) + std::string(((char *)(string_pool + 506LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } 
        // New stack: 14
        // SWAP; Stack: 14
        std::swap(cstack13, cstack12);
        // New stack: 14
        // INVOKESTATIC native/magic/1/linkcallsite/obfuscator0.7353569731942222.a(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/invoke/MemberName;; Stack: 14
        cstack8.l = utils::link_call_site(env, cstack8.l, cstack9.l, cstack10.l, cstack11.l, cstack12.l, cstack13.l); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 9
        // POP; Stack: 9
        ;
        // New stack: 8
        // ICONST_0; Stack: 8
        cstack8.i = 0;
        // New stack: 9
        // AALOAD; Stack: 9
        if (cstack7.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 98LL)), -1); else { cstack7.l = env->GetObjectArrayElement((jobjectArray) cstack7.l, cstack8.i); refs.insert(cstack7.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 8
        // DUP; Stack: 8
        cstack8 = cstack7;
        // New stack: 9
        // INSTANCEOF java/lang/invoke/CallSite; Stack: 9
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } cstack8.i = cstack8.l == nullptr ? false : env->IsInstanceOf(cstack8.l, (cclasses[10]));
        // New stack: 9
        // IFEQ L31; Stack: 9
        if (cstack8.i == 0) goto L31;
        // New stack: 8
        // INVOKEINTERFACE java/lang/invoke/CallSite.getTarget()Ljava/lang/invoke/MethodHandle;; Stack: 8
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[10]), ((char *)(string_pool + 523LL)), ((char *)(string_pool + 533LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }  } if (cstack7.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 567LL)), -1); else { cstack7.l = env->CallObjectMethod(cstack7.l, (cmethods[4])); refs.insert(cstack7.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 8
        // LABEL L31; Stack: 8
        L31: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; }
        // New stack: 8
        // FRAME FULL L: [dev/sakura/L3MonKe_L, java/util/concurrent/atomic/AtomicBoolean, dev/sakura/L3MonKe_B, 1, java/lang/String, java/lang/String, java/lang/String, java/util/Set, java/lang/String, java/lang/String, 4, 4, 4, 4, 4, [Ljava/lang/String;, 0, java/io/IOException] S: [java/util/concurrent/atomic/AtomicBoolean, 1, 1, 4, 4, java/lang/Object]; Stack: 8
        refs.erase(cstack0.l); refs.erase(cstack7.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal4.l); refs.erase(clocal5.l); refs.erase(clocal6.l); refs.erase(clocal7.l); refs.erase(clocal8.l); refs.erase(clocal9.l); refs.erase(clocal20.l); refs.erase(clocal22.l); 
        utils::clear_refs(env, refs);
        // New stack: 8
        // CHECKCAST java/lang/invoke/MethodHandle; Stack: 8
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } if (cstack7.l != nullptr && !env->IsInstanceOf(cstack7.l, (cclasses[11]))) { utils::throw_re(env, ((char *)(string_pool + 109LL)), (std::string(((char *)(string_pool + 138LL))) + std::string(((char *)(string_pool + 594LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_3; } } 
        // New stack: 8
        // GOTO L32; Stack: 8
        goto L32;
        // New stack: 8
        // LABEL L16; Stack: 8
        L16: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 8
        // FRAME SAME1 L: null S: [java/lang/Throwable]; Stack: 8
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal4.l); refs.erase(clocal5.l); refs.erase(clocal6.l); refs.erase(clocal7.l); refs.erase(clocal8.l); refs.erase(clocal9.l); refs.erase(clocal20.l); refs.erase(clocal22.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // INSTANCEOF java/lang/BootstrapMethodError; Stack: 2
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } cstack1.i = cstack1.l == nullptr ? false : env->IsInstanceOf(cstack1.l, (cclasses[12]));
        // New stack: 2
        // IFNE L33; Stack: 2
        if (cstack1.i != 0) goto L33;
        // New stack: 1
        // NEW java/lang/BootstrapMethodError; Stack: 1
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (jobject obj = env->AllocObject((cclasses[12]))) { cstack1.l = obj; refs.insert(obj); } 
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
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[5]) { cmethods[5] = env->GetMethodID((cclasses[12]), ((char *)(string_pool + 624LL)), ((char *)(string_pool + 631LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 656LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[12]), (cmethods[5]), cstack2.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // LABEL L33; Stack: 1
        L33: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/Object]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal4.l); refs.erase(clocal5.l); refs.erase(clocal6.l); refs.erase(clocal7.l); refs.erase(clocal8.l); refs.erase(clocal9.l); refs.erase(clocal20.l); refs.erase(clocal22.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 679LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // LABEL L32; Stack: 0
        L32: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // FRAME FULL L: [dev/sakura/L3MonKe_L, java/util/concurrent/atomic/AtomicBoolean, dev/sakura/L3MonKe_B, 1, java/lang/String, java/lang/String, java/lang/String, java/util/Set, java/lang/String, java/lang/String, 4, 4, 4, 4, 4, [Ljava/lang/String;, 0, java/io/IOException] S: [java/util/concurrent/atomic/AtomicBoolean, 1, 1, 4, 4, java/lang/invoke/MethodHandle]; Stack: 0
        refs.erase(cstack0.l); refs.erase(cstack7.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal4.l); refs.erase(clocal5.l); refs.erase(clocal6.l); refs.erase(clocal7.l); refs.erase(clocal8.l); refs.erase(clocal9.l); refs.erase(clocal20.l); refs.erase(clocal22.l); 
        utils::clear_refs(env, refs);
        // New stack: 8
        // INVOKESTATIC native/magic/1/invoke/obfuscator0.7353569731942222.a(Ljava/lang/Object;ZZJJLjava/lang/invoke/MethodHandle;)Z; Stack: 8
        if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { cclasses_mtx[13].lock(); if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[16]))) { cclasses[13] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[13].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[15]) { cmethods[15] = env->GetStaticMethodID((cclasses[13]), ((char *)(string_pool + 18982LL)), ((char *)(string_pool + 18998LL))); if (env->ExceptionCheck()) { return (void) 0; }  } cstack0.i = (jint) env->CallStaticBooleanMethod((cclasses[13]), (cmethods[15]), cstack0.l, cstack1.i, cstack2.i, cstack3.j, cstack5.j, cstack7.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // IFEQ L34; Stack: 1
        if (cstack0.i == 0) goto L34;
        // New stack: 0
        // ALOAD 2; Stack: 0
        cstack0.l = clocal2.l; refs.insert(cstack0.l);
        // New stack: 1
        // LABEL L35; Stack: 1
        L35: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // NEW dev/sakura/L3MonKe_g; Stack: 1
        if (!cclasses[23] || env->IsSameObject(cclasses[23], NULL)) { cclasses_mtx[23].lock(); if (!cclasses[23] || env->IsSameObject(cclasses[23], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[42]))) { cclasses[23] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[23].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (jobject obj = env->AllocObject((cclasses[23]))) { cstack1.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 2
        // DUP; Stack: 2
        cstack2 = cstack1;
        // New stack: 3
        // ICONST_0; Stack: 3
        cstack3.i = 0;
        // New stack: 4
        // LCONST_0; Stack: 4
        cstack4.j = 0;
        // New stack: 6
        // ICONST_0; Stack: 6
        cstack6.i = 0;
        // New stack: 7
        // ALOAD 22; Stack: 7
        cstack7.l = clocal22.l; refs.insert(cstack7.l);
        // New stack: 8
        // LDC -6915954250002847529; Stack: 8
        cstack8.j = -6915954250002847529LL;
        // New stack: 10
        // LLOAD 10; Stack: 10
        cstack10.j = clocal10.j;
        // New stack: 12
        // LABEL L13; Stack: 12
        L13: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }
        // New stack: 12
        // ICONST_1; Stack: 12
        cstack12.i = 1;
        // New stack: 13
        // ANEWARRAY java/lang/Object; Stack: 13
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } if (cstack12.i < 0) utils::throw_re(env, ((char *)(string_pool + 210LL)), ((char *)(string_pool + 247LL)), -1); else { cstack12.l = env->NewObjectArray(cstack12.i, (cclasses[5]), nullptr); refs.insert(cstack12.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }
        // New stack: 13
        // DUP; Stack: 13
        cstack13 = cstack12;
        // New stack: 14
        // LDC Ldev/sakura/L3MonKe_h;; Stack: 14
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } cstack14.l = (cclasses[6]);
        // New stack: 15
        // SWAP; Stack: 15
        std::swap(cstack14, cstack13);
        // New stack: 15
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.7353569731942222.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 15
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } cstack15.l = lookup;
        // New stack: 16
        // LDC Ldev/sakura/client/L3MonKe_f2;; Stack: 16
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[6]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } cstack16.l = (cclasses[7]);
        // New stack: 17
        // LDC a; Stack: 17
        cstack17.l = (cstrings[7]);
        // New stack: 18
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;; Stack: 18
        cstack18.l = (cstrings[8]);
        // New stack: 19
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.7353569731942222.a()Ljava/lang/ClassLoader;; Stack: 19
        cstack19.l = classloader;
        // New stack: 20
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 20
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } if (!cmethods[2]) { cmethods[2] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 272LL)), ((char *)(string_pool + 299LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }  } cstack18.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[2]), cstack18.l, cstack19.l); refs.insert(cstack18.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }
        // New stack: 19
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 19
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[10]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } if (!cmethods[3]) { cmethods[3] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 372LL)), ((char *)(string_pool + 383LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }  } if (cstack15.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 481LL)), -1); else { cstack15.l = env->CallObjectMethod(cstack15.l, (cmethods[3]), cstack16.l, cstack17.l, cstack18.l); refs.insert(cstack15.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }
        // New stack: 16
        // SWAP; Stack: 16
        std::swap(cstack15, cstack14);
        // New stack: 16
        // LDC \u00de; Stack: 16
        cstack16.l = (cstrings[36]);
        // New stack: 17
        // SWAP; Stack: 17
        std::swap(cstack16, cstack15);
        // New stack: 17
        // LDC (Ljava/lang/Object;JJ)Ljava/lang/String;; Stack: 17
        cstack17.l = (cstrings[18]);
        // New stack: 18
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.7353569731942222.a()Ljava/lang/ClassLoader;; Stack: 18
        cstack18.l = classloader;
        // New stack: 19
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 19
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } if (!cmethods[2]) { cmethods[2] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 272LL)), ((char *)(string_pool + 299LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }  } cstack17.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[2]), cstack17.l, cstack18.l); refs.insert(cstack17.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }
        // New stack: 18
        // SWAP; Stack: 18
        std::swap(cstack17, cstack16);
        // New stack: 18
        // LDC 0; Stack: 18
        cstack18.i = 0;
        // New stack: 19
        // ANEWARRAY java/lang/Object; Stack: 19
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } if (cstack18.i < 0) utils::throw_re(env, ((char *)(string_pool + 210LL)), ((char *)(string_pool + 247LL)), -1); else { cstack18.l = env->NewObjectArray(cstack18.i, (cclasses[5]), nullptr); refs.insert(cstack18.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }
        // New stack: 19
        // CHECKCAST java/lang/Object; Stack: 19
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } if (cstack18.l != nullptr && !env->IsInstanceOf(cstack18.l, (cclasses[5]))) { utils::throw_re(env, ((char *)(string_pool + 109LL)), (std::string(((char *)(string_pool + 138LL))) + std::string(((char *)(string_pool + 506LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } 
        // New stack: 19
        // SWAP; Stack: 19
        std::swap(cstack18, cstack17);
        // New stack: 19
        // INVOKESTATIC native/magic/1/linkcallsite/obfuscator0.7353569731942222.a(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/invoke/MemberName;; Stack: 19
        cstack13.l = utils::link_call_site(env, cstack13.l, cstack14.l, cstack15.l, cstack16.l, cstack17.l, cstack18.l); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }
        // New stack: 14
        // POP; Stack: 14
        ;
        // New stack: 13
        // ICONST_0; Stack: 13
        cstack13.i = 0;
        // New stack: 14
        // AALOAD; Stack: 14
        if (cstack12.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 98LL)), -1); else { cstack12.l = env->GetObjectArrayElement((jobjectArray) cstack12.l, cstack13.i); refs.insert(cstack12.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }
        // New stack: 13
        // DUP; Stack: 13
        cstack13 = cstack12;
        // New stack: 14
        // INSTANCEOF java/lang/invoke/CallSite; Stack: 14
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } cstack13.i = cstack13.l == nullptr ? false : env->IsInstanceOf(cstack13.l, (cclasses[10]));
        // New stack: 14
        // IFEQ L36; Stack: 14
        if (cstack13.i == 0) goto L36;
        // New stack: 13
        // INVOKEINTERFACE java/lang/invoke/CallSite.getTarget()Ljava/lang/invoke/MethodHandle;; Stack: 13
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[10]), ((char *)(string_pool + 523LL)), ((char *)(string_pool + 533LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }  } if (cstack12.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 567LL)), -1); else { cstack12.l = env->CallObjectMethod(cstack12.l, (cmethods[4])); refs.insert(cstack12.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }
        // New stack: 13
        // LABEL L36; Stack: 13
        L36: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; }
        // New stack: 13
        // FRAME FULL L: [dev/sakura/L3MonKe_L, java/util/concurrent/atomic/AtomicBoolean, dev/sakura/L3MonKe_B, 1, java/lang/String, java/lang/String, java/lang/String, java/util/Set, java/lang/String, java/lang/String, 4, 4, 4, 4, 4, [Ljava/lang/String;, 0, java/io/IOException] S: [dev/sakura/L3MonKe_B, org.objectweb.asm.tree.LabelNode@654b72c0, org.objectweb.asm.tree.LabelNode@654b72c0, 1, 4, 1, java/io/IOException, 4, 4, java/lang/Object]; Stack: 13
        refs.erase(cstack0.l); refs.erase(cstack1.l); refs.erase(cstack2.l); refs.erase(cstack7.l); refs.erase(cstack12.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal4.l); refs.erase(clocal5.l); refs.erase(clocal6.l); refs.erase(clocal7.l); refs.erase(clocal8.l); refs.erase(clocal9.l); refs.erase(clocal20.l); refs.erase(clocal22.l); 
        utils::clear_refs(env, refs);
        // New stack: 13
        // CHECKCAST java/lang/invoke/MethodHandle; Stack: 13
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } if (cstack12.l != nullptr && !env->IsInstanceOf(cstack12.l, (cclasses[11]))) { utils::throw_re(env, ((char *)(string_pool + 109LL)), (std::string(((char *)(string_pool + 138LL))) + std::string(((char *)(string_pool + 594LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_4; } } 
        // New stack: 13
        // GOTO L37; Stack: 13
        goto L37;
        // New stack: 13
        // LABEL L14; Stack: 13
        L14: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 13
        // FRAME SAME1 L: null S: [java/lang/Throwable]; Stack: 13
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal4.l); refs.erase(clocal5.l); refs.erase(clocal6.l); refs.erase(clocal7.l); refs.erase(clocal8.l); refs.erase(clocal9.l); refs.erase(clocal20.l); refs.erase(clocal22.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // INSTANCEOF java/lang/BootstrapMethodError; Stack: 2
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } cstack1.i = cstack1.l == nullptr ? false : env->IsInstanceOf(cstack1.l, (cclasses[12]));
        // New stack: 2
        // IFNE L38; Stack: 2
        if (cstack1.i != 0) goto L38;
        // New stack: 1
        // NEW java/lang/BootstrapMethodError; Stack: 1
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (jobject obj = env->AllocObject((cclasses[12]))) { cstack1.l = obj; refs.insert(obj); } 
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
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[5]) { cmethods[5] = env->GetMethodID((cclasses[12]), ((char *)(string_pool + 624LL)), ((char *)(string_pool + 631LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 656LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[12]), (cmethods[5]), cstack2.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // LABEL L38; Stack: 1
        L38: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/Object]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal4.l); refs.erase(clocal5.l); refs.erase(clocal6.l); refs.erase(clocal7.l); refs.erase(clocal8.l); refs.erase(clocal9.l); refs.erase(clocal20.l); refs.erase(clocal22.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 679LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // LABEL L37; Stack: 0
        L37: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // FRAME FULL L: [dev/sakura/L3MonKe_L, java/util/concurrent/atomic/AtomicBoolean, dev/sakura/L3MonKe_B, 1, java/lang/String, java/lang/String, java/lang/String, java/util/Set, java/lang/String, java/lang/String, 4, 4, 4, 4, 4, [Ljava/lang/String;, 0, java/io/IOException] S: [dev/sakura/L3MonKe_B, org.objectweb.asm.tree.LabelNode@654b72c0, org.objectweb.asm.tree.LabelNode@654b72c0, 1, 4, 1, java/io/IOException, 4, 4, java/lang/invoke/MethodHandle]; Stack: 0
        refs.erase(cstack0.l); refs.erase(cstack1.l); refs.erase(cstack2.l); refs.erase(cstack7.l); refs.erase(cstack12.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal4.l); refs.erase(clocal5.l); refs.erase(clocal6.l); refs.erase(clocal7.l); refs.erase(clocal8.l); refs.erase(clocal9.l); refs.erase(clocal20.l); refs.erase(clocal22.l); 
        utils::clear_refs(env, refs);
        // New stack: 13
        // INVOKESTATIC native/magic/1/invoke/obfuscator0.7353569731942222.a(Ljava/lang/Object;JJLjava/lang/invoke/MethodHandle;)Ljava/lang/String;; Stack: 13
        if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { cclasses_mtx[13].lock(); if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[16]))) { cclasses[13] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[13].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[8]) { cmethods[8] = env->GetStaticMethodID((cclasses[13]), ((char *)(string_pool + 869LL)), ((char *)(string_pool + 884LL))); if (env->ExceptionCheck()) { return (void) 0; }  } cstack7.l = env->CallStaticObjectMethod((cclasses[13]), (cmethods[8]), cstack7.l, cstack8.j, cstack10.j, cstack12.l); refs.insert(cstack7.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 8
        // CHECKCAST java/lang/String; Stack: 8
        if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { cclasses_mtx[1].lock(); if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[1]))) { cclasses[1] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[1].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (cstack7.l != nullptr && !env->IsInstanceOf(cstack7.l, (cclasses[1]))) { utils::throw_re(env, ((char *)(string_pool + 109LL)), (std::string(((char *)(string_pool + 138LL))) + std::string(((char *)(string_pool + 852LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (void) 0; } } 
        // New stack: 8
        // IFNONNULL L39; Stack: 8
        if (!env->IsSameObject(cstack7.l, nullptr)) goto L39;
        // New stack: 7
        // LDC ; Stack: 7
        cstack7.l = (cstrings[21]);
        // New stack: 8
        // GOTO L40; Stack: 8
        goto L40;
        // New stack: 8
        // LABEL L39; Stack: 8
        L39: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 8
        // FRAME FULL L: [dev/sakura/L3MonKe_L, java/util/concurrent/atomic/AtomicBoolean, dev/sakura/L3MonKe_B, 1, java/lang/String, java/lang/String, java/lang/String, java/util/Set, java/lang/String, java/lang/String, 4, 4, 4, 4, 4, [Ljava/lang/String;, 0, java/io/IOException] S: [dev/sakura/L3MonKe_B, org.objectweb.asm.tree.LabelNode@654b72c0, org.objectweb.asm.tree.LabelNode@654b72c0, 1, 4, 1]; Stack: 8
        refs.erase(cstack0.l); refs.erase(cstack1.l); refs.erase(cstack2.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal4.l); refs.erase(clocal5.l); refs.erase(clocal6.l); refs.erase(clocal7.l); refs.erase(clocal8.l); refs.erase(clocal9.l); refs.erase(clocal20.l); refs.erase(clocal22.l); 
        utils::clear_refs(env, refs);
        // New stack: 7
        // ALOAD 22; Stack: 7
        cstack7.l = clocal22.l; refs.insert(cstack7.l);
        // New stack: 8
        // LDC -6915954250002847529; Stack: 8
        cstack8.j = -6915954250002847529LL;
        // New stack: 10
        // LLOAD 10; Stack: 10
        cstack10.j = clocal10.j;
        // New stack: 12
        // LABEL L11; Stack: 12
        L11: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }
        // New stack: 12
        // ICONST_1; Stack: 12
        cstack12.i = 1;
        // New stack: 13
        // ANEWARRAY java/lang/Object; Stack: 13
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } if (cstack12.i < 0) utils::throw_re(env, ((char *)(string_pool + 210LL)), ((char *)(string_pool + 247LL)), -1); else { cstack12.l = env->NewObjectArray(cstack12.i, (cclasses[5]), nullptr); refs.insert(cstack12.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }
        // New stack: 13
        // DUP; Stack: 13
        cstack13 = cstack12;
        // New stack: 14
        // LDC Ldev/sakura/L3MonKe_h;; Stack: 14
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } cstack14.l = (cclasses[6]);
        // New stack: 15
        // SWAP; Stack: 15
        std::swap(cstack14, cstack13);
        // New stack: 15
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.7353569731942222.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 15
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } cstack15.l = lookup;
        // New stack: 16
        // LDC Ldev/sakura/client/L3MonKe_f2;; Stack: 16
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[6]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } cstack16.l = (cclasses[7]);
        // New stack: 17
        // LDC a; Stack: 17
        cstack17.l = (cstrings[7]);
        // New stack: 18
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;; Stack: 18
        cstack18.l = (cstrings[8]);
        // New stack: 19
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.7353569731942222.a()Ljava/lang/ClassLoader;; Stack: 19
        cstack19.l = classloader;
        // New stack: 20
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 20
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } if (!cmethods[2]) { cmethods[2] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 272LL)), ((char *)(string_pool + 299LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }  } cstack18.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[2]), cstack18.l, cstack19.l); refs.insert(cstack18.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }
        // New stack: 19
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 19
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[10]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } if (!cmethods[3]) { cmethods[3] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 372LL)), ((char *)(string_pool + 383LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }  } if (cstack15.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 481LL)), -1); else { cstack15.l = env->CallObjectMethod(cstack15.l, (cmethods[3]), cstack16.l, cstack17.l, cstack18.l); refs.insert(cstack15.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }
        // New stack: 16
        // SWAP; Stack: 16
        std::swap(cstack15, cstack14);
        // New stack: 16
        // LDC \u00de; Stack: 16
        cstack16.l = (cstrings[36]);
        // New stack: 17
        // SWAP; Stack: 17
        std::swap(cstack16, cstack15);
        // New stack: 17
        // LDC (Ljava/lang/Object;JJ)Ljava/lang/String;; Stack: 17
        cstack17.l = (cstrings[18]);
        // New stack: 18
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.7353569731942222.a()Ljava/lang/ClassLoader;; Stack: 18
        cstack18.l = classloader;
        // New stack: 19
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 19
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } if (!cmethods[2]) { cmethods[2] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 272LL)), ((char *)(string_pool + 299LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }  } cstack17.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[2]), cstack17.l, cstack18.l); refs.insert(cstack17.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }
        // New stack: 18
        // SWAP; Stack: 18
        std::swap(cstack17, cstack16);
        // New stack: 18
        // LDC 0; Stack: 18
        cstack18.i = 0;
        // New stack: 19
        // ANEWARRAY java/lang/Object; Stack: 19
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } if (cstack18.i < 0) utils::throw_re(env, ((char *)(string_pool + 210LL)), ((char *)(string_pool + 247LL)), -1); else { cstack18.l = env->NewObjectArray(cstack18.i, (cclasses[5]), nullptr); refs.insert(cstack18.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }
        // New stack: 19
        // CHECKCAST java/lang/Object; Stack: 19
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } if (cstack18.l != nullptr && !env->IsInstanceOf(cstack18.l, (cclasses[5]))) { utils::throw_re(env, ((char *)(string_pool + 109LL)), (std::string(((char *)(string_pool + 138LL))) + std::string(((char *)(string_pool + 506LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } 
        // New stack: 19
        // SWAP; Stack: 19
        std::swap(cstack18, cstack17);
        // New stack: 19
        // INVOKESTATIC native/magic/1/linkcallsite/obfuscator0.7353569731942222.a(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/invoke/MemberName;; Stack: 19
        cstack13.l = utils::link_call_site(env, cstack13.l, cstack14.l, cstack15.l, cstack16.l, cstack17.l, cstack18.l); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }
        // New stack: 14
        // POP; Stack: 14
        ;
        // New stack: 13
        // ICONST_0; Stack: 13
        cstack13.i = 0;
        // New stack: 14
        // AALOAD; Stack: 14
        if (cstack12.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 98LL)), -1); else { cstack12.l = env->GetObjectArrayElement((jobjectArray) cstack12.l, cstack13.i); refs.insert(cstack12.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }
        // New stack: 13
        // DUP; Stack: 13
        cstack13 = cstack12;
        // New stack: 14
        // INSTANCEOF java/lang/invoke/CallSite; Stack: 14
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } cstack13.i = cstack13.l == nullptr ? false : env->IsInstanceOf(cstack13.l, (cclasses[10]));
        // New stack: 14
        // IFEQ L41; Stack: 14
        if (cstack13.i == 0) goto L41;
        // New stack: 13
        // INVOKEINTERFACE java/lang/invoke/CallSite.getTarget()Ljava/lang/invoke/MethodHandle;; Stack: 13
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[10]), ((char *)(string_pool + 523LL)), ((char *)(string_pool + 533LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }  } if (cstack12.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 567LL)), -1); else { cstack12.l = env->CallObjectMethod(cstack12.l, (cmethods[4])); refs.insert(cstack12.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }
        // New stack: 13
        // LABEL L41; Stack: 13
        L41: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; }
        // New stack: 13
        // FRAME FULL L: [dev/sakura/L3MonKe_L, java/util/concurrent/atomic/AtomicBoolean, dev/sakura/L3MonKe_B, 1, java/lang/String, java/lang/String, java/lang/String, java/util/Set, java/lang/String, java/lang/String, 4, 4, 4, 4, 4, [Ljava/lang/String;, 0, java/io/IOException] S: [dev/sakura/L3MonKe_B, org.objectweb.asm.tree.LabelNode@654b72c0, org.objectweb.asm.tree.LabelNode@654b72c0, 1, 4, 1, java/io/IOException, 4, 4, java/lang/Object]; Stack: 13
        refs.erase(cstack0.l); refs.erase(cstack1.l); refs.erase(cstack2.l); refs.erase(cstack7.l); refs.erase(cstack12.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal4.l); refs.erase(clocal5.l); refs.erase(clocal6.l); refs.erase(clocal7.l); refs.erase(clocal8.l); refs.erase(clocal9.l); refs.erase(clocal20.l); refs.erase(clocal22.l); 
        utils::clear_refs(env, refs);
        // New stack: 13
        // CHECKCAST java/lang/invoke/MethodHandle; Stack: 13
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } if (cstack12.l != nullptr && !env->IsInstanceOf(cstack12.l, (cclasses[11]))) { utils::throw_re(env, ((char *)(string_pool + 109LL)), (std::string(((char *)(string_pool + 138LL))) + std::string(((char *)(string_pool + 594LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_5; } } 
        // New stack: 13
        // GOTO L42; Stack: 13
        goto L42;
        // New stack: 13
        // LABEL L12; Stack: 13
        L12: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 13
        // FRAME SAME1 L: null S: [java/lang/Throwable]; Stack: 13
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal4.l); refs.erase(clocal5.l); refs.erase(clocal6.l); refs.erase(clocal7.l); refs.erase(clocal8.l); refs.erase(clocal9.l); refs.erase(clocal20.l); refs.erase(clocal22.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // INSTANCEOF java/lang/BootstrapMethodError; Stack: 2
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } cstack1.i = cstack1.l == nullptr ? false : env->IsInstanceOf(cstack1.l, (cclasses[12]));
        // New stack: 2
        // IFNE L43; Stack: 2
        if (cstack1.i != 0) goto L43;
        // New stack: 1
        // NEW java/lang/BootstrapMethodError; Stack: 1
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (jobject obj = env->AllocObject((cclasses[12]))) { cstack1.l = obj; refs.insert(obj); } 
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
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[5]) { cmethods[5] = env->GetMethodID((cclasses[12]), ((char *)(string_pool + 624LL)), ((char *)(string_pool + 631LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 656LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[12]), (cmethods[5]), cstack2.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // LABEL L43; Stack: 1
        L43: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/Object]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal4.l); refs.erase(clocal5.l); refs.erase(clocal6.l); refs.erase(clocal7.l); refs.erase(clocal8.l); refs.erase(clocal9.l); refs.erase(clocal20.l); refs.erase(clocal22.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 679LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // LABEL L42; Stack: 0
        L42: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // FRAME FULL L: [dev/sakura/L3MonKe_L, java/util/concurrent/atomic/AtomicBoolean, dev/sakura/L3MonKe_B, 1, java/lang/String, java/lang/String, java/lang/String, java/util/Set, java/lang/String, java/lang/String, 4, 4, 4, 4, 4, [Ljava/lang/String;, 0, java/io/IOException] S: [dev/sakura/L3MonKe_B, org.objectweb.asm.tree.LabelNode@654b72c0, org.objectweb.asm.tree.LabelNode@654b72c0, 1, 4, 1, java/io/IOException, 4, 4, java/lang/invoke/MethodHandle]; Stack: 0
        refs.erase(cstack0.l); refs.erase(cstack1.l); refs.erase(cstack2.l); refs.erase(cstack7.l); refs.erase(cstack12.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal4.l); refs.erase(clocal5.l); refs.erase(clocal6.l); refs.erase(clocal7.l); refs.erase(clocal8.l); refs.erase(clocal9.l); refs.erase(clocal20.l); refs.erase(clocal22.l); 
        utils::clear_refs(env, refs);
        // New stack: 13
        // INVOKESTATIC native/magic/1/invoke/obfuscator0.7353569731942222.a(Ljava/lang/Object;JJLjava/lang/invoke/MethodHandle;)Ljava/lang/String;; Stack: 13
        if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { cclasses_mtx[13].lock(); if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[16]))) { cclasses[13] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[13].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[8]) { cmethods[8] = env->GetStaticMethodID((cclasses[13]), ((char *)(string_pool + 869LL)), ((char *)(string_pool + 884LL))); if (env->ExceptionCheck()) { return (void) 0; }  } cstack7.l = env->CallStaticObjectMethod((cclasses[13]), (cmethods[8]), cstack7.l, cstack8.j, cstack10.j, cstack12.l); refs.insert(cstack7.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 8
        // CHECKCAST java/lang/String; Stack: 8
        if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { cclasses_mtx[1].lock(); if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[1]))) { cclasses[1] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[1].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (cstack7.l != nullptr && !env->IsInstanceOf(cstack7.l, (cclasses[1]))) { utils::throw_re(env, ((char *)(string_pool + 109LL)), (std::string(((char *)(string_pool + 138LL))) + std::string(((char *)(string_pool + 852LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (void) 0; } } 
        // New stack: 8
        // LABEL L40; Stack: 8
        L40: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 8
        // FRAME FULL L: [dev/sakura/L3MonKe_L, java/util/concurrent/atomic/AtomicBoolean, dev/sakura/L3MonKe_B, 1, java/lang/String, java/lang/String, java/lang/String, java/util/Set, java/lang/String, java/lang/String, 4, 4, 4, 4, 4, [Ljava/lang/String;, 0, java/io/IOException] S: [dev/sakura/L3MonKe_B, org.objectweb.asm.tree.LabelNode@654b72c0, org.objectweb.asm.tree.LabelNode@654b72c0, 1, 4, 1, java/lang/String]; Stack: 8
        refs.erase(cstack0.l); refs.erase(cstack1.l); refs.erase(cstack2.l); refs.erase(cstack7.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal4.l); refs.erase(clocal5.l); refs.erase(clocal6.l); refs.erase(clocal7.l); refs.erase(clocal8.l); refs.erase(clocal9.l); refs.erase(clocal20.l); refs.erase(clocal22.l); 
        utils::clear_refs(env, refs);
        // New stack: 8
        // SIPUSH 22747; Stack: 8
        cstack8.i = (jint) 22747;
        // New stack: 9
        // LDC 5663424459834487459; Stack: 9
        cstack9.j = 5663424459834487459LL;
        // New stack: 11
        // LLOAD 10; Stack: 11
        cstack11.j = clocal10.j;
        // New stack: 13
        // LXOR; Stack: 13
        cstack9.j = cstack9.j ^ cstack11.j;
        // New stack: 11
        // LABEL L9; Stack: 11
        L9: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; }
        // New stack: 11
        // ICONST_1; Stack: 11
        cstack11.i = 1;
        // New stack: 12
        // ANEWARRAY java/lang/Object; Stack: 12
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; } } if (cstack11.i < 0) utils::throw_re(env, ((char *)(string_pool + 210LL)), ((char *)(string_pool + 247LL)), -1); else { cstack11.l = env->NewObjectArray(cstack11.i, (cclasses[5]), nullptr); refs.insert(cstack11.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; }
        // New stack: 12
        // DUP; Stack: 12
        cstack12 = cstack11;
        // New stack: 13
        // LDC Ldev/sakura/L3MonKe_h;; Stack: 13
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; } } cstack13.l = (cclasses[6]);
        // New stack: 14
        // SWAP; Stack: 14
        std::swap(cstack13, cstack12);
        // New stack: 14
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.7353569731942222.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 14
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; } } cstack14.l = lookup;
        // New stack: 15
        // LDC Ldev/sakura/L3MonKe_h;; Stack: 15
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; } } cstack15.l = (cclasses[6]);
        // New stack: 16
        // LDC a; Stack: 16
        cstack16.l = (cstrings[7]);
        // New stack: 17
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;; Stack: 17
        cstack17.l = (cstrings[8]);
        // New stack: 18
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.7353569731942222.a()Ljava/lang/ClassLoader;; Stack: 18
        cstack18.l = classloader;
        // New stack: 19
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 19
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; } } if (!cmethods[2]) { cmethods[2] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 272LL)), ((char *)(string_pool + 299LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; }  } cstack17.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[2]), cstack17.l, cstack18.l); refs.insert(cstack17.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; }
        // New stack: 18
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 18
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[10]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; } } if (!cmethods[3]) { cmethods[3] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 372LL)), ((char *)(string_pool + 383LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; }  } if (cstack14.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 481LL)), -1); else { cstack14.l = env->CallObjectMethod(cstack14.l, (cmethods[3]), cstack15.l, cstack16.l, cstack17.l); refs.insert(cstack14.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; }
        // New stack: 15
        // SWAP; Stack: 15
        std::swap(cstack14, cstack13);
        // New stack: 15
        // LDC m; Stack: 15
        cstack15.l = (cstrings[34]);
        // New stack: 16
        // SWAP; Stack: 16
        std::swap(cstack15, cstack14);
        // New stack: 16
        // LDC (IJ)Ljava/lang/String;; Stack: 16
        cstack16.l = (cstrings[35]);
        // New stack: 17
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.7353569731942222.a()Ljava/lang/ClassLoader;; Stack: 17
        cstack17.l = classloader;
        // New stack: 18
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 18
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; } } if (!cmethods[2]) { cmethods[2] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 272LL)), ((char *)(string_pool + 299LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; }  } cstack16.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[2]), cstack16.l, cstack17.l); refs.insert(cstack16.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; }
        // New stack: 17
        // SWAP; Stack: 17
        std::swap(cstack16, cstack15);
        // New stack: 17
        // LDC 0; Stack: 17
        cstack17.i = 0;
        // New stack: 18
        // ANEWARRAY java/lang/Object; Stack: 18
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; } } if (cstack17.i < 0) utils::throw_re(env, ((char *)(string_pool + 210LL)), ((char *)(string_pool + 247LL)), -1); else { cstack17.l = env->NewObjectArray(cstack17.i, (cclasses[5]), nullptr); refs.insert(cstack17.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; }
        // New stack: 18
        // CHECKCAST java/lang/Object; Stack: 18
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; } } if (cstack17.l != nullptr && !env->IsInstanceOf(cstack17.l, (cclasses[5]))) { utils::throw_re(env, ((char *)(string_pool + 109LL)), (std::string(((char *)(string_pool + 138LL))) + std::string(((char *)(string_pool + 506LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; } } 
        // New stack: 18
        // SWAP; Stack: 18
        std::swap(cstack17, cstack16);
        // New stack: 18
        // INVOKESTATIC native/magic/1/linkcallsite/obfuscator0.7353569731942222.a(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/invoke/MemberName;; Stack: 18
        cstack12.l = utils::link_call_site(env, cstack12.l, cstack13.l, cstack14.l, cstack15.l, cstack16.l, cstack17.l); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; }
        // New stack: 13
        // POP; Stack: 13
        ;
        // New stack: 12
        // ICONST_0; Stack: 12
        cstack12.i = 0;
        // New stack: 13
        // AALOAD; Stack: 13
        if (cstack11.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 98LL)), -1); else { cstack11.l = env->GetObjectArrayElement((jobjectArray) cstack11.l, cstack12.i); refs.insert(cstack11.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; }
        // New stack: 12
        // DUP; Stack: 12
        cstack12 = cstack11;
        // New stack: 13
        // INSTANCEOF java/lang/invoke/CallSite; Stack: 13
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; } } cstack12.i = cstack12.l == nullptr ? false : env->IsInstanceOf(cstack12.l, (cclasses[10]));
        // New stack: 13
        // IFEQ L44; Stack: 13
        if (cstack12.i == 0) goto L44;
        // New stack: 12
        // INVOKEINTERFACE java/lang/invoke/CallSite.getTarget()Ljava/lang/invoke/MethodHandle;; Stack: 12
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[10]), ((char *)(string_pool + 523LL)), ((char *)(string_pool + 533LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; }  } if (cstack11.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 567LL)), -1); else { cstack11.l = env->CallObjectMethod(cstack11.l, (cmethods[4])); refs.insert(cstack11.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; }
        // New stack: 12
        // LABEL L44; Stack: 12
        L44: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; }
        // New stack: 12
        // FRAME FULL L: [dev/sakura/L3MonKe_L, java/util/concurrent/atomic/AtomicBoolean, dev/sakura/L3MonKe_B, 1, java/lang/String, java/lang/String, java/lang/String, java/util/Set, java/lang/String, java/lang/String, 4, 4, 4, 4, 4, [Ljava/lang/String;, 0, java/io/IOException] S: [dev/sakura/L3MonKe_B, org.objectweb.asm.tree.LabelNode@654b72c0, org.objectweb.asm.tree.LabelNode@654b72c0, 1, 4, 1, java/lang/String, 1, 4, java/lang/Object]; Stack: 12
        refs.erase(cstack0.l); refs.erase(cstack1.l); refs.erase(cstack2.l); refs.erase(cstack7.l); refs.erase(cstack11.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal4.l); refs.erase(clocal5.l); refs.erase(clocal6.l); refs.erase(clocal7.l); refs.erase(clocal8.l); refs.erase(clocal9.l); refs.erase(clocal20.l); refs.erase(clocal22.l); 
        utils::clear_refs(env, refs);
        // New stack: 12
        // CHECKCAST java/lang/invoke/MethodHandle; Stack: 12
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; } } if (cstack11.l != nullptr && !env->IsInstanceOf(cstack11.l, (cclasses[11]))) { utils::throw_re(env, ((char *)(string_pool + 109LL)), (std::string(((char *)(string_pool + 138LL))) + std::string(((char *)(string_pool + 594LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_6; } } 
        // New stack: 12
        // GOTO L45; Stack: 12
        goto L45;
        // New stack: 12
        // LABEL L10; Stack: 12
        L10: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 12
        // FRAME SAME1 L: null S: [java/lang/Throwable]; Stack: 12
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal4.l); refs.erase(clocal5.l); refs.erase(clocal6.l); refs.erase(clocal7.l); refs.erase(clocal8.l); refs.erase(clocal9.l); refs.erase(clocal20.l); refs.erase(clocal22.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // INSTANCEOF java/lang/BootstrapMethodError; Stack: 2
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } cstack1.i = cstack1.l == nullptr ? false : env->IsInstanceOf(cstack1.l, (cclasses[12]));
        // New stack: 2
        // IFNE L46; Stack: 2
        if (cstack1.i != 0) goto L46;
        // New stack: 1
        // NEW java/lang/BootstrapMethodError; Stack: 1
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (jobject obj = env->AllocObject((cclasses[12]))) { cstack1.l = obj; refs.insert(obj); } 
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
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[5]) { cmethods[5] = env->GetMethodID((cclasses[12]), ((char *)(string_pool + 624LL)), ((char *)(string_pool + 631LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 656LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[12]), (cmethods[5]), cstack2.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // LABEL L46; Stack: 1
        L46: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/Object]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal4.l); refs.erase(clocal5.l); refs.erase(clocal6.l); refs.erase(clocal7.l); refs.erase(clocal8.l); refs.erase(clocal9.l); refs.erase(clocal20.l); refs.erase(clocal22.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 679LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // LABEL L45; Stack: 0
        L45: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // FRAME FULL L: [dev/sakura/L3MonKe_L, java/util/concurrent/atomic/AtomicBoolean, dev/sakura/L3MonKe_B, 1, java/lang/String, java/lang/String, java/lang/String, java/util/Set, java/lang/String, java/lang/String, 4, 4, 4, 4, 4, [Ljava/lang/String;, 0, java/io/IOException] S: [dev/sakura/L3MonKe_B, org.objectweb.asm.tree.LabelNode@654b72c0, org.objectweb.asm.tree.LabelNode@654b72c0, 1, 4, 1, java/lang/String, 1, 4, java/lang/invoke/MethodHandle]; Stack: 0
        refs.erase(cstack0.l); refs.erase(cstack1.l); refs.erase(cstack2.l); refs.erase(cstack7.l); refs.erase(cstack11.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal4.l); refs.erase(clocal5.l); refs.erase(clocal6.l); refs.erase(clocal7.l); refs.erase(clocal8.l); refs.erase(clocal9.l); refs.erase(clocal20.l); refs.erase(clocal22.l); 
        utils::clear_refs(env, refs);
        // New stack: 12
        // INVOKESTATIC native/magic/1/invoke/obfuscator0.7353569731942222.a(IJLjava/lang/invoke/MethodHandle;)Ljava/lang/String;; Stack: 12
        if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { cclasses_mtx[13].lock(); if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[16]))) { cclasses[13] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[13].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[12]) { cmethods[12] = env->GetStaticMethodID((cclasses[13]), ((char *)(string_pool + 783LL)), ((char *)(string_pool + 798LL))); if (env->ExceptionCheck()) { return (void) 0; }  } cstack8.l = env->CallStaticObjectMethod((cclasses[13]), (cmethods[12]), cstack8.i, cstack9.j, cstack11.l); refs.insert(cstack8.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 9
        // CHECKCAST java/lang/String; Stack: 9
        if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { cclasses_mtx[1].lock(); if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[1]))) { cclasses[1] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[1].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (cstack8.l != nullptr && !env->IsInstanceOf(cstack8.l, (cclasses[1]))) { utils::throw_re(env, ((char *)(string_pool + 109LL)), (std::string(((char *)(string_pool + 138LL))) + std::string(((char *)(string_pool + 852LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (void) 0; } } 
        // New stack: 9
        // SWAP; Stack: 9
        std::swap(cstack8, cstack7);
        // New stack: 9
        // LABEL L7; Stack: 9
        L7: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; }
        // New stack: 9
        // ICONST_1; Stack: 9
        cstack9.i = 1;
        // New stack: 10
        // ANEWARRAY java/lang/Object; Stack: 10
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; } } if (cstack9.i < 0) utils::throw_re(env, ((char *)(string_pool + 210LL)), ((char *)(string_pool + 247LL)), -1); else { cstack9.l = env->NewObjectArray(cstack9.i, (cclasses[5]), nullptr); refs.insert(cstack9.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; }
        // New stack: 10
        // DUP; Stack: 10
        cstack10 = cstack9;
        // New stack: 11
        // LDC Ldev/sakura/L3MonKe_h;; Stack: 11
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; } } cstack11.l = (cclasses[6]);
        // New stack: 12
        // SWAP; Stack: 12
        std::swap(cstack11, cstack10);
        // New stack: 12
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.7353569731942222.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 12
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; } } cstack12.l = lookup;
        // New stack: 13
        // LDC Ljava/lang/invoke/StringConcatFactory;; Stack: 13
        if (!cclasses[24] || env->IsSameObject(cclasses[24], NULL)) { cclasses_mtx[24].lock(); if (!cclasses[24] || env->IsSameObject(cclasses[24], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[43]))) { cclasses[24] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[24].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; } } cstack13.l = (cclasses[24]);
        // New stack: 14
        // LDC makeConcatWithConstants; Stack: 14
        cstack14.l = (cstrings[44]);
        // New stack: 15
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/invoke/CallSite;; Stack: 15
        cstack15.l = (cstrings[45]);
        // New stack: 16
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.7353569731942222.a()Ljava/lang/ClassLoader;; Stack: 16
        cstack16.l = classloader;
        // New stack: 17
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 17
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; } } if (!cmethods[2]) { cmethods[2] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 272LL)), ((char *)(string_pool + 299LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; }  } cstack15.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[2]), cstack15.l, cstack16.l); refs.insert(cstack15.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; }
        // New stack: 16
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 16
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[10]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; } } if (!cmethods[3]) { cmethods[3] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 372LL)), ((char *)(string_pool + 383LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; }  } if (cstack12.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 481LL)), -1); else { cstack12.l = env->CallObjectMethod(cstack12.l, (cmethods[3]), cstack13.l, cstack14.l, cstack15.l); refs.insert(cstack12.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; }
        // New stack: 13
        // SWAP; Stack: 13
        std::swap(cstack12, cstack11);
        // New stack: 13
        // LDC makeConcatWithConstants; Stack: 13
        cstack13.l = (cstrings[44]);
        // New stack: 14
        // SWAP; Stack: 14
        std::swap(cstack13, cstack12);
        // New stack: 14
        // LDC (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;; Stack: 14
        cstack14.l = (cstrings[46]);
        // New stack: 15
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.7353569731942222.a()Ljava/lang/ClassLoader;; Stack: 15
        cstack15.l = classloader;
        // New stack: 16
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 16
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; } } if (!cmethods[2]) { cmethods[2] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 272LL)), ((char *)(string_pool + 299LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; }  } cstack14.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[2]), cstack14.l, cstack15.l); refs.insert(cstack14.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; }
        // New stack: 15
        // SWAP; Stack: 15
        std::swap(cstack14, cstack13);
        // New stack: 15
        // LDC 1; Stack: 15
        cstack15.i = 1;
        // New stack: 16
        // ANEWARRAY java/lang/Object; Stack: 16
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; } } if (cstack15.i < 0) utils::throw_re(env, ((char *)(string_pool + 210LL)), ((char *)(string_pool + 247LL)), -1); else { cstack15.l = env->NewObjectArray(cstack15.i, (cclasses[5]), nullptr); refs.insert(cstack15.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; }
        // New stack: 16
        // DUP; Stack: 16
        cstack16 = cstack15;
        // New stack: 17
        // LDC 0; Stack: 17
        cstack17.i = 0;
        // New stack: 18
        // LDC \u0001\u0001; Stack: 18
        cstack18.l = (cstrings[47]);
        // New stack: 19
        // AASTORE; Stack: 19
        if (cstack16.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 1072LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack16.l, cstack17.i, cstack18.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; }
        // New stack: 16
        // CHECKCAST java/lang/Object; Stack: 16
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; } } if (cstack15.l != nullptr && !env->IsInstanceOf(cstack15.l, (cclasses[5]))) { utils::throw_re(env, ((char *)(string_pool + 109LL)), (std::string(((char *)(string_pool + 138LL))) + std::string(((char *)(string_pool + 506LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; } } 
        // New stack: 16
        // SWAP; Stack: 16
        std::swap(cstack15, cstack14);
        // New stack: 16
        // INVOKESTATIC native/magic/1/linkcallsite/obfuscator0.7353569731942222.a(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/invoke/MemberName;; Stack: 16
        cstack10.l = utils::link_call_site(env, cstack10.l, cstack11.l, cstack12.l, cstack13.l, cstack14.l, cstack15.l); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; }
        // New stack: 11
        // POP; Stack: 11
        ;
        // New stack: 10
        // ICONST_0; Stack: 10
        cstack10.i = 0;
        // New stack: 11
        // AALOAD; Stack: 11
        if (cstack9.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 98LL)), -1); else { cstack9.l = env->GetObjectArrayElement((jobjectArray) cstack9.l, cstack10.i); refs.insert(cstack9.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; }
        // New stack: 10
        // DUP; Stack: 10
        cstack10 = cstack9;
        // New stack: 11
        // INSTANCEOF java/lang/invoke/CallSite; Stack: 11
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; } } cstack10.i = cstack10.l == nullptr ? false : env->IsInstanceOf(cstack10.l, (cclasses[10]));
        // New stack: 11
        // IFEQ L47; Stack: 11
        if (cstack10.i == 0) goto L47;
        // New stack: 10
        // INVOKEINTERFACE java/lang/invoke/CallSite.getTarget()Ljava/lang/invoke/MethodHandle;; Stack: 10
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[10]), ((char *)(string_pool + 523LL)), ((char *)(string_pool + 533LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; }  } if (cstack9.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 567LL)), -1); else { cstack9.l = env->CallObjectMethod(cstack9.l, (cmethods[4])); refs.insert(cstack9.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; }
        // New stack: 10
        // LABEL L47; Stack: 10
        L47: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; }
        // New stack: 10
        // FRAME FULL L: [dev/sakura/L3MonKe_L, java/util/concurrent/atomic/AtomicBoolean, dev/sakura/L3MonKe_B, 1, java/lang/String, java/lang/String, java/lang/String, java/util/Set, java/lang/String, java/lang/String, 4, 4, 4, 4, 4, [Ljava/lang/String;, 0, java/io/IOException] S: [dev/sakura/L3MonKe_B, org.objectweb.asm.tree.LabelNode@654b72c0, org.objectweb.asm.tree.LabelNode@654b72c0, 1, 4, 1, java/lang/String, java/lang/String, java/lang/Object]; Stack: 10
        refs.erase(cstack0.l); refs.erase(cstack1.l); refs.erase(cstack2.l); refs.erase(cstack7.l); refs.erase(cstack8.l); refs.erase(cstack9.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal4.l); refs.erase(clocal5.l); refs.erase(clocal6.l); refs.erase(clocal7.l); refs.erase(clocal8.l); refs.erase(clocal9.l); refs.erase(clocal20.l); refs.erase(clocal22.l); 
        utils::clear_refs(env, refs);
        // New stack: 10
        // CHECKCAST java/lang/invoke/MethodHandle; Stack: 10
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; } } if (cstack9.l != nullptr && !env->IsInstanceOf(cstack9.l, (cclasses[11]))) { utils::throw_re(env, ((char *)(string_pool + 109LL)), (std::string(((char *)(string_pool + 138LL))) + std::string(((char *)(string_pool + 594LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_7; } } 
        // New stack: 10
        // GOTO L48; Stack: 10
        goto L48;
        // New stack: 10
        // LABEL L8; Stack: 10
        L8: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 10
        // FRAME SAME1 L: null S: [java/lang/Throwable]; Stack: 10
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal4.l); refs.erase(clocal5.l); refs.erase(clocal6.l); refs.erase(clocal7.l); refs.erase(clocal8.l); refs.erase(clocal9.l); refs.erase(clocal20.l); refs.erase(clocal22.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // INSTANCEOF java/lang/BootstrapMethodError; Stack: 2
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } cstack1.i = cstack1.l == nullptr ? false : env->IsInstanceOf(cstack1.l, (cclasses[12]));
        // New stack: 2
        // IFNE L49; Stack: 2
        if (cstack1.i != 0) goto L49;
        // New stack: 1
        // NEW java/lang/BootstrapMethodError; Stack: 1
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (jobject obj = env->AllocObject((cclasses[12]))) { cstack1.l = obj; refs.insert(obj); } 
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
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[5]) { cmethods[5] = env->GetMethodID((cclasses[12]), ((char *)(string_pool + 624LL)), ((char *)(string_pool + 631LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 656LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[12]), (cmethods[5]), cstack2.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // LABEL L49; Stack: 1
        L49: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/Object]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal4.l); refs.erase(clocal5.l); refs.erase(clocal6.l); refs.erase(clocal7.l); refs.erase(clocal8.l); refs.erase(clocal9.l); refs.erase(clocal20.l); refs.erase(clocal22.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 679LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // LABEL L48; Stack: 0
        L48: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // FRAME FULL L: [dev/sakura/L3MonKe_L, java/util/concurrent/atomic/AtomicBoolean, dev/sakura/L3MonKe_B, 1, java/lang/String, java/lang/String, java/lang/String, java/util/Set, java/lang/String, java/lang/String, 4, 4, 4, 4, 4, [Ljava/lang/String;, 0, java/io/IOException] S: [dev/sakura/L3MonKe_B, org.objectweb.asm.tree.LabelNode@654b72c0, org.objectweb.asm.tree.LabelNode@654b72c0, 1, 4, 1, java/lang/String, java/lang/String, java/lang/invoke/MethodHandle]; Stack: 0
        refs.erase(cstack0.l); refs.erase(cstack1.l); refs.erase(cstack2.l); refs.erase(cstack7.l); refs.erase(cstack8.l); refs.erase(cstack9.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal4.l); refs.erase(clocal5.l); refs.erase(clocal6.l); refs.erase(clocal7.l); refs.erase(clocal8.l); refs.erase(clocal9.l); refs.erase(clocal20.l); refs.erase(clocal22.l); 
        utils::clear_refs(env, refs);
        // New stack: 10
        // INVOKESTATIC native/magic/1/invoke/obfuscator0.7353569731942222.a(Ljava/lang/String;Ljava/lang/String;Ljava/lang/invoke/MethodHandle;)Ljava/lang/String;; Stack: 10
        if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { cclasses_mtx[13].lock(); if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[16]))) { cclasses[13] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[13].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[16]) { cmethods[16] = env->GetStaticMethodID((cclasses[13]), ((char *)(string_pool + 19055LL)), ((char *)(string_pool + 19071LL))); if (env->ExceptionCheck()) { return (void) 0; }  } cstack7.l = env->CallStaticObjectMethod((cclasses[13]), (cmethods[16]), cstack7.l, cstack8.l, cstack9.l); refs.insert(cstack7.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 8
        // CHECKCAST java/lang/String; Stack: 8
        if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { cclasses_mtx[1].lock(); if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[1]))) { cclasses[1] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[1].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (cstack7.l != nullptr && !env->IsInstanceOf(cstack7.l, (cclasses[1]))) { utils::throw_re(env, ((char *)(string_pool + 109LL)), (std::string(((char *)(string_pool + 138LL))) + std::string(((char *)(string_pool + 852LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (void) 0; } } 
        // New stack: 8
        // INVOKESPECIAL dev/sakura/L3MonKe_g.<init>(ZJZLjava/lang/String;)V; Stack: 8
        if (!cclasses[23] || env->IsSameObject(cclasses[23], NULL)) { cclasses_mtx[23].lock(); if (!cclasses[23] || env->IsSameObject(cclasses[23], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[42]))) { cclasses[23] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[23].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[17]) { cmethods[17] = env->GetMethodID((cclasses[23]), ((char *)(string_pool + 624LL)), ((char *)(string_pool + 19159LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack2.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 656LL)), -1); else env->CallNonvirtualVoidMethod(cstack2.l, (cclasses[23]), (cmethods[17]), cstack3.i, cstack4.j, cstack6.i, cstack7.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 2
        // LLOAD 12; Stack: 2
        cstack2.j = clocal12.j;
        // New stack: 4
        // DUP2_X1; Stack: 4
        cstack4 = cstack2; cstack5 = cstack3; cstack3 = cstack1; cstack2 = cstack5; cstack1 = cstack4;
        // New stack: 6
        // POP2; Stack: 6
        ;
        // New stack: 4
        // ICONST_2; Stack: 4
        cstack4.i = 2;
        // New stack: 5
        // ANEWARRAY java/lang/Object; Stack: 5
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (cstack4.i < 0) utils::throw_re(env, ((char *)(string_pool + 210LL)), ((char *)(string_pool + 247LL)), -1); else { cstack4.l = env->NewObjectArray(cstack4.i, (cclasses[5]), nullptr); refs.insert(cstack4.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 5
        // DUP_X1; Stack: 5
        cstack5 = cstack4; cstack4 = cstack3; cstack3 = cstack5;
        // New stack: 6
        // SWAP; Stack: 6
        std::swap(cstack5, cstack4);
        // New stack: 6
        // ICONST_1; Stack: 6
        cstack6.i = 1;
        // New stack: 7
        // SWAP; Stack: 7
        std::swap(cstack6, cstack5);
        // New stack: 7
        // AASTORE; Stack: 7
        if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 1072LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack4.l, cstack5.i, cstack6.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
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
        if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { cclasses_mtx[2].lock(); if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[2]))) { cclasses[2] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[2].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[1]) { cmethods[1] = env->GetStaticMethodID((cclasses[2]), ((char *)(string_pool + 1044LL)), ((char *)(string_pool + 1052LL))); if (env->ExceptionCheck()) { return (void) 0; }  } cstack3.l = env->CallStaticObjectMethod((cclasses[2]), (cmethods[1]), cstack3.j); refs.insert(cstack3.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 4
        // ICONST_0; Stack: 4
        cstack4.i = 0;
        // New stack: 5
        // SWAP; Stack: 5
        std::swap(cstack4, cstack3);
        // New stack: 5
        // AASTORE; Stack: 5
        if (cstack2.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 1072LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack2.l, cstack3.i, cstack4.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 2
        // LDC -6908846570346013755; Stack: 2
        cstack2.j = -6908846570346013755LL;
        // New stack: 4
        // LLOAD 10; Stack: 4
        cstack4.j = clocal10.j;
        // New stack: 6
        // LABEL L5; Stack: 6
        L5: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; }
        // New stack: 6
        // ICONST_1; Stack: 6
        cstack6.i = 1;
        // New stack: 7
        // ANEWARRAY java/lang/Object; Stack: 7
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; } } if (cstack6.i < 0) utils::throw_re(env, ((char *)(string_pool + 210LL)), ((char *)(string_pool + 247LL)), -1); else { cstack6.l = env->NewObjectArray(cstack6.i, (cclasses[5]), nullptr); refs.insert(cstack6.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; }
        // New stack: 7
        // DUP; Stack: 7
        cstack7 = cstack6;
        // New stack: 8
        // LDC Ldev/sakura/L3MonKe_h;; Stack: 8
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; } } cstack8.l = (cclasses[6]);
        // New stack: 9
        // SWAP; Stack: 9
        std::swap(cstack8, cstack7);
        // New stack: 9
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.7353569731942222.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 9
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; } } cstack9.l = lookup;
        // New stack: 10
        // LDC Ldev/sakura/client/L3MonKe_f2;; Stack: 10
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[6]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; } } cstack10.l = (cclasses[7]);
        // New stack: 11
        // LDC a; Stack: 11
        cstack11.l = (cstrings[7]);
        // New stack: 12
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;; Stack: 12
        cstack12.l = (cstrings[8]);
        // New stack: 13
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.7353569731942222.a()Ljava/lang/ClassLoader;; Stack: 13
        cstack13.l = classloader;
        // New stack: 14
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 14
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; } } if (!cmethods[2]) { cmethods[2] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 272LL)), ((char *)(string_pool + 299LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; }  } cstack12.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[2]), cstack12.l, cstack13.l); refs.insert(cstack12.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; }
        // New stack: 13
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 13
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[10]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; } } if (!cmethods[3]) { cmethods[3] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 372LL)), ((char *)(string_pool + 383LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; }  } if (cstack9.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 481LL)), -1); else { cstack9.l = env->CallObjectMethod(cstack9.l, (cmethods[3]), cstack10.l, cstack11.l, cstack12.l); refs.insert(cstack9.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; }
        // New stack: 10
        // SWAP; Stack: 10
        std::swap(cstack9, cstack8);
        // New stack: 10
        // LDC \u00de; Stack: 10
        cstack10.l = (cstrings[36]);
        // New stack: 11
        // SWAP; Stack: 11
        std::swap(cstack10, cstack9);
        // New stack: 11
        // LDC (Ljava/lang/Object;Ljava/lang/Object;JJ)V; Stack: 11
        cstack11.l = (cstrings[48]);
        // New stack: 12
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.7353569731942222.a()Ljava/lang/ClassLoader;; Stack: 12
        cstack12.l = classloader;
        // New stack: 13
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 13
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; } } if (!cmethods[2]) { cmethods[2] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 272LL)), ((char *)(string_pool + 299LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; }  } cstack11.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[2]), cstack11.l, cstack12.l); refs.insert(cstack11.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; }
        // New stack: 12
        // SWAP; Stack: 12
        std::swap(cstack11, cstack10);
        // New stack: 12
        // LDC 0; Stack: 12
        cstack12.i = 0;
        // New stack: 13
        // ANEWARRAY java/lang/Object; Stack: 13
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; } } if (cstack12.i < 0) utils::throw_re(env, ((char *)(string_pool + 210LL)), ((char *)(string_pool + 247LL)), -1); else { cstack12.l = env->NewObjectArray(cstack12.i, (cclasses[5]), nullptr); refs.insert(cstack12.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; }
        // New stack: 13
        // CHECKCAST java/lang/Object; Stack: 13
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; } } if (cstack12.l != nullptr && !env->IsInstanceOf(cstack12.l, (cclasses[5]))) { utils::throw_re(env, ((char *)(string_pool + 109LL)), (std::string(((char *)(string_pool + 138LL))) + std::string(((char *)(string_pool + 506LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; } } 
        // New stack: 13
        // SWAP; Stack: 13
        std::swap(cstack12, cstack11);
        // New stack: 13
        // INVOKESTATIC native/magic/1/linkcallsite/obfuscator0.7353569731942222.a(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/invoke/MemberName;; Stack: 13
        cstack7.l = utils::link_call_site(env, cstack7.l, cstack8.l, cstack9.l, cstack10.l, cstack11.l, cstack12.l); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; }
        // New stack: 8
        // POP; Stack: 8
        ;
        // New stack: 7
        // ICONST_0; Stack: 7
        cstack7.i = 0;
        // New stack: 8
        // AALOAD; Stack: 8
        if (cstack6.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 98LL)), -1); else { cstack6.l = env->GetObjectArrayElement((jobjectArray) cstack6.l, cstack7.i); refs.insert(cstack6.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; }
        // New stack: 7
        // DUP; Stack: 7
        cstack7 = cstack6;
        // New stack: 8
        // INSTANCEOF java/lang/invoke/CallSite; Stack: 8
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; } } cstack7.i = cstack7.l == nullptr ? false : env->IsInstanceOf(cstack7.l, (cclasses[10]));
        // New stack: 8
        // IFEQ L50; Stack: 8
        if (cstack7.i == 0) goto L50;
        // New stack: 7
        // INVOKEINTERFACE java/lang/invoke/CallSite.getTarget()Ljava/lang/invoke/MethodHandle;; Stack: 7
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[10]), ((char *)(string_pool + 523LL)), ((char *)(string_pool + 533LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; }  } if (cstack6.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 567LL)), -1); else { cstack6.l = env->CallObjectMethod(cstack6.l, (cmethods[4])); refs.insert(cstack6.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; }
        // New stack: 7
        // LABEL L50; Stack: 7
        L50: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; }
        // New stack: 7
        // FRAME FULL L: [dev/sakura/L3MonKe_L, java/util/concurrent/atomic/AtomicBoolean, dev/sakura/L3MonKe_B, 1, java/lang/String, java/lang/String, java/lang/String, java/util/Set, java/lang/String, java/lang/String, 4, 4, 4, 4, 4, [Ljava/lang/String;, 0, java/io/IOException] S: [dev/sakura/L3MonKe_B, [Ljava/lang/Object;, 4, 4, java/lang/Object]; Stack: 7
        refs.erase(cstack0.l); refs.erase(cstack1.l); refs.erase(cstack6.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal4.l); refs.erase(clocal5.l); refs.erase(clocal6.l); refs.erase(clocal7.l); refs.erase(clocal8.l); refs.erase(clocal9.l); refs.erase(clocal20.l); refs.erase(clocal22.l); 
        utils::clear_refs(env, refs);
        // New stack: 7
        // CHECKCAST java/lang/invoke/MethodHandle; Stack: 7
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; } } if (cstack6.l != nullptr && !env->IsInstanceOf(cstack6.l, (cclasses[11]))) { utils::throw_re(env, ((char *)(string_pool + 109LL)), (std::string(((char *)(string_pool + 138LL))) + std::string(((char *)(string_pool + 594LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_8; } } 
        // New stack: 7
        // GOTO L51; Stack: 7
        goto L51;
        // New stack: 7
        // LABEL L6; Stack: 7
        L6: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 7
        // FRAME SAME1 L: null S: [java/lang/Throwable]; Stack: 7
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal4.l); refs.erase(clocal5.l); refs.erase(clocal6.l); refs.erase(clocal7.l); refs.erase(clocal8.l); refs.erase(clocal9.l); refs.erase(clocal20.l); refs.erase(clocal22.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // INSTANCEOF java/lang/BootstrapMethodError; Stack: 2
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } cstack1.i = cstack1.l == nullptr ? false : env->IsInstanceOf(cstack1.l, (cclasses[12]));
        // New stack: 2
        // IFNE L52; Stack: 2
        if (cstack1.i != 0) goto L52;
        // New stack: 1
        // NEW java/lang/BootstrapMethodError; Stack: 1
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (jobject obj = env->AllocObject((cclasses[12]))) { cstack1.l = obj; refs.insert(obj); } 
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
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[5]) { cmethods[5] = env->GetMethodID((cclasses[12]), ((char *)(string_pool + 624LL)), ((char *)(string_pool + 631LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 656LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[12]), (cmethods[5]), cstack2.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // LABEL L52; Stack: 1
        L52: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/Object]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal4.l); refs.erase(clocal5.l); refs.erase(clocal6.l); refs.erase(clocal7.l); refs.erase(clocal8.l); refs.erase(clocal9.l); refs.erase(clocal20.l); refs.erase(clocal22.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 679LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // LABEL L51; Stack: 0
        L51: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // FRAME FULL L: [dev/sakura/L3MonKe_L, java/util/concurrent/atomic/AtomicBoolean, dev/sakura/L3MonKe_B, 1, java/lang/String, java/lang/String, java/lang/String, java/util/Set, java/lang/String, java/lang/String, 4, 4, 4, 4, 4, [Ljava/lang/String;, 0, java/io/IOException] S: [dev/sakura/L3MonKe_B, [Ljava/lang/Object;, 4, 4, java/lang/invoke/MethodHandle]; Stack: 0
        refs.erase(cstack0.l); refs.erase(cstack1.l); refs.erase(cstack6.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal4.l); refs.erase(clocal5.l); refs.erase(clocal6.l); refs.erase(clocal7.l); refs.erase(clocal8.l); refs.erase(clocal9.l); refs.erase(clocal20.l); refs.erase(clocal22.l); 
        utils::clear_refs(env, refs);
        // New stack: 7
        // INVOKESTATIC native/magic/1/invoke/obfuscator0.7353569731942222.a(Ljava/lang/Object;Ljava/lang/Object;JJLjava/lang/invoke/MethodHandle;)V; Stack: 7
        if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { cclasses_mtx[13].lock(); if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[16]))) { cclasses[13] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[13].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[18]) { cmethods[18] = env->GetStaticMethodID((cclasses[13]), ((char *)(string_pool + 7319LL)), ((char *)(string_pool + 7335LL))); if (env->ExceptionCheck()) { return (void) 0; }  } env->CallStaticVoidMethod((cclasses[13]), (cmethods[18]), cstack0.l, cstack1.l, cstack2.j, cstack4.j, cstack6.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // LABEL L34; Stack: 0
        L34: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // FRAME SAME L: null S: null; Stack: 0
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal4.l); refs.erase(clocal5.l); refs.erase(clocal6.l); refs.erase(clocal7.l); refs.erase(clocal8.l); refs.erase(clocal9.l); refs.erase(clocal20.l); refs.erase(clocal22.l); 
        utils::clear_refs(env, refs);
        // New stack: 0
        // RETURN; Stack: 0
        return;
        // New stack: 0
        // LABEL L30; Stack: 0
        L30: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // FRAME FULL L: [dev/sakura/L3MonKe_L, java/util/concurrent/atomic/AtomicBoolean, dev/sakura/L3MonKe_B, 1, java/lang/String, java/lang/String, java/lang/String, java/util/Set, java/lang/String, java/lang/String, 4, 4, 4, 4, 4, [Ljava/lang/String;, dev/sakura/L3MonKe_e] S: []; Stack: 0
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal4.l); refs.erase(clocal5.l); refs.erase(clocal6.l); refs.erase(clocal7.l); refs.erase(clocal8.l); refs.erase(clocal9.l); refs.erase(clocal20.l); refs.erase(clocal21.l); 
        utils::clear_refs(env, refs);
        // New stack: 0
        // ILOAD 3; Stack: 0
        cstack0.i = clocal3.i;
        // New stack: 1
        // IFEQ L53; Stack: 1
        if (cstack0.i == 0) goto L53;
        // New stack: 0
        // ALOAD 21; Stack: 0
        cstack0.l = clocal21.l; refs.insert(cstack0.l);
        // New stack: 1
        // ALOAD 4; Stack: 1
        cstack1.l = clocal4.l; refs.insert(cstack1.l);
        // New stack: 2
        // ALOAD 5; Stack: 2
        cstack2.l = clocal5.l; refs.insert(cstack2.l);
        // New stack: 3
        // LLOAD 18; Stack: 3
        cstack3.j = clocal18.j;
        // New stack: 5
        // ALOAD 6; Stack: 5
        cstack5.l = clocal6.l; refs.insert(cstack5.l);
        // New stack: 6
        // ALOAD 7; Stack: 6
        cstack6.l = clocal7.l; refs.insert(cstack6.l);
        // New stack: 7
        // ALOAD 8; Stack: 7
        cstack7.l = clocal8.l; refs.insert(cstack7.l);
        // New stack: 8
        // ALOAD 9; Stack: 8
        cstack8.l = clocal9.l; refs.insert(cstack8.l);
        // New stack: 9
        // BIPUSH 7; Stack: 9
        cstack9.i = (jint) 7;
        // New stack: 10
        // ANEWARRAY java/lang/Object; Stack: 10
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (cstack9.i < 0) utils::throw_re(env, ((char *)(string_pool + 210LL)), ((char *)(string_pool + 247LL)), -1); else { cstack9.l = env->NewObjectArray(cstack9.i, (cclasses[5]), nullptr); refs.insert(cstack9.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 10
        // DUP_X1; Stack: 10
        cstack10 = cstack9; cstack9 = cstack8; cstack8 = cstack10;
        // New stack: 11
        // SWAP; Stack: 11
        std::swap(cstack10, cstack9);
        // New stack: 11
        // BIPUSH 6; Stack: 11
        cstack11.i = (jint) 6;
        // New stack: 12
        // SWAP; Stack: 12
        std::swap(cstack11, cstack10);
        // New stack: 12
        // AASTORE; Stack: 12
        if (cstack9.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 1072LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack9.l, cstack10.i, cstack11.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 9
        // DUP_X1; Stack: 9
        cstack9 = cstack8; cstack8 = cstack7; cstack7 = cstack9;
        // New stack: 10
        // SWAP; Stack: 10
        std::swap(cstack9, cstack8);
        // New stack: 10
        // ICONST_5; Stack: 10
        cstack10.i = 5;
        // New stack: 11
        // SWAP; Stack: 11
        std::swap(cstack10, cstack9);
        // New stack: 11
        // AASTORE; Stack: 11
        if (cstack8.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 1072LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack8.l, cstack9.i, cstack10.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 8
        // DUP_X1; Stack: 8
        cstack8 = cstack7; cstack7 = cstack6; cstack6 = cstack8;
        // New stack: 9
        // SWAP; Stack: 9
        std::swap(cstack8, cstack7);
        // New stack: 9
        // ICONST_4; Stack: 9
        cstack9.i = 4;
        // New stack: 10
        // SWAP; Stack: 10
        std::swap(cstack9, cstack8);
        // New stack: 10
        // AASTORE; Stack: 10
        if (cstack7.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 1072LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack7.l, cstack8.i, cstack9.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 7
        // DUP_X1; Stack: 7
        cstack7 = cstack6; cstack6 = cstack5; cstack5 = cstack7;
        // New stack: 8
        // SWAP; Stack: 8
        std::swap(cstack7, cstack6);
        // New stack: 8
        // ICONST_3; Stack: 8
        cstack8.i = 3;
        // New stack: 9
        // SWAP; Stack: 9
        std::swap(cstack8, cstack7);
        // New stack: 9
        // AASTORE; Stack: 9
        if (cstack6.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 1072LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack6.l, cstack7.i, cstack8.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
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
        if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { cclasses_mtx[2].lock(); if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[2]))) { cclasses[2] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[2].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[1]) { cmethods[1] = env->GetStaticMethodID((cclasses[2]), ((char *)(string_pool + 1044LL)), ((char *)(string_pool + 1052LL))); if (env->ExceptionCheck()) { return (void) 0; }  } cstack5.l = env->CallStaticObjectMethod((cclasses[2]), (cmethods[1]), cstack5.j); refs.insert(cstack5.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 6
        // ICONST_2; Stack: 6
        cstack6.i = 2;
        // New stack: 7
        // SWAP; Stack: 7
        std::swap(cstack6, cstack5);
        // New stack: 7
        // AASTORE; Stack: 7
        if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 1072LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack4.l, cstack5.i, cstack6.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
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
        if (cstack3.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 1072LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack3.l, cstack4.i, cstack5.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 3
        // DUP_X1; Stack: 3
        cstack3 = cstack2; cstack2 = cstack1; cstack1 = cstack3;
        // New stack: 4
        // SWAP; Stack: 4
        std::swap(cstack3, cstack2);
        // New stack: 4
        // ICONST_0; Stack: 4
        cstack4.i = 0;
        // New stack: 5
        // SWAP; Stack: 5
        std::swap(cstack4, cstack3);
        // New stack: 5
        // AASTORE; Stack: 5
        if (cstack2.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 1072LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack2.l, cstack3.i, cstack4.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 2
        // LDC -6788967130574927852; Stack: 2
        cstack2.j = -6788967130574927852LL;
        // New stack: 4
        // LLOAD 10; Stack: 4
        cstack4.j = clocal10.j;
        // New stack: 6
        // LABEL L3; Stack: 6
        L3: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; }
        // New stack: 6
        // ICONST_1; Stack: 6
        cstack6.i = 1;
        // New stack: 7
        // ANEWARRAY java/lang/Object; Stack: 7
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; } } if (cstack6.i < 0) utils::throw_re(env, ((char *)(string_pool + 210LL)), ((char *)(string_pool + 247LL)), -1); else { cstack6.l = env->NewObjectArray(cstack6.i, (cclasses[5]), nullptr); refs.insert(cstack6.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; }
        // New stack: 7
        // DUP; Stack: 7
        cstack7 = cstack6;
        // New stack: 8
        // LDC Ldev/sakura/L3MonKe_h;; Stack: 8
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; } } cstack8.l = (cclasses[6]);
        // New stack: 9
        // SWAP; Stack: 9
        std::swap(cstack8, cstack7);
        // New stack: 9
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.7353569731942222.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 9
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; } } cstack9.l = lookup;
        // New stack: 10
        // LDC Ldev/sakura/client/L3MonKe_f2;; Stack: 10
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[6]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; } } cstack10.l = (cclasses[7]);
        // New stack: 11
        // LDC a; Stack: 11
        cstack11.l = (cstrings[7]);
        // New stack: 12
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;; Stack: 12
        cstack12.l = (cstrings[8]);
        // New stack: 13
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.7353569731942222.a()Ljava/lang/ClassLoader;; Stack: 13
        cstack13.l = classloader;
        // New stack: 14
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 14
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; } } if (!cmethods[2]) { cmethods[2] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 272LL)), ((char *)(string_pool + 299LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; }  } cstack12.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[2]), cstack12.l, cstack13.l); refs.insert(cstack12.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; }
        // New stack: 13
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 13
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[10]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; } } if (!cmethods[3]) { cmethods[3] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 372LL)), ((char *)(string_pool + 383LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; }  } if (cstack9.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 481LL)), -1); else { cstack9.l = env->CallObjectMethod(cstack9.l, (cmethods[3]), cstack10.l, cstack11.l, cstack12.l); refs.insert(cstack9.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; }
        // New stack: 10
        // SWAP; Stack: 10
        std::swap(cstack9, cstack8);
        // New stack: 10
        // LDC \u00de; Stack: 10
        cstack10.l = (cstrings[36]);
        // New stack: 11
        // SWAP; Stack: 11
        std::swap(cstack10, cstack9);
        // New stack: 11
        // LDC (Ljava/lang/Object;Ljava/lang/Object;JJ)V; Stack: 11
        cstack11.l = (cstrings[48]);
        // New stack: 12
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.7353569731942222.a()Ljava/lang/ClassLoader;; Stack: 12
        cstack12.l = classloader;
        // New stack: 13
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 13
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; } } if (!cmethods[2]) { cmethods[2] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 272LL)), ((char *)(string_pool + 299LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; }  } cstack11.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[2]), cstack11.l, cstack12.l); refs.insert(cstack11.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; }
        // New stack: 12
        // SWAP; Stack: 12
        std::swap(cstack11, cstack10);
        // New stack: 12
        // LDC 0; Stack: 12
        cstack12.i = 0;
        // New stack: 13
        // ANEWARRAY java/lang/Object; Stack: 13
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; } } if (cstack12.i < 0) utils::throw_re(env, ((char *)(string_pool + 210LL)), ((char *)(string_pool + 247LL)), -1); else { cstack12.l = env->NewObjectArray(cstack12.i, (cclasses[5]), nullptr); refs.insert(cstack12.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; }
        // New stack: 13
        // CHECKCAST java/lang/Object; Stack: 13
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; } } if (cstack12.l != nullptr && !env->IsInstanceOf(cstack12.l, (cclasses[5]))) { utils::throw_re(env, ((char *)(string_pool + 109LL)), (std::string(((char *)(string_pool + 138LL))) + std::string(((char *)(string_pool + 506LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; } } 
        // New stack: 13
        // SWAP; Stack: 13
        std::swap(cstack12, cstack11);
        // New stack: 13
        // INVOKESTATIC native/magic/1/linkcallsite/obfuscator0.7353569731942222.a(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/invoke/MemberName;; Stack: 13
        cstack7.l = utils::link_call_site(env, cstack7.l, cstack8.l, cstack9.l, cstack10.l, cstack11.l, cstack12.l); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; }
        // New stack: 8
        // POP; Stack: 8
        ;
        // New stack: 7
        // ICONST_0; Stack: 7
        cstack7.i = 0;
        // New stack: 8
        // AALOAD; Stack: 8
        if (cstack6.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 98LL)), -1); else { cstack6.l = env->GetObjectArrayElement((jobjectArray) cstack6.l, cstack7.i); refs.insert(cstack6.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; }
        // New stack: 7
        // DUP; Stack: 7
        cstack7 = cstack6;
        // New stack: 8
        // INSTANCEOF java/lang/invoke/CallSite; Stack: 8
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; } } cstack7.i = cstack7.l == nullptr ? false : env->IsInstanceOf(cstack7.l, (cclasses[10]));
        // New stack: 8
        // IFEQ L54; Stack: 8
        if (cstack7.i == 0) goto L54;
        // New stack: 7
        // INVOKEINTERFACE java/lang/invoke/CallSite.getTarget()Ljava/lang/invoke/MethodHandle;; Stack: 7
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[10]), ((char *)(string_pool + 523LL)), ((char *)(string_pool + 533LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; }  } if (cstack6.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 567LL)), -1); else { cstack6.l = env->CallObjectMethod(cstack6.l, (cmethods[4])); refs.insert(cstack6.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; }
        // New stack: 7
        // LABEL L54; Stack: 7
        L54: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; }
        // New stack: 7
        // FRAME FULL L: [dev/sakura/L3MonKe_L, java/util/concurrent/atomic/AtomicBoolean, dev/sakura/L3MonKe_B, 1, java/lang/String, java/lang/String, java/lang/String, java/util/Set, java/lang/String, java/lang/String, 4, 4, 4, 4, 4, [Ljava/lang/String;, dev/sakura/L3MonKe_e] S: [dev/sakura/L3MonKe_e, [Ljava/lang/Object;, 4, 4, java/lang/Object]; Stack: 7
        refs.erase(cstack0.l); refs.erase(cstack1.l); refs.erase(cstack6.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal4.l); refs.erase(clocal5.l); refs.erase(clocal6.l); refs.erase(clocal7.l); refs.erase(clocal8.l); refs.erase(clocal9.l); refs.erase(clocal20.l); refs.erase(clocal21.l); 
        utils::clear_refs(env, refs);
        // New stack: 7
        // CHECKCAST java/lang/invoke/MethodHandle; Stack: 7
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; } } if (cstack6.l != nullptr && !env->IsInstanceOf(cstack6.l, (cclasses[11]))) { utils::throw_re(env, ((char *)(string_pool + 109LL)), (std::string(((char *)(string_pool + 138LL))) + std::string(((char *)(string_pool + 594LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_9; } } 
        // New stack: 7
        // GOTO L55; Stack: 7
        goto L55;
        // New stack: 7
        // LABEL L4; Stack: 7
        L4: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 7
        // FRAME SAME1 L: null S: [java/lang/Throwable]; Stack: 7
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal4.l); refs.erase(clocal5.l); refs.erase(clocal6.l); refs.erase(clocal7.l); refs.erase(clocal8.l); refs.erase(clocal9.l); refs.erase(clocal20.l); refs.erase(clocal21.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // INSTANCEOF java/lang/BootstrapMethodError; Stack: 2
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } cstack1.i = cstack1.l == nullptr ? false : env->IsInstanceOf(cstack1.l, (cclasses[12]));
        // New stack: 2
        // IFNE L56; Stack: 2
        if (cstack1.i != 0) goto L56;
        // New stack: 1
        // NEW java/lang/BootstrapMethodError; Stack: 1
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (jobject obj = env->AllocObject((cclasses[12]))) { cstack1.l = obj; refs.insert(obj); } 
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
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[5]) { cmethods[5] = env->GetMethodID((cclasses[12]), ((char *)(string_pool + 624LL)), ((char *)(string_pool + 631LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 656LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[12]), (cmethods[5]), cstack2.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // LABEL L56; Stack: 1
        L56: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/Object]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal4.l); refs.erase(clocal5.l); refs.erase(clocal6.l); refs.erase(clocal7.l); refs.erase(clocal8.l); refs.erase(clocal9.l); refs.erase(clocal20.l); refs.erase(clocal21.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 679LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // LABEL L55; Stack: 0
        L55: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // FRAME FULL L: [dev/sakura/L3MonKe_L, java/util/concurrent/atomic/AtomicBoolean, dev/sakura/L3MonKe_B, 1, java/lang/String, java/lang/String, java/lang/String, java/util/Set, java/lang/String, java/lang/String, 4, 4, 4, 4, 4, [Ljava/lang/String;, dev/sakura/L3MonKe_e] S: [dev/sakura/L3MonKe_e, [Ljava/lang/Object;, 4, 4, java/lang/invoke/MethodHandle]; Stack: 0
        refs.erase(cstack0.l); refs.erase(cstack1.l); refs.erase(cstack6.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal4.l); refs.erase(clocal5.l); refs.erase(clocal6.l); refs.erase(clocal7.l); refs.erase(clocal8.l); refs.erase(clocal9.l); refs.erase(clocal20.l); refs.erase(clocal21.l); 
        utils::clear_refs(env, refs);
        // New stack: 7
        // INVOKESTATIC native/magic/1/invoke/obfuscator0.7353569731942222.a(Ljava/lang/Object;Ljava/lang/Object;JJLjava/lang/invoke/MethodHandle;)V; Stack: 7
        if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { cclasses_mtx[13].lock(); if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[16]))) { cclasses[13] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[13].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[18]) { cmethods[18] = env->GetStaticMethodID((cclasses[13]), ((char *)(string_pool + 7319LL)), ((char *)(string_pool + 7335LL))); if (env->ExceptionCheck()) { return (void) 0; }  } env->CallStaticVoidMethod((cclasses[13]), (cmethods[18]), cstack0.l, cstack1.l, cstack2.j, cstack4.j, cstack6.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // ALOAD 20; Stack: 0
        cstack0.l = clocal20.l; refs.insert(cstack0.l);
        // New stack: 1
        // IFNONNULL L57; Stack: 1
        if (!env->IsSameObject(cstack0.l, nullptr)) goto L57;
        // New stack: 0
        // LABEL L53; Stack: 0
        L53: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // FRAME SAME L: null S: null; Stack: 0
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal4.l); refs.erase(clocal5.l); refs.erase(clocal6.l); refs.erase(clocal7.l); refs.erase(clocal8.l); refs.erase(clocal9.l); refs.erase(clocal20.l); refs.erase(clocal21.l); 
        utils::clear_refs(env, refs);
        // New stack: 0
        // ALOAD 21; Stack: 0
        cstack0.l = clocal21.l; refs.insert(cstack0.l);
        // New stack: 1
        // ALOAD 4; Stack: 1
        cstack1.l = clocal4.l; refs.insert(cstack1.l);
        // New stack: 2
        // ALOAD 5; Stack: 2
        cstack2.l = clocal5.l; refs.insert(cstack2.l);
        // New stack: 3
        // LLOAD 14; Stack: 3
        cstack3.j = clocal14.j;
        // New stack: 5
        // ALOAD 6; Stack: 5
        cstack5.l = clocal6.l; refs.insert(cstack5.l);
        // New stack: 6
        // ALOAD 7; Stack: 6
        cstack6.l = clocal7.l; refs.insert(cstack6.l);
        // New stack: 7
        // ALOAD 8; Stack: 7
        cstack7.l = clocal8.l; refs.insert(cstack7.l);
        // New stack: 8
        // BIPUSH 6; Stack: 8
        cstack8.i = (jint) 6;
        // New stack: 9
        // ANEWARRAY java/lang/Object; Stack: 9
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (cstack8.i < 0) utils::throw_re(env, ((char *)(string_pool + 210LL)), ((char *)(string_pool + 247LL)), -1); else { cstack8.l = env->NewObjectArray(cstack8.i, (cclasses[5]), nullptr); refs.insert(cstack8.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 9
        // DUP_X1; Stack: 9
        cstack9 = cstack8; cstack8 = cstack7; cstack7 = cstack9;
        // New stack: 10
        // SWAP; Stack: 10
        std::swap(cstack9, cstack8);
        // New stack: 10
        // ICONST_5; Stack: 10
        cstack10.i = 5;
        // New stack: 11
        // SWAP; Stack: 11
        std::swap(cstack10, cstack9);
        // New stack: 11
        // AASTORE; Stack: 11
        if (cstack8.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 1072LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack8.l, cstack9.i, cstack10.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 8
        // DUP_X1; Stack: 8
        cstack8 = cstack7; cstack7 = cstack6; cstack6 = cstack8;
        // New stack: 9
        // SWAP; Stack: 9
        std::swap(cstack8, cstack7);
        // New stack: 9
        // ICONST_4; Stack: 9
        cstack9.i = 4;
        // New stack: 10
        // SWAP; Stack: 10
        std::swap(cstack9, cstack8);
        // New stack: 10
        // AASTORE; Stack: 10
        if (cstack7.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 1072LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack7.l, cstack8.i, cstack9.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 7
        // DUP_X1; Stack: 7
        cstack7 = cstack6; cstack6 = cstack5; cstack5 = cstack7;
        // New stack: 8
        // SWAP; Stack: 8
        std::swap(cstack7, cstack6);
        // New stack: 8
        // ICONST_3; Stack: 8
        cstack8.i = 3;
        // New stack: 9
        // SWAP; Stack: 9
        std::swap(cstack8, cstack7);
        // New stack: 9
        // AASTORE; Stack: 9
        if (cstack6.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 1072LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack6.l, cstack7.i, cstack8.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
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
        if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { cclasses_mtx[2].lock(); if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[2]))) { cclasses[2] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[2].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[1]) { cmethods[1] = env->GetStaticMethodID((cclasses[2]), ((char *)(string_pool + 1044LL)), ((char *)(string_pool + 1052LL))); if (env->ExceptionCheck()) { return (void) 0; }  } cstack5.l = env->CallStaticObjectMethod((cclasses[2]), (cmethods[1]), cstack5.j); refs.insert(cstack5.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 6
        // ICONST_2; Stack: 6
        cstack6.i = 2;
        // New stack: 7
        // SWAP; Stack: 7
        std::swap(cstack6, cstack5);
        // New stack: 7
        // AASTORE; Stack: 7
        if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 1072LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack4.l, cstack5.i, cstack6.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
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
        if (cstack3.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 1072LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack3.l, cstack4.i, cstack5.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 3
        // DUP_X1; Stack: 3
        cstack3 = cstack2; cstack2 = cstack1; cstack1 = cstack3;
        // New stack: 4
        // SWAP; Stack: 4
        std::swap(cstack3, cstack2);
        // New stack: 4
        // ICONST_0; Stack: 4
        cstack4.i = 0;
        // New stack: 5
        // SWAP; Stack: 5
        std::swap(cstack4, cstack3);
        // New stack: 5
        // AASTORE; Stack: 5
        if (cstack2.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 1072LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack2.l, cstack3.i, cstack4.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 2
        // LDC -6898785268804145187; Stack: 2
        cstack2.j = -6898785268804145187LL;
        // New stack: 4
        // LLOAD 10; Stack: 4
        cstack4.j = clocal10.j;
        // New stack: 6
        // LABEL L1; Stack: 6
        L1: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; }
        // New stack: 6
        // ICONST_1; Stack: 6
        cstack6.i = 1;
        // New stack: 7
        // ANEWARRAY java/lang/Object; Stack: 7
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; } } if (cstack6.i < 0) utils::throw_re(env, ((char *)(string_pool + 210LL)), ((char *)(string_pool + 247LL)), -1); else { cstack6.l = env->NewObjectArray(cstack6.i, (cclasses[5]), nullptr); refs.insert(cstack6.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; }
        // New stack: 7
        // DUP; Stack: 7
        cstack7 = cstack6;
        // New stack: 8
        // LDC Ldev/sakura/L3MonKe_h;; Stack: 8
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; } } cstack8.l = (cclasses[6]);
        // New stack: 9
        // SWAP; Stack: 9
        std::swap(cstack8, cstack7);
        // New stack: 9
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.7353569731942222.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 9
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; } } cstack9.l = lookup;
        // New stack: 10
        // LDC Ldev/sakura/client/L3MonKe_f2;; Stack: 10
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[6]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; } } cstack10.l = (cclasses[7]);
        // New stack: 11
        // LDC a; Stack: 11
        cstack11.l = (cstrings[7]);
        // New stack: 12
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;; Stack: 12
        cstack12.l = (cstrings[8]);
        // New stack: 13
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.7353569731942222.a()Ljava/lang/ClassLoader;; Stack: 13
        cstack13.l = classloader;
        // New stack: 14
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 14
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; } } if (!cmethods[2]) { cmethods[2] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 272LL)), ((char *)(string_pool + 299LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; }  } cstack12.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[2]), cstack12.l, cstack13.l); refs.insert(cstack12.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; }
        // New stack: 13
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 13
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[10]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; } } if (!cmethods[3]) { cmethods[3] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 372LL)), ((char *)(string_pool + 383LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; }  } if (cstack9.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 481LL)), -1); else { cstack9.l = env->CallObjectMethod(cstack9.l, (cmethods[3]), cstack10.l, cstack11.l, cstack12.l); refs.insert(cstack9.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; }
        // New stack: 10
        // SWAP; Stack: 10
        std::swap(cstack9, cstack8);
        // New stack: 10
        // LDC \u00de; Stack: 10
        cstack10.l = (cstrings[36]);
        // New stack: 11
        // SWAP; Stack: 11
        std::swap(cstack10, cstack9);
        // New stack: 11
        // LDC (Ljava/lang/Object;Ljava/lang/Object;JJ)V; Stack: 11
        cstack11.l = (cstrings[48]);
        // New stack: 12
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.7353569731942222.a()Ljava/lang/ClassLoader;; Stack: 12
        cstack12.l = classloader;
        // New stack: 13
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 13
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; } } if (!cmethods[2]) { cmethods[2] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 272LL)), ((char *)(string_pool + 299LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; }  } cstack11.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[2]), cstack11.l, cstack12.l); refs.insert(cstack11.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; }
        // New stack: 12
        // SWAP; Stack: 12
        std::swap(cstack11, cstack10);
        // New stack: 12
        // LDC 0; Stack: 12
        cstack12.i = 0;
        // New stack: 13
        // ANEWARRAY java/lang/Object; Stack: 13
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; } } if (cstack12.i < 0) utils::throw_re(env, ((char *)(string_pool + 210LL)), ((char *)(string_pool + 247LL)), -1); else { cstack12.l = env->NewObjectArray(cstack12.i, (cclasses[5]), nullptr); refs.insert(cstack12.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; }
        // New stack: 13
        // CHECKCAST java/lang/Object; Stack: 13
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; } } if (cstack12.l != nullptr && !env->IsInstanceOf(cstack12.l, (cclasses[5]))) { utils::throw_re(env, ((char *)(string_pool + 109LL)), (std::string(((char *)(string_pool + 138LL))) + std::string(((char *)(string_pool + 506LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; } } 
        // New stack: 13
        // SWAP; Stack: 13
        std::swap(cstack12, cstack11);
        // New stack: 13
        // INVOKESTATIC native/magic/1/linkcallsite/obfuscator0.7353569731942222.a(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/invoke/MemberName;; Stack: 13
        cstack7.l = utils::link_call_site(env, cstack7.l, cstack8.l, cstack9.l, cstack10.l, cstack11.l, cstack12.l); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; }
        // New stack: 8
        // POP; Stack: 8
        ;
        // New stack: 7
        // ICONST_0; Stack: 7
        cstack7.i = 0;
        // New stack: 8
        // AALOAD; Stack: 8
        if (cstack6.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 98LL)), -1); else { cstack6.l = env->GetObjectArrayElement((jobjectArray) cstack6.l, cstack7.i); refs.insert(cstack6.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; }
        // New stack: 7
        // DUP; Stack: 7
        cstack7 = cstack6;
        // New stack: 8
        // INSTANCEOF java/lang/invoke/CallSite; Stack: 8
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; } } cstack7.i = cstack7.l == nullptr ? false : env->IsInstanceOf(cstack7.l, (cclasses[10]));
        // New stack: 8
        // IFEQ L58; Stack: 8
        if (cstack7.i == 0) goto L58;
        // New stack: 7
        // INVOKEINTERFACE java/lang/invoke/CallSite.getTarget()Ljava/lang/invoke/MethodHandle;; Stack: 7
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[10]), ((char *)(string_pool + 523LL)), ((char *)(string_pool + 533LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; }  } if (cstack6.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 567LL)), -1); else { cstack6.l = env->CallObjectMethod(cstack6.l, (cmethods[4])); refs.insert(cstack6.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; }
        // New stack: 7
        // LABEL L58; Stack: 7
        L58: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; }
        // New stack: 7
        // FRAME FULL L: [dev/sakura/L3MonKe_L, java/util/concurrent/atomic/AtomicBoolean, dev/sakura/L3MonKe_B, 1, java/lang/String, java/lang/String, java/lang/String, java/util/Set, java/lang/String, java/lang/String, 4, 4, 4, 4, 4, [Ljava/lang/String;, dev/sakura/L3MonKe_e] S: [dev/sakura/L3MonKe_e, [Ljava/lang/Object;, 4, 4, java/lang/Object]; Stack: 7
        refs.erase(cstack0.l); refs.erase(cstack1.l); refs.erase(cstack6.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal4.l); refs.erase(clocal5.l); refs.erase(clocal6.l); refs.erase(clocal7.l); refs.erase(clocal8.l); refs.erase(clocal9.l); refs.erase(clocal20.l); refs.erase(clocal21.l); 
        utils::clear_refs(env, refs);
        // New stack: 7
        // CHECKCAST java/lang/invoke/MethodHandle; Stack: 7
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; } } if (cstack6.l != nullptr && !env->IsInstanceOf(cstack6.l, (cclasses[11]))) { utils::throw_re(env, ((char *)(string_pool + 109LL)), (std::string(((char *)(string_pool + 138LL))) + std::string(((char *)(string_pool + 594LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_10; } } 
        // New stack: 7
        // GOTO L59; Stack: 7
        goto L59;
        // New stack: 7
        // LABEL L2; Stack: 7
        L2: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 7
        // FRAME SAME1 L: null S: [java/lang/Throwable]; Stack: 7
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal4.l); refs.erase(clocal5.l); refs.erase(clocal6.l); refs.erase(clocal7.l); refs.erase(clocal8.l); refs.erase(clocal9.l); refs.erase(clocal20.l); refs.erase(clocal21.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // INSTANCEOF java/lang/BootstrapMethodError; Stack: 2
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } cstack1.i = cstack1.l == nullptr ? false : env->IsInstanceOf(cstack1.l, (cclasses[12]));
        // New stack: 2
        // IFNE L60; Stack: 2
        if (cstack1.i != 0) goto L60;
        // New stack: 1
        // NEW java/lang/BootstrapMethodError; Stack: 1
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (jobject obj = env->AllocObject((cclasses[12]))) { cstack1.l = obj; refs.insert(obj); } 
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
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[5]) { cmethods[5] = env->GetMethodID((cclasses[12]), ((char *)(string_pool + 624LL)), ((char *)(string_pool + 631LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 656LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[12]), (cmethods[5]), cstack2.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // LABEL L60; Stack: 1
        L60: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/Object]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal4.l); refs.erase(clocal5.l); refs.erase(clocal6.l); refs.erase(clocal7.l); refs.erase(clocal8.l); refs.erase(clocal9.l); refs.erase(clocal20.l); refs.erase(clocal21.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 679LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // LABEL L59; Stack: 0
        L59: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // FRAME FULL L: [dev/sakura/L3MonKe_L, java/util/concurrent/atomic/AtomicBoolean, dev/sakura/L3MonKe_B, 1, java/lang/String, java/lang/String, java/lang/String, java/util/Set, java/lang/String, java/lang/String, 4, 4, 4, 4, 4, [Ljava/lang/String;, dev/sakura/L3MonKe_e] S: [dev/sakura/L3MonKe_e, [Ljava/lang/Object;, 4, 4, java/lang/invoke/MethodHandle]; Stack: 0
        refs.erase(cstack0.l); refs.erase(cstack1.l); refs.erase(cstack6.l); 
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal4.l); refs.erase(clocal5.l); refs.erase(clocal6.l); refs.erase(clocal7.l); refs.erase(clocal8.l); refs.erase(clocal9.l); refs.erase(clocal20.l); refs.erase(clocal21.l); 
        utils::clear_refs(env, refs);
        // New stack: 7
        // INVOKESTATIC native/magic/1/invoke/obfuscator0.7353569731942222.a(Ljava/lang/Object;Ljava/lang/Object;JJLjava/lang/invoke/MethodHandle;)V; Stack: 7
        if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { cclasses_mtx[13].lock(); if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[16]))) { cclasses[13] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[13].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[18]) { cmethods[18] = env->GetStaticMethodID((cclasses[13]), ((char *)(string_pool + 7319LL)), ((char *)(string_pool + 7335LL))); if (env->ExceptionCheck()) { return (void) 0; }  } env->CallStaticVoidMethod((cclasses[13]), (cmethods[18]), cstack0.l, cstack1.l, cstack2.j, cstack4.j, cstack6.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // LABEL L57; Stack: 0
        L57: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // FRAME SAME L: null S: null; Stack: 0
        refs.erase(clocal0.l); refs.erase(clocal1.l); refs.erase(clocal2.l); refs.erase(clocal4.l); refs.erase(clocal5.l); refs.erase(clocal6.l); refs.erase(clocal7.l); refs.erase(clocal8.l); refs.erase(clocal9.l); refs.erase(clocal20.l); refs.erase(clocal21.l); 
        utils::clear_refs(env, refs);
        // New stack: 0
        // RETURN; Stack: 0
        return;
        // New stack: 0
        return (void) 0;
        L_CATCH_7: if (env->IsInstanceOf(cstack0.l, (cclasses[0]))) { goto L8; }
        env->Throw((jthrowable) cstack0.l); return (void) 0;
        L_CATCH_1: if (env->IsInstanceOf(cstack0.l, (cclasses[21]))) { goto L23; }
        env->Throw((jthrowable) cstack0.l); return (void) 0;
        L_CATCH_0: if (env->IsInstanceOf(cstack0.l, (cclasses[0]))) { goto L20; }
        env->Throw((jthrowable) cstack0.l); return (void) 0;
        L_CATCH_2: if (env->IsInstanceOf(cstack0.l, (cclasses[0]))) { goto L18; }
        goto L_CATCH_1;
        L_CATCH_9: if (env->IsInstanceOf(cstack0.l, (cclasses[0]))) { goto L4; }
        env->Throw((jthrowable) cstack0.l); return (void) 0;
        L_CATCH_3: if (env->IsInstanceOf(cstack0.l, (cclasses[0]))) { goto L16; }
        env->Throw((jthrowable) cstack0.l); return (void) 0;
        L_CATCH_6: if (env->IsInstanceOf(cstack0.l, (cclasses[0]))) { goto L10; }
        env->Throw((jthrowable) cstack0.l); return (void) 0;
        L_CATCH_8: if (env->IsInstanceOf(cstack0.l, (cclasses[0]))) { goto L6; }
        env->Throw((jthrowable) cstack0.l); return (void) 0;
        L_CATCH_5: if (env->IsInstanceOf(cstack0.l, (cclasses[0]))) { goto L12; }
        env->Throw((jthrowable) cstack0.l); return (void) 0;
        L_CATCH_4: if (env->IsInstanceOf(cstack0.l, (cclasses[0]))) { goto L14; }
        env->Throw((jthrowable) cstack0.l); return (void) 0;
        L_CATCH_10: if (env->IsInstanceOf(cstack0.l, (cclasses[0]))) { goto L2; }
        env->Throw((jthrowable) cstack0.l); return (void) 0;
    }
    
    // <clinit>()V
    void JNICALL __ngen_special_clinit_6_5(JNIEnv *env, jobject ignored_hidden, jclass clazz) {
        env->DeleteLocalRef(ignored_hidden);
        jobject classloader = utils::get_classloader_from_class(env, clazz);
        if (env->ExceptionCheck()) { return (void) 0; }
        if (classloader == nullptr) { env->FatalError(((char *)(string_pool + 47LL))); return (void) 0; }
    
        jobject lookup = nullptr;
        // try-catch-class java/lang/Throwable
        if (!cclasses[0] || env->IsSameObject(cclasses[0], NULL)) { cclasses_mtx[0].lock(); if (!cclasses[0] || env->IsSameObject(cclasses[0], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[0]))) { cclasses[0] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[0].unlock(); if (env->ExceptionCheck()) { return (void) 0; } }
        jvalue cstack0 = {}, cstack1 = {}, cstack2 = {}, cstack3 = {}, cstack4 = {}, cstack5 = {}, cstack6 = {}, cstack7 = {}, cstack8 = {}, cstack9 = {}, cstack10 = {}, cstack11 = {}, cstack12 = {};
        jvalue clocal0 = {}, clocal1 = {}, clocal2 = {}, clocal3 = {}, clocal4 = {}, clocal5 = {}, clocal6 = {}, clocal7 = {}, clocal8 = {}, clocal9 = {}, clocal10 = {}, clocal11 = {}, clocal12 = {}, clocal13 = {}, clocal14 = {}, clocal15 = {}, clocal16 = {}, clocal17 = {};
        std::unordered_set<jobject> refs;
    
    
        // LDC -1912875551553990230; Stack: 0
        cstack0.j = -1912875551553990230LL;
        // New stack: 2
        // LDC -7232238035842018976; Stack: 2
        cstack2.j = -7232238035842018976LL;
        // New stack: 4
        // INVOKESTATIC java/lang/invoke/MethodHandles.lookup()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 4
        if (!cclasses[25] || env->IsSameObject(cclasses[25], NULL)) { cclasses_mtx[25].lock(); if (!cclasses[25] || env->IsSameObject(cclasses[25], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[49]))) { cclasses[25] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[25].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[19]) { cmethods[19] = env->GetStaticMethodID((cclasses[25]), ((char *)(string_pool + 1740LL)), ((char *)(string_pool + 1747LL))); if (env->ExceptionCheck()) { return (void) 0; }  } cstack4.l = env->CallStaticObjectMethod((cclasses[25]), (cmethods[19])); refs.insert(cstack4.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 5
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.lookupClass()Ljava/lang/Class;; Stack: 5
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[10]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[20]) { cmethods[20] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 1789LL)), ((char *)(string_pool + 1801LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 481LL)), -1); else { cstack4.l = env->CallObjectMethod(cstack4.l, (cmethods[20])); refs.insert(cstack4.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 5
        // INVOKESTATIC dev/sakura/client/L3MonKe_lw.a(JJLjava/lang/Object;)Ldev/sakura/client/L3MonKe_d5;; Stack: 5
        if (!cclasses[26] || env->IsSameObject(cclasses[26], NULL)) { cclasses_mtx[26].lock(); if (!cclasses[26] || env->IsSameObject(cclasses[26], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[50]))) { cclasses[26] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[26].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[21]) { cmethods[21] = env->GetStaticMethodID((cclasses[26]), ((char *)(string_pool + 206LL)), ((char *)(string_pool + 1821LL))); if (env->ExceptionCheck()) { return (void) 0; }  } cstack0.l = env->CallStaticObjectMethod((cclasses[26]), (cmethods[21]), cstack0.j, cstack2.j, cstack4.l); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // LDC 152092582079730; Stack: 1
        cstack1.j = 152092582079730LL;
        // New stack: 3
        // INVOKEINTERFACE dev/sakura/client/L3MonKe_d5.a(J)J; Stack: 3
        if (!cclasses[27] || env->IsSameObject(cclasses[27], NULL)) { cclasses_mtx[27].lock(); if (!cclasses[27] || env->IsSameObject(cclasses[27], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[51]))) { cclasses[27] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[27].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[22]) { cmethods[22] = env->GetMethodID((cclasses[27]), ((char *)(string_pool + 206LL)), ((char *)(string_pool + 1874LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 1879LL)), -1); else cstack0.j = env->CallLongMethod(cstack0.l, (cmethods[22]), cstack1.j); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 2
        // PUTSTATIC dev/sakura/L3MonKe_h.a J; Stack: 2
        if (!cclasses[4]  || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cfields[0]) { cfields[0] = env->GetStaticFieldID((cclasses[4]), ((char *)(string_pool + 206LL)), ((char *)(string_pool + 208LL))); if (env->ExceptionCheck()) { return (void) 0; }  } env->SetStaticLongField((cclasses[4]), (cfields[0]), cstack0.j); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // GETSTATIC dev/sakura/L3MonKe_h.a J; Stack: 0
        if (!cclasses[4]  || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cfields[0]) { cfields[0] = env->GetStaticFieldID((cclasses[4]), ((char *)(string_pool + 206LL)), ((char *)(string_pool + 208LL))); if (env->ExceptionCheck()) { return (void) 0; }  } cstack0.j = env->GetStaticLongField((cclasses[4]), (cfields[0])); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 2
        // LDC 66487391551166; Stack: 2
        cstack2.j = 66487391551166LL;
        // New stack: 4
        // LXOR; Stack: 4
        cstack0.j = cstack0.j ^ cstack2.j;
        // New stack: 2
        // LSTORE 16; Stack: 2
        clocal16.j = cstack0.j;
        // New stack: 0
        // ICONST_1; Stack: 0
        cstack0.i = 1;
        // New stack: 1
        // ANEWARRAY java/lang/String; Stack: 1
        if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { cclasses_mtx[1].lock(); if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[1]))) { cclasses[1] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[1].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (cstack0.i < 0) utils::throw_re(env, ((char *)(string_pool + 210LL)), ((char *)(string_pool + 247LL)), -1); else { cstack0.l = env->NewObjectArray(cstack0.i, (cclasses[1]), nullptr); refs.insert(cstack0.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // NEW java/util/HashMap; Stack: 1
        if (!cclasses[28] || env->IsSameObject(cclasses[28], NULL)) { cclasses_mtx[28].lock(); if (!cclasses[28] || env->IsSameObject(cclasses[28], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[52]))) { cclasses[28] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[28].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (jobject obj = env->AllocObject((cclasses[28]))) { cstack1.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 2
        // DUP; Stack: 2
        cstack2 = cstack1;
        // New stack: 3
        // BIPUSH 13; Stack: 3
        cstack3.i = (jint) 13;
        // New stack: 4
        // INVOKESPECIAL java/util/HashMap.<init>(I)V; Stack: 4
        if (!cclasses[28] || env->IsSameObject(cclasses[28], NULL)) { cclasses_mtx[28].lock(); if (!cclasses[28] || env->IsSameObject(cclasses[28], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[52]))) { cclasses[28] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[28].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[23]) { cmethods[23] = env->GetMethodID((cclasses[28]), ((char *)(string_pool + 624LL)), ((char *)(string_pool + 1904LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack2.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 656LL)), -1); else env->CallNonvirtualVoidMethod(cstack2.l, (cclasses[28]), (cmethods[23]), cstack3.i); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 2
        // PUTSTATIC dev/sakura/L3MonKe_h.d Ljava/util/Map;; Stack: 2
        if (!cclasses[4]  || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cfields[1]) { cfields[1] = env->GetStaticFieldID((cclasses[4]), ((char *)(string_pool + 1909LL)), ((char *)(string_pool + 1911LL))); if (env->ExceptionCheck()) { return (void) 0; }  } env->SetStaticObjectField((cclasses[4]), (cfields[1]), cstack1.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // LDC 9093127178931047941; Stack: 1
        cstack1.j = 9093127178931047941LL;
        // New stack: 3
        // LLOAD 16; Stack: 3
        cstack3.j = clocal16.j;
        // New stack: 5
        // LABEL L3; Stack: 5
        L3: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 5
        // ICONST_1; Stack: 5
        cstack5.i = 1;
        // New stack: 6
        // ANEWARRAY java/lang/Object; Stack: 6
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack5.i < 0) utils::throw_re(env, ((char *)(string_pool + 210LL)), ((char *)(string_pool + 247LL)), -1); else { cstack5.l = env->NewObjectArray(cstack5.i, (cclasses[5]), nullptr); refs.insert(cstack5.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 6
        // DUP; Stack: 6
        cstack6 = cstack5;
        // New stack: 7
        // LDC Ldev/sakura/L3MonKe_h;; Stack: 7
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack7.l = (cclasses[6]);
        // New stack: 8
        // SWAP; Stack: 8
        std::swap(cstack7, cstack6);
        // New stack: 8
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.7353569731942222.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 8
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack8.l = lookup;
        // New stack: 9
        // LDC Ldev/sakura/client/L3MonKe_f2;; Stack: 9
        if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { cclasses_mtx[7].lock(); if (!cclasses[7] || env->IsSameObject(cclasses[7], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[6]))) { cclasses[7] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[7].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack9.l = (cclasses[7]);
        // New stack: 10
        // LDC a; Stack: 10
        cstack10.l = (cstrings[7]);
        // New stack: 11
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;; Stack: 11
        cstack11.l = (cstrings[8]);
        // New stack: 12
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.7353569731942222.a()Ljava/lang/ClassLoader;; Stack: 12
        cstack12.l = classloader;
        // New stack: 13
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 13
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[2]) { cmethods[2] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 272LL)), ((char *)(string_pool + 299LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack11.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[2]), cstack11.l, cstack12.l); refs.insert(cstack11.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 12
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 12
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[10]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[3]) { cmethods[3] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 372LL)), ((char *)(string_pool + 383LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack8.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 481LL)), -1); else { cstack8.l = env->CallObjectMethod(cstack8.l, (cmethods[3]), cstack9.l, cstack10.l, cstack11.l); refs.insert(cstack8.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 9
        // SWAP; Stack: 9
        std::swap(cstack8, cstack7);
        // New stack: 9
        // LDC \u00d1; Stack: 9
        cstack9.l = (cstrings[11]);
        // New stack: 10
        // SWAP; Stack: 10
        std::swap(cstack9, cstack8);
        // New stack: 10
        // LDC (Ljava/lang/Object;JJ)V; Stack: 10
        cstack10.l = (cstrings[12]);
        // New stack: 11
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.7353569731942222.a()Ljava/lang/ClassLoader;; Stack: 11
        cstack11.l = classloader;
        // New stack: 12
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 12
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[2]) { cmethods[2] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 272LL)), ((char *)(string_pool + 299LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack10.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[2]), cstack10.l, cstack11.l); refs.insert(cstack10.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 11
        // SWAP; Stack: 11
        std::swap(cstack10, cstack9);
        // New stack: 11
        // LDC 0; Stack: 11
        cstack11.i = 0;
        // New stack: 12
        // ANEWARRAY java/lang/Object; Stack: 12
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack11.i < 0) utils::throw_re(env, ((char *)(string_pool + 210LL)), ((char *)(string_pool + 247LL)), -1); else { cstack11.l = env->NewObjectArray(cstack11.i, (cclasses[5]), nullptr); refs.insert(cstack11.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 12
        // CHECKCAST java/lang/Object; Stack: 12
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack11.l != nullptr && !env->IsInstanceOf(cstack11.l, (cclasses[5]))) { utils::throw_re(env, ((char *)(string_pool + 109LL)), (std::string(((char *)(string_pool + 138LL))) + std::string(((char *)(string_pool + 506LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } 
        // New stack: 12
        // SWAP; Stack: 12
        std::swap(cstack11, cstack10);
        // New stack: 12
        // INVOKESTATIC native/magic/1/linkcallsite/obfuscator0.7353569731942222.a(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/invoke/MemberName;; Stack: 12
        cstack6.l = utils::link_call_site(env, cstack6.l, cstack7.l, cstack8.l, cstack9.l, cstack10.l, cstack11.l); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 7
        // POP; Stack: 7
        ;
        // New stack: 6
        // ICONST_0; Stack: 6
        cstack6.i = 0;
        // New stack: 7
        // AALOAD; Stack: 7
        if (cstack5.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 98LL)), -1); else { cstack5.l = env->GetObjectArrayElement((jobjectArray) cstack5.l, cstack6.i); refs.insert(cstack5.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 6
        // DUP; Stack: 6
        cstack6 = cstack5;
        // New stack: 7
        // INSTANCEOF java/lang/invoke/CallSite; Stack: 7
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack6.i = cstack6.l == nullptr ? false : env->IsInstanceOf(cstack6.l, (cclasses[10]));
        // New stack: 7
        // IFEQ L5; Stack: 7
        if (cstack6.i == 0) goto L5;
        // New stack: 6
        // INVOKEINTERFACE java/lang/invoke/CallSite.getTarget()Ljava/lang/invoke/MethodHandle;; Stack: 6
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[10]), ((char *)(string_pool + 523LL)), ((char *)(string_pool + 533LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack5.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 567LL)), -1); else { cstack5.l = env->CallObjectMethod(cstack5.l, (cmethods[4])); refs.insert(cstack5.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 6
        // LABEL L5; Stack: 6
        L5: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 6
        // FRAME FULL L: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4] S: [[Ljava/lang/String;, 4, 4, java/lang/Object]; Stack: 6
        refs.erase(cstack0.l); refs.erase(cstack5.l); 
        utils::clear_refs(env, refs);
        // New stack: 6
        // CHECKCAST java/lang/invoke/MethodHandle; Stack: 6
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack5.l != nullptr && !env->IsInstanceOf(cstack5.l, (cclasses[11]))) { utils::throw_re(env, ((char *)(string_pool + 109LL)), (std::string(((char *)(string_pool + 138LL))) + std::string(((char *)(string_pool + 594LL)))).c_str(), -1); 
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
        utils::clear_refs(env, refs);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // INSTANCEOF java/lang/BootstrapMethodError; Stack: 2
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } cstack1.i = cstack1.l == nullptr ? false : env->IsInstanceOf(cstack1.l, (cclasses[12]));
        // New stack: 2
        // IFNE L7; Stack: 2
        if (cstack1.i != 0) goto L7;
        // New stack: 1
        // NEW java/lang/BootstrapMethodError; Stack: 1
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (jobject obj = env->AllocObject((cclasses[12]))) { cstack1.l = obj; refs.insert(obj); } 
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
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[5]) { cmethods[5] = env->GetMethodID((cclasses[12]), ((char *)(string_pool + 624LL)), ((char *)(string_pool + 631LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 656LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[12]), (cmethods[5]), cstack2.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // LABEL L7; Stack: 1
        L7: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/Object]; Stack: 1
        refs.erase(cstack0.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 679LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // LABEL L6; Stack: 0
        L6: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // FRAME FULL L: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4] S: [[Ljava/lang/String;, 4, 4, java/lang/invoke/MethodHandle]; Stack: 0
        refs.erase(cstack0.l); refs.erase(cstack5.l); 
        utils::clear_refs(env, refs);
        // New stack: 6
        // INVOKESTATIC native/magic/1/invoke/obfuscator0.7353569731942222.a(Ljava/lang/Object;JJLjava/lang/invoke/MethodHandle;)V; Stack: 6
        if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { cclasses_mtx[13].lock(); if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[16]))) { cclasses[13] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[13].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[6]) { cmethods[6] = env->GetStaticMethodID((cclasses[13]), ((char *)(string_pool + 5836LL)), ((char *)(string_pool + 5852LL))); if (env->ExceptionCheck()) { return (void) 0; }  } env->CallStaticVoidMethod((cclasses[13]), (cmethods[6]), cstack0.l, cstack1.j, cstack3.j, cstack5.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // LDC DES/CBC/PKCS5Padding; Stack: 0
        cstack0.l = (cstrings[53]);
        // New stack: 1
        // INVOKESTATIC javax/crypto/Cipher.getInstance(Ljava/lang/String;)Ljavax/crypto/Cipher;; Stack: 1
        if (!cclasses[29] || env->IsSameObject(cclasses[29], NULL)) { cclasses_mtx[29].lock(); if (!cclasses[29] || env->IsSameObject(cclasses[29], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[54]))) { cclasses[29] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[29].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[24]) { cmethods[24] = env->GetStaticMethodID((cclasses[29]), ((char *)(string_pool + 1927LL)), ((char *)(string_pool + 1939LL))); if (env->ExceptionCheck()) { return (void) 0; }  } cstack0.l = env->CallStaticObjectMethod((cclasses[29]), (cmethods[24]), cstack0.l); refs.insert(cstack0.l); 
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
        cstack2.l = (cstrings[55]);
        // New stack: 3
        // INVOKESTATIC javax/crypto/SecretKeyFactory.getInstance(Ljava/lang/String;)Ljavax/crypto/SecretKeyFactory;; Stack: 3
        if (!cclasses[30] || env->IsSameObject(cclasses[30], NULL)) { cclasses_mtx[30].lock(); if (!cclasses[30] || env->IsSameObject(cclasses[30], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[56]))) { cclasses[30] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[30].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[25]) { cmethods[25] = env->GetStaticMethodID((cclasses[30]), ((char *)(string_pool + 1927LL)), ((char *)(string_pool + 1981LL))); if (env->ExceptionCheck()) { return (void) 0; }  } cstack2.l = env->CallStaticObjectMethod((cclasses[30]), (cmethods[25]), cstack2.l); refs.insert(cstack2.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 3
        // BIPUSH 8; Stack: 3
        cstack3.i = (jint) 8;
        // New stack: 4
        // NEWARRAY 8; Stack: 4
        if (cstack3.i < 0) utils::throw_re(env, ((char *)(string_pool + 210LL)), ((char *)(string_pool + 2033LL)), -1); else { cstack3.l = env->NewByteArray(cstack3.i); refs.insert(cstack3.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 4
        // DUP; Stack: 4
        cstack4 = cstack3;
        // New stack: 5
        // ICONST_0; Stack: 5
        cstack5.i = 0;
        // New stack: 6
        // LLOAD 16; Stack: 6
        cstack6.j = clocal16.j;
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
        if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 2062LL)), -1); else { utils::bastore(env, (jarray) cstack4.l, cstack5.i, cstack6.i); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 4
        // ICONST_1; Stack: 4
        cstack4.i = 1;
        // New stack: 5
        // ISTORE 8; Stack: 5
        clocal8.i = cstack4.i;
        // New stack: 4
        // LABEL L8; Stack: 4
        L8: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 4
        // FRAME FULL L: [0, 0, 0, 0, 0, 0, 0, javax/crypto/Cipher, 1, 0, 0, 0, 0, 0, 0, 0, 4] S: [javax/crypto/Cipher, 1, javax/crypto/SecretKeyFactory, [B]; Stack: 4
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
        // IF_ICMPGE L9; Stack: 6
        if (cstack4.i >= cstack5.i) goto L9;
        // New stack: 4
        // DUP; Stack: 4
        cstack4 = cstack3;
        // New stack: 5
        // ILOAD 8; Stack: 5
        cstack5.i = clocal8.i;
        // New stack: 6
        // LLOAD 16; Stack: 6
        cstack6.j = clocal16.j;
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
        if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 2062LL)), -1); else { utils::bastore(env, (jarray) cstack4.l, cstack5.i, cstack6.i); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 4
        // IINC 8 1; Stack: 4
        clocal8.i += 1;
        // New stack: 4
        // GOTO L8; Stack: 4
        goto L8;
        // New stack: 4
        // LABEL L9; Stack: 4
        L9: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 4
        // FRAME FULL L: [0, 0, 0, 0, 0, 0, 0, javax/crypto/Cipher, 1, 0, 0, 0, 0, 0, 0, 0, 4] S: [javax/crypto/Cipher, 1, javax/crypto/SecretKeyFactory, [B]; Stack: 4
        refs.erase(cstack0.l); refs.erase(cstack2.l); refs.erase(cstack3.l); 
        refs.erase(clocal7.l); 
        utils::clear_refs(env, refs);
        // New stack: 4
        // NEW javax/crypto/spec/DESKeySpec; Stack: 4
        if (!cclasses[31] || env->IsSameObject(cclasses[31], NULL)) { cclasses_mtx[31].lock(); if (!cclasses[31] || env->IsSameObject(cclasses[31], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[57]))) { cclasses[31] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[31].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (jobject obj = env->AllocObject((cclasses[31]))) { cstack4.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 5
        // DUP_X1; Stack: 5
        cstack5 = cstack4; cstack4 = cstack3; cstack3 = cstack5;
        // New stack: 6
        // SWAP; Stack: 6
        std::swap(cstack5, cstack4);
        // New stack: 6
        // INVOKESPECIAL javax/crypto/spec/DESKeySpec.<init>([B)V; Stack: 6
        if (!cclasses[31] || env->IsSameObject(cclasses[31], NULL)) { cclasses_mtx[31].lock(); if (!cclasses[31] || env->IsSameObject(cclasses[31], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[57]))) { cclasses[31] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[31].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[26]) { cmethods[26] = env->GetMethodID((cclasses[31]), ((char *)(string_pool + 624LL)), ((char *)(string_pool + 2074LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 656LL)), -1); else env->CallNonvirtualVoidMethod(cstack4.l, (cclasses[31]), (cmethods[26]), cstack5.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 4
        // INVOKEVIRTUAL javax/crypto/SecretKeyFactory.generateSecret(Ljava/security/spec/KeySpec;)Ljavax/crypto/SecretKey;; Stack: 4
        if (!cclasses[30] || env->IsSameObject(cclasses[30], NULL)) { cclasses_mtx[30].lock(); if (!cclasses[30] || env->IsSameObject(cclasses[30], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[56]))) { cclasses[30] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[30].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[27]) { cmethods[27] = env->GetMethodID((cclasses[30]), ((char *)(string_pool + 2080LL)), ((char *)(string_pool + 2095LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack2.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 481LL)), -1); else { cstack2.l = env->CallObjectMethod(cstack2.l, (cmethods[27]), cstack3.l); refs.insert(cstack2.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 3
        // NEW javax/crypto/spec/IvParameterSpec; Stack: 3
        if (!cclasses[32] || env->IsSameObject(cclasses[32], NULL)) { cclasses_mtx[32].lock(); if (!cclasses[32] || env->IsSameObject(cclasses[32], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[58]))) { cclasses[32] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[32].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (jobject obj = env->AllocObject((cclasses[32]))) { cstack3.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 4
        // DUP; Stack: 4
        cstack4 = cstack3;
        // New stack: 5
        // BIPUSH 8; Stack: 5
        cstack5.i = (jint) 8;
        // New stack: 6
        // NEWARRAY 8; Stack: 6
        if (cstack5.i < 0) utils::throw_re(env, ((char *)(string_pool + 210LL)), ((char *)(string_pool + 2033LL)), -1); else { cstack5.l = env->NewByteArray(cstack5.i); refs.insert(cstack5.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 6
        // INVOKESPECIAL javax/crypto/spec/IvParameterSpec.<init>([B)V; Stack: 6
        if (!cclasses[32] || env->IsSameObject(cclasses[32], NULL)) { cclasses_mtx[32].lock(); if (!cclasses[32] || env->IsSameObject(cclasses[32], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[58]))) { cclasses[32] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[32].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[28]) { cmethods[28] = env->GetMethodID((cclasses[32]), ((char *)(string_pool + 624LL)), ((char *)(string_pool + 2074LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 656LL)), -1); else env->CallNonvirtualVoidMethod(cstack4.l, (cclasses[32]), (cmethods[28]), cstack5.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 4
        // INVOKEVIRTUAL javax/crypto/Cipher.init(ILjava/security/Key;Ljava/security/spec/AlgorithmParameterSpec;)V; Stack: 4
        if (!cclasses[29] || env->IsSameObject(cclasses[29], NULL)) { cclasses_mtx[29].lock(); if (!cclasses[29] || env->IsSameObject(cclasses[29], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[54]))) { cclasses[29] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[29].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[29]) { cmethods[29] = env->GetMethodID((cclasses[29]), ((char *)(string_pool + 2150LL)), ((char *)(string_pool + 2155LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 2222LL)), -1); else env->CallVoidMethod(cstack0.l, (cmethods[29]), cstack1.i, cstack2.l, cstack3.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // ICONST_3; Stack: 0
        cstack0.i = 3;
        // New stack: 1
        // ANEWARRAY java/lang/String; Stack: 1
        if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { cclasses_mtx[1].lock(); if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[1]))) { cclasses[1] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[1].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (cstack0.i < 0) utils::throw_re(env, ((char *)(string_pool + 210LL)), ((char *)(string_pool + 247LL)), -1); else { cstack0.l = env->NewObjectArray(cstack0.i, (cclasses[1]), nullptr); refs.insert(cstack0.l); } 
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
        // LDC \u0094\u0088S\u0019v\u007f\u0095\u00d4\u0003\u0085\u000c\u001e\u0096YY\u0017&Vz\u00b63\u00c1\u00da\u00f3@W"$\u001d\u009e3N\u00d6]\u00ca\u00c1$\u00d8\u0089\u0089~\u00c2S\u00ea\u00b4\u00d1D\u0006yV\u0017a?\u009c\u00c3`u\u00a6u\u00e9!\u00ec\u00a1\u0003\u0097{\u0083\u0006\u0003h\u009fl\u0000\u0009\u00d3\u008b\u0012&\u00ecfQ\u00b9\u008f*\u001f\u0000u\u0002\u00d3(\u00b8\u00c0\u009fq\u0006o\u00daiE\u00b9\u000c\u009a\u00b9\u001eK/\u0019%\u00e8\u00e9\\u008b\u000f$\u00fd\u00e3\u00a0\u00cfNp\u00a3\u000e\u0005[\u0001C\u009f\u0017\u009a%; Stack: 0
        cstack0.l = (cstrings[59]);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // ASTORE 11; Stack: 2
        clocal11.l = cstack1.l; refs.insert(cstack1.l);
        // New stack: 1
        // INVOKEVIRTUAL java/lang/String.length()I; Stack: 1
        if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { cclasses_mtx[1].lock(); if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[1]))) { cclasses[1] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[1].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[30]) { cmethods[30] = env->GetMethodID((cclasses[1]), ((char *)(string_pool + 2245LL)), ((char *)(string_pool + 2252LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 2256LL)), -1); else cstack0.i = env->CallIntMethod(cstack0.l, (cmethods[30])); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // ISTORE 13; Stack: 1
        clocal13.i = cstack0.i;
        // New stack: 0
        // BIPUSH 24; Stack: 0
        cstack0.i = (jint) 24;
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
        // LABEL L10; Stack: 0
        L10: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // FRAME FULL L: [0, 0, 0, 0, 0, 0, 0, javax/crypto/Cipher, 1, 1, 1, java/lang/String, 1, 1, [Ljava/lang/String;, 0, 4] S: []; Stack: 0
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
        if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { cclasses_mtx[1].lock(); if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[1]))) { cclasses[1] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[1].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[31]) { cmethods[31] = env->GetMethodID((cclasses[1]), ((char *)(string_pool + 2278LL)), ((char *)(string_pool + 2288LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 481LL)), -1); else { cstack0.l = env->CallObjectMethod(cstack0.l, (cmethods[31]), cstack1.i, cstack2.i); refs.insert(cstack0.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // ICONST_M1; Stack: 1
        cstack1.i = -1;
        // New stack: 2
        // GOTO L11; Stack: 2
        goto L11;
        // New stack: 2
        // LABEL L12; Stack: 2
        L12: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 2
        // FRAME FULL L: [0, 0, 0, 0, 0, 0, 0, javax/crypto/Cipher, 1, 1, 1, java/lang/String, 1, 1, [Ljava/lang/String;, [B, 4] S: [java/lang/String]; Stack: 2
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
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 1072LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack0.l, cstack1.i, cstack2.l); } 
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
        // IF_ICMPGE L13; Stack: 2
        if (cstack0.i >= cstack1.i) goto L13;
        // New stack: 0
        // ALOAD 11; Stack: 0
        cstack0.l = clocal11.l; refs.insert(cstack0.l);
        // New stack: 1
        // ILOAD 9; Stack: 1
        cstack1.i = clocal9.i;
        // New stack: 2
        // INVOKEVIRTUAL java/lang/String.charAt(I)C; Stack: 2
        if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { cclasses_mtx[1].lock(); if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[1]))) { cclasses[1] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[1].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[32]) { cmethods[32] = env->GetMethodID((cclasses[1]), ((char *)(string_pool + 2311LL)), ((char *)(string_pool + 2318LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 2323LL)), -1); else cstack0.i = (jint) env->CallCharMethod(cstack0.l, (cmethods[32]), cstack1.i); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // ISTORE 10; Stack: 1
        clocal10.i = cstack0.i;
        // New stack: 0
        // GOTO L10; Stack: 0
        goto L10;
        // New stack: 0
        // LABEL L13; Stack: 0
        L13: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // FRAME SAME L: null S: null; Stack: 0
        refs.erase(clocal7.l); refs.erase(clocal11.l); refs.erase(clocal14.l); refs.erase(clocal15.l); 
        utils::clear_refs(env, refs);
        // New stack: 0
        // ALOAD 14; Stack: 0
        cstack0.l = clocal14.l; refs.insert(cstack0.l);
        // New stack: 1
        // PUTSTATIC dev/sakura/L3MonKe_h.b [Ljava/lang/String;; Stack: 1
        if (!cclasses[4]  || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cfields[2]) { cfields[2] = env->GetStaticFieldID((cclasses[4]), ((char *)(string_pool + 2346LL)), ((char *)(string_pool + 759LL))); if (env->ExceptionCheck()) { return (void) 0; }  } env->SetStaticObjectField((cclasses[4]), (cfields[2]), cstack0.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // ICONST_3; Stack: 0
        cstack0.i = 3;
        // New stack: 1
        // ANEWARRAY java/lang/String; Stack: 1
        if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { cclasses_mtx[1].lock(); if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[1]))) { cclasses[1] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[1].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (cstack0.i < 0) utils::throw_re(env, ((char *)(string_pool + 210LL)), ((char *)(string_pool + 247LL)), -1); else { cstack0.l = env->NewObjectArray(cstack0.i, (cclasses[1]), nullptr); refs.insert(cstack0.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // PUTSTATIC dev/sakura/L3MonKe_h.c [Ljava/lang/String;; Stack: 1
        if (!cclasses[4]  || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cfields[3]) { cfields[3] = env->GetStaticFieldID((cclasses[4]), ((char *)(string_pool + 2348LL)), ((char *)(string_pool + 759LL))); if (env->ExceptionCheck()) { return (void) 0; }  } env->SetStaticObjectField((cclasses[4]), (cfields[3]), cstack0.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // SIPUSH 8876; Stack: 0
        cstack0.i = (jint) 8876;
        // New stack: 1
        // LDC 2119606007340258953; Stack: 1
        cstack1.j = 2119606007340258953LL;
        // New stack: 3
        // LLOAD 16; Stack: 3
        cstack3.j = clocal16.j;
        // New stack: 5
        // LXOR; Stack: 5
        cstack1.j = cstack1.j ^ cstack3.j;
        // New stack: 3
        // LABEL L1; Stack: 3
        L1: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 3
        // ICONST_1; Stack: 3
        cstack3.i = 1;
        // New stack: 4
        // ANEWARRAY java/lang/Object; Stack: 4
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (cstack3.i < 0) utils::throw_re(env, ((char *)(string_pool + 210LL)), ((char *)(string_pool + 247LL)), -1); else { cstack3.l = env->NewObjectArray(cstack3.i, (cclasses[5]), nullptr); refs.insert(cstack3.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 4
        // DUP; Stack: 4
        cstack4 = cstack3;
        // New stack: 5
        // LDC Ldev/sakura/L3MonKe_h;; Stack: 5
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } cstack5.l = (cclasses[6]);
        // New stack: 6
        // SWAP; Stack: 6
        std::swap(cstack5, cstack4);
        // New stack: 6
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.7353569731942222.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 6
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } cstack6.l = lookup;
        // New stack: 7
        // LDC Ldev/sakura/L3MonKe_h;; Stack: 7
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } cstack7.l = (cclasses[6]);
        // New stack: 8
        // LDC a; Stack: 8
        cstack8.l = (cstrings[7]);
        // New stack: 9
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;; Stack: 9
        cstack9.l = (cstrings[8]);
        // New stack: 10
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.7353569731942222.a()Ljava/lang/ClassLoader;; Stack: 10
        cstack10.l = classloader;
        // New stack: 11
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 11
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (!cmethods[2]) { cmethods[2] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 272LL)), ((char *)(string_pool + 299LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }  } cstack9.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[2]), cstack9.l, cstack10.l); refs.insert(cstack9.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 10
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 10
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[10]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (!cmethods[3]) { cmethods[3] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 372LL)), ((char *)(string_pool + 383LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }  } if (cstack6.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 481LL)), -1); else { cstack6.l = env->CallObjectMethod(cstack6.l, (cmethods[3]), cstack7.l, cstack8.l, cstack9.l); refs.insert(cstack6.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 7
        // SWAP; Stack: 7
        std::swap(cstack6, cstack5);
        // New stack: 7
        // LDC m; Stack: 7
        cstack7.l = (cstrings[34]);
        // New stack: 8
        // SWAP; Stack: 8
        std::swap(cstack7, cstack6);
        // New stack: 8
        // LDC (IJ)Ljava/lang/String;; Stack: 8
        cstack8.l = (cstrings[35]);
        // New stack: 9
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.7353569731942222.a()Ljava/lang/ClassLoader;; Stack: 9
        cstack9.l = classloader;
        // New stack: 10
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 10
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (!cmethods[2]) { cmethods[2] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 272LL)), ((char *)(string_pool + 299LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }  } cstack8.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[2]), cstack8.l, cstack9.l); refs.insert(cstack8.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 9
        // SWAP; Stack: 9
        std::swap(cstack8, cstack7);
        // New stack: 9
        // LDC 0; Stack: 9
        cstack9.i = 0;
        // New stack: 10
        // ANEWARRAY java/lang/Object; Stack: 10
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (cstack9.i < 0) utils::throw_re(env, ((char *)(string_pool + 210LL)), ((char *)(string_pool + 247LL)), -1); else { cstack9.l = env->NewObjectArray(cstack9.i, (cclasses[5]), nullptr); refs.insert(cstack9.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 10
        // CHECKCAST java/lang/Object; Stack: 10
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (cstack9.l != nullptr && !env->IsInstanceOf(cstack9.l, (cclasses[5]))) { utils::throw_re(env, ((char *)(string_pool + 109LL)), (std::string(((char *)(string_pool + 138LL))) + std::string(((char *)(string_pool + 506LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } 
        // New stack: 10
        // SWAP; Stack: 10
        std::swap(cstack9, cstack8);
        // New stack: 10
        // INVOKESTATIC native/magic/1/linkcallsite/obfuscator0.7353569731942222.a(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/invoke/MemberName;; Stack: 10
        cstack4.l = utils::link_call_site(env, cstack4.l, cstack5.l, cstack6.l, cstack7.l, cstack8.l, cstack9.l); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 5
        // POP; Stack: 5
        ;
        // New stack: 4
        // ICONST_0; Stack: 4
        cstack4.i = 0;
        // New stack: 5
        // AALOAD; Stack: 5
        if (cstack3.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 98LL)), -1); else { cstack3.l = env->GetObjectArrayElement((jobjectArray) cstack3.l, cstack4.i); refs.insert(cstack3.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 4
        // DUP; Stack: 4
        cstack4 = cstack3;
        // New stack: 5
        // INSTANCEOF java/lang/invoke/CallSite; Stack: 5
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } cstack4.i = cstack4.l == nullptr ? false : env->IsInstanceOf(cstack4.l, (cclasses[10]));
        // New stack: 5
        // IFEQ L14; Stack: 5
        if (cstack4.i == 0) goto L14;
        // New stack: 4
        // INVOKEINTERFACE java/lang/invoke/CallSite.getTarget()Ljava/lang/invoke/MethodHandle;; Stack: 4
        if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { cclasses_mtx[10].lock(); if (!cclasses[10] || env->IsSameObject(cclasses[10], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[13]))) { cclasses[10] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[10].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (!cmethods[4]) { cmethods[4] = env->GetMethodID((cclasses[10]), ((char *)(string_pool + 523LL)), ((char *)(string_pool + 533LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }  } if (cstack3.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 567LL)), -1); else { cstack3.l = env->CallObjectMethod(cstack3.l, (cmethods[4])); refs.insert(cstack3.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 4
        // LABEL L14; Stack: 4
        L14: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; }
        // New stack: 4
        // FRAME FULL L: [0, 0, 0, 0, 0, 0, 0, javax/crypto/Cipher, 1, 1, 1, java/lang/String, 1, 1, [Ljava/lang/String;, [B, 4] S: [1, 4, java/lang/Object]; Stack: 4
        refs.erase(cstack3.l); 
        refs.erase(clocal7.l); refs.erase(clocal11.l); refs.erase(clocal14.l); refs.erase(clocal15.l); 
        utils::clear_refs(env, refs);
        // New stack: 4
        // CHECKCAST java/lang/invoke/MethodHandle; Stack: 4
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } if (cstack3.l != nullptr && !env->IsInstanceOf(cstack3.l, (cclasses[11]))) { utils::throw_re(env, ((char *)(string_pool + 109LL)), (std::string(((char *)(string_pool + 138LL))) + std::string(((char *)(string_pool + 594LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_1; } } 
        // New stack: 4
        // GOTO L15; Stack: 4
        goto L15;
        // New stack: 4
        // LABEL L2; Stack: 4
        L2: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 4
        // FRAME SAME1 L: null S: [java/lang/Throwable]; Stack: 4
        refs.erase(cstack0.l); 
        refs.erase(clocal7.l); refs.erase(clocal11.l); refs.erase(clocal14.l); refs.erase(clocal15.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // INSTANCEOF java/lang/BootstrapMethodError; Stack: 2
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } cstack1.i = cstack1.l == nullptr ? false : env->IsInstanceOf(cstack1.l, (cclasses[12]));
        // New stack: 2
        // IFNE L16; Stack: 2
        if (cstack1.i != 0) goto L16;
        // New stack: 1
        // NEW java/lang/BootstrapMethodError; Stack: 1
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (jobject obj = env->AllocObject((cclasses[12]))) { cstack1.l = obj; refs.insert(obj); } 
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
        if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { cclasses_mtx[12].lock(); if (!cclasses[12] || env->IsSameObject(cclasses[12], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[15]))) { cclasses[12] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[12].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[5]) { cmethods[5] = env->GetMethodID((cclasses[12]), ((char *)(string_pool + 624LL)), ((char *)(string_pool + 631LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 656LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[12]), (cmethods[5]), cstack2.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // LABEL L16; Stack: 1
        L16: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // FRAME SAME1 L: null S: [java/lang/Object]; Stack: 1
        refs.erase(cstack0.l); 
        refs.erase(clocal7.l); refs.erase(clocal11.l); refs.erase(clocal14.l); refs.erase(clocal15.l); 
        utils::clear_refs(env, refs);
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 679LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // LABEL L15; Stack: 0
        L15: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // FRAME FULL L: [0, 0, 0, 0, 0, 0, 0, javax/crypto/Cipher, 1, 1, 1, java/lang/String, 1, 1, [Ljava/lang/String;, [B, 4] S: [1, 4, java/lang/invoke/MethodHandle]; Stack: 0
        refs.erase(cstack3.l); 
        refs.erase(clocal7.l); refs.erase(clocal11.l); refs.erase(clocal14.l); refs.erase(clocal15.l); 
        utils::clear_refs(env, refs);
        // New stack: 4
        // INVOKESTATIC native/magic/1/invoke/obfuscator0.7353569731942222.a(IJLjava/lang/invoke/MethodHandle;)Ljava/lang/String;; Stack: 4
        if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { cclasses_mtx[13].lock(); if (!cclasses[13] || env->IsSameObject(cclasses[13], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[16]))) { cclasses[13] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[13].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[12]) { cmethods[12] = env->GetStaticMethodID((cclasses[13]), ((char *)(string_pool + 783LL)), ((char *)(string_pool + 798LL))); if (env->ExceptionCheck()) { return (void) 0; }  } cstack0.l = env->CallStaticObjectMethod((cclasses[13]), (cmethods[12]), cstack0.i, cstack1.j, cstack3.l); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // CHECKCAST java/lang/String; Stack: 1
        if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { cclasses_mtx[1].lock(); if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[1]))) { cclasses[1] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[1].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (cstack0.l != nullptr && !env->IsInstanceOf(cstack0.l, (cclasses[1]))) { utils::throw_re(env, ((char *)(string_pool + 109LL)), (std::string(((char *)(string_pool + 138LL))) + std::string(((char *)(string_pool + 852LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (void) 0; } } 
        // New stack: 1
        // PUTSTATIC dev/sakura/L3MonKe_h.L3MonKe_n Ljava/lang/String;; Stack: 1
        if (!cclasses[4]  || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cfields[4]) { cfields[4] = env->GetStaticFieldID((cclasses[4]), ((char *)(string_pool + 8596LL)), ((char *)(string_pool + 17905LL))); if (env->ExceptionCheck()) { return (void) 0; }  } env->SetStaticObjectField((cclasses[4]), (cfields[4]), cstack0.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // GOTO L17; Stack: 0
        goto L17;
        // New stack: 0
        // LABEL L11; Stack: 0
        L11: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // FRAME FULL L: [0, 0, 0, 0, 0, 0, 0, javax/crypto/Cipher, 1, 1, 1, java/lang/String, 1, 1, [Ljava/lang/String;, 0, 4] S: [java/lang/String, 1]; Stack: 0
        refs.erase(cstack0.l); 
        refs.erase(clocal7.l); refs.erase(clocal11.l); refs.erase(clocal14.l); 
        utils::clear_refs(env, refs);
        // New stack: 2
        // SWAP; Stack: 2
        std::swap(cstack1, cstack0);
        // New stack: 2
        // LDC ISO-8859-1; Stack: 2
        cstack2.l = (cstrings[60]);
        // New stack: 3
        // INVOKEVIRTUAL java/lang/String.getBytes(Ljava/lang/String;)[B; Stack: 3
        if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { cclasses_mtx[1].lock(); if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[1]))) { cclasses[1] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[1].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[33]) { cmethods[33] = env->GetMethodID((cclasses[1]), ((char *)(string_pool + 2350LL)), ((char *)(string_pool + 2359LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 481LL)), -1); else { cstack1.l = env->CallObjectMethod(cstack1.l, (cmethods[33]), cstack2.l); refs.insert(cstack1.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 2
        // ALOAD 7; Stack: 2
        cstack2.l = clocal7.l; refs.insert(cstack2.l);
        // New stack: 3
        // SWAP; Stack: 3
        std::swap(cstack2, cstack1);
        // New stack: 3
        // INVOKEVIRTUAL javax/crypto/Cipher.doFinal([B)[B; Stack: 3
        if (!cclasses[29] || env->IsSameObject(cclasses[29], NULL)) { cclasses_mtx[29].lock(); if (!cclasses[29] || env->IsSameObject(cclasses[29], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[54]))) { cclasses[29] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[29].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[34]) { cmethods[34] = env->GetMethodID((cclasses[29]), ((char *)(string_pool + 2382LL)), ((char *)(string_pool + 2390LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 481LL)), -1); else { cstack1.l = env->CallObjectMethod(cstack1.l, (cmethods[34]), cstack2.l); refs.insert(cstack1.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 2
        // ASTORE 15; Stack: 2
        clocal15.l = cstack1.l; refs.insert(cstack1.l);
        // New stack: 1
        // ALOAD 15; Stack: 1
        cstack1.l = clocal15.l; refs.insert(cstack1.l);
        // New stack: 2
        // INVOKESTATIC dev/sakura/L3MonKe_h.a([B)Ljava/lang/String;; Stack: 2
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[35]) { cmethods[35] = env->GetStaticMethodID((cclasses[4]), ((char *)(string_pool + 206LL)), ((char *)(string_pool + 2397LL))); if (env->ExceptionCheck()) { return (void) 0; }  } cstack1.l = env->CallStaticObjectMethod((cclasses[4]), (cmethods[35]), cstack1.l); refs.insert(cstack1.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 2
        // INVOKEVIRTUAL java/lang/String.intern()Ljava/lang/String;; Stack: 2
        if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { cclasses_mtx[1].lock(); if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[1]))) { cclasses[1] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[1].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[36]) { cmethods[36] = env->GetMethodID((cclasses[1]), ((char *)(string_pool + 2420LL)), ((char *)(string_pool + 2427LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 481LL)), -1); else { cstack1.l = env->CallObjectMethod(cstack1.l, (cmethods[36])); refs.insert(cstack1.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 2
        // SWAP; Stack: 2
        std::swap(cstack1, cstack0);
        // New stack: 2
        // POP; Stack: 2
        ;
        // New stack: 1
        // GOTO L12; Stack: 1
        goto L12;
        // New stack: 1
        // LABEL L17; Stack: 1
        L17: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // FRAME FULL L: [0, 0, 0, 0, 0, 0, 0, javax/crypto/Cipher, 1, 1, 1, java/lang/String, 1, 1, [Ljava/lang/String;, [B, 4] S: []; Stack: 1
        refs.erase(clocal7.l); refs.erase(clocal11.l); refs.erase(clocal14.l); refs.erase(clocal15.l); 
        utils::clear_refs(env, refs);
        // New stack: 0
        // LDC DES/CBC/NoPadding; Stack: 0
        cstack0.l = (cstrings[61]);
        // New stack: 1
        // INVOKESTATIC javax/crypto/Cipher.getInstance(Ljava/lang/String;)Ljavax/crypto/Cipher;; Stack: 1
        if (!cclasses[29] || env->IsSameObject(cclasses[29], NULL)) { cclasses_mtx[29].lock(); if (!cclasses[29] || env->IsSameObject(cclasses[29], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[54]))) { cclasses[29] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[29].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[24]) { cmethods[24] = env->GetStaticMethodID((cclasses[29]), ((char *)(string_pool + 1927LL)), ((char *)(string_pool + 1939LL))); if (env->ExceptionCheck()) { return (void) 0; }  } cstack0.l = env->CallStaticObjectMethod((cclasses[29]), (cmethods[24]), cstack0.l); refs.insert(cstack0.l); 
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
        cstack2.l = (cstrings[55]);
        // New stack: 3
        // INVOKESTATIC javax/crypto/SecretKeyFactory.getInstance(Ljava/lang/String;)Ljavax/crypto/SecretKeyFactory;; Stack: 3
        if (!cclasses[30] || env->IsSameObject(cclasses[30], NULL)) { cclasses_mtx[30].lock(); if (!cclasses[30] || env->IsSameObject(cclasses[30], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[56]))) { cclasses[30] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[30].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[25]) { cmethods[25] = env->GetStaticMethodID((cclasses[30]), ((char *)(string_pool + 1927LL)), ((char *)(string_pool + 1981LL))); if (env->ExceptionCheck()) { return (void) 0; }  } cstack2.l = env->CallStaticObjectMethod((cclasses[30]), (cmethods[25]), cstack2.l); refs.insert(cstack2.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 3
        // BIPUSH 8; Stack: 3
        cstack3.i = (jint) 8;
        // New stack: 4
        // NEWARRAY 8; Stack: 4
        if (cstack3.i < 0) utils::throw_re(env, ((char *)(string_pool + 210LL)), ((char *)(string_pool + 2033LL)), -1); else { cstack3.l = env->NewByteArray(cstack3.i); refs.insert(cstack3.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 4
        // DUP; Stack: 4
        cstack4 = cstack3;
        // New stack: 5
        // ICONST_0; Stack: 5
        cstack5.i = 0;
        // New stack: 6
        // LLOAD 16; Stack: 6
        cstack6.j = clocal16.j;
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
        if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 2062LL)), -1); else { utils::bastore(env, (jarray) cstack4.l, cstack5.i, cstack6.i); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 4
        // ICONST_1; Stack: 4
        cstack4.i = 1;
        // New stack: 5
        // ISTORE 3; Stack: 5
        clocal3.i = cstack4.i;
        // New stack: 4
        // LABEL L18; Stack: 4
        L18: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 4
        // FRAME FULL L: [0, 0, javax/crypto/Cipher, 1, 0, 0, 0, javax/crypto/Cipher, 1, 1, 1, java/lang/String, 1, 1, [Ljava/lang/String;, [B, 4] S: [javax/crypto/Cipher, 1, javax/crypto/SecretKeyFactory, [B]; Stack: 4
        refs.erase(cstack0.l); refs.erase(cstack2.l); refs.erase(cstack3.l); 
        refs.erase(clocal2.l); refs.erase(clocal7.l); refs.erase(clocal11.l); refs.erase(clocal14.l); refs.erase(clocal15.l); 
        utils::clear_refs(env, refs);
        // New stack: 4
        // ILOAD 3; Stack: 4
        cstack4.i = clocal3.i;
        // New stack: 5
        // BIPUSH 8; Stack: 5
        cstack5.i = (jint) 8;
        // New stack: 6
        // IF_ICMPGE L19; Stack: 6
        if (cstack4.i >= cstack5.i) goto L19;
        // New stack: 4
        // DUP; Stack: 4
        cstack4 = cstack3;
        // New stack: 5
        // ILOAD 3; Stack: 5
        cstack5.i = clocal3.i;
        // New stack: 6
        // LLOAD 16; Stack: 6
        cstack6.j = clocal16.j;
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
        if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 2062LL)), -1); else { utils::bastore(env, (jarray) cstack4.l, cstack5.i, cstack6.i); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 4
        // IINC 3 1; Stack: 4
        clocal3.i += 1;
        // New stack: 4
        // GOTO L18; Stack: 4
        goto L18;
        // New stack: 4
        // LABEL L19; Stack: 4
        L19: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 4
        // FRAME FULL L: [0, 0, javax/crypto/Cipher, 1, 0, 0, 0, javax/crypto/Cipher, 1, 1, 1, java/lang/String, 1, 1, [Ljava/lang/String;, [B, 4] S: [javax/crypto/Cipher, 1, javax/crypto/SecretKeyFactory, [B]; Stack: 4
        refs.erase(cstack0.l); refs.erase(cstack2.l); refs.erase(cstack3.l); 
        refs.erase(clocal2.l); refs.erase(clocal7.l); refs.erase(clocal11.l); refs.erase(clocal14.l); refs.erase(clocal15.l); 
        utils::clear_refs(env, refs);
        // New stack: 4
        // NEW javax/crypto/spec/DESKeySpec; Stack: 4
        if (!cclasses[31] || env->IsSameObject(cclasses[31], NULL)) { cclasses_mtx[31].lock(); if (!cclasses[31] || env->IsSameObject(cclasses[31], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[57]))) { cclasses[31] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[31].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (jobject obj = env->AllocObject((cclasses[31]))) { cstack4.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 5
        // DUP_X1; Stack: 5
        cstack5 = cstack4; cstack4 = cstack3; cstack3 = cstack5;
        // New stack: 6
        // SWAP; Stack: 6
        std::swap(cstack5, cstack4);
        // New stack: 6
        // INVOKESPECIAL javax/crypto/spec/DESKeySpec.<init>([B)V; Stack: 6
        if (!cclasses[31] || env->IsSameObject(cclasses[31], NULL)) { cclasses_mtx[31].lock(); if (!cclasses[31] || env->IsSameObject(cclasses[31], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[57]))) { cclasses[31] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[31].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[26]) { cmethods[26] = env->GetMethodID((cclasses[31]), ((char *)(string_pool + 624LL)), ((char *)(string_pool + 2074LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 656LL)), -1); else env->CallNonvirtualVoidMethod(cstack4.l, (cclasses[31]), (cmethods[26]), cstack5.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 4
        // INVOKEVIRTUAL javax/crypto/SecretKeyFactory.generateSecret(Ljava/security/spec/KeySpec;)Ljavax/crypto/SecretKey;; Stack: 4
        if (!cclasses[30] || env->IsSameObject(cclasses[30], NULL)) { cclasses_mtx[30].lock(); if (!cclasses[30] || env->IsSameObject(cclasses[30], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[56]))) { cclasses[30] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[30].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[27]) { cmethods[27] = env->GetMethodID((cclasses[30]), ((char *)(string_pool + 2080LL)), ((char *)(string_pool + 2095LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack2.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 481LL)), -1); else { cstack2.l = env->CallObjectMethod(cstack2.l, (cmethods[27]), cstack3.l); refs.insert(cstack2.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 3
        // NEW javax/crypto/spec/IvParameterSpec; Stack: 3
        if (!cclasses[32] || env->IsSameObject(cclasses[32], NULL)) { cclasses_mtx[32].lock(); if (!cclasses[32] || env->IsSameObject(cclasses[32], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[58]))) { cclasses[32] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[32].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (jobject obj = env->AllocObject((cclasses[32]))) { cstack3.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 4
        // DUP; Stack: 4
        cstack4 = cstack3;
        // New stack: 5
        // BIPUSH 8; Stack: 5
        cstack5.i = (jint) 8;
        // New stack: 6
        // NEWARRAY 8; Stack: 6
        if (cstack5.i < 0) utils::throw_re(env, ((char *)(string_pool + 210LL)), ((char *)(string_pool + 2033LL)), -1); else { cstack5.l = env->NewByteArray(cstack5.i); refs.insert(cstack5.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 6
        // INVOKESPECIAL javax/crypto/spec/IvParameterSpec.<init>([B)V; Stack: 6
        if (!cclasses[32] || env->IsSameObject(cclasses[32], NULL)) { cclasses_mtx[32].lock(); if (!cclasses[32] || env->IsSameObject(cclasses[32], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[58]))) { cclasses[32] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[32].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[28]) { cmethods[28] = env->GetMethodID((cclasses[32]), ((char *)(string_pool + 624LL)), ((char *)(string_pool + 2074LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 656LL)), -1); else env->CallNonvirtualVoidMethod(cstack4.l, (cclasses[32]), (cmethods[28]), cstack5.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 4
        // INVOKEVIRTUAL javax/crypto/Cipher.init(ILjava/security/Key;Ljava/security/spec/AlgorithmParameterSpec;)V; Stack: 4
        if (!cclasses[29] || env->IsSameObject(cclasses[29], NULL)) { cclasses_mtx[29].lock(); if (!cclasses[29] || env->IsSameObject(cclasses[29], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[54]))) { cclasses[29] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[29].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[29]) { cmethods[29] = env->GetMethodID((cclasses[29]), ((char *)(string_pool + 2150LL)), ((char *)(string_pool + 2155LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 2222LL)), -1); else env->CallVoidMethod(cstack0.l, (cmethods[29]), cstack1.i, cstack2.l, cstack3.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // LDC 3016649512829207097; Stack: 0
        cstack0.j = 3016649512829207097LL;
        // New stack: 2
        // ICONST_M1; Stack: 2
        cstack2.i = -1;
        // New stack: 3
        // GOTO L20; Stack: 3
        goto L20;
        // New stack: 3
        // LABEL L21; Stack: 3
        L21: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 3
        // FRAME FULL L: [0, 0, javax/crypto/Cipher, 1, 4, [B, javax/crypto/Cipher, 1, 1, 1, java/lang/String, 1, 1, [Ljava/lang/String;, [B, 4] S: [4]; Stack: 3
        refs.erase(clocal2.l); refs.erase(clocal6.l); refs.erase(clocal7.l); refs.erase(clocal11.l); refs.erase(clocal14.l); refs.erase(clocal15.l); 
        utils::clear_refs(env, refs);
        // New stack: 2
        // LSTORE 0; Stack: 2
        clocal0.j = cstack0.j;
        // New stack: 0
        // LLOAD 0; Stack: 0
        cstack0.j = clocal0.j;
        // New stack: 2
        // DUP2; Stack: 2
        cstack2 = cstack0; cstack3 = cstack1;
        // New stack: 4
        // PUTSTATIC dev/sakura/L3MonKe_h.L3MonKe_Q J; Stack: 4
        if (!cclasses[4]  || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cfields[5]) { cfields[5] = env->GetStaticFieldID((cclasses[4]), ((char *)(string_pool + 19184LL)), ((char *)(string_pool + 208LL))); if (env->ExceptionCheck()) { return (void) 0; }  } env->SetStaticLongField((cclasses[4]), (cfields[5]), cstack2.j); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 2
        // POP2; Stack: 2
        ;
        // New stack: 0
        // GOTO L22; Stack: 0
        goto L22;
        // New stack: 0
        // LABEL L20; Stack: 0
        L20: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // FRAME FULL L: [0, 0, javax/crypto/Cipher, 1, 0, 0, 0, javax/crypto/Cipher, 1, 1, 1, java/lang/String, 1, 1, [Ljava/lang/String;, [B, 4] S: [4, 1]; Stack: 0
        refs.erase(clocal2.l); refs.erase(clocal7.l); refs.erase(clocal11.l); refs.erase(clocal14.l); refs.erase(clocal15.l); 
        utils::clear_refs(env, refs);
        // New stack: 3
        // DUP_X2; Stack: 3
        cstack3 = cstack2; cstack2 = cstack1; cstack1 = cstack0; cstack0 = cstack3;
        // New stack: 4
        // POP; Stack: 4
        ;
        // New stack: 3
        // LSTORE 4; Stack: 3
        clocal4.j = cstack1.j;
        // New stack: 1
        // BIPUSH 8; Stack: 1
        cstack1.i = (jint) 8;
        // New stack: 2
        // NEWARRAY 8; Stack: 2
        if (cstack1.i < 0) utils::throw_re(env, ((char *)(string_pool + 210LL)), ((char *)(string_pool + 2033LL)), -1); else { cstack1.l = env->NewByteArray(cstack1.i); refs.insert(cstack1.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 2
        // DUP; Stack: 2
        cstack2 = cstack1;
        // New stack: 3
        // ICONST_0; Stack: 3
        cstack3.i = 0;
        // New stack: 4
        // LLOAD 4; Stack: 4
        cstack4.j = clocal4.j;
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
        if (cstack2.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 2062LL)), -1); else { utils::bastore(env, (jarray) cstack2.l, cstack3.i, cstack4.i); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 2
        // DUP; Stack: 2
        cstack2 = cstack1;
        // New stack: 3
        // ICONST_1; Stack: 3
        cstack3.i = 1;
        // New stack: 4
        // LLOAD 4; Stack: 4
        cstack4.j = clocal4.j;
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
        if (cstack2.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 2062LL)), -1); else { utils::bastore(env, (jarray) cstack2.l, cstack3.i, cstack4.i); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 2
        // DUP; Stack: 2
        cstack2 = cstack1;
        // New stack: 3
        // ICONST_2; Stack: 3
        cstack3.i = 2;
        // New stack: 4
        // LLOAD 4; Stack: 4
        cstack4.j = clocal4.j;
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
        if (cstack2.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 2062LL)), -1); else { utils::bastore(env, (jarray) cstack2.l, cstack3.i, cstack4.i); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 2
        // DUP; Stack: 2
        cstack2 = cstack1;
        // New stack: 3
        // ICONST_3; Stack: 3
        cstack3.i = 3;
        // New stack: 4
        // LLOAD 4; Stack: 4
        cstack4.j = clocal4.j;
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
        if (cstack2.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 2062LL)), -1); else { utils::bastore(env, (jarray) cstack2.l, cstack3.i, cstack4.i); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 2
        // DUP; Stack: 2
        cstack2 = cstack1;
        // New stack: 3
        // ICONST_4; Stack: 3
        cstack3.i = 4;
        // New stack: 4
        // LLOAD 4; Stack: 4
        cstack4.j = clocal4.j;
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
        if (cstack2.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 2062LL)), -1); else { utils::bastore(env, (jarray) cstack2.l, cstack3.i, cstack4.i); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 2
        // DUP; Stack: 2
        cstack2 = cstack1;
        // New stack: 3
        // ICONST_5; Stack: 3
        cstack3.i = 5;
        // New stack: 4
        // LLOAD 4; Stack: 4
        cstack4.j = clocal4.j;
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
        if (cstack2.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 2062LL)), -1); else { utils::bastore(env, (jarray) cstack2.l, cstack3.i, cstack4.i); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 2
        // DUP; Stack: 2
        cstack2 = cstack1;
        // New stack: 3
        // BIPUSH 6; Stack: 3
        cstack3.i = (jint) 6;
        // New stack: 4
        // LLOAD 4; Stack: 4
        cstack4.j = clocal4.j;
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
        if (cstack2.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 2062LL)), -1); else { utils::bastore(env, (jarray) cstack2.l, cstack3.i, cstack4.i); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 2
        // DUP; Stack: 2
        cstack2 = cstack1;
        // New stack: 3
        // BIPUSH 7; Stack: 3
        cstack3.i = (jint) 7;
        // New stack: 4
        // LLOAD 4; Stack: 4
        cstack4.j = clocal4.j;
        // New stack: 6
        // L2I; Stack: 6
        cstack4.i = (jint) cstack4.j;
        // New stack: 5
        // I2B; Stack: 5
        cstack4.i = (jint) (jbyte) cstack4.i;
        // New stack: 5
        // BASTORE; Stack: 5
        if (cstack2.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 2062LL)), -1); else { utils::bastore(env, (jarray) cstack2.l, cstack3.i, cstack4.i); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 2
        // ALOAD 2; Stack: 2
        cstack2.l = clocal2.l; refs.insert(cstack2.l);
        // New stack: 3
        // SWAP; Stack: 3
        std::swap(cstack2, cstack1);
        // New stack: 3
        // INVOKEVIRTUAL javax/crypto/Cipher.doFinal([B)[B; Stack: 3
        if (!cclasses[29] || env->IsSameObject(cclasses[29], NULL)) { cclasses_mtx[29].lock(); if (!cclasses[29] || env->IsSameObject(cclasses[29], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[54]))) { cclasses[29] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[29].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[34]) { cmethods[34] = env->GetMethodID((cclasses[29]), ((char *)(string_pool + 2382LL)), ((char *)(string_pool + 2390LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 481LL)), -1); else { cstack1.l = env->CallObjectMethod(cstack1.l, (cmethods[34]), cstack2.l); refs.insert(cstack1.l); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 2
        // ASTORE 6; Stack: 2
        clocal6.l = cstack1.l; refs.insert(cstack1.l);
        // New stack: 1
        // ALOAD 6; Stack: 1
        cstack1.l = clocal6.l; refs.insert(cstack1.l);
        // New stack: 2
        // ICONST_0; Stack: 2
        cstack2.i = 0;
        // New stack: 3
        // BALOAD; Stack: 3
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 2479LL)), -1); else { cstack1.i = (jint) utils::baload(env, (jarray) cstack1.l, cstack2.i); } 
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
        // ALOAD 6; Stack: 3
        cstack3.l = clocal6.l; refs.insert(cstack3.l);
        // New stack: 4
        // ICONST_1; Stack: 4
        cstack4.i = 1;
        // New stack: 5
        // BALOAD; Stack: 5
        if (cstack3.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 2479LL)), -1); else { cstack3.i = (jint) utils::baload(env, (jarray) cstack3.l, cstack4.i); } 
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
        // ALOAD 6; Stack: 3
        cstack3.l = clocal6.l; refs.insert(cstack3.l);
        // New stack: 4
        // ICONST_2; Stack: 4
        cstack4.i = 2;
        // New stack: 5
        // BALOAD; Stack: 5
        if (cstack3.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 2479LL)), -1); else { cstack3.i = (jint) utils::baload(env, (jarray) cstack3.l, cstack4.i); } 
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
        // ALOAD 6; Stack: 3
        cstack3.l = clocal6.l; refs.insert(cstack3.l);
        // New stack: 4
        // ICONST_3; Stack: 4
        cstack4.i = 3;
        // New stack: 5
        // BALOAD; Stack: 5
        if (cstack3.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 2479LL)), -1); else { cstack3.i = (jint) utils::baload(env, (jarray) cstack3.l, cstack4.i); } 
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
        // ALOAD 6; Stack: 3
        cstack3.l = clocal6.l; refs.insert(cstack3.l);
        // New stack: 4
        // ICONST_4; Stack: 4
        cstack4.i = 4;
        // New stack: 5
        // BALOAD; Stack: 5
        if (cstack3.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 2479LL)), -1); else { cstack3.i = (jint) utils::baload(env, (jarray) cstack3.l, cstack4.i); } 
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
        // ALOAD 6; Stack: 3
        cstack3.l = clocal6.l; refs.insert(cstack3.l);
        // New stack: 4
        // ICONST_5; Stack: 4
        cstack4.i = 5;
        // New stack: 5
        // BALOAD; Stack: 5
        if (cstack3.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 2479LL)), -1); else { cstack3.i = (jint) utils::baload(env, (jarray) cstack3.l, cstack4.i); } 
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
        // ALOAD 6; Stack: 3
        cstack3.l = clocal6.l; refs.insert(cstack3.l);
        // New stack: 4
        // BIPUSH 6; Stack: 4
        cstack4.i = (jint) 6;
        // New stack: 5
        // BALOAD; Stack: 5
        if (cstack3.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 2479LL)), -1); else { cstack3.i = (jint) utils::baload(env, (jarray) cstack3.l, cstack4.i); } 
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
        // ALOAD 6; Stack: 3
        cstack3.l = clocal6.l; refs.insert(cstack3.l);
        // New stack: 4
        // BIPUSH 7; Stack: 4
        cstack4.i = (jint) 7;
        // New stack: 5
        // BALOAD; Stack: 5
        if (cstack3.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 2479LL)), -1); else { cstack3.i = (jint) utils::baload(env, (jarray) cstack3.l, cstack4.i); } 
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
        // GOTO L21; Stack: 2
        goto L21;
        // New stack: 2
        // LABEL L22; Stack: 2
        L22: if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 2
        // FRAME FULL L: [4, javax/crypto/Cipher, 1, 4, [B, javax/crypto/Cipher, 1, 1, 1, java/lang/String, 1, 1, [Ljava/lang/String;, [B, 4] S: []; Stack: 2
        refs.erase(clocal2.l); refs.erase(clocal6.l); refs.erase(clocal7.l); refs.erase(clocal11.l); refs.erase(clocal14.l); refs.erase(clocal15.l); 
        utils::clear_refs(env, refs);
        // New stack: 0
        // NEW java/util/concurrent/atomic/AtomicReference; Stack: 0
        if (!cclasses[33] || env->IsSameObject(cclasses[33], NULL)) { cclasses_mtx[33].lock(); if (!cclasses[33] || env->IsSameObject(cclasses[33], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[62]))) { cclasses[33] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[33].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (jobject obj = env->AllocObject((cclasses[33]))) { cstack0.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // LDC ; Stack: 2
        cstack2.l = (cstrings[21]);
        // New stack: 3
        // INVOKESPECIAL java/util/concurrent/atomic/AtomicReference.<init>(Ljava/lang/Object;)V; Stack: 3
        if (!cclasses[33] || env->IsSameObject(cclasses[33], NULL)) { cclasses_mtx[33].lock(); if (!cclasses[33] || env->IsSameObject(cclasses[33], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[62]))) { cclasses[33] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[33].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cmethods[37]) { cmethods[37] = env->GetMethodID((cclasses[33]), ((char *)(string_pool + 624LL)), ((char *)(string_pool + 19194LL))); if (env->ExceptionCheck()) { return (void) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 656LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[33]), (cmethods[37]), cstack2.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 1
        // PUTSTATIC dev/sakura/L3MonKe_h.L3MonKe_y Ljava/util/concurrent/atomic/AtomicReference;; Stack: 1
        if (!cclasses[4]  || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cfields[6]) { cfields[6] = env->GetStaticFieldID((cclasses[4]), ((char *)(string_pool + 1438LL)), ((char *)(string_pool + 19216LL))); if (env->ExceptionCheck()) { return (void) 0; }  } env->SetStaticObjectField((cclasses[4]), (cfields[6]), cstack0.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // RETURN; Stack: 0
        return;
        // New stack: 0
        return (void) 0;
        L_CATCH_0: if (env->IsInstanceOf(cstack0.l, (cclasses[0]))) { goto L4; }
        env->Throw((jthrowable) cstack0.l); return (void) 0;
        L_CATCH_1: if (env->IsInstanceOf(cstack0.l, (cclasses[0]))) { goto L2; }
        env->Throw((jthrowable) cstack0.l); return (void) 0;
    }
    
    // L3MonKe_i([Ljava/lang/String;)V
    void JNICALL __ngen_native_L3MonKe_i6(JNIEnv *env, jclass clazz, jarray arg0) {
        jobject classloader = utils::get_classloader_from_class(env, clazz);
        if (env->ExceptionCheck()) { return (void) 0; }
        if (classloader == nullptr) { env->FatalError(((char *)(string_pool + 47LL))); return (void) 0; }
    
        jobject lookup = nullptr;
        jvalue cstack0 = {};
        jvalue clocal0 = {};
        std::unordered_set<jobject> refs;
    
        clocal0.l = arg0; refs.insert(clocal0.l);
    
        // ALOAD 0; Stack: 0
        cstack0.l = clocal0.l; refs.insert(cstack0.l);
        // New stack: 1
        // PUTSTATIC dev/sakura/L3MonKe_h.L3MonKe_Z [Ljava/lang/String;; Stack: 1
        if (!cclasses[4]  || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { return (void) 0; } } if (!cfields[7]) { cfields[7] = env->GetStaticFieldID((cclasses[4]), ((char *)(string_pool + 19262LL)), ((char *)(string_pool + 759LL))); if (env->ExceptionCheck()) { return (void) 0; }  } env->SetStaticObjectField((cclasses[4]), (cfields[7]), cstack0.l); 
        if (env->ExceptionCheck()) { return (void) 0; }
        // New stack: 0
        // RETURN; Stack: 0
        return;
        // New stack: 0
        return (void) 0;
    }
    
    // L3MonKe_K()[Ljava/lang/String;
    jarray JNICALL __ngen_native_L3MonKe_K7(JNIEnv *env, jclass clazz) {
        jobject classloader = utils::get_classloader_from_class(env, clazz);
        if (env->ExceptionCheck()) { return (jarray) 0; }
        if (classloader == nullptr) { env->FatalError(((char *)(string_pool + 47LL))); return (jarray) 0; }
    
        jobject lookup = nullptr;
        jvalue cstack0 = {};
        std::unordered_set<jobject> refs;
    
    
        // GETSTATIC dev/sakura/L3MonKe_h.L3MonKe_Z [Ljava/lang/String;; Stack: 0
        if (!cclasses[4]  || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { return (jarray) 0; } } if (!cfields[7]) { cfields[7] = env->GetStaticFieldID((cclasses[4]), ((char *)(string_pool + 19262LL)), ((char *)(string_pool + 759LL))); if (env->ExceptionCheck()) { return (jarray) 0; }  } cstack0.l = env->GetStaticObjectField((cclasses[4]), (cfields[7])); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { return (jarray) 0; }
        // New stack: 1
        // ARETURN; Stack: 1
        return (jarray) cstack0.l;
        // New stack: 0
        return (jarray) 0;
    }
    
    // a([B)Ljava/lang/String;
    jobject JNICALL __ngen_native_a8(JNIEnv *env, jclass clazz, jarray arg0) {
        jobject classloader = utils::get_classloader_from_class(env, clazz);
        if (env->ExceptionCheck()) { return (jobject) 0; }
        if (classloader == nullptr) { env->FatalError(((char *)(string_pool + 47LL))); return (jobject) 0; }
    
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
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 1422LL)), -1); else cstack0.i = env->GetArrayLength((jarray) cstack0.l); 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // ISTORE 2; Stack: 2
        clocal2.i = cstack1.i;
        // New stack: 1
        // NEWARRAY 5; Stack: 1
        if (cstack0.i < 0) utils::throw_re(env, ((char *)(string_pool + 210LL)), ((char *)(string_pool + 2530LL)), -1); else { cstack0.l = env->NewCharArray(cstack0.i); refs.insert(cstack0.l); } 
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
        if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 2479LL)), -1); else { cstack1.i = (jint) utils::baload(env, (jarray) cstack1.l, cstack2.i); } 
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
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 2559LL)), -1); else { jchar temp = (jchar) cstack2.i; env->SetCharArrayRegion((jcharArray) cstack0.l, cstack1.i, 1, &temp); } 
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
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 2479LL)), -1); else { cstack0.i = (jint) utils::baload(env, (jarray) cstack0.l, cstack1.i); } 
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
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 2559LL)), -1); else { jchar temp = (jchar) cstack2.i; env->SetCharArrayRegion((jcharArray) cstack0.l, cstack1.i, 1, &temp); } 
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
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 2479LL)), -1); else { cstack0.i = (jint) utils::baload(env, (jarray) cstack0.l, cstack1.i); } 
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
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 2479LL)), -1); else { cstack0.i = (jint) utils::baload(env, (jarray) cstack0.l, cstack1.i); } 
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
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 2559LL)), -1); else { jchar temp = (jchar) cstack2.i; env->SetCharArrayRegion((jcharArray) cstack0.l, cstack1.i, 1, &temp); } 
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
        if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { cclasses_mtx[1].lock(); if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[1]))) { cclasses[1] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[1].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (jobject obj = env->AllocObject((cclasses[1]))) { cstack0.l = obj; refs.insert(obj); } 
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
        if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { cclasses_mtx[1].lock(); if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[1]))) { cclasses[1] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[1].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[38]) { cmethods[38] = env->GetMethodID((cclasses[1]), ((char *)(string_pool + 624LL)), ((char *)(string_pool + 2571LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 656LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[1]), (cmethods[38]), cstack2.l, cstack3.i, cstack4.i); 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // ARETURN; Stack: 1
        return (jobject) cstack0.l;
        // New stack: 0
        return (jobject) 0;
    }
    
    // a(IJ)Ljava/lang/String;
    jobject JNICALL __ngen_native_a9(JNIEnv *env, jclass clazz, jint arg0, jlong arg1) {
        jobject classloader = utils::get_classloader_from_class(env, clazz);
        if (env->ExceptionCheck()) { return (jobject) 0; }
        if (classloader == nullptr) { env->FatalError(((char *)(string_pool + 47LL))); return (jobject) 0; }
    
        jobject lookup = nullptr;
        // try-catch-class java/lang/Exception
        if (!cclasses[34] || env->IsSameObject(cclasses[34], NULL)) { cclasses_mtx[34].lock(); if (!cclasses[34] || env->IsSameObject(cclasses[34], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[63]))) { cclasses[34] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[34].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } }
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
        // SIPUSH 2736; Stack: 1
        cstack1.i = (jint) 2736;
        // New stack: 2
        // IXOR; Stack: 2
        cstack0.i = cstack0.i ^ cstack1.i;
        // New stack: 1
        // ISTORE 5; Stack: 1
        clocal5.i = cstack0.i;
        // New stack: 0
        // GETSTATIC dev/sakura/L3MonKe_h.c [Ljava/lang/String;; Stack: 0
        if (!cclasses[4]  || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cfields[3]) { cfields[3] = env->GetStaticFieldID((cclasses[4]), ((char *)(string_pool + 2348LL)), ((char *)(string_pool + 759LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } cstack0.l = env->GetStaticObjectField((cclasses[4]), (cfields[3])); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // ILOAD 5; Stack: 1
        cstack1.i = clocal5.i;
        // New stack: 2
        // AALOAD; Stack: 2
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 98LL)), -1); else { cstack0.l = env->GetObjectArrayElement((jobjectArray) cstack0.l, cstack1.i); refs.insert(cstack0.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // IFNONNULL L4; Stack: 1
        if (!env->IsSameObject(cstack0.l, nullptr)) goto L4;
        // New stack: 0
        // LABEL L1; Stack: 0
        L1: if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 0
        // INVOKESTATIC java/lang/Thread.currentThread()Ljava/lang/Thread;; Stack: 0
        if (!cclasses[18] || env->IsSameObject(cclasses[18], NULL)) { cclasses_mtx[18].lock(); if (!cclasses[18] || env->IsSameObject(cclasses[18], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[24]))) { cclasses[18] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[18].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[39]) { cmethods[39] = env->GetStaticMethodID((cclasses[18]), ((char *)(string_pool + 2602LL)), ((char *)(string_pool + 2616LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack0.l = env->CallStaticObjectMethod((cclasses[18]), (cmethods[39])); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 1
        // INVOKEVIRTUAL java/lang/Thread.threadId()J; Stack: 1
        if (!cclasses[18] || env->IsSameObject(cclasses[18], NULL)) { cclasses_mtx[18].lock(); if (!cclasses[18] || env->IsSameObject(cclasses[18], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[24]))) { cclasses[18] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[18].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[40]) { cmethods[40] = env->GetMethodID((cclasses[18]), ((char *)(string_pool + 2637LL)), ((char *)(string_pool + 179LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 183LL)), -1); else cstack0.j = env->CallLongMethod(cstack0.l, (cmethods[40])); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 2
        // INVOKESTATIC java/lang/Long.valueOf(J)Ljava/lang/Long;; Stack: 2
        if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { cclasses_mtx[2].lock(); if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[2]))) { cclasses[2] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[2].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[1]) { cmethods[1] = env->GetStaticMethodID((cclasses[2]), ((char *)(string_pool + 1044LL)), ((char *)(string_pool + 1052LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack0.l = env->CallStaticObjectMethod((cclasses[2]), (cmethods[1]), cstack0.j); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 1
        // ASTORE 3; Stack: 1
        clocal3.l = cstack0.l; refs.insert(cstack0.l);
        // New stack: 0
        // GETSTATIC dev/sakura/L3MonKe_h.d Ljava/util/Map;; Stack: 0
        if (!cclasses[4]  || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cfields[1]) { cfields[1] = env->GetStaticFieldID((cclasses[4]), ((char *)(string_pool + 1909LL)), ((char *)(string_pool + 1911LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack0.l = env->GetStaticObjectField((cclasses[4]), (cfields[1])); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 1
        // ALOAD 3; Stack: 1
        cstack1.l = clocal3.l; refs.insert(cstack1.l);
        // New stack: 2
        // INVOKEINTERFACE java/util/Map.get(Ljava/lang/Object;)Ljava/lang/Object;; Stack: 2
        if (!cclasses[35] || env->IsSameObject(cclasses[35], NULL)) { cclasses_mtx[35].lock(); if (!cclasses[35] || env->IsSameObject(cclasses[35], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[64]))) { cclasses[35] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[35].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[41]) { cmethods[41] = env->GetMethodID((cclasses[35]), ((char *)(string_pool + 2646LL)), ((char *)(string_pool + 2650LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 567LL)), -1); else { cstack0.l = env->CallObjectMethod(cstack0.l, (cmethods[41]), cstack1.l); refs.insert(cstack0.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 1
        // CHECKCAST [Ljava/lang/Object;; Stack: 1
        if (!cclasses[36] || env->IsSameObject(cclasses[36], NULL)) { cclasses_mtx[36].lock(); if (!cclasses[36] || env->IsSameObject(cclasses[36], NULL)) { if (jclass clazz = env->FindClass(((char *)(string_pool + 2689LL)))) { cclasses[36] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[36].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack0.l != nullptr && !env->IsInstanceOf(cstack0.l, (cclasses[36]))) { utils::throw_re(env, ((char *)(string_pool + 109LL)), (std::string(((char *)(string_pool + 138LL))) + std::string(((char *)(string_pool + 2689LL)))).c_str(), -1); 
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
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack0.i < 0) utils::throw_re(env, ((char *)(string_pool + 210LL)), ((char *)(string_pool + 247LL)), -1); else { cstack0.l = env->NewObjectArray(cstack0.i, (cclasses[5]), nullptr); refs.insert(cstack0.l); } 
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
        cstack2.l = (cstrings[53]);
        // New stack: 3
        // INVOKESTATIC javax/crypto/Cipher.getInstance(Ljava/lang/String;)Ljavax/crypto/Cipher;; Stack: 3
        if (!cclasses[29] || env->IsSameObject(cclasses[29], NULL)) { cclasses_mtx[29].lock(); if (!cclasses[29] || env->IsSameObject(cclasses[29], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[54]))) { cclasses[29] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[29].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[24]) { cmethods[24] = env->GetStaticMethodID((cclasses[29]), ((char *)(string_pool + 1927LL)), ((char *)(string_pool + 1939LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack2.l = env->CallStaticObjectMethod((cclasses[29]), (cmethods[24]), cstack2.l); refs.insert(cstack2.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 3
        // AASTORE; Stack: 3
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 1072LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack0.l, cstack1.i, cstack2.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 0
        // ALOAD 4; Stack: 0
        cstack0.l = clocal4.l; refs.insert(cstack0.l);
        // New stack: 1
        // ICONST_1; Stack: 1
        cstack1.i = 1;
        // New stack: 2
        // LDC DES; Stack: 2
        cstack2.l = (cstrings[55]);
        // New stack: 3
        // INVOKESTATIC javax/crypto/SecretKeyFactory.getInstance(Ljava/lang/String;)Ljavax/crypto/SecretKeyFactory;; Stack: 3
        if (!cclasses[30] || env->IsSameObject(cclasses[30], NULL)) { cclasses_mtx[30].lock(); if (!cclasses[30] || env->IsSameObject(cclasses[30], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[56]))) { cclasses[30] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[30].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[25]) { cmethods[25] = env->GetStaticMethodID((cclasses[30]), ((char *)(string_pool + 1927LL)), ((char *)(string_pool + 1981LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack2.l = env->CallStaticObjectMethod((cclasses[30]), (cmethods[25]), cstack2.l); refs.insert(cstack2.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 3
        // AASTORE; Stack: 3
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 1072LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack0.l, cstack1.i, cstack2.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 0
        // ALOAD 4; Stack: 0
        cstack0.l = clocal4.l; refs.insert(cstack0.l);
        // New stack: 1
        // ICONST_2; Stack: 1
        cstack1.i = 2;
        // New stack: 2
        // NEW javax/crypto/spec/IvParameterSpec; Stack: 2
        if (!cclasses[32] || env->IsSameObject(cclasses[32], NULL)) { cclasses_mtx[32].lock(); if (!cclasses[32] || env->IsSameObject(cclasses[32], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[58]))) { cclasses[32] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[32].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (jobject obj = env->AllocObject((cclasses[32]))) { cstack2.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 3
        // DUP; Stack: 3
        cstack3 = cstack2;
        // New stack: 4
        // BIPUSH 8; Stack: 4
        cstack4.i = (jint) 8;
        // New stack: 5
        // NEWARRAY 8; Stack: 5
        if (cstack4.i < 0) utils::throw_re(env, ((char *)(string_pool + 210LL)), ((char *)(string_pool + 2033LL)), -1); else { cstack4.l = env->NewByteArray(cstack4.i); refs.insert(cstack4.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 5
        // INVOKESPECIAL javax/crypto/spec/IvParameterSpec.<init>([B)V; Stack: 5
        if (!cclasses[32] || env->IsSameObject(cclasses[32], NULL)) { cclasses_mtx[32].lock(); if (!cclasses[32] || env->IsSameObject(cclasses[32], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[58]))) { cclasses[32] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[32].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[28]) { cmethods[28] = env->GetMethodID((cclasses[32]), ((char *)(string_pool + 624LL)), ((char *)(string_pool + 2074LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack3.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 656LL)), -1); else env->CallNonvirtualVoidMethod(cstack3.l, (cclasses[32]), (cmethods[28]), cstack4.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 3
        // AASTORE; Stack: 3
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 1072LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack0.l, cstack1.i, cstack2.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 0
        // GETSTATIC dev/sakura/L3MonKe_h.d Ljava/util/Map;; Stack: 0
        if (!cclasses[4]  || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cfields[1]) { cfields[1] = env->GetStaticFieldID((cclasses[4]), ((char *)(string_pool + 1909LL)), ((char *)(string_pool + 1911LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack0.l = env->GetStaticObjectField((cclasses[4]), (cfields[1])); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 1
        // ALOAD 3; Stack: 1
        cstack1.l = clocal3.l; refs.insert(cstack1.l);
        // New stack: 2
        // ALOAD 4; Stack: 2
        cstack2.l = clocal4.l; refs.insert(cstack2.l);
        // New stack: 3
        // INVOKEINTERFACE java/util/Map.put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;; Stack: 3
        if (!cclasses[35] || env->IsSameObject(cclasses[35], NULL)) { cclasses_mtx[35].lock(); if (!cclasses[35] || env->IsSameObject(cclasses[35], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[64]))) { cclasses[35] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[35].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[42]) { cmethods[42] = env->GetMethodID((cclasses[35]), ((char *)(string_pool + 2709LL)), ((char *)(string_pool + 2713LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 567LL)), -1); else { cstack0.l = env->CallObjectMethod(cstack0.l, (cmethods[42]), cstack1.l, cstack2.l); refs.insert(cstack0.l); } 
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
        if (!cclasses[37] || env->IsSameObject(cclasses[37], NULL)) { cclasses_mtx[37].lock(); if (!cclasses[37] || env->IsSameObject(cclasses[37], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[65]))) { cclasses[37] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[37].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (jobject obj = env->AllocObject((cclasses[37]))) { cstack0.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // LDC dev/sakura/L3MonKe_h; Stack: 2
        cstack2.l = (cstrings[66]);
        // New stack: 3
        // ALOAD 9; Stack: 3
        cstack3.l = clocal9.l; refs.insert(cstack3.l);
        // New stack: 4
        // INVOKESPECIAL java/lang/RuntimeException.<init>(Ljava/lang/String;Ljava/lang/Throwable;)V; Stack: 4
        if (!cclasses[37] || env->IsSameObject(cclasses[37], NULL)) { cclasses_mtx[37].lock(); if (!cclasses[37] || env->IsSameObject(cclasses[37], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[65]))) { cclasses[37] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[37].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[43]) { cmethods[43] = env->GetMethodID((cclasses[37]), ((char *)(string_pool + 624LL)), ((char *)(string_pool + 2770LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 656LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[37]), (cmethods[43]), cstack2.l, cstack3.l); 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 679LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
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
        if (cstack0.i < 0) utils::throw_re(env, ((char *)(string_pool + 210LL)), ((char *)(string_pool + 2033LL)), -1); else { cstack0.l = env->NewByteArray(cstack0.i); refs.insert(cstack0.l); } 
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
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 2062LL)), -1); else { utils::bastore(env, (jarray) cstack0.l, cstack1.i, cstack2.i); } 
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
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 2062LL)), -1); else { utils::bastore(env, (jarray) cstack0.l, cstack1.i, cstack2.i); } 
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
        if (!cclasses[31] || env->IsSameObject(cclasses[31], NULL)) { cclasses_mtx[31].lock(); if (!cclasses[31] || env->IsSameObject(cclasses[31], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[57]))) { cclasses[31] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[31].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (jobject obj = env->AllocObject((cclasses[31]))) { cstack0.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // ALOAD 6; Stack: 2
        cstack2.l = clocal6.l; refs.insert(cstack2.l);
        // New stack: 3
        // INVOKESPECIAL javax/crypto/spec/DESKeySpec.<init>([B)V; Stack: 3
        if (!cclasses[31] || env->IsSameObject(cclasses[31], NULL)) { cclasses_mtx[31].lock(); if (!cclasses[31] || env->IsSameObject(cclasses[31], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[57]))) { cclasses[31] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[31].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[26]) { cmethods[26] = env->GetMethodID((cclasses[31]), ((char *)(string_pool + 624LL)), ((char *)(string_pool + 2074LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 656LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[31]), (cmethods[26]), cstack2.l); 
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
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 98LL)), -1); else { cstack0.l = env->GetObjectArrayElement((jobjectArray) cstack0.l, cstack1.i); refs.insert(cstack0.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // CHECKCAST javax/crypto/SecretKeyFactory; Stack: 1
        if (!cclasses[30] || env->IsSameObject(cclasses[30], NULL)) { cclasses_mtx[30].lock(); if (!cclasses[30] || env->IsSameObject(cclasses[30], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[56]))) { cclasses[30] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[30].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (cstack0.l != nullptr && !env->IsInstanceOf(cstack0.l, (cclasses[30]))) { utils::throw_re(env, ((char *)(string_pool + 109LL)), (std::string(((char *)(string_pool + 138LL))) + std::string(((char *)(string_pool + 2813LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (jobject) 0; } } 
        // New stack: 1
        // ALOAD 7; Stack: 1
        cstack1.l = clocal7.l; refs.insert(cstack1.l);
        // New stack: 2
        // INVOKEVIRTUAL javax/crypto/SecretKeyFactory.generateSecret(Ljava/security/spec/KeySpec;)Ljavax/crypto/SecretKey;; Stack: 2
        if (!cclasses[30] || env->IsSameObject(cclasses[30], NULL)) { cclasses_mtx[30].lock(); if (!cclasses[30] || env->IsSameObject(cclasses[30], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[56]))) { cclasses[30] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[30].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[27]) { cmethods[27] = env->GetMethodID((cclasses[30]), ((char *)(string_pool + 2080LL)), ((char *)(string_pool + 2095LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 481LL)), -1); else { cstack0.l = env->CallObjectMethod(cstack0.l, (cmethods[27]), cstack1.l); refs.insert(cstack0.l); } 
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
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 98LL)), -1); else { cstack0.l = env->GetObjectArrayElement((jobjectArray) cstack0.l, cstack1.i); refs.insert(cstack0.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // CHECKCAST javax/crypto/Cipher; Stack: 1
        if (!cclasses[29] || env->IsSameObject(cclasses[29], NULL)) { cclasses_mtx[29].lock(); if (!cclasses[29] || env->IsSameObject(cclasses[29], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[54]))) { cclasses[29] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[29].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (cstack0.l != nullptr && !env->IsInstanceOf(cstack0.l, (cclasses[29]))) { utils::throw_re(env, ((char *)(string_pool + 109LL)), (std::string(((char *)(string_pool + 138LL))) + std::string(((char *)(string_pool + 2843LL)))).c_str(), -1); 
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
        if (cstack3.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 98LL)), -1); else { cstack3.l = env->GetObjectArrayElement((jobjectArray) cstack3.l, cstack4.i); refs.insert(cstack3.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 4
        // CHECKCAST javax/crypto/spec/IvParameterSpec; Stack: 4
        if (!cclasses[32] || env->IsSameObject(cclasses[32], NULL)) { cclasses_mtx[32].lock(); if (!cclasses[32] || env->IsSameObject(cclasses[32], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[58]))) { cclasses[32] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[32].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (cstack3.l != nullptr && !env->IsInstanceOf(cstack3.l, (cclasses[32]))) { utils::throw_re(env, ((char *)(string_pool + 109LL)), (std::string(((char *)(string_pool + 138LL))) + std::string(((char *)(string_pool + 2863LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (jobject) 0; } } 
        // New stack: 4
        // INVOKEVIRTUAL javax/crypto/Cipher.init(ILjava/security/Key;Ljava/security/spec/AlgorithmParameterSpec;)V; Stack: 4
        if (!cclasses[29] || env->IsSameObject(cclasses[29], NULL)) { cclasses_mtx[29].lock(); if (!cclasses[29] || env->IsSameObject(cclasses[29], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[54]))) { cclasses[29] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[29].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[29]) { cmethods[29] = env->GetMethodID((cclasses[29]), ((char *)(string_pool + 2150LL)), ((char *)(string_pool + 2155LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 2222LL)), -1); else env->CallVoidMethod(cstack0.l, (cmethods[29]), cstack1.i, cstack2.l, cstack3.l); 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 0
        // GETSTATIC dev/sakura/L3MonKe_h.b [Ljava/lang/String;; Stack: 0
        if (!cclasses[4]  || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cfields[2]) { cfields[2] = env->GetStaticFieldID((cclasses[4]), ((char *)(string_pool + 2346LL)), ((char *)(string_pool + 759LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } cstack0.l = env->GetStaticObjectField((cclasses[4]), (cfields[2])); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // ILOAD 5; Stack: 1
        cstack1.i = clocal5.i;
        // New stack: 2
        // AALOAD; Stack: 2
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 98LL)), -1); else { cstack0.l = env->GetObjectArrayElement((jobjectArray) cstack0.l, cstack1.i); refs.insert(cstack0.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // LDC ISO-8859-1; Stack: 1
        cstack1.l = (cstrings[60]);
        // New stack: 2
        // INVOKEVIRTUAL java/lang/String.getBytes(Ljava/lang/String;)[B; Stack: 2
        if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { cclasses_mtx[1].lock(); if (!cclasses[1] || env->IsSameObject(cclasses[1], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[1]))) { cclasses[1] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[1].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[33]) { cmethods[33] = env->GetMethodID((cclasses[1]), ((char *)(string_pool + 2350LL)), ((char *)(string_pool + 2359LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 481LL)), -1); else { cstack0.l = env->CallObjectMethod(cstack0.l, (cmethods[33]), cstack1.l); refs.insert(cstack0.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // ASTORE 9; Stack: 1
        clocal9.l = cstack0.l; refs.insert(cstack0.l);
        // New stack: 0
        // GETSTATIC dev/sakura/L3MonKe_h.c [Ljava/lang/String;; Stack: 0
        if (!cclasses[4]  || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cfields[3]) { cfields[3] = env->GetStaticFieldID((cclasses[4]), ((char *)(string_pool + 2348LL)), ((char *)(string_pool + 759LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } cstack0.l = env->GetStaticObjectField((cclasses[4]), (cfields[3])); refs.insert(cstack0.l); 
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
        if (cstack2.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 98LL)), -1); else { cstack2.l = env->GetObjectArrayElement((jobjectArray) cstack2.l, cstack3.i); refs.insert(cstack2.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 3
        // CHECKCAST javax/crypto/Cipher; Stack: 3
        if (!cclasses[29] || env->IsSameObject(cclasses[29], NULL)) { cclasses_mtx[29].lock(); if (!cclasses[29] || env->IsSameObject(cclasses[29], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[54]))) { cclasses[29] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[29].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (cstack2.l != nullptr && !env->IsInstanceOf(cstack2.l, (cclasses[29]))) { utils::throw_re(env, ((char *)(string_pool + 109LL)), (std::string(((char *)(string_pool + 138LL))) + std::string(((char *)(string_pool + 2843LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (jobject) 0; } } 
        // New stack: 3
        // ALOAD 9; Stack: 3
        cstack3.l = clocal9.l; refs.insert(cstack3.l);
        // New stack: 4
        // INVOKEVIRTUAL javax/crypto/Cipher.doFinal([B)[B; Stack: 4
        if (!cclasses[29] || env->IsSameObject(cclasses[29], NULL)) { cclasses_mtx[29].lock(); if (!cclasses[29] || env->IsSameObject(cclasses[29], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[54]))) { cclasses[29] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[29].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[34]) { cmethods[34] = env->GetMethodID((cclasses[29]), ((char *)(string_pool + 2382LL)), ((char *)(string_pool + 2390LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack2.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 481LL)), -1); else { cstack2.l = env->CallObjectMethod(cstack2.l, (cmethods[34]), cstack3.l); refs.insert(cstack2.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 3
        // INVOKESTATIC dev/sakura/L3MonKe_h.a([B)Ljava/lang/String;; Stack: 3
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[35]) { cmethods[35] = env->GetStaticMethodID((cclasses[4]), ((char *)(string_pool + 206LL)), ((char *)(string_pool + 2397LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } cstack2.l = env->CallStaticObjectMethod((cclasses[4]), (cmethods[35]), cstack2.l); refs.insert(cstack2.l); 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 3
        // AASTORE; Stack: 3
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 1072LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack0.l, cstack1.i, cstack2.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 0
        // LABEL L4; Stack: 0
        L4: if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 0
        // FRAME FULL L: [1, 4, 0, 0, 1] S: []; Stack: 0
        utils::clear_refs(env, refs);
        // New stack: 0
        // GETSTATIC dev/sakura/L3MonKe_h.c [Ljava/lang/String;; Stack: 0
        if (!cclasses[4]  || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cfields[3]) { cfields[3] = env->GetStaticFieldID((cclasses[4]), ((char *)(string_pool + 2348LL)), ((char *)(string_pool + 759LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } cstack0.l = env->GetStaticObjectField((cclasses[4]), (cfields[3])); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // ILOAD 5; Stack: 1
        cstack1.i = clocal5.i;
        // New stack: 2
        // AALOAD; Stack: 2
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 98LL)), -1); else { cstack0.l = env->GetObjectArrayElement((jobjectArray) cstack0.l, cstack1.i); refs.insert(cstack0.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // ARETURN; Stack: 1
        return (jobject) cstack0.l;
        // New stack: 0
        return (jobject) 0;
        L_CATCH_0: if (env->IsInstanceOf(cstack0.l, (cclasses[34]))) { goto L3; }
        env->Throw((jthrowable) cstack0.l); return (jobject) 0;
    }
    
    // a(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/invoke/MutableCallSite;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;
    jobject JNICALL __ngen_native_a10(JNIEnv *env, jclass clazz, jobject arg0, jobject arg1, jobject arg2, jarray arg3) {
        jobject classloader = utils::get_classloader_from_class(env, clazz);
        if (env->ExceptionCheck()) { return (jobject) 0; }
        if (classloader == nullptr) { env->FatalError(((char *)(string_pool + 47LL))); return (jobject) 0; }
    
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
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 98LL)), -1); else { cstack0.l = env->GetObjectArrayElement((jobjectArray) cstack0.l, cstack1.i); refs.insert(cstack0.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // CHECKCAST java/lang/Integer; Stack: 1
        if (!cclasses[38] || env->IsSameObject(cclasses[38], NULL)) { cclasses_mtx[38].lock(); if (!cclasses[38] || env->IsSameObject(cclasses[38], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[67]))) { cclasses[38] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[38].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (cstack0.l != nullptr && !env->IsInstanceOf(cstack0.l, (cclasses[38]))) { utils::throw_re(env, ((char *)(string_pool + 109LL)), (std::string(((char *)(string_pool + 138LL))) + std::string(((char *)(string_pool + 3028LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (jobject) 0; } } 
        // New stack: 1
        // INVOKEVIRTUAL java/lang/Integer.intValue()I; Stack: 1
        if (!cclasses[38] || env->IsSameObject(cclasses[38], NULL)) { cclasses_mtx[38].lock(); if (!cclasses[38] || env->IsSameObject(cclasses[38], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[67]))) { cclasses[38] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[38].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[44]) { cmethods[44] = env->GetMethodID((cclasses[38]), ((char *)(string_pool + 3046LL)), ((char *)(string_pool + 2252LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 2256LL)), -1); else cstack0.i = env->CallIntMethod(cstack0.l, (cmethods[44])); 
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
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 98LL)), -1); else { cstack0.l = env->GetObjectArrayElement((jobjectArray) cstack0.l, cstack1.i); refs.insert(cstack0.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // CHECKCAST java/lang/Long; Stack: 1
        if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { cclasses_mtx[2].lock(); if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[2]))) { cclasses[2] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[2].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (cstack0.l != nullptr && !env->IsInstanceOf(cstack0.l, (cclasses[2]))) { utils::throw_re(env, ((char *)(string_pool + 109LL)), (std::string(((char *)(string_pool + 138LL))) + std::string(((char *)(string_pool + 154LL)))).c_str(), -1); 
        if (env->ExceptionCheck()) { return (jobject) 0; } } 
        // New stack: 1
        // INVOKEVIRTUAL java/lang/Long.longValue()J; Stack: 1
        if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { cclasses_mtx[2].lock(); if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[2]))) { cclasses[2] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[2].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[0]) { cmethods[0] = env->GetMethodID((cclasses[2]), ((char *)(string_pool + 169LL)), ((char *)(string_pool + 179LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 183LL)), -1); else cstack0.j = env->CallLongMethod(cstack0.l, (cmethods[0])); 
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
        // INVOKESTATIC dev/sakura/L3MonKe_h.a(IJ)Ljava/lang/String;; Stack: 3
        if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { cclasses_mtx[4].lock(); if (!cclasses[4] || env->IsSameObject(cclasses[4], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[4] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[4].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[45]) { cmethods[45] = env->GetStaticMethodID((cclasses[4]), ((char *)(string_pool + 206LL)), ((char *)(string_pool + 2579LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } cstack0.l = env->CallStaticObjectMethod((cclasses[4]), (cmethods[45]), cstack0.i, cstack1.j); refs.insert(cstack0.l); 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // ASTORE 7; Stack: 1
        clocal7.l = cstack0.l; refs.insert(cstack0.l);
        // New stack: 0
        // LDC Ljava/lang/String;; Stack: 0
        if (!cclasses[39] || env->IsSameObject(cclasses[39], NULL)) { cclasses_mtx[39].lock(); if (!cclasses[39] || env->IsSameObject(cclasses[39], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[1]))) { cclasses[39] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[39].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } cstack0.l = (cclasses[39]);
        // New stack: 1
        // ALOAD 7; Stack: 1
        cstack1.l = clocal7.l; refs.insert(cstack1.l);
        // New stack: 2
        // INVOKESTATIC java/lang/invoke/MethodHandles.constant(Ljava/lang/Class;Ljava/lang/Object;)Ljava/lang/invoke/MethodHandle;; Stack: 2
        if (!cclasses[25] || env->IsSameObject(cclasses[25], NULL)) { cclasses_mtx[25].lock(); if (!cclasses[25] || env->IsSameObject(cclasses[25], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[49]))) { cclasses[25] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[25].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[46]) { cmethods[46] = env->GetStaticMethodID((cclasses[25]), ((char *)(string_pool + 3055LL)), ((char *)(string_pool + 3064LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } cstack0.l = env->CallStaticObjectMethod((cclasses[25]), (cmethods[46]), cstack0.l, cstack1.l); refs.insert(cstack0.l); 
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
        if (!cclasses[40] || env->IsSameObject(cclasses[40], NULL)) { cclasses_mtx[40].lock(); if (!cclasses[40] || env->IsSameObject(cclasses[40], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[68]))) { cclasses[40] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[40].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (cstack3.i < 0) utils::throw_re(env, ((char *)(string_pool + 210LL)), ((char *)(string_pool + 247LL)), -1); else { cstack3.l = env->NewObjectArray(cstack3.i, (cclasses[40]), nullptr); refs.insert(cstack3.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 4
        // DUP; Stack: 4
        cstack4 = cstack3;
        // New stack: 5
        // ICONST_0; Stack: 5
        cstack5.i = 0;
        // New stack: 6
        // GETSTATIC java/lang/Integer.TYPE Ljava/lang/Class;; Stack: 6
        if (!cclasses[38]  || env->IsSameObject(cclasses[38], NULL)) { cclasses_mtx[38].lock(); if (!cclasses[38] || env->IsSameObject(cclasses[38], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[67]))) { cclasses[38] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[38].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cfields[8]) { cfields[8] = env->GetStaticFieldID((cclasses[38]), ((char *)(string_pool + 3133LL)), ((char *)(string_pool + 3138LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } cstack6.l = env->GetStaticObjectField((cclasses[38]), (cfields[8])); refs.insert(cstack6.l); 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 7
        // AASTORE; Stack: 7
        if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 1072LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack4.l, cstack5.i, cstack6.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 4
        // DUP; Stack: 4
        cstack4 = cstack3;
        // New stack: 5
        // ICONST_1; Stack: 5
        cstack5.i = 1;
        // New stack: 6
        // GETSTATIC java/lang/Long.TYPE Ljava/lang/Class;; Stack: 6
        if (!cclasses[2]  || env->IsSameObject(cclasses[2], NULL)) { cclasses_mtx[2].lock(); if (!cclasses[2] || env->IsSameObject(cclasses[2], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[2]))) { cclasses[2] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[2].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cfields[9]) { cfields[9] = env->GetStaticFieldID((cclasses[2]), ((char *)(string_pool + 3133LL)), ((char *)(string_pool + 3138LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } cstack6.l = env->GetStaticObjectField((cclasses[2]), (cfields[9])); refs.insert(cstack6.l); 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 7
        // AASTORE; Stack: 7
        if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 1072LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack4.l, cstack5.i, cstack6.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 4
        // INVOKESTATIC java/lang/invoke/MethodHandles.dropArguments(Ljava/lang/invoke/MethodHandle;I[Ljava/lang/Class;)Ljava/lang/invoke/MethodHandle;; Stack: 4
        if (!cclasses[25] || env->IsSameObject(cclasses[25], NULL)) { cclasses_mtx[25].lock(); if (!cclasses[25] || env->IsSameObject(cclasses[25], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[49]))) { cclasses[25] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[25].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[47]) { cmethods[47] = env->GetStaticMethodID((cclasses[25]), ((char *)(string_pool + 3156LL)), ((char *)(string_pool + 3170LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } cstack1.l = env->CallStaticObjectMethod((cclasses[25]), (cmethods[47]), cstack1.l, cstack2.i, cstack3.l); refs.insert(cstack1.l); 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 2
        // INVOKEVIRTUAL java/lang/invoke/MutableCallSite.setTarget(Ljava/lang/invoke/MethodHandle;)V; Stack: 2
        if (!cclasses[41] || env->IsSameObject(cclasses[41], NULL)) { cclasses_mtx[41].lock(); if (!cclasses[41] || env->IsSameObject(cclasses[41], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[69]))) { cclasses[41] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[41].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[48]) { cmethods[48] = env->GetMethodID((cclasses[41]), ((char *)(string_pool + 3254LL)), ((char *)(string_pool + 3264LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 2222LL)), -1); else env->CallVoidMethod(cstack0.l, (cmethods[48]), cstack1.l); 
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
    jobject JNICALL __ngen_native_a11(JNIEnv *env, jclass clazz, jobject arg0, jobject arg1, jobject arg2) {
        jobject classloader = utils::get_classloader_from_class(env, clazz);
        if (env->ExceptionCheck()) { return (jobject) 0; }
        if (classloader == nullptr) { env->FatalError(((char *)(string_pool + 47LL))); return (jobject) 0; }
    
        jobject lookup = nullptr;
        // try-catch-class java/lang/Exception
        if (!cclasses[34] || env->IsSameObject(cclasses[34], NULL)) { cclasses_mtx[34].lock(); if (!cclasses[34] || env->IsSameObject(cclasses[34], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[63]))) { cclasses[34] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[34].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } }
        jvalue cstack0 = {}, cstack1 = {}, cstack2 = {}, cstack3 = {}, cstack4 = {}, cstack5 = {}, cstack6 = {};
        jvalue clocal0 = {}, clocal1 = {}, clocal2 = {}, clocal3 = {}, clocal4 = {};
        std::unordered_set<jobject> refs;
    
        clocal0.l = arg0; refs.insert(clocal0.l);
        clocal1.l = arg1; refs.insert(clocal1.l);
        clocal2.l = arg2; refs.insert(clocal2.l);
    
        // NEW java/lang/invoke/MutableCallSite; Stack: 0
        if (!cclasses[41] || env->IsSameObject(cclasses[41], NULL)) { cclasses_mtx[41].lock(); if (!cclasses[41] || env->IsSameObject(cclasses[41], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[69]))) { cclasses[41] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[41].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (jobject obj = env->AllocObject((cclasses[41]))) { cstack0.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // ALOAD 2; Stack: 2
        cstack2.l = clocal2.l; refs.insert(cstack2.l);
        // New stack: 3
        // INVOKESPECIAL java/lang/invoke/MutableCallSite.<init>(Ljava/lang/invoke/MethodType;)V; Stack: 3
        if (!cclasses[41] || env->IsSameObject(cclasses[41], NULL)) { cclasses_mtx[41].lock(); if (!cclasses[41] || env->IsSameObject(cclasses[41], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[69]))) { cclasses[41] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[41].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[49]) { cmethods[49] = env->GetMethodID((cclasses[41]), ((char *)(string_pool + 624LL)), ((char *)(string_pool + 3415LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 656LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[41]), (cmethods[49]), cstack2.l); 
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
        // INVOKESTATIC native/magic/1/lookup/obfuscator0.7353569731942222.a()Ljava/lang/invoke/MethodHandles$Lookup;; Stack: 1
        if (lookup == nullptr) { lookup = utils::get_lookup(env, clazz); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack1.l = lookup;
        // New stack: 2
        // LDC Ldev/sakura/L3MonKe_h;; Stack: 2
        if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { cclasses_mtx[6].lock(); if (!cclasses[6] || env->IsSameObject(cclasses[6], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[4]))) { cclasses[6] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[6].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack2.l = (cclasses[6]);
        // New stack: 3
        // LDC a; Stack: 3
        cstack3.l = (cstrings[7]);
        // New stack: 4
        // LDC (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/invoke/MutableCallSite;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;; Stack: 4
        cstack4.l = (cstrings[70]);
        // New stack: 5
        // INVOKESTATIC native/magic/1/classloader/obfuscator0.7353569731942222.a()Ljava/lang/ClassLoader;; Stack: 5
        cstack5.l = classloader;
        // New stack: 6
        // INVOKESTATIC java/lang/invoke/MethodType.fromMethodDescriptorString(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/invoke/MethodType;; Stack: 6
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[2]) { cmethods[2] = env->GetStaticMethodID((cclasses[8]), ((char *)(string_pool + 272LL)), ((char *)(string_pool + 299LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack4.l = env->CallStaticObjectMethod((cclasses[8]), (cmethods[2]), cstack4.l, cstack5.l); refs.insert(cstack4.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 5
        // INVOKEVIRTUAL java/lang/invoke/MethodHandles$Lookup.findStatic(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 5
        if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { cclasses_mtx[9].lock(); if (!cclasses[9] || env->IsSameObject(cclasses[9], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[10]))) { cclasses[9] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[9].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[3]) { cmethods[3] = env->GetMethodID((cclasses[9]), ((char *)(string_pool + 372LL)), ((char *)(string_pool + 383LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 481LL)), -1); else { cstack1.l = env->CallObjectMethod(cstack1.l, (cmethods[3]), cstack2.l, cstack3.l, cstack4.l); refs.insert(cstack1.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 2
        // LDC [Ljava/lang/Object;; Stack: 2
        if (!cclasses[36] || env->IsSameObject(cclasses[36], NULL)) { cclasses_mtx[36].lock(); if (!cclasses[36] || env->IsSameObject(cclasses[36], NULL)) { if (jclass clazz = env->FindClass(((char *)(string_pool + 2689LL)))) { cclasses[36] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[36].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } cstack2.l = (cclasses[36]);
        // New stack: 3
        // ALOAD 2; Stack: 3
        cstack3.l = clocal2.l; refs.insert(cstack3.l);
        // New stack: 4
        // INVOKEVIRTUAL java/lang/invoke/MethodType.parameterCount()I; Stack: 4
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[50]) { cmethods[50] = env->GetMethodID((cclasses[8]), ((char *)(string_pool + 3448LL)), ((char *)(string_pool + 2252LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack3.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 2256LL)), -1); else cstack3.i = env->CallIntMethod(cstack3.l, (cmethods[50])); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 4
        // INVOKEVIRTUAL java/lang/invoke/MethodHandle.asCollector(Ljava/lang/Class;I)Ljava/lang/invoke/MethodHandle;; Stack: 4
        if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { cclasses_mtx[11].lock(); if (!cclasses[11] || env->IsSameObject(cclasses[11], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[14]))) { cclasses[11] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[11].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[51]) { cmethods[51] = env->GetMethodID((cclasses[11]), ((char *)(string_pool + 3463LL)), ((char *)(string_pool + 3475LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 481LL)), -1); else { cstack1.l = env->CallObjectMethod(cstack1.l, (cmethods[51]), cstack2.l, cstack3.i); refs.insert(cstack1.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 2
        // ICONST_0; Stack: 2
        cstack2.i = 0;
        // New stack: 3
        // ICONST_3; Stack: 3
        cstack3.i = 3;
        // New stack: 4
        // ANEWARRAY java/lang/Object; Stack: 4
        if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { cclasses_mtx[5].lock(); if (!cclasses[5] || env->IsSameObject(cclasses[5], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[5]))) { cclasses[5] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[5].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (cstack3.i < 0) utils::throw_re(env, ((char *)(string_pool + 210LL)), ((char *)(string_pool + 247LL)), -1); else { cstack3.l = env->NewObjectArray(cstack3.i, (cclasses[5]), nullptr); refs.insert(cstack3.l); } 
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
        if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 1072LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack4.l, cstack5.i, cstack6.l); } 
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
        if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 1072LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack4.l, cstack5.i, cstack6.l); } 
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
        if (cstack4.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 1072LL)), -1); else { env->SetObjectArrayElement((jobjectArray) cstack4.l, cstack5.i, cstack6.l); } 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 4
        // INVOKESTATIC java/lang/invoke/MethodHandles.insertArguments(Ljava/lang/invoke/MethodHandle;I[Ljava/lang/Object;)Ljava/lang/invoke/MethodHandle;; Stack: 4
        if (!cclasses[25] || env->IsSameObject(cclasses[25], NULL)) { cclasses_mtx[25].lock(); if (!cclasses[25] || env->IsSameObject(cclasses[25], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[49]))) { cclasses[25] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[25].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[52]) { cmethods[52] = env->GetStaticMethodID((cclasses[25]), ((char *)(string_pool + 3527LL)), ((char *)(string_pool + 3543LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack1.l = env->CallStaticObjectMethod((cclasses[25]), (cmethods[52]), cstack1.l, cstack2.i, cstack3.l); refs.insert(cstack1.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 2
        // ALOAD 2; Stack: 2
        cstack2.l = clocal2.l; refs.insert(cstack2.l);
        // New stack: 3
        // INVOKESTATIC java/lang/invoke/MethodHandles.explicitCastArguments(Ljava/lang/invoke/MethodHandle;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;; Stack: 3
        if (!cclasses[25] || env->IsSameObject(cclasses[25], NULL)) { cclasses_mtx[25].lock(); if (!cclasses[25] || env->IsSameObject(cclasses[25], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[49]))) { cclasses[25] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[25].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[53]) { cmethods[53] = env->GetStaticMethodID((cclasses[25]), ((char *)(string_pool + 3628LL)), ((char *)(string_pool + 3650LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } cstack1.l = env->CallStaticObjectMethod((cclasses[25]), (cmethods[53]), cstack1.l, cstack2.l); refs.insert(cstack1.l); 
        if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }
        // New stack: 2
        // INVOKEVIRTUAL java/lang/invoke/MutableCallSite.setTarget(Ljava/lang/invoke/MethodHandle;)V; Stack: 2
        if (!cclasses[41] || env->IsSameObject(cclasses[41], NULL)) { cclasses_mtx[41].lock(); if (!cclasses[41] || env->IsSameObject(cclasses[41], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[69]))) { cclasses[41] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[41].unlock(); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; } } if (!cmethods[48]) { cmethods[48] = env->GetMethodID((cclasses[41]), ((char *)(string_pool + 3254LL)), ((char *)(string_pool + 3264LL))); if (env->ExceptionCheck()) { jthrowable exception = env->ExceptionOccurred(); env->ExceptionClear(); cstack0.l = exception; refs.insert(exception); goto L_CATCH_0; }  } if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 2222LL)), -1); else env->CallVoidMethod(cstack0.l, (cmethods[48]), cstack1.l); 
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
        if (!cclasses[37] || env->IsSameObject(cclasses[37], NULL)) { cclasses_mtx[37].lock(); if (!cclasses[37] || env->IsSameObject(cclasses[37], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[65]))) { cclasses[37] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[37].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (jobject obj = env->AllocObject((cclasses[37]))) { cstack0.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // DUP; Stack: 1
        cstack1 = cstack0;
        // New stack: 2
        // NEW java/lang/StringBuilder; Stack: 2
        if (!cclasses[42] || env->IsSameObject(cclasses[42], NULL)) { cclasses_mtx[42].lock(); if (!cclasses[42] || env->IsSameObject(cclasses[42], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[71]))) { cclasses[42] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[42].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (jobject obj = env->AllocObject((cclasses[42]))) { cstack2.l = obj; refs.insert(obj); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 3
        // DUP; Stack: 3
        cstack3 = cstack2;
        // New stack: 4
        // INVOKESPECIAL java/lang/StringBuilder.<init>()V; Stack: 4
        if (!cclasses[42] || env->IsSameObject(cclasses[42], NULL)) { cclasses_mtx[42].lock(); if (!cclasses[42] || env->IsSameObject(cclasses[42], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[71]))) { cclasses[42] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[42].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[54]) { cmethods[54] = env->GetMethodID((cclasses[42]), ((char *)(string_pool + 624LL)), ((char *)(string_pool + 779LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack3.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 656LL)), -1); else env->CallNonvirtualVoidMethod(cstack3.l, (cclasses[42]), (cmethods[54])); 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 3
        // LDC dev/sakura/L3MonKe_h; Stack: 3
        cstack3.l = (cstrings[66]);
        // New stack: 4
        // INVOKEVIRTUAL java/lang/StringBuilder.append(Ljava/lang/String;)Ljava/lang/StringBuilder;; Stack: 4
        if (!cclasses[42] || env->IsSameObject(cclasses[42], NULL)) { cclasses_mtx[42].lock(); if (!cclasses[42] || env->IsSameObject(cclasses[42], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[71]))) { cclasses[42] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[42].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[55]) { cmethods[55] = env->GetMethodID((cclasses[42]), ((char *)(string_pool + 3744LL)), ((char *)(string_pool + 3751LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack2.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 481LL)), -1); else { cstack2.l = env->CallObjectMethod(cstack2.l, (cmethods[55]), cstack3.l); refs.insert(cstack2.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 3
        // LDC  : ; Stack: 3
        cstack3.l = (cstrings[72]);
        // New stack: 4
        // INVOKEVIRTUAL java/lang/StringBuilder.append(Ljava/lang/String;)Ljava/lang/StringBuilder;; Stack: 4
        if (!cclasses[42] || env->IsSameObject(cclasses[42], NULL)) { cclasses_mtx[42].lock(); if (!cclasses[42] || env->IsSameObject(cclasses[42], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[71]))) { cclasses[42] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[42].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[55]) { cmethods[55] = env->GetMethodID((cclasses[42]), ((char *)(string_pool + 3744LL)), ((char *)(string_pool + 3751LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack2.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 481LL)), -1); else { cstack2.l = env->CallObjectMethod(cstack2.l, (cmethods[55]), cstack3.l); refs.insert(cstack2.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 3
        // ALOAD 1; Stack: 3
        cstack3.l = clocal1.l; refs.insert(cstack3.l);
        // New stack: 4
        // INVOKEVIRTUAL java/lang/StringBuilder.append(Ljava/lang/String;)Ljava/lang/StringBuilder;; Stack: 4
        if (!cclasses[42] || env->IsSameObject(cclasses[42], NULL)) { cclasses_mtx[42].lock(); if (!cclasses[42] || env->IsSameObject(cclasses[42], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[71]))) { cclasses[42] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[42].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[55]) { cmethods[55] = env->GetMethodID((cclasses[42]), ((char *)(string_pool + 3744LL)), ((char *)(string_pool + 3751LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack2.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 481LL)), -1); else { cstack2.l = env->CallObjectMethod(cstack2.l, (cmethods[55]), cstack3.l); refs.insert(cstack2.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 3
        // LDC  : ; Stack: 3
        cstack3.l = (cstrings[72]);
        // New stack: 4
        // INVOKEVIRTUAL java/lang/StringBuilder.append(Ljava/lang/String;)Ljava/lang/StringBuilder;; Stack: 4
        if (!cclasses[42] || env->IsSameObject(cclasses[42], NULL)) { cclasses_mtx[42].lock(); if (!cclasses[42] || env->IsSameObject(cclasses[42], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[71]))) { cclasses[42] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[42].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[55]) { cmethods[55] = env->GetMethodID((cclasses[42]), ((char *)(string_pool + 3744LL)), ((char *)(string_pool + 3751LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack2.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 481LL)), -1); else { cstack2.l = env->CallObjectMethod(cstack2.l, (cmethods[55]), cstack3.l); refs.insert(cstack2.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 3
        // ALOAD 2; Stack: 3
        cstack3.l = clocal2.l; refs.insert(cstack3.l);
        // New stack: 4
        // INVOKEVIRTUAL java/lang/invoke/MethodType.toString()Ljava/lang/String;; Stack: 4
        if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { cclasses_mtx[8].lock(); if (!cclasses[8] || env->IsSameObject(cclasses[8], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[9]))) { cclasses[8] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[8].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[56]) { cmethods[56] = env->GetMethodID((cclasses[8]), ((char *)(string_pool + 3797LL)), ((char *)(string_pool + 2427LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack3.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 481LL)), -1); else { cstack3.l = env->CallObjectMethod(cstack3.l, (cmethods[56])); refs.insert(cstack3.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 4
        // INVOKEVIRTUAL java/lang/StringBuilder.append(Ljava/lang/String;)Ljava/lang/StringBuilder;; Stack: 4
        if (!cclasses[42] || env->IsSameObject(cclasses[42], NULL)) { cclasses_mtx[42].lock(); if (!cclasses[42] || env->IsSameObject(cclasses[42], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[71]))) { cclasses[42] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[42].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[55]) { cmethods[55] = env->GetMethodID((cclasses[42]), ((char *)(string_pool + 3744LL)), ((char *)(string_pool + 3751LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack2.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 481LL)), -1); else { cstack2.l = env->CallObjectMethod(cstack2.l, (cmethods[55]), cstack3.l); refs.insert(cstack2.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 3
        // INVOKEVIRTUAL java/lang/StringBuilder.toString()Ljava/lang/String;; Stack: 3
        if (!cclasses[42] || env->IsSameObject(cclasses[42], NULL)) { cclasses_mtx[42].lock(); if (!cclasses[42] || env->IsSameObject(cclasses[42], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[71]))) { cclasses[42] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[42].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[57]) { cmethods[57] = env->GetMethodID((cclasses[42]), ((char *)(string_pool + 3797LL)), ((char *)(string_pool + 2427LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack2.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 481LL)), -1); else { cstack2.l = env->CallObjectMethod(cstack2.l, (cmethods[57])); refs.insert(cstack2.l); } 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 3
        // ALOAD 4; Stack: 3
        cstack3.l = clocal4.l; refs.insert(cstack3.l);
        // New stack: 4
        // INVOKESPECIAL java/lang/RuntimeException.<init>(Ljava/lang/String;Ljava/lang/Throwable;)V; Stack: 4
        if (!cclasses[37] || env->IsSameObject(cclasses[37], NULL)) { cclasses_mtx[37].lock(); if (!cclasses[37] || env->IsSameObject(cclasses[37], NULL)) { if (jclass clazz = utils::find_class_wo_static(env, classloader, (cstrings[65]))) { cclasses[37] = (jclass) env->NewWeakGlobalRef(clazz); env->DeleteLocalRef(clazz); } } cclasses_mtx[37].unlock(); if (env->ExceptionCheck()) { return (jobject) 0; } } if (!cmethods[43]) { cmethods[43] = env->GetMethodID((cclasses[37]), ((char *)(string_pool + 624LL)), ((char *)(string_pool + 2770LL))); if (env->ExceptionCheck()) { return (jobject) 0; }  } if (cstack1.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 656LL)), -1); else env->CallNonvirtualVoidMethod(cstack1.l, (cclasses[37]), (cmethods[43]), cstack2.l, cstack3.l); 
        if (env->ExceptionCheck()) { return (jobject) 0; }
        // New stack: 1
        // ATHROW; Stack: 1
        if (cstack0.l == nullptr) utils::throw_re(env, ((char *)(string_pool + 67LL)), ((char *)(string_pool + 679LL)), -1); else { jthrowable exception = (jthrowable) cstack0.l; env->Throw(exception); refs.insert(exception); } 
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
        L_CATCH_0: if (env->IsInstanceOf(cstack0.l, (cclasses[34]))) { goto L3; }
        env->Throw((jthrowable) cstack0.l); return (jobject) 0;
    }
    
    
    void __ngen_register_methods(JNIEnv *env, jclass clazz) {
        string_pool = string_pool::get_pool();

        if (jstring str = env->NewStringUTF(((char *)(string_pool + 15824LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[21] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 6316LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[40] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 19304LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[42] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 3995LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[27] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 19325LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[4] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 4241LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[67] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 4259LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[17] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 19346LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[37] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 4283LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[2] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 4480LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[24] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 4497LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[64] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 4511LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[51] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 6682LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[39] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 3299LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[8] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 18771LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[31] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 19371LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[46] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 19428LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[47] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 4617LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[10] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 18785LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[32] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 19431LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[3] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 4655LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[49] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 19452LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[20] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 19466LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[23] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 4744LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[63] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 12256LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[44] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 4771LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[13] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 4828LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[71] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 4864LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[18] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 4930LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[0] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 5021LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[14] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 5051LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[16] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 5074LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[57] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 5103LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[72] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 9625LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[48] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 5189LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[56] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 5261LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[58] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 5295LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[61] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 5313LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[1] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 5330LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[6] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 5362LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[53] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 5383LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[50] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 206LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[7] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 5430LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[60] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 19487LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[38] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 5465LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[65] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 19507LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[34] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 779LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[30] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 19509LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[59] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 5619LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[69] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 3960LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[25] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 19695LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[28] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 2897LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[70] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 19699LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[33] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 19718LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[41] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 14625LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[45] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 4797LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[15] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 14778LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[43] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 19744LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[19] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 19782LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[22] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 5130LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[52] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 5148LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[54] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 6896LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[62] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 5186LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[11] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 2579LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[35] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 19824LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[29] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 5359LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[36] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 7159LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[12] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 5505LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[9] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 5552LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[55] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 5586LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[5] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 5603LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[68] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 5652LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[26] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }
        if (jstring str = env->NewStringUTF(((char *)(string_pool + 20040LL)))) { if (jstring int_str = utils::get_interned(env, str)) { cstrings[66] = (jstring) env->NewGlobalRef(int_str); env->DeleteLocalRef(str); env->DeleteLocalRef(int_str); } }

        JNINativeMethod __ngen_methods[] = {
            { ((char *)(string_pool + 17954LL)), ((char *)(string_pool + 5813LL)), (void *)&__ngen_native_L3MonKe_I1 },
            { ((char *)(string_pool + 8785LL)), ((char *)(string_pool + 5813LL)), (void *)&__ngen_native_L3MonKe_A2 },
            { ((char *)(string_pool + 15102LL)), ((char *)(string_pool + 5813LL)), (void *)&__ngen_native_L3MonKe_p3 },
            { ((char *)(string_pool + 18771LL)), ((char *)(string_pool + 18785LL)), (void *)&__ngen_native_lambdau36authu3604 },
            { ((char *)(string_pool + 7912LL)), ((char *)(string_pool + 11970LL)), (void *)&__ngen_native_L3MonKe_i6 },
            { ((char *)(string_pool + 19272LL)), ((char *)(string_pool + 19282LL)), (void *)&__ngen_native_L3MonKe_K7 },
            { ((char *)(string_pool + 206LL)), ((char *)(string_pool + 2397LL)), (void *)&__ngen_native_a8 },
            { ((char *)(string_pool + 206LL)), ((char *)(string_pool + 2579LL)), (void *)&__ngen_native_a9 },
            { ((char *)(string_pool + 206LL)), ((char *)(string_pool + 2897LL)), (void *)&__ngen_native_a10 },
            { ((char *)(string_pool + 206LL)), ((char *)(string_pool + 3299LL)), (void *)&__ngen_native_a11 },
        };

        if (clazz) env->RegisterNatives(clazz, __ngen_methods, sizeof(__ngen_methods) / sizeof(__ngen_methods[0]));
        if (env->ExceptionCheck()) { fprintf(stderr, "Exception occured while registering native_jvm for %s\n", ((char *)(string_pool + 19325LL))); fflush(stderr); env->ExceptionDescribe(); env->ExceptionClear(); }

        {
            jclass hidden_class = env->FindClass(((char *)(string_pool + 5664LL)));
            JNINativeMethod __ngen_hidden_methods[] = {
                { ((char *)(string_pool + 20061LL)), ((char *)(string_pool + 5707LL)), (void *)&__ngen_special_clinit_6_5 },
            };
            if (hidden_class) env->RegisterNatives(hidden_class, __ngen_hidden_methods, sizeof(__ngen_hidden_methods) / sizeof(__ngen_hidden_methods[0]));
            if (env->ExceptionCheck()) { fprintf(stderr, "Exception occured while registering native_jvm for %s\n", ((char *)(string_pool + 5051LL))); fflush(stderr); env->ExceptionDescribe(); env->ExceptionClear(); }
            env->DeleteLocalRef(hidden_class);
        }
    }
}