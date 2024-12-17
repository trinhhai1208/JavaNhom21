package backend.models;

public class Librarian {
	private String maTT;
	private String accountName;
	private String password;
	public Librarian() {
		
	}
	public Librarian(String maTT,String accountName, String password) {
		super();
		this.maTT=maTT;
		this.accountName = accountName;
		this.password = password;
	}
	
	
	
	public String getMaTT() {
		return maTT;
	}
	public void setMaTT(String maTT) {
		this.maTT = maTT;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	@Override
    public String toString() {
        return String.format("%-5s | %-15s | %-15s", maTT, accountName, password);
    }
	
}
