package es.upm.miw.dasm.marvelfanatics.api.models.marvel;

public class MarvelWrapper<T> {
    private int code;
    private String status;
    private MarvelQueryResults<T> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public MarvelQueryResults<T> getData() {
        return data;
    }

    public void setData(MarvelQueryResults<T> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "MarvelWrapper{" +
                "code=" + code +
                ", status='" + status + '\'' +
                ", data=" + data +
                '}';
    }
}
