package designer.server.http;

import com.fasterxml.jackson.annotation.JsonValue;

public enum StatusMessage {
  OK("成功"),
  ERROR("失败"),
  LOGINERROR("用户名密码错误");

  private final String message;

  StatusMessage(String message) {
    this.message = message;
  }

  @JsonValue
  public String getMessage() {
    return message;
  }
}
