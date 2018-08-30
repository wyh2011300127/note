package com.yuheng.wang.common.util;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * RSA 工具类。提供加密，解密，生成密钥对等方法
 * 
 * @author wangyuheng
 * @date 2018/08/27
 */
public class RsaUtil {
    private static Logger log = LoggerFactory.getLogger(RsaUtil.class);
    private static String RSAKeyStoreStr = RsaUtil.class.getResource("").getPath();
    // 启动项目时变更为WEB-INF，执行main方法时改成target
    private final static String WEB_PATH = "WEB-INF";
    private static String RSAKeyStore
        = RSAKeyStoreStr.substring(0, RSAKeyStoreStr.indexOf(WEB_PATH)) + "/" + WEB_PATH + "/classes/RSAKey.txt";
    private static KeyFactory keyFac = null;
    private static RsaUtil rsaUtil = new RsaUtil();
    private static Object lock = new Object();

    static {
        try {
            keyFac = KeyFactory.getInstance("RSA", RsaUtil.getBouncyProvider());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            // 生成一个加密钥对
            KeyPair keyPair = RsaUtil.generateKeyPair();
            PublicKey pubKey = keyPair.getPublic();
            PrivateKey priKey = keyPair.getPrivate();
            System.out.println("keyPair.getPublic():" + pubKey);
            System.out.println("keyPair.getPrivate():" + priKey);

            // 测试字符串
            String testStr = "ABCDETFY";
            // 加密
            byte[] encrypt = encrypt(pubKey, testStr.getBytes());
            System.out.println(new String(encrypt));
            // 解密
            byte[] decrypt = decrypt(priKey, encrypt);
            System.out.println(new String(decrypt));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取一个实例
     * 
     * @return
     */
    public static RsaUtil getInstance() {
        return rsaUtil;
    }

    private static Provider getBouncyProvider() {
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            synchronized (lock) {
                if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
                    log.info("Security Provider BC not found !");
                    Security.addProvider(new BouncyCastleProvider());
                }
            }
        }
        return Security.getProvider(BouncyCastleProvider.PROVIDER_NAME);
    }

