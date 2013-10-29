package net.maivic.comm;
/**
 *  A {@link LazyResponse} interface hich additionally has setters 
 * @author paul
 *
 */
public interface SettableLazyResponse <T> extends LazyResponse<T> {
	void setSuccess(T successValue);
	void setFailure(Throwable failure);
	void setProgress(int progress);
	void setMaxProgress(int maxProgress);
}
