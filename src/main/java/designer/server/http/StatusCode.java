package designer.server.http;

import com.fasterxml.jackson.annotation.JsonValue;

public enum StatusCode {
  OK(200),
  ERROR(500);

  private final Integer code;

  StatusCode(Integer code) {
    this.code = code;
  }

  @JsonValue
  public String getCode() {
    return code.toString();
  }
}
