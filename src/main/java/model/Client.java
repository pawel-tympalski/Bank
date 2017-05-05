package model;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import service.Report;

public class Client implements Report, Comparable<Client>, Serializable{
	
	private static final long serialVersionUID = 4594037839301903790L;
	private String name;
	private Set<AbstractAccount> accounts;
	private AbstractAccount activeAccount;
	private Gender gender;
	private float overdraft;
	private String phone;
	private String email;
	private String city;
	private int id;
	
	public Client(String name,Gender gender, float overdraft, String phone, String email,String city, int id) {
		
		this.name = name;
		this.accounts = new HashSet<AbstractAccount>();
		this.gender = gender;
		this.phone = phone;
		this.overdraft = overdraft;
		this.email = email;
		this.city = city;
		this.id = id;
	}
	public Client(){
		
	}

	public float getOverdraft() {
		return overdraft;
	}



	public void setOverdraft(float overdraft) {
		this.overdraft = overdraft;
	}



	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getCity() {
		return city;
	}



	public void setCity(String city) {
		this.city = city;
	}



	@Override
	public String toString(){
		
		StringBuilder sb = new StringBuilder();
		sb.append("Name of the client " + name);
		sb.append("\nNumber of accounts " + accounts.size());
		sb.append("\nActive account " + activeAccount.getClass().getName());
		sb.append("\nPhone number " + phone);
		sb.append("\nEmail " + email);
		sb.append("\nOverdraft " + overdraft );
		sb.append("\nGender " + gender);
		
		return sb.toString(); 
	}
	
	

	@Override
	public int hashCode() {
		return id;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Client other = (Client) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}



	public void setActiveAccount(AbstractAccount activeAccount) {
		this.activeAccount = activeAccount;
	}
	
	public AbstractAccount getActiveAccount(){
		
		return activeAccount;
	}

	public Set<AbstractAccount> getAccounts() {
		return Collections.unmodifiableSet(accounts);
	}
	
	public float getBalance(){
		
		return activeAccount.getBalance();
		
	}

	public void addAccount(AbstractAccount account){
		
		accounts.add(account);
	}
	
	public String getClientSalutation(){
		
		switch(gender){
			case FEMALE:
				return gender.getTitle();
			case MALE:
		}	
			
		return gender.getTitle();
	}
	
	
	public String getName(){
		
		return name;
	}
	
	@Override
	public void printReport() {
		
		 System.out.println("Client Name: " + getClientSalutation()+ name + ", Balance of active account: " + getBalance() +
				 ", Type of active account-" + activeAccount.getClass().getName() + ", Number of accounts: " + accounts.size()); ;
	}



	@Override
	public int compareTo(Client arg) {
		return this.getName().compareTo(arg.getName());
	}
	
	
	public int getId() {
		return id;
	}
 
}
