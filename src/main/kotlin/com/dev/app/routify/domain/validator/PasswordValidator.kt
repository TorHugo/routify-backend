package com.dev.app.routify.domain.validator

import com.dev.app.routify.domain.annotation.Password
import com.dev.app.routify.domain.exception.template.DomainException
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class PasswordValidator : ConstraintValidator<Password, String> {
    companion object {
        const val DEFAULT_PASSWORD_CANNOT_BE_NULL: String = "password.cannot.be.null"
        const val DEFAULT_PASSWORD_LEAST_EIGHT_CHARACTERS: String = "password.least.eight.characters"
        const val DEFAULT_PASSWORD_DOES_NOT_CONTAIN_UPPERCASE: String = "password.does.not.contain.uppercase"
        const val DEFAULT_PASSWORD_DOES_NOT_CONTAIN_LOWERCASE: String = "password.does.not.contain.lowercase"
        const val DEFAULT_PASSWORD_DOES_NOT_CONTAIN_SPECIAL_CHARACTER: String = "password.does.not.contain.special.character"

        const val DEFAULT_PASSWORD_UPPERCASE_REGEX: String = ".*[A-Z].*"
        const val DEFAULT_PASSWORD_LOWERCASE_REGEX: String = ".*[a-z].*"
        const val DEFAULT_PASSWORD_SPECIAL_CHARACTER_REGEX: String = ".*\\W.*"
    }

    override fun isValid(password: String?, context: ConstraintValidatorContext?): Boolean {
        if (password == null) throw DomainException(DEFAULT_PASSWORD_CANNOT_BE_NULL)
        if (password.length < 8) throw DomainException(DEFAULT_PASSWORD_LEAST_EIGHT_CHARACTERS)
        if (!password.matches(Regex(DEFAULT_PASSWORD_UPPERCASE_REGEX))) throw DomainException(DEFAULT_PASSWORD_DOES_NOT_CONTAIN_UPPERCASE)
        if (!password.matches(Regex(DEFAULT_PASSWORD_LOWERCASE_REGEX))) throw DomainException(DEFAULT_PASSWORD_DOES_NOT_CONTAIN_LOWERCASE)
        if (!password.matches(Regex(DEFAULT_PASSWORD_SPECIAL_CHARACTER_REGEX))) throw DomainException(DEFAULT_PASSWORD_DOES_NOT_CONTAIN_SPECIAL_CHARACTER)
        return true
    }
}
