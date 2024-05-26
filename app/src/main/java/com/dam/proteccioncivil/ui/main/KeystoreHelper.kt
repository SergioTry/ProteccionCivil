package com.dam.proteccioncivil.ui.main

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.IvParameterSpec

object KeystoreHelper {

    private const val KEY_ALIAS = "HelloWorld"
    private const val ANDROID_KEYSTORE = "AndroidKeyStore"

    @Throws(Exception::class)
    fun generateKey() {
        val keyStore = KeyStore.getInstance(ANDROID_KEYSTORE)
        keyStore.load(null)

        if (!keyStore.containsAlias(KEY_ALIAS)) {
            val keyGenerator =
                KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, ANDROID_KEYSTORE)
            keyGenerator.init(
                KeyGenParameterSpec.Builder(
                    KEY_ALIAS,
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                )
                    .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                    // En versiones futuras se debe implementar la seguridad de autenticación
                    // .setUserAuthenticationRequired(true)  // Requiere autenticación del usuario
                    .setRandomizedEncryptionRequired(true)
                    .build()
            )
            keyGenerator.generateKey()
        }
    }

    @Throws(Exception::class)
    fun getKey(): SecretKey {
        val keyStore = KeyStore.getInstance(ANDROID_KEYSTORE)
        keyStore.load(null)
        return (keyStore.getEntry(KEY_ALIAS, null) as KeyStore.SecretKeyEntry).secretKey
    }

    @Throws(Exception::class)
    fun encriptar(datos: String, secretKey: SecretKey): Pair<String, ByteArray> {
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        val datosEncriptadosBytes = cipher.doFinal(datos.toByteArray())
        val datosEncriptados = android.util.Base64.encodeToString(datosEncriptadosBytes, android.util.Base64.DEFAULT)
        return Pair(datosEncriptados, cipher.iv)
    }


    @Throws(Exception::class)
    fun desencriptar(datosEncriptados: String, iv: ByteArray, secretKey: SecretKey): String {
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        val datosEncriptadosBytes = android.util.Base64.decode(datosEncriptados, android.util.Base64.DEFAULT)
        val gcmSpec = GCMParameterSpec(128, iv)  // 128 es el tamaño del tag de autenticación
        cipher.init(Cipher.DECRYPT_MODE, secretKey, gcmSpec)
        val datosDesencriptadosBytes = cipher.doFinal(datosEncriptadosBytes)
        return String(datosDesencriptadosBytes)
    }

}