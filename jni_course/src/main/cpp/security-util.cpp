#include <jni.h>
#include <string>
#include<android/log.h>

#ifndef LOG_TAG
#define LOG_TAG "Jni_Log"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG,LOG_TAG ,__VA_ARGS__) // 定义LOGD类型
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,LOG_TAG ,__VA_ARGS__) // 定义LOGI类型
#define LOGW(...) __android_log_print(ANDROID_LOG_WARN,LOG_TAG ,__VA_ARGS__) // 定义LOGW类型
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,LOG_TAG ,__VA_ARGS__) // 定义LOGE类型
#define LOGF(...) __android_log_print(ANDROID_LOG_FATAL,LOG_TAG ,__VA_ARGS__) // 定义LOGF类型
#endif

extern "C" JNIEXPORT jstring JNICALL
Java_com_huoergai_jni_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT void JNICALL
Java_com_huoergai_jni_MainActivity_printUser(JNIEnv *env, jobject thiz, jobject foo) {
    jclass userClass = env->GetObjectClass(foo);
    jmethodID toStringMethodId = env->GetMethodID(userClass, "toString", "()Ljava/lang/String;");
    auto toString = (jstring) env->CallObjectMethod(foo, toStringMethodId);
    LOGD("log from JNI: %s", env->GetStringUTFChars(toString, 0));
}

extern "C"
JNIEXPORT jobject JNICALL
Java_com_huoergai_jni_MainActivity_getUser(JNIEnv *env, jobject thiz) {
    jclass userClass = env->FindClass("com/huoergai/jni/User");
    jobject userObj = env->AllocObject(userClass);

    jfieldID nameField = env->GetFieldID(userClass, "name", "Ljava/lang/String;");
    jfieldID ageField = env->GetFieldID(userClass, "age", "I");
    jfieldID statureField = env->GetFieldID(userClass, "stature", "F");

    std::string name = "xiao_ming";
    env->SetObjectField(userObj, nameField, env->NewStringUTF(name.c_str()));
    env->SetIntField(userObj, ageField, 10);
    env->SetFloatField(userObj, statureField, 1.70f);

    return userObj;
}