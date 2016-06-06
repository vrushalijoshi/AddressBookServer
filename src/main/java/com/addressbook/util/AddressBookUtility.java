package com.addressbook.util;

import java.util.Set;

/**
 * Utility class provides helper methods used to build the response
 * 
 * @author Vrushali
 *
 */
public class AddressBookUtility {
	public static <T> String getErrorData(Set<T> ids) {
		StringBuffer buffer = new StringBuffer();
		for (T id : ids) {
			buffer.append(id.toString());
			buffer.append(",");
		}
		return buffer.substring(0, buffer.length() - 1).toString();
	}
}
