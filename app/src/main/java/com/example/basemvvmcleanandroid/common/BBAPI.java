// Copyright(c) 2013 Bluebird Inc. All rights reserved.
package com.example.basemvvmcleanandroid.common;

public class BBAPI {
			
	//+ RESULT
	public static final int BBAPI_SUCCESS				= 0;
	public static final int BBAPI_FAILED				= -1;
	public static final int BBAPI_NOT_SUPPORTED			= -2;
	public static final int BBAPI_ERROR_CHECKSUM		= -3;
	public static final int BBAPI_ERROR_NO_RESPONSE		= -4;
	public static final int BBAPI_BATTERY_LOW			= -5;
	
	//Barcode
	public static final int ERROR_FAILED				= -1;
	public static final int ERROR_NOT_SUPPORTED			= -2;
	public static final int ERROR_NO_RESPONSE			= -4;
	public static final int ERROR_BATTERY_LOW			= -5;
	public static final int ERROR_BARCODE_DECODING_TIMEOUT			= -6;
	//@Deprecated
	public static final int ERROR_BARCODE_ERROR_USE_TIMEOUT			= -7;
	//@Deprecated
	public static final int ERROR_BARCODE_ERROR_ALREADY_OPENED		= -8;
	public static final int BBAPI_ERROR_BARCODE_CAMERA_USED			= -9;
	public static final int ERROR_BARCODE_EXCEED_ASCII_CODE			= -10;	
	//- RESULT
	
	//+ DEVICE KEY CODE
	public static final int KEYCODE_SCAN_PRESS = 0;
	public static final int KEYCODE_SCAN_RELEASE = 1;
	public static final int KEYCODE_PTT_PRESS = 2;
	public static final int KEYCODE_PTT_RELEASE = 3;
	public static final int KEYCODE_VOLUME_PRESS = 4;
	public static final int KEYCODE_VOLUME_RELEASE = 5;
	//- DEVICE KEY CODE
		
	
	//+ BARCODE SYMBOLOGY
	
	//+MIN/MAX LENGTH
	/*
	CODE39: default: 3~30, range:0~55
	CODE93: default: 3~30, range:0~55
	CODE128: default: 3~30, range:0~55
	INTERLEAVED2OF5: default: 3~30, range:0~55
	CODABAR: default: 3~30, range:0~55
	CODE11: default: 3~30, range:0~55
	MSI: default: 3~20, range:0~55
	MATRIX2OF5: default: 3~20, range:0~55
	DISCRETE2OF5: default: 3~30, range:0~55
	*/
	//-MIN/MAX LENGTH
	
	//SE47XX
	public static final int SYMBOLOGY_UPC_A 							= 1;
	public static final int SYMBOLOGY_UPC_E 							= 2;
	public static final int SYMBOLOGY_UPC_E1 							= 3;
	public static final int SYMBOLOGY_EAN8 								= 4;
	public static final int SYMBOLOGY_EAN13 							= 5;
	public static final int SYMBOLOGY_BOOKLAND 							= 6;
	public static final int SYMBOLOGY_SUPPLEMENTAL_CODE 				= 7;
	public static final int SYMBOLOGY_CODE39 							= 8;
	public static final int SYMBOLOGY_CODE93 							= 9;
	public static final int SYMBOLOGY_CODE128 							= 10;
	public static final int SYMBOLOGY_INTERLEAVED2OF5 					= 11;
	public static final int SYMBOLOGY_CODABAR 							= 12;
	public static final int SYMBOLOGY_CODE11 							= 13;
	public static final int SYMBOLOGY_MSI 								= 14;
	public static final int SYMBOLOGY_GS1 								= 15;
	public static final int SYMBOLOGY_PDF417 							= 16;
	public static final int SYMBOLOGY_ISBT128 							= 17;
	public static final int SYMBOLOGY_COMPOSITE_CC_C 					= 18;
	public static final int SYMBOLOGY_MATRIX2OF5 						= 19;
	public static final int SYMBOLOGY_DATAMATRIX 						= 20;
	public static final int SYMBOLOGY_MAXICODE 							= 21;
	public static final int SYMBOLOGY_AZTECCODE 						= 22;
	public static final int SYMBOLOGY_MICROPDF 							= 23;
	public static final int SYMBOLOGY_QRCODE 							= 24;
	public static final int SYMBOLOGY_TRIOPTIC_CODE 					= 25;
	public static final int SYMBOLOGY_DISCRETE2OF5 						= 26;
	public static final int SYMBOLOGY_USPS4CB 							= 27;
	public static final int SYMBOLOGY_AUSTRALIA_POST 					= 28;
	public static final int SYMBOLOGY_UK_POST 							= 29;
	public static final int SYMBOLOGY_CHINESE_POST 						= 30;
	public static final int SYMBOLOGY_JAPANESE_POST 					= 31;
	public static final int SYMBOLOGY_NETHERLANDS_POST 					= 32;
	public static final int SYMBOLOGY_KOREAN_POST 						= 33;
	public static final int SYMBOLOGY_US_POSTNET 						= 34;
	public static final int SYMBOLOGY_US_PLANET 						= 35;
	
