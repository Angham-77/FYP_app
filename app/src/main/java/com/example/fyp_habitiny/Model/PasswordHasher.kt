package com.example.fyp_habitiny.Model
import at.favre.lib.crypto.bcrypt.BCrypt
import java.security.MessageDigest

class PasswordHasher {
    companion object {
        fun hashPassword(plainPassword: String): String {
            val digest = MessageDigest.getInstance("SHA-256")
            val hashedBytes = digest.digest(plainPassword.toByteArray())
            return bytesToHex(hashedBytes)
        }

        private fun bytesToHex(bytes: ByteArray): String {
            val hexArray = "0123456789ABCDEF".toCharArray()
            val hexChars = CharArray(bytes.size * 2)
            for (i in bytes.indices) {
                val v = bytes[i].toInt() and 0xFF
                hexChars[i * 2] = hexArray[v ushr 4]
                hexChars[i * 2 + 1] = hexArray[v and 0x0F]
            }
            return String(hexChars)
        }
    }
}



