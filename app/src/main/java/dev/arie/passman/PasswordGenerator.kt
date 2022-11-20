package dev.arie.passman

class PasswordGenerator {

    private external fun generate(): String?

    fun generatePassword(): String? {
        return generate()
    }
}