	public static final int SYMBOLOGY_UPC_A_PREAMBLE 					= 36;
	public static final int SYMBOLOGY_UPC_A_TRANSMIT_CHECK_DIGIT 		= 37;
	public static final int SYMBOLOGY_UPC_E_PREAMBLE 					= 38;
	public static final int SYMBOLOGY_UPC_E_TRANSMIT_CHECK_DIGIT 		= 39;
	public static final int SYMBOLOGY_UPC_E1_PREAMBLE 					= 40;
	public static final int SYMBOLOGY_UPC_E1_TRANSMIT_CHECK_DIGIT 		= 41;
	public static final int SYMBOLOGY_EAN8_EXTEND 						= 42;
	public static final int SYMBOLOGY_EAN_TRANSMIT_ISSN 				= 43;
	public static final int SYMBOLOGY_BOOKLAND_ISBN 					= 44;
	public static final int SYMBOLOGY_SUPPLEMENTAL_REDUNDANCY 			= 45;
	public static final int SYMBOLOGY_SUPPLEMENTAL_AIM_ID 				= 46;
	public static final int SYMBOLOGY_CODE39_LENGTH_MIN 				= 47;
	public static final int SYMBOLOGY_CODE39_LENGTH_MAX 				= 48;
	public static final int SYMBOLOGY_CODE39_CHECK_DIGIT 				= 49;
	public static final int SYMBOLOGY_CODE39_TRANSMIT_CHECK_DIGIT 		= 50;
	public static final int SYMBOLOGY_CODE39_FULL_ASCII 				= 51;
	public static final int SYMBOLOGY_CODE93_LENGTH_MIN 				= 52;
	public static final int SYMBOLOGY_CODE93_LENGTH_MAX 				= 53;
	public static final int SYMBOLOGY_CODE128_LENGTH_MIN 				= 54;
	public static final int SYMBOLOGY_CODE128_LENGTH_MAX 				= 55;
	public static final int SYMBOLOGY_CODE128_EMULATION 				= 56;
	public static final int SYMBOLOGY_INTERLEAVED2OF5_LENGTH_MIN 		= 57;
	public static final int SYMBOLOGY_INTERLEAVED2OF5_LENGTH_MAX 		= 58;
	public static final int SYMBOLOGY_INTERLEAVED2OF5_CHECK_DIGIT 		= 59;
	public static final int SYMBOLOGY_INTERLEAVED2OF5_TRANSMIT_CHECK_DIGIT = 60;
	public static final int SYMBOLOGY_CODABAR_LENGTH_MIN 				= 61;
	public static final int SYMBOLOGY_CODABAR_LENGTH_MAX 				= 62;
	public static final int SYMBOLOGY_CODABAR_CLSI_EDITING 				= 63;
	public static final int SYMBOLOGY_CODABAR_NOTIS_EDITING 			= 64;
	public static final int SYMBOLOGY_CODE11_LENGTH_MIN 				= 65;
	public static final int SYMBOLOGY_CODE11_LENGTH_MAX 				= 66;
	public static final int SYMBOLOGY_CODE11_CHECK_DIGIT 				= 67;
	public static final int SYMBOLOGY_CODE11_TRANSMIT_CHECK_DIGIT 		= 68;
	public static final int SYMBOLOGY_MSI_LENGTH_MIN 					= 69;
	public static final int SYMBOLOGY_MSI_LENGTH_MAX 					= 70;
	public static final int SYMBOLOGY_MSI_CHECK_DIGIT 					= 71;
	public static final int SYMBOLOGY_MSI_TRANSMIT_CHECK_DIGIT 			= 72;
	public static final int SYMBOLOGY_MSI_CHECK_DIGIT_ALGORITHM 		= 73;
	public static final int SYMBOLOGY_GS1_LIMITED 						= 74;
	public static final int SYMBOLOGY_GS1_LIMITED_SECURITY_LEVEL 		= 75;
	public static final int SYMBOLOGY_ISBT128_CONCATENATION 			= 76;
	public static final int SYMBOLOGY_ISBT128_CHECK_TABLE 				= 77;
	public static final int SYMBOLOGY_ISBT128_CONCATENATION_REDUNDANCY 	= 78;
	public static final int SYMBOLOGY_MATRIX2OF5_LENGTH_MIN 			= 79;
	public static final int SYMBOLOGY_MATRIX2OF5_LENGTH_MAX 			= 80;
	public static final int SYMBOLOGY_MATRIX2OF5_SUPPLEMENTAL_REDUNDANCY = 81;
	public static final int SYMBOLOGY_MATRIX2OF5_CHECK_DIGIT 			= 82;
	public static final int SYMBOLOGY_MATRIX2OF5_TRANSMIT_CHECK_DIGIT 	= 83;
	public static final int SYMBOLOGY_COMPOSITE_CC_AB 					= 84;
	public static final int SYMBOLOGY_COMPOSITE_TLC_39 					= 85;
	public static final int SYMBOLOGY_COMPOSITE_UPC 					= 86;
	public static final int SYMBOLOGY_DATAMATRIX_INVERSE 				= 87;
	public static final int SYMBOLOGY_DATAMATRIX_ONLY 					= 88;
	public static final int SYMBOLOGY_DISCRETE2OF5_LENGTH_MIN 			= 89;
	public static final int SYMBOLOGY_DISCRETE2OF5_LENGTH_MAX 			= 90;
	public static final int SYMBOLOGY_US_TRANSMIT_CHECK_DIGIT 			= 91;
	public static final int SYMBOLOGY_QRCODE_INVERSE 					= 92;
	public static final int SYMBOLOGY_SPECIFIC_SECURITY 				= 93;
	public static final int SYMBOLOGY_SPECIFIC_INTERCHARACTER 			= 94;
	public static final int SYMBOLOGY_COUPON_REPORT 					= 95;
	public static final int SYMBOLOGY_CONVERT_UPCE_TO_A 				= 96;
	public static final int SYMBOLOGY_CONVERT_UPCE1_TO_A 				= 97;
	public static final int SYMBOLOGY_CONVERT_CODE39_TO_32 				= 98;
	public static final int SYMBOLOGY_CONVERT_I2OF5_TO_EAN13 			= 99;
	public static final int SYMBOLOGY_CONVERT_GS1_TO_UPCEAN 			= 100;
	public static final int SYMBOLOGY_GS1_DATABAR_EXPANDED 				= 101;
	public static final int SYMBOLOGY_GS1_128_EMULATION_FOR_UCC_COMPOSITE_CODE = 102;
	public static final int SYMBOLOGY_INVERSE_1D 						= 103;
	public static final int SYMBOLOGY_UPU_FICS_POSTAL 					= 104;
	public static final int SYMBOLOGY_UPC_COMPOSITE_MODE 				= 105;
	public static final int SYMBOLOGY_AZTEC_INVERSE 					= 106;
	public static final int SYMBOLOGY_SPECIFIC_REDUNDANCY_LEVEL 		= 107;
	public static final int SYMBOLOGY_AUSTRALIA_POST_FORMAT 			= 108;
	public static final int SYMBOLOGY_TRANSMIT_UK_POST_CHECK_DIGIT 		= 109;
	public static final int SYMBOLOGY_CODE32_PREFIX 					= 110;
	public static final int SYMBOLOGY_USER_SUPPLEMENTAL_1 				= 111;
	public static final int SYMBOLOGY_USER_SUPPLEMENTAL_2 				= 112;
	public static final int SYMBOLOGY_MICROQR							= 113;
	
