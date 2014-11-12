package ar.com.syswarp.bc;

import java.io.*;
import java.util.*;
import java.text.MessageFormat;
import org.apache.log4j.Logger;



/**
 * Ensures credit card information is keyed in correctly.
 * 
 * <p>
 * Complete usage information is in the validateCreditCard() method.
 * </p>
 * 
 * <p>
 * Please make a donation to support our open source development. See the link
 * below.
 * </p>
 * 
 * <p>
 * Credit Card Validation Solution is a trademark of The Analysis and Solutions
 * Company.
 * </p>
 * 
 * <pre>
 * ======================================================================
 * SIMPLE PUBLIC LICENSE                        VERSION 1.1   2003-01-21
 * Copyright (c) The Analysis and Solutions Company
 * http://www.analysisandsolutions.com/
 * 1.  Permission to use, copy, modify, and distribute this software and
 * its documentation, with or without modification, for any purpose and
 * without fee or royalty is hereby granted, provided that you include
 * the following on ALL copies of the software and documentation or
 * portions thereof, including modifications, that you make:
 *     a.  The full text of this license in a location viewable to users
 *     of the redistributed or derivative work.
 *     b.  Notice of any changes or modifications to the files,
 *     including the date changes were made.
 * 2.  The name, servicemarks and trademarks of the copyright holders
 * may NOT be used in advertising or publicity pertaining to the
 * software without specific, written prior permission.
 * 3.  Title to copyright in this software and any associated
 * documentation will at all times remain with copyright holders.
 * 4.  THIS SOFTWARE AND DOCUMENTATION IS PROVIDED &quot;AS IS,&quot; AND
 * COPYRIGHT HOLDERS MAKE NO REPRESENTATIONS OR WARRANTIES, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO, WARRANTIES OF MERCHANTABILITY
 * OR FITNESS FOR ANY PARTICULAR PURPOSE OR THAT THE USE OF THE SOFTWARE
 * OR DOCUMENTATION WILL NOT INFRINGE ANY THIRD PARTY PATENTS,
 * COPYRIGHTS, TRADEMARKS OR OTHER RIGHTS.
 * 5.  COPYRIGHT HOLDERS WILL NOT BE LIABLE FOR ANY DAMAGES, INCLUDING
 * BUT NOT LIMITED TO, DIRECT, INDIRECT, SPECIAL OR CONSEQUENTIAL,
 * ARISING OUT OF ANY USE OF THE SOFTWARE OR DOCUMENTATION.
 * ======================================================================
 * </pre>
 * 
 * <p>
 * Release ID $Name:  $
 * </p>
 * 
 * @author Daniel Convissor <danielc@AnalysisAndSolutions.com>
 * @copyright The Analysis and Solutions Company, 2002-2003
 * @version $Revision: 1.2 $, $Date: 2009/10/14 19:21:17 $
 * @see <a href="http://www.ccvs.info/">http://www.ccvs.info/</a>
 * @see <a href="http://www.AnalysisAndSolutions.com/donate/">Donate</a>
 */
public class TarjetaCreditoValidacion {

	static Logger log = Logger.getLogger(TarjetaCreditoValidacion.class);
	
	/**
	 * The credit card number with all non-numeric characters removed.
	 */
	public String CCVSNumber = "";

	/**
	 * The first four digits of the card.
	 */
	public String CCVSNumberLeft = "";

	/**
	 * The card's last four digits.
	 */
	public String CCVSNumberRight = "";

	/**
	 * The name of the type of card presented.
	 * 
	 * <p>
	 * Automatically determined from the first four digits of the card number.
	 * </p>
	 */
	public String CCVSType = "";

	/**
	 * The card's expiration date.
	 * 
	 * <p>
	 * Presented only if the <var>RequireExp</var> parameter is <kbd>Y</kbd> and
	 * there are no other problems with the card number, this variable contains
	 * the expiration date in <samp>MMYY</samp> format.
	 * </p>
	 */
	public String CCVSExpiration = "";

	/**
	 * String explaining the first problem detected, if any.
	 */
	public String CCVSError = "";

