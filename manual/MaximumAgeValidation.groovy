

// Getting value from birthday field in Form
FormFieldContent birthDateFormField = applicationForm.getFields().get("personalDataGroup:0:birthDate")

// Saving value in Date format
Date birthDateValue = birthDateFormField.getValue() as Date

// Have to be replaced by the desired maximal age that shall be allowed
int maximumAgeAllowed = REPLACE_ME_BY_NUMBER

// Setts 'isFormValid' on false if the value from 'personsAge' is reached
if ((new MinimumAgeValidator(maximumAgeAllowed).isAtLeastThatOld(birthDateValue))) {
  execution.setVariable("isFormValid", false)
  final String errorAgeValidation = "Für diesen Antrag darf das Alter nicht höher als $maximumAgeAllowed Jahre betragen."
  birthDateFormField.setValidationMessages([errorAgeValidation])
}

import groovy.time.TimeCategory

// Code made by Dennis Weber
class MinimumAgeValidator {
  private int minimumAgeInYears

  MinimumAgeValidator(int minimumAgeInYears) {
    this.minimumAgeInYears = minimumAgeInYears
  }

  /**
   * Returns true if a person born on "birthday" is at least as old
   * as the value specified in the constructor
   * @param birthday
   */
  boolean isAtLeastThatOld(Date birthday) {
    use(TimeCategory) {
      Date now = new Date()
      Date someYearsAgo = now - minimumAgeInYears.year

      return birthday.before(someYearsAgo)
    }
  }
}
