package com.nowackdynamics.serv.framework.response;

public class ErrorCodes {

    public static int ERR_SUCCESS                   = 100; // No error

    public static int SHORT_PIN                     = 0; // Pin is too short
    public static int LONG_PIN                      = 1; // Pin is too long
    public static int PIN_FORMAT                    = 2; // Pin contains illegal characters
    public static int PIN_INVALID                   = 3; // Provided pin is invalid during check

    public static int EMAIL_FORMAT                  = 4; // Email is not in a proper format

    public static int RECEIPT_FORMAT_DRP            = 5; // Receipt is not in DRF
    public static int RECEIPT_FORMAT_B64            = 6; // Receipt is not Base64 encoded (or invalid)
    public static int RECEIPT_CLAIMED               = 7; // Receipt is already claimed
    public static int RECEIPT_INVALID               = 8; // Receipt is invalid
    public static int RECEIPT_EXISTS                = 9; // Receipt already exists

    public static int SECURITY_FAILURE              = 10; // NDS could not verify the request to the RPD only endpoint

    public static int ACCOUNT_EXISTS                = 11; // Account is already registered
    public static int ACCOUNT_INVALID               = 12; // Account is invalid or does not exist
    public static int EMAIL_USED                    = 13; // Email is already in use
    public static int EMAIL_SAME                    = 14; // New email is same as old
    public static int EMAIL_VERIFIED                = 15; // Email is already verified
    public static int EMAIL_VERIFICATION_EXPIRED    = 16; // Email verification code has expired
    public static int EMAIL_VERIFICATION_INCORRECT  = 17; // Provided verification code is invalid
    public static int EMAIL_NOT_VERIFIED            = 18; // User's email is not verified
    public static int PIN_VERIFICATION_EXPIRED      = 19; // The PIN verification token has expired

    public static int UNKNOWN_GENERIC               = 99; // Generic or unknown error, cache receipt data locally(if relevant)
}