	//for N5600
	public static final int SYMBOLOGY_CODE49							= 114;
	public static final int SYMBOLOGY_OCR								= 115;
	public static final int SYMBOLOGY_CANADIAN_POST						= 116;
	public static final int SYMBOLOGY_IATA25							= 117;
	public static final int SYMBOLOGY_TLCODE39							= 118;
	public static final int SYMBOLOGY_CODE32							= 119;
	public static final int SYMBOLOGY_STRAIGHT2OF5						= 120;
	public static final int SYMBOLOGY_PLESSEY							= 121;
	public static final int SYMBOLOGY_TELEPEN							= 122;
	public static final int SYMBOLOGY_CODE16K							= 123;
	public static final int SYMBOLOGY_POSI_CODE							= 124;
	public static final int SYMBOLOGY_IDTAG								= 125;
	public static final int SYMBOLOGY_LABEL4							= 126;
	public static final int SYMBOLOGY_LABEL5							= 127;
	public static final int SYMBOLOGY_GS1_128							= 128;
	public static final int SYMBOLOGY_NUMBER							= 129;
	public static final int SYMBOLOGY_ALL								= 130;
	public static final int SYMBOLOGY_CODABLOCK							= 131;
	public static final int SYMBOLOGY_RSS14								= 132;
	public static final int SYMBOLOGY_CHINESE_2OF5 						= 133;
	
