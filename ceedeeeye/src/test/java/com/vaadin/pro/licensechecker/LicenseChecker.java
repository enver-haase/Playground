/**
 * Copyright (C) 2012 Vaadin Ltd
 *
 * This program is available under Commercial Vaadin Add-On License 3.0
 * (CVALv3).
 *
 * See the file licensing.txt distributed with this software for more
 * information about licensing.
 *
 * You should have received a copy of the license along with this program.
 * If not, see <http://vaadin.com/license/cval-3>.
 */
package com.vaadin.pro.licensechecker;

import java.util.logging.Logger;

public class LicenseChecker {

	public interface Callback {

		public void ok();

		public void failed(Exception e);
	}

	public static void checkLicenseFromStaticBlock(String productName,
												   String productVersion) {
		try {
			//checkLicense(productName, productVersion);
		} catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	public static void checkLicense(String productName, String productVersion) {
		checkLicense(new Product(productName, productVersion));
	}

	public static void checkLicenseAsync(String productName,
										 String productVersion, Callback callback) {
		new Thread(() -> {
			try {
				checkLicense(new Product(productName, productVersion));
				callback.ok();
			} catch (Exception e) {
				callback.failed(e);
			}
		}).start();
	}

	private static void checkLicense(Product product) {
		getLogger().fine("Checking license for " + product);
		ProKey proKey = LocalProKey.get();
		if (proKey == null) {
			// No proKey found - probably first launch
			proKey = VaadinComIntegration.openBrowserAndWaitForKey();
			if (proKey != null) {
				LocalProKey.write(proKey);
			}
		}

		ProKeyValidator.validate(product, proKey);
	}

	public static Logger getLogger() {
		return Logger.getLogger(LicenseChecker.class.getName());
	}

}
