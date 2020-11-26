import groovy.time.TimeCategory



// Have to be replaced by the desired minimal age that shall be allowed
int minimumAgeAllowed = REPLACE_ME_BY_NUMBER

// Setts 'isFormValid' on false if the value from 'personsAge' is not reached
if (!(new MinimumAgeValidator(minimumAgeAllowed).isAtLeastThatOld(birthDateValue))) {
  execution.setVariable("isFormValid", false)
  final String errorAgeValidation = "Für diesen Antrag muss das Alter mindestens $minimumAgeAllowed Jahre betragen."
  birthDateFormField.setValidationMessages([errorAgeValidation])
}



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

