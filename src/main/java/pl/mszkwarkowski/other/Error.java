package pl.mszkwarkowski.other;

import io.swagger.annotations.ApiModelProperty;

/**
 * Represent exception message as object.
 */
public class Error {
    @ApiModelProperty(position = 1, required = true, notes = "Error's id.")
    private Integer status;
    @ApiModelProperty(position = 2, required = true, notes = "Error's type.")
    private String error;
    @ApiModelProperty(position = 3, required = true, notes = "Error's message.")
    private String message;

    public Error(Integer status, String error, String message) {
        this.status = status;
        this.error = error;
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
