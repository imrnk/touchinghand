package org.touchinghand.client.dto;

import java.time.LocalDateTime;

public class ClientDTO {

	private final int clientId;
	private final String name;
	private final String gender;
	private final int age;
	private final String maritalStat;
	private final String profession;
	private final String education;
	private final String address;
	private final String city;
	private final String state;
	private final int pinCode;
	private final String country;
	private final String phone;
	private final String phone2;
	private final String email;
	private final String referenceCounsellor;
	private final boolean active;
	private final LocalDateTime followUpDate;

	private ClientDTO(ClientBuilder builder) {
		this.clientId = builder.clientId;
		this.name = builder.name;
		this.phone = builder.phone;
		this.gender = builder.gender;
		this.age = builder.age;
		this.maritalStat = builder.maritalStat;
		this.profession = builder.profession;
		this.education = builder.education;
		this.address = builder.address;
		this.city = builder.city;
		this.state = builder.state;
		this.pinCode = builder.pinCode;
		this.country = builder.country;
		this.phone2 = builder.phone2;
		this.email = builder.email;
		this.referenceCounsellor = builder.referenceCounsellor;
		this.active = builder.active;
		this.followUpDate = builder.followUpDate;
	}

	public static class ClientBuilder {
		// Required parameters
		private final int clientId;
		private final String name;
		private final String phone;

		// Optional Parameters
		private String gender = "";
		private int age = 0;
		private String maritalStat = "";
		private String profession = "";
		private String education = "";
		private String address = "";
		private String city = "";
		private String state = "";
		private int pinCode = 0;
		private String country = "";
		private String phone2 = "";
		private String email = "";
		private String referenceCounsellor = "";
		private boolean active;
		private LocalDateTime followUpDate = null;

		public ClientBuilder(int clientId, String name, String phone) {
			this.clientId = clientId;
			this.name = name;
			this.phone = phone;
		}

		public ClientBuilder age(int age) {
			this.age = age;
			return this;
		}

		public ClientBuilder education(String education) {
			this.education = education;
			return this;
		}

		public ClientBuilder profession(String profession) {
			this.profession = profession;
			return this;
		}

		public ClientBuilder gender(String gender) {
			this.gender = gender;
			return this;
		}

		public ClientBuilder maritalStat(String maritalStat) {
			this.maritalStat = maritalStat;
			return this;
		}

		public ClientBuilder address(String address) {
			this.address = address;
			return this;
		}

		public ClientBuilder city(String city) {
			this.city = city;
			return this;
		}

		public ClientBuilder state(String state) {
			this.state = state;
			return this;
		}

		public ClientBuilder pinCode(int pinCode) {
			this.pinCode = pinCode;
			return this;
		}

		public ClientBuilder country(String country) {
			this.country = country;
			return this;
		}

		public ClientBuilder phone2(String phone2) {
			this.phone2 = phone2;
			return this;
		}

		public ClientBuilder email(String email) {
			this.email = email;
			return this;
		}

		public ClientBuilder referenceCounsellor(String referenceCounsellor) {
			this.referenceCounsellor = referenceCounsellor;
			return this;
		}

		public ClientBuilder active(boolean active) {
			this.active = active;
			return this;
		}

		public ClientBuilder followUpDate(LocalDateTime followUp) {
			this.followUpDate = followUp;
			return this;
		}

		public ClientDTO build() {
			return new ClientDTO(this);
		}

	}

	/**
	 * @return the clientId
	 */
	public int getClientId() {
		return clientId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @return the age
	 */
	public int getAge() {
		return age;
	}

	/**
	 * @return the maritalStat
	 */
	public String getMaritalStat() {
		return maritalStat;
	}

	/**
	 * @return the profession
	 */
	public String getProfession() {
		return profession;
	}

	/**
	 * @return the education
	 */
	public String getEducation() {
		return education;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @return the pinCode
	 */
	public int getPinCode() {
		return pinCode;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @return the phone2
	 */
	public String getPhone2() {
		return phone2;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @return the referenceCounsellor
	 */
	public String getReferenceCounsellor() {
		return referenceCounsellor;
	}

	/**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @return the followUpDate
	 */
	public LocalDateTime getFollowUpDate() {
		return followUpDate;
	}
}
