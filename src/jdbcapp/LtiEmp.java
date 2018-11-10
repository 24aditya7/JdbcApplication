package jdbcapp;
public class LtiEmp {
	private int rno;
	private String uname;
	private String pass;
	private double amt;
	
	public int getRno() {
		return rno;
	}
	public void setRno(int rno) {
		this.rno = rno;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public double getAmt() {
		return amt;
	}
	public void setAmt(double amt) {
		this.amt = amt;
	}
	
	@Override
	public String toString() {
		return "LtiEmp [rno=" + rno + ", uname=" + uname + ", pass=" + pass + ", amt=" + amt + "]";
	}
}
