package lab1;

/**
 * Created with IntelliJ IDEA.
 * User: fgheorghe
 * Date: 10/31/12
 * Time: 12:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class Pair {
    private Integer code;
    private Integer location;

    public Pair(){
        code = -1;
        location = -1;
    }

    public Pair(Integer code, Integer location) {
        this.code = code;
        this.location = location;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getLocation() {
        return location;
    }

    public void setLocation(Integer location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Pair{" +
                "code=" + code +
                ", location=" + location +
                '}';
    }
}
