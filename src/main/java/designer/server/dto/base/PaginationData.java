package designer.server.dto.base;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaginationData<T> {
  private List<T> list;

  private long current;

  private long pageSize;

  private long total;

  public static <T> PaginationData<T> createData(Page<T> page) {
    PaginationData<T> data = new PaginationData<>();
    data.setCurrent(page.getCurrent());
    data.setPageSize(page.getSize());
    data.setTotal(page.getTotal());
    data.setList(page.getRecords());
    return data;
  }
}
