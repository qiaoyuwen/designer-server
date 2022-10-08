package designer.server.dto.base;

import org.springframework.lang.Nullable;
import designer.server.http.StatusCode;
import designer.server.http.StatusMessage;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseDTO<T> {
  private Boolean success;
  @Nullable
  private T data;
  private StatusCode code;
  private StatusMessage message;

  public ResponseDTO() {
    this.setSuccess(true);
    this.setCode(StatusCode.OK);
    this.setMessage(StatusMessage.OK);
    this.setData(null);
  }

  public ResponseDTO(T data) {
    this.setSuccess(true);
    this.setCode(StatusCode.OK);
    this.setMessage(StatusMessage.OK);
    this.setData(data);
  }

  public ResponseDTO(StatusMessage message) {
    this.setSuccess(false);
    this.setCode(StatusCode.ERROR);
    this.setMessage(message);
    this.setData(null);
  }

  public ResponseDTO(StatusMessage message, T data) {
    this.setSuccess(false);
    this.setCode(StatusCode.ERROR);
    this.setMessage(message);
    this.setData(data);
  }

  public ResponseDTO(StatusCode code, StatusMessage message) {
    this.setSuccess(false);
    this.setCode(code);
    this.setMessage(message);
    this.setData(null);
  }
}