    /**
     * * 生成密钥对 *
     * 
     * @return KeyPair *
     * @throws EncryptException
     */
    public static KeyPair generateKeyPair() throws Exception {
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA", RsaUtil.getBouncyProvider());
            // 没什么好说的了，这个值关系到块加密的大小，可以更改，但是不要太大，否则效率会低
            final int keySize = 1024;
            keyPairGen.initialize(keySize, new SecureRandom());
            KeyPair keyPair = keyPairGen.generateKeyPair();

            saveKeyPair(keyPair);
            return keyPair;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * 获取密钥对
     * 
     * @return
     * @throws Exception
     */
    public static KeyPair getKeyPair() throws Exception {
        FileInputStream fis = new FileInputStream(RSAKeyStore);
        ObjectInputStream oos = new ObjectInputStream(fis);
        KeyPair kp = (KeyPair)oos.readObject();
        oos.close();
        fis.close();
        return kp;
    }

    /**
     * 保存生成的私密
     * 
     * @param kp
     * @throws Exception
     */
    public static void saveKeyPair(KeyPair kp) throws Exception {
        FileOutputStream fos = new FileOutputStream(RSAKeyStore);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        // 生成密钥
        oos.writeObject(kp);
        oos.close();
        fos.close();
    }

    /**
     * * 生成公钥 *
     * 
     * @param modulus *
     * @param publicExponent *
     * @return RSAPublicKey *
     * @throws Exception
     */
    public static RSAPublicKey generateRSAPublicKey(byte[] modulus, byte[] publicExponent) throws Exception {
        RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(new BigInteger(modulus), new BigInteger(publicExponent));
        try {
            return (RSAPublicKey)keyFac.generatePublic(pubKeySpec);
        } catch (InvalidKeySpecException ex) {
            throw new Exception(ex.getMessage());
        }
    }

    /**
     * * 生成私钥 *
     * 
     * @param modulus *
     * @param privateExponent *
     * @return RSAPrivateKey *
     * @throws Exception
     */
    public static RSAPrivateKey generateRSAPrivateKey(byte[] modulus, byte[] privateExponent) throws Exception {
        RSAPrivateKeySpec priKeySpec = new RSAPrivateKeySpec(new BigInteger(modulus), new BigInteger(privateExponent));
        try {
            return (RSAPrivateKey)keyFac.generatePrivate(priKeySpec);
        } catch (InvalidKeySpecException ex) {
            throw new Exception(ex.getMessage());
        }
    }

    /**
     * * 加密 *
     * 
     * @param key 加密的密钥 *
     * @param data 待加密的明文数据 *
     * @return 加密后的数据 *
     * @throws Exception
     */
    public static byte[] encrypt(PublicKey pk, byte[] data) throws Exception {
        try {
            Cipher cipher = Cipher.getInstance("RSA", RsaUtil.getBouncyProvider());
            cipher.init(Cipher.ENCRYPT_MODE, pk);
            // 获得加密块大小，如：加密前数据为128个byte，而key_size=1024
            int blockSize = cipher.getBlockSize();
            // 加密块大小为127
            // byte,加密后为128个byte;因此共有2个加密块，第一个127
            // byte第二个为1个byte
            // 获得加密块加密后块大小
            int outputSize = cipher.getOutputSize(data.length);
            int leavedSize = data.length % blockSize;
            int blocksSize = leavedSize != 0 ? data.length / blockSize + 1 : data.length / blockSize;
            byte[] raw = new byte[outputSize * blocksSize];
            int i = 0;
            while (data.length - i * blockSize > 0) {
                if (data.length - i * blockSize > blockSize) {
                    cipher.doFinal(data, i * blockSize, blockSize, raw, i * outputSize);
                } else {
                    cipher.doFinal(data, i * blockSize, data.length - i * blockSize, raw, i * outputSize);
                }
                // 这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把byte[]放到
                // ByteArrayOutputStream中，而最后doFinal的时候才将所有的byte[]进行加密，可是到了此时加密块大小很可能已经超出了
                // OutputSize所以只好用dofinal方法。

                i++;
            }
            return raw;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * * 解密 *
     * 
     * @param key 解密的密钥 *
     * @param raw 已经加密的数据 *
     * @return 解密后的明文 *
     * @throws Exception
     */
    public static byte[] decrypt(PrivateKey pk, byte[] raw) throws Exception {
        try {
            Cipher cipher = Cipher.getInstance("RSA", RsaUtil.getBouncyProvider());
            cipher.init(Cipher.DECRYPT_MODE, pk);
            int blockSize = cipher.getBlockSize();
            ByteArrayOutputStream bout = new ByteArrayOutputStream(64);
            int j = 0;

            while (raw.length - j * blockSize > 0) {
                bout.write(cipher.doFinal(raw, j * blockSize, blockSize));
                j++;
            }
            return bout.toByteArray();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * 根据加密字符串进行解密
     * 
     * @param encodeStr
     * @return
     * @throws Exception
     */
    public String getDecodeStr(String encodeStr) throws Exception {
        StringBuffer sb = new StringBuffer();
        try {
            byte[] enResult = stringToByte(encodeStr);
            byte[] deResult = decrypt(getKeyPair().getPrivate(), enResult);
            sb.append(new String(deResult));
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return sb.reverse().toString();
    }

    public static byte[] stringToByte(String str) {

        if (null == str || "".equals(str)) {
            return null;
        }
        str = str.toUpperCase();
        int length = str.length() / 2;
        char[] hexChars = str.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte)(charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));

        }
        return d;
    }

    public static byte charToByte(char c) {
        return (byte)"0123456789ABCDEF".indexOf(c);
    }

}
