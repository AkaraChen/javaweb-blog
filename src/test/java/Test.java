import com.artisan.dao.UserGet;
import lombok.SneakyThrows;

public class Test {
	@SneakyThrows
	public static void main(String[] args) {
		System.out.println(UserGet.byPost(2));
	}
}