	//SE47XX
	public static final int SYMBOLOGY_HANXIN 							= 134;
	public static final int SYMBOLOGY_HANXIN_INVERSE 					= 135;
	public static final int SYMBOLOGY_IATA			 					= 136;
	public static final int SYMBOLOGY_EAN128		 					= 137;
	public static final int SYMBOLOGY_UPC_D			 					= 138;
	public static final int SYMBOLOGY_GS1_DATABAR			 			= 139;
	public static final int SYMBOLOGY_SCANLET			 				= 140;
	public static final int SYMBOLOGY_CUECODE			 				= 141;
	public static final int SYMBOLOGY_SIGNATURE_CAPTURE			 		= 142;
	//- BARCODE SYMBOLOGY
	
	//+MODE
	public static final int BARCODE_MODE_PREFIX					= 500;
	public static final int BARCODE_MODE_SUFFIX 				= 501;	
	public static final int BARCODE_MODE_SOUND 					= 502;
	public static final int BARCODE_MODE_TRIGGER 				= 503;
	public static final int BARCODE_MODE_AIMER 					= 504;
	public static final int BARCODE_MODE_ILLUMINATION 			= 505;
	public static final int BARCODE_MODE_DECODE_TIMEOUT 		= 506;
	public static final int BARCODE_MODE_SAME_SYMBOL_TIMEOUT 	= 507;
	public static final int BARCODE_MODE_PREAMBLE 				= 543;
	public static final int BARCODE_MODE_POSTAMBLE 				= 544;	
	public static final int BARCODE_MODE_DATA_WEDGE_TYPE 		= 547;
	public static final int BARCODE_MODE_PICKLIST				= 548;
	//-MODE

}
