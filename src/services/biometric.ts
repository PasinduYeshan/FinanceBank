import ReactNativeBiometrics, { BiometryTypes } from 'react-native-biometrics'

const rnBiometrics = new ReactNativeBiometrics()

rnBiometrics.isSensorAvailable()
  .then((resultObject) => {
    const { available, biometryType } = resultObject

    if (available && biometryType === BiometryTypes.TouchID) {
      console.log('TouchID is supported')
    } else if (available && biometryType === BiometryTypes.FaceID) {
      console.log('FaceID is supported')
    } else if (available && biometryType === BiometryTypes.Biometrics) {
      console.log('Biometrics is supported')
    } else {
      console.log('Biometrics not supported')
    }
  })