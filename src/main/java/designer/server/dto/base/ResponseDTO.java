package designer.server.dto.base;

import org.springframework.lang.Nullable;
import designer.server.http.StatusCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseDTO<T> {
  private Boolean success;
  @Nullable
  private T data;
  private StatusCode code;
  private String message;

  private ResponseDTO() {
    this.setSuccess(true);
    this.setCode(StatusCode.OK);
    this.setMessage("响应成功");
    this.setData(null);
  }

  protected ResponseDTO(T data) {
    this.setSuccess(true);
    this.setCode(StatusCode.OK);
    this.setMessage("响应成功");
    this.setData(data);
  }

  private ResponseDTO(String message) {
    this.setSuccess(false);
    this.setCode(StatusCode.ERROR);
    this.setMessage(message);
    this.setData(null);
  }

  private ResponseDTO(T data, String message) {
    this.setSuccess(false);
    this.setCode(StatusCode.ERROR);
    this.setMessage(message);
    this.setData(data);
  }

  public static ResponseDTO<Void> success() {
    return new ResponseDTO<>();
  }

  public static <T> ResponseDTO<T> success(T data) {
    return new ResponseDTO<>(data);
  }

  public static ResponseDTO<Void> failed(String message) {
    return new ResponseDTO<>(message);
  }

  public static <T> ResponseDTO<T> failed(T data, String message) {
    return new ResponseDTO<>(data, message);
  }

  public static <T> ResponseDTO<PaginationData<T>> pagination(PaginationData<T> data) {
    return new ResponseDTO<>(data);
  }
}
