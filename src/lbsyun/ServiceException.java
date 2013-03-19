package lbsyun;

/**
 * LBS云SDK的异常返回
 * 
 * @author kuangzhijie
 *
 */
public class ServiceException extends Exception {
	private static final long serialVersionUID = -1L;

	private int errorCode = -1;

	public ServiceException() {
	}

	public ServiceException(String errorMsg) {
		super(errorMsg);
	}

	public ServiceException(Exception cause) {
		super(cause);
	}

	public ServiceException(String errorMsg, Exception cause) {
		super(errorMsg, cause);
	}

	public ServiceException(String errorMsg, int errorCode) {
		super(errorMsg);
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}

}
