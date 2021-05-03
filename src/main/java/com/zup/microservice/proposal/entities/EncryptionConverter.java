package com.zup.microservice.proposal.entities;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Converter
public class EncryptionConverter implements AttributeConverter<String, String> {
//
//	@Value("${encryption.key}")
//	private String secretKey;
	private final String algorithm = "AES";
	
	private final Key key;
	private final Cipher decryptor;
	private final Cipher encryptor;
	
	public EncryptionConverter(@Value("${encryption.key}") String secretKey) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
		key = new SecretKeySpec(secretKey.getBytes(), algorithm);
		encryptor = Cipher.getInstance(algorithm);
		encryptor.init(Cipher.ENCRYPT_MODE, key);
		decryptor = Cipher.getInstance(algorithm);
		decryptor.init(Cipher.DECRYPT_MODE, key);
	}

	@Override
	public String convertToDatabaseColumn(String attribute) {
		try {
			return Base64.getEncoder().encodeToString(encryptor.doFinal(attribute.getBytes()));
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
			throw new IllegalArgumentException("documento não pode ser encriptado");
		}
	}

	@Override
	public String convertToEntityAttribute(String dbData) {
		try {
			return new String(decryptor.doFinal(Base64.getDecoder().decode(dbData)));
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
			throw new IllegalArgumentException("documento não pode ser desencriptado");
		}
	}

}
