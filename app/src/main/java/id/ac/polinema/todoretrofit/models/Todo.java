package id.ac.polinema.todoretrofit.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Todo implements Parcelable {

	@SerializedName("id")
	@Expose
	private Integer id;
	@SerializedName("todo")
	@Expose
	private String todo;
	@SerializedName("done")
	@Expose
	private Boolean done;
	@SerializedName("user")
	@Expose
	private User user;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTodo() {
		return todo;
	}

	public void setTodo(String todo) {
		this.todo = todo;
	}

	public Boolean getDone() {
		return done;
	}

	public void setDone(Boolean done) {
		this.done = done;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Todo{" +
			"id=" + id +
			", todo='" + todo + '\'' +
			", done=" + done +
			", user=" + user +
			'}';
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeValue(this.id);
		dest.writeString(this.todo);
		dest.writeValue(this.done);
		dest.writeParcelable(this.user, flags);
	}

	public Todo() {
	}

	protected Todo(Parcel in) {
		this.id = (Integer) in.readValue(Integer.class.getClassLoader());
		this.todo = in.readString();
		this.done = (Boolean) in.readValue(Boolean.class.getClassLoader());
		this.user = in.readParcelable(User.class.getClassLoader());
	}

	public static final Creator<Todo> CREATOR = new Creator<Todo>() {
		@Override
		public Todo createFromParcel(Parcel source) {
			return new Todo(source);
		}

		@Override
		public Todo[] newArray(int size) {
			return new Todo[size];
		}
	};
}
