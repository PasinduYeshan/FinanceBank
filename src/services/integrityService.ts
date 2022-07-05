import {NativeModules} from 'react-native';
import {storeString} from '../utils/Storage';
// Get Entgra Services from Native Modules and export
const IntegrityServiceManager: IIntegrityServiceManager = NativeModules.IntegrityServiceManager;

interface IIntegrityServiceManager {
  getIntegrityToken(nonce: string): Promise<any>;
}

/**
 * This method return a Promise that resolves with the integrity token,
 * @param nonce - The nonce to use in the integrity token
 * @example
 * ```
 * getIntegrityToken(nonce).then((response) => {
 *     console.log(response);
 * }).catch((error) => {
 *     console.error(error);
 * });
 * ```
 */
 export async function getIntegrityToken(nonce: string): Promise<string> {
  try {
    const res = await IntegrityServiceManager.getIntegrityToken(nonce);
    return res;
  } catch (error) {
    console.error(error);
    return 'Get Integrity Token Failed';
  }
}

export default IntegrityServiceManager as IIntegrityServiceManager;
