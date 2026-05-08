package com.example.sonara.features.cadastrar.validation

import com.example.sonara.core.validation.ValidationResult
import com.example.sonara.core.validation.Validator
import com.example.sonara.domain.model.Gender

object GenderValidator : Validator<Gender?> {

    override fun validate(value: Gender?): ValidationResult {

        return if (value == null) {

            ValidationResult.Error(
                "Selecione um gênero"
            )

        } else {
            ValidationResult.Success
        }
    }
}