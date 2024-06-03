/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.1.1
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */
package Effekseer.swig

object EffekseerCoreJNI {
    external fun new_EffekseerBackendCore(): Long
    external fun delete_EffekseerBackendCore(jarg1: Long)
    external fun EffekseerBackendCore_GetDevice(): Int
    external fun EffekseerBackendCore_InitializeWithOpenGL(): Boolean
    external fun EffekseerBackendCore_Terminate()
    external fun new_EffekseerEffectCore(): Long
    external fun delete_EffekseerEffectCore(jarg1: Long)
    external fun EffekseerEffectCore_Load(
        jarg1: Long,
        jarg1_: EffekseerEffectCore?,
        jarg2: ByteArray?,
        jarg3: Int,
        jarg4: Float
    ): Boolean

    external fun EffekseerEffectCore_GetTexturePath(
        jarg1: Long,
        jarg1_: EffekseerEffectCore?,
        jarg2: Int,
        jarg3: Int
    ): String?

    external fun EffekseerEffectCore_GetTextureCount(jarg1: Long, jarg1_: EffekseerEffectCore?, jarg2: Int): Int
    external fun EffekseerEffectCore_LoadTexture(
        jarg1: Long,
        jarg1_: EffekseerEffectCore?,
        jarg2: ByteArray?,
        jarg3: Int,
        jarg4: Int,
        jarg5: Int
    ): Boolean

    external fun EffekseerEffectCore_HasTextureLoaded(
        jarg1: Long,
        jarg1_: EffekseerEffectCore?,
        jarg2: Int,
        jarg3: Int
    ): Boolean

    external fun EffekseerEffectCore_GetModelPath(jarg1: Long, jarg1_: EffekseerEffectCore?, jarg2: Int): String?
    external fun EffekseerEffectCore_GetModelCount(jarg1: Long, jarg1_: EffekseerEffectCore?): Int
    external fun EffekseerEffectCore_LoadModel(
        jarg1: Long,
        jarg1_: EffekseerEffectCore?,
        jarg2: ByteArray?,
        jarg3: Int,
        jarg4: Int
    ): Boolean

    external fun EffekseerEffectCore_HasModelLoaded(jarg1: Long, jarg1_: EffekseerEffectCore?, jarg2: Int): Boolean
    external fun EffekseerEffectCore_GetMaterialPath(jarg1: Long, jarg1_: EffekseerEffectCore?, jarg2: Int): String?
    external fun EffekseerEffectCore_GetMaterialCount(jarg1: Long, jarg1_: EffekseerEffectCore?): Int
    external fun EffekseerEffectCore_LoadMaterial(
        jarg1: Long,
        jarg1_: EffekseerEffectCore?,
        jarg2: ByteArray?,
        jarg3: Int,
        jarg4: Int
    ): Boolean

    external fun EffekseerEffectCore_GetCurvePath(jarg1: Long, jarg1_: EffekseerEffectCore?, jarg2: Int): String?
    external fun EffekseerEffectCore_HasMaterialLoaded(jarg1: Long, jarg1_: EffekseerEffectCore?, jarg2: Int): Boolean
    external fun EffekseerEffectCore_GetCurveCount(jarg1: Long, jarg1_: EffekseerEffectCore?): Int
    external fun EffekseerEffectCore_LoadCurve(
        jarg1: Long,
        jarg1_: EffekseerEffectCore?,
        jarg2: ByteArray?,
        jarg3: Int,
        jarg4: Int
    ): Boolean

    external fun EffekseerEffectCore_HasCurveLoaded(jarg1: Long, jarg1_: EffekseerEffectCore?, jarg2: Int): Boolean
    external fun EffekseerEffectCore_GetTermMax(jarg1: Long, jarg1_: EffekseerEffectCore?): Int
    external fun EffekseerEffectCore_GetTermMin(jarg1: Long, jarg1_: EffekseerEffectCore?): Int
    external fun new_EffekseerManagerCore(): Long
    external fun delete_EffekseerManagerCore(jarg1: Long)
    external fun EffekseerManagerCore_Initialize__SWIG_0(
        jarg1: Long,
        jarg1_: EffekseerManagerCore?,
        jarg2: Int,
        jarg3: Boolean
    ): Boolean