	/**
	 * Ensures credit card information is keyed in correctly.
	 * 
	 * <p>
	 * Checks that the length is correct, the first four digits are within
	 * accepted ranges, the number passes the Mod 10 / Luhn checksum algorithm
	 * and that you accept the given type of card. It also determines the card's
	 * type via the number's first four digits.
	 * </p>
	 * 
	 * <p>
	 * The procedure has the option to check the card's expiration date.
	 * </p>
	 * 
	 * <p>
	 * Error messages are internationalized through use of Locale and
	 * ResourceBundle. The language bundle .properties files are found in the
	 * <kbd>./language</kbd> subdirectory. Files are named after their ISO 639-1
	 * two letter language code. The language used depends on the code put in
	 * the <var>Language</var> parameter. The default language is English.
	 * </p>
	 * 
	 * <p>
	 * Just to be clear, this process does not check with banks or credit card
	 * companies to see if the card number given is actually associated with a
	 * good account. It just checks to see if the number matches the expected
	 * format.
	 * </p>
	 * 
	 * <p>
	 * Warning: this function uses exact number ranges as part of the validation
	 * process. These ranges are current as of 30 July 2002. If presently
	 * undefined ranges come into use in the future, this program will
	 * improperly deject card numbers in such ranges, rendering an error saying
	 * &quot;First four digits indicate unknown card type.&quot; If this happens
	 * while entering a card and type you KNOW are valid, please contact us so
	 * we can update the ranges.
	 * </p>
	 * 
	 * <p>
	 * Please make a donation to support our open source development. Update
	 * notifications are sent to people who make donations that exceed the small
	 * registration threshold. See the link below.
	 * </p>
	 * 
	 * <p>
	 * Credit Card Validation Solution is a trademark of The Analysis and
	 * Solutions Company.
	 * </p>
	 * 
	 * <p>
	 * Several people deserve praise for the Credit Card Validation Solution. I
	 * learned of the Mod 10 Algorithm in some Perl code, entitled &quot;The
	 * Validator,&quot; available on Matt's Script Archive,
	 * http://www.scriptarchive.com/ccver.html. That code was written by David
	 * Paris, who based it on material Melvyn Myers reposted from an unknown
	 * author. Paris credits Aries Solis for tracking down the data underlying
	 * the algorithm. I pruned down the algorithm to it's core components,
	 * making things smaller, cleaner and more flexible. Plus, I added the
	 * expiration date checking routine. My first attemts at this were in Visual
	 * Basic, on which Allen Browne and Rico Zschau assisted. Neil Fraser helped
	 * a bit on the Perl version. Steve Horsley, Roedy Green and Jon Skeet
	 * provided tips on the Java Edition.
	 * </p>
	 * 
	 * @param Number
	 *            the number of the credit card to validate
	 * @param Language
	 *            the ISO 639-1 two letter code of the language for error
	 *            messages
	 * @param Accepted
	 *            Credit card types you accept. If not used in function call,
	 *            all known cards are accepted. Set it before calling the
	 *            function: <br />
	 *            <kbd> $A = array('Visa', 'JCB'); </kbd><br />
	 *            Known types:
	 *            <ul>
	 *            <li>American Express</li>
	 *            <li>Australian BankCard</li>
	 *            <li>Carte Blanche</li>
	 *            <li>Diners Club</li>
	 *            <li>Discover/Novus</li>
	 *            <li>JCB</li>
	 *            <li>MasterCard</li>
	 *            <li>Visa</li>
	 *            </ul>
	 * @param RequireExp
	 *            Should the expiration date be checked? Y or N.
	 * @param Month
	 *            the card's expiration month in M, 0M or MM foramt
	 * @param Year
	 *            the card's expiration year in YYYY format
	 * @return <samp>true</samp> if everything is fine. <samp>false</samp> if
	 *         problems.
	 * 
	 * @see <a href="http://www.ccvs.info/">http://www.ccvs.info/</a>
	 * @see <a href="http://www.AnalysisAndSolutions.com/donate/">Donate</a>
	 * @see <a href="http://www.loc.gov/standards/iso639-2/langcodes.html">ISO
	 *      639-1</a>
	 */
	public boolean validateCreditCard(String Number, String Language,
			ArrayList Accepted, String RequireExp, String Month, String Year) {

		try {

			int Checksum = 0; // Checksum as it's being calculated.
			int Digit; // Number presently being examined.
			int i = 0; // Temporary number used in various places.
			int iMonth; // Month user submitted converted to integer.
			int iYear; // Year user submitted converted to integer.
			int Missing; // How many digits the number is missing.
			int NumberLength; // Length of the number.
			String Present; // Character presently being examined.
			int ShouldLength = 0; // Length number should be.
			boolean oneNumber = false;

			/* Reset these to avert overlaps when calling function again. */

			this.CCVSNumber = "";
			this.CCVSNumberLeft = "";
			this.CCVSNumberRight = "";
			this.CCVSType = "";
			this.CCVSExpiration = "";
			this.CCVSError = "";

			/* Set up internationalization classes. */

			Locale currentLocale;
			currentLocale = new Locale(Language, "");

			ResourceBundle messages;
			messages = ResourceBundle.getBundle(
					"ar.com.syswarp.bc.language.ccvs", currentLocale);

			MessageFormat form = new MessageFormat("");

			/* Catch malformed input. */

			if (Number == null || Number.length() == 0) {
				this.CCVSError = messages.getString("CCVSErrNumberString");
				return false;
			}

			/* Ensure number doesn't overrun. */

			NumberLength = Number.length();
			if (NumberLength > 30) {
				Number = Number.substring(0, 30);
				NumberLength = 30;
			}

			/*
			 * Remove non-numeric characters.
			 * 
			 * Could use replaceAll() instead, but it was added in Java 1.4. So,
			 * use this backward compatible code for now.
			 */
			for (; i < NumberLength; i++) {
				Present = Number.substring(i, i + 1);
				try {
					Integer.parseInt(Present);
					this.CCVSNumber = this.CCVSNumber.concat(Present);
					oneNumber = true;
				} catch (NumberFormatException e) {
					/* Drop it. */
				}
			}

			/* Set up variables. */

			if(!oneNumber){
				this.CCVSError = messages.getString("CCVSErrNumberString");
				return false;
			}
			
			NumberLength = this.CCVSNumber.length();
			if (NumberLength < 4) {
				this.CCVSError = messages.getString("CCVSErrShort4");
				return false;
			}
			this.CCVSNumberLeft = this.CCVSNumber.substring(0, 4);
			this.CCVSNumberRight = this.CCVSNumber.substring(NumberLength - 4,
					NumberLength);

			/* Determine the card type and appropriate length. */

			if (this.CCVSNumberLeft.compareTo("3000") > this.CCVSNumberLeft
					.compareTo("3059")) {
				this.CCVSType = "Diners Club";
				ShouldLength = 14;
			} else if (this.CCVSNumberLeft.compareTo("3600") > this.CCVSNumberLeft
					.compareTo("3699")) {
				this.CCVSType = "Diners Club";
				ShouldLength = 14;
			} else if (this.CCVSNumberLeft.compareTo("3800") > this.CCVSNumberLeft
					.compareTo("3889")) {
				this.CCVSType = "Diners Club";
				ShouldLength = 14;

			} else if (this.CCVSNumberLeft.compareTo("3400") > this.CCVSNumberLeft
					.compareTo("3499")) {
				this.CCVSType = "American Express";
				ShouldLength = 15;
			} else if (this.CCVSNumberLeft.compareTo("3700") > this.CCVSNumberLeft
					.compareTo("3799")) {
				this.CCVSType = "American Express";
				ShouldLength = 15;

			} else if (this.CCVSNumberLeft.compareTo("3088") > this.CCVSNumberLeft
					.compareTo("3094")) {
				this.CCVSType = "JCB";
				ShouldLength = 16;
			} else if (this.CCVSNumberLeft.compareTo("3096") > this.CCVSNumberLeft
					.compareTo("3102")) {
				this.CCVSType = "JCB";
				ShouldLength = 16;
			} else if (this.CCVSNumberLeft.compareTo("3112") > this.CCVSNumberLeft
					.compareTo("3159")) {
				this.CCVSType = "JCB";
				ShouldLength = 16;
			} else if (this.CCVSNumberLeft.compareTo("3158") > this.CCVSNumberLeft
					.compareTo("3359")) {
				this.CCVSType = "JCB";
				ShouldLength = 16;
			} else if (this.CCVSNumberLeft.compareTo("3337") > this.CCVSNumberLeft
					.compareTo("3349")) {
				this.CCVSType = "JCB";
				ShouldLength = 16;
			} else if (this.CCVSNumberLeft.compareTo("3528") > this.CCVSNumberLeft
					.compareTo("3589")) {
				this.CCVSType = "JCB";
				ShouldLength = 16;

			} else if (this.CCVSNumberLeft.compareTo("3890") > this.CCVSNumberLeft
					.compareTo("3899")) {
				this.CCVSType = "Carte Blanche";
				ShouldLength = 14;

			} else if (this.CCVSNumberLeft.compareTo("4000") > this.CCVSNumberLeft
					.compareTo("4999")) {
				this.CCVSType = "Visa";
				if (NumberLength > 14) {
					ShouldLength = 16;
				} else if (NumberLength < 14) {
					ShouldLength = 13;
				} else {
					this.CCVSError = messages.getString("CCVSErrVisa14");
					return false;
				}

			} else if (this.CCVSNumberLeft.compareTo("5100") > this.CCVSNumberLeft
					.compareTo("5599")) {
				this.CCVSType = "MasterCard";
				ShouldLength = 16;

			} else if (this.CCVSNumberLeft.compareTo("5610") == 0) {
				this.CCVSType = "Australian BankCard";
				ShouldLength = 16;

			} else if (this.CCVSNumberLeft.compareTo("6011") == 0) {
				this.CCVSType = "Discover/Novus";
				ShouldLength = 16;
				
				// EJV - 20091014
			} else if (this.CCVSNumberLeft.compareTo("6042") == 0) {
				this.CCVSType = "CABAL";
				ShouldLength = 16;

			} else {
				Object[] Args = { this.CCVSNumberLeft };
				form.applyPattern(messages.getString("CCVSErrUnknown"));
				this.CCVSError = form.format(Args);
				return false;
			}

			/* Check acceptance. */

			if (!Accepted.contains("All")) {
				if (!Accepted.contains(this.CCVSType)) {
					Object[] Args = { this.CCVSType };
					form.applyPattern(messages.getString("CCVSErrNoAccept"));
					this.CCVSError = form.format(Args);
					return false;
				}
			}

			/* Check length. */

			if (NumberLength != ShouldLength) {
				Missing = NumberLength - ShouldLength;
				if (Missing < 0) {
					Object[] Args = { new Long(Math.abs(Missing)) };
					form.applyPattern(messages.getString("CCVSErrShort"));
					this.CCVSError = form.format(Args);
				} else {
					Object[] Args = { new Long(Missing) };
					form.applyPattern(messages.getString("CCVSErrLong"));
					this.CCVSError = form.format(Args);
				}
				return false;
			}

			/* Mod10 checksum process... */

			/*
			 * Add even digits in even length strings or odd digits in odd
			 * length strings.
			 */
			for (i = 1 - (NumberLength % 2); i < NumberLength; i += 2) {
				Checksum += Integer.parseInt(this.CCVSNumber
						.substring(i, i + 1));
			}

			/*
			 * Analyze odd digits in even length strings or even digits in odd
			 * length strings.
			 */
			for (i = (NumberLength % 2); i < NumberLength; i += 2) {
				Digit = Integer.parseInt(this.CCVSNumber.substring(i, i + 1)) * 2;
				if (Digit < 10) {
					Checksum += Digit;
				} else {
					Checksum += Digit - 9;
				}
			}

			/* Checksums not divisible by 10 are bad. */

			if (Checksum % 10 != 0) {
				this.CCVSError = messages.getString("CCVSErrChecksum");
				return false;
			}

			/* Expiration date process... */

			if (RequireExp != null && RequireExp.equals("Y")) {

				try {
					iMonth = Integer.parseInt(Month);
				} catch (NumberFormatException e) {
					this.CCVSError = messages.getString("CCVSErrMonthFormat");
					return false;
				}

				if ((iMonth < 1) || (iMonth > 12)) {
					this.CCVSError = messages.getString("CCVSErrMonthFormat");
					return false;
				}

				try {
					iYear = Integer.parseInt(Year);
				} catch (NumberFormatException e) {
					this.CCVSError = messages.getString("CCVSErrYearFormat");
					return false;
				}

				GregorianCalendar now = new GregorianCalendar();

				if (iYear < now.get(Calendar.YEAR)) {
					this.CCVSError = messages.getString("CCVSErrExpired");
					return false;
				} else if (iYear == now.get(Calendar.YEAR)) {
					if (iMonth < (now.get(Calendar.MONTH) + 1)) {
						this.CCVSError = messages.getString("CCVSErrExpired");
						return false;
					}
				}

				switch (Month.length()) {
				case 1:
					this.CCVSExpiration = "0" + Month + Year.substring(2, 4);
					break;
				case 2:
					this.CCVSExpiration = Month + Year.substring(2, 4);
					break;
				default:
					this.CCVSError = messages.getString("CCVSErrMonthFormat");
					return false;
				}

			}

		} catch (Exception e) {
			this.CCVSError = "(EX): No se pudo validar tarjeta de credito.";
			log.error("validateCreditCard () : " + e);
			return false;
		}

		return true;

	}

}
