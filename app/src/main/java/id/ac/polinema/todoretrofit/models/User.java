package id.ac.polinema.todoretrofit.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User implements Parcelable {

	@SerializedName("id")
	@Expose
	private Integer id;
	@SerializedName("username")
	@Expose
	private String username;
	@SerializedName("name")
	@Expose
	private String name;
	@SerializedName("password")
	@Expose
	private String password;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeValue(this.id);
		dest.writeString(this.username);
		dest.writeString(this.name);
		dest.writeString(this.password);
	}

	public User() {
	}

	protected User(Parcel in) {
		this.id = (Integer) in.readValue(Integer.class.getClassLoader());
		this.username = in.readString();
		this.name = in.readString();
		this.password = in.readString();
	}

	public static final Creator<User> CREATOR = new Creator<User>() {
		@Override
		public User createFromParcel(Parcel source) {
			return new User(source);
		}

		@Override
		public User[] newArray(int size) {
			return new User[size];
		}
	};
}
