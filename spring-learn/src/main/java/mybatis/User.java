package mybatis;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class User {
	private String id;
	private String name;
	private Integer age;
	private Date bir;
}
