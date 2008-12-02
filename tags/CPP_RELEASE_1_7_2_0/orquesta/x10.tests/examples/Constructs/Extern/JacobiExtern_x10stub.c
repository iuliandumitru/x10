/*
 * Filename:JacobiExtern_x10stub.c
 * Generated: 4/16/08 11:11 AM */
/*Automatically generated -- DO NOT EDIT THIS FILE */
#include <sys/types.h>
#include <jni.h>
#ifdef __cplusplus
extern "C" {
#endif

/* * * * * * * */
extern double   JacobiExtern_computeError(double* dbl_x10PoInTeR, int* dbl_x10DeScRiPtOr, double* temp_x10PoInTeR, int* temp_x10DeScRiPtOr);
JNIEXPORT jdouble JNICALL
Java_JacobiExtern_JacobiExtern_1computeError(JNIEnv *env, jclass cls, jdoubleArray dbl, jintArray dbl_x10DeScRiPtOr, jdoubleArray temp, jintArray temp_x10DeScRiPtOr) {
#ifdef __cplusplus
  void *dbl_x10PoInTeR = (env)->GetPrimitiveArrayCritical(dbl,0);
#else
  void *dbl_x10PoInTeR = (*env)->GetPrimitiveArrayCritical(env,dbl,0);
#endif
#ifdef __cplusplus
  void *dbl_x10DeScRiPtOr_x10PoInTeR = (env)->GetPrimitiveArrayCritical(dbl_x10DeScRiPtOr,0);
#else
  void *dbl_x10DeScRiPtOr_x10PoInTeR = (*env)->GetPrimitiveArrayCritical(env,dbl_x10DeScRiPtOr,0);
#endif
#ifdef __cplusplus
  void *temp_x10PoInTeR = (env)->GetPrimitiveArrayCritical(temp,0);
#else
  void *temp_x10PoInTeR = (*env)->GetPrimitiveArrayCritical(env,temp,0);
#endif
#ifdef __cplusplus
  void *temp_x10DeScRiPtOr_x10PoInTeR = (env)->GetPrimitiveArrayCritical(temp_x10DeScRiPtOr,0);
#else
  void *temp_x10DeScRiPtOr_x10PoInTeR = (*env)->GetPrimitiveArrayCritical(env,temp_x10DeScRiPtOr,0);
#endif


double _x10ReTuRnVaL=(jdouble)  JacobiExtern_computeError((double*)dbl_x10PoInTeR, (int*) dbl_x10DeScRiPtOr_x10PoInTeR, (double*)temp_x10PoInTeR, (int*) temp_x10DeScRiPtOr_x10PoInTeR);

#ifdef __cplusplus
  (env)->ReleasePrimitiveArrayCritical(temp_x10DeScRiPtOr,temp_x10DeScRiPtOr_x10PoInTeR,0);
#else
  (*env)->ReleasePrimitiveArrayCritical(env,temp_x10DeScRiPtOr,temp_x10DeScRiPtOr_x10PoInTeR,0);
#endif
#ifdef __cplusplus
  (env)->ReleasePrimitiveArrayCritical(temp,temp_x10PoInTeR,0);
#else
  (*env)->ReleasePrimitiveArrayCritical(env,temp,temp_x10PoInTeR,0);
#endif
#ifdef __cplusplus
  (env)->ReleasePrimitiveArrayCritical(dbl_x10DeScRiPtOr,dbl_x10DeScRiPtOr_x10PoInTeR,0);
#else
  (*env)->ReleasePrimitiveArrayCritical(env,dbl_x10DeScRiPtOr,dbl_x10DeScRiPtOr_x10PoInTeR,0);
#endif
#ifdef __cplusplus
  (env)->ReleasePrimitiveArrayCritical(dbl,dbl_x10PoInTeR,0);
#else
  (*env)->ReleasePrimitiveArrayCritical(env,dbl,dbl_x10PoInTeR,0);
#endif
return _x10ReTuRnVaL;
}
#ifndef __WIN32__
extern JNIEXPORT __typeof(Java_JacobiExtern_JacobiExtern_1computeError) JNICALL
_Java_JacobiExtern_JacobiExtern_1computeError
__attribute((alias("Java_JacobiExtern_JacobiExtern_1computeError")));
#endif


#ifdef __cplusplus
}
#endif