    external fun EffekseerManagerCore_Initialize__SWIG_1(
        jarg1: Long,
        jarg1_: EffekseerManagerCore?,
        jarg2: Int
    ): Boolean

    external fun EffekseerManagerCore_Update(jarg1: Long, jarg1_: EffekseerManagerCore?, jarg2: Float)
    external fun EffekseerManagerCore_BeginUpdate(jarg1: Long, jarg1_: EffekseerManagerCore?)
    external fun EffekseerManagerCore_EndUpdate(jarg1: Long, jarg1_: EffekseerManagerCore?)
    external fun EffekseerManagerCore_UpdateHandleToMoveToFrame(
        jarg1: Long,
        jarg1_: EffekseerManagerCore?,
        jarg2: Int,
        jarg3: Float
    )

    external fun EffekseerManagerCore_Play(
        jarg1: Long,
        jarg1_: EffekseerManagerCore?,
        jarg2: Long,
        jarg2_: EffekseerEffectCore?
    ): Int

    external fun EffekseerManagerCore_StopAllEffects(jarg1: Long, jarg1_: EffekseerManagerCore?)
    external fun EffekseerManagerCore_Stop(jarg1: Long, jarg1_: EffekseerManagerCore?, jarg2: Int)
    external fun EffekseerManagerCore_SetPaused(jarg1: Long, jarg1_: EffekseerManagerCore?, jarg2: Int, jarg3: Boolean)
    external fun EffekseerManagerCore_SetShown(jarg1: Long, jarg1_: EffekseerManagerCore?, jarg2: Int, jarg3: Boolean)
    external fun EffekseerManagerCore_SendTrigger(jarg1: Long, jarg1_: EffekseerManagerCore?, jarg2: Int, jarg3: Int)
    external fun EffekseerManagerCore_SetEffectPosition(
        jarg1: Long,
        jarg1_: EffekseerManagerCore?,
        jarg2: Int,
        jarg3: Float,
        jarg4: Float,
        jarg5: Float
    )

    external fun EffekseerManagerCore_SetEffectRotation(
        jarg1: Long,
        jarg1_: EffekseerManagerCore?,
        jarg2: Int,
        jarg3: Float,
        jarg4: Float,
        jarg5: Float
    )

    external fun EffekseerManagerCore_SetEffectScale(
        jarg1: Long,
        jarg1_: EffekseerManagerCore?,
        jarg2: Int,
        jarg3: Float,
        jarg4: Float,
        jarg5: Float
    )

    external fun EffekseerManagerCore_SetLayerParameter(
        jarg1: Long,
        jarg1_: EffekseerManagerCore?,
        jarg2: Int,
        jarg3: Float,
        jarg4: Float,
        jarg5: Float,
        jarg6: Float
    )

    external fun EffekseerManagerCore_SetEffectTransformMatrix(
        jarg1: Long,
        jarg1_: EffekseerManagerCore?,
        jarg2: Int,
        jarg3: Float,
        jarg4: Float,
        jarg5: Float,
        jarg6: Float,
        jarg7: Float,
        jarg8: Float,
        jarg9: Float,
        jarg10: Float,
        jarg11: Float,
        jarg12: Float,
        jarg13: Float,
        jarg14: Float
    )

    external fun EffekseerManagerCore_SetEffectTransformBaseMatrix(
        jarg1: Long,
        jarg1_: EffekseerManagerCore?,
        jarg2: Int,
        jarg3: Float,
        jarg4: Float,
        jarg5: Float,
        jarg6: Float,
        jarg7: Float,
        jarg8: Float,
        jarg9: Float,
        jarg10: Float,
        jarg11: Float,
        jarg12: Float,
        jarg13: Float,
        jarg14: Float
    )

