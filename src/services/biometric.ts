import ReactNativeBiometrics, {BiometryTypes} from 'react-native-biometrics';
const rnBiometrics = new ReactNativeBiometrics();

export async function checkBiometricsAvailability() {
  rnBiometrics.isSensorAvailable().then(resultObject => {
    const {available, biometryType} = resultObject;

    if (available && biometryType === BiometryTypes.TouchID) {
      console.log('TouchID is supported');
    } else if (available && biometryType === BiometryTypes.FaceID) {
      console.log('FaceID is supported');
    } else if (available && biometryType === BiometryTypes.Biometrics) {
      console.log('Biometrics is supported');
    } else {
      console.log('Biometrics not supported');
    }
  });
}

export async function promptForBiometric() {
  try {
    const resultObject = await rnBiometrics.simplePrompt({
      promptMessage: 'Confirm fingerprint',
    });
    if (resultObject.success) {
      return true;
    } else {
      return false;
    }
  } catch (error) {
    console.log(error);
    return false;
  }
}
