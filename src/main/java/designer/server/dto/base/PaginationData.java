package designer.server.dto.base;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaginationData<T> {
  private List<T> list;

  private int current;

  private int pageSize;

  private int total;
}