    external fun EffekseerManagerCore_DrawBack__SWIG_0(jarg1: Long, jarg1_: EffekseerManagerCore?, jarg2: Int)
    external fun EffekseerManagerCore_DrawBack__SWIG_1(jarg1: Long, jarg1_: EffekseerManagerCore?)
    external fun EffekseerManagerCore_DrawFront__SWIG_0(jarg1: Long, jarg1_: EffekseerManagerCore?, jarg2: Int)
    external fun EffekseerManagerCore_DrawFront__SWIG_1(jarg1: Long, jarg1_: EffekseerManagerCore?)
    external fun EffekseerManagerCore_SetLayer(jarg1: Long, jarg1_: EffekseerManagerCore?, jarg2: Int, jarg3: Int)
    external fun EffekseerManagerCore_SetCameraParameter(
        jarg1: Long,
        jarg1_: EffekseerManagerCore?,
        jarg2: Float,
        jarg3: Float,
        jarg4: Float,
        jarg5: Float,
        jarg6: Float,
        jarg7: Float
    )

    external fun EffekseerManagerCore_SetProjectionMatrix(
        jarg1: Long,
        jarg1_: EffekseerManagerCore?,
        jarg2: Float,
        jarg3: Float,
        jarg4: Float,
        jarg5: Float,
        jarg6: Float,
        jarg7: Float,
        jarg8: Float,
        jarg9: Float,
        jarg10: Float,
        jarg11: Float,
        jarg12: Float,
        jarg13: Float,
        jarg14: Float,
        jarg15: Float,
        jarg16: Float,
        jarg17: Float
    )

    external fun EffekseerManagerCore_SetCameraMatrix(
        jarg1: Long,
        jarg1_: EffekseerManagerCore?,
        jarg2: Float,
        jarg3: Float,
        jarg4: Float,
        jarg5: Float,
        jarg6: Float,
        jarg7: Float,
        jarg8: Float,
        jarg9: Float,
        jarg10: Float,
        jarg11: Float,
        jarg12: Float,
        jarg13: Float,
        jarg14: Float,
        jarg15: Float,
        jarg16: Float,
        jarg17: Float
    )

    external fun EffekseerManagerCore_Exists(jarg1: Long, jarg1_: EffekseerManagerCore?, jarg2: Int): Boolean
    external fun EffekseerManagerCore_SetViewProjectionMatrixWithSimpleWindow(
        jarg1: Long,
        jarg1_: EffekseerManagerCore?,
        jarg2: Int,
        jarg3: Int
    )

    external fun EffekseerManagerCore_SetDynamicInput(
        jarg1: Long,
        jarg1_: EffekseerManagerCore?,
        jarg2: Int,
        jarg3: Int,
        jarg4: Float
    )

    external fun EffekseerManagerCore_GetDynamicInput(
        jarg1: Long,
        jarg1_: EffekseerManagerCore?,
        jarg2: Int,
        jarg3: Int
    ): Float

    external fun EffekseerManagerCore_LaunchWorkerThreads(jarg1: Long, jarg1_: EffekseerManagerCore?, jarg2: Int)
    external fun EffekseerManagerCore_SetBackground(
        jarg1: Long,
        jarg1_: EffekseerManagerCore?,
        jarg2: Long,
        jarg3: Boolean
    )

    external fun EffekseerManagerCore_UnsetBackground(jarg1: Long, jarg1_: EffekseerManagerCore?)
    external fun EffekseerManagerCore_SetDepth(jarg1: Long, jarg1_: EffekseerManagerCore?, jarg2: Long, jarg3: Boolean)
    external fun EffekseerManagerCore_UnsetDepth(jarg1: Long, jarg1_: EffekseerManagerCore?)
    external fun EffekseerManagerCore_GetInstanceCount(jarg1: Long, jarg1_: EffekseerManagerCore?, jarg2: Int): Int
    external fun EffekseerManagerCore_GetTotalInstanceCount(jarg1: Long, jarg1_: EffekseerManagerCore?): Int
}
