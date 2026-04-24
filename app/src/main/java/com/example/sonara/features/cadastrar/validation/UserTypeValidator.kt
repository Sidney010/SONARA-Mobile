package com.example.sonara.features.cadastrar.validation

import com.example.sonara.core.validation.ValidationResult
import com.example.sonara.core.validation.Validator
import com.example.sonara.domain.model.UserType

object UserTypeValidator : Validator<UserType?> {

    override fun validate(value: UserType?): ValidationResult {
        return if (value == null) {
            ValidationResult.Error("Selecione um tipo de usuário")
        } else {
            ValidationResult.Success
        }
    }
}