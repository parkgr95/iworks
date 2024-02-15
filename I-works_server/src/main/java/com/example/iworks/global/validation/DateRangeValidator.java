package com.example.iworks.global.validation;

import com.example.iworks.global.dto.DateCondition;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class DateRangeValidator implements ConstraintValidator<ValidDateRange, DateCondition> {
    @Override
    public void initialize(ValidDateRange constraintAnnotation) {
    }

    @Override
    public boolean isValid(DateCondition dateCondition, ConstraintValidatorContext context) {
        if (dateCondition == null) {
            return true;
        }
        return dateCondition.getStartDate() == null || dateCondition.getEndDate() == null ||
                dateCondition.getStartDate().isBefore(dateCondition.getEndDate());
    }
}
