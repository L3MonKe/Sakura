#include "native_jvm.hpp"
#include "native_jvm_output.hpp"
#include "string_pool.hpp"

#include "output/dev_sakura_client_Mahiro_BF_0.hpp"
#include "output/dev_sakura_client_Mahiro_BY_1.hpp"
#include "output/dev_sakura_client_Mahiro_Bh_2.hpp"
#include "output/dev_sakura_client_Mahiro_Kg_3.hpp"
#include "output/dev_sakura_client_Mahiro_T7_4.hpp"
#include "output/dev_sakura_client_Mahiro_TJ_5.hpp"
#include "output/dev_sakura_client_Mahiro_T__6.hpp"
#include "output/dev_sakura_client_Mahiro_UJ_7.hpp"
#include "output/dev_sakura_client_Mahiro_e_8.hpp"
#include "output/dev_sakura_client_Mahiro_eN_9.hpp"
#include "output/dev_sakura_client_Mahiro_eZ_10.hpp"
#include "output/dev_sakura_client_Mahiro_es_11.hpp"
#include "output/data_native0_hidden_Hidden0.hpp"


namespace native_jvm {

    typedef void (* reg_method)(JNIEnv *,jclass);

    reg_method reg_methods[12];

    void register_for_class(JNIEnv *env, jclass, jint id, jclass clazz) {
        reg_methods[id](env, clazz);
    }

    void prepare_lib(JNIEnv *env) {
        utils::init_utils(env);
        if (env->ExceptionCheck())
            return;

        char* string_pool = string_pool::get_pool();

        reg_methods[0] = &(native_jvm::classes::__ngen_dev_sakura_client_Mahiro_BF_0::__ngen_register_methods);
        reg_methods[1] = &(native_jvm::classes::__ngen_dev_sakura_client_Mahiro_BY_1::__ngen_register_methods);
        reg_methods[2] = &(native_jvm::classes::__ngen_dev_sakura_client_Mahiro_Bh_2::__ngen_register_methods);
        reg_methods[3] = &(native_jvm::classes::__ngen_dev_sakura_client_Mahiro_Kg_3::__ngen_register_methods);
        reg_methods[4] = &(native_jvm::classes::__ngen_dev_sakura_client_Mahiro_T7_4::__ngen_register_methods);
        reg_methods[5] = &(native_jvm::classes::__ngen_dev_sakura_client_Mahiro_TJ_5::__ngen_register_methods);
        reg_methods[6] = &(native_jvm::classes::__ngen_dev_sakura_client_Mahiro_T__6::__ngen_register_methods);
        reg_methods[7] = &(native_jvm::classes::__ngen_dev_sakura_client_Mahiro_UJ_7::__ngen_register_methods);
        reg_methods[8] = &(native_jvm::classes::__ngen_dev_sakura_client_Mahiro_e_8::__ngen_register_methods);
        reg_methods[9] = &(native_jvm::classes::__ngen_dev_sakura_client_Mahiro_eN_9::__ngen_register_methods);
        reg_methods[10] = &(native_jvm::classes::__ngen_dev_sakura_client_Mahiro_eZ_10::__ngen_register_methods);
        reg_methods[11] = &(native_jvm::classes::__ngen_dev_sakura_client_Mahiro_es_11::__ngen_register_methods);
        env->DeleteLocalRef(env->DefineClass(((char *)(string_pool + 6490LL)), nullptr, native_jvm::data::__ngen_data_native0_hidden_Hidden0::get_class_data(), native_jvm::data::__ngen_data_native0_hidden_Hidden0::get_class_data_length()));


        if (env->ExceptionCheck())
            return;

        char method_name[] = "registerNativesForClass";
        char method_desc[] = "(ILjava/lang/Class;)V";
        JNINativeMethod loader_methods[] = {
            { (char *) method_name, (char *) method_desc, (void *)&register_for_class }
        };
        env->RegisterNatives(env->FindClass("native0/Loader"), loader_methods, 1);
    }
}

extern "C" JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *vm, void *reserved) {
    JNIEnv *env = nullptr;
    vm->GetEnv((void **)&env, JNI_VERSION_1_8);
    native_jvm::prepare_lib(env);
    return JNI_VERSION_1_8;
}