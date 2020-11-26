import java.nio.charset.Charset


// Linking to Formular
FormFieldContent zipCodeFormField = applicationForm.getFields().get("adressGroup:0:postalCode")

// Getting value of field
String zipCodeFromUser = zipCodeFormField.getValue() as String

// Get prozessparameter and write it into a byte array
byte[] csvFile = ProzessParameterReader.getProcessParameterAsBinary( 6029145L,"validZipCodeCsv")

// If the given postal code is not in the byte array, set 'isFormValid' to false and wirte an error message.
if (!(ValidateZipCodeTask.validZipCode(csvFile, zipCodeFromUser))) {
  execution.setVariable("isFormValid", false)
  final String errorZipValidation = "Die eingegebene Postleitzahl: $zipCodeFromUser liegt außerhalb unseres Zuständigkeitsbereichs."
  zipCodeFormField.setValidationMessages([errorZipValidation])
}

class ValidateZipCodeTask {
  static boolean validZipCode(byte[] zipCodesByteArray, String userSpecifiedZipCode) {
    String validZipCodesString = new String(zipCodesByteArray, Charset.forName("UTF-8"))

    // Split lines
    String[] lines = validZipCodesString.split("\n")
    for (line in lines) {
      // ignore the header line
      if (line == "Ort;Zusatz;Plz;Vorwahl;Bundesland") {
        continue
      }

      String[] cells = line.split(";")
      String currentZipCode = cells[2]

      if (currentZipCode == userSpecifiedZipCode) {
        // We found the user specified zip code in the supplied file of valid zip codes!
        return true
      } else {
        // This is not the user-specified zip code, but another one might be. So we keep searching!
      }
    }

    // Oh no! The list is empty now and we didn't find the user specified zip code
    // Therefore it must be invalid
    return false
  }
}