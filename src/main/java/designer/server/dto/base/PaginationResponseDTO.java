package designer.server.dto.base;

public class PaginationResponseDTO<T> extends ResponseDTO<PaginationData<T>> {

  public PaginationResponseDTO(PaginationData<T> data) {
    super(data);
  }
}
