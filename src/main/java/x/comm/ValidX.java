package x.comm;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

public class ValidX {

	public ValidX() {
		super();
		// TODO Auto-generated constructor stub
	}

	/*
	 * true: success false: error
	 */
	public boolean isPhone(String str, boolean isZero) {
		if (StringUtils.isBlank(str))
			return false;
		if (!NumberUtils.isDigits(str))
			return false;
		if (!(StringUtils.length(str) >= 9 && StringUtils.length(str) <= 10)) {
			return false;
		} else {
			if (isZero) {
				if (!StringUtils.startsWith(str, "0")) {
					return false;
				}
			}
		}

		return true;
	}

	public boolean isPhone(String str) {
		return isPhone(str, false);
	}

	/*
	 * true: success false: error
	 */
	public boolean isAmt(double amt, double max) {
		String strAmt = String.valueOf(amt);
		if (StringUtils.isBlank(strAmt))
			return false;

		if (!NumberUtils.isNumber(strAmt))
			return false;

		if ((NumberUtils.toDouble(strAmt) <= 0))
			return false;

		if (max > 0) {
			if (amt > max)
				return false;
		}

		return true;
	}

	/*
	 * true: success false: error
	 */
	public boolean isAmt(double amt) {
		return isAmt(amt, 0);
	}

	/*
	 * true: success false: error
	 */
	public boolean isCrrcy(String str) {
		if (StringUtils.isBlank(str))
			return false;
		if (NumberUtils.isDigits(str))
			return false;
		if (StringUtils.length(str) > 3)
			return false;
		if (!(StringUtils.equalsIgnoreCase("USD", str) || StringUtils
				.equalsIgnoreCase("KHR", str)))
			return false;

		return true;
	}

	/*
	 * true: success false: error
	 */
	public boolean isAcctNo(String str) {
		if (StringUtils.isBlank(str))
			return false;

		if (!NumberUtils.isNumber(str))
			return false;

		if (StringUtils.length(str) != 9)
			return false;

		return true;
	}

	/*
	 * true: success false: error
	 */
	public boolean isChnlCd(String str) {
		String[] arr = { "01", "02", "03", "04" };
		if (StringUtils.isBlank(str))
			return false;
		if (NumberUtils.isDigits(str))
			return false;
		if (StringUtils.length(str) != 2)
			return false;
		for (String i : arr) {
			if (!StringUtils.equals(i, str))
				return false;
		}
		return true;
	}

}